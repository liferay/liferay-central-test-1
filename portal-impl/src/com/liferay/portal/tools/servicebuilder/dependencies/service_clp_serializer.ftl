package ${packagePath}.service;

<#list entities as entity>
	<#if entity.hasColumns()>
		import ${packagePath}.model.${entity.name}Clp;
	</#if>
</#list>

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.BaseModel;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClpSerializer {

	public static final String SERVLET_CONTEXT_NAME = "${pluginName}";

	public static void setClassLoader(ClassLoader classLoader) {
		_classLoader = classLoader;
	}

	public static Object translateInput(BaseModel<?> oldModel) {
		Class<?> oldModelClass = oldModel.getClass();

		String oldModelClassName = oldModelClass.getName();

		<#list entities as entity>
			<#if entity.hasColumns()>
				if (oldModelClassName.equals(${entity.name}Clp.class.getName())) {
					${entity.name}Clp oldCplModel = (${entity.name}Clp)oldModel;

					ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

					try {
						Thread.currentThread().setContextClassLoader(_classLoader);

						try {
							Class<?> newModelClass = Class.forName("${packagePath}.model.impl.${entity.name}Impl", true, _classLoader);

							Object newModel = newModelClass.newInstance();

							<#list entity.regularColList as column>
								Method method${column_index} = newModelClass.getMethod("set${column.methodName}", new Class[] {
									<#if column.isPrimitiveType()>
										${serviceBuilder.getPrimitiveObj(column.type)}.TYPE
									<#else>
										${column.type}.class
									</#if>
								});

								<#if column.isPrimitiveType()>
									${serviceBuilder.getPrimitiveObj(column.type)}
								<#else>
									${column.type}
								</#if>

								value${column_index} =

								<#if column.isPrimitiveType()>
									new ${serviceBuilder.getPrimitiveObj(column.type)}(
								</#if>

								oldCplModel.get${column.methodName}()

								<#if column.isPrimitiveType()>
									)
								</#if>

								;

								method${column_index}.invoke(newModel, value${column_index});
							</#list>

							return newModel;
						}
						catch (Exception e) {
							_log.error(e, e);
						}
					}
					finally {
						Thread.currentThread().setContextClassLoader(contextClassLoader);
					}
				}
			</#if>
		</#list>

		return oldModel;
	}

	public static Object translateInput(List<Object> oldList) {
		List<Object> newList = new ArrayList<Object>(oldList.size());

		for (int i = 0; i < oldList.size(); i++) {
			Object curObj = oldList.get(i);

			newList.add(translateInput(curObj));
		}

		return newList;
	}

	public static Object translateInput(Object obj) {
		if (obj instanceof BaseModel<?>) {
			return translateInput((BaseModel<?>)obj);
		}
		else if (obj instanceof List<?>) {
			return translateInput((List<Object>)obj);
		}
		else {
			return obj;
		}
	}

	public static Object translateOutput(BaseModel<?> oldModel) {
		Class<?> oldModelClass = oldModel.getClass();

		String oldModelClassName = oldModelClass.getName();

		<#list entities as entity>
			<#if entity.hasColumns()>
				if (oldModelClassName.equals("${packagePath}.model.impl.${entity.name}Impl")) {
					ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

					try {
						Thread.currentThread().setContextClassLoader(_classLoader);

						try {
							${entity.name}Clp newModel = new ${entity.name}Clp();

							<#list entity.regularColList as column>
								Method method${column_index} = oldModelClass.getMethod("get${column.methodName}");

								<#if column.isPrimitiveType()>
									${serviceBuilder.getPrimitiveObj(column.type)}
								<#else>
									${column.type}
								</#if>

								value${column_index} =

								(

								<#if column.isPrimitiveType()>
									${serviceBuilder.getPrimitiveObj(column.type)}
								<#else>
									${column.type}
								</#if>

								)

								method${column_index}.invoke(oldModel, (Object[])null);

								newModel.set${column.methodName}(value${column_index});
							</#list>

							return newModel;
						}
						catch (Exception e) {
							_log.error(e, e);
						}
					}
					finally {
						Thread.currentThread().setContextClassLoader(contextClassLoader);
					}
				}
			</#if>
		</#list>

		return oldModel;
	}

	public static Object translateOutput(List<Object> oldList) {
		List<Object> newList = new ArrayList<Object>(oldList.size());

		for (int i = 0; i < oldList.size(); i++) {
			Object curObj = oldList.get(i);

			newList.add(translateOutput(curObj));
		}

		return newList;
	}

	public static Object translateOutput(Object obj) {
		if (obj instanceof BaseModel<?>) {
			return translateOutput((BaseModel<?>)obj);
		}
		else if (obj instanceof List<?>) {
			return translateOutput((List<Object>)obj);
		}
		else {
			return obj;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ClpSerializer.class);

	private static ClassLoader _classLoader;

}