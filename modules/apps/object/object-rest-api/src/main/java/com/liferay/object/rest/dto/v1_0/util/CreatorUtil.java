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

package com.liferay.object.rest.dto.v1_0.util;

import com.liferay.headless.delivery.dto.v1_0.Creator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 * @author Jürgen Kappler
 */
public class CreatorUtil {

	public static Creator toCreator(Portal portal, UriInfo uriInfo, User user) {
		if ((user == null) || user.isGuestUser()) {
			return null;
		}

		return new Creator() {
			{
				additionalName = user.getMiddleName();
				contentType = "UserAccount";
				familyName = user.getLastName();
				givenName = user.getFirstName();
				id = user.getUserId();
				name = user.getFullName();

				setImage(
					() -> {
						if (user.getPortraitId() == 0) {
							return null;
						}

						ThemeDisplay themeDisplay = new ThemeDisplay() {
							{
								setPathImage(portal.getPathImage());
							}
						};

						return user.getPortraitURL(themeDisplay);
					});
				setProfileURL(
					() -> {
						if (uriInfo == null) {
							return null;
						}

						MultivaluedMap<String, String> queryParameters =
							uriInfo.getQueryParameters();

						String nestedFields = queryParameters.getFirst(
							"nestedFields");

						if ((nestedFields == null) ||
							!nestedFields.contains("profileURL")) {

							return null;
						}

						Group group = user.getGroup();

						ThemeDisplay themeDisplay = new ThemeDisplay() {
							{
								setPortalURL(StringPool.BLANK);
								setSiteGroupId(group.getGroupId());
							}
						};

						return group.getDisplayURL(themeDisplay);
					});
			}
		};
	}

}