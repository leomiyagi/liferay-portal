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

package com.liferay.account.admin.web.internal.security.permission.resource;

import com.liferay.account.model.AccountRole;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Pei-Jung Lan
 */
public class AccountRolePermission {

	public static boolean contains(
		PermissionChecker permissionChecker, AccountRole accountRole,
		String actionId) {

		try {
			ModelResourcePermission<AccountRole> modelResourcePermission =
				_accountRoleModelResourcePermissionSnapshot.get();

			return modelResourcePermission.contains(
				permissionChecker, accountRole, actionId);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return false;
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long accountRoleId,
		String actionId) {

		try {
			ModelResourcePermission<AccountRole> modelResourcePermission =
				_accountRoleModelResourcePermissionSnapshot.get();

			return modelResourcePermission.contains(
				permissionChecker, accountRoleId, actionId);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountRolePermission.class);

	private static final Snapshot<ModelResourcePermission<AccountRole>>
		_accountRoleModelResourcePermissionSnapshot = new Snapshot<>(
			AccountRolePermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.account.model.AccountRole)");

}