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

package com.liferay.jenkins.results.parser;

/**
 * @author Peter Yoo
 */
public class RemoteGitRepository extends BaseGitRepository {

	public String getHostname() {
		return getFromJSONObjectString("hostname");
	}

	public String getRemoteURL() {
		return JenkinsResultsParserUtil.combine(
			"git@", getHostname(), ":", getUsername(), "/", getName());
	}

	public String getUsername() {
		return getFromJSONObjectString("username");
	}

	protected RemoteGitRepository(GitRemote gitRemote) {
		this(
			gitRemote.getHostname(), gitRemote.getGitRepositoryName(),
			gitRemote.getUsername());
	}

	protected RemoteGitRepository(
		String hostname, String gitRepositoryName, String username) {

		super(gitRepositoryName);

		if ((hostname == null) || hostname.isEmpty()) {
			throw new IllegalArgumentException("Hostname is null");
		}

		if ((username == null) || username.isEmpty()) {
			throw new IllegalArgumentException("Username is null");
		}

		putIntoJSONObject("hostname", hostname);
		putIntoJSONObject("username", username);

		validateJSONObject(_REQUIRED_KEYS);
	}

	private static final String[] _REQUIRED_KEYS = {"hostname", "username"};

}