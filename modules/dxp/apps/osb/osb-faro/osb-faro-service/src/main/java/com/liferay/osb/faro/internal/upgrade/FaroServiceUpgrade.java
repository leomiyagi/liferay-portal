/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.internal.upgrade;

import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Matthew Kong
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class FaroServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "1.1.0",
			new com.liferay.osb.faro.internal.upgrade.v1_1_0.
				UpgradeFaroProject());
		registry.register(
			"1.1.0", "2.0.0",
			new com.liferay.osb.faro.internal.upgrade.v2_0_0.
				UpgradeFaroProject());
		registry.register(
			"2.0.0", "3.0.0",
			new com.liferay.osb.faro.internal.upgrade.v3_0_0.
				UpgradeFaroProject());
		registry.register(
			"3.0.0", "4.0.0",
			new com.liferay.osb.faro.internal.upgrade.v4_0_0.
				UpgradeFaroPreferences(),
			new com.liferay.osb.faro.internal.upgrade.v4_0_0.
				UpgradeFaroProject());
		registry.register(
			"4.0.0", "5.0.0",
			new com.liferay.osb.faro.internal.upgrade.v5_0_0.
				UpgradeFaroProject());
		registry.register(
			"5.0.0", "6.0.0",
			new com.liferay.osb.faro.internal.upgrade.v6_0_0.
				UpgradeFaroProject(),
			new com.liferay.osb.faro.internal.upgrade.v6_0_0.
				UpgradeFaroProjectEmailAddressDomain());
		registry.register(
			"6.0.0", "7.0.0",
			new com.liferay.osb.faro.internal.upgrade.v7_0_0.
				UpgradeFaroChannel());
		registry.register(
			"7.0.0", "8.0.0",
			new com.liferay.osb.faro.internal.upgrade.v8_0_0.
				UpgradeFaroProject());
		registry.register(
			"8.0.0", "9.0.0",
			new com.liferay.osb.faro.internal.upgrade.v9_0_0.
				UpgradeFaroProject());
		registry.register(
			"9.0.0", "10.0.0",
			new com.liferay.osb.faro.internal.upgrade.v10_0_0.
				UpgradeFaroProject());
		registry.register(
			"10.0.0", "11.0.0",
			new com.liferay.osb.faro.internal.upgrade.v11_0_0.
				UpgradeFaroNotification());
		registry.register(
			"11.0.0", "12.0.0",
			new com.liferay.osb.faro.internal.upgrade.v12_0_0.
				UpgradeFaroProject());
		registry.register(
			"12.0.0", "13.0.0",
			new com.liferay.osb.faro.internal.upgrade.v13_0_0.
				UpgradeFaroUser());
		registry.register(
			"13.0.0", "14.0.0",
			new com.liferay.osb.faro.internal.upgrade.v14_0_0.
				UpgradeFaroChannel());
		registry.register(
			"14.0.0", "15.0.0",
			new com.liferay.osb.faro.internal.upgrade.v15_0_0.
				UpgradeFaroProject());
		registry.register(
			"15.0.0", "16.0.0",
			new com.liferay.osb.faro.internal.upgrade.v16_0_0.
				UpgradeUserGroupRole());
	}

}