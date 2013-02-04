/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.trash.model.TrashEntry;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;

/**
 * Provides the base implementation of {@link TrashHandler}.
 *
 * @author Alexander Chow
 * @author Zsolt Berentey
 * @see    {@link TrashHandler}
 */
public abstract class BaseTrashHandler implements TrashHandler {

	/**
	 * Checks if a duplicate trash entry already exists in the destination
	 * container.
	 *
	 * <p>
	 * This method is used to check for duplicates when a trash entry is being
	 * restored or moved out of the Recycle Bin.
	 * </p>
	 *
	 * @param  trashEntry the trash entry to check
	 * @param  containerModelId the primary key of the destination (e.g. folder)
	 * @param  newName the new name to be assigned to the trash entry
	 *         (optionally <code>null</code> to forego renaming the trash entry)
	 * @throws PortalException if a duplicate trash entry already existed in the
	 *         destination container
	 * @throws SystemException if a system exception occurred
	 */
	public void checkDuplicateTrashEntry(
			TrashEntry trashEntry, long containerModelId, String newName)
		throws PortalException, SystemException {
	}

	public void deleteTrashEntries(long[] classPKs)
		throws PortalException, SystemException {

		deleteTrashEntries(classPKs, true);
	}

	public void deleteTrashEntry(long classPK)
		throws PortalException, SystemException {

		deleteTrashEntries(new long[] {classPK});
	}

	public void deleteTrashEntry(long classPK, boolean checkPermission)
		throws PortalException, SystemException {

		deleteTrashEntries(new long[] {classPK}, checkPermission);
	}

	/**
	 * Returns the container model with the primary key.
	 *
	 * @param  containerModelId the primary key of the container model
	 * @return the container model with the primary key
	 * @throws PortalException if a container model with the primary key could
	 *         not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ContainerModel getContainerModel(long containerModelId)
		throws PortalException, SystemException {

		return null;
	}

	public String getContainerModelClassName() {
		return StringPool.BLANK;
	}

	public String getContainerModelName() {
		return StringPool.BLANK;
	}

	/**
	 * Returns a range of all the container models that are children of the
	 * parent container model identified by the container model ID. These
	 * container models must be able to contain the entity identified by the
	 * primary key.
	 *
	 * <p>
	 * This method checks for the view permission when retrieving the container
	 * models.
	 * </p>
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. The <code>start</code> and <code>end</code>
	 * values are not primary keys but, rather, indexes in the result set. Thus,
	 * <code>0</code> refers to the first result in the set. Setting both
	 * <code>start</code> and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  classPK the primary key of an entity the container models must be
	 *         able to contain
	 * @param  containerModelId the primary key of the parent container model
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of matching container models
	 * @throws PortalException if a trash entry with the primary key could not
	 *         be found
	 * @throws SystemException if a system exception occurred
	 */
	public List<ContainerModel> getContainerModels(
			long classPK, long containerModelId, int start, int end)
		throws PortalException, SystemException {

		return Collections.emptyList();
	}

	/**
	 * Returns the number of container models that are children of the parent
	 * container model identified by the container model ID. These container
	 * models must be able to contain the entity identified by the primary key.
	 *
	 * <p>
	 * This method checks for the view permission when counting the container
	 * models.
	 * </p>
	 *
	 * @param  classPK the primary key of an entity the container models must be
	 *         able to contain
	 * @param  containerModelId the primary key of the parent container model
	 * @return the number of matching container models
	 * @throws PortalException if a trash entry with the primary key could not
	 *         be found
	 * @throws SystemException if a system exception occurred
	 */
	public int getContainerModelsCount(long classPK, long containerModelId)
		throws PortalException, SystemException {

		return 0;
	}

	public String getDeleteMessage() {
		return "deleted-in-x";
	}

	/**
	 * @throws PortalException
	 * @throws SystemException if a system exception occurred
	 */
	public ContainerModel getParentContainerModel(long classPK)
		throws PortalException, SystemException {

		return null;
	}

	/**
	 * @throws PortalException
	 * @throws SystemException if a system exception occurred
	 */
	public List<ContainerModel> getParentContainerModels(long classPK)
		throws PortalException, SystemException {

		return Collections.emptyList();
	}

	/**
	 * Returns the link to the location to which the trash entry was restored.
	 *
	 * @param  portletRequest the portlet request
	 * @param  classPK the primary key of the restored trash entry
	 * @return the restore link
	 * @throws PortalException if a trash entry with the primary key could not
	 *         be found
	 * @throws SystemException if a system exception occurred
	 */
	public String getRestoreLink(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		return StringPool.BLANK;
	}

	/**
	 * Returns the message describing the location to which the trash entry was
	 * restored.
	 *
	 * @param  portletRequest the portlet request
	 * @param  classPK the primary key of the restored trash entry
	 * @return the restore message
	 * @throws PortalException if a trash entry with the primary key could not
	 *         be found
	 * @throws SystemException if a system exception occurred
	 */
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		return StringPool.BLANK;
	}

	public String getRootContainerModelName() {
		return StringPool.BLANK;
	}

	public String getSubcontainerModelName() {
		return StringPool.BLANK;
	}

	public String getTrashContainedModelName() {
		return StringPool.BLANK;
	}

	/**
	 * @throws PortalException
	 * @throws SystemException if a system exception occurred
	 */
	public int getTrashContainedModelsCount(long classPK)
		throws PortalException, SystemException {

		return 0;
	}

	/**
	 * @throws PortalException
	 * @throws SystemException if a system exception occurred
	 */
	public List<TrashRenderer> getTrashContainedModelTrashRenderers(
			long classPK, int start, int end)
		throws PortalException, SystemException {

		return Collections.emptyList();
	}

	public String getTrashContainerModelName() {
		return StringPool.BLANK;
	}

	/**
	 * @throws PortalException
	 * @throws SystemException if a system exception occurred
	 */
	public int getTrashContainerModelsCount(long classPK)
		throws PortalException, SystemException {

		return 0;
	}

	/**
	 * @throws PortalException
	 * @throws SystemException if a system exception occurred
	 */
	public List<TrashRenderer> getTrashContainerModelTrashRenderers(
			long classPK, int start, int end)
		throws PortalException, SystemException {

		return Collections.emptyList();
	}

	public TrashRenderer getTrashRenderer(long classPK)
		throws PortalException, SystemException {

		AssetRendererFactory assetRendererFactory = getAssetRendererFactory();

		if (assetRendererFactory != null) {
			AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
				classPK);

			if (assetRenderer instanceof TrashRenderer) {
				return (TrashRenderer)assetRenderer;
			}
		}

		return null;
	}

	public boolean hasTrashPermission(
			PermissionChecker permissionChecker, long groupId, long classPK,
			String trashActionId)
		throws PortalException, SystemException {

		String actionId = trashActionId;

		if (trashActionId.equals(ActionKeys.DELETE)) {
			actionId = ActionKeys.DELETE;
		}
		else if (trashActionId.equals(TrashActionKeys.OVERWRITE)) {
			actionId = ActionKeys.DELETE;
		}
		else if (trashActionId.equals(TrashActionKeys.MOVE)) {
			return false;
		}
		else if (trashActionId.equals(TrashActionKeys.RENAME)) {
			actionId = ActionKeys.UPDATE;
		}
		else if (trashActionId.equals(TrashActionKeys.RESTORE)) {
			actionId = ActionKeys.DELETE;
		}

		return hasPermission(permissionChecker, classPK, actionId);
	}

	public boolean isContainerModel() {
		return false;
	}

	public boolean isDeletable() {
		return true;
	}

	/**
	 * @throws PortalException
	 * @throws SystemException if a system exception occurred
	 */
	public boolean isInTrashContainer(long classPK)
		throws PortalException, SystemException {

		return false;
	}

	public boolean isMovable() {
		return false;
	}

	/**
	 * Returns <code>true</code> if the trash entry can be restored to its
	 * original location.
	 *
	 * <p>
	 * This method usually returns <code>false</code> if the container (e.g.
	 * folder) of the trash entry is no longer available (e.g. moved to the
	 * Recycle Bin or deleted).
	 * </p>
	 *
	 * @param  classPK the primary key of the trash entry
	 * @return <code>true</code> if the trash entry can be restored to its
	 *         original location; <code>false</code> otherwise
	 * @throws PortalException if a trash entry with the primary key could not
	 *         be found
	 * @throws SystemException if a system exception occurred
	 */
	public boolean isRestorable(long classPK)
		throws PortalException, SystemException {

		return true;
	}

	/**
	 * @throws PortalException
	 * @throws SystemException
	 */
	public void moveEntry(
			long classPK, long containerModelId, ServiceContext serviceContext)
		throws PortalException, SystemException {
	}

	public void moveTrashEntry(
			long classPK, long containerModelId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (isRestorable(classPK)) {
			restoreTrashEntry(classPK);
		}

		_log.error("moveTrashEntry() is not implemented in " +
			getClass().getName());

		throw new SystemException();
	}

	/**
	 * Restores the model entity thas is related to a model entity (i.e., an
	 * attachment)
	 * 
	 * @param className the class name of the model entity related
	 * @param classPK the primary key of the model entity related
	 * @throws PortalException if a model entity with the primary key could not
	 *         be found
	 * @throws SystemException if a system exception occurred
	 */
	public void restoreRelatedTrashEntry(String className, long classPK)
		throws PortalException, SystemException {
	}

	public void restoreTrashEntry(long classPK)
		throws PortalException, SystemException {

		restoreTrashEntries(new long[] {classPK});
	}

	/**
	 * Updates the title of the trash entry with the primary key. This method is
	 * called by {@link com.liferay.portlet.trash.action.EditEntryAction} before
	 * restoring the trash entry via its restore rename action.
	 *
	 * @param  classPK the primary key of the trash entry
	 * @param  title the title to be assigned
	 * @throws PortalException if a trash entry with the primary key could not
	 *         be found
	 * @throws SystemException if a system exception occurred
	 */
	public void updateTitle(long classPK, String title)
		throws PortalException, SystemException {
	}

	protected AssetRendererFactory getAssetRendererFactory() {
		return AssetRendererFactoryRegistryUtil.
			getAssetRendererFactoryByClassName(getClassName());
	}

	protected abstract boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException, SystemException;

	private static Log _log = LogFactoryUtil.getLog(BaseTrashHandler.class);

}