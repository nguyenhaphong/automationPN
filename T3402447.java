package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.junit.TestLogger;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.data.DataGen;
import net.nintendo.automation.ui.models.admin.AdminHomePage;
import net.nintendo.automation.ui.models.admin.AdminUserDetailPage;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.ndidMS.ManagementSystemLogin;
import net.nintendo.automation.ui.models.ndp.NDPBasicInformationTab;
import net.nintendo.automation.ui.models.ndp.NDPCompanyNDARequiredPage;
import net.nintendo.automation.ui.models.ndp.NDPCreateProductPage;
import net.nintendo.automation.ui.models.ndp.NDPCustomAgreementsPage;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPFinanceInformationPage;
import net.nintendo.automation.ui.models.ndp.NDPFinancialInformationTab;
import net.nintendo.automation.ui.models.ndp.NDPFindPartnerModal;
import net.nintendo.automation.ui.models.ndp.NDPGettingStartedPage;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPLicensingWaiverRequestsTab;
import net.nintendo.automation.ui.models.ndp.NDPMyAccountPage;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.NDPOrganizationInformationPage;
import net.nintendo.automation.ui.models.ndp.NDPOrganizationRelationshipsTab;
import net.nintendo.automation.ui.models.ndp.NDPPayeeManagementPage;
import net.nintendo.automation.ui.models.ndp.NDPSignInPage;
import net.nintendo.automation.ui.models.ndp.NDPViewOrganizationPage;
import net.nintendo.automation.ui.models.ndp.NDPViewTaskPage;
import net.nintendo.automation.ui.models.ndp.admin.NDPUserManagementPage;
import net.nintendo.automation.ui.models.ndp.admin.NDPViewUserPage;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoFeaturesPage;
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
import net.nintendo.automation_core.common_utils.enums.LotcheckRegion;
import net.nintendo.automation_core.extent_reports.extensions.IJUnitTestReporter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Classname: T3402447
 * Version: 1.0.0
 * Purpose:
 */
@Tag("T3402447")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3402447 extends CommonBaseTest {
    private static Browser browser;
    private static User romUser;
    private static User ndpUserA;
    private static User ndpUserB;
    private static User adminUser;
    private static String organizationName_A;
    private static String bmsCode_expected;
    private static String organizationUUID_B;
    private static String successMessageAddGlobalAgreement;
    private static String successMessageAddAmericaAgreement;
    private static String successMessageAddSubRelationship;
    private static String current_page_A;
    private static String current_page_B;
    private static User slcmsUser;
    private static User testrailUser;
    private static User licPowerUser;
    private static User licApproverUser;
    private static User licApproverCoordUser;
    private static User licAdmin;
    private static String publishingRelationship;
    private static String applicationId;
    private static String gameCode;
    private static String romInitialName;
    private static CommonUIComponent currentPage;
    private static TestLogger logger;

    private static String productName;

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();

        ndpUserA = new User();
        ndpUserA.setPassword(TextConstants.nintendo_approval_Password);
        ndpUserA.setUserName(DataGen.getRandomUserName());

        ndpUserB = new User();
        ndpUserB.setPassword(TextConstants.nintendo_approval_Password);
        ndpUserB.setUserName(DataGen.getRandomUserName());
        adminUser = new User();
        adminUser.setPassword(TextConstants.nintendo_approval_Password);
        adminUser.setUserName(TextConstants.omniAdmin);
        licPowerUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_pwr_user_NCL);
        licApproverUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approver_NCL);
        licApproverCoordUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
        licAdmin = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_admin_NCL);
        slcmsUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);
        testrailUser = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER);
        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);

        publishingRelationship = TextConstants.third_Party_Type;
        productName = DataGen.getRandomProductName();
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
    }

    @AfterAll
    static void terminateBrowser() {
        BrowserManager.closeBrowser(browser);
        //UserManager.releaseUser(ServiceType.NDP, ndpUserA);
        //UserManager.releaseUser(ServiceType.NDP, ndpUserB);
        UserManager.releaseUser(ServiceType.SLCMS, slcmsUser);
        UserManager.releaseUser(ServiceType.SEARCH, romUser);
        UserManager.releaseUser(ServiceType.NDP, licApproverUser);
        UserManager.releaseUser(ServiceType.NDP, licPowerUser);
        UserManager.releaseUser(ServiceType.PMMS, testrailUser);
    }

    @BeforeEach
    public void each(IJUnitTestReporter testReport) {
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
    }

    @Test()
    @Order(1)
    public void registerTwoOrganizationsAndSignTheNDAForBoth_Step1(IJUnitTestReporter testReporter) {
        String genOrganization = DataGen.getRandomOrganizationName();
        organizationName_A = genOrganization + "123@!%*";
        bmsCode_expected = genOrganization.replaceAll(" ", "").toUpperCase(Locale.ROOT) + "123";
        NDPHomePage homePage = (NDPHomePage) currentPage;
        homePage.acceptCookie();
        CommonAction.createOrganization(homePage, organizationName_A, ndpUserA, "Address Line A", "Ha Noi", "Bahamas", "ASEAN", "000010");
        ManagementSystemLogin managementSystemLogin = browser.openURL(ManagementSystemLogin.class, PageState.PRE_LOGIN, testReporter);
        managementSystemLogin.signIn(adminUser)
                .clickManageUsers()
                .enterNintendoDeveloperID(ndpUserA.getUserName())
                .clickSearchButton()
                .clickUserNameInTableByNintendoID(ndpUserA.getUserName())
                .clickChangePassword()
                .enterYourPassword(ndpUserA.getPassword())
                .enterNewPassword(ndpUserA.getPassword())
                .enterConfirmNewPassword(ndpUserA.getPassword())
                .selectOkButton();
        browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReporter);
        NDPSignInPage signInPage = homePage.clickSignInPage();
        if (signInPage.signInNewlyRegisteredOrganizationAndIsOrganizationApproved(ndpUserA, true)) {
            NDPCompanyNDARequiredPage companyNDARequiredPage = browser.getPage(NDPCompanyNDARequiredPage.class, testReporter);
            companyNDARequiredPage.selectByClickingTheSubmitButtonCheckbox();
            if (companyNDARequiredPage.clickSubmitButtonWithError()) {
                currentPage = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter).clickGettingStarted();
            } else {
                currentPage = companyNDARequiredPage.afterClickSubmitButtonNotError().clickHereLink();
            }
        } else {
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
            currentPage = viewTaskPage.clickUserMenuDropdownList()
                    .clickSignOutButton()
                    .clickSignInPage()
                    .signIn(ndpUserA)
                    .clickGettingStarted();
        }
        NDPGettingStartedPage gettingStartedPage = (NDPGettingStartedPage) currentPage;
        current_page_A = gettingStartedPage.getPageTitle();

        NDPHomePage ndpHomePage = gettingStartedPage.clickUserMenuDropdownList().clickSignOutButton();

        CommonAction.createOrganization(ndpHomePage, DataGen.getRandomOrganizationName(), ndpUserB, "Address Line B", "Ha noi", "Bahamas", "ASEAN", "000010");
        browser.openURL(ManagementSystemLogin.class, PageState.PRE_LOGIN, testReporter);
        managementSystemLogin.signIn(adminUser)
                .clickManageUsers()
                .enterNintendoDeveloperID(ndpUserB.getUserName())
                .clickSearchButton()
                .clickUserNameInTableByNintendoID(ndpUserB.getUserName())
                .clickChangePassword()
                .enterYourPassword(ndpUserB.getPassword())
                .enterNewPassword(ndpUserB.getPassword())
                .enterConfirmNewPassword(ndpUserB.getPassword())
                .selectOkButton();

        browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReporter);
        homePage.clickSignInPage();
        if (signInPage.signInNewlyRegisteredOrganizationAndIsOrganizationApproved(ndpUserB, false)) {
            NDPCompanyNDARequiredPage companyNDARequiredPage = browser.getPage(NDPCompanyNDARequiredPage.class, testReporter);
            companyNDARequiredPage.selectByClickingTheSubmitButtonCheckbox();
            if (companyNDARequiredPage.clickSubmitButtonWithError()) {
                currentPage = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter).clickGettingStarted();
            } else {
                currentPage = companyNDARequiredPage.afterClickSubmitButtonNotError().clickHereLink();
            }
        } else {
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
            currentPage = viewTaskPage.clickUserMenuDropdownList()
                    .clickSignOutButton()
                    .clickSignInPage()
                    .signIn(ndpUserB)
                    .clickGettingStarted();
        }
        current_page_B = gettingStartedPage.getPageTitle();

        NDPBasicInformationTab basicInformationTab = gettingStartedPage.navTopNavigation().clickAdmin().clickOrganizationInformationMenu().navBasicInformationTab();
        organizationUUID_B = basicInformationTab.getOrganizationUUID();
        currentPage = basicInformationTab;
    }

    @Test()
    @Order(2)
    public void verifyOrganizationsAreRegisteredSuccessfully_Expected1() {
        NDPBasicInformationTab basicInformationTab = (NDPBasicInformationTab) currentPage;
        currentPage = basicInformationTab.navTopNavigationBar().clickUserMenuDropdownList().clickSignOutButton();
        assertAll(() -> assertEquals("Getting Started - Nintendo Developer Portal", current_page_A, "Verify organization A are registered successfully."),
                () -> assertEquals("Getting Started - Nintendo Developer Portal", current_page_B, "Verify organization B are registered successfully."));
    }

    @Test()
    @Order(3)
    public void loginNdpByLicPowerUserAndNavigateToCustomAgreements_Step2() {
        NDPHomePage homePage = (NDPHomePage) currentPage;
        currentPage = homePage.clickSignInPage().signIn(licPowerUser).clickInternalTab().clickCustomAgreements();
    }

    @Test()
    @Order(4)
    public void verifyTheCustomAgreementsPortletIsDisplayed_Expected2() {
        NDPCustomAgreementsPage customAgreementsPage = (NDPCustomAgreementsPage) currentPage;
        assertEquals("Custom Agreements - Nintendo Developer Portal", customAgreementsPage.getPageTitle(), "Verify the Custom Agreements portlet is displayed");
    }

    @Test()
    @Order(5)
    public void selectSearchIcon_Step3() {
        NDPCustomAgreementsPage customAgreementsPage = (NDPCustomAgreementsPage) currentPage;
        currentPage = customAgreementsPage.clickSearchIcon();
    }

    @Test()
    @Order(6)
    public void insertOrganizationName_Step4() {
        NDPFindPartnerModal findPartnerModal = (NDPFindPartnerModal) currentPage;
        findPartnerModal.enterOrganizationName(organizationName_A)
                .clickSearch();
    }

    @Test()
    @Order(7)
    public void verifyFindPartnerModal_ManualSupport_Expected4() {
        NDPFindPartnerModal findPartnerModal = (NDPFindPartnerModal) currentPage;
        findPartnerModal.logInfoWithScreenshot();
    }

    @Test()
    @Order(8)
    public void executeSwitchDigitalGlobalAndSwitchPackageGoodAmericaAgreementForA_Step5() {
        NDPFindPartnerModal findPartnerModal = (NDPFindPartnerModal) currentPage;
        NDPCustomAgreementsPage customAgreementsPage = findPartnerModal.selectOrg(organizationName_A).clickChooseSelectedPartner();
        customAgreementsPage.selectPlatform("Nintendo Switch")
                .selectAgreementType("Publisher Agreement (Digital)")
                .selectRegion("Global")
                .clickUpload()
                .selectFileInPC("Judgement.pdf")
                .setExecutionDate(0)
                .setExpirationDate(100)
                .selectAgreementApprove()
                .clickSubmit();
        successMessageAddGlobalAgreement = customAgreementsPage.getSuccessMessage();
        customAgreementsPage.clickSearchIcon()
                .enterOrganizationName(organizationName_A)
                .clickSearch()
                .selectOrg(organizationName_A).clickChooseSelectedPartner()
                .selectPlatform("Nintendo Switch")
                .selectAgreementType("Publisher Agreement (Packaged Goods)")
                .selectRegion("Americas")
                .clickUpload()
                .selectFileInPC("Judgement.pdf")
                .setExecutionDate(0)
                .setExpirationDate(100)
                .selectAgreementApprove()
                .clickSubmit();
        successMessageAddAmericaAgreement = customAgreementsPage.getSuccessMessage();
        currentPage = customAgreementsPage;
    }

    @Test()
    @Order(9)
    public void verifyAddAgreementsSuccessfully_Expected5() {
        NDPCustomAgreementsPage customAgreementsPage = (NDPCustomAgreementsPage) currentPage;
        currentPage = customAgreementsPage.navTopNavigationBar().clickUserMenuDropdownList().clickSignOutButton();
        assertAll(() -> assertEquals("Agreement has been saved.", successMessageAddGlobalAgreement, "Verify add agreements global successfully"),
                () -> assertEquals("Agreement has been saved.", successMessageAddAmericaAgreement, "Verify add agreements americas successfully"));
    }

    @Test()
    @Order(10)
    public void loginNdpByOrgAAndNavigateToOrganizationInformation_Step6() {
        NDPHomePage homePage = (NDPHomePage) currentPage;
        currentPage = homePage.clickSignInPage().signIn(ndpUserA).navTopNavigation().clickAdmin().clickOrganizationInformationMenu();

    }

    @Test()
    @Order(11)
    public void verifyTheOrganizationInformationIsDisplayed_Expected6() {
        NDPOrganizationInformationPage organizationInformationPage = (NDPOrganizationInformationPage) currentPage;
        assertEquals("Organization Information - Nintendo Developer Portal", organizationInformationPage.getPageTitle(), "Verify The Organization Information page is displayed");
    }

    @Test()
    @Order(12)
    public void clickOrganizationRelationshipsTabAndAddRelationship_Step7() {
        NDPOrganizationInformationPage organizationInformationPage = (NDPOrganizationInformationPage) currentPage;
        NDPOrganizationRelationshipsTab organizationRelationshipsTab = organizationInformationPage.clickOrganizationRelationshipsTab();
        organizationRelationshipsTab.clickAddRelationship()
                .enterRelatedOrganizationUUID(organizationUUID_B)
                .selectRelatedOrganizationRelationship("Sub-Organization (child)")
                .selectSharedPublishingAgreements()
                .clickSubmit();
        successMessageAddSubRelationship = organizationRelationshipsTab.getSuccessMessage();
        currentPage = organizationRelationshipsTab;
    }

    @Test()
    @Order(13)
    public void verifyAddSubRelationship_Expected7() {
        NDPOrganizationRelationshipsTab organizationRelationshipsTab = (NDPOrganizationRelationshipsTab) currentPage;
        currentPage = organizationRelationshipsTab.navTopNavigationBar().clickUserMenuDropdownList().clickSignOutButton();
        String expectContent = "Your request is being reviewed. You will be notified by email when the request has been completed.";
        assertEquals(expectContent, successMessageAddSubRelationship, "Verify add relationship successfully ");
    }

    @Test()
    @Order(14)
    public void loginNdpByLicApproveAndApproveRelationshipRequest_Step8() {
        NDPHomePage homePage = (NDPHomePage) currentPage;
        currentPage = homePage.clickSignInPage().signIn(licApproverUser).clickOnMyTaskMenu().clickOnTask("Organization Relationship Request")
                .selectAssignToMeButton()
                .selectOKButtonInAssignToMeModal()
                .selectApproveRequestButton()
                .selectOKButtonInApproveRequestModal()
                .waitingForApproveSuccessful();
    }

    @Test()
    @Order(15)
    public void verifyOrganizationRelationshipIsCorrect_Expected8() {
        NDPViewTaskPage viewTaskPage = (NDPViewTaskPage) currentPage;
        browser.refresh();
        NDPOrganizationRelationshipsTab organizationRelationshipsTab = viewTaskPage.clickUserMenuDropdownList()
                .clickSignOutButton().clickSignInPage().signIn(ndpUserA).navTopNavigation().clickAdmin()
                .clickOrganizationInformationMenu().clickOrganizationRelationshipsTab();
        boolean isOrganizationRelationshipCorrect = organizationRelationshipsTab.isOrganizationRelationshipCorrect(organizationUUID_B, "Sub", "ALL-agreements");
        assertTrue(isOrganizationRelationshipCorrect, "Verify organization relationship info are correct");
    }

    @Test()
    @Order(16)
    public void loginNdpAdminAndAssignCompanyNxAccessRoleForUserAAndUserB_Step9(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(AdminHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        AdminHomePage adminHomePage = (AdminHomePage) currentPage;
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

    }

    @Test()
    @Order(17)
    public void verifyOrgCanAccessToTheCompanyNxUserRole_Expected9(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        NDPDevelopmentHome developmentHome = (NDPDevelopmentHome) currentPage;
        NDPMyAccountPage myAccountPage = developmentHome.clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(ndpUserB).clickUserMenuDropdownList().clickManageAccount().clickEditProfileButton();
        boolean isCompanyNXUserDisplayed_userA = myAccountPage.isCompanyNXUserDisplayed();
        myAccountPage.clickCancel().navTopNavigationBar().clickUserMenuDropdownList().clickSignOutButton()
                .clickSignInPage().signIn(ndpUserA).clickUserMenuDropdownList().clickManageAccount().clickEditProfileButton();
        boolean isCompanyNXUserDisplayed_userB = myAccountPage.isCompanyNXUserDisplayed();
        currentPage = myAccountPage;
        assertAll(() -> assertTrue(isCompanyNXUserDisplayed_userA, "Verify org A can access to the Company NX User role."),
                () -> assertTrue(isCompanyNXUserDisplayed_userB, "Verify org B can access to the Company NX User role."));
    }

    @Test()
    @Order(18)
    public void byUserANavigateToUserManagement_Step10() {
        NDPMyAccountPage myAccountPage = (NDPMyAccountPage) currentPage;
        currentPage = myAccountPage.navTopNavigation().clickAdmin().clickUserManagementMenu();
    }

    @Test()
    @Order(19)
    public void verifyUserManagementPageIsDisplayed_Expected10() {
        NDPUserManagementPage managementPage = (NDPUserManagementPage) currentPage;
        assertEquals("User Management - Nintendo Developer Portal", managementPage.getPageTitle(), "Verify the User Management page is displayed");

    }

    @Test()
    @Order(20)
    public void assignCompanyNxUserAndCompanyFinanceUserRolesForUserA_Step11() {
        NDPUserManagementPage managementPage = (NDPUserManagementPage) currentPage;
        currentPage = managementPage.selectCompanyUserByNDID(ndpUserA.getUserName().toLowerCase(Locale.ROOT)).clickEditProfileButton()
                .selectCompanyNxUserRole()
                .selectCompanyFinanceUserRole()
                .clickSaveChangesButton();
    }

    @Test()
    @Order(21)
    public void verifyUserCanAccessMyProductsPage_Expected11() {
        NDPViewUserPage viewUserPage = (NDPViewUserPage) currentPage;
        NDPMyProductsPage myProductsPage = viewUserPage.navTopNavigation().clickMyProducts();
        currentPage = myProductsPage;
        assertEquals("My Products - Nintendo Developer Portal", myProductsPage.getPageTitle(), "Verify user A can access my products page");
    }

    @Test()
    @Order(22)
    public void setJapanLanguageAndClickAddNewPayeeTaxForNintendoSwitchPayee_Step12() {
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        myProductsPage.navTopNavigationBar().clickUserMenuDropdownList().selectJapanLanguage();
        currentPage = myProductsPage.navTopNavigation().clickAdmin().clickFinanceInformationMenu().clickAddNewPayeeNintendoSwitch();
    }

    @Test()
    @Order(23)
    public void verifyThePayeeCreateFormIsDisplayed_Expected12() {
        NDPFinanceInformationPage financeInformationPage = (NDPFinanceInformationPage) currentPage;
        assertTrue(financeInformationPage.isThePayeeCreateFormDisplayed(), "Verify the payee creation form is displayed");
    }

    @Test()
    @Order(24)
    public void fillOutRequiredInformationAndSubmitForm_Step13() {
        NDPFinanceInformationPage financeInformationPage = (NDPFinanceInformationPage) currentPage;
        financeInformationPage.selectCountryPayeeBank("Bahamas / バハマ")
                .enterAccountName("Test account name auto")
                .enterAccountHolderStreet1("Account Holder Street1")
                .enterAccountHolderCity("Account Holder City")
                .enterAccountHolderPostalCode("123")
                .selectCountryAccountHolder("Bahamas / バハマ")
                .enterAccountNumber("32356789")
                .selectAccountCurrency()
                .selectAccountType("普通")
                .enterBankName("Bank name")
                .enterBranchOfficeName("Branch Office Name")
                .enterBicSwiftCode("TESTCODE")
                .clickNext();
    }

    @Test()
    @Order(25)
    public void verifyPayeeIsCreatedSuccessfully_Expected13() {
        NDPFinanceInformationPage financeInformationPage = (NDPFinanceInformationPage) currentPage;
        assertAll(() -> assertTrue(financeInformationPage.isPayeeCreated("Test account name auto - Bank name (..6789)"), "Verify payee is displayed"),
                () -> assertEquals("銀行口座情報が正常に更新されました。", financeInformationPage.getSuccessMessage(), "Verify payee is created successfully"));

    }

    @Test()
    @Order(26)
    public void loginNdpByLicAdminNclAndNavigateToPayeeManagementPage_Step14() {
        NDPFinanceInformationPage financeInformationPage = (NDPFinanceInformationPage) currentPage;
        NDPHomePage homePage = financeInformationPage.navTopNavigationBar().clickUserMenuDropdownList().clickSignOutButton();
        currentPage = homePage.clickSignInPage().signIn(licAdmin).clickInternalTab().clickFinance().clickPayeeManagement();
    }

    @Test()
    @Order(27)
    public void verifyNewlyCreatePayeeIsNotDisplayed_Expected14() {
        NDPPayeeManagementPage payeeManagementPage = (NDPPayeeManagementPage) currentPage;
        payeeManagementPage.enterPayee("Test account name auto");
        assertTrue(payeeManagementPage.isPayeeNotDisplayed(), "Verify the newly created payee is NOT displayed");
    }

    @Test()
    @Order(28)
    public void navigateToOrganizationManagement_Step15() {
        NDPPayeeManagementPage payeeManagementPage = (NDPPayeeManagementPage) currentPage;
        currentPage = payeeManagementPage.clickOrganizationManagementTab()
                .clickAdvancedSearchLink()
                .enterOrgNameOnAdvanceModal(organizationName_A)
                .selectHomeRegionOnAdvanceModal(LotcheckRegion.NOA.toString())
                .clickIconSearchOnAdvanceModal()
                .clickOnOrg(organizationName_A);
    }

    @Test()
    @Order(29)
    public void selectFinancialInformationTab_Step16() {
        NDPViewOrganizationPage viewOrganizationPage = (NDPViewOrganizationPage) currentPage;
        currentPage = viewOrganizationPage.clickFinancialInformation();
    }

    @Test()
    @Order(30)
    public void verifyPayeeInfo_Expected16() {
        NDPFinancialInformationTab financialInformationTab = (NDPFinancialInformationTab) currentPage;
        assertAll(() -> assertEquals(bmsCode_expected, financialInformationTab.getBmsCode_Global(), "Verify bms code"),
                () -> assertEquals("Test account name auto - Bank name (..6789)", financialInformationTab.getPayeeName_Global(), "Verify payee name"),
                () -> assertEquals("GLOBAL", financialInformationTab.getPayeeRegionType_Global(), "Verify payee region type"),
                () -> assertEquals("N/A", financialInformationTab.getTaxInfo_Global(), "Verify tax info"));
    }

    @Test()
    @Order(31)
    public void logInNdpByUserA_Step17() {
        NDPFinancialInformationTab financialInformationTab = (NDPFinancialInformationTab) currentPage;
        currentPage = financialInformationTab.navTopNavigationBar().clickUserMenuDropdownList().clickSignOutButton()
                .clickSignInPage().signIn(ndpUserA);
    }

    @Test()
    @Order(32)
    public void createNewProduct_Step18() {
        NDPDevelopmentHome developmentHome = (NDPDevelopmentHome) currentPage;
        developmentHome.clickUserMenuDropdownList().selectEnglishAnyWay();
        currentPage = developmentHome.clickMyProducts().createNewProduct()
                .selectProductType(TextConstants.full_Product)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalJapanSalesRegion()
                .selectDigitalEuropeSalesRegion()
                .selectDigitalAmericasSalesRegion()
                .selectDigitalAsiaSalesRegion()
                .selectProductName(productName)
                .selectProductNameKana(TextConstants.productKana)
                .clickCreateButton();
    }

    @Test()
    @Order(33)
    public void navigateToReleaseErrors_Step19() {
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        currentPage = myProductsPage.clickProductByName(productName).clickReleases().selectInitialRelease().nav().clickIssues().openReleaseErrorsTab();
    }

    @Test()
    @Order(34)
    public void verifyBmsCodeErrorIsNotDisplayed_Expected19() {
        NDPReleaseInfoIssuePage releaseInfoIssuePage = (NDPReleaseInfoIssuePage) currentPage;
        assertTrue(releaseInfoIssuePage.isBmsCodeIssueNotDisplayed(), "Verify there is no BMS code error");
    }

    @Test()
    @Order(35)
    public void loginNdpByUserBAndAssignCompanyNxUserRole_Step20() {
        NDPReleaseInfoIssuePage releaseInfoIssuePage = (NDPReleaseInfoIssuePage) currentPage;
        currentPage = releaseInfoIssuePage.nav().clickUserMenuDropdownList().clickSignOutButton()
                .clickSignInPage().signIn(ndpUserB)
                .clickUserMenuDropdownList()
                .clickManageAccount()
                .clickEditProfileButton()
                .selectCompanyNxUserRole()
                .clickSaveChangesButton();
    }

    @Test()
    @Order(36)
    public void verifyUserBCanAccessMyProductsPage_Expected20() {
        NDPMyAccountPage myAccountPage = (NDPMyAccountPage) currentPage;
        NDPMyProductsPage myProductsPage = myAccountPage.navTopNavigation().clickMyProducts();
        currentPage = myProductsPage;
        assertEquals("My Products - Nintendo Developer Portal", myProductsPage.getPageTitle(), "Verify user can access my products page");

    }

    @Test()
    @Order(37)
    public void createProduct_Step21() {
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        currentPage = myProductsPage.createNewProduct();
    }

    @Test()
    @Order(38)
    public void verifyCreateProductFormIsDisplayed_Expected21() {
        NDPCreateProductPage createProductPage = (NDPCreateProductPage) currentPage;
        assertEquals("Create Product - Nintendo Developer Portal", createProductPage.getPageTitle(), "Verify create product form is displayed");
    }

    @Test()
    @Order(39)
    public void setTargetSaleRegionIsAmericasForPhysicalAndDigitalAndSubmit_Step22() {
        NDPCreateProductPage createProductPage = (NDPCreateProductPage) currentPage;
        currentPage = createProductPage.selectProductType(TextConstants.full_Product)
                .selectTargetDeliveryFormat(TextConstants.both_Target_Type)
                .selectDigitalAmericasSalesRegion()
                .selectPhysicalAmericasSalesRegion()
                .selectProductName(productName)
                .clickCreateButton();
    }

    @Test()
    @Order(40)
    public void verifyProductIsCreatedSuccessfully_Expected22() {
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        assertTrue(myProductsPage.productCreatedIsDisplayed(productName), "Verify product is displayed on my products page");
    }

    @Test()
    @Order(41)
    public void navigateToReleaseInfoOfCreatedProduct_Step23() {
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        NDPProductDashboardPage productDashboardPage = myProductsPage.clickProductByName(productName);
        CommonAction.issueGameCode(productDashboardPage, 65, TextConstants.no_Upper_Value);
        gameCode = productDashboardPage.clickProductInfo().getGameCode();
        applicationId = productDashboardPage.clickProductInfo().getApplicationID();
        currentPage = productDashboardPage.clickReleases().selectInitialRelease();
    }

    @Test()
    @Order(42)
    public void verifyDisplayReleaseInfoOfCreatedProduct_Expected23() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        assertEquals(productName, releaseInfoPage.getProductName(), "Verify open release info page of created product");
    }

    @Test()
    @Order(43)
    public void navigateToReleaseErrorsTab_Step24() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        currentPage = releaseInfoPage.nav().clickIssues().openReleaseErrorsTab();
    }

    @Test()
    @Order(44)
    public void verifyNoReleaseErrorGenerateForAgreement_Expected24() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        assertTrue(issuePage.isAgreementErrorNotDisplayed(), "Verify no release error is generated for agreement");
    }

    @Test()
    @Order(45)
    public void returnReleaseInfoPageAndAddEuropeAsPhysical_Step25() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        currentPage = issuePage.nav().clickReleaseInfo().selectPhysicalEuropeSalesRegion().save();
    }

    @Test()
    @Order(46)
    public void verifyEuropePhysicalSaleRegionIsSaved_Expected25() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        assertTrue(releaseInfoPage.isEuropePhysicalSaleRegionSelected(), "Verify Europe + Australia is saved as a Physical sales region ");
    }

    @Test()
    @Order(47)
    public void navigateToReleaseErrorsAgain_Step26() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        currentPage = releaseInfoPage.nav().clickIssues().openReleaseErrorsTab();
    }

    @Test()
    @Order(48)
    public void verifyAReleaseErrorIsDisplayedForRetailEuropeAgreement_Expected26() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        assertTrue(issuePage.isSwitchRetailEuropeAgreementErrorDisplayed(), "Verify Switch Retail Europe agreement error is displayed");
    }

    @Test()
    @Order(49)
    public void removeEuropeSaleRegionAndSubmitReleaseToLotcheck_Step27(IJUnitTestReporter testReport) {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        NDPReleaseInfoPage releaseInfoPage = issuePage.nav().clickReleaseInfo();
        releaseInfoPage.unselectPhysicalEuropeSalesRegion();
        CommonAction.selectROMSameAllRegion(releaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);
        NDPReleaseInfoFeaturesPage releaseInfoFeaturesPage = releaseInfoPage.nav().clickFeatures();
        CommonAction.getFeatureOptionData(releaseInfoPage, TextConstants.allOff, TextConstants.allOff, TextConstants.allOff, false, "", false, "");
        releaseInfoFeaturesPage.clickContinue();
        releaseInfoPage.nav().clickGuidelines().selectCompliesWithAllOfTheAboveGuidelines().clickOnSaveButton();
        releaseInfoPage.nav().clickAgeRating().clickOnAddStandardRatingButton()
                .selectStandardAgeRating("ESRB")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectRatingValue("Not required")
                .clickOnSaveButtonInEditStandardRating();
        releaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton();

        createRom(testReport);
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPHomePage homePage = (NDPHomePage) currentPage;
        NDPMyProductsPage myProductsPage = homePage.clickSignInPage().signIn(ndpUserB).clickMyProducts();
        releaseInfoPage = myProductsPage.clickProductByName(productName).clickReleases().selectInitialRelease();
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = releaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, romInitialName);
        releaseInfoPage.nav().clickIssues().openRequiredApprovalTab().performAllIssueAtApprovalTab(false);

        NDPDevelopmentHome developmentHome = releaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licApproverCoordUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        licensingWaiverRequestsTab.waitingForResultTableLoaded()
                .enterSearchingBox(gameCode)
                .clickOnMagnifyingIconButton()
                .waitingForResultTableLoaded();
        int numberOfRequestByPendingStatus = licensingWaiverRequestsTab.collectNumberOfRequestByPendingStatus();
        for (int i = 0; i < numberOfRequestByPendingStatus; i++) {
            developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton().clickOnLicensingWaiverRequestsTab();
            licensingWaiverRequestsTab.waitingForResultTableLoaded()
                    .enterSearchingBox(gameCode)
                    .clickOnMagnifyingIconButton()
                    .waitingForResultTableLoaded();
            currentPage = licensingWaiverRequestsTab.openLicensingWaiverRequestByPendingStatus().selectAssignToMeButton()
                    .selectOKButtonInAssignToMeModal()
                    .selectApproveRequestButton()
                    .selectOKButtonInApproveRequestModal()
                    .waitingForApproveSuccessful();

        }
        NDPViewTaskPage ndpViewTaskPage = (NDPViewTaskPage) currentPage;
        currentPage = releaseInfoPage;
        ndpViewTaskPage.clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(ndpUserB)
                .clickMyProducts().clickProductByGameCode(gameCode).clickReleases().selectInitialRelease().nav().clickSubmitButton()
                .clickSubmit();
        WaitUtils.idle(5000);
    }

    @Test()
    @Order(50)
    public void verifySubmitReleaseSuccessfully_Expected27() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        assertEquals(TextConstants.submitToLotcheck, releaseInfoPage.getStatusValue(), TextConstants.initialSubmitSuccess);
    }

    @Test()
    @Order(51)
    public void navigateToRequiredApprovalsTab_Step28() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        currentPage = releaseInfoPage.nav().clickIssues().openRequiredApprovalTab();
    }

    @Test()
    @Order(52)
    public void verifyNoRequiredApproveAboutGlobalPayeeHasNotBeenSetup_Expected28() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        assertTrue(issuePage.getRequiredApprovalMessage().contains("These issues have already had a special approval requested that has been approved by Nintendo."), "Verify No Required Approval is present stating that a global payee has not been set up");
    }

    @Test()
    @Order(53)
    public void loginNdpByLicPowerUserAndAddCustomAgreementForOrgA_Step29() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        currentPage = issuePage.nav().clickUserMenuDropdownList().clickSignOutButton()
                .clickSignInPage().signIn(licPowerUser).clickInternalTab().clickCustomAgreements()
                .clickSearchIcon()
                .enterOrganizationName(organizationName_A)
                .clickSearch()
                .selectOrg(organizationName_A).clickChooseSelectedPartner()
                .selectPlatform("Nintendo Switch")
                .selectAgreementType("Publisher Agreement (Digital)")
                .selectRegion("China")
                .clickUpload()
                .selectFileInPC("Judgement.pdf")
                .setExecutionDate(0)
                .setExpirationDate(100)
                .selectAgreementApprove()
                .clickSubmit();
    }

    @Test()
    @Order(54)
    public void loginNdpByUserA_Step30() {
        NDPCustomAgreementsPage customAgreementsPage = (NDPCustomAgreementsPage) currentPage;
        currentPage = customAgreementsPage
                .navTopNavigationBar()
                .clickUserMenuDropdownList()
                .clickSignOutButton()
                .clickSignInPage()
                .signIn(ndpUserA);

    }

    @Test()
    @Order(55)
    public void setJapanLanguageAndNavigateToFinancialInformation_Step31() {
        NDPDevelopmentHome developmentHome = (NDPDevelopmentHome) currentPage;
        developmentHome.clickUserMenuDropdownList().selectJapanLanguage();
        currentPage = developmentHome.navTopNavigation().clickAdmin().clickFinanceInformationMenu();
    }

    @Test()
    @Order(56)
    public void addNewPayeeOnNintendoSwitchChinaPayee_Step32() {
        NDPFinanceInformationPage financeInformationPage = (NDPFinanceInformationPage) currentPage;
        financeInformationPage.clickAddNewPayeeNintendoSwitch()
                .selectCountryPayeeBank("China / 中国")
                .enterAccountHolderNameChina("营营营")
                .enterAccountNumber("23243232123423")
                .enterBankNameChina("营")
                .enterBankOfficeNameChina("营")
                .enterContactNameChina("营")
                .enterContactEmailAddressChina(TextConstants.email_Test)
                .chooseAccountOpeningCertificate()
                .selectFileInPC("Judgement.pdf")
                .chooseBusinessLicenseCertificate()
                .selectFileInPC("Judgement.pdf")
                .clickNext();
    }

    @Test()
    @Order(57)
    public void verifyPayeeAddedSuccessfully_Expected32() {
        NDPFinanceInformationPage financeInformationPage = (NDPFinanceInformationPage) currentPage;
        assertAll(() -> assertTrue(financeInformationPage.isPayeeCreated("营营营 - 营 (..3423)"), "Verify payee is displayed"),
                () -> assertEquals("銀行口座情報が正常に更新されました。", financeInformationPage.getSuccessMessage(), "Verify payee is created successfully"));

    }

    @Test()
    @Order(58)
    public void loginNdpByLicAdminNclAgain_Step33() {
        NDPFinanceInformationPage financeInformationPage = (NDPFinanceInformationPage) currentPage;
        NDPHomePage homePage = financeInformationPage.navTopNavigationBar().clickUserMenuDropdownList().clickSignOutButton();
        currentPage = homePage.clickSignInPage().signIn(licAdmin);
    }

    @Test()
    @Order(59)
    public void navigateToPayeeManagementPageAgain_Step34() {
        NDPDevelopmentHome developmentHome = (NDPDevelopmentHome) currentPage;
        currentPage = developmentHome.clickInternalTab().clickFinance().clickPayeeManagement();
    }

    @Test()
    @Order(60)
    public void verifyNewlyCreatePayeeChinaIsNotDisplayed_Expected34() {
        NDPPayeeManagementPage payeeManagementPage = (NDPPayeeManagementPage) currentPage;
        payeeManagementPage.enterPayee("营营营");
        assertTrue(payeeManagementPage.isPayeeNotDisplayed(), "Verify the newly created payee is NOT displayed");
    }

    @Test()
    @Order(61)
    public void navigateToOrganizationManagementAgain_Step35() {
        NDPPayeeManagementPage payeeManagementPage = (NDPPayeeManagementPage) currentPage;
        currentPage = payeeManagementPage.clickOrganizationManagementTab()
                .clickAdvancedSearchLink()
                .enterOrgNameOnAdvanceModal(organizationName_A)
                .selectHomeRegionOnAdvanceModal(LotcheckRegion.NOA.toString())
                .clickIconSearchOnAdvanceModal()
                .clickOnOrg(organizationName_A);
    }

    @Test()
    @Order(62)
    public void selectFinancialInformationTabAgain_Step36() {
        NDPViewOrganizationPage viewOrganizationPage = (NDPViewOrganizationPage) currentPage;
        currentPage = viewOrganizationPage.clickFinancialInformation();
    }

    @Test()
    @Order(63)
    public void verifyPayeeChinaInfo_Expected36() {
        NDPFinancialInformationTab financialInformationTab = (NDPFinancialInformationTab) currentPage;
        assertTrue(financialInformationTab.isPayeeDisplayed("营营营 - 营 (..3423)"));
    }


    private static void createRom(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        ROMLoginPage romLoginPage = (ROMLoginPage) currentPage;
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom.selectRequester(TextConstants.test_User)
                .selectType(TextConstants.initial_Release)
                .selectTargetDeliveryFormat(TextConstants.both_Target_Type)
                .enterProductName(productName)
                .enterApplicationId(applicationId)
                .selectPublishingRelationShip(publishingRelationship)
                .enterDisplayVersion("1.0.0")
                .enterReleaseVersion("00")
                .selectESRBRating("ESRB: Not required")
                .clickOnCreateButton()
                .waitIsLoaded();
        romInitialName = romCreateRom.getRomNameTable(applicationId);
        romCreateRom.clickOnROMFileOnROMTable(romInitialName);
        WaitUtils.idle(5000);
    }

}
