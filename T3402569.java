package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.junit.TestLogger;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.data.DataGen;
import net.nintendo.automation.ui.data.features.FeatureSettingsManager;
import net.nintendo.automation.ui.data.features.enums.DownloadableContentEnum;
import net.nintendo.automation.ui.data.features.enums.LotcheckRemotePresenceEnum;
import net.nintendo.automation.ui.models.admin.AdminControlPanelPage;
import net.nintendo.automation.ui.models.admin.AdminD4CIntegrationPage;
import net.nintendo.automation.ui.models.admin.AdminDevelopmentHome;
import net.nintendo.automation.ui.models.admin.AdminHomePage;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.d4c.D4CRomPublishSuit;
import net.nintendo.automation.ui.models.d4c.D4CRomTab;
import net.nintendo.automation.ui.models.email.EmailPage;
import net.nintendo.automation.ui.models.email.ViewHtmlInbucketLotcheckEmailPage;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthPage;
import net.nintendo.automation.ui.models.lcms.LotcheckEditAccountPreferences;
import net.nintendo.automation.ui.models.lcms.LotcheckHomePage;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.ui.models.lcms.LotcheckQueuePage;
import net.nintendo.automation.ui.models.lcms.LotcheckReorderScheduleGroupDialog;
import net.nintendo.automation.ui.models.lcms.LotcheckTestPlanRow;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckChangeSubmissionJudgment;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportMatrix;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionContents;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssueDetails;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssuePartnerThread;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssues;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPLicensingWaiverRequestsTab;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.NDPViewTaskPage;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardDownloadableContentTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardReleasesTab;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoFeaturesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoGuidelinesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoIssuePage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoROMUpload;
import net.nintendo.automation.ui.models.rom.ROMCreateRom;
import net.nintendo.automation.ui.models.rom.ROMLoginPage;
import net.nintendo.automation.ui.models.testrail.TestRailAddResultDialog;
import net.nintendo.automation.ui.models.testrail.TestRailDashboardPage;
import net.nintendo.automation.ui.models.testrail.TestRailLoginPage;
import net.nintendo.automation.ui.models.testrail.TestRailManagerDashboardPage;
import net.nintendo.automation.ui.models.testrail.TestRailMySettingPage;
import net.nintendo.automation.ui.models.testrail.TestRailMyToDoPage;
import net.nintendo.automation.ui.models.testrail.TestRailTestPlanTable;
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

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("T3402569")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3402569 extends CommonBaseTest {
    private static Browser browser;
    private static User romUser;
    private static User ndpUser;
    private static User lcAdminNcl;
    private static User lcOverseasCoordinatorNcl;
    private static User lcAdminNoa;
    private static User trAdminNcl;
    private static User trAdminNoa;
    private static User licApprovalUser;
    private static User licPowerUser;
    private static CommonUIComponent currentPage;
    private static TestLogger logger;
    private static String productName;
    private static String initialCode;
    private static boolean isTestPlanNotVisible;
    private static String applicationId;
    private static String gameCode;
    private static String scheduleGroupName_01;
    private static String scheduleGroupName_02;
    private static boolean checkScheduleOrder;
    private static String romInitialName;
    private static String romAocName;
    private static String publishingRelationship;
    private static String statusTestPlanNcl;
    private static String statusTestPlanNoa;
    private static String testcaseName;
    private static String testcaseNo;
    private static String emailName;
    private static String testPlanName_NCL;
    private static String testPlanName_NOA;

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_Hiendt);
        licApprovalUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
        licPowerUser = new User();
        licPowerUser.setUserName(TextConstants.lic_pwr_user_NCL);
        licPowerUser.setPassword(TextConstants.nintendo_approval_Password);
        lcAdminNcl = UserManager.get(ServiceType.SLCMS, UserType.LOTCHECK_ADMIN_NCL);
        lcAdminNoa = UserManager.get(ServiceType.SLCMS, UserType.LOTCHECK_ADMIN_NOA);
        lcOverseasCoordinatorNcl = UserManager.get(ServiceType.SLCMS, UserType.UNAUTHENTICATED_USER);

        trAdminNcl = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER);
        trAdminNoa = UserManager.get(ServiceType.PMMS, UserType.LOTCHECK_ADMIN_NOA);

        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);
        publishingRelationship = TextConstants.third_Party_Type;
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
    }

    @BeforeEach
    public void each(IJUnitTestReporter testReport) {
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
    }

    @AfterAll
    public static void terminateBrowser() {
        BrowserManager.closeBrowser(browser);
        UserManager.releaseUser(ServiceType.NDP, ndpUser);
        UserManager.releaseUser(ServiceType.NDP, licApprovalUser);
        UserManager.releaseUser(ServiceType.SLCMS, lcAdminNcl);
        UserManager.releaseUser(ServiceType.SLCMS, lcAdminNoa);
        UserManager.releaseUser(ServiceType.SLCMS, lcOverseasCoordinatorNcl);
        UserManager.releaseUser(ServiceType.PMMS, trAdminNcl);
        UserManager.releaseUser(ServiceType.PMMS, trAdminNoa);
        UserManager.releaseUser(ServiceType.SEARCH, romUser);
    }

    @Test()
    @Order(1)
    public void createNewProduct_Step1() {
        NDPHomePage homePage = (NDPHomePage) currentPage;
        productName = DataGen.getRandomProductName();
        NDPMyProductsPage myProductsPage = homePage.acceptCookie().clickSignInPage().signIn(ndpUser).clickMyProducts();
        myProductsPage.createNewProduct()
                .selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(publishingRelationship)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalAmericasSalesRegion()
                .selectProductName(productName)
                .clickCreateButton();
        currentPage = myProductsPage;
    }

    @Test()
    @Order(2)
    public void verifyProductAppearInTheListUnderMyProduct_Expected1() {
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        assertTrue(myProductsPage.productCreatedIsDisplayed(productName), "Verify created product is displayed on my product page");
    }

    @Test()
    @Order(3)
    public void submitInitialReleaseToLotcheck_Step2(IJUnitTestReporter testReport) {
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        NDPProductDashboardPage productDashboardPage = myProductsPage.clickProductByName(productName);
        CommonAction.issueGameCode(productDashboardPage, 60, "NO");

        applicationId = productDashboardPage.clickProductInfo().getApplicationID();
        gameCode = productDashboardPage.clickProductInfo().getGameCode();
        initialCode = productDashboardPage.clickProductInfo().getInitialCode();

        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = productDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        NDPReleaseInfoPage releaseInfoPage = ndpReleaseInfoPage.nav().clickReleaseInfo();

        CommonAction.selectROMSameAllRegion(releaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);

        NDPReleaseInfoFeaturesPage releaseInfoFeaturesPage = releaseInfoPage.nav().clickFeatures();
        CommonAction.getFeatureOptionData(releaseInfoPage, TextConstants.allOff, TextConstants.allOff, TextConstants.allOff, false, "", false, "");
        releaseInfoFeaturesPage.clickContinue();
        releaseInfoPage.nav().clickGuidelines().selectCompliesWithAllOfTheAboveGuidelines().clickOnSaveButton();
        releaseInfoPage.nav().clickAgeRating().clickOnAddStandardRatingButton()
                .selectStandardAgeRating("ClassInd")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectRatingValue("Not required")
                .clickOnSaveButtonInEditStandardRating();
        releaseInfoPage.nav().clickAgeRating().clickOnAddStandardRatingButton()
                .selectStandardAgeRating("ESRB")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectRatingValue("Not required")
                .clickOnSaveButtonInEditStandardRating();

        createRom(testReport);
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        myProductsPage = (NDPMyProductsPage) currentPage;
        ndpProductDashboardReleasesTab = myProductsPage.clickProductByGameCode(initialCode).clickReleases();
        releaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = releaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, romInitialName);
        releaseInfoPage.nav().clickIssues().openRequiredApprovalTab().performAllIssueAtApprovalTab(false);

        NDPDevelopmentHome developmentHome = ndpReleaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licApprovalUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, gameCode);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser).clickMyProducts()
                .clickProductByGameCode(gameCode).clickReleases().selectInitialRelease().nav().clickSubmitButton().enterDeveloperComment("Test comment\n" +
                        "Developer Comments")
                .clickSubmit();
        WaitUtils.idle(3000);
    }

    @Test()
    @Order(4)
    public void verifySubmitSuccessfully_Expected2(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        String status = releaseInfoPage.getStatusValue();

        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        TestRailDashboardPage testRailDashboardPage = testRailLoginPage.signInTestRail(trAdminNcl);
        WaitUtils.idle(3000);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(initialCode)
                .selectStatusUntested()
                .selectStatusPassed();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        boolean isTestPlanDisplayed = testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar().isTestPlanDisplayed(gameCode);
        testRailManagerDashboardPage.logOut();
        assertAll(() -> assertEquals(TextConstants.submitToLotcheck, status, TextConstants.initialSubmitSuccess),
                () -> assertTrue(isTestPlanDisplayed, "Verify test plan appears in testrail"));
    }

    @Test()
    @Order(5)
    public void loginSlcmsByLcAdminAndNavigateToTesterAssignmentQueue_Step3(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(lcAdminNcl);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickTesterAssignment(LotcheckRegion.NCL);
        lotcheckQueuePage.searchByInitialCode(initialCode);
        currentPage = lotcheckQueuePage.clickPlannedTestDateNotSet();
    }

    @Test()
    @Order(6)
    public void verifySubmissionAppearInTesterAssignmentQueue_Expected3() {
        LotcheckTestPlanRow lotcheckTestPlanRow = (LotcheckTestPlanRow) currentPage;
        assertTrue(lotcheckTestPlanRow.isTestPlanDisplayed(gameCode), "Verify test plans for the submission appear in the Tester Assignment Queue.");
    }

    @Test()
    @Order(7)
    public void moveTestPlansThroughInTestingQueue_Step4(IJUnitTestReporter testReport) {
        LotcheckTestPlanRow lotcheckTestPlanRow = (LotcheckTestPlanRow) currentPage;
        lotcheckTestPlanRow.performAction("Assignment Complete");
        LotcheckLandingPage lotcheckLandingPage = lotcheckTestPlanRow.navLotcheckLandingPage();

        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToTestSetupReadyQueue(LotcheckRegion.NCL.toString());
        lotcheckQueuePage.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        lotcheckTestPlanRow.performAction("Start Test Setup (PROD)");

        lotcheckLandingPage.navigationToInTestSetupQueue(LotcheckRegion.NCL.toString());
        lotcheckQueuePage.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        lotcheckTestPlanRow.performAction("Setup Complete (PROD)");

        lotcheckLandingPage.navigationToInTestingQueue(LotcheckRegion.NCL.toString());
        lotcheckQueuePage.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        statusTestPlanNcl = lotcheckQueuePage.getTestPlanStatus(10);
        lotcheckQueuePage.refreshPageSLCMS().logOut();

        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        lotcheckQueuePage = hyruleAuthPage.loginWithOvdLink(lcAdminNoa).clickTesterAssignment(LotcheckRegion.NOA);
        lotcheckTestPlanRow = lotcheckQueuePage.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        lotcheckTestPlanRow.performAction("Assignment Complete");
        lotcheckLandingPage = lotcheckTestPlanRow.navLotcheckLandingPage();

        lotcheckQueuePage = lotcheckLandingPage.navigationToTestSetupReadyQueue(LotcheckRegion.NOA.toString());
        lotcheckQueuePage.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        lotcheckTestPlanRow.performAction("Start Test Setup (PROD)");

        lotcheckLandingPage.navigationToInTestSetupQueue(LotcheckRegion.NOA.toString());
        lotcheckQueuePage.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        lotcheckTestPlanRow.performAction("Setup Complete (PROD)");

        lotcheckLandingPage.navigationToInTestingQueue(LotcheckRegion.NOA.toString());
        lotcheckQueuePage.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        statusTestPlanNoa = lotcheckQueuePage.getTestPlanStatus(10);

    }

    @Test()
    @Order(8)
    public void verifyStatusAllTestPlan_Expected4() {
        assertAll(() -> assertEquals("In Testing", statusTestPlanNcl, "Verify status NCL test plan is In Testing"),
                () -> assertEquals("In Testing", statusTestPlanNoa, "Verify status NOA test plan is In Testing"));
    }

    @Test()
    @Order(9)
    public void loginTrAdminNoaAndLocaleToTestPlanOnManageDashboard_Step5(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        TestRailDashboardPage testRailDashboardPage = testRailLoginPage.signInTestRail(trAdminNoa);
        WaitUtils.idle(3000);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(initialCode)
                .selectStatusUntested();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar();
        currentPage = testRailManagerDashboardPage;
    }


    @Test()
    @Order(10)
    public void verifyTestPlanIsDisplayedOnManageDashboard_Expected5() {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        assertTrue(testRailManagerDashboardPage.isTestPlanDisplayed(gameCode), "Verify test plan is displayed");
    }

    @Test()
    @Order(11)
    public void assignATestcaseToCurrentUser_Step6() {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        testRailManagerDashboardPage.clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun()
                .selectTestcaseCheckbox(1)
                .clickAssignTo()
                .selectSetValueAssignToRadio()
                .selectUser("Me")
                .clickOk()
                .waitingLoadingTestSuite();
        testcaseName = testRailManagerDashboardPage.getTestcaseName(1);
        testcaseNo = testcaseName.split(":")[0].trim();
    }

    @Test()
    @Order(12)
    public void verifyAssignSuccessfullyAndTestcaseDisplayOnMyToDo_Expected6() {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        String assignToUser = testRailManagerDashboardPage.getAssignToUser(testcaseName);
        TestRailMyToDoPage myToDoPage = testRailManagerDashboardPage.topNav()
                .navigateToMyToDo();
        myToDoPage.actionFill()
                .enterTestPlanNameFilter(gameCode);
        myToDoPage.actionFill().clickOnApplyFiltersButton();
        TestRailTestPlanTable testPlanTable = myToDoPage.navTestPlanTable()
                .clickOnPlannedTestDateNotSetBar();
        currentPage = testPlanTable;
        assertAll(() -> assertEquals("TRAdmin English (NOA)", assignToUser, "Verify assign successfully"),
                () -> assertTrue(testPlanTable.isTestPlanDisplayed(gameCode), "Verify test plan is displayed on My todo page"));
    }

    @Test()
    @Order(13)
    public void openTestcaseAssigned_Step7() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        currentPage = testPlanTable.clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun()
                .openTestcaseDetail(testcaseName)
                .clickAddTestResult();
    }

    @Test()
    @Order(14)
    public void verifyAddResultWindowDisplay_Expected7() {
        TestRailAddResultDialog addResultDialog = (TestRailAddResultDialog) currentPage;
        assertEquals("Add Result", addResultDialog.getModalTitle(), "Verify The Add Result window is displayed.");
    }

    @Test()
    @Order(15)
    public void addResult_Step8() {
        TestRailAddResultDialog addResultDialog = (TestRailAddResultDialog) currentPage;
        currentPage = addResultDialog.selectStatus("Not Performed")
                .selectSubStatus(" NCL")
                .clickAddResult()
                .waitingLoadingTestSuite();
    }

    @Test()
    @Order(16)
    public void verifyAddResultSuccessfully_Expected8() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        String testcaseStatus = testPlanTable.getStatusValue(testcaseName);
        assertEquals("Not Performed", testcaseStatus, "Verify status of testcase");
    }


    @Test()
    @Order(17)
    public void goToInbucket_Step9(IJUnitTestReporter testReport) {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        testPlanTable.logOut().signInTestRail(trAdminNcl).navTopTestrail().navigateToManagerDashboard().checkErrorPopup();

        LotcheckLandingPage landingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        landingPage.clickOnUserNameMenu().clickOnSignoutButton();
        LotcheckHomePage lotcheckHomePage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReport);
        lotcheckHomePage.continueToLogin().loginWithOvdLink(lcOverseasCoordinatorNcl);

        currentPage = browser.openURL(EmailPage.class, PageState.PRE_LOGIN, testReport);

    }

    @Test()
    @Order(18)
    public void verifyMailSendToCoordinatorUser_Expected9() {
        EmailPage emailPage = (EmailPage) currentPage;
        emailPage.signInEmail("coordinator_noa").waitListMailDisplay();
        emailName = "[NDP][NOA][TestRail][Not_Performed] " + gameCode + " 00.00 (0) [DL] " + testcaseName;
        boolean isEmailSentToNoa = emailPage.confirmEmailDisplay(emailName);
        emailPage.signInEmail("coordinator_noe");
        boolean isEmailSentToNoe = emailPage.confirmEmailDisplay(emailName);
        emailPage.signInEmail("coordinator_ncl");
        boolean isEmailSentToNcl = emailPage.confirmEmailDisplay(emailName);
        assertAll(() -> assertTrue(isEmailSentToNcl, "Verify email sent to ncl user"),
                () -> assertTrue(isEmailSentToNoa, "Verify email sent to noa user"),
                () -> assertTrue(isEmailSentToNoe, "Verify email sent to noe user"));
    }

    @Test()
    @Order(19)
    public void clickTestcaseLink_Step10() {
        EmailPage emailPage = (EmailPage) currentPage;
        ViewHtmlInbucketLotcheckEmailPage viewHtmlInbucketLotcheckEmailPage = emailPage.clickEmailByName(emailName).selectHTMLLotcheckMail();
        currentPage = viewHtmlInbucketLotcheckEmailPage.clickNclTestCaseMyToDo();

    }

    @Test()
    @Order(20)
    public void verifyDirectToCorrectNclTestPlan_Expected10() {
        TestRailMyToDoPage myToDoPage = (TestRailMyToDoPage) currentPage;
        boolean isNclTestcaseDisplayedMyToDo = myToDoPage.testCaseDetailIsDisplayed(testcaseNo);

        ViewHtmlInbucketLotcheckEmailPage viewHtmlInbucketLotcheckEmailPage = myToDoPage.backToPreviousPage();
        TestRailManagerDashboardPage managerDashboardPage = viewHtmlInbucketLotcheckEmailPage.clickNclTestCaseManageDashboard();
        boolean isNclTestcaseDisplayedManageDashboard = managerDashboardPage.testCaseDetailIsDisplayed(testcaseNo);

        viewHtmlInbucketLotcheckEmailPage = managerDashboardPage.backToPreviousPage();
        LotcheckReportSubmissionOverview overview = viewHtmlInbucketLotcheckEmailPage.clickSubmissionLevel();
        String gameCode_actual = overview.getGameCodeOfSubmission();

        viewHtmlInbucketLotcheckEmailPage = overview.backToPreviousPage();
        myToDoPage = viewHtmlInbucketLotcheckEmailPage.clickOriginalTestcase();
        String errorMessage_Actual = myToDoPage.getErrorMessage();
        String errorMessage_Expected = "Unable to display Plan. The Plan ID either doesn't exist or it is being excluded from the view by the applied filters.";
        assertAll(() -> assertEquals(gameCode, gameCode_actual, "Verify directed to correct submission"),
                () -> assertTrue(isNclTestcaseDisplayedManageDashboard, "Verify directed to testcase ncl in manage dashboard"),
                () -> assertTrue(isNclTestcaseDisplayedMyToDo, "Verify directed to testcase ncl in my todo"),
                () -> assertEquals(errorMessage_Expected, errorMessage_Actual, "Verify directed to original testcase"));
    }

    @Test()
    @Order(21)
    public void loginTestRailByTrAdminAndSubmitBugForAnyTestcase_Step11(IJUnitTestReporter testReport) {
        TestRailDashboardPage dashboardPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport);
        TestRailManagerDashboardPage managerDashboardPage = dashboardPage.navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
        managerDashboardPage.actionFill().enterTestPlanNameFilter(initialCode)
                .selectStatusUntested();
        managerDashboardPage.actionFill().clickOnApplyFiltersButton();
        managerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun();
        testcaseName = managerDashboardPage.getTestcaseName(1);
        currentPage = managerDashboardPage.openTestcaseDetail(1)
                .clickSubmitBug()
                .enterDescription("Test submit bug")
                .clickSubmit()
                .clickOk()
                .clickClose();
    }

    @Test()
    @Order(22)
    public void verifySubmitBugSuccessfully_Expected11() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        assertTrue(testPlanTable.isTestCaseSubmittedBug(testcaseName), "Verify submit bug successfully");

    }

    @Test()
    @Order(23)
    public void loginSlcmsByLcOverseasCoordinatorNclThenPublishBug_Step12(IJUnitTestReporter testReport) {
        LotcheckLandingPage landingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        currentPage = landingPage.searchProduct(initialCode).selectSubmission(gameCode, "00", "00").navBar().clickLotcheckIssues().clickMenuOfIssue(testcaseName)
                .selectViewIssueDetailsOfIssue(testcaseName).performPublishIssue("Bug");
    }

    @Test()
    @Order(24)
    public void verifyPublishBugSuccessfully_Expected12() {
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails = (LotcheckReportSubmissionLotcheckIssueDetails) currentPage;
        String partnerThreadStatus = submissionLotcheckIssueDetails.getPartnerThreadStatus();
        assertEquals("NEEDS RESPONSE", partnerThreadStatus, "Verify partner Thread Status after publish");
    }

    @Test()
    @Order(25)
    public void loginNdpByPartnerAndSelectWillFixInRomForIssue_Step13(IJUnitTestReporter testReport) {
        NDPMyProductsPage myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        currentPage = myProductsPage.clickProductByGameCode(gameCode).clickReleases().selectInitialRelease().nav().clickIssues()
                .expandUnresolve().expandIssue().selectResolution(" Will Fix in ROM ").clickSendResolutionButton();
    }

    @Test()
    @Order(26)
    public void verifySubmitRespondSuccessfully_Expected13() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        issuePage.expandResolve();
        assertEquals("Will Fix in ROM", issuePage.getStatusResolvedOfLotcheckIssue(0), "Verify submit respond successfully");
    }

    @Test()
    @Order(27)
    public void openTestRailByTrAdminAndJudgmentNgForSubmission_Step14(IJUnitTestReporter testReport) {
        TestRailDashboardPage dashboardPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport);
        TestRailManagerDashboardPage managerDashboardPage = dashboardPage.navTopTestrail().navigateToManagerDashboard();
        CommonAction.setStatusOnTestrailIncludeFailed(managerDashboardPage, initialCode, TextConstants.failedStatus, TextConstants.percentCompleted, TextConstants.NG);
        isTestPlanNotVisible = managerDashboardPage.testPlanIsNotVisible(gameCode);

        dashboardPage.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton().signInTestRail(trAdminNoa)
                .navTopTestrail().navigateToManagerDashboard();
        CommonAction.setStatusOnTestrailIncludeFailed(managerDashboardPage, initialCode, TextConstants.failedStatus, TextConstants.percentCompleted, TextConstants.NG);
        currentPage = managerDashboardPage;
    }

    @Test()
    @Order(28)
    public void verifyJudgmentNgSuccessfully_Expected14() {
        TestRailManagerDashboardPage managerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        assertAll(() -> assertTrue(isTestPlanNotVisible, "Verify judgment NCL test plan successfully"),
                () -> assertTrue(managerDashboardPage.testPlanIsNotVisible(gameCode), "Verify judgment NOA test plan successfully"));
    }

    @Test()
    @Order(29)
    public void loginSlcmsByLcOverseasCoordinatorNclAndApproveTestPlanJudgment_Step15(IJUnitTestReporter testReport) {
        LotcheckLandingPage landingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckQueuePage lotcheckQueuePage = landingPage.clickJudgment(LotcheckRegion.NCL);
        CommonAction.approvalJudgmentStatus(lotcheckQueuePage, initialCode, 200, TextConstants.NG, true, "1");

        LotcheckHomePage lotcheckHomePage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReport);
        lotcheckHomePage.continueToLogin().loginWithOvdLink(lcAdminNoa).clickJudgment(LotcheckRegion.NOA);
        CommonAction.approvalJudgmentStatus(lotcheckQueuePage, initialCode, 200, TextConstants.NG, true, "1");
    }

    @Test()
    @Order(30)
    public void verifySubmissionJudgmentNg_Expected15(IJUnitTestReporter testReport) {
        LotcheckHomePage lotcheckHomePage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReport);
        LotcheckReportSubmissionOverview submissionOverview = lotcheckHomePage.continueToLogin().loginWithOvdLink(lcOverseasCoordinatorNcl).searchProduct(initialCode).selectSubmission(gameCode, "00", "00");
        HashMap<LotcheckReportSubmissionOverview.SubmissionOverviewAttribute, String> submissionOverviewAttribute = submissionOverview.getOverviewAttributes();
        currentPage = submissionOverview;
        String internalStatus = submissionOverviewAttribute.get(LotcheckReportSubmissionOverview.SubmissionOverviewAttribute.InternalStatus);
        assertEquals("NG", internalStatus, "Verify submission status is NG");
    }

    @Test()
    @Order(31)
    public void moveToSubmissionContents_Step16() {
        LotcheckReportSubmissionOverview submissionOverview = (LotcheckReportSubmissionOverview) currentPage;
        currentPage = submissionOverview.navBar().clickContents();
    }

    @Test()
    @Order(32)
    public void verifyLcrSubmissionContentsPageDisplayed_Expected16() {
        LotcheckReportSubmissionContents submissionContents = (LotcheckReportSubmissionContents) currentPage;
        LotcheckEditAccountPreferences editAccountPreferences = submissionContents.navBar().clickMyAccount();
        if (editAccountPreferences.isCurrentLanguageEnglish()) {
            editAccountPreferences.clickCancelButton();
        } else {
            editAccountPreferences.selectLanguage(TextConstants.english)
                    .clickSaveButton();
            submissionContents.waitPageLoaded();
        }
        assertEquals("Contents Submission: LCR - LCMS", submissionContents.getPageTitle(), "Verify lcr content page displayed");
    }

    @Test()
    @Order(33)
    public void clickChangeJudgment_Step17() {
        LotcheckReportSubmissionContents submissionContents = (LotcheckReportSubmissionContents) currentPage;
        LotcheckReportMatrix reportMatrix = submissionContents.navReportMatrix();
        currentPage = reportMatrix.clickChangeJudgment();
    }

    @Test()
    @Order(34)
    public void verifyChangeJudgmentModalIsDisplayed_Expected17() {
        LotcheckChangeSubmissionJudgment changeSubmissionJudgment = (LotcheckChangeSubmissionJudgment) currentPage;
        assertEquals("Change Submission Judgment", changeSubmissionJudgment.getModalTitle(), "Verify change judgment modal displayed");
    }

    @Test()
    @Order(35)
    public void selectNoJudgment_Step18() {
        LotcheckChangeSubmissionJudgment changeSubmissionJudgment = (LotcheckChangeSubmissionJudgment) currentPage;
        changeSubmissionJudgment.selectNewJudgment("No Judgment");
    }

    @Test()
    @Order(36)
    public void verifyNoJudgmentIsSelected_Expected18() {
        LotcheckChangeSubmissionJudgment changeSubmissionJudgment = (LotcheckChangeSubmissionJudgment) currentPage;
        assertEquals("No Judgment", changeSubmissionJudgment.getSelectedNewJudgment(), "Verify No Judgement is selected");
    }

    @Test()
    @Order(37)
    public void clickConfirm_Step19() {
        LotcheckChangeSubmissionJudgment changeSubmissionJudgment = (LotcheckChangeSubmissionJudgment) currentPage;
        currentPage = changeSubmissionJudgment.clickConfirmButton();
    }

    @Test()
    @Order(38)
    public void verifyWarningMessage_Expected19() {
        LotcheckReportMatrix reportMatrix = (LotcheckReportMatrix) currentPage;
        String message_EN = reportMatrix.getErrorMessage();
        reportMatrix.clickCloseMessage();

        reportMatrix.navBar().clickMyAccount().selectLanguage(TextConstants.japanese).clickSaveButton();
        reportMatrix.waitPageLoaded();

        reportMatrix.clickChangeJudgment().selectNewJudgment("No Judgment").clickConfirmButton();
        String message_JP = reportMatrix.getErrorMessage();
        reportMatrix.clickCloseMessage();

        reportMatrix.navBar().clickMyAccount().selectLanguage(TextConstants.english).clickSaveButton();
        reportMatrix.waitPageLoaded();
        String message_EN_Expected = "Problems updating judgment status: Issues under Submission are not fixed (SLCMS0027)";
        String message_JP_Expected = "Problems updating judgment status: Issues under Submission are not fixed (SLCMS0027)";
        assertAll(() -> assertEquals(message_EN_Expected, message_EN, "Verify error message (EN)"),
                () -> assertEquals(message_JP_Expected, message_JP, "Verify error message (JP)"));
    }

    @Test()
    @Order(39)
    public void loginNdpByPartnerAndSubmitTemporaryWaiverRequest_Step20(IJUnitTestReporter testReport) {
        NDPMyProductsPage myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        currentPage = myProductsPage.clickProductByGameCode(gameCode).clickReleases().selectInitialRelease().nav().clickIssues().clickOnLotcheckIssuesTab().expandResolve().expandIssue().selectDesiredApprovalTypeAtTemporaryApprovalRequiredApproval()
                .enterReasonForRequest("Test Temporary Waiver Request")
                .clickSendResolutionButton();
    }

    @Test()
    @Order(40)
    public void verifySubmitTemporaryWaiverRequestSuccessfully_Expected20() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        issuePage.expandUnresolve();
        assertEquals("Temporary Waiver Requested", issuePage.getStatusOfIssue(), "Verify submit Temporary waiver request successfully");
    }

    @Test()
    @Order(41)
    public void loginNdpByLicApprovalCoordinatorThenApproveTemporaryWaiverRequest_Step21() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        NDPDevelopmentHome developmentHome = issuePage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licApprovalUser);
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
    }

    @Test()
    @Order(42)
    public void verifyTemporaryWaiverRequestIsApproved_Expected21() {
        NDPViewTaskPage ndpViewTaskPage = (NDPViewTaskPage) currentPage;
        NDPReleaseInfoIssuePage issuePage = ndpViewTaskPage.clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(ndpUser)
                .clickMyProducts().clickProductByGameCode(gameCode).clickReleases().selectInitialRelease().nav().clickIssues()
                .clickOnLotcheckIssuesTab().expandResolve();
        currentPage = issuePage;
        assertEquals("Temporary Waiver Approved", issuePage.getStatusOfIssue(), "Verify status is: Temporary Waiver Approved");
    }

    @Test()
    @Order(43)
    public void navigateToIssueTabAndPostComment_Step22() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        issuePage.expandIssue().clickViewThread().enterDescription("Test post comment").clickPost();

    }

    @Test()
    @Order(44)
    public void loginSlcmsByLcCoordinatorAndClickChangeJudgment_Step23(IJUnitTestReporter testReport) {
        LotcheckLandingPage landingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        currentPage = landingPage.searchProduct(initialCode).selectSubmission(gameCode, "00", "00").matrix().clickChangeJudgment();
    }

    @Test()
    @Order(45)
    public void verifyChangeSubmissionJudgmentIsDisplayed_Expected23() {
        LotcheckChangeSubmissionJudgment changeSubmissionJudgment = (LotcheckChangeSubmissionJudgment) currentPage;
        assertEquals("Change Submission Judgment", changeSubmissionJudgment.getModalTitle(), "Verify change judgment modal displayed");
    }

    @Test()
    @Order(46)
    public void selectNoJudgment_Step24() {
        LotcheckChangeSubmissionJudgment changeSubmissionJudgment = (LotcheckChangeSubmissionJudgment) currentPage;
        changeSubmissionJudgment.selectNewJudgment("No Judgment");
    }

    @Test()
    @Order(47)
    public void verifyNoJudgmentIsSelected_Expected24() {
        LotcheckChangeSubmissionJudgment changeSubmissionJudgment = (LotcheckChangeSubmissionJudgment) currentPage;
        assertEquals("No Judgment", changeSubmissionJudgment.getSelectedNewJudgment(), "Verify No Judgement is selected");
    }

    @Test()
    @Order(48)
    public void clickOnConfirmButton_Step25() {
        LotcheckChangeSubmissionJudgment changeSubmissionJudgment = (LotcheckChangeSubmissionJudgment) currentPage;
        currentPage = changeSubmissionJudgment.clickConfirmButton();
    }

    @Test()
    @Order(49)
    public void verifyChangeSubmissionJudgmentSuccessfully_Expected25() {
        LotcheckReportMatrix reportMatrix = (LotcheckReportMatrix) currentPage;
        boolean isErrorMessageNotDisplayed = reportMatrix.isErrorMessageNotDisplayed();
        LotcheckReportSubmissionOverview submissionOverview = reportMatrix.navLcrOverview();
        HashMap<LotcheckReportSubmissionOverview.SubmissionOverviewAttribute, String> submissionOverviewAttribute = submissionOverview.getOverviewAttributes();
        String internalStatus = submissionOverviewAttribute.get(LotcheckReportSubmissionOverview.SubmissionOverviewAttribute.InternalStatus);

        LotcheckReportSubmissionLotcheckIssues lotcheckIssues = submissionOverview.navBar().clickLotcheckIssues();
        String statusOfIssue = lotcheckIssues.getStatusOfIssueByName(testcaseName);

        LotcheckReportSubmissionLotcheckIssuePartnerThread partnerThread = lotcheckIssues.clickMenuOfIssue(testcaseName).selectViewIssueDetails().clickPartnerThreadTab();
        boolean isCommentDisplayed = partnerThread.isCommentDisplayed("6", "Test post comment");
        assertAll(() -> assertEquals("TESTING", internalStatus, "Verify submission status is TESTING"),
                () -> assertTrue(isErrorMessageNotDisplayed, "Verify Warning message is NOT displayed"),
                () -> assertEquals("TEMPORARY WAIVER APPROVED", statusOfIssue, "Verify issue from the subsequent Submission copy to the current Submission"),
                () -> assertTrue(isCommentDisplayed, "Verify comment from the subsequent Submission copy to the current Submission"));
    }

    @Test()
    @Order(50)
    public void backToTestRailAndJudgmentOkForSubmission_Step26(IJUnitTestReporter testReport) {
        TestRailDashboardPage dashboardPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport);
        TestRailManagerDashboardPage managerDashboardPage = dashboardPage.navTopTestrail().navigateToManagerDashboard();
        CommonAction.setStatusOnTestrailIncludeFailed(managerDashboardPage, initialCode, TextConstants.passedStatus, TextConstants.percentCompleted, TextConstants.OK);
        isTestPlanNotVisible = managerDashboardPage.testPlanIsNotVisible(gameCode);

        dashboardPage.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton().signInTestRail(trAdminNcl)
                .navTopTestrail().navigateToManagerDashboard();
        CommonAction.setStatusOnTestrailIncludeFailed(managerDashboardPage, initialCode, TextConstants.passedStatus, TextConstants.percentCompleted, TextConstants.OK);
        currentPage = managerDashboardPage;
    }

    @Test()
    @Order(51)
    public void verifyJudgmentOkSuccessfully_Expected26() {
        TestRailManagerDashboardPage managerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        assertAll(() -> assertTrue(isTestPlanNotVisible, "Verify judgment NCL test plan successfully"),
                () -> assertTrue(managerDashboardPage.testPlanIsNotVisible(gameCode), "Verify judgment NOA test plan successfully"));
    }

    @Test()
    @Order(52)
    public void loginLcAdminAndApproveOkForSubmission_Step27(IJUnitTestReporter testReport) {
        LotcheckLandingPage landingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        landingPage.clickOnUserNameMenu().clickOnSignoutButton();
        LotcheckHomePage lotcheckHomePage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReport);
        LotcheckQueuePage lotcheckQueuePage = lotcheckHomePage.continueToLogin().loginWithOvdLink(lcAdminNcl).clickJudgment(LotcheckRegion.NCL);
        CommonAction.approvalJudgmentStatus(lotcheckQueuePage, initialCode, 100, TextConstants.OK, true, "1");

        lotcheckHomePage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReport);
        lotcheckHomePage.continueToLogin().loginWithOvdLink(lcAdminNoa).clickJudgment(LotcheckRegion.NOA);
        CommonAction.approvalJudgmentStatus(lotcheckQueuePage, initialCode, 100, TextConstants.OK, false, "1");
        currentPage = lotcheckQueuePage.refreshPageSLCMS();
    }

    @Test()
    @Order(53)
    public void verifySubmissionJudgmentOk_Expected27(IJUnitTestReporter testReport) {
        LotcheckLandingPage landingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckReportSubmissionOverview submissionOverview = landingPage.searchProduct(initialCode).selectSubmission(gameCode, "00", "00");
        HashMap<LotcheckReportSubmissionOverview.SubmissionOverviewAttribute, String> submissionOverviewAttribute = submissionOverview.getOverviewAttributes();
        String internalStatus = submissionOverviewAttribute.get(LotcheckReportSubmissionOverview.SubmissionOverviewAttribute.InternalStatus);
        assertEquals("OK", internalStatus, "Verify submission status is OK");
    }

    @Test()
    @Order(54)
    public void loginNdpAndCreateAddPhysicalRelease_Step28(IJUnitTestReporter testReport) {
        NDPMyProductsPage myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        currentPage = myProductsPage.clickProductByGameCode(gameCode).clickReleases().clickCreateNewReleaseButton()
                .selectReleaseType(TextConstants.add_Physical_Update)
                .selectExpectedSubmissionDate(5)
                .selectExpectedReleaseDate(90)
                .clickCreateButton();
    }

    @Test()
    @Order(55)
    public void completeAllSectionAndSubmitToLotcheck_Step29(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentPage;
        CommonAction.selectROMSameAllRegion(ndpReleaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);

        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = ndpReleaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoGuidelinesPage.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();
        currentPage = browser.openURL(D4CRomPublishSuit.class, PageState.PRE_LOGIN);
        D4CRomPublishSuit d4cRom = (D4CRomPublishSuit) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        String idApplication = applicationId.substring(2);
        D4CRomTab d4cRomTab = d4cRom.clickTabRom().enterApplicationID(idApplication).clickSearchButton();
        String romIdAddPhysical = d4cRomTab.getRomId();
        WaitUtils.idle(4000);
        currentPage = browser.openURL(AdminHomePage.class, PageState.PRE_LOGIN);
        AdminHomePage adminHomePage = (AdminHomePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        AdminDevelopmentHome adminDevelopmentHome = adminHomePage.acceptCookie().clickSignInPage().signIn(TextConstants.omniAdmin, TextConstants.omniAdmin_Password);
        AdminControlPanelPage adminControlPanelPage = adminDevelopmentHome.clickMenuAdmin().clickControlPanel();
        AdminD4CIntegrationPage adminD4CIntegrationPage = adminControlPanelPage.clickD4CIntegrationAdminButton();
        adminD4CIntegrationPage.submitROMCheckResults(romIdAddPhysical);

        NDPMyProductsPage myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        myProductsPage.clickProductByGameCode(gameCode).clickReleases().selectRelease(TextConstants.add_Physical_Update_Release, "1.0.0");

        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
        issuePage.openRequiredApprovalTab();
        issuePage.performAllIssueAtApprovalTab(false);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();


        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(licApprovalUser).clickOnMyTaskMenu();
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, gameCode);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser).clickMyProducts();
        myProductsPage.clickProductByGameCode(gameCode).clickReleases().selectRelease(TextConstants.add_Physical_Update_Release, "1.0.0");
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.commentSubmission).clickSubmit();
        WaitUtils.idle(3000);
    }

    @Test()
    @Order(56)
    public void openTestRailAndCheckDisplayOfTestPlan_Step30(IJUnitTestReporter testReport) {
        TestRailDashboardPage dashboardPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport);
        TestRailManagerDashboardPage managerDashboardPage = dashboardPage.navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
        managerDashboardPage.actionFill().enterTestPlanNameFilter(initialCode)
                .selectStatusUntested();
        managerDashboardPage.actionFill().clickOnApplyFiltersButton();
        managerDashboardPage.clickOnPlannedTestDateNotSetBar();
        currentPage = managerDashboardPage;
    }

    @Test()
    @Order(57)
    public void verifyDisplayOfTestPlan_Expected30(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage managerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        testPlanName_NCL = managerDashboardPage.getTestPlanName();
        managerDashboardPage.actionFill().selectTestPlanRegionFilter(LotcheckRegion.NOA.toString())
                .selectTestPlanRegionFilter(LotcheckRegion.NCL.toString());
        managerDashboardPage.actionFill().clickOnApplyFiltersButton();
        managerDashboardPage.clickOnPlannedTestDateNotSetBar();
        testPlanName_NOA = managerDashboardPage.getTestPlanName();

        TestRailMySettingPage mySettingPage = managerDashboardPage.clickManageAccount().clickMySetting();
        if (!mySettingPage.getSelectedTimeZone().equals("(UTC+07:00) Jakarta")) {
            mySettingPage.selectTimeZone("(UTC+07:00) Jakarta")
                    .saveSetting();
        }
        //Check due date
        mySettingPage.navTopTestrail().navigateToDashboard().navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
        managerDashboardPage.actionFill().enterTestPlanNameFilter(initialCode)
                .selectStatusUntested();
        managerDashboardPage.actionFill().clickOnApplyFiltersButton();
        managerDashboardPage.clickOnPlannedTestDateNotSetBar();
        String testDueDate_NCL = managerDashboardPage.getTestDueDate();
        String testDueDate_NCL_Compare = testDueDate_NCL.substring(0, 16) + testDueDate_NCL.substring(19, 23);

        managerDashboardPage.actionFill().selectTestPlanRegionFilter(LotcheckRegion.NOA.toString())
                .selectTestPlanRegionFilter(LotcheckRegion.NCL.toString());
        managerDashboardPage.actionFill().clickOnApplyFiltersButton();
        managerDashboardPage.clickOnPlannedTestDateNotSetBar();
        String testDueDate_NOA = managerDashboardPage.getTestDueDate();
        String testDueDate_NOA_Compare = testDueDate_NOA.substring(0, 16) + testDueDate_NOA.substring(19, 23);

        LotcheckLandingPage landingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckReportSubmissionOverview submissionOverview = landingPage.searchProduct(initialCode).selectSubmission(gameCode, "00", "100");
        LotcheckEditAccountPreferences editAccountPreferences = submissionOverview.navBar().clickMyAccount();
        if (editAccountPreferences.isCurrentTimeZone("(GMT+07:00) Asia/Jakarta")) {
            editAccountPreferences.clickCancelButton();
        } else {
            editAccountPreferences.selectTimeZone("(GMT+07:00) Asia/Jakarta")
                    .clickSaveButton();
            submissionOverview.waitPageLoaded();
        }
        HashMap<LotcheckReportSubmissionOverview.SubmissionOverviewAttribute, String> submissionOverviewAttribute = submissionOverview.getOverviewAttributes();
        currentPage = submissionOverview;
        String testDueDate_Sclms = submissionOverviewAttribute.get(LotcheckReportSubmissionOverview.SubmissionOverviewAttribute.TestDueDate);

        assertAll(() -> assertFalse(testPlanName_NCL.contains("[NCL] @LCO"), "Verify Lotcheck Owner is not NCL"),
                () -> assertTrue(testPlanName_NOA.contains("[NOA] @LCO"), "Verify Lotcheck Owner is NOA and display after region data"),
                () -> assertEquals(testDueDate_Sclms, testDueDate_NCL_Compare, "Verify Due Date NCL shows the value default set automatically at SLCMS"),
                () -> assertEquals(testDueDate_Sclms, testDueDate_NOA_Compare, "Verify Due Date NOA shows the value default set automatically at SLCMS"));
    }

    @Test()
    @Order(58)
    public void asSlcmsChangeLcoToNcl_Step31() {
        LotcheckReportSubmissionOverview submissionOverview = (LotcheckReportSubmissionOverview) currentPage;
        submissionOverview.clickMenu().selectChangeLCO()
                .selectNewLCO(LotcheckRegion.NCL.toString()).clickSave();
        submissionOverview.navBar().logOut();
    }

    @Test()
    @Order(59)
    public void asTestRailCheckDisplayOfTestPlan_Step32(IJUnitTestReporter testReport) {
        TestRailDashboardPage dashboardPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport);
        TestRailManagerDashboardPage managerDashboardPage = dashboardPage.navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
        managerDashboardPage.actionFill().enterTestPlanNameFilter(initialCode)
                .selectStatusUntested();
        managerDashboardPage.actionFill().clickOnApplyFiltersButton();
        managerDashboardPage.clickOnPlannedTestDateNotSetBar();
        testPlanName_NCL = managerDashboardPage.getTestPlanName();

        managerDashboardPage.actionFill().selectTestPlanRegionFilter(LotcheckRegion.NOA.toString())
                .selectTestPlanRegionFilter(LotcheckRegion.NCL.toString());
        managerDashboardPage.actionFill().clickOnApplyFiltersButton();
        managerDashboardPage.clickOnPlannedTestDateNotSetBar();
        testPlanName_NOA = managerDashboardPage.getTestPlanName();

    }

    @Test()
    @Order(60)
    public void verifyLotcheckOwnerIsNcl_Expected32() {
        assertAll(() -> assertTrue(testPlanName_NCL.contains("[NCL] @LCO"), "Verify Lotcheck Owner is NCL and display after region data"),
                () -> assertFalse(testPlanName_NOA.contains("[NOA] @LCO"), "Verify Lotcheck Owner is not NOA"));
    }

    @Test()
    @Order(61)
    public void loginNdpByLicPowerUserAndChangePriorityToYes_Step33(IJUnitTestReporter testReport) {
        NDPMyProductsPage myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton().clickSignInPage().signIn(licPowerUser).clickInternalTab().clickSponsorQueueButton()
                .waitingLoadingValueSearch().enterSearchContent(initialCode).clickSearch()
                .waitingLoadingValueSearch().selectRelease(initialCode, "Add Physical(Update)")
                .clickEditDetailButton()
                .selectYesPriority()
                .clickSaveButton();
    }

    @Test()
    @Order(62)
    public void asTestRailCheckDisplayOfTestPlan_Step34(IJUnitTestReporter testReport) {
        TestRailDashboardPage dashboardPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport);
        TestRailManagerDashboardPage managerDashboardPage = dashboardPage.navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
        managerDashboardPage.actionFill().enterTestPlanNameFilter(initialCode)
                .selectStatusUntested();
        managerDashboardPage.actionFill().clickOnApplyFiltersButton();
        managerDashboardPage.clickOnPlannedTestDateNotSetBar();
        currentPage = managerDashboardPage;

    }

    @Test()
    @Order(63)
    public void verifyDisplayOfTestPlan_Step34() {
        TestRailManagerDashboardPage managerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        assertTrue(managerDashboardPage.isPriorityIconDisplayedToTheTopLeftOfTheTestPlanName(), "Verify icons (!) representing high priority level to the top (left) of the plan name on Testrail");
    }

    @Test()
    @Order(64)
    public void submitAocReleaseAndCreateScheduleGroup_TestConditionForStep35(IJUnitTestReporter testReport) throws IOException {
        logger.logInfo("setup Schedule Group to check Reorder Schedule Groups");
        createRomAoc(testReport);
        NDPMyProductsPage myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPProductDashboardDownloadableContentTab downloadableContentTab = myProductsPage.clickOnUserNameMenu().clickOnSignOutButton().clickSignInPage().signIn(ndpUser).clickMyProducts().clickProductByGameCode(gameCode)
                .clickDownloadableContent()
                .clickCreateDLCArchive()
                .selectExpectedLotcheckSubmissionDate(15)
                .selectExpectedReleaseDate(90)
                .clickCreate();
        NDPReleaseInfoPage ndpReleaseInfoPage = downloadableContentTab.clickDLCReleaseByArchiveNo("00")
                .nav().clickReleaseInfo()
                .selectRomsThatDifferBetweenRegion(TextConstants.selectRomSameAllRegion)
                .selectLanguageUsedForTextEntry(TextConstants.english)
                .save();
        HashMap<LotcheckRemotePresenceEnum, String> lotcheckSettings = FeatureSettingsManager.getLotcheckPresenceSettings("Confirm");
        HashMap<DownloadableContentEnum, String> downloadableContentSettings = FeatureSettingsManager.getDownloadableContentSettings("Confirm");
        ndpReleaseInfoPage.nav().clickFeatures()
                .clickEditLotcheckRemotePresence()
                .setFeatures(lotcheckSettings).clickSave()
                .clickEditDownloadableContent()
                .setFeatures(downloadableContentSettings).clickSave();
        ndpReleaseInfoPage.nav().clickGuidelines().selectCompliesWithAllOfTheAboveGuidelines().clickOnSaveButton();
        ndpReleaseInfoPage.nav().clickTestScenarios().expandAllSections()
                .expandAllTestScenes()
                .enterNameHowToCheckDLC("Name How To Check DLC")
                .enterExplainHowToCheckDLC("Explain How To Check DLC")
                .clickOnSaveButton();

        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = ndpReleaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, romAocName);
        ndpReleaseInfoPage.nav().clickIssues().openRequiredApprovalTab().performAllIssueAtApprovalTab(false);
        NDPDevelopmentHome developmentHome = ndpReleaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licApprovalUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, gameCode);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
        browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser).clickMyProducts().clickProductByGameCode(gameCode).clickDownloadableContent().clickDLCReleaseByArchiveNo("00").nav().clickSubmitButton()
                .clickSubmit();
        WaitUtils.idle(2000);

        LotcheckLandingPage landingPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReport).continueToLogin().loginWithOvdLink(lcAdminNcl);

        LotcheckQueuePage lotcheckQueuePage = landingPage.clickOverallLotcheck(LotcheckRegion.NCL);

        LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickSchedule();
        scheduleGroupName_01 = "Selenium_ScheduleGroup_01";
        scheduleGroupName_02 = "Selenium_ScheduleGroup_02";
        if (lotcheckTestPlanRow.isScheduleGroupNotDisplayed(scheduleGroupName_01)) {
            lotcheckTestPlanRow.clickMenuSchedule().selectCreateNewScheduleGroup().enterScheduleGroupName(scheduleGroupName_01).clickConfirm().clickSaveChange();
        }
        if (lotcheckTestPlanRow.isScheduleGroupNotDisplayed(scheduleGroupName_02)) {
            lotcheckTestPlanRow.clickMenuSchedule().selectCreateNewScheduleGroup().enterScheduleGroupName(scheduleGroupName_02).clickConfirm().clickSaveChange();
        }
        lotcheckTestPlanRow.clickMenuSchedule(scheduleGroupName_01).selectAddTestPlans(scheduleGroupName_01).selectTestPlan("HAC-M-" + initialCode).clickConfirm()
                .clickSaveChange();
        lotcheckTestPlanRow.clickMenuSchedule(scheduleGroupName_02).selectAddTestPlans(scheduleGroupName_02).selectTestPlan(gameCode).clickConfirm()
                .clickSaveChange();
        lotcheckTestPlanRow.refreshPageSLCMS().clickSchedule();
        LotcheckReorderScheduleGroupDialog reorderScheduleGroupDialog = lotcheckTestPlanRow.clickMenuSchedule().selectReorderScheduleGroup();
        if (reorderScheduleGroupDialog.checkOrderSchedule(scheduleGroupName_01, scheduleGroupName_02)) {
            reorderScheduleGroupDialog.reorderScheduleGroup(scheduleGroupName_02, scheduleGroupName_01);
            checkScheduleOrder = true;
        } else {
            reorderScheduleGroupDialog.reorderScheduleGroup(scheduleGroupName_01, scheduleGroupName_02);
            checkScheduleOrder = false;
        }

    }


    @Test()
    @Order(65)
    public void clickConfirm_Step36() {
        LotcheckReorderScheduleGroupDialog reorderScheduleGroupDialog = (LotcheckReorderScheduleGroupDialog) currentPage;
        reorderScheduleGroupDialog.clickConfirm().clickSaveChange();
    }

    @Test()
    @Order(66)
    public void asTestRailCheckDisplayOfScheduleGroup_Step37(IJUnitTestReporter testReport) {
        TestRailDashboardPage dashboardPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport);
        currentPage = dashboardPage.navTopTestrail().navigateToManagerDashboard().checkErrorPopup();

    }

    @Test()
    @Order(67)
    public void verifyDisplayOfScheduleGroup_Expected37() {
        TestRailManagerDashboardPage managerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        if (checkScheduleOrder) {
            assertTrue(managerDashboardPage.isScheduleGroupDisplayedCorrect(scheduleGroupName_02, scheduleGroupName_01), "Verify Schedule Group shows the values ordered at SLCMS");
        } else {
            assertTrue(managerDashboardPage.isScheduleGroupDisplayedCorrect(scheduleGroupName_01, scheduleGroupName_02), "Verify Schedule Group shows the values ordered at SLCMS");
        }

    }


    private static void createRom(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        ROMLoginPage romLoginPage = (ROMLoginPage) currentPage;
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom.selectRequester(TextConstants.test_User)
                .selectType(TextConstants.initial_Release)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .enterProductName(productName)
                .enterApplicationId(applicationId)
                .selectPublishingRelationShip(publishingRelationship)
                .enterDisplayVersion("1.0.0")
                .enterReleaseVersion("00")
                .selectClassIndRating("ClassInd: Not required")
                .selectESRBRating("ESRB: Not required")
                .clickOnCreateButton()
                .waitIsLoaded();
        romInitialName = romCreateRom.getRomNameTable(applicationId);
        romCreateRom.clickOnROMFileOnROMTable(romInitialName);
        WaitUtils.idle(5000);
    }

    private static void createRomAoc(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        ROMLoginPage romLoginPage = (ROMLoginPage) currentPage;
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom.selectRequester(TextConstants.test_User)
                .selectType(TextConstants.aoc_Release)
                .enterApplicationId(applicationId)
                .enterReleaseVersion("0")
                .enterIndex("1")
                .enterReqAppVersion("0")
                .enterTagName("Tag name")
                .clickOnCreateButton()
                .waitIsLoaded();
        romAocName = romCreateRom.getRomNameTable(applicationId);
        romCreateRom.clickOnROMFileOnROMTable(romAocName);
        WaitUtils.idle(5000);
    }

    //37 step + 29 expected + 1 testCondition=67 orders;
}
