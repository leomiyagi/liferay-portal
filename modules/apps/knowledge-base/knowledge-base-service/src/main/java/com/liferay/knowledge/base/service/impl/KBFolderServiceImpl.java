/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.knowledge.base.service.impl;

import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.base.KBFolderServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=kb",
		"json.web.service.context.path=KBFolder"
	},
	service = AopService.class
)
public class KBFolderServiceImpl extends KBFolderServiceBaseImpl {

	@Override
	public KBFolder addKBFolder(
			String externalReferenceCode, long groupId,
			long parentResourceClassNameId, long parentResourcePrimKey,
			String name, String description, ServiceContext serviceContext)
		throws PortalException {

		ModelResourcePermissionUtil.check(
			_kbFolderModelResourcePermission, getPermissionChecker(), groupId,
			parentResourcePrimKey, KBActionKeys.ADD_KB_FOLDER);

		return kbFolderLocalService.addKBFolder(
			externalReferenceCode, getUserId(), groupId,
			parentResourceClassNameId, parentResourcePrimKey, name, description,
			serviceContext);
	}

	@Override
	public KBFolder deleteKBFolder(long kbFolderId) throws PortalException {
		_kbFolderModelResourcePermission.check(
			getPermissionChecker(), kbFolderId, KBActionKeys.DELETE);

		return kbFolderLocalService.deleteKBFolder(kbFolderId);
	}

	@Override
	public KBFolder fetchFirstChildKBFolder(long groupId, long kbFolderId)
		throws PortalException {

		return fetchFirstChildKBFolder(groupId, kbFolderId, null);
	}

	@Override
	public KBFolder fetchFirstChildKBFolder(
			long groupId, long kbFolderId,
			OrderByComparator<KBFolder> orderByComparator)
		throws PortalException {

		List<KBFolder> kbFolders = kbFolderPersistence.filterFindByG_P(
			groupId, kbFolderId, 0, 1, orderByComparator);

		if (kbFolders.isEmpty()) {
			return null;
		}

		return kbFolders.get(0);
	}

	@Override
	public KBFolder fetchKBFolder(long kbFolderId) throws PortalException {
		KBFolder kbFolder = kbFolderLocalService.fetchKBFolder(kbFolderId);

		if (kbFolder != null) {
			_kbFolderModelResourcePermission.check(
				getPermissionChecker(), kbFolder, KBActionKeys.VIEW);
		}

		return kbFolder;
	}

	@Override
	public KBFolder fetchKBFolderByUrlTitle(
			long groupId, long parentKbFolderId, String urlTitle)
		throws PortalException {

		KBFolder kbFolder = kbFolderLocalService.fetchKBFolderByUrlTitle(
			groupId, parentKbFolderId, urlTitle);

		if (kbFolder == null) {
			return null;
		}

		_kbFolderModelResourcePermission.check(
			getPermissionChecker(), kbFolder, KBActionKeys.VIEW);

		return kbFolder;
	}

	@Override
	public KBFolder getKBFolder(long kbFolderId) throws PortalException {
		_kbFolderModelResourcePermission.check(
			getPermissionChecker(), kbFolderId, KBActionKeys.VIEW);

		return kbFolderLocalService.getKBFolder(kbFolderId);
	}

	@Override
	public KBFolder getKBFolderByExternalReferenceCode(
			long groupId, String externalReferenceCode)
		throws PortalException {

		KBFolder kbFolder =
			kbFolderLocalService.getKBFolderByExternalReferenceCode(
				externalReferenceCode, groupId);

		_kbFolderModelResourcePermission.check(
			getPermissionChecker(), kbFolder, KBActionKeys.VIEW);

		return kbFolder;
	}

	@Override
	public KBFolder getKBFolderByUrlTitle(
			long groupId, long parentKbFolderId, String urlTitle)
		throws PortalException {

		KBFolder kbFolder = kbFolderLocalService.getKBFolderByUrlTitle(
			groupId, parentKbFolderId, urlTitle);

		_kbFolderModelResourcePermission.check(
			getPermissionChecker(), kbFolder, KBActionKeys.VIEW);

		return kbFolder;
	}

	@Override
	public List<KBFolder> getKBFolders(
			long groupId, long parentKBFolderId, int start, int end)
		throws PortalException {

		return kbFolderPersistence.filterFindByG_P(
			groupId, parentKBFolderId, start, end);
	}

	@Override
	public List<Object> getKBFoldersAndKBArticles(
		long groupId, long parentResourcePrimKey, int status, int start,
		int end, OrderByComparator<?> orderByComparator) {

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(
			status, start, end, orderByComparator);

		return kbFolderFinder.filterFindF_A_ByG_P(
			groupId, parentResourcePrimKey, queryDefinition);
	}

	@Override
	public int getKBFoldersAndKBArticlesCount(
		long groupId, long parentResourcePrimKey, int status) {

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(status);

		return kbFolderFinder.filterCountF_A_ByG_P(
			groupId, parentResourcePrimKey, queryDefinition);
	}

	@Override
	public int getKBFoldersCount(long groupId, long parentKBFolderId)
		throws PortalException {

		return kbFolderPersistence.filterCountByG_P(groupId, parentKBFolderId);
	}

	@Override
	public void moveKBFolder(long kbFolderId, long parentKBFolderId)
		throws PortalException {

		_kbFolderModelResourcePermission.check(
			getPermissionChecker(), kbFolderId, KBActionKeys.MOVE_KB_FOLDER);

		kbFolderLocalService.moveKBFolder(kbFolderId, parentKBFolderId);
	}

	@Override
	public KBFolder updateKBFolder(
			long parentResourceClassNameId, long parentResourcePrimKey,
			long kbFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		_kbFolderModelResourcePermission.check(
			getPermissionChecker(), kbFolderId, KBActionKeys.UPDATE);

		return kbFolderLocalService.updateKBFolder(
			parentResourceClassNameId, parentResourcePrimKey, kbFolderId, name,
			description, serviceContext);
	}

	@Reference(
		target = "(model.class.name=com.liferay.knowledge.base.model.KBFolder)"
	)
	private ModelResourcePermission<KBFolder> _kbFolderModelResourcePermission;

}