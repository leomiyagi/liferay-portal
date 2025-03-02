import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import {useEffect, useState} from 'react';

import {getCompanyId} from '../../liferay/constants';
import {Liferay} from '../../liferay/liferay';
import {
	getAccountInfo,
	getAccountInfoFromCommerce,
	getAccounts,
	getChannels,
	getDeliveryProduct,
	getPaymentMethodURL,
	getProduct,
	getProductSKU,
	getSKUCustomFieldExpandoValue,
	getUserAccount,
	getUserAccountsById,
	patchOrderByERC,
	postCartByChannelId,
	postCheckoutCart,
} from '../../utils/api';

import './GetAppModal.scss';
import {SelectPaymentMethod} from './SelectPaymentMethod';

interface App {
	createdBy: string;
	id: number;
	name: {en_US: string} | string;
	price: number;
	productId: number;
	urlImage: string;
}

interface GetAppModalProps {
	handleClose: () => void;
}

const initialBillingAddress = {
	city: '',
	country: '',
	countryISOCode: 'US',
	name: '',
	phoneNumber: '',
	region: '',
	street1: '',
	street2: '',
	zip: '',
};

export function GetAppModal({handleClose}: GetAppModalProps) {
	const {observer, onClose} = useModal({
		onClose: handleClose,
	});
	const [account, setAccount] = useState<AccountBrief>();
	const [accountPublisher, setAccountPublisher] = useState<Account>();
	const [app, setApp] = useState<App>({
		createdBy: '',
		id: 0,
		name: '',
		price: 0,
		productId: 0,
		urlImage: '',
	});
	const [appVersion, setAppVersion] = useState<string>('');
	const [channel, setChannel] = useState<Channel>({
		currencyCode: '',
		externalReferenceCode: '',
		id: 0,
		name: '',
		siteGroupId: 0,
		type: '',
	});
	const [currentUser, setCurrentUser] = useState<{emailAddress: string}>();
	const [sku, setSku] = useState<SKU>({
		cost: 0,
		externalReferenceCode: '',
		id: 0,
		price: 0,
		sku: '',
		skuOptions: [],
	});

	const [addresses, setAddresses] = useState<PostalAddressResponse[]>([]);

	const [billingAddress, setBillingAddress] = useState<BillingAddress>(
		initialBillingAddress
	);

	const [selectedPaymentMethod, setSelectedPaymentMethod] =
		useState<PaymentMethodSelector>('pay');

	const [selectedAddress, setSelectedAddress] = useState('');

	const [showNewAddressButton, setShowNewAddressButton] = useState(false);

	const [enableTrialMethod, setEnableTrialMethod] = useState<string>('no');

	const [phoneNumber, setPhoneNumber] = useState('');

	const [purchaseOrderNumber, setPurchaseOrderNumber] = useState<string>('');
	const [email, setEmail] = useState<string>('');

	const [freeApp, setFreeApp] = useState<boolean>(false);

	useEffect(() => {
		const getModalInfo = async () => {
			const channels = await getChannels();

			const channel =
				channels.find(
					(channel) => channel.name === 'Marketplace Channel'
				) || channels[0];

			setChannel(channel);

			const currentUser = await getUserAccount();

			setCurrentUser(currentUser);

			const response = await getUserAccountsById();

			const userAccounts = (await response.json()) as UserAccount;
			let accountId;

			if (userAccounts.accountBriefs.length) {
				accountId = userAccounts.accountBriefs[0].id;
			}
			else {
				accountId = 50307;
			}

			const currentAccount = await getAccountInfo({
				accountId,
			});

			// The call for getAccountInfoFromCommerce is only temporary

			const currentAccountCommerce = await getAccountInfoFromCommerce(
				accountId
			);

			setAccount({
				...currentAccount,
				logoURL: currentAccountCommerce.logoURL,
			});

			const app = await getDeliveryProduct({
				accountId,
				appId: Liferay.MarketplaceCustomerFlow.appId,
				channelId: channel.id,
			});

			setApp(app);

			const skuResponse = await getProductSKU({
				appProductId: Liferay.MarketplaceCustomerFlow.appId,
			});

			let sku;

			if (skuResponse.items.length > 1) {
				let isTrial;
				isTrial = skuResponse.items
					.map((sku) =>
						sku.skuOptions.find((option) => option.value === 'yes')
					)
					.filter((sku) => sku)[0]?.value;
				setEnableTrialMethod(isTrial as string);
				sku = skuResponse.items.find((sku) => sku.price !== 0);
			}
			else {
				sku = skuResponse.items[0];
			}
			setSku(sku as SKU);

			if (sku?.price === 0 && sku.skuOptions[0].value === 'no') {
				setFreeApp(true);
				setSelectedPaymentMethod(null);
			}

			const account = await getAccountInfo({accountId});

			const postalAddresses = account?.accountUserAccounts[0]
				.userAccountContactInformation
				.postalAddresses as PostalAddressResponse[];

			let addresses = [] as PostalAddressResponse[];

			postalAddresses?.map((item) => {
				addresses = [...addresses, item];
			});

			setAddresses(addresses);

			const telephone =
				account.accountUserAccounts[0].userAccountContactInformation
					.telephones[0];

			if (telephone) {
				setPhoneNumber(
					`(${telephone.extension}) ${telephone.phoneNumber}`
				);
			}

			const versionResponse = await getSKUCustomFieldExpandoValue({
				companyId: Number(getCompanyId()),
				customFieldName: 'version',
				skuId: sku?.id as number,
			});

			if (typeof versionResponse === 'string') {
				setAppVersion(versionResponse);
			}

			const adminProduct = await getProduct({
				appERC: app?.externalReferenceCode,
			});

			const catalogId = adminProduct?.catalogId;
			const accounts = await getAccounts();

			const accountPublisher = accounts?.items.find(
				({customFields}: Account) => {
					if (customFields) {
						const catalogIdField = customFields.find(
							(customField: {
								customValue: {data: string};
								name: string;
							}) => customField.name === 'CatalogId'
						);
	
						return catalogIdField?.customValue.data == catalogId;
					}
				}
			);

			setAccountPublisher(accountPublisher);
		};
		getModalInfo();
	}, []);

	async function handleGetApp() {
		const cart: Partial<Cart> = {
			accountId: account?.id as number,
			cartItems: [
				{
					price: {
						currency: channel.currencyCode,
						discount: 0,
						finalPrice: app.price,
						price: app.price,
					},
					productId: app.id,
					quantity: 1,
					settings: {
						maxQuantity: 1,
					},
					skuId: sku?.id as number,
				},
			],
			currencyCode: channel.currencyCode,
		};

		const origin = window.location.origin;

		let newCart: Partial<Cart> = {};

		let cartResponse;

		if (freeApp) {
			newCart = {
				...cart,
			};

			cartResponse = await postCartByChannelId({
				cartBody: newCart,
				channelId: channel.id,
			});

			const cartCheckoutResponse = await postCheckoutCart({
				cartId: cartResponse.id,
			});

			const newOrderStatus = {
				orderStatus: 1,
			};

			await patchOrderByERC(
				cartCheckoutResponse.orderUUID,
				newOrderStatus
			);
		}
		else {
			newCart = {};
			if (selectedPaymentMethod === 'pay') {
				newCart = {
					...cart,
					billingAddress,
					paymentMethod: 'paypal',
				};
			}

			if (selectedPaymentMethod === 'order') {
				newCart = {
					...cart,
					billingAddress,
					purchaseOrderNumber,
					author: email,
				};
			}

			if (selectedPaymentMethod === 'trial') {
				newCart = {
					...cart,
					billingAddress,
				};
			}

			cartResponse = await postCartByChannelId({
				cartBody: newCart,
				channelId: channel.id,
			});

			await postCheckoutCart({cartId: cartResponse.id});
		}

		const url = `${origin}/next-steps?orderId=${cartResponse.id}&logoURL=${account?.logoURL}&appLogoURL=${app?.urlImage}&accountName=${account?.name}&accountLogo=${account?.logoURL}&appName=${app.name}`;

		const paymentMethodURL = await getPaymentMethodURL(
			cartResponse.id,
			url
		);

		window.location.href =
			selectedPaymentMethod === 'pay' ? paymentMethodURL : url;

		onClose();
	}

	return (
		<div className="modal-open">
			<ClayModal observer={observer}>
				<div className="get-app-modal-header-container">
					<div className="get-app-modal-header-left-content">
						<span className="get-app-modal-header-title">
							{freeApp ? 'Confirm Install' : 'Confirm Payment'}
						</span>

						<span className="get-app-modal-header-description">
							{freeApp
								? 'Confirm installation of this free app.'
								: 'Choose app payment method and edit purchasing details'}
						</span>
					</div>

					<ClayButton displayType="unstyled" onClick={onClose}>
						<ClayIcon symbol="times" />
					</ClayButton>
				</div>

				<ClayModal.Body>
					<div className="get-app-modal-body-card-container">
						<div className="get-app-modal-body-card-header">
							<span className="get-app-modal-body-card-header-left-content">
								App Details
							</span>

							<div className="get-app-modal-body-card-header-right-content-container">
								<div className="get-app-modal-body-card-header-right-content-account-info">
									<span className="get-app-modal-body-card-header-right-content-account-info-name">
										{account?.name}
									</span>

									<span className="get-app-modal-body-card-header-right-content-account-info-email">
										{currentUser?.emailAddress}
									</span>
								</div>

								<img
									alt="Account icon"
									className="get-app-modal-body-card-header-right-content-account-info-icon"
									src={account?.logoURL}
								/>
							</div>
						</div>

						<div className="get-app-modal-body-container">
							<div className="get-app-modal-body-content-container">
								<div className="get-app-modal-body-content-left">
									<img
										alt="App Image"
										className="get-app-modal-body-content-image"
										src={app.urlImage}
									/>

									<div className="get-app-modal-body-content-app-info-container">
										<span className="get-app-modal-body-content-app-info-name">
											{typeof app?.name === 'string'
												? app?.name
												: app?.name.en_US}
										</span>

										<span className="get-app-modal-body-content-app-info-version">
											{appVersion} by{' '}

											{accountPublisher?.name}
										</span>
									</div>
								</div>

								<div className="get-app-modal-body-content-right">
									<span className="get-app-modal-body-content-right-price">
										Price
									</span>

									<span className="get-app-modal-body-content-right-value">
										{freeApp ? 'Free' : `$ ${sku.price}`}
									</span>

									{!freeApp && (
										<div className="get-app-modal-body-content-right-subscription-container">
											<span className="get-app-modal-body-content-right-subscription-text">
												Annually
											</span>
										</div>
									)}
								</div>
							</div>

							<div>
								<ClayIcon
									className="get-app-modal-body-content-alert-icon"
									symbol="info-panel-open"
								/>

								<span className="get-app-modal-body-content-alert-message">
									{freeApp
										? ' A free app does not include support, maintenance or updates from the publisher.'
										: 'A subscription license includes support, maintenance and updates for the app as long as the subscription is current.'}
								</span>
							</div>
						</div>
					</div>

					{!freeApp && (
						<>
							<div className="get-app-modal-text-divider">
								Select payment method
							</div>

							<SelectPaymentMethod
								addresses={addresses}
								billingAddress={billingAddress}
								email={email}
								enableTrialMethod={enableTrialMethod}
								phoneNumber={phoneNumber}
								purchaseOrderNumber={purchaseOrderNumber}
								selectedAddress={selectedAddress}
								selectedPaymentMethod={selectedPaymentMethod}
								setBillingAddress={setBillingAddress}
								setEmail={setEmail}
								setPurchaseOrderNumber={setPurchaseOrderNumber}
								setSelectedAddress={setSelectedAddress}
								setSelectedPaymentMethod={
									setSelectedPaymentMethod
								}
								setShowNewAddressButton={
									setShowNewAddressButton
								}
								showNewAddressButton={showNewAddressButton}
							/>
						</>
					)}
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<div className="get-app-modal-footer">
							<ClayButton.Group spaced>
								<button
									className="get-app-modal-button-cancel"
									onClick={onClose}
								>
									Cancel
								</button>

								<button
									className="get-app-modal-button-get-this-app"
									onClick={handleGetApp}
								>
									{!freeApp && selectedPaymentMethod === 'pay'
										? `Pay $${sku?.price} Now`
										: selectedPaymentMethod === 'trial'
										? 'Start Free Trial'
										: selectedPaymentMethod === 'order'
										? 'Request Purchase Order'
										: 'Get This App'}
								</button>
							</ClayButton.Group>

							<span>
								{!freeApp &&
									selectedPaymentMethod === 'pay' &&
									'You will be redirected to PayPal to complete payment'}
							</span>
						</div>
					}
				/>
			</ClayModal>
		</div>
	);
}
