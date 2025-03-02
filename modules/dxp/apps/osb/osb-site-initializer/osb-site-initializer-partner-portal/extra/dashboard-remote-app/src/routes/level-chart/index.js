/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayAlert from '@clayui/alert';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {useEffect, useState} from 'react';

import PartnershipLevel from '../../common/components/PartnershipLevel';
import Container from '../../common/components/container';
import {PartnerRoles} from '../../common/enums/partnerRoles';
import {PartnershipLevels} from '../../common/enums/partnershipLevels';
import {partnerLevelProperties} from '../../common/mock/mock';
import ClayIconProvider from '../../common/utils/ClayIconProvider';

import './index.css';

export default function () {
	const [data, setData] = useState();
	const [headcount, setHeadcount] = useState();
	const [completed, setCompleted] = useState();
	const [loading, setLoading] = useState(false);

	const getAccountInformation = async () => {
		setLoading(true);

		// eslint-disable-next-line @liferay/portal/no-global-fetch
		const myUserAccountsRequest = await fetch(
			'/o/headless-admin-user/v1.0/my-user-account',
			{
				headers: {
					'accept': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
			}
		);

		if (myUserAccountsRequest.ok) {
			const {accountBriefs} = await myUserAccountsRequest.json();

			if (accountBriefs.length) {
				// eslint-disable-next-line @liferay/portal/no-global-fetch
				const accountRequest = await fetch(
					`/o/headless-admin-user/v1.0/accounts/${accountBriefs[0].id}`,
					{
						headers: {
							'accept': 'application/json',
							'x-csrf-token': Liferay.authToken,
						},
					}
				);

				// eslint-disable-next-line @liferay/portal/no-global-fetch
				const accountUsersRequest = await fetch(
					`/o/headless-admin-user/v1.0/accounts/${accountBriefs[0].id}/user-accounts`,
					{
						headers: {
							'accept': 'application/json',
							'x-csrf-token': Liferay.authToken,
						},
					}
				);

				const checkedProperties = {};

				if (accountRequest.ok) {
					const accountData = await accountRequest.json();

					if (
						accountData.partnerLevel !==
						PartnershipLevels.AUTHORIZED
					) {
						checkedProperties['solutionDeliveryCertification'] =
							accountData.solutionDeliveryCertification;

						checkedProperties['marketingPlan'] =
							accountData.marketingPlan;

						checkedProperties['marketingPerformance'] = Boolean(
							accountData.marketingPerformance
						);

						if (
							accountData.partnerLevel === PartnershipLevels.GOLD
						) {
							const hasMatchingARR =
								accountData.aRRAmount ===
								partnerLevelProperties[accountData.partnerLevel]
									.growthARR;

							const hastMatchingNPOrNB =
								accountData.newProjectExistingBusiness ===
								partnerLevelProperties[accountData.partnerLevel]
									.newProjectExistingBusiness;

							checkedProperties['arr'] =
								hasMatchingARR || hastMatchingNPOrNB;
						}

						const growthRenewalARRTotal =
							accountData.growthARR + accountData.renewalARR;

						if (
							accountData.partnerLevel ===
								PartnershipLevels.PLATINUM &&
							growthRenewalARRTotal > 0 &&
							accountData.aRRAmount >= growthRenewalARRTotal
						) {
							checkedProperties['arr'] = true;
						}

						if (accountUsersRequest.ok) {
							const {
								items: accountUsers,
							} = await accountUsersRequest.json();

							const countHeadcount = {
								partnerMarketingUser: 0,
								partnerSalesUsers: 0,
							};

							accountUsers.forEach((user) => {
								if (
									user.accountBriefs[0].roleBriefs.find(
										(role) =>
											role.name ===
											PartnerRoles.MARKETING_USER
									)
								) {
									countHeadcount['partnerMarketingUser'] += 1;
								}

								if (
									user.accountBriefs[0].roleBriefs.find(
										(role) =>
											role.name ===
											PartnerRoles.SALES_USERS
									)
								) {
									countHeadcount['partnerSalesUsers'] += 1;
								}
							});

							if (
								countHeadcount.partnerMarketingUser >=
									partnerLevelProperties[
										accountData.partnerLevel
									].partnerMarketingUser &&
								countHeadcount.partnerSalesUsers >=
									partnerLevelProperties[
										accountData.partnerLevel
									].partnerSalesUsers
							) {
								checkedProperties['headcount'] = true;
							}

							setHeadcount(countHeadcount);
						}
					}

					setData(accountData);
					setCompleted(checkedProperties);
				}
			}
		}
		setLoading(false);
	};

	useEffect(() => {
		getAccountInformation();
	}, []);

	const BuildPartnershipLevel = () => {
		if (loading) {
			return <ClayLoadingIndicator className="mb-10 mt-9" size="md" />;
		}

		if (!data && !loading) {
			return (
				<ClayAlert
					className="mb-8 mt-8 mx-auto text-center w-50"
					displayType="info"
					title="Info:"
				>
					No Data Available
				</ClayAlert>
			);
		}

		return (
			<PartnershipLevel
				completed={completed}
				data={data}
				headcount={headcount}
			/>
		);
	};

	return (
		<ClayIconProvider>
			<Container title="Partnership Level">
				<BuildPartnershipLevel />
			</Container>
		</ClayIconProvider>
	);
}
