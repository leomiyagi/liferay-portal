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

package com.liferay.account.admin.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.account.admin.web.internal.constants.AccountScreenNavigationEntryConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "screen.navigation.category.order:Integer=20",
	service = ScreenNavigationCategory.class
)
public class AccountRoleDefinePermissionsScreenNavigationCategory
	extends BaseAccountRoleDefinePermissionsScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return AccountScreenNavigationEntryConstants.
			CATEGORY_KEY_DEFINE_PERMISSIONS;
	}

}