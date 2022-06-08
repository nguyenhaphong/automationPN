package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.email.EmailReferenceManager;
import net.nintendo.automation.email.EmailTemplate;
import net.nintendo.automation.email.MailBoxManager;
import net.nintendo.automation.email.models.EmailReference;
import net.nintendo.automation.email.models.InbucketEmail;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.data.DataGen;
import net.nintendo.automation.ui.models.admin.AdminHomePage;
import net.nintendo.automation.ui.models.admin.AdminUserDetailPage;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.email.EmailPage;
import net.nintendo.automation.ui.models.iarc.IARCQuestionnaireFinalRatingPage;
import net.nintendo.automation.ui.models.iarc.IARCQuestionnairePage;
import net.nintendo.automation.ui.models.ndidMS.ManagementSystemLogin;
import net.nintendo.automation.ui.models.ndp.NDPCompanyNDARequiredPage;
import net.nintendo.automation.ui.models.ndp.NDPCustomAgreementsPage;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPFinanceInformationPage;
import net.nintendo.automation.ui.models.ndp.NDPFinancialInformationTab;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPLicensingWaiverRequestsTab;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.NDPPayeeManagementPage;
import net.nintendo.automation.ui.models.ndp.NDPSignInPage;
import net.nintendo.automation.ui.models.ndp.NDPViewOrganizationPage;
import net.nintendo.automation.ui.models.ndp.NDPViewTaskPage;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardMembersTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardProductInfoTab;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoAgeRating;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoGuidelinesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoIssuePage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoROMUpload;
import net.nintendo.automation.ui.models.rom.ROMCreateRom;
import net.nintendo.automation.ui.models.rom.ROMLoginPage;
import net.nintendo.automation.ui.users.User;
import net.nintendo.automation.ui.users.UserManager;
import net.nintendo.automation.utils.WaitUtils;
import net.nintendo.automation_core.common_client.enums.ServiceType;
import net.nintendo.automation_core.common_client.enums.UserType;
import net.nintendo.automation_core.extent_reports.extensions.IJUnitTestReporter;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Classname: T3404503
 * Version: 1.0.0
 * Purpose:
 */
@Tag("T3404503")
//@ScenarioTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3404503 extends CommonBaseTest {
    private static Browser browser;
    private static User romUser;
    private static User adminUser;
    private static User ndpUserA;
    private static User ndpUserB;
    private static User licApproverUser;
    private static User licApprovalCoordUser;
    private static User licUser;
    private static User licAdmin;
    private static User licPowerUser;
    private static User financeReviewerUser;
    private static String organizationName;
    private static String productName;
    private static String uuidOrg;
    private static String gameCode;
    private static String applicationID;
    private static String initialCode;
    private static String fileRomName;
    private static String payeeName;
    private static String bmsCode;
    private static String accountHolderStreet1 ="Account Holder Street1";
    private static String accountHolderCity ="Account Holder City";
    private static String postalCode ="123";
    private static String countryAccount="Bahamas";
    private static String bankName="Bank name";

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        licApprovalCoordUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);
        licApproverUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approver_NCL);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_user_NCL);
        licPowerUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_pwr_user_NCL);
        licAdmin = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_admin_NCL);
        financeReviewerUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.finance_reviewer_ncl);

        productName = DataGen.getRandomProductName();
        payeeName = DataGen.getRandomString(4);
        bmsCode = DataGen.getRandomCharacter(32).toUpperCase(Locale.ROOT);
        ndpUserA = new User();
        ndpUserA.setPassword("nintendo");
        ndpUserA.setUserName(DataGen.getRandomUserName() + "_admin");
        ndpUserB = new User();
        ndpUserB.setPassword("nintendo");
        ndpUserB.setUserName(DataGen.getRandomUserName().toLowerCase(Locale.ROOT) + "_contributor");
        adminUser = new User();
        adminUser.setPassword("nintendo");
        adminUser.setUserName("prdcvdev1_omniadmin01");
    }

    @AfterAll
    static void terminateBrowser() {
        BrowserManager.closeBrowser(browser);
        UserManager.releaseUser(ServiceType.SEARCH, romUser);
        UserManager.releaseUser(ServiceType.NDP, licApprovalCoordUser);
        UserManager.releaseUser(ServiceType.NDP, licUser);
        UserManager.releaseUser(ServiceType.NDP, licAdmin);
        UserManager.releaseUser(ServiceType.NDP, licPowerUser);
        UserManager.releaseUser(ServiceType.NDP, licApproverUser);
        UserManager.releaseUser(ServiceType.NDP, financeReviewerUser);
    }

    @Test
    @Order(0)
    public void TestPreconditions(IJUnitTestReporter testReporter) {
        //create organization
        organizationName = DataGen.getRandomCharacter(6) + "Company";
        NDPHomePage homePage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReporter)
                .acceptCookie();
        CommonAction.createOrganization(homePage, organizationName, ndpUserA, "Address Line 1", "Ha Noi", "Bahamas", "ASEAN", "000010");
        // change password - userA
        ManagementSystemLogin managementSystemLogin = browser.openURL(ManagementSystemLogin.class, PageState.PRE_LOGIN, testReporter);
        CommonAction.changePasswordForUser(managementSystemLogin, adminUser, ndpUserA);

        NDPSignInPage signInPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReporter).clickSignInPage();
        if (signInPage.signInNewlyRegisteredOrganizationAndIsOrganizationApproved(ndpUserA, true)) {
            NDPCompanyNDARequiredPage companyNDARequiredPage = browser.getPage(NDPCompanyNDARequiredPage.class, testReporter);
            companyNDARequiredPage.selectByClickingTheSubmitButtonCheckbox();
            if (companyNDARequiredPage.clickSubmitButtonWithError()) {
                browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter).clickGettingStarted();
            } else {
                companyNDARequiredPage.afterClickSubmitButtonNotError().clickHereLink();
            }
        } else {
            //approve organization
            NDPViewTaskPage viewTaskPage = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter)
                    .clickUserMenuDropdownList()
                    .clickSignOutButton()
                    .clickSignInPage()
                    .signIn(licApproverUser)
                    .clickOnMyTaskMenu()
                    .clickOnTask("Global NDA")
                    .selectAssignToMeButton()
                    .selectOKButtonInAssignToMeModal()
                    .selectRetryRequestButton()
                    .selectOKButtonInRetryRequestModal()
                    .waitingForApproveSuccessful();
            browser.refresh();
            viewTaskPage.clickUserMenuDropdownList()
                    .clickSignOutButton()
                    .clickSignInPage()
                    .signIn(ndpUserA)
                    .clickGettingStarted();
        }
        // add user contributor - userB
        NDPDevelopmentHome developmentHome = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter);
        uuidOrg = developmentHome.navTopNavigation()
                .clickAdmin()
                .clickOrganizationInformation()
                .getOrganizationUUID();
        browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter)
                .navTopNavigation()
                .clickAdmin()
                .clickUserManagementMenu()
                .clickAddNewUserButton()
                .enterFirstName(DataGen.getRandomUserName())
                .enterLastName(DataGen.getRandomUserName())
                .enterNintendoDeveloperID(ndpUserB.getUserName())
                .enterEmailAddress(ndpUserB.getUserName() + TextConstants.inbucket_Suffix)
                .clickSaveNewUserButton();
        //change password - userB
        browser.openURL(ManagementSystemLogin.class, PageState.PRE_LOGIN, testReporter);
        CommonAction.changePasswordForUser(managementSystemLogin, adminUser, ndpUserB);

        browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter);
        developmentHome.clickUserMenuDropdownList()
                .clickSignOutButton()
                .clickSignInPage()
                .signInNewlyAddUser(ndpUserB);
        //add agreement
        NDPCustomAgreementsPage customAgreementsPage = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter)
                .clickUserMenu()
                .clickLogout()
                .clickSignInPage()
                .signIn(licPowerUser)
                .clickInternalTab()
                .clickCustomAgreements()
                .clickSearchIcon()
                .enterOrganizationName(uuidOrg)
                .clickSearch()
                .selectOrg(organizationName)
                .clickChooseSelectedPartner()
                .selectPlatform("Nintendo Switch")
                .selectAgreementType("Publisher Agreement (Digital)")
                .selectRegion("Global")
                .clickUpload()
                .selectFileInPC("Judgement.pdf")
                .setExecutionDate(0)
                .setExpirationDate(100)
                .selectAgreementApprove()
                .clickSubmit();
        // add role
        AdminHomePage adminHomePage = browser.openURL(AdminHomePage.class, PageState.PRE_LOGIN);
        AdminUserDetailPage userDetailPage = adminHomePage.acceptCookie().clickSignInPage().signIn(adminUser).clickMenuAdmin().clickControlPanel()
                .clickUsersAndOrganizations().clickAllUsersTab()
                .enterOrganizationName(ndpUserA.getUserName())
                .clickSearch()
                .clickAction()
                .clickEdit();
        userDetailPage.clickRole()
                .clickSearchRegularRole()
                .enterRole("NX")
                .clickSearch()
                .clickChoose("Company NX Access")
                .clickSearchRegularRole()
                .enterRole("Finance")
                .clickSearch()
                .clickChoose("Company Finance (MIRROR)")
                .clickSave()
                .clickBack()
                .enterOrganizationName(ndpUserB.getUserName())
                .clickSearch()
                .clickAction()
                .clickEdit()
                .clickRole()
                .clickSearchRegularRole()
                .enterRole("NX")
                .clickSearch()
                .clickChoose("Company NX Access")
                .clickSave();
        userDetailPage.clickUserMenu().clickSignOut();
        browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter).clickUserMenuDropdownList().clickSignOutButton().clickSignInPage()
                .signIn(ndpUserA)
                .clickUserMenuDropdownList()
                .clickManageAccount()
                .clickEditProfileButton()
                .selectCompanyFinanceRole()
                .selectCompanyNxUserRole()
                .clickSaveChangesButton();
        browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter)
                .navTopNavigation()
                .clickAdmin()
                .clickUserManagementMenu()
                .selectCompanyUserByNDID(ndpUserB.getUserName())
                .clickEditProfileButton()
                .selectCompanyNXUser()
                .clickSaveChangesButton();
        //add new Nintendo 3ds/WiiU payee
        NDPFinanceInformationPage financeInformationPage = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter)
                .navTopNavigation()
                .clickAdmin()
                .clickFinanceInformationMenu()
                .clickAddNewPayeeNintendo3DSWiiU()
                .selectCountryPayeeBank("Bahamas")
                .enterAccountName(payeeName + " Test regional")
                .enterAccountHolderStreet1(accountHolderStreet1)
                .enterAccountHolderCity(accountHolderCity)
                .enterAccountHolderPostalCode(postalCode)
                .selectCountryAccountHolder(countryAccount)
                .enterAccountNumber("32351234")
                .selectAccountType("Savings")
                .enterBankName(bankName)
                .enterBranchOfficeName("Branch Office Name")
                .enterBicSwiftCode("BICSWIFT")
                .clickNext()
                .clickCancelButton();
        // add regional 2nd - nintendo 3ds /WiiU payee
        financeInformationPage.clickAddNewPayeeNintendo3DSWiiU()
                .selectCountryPayeeBank("Bahamas")
                .enterAccountName(payeeName + " Test regional 2nd")
                .enterAccountHolderStreet1(accountHolderStreet1)
                .enterAccountHolderCity(accountHolderCity)
                .enterAccountHolderPostalCode(postalCode)
                .selectCountryAccountHolder(countryAccount)
                .enterAccountNumber("32351235")
                .selectAccountType("Savings")
                .enterBankName(bankName)
                .enterBranchOfficeName("Branch Office Name")
                .enterBicSwiftCode("BICSWIFT")
                .clickNext()
                .clickCancelButton();
    }

    @Test()
    @Order(1)
    public void createNewProductAndIssueGameCode_Step1(IJUnitTestReporter testReporter) {
        NDPDevelopmentHome developmentHome = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter);
        NDPMyProductsPage myProductsPage = developmentHome.clickMyProducts()
                .createNewProduct()
                .selectProductType(TextConstants.full_Product)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalJapanSalesRegion()
                .selectDigitalEuropeSalesRegion()
                .selectProductName(productName)
                .selectProductNameKana(TextConstants.productKana)
                .clickCreateButton();
        NDPProductDashboardPage myProductDashBoard = myProductsPage.clickProductByName(productName);
        CommonAction.issueGameCode(myProductDashBoard, 60, "NO");
        gameCode = myProductDashBoard.clickProductInfo().getGameCode();
    }

    @Test
    @Order(2)
    public void addUserBToProductContributor_Step2(IJUnitTestReporter testReporter) {
        NDPProductDashboardPage myProductDashBoard = browser.getPage(NDPProductDashboardPage.class, testReporter);
        NDPProductDashboardMembersTab productDashboardMembersTab = myProductDashBoard.clickMembersTab()
                .selectAddUserButton()
                .selectMyCompanyUsers()
                .selectInternalUsers(ndpUserB.getUserName())
                .selectAccessRole(TextConstants.contributor_User)
                .clickAddUserButton();
        assertEquals(productDashboardMembersTab.getSuccessNotificationText(), TextConstants.addMemberSuccess, "Add member successfully");
    }

    @Test
    @Order(3)
    public void navigateToReleaseDashboard_Step3(IJUnitTestReporter testReporter) {
        NDPProductDashboardPage myProductDashBoard = browser.getPage(NDPProductDashboardPage.class, testReporter);
        myProductDashBoard.clickReleases().selectInitialRelease();
    }

    @Test
    @Order(4)
    public void asUserBNavigateToAgeRatingScreen_Step4(IJUnitTestReporter testReporter) {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReporter);
        releaseInfoPage.nav().clickUserMenuDropdownList()
                .clickSignOutButton()
                .clickSignInPage()
                .signInNewlyAddUser(ndpUserB)
                .clickMyProducts()
                .clickProductByName(productName)
                .clickReleases()
                .selectInitialRelease()
                .nav()
                .clickAgeRating();
    }

    @Test
    @Order(5)
    public void clickOnAddIARCRatingButton_Step5(IJUnitTestReporter testReporter) {
        NDPReleaseInfoAgeRating ageRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReporter);
        ageRating.clickOnAddIARCRatingButton();
    }

    @Test
    @Order(6)
    public void verifyRequestNewIARCRatingsForm_Expected5(IJUnitTestReporter testReporter) {
        NDPReleaseInfoAgeRating ageRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReporter);
        assertTrue(ageRating.isRequestNewIARCRatingsFormDisplayed(), "TheRequest New IARC Ratings form is appear.");
    }

    @Test
    @Order(7)
    public void confirmDisplayingRequestNewIARCRatingForms_Step6(IJUnitTestReporter testReporter) {
        NDPReleaseInfoAgeRating ageRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReporter);
    }

    @Test
    @Order(8)
    public void confirmDisplayingRequestNewIARCRatingForms_Expected6(IJUnitTestReporter testReporter) {
        NDPReleaseInfoAgeRating ageRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReporter);
        ageRating.verifyRequestNewIARCCertificateClickable()
                .verifyImportExistingIARCCertificateClickable();
        assertAll(() -> assertTrue(ageRating.isContactEmailAddressRequiredFieldDisplay(), "The Contact Email Address is asterisk field remark for required field"),
                () -> assertTrue(ageRating.isPublicEmailAddressRequiredFieldDisplay(), "The Public Email Address is asterisk field remark for required field"),
                () -> assertFalse(ageRating.isContinueButtonEnabled(), "The Continue Button is Disabled"));
    }

    @Test()
    @Order(9)
    public void inputValidDataForContactEmailAddressTextBox_Step7(IJUnitTestReporter testReport) {
        NDPReleaseInfoAgeRating ageRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReport);
        ageRating.enterContactEmailAddress(ndpUserB.getUserName() + TextConstants.inbucket_Suffix);
    }

    @Test()
    @Order(10)
    public void confirmStatusOfContinueButton_Step8(IJUnitTestReporter testReport) {
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReport);
    }

    @Test()
    @Order(11)
    public void confirmStatusOfContinueButton_Expected8(IJUnitTestReporter testReport) {
        NDPReleaseInfoAgeRating ageRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReport);
        assertFalse(ageRating.isContinueButtonEnabled(), "The Continue Button is Disabled");
    }

    @Test()
    @Order(12)
    public void performFillOutRequestNewIARCRatingsForm_Step9(IJUnitTestReporter testReport) {
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReport)
                .enterContactEmailAddress(ndpUserB.getUserName() + TextConstants.inbucket_Suffix)
                .enterPublicEmailAddress(ndpUserB.getUserName() + TextConstants.inbucket_Suffix)
                .selectIARCRequestNewIARCCertificate();
    }

    @Test()
    @Order(13)
    public void confirmStatusOfContinueButton_Step10(IJUnitTestReporter testReport) {
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReport);
    }

    @Test()
    @Order(14)
    public void confirmStatusOfContinueButton_Expected10(IJUnitTestReporter testReport) {
        NDPReleaseInfoAgeRating ageRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReport);
        assertTrue(ageRating.isContinueButtonEnabled(), "The Continue Button is Enabled");
    }

    @Test()
    @Order(15)
    public void clickOnContinueButton_Step11(IJUnitTestReporter testReport) {
        NDPReleaseInfoAgeRating ageRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReport);
        ageRating.clickRequestNewIarcContinue();
    }

    @Test()
    @Order(16)
    public void VerifyRequestNewIARCRating_Expected11(IJUnitTestReporter testReport) {
        NDPReleaseInfoAgeRating ageRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReport);
        assertTrue(ageRating.isViewIARCAgeRatingsButtonDisplayed(), "The Screen navigate to Background Tab in this window");
        WaitUtils.idle(6000);
        browser.switchToTabByTitle(IARCQuestionnairePage.pageTitle);
    }

    @Test()
    @Order(17)
    public void CompleteIARCQuestionnaireTabSuchThatTheValueOfGenericIsSeven_Step12(IJUnitTestReporter testReport) {
        IARCQuestionnaireFinalRatingPage questionnaireFinalRating =
                browser.getPage(IARCQuestionnairePage.class, testReport)
                        .selectGame()
                        /* Create generic 7+ */
                        .selectViolenceOrBloodYesDoesTheGameContain()
                        .selectViolenceOrImpliedViolenceAgainstHumansCheckbox()
                        .selectFantasticalSettingDoesTheViolenceOccur()
                        .selectNoHavePixelatedOrChildlikeStyle()
                        .selectDescribeUnrealisticTheReactionsToViolence()
                        .selectThisViolencePresentedInTheGameReferredTo()
                        .selectNoneTheLevelOfBloodAndGoreAssociatedWithThisViolence()
                        .selectYesTheGameTakePlaceInARealisticOrHistoricalWarSetting()
                        .selectNoCanInnocentOrDefenselessCharactersBeSeriouslyInjuredOrKilled()
                        .selectYesDisturbingElementsSuchAsFierceSoundsSinisterOrIntimidatingCharactersOrDarkOvertones()
                        /**/
                        .selectFearNoDoesTheGameContain()
                        .selectSexualityNoDoesTheGameContain()
                        .selectSimulatedGamblingNoDoesTheGameContain()
                        .selectLanguageNoDoesTheGameContain()
                        .selectControlledSubstanceNoDoesTheGameContain()
                        .selectCrudeHumorNoDoesTheGameContain()
                        .selectMiscellaneousNoDoesTheGameNativelyAllowUsersToInteract()
                        .selectMiscellaneousNoDoesTheGameShareTheUsers()
                        .selectMiscellaneousNoDoesTheGameAllowUsersToPurchase()
                        .selectMiscellaneousNoDoesTheGameContainAnySwatikas()
                        .selectMiscellaneousNoDoesTheGameContainAnyContent()
                        .selectMiscellaneousNoDoesTheGameContainDetailedDescriptions()
                        .selectMiscellaneousNoDoesTheGameAdvocate()
                        .clickArrowIconNext()
                        .clickNext();
        browser.closeTabByTitle(IARCQuestionnairePage.pageTitle); // Close IARC tab because currently, closeBrowser only closes single tab
    }

    @Test
    @Order(18)
    public void navigateToGetIARCRatingPageAndClickOnViewIARCAgeRatingButton_Step13(IJUnitTestReporter testReporter) {
        NDPReleaseInfoAgeRating ageRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReporter);
        ageRating.clickViewIARCAgeRatings();
    }

    @Test
    @Order(19)
    public void verifyRatingValueOfCEROGenericRating_Expected13(IJUnitTestReporter testReporter) {
        NDPReleaseInfoAgeRating ageRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReporter);
        assertEquals("7+", ageRating.getRatingValueByRatingAgency("CERO/Generic"), "System  displays this rating data 7+ for CERO/Generic rating.");
    }

    @Test
    @Order(20)
    public void asUserAGoToIssuesPAgeAndSelectRequiredApprovalTab_Step14(IJUnitTestReporter testReporter) {
        NDPReleaseInfoAgeRating ageRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReporter);
        ageRating.nav().clickOnUserNameMenu()
                .clickOnSignOutButton()
                .clickSignInPage()
                .signIn(ndpUserA)
                .clickMyProducts()
                .clickProductByName(productName)
                .clickReleases()
                .selectInitialRelease()
                .nav()
                .clickIssues()
                .openRequiredApprovalTab();
    }

    @Test
    @Order(21)
    public void verifyIssueDescriptionOnRequiredApprovalTab_Expected14(IJUnitTestReporter testReporter) {
        NDPReleaseInfoIssuePage issuePage = browser.getPage(NDPReleaseInfoIssuePage.class, testReporter);
        issuePage.expandFirstUnresolvedRequiredApprovals();
        String descriptionValue = issuePage.getIssueDescriptionOnRequiredApproval();
        String expectDescription = "A Nintendo Switch payee must be set up in order to submit your product to Lotcheck." +
                " Please contact your Company Finance user to set up the payee on the Nintendo Developer Portal." +
                " The payee information processing may take several days to complete once it is submitted to Nintendo." +
                " Please add your payee information early to allow for the processing time.";
        assertEquals(expectDescription, descriptionValue, "Description is displayed correct");
    }

    @Test
    @Order(22)
    public void submitAndApproveTheWaiverRequest_Step15(IJUnitTestReporter testReporter) {
        NDPReleaseInfoIssuePage issuePage = browser.getPage(NDPReleaseInfoIssuePage.class, testReporter);
        issuePage.selectDesiredApprovalTypeAtFirstUnresolvedRequiredApproval()
                .enterReasonForRequest(TextConstants.enterReasonForRequest)
                .clickOnSendResolutionButton()
                .openRequiredApprovalTab();
        issuePage.nav().clickOnUserNameMenu().clickOnSignOutButton();
        //Approval Issue Task Requested
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(licApprovalCoordUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, gameCode);
        NDPViewTaskPage viewTaskPage = browser.getPage(NDPViewTaskPage.class, testReporter);
        browser.refresh();
        viewTaskPage.clickUserMenuDropdownList().clickSignOutButton();
        browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN)
                .clickSignInPage()
                .signIn(ndpUserA)
                .clickMyProducts()
                .clickProductByName(productName)
                .clickReleases()
                .selectInitialRelease()
                .nav().clickIssues();
    }

    @Test
    @Order(23)
    public void verifyAfterApproveWaiverRequest_Expected15(IJUnitTestReporter testReporter) {
        NDPReleaseInfoIssuePage issuePage = browser.getPage(NDPReleaseInfoIssuePage.class, testReporter);
        int numUnresolved = issuePage.getNumberOfCurrentUnresolvedRequiredApprovals();
        assertEquals(0, numUnresolved, "There is no longer a pending issue requiring a Nintendo Switch payee.");
    }

    @Test
    @Order(24)
    public void navigateToFinanceInformation_Step16(IJUnitTestReporter testReporter) {
        NDPDevelopmentHome developmentHome = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter);
        developmentHome.navTopNavigation().clickAdmin().clickFinanceInformationMenu();
    }

    @Test
    @Order(25)
    public void selectAddNewPayeeAndTax_Step17(IJUnitTestReporter testReporter) {
        NDPFinanceInformationPage financeInformationPage = browser.getPage(NDPFinanceInformationPage.class, testReporter);
        financeInformationPage.clickAddNewPayeeNintendoSwitch();
    }

    @Test()
    @Order(26)
    public void fillInAllRequestFieldsForSelectedCountry_Step18(IJUnitTestReporter testReporter) {
        NDPFinanceInformationPage financeInformationPage = browser.getPage(NDPFinanceInformationPage.class, testReporter);
        financeInformationPage.selectCountryPayeeBank("Bahamas")
                .enterAccountName(payeeName + " Test nintendoSwitch")
                .enterAccountHolderStreet1(accountHolderStreet1)
                .enterAccountHolderCity(accountHolderCity)
                .enterAccountHolderPostalCode(postalCode)
                .selectCountryAccountHolder(countryAccount)
                .enterAccountNumber("32356789")
                .selectAccountCurrency()
                .selectAccountType("Savings")
                .enterBankName(bankName)
                .enterBranchOfficeName("Branch Office Name")
                .enterBicSwiftCode("TESTCODE");
    }

    @Test()
    @Order(27)
    public void selectNextButton_Step19(IJUnitTestReporter testReporter) {
        NDPFinanceInformationPage financeInformationPage = browser.getPage(NDPFinanceInformationPage.class, testReporter);
        financeInformationPage.clickNext();
    }

    @Test()
    @Order(28)
    public void verifyNewlyAddedPayeeInformation_Expected19(IJUnitTestReporter testReporter) {
        NDPFinanceInformationPage financeInformationPage = browser.getPage(NDPFinanceInformationPage.class, testReporter);
        assertAll(() -> assertTrue(financeInformationPage.isPayeeCreated(payeeName + " Test nintendoSwitch - Bank name (..6789)"), "Verify newly added payee is displayed"),
                () -> assertEquals("Bank account info has been updated successfully", financeInformationPage.getSuccessMessage(), "Verify payee is created successfully"),
                () -> assertFalse(financeInformationPage.isAddNewPayeeNintendoSwitchButtonVisibled(), "Add New Payee Nintendo Switch Button should not appear"));
    }

    @Test
    @Order(29)
    public void accessToInbucket_Step20(IJUnitTestReporter testReport) {
        EmailPage emailPage = browser.openURL(EmailPage.class, PageState.PRE_LOGIN, testReport);
    }

    @Test
    @Order(30)
    public void checkEmailSentToPartnerAndLicensingAdmin_Expected20(IJUnitTestReporter testReport) {
        // F2 sent to Partner - Company Finance user
        Map<String, String> data = new HashMap<>();
        data.put("USER_FULL_NAME", payeeName+ " Test nintendoSwitch");
        data.put("LOCATION_ADDRESS",accountHolderStreet1+", "+accountHolderCity+" "+postalCode+" "+countryAccount);
        data.put("BANK_NAME", bankName);
        data.put("COMPANY_NAME", organizationName);
        data.put("BMS_CODE",organizationName.toUpperCase(Locale.ROOT));
        data.put("TARGET_URL", "https://developer.nintendo.com/group/development/admin/financial-information");
        EmailReference emailF2 = EmailReferenceManager.getInstance().compose(EmailTemplate.TEMPLATE_EMAIL_COMPANY_FINANCIAL_PAYEE_INFORMATION_ADDED_EXTERNAL, data);
        InbucketEmail msgF2 = MailBoxManager.getInstance().getInbucketEmail(ndpUserA.getUserName(), "Nintendo Developer Portal - Payee Information Added");
        // F8 sent to Licensing Admin (Internal)
        EmailReference emailF8 = EmailReferenceManager.getInstance().compose(EmailTemplate.F8, data);
        InbucketEmail msgF8 = MailBoxManager.getInstance().getInbucketEmail(TextConstants.lic_admin_NCL, "Nintendo Developer Portal - New Payee Created Successfully");

        assertAll(() -> assertEquals(emailF2.getSubject().getEn(), msgF2.getSubject().getText(),
                        "Subject Content F2"),
                () -> assertEquals(emailF2.getBodyHtml().getEn(), msgF2.getBody().getHtml(),
                        "Body Content F2"),
                () -> assertEquals(emailF8.getSubject().getEn(), msgF8.getSubject().getText(),
                        "Subject Content F8"),
                () -> assertEquals(emailF8.getBodyHtml().getEn(), msgF8.getBody().getHtml(),
                        "Body Content F8"));
    }

    @Test()
    @Order(31)
    public void createNewProductAndIssueGameCode_Step21(IJUnitTestReporter testReporter) {
        productName = DataGen.getRandomProductName();
        NDPDevelopmentHome developmentHome = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter);
        NDPMyProductsPage myProductsPage = developmentHome.clickMyProducts()
                .createNewProduct()
                .selectProductType(TextConstants.full_Product)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalJapanSalesRegion()
                .selectProductName(productName)
                .selectProductNameKana(TextConstants.productKana)
                .clickCreateButton();
        NDPProductDashboardPage myProductDashBoard = myProductsPage.clickProductByName(productName);
        CommonAction.issueGameCode(myProductDashBoard, 60, "NO");
        NDPProductDashboardProductInfoTab productInfoTab = myProductDashBoard.clickProductInfo();
        applicationID = productInfoTab.getApplicationID();
        gameCode = productInfoTab.getGameCode();
        initialCode = productInfoTab.getInitialCode();
    }

    @Test()
    @Order(32)
    public void completeAllTasksAndResolveAllIssues_Step22(IJUnitTestReporter testReporter) {
        NDPProductDashboardPage productDashboardPage = browser.getPage(NDPProductDashboardPage.class, testReporter);
        //create release info
        NDPReleaseInfoPage releaseInfoPage = productDashboardPage.clickReleases()
                .selectInitialRelease()
                .nav()
                .clickReleaseInfo();
        CommonAction.selectROMSameAllRegion(releaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);
        // //create Feature
        CommonAction.getFeatureOptionData(releaseInfoPage, TextConstants.allOff, TextConstants.allOff, TextConstants.allOff, false, "", false, "");
        //Create GuildLine
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = releaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoGuidelinesPage.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();
        //Create Test Scenarios
        //Create Age Rating
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = releaseInfoPage.nav().clickAgeRating();
        ndpReleaseInfoAgeRating.clickOnAddStandardRatingButton()
                .selectStandardAgeRating("CERO")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectRatingValue("A")
                .selectDescriptorAndNotice("Sexual Content")
                .uploadCertificateAttachment(new File("src/test/resources/DataFile/ab.jpg"))
                .clickOnSaveButtonInEditStandardRating();

        //create file ROM
        ROMLoginPage romLoginPage = browser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN, testReporter);
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom.selectRequester(TextConstants.test_User)
                .selectType(TextConstants.initial_Release)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .enterProductName(productName)
                .enterApplicationId(applicationID)
                .selectPublishingRelationShip(TextConstants.first_Party_Type)
                .enterDisplayVersion("1.0.0")
                .enterReleaseVersion("00")
                .selectCERORating("CERO: A")
                .clickOnCreateButton()
                .waitIsLoaded();
        fileRomName = romCreateRom.getRomName();
        romCreateRom.clickOnROMFileOnROMTable(applicationID);
        WaitUtils.idle(8000);
        romCreateRom.clickOnUserNameMenu().clickOnLogoutButton();

        //Upload ROM
        browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReporter)
                .clickProductByName(productName)
                .clickReleases()
                .selectInitialRelease();
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = releaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
        //Issues
        NDPReleaseInfoIssuePage issuePage = releaseInfoPage.nav().clickIssues();
        CommonAction.enterReasonForRequest(issuePage);
        issuePage.nav().clickOnUserNameMenu().clickOnSignOutButton();
        //Approval Issue TaskRequested
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(licApprovalCoordUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, gameCode);
        browser.refresh();
        NDPViewTaskPage viewTaskPage = browser.getPage(NDPViewTaskPage.class, testReporter);
        viewTaskPage.clickUserMenuDropdownList().clickSignOutButton();
    }

    @Test
    @Order(33)
    public void goToIssuePageAndSelectReleaseErrorTab_Step23(IJUnitTestReporter testReporter) {
        NDPReleaseInfoIssuePage issuePage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReporter)
                .clickSignInPage()
                .signIn(ndpUserA)
                .clickMyProducts()
                .clickProductByName(productName)
                .clickReleases()
                .selectInitialRelease()
                .nav().clickIssues();
        issuePage.openReleaseErrorsTab();
    }

    @Test
    @Order(34)
    public void verifyReleaseErrorTabAndCanSubmitReleaseToLotcheck_Expected23(IJUnitTestReporter testReporter) {
        NDPReleaseInfoIssuePage issuePage = browser.getPage(NDPReleaseInfoIssuePage.class, testReporter);
        assertAll(() -> assertEquals("There are no Release Errors to display", issuePage.getReleaseErrorsMessage(), "Verify no issue on Release Errors tab"),
                () -> assertTrue(issuePage.nav().isIssuesCompleted(), "Verify Issues section is completed"),
                () -> assertTrue(issuePage.nav().isSubmitButtonClickable(), "User can submit release to Lotcheck"));
    }

    @Test
    @Order(35)
    public void partnerViewNintendoSwitchPayeeDetails_Step24(IJUnitTestReporter testReporter) {
        NDPDevelopmentHome developmentHome = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter);
        developmentHome.navTopNavigation().clickAdmin().clickFinanceInformationMenu().clickViewDetailNintendoSwitchPayee();
    }

    @Test
    @Order(36)
    public void partnerAndLicensingViewDetailNintendoSwitchPayee_Expected24(IJUnitTestReporter testReporter) {
        NDPFinanceInformationPage financeInformationPage = browser.getPage(NDPFinanceInformationPage.class, testReporter);
        boolean isPartnerView = financeInformationPage.isTheViewDetailPayeeDisplayed();
        //view as finance reviewer
        NDPFinancialInformationTab financialInformationTab = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter)
                .clickUserMenuDropdownList()
                .clickSignOutButton()
                .clickSignInPage()
                .signIn(financeReviewerUser)
                .navTopNavigation()
                .clickInternalTab()
                .clickOrganizationManagementTab()
                .clickAdvancedSearchLink()
                .enterOrgNameOnAdvanceModal(organizationName)
                .selectHomeRegionOnAdvanceModal("NOA")
                .clickIconSearchOnAdvanceModal()
                .clickOnOrg(organizationName)
                .clickFinancialInformation();
        boolean isFinanceReviewerView = financialInformationTab.isViewDetailPayeeDisplayed();
        //view as Licensing Admin
        browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter)
                .clickUserMenuDropdownList()
                .clickSignOutButton()
                .clickSignInPage()
                .signIn(licAdmin)
                .navTopNavigation()
                .clickInternalTab()
                .clickOrganizationManagementTab()
                .clickAdvancedSearchLink()
                .enterOrgNameOnAdvanceModal(organizationName)
                .selectHomeRegionOnAdvanceModal("NOA")
                .clickIconSearchOnAdvanceModal()
                .clickOnOrg(organizationName)
                .clickFinancialInformation();
        boolean isLicAdminViewPayee = financialInformationTab.isPayeeDisplayed();
        boolean isLicAdminViewDetail = financialInformationTab.isViewDetailPayeeDisplayed();
        //Todo: bug - won't fix G3PTDEV4NX-29456
        //view as Licensing User
        browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter)
                .clickUserMenuDropdownList()
                .clickSignOutButton()
                .clickSignInPage()
                .signIn(licUser)
                .navTopNavigation()
                .clickInternalTab()
                .clickOrganizationManagementTab()
                .clickAdvancedSearchLink()
                .enterOrgNameOnAdvanceModal(organizationName)
                .selectHomeRegionOnAdvanceModal("NOA")
                .clickIconSearchOnAdvanceModal()
                .clickOnOrg(organizationName)
                .clickFinancialInformation();
        boolean isLicUserViewPayee = financialInformationTab.isPayeeDisplayed();
        boolean isLicUserViewDetail = financialInformationTab.isViewDetailPayeeDisplayed();

        assertAll(() -> assertTrue(isPartnerView, "Partner can View Details of Payee"),
                () -> assertTrue(isFinanceReviewerView, "Finance Reviewer will see the View Detail link in the tile."),
                () -> assertTrue(isLicAdminViewPayee, "Licensing Admin will see the list"),
                () -> assertFalse(isLicAdminViewDetail, "Licensing Admin will not see the View Detail link in the tile."),
                () -> assertTrue(isLicUserViewPayee, "Licensing User will see the list"),
                () -> assertFalse(isLicUserViewDetail, "Licensing User will not see the View Detail link in the tile."));
    }

    @Test
    @Order(37)
    public void partnerEditAndUpdateInformationInFinanceForm_Step25(IJUnitTestReporter testReporter) {
        NDPFinanceInformationPage financeInformationPage = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter)
                .clickUserMenuDropdownList()
                .clickSignOutButton()
                .clickSignInPage()
                .signIn(ndpUserA)
                .navTopNavigation()
                .clickAdmin()
                .clickFinanceInformationMenu()
                .clickViewDetailNintendoSwitchPayee()
                .clickEditButton()
                .enterAccountName(payeeName + " Test nintendoSwitch update")
                .enterAccountNumber("32356788")
                .selectAccountCurrency()
                .clickSubmitButton();
    }

    @Test()
    @Order(38)
    public void verifyUpdatePayeeInformationSuccessfully_Expected25(IJUnitTestReporter testReporter) {
        NDPFinanceInformationPage financeInformationPage = browser.getPage(NDPFinanceInformationPage.class, testReporter);
        assertAll(() -> assertTrue(financeInformationPage.isPayeeCreated(payeeName + " Test nintendoSwitch update - Bank name (..6788)"), "Verify update payee information is displayed"),
                () -> assertEquals("Bank account info has been updated successfully", financeInformationPage.getSuccessMessage(), "Verify payee is updated successfully"));
    }

    @Test
    @Order(39)
    public void accessToInbucketAgain_Step26(IJUnitTestReporter testReport) {
        EmailPage emailPage = browser.openURL(EmailPage.class, PageState.PRE_LOGIN, testReport);
    }

    @Test
    @Order(40)
    public void checkEmailSentToPartnerUser_Expected26(IJUnitTestReporter testReport) {
        // F3 sent to Partner - Company Finance user
        Map<String, String> data = new HashMap<>();
        data.put("USER_FULL_NAME", payeeName+ " Test nintendoSwitch update");
        data.put("LOCATION_ADDRESS",accountHolderStreet1+", "+accountHolderCity+" "+postalCode+" "+countryAccount);
        data.put("BANK_NAME", bankName);
        data.put("COMPANY_NAME", organizationName);
        data.put("TARGET_URL", "https://developer.nintendo.com/group/development/admin/financial-information");
        EmailReference emailF3 = EmailReferenceManager.getInstance().compose(EmailTemplate.TEMPLATE_EMAIL_COMPANY_FINANCIAL_PAYEE_INFORMATION_UPDATED_EXTERNAL, data);
        InbucketEmail msgF3 = MailBoxManager.getInstance().getInbucketEmail(ndpUserA.getUserName(), "Nintendo Developer Portal - Payee Information Updated");
        assertAll(() -> assertEquals(emailF3.getSubject().getEn(), msgF3.getSubject().getText(),
                        "Subject Content F3"),
                () -> assertEquals(emailF3.getBodyHtml().getEn(), msgF3.getBody().getHtml(),
                        "Body Content F3"));

    }

    @Test
    @Order(41)
    public void partnerClickEditFinanceInformation_Step27(IJUnitTestReporter testReporter) {
        NDPFinanceInformationPage financeInformationPage = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter)
                .navTopNavigation()
                .clickAdmin()
                .clickFinanceInformationMenu();
        financeInformationPage.clickViewDetailNintendoSwitchPayee().clickEditButton();
    }

    @Test()
    @Order(42)
    public void selectATemplateOForBankCountry_Step28(IJUnitTestReporter testReporter) {
        NDPFinanceInformationPage financeInformationPage = browser.getPage(NDPFinanceInformationPage.class, testReporter);
        financeInformationPage.selectCountryPayeeBank("France");
    }

    @Test()
    @Order(43)
    public void confirmDisplayAllItemInBankTemplateO_Expected28(IJUnitTestReporter testReporter) {
        NDPFinanceInformationPage financeInformationPage = browser.getPage(NDPFinanceInformationPage.class, testReporter);
        List<String> accountTypeList = List.of("Savings", "Checking");
        assertAll(() -> assertTrue(financeInformationPage.isAccountHolderNameDisplayTextField(), "Account Holder Name: text field"),
                () -> assertTrue(financeInformationPage.isAccountHolderNameDisplayRequiredField(), "Account Holder Name: required"),
                () -> assertTrue(financeInformationPage.isAccountHolderStreet1DisplayTextField(), "Account Holder Street 1: text field"),
                () -> assertTrue(financeInformationPage.isAccountHolderStreet1DisplayRequiredField(), "Account Holder Street 1: required"),
                () -> assertTrue(financeInformationPage.isAccountHolderStreet2DisplayTextField(), "Account Holder Street 2: text field"),
                () -> assertFalse(financeInformationPage.isAccountHolderStreet2DisplayRequiredField(), "Account Holder Street 2: no required"),
                () -> assertTrue(financeInformationPage.isAccountHolderCityDisplayTextField(), "Account Holder City: text field"),
                () -> assertTrue(financeInformationPage.isAccountHolderCityDisplayRequiredField(), "Account Holder City: required"),
                () -> assertTrue(financeInformationPage.isAccountHolderPostalCodeDisplayTextField(), "Account Holder Postal Code: text field"),
                () -> assertTrue(financeInformationPage.isAccountHolderPostalCodeDisplayRequiredField(), "Account Holder Postal Code: required"),
                () -> assertTrue(financeInformationPage.isCountryAccountHolderDisplayRequiredField(), "Country/Region: Drop-down list, required"),
                () -> assertTrue(financeInformationPage.isAccountHolderStateProvinceRegionDisplayTextField(), "Account Holder State/Province/Region: text field"),
                () -> assertFalse(financeInformationPage.isAccountHolderStateProvinceRegionDisplayRequiredField(), "Account Holder State/Province/Region: no required"),
                () -> assertTrue(financeInformationPage.isIBANCodeDisplayTextField(), "IBAN Code: text field"),
                () -> assertTrue(financeInformationPage.isIBANCodeDisplayRequiredField(), "IBAN Code: required"),
                () -> assertEquals(accountTypeList, financeInformationPage.getAccountTypeOptions(), "Account Type: Enum (Single), Check or Savings"),
                () -> assertTrue(financeInformationPage.isCountryAccountTypeRequiredField(), "Account Type: required"),
                () -> assertTrue(financeInformationPage.isBankNameDisplayTextField(), "Bank Name: text field"),
                () -> assertTrue(financeInformationPage.isBankNameDisplayRequiredField(), "Bank Name: required"),
                () -> assertTrue(financeInformationPage.isBankNumberDisplayTextField(), "Bank Number: text field"),
                () -> assertTrue(financeInformationPage.isBankNumberDisplayRequiredField(), "Bank Number: required"),
               /* ()->assertTrue(financeInformationPage.isBankOfficeNumberDisplayTextField(),""),
                ()->assertTrue(financeInformationPage.isBankOfficeNumberDisplayRequiredField(),""),*/ //Todo: BUG - backlog: G3PTDEV4NX-10088
                () -> assertTrue(financeInformationPage.isBicSwiftCodeDisplayTextField(), "BIC/SWIFT Code: text field"),
                () -> assertTrue(financeInformationPage.isBicSwiftCodeDisplayRequiredField(), "BIC/SWIFT Code: required"));
    }

    @Test
    @Order(44)
    public void partnerClickEditFinanceInformation_Step29(IJUnitTestReporter testReporter) {
        NDPFinanceInformationPage financeInformationPage = browser.getPage(NDPFinanceInformationPage.class, testReporter);
        financeInformationPage.clickCancelButtonAndCloseAlert()
                .clickViewDetailNintendoSwitchPayee()
                .clickEditButton();
    }

    @Test()
    @Order(45)
    public void selectATemplateEForBankCountryCanada_Step30(IJUnitTestReporter testReporter) {
        NDPFinanceInformationPage financeInformationPage = browser.getPage(NDPFinanceInformationPage.class, testReporter);
        financeInformationPage.selectCountryPayeeBank("Canada");
    }

    @Test()
    @Order(46)
    public void verifyDisplayFormFields_Expected30(IJUnitTestReporter testReporter) {
        NDPFinanceInformationPage financeInformationPage = browser.getPage(NDPFinanceInformationPage.class, testReporter);
        assertAll(() -> assertFalse(financeInformationPage.isFieldByNameDisplayed("Branch Transit Number"), "The Branch Transit Number not display in form."),
                () -> assertTrue(financeInformationPage.isFieldByNameDisplayed("Bank Routing Number"), "The Bank Routing Number display in form."));
    }

    @Test
    @Order(47)
    public void addNewPayeeOnNintendo3DSWiiU_Step31(IJUnitTestReporter testReporter) {
        NDPFinanceInformationPage financeInformationPage = browser.getPage(NDPFinanceInformationPage.class, testReporter);
        financeInformationPage.clickCancelButtonAndCloseAlert()
                .clickAddNewPayeeNintendo3DSWiiU()
                .selectCountryPayeeBank("Bahamas")
                .enterAccountName(payeeName + " Test regional 3rd")
                .enterAccountHolderStreet1(accountHolderStreet1)
                .enterAccountHolderCity(accountHolderCity)
                .enterAccountHolderPostalCode(postalCode)
                .selectCountryAccountHolder(countryAccount)
                .enterAccountNumber("32351222")
                .selectAccountType("Savings")
                .enterBankName(bankName)
                .enterBranchOfficeName("Branch Office Name")
                .enterBicSwiftCode("BICSWIFT")
                .clickNext()
                .clickCancelButton();
    }

    @Test
    @Order(48)
    public void loginNDPByLicensingAdmin_Step32(IJUnitTestReporter testReporter) {
        browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter)
                .clickUserMenuDropdownList()
                .clickSignOutButton()
                .clickSignInPage()
                .signIn(licAdmin);
    }

    @Test
    @Order(49)
    public void navigateToPayeeManagement_Step33(IJUnitTestReporter testReporter) {
        browser.getPage(NDPDevelopmentHome.class, testReporter).navTopNavigation()
                .clickInternalTab()
                .clickFinance()
                .clickPayeeManagement();
    }

    @Test
    @Order(50)
    public void verifyNewlyCreatedPayeeDisplayedInPayeeManagement_Expected33(IJUnitTestReporter testReporter) {
        NDPPayeeManagementPage payeeManagementPage = browser.getPage(NDPPayeeManagementPage.class, testReporter);
        payeeManagementPage.enterPayee(payeeName + " Test regional 3rd");
        assertTrue(payeeManagementPage.isPayeeDisplayed(payeeName + " Test regional 3rd"), "The newly created payee is displayed in Payee Management");
    }

    @Test
    @Order(51)
    public void selectNewlyCreatedPayee_Step34(IJUnitTestReporter testReporter) {
        NDPPayeeManagementPage payeeManagementPage = browser.getPage(NDPPayeeManagementPage.class, testReporter);
        payeeManagementPage.selectPayeeByName(payeeName + " Test regional 3rd");
    }

    @Test
    @Order(52)
    public void verifyOrganizationFieldInPayeeDetail_Expected34(IJUnitTestReporter testReporter) {
        NDPPayeeManagementPage payeeManagementPage = browser.getPage(NDPPayeeManagementPage.class, testReporter);
        String getTitleNo1 = payeeManagementPage.getTitleNameInPayeeDetailByNo(1);
        String getTitleNo2 = payeeManagementPage.getTitleNameInPayeeDetailByNo(2);
        String getOrganizationNameDefault = payeeManagementPage.getOrganizationName();
        assertAll(() -> assertEquals("BMS Code", getTitleNo1, "the BMS Code field is displayed above"),
                () -> assertEquals("Organization Name", getTitleNo2, "the Organization Name field is displayed below"),
                () -> assertEquals(organizationName, getOrganizationNameDefault, "Default value of Organization Name field is Organization Name that partner registered"));
    }

    @Test
    @Order(53)
    public void enterBMSCodeAndOrganizationField_Step35(IJUnitTestReporter testReporter) {
        NDPPayeeManagementPage payeeManagementPage = browser.getPage(NDPPayeeManagementPage.class, testReporter);
        payeeManagementPage.enterBMSCode(bmsCode)
                .enterOrganizationName(organizationName + "カタカナ");
    }

    @Test
    @Order(54)
    public void clickSaveBMSButton_Step36(IJUnitTestReporter testReporter) {
        NDPPayeeManagementPage payeeManagementPage = browser.getPage(NDPPayeeManagementPage.class, testReporter);
        payeeManagementPage.clickSaveBMSCodeButton().clickYesButtonOnConfirmModal();
    }

    @Test
    @Order(55)
    public void verifyErrorAfterSaveBMSCodeNoSuccessfully_Expected36(IJUnitTestReporter testReporter) {
        NDPPayeeManagementPage payeeManagementPage = browser.getPage(NDPPayeeManagementPage.class, testReporter);
        String error = "Updating BMS Code Failed.\n" +
                "parameter 'name' is invalid";
        assertEquals(error, payeeManagementPage.getErrorMessage(), "BMS Code is not created successfully, An error message box is displayed");
    }

    @Test
    @Order(56)
    public void enterBMSCodeAndOrganizationFieldAgain_Step37(IJUnitTestReporter testReporter) {
        NDPPayeeManagementPage payeeManagementPage = browser.getPage(NDPPayeeManagementPage.class, testReporter);
        String newOrgName = DataGen.getRandomString(64).toUpperCase(Locale.ROOT);
        payeeManagementPage.enterBMSCode(bmsCode)
                .enterOrganizationName(newOrgName);
    }

    @Test
    @Order(57)
    public void clickSaveBMSButton_Step38(IJUnitTestReporter testReporter) {
        NDPPayeeManagementPage payeeManagementPage = browser.getPage(NDPPayeeManagementPage.class, testReporter);
        payeeManagementPage.clickSaveBMSCodeButton().clickYesButtonOnConfirmModal();
    }

    @Test
    @Order(58)
    public void verifyMessageAfterSaveBMSCodeSuccessfully_Expected38(IJUnitTestReporter testReporter) {
        NDPPayeeManagementPage payeeManagementPage = browser.getPage(NDPPayeeManagementPage.class, testReporter);
        String message = "Payee has been updated successfully.";
        assertEquals(message, payeeManagementPage.getSuccessMessage(), "BMS Code is added for Regional payee successfully, An message box is displayed");
    }

    @Test
    @Order(59)
    public void navigateToBasicInformation_Step39(IJUnitTestReporter testReporter) {
        NDPViewOrganizationPage viewOrganizationPage = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter)
                .navTopNavigation()
                .clickInternalTab()
                .clickOrganizationManagementTab()
                .clickAdvancedSearchLink()
                .enterOrgNameOnAdvanceModal(organizationName)
                .selectHomeRegionOnAdvanceModal("NOA")
                .clickIconSearchOnAdvanceModal()
                .clickOnOrg(organizationName);
    }

    @Test
    @Order(60)
    public void verifyLegalOrganizationNameInBasicInformation_Expected39(IJUnitTestReporter testReporter) {
        NDPViewOrganizationPage viewOrganizationPage = browser.getPage(NDPViewOrganizationPage.class, testReporter);
        assertEquals(organizationName, viewOrganizationPage.getLegalOrganizationName(), "Legal Name is not updated according to the value that Licensing user updated in Organization Name field");
    }
}
