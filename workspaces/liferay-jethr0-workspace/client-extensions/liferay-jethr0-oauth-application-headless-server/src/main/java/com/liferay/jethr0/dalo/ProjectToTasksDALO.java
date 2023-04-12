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

import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.task.Task;
import com.liferay.jethr0.task.TaskFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectToTasksDALO extends BaseRelationshipDALO {

	public JSONObject createRelationship(Project project, Task task) {
		return create("/o/c/projects", project.getId(), task.getId());
	}

	public void deleteRelationship(Project project, Task task) {
		delete("/o/c/projects", project.getId(), task.getId());
	}

	public Set<Task> retrieveTasks(Project project) {
		Set<Task> tasks = new HashSet<>();

		for (JSONObject responseJSONObject :
				retrieve("/o/c/projects", project.getId())) {

			tasks.add(TaskFactory.newTask(project, responseJSONObject));
		}

		return tasks;
	}

	public void updateRelationships(Project project) {
		Set<Task> remoteTasks = retrieveTasks(project);

		for (Task task : project.getTasks()) {
			if (remoteTasks.contains(task)) {
				remoteTasks.removeAll(Collections.singletonList(task));

				continue;
			}

			createRelationship(project, task);
		}

		for (Task remoteTask : remoteTasks) {
			deleteRelationship(project, remoteTask);
		}
	}

	@Override
	protected String getObjectRelationshipName() {
		return "projectToTasks";
	}

}