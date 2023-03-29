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

package com.liferay.osb.faro.web.internal.card.template.type;

import com.liferay.osb.faro.contacts.model.constants.ContactsCardTemplateConstants;
import com.liferay.osb.faro.web.internal.model.display.contacts.card.template.ContactsCardTemplateDisplay;
import com.liferay.osb.faro.web.internal.model.display.contacts.card.template.ConversionHealthContactsCardTemplateDisplay;
import com.liferay.petra.string.StringPool;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shinn Lok
 */
@Component(immediate = true, service = ContactsCardTemplateType.class)
public class ConversionHealthContactsCardTemplateType
	extends BaseContactsCardTemplateType {

	@Override
	public String getDefaultName() {
		return _DEFAULT_NAME;
	}

	@Override
	public Map<String, Object> getDefaultSettings() {
		return _defaultSettings;
	}

	@Override
	public Class<? extends ContactsCardTemplateDisplay> getDisplayClass() {
		return ConversionHealthContactsCardTemplateDisplay.class;
	}

	@Override
	public int getType() {
		return ContactsCardTemplateConstants.TYPE_CONVERSION_HEALTH;
	}

	private static final String _DEFAULT_NAME = "Conversion Health";

	private static final Map<String, Object> _defaultSettings =
		new HashMap<String, Object>() {
			{
				put("endDateTime", 0);
				put("interval", 30);
				put("minCurrentStageId", StringPool.BLANK);
				put("stageId", StringPool.BLANK);
				put("startDateTime", 0);
				put(
					"unit",
					ContactsCardTemplateConstants.SETTINGS_UNIT_PERCENTAGE);
			}
		};

}