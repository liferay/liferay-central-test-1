/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.spring.aop;

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.extension.BaseModelExtension;
import com.liferay.portal.service.extension.ModelExtensionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="AbstractModelExtensionHandler.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Raymond Augé
 *
 */
public abstract class AbstractModelExtensionHandler
	implements ModelExtensionHandler {

	public BaseModelExtension getBaseModelExtensionClass() {
		return _baseModelExtensionClass;
	}

	public List<String> getExtensionMethodNames() {
		return _extensionMethodNames;
	}

	public void setBaseModelExtensionClass(
		BaseModelExtension baseModelExtensionClass) {

		_baseModelExtensionClass = baseModelExtensionClass;
	}

	public void setExtensionMethodNames(List<String> extensionMethodNames) {
		_extensionMethodNames = extensionMethodNames;
	}

	public class WrappedList extends ArrayList<BaseModel> {

		public WrappedList(List<BaseModel> models) {
			super(models);
		}

		public BaseModel get(int index) {
			return (BaseModel)extendSingle((BaseModel)super.get(index));
		}

	}

	private BaseModelExtension _baseModelExtensionClass;
	private List<String> _extensionMethodNames;

}
