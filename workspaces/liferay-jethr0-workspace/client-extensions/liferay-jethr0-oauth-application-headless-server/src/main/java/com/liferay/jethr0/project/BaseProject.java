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

package com.liferay.jethr0.project;

import com.liferay.jethr0.gitbranch.GitBranch;
import com.liferay.jethr0.testsuite.TestSuite;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseProject implements Project {

	@Override
	public void addTestSuite(TestSuite testSuite) {
		addTestSuites(Arrays.asList(testSuite));
	}

	@Override
	public void addTestSuites(List<TestSuite> testSuites) {
		for (TestSuite testSuite : testSuites) {
			if (_testSuites.contains(testSuite)) {
				continue;
			}

			_testSuites.add(testSuite);
		}
	}

	@Override
	public Date getCreatedDate() {
		return _createdDate;
	}

	@Override
	public long getId() {
		return _id;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = new JSONObject();

		Project.State state = getState();
		Project.Type type = getType();

		jsonObject.put(
			"dateCreated", _simpleDateFormat.format(getCreatedDate())
		).put(
			"id", getId()
		).put(
			"name", getName()
		).put(
			"priority", getPriority()
		).put(
			"state", state.getJSONObject()
		).put(
			"type", type.getJSONObject()
		);

		return jsonObject;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public int getPriority() {
		return _priority;
	}

	@Override
	public State getState() {
		return _state;
	}

	@Override
	public List<TestSuite> getTestSuites() {
		return _testSuites;
	}

	@Override
	public Type getType() {
		return _type;
	}

	@Override
	public void removeTestSuite(TestSuite testSuite) {
		_testSuites.remove(testSuite);
	}

	public void removeTestSuites(List<TestSuite> testSuites) {
		_testSuites.removeAll(testSuites);
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setPriority(int priority) {
		_priority = priority;
	}

	@Override
	public void setState(State state) {
		_state = state;
	}

	@Override
	public String toString() {
		return String.valueOf(getJSONObject());
	}

	protected BaseProject(JSONObject jsonObject) {
		try {
			_createdDate = _simpleDateFormat.parse(
				jsonObject.getString("dateCreated"));
		}
		catch (ParseException parseException) {
			throw new RuntimeException(parseException);
		}

		_id = jsonObject.getLong("id");
		_name = jsonObject.getString("name");
		_priority = jsonObject.optInt("priority");
		_state = State.get(jsonObject.getJSONObject("state"));
		_type = Type.get(jsonObject.getJSONObject("type"));
	}

	private static final SimpleDateFormat _simpleDateFormat =
		new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

	private final Date _createdDate;
	private final long _id;
	private String _name;
	private int _priority;
	private State _state;
	private final List<TestSuite> _testSuites = new ArrayList<>();
	private final Type _type;

}