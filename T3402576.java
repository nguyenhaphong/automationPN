package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.junit.TestLogger;
import net.nintendo.automation.junit.extensions.ScenarioTest;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.data.DataGen;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthPage;
import net.nintendo.automation.ui.models.lcms.*;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssueDetails;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssues;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.ndp.*;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardProductInfoTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardReleasesTab;
import net.nintendo.automation.ui.models.ndp.existing_release.*;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPConfirmationDialog;
import net.nintendo.automation.ui.models.rom.ROMCreateRom;
import net.nintendo.automation.ui.models.rom.ROMLoginPage;
import net.nintendo.automation.ui.models.testrail.*;
import net.nintendo.automation.ui.users.User;
import net.nintendo.automation.ui.users.UserManager;
import net.nintendo.automation.utils.WaitUtils;
import net.nintendo.automation_core.common_client.enums.ServiceType;
import net.nintendo.automation_core.common_client.enums.UserType;
import net.nintendo.automation_core.common_utils.enums.LotcheckRegion;
import net.nintendo.automation_core.extent_reports.extensions.IJUnitTestReporter;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Classname: T3402576
 * Version: 1.0.0
 * Purpose:
 */
@Tag("T3402576")
@ScenarioTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3402576 extends CommonBaseTest {
    private static Browser browser;
    private static Browser romBrowser;
    private static User romUser;
    private static User ndpUser;
    private static User slcmsUser;
    private static User testrailUser;
    private static User licUser;
    private static CommonUIComponent currentPage;
    private static TestLogger logger;

    private static String productName;
    private static String applicationID;
    private static String gameCode;
    private static String initialCode;
    private static String fileRomName;

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_NguyenPhong);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
        slcmsUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);
        testrailUser = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER);
        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);

        productName = DataGen.getRandomProductName();
        NDPHomePage ndpHomePage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
        ndpHomePage.acceptCookie();
        NDPSignInPage signInPage = ndpHomePage.clickSignInPage();
        signInPage.signIn(ndpUser);
    }

    @AfterAll
    static void terminateBrowser() {
        BrowserManager.closeBrowser(browser);
        UserManager.releaseUser(ServiceType.NDP, ndpUser);
        UserManager.releaseUser(ServiceType.SLCMS, slcmsUser);
        UserManager.releaseUser(ServiceType.SEARCH, romUser);
        UserManager.releaseUser(ServiceType.NDP, licUser);
        UserManager.releaseUser(ServiceType.PMMS, testrailUser);
    }

    @Test
    @Order(1)
    public void signInAsAPartnerAndCreateANewProduct_Step1(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage ndpMyProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPCreateProductPage createProductPage = ndpMyProductsPage.createNewProduct();
        createProductPage.selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(TextConstants.third_Party_Type)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalJapanSalesRegion()
                .selectProductName(productName)
                .selectProductNameKana(TextConstants.productKana)
                .clickCreateButton();
        NDPProductDashboardPage ndpProductDashboardPage = ndpMyProductsPage.clickProductByName(productName);
        CommonAction.issueGameCode(ndpProductDashboardPage, 80,"NO");
        currentPage = ndpProductDashboardPage;
    }

    @Test
    @Order(2)
    public void fillOutTheReleaseDashboardAndSubmitToLotcheck_Step2(IJUnitTestReporter testReport) {
        NDPProductDashboardPage ndpProductDashboardPage = (NDPProductDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();
        applicationID = ndpProductDashboardProductInfoTab.getApplicationID();
        gameCode = ndpProductDashboardProductInfoTab.getGameCode();
        initialCode = ndpProductDashboardProductInfoTab.getInitialCode();
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        //create release info
        NDPReleaseInfoPage releaseInfoPage = ndpReleaseInfoPage.nav().clickReleaseInfo();
        CommonAction.selectROMSameAllRegion(releaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);
        // //create Feature
        CommonAction.getFeatureOptionData(releaseInfoPage, TextConstants.allOff, TextConstants.allOff, TextConstants.allOff, false, "", false, "");
        //Create GuildLine
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = ndpReleaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoGuidelinesPage.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();
        //Create Age Rating
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = ndpReleaseInfoPage.nav().clickAgeRating();
        ndpReleaseInfoAgeRating.clickOnAddStandardRatingButton()
                .selectStandardAgeRating("CERO")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectRatingValue("Not required")
                .clickOnSaveButtonInEditStandardRating();
        //create file ROM
        romBrowser = BrowserManager.getNewInstance();
        ROMLoginPage romLoginPage = romBrowser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN, testReport);
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom
                .selectRequester(TextConstants.test_User)
                .selectType(TextConstants.initial_Release)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .enterProductName(productName)
                .enterApplicationId(applicationID)
                .selectPublishingRelationShip(TextConstants.third_Party_Type)
                .enterDisplayVersion("1.0.0")
                .enterReleaseVersion("00")
                .selectCERORating("CERO: Not required")
                .clickOnCreateButton()
                .waitIsLoaded();
        fileRomName = romCreateRom.getRomName();
        romCreateRom.clickOnROMFileOnROMTable(applicationID);
        WaitUtils.idle(8000);
        romCreateRom.clickOnUserNameMenu().clickOnLogoutButton();
        BrowserManager.closeBrowser(romBrowser);
        //Upload ROM
        NDPReleaseInfoReleaseListNav nav = browser.getPage(NDPReleaseInfoReleaseListNav.class, testReport);
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = nav.clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
        //Issues
        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
        CommonAction.enterReasonForRequest(issuePage);
        nav.clickOnUserNameMenu().clickOnSignOutButton();
        //Approval Issue TaskRequested
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReport).clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, gameCode);
        NDPViewTaskPage ndpViewTaskPage = browser.getPage(NDPViewTaskPage.class, testReport);
        ndpViewTaskPage.clickUserMenuDropdownList().clickSignOutButton();
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser)
                .clickMyProducts()
                .clickProductByName(productName)
                .clickReleases()
                .selectInitialRelease();
        //submit product
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.enterDeveloperComment).clickSubmit();
        WaitUtils.idle(3000);
    }

    @Test
    @Order(3)
    public void signInToTestrailAndGoToTheManagerDashboard_Step3(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN, testReport);
        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailDashboardPage testRailDashboardPage = testRailLoginPage.signInTestRail(testrailUser);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
        testRailManagerDashboardPage.waitingLoadingTestPlan();
        currentPage = testRailManagerDashboardPage;
    }

    @Test
    @Order(4)
    public void selectTheSubmittedTestPlanAndAssignItToSelf_Step4(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        testRailManagerDashboardPage.actionFill()
                .enterTestPlanNameFilter(initialCode)
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan()
                .clickAssignTo()
                .selectSetValueAssignToRadio()
                .selectUser("Me")
                .clickOk()
                .waitingLoadingTestSuite();
    }

    @Test
    @Order(5)
    public void goToMyTodoAndFindTheTestPlan_Step5(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailMyToDoPage testRailMyToDoPage = testRailManagerDashboardPage.topNav().navigateToMyToDo();
        testRailMyToDoPage.actionFill()
                .enterTestPlanNameFilter(initialCode)
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan();
        currentPage = testRailMyToDoPage;
    }

    @Test
    @Order(6)
    public void selectATestCaseInTheTestPlan_Step6(IJUnitTestReporter testReport) {
        TestRailMyToDoPage testRailMyToDoPage = (TestRailMyToDoPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        currentPage = testRailMyToDoPage.navTestPlanTable()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun()
                .openTestcaseDetail(1);
    }

    @Test
    @Order(7)
    public void inTheTestCaseModalSubmitABug_Step7(IJUnitTestReporter testReport) {
        TestRailDetailTestcaseDialog testRailDetailTestcaseDialog = (TestRailDetailTestcaseDialog) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailSubmitBugDialog testRailSubmitBugDialog = testRailDetailTestcaseDialog.clickSubmitBug();
        testRailSubmitBugDialog.enterTitleInFrame("Submit bug 1")
                .enterDescription(TextConstants.enterSubmitBug)
                .clickSubmit()
                .clickOk().clickClose();
    }

    @Test
    @Order(8)
    public void signedInAsLotcheckNavigateToLotcheckReport_Step8(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReport);
        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickOverallLotcheck(LotcheckRegion.NCL);
        lotcheckQueuePage.searchByInitialCode(initialCode)
                .clickPlannedTestDateNotSet();
        currentPage = lotcheckQueuePage.navigateToSubmissionLevelInTestplan("00");
    }

    @Test
    @Order(9)
    public void selectLotcheckIssuesAndConfirmThatTheNewIssueIsInCurrentSubmissionIssues_Step9(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionOverview reportSubmissionOverview = (LotcheckReportSubmissionOverview) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues = reportSubmissionOverview.navBar().clickLotcheckIssues();
        WaitUtils.idle(3000);
        assertAll(() -> assertTrue(reportSubmissionLotcheckIssues.issueIsDisplayed("Submit bug 1"), "Verify issue is displayed"),
                () -> assertEquals(1, reportSubmissionLotcheckIssues.getNumberOfLotcheckIssueInCurrentSubmissionIssues(), "verify issue is in Current Submission Issues"));
        currentPage = reportSubmissionLotcheckIssues;
    }

    @Test
    @Order(10)
    public void publishBugToPartner_Step10(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues = (LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckReportSubmissionLotcheckIssueDetails reportSubmissionLotcheckIssueDetails = reportSubmissionLotcheckIssues.clickMenuOption().selectViewIssueDetails();
        reportSubmissionLotcheckIssueDetails.clickMenu()
                .selectPublishToPublic()
                .clickPublish();
        WaitUtils.idle(3000);
    }

    @Test
    @Order(11)
    public void signedInAsThePartnerNavigateToTheReleaseDashboard_Step11(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage ndpMyProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        currentPage = ndpMyProductsPage.clickProductByName(productName).clickReleases().selectInitialRelease().nav().clickIssues();
    }

    @Test
    @Order(12)
    public void respondToTheLotcheckIssueWithWillFixInRom_Step12(IJUnitTestReporter testReport) {
        NDPReleaseInfoIssuePage ndpReleaseInfoIssuePage = (NDPReleaseInfoIssuePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpReleaseInfoIssuePage.expandUnresolve()
                .clickExpandTestCase()
                .clickSendResolutionButton();
    }

    @Test
    @Order(13)
    public void asATradminJudgmentTestPlanOK_Step13(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport);
        TestRailDashboardPage testRailDashboardPage = (TestRailDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard().checkErrorPopup().waitingLoadingTestPlan();
        testRailManagerDashboardPage.actionFill()
                .enterTestPlanNameFilter(initialCode)
                .selectStatusUntested()
                .selectStatusPassed()
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan()
                .clickOnSetStatusButton()
                .selectStatusOnSetStatusModal("Passed")
                .clickOnUpdateStatusButtonOnSetStatusModal()
                .waitingLoadingTestSuite();
        WaitUtils.idle(5000);
        testRailManagerDashboardPage.clickOnPadlockButtonOfTheFirstTestPlan()
                .selectTestPlanJudgment("OK")
                .clickOnYesButtonOnConfirmationModal();
    }

    @Test
    @Order(14)
    public void navigateToJudgmentApprovalQueue_Step15(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        currentPage = lotcheckLandingPage.navigationToJudgmentApprovalQueue(LotcheckRegion.NCL.toString());
        WaitUtils.idle(10000);
    }

    @Test
    @Order(15)
    public void selectApproveJudgment_Step16(IJUnitTestReporter testReport) {
        LotcheckQueuePage lotcheckQueuePage = (LotcheckQueuePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        lotcheckQueuePage.searchByInitialCode(initialCode);
        LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
        currentPage = lotcheckTestPlanRow.selectApproveJudgment();
        WaitUtils.idle(5000);
    }

    @Test
    @Order(16)
    public void verifyAWarningMessageDisplayedInPopup_Step17(IJUnitTestReporter testReport) {
        LotcheckTestPlanActionConfirmationModal testPlanActionConfirmationModal = (LotcheckTestPlanActionConfirmationModal) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertTrue(testPlanActionConfirmationModal.isWarningMessageDisplayed(), "Verify a warning message displayed in Judgment Approval Popup");
    }

    @Test
    @Order(17)
    public void verifyAWarningMessageDisplays_Expected17(IJUnitTestReporter testReport) {
        LotcheckTestPlanActionConfirmationModal testPlanActionConfirmationModal = (LotcheckTestPlanActionConfirmationModal) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertEquals("There are still unresolved Lotcheck Issues.\n" + "Click the Cancel button to check the status of Lotcheck Issues.", testPlanActionConfirmationModal.getWarningMessage(),
                "Verify a warning message displays correct");
    }

    @Test
    @Order(18)
    public void selectCancelSubmission_Step14(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage ndpMyProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpMyProductsPage.clickProductByName(productName).clickReleases().selectInitialRelease();
        NDPConfirmationDialog ndpConfirmationDialog = ndpReleaseInfoPage.nav().clickCancelSubmitButton();
        ndpConfirmationDialog.clickOKInConfirm();
    }

    @Test
    @Order(19)
    public void navigateToJudgmentApprovalQueue_Step18(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        currentPage = lotcheckLandingPage.navigationToJudgmentApprovalQueue(LotcheckRegion.NCL.toString());
    }

    @Test
    @Order(20)
    public void selectApproveJudgment_Step19(IJUnitTestReporter testReport) {
        LotcheckQueuePage lotcheckQueuePage = (LotcheckQueuePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        WaitUtils.idle(10000);
        CommonAction.findProductInLCQueue(lotcheckQueuePage, 20, initialCode);
        LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
        currentPage = lotcheckTestPlanRow.selectApproveJudgment();
        WaitUtils.idle(5000);
    }

    @Test
    @Order(21)
    public void verifyDisplaysWarningMessage_Expected19(IJUnitTestReporter testReport) {
        LotcheckTestPlanActionConfirmationModal testPlanActionConfirmationModal = (LotcheckTestPlanActionConfirmationModal) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertAll(() -> assertTrue(testPlanActionConfirmationModal.isWarningMessageWhenCancelledSubmissionDisplayed(), "Verify a warning message displayed in Judgment Approval Popup"),
                () -> assertEquals("Cannot approve Judgment because the Partner has requested cancellation. Please reject the current Judgment and enter a new Judgment in TestRail.",
                        testPlanActionConfirmationModal.getWarningMessageWhenCancelledSubmission(), "Verify a warning message displays correct"));
    }

    @Test
    @Order(22)
    public void rejectJudgment_UpdateStep(IJUnitTestReporter testReport) {
        LotcheckTestPlanActionConfirmationModal testPlanActionConfirmationModal = (LotcheckTestPlanActionConfirmationModal) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckTestPlanRow lotcheckTestPlanRow = testPlanActionConfirmationModal.clickCancel();
        lotcheckTestPlanRow.selectRejectJudgment().clickConfirm();
    }

    @Test
    @Order(23)
    public void asATradminJudgmentTestPlanCancelled_UpdateStep(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport);
        TestRailDashboardPage testRailDashboardPage = (TestRailDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard().checkErrorPopup().waitingLoadingTestPlan();
        testRailManagerDashboardPage.actionFill()
                .enterTestPlanNameFilter(initialCode)
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnPadlockButtonOfTheFirstTestPlan()
                .selectTestPlanJudgment("Canceled")
                .clickOnYesButtonOnConfirmationModal();
        WaitUtils.idle(5000);
    }

    @Test
    @Order(24)
    public void approveJudgment_UpdateStep(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToJudgmentApprovalQueue(LotcheckRegion.NCL.toString());
        WaitUtils.idle(90000);
        lotcheckQueuePage.searchByInitialCode(initialCode);
        LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
        lotcheckTestPlanRow.selectApproveJudgment().clickConfirm();
    }

    @Test
    @Order(25)
    public void fillOutTheReleaseDashboard_Step20(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        ndpReleaseInfoPage.nav().clickReleaseInfo();
        ndpReleaseInfoPage.selectExpectedSubmissionDate().chooseDateInCalender(2);
        ndpReleaseInfoPage.save();
        //complete guideline
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = ndpReleaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoGuidelinesPage.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();
        //complete Rom upload
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = ndpReleaseInfoGuidelinesPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
        //Delete file Rom in folder
//        ndpReleaseInfoROMUpload.deleteFile(fileRomName);
        ndpReleaseInfoPage.nav().clickIssues();
        currentPage = ndpReleaseInfoPage;
    }

    @Test
    @Order(26)
    public void submitTheProductToLotcheck_Step21(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.enterDeveloperComment).clickSubmit();
        WaitUtils.idle(3000);
    }

    @Test
    @Order(27)
    public void returnToTestrail_Step22(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport);
        TestRailDashboardPage testRailDashboardPage = (TestRailDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
        testRailManagerDashboardPage.waitingLoadingTestPlan();
        currentPage = testRailManagerDashboardPage;
    }

    @Test
    @Order(28)
    public void findTheSecondSubmissionTestPlanAndAssignToSelf_Step23(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        testRailManagerDashboardPage.actionFill()
                .enterTestPlanNameFilter(initialCode)
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan()
                .clickAssignTo()
                .selectSetValueAssignToRadio()
                .selectUser("Me")
                .clickOk()
                .waitingLoadingTestSuite();
    }

    @Test
    @Order(29)
    public void goToMyTodo_Step24(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailMyToDoPage testRailMyToDoPage = testRailManagerDashboardPage.topNav().navigateToMyToDo();
        testRailMyToDoPage.actionFill()
                .enterTestPlanNameFilter(initialCode)
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan();
        currentPage = testRailMyToDoPage;
    }

    @Test
    @Order(30)
    public void selectAnotherTestcaseInTheTestPlan_Step25(IJUnitTestReporter testReport) {
        TestRailMyToDoPage testRailMyToDoPage = (TestRailMyToDoPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        currentPage = testRailMyToDoPage.navTestPlanTable()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun()
                .openTestcaseDetail(2);
    }

    @Test
    @Order(31)
    public void inTheTestcaseModalSubmitABug_Step26(IJUnitTestReporter testReport) {
        TestRailDetailTestcaseDialog testRailDetailTestcaseDialog = (TestRailDetailTestcaseDialog) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailSubmitBugDialog testRailSubmitBugDialog = testRailDetailTestcaseDialog.clickSubmitBug();
        testRailSubmitBugDialog.enterTitle("Submit bug 2")
                .enterDescription(TextConstants.enterSubmitBug)
                .clickSubmit()
                .clickOk().clickClose();
    }

    @Test
    @Order(32)
    public void signedInAsLotcheckNavigateToLotcheckReport_Step27(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickOverallLotcheck(LotcheckRegion.NCL);
        lotcheckQueuePage.searchByInitialCode(initialCode)
                .clickPlannedTestDateNotSet();
        currentPage = lotcheckQueuePage.navigateToSubmissionLevelInTestplan("00");
    }

    @Test
    @Order(33)
    public void selectLotcheckIssues_Step28(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionOverview reportSubmissionOverview = (LotcheckReportSubmissionOverview) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues = reportSubmissionOverview.navBar().clickLotcheckIssues();
        WaitUtils.idle(3000);
        reportSubmissionLotcheckIssues.clickXpandVersion();
        currentPage = reportSubmissionLotcheckIssues;
    }

    @Test
    @Order(34)
    public void verifyTheIssueFromFirstSubmissionIsInProductResolutionArchiveIssuesTheNewIssueIsInCurrentSubmissionIssues_Expected28(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues = (LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertAll(() -> assertTrue(reportSubmissionLotcheckIssues.recordIsDisplayInCurrentSubmissionIssues(), "Verify issue is in Current Submission Issues"),
                () -> assertEquals("Submit bug 2", reportSubmissionLotcheckIssues.getTitleOfIssue("Submit bug 2"), "Verify the new issue is in Current Submission Issue"),
                () -> assertTrue(reportSubmissionLotcheckIssues.recordIsDisplayInProductResolutionArchiveIssues(), "Verify issue is in Product Resolution Archive Issue"),
                () -> assertEquals("Submit bug 1", reportSubmissionLotcheckIssues.getTitleOfIssue("Submit bug 1"), "Verify the issue from first submission is in the Product Archive Issue"));
    }

    @Test
    @Order(35)
    public void publishBugToPartner_Step29(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues = (LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckReportSubmissionLotcheckIssueDetails reportSubmissionLotcheckIssueDetails = reportSubmissionLotcheckIssues.clickMenuOption().selectViewIssueDetails();
        reportSubmissionLotcheckIssueDetails.clickMenu()
                .selectPublishToPublic()
                .clickPublish();
        WaitUtils.idle(3000);
    }

    @Test
    @Order(36)
    public void signedInAsThePartnerNavigateToTheReleaseDashboard_Step30(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage ndpMyProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        currentPage = ndpMyProductsPage.clickProductByName(productName).clickReleases().selectInitialRelease().nav().clickIssues();
    }

    @Test
    @Order(37)
    public void respondToTheLotcheckIssueWithWillFixInRom_Step31(IJUnitTestReporter testReport) {
        NDPReleaseInfoIssuePage ndpReleaseInfoIssuePage = (NDPReleaseInfoIssuePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpReleaseInfoIssuePage.expandUnresolve()
                .clickExpandTestCase()
                .clickSendResolutionButton();
    }

    @Test
    @Order(38)
    public void returnToTestrail_Step32(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport);
        TestRailDashboardPage testRailDashboardPage = (TestRailDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
        testRailManagerDashboardPage.waitingLoadingTestPlan();
        currentPage = testRailManagerDashboardPage;
    }

    @Test
    @Order(39)
    public void findTheSecondSubmissionTestPlanAndSelectAll_Step33(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        testRailManagerDashboardPage.actionFill()
                .enterTestPlanNameFilter(initialCode)
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan();
    }

    @Test
    @Order(40)
    public void setTheTestPlanStatusToFailed_Step34(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        testRailManagerDashboardPage.clickOnSetStatusButton()
                .selectStatusOnSetStatusModal("Failed")
                .clickOnUpdateStatusButtonOnSetStatusModal()
                .waitingLoadingTestSuite();
        WaitUtils.idle(5000);
    }

    @Test
    @Order(41)
    public void selectTheLockIcon_Step35(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        testRailManagerDashboardPage.clickOnPadlockButtonOfTheFirstTestPlan();
    }

    @Test
    @Order(42)
    public void setTheJudgmentToNG_Step36(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        testRailManagerDashboardPage.clickOnPadlockButtonOfTheFirstTestPlan()
                .selectTestPlanJudgment("NG")
                .clickOnYesButtonOnConfirmationModal();
    }

    @Test
    @Order(43)
    public void returnToLotcheckAndNavigateToJudgmentApprovalQueue_Step37(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        currentPage = lotcheckLandingPage.navigationToJudgmentApprovalQueue(LotcheckRegion.NCL.toString());
    }

    @Test
    @Order(44)
    public void approveTheNGJudgment_Step38(IJUnitTestReporter testReport) {
        LotcheckQueuePage lotcheckQueuePage = (LotcheckQueuePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        lotcheckQueuePage.searchByInitialCode(initialCode);
        LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
        lotcheckTestPlanRow.selectApproveJudgment().clickConfirm();
    }

    @Test
    @Order(45)
    public void signInAsThePartnerAndSubmitTheSameProductToLotcheck_Step39(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        ndpReleaseInfoPage.nav().clickReleaseInfo();
        ndpReleaseInfoPage.selectExpectedSubmissionDate().chooseDateInCalender(2);
        ndpReleaseInfoPage.save();
        //complete guideline
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = ndpReleaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoGuidelinesPage.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();
        //complete Rom upload
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = ndpReleaseInfoGuidelinesPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
        //Delete file Rom in folder
        ndpReleaseInfoROMUpload.deleteFile(fileRomName);
        ndpReleaseInfoPage.nav().clickIssues();
        //Submit product
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.enterDeveloperComment).clickSubmit();
        WaitUtils.idle(3000);
    }

    @Test
    @Order(46)
    public void returnToTestRail_Step40(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport);
        TestRailDashboardPage testRailDashboardPage = (TestRailDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
        testRailManagerDashboardPage.waitingLoadingTestPlan();
        currentPage = testRailManagerDashboardPage;
    }

    @Test
    @Order(47)
    public void findTheThirdSubmissionTestPlanAndAssignToSelf_Step41(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        testRailManagerDashboardPage.actionFill()
                .enterTestPlanNameFilter(initialCode)
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan()
                .clickAssignTo()
                .selectSetValueAssignToRadio()
                .selectUser("Me")
                .clickOk()
                .waitingLoadingTestSuite();
    }

    @Test
    @Order(48)
    public void goToMyTodo_Step42(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailMyToDoPage testRailMyToDoPage = testRailManagerDashboardPage.topNav().navigateToMyToDo();
        testRailMyToDoPage.actionFill()
                .enterTestPlanNameFilter(initialCode)
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan();
        currentPage = testRailMyToDoPage;
    }

    @Test
    @Order(49)
    public void selectAnotherTestCaseInTheTestPlan_Step43(IJUnitTestReporter testReport) {
        TestRailMyToDoPage testRailMyToDoPage = (TestRailMyToDoPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        currentPage = testRailMyToDoPage.navTestPlanTable()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun()
                .openTestcaseDetail(3);
    }

    @Test
    @Order(50)
    public void inTheTestCaseModalSubmitABug_Step44(IJUnitTestReporter testReport) {
        TestRailDetailTestcaseDialog testRailDetailTestcaseDialog = (TestRailDetailTestcaseDialog) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailSubmitBugDialog testRailSubmitBugDialog = testRailDetailTestcaseDialog.clickSubmitBug();
        testRailSubmitBugDialog.enterTitle("Submit bug 3")
                .enterDescription(TextConstants.enterSubmitBug)
                .clickSubmit()
                .clickOk().clickClose();
    }

    @Test
    @Order(51)
    public void returnToLotcheckAndFindTheProductViaSubmissionSearch_Step45(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = lotcheckLandingPage.navigateToSubmissionSearch();
        lotcheckSubmissionSearchPage.searchSubmission(initialCode);
        currentPage = lotcheckSubmissionSearchPage;
    }

    //Remove step 46
    @Test
    @Order(52)
    public void selectTheThirdSubmission_Step47(IJUnitTestReporter testReport) {
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = (LotcheckSubmissionSearchPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        currentPage = lotcheckSubmissionSearchPage.selectSubmission(gameCode, "00", "02");
    }

    @Test
    @Order(53)
    public void selectTheLotcheckIssuesTab_Step48(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionOverview reportSubmissionOverview = (LotcheckReportSubmissionOverview) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues = reportSubmissionOverview.navBar().clickLotcheckIssues();
        WaitUtils.idle(3000);
        reportSubmissionLotcheckIssues.expandCollapsedIssueInArchive();
        currentPage = reportSubmissionLotcheckIssues;
    }

    @Test
    @Order(54)
    public void verifyExpected_Expected48(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues = (LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertAll(() -> assertTrue(reportSubmissionLotcheckIssues.recordIsDisplayInCurrentSubmissionIssues(), "Verify the new issue is in Current Submission Issues"),
                () -> assertEquals("Submit bug 3", reportSubmissionLotcheckIssues.getTitleOfIssue("Submit bug 3"), "Verify the new issue is in Current Submission Issue"),
                () -> assertTrue(reportSubmissionLotcheckIssues.recordIsDisplayInProductResolutionArchiveIssues(), "Verify the Issue from the First Submission is found in Product Resolution Archive"),
                () -> assertEquals("Submit bug 1", reportSubmissionLotcheckIssues.getTitleOfIssue("Submit bug 1"), "Verify the Issue from the First Submission is found in Product Resolution Archive"),
                () -> assertTrue(reportSubmissionLotcheckIssues.recordIsDisplayInProductResolutionArchiveIssuesInSecondSubmission(), "Verify the Issue from the Second Submission is found in Product " +
                        "Resolution Archive Issues"),
                () -> assertEquals("Submit bug 2", reportSubmissionLotcheckIssues.getTitleOfIssue("Submit bug 2"), "The Issue from the Second Submission is found in Product Resolution Archive " +
                        "Issues"),
                () -> assertEquals("FIXED", reportSubmissionLotcheckIssues.getIssueStatusOfFirstSubmissionInProductArchiveIssue(), "Verify the status of issue in Product Resolution Archive Issues " +
                        "is Fixed"),
                () -> assertEquals("FIXED", reportSubmissionLotcheckIssues.getIssueStatusOfSecondSubmissionInProductArchiveIssue(), "Verify the status of issue in Product Resolution Archive Issues " +
                        "is Fixed"));
    }
}
