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

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Arrays;

/**
 * @author Drew Brokke
 */
public class UpgradeUsersUserGroups extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String[] expectedPrimaryKeyColumnNames = {
			"userId", "userGroupId", "ctCollectionId"
		};

		if (Arrays.equals(
				expectedPrimaryKeyColumnNames,
				getPrimaryKeyColumnNames(connection, "Users_UserGroups"))) {

			return;
		}

		removePrimaryKey("Users_UserGroups");

		runSQL(
			String.format(
				"alter table Users_UserGroups add primary key (%s)",
				ArrayUtil.toString(
					expectedPrimaryKeyColumnNames, (String)null)));
	}

}