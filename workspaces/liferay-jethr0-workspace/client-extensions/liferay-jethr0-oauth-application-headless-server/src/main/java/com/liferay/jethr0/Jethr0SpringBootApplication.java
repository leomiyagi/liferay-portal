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

package com.liferay.jethr0;

import com.liferay.client.extension.util.spring.boot.ClientExtensionUtilSpringBootComponentScan;
import com.liferay.client.extension.util.spring.boot.LiferayOAuth2Util;
import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.build.queue.BuildQueue;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.comparator.ProjectComparator;
import com.liferay.jethr0.project.prioritizer.ProjectPrioritizer;
import com.liferay.jethr0.project.queue.ProjectQueue;
import com.liferay.jethr0.project.repository.ProjectComparatorRepository;
import com.liferay.jethr0.project.repository.ProjectPrioritizerRepository;
import com.liferay.jethr0.project.repository.ProjectRepository;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

/**
 * @author Michael Hashimoto
 */
@Import(ClientExtensionUtilSpringBootComponentScan.class)
@SpringBootApplication
public class Jethr0SpringBootApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext configurableApplicationContext =
			SpringApplication.run(Jethr0SpringBootApplication.class, args);

		JenkinsQueue jenkinsQueue = configurableApplicationContext.getBean(
			JenkinsQueue.class);

		jenkinsQueue.update();

		jenkinsQueue.invoke();
	}

	@Bean
	public ActiveMQConnectionFactory getActiveMQConnectionFactory() {
		ActiveMQConnectionFactory activeMQConnectionFactory =
			new ActiveMQConnectionFactory();

		activeMQConnectionFactory.setBrokerURL(_jmsBrokerURL);
		activeMQConnectionFactory.setPassword(_jmsUserPassword);
		activeMQConnectionFactory.setUserName(_jmsUserName);

		return activeMQConnectionFactory;
	}

	@Bean
	public BuildQueue getBuildQueue(ProjectQueue projectQueue) {
		BuildQueue buildQueue = new BuildQueue();

		buildQueue.setProjectQueue(projectQueue);

		return buildQueue;
	}

	@Bean
	public JmsListenerContainerFactory getJmsListenerContainerFactory(
		ActiveMQConnectionFactory activeMQConnectionFactory) {

		DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory =
			new DefaultJmsListenerContainerFactory();

		defaultJmsListenerContainerFactory.setConnectionFactory(
			activeMQConnectionFactory);

		return defaultJmsListenerContainerFactory;
	}

	@Bean
	public JmsTemplate getJmsTemplate(ConnectionFactory connectionFactory) {
		JmsTemplate jmsTemplate = new JmsTemplate();

		jmsTemplate.setConnectionFactory(connectionFactory);
		jmsTemplate.setDefaultDestinationName(_jmsJenkinsBuildQueue);

		return jmsTemplate;
	}

	@Bean
	public OAuth2AccessToken getOAuth2AccessToken(
		AuthorizedClientServiceOAuth2AuthorizedClientManager
			authorizedClientServiceOAuth2AuthorizedClientManager) {

		return LiferayOAuth2Util.getOAuth2AccessToken(
			authorizedClientServiceOAuth2AuthorizedClientManager,
			_liferayOAuthApplicationExternalReferenceCodes);
	}

	@Bean
	public ProjectQueue getProjectQueue(
		ProjectComparatorRepository projectComparatorRepository,
		ProjectPrioritizerRepository projectPrioritizerRepository,
		ProjectRepository projectRepository) {

		ProjectQueue projectQueue = new ProjectQueue();

		projectQueue.setProjectPrioritizer(
			_getDefaultProjectPrioritizer(
				projectComparatorRepository, projectPrioritizerRepository));

		projectQueue.addProjects(
			projectRepository.getByStates(
				Project.State.QUEUED, Project.State.RUNNING));

		for (Project project : projectQueue.getProjects()) {
			System.out.println(project);

			for (Build build : project.getBuilds()) {
				System.out.println("> " + build);
			}
		}

		return projectQueue;
	}

	private ProjectPrioritizer _getDefaultProjectPrioritizer(
		ProjectComparatorRepository projectComparatorRepository,
		ProjectPrioritizerRepository projectPrioritizerRepository) {

		ProjectPrioritizer projectPrioritizer =
			projectPrioritizerRepository.getByName(_liferayProjectPrioritizer);

		if (projectPrioritizer != null) {
			return projectPrioritizer;
		}

		projectPrioritizer = projectPrioritizerRepository.add(
			_liferayProjectPrioritizer);

		projectComparatorRepository.add(
			projectPrioritizer, 1, ProjectComparator.Type.PROJECT_PRIORITY,
			null);
		projectComparatorRepository.add(
			projectPrioritizer, 2, ProjectComparator.Type.FIFO, null);

		return projectPrioritizer;
	}

	@Value("${jms.broker.url}")
	private String _jmsBrokerURL;

	@Value("${jms.jenkins.build.queue}")
	private String _jmsJenkinsBuildQueue;

	@Value("${jms.user.name}")
	private String _jmsUserName;

	@Value("${jms.user.password}")
	private String _jmsUserPassword;

	@Value("${liferay.oauth.application.external.reference.codes}")
	private String _liferayOAuthApplicationExternalReferenceCodes;

	@Value("${liferay.jethr0.project.prioritizer}")
	private String _liferayProjectPrioritizer;

}