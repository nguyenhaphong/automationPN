package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.junit.TestLogger;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.data.DataGen;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthPage;
import net.nintendo.automation.ui.models.lcms.LotcheckHomePage;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.ui.models.lcms.LotcheckQueuePage;
import net.nintendo.automation.ui.models.lcms.LotcheckTestPlanRow;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssueDetails;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssuePartnerThread;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssueThread;
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
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPCreateReleasePage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardProductInfoTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardReleasesTab;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoFeaturesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoGuidelinesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoIssuePage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoROMUpload;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoReleaseListNav;
import net.nintendo.automation.ui.models.rom.ROMCreateRom;
import net.nintendo.automation.ui.models.rom.ROMLoginPage;
import net.nintendo.automation.ui.models.testrail.TestRailAddProblemDescriptionDialog;
import net.nintendo.automation.ui.models.testrail.TestRailAddRemarkDialog;
import net.nintendo.automation.ui.models.testrail.TestRailDashboardPage;
import net.nintendo.automation.ui.models.testrail.TestRailDetailTestcaseDialog;
import net.nintendo.automation.ui.models.testrail.TestRailLoginPage;
import net.nintendo.automation.ui.models.testrail.TestRailManagerDashboardPage;
import net.nintendo.automation.ui.models.testrail.TestRailMyToDoPage;
import net.nintendo.automation.ui.models.testrail.TestRailPreviewDialog;
import net.nintendo.automation.ui.models.testrail.TestRailSubmitBugDialog;
import net.nintendo.automation.ui.models.testrail.TestRailTestPlanTable;
import net.nintendo.automation.ui.users.User;
import net.nintendo.automation.ui.users.UserManager;
import net.nintendo.automation.utils.WaitUtils;
import net.nintendo.automation_core.common_client.enums.ServiceType;
import net.nintendo.automation_core.common_client.enums.UserType;
import net.nintendo.automation_core.common_utils.enums.LotcheckRegion;
import net.nintendo.automation_core.extent_reports.extensions.IJUnitTestReporter;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Classname: T3402471
 * Version: 1.0.0
 * Purpose:
 */
@Tag("T3402471")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3402471 extends CommonBaseTest {
    private static Browser browser;
    private static User romUser;
    private static User ndpUser;
    private static User slcmsUser;
    private static User testrailUser;
    private static User licUser;
    private static CommonUIComponent currentPage;
    private static TestLogger logger;
    private static Browser romBrowser;

    //private static String productName="Selenium_ouLN";
    private static String publishingRelationship;
    private static String productName;
    private static String applicationID;
    private static String initialCode;
    private static String gameCode;
    private static String fileRomName;
    private static String warningMessage;
    private static String messageExpected = "This Issue is not active in a current submission. Therefore, fields related to Issue Information cannot be edited. However, posts can be made to the Partner and Lotcheck Threads.";
    private static boolean publishToPublicWillNotBeAvailable;
    private static boolean resetIssueWillNotBeAvailable;
    private static boolean editDetailENWillNotBeAvailable;
    private static boolean licTypeReplyPartner;
    private static boolean partnerThreadStatusReplyPartner;
    private static boolean publishToPublicIsUnavailable;
    private static boolean resetIssueIsUnavailable;
    private static boolean lotcheckThreadStatusReplyPartner;
    private static String titleCreatePage;
    private static boolean testPlanIsSubmitToLotcheck;
    private static boolean testPlanIsAppear;
    private static boolean modalWindowViewDetail;
    private static String descriptionBug = "bug submit at patch match with initial";
    private static String responseForApprovalOrReject = "test comment";
    private static boolean recordIsDisplayCurrentIssue;
    private static int numberPermanentWaives;
    private static int numberTemporaryWaive;
    private static int numberWillFixInROM;
    private static int numberClosed;
    private static String valueReasonForRequestPermanentWaiver1;
    private static String valueResponseForApprovalOrRejectionPermanentWaiver1;
    private static String valueReasonForRequestPermanentWaiver2;
    private static String valueResponseForApprovalOrRejectionPermanentWaiver2;
    private static String valueReasonForRequestPermanentWaiver3;
    private static String valueResponseForApprovalOrRejectionPermanentWaiver3;
    private static String valueReasonForRequestTemporaryWaiver;
    private static String valueResponseForApprovalOrRejectionTemporaryWaiver;
    private static String numberIssuesReported;
    private static String valueReasonForRequestInNDP;
    private static String valueResponseForApprovalOrRejectionInNDP;
    private static String testcase_01;
    private static String testcase_02;
    private static String testcase_03;
    private static String testcase_04;
    private static String testcase_05;
    private static String testcase_06;
    private static String testcase_07;
    //private static String testcase_02;


    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_Quynhpnt2);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
        slcmsUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);
        testrailUser = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER);
        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);
        publishingRelationship = TextConstants.third_Party_Type;
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
    }

    @AfterAll
    static void terminateBrowser() {
        //BrowserManager.closeBrowser(browser);
        UserManager.releaseUser(ServiceType.NDP, ndpUser);
        UserManager.releaseUser(ServiceType.SLCMS, slcmsUser);
        UserManager.releaseUser(ServiceType.SEARCH, romUser);
        UserManager.releaseUser(ServiceType.NDP, licUser);
        UserManager.releaseUser(ServiceType.PMMS, testrailUser);
    }

    @BeforeEach
    public void each(IJUnitTestReporter testReport) {
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
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
                .enterApplicationId(applicationID)
                .selectPublishingRelationShip(publishingRelationship)
                .enterDisplayVersion("1.0.0")
                .enterReleaseVersion("00")
                .selectCERORating("CERO: Not required")
                .clickOnCreateButton()
                .waitIsLoaded();
        fileRomName = romCreateRom.getRomNameTable(applicationID);
        romCreateRom.clickOnROMFileOnROMTable(fileRomName);
        WaitUtils.idle(5000);
    }

    @Test()
    @Order(1)
    public void submitInitialReleaseToLotcheck_TestCondition(IJUnitTestReporter testReport) {
        productName = DataGen.getRandomProductName();
        NDPHomePage homePage = (NDPHomePage) currentPage;
        NDPMyProductsPage myProductsPage = homePage.acceptCookie().clickSignInPage().signIn(ndpUser).clickMyProducts();
        myProductsPage.createNewProduct()
                .selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(publishingRelationship)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalJapanSalesRegion()
                .selectProductName(productName)
                .selectProductNameKana(TextConstants.productKana)
                .clickCreateButton();
        NDPProductDashboardPage myProductDashBoard = myProductsPage.clickProductByName(productName);
        currentPage = myProductDashBoard;
        CommonAction.issueGameCode(myProductDashBoard, 60, "NO");
        NDPProductDashboardPage ndpProductDashboardPage = (NDPProductDashboardPage) currentPage;
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();
        applicationID = ndpProductDashboardProductInfoTab.getApplicationID();
        gameCode = ndpProductDashboardProductInfoTab.getGameCode();
        initialCode = ndpProductDashboardProductInfoTab.getInitialCode();

        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        NDPReleaseInfoPage releaseInfoPage = ndpReleaseInfoPage.nav().clickReleaseInfo();

        CommonAction.selectROMSameAllRegion(releaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);

        NDPReleaseInfoFeaturesPage releaseInfoFeaturesPage = releaseInfoPage.nav().clickFeatures();
        CommonAction.getFeatureOptionData(releaseInfoPage, TextConstants.allOff, TextConstants.allOff, TextConstants.allOff, false, "", false, "");
        releaseInfoFeaturesPage.clickContinue();
        releaseInfoPage.nav().clickGuidelines().selectCompliesWithAllOfTheAboveGuidelines().clickOnSaveButton();
        releaseInfoPage.nav().clickAgeRating().clickOnAddStandardRatingButton()
                .selectStandardAgeRating("CERO")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectRatingValue("Not required")
                .clickOnSaveButtonInEditStandardRating();
        releaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton();

        createRom(testReport);
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        homePage = (NDPHomePage) currentPage;
        myProductsPage = homePage.clickSignInPage().signIn(ndpUser).clickMyProducts();
        ndpProductDashboardReleasesTab = myProductsPage.clickProductByGameCode(initialCode).clickReleases();
        releaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = releaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
        releaseInfoPage.nav().clickIssues().openRequiredApprovalTab().performAllIssueAtApprovalTab(false);

        NDPDevelopmentHome developmentHome = ndpReleaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
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

        ndpViewTaskPage.clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(ndpUser)
                .clickMyProducts().clickProductByGameCode(gameCode).clickReleases().selectInitialRelease().nav().clickSubmitButton()
                .clickSubmit();
        assertEquals(TextConstants.submitToLotcheck, ndpReleaseInfoPage.getStatusValue(), TextConstants.initialSubmitSuccess);
    }

    @Test()
    @Order(2)
    public void loginTestRailAndNavToMyToDoAndFilterTestPlan_Step(IJUnitTestReporter testReport) {

        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        TestRailManagerDashboardPage managerDashboardPage = testRailLoginPage.signInTestRail(testrailUser).navTopTestRail().navigateToManagerDashboard();
        managerDashboardPage.checkErrorPopup();
        managerDashboardPage.actionFill().enterTestPlanNameFilter(gameCode)
                .selectStatusUntested().selectStatusPassed();
        managerDashboardPage.actionFill().clickOnApplyFiltersButton();
        managerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan()
                .clickAssignTo()
                .selectSetValueAssignToRadio()
                .selectUser("Me")
                .clickOk()
                .waitingLoadingTestSuite();
        TestRailMyToDoPage myToDoPage = managerDashboardPage.topNav()
                .navigateToMyToDo();
        myToDoPage.actionFill()
                .enterTestPlanNameFilter(gameCode);
        myToDoPage.actionFill().clickOnApplyFiltersButton();
        currentPage = myToDoPage.navTestPlanTable()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite();
    }

    @Test()
    @Order(3)
    public void verifyTestPlanOnTestConditionAreDisplayed_Expected() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        boolean isTestPlanDisplayed = !testPlanTable.testplanIsNotVisible(gameCode);
        assertAll(() -> assertEquals("My Todo", testPlanTable.getPageTitle(), "Verify the My Todo page is displayed"),
                () -> assertTrue(isTestPlanDisplayed, "Verify test plan on test condition is displayed"));
    }

    @Test()
    @Order(4)
    public void fullyExpandATestPlanAndSelectATestCase_Step() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        testPlanTable.expandAllTestSuites()
                .clickOnTheFirstTestRun();
        testcase_01 = testPlanTable.getTestcaseName(1);
        currentPage = testPlanTable.openTestcaseDetail(1);
    }

    @Test()
    @Order(5)
    public void verifyTestcaseViewModalIsDisplayed_Expected() {
        TestRailDetailTestcaseDialog detailTestcaseDialog = (TestRailDetailTestcaseDialog) currentPage;
        assertTrue(detailTestcaseDialog.isDetailTestcaseModalOfTestcaseDisplayed(testcase_01), "Verify user can access the Test Case View dialog");
    }

    @Test()
    @Order(6)
    public void selectSubmitBug_Step() {
        TestRailDetailTestcaseDialog detailTestcaseDialog = (TestRailDetailTestcaseDialog) currentPage;
        TestRailSubmitBugDialog submitBugDialog = detailTestcaseDialog.clickSubmitBug();
        submitBugDialog.addTitle("Permanent Waiver")
                .enterDescription("> test\n" +
                        "Enter description");
        currentPage = submitBugDialog;
    }

    @Test()
    @Order(7)
    public void clickOnPreviewButton_Step() {
        TestRailSubmitBugDialog submitBugDialog = (TestRailSubmitBugDialog) currentPage;
        currentPage = submitBugDialog.clickPreview();
    }

    @Test()
    @Order(8)
    public void verifyDisplayOfPreviewWindow_Expected() {
        TestRailPreviewDialog previewDialog = (TestRailPreviewDialog) currentPage;
        assertEquals("test\n" +
                "Enter description", previewDialog.getDescription(), "Verify display of description");
    }

    @Test()
    @Order(9)
    public void clickCloseButton_Step() {
        TestRailPreviewDialog previewDialog = (TestRailPreviewDialog) currentPage;
        currentPage = previewDialog.clickClose();
    }

    @Test()
    @Order(10)
    public void clickSubmit_Step() {
        TestRailSubmitBugDialog submitBugDialog = (TestRailSubmitBugDialog) currentPage;
        currentPage = submitBugDialog.clickSubmit();
    }

    @Test()
    @Order(11)
    public void verifySubmitBugSuccessfully_Expected() {
        TestRailSubmitBugDialog submitBugDialog = (TestRailSubmitBugDialog) currentPage;
        String message = submitBugDialog.getMessageSuccessfully();
        TestRailDetailTestcaseDialog detailTestcaseDialog = submitBugDialog.clickOk();
        boolean isTestInformationTabDisplayed = detailTestcaseDialog.isTestInformationTabDisplayed();
        currentPage = detailTestcaseDialog;
        assertAll(() -> assertEquals("Your Issue has been submitted successfully.", message, "Verify submit bug successfully"),
                () -> assertTrue(isTestInformationTabDisplayed, "Verify Test information tab is displayed"));

    }

    @Test()
    @Order(12)
    public void clickCloseAndSelectAnotherTestCase() {
        TestRailDetailTestcaseDialog detailTestcaseDialog = (TestRailDetailTestcaseDialog) currentPage;
        TestRailTestPlanTable testPlanTable = detailTestcaseDialog.clickClose();
        TestRailMyToDoPage myToDoPage = testPlanTable.navTopTestrail().navigateToMyToDo();
        myToDoPage.actionFill()
                .enterTestPlanNameFilter(gameCode);
        myToDoPage.actionFill().clickOnApplyFiltersButton();
        myToDoPage.navTestPlanTable()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun();
        testcase_02 = testPlanTable.getTestcaseName(2);
        currentPage = testPlanTable.openTestcaseDetail(2);

    }

    @Test()
    @Order(13)
    public void verifyTestcaseViewModalOfTestcaseO2IsDisplayed_Expected() {
        TestRailDetailTestcaseDialog detailTestcaseDialog = (TestRailDetailTestcaseDialog) currentPage;
        assertTrue(detailTestcaseDialog.isDetailTestcaseModalOfTestcaseDisplayed(testcase_02), "Verify user can access the Test Case View dialog");
    }

    @Test()
    @Order(14)
    public void performSubmitAnotherTest_Step() {
        TestRailDetailTestcaseDialog detailTestcaseDialog = (TestRailDetailTestcaseDialog) currentPage;
        currentPage = detailTestcaseDialog.clickSubmitBug().addTitle("Temporary Waiver").enterDescription("Test case 02")
                .clickSubmit();
    }

    @Test()
    @Order(15)
    public void verifySubmitSuccessfullyForAnotherTestcase_Expected() {
        TestRailSubmitBugDialog submitBugDialog = (TestRailSubmitBugDialog) currentPage;
        String message = submitBugDialog.getMessageSuccessfully();
        currentPage = submitBugDialog.clickOk();
        assertEquals("Your Issue has been submitted successfully.", message, "Verify submit bug successfully");
    }

    @Test()
    @Order(16)
    public void repeatSteps7And8ToSubmitBugForThreeTestcases_Step() {
        TestRailDetailTestcaseDialog detailTestcaseDialog = (TestRailDetailTestcaseDialog) currentPage;
        TestRailTestPlanTable testPlanTable = detailTestcaseDialog.clickClose();
        testcase_03 = testPlanTable.getTestcaseName(3);
        testPlanTable.openTestcaseDetail(3)
                .clickSubmitBug()
                .enterDescription("Test case 03")
                .addTitle("Will Fix in ROM")
                .clickSubmit()
                .clickOk()
                .clickClose();
        testcase_04 = testPlanTable.getTestcaseName(4);
        testPlanTable.openTestcaseDetail(4)
                .clickSubmitBug()
                .enterDescription("Test case 04")
                .addTitle("Closed")
                .clickSubmit()
                .clickOk()
                .clickClose();
        testcase_05 = testPlanTable.getTestcaseName(5);
        currentPage = testPlanTable.openTestcaseDetail(5)
                .clickSubmitBug()
                .enterDescription("Test case 05")
                .addTitle("Need Response")
                .clickSubmit()
                .clickOk()
                .clickClose();
    }

    @Test()
    @Order(17)
    public void verifyFiveIssuesCreated_Expected() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        assertAll(() -> assertTrue(testPlanTable.isTestCaseSubmittedBug(testcase_01), "Verify submit bug for testcase 01 successfully"),
                () -> assertTrue(testPlanTable.isTestCaseSubmittedBug(testcase_02), "Verify submit bug for testcase 02 successfully"),
                () -> assertTrue(testPlanTable.isTestCaseSubmittedBug(testcase_03), "Verify submit bug for testcase 03 successfully"),
                () -> assertTrue(testPlanTable.isTestCaseSubmittedBug(testcase_04), "Verify submit bug for testcase 04 successfully"),
                () -> assertTrue(testPlanTable.isTestCaseSubmittedBug(testcase_05), "Verify submit bug for testcase 05 successfully"));

    }

    @Test()
    @Order(18)
    public void addRemark_Step() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        currentPage = testPlanTable.clickAddRemark();
    }

    @Test()
    @Order(19)
    public void verifyDisplayAddRemarkModal_Expected() {
        TestRailAddRemarkDialog addRemarkDialog = (TestRailAddRemarkDialog) currentPage;
        assertEquals("Add Remark", addRemarkDialog.getModalName(), "Verify the Add Remark modal window is displayed");
    }

    @Test()
    @Order(20)
    public void completeAddRemarkTestcase_Step() {
        TestRailAddRemarkDialog addRemarkDialog = (TestRailAddRemarkDialog) currentPage;
        testcase_06 = "Title of remark case 06";
        currentPage = addRemarkDialog.enterTitle(testcase_06)
                .enterDescription("Description of remark case")
                .selectTestcaseType()
                .enterEstimateTestingTime("10")
                .clickSubmit();
    }

    @Test()
    @Order(21)
    public void verifyAddRemarkTestcaseSuccessfully_Expected() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        TestRailManagerDashboardPage managerDashboardPage = testPlanTable.navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
        managerDashboardPage.actionFill().enterTestPlanNameFilter(gameCode)
                .selectStatusUntested().selectStatusPassed();
        managerDashboardPage.actionFill().clickOnApplyFiltersButton();
        currentPage = managerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .expandAllTestRuns()
                .clickOnAllTestCases();
        managerDashboardPage.scrollToRemarkBottom();
        boolean isRemarkTestcaseDisplayed = managerDashboardPage.isTestcaseDisplayed("Remarks", testcase_06);
        assertTrue(isRemarkTestcaseDisplayed, "Verify testcase remark added is displayed");

    }

    @Test()
    @Order(22)
    public void clickSubmitBugForRemarkTestcase_Step() {
        TestRailManagerDashboardPage managerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        managerDashboardPage.selectTestcaseCheckbox_Compatibility("Remarks", 1)
                .clickAssignTo()
                .selectSetValueAssignToRadio()
                .selectUser("Me")
                .clickOk()
                .waitingLoadingTestSuite();
        TestRailMyToDoPage myToDoPage = managerDashboardPage.topNav()
                .navigateToMyToDo();
        myToDoPage.actionFill()
                .enterTestPlanNameFilter(gameCode);
        myToDoPage.actionFill().clickOnApplyFiltersButton();
        currentPage = myToDoPage.navTestPlanTable()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .expandAllTestRuns()
                .openTestCaseDetail("Remarks", testcase_06)
                .clickSubmitBug();
    }

    @Test()
    @Order(23)
    public void verifyTestcaseViewModalOfRemarksTestcaseIsDisplayed_Expected() {
        TestRailSubmitBugDialog submitBugDialog = (TestRailSubmitBugDialog) currentPage;
        assertTrue(submitBugDialog.isSubmitBugDialogDisplayedForTestcase(testcase_06), "Verify the Submit Bug modal window is displayed for the remark test case");
    }

    @Test()
    @Order(24)
    public void submitBugForRemarksTestcase_Step() {
        TestRailSubmitBugDialog submitBugDialog = (TestRailSubmitBugDialog) currentPage;
        currentPage = submitBugDialog.enterDescription("Test case 06")
                .addTitle("Permanent Waiver")
                .clickSubmit();
    }

    @Test()
    @Order(25)
    public void verifySubmitBugForRemarksTestcaseSuccessfully_Expected() {
        TestRailSubmitBugDialog submitBugDialog = (TestRailSubmitBugDialog) currentPage;
        String message = submitBugDialog.getMessageSuccessfully();
        assertEquals("Your Issue has been submitted successfully.", message, "Verify submit bug successfully");
    }

    @Test()
    @Order(26)
    public void closeTestcaseViewModalAndSelectAddProblemDescription_Step() {
        TestRailSubmitBugDialog submitBugDialog = (TestRailSubmitBugDialog) currentPage;
        TestRailTestPlanTable testPlanTable = submitBugDialog.clickOk().clickClose();
        TestRailMyToDoPage myToDoPage = testPlanTable.navTopTestrail().navigateToMyToDo();
        myToDoPage.actionFill()
                .enterTestPlanNameFilter(gameCode);
        myToDoPage.actionFill().clickOnApplyFiltersButton();
        myToDoPage.navTestPlanTable()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite();
        currentPage = testPlanTable.clickAddProblemDescription();
    }

    @Test()
    @Order(27)
    public void verifyAddProblemDescriptionModalIsDisplaye_Expected() {
        TestRailAddProblemDescriptionDialog addProblemDescriptionDialog = (TestRailAddProblemDescriptionDialog) currentPage;
        assertEquals("Add Problem Description", addProblemDescriptionDialog.getModalName(), "Verify Add Problem Description modal is displayed");
    }

    @Test()
    @Order(28)
    public void enterValidInformationAndSubmit_Step() {
        TestRailAddProblemDescriptionDialog addProblemDescriptionDialog = (TestRailAddProblemDescriptionDialog) currentPage;
        testcase_07 = "Problem Description testcase";
        currentPage = addProblemDescriptionDialog.enterTitle(testcase_07)
                .enterEstimateTestingTime("15")
                .clickSubmit();
    }

    @Test()
    @Order(29)
    public void verifyProblemDescriptionTestcaseIsDisplayed_Expected() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        TestRailMyToDoPage myToDoPage = testPlanTable.navTopTestrail().navigateToMyToDo();
        myToDoPage.actionFill()
                .enterTestPlanNameFilter(gameCode);
        myToDoPage.actionFill().clickOnApplyFiltersButton();
        myToDoPage.navTestPlanTable()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .expandAllTestRuns()
                .clickOnAllTestCases();
        myToDoPage.scrollToProblemBottom();
        boolean isProblemDescriptionTestcaseDisplayed = testPlanTable.isTestcaseDisplayed("Problem Description", testcase_07);
        assertTrue(isProblemDescriptionTestcaseDisplayed, "Verify problem description testcase added is displayed");
    }

    @Test()
    @Order(30)
    public void openProblemDescriptionTestcaseAndSelectSubmitBug_Step() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        currentPage = testPlanTable.openTestCaseDetail("Problem Description", testcase_07)
                .clickSubmitBug();
    }

    @Test()
    @Order(31)
    public void verifyTestcaseViewModalOfProblemDescriptionTestcaseIsDisplayed_Expected() {
        TestRailSubmitBugDialog submitBugDialog = (TestRailSubmitBugDialog) currentPage;
        assertTrue(submitBugDialog.isSubmitBugDialogDisplayedForTestcase(testcase_07), "Verify the Submit Bug modal window is displayed for the problem description test case");
    }

    @Test()
    @Order(32)
    public void submitBugForProblemDescriptionTestcase_Step() {
        TestRailSubmitBugDialog submitBugDialog = (TestRailSubmitBugDialog) currentPage;
        currentPage = submitBugDialog.enterDescription("Test case 07")
                .addTitle("Permanent Waiver")
                .clickSubmit();
    }

    @Test()
    @Order(33)
    public void verifySubmitBugForProblemDescriptionTestcaseSuccessfully_Expected() {
        TestRailSubmitBugDialog submitBugDialog = (TestRailSubmitBugDialog) currentPage;
        String message = submitBugDialog.getMessageSuccessfully();
        assertEquals("Your Issue has been submitted successfully.", message, "Verify submit bug successfully");
    }

    @Test()
    @Order(34)
    public void publishSevenIssuesToPartnerWithTypeBug_Step(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickTesterAssignment(LotcheckRegion.NCL);
        int i = 0;
        while (i < 50) {
            lotcheckLandingPage.navigationToTesterAssignmentQueue("NCL").searchByInitialCode(initialCode);
            String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
            i++;
            if (numberValue.equalsIgnoreCase("1")) {
                break;
            }
        }
        lotcheckQueuePage.clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview submissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        LotcheckReportSubmissionLotcheckIssues submissionLotcheckIssues = submissionOverview.navBar().clickLotcheckIssues();
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails = submissionLotcheckIssues.clickMenuOfIssue(testcase_01)
                .selectViewIssueDetailsOfIssue(testcase_01);
        submissionLotcheckIssueDetails.performPublishIssue("Bug");

        submissionLotcheckIssueDetails.clickBackToLotcheckIssue();
        submissionLotcheckIssues.clickMenuOfIssue(testcase_02)
                .selectViewIssueDetailsOfIssue(testcase_02);
        submissionLotcheckIssueDetails.performPublishIssue("Bug");

        submissionLotcheckIssueDetails.clickBackToLotcheckIssue();
        submissionLotcheckIssues.clickMenuOfIssue(testcase_03)
                .selectViewIssueDetailsOfIssue(testcase_03);
        submissionLotcheckIssueDetails.performPublishIssue("Bug");

        submissionLotcheckIssueDetails.clickBackToLotcheckIssue();
        submissionLotcheckIssues.clickMenuOfIssue(testcase_04)
                .selectViewIssueDetailsOfIssue(testcase_04);
        submissionLotcheckIssueDetails.performPublishIssue("Bug");

        submissionLotcheckIssueDetails.clickBackToLotcheckIssue();
        submissionLotcheckIssues.clickMenuOfIssue(testcase_05)
                .selectViewIssueDetailsOfIssue(testcase_05);
        submissionLotcheckIssueDetails.performPublishIssue("Bug");

        submissionLotcheckIssueDetails.clickBackToLotcheckIssue();
        submissionLotcheckIssues.clickMenuOfIssue(testcase_06)
                .selectViewIssueDetailsOfIssue(testcase_06);
        submissionLotcheckIssueDetails.performPublishIssue("Bug");

        submissionLotcheckIssueDetails.clickBackToLotcheckIssue();
        submissionLotcheckIssues.clickMenuOfIssue(testcase_07)
                .selectViewIssueDetailsOfIssue(testcase_07);
        submissionLotcheckIssueDetails.performPublishIssue("Bug");

    }

    @Test()
    @Order(35)
    public void loginNDPAndNavigateToIssuePageOfReleaseDashboard_Step(IJUnitTestReporter testReport) {
        NDPDevelopmentHome developmentHome = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReport);
        developmentHome.clickMyProducts()
                .clickProductByName(productName)
                .clickReleases()
                .selectInitialRelease()
                .nav()
                .clickIssues();
    }

    @Test()
    @Order(36)
    public void verifyIssuePageAndLotcheckIssue_Expected(IJUnitTestReporter testReport) {
        NDPReleaseInfoIssuePage issuePage = browser.getPage(NDPReleaseInfoIssuePage.class, testReport);
        issuePage.expandUnresolve();
        int num = issuePage.getNumberOfCurrentUnresolvedLotcheckIssue();
        assertEquals(7, num, "Seven Lotcheck Issues will be listed");
    }

    @Test()
    @Order(37)
    public void sendResolutionForSevenLotcheckIssue_Step(IJUnitTestReporter testReport) {
        NDPReleaseInfoIssuePage issuePage = browser.getPage(NDPReleaseInfoIssuePage.class, testReport);
        int numUnresolved = issuePage.getNumberOfCurrentUnresolvedLotcheckIssue();
        int j = 0;
        for (int i = 0; i < numUnresolved - 1; i++) {
            issuePage.expandUnresolvedLotcheckIssue(j);
            String nameIssue = issuePage.getNameUnresolvedLotcheckIssue(i);
            if (nameIssue.contains("Permanent Waiver")) {
                issuePage.selectResolution(" Request Waiver ")
                        .selectDesiredApprovalTypeAtSecondUnresolvedRequiredApproval()
                        .enterReasonForLotcheckIssue(TextConstants.enterReasonForRequest)
                        .clickSendResolutionOfFirstLotcheckIssue()
                        .expandUnresolve();
            }
            if (nameIssue.contains("Temporary Waiver")) {
                issuePage.selectResolution(" Request Waiver ")
                        .selectDesiredApprovalTypeAtFirstUnresolvedRequiredApproval()
                        .enterReasonForLotcheckIssue(TextConstants.enterReasonForRequest)
                        .clickSendResolutionOfFirstLotcheckIssue()
                        .expandUnresolve();
            }
            if (nameIssue.contains("Will Fix in ROM")) {
                issuePage.selectResolution(" Will Fix in ROM ")
                        .clickSendResolutionOfFirstLotcheckIssue()
                        .expandUnresolve();
                i--;
            }
            if (nameIssue.contains("Closed") || nameIssue.contains("Need Response")) {
                browser.refresh();
                issuePage.expandUnresolve();
                j++;
            }
        }
    }

    @Test()
    @Order(38)
    public void verifyStatusOfLotcheckIssue_Expected(IJUnitTestReporter testReport) {
        NDPReleaseInfoIssuePage issuePage = browser.getPage(NDPReleaseInfoIssuePage.class, testReport);
        issuePage.expandResolve();
        assertEquals("Will Fix in ROM", issuePage.getStatusResolvedOfLotcheckIssue(0), "The Lotcheck Issue resolved as \"Will Fix in ROM\" will have the status \"Will Fix in ROM\"");
        issuePage.expandUnresolve();
        int numUnresolved = issuePage.getNumberOfCurrentUnresolvedLotcheckIssue();
        for (int i = 0; i < numUnresolved; i++) {
            String nameIssue = issuePage.getNameUnresolvedLotcheckIssue(i);
            if (nameIssue.contains("Permanent Waiver")) {
                assertEquals(" Permanent Waiver Requested ", issuePage.getStatusUnresolvedOfLotcheckIssue(i), "Status correct");
            }
            if (nameIssue.contains("Temporary Waiver")) {
                assertEquals(" Temporary Waiver Requested ", issuePage.getStatusUnresolvedOfLotcheckIssue(i), "Status correct");
            }
            if (nameIssue.contains("Closed") || nameIssue.contains("Need Response")) {
                assertEquals(" Needs Response ", issuePage.getStatusUnresolvedOfLotcheckIssue(i), "Status correct");
            }
        }
    }

    @Test()
    @Order(39)
    public void approvalForRequestedWaiver_Step(IJUnitTestReporter testReport) {
        NDPReleaseInfoReleaseListNav nav = browser.getPage(NDPReleaseInfoReleaseListNav.class, testReport);
        nav.clickOnUserNameMenu().clickOnSignOutButton();
        //Approval Issue TaskRequested
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        licensingWaiverRequestsTab.waitingForResultTableLoaded()
                .enterSearchingBox(gameCode)
                .clickOnMagnifyingIconButton()
                .waitingForResultTableLoaded();
        int numberOfRequestByPendingStatus = licensingWaiverRequestsTab.collectNumberOfRequestByPendingStatus();
        System.out.println(numberOfRequestByPendingStatus);
        for (int i = 0; i < numberOfRequestByPendingStatus; i++) {
            developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton().clickOnLicensingWaiverRequestsTab()
                    .waitingForResultTableLoaded()
                    .enterSearchingBox(gameCode)
                    .clickOnMagnifyingIconButton()
                    .waitingForResultTableLoaded()
                    .openLicensingWaiverRequestByPendingStatus()
                    .selectAssignToMeButton()
                    .selectOKButtonInAssignToMeModal()
                    .selectEditButton()
                    .enterResponseForApprovalOrReject(responseForApprovalOrReject)
                    .selectSaveButton()
                    .selectApproveRequestButton()
                    .selectOKButtonInApproveRequestModal()
                    .waitingForApproveSuccessful();
        }
        NDPViewTaskPage viewTaskPage = browser.getPage(NDPViewTaskPage.class, testReport);
        viewTaskPage.clickUserMenuDropdownList().clickSignOutButton();
    }

    @Test()
    @Order(40)
    public void verifyLotcheckIssueAfterApprovalForRequestedWaiver_Expected(IJUnitTestReporter testReport) {
        NDPReleaseInfoIssuePage issuePage = browser.getPage(NDPHomePage.class, testReport).clickSignInPage()
                .signIn(ndpUser)
                .clickMyProducts()
                .clickProductByName(productName)
                .clickReleases()
                .selectInitialRelease()
                .nav()
                .clickIssues();
        issuePage.expandResolve();
        int numResolved = issuePage.getNumberOfCurrentResolvedLotcheckIssue();
        for (int i = 0; i < numResolved; i++) {
            String nameIssue = issuePage.getNameResolvedLotcheckIssue(i);
            if (nameIssue.contains("Permanent Waiver")) {
                assertEquals("Permanent Waiver Approved", issuePage.getStatusResolvedOfLotcheckIssue(i), "Status correct");
            }
            if (nameIssue.contains("Temporary Waiver")) {
                assertEquals("Temporary Waiver Approved", issuePage.getStatusResolvedOfLotcheckIssue(i), "Status correct");
            }
        }
    }

    @Test()
    @Order(41)
    public void loginSLCMSAndNavigateLotcheckIssuePage_Step(IJUnitTestReporter testReport) {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickTesterAssignment(LotcheckRegion.NCL);
        CommonAction.findProductInLCQueue(lotcheckLandingPage, 30, LotcheckRegion.NCL.toString(), initialCode);
        lotcheckQueuePage.clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        lotcheckReportSubmissionOverview.clickLotchekIssueTab();
    }

    @Test()
    @Order(42)
    public void verifyStatusOfLotcheckIssueInLotcheckIssuePage_Expected(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues submissionLotcheckIssues = browser.getPage(LotcheckReportSubmissionLotcheckIssues.class, testReport);
        assertAll(() -> assertEquals(1, submissionLotcheckIssues.getNumberOfLotcheckIssueResolvedWillFixInRom(), "the number of will fix in ROM status correct"),
                () -> assertEquals(4, submissionLotcheckIssues.getNumberOfLotcheckIssueResolvedSuccess(), "the number of resolved status correct"),
                () -> assertEquals(2, submissionLotcheckIssues.getNumberOfLotcheckIssueUnresolved(), "the number of need response status correct"));
    }

    @Test()
    @Order(43)
    public void viewIssueDetailOfTestCase1_Step(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues submissionLotcheckIssues = browser.getPage(LotcheckReportSubmissionLotcheckIssues.class, testReport);
        submissionLotcheckIssues.clickMenuOfIssue(testcase_01)
                .selectViewIssueDetailsOfIssue(testcase_01);
    }

    @Test()
    @Order(44)
    public void verifyIssueInIssueDetailPage_Expected(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssueDetails issueDetails = browser.getPage(LotcheckReportSubmissionLotcheckIssueDetails.class, testReport);
        String status = issueDetails.getStatusOfIssue();
        String description = issueDetails.getDescriptionBug();
        assertAll(() -> assertEquals("PERMANENT WAIVER APPROVED", status, "status correct"),
                () -> assertEquals("> test\n" + "Enter description", description, "The line break is displayed"));
    }

    @Test()
    @Order(45)
    public void viewIssueDetailOfTestCaseClosedAndSelectClosed_Step(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues submissionLotcheckIssues = browser.getPage(LotcheckReportSubmissionLotcheckIssueDetails.class, testReport).clickBackToLotcheckIssue();
        submissionLotcheckIssues.clickMenuOfIssue(testcase_04)
                .selectViewIssueDetailsOfIssue(testcase_04)
                .clickPartnerThreadTab()
                .selectPartnerThreadStatus("Closed")
                .selectReplyToPartner()
                .selectIssueDetailTab();

    }

    @Test()
    @Order(46)
    public void verifyStatusOfIssueClosedInIssueDetailPage_Expected(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssueDetails issueDetails = browser.getPage(LotcheckReportSubmissionLotcheckIssueDetails.class, testReport);
        browser.refresh();
        String status = issueDetails.getStatusOfIssue();
        assertEquals("CLOSED", status, "status correct");
        LotcheckReportSubmissionLotcheckIssues submissionLotcheckIssues = issueDetails.clickBackToLotcheckIssue();
        assertEquals("CLOSED", submissionLotcheckIssues.getStatusOfIssueByName(testcase_04), "status corect");
        //verify on NDP
        NDPReleaseInfoIssuePage issuePage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport)
                .clickProductByName(productName)
                .clickReleases()
                .selectInitialRelease()
                .nav()
                .clickIssues()
                .expandResolve();
        int numResolved = issuePage.getNumberOfCurrentResolvedLotcheckIssue();
        for (int i = 0; i < numResolved; i++) {
            String nameIssue = issuePage.getNameResolvedLotcheckIssue(i);
            if (nameIssue.contains("Closed")) {
                assertEquals("Closed", issuePage.getStatusResolvedOfLotcheckIssue(i), "Status correct");
            }
        }
    }

    @Test()
    @Order(47)
    public void editTemporaryWaiverApprovedTestCase_Step(IJUnitTestReporter testReport) {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickTesterAssignment(LotcheckRegion.NCL);
        CommonAction.findProductInLCQueue(lotcheckLandingPage, 30, LotcheckRegion.NCL.toString(), initialCode);
        lotcheckQueuePage.clickPlannedTestDateNotSet();
        LotcheckReportSubmissionLotcheckIssues submissionLotcheckIssues = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00")
                .clickLotchekIssueTab();
        submissionLotcheckIssues.clickMenuOfIssue(testcase_02)
                .selectViewIssueDetailsOfIssue(testcase_02)
                .clickMenuAtIssueDetails()
                .selectEditDetailsEN()
                .editTitleEN("Temporary Waiver_edit")
                .clickSaveButton();
    }

    @Test()
    @Order(48)
    public void verifyWarningMessageInPartnerThreadTab_Expected(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssueDetails issueDetails = browser.getPage(LotcheckReportSubmissionLotcheckIssueDetails.class, testReport);
        LotcheckReportSubmissionLotcheckIssuePartnerThread partnerThread = issueDetails.clickPartnerThreadTab();
        String messageEN = partnerThread.getContentWarningMessage();
        partnerThread.clickMyAccount().selectLanguage(TextConstants.japanese).clickSaveButton();
        WaitUtils.idle(5000);
        String messageJP = partnerThread.getContentWarningMessage();
        assertAll(() -> assertEquals(TextConstants.warningMessagePartnerThreadEN, messageEN, "message correct"),
                () -> assertEquals(TextConstants.warningMessagePartnerThreadJP, messageJP, "message correct"));

    }

    @Test()
    @Order(49)
    public void setTestPlanToInTestingQueue_Step(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssuePartnerThread partnerThread = browser.getPage(LotcheckReportSubmissionLotcheckIssuePartnerThread.class, testReport);
        partnerThread.clickMyAccount().selectLanguage(TextConstants.english).clickSaveButton();
        WaitUtils.idle(5000);
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickTesterAssignment(LotcheckRegion.NCL);
        CommonAction.findProductInLCQueue(lotcheckLandingPage, 30, LotcheckRegion.NCL.toString(), initialCode);
        LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
        lotcheckTestPlanRow.selectAssignmentComplete().clickConfirm();

        lotcheckLandingPage.navigationToTestSetupReadyQueue(LotcheckRegion.NCL.toString());
        lotcheckQueuePage.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        lotcheckTestPlanRow.selectStartTestSetupProd().clickConfirm();

        lotcheckLandingPage.navigationToInTestSetupQueue(LotcheckRegion.NCL.toString());
        lotcheckQueuePage.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        lotcheckTestPlanRow.selectSetupCompleteProd().clickConfirm();

    }

    @Test()
    @Order(50)
    public void verifyTestPlanInTestingQueue_Expected(IJUnitTestReporter testReport) {
        LotcheckLandingPage lotcheckLandingPage = browser.getPage(LotcheckLandingPage.class, testReport);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToInTestingQueue(LotcheckRegion.NCL.toString());
        lotcheckQueuePage.searchByInitialCode(initialCode);
        String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
        assertEquals("1", numberValue, "the test plan is advanced to the In Testing queue.");
    }

    @Test()
    @Order(51)
    public void navigateToTestRailAndSetStatusFailed_Step(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage managerDashboardPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport)
                .navTopTestRail()
                .navigateToManagerDashboard()
                .checkErrorPopup()
                .actionFill()
                .enterTestPlanNameFilter(initialCode)
                .selectStatusUntested()
                .selectStatusPassed()
                .selectStatusFailed()
                .selectStatusReviewing()
                .selectStatusNotPerformed()
                .clickOnApplyFiltersButton();

        managerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan()
                .clickOnSetStatusButton()
                .selectStatusOnSetStatusModal("Failed")
                .clickOnUpdateStatusButtonOnSetStatusModal()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .expandAllTestRuns();
    }

    @Test()
    @Order(52)
    public void verifyTestPlanStatusIsFailed_Expected(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage managerDashboardPage = browser.getPage(TestRailManagerDashboardPage.class, testReport);
        String percentOfTestPlan = managerDashboardPage.getPercentOfTestPlan();
        Boolean checkStatusOfAllTestcases = managerDashboardPage.checkStatusOfAllTestcases("Failed");
        assertAll(() -> assertEquals(TextConstants.percentCompleted, percentOfTestPlan, "Verify set status for all testcase successfully"),
                () -> assertTrue(checkStatusOfAllTestcases, "Verify all testcase set status is Failed"));
    }

    @Test()
    @Order(53)
    public void setNGFinalJudgment_Step(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage managerDashboardPage = browser.getPage(TestRailManagerDashboardPage.class, testReport);
        managerDashboardPage.actionFill()
                .clickOnApplyFiltersButton()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnPadlockButtonOfTheFirstTestPlan()
                .selectTestPlanJudgment("NG")
                .clickOnYesButtonOnConfirmationModal();
    }

    @Test()
    @Order(54)
    public void verifyTestPlanDisappearAfterNGFinalJudgment_Expected(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage managerDashboardPage = browser.getPage(TestRailManagerDashboardPage.class, testReport);
        managerDashboardPage.actionFill().clickOnApplyFiltersButton();
        Boolean testPlanIsNotVisible = managerDashboardPage.testplanIsNotVisible(gameCode);
        assertTrue(testPlanIsNotVisible, TextConstants.testplanIsNotVisible);
    }

    @Test()
    @Order(55)
    public void approveJudgmentForTestPlan_Step(IJUnitTestReporter testReport) {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToJudgmentApprovalQueue(LotcheckRegion.NCL.toString());
        CommonAction.approvalJudgmentStatus(lotcheckQueuePage, initialCode, 30, "NG", false, "1");
    }

    @Test()
    @Order(56)
    public void verifyTestPlanInFinishQueue_Expected(IJUnitTestReporter testReport) {
        LotcheckLandingPage lotcheckLandingPage = browser.getPage(LotcheckLandingPage.class, testReport);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToFinishedQueue(LotcheckRegion.NCL.toString());
        lotcheckQueuePage.searchByInitialCode(initialCode);
        String status = lotcheckQueuePage.getJudgmentStatus();
        assertEquals("NG", status, "the NG judgement will be approved");
        lotcheckQueuePage.clickOnUserNameMenu().clickOnLogoutButton();
    }

    @Test()
    @Order(57)
    public void loginNDPAsProductAdminAndNavigateReleaseDashboard_Step(IJUnitTestReporter testReport) {
        NDPReleaseInfoIssuePage issuePage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport)
                .clickProductByName(productName)
                .clickReleases()
                .selectInitialRelease()
                .nav()
                .clickIssues()
                .expandUnresolve();
    }

    @Test()
    @Order(58)
    public void requestTemporaryWaiverForNeedResponseIssue_Step(IJUnitTestReporter testReport) {
        NDPReleaseInfoIssuePage issuePage = browser.getPage(NDPReleaseInfoIssuePage.class, testReport);
        issuePage.expandUnresolvedLotcheckIssue(0)
                .selectResolution(" Request Waiver ")
                .selectDesiredApprovalTypeAtFirstUnresolvedRequiredApproval()
                .enterReasonForLotcheckIssue(TextConstants.enterReasonForRequest)
                .clickSendResolutionOfFirstLotcheckIssue();
    }

    @Test()
    @Order(59)
    public void approvalForRequestedTemporaryWaiver_Step(IJUnitTestReporter testReport) {
        NDPReleaseInfoReleaseListNav nav = browser.getPage(NDPReleaseInfoReleaseListNav.class, testReport);
        nav.clickOnUserNameMenu().clickOnSignOutButton();
        //Approval Issue TaskRequested
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        licensingWaiverRequestsTab.waitingForResultTableLoaded()
                .enterSearchingBox(gameCode)
                .clickOnMagnifyingIconButton()
                .waitingForResultTableLoaded();
        int numberOfRequestByPendingStatus = licensingWaiverRequestsTab.collectNumberOfRequestByPendingStatus();
        System.out.println(numberOfRequestByPendingStatus);
        for (int i = 0; i < numberOfRequestByPendingStatus; i++) {
            developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton().clickOnLicensingWaiverRequestsTab()
                    .waitingForResultTableLoaded()
                    .enterSearchingBox(gameCode)
                    .clickOnMagnifyingIconButton()
                    .waitingForResultTableLoaded()
                    .openLicensingWaiverRequestByPendingStatus()
                    .selectAssignToMeButton()
                    .selectOKButtonInAssignToMeModal()
                    .selectEditButton()
                    .enterResponseForApprovalOrReject(responseForApprovalOrReject)
                    .selectSaveButton()
                    .selectApproveRequestButton()
                    .selectOKButtonInApproveRequestModal()
                    .waitingForApproveSuccessful();
        }
        NDPViewTaskPage viewTaskPage = browser.getPage(NDPViewTaskPage.class, testReport);
        viewTaskPage.clickUserMenuDropdownList().clickSignOutButton();
    }

    @Test()
    @Order(60)
    public void submitTheReleaseToLotcheckAgain_Step(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPHomePage.class, testReport).clickSignInPage()
                .signIn(ndpUser)
                .clickMyProducts()
                .clickProductByName(productName)
                .clickReleases()
                .selectInitialRelease();
        //complete release info
        releaseInfoPage.selectExpectedSubmissionDate().chooseDateInCalender(2);
        releaseInfoPage.save();
        //complete guideline
        releaseInfoPage.nav().clickGuidelines().selectCompliesWithAllOfTheAboveGuidelines().clickOnSaveButton();
        //complete rom upload
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = releaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
        //submit
        releaseInfoPage.nav().clickIssues().nav().clickSubmitButton()
                .clickSubmit();
    }

    @Test()
    @Order(61)
    public void setLanguageInSLCMS_Step(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
        lotcheckLandingPage.clickOnUserNameMenu().clickMyAccount();
        String labelLaguage = lotcheckLandingPage.getLabelLanguage();
        lotcheckLandingPage.clickCancelButtonOnEditAccountPreferences();
        if (!labelLaguage.equals("Language")) {
            lotcheckLandingPage.clickOnUserNameMenu().clickMyAccount().selectUserLanguage("English").clickSaveButton();
        }
        currentPage = lotcheckLandingPage;
    }

    @Test()
    @Order(63)
    public void viewIssueDetailsPageOfTemporaryWaiverApproved_Step(IJUnitTestReporter testReport) throws InterruptedException {
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickTesterAssignment(LotcheckRegion.NCL);
        int i = 0;
        while (i < 50) {
            lotcheckLandingPage.navigationToTesterAssignmentQueue("NCL").searchByInitialCode(initialCode);
            String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
            i++;
            if (numberValue.equalsIgnoreCase("1")) {
                break;
            }
        }
        lotcheckQueuePage.clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview submissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues = submissionOverview.navBar().clickLotcheckIssues();
        reportSubmissionLotcheckIssues.clickXpandVersion();
        LotcheckReportSubmissionLotcheckIssueDetails issueDetails = reportSubmissionLotcheckIssues.clickMenuOfIssueName("Temporary Waiver").clickViewIssueDetailOfIssueName("Temporary Waiver");
        warningMessage = issueDetails.getWarningIssue();
        currentPage = issueDetails;
    }

    @Test()
    @Order(64)
    public void checkWarningMessage_Expected(IJUnitTestReporter testReport) throws InterruptedException {
        assertAll(
                () -> assertEquals(messageExpected, warningMessage, "Issue Details page is opened with warning message at the top of page.")
        );
    }

    @Test()
    @Order(65)
    public void selectTheActionsMenuInTheIssueDetailsTab_Step(IJUnitTestReporter testReport) throws InterruptedException {
        LotcheckReportSubmissionLotcheckIssueDetails issueDetails = (LotcheckReportSubmissionLotcheckIssueDetails) currentPage;
        publishToPublicWillNotBeAvailable = issueDetails.clickMenu().checkPublishToPublicDisplay();
        resetIssueWillNotBeAvailable = issueDetails.clickMenu().checkResetIssueDisplay();
        editDetailENWillNotBeAvailable = issueDetails.clickMenuAtIssueDetails().checkEditDetailsENDisplay();
        currentPage = issueDetails;
    }

    @Test()
    @Order(66)
    public void openTheActionsMenu_Expected(IJUnitTestReporter testReport) throws InterruptedException {
        assertAll(
                () -> assertFalse(publishToPublicWillNotBeAvailable, "action will be unavailable."),
                () -> assertFalse(resetIssueWillNotBeAvailable, "will not be available."),
                () -> assertFalse(editDetailENWillNotBeAvailable, "action will be hidden.")
        );
    }

    @Test()
    @Order(67)
    public void selectSelectPartnerThreadTab_Step(IJUnitTestReporter testReport) throws InterruptedException {
        LotcheckReportSubmissionLotcheckIssueDetails issueDetails = (LotcheckReportSubmissionLotcheckIssueDetails) currentPage;
        LotcheckReportSubmissionLotcheckIssuePartnerThread partnerThread = issueDetails.clickPartnerThreadTab();
        licTypeReplyPartner = partnerThread.checkLICTypeDisable();
        partnerThreadStatusReplyPartner = partnerThread.checkPartnerThreadStatusDisable();
        currentPage = partnerThread;
    }

    @Test()
    @Order(68)
    public void openThePartnerThreadTab_Expected(IJUnitTestReporter testReport) throws InterruptedException {
        assertAll(
                () -> assertTrue(licTypeReplyPartner, "The LCI Type in the Reply to Partner form will be disabled."),
                () -> assertTrue(partnerThreadStatusReplyPartner, "The Partner Thread Status drop-down in the Reply to Partner form will be disabled.")
        );
    }

    @Test()
    @Order(69)
    public void openTheActionsMenuOnPartnerThreadTab_Step(IJUnitTestReporter testReport) throws InterruptedException {
        LotcheckReportSubmissionLotcheckIssuePartnerThread partnerThread = (LotcheckReportSubmissionLotcheckIssuePartnerThread) currentPage;
        publishToPublicIsUnavailable = partnerThread.nav().clickMenu().checkPublishToPublicDisplay();
        resetIssueIsUnavailable = partnerThread.nav().clickMenu().checkResetIssueDisplay();
        currentPage = partnerThread;
    }

    @Test()
    @Order(70)
    public void checkActionInActionMenuThePartnerThreadTab_Expected(IJUnitTestReporter testReport) throws InterruptedException {
        assertAll(
                () -> assertFalse(publishToPublicIsUnavailable, "publish to public action will not available."),
                () -> assertFalse(resetIssueIsUnavailable, "Reset Issue action will not available.")
        );
    }

    @Test()
    @Order(71)
    public void selectTheLotcheckThreadTab_Step(IJUnitTestReporter testReport) throws InterruptedException {
        LotcheckReportSubmissionLotcheckIssuePartnerThread partnerThread = (LotcheckReportSubmissionLotcheckIssuePartnerThread) currentPage;
        LotcheckReportSubmissionLotcheckIssueThread issueThread = partnerThread.nav().clickThreadIssueTab();
        lotcheckThreadStatusReplyPartner = issueThread.checkLotcheckThreadStatusDisable();
        currentPage = issueThread;
    }

    @Test()
    @Order(72)
    public void checkLotcheckThreadStatus_Expected(IJUnitTestReporter testReport) throws InterruptedException {
        assertAll(
                () -> assertTrue(lotcheckThreadStatusReplyPartner, "Lotcheck Thread Status drop-down in the Reply to Lotcheck Form is disabled.")
        );
    }

    @Test()
    @Order(73)
    public void setTheJudgmentForNewSubmissionToOK_Step(IJUnitTestReporter testReport) throws InterruptedException {
        TestRailManagerDashboardPage testRailManagerDashboardPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport)
                .navTopTestRail().navigateToManagerDashboard();
        CommonAction.setStatusOnTestrail(testRailManagerDashboardPage, initialCode, "Passed", TextConstants.percentCompleted, TextConstants.OK);
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickJudgment(LotcheckRegion.NCL);
        CommonAction.approvalJudgmentStatus(lotcheckQueuePage, initialCode, 100, TextConstants.OK, false, "1");
    }

    @Test()
    @Order(74)
    public void createNewRelease_Step(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPCreateReleasePage ndpCreateReleasePage = ndpProductDashboardReleasesTab.clickCreateNewReleaseButton();
        ndpCreateReleasePage
                .enterDisplayVersion("1.0.1")
                .selectExpectedSubmissionDate(5)
                .selectExpectedReleaseDate(20)
                .selectFreeToPlay("NO")
                .enterDescribeTheFeaturesPlanned(TextConstants.enterDescribeTheFeaturesPlanned)
                .enterDescribeTheBugsIssuesPlanned(TextConstants.enterDescribeTheBugsIssuesPlanned)
                .clickCreateButton();
        titleCreatePage = ndpCreateReleasePage.getPageTitle();
    }

    @Test()
    @Order(75)
    public void createNewRelease_Expected(IJUnitTestReporter testReport) throws InterruptedException {
        assertAll(
                () -> assertEquals("Release 01", titleCreatePage, "The Create New Release page will be displayed.")
        );
    }

    @Test()
    @Order(76)
    public void enterValidInformationAndSubmitPatch_Step(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectPatchRelease();
        // release info
        ndpReleaseInfoPage.nav().clickReleaseInfo();
        CommonAction.selectROMDiffAllRegion(ndpReleaseInfoPage, TextConstants.selectRomDifferBetweenRegion, TextConstants.english);
        //create Feature

        NDPReleaseInfoFeaturesPage releaseFeatures = ndpReleaseInfoPage.nav().clickFeatures();
        CommonAction.getPatchFeatures(ndpReleaseInfoPage, TextConstants.allOff, TextConstants.allOff, TextConstants.allOff, TextConstants.allOff);
        //refresh or click continue is required to see features as completed
        releaseFeatures.clickContinue();
        // assertTrue(ndpReleaseInfoPage.nav().isFeaturesCompleted());
        //Create GuildLine
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = ndpReleaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoGuidelinesPage.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();
        //create ROM
        String originalApp = "Application_" + applicationID + "_v00.nsp";
        //create ROM
        romBrowser = BrowserManager.getNewInstance();
        currentPage = romBrowser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN);
        ROMLoginPage romLoginPage = (ROMLoginPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom.reloadPage()
                .selectRequester(TextConstants.test_User)
                .selectType(TextConstants.patch_Release)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .enterProductName(productName)
                .enterApplicationId(applicationID)
                .selectPublishingRelationShip(TextConstants.third_Party_Type)
                .enterDisplayVersion("1.0.1")
                .enterReleaseVersion("01")
                .enterOriginalApp(originalApp)
                .selectCERORating("CERO: Not required")
                .clickOnCreateButton()
                .waitIsLoaded();
        fileRomName = romCreateRom.getRomNameTable(applicationID);
        romCreateRom.clickOnROMFileOnROMTable(applicationID);
        WaitUtils.idle(10000);
        romCreateRom.clickOnUserNameMenu().clickOnLogoutButton();
        BrowserManager.closeBrowser(romBrowser);
        //upload ROM
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = ndpReleaseInfoPage.nav().clickROMUpload();
        ndpReleaseInfoROMUpload.selectRomFileButton().selectFileInPC(fileRomName);
        ndpReleaseInfoROMUpload.clickUpload()
                .clickUploadNow()
                .waitForUploadComplete(TextConstants.romUpload);
        ndpReleaseInfoROMUpload.deleteFile(fileRomName);
        //Request Issue
        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
        issuePage.openRequiredApprovalTab();
        issuePage.performAllIssueAtApprovalTab(false);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
        //Approval Issue TaskRequested
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(licUser).clickOnMyTaskMenu();
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, gameCode);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
        //submit patch
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser).clickMyProducts();
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        myProductsPage.clickProductByName(productName);
        ndpProductDashboardPage.clickReleases();
        ndpProductDashboardReleasesTab.selectPatchRelease();
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.enterDeveloperComment).clickSubmit();
        WaitUtils.idle(3000);
    }

    @Test()
    @Order(77)
    public void submitThePatchReleaseToLotcheck_Step(IJUnitTestReporter testReport) throws Exception {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickTesterAssignment(LotcheckRegion.NCL);
        int i = 0;
        while (i < 50) {
            lotcheckLandingPage.navigationToTesterAssignmentQueue(LotcheckRegion.NCL.toString()).searchByInitialCode(initialCode);
            String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
            i++;
            if (numberValue.equalsIgnoreCase("1")) {
                break;
            }
        }
        lotcheckQueuePage.clickPlannedTestDateNotSet();
        testPlanIsSubmitToLotcheck = lotcheckQueuePage.isDisplayedTestPlan(gameCode);
    }

    @Test()
    @Order(78)
    public void submitThePatchReleaseToLotcheck_Expected(IJUnitTestReporter testReport) throws InterruptedException {
        assertAll(
                () -> assertFalse(testPlanIsSubmitToLotcheck, "The Patch release will be submitted to Lotcheck.")
        );
    }

    @Test()
    @Order(79)
    public void openMyTodo_Step(IJUnitTestReporter testReport) throws InterruptedException {
        TestRailManagerDashboardPage testRailManagerDashboardPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport)
                .navTopTestRail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(initialCode)
                .selectStatusUntested().selectStatusPassed();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan()
                .clickAssignTo()
                .selectSetValueAssignToRadio()
                .selectUser("Me")
                .clickOk()
                .waitingLoadingTestSuite();
        WaitUtils.idle(20000);
        TestRailMyToDoPage testRailMyTodoTab = testRailManagerDashboardPage.topNav().navigateToMyToDo();
        testRailMyTodoTab.actionFill().enterTestPlanNameFilter(initialCode);
        testRailMyTodoTab.actionFill().clickOnApplyFiltersButton();
        testRailMyTodoTab
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .expandAllTestRuns();
        currentPage = testRailMyTodoTab;
        testPlanIsAppear = testRailMyTodoTab.checkTestPlanAppearInTestRailMyToDoTab();
    }

    @Test()
    @Order(80)
    public void theMyTodoPageIsDisplayedAndAllSubmittedTestPlans_Expected(IJUnitTestReporter testReport) throws InterruptedException {
        assertAll(
                () -> assertTrue(testPlanIsAppear, "Test case is display in TR My To do Tab")
        );
    }

    @Test()
    @Order(81)
    public void selectATitleForATestCaseAtHadAnIssueReportedInTheInitialRelease_Step(IJUnitTestReporter testReport) {
        TestRailMyToDoPage tesRailMyTodoTab = (TestRailMyToDoPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailDetailTestcaseDialog testRailDetailTestcaseDialog = tesRailMyTodoTab.clickOnTheFirstTestRun()
                .clickOnTheFirstTestcaseByName(testcase_02);
        currentPage = testRailDetailTestcaseDialog;
        modalWindowViewDetail = tesRailMyTodoTab.testCaseDetailIsDisplayed();
    }

    @Test()
    @Order(82)
    public void theTestCaseViewModalWindowWillBeDisplayed_Expected(IJUnitTestReporter testReport) throws InterruptedException {
        assertAll(
                () -> assertTrue(modalWindowViewDetail, "TThe Test Case View modal window will be displayed")
        );
    }

    @Test()
    @Order(83)
    public void submitBugEnterADescription_Step(IJUnitTestReporter testReport) {
        TestRailDetailTestcaseDialog testRailDetailTestcaseDialog = (TestRailDetailTestcaseDialog) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        testRailDetailTestcaseDialog.clickSubmitBug().enterDescription(descriptionBug).clickSubmit().clickOk();
    }

    @Test()
    @Order(84)
    public void navigateToTheLotcheckIssuesPage_Step(IJUnitTestReporter testReport) throws InterruptedException {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickTesterAssignment(LotcheckRegion.NCL);
        int i = 0;
        while (i < 50) {
            lotcheckLandingPage.navigationToTesterAssignmentQueue("NCL").searchByInitialCode(initialCode);
            String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
            i++;
            if (numberValue.equalsIgnoreCase("1")) {
                break;
            }
        }
        lotcheckQueuePage.clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview submissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("01");
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues = submissionOverview.navBar().clickLotcheckIssues();
        recordIsDisplayCurrentIssue = reportSubmissionLotcheckIssues.recordIsDisplayInCurrentSubmissionIssue();
        reportSubmissionLotcheckIssues.clickXpandVersion();
        numberPermanentWaives = reportSubmissionLotcheckIssues.getNumberArchiveIssues("Permanent Waiver Approved");
        numberTemporaryWaive = reportSubmissionLotcheckIssues.getNumberArchiveIssues("Temporary Waiver Approved");
        numberWillFixInROM = reportSubmissionLotcheckIssues.getNumberArchiveIssues("Fixed");
        numberClosed = reportSubmissionLotcheckIssues.getNumberArchiveIssues("Closed");
        reportSubmissionLotcheckIssues.expandCollapsedIssueByIssueName(1, "Permanent Waiver");
        valueReasonForRequestPermanentWaiver1 = reportSubmissionLotcheckIssues.getReasonForRequest();
        valueResponseForApprovalOrRejectionPermanentWaiver1 = reportSubmissionLotcheckIssues.getResponseForApprovalOrRejection();
        reportSubmissionLotcheckIssues.expandCollapsedIssueByIssueName(1, "Permanent Waiver");
        WaitUtils.idle(1000);
        reportSubmissionLotcheckIssues.expandCollapsedIssueByIssueName(2, "Permanent Waiver");
        valueReasonForRequestPermanentWaiver2 = reportSubmissionLotcheckIssues.getReasonForRequest();
        valueResponseForApprovalOrRejectionPermanentWaiver2 = reportSubmissionLotcheckIssues.getResponseForApprovalOrRejection();
        reportSubmissionLotcheckIssues.expandCollapsedIssueByIssueName(2, "Permanent Waiver");
        WaitUtils.idle(1000);
        reportSubmissionLotcheckIssues.expandCollapsedIssueByIssueName(7, "Permanent Waiver");
        valueReasonForRequestPermanentWaiver3 = reportSubmissionLotcheckIssues.getReasonForRequest();
        valueResponseForApprovalOrRejectionPermanentWaiver3 = reportSubmissionLotcheckIssues.getResponseForApprovalOrRejection();
        reportSubmissionLotcheckIssues.expandCollapsedIssueByIssueName(7, "Permanent Waiver");
        reportSubmissionLotcheckIssues.expandCollapsedIssueByIssueName(6, "Temporary Waiver");
        valueReasonForRequestTemporaryWaiver = reportSubmissionLotcheckIssues.getReasonForRequest();
        valueResponseForApprovalOrRejectionTemporaryWaiver = reportSubmissionLotcheckIssues.getResponseForApprovalOrRejection();
    }

    @Test()
    @Order(85)
    public void checkCurrentIssueAndProductResolutionArchiveIssues_Expected(IJUnitTestReporter testReport) throws InterruptedException {
        assertAll(
                () -> assertTrue(recordIsDisplayCurrentIssue, "The Current Issues section will contain the one unresolved Lotcheck Issue from the Patch release"),
                () -> assertEquals(3, numberPermanentWaives, "three Permanent Waives"),
                () -> assertEquals(2, numberTemporaryWaive, "two Temporary Waive"),
                () -> assertEquals(1, numberWillFixInROM, "one Will Fix in ROM"),
                () -> assertEquals(1, numberClosed, "one Closed"),
                () -> assertEquals(TextConstants.enterReasonForRequest, valueReasonForRequestPermanentWaiver1, "value Reason For Request Permanent Waiver 1"),
                () -> assertEquals(responseForApprovalOrReject, valueResponseForApprovalOrRejectionPermanentWaiver1, "value Response For Approval Or Rejection Permanent Waiver 1"),
                () -> assertEquals(TextConstants.enterReasonForRequest, valueReasonForRequestPermanentWaiver2, "value Reason For Request Permanent Waiver 2"),
                () -> assertEquals(responseForApprovalOrReject, valueResponseForApprovalOrRejectionPermanentWaiver2, "value Response For Approval Or Rejection Permanent Waiver 2"),
                () -> assertEquals(TextConstants.enterReasonForRequest, valueReasonForRequestPermanentWaiver3, "value Reason For Request Permanent Waiver 3"),
                () -> assertEquals(responseForApprovalOrReject, valueResponseForApprovalOrRejectionPermanentWaiver3, "value Response For Approval Or Rejection Permanent Waiver 3"),
                () -> assertEquals(TextConstants.enterReasonForRequest, valueReasonForRequestTemporaryWaiver, "value Reason For Request Temporary Waiver"),
                () -> assertEquals(responseForApprovalOrReject, valueResponseForApprovalOrRejectionTemporaryWaiver, "value Response For Approval Or Rejection Temporary Waiver")
        );
    }

    @Test()
    @Order(86)
    public void navigateNDpReleaseDashboardForTheSubmission_Step(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectPatchRelease();
        currentPage = ndpReleaseInfoPage;
        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
    }

    @Test()
    @Order(87)
    public void navigateToLotcheckIssues_Step(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
        numberIssuesReported = issuePage.getNumberResolved();
    }

    @Test()
    @Order(88)
    public void checkReportedInTheInitialReleaseWillBeDisplayedUnder_Expected(IJUnitTestReporter testReport) throws InterruptedException {
        assertAll(
                () -> assertEquals("7", numberIssuesReported, "7 issues reported in the initial release will be displayed under Past Release Versions (7) section")
        );
    }

    @Test()
    @Order(89)
    public void ViewIssueForThePermenantWaiver_Step(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
        issuePage.clickViewIssue(1, "Permanent Waiver");
        valueReasonForRequestInNDP = issuePage.getReasonForRequest();
        valueResponseForApprovalOrRejectionInNDP = issuePage.getResponseForApprovalOrRejection();
    }

    @Test()
    @Order(90)
    public void checkContentIssueInNDP_Expected(IJUnitTestReporter testReport) throws InterruptedException {
        assertAll(
                () -> assertEquals(TextConstants.enterReasonForRequest, valueReasonForRequestInNDP, "The content of Reason for Request must be preserved as it was"),
                () -> assertEquals(responseForApprovalOrReject, valueResponseForApprovalOrRejectionInNDP, "The content of Reason for Approval or Rejection must be preserved as it was")
        );
    }
}
