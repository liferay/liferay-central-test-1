/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.expando.model;


/**
 * <a href="ExpandoValue.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the <code>ExpandoValue</code> table
 * in the database.
 * </p>
 *
 * <p>
 * Customize <code>com.liferay.portlet.expando.service.model.impl.ExpandoValueImpl</code>
 * and rerun the ServiceBuilder to generate the new methods.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.expando.service.model.ExpandoValueModel
 * @see com.liferay.portlet.expando.service.model.impl.ExpandoValueImpl
 * @see com.liferay.portlet.expando.service.model.impl.ExpandoValueModelImpl
 *
 */
public interface ExpandoValue extends ExpandoValueModel {
	public boolean getBoolean()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setBoolean(boolean data)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public boolean[] getBooleanArray()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setBooleanArray(boolean[] data)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.util.Date getDate()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setDate(java.util.Date data)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.util.Date[] getDateArray()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setDateArray(java.util.Date[] data)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public double getDouble()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setDouble(double data)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public double[] getDoubleArray()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setDoubleArray(double[] data)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public float getFloat()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setFloat(float data)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public float[] getFloatArray()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setFloatArray(float[] data)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public int getInteger()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setInteger(int data)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public int[] getIntegerArray()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setIntegerArray(int[] data)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public long getLong()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setLong(long data)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public long[] getLongArray()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setLongArray(long[] data)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public short getShort()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setShort(short data)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public short[] getShortArray()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setShortArray(short[] data)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.lang.String getString()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setString(java.lang.String data)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.lang.String[] getStringArray()
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setStringArray(java.lang.String[] data)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;
}