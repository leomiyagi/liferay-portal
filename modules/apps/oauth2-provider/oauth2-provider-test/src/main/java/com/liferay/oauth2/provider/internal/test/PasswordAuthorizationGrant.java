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

package com.liferay.oauth2.provider.internal.test;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

/**
 * @author Arthur Chan
 */
public class PasswordAuthorizationGrant implements AuthorizationGrant {

	public PasswordAuthorizationGrant(String userName, String password) {
		_authorizationGrantParameters.add("grant_type", "password");
		_authorizationGrantParameters.add("password", password);
		_authorizationGrantParameters.add("username", userName);
	}

	@Override
	public MultivaluedMap<String, String> getAuthorizationGrantParameters() {
		return _authorizationGrantParameters;
	}

	private final MultivaluedMap<String, String> _authorizationGrantParameters =
		new MultivaluedHashMap<>();

}