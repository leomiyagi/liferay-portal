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

package com.liferay.jethr0.dalo;

import com.liferay.jethr0.gitbranch.GitBranch;
import com.liferay.jethr0.gitbranch.GitBranchFactory;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.ProjectFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectsToGitBranchesDALO extends BaseRelationshipDALO {

	public JSONObject createRelationship(Project project, GitBranch gitBranch) {
		return create("/o/c/projects", project.getId(), gitBranch.getId());
	}

	public void deleteRelationship(Project project, GitBranch gitBranch) {
		delete("/o/c/projects", project.getId(), gitBranch.getId());
	}

	public Set<GitBranch> retrieveGitBranches(Project project) {
		Set<GitBranch> gitBranches = new HashSet<>();

		for (JSONObject responseJSONObject :
				retrieve("/o/c/projects", project.getId())) {

			gitBranches.add(GitBranchFactory.newGitBranch(responseJSONObject));
		}

		return gitBranches;
	}

	public Set<Project> retrieveProjects(GitBranch gitBranch) {
		Set<Project> projects = new HashSet<>();

		for (JSONObject responseJSONObject :
				retrieve("/o/c/gitbranches", gitBranch.getId())) {

			projects.add(_projectFactory.newEntity(responseJSONObject));
		}

		return projects;
	}

	public void updateRelationships(GitBranch gitBranch) {
		Set<Project> remoteProjects = retrieveProjects(gitBranch);

		for (Project project : gitBranch.getProjects()) {
			if (remoteProjects.contains(project)) {
				remoteProjects.removeAll(Collections.singletonList(project));

				continue;
			}

			createRelationship(project, gitBranch);
		}

		for (Project remoteProject : remoteProjects) {
			deleteRelationship(remoteProject, gitBranch);
		}
	}

	public void updateRelationships(Project project) {
		Set<GitBranch> remoteGitBranches = retrieveGitBranches(project);

		for (GitBranch gitBranch : project.getGitBranches()) {
			if (remoteGitBranches.contains(gitBranch)) {
				remoteGitBranches.removeAll(
					Collections.singletonList(gitBranch));

				continue;
			}

			createRelationship(project, gitBranch);
		}

		for (GitBranch remoteGitBranch : remoteGitBranches) {
			deleteRelationship(project, remoteGitBranch);
		}
	}

	@Override
	protected String getObjectRelationshipName() {
		return "projectsToGitBranches";
	}

	@Autowired
	private ProjectFactory _projectFactory;

}