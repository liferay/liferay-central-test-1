package ${packagePath}.service.persistence;

import ${packagePath}.model.${entity.name};
import ${packagePath}.service.${entity.name}LocalServiceUtil;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.BaseActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

/**
 * @author ${author}
 * @generated
 */
public class ${entity.name}ExportActionableDynamicQuery extends ${entity.name}ActionableDynamicQuery {

	public ${entity.name}ExportActionableDynamicQuery(PortletDataContext portletDataContext) throws SystemException {
		_portletDataContext = portletDataContext;

		setGroupId(_portletDataContext.getScopeGroupId());
	}

	@Override
	protected void addCriteria(DynamicQuery dynamicQuery) {
		_portletDataContext.addDateRangeCriteria(dynamicQuery, "modifiedDate");
	}

	@Override
	@SuppressWarnings("unused")
	protected void performAction(Object object) throws PortalException, SystemException {
		${entity.name} stagedModel = (${entity.name})object;

		StagedModelDataHandlerUtil.exportStagedModel(_portletDataContext, stagedModel);
	}

	private PortletDataContext _portletDataContext;

}