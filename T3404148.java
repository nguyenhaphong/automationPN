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
import net.nintendo.automation.ui.models.lcms.LotcheckHomePage;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.ui.models.lcms.LotcheckQueuePage;
import net.nintendo.automation.ui.models.lcms.LotcheckTestPlanRow;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssueDetails;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssuePartnerThread;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssues;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.ndp.NDPCreateProductPage;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPLicensingWaiverRequestsTab;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.NDPSignInPage;
import net.nintendo.automation.ui.models.ndp.NDPViewTaskPage;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_internal.NDPInternalPage;
import net.nintendo.automation.ui.models.ndp.existing_internal.NDPProductSearchPage;
import net.nintendo.automation.ui.models.ndp.existing_internal.view_product_dashboard.NDPSettingTab;
import net.nintendo.automation.ui.models.ndp.existing_internal.view_product_dashboard.NDPViewProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPCreateReleasePage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardMessagesTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardProductInfoTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardReleasesTab;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPMessageBoardPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoAgeRating;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoFeaturesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoGuidelinesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoIssuePage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoROMUpload;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoReleaseListNav;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPConfirmationDialog;
import net.nintendo.automation.ui.models.rom.ROMCreateRom;
import net.nintendo.automation.ui.models.rom.ROMLoginPage;
import net.nintendo.automation.ui.models.testrail.TestRailAddProblemDescriptionDialog;
import net.nintendo.automation.ui.models.testrail.TestRailDashboardPage;
import net.nintendo.automation.ui.models.testrail.TestRailDetailTestcaseDialog;
import net.nintendo.automation.ui.models.testrail.TestRailLoginPage;
import net.nintendo.automation.ui.models.testrail.TestRailManagerDashboardPage;
import net.nintendo.automation.ui.models.testrail.TestRailMyToDoPage;
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

import static org.junit.jupiter.api.Assertions.*;


/**
 * Classname: T3404148
 * Version: 1.0.0
 * Purpose:
 */
@Tag("T3404148")
@ScenarioTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3404148 extends CommonBaseTest {
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
    private static String valueInitialCode;
    private static String initialCodeInSubmission;
    private static String testcase_01;
    private static String messageSubmitBugSuccess;
    private static boolean imageIsDisplayed;
    private static String widthOfImageInSLCMS;
    private static String heightOfImageInSLCMS;
    private static String widthOfImageInNDP;
    private static String heightOfImageInNDP;
    private static boolean imageUploadIsDisplayed;
    private static String widthOfImageUploadInSLCMS;
    private static String heightOfImageUploadInSLCMS;
    private static String fileRomNamePatch;
    private static String currentURL;

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
        currentURL = ndpHomePage.getURL();
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
    public void createAProductIncludeMPAROMWith10ChildROMsAndSubmitToLotcheck_Step1(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPCreateProductPage createProductPage = myProductsPage.createNewProduct();
        createProductPage.selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(TextConstants.third_Party_Type)
                .selectTargetDeliveryFormat(TextConstants.both_Target_Type)
                .selectDigitalJapanSalesRegion()
                .selectPhysicalJapanSalesRegion()
                .selectProductName(productName)
                .selectProductNameKana(TextConstants.productKana)
                .clickCreateButton();
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        CommonAction.issueGameCode(ndpProductDashboardPage, 80,"NO");

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
        //Create Test Scenarios
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
                .selectMPACheckbox()
                .selectNumberOfProgram("11")
                .selectTargetDeliveryFormat(TextConstants.both_Target_Type)
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
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser).clickMyProducts();
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        //enter test scenarios
        myProductsPage.clickProductByName(productName).clickReleases().selectInitialRelease().nav().clickTestScenarios().expandAllSections()
                .expandAllTestScenes()
                .enterSubjectForProgramSpecificationsHowToStartUpTheIncludedProgramsIncluded(TextConstants.commentTestScenario, 0)
                .enterExplainForProgramSpecificationsHowToStartUpTheIncludedProgramsIncluded(TextConstants.commentExplainProgram, 0)
                .clickOnSaveButton();
        WaitUtils.idle(5000);
        releaseInfoPage.nav().clickIssues();
        //submit product
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.enterDeveloperComment).selectSubmitButton().openNewTabAndCloseCurrentTab();

        NDPMyProductsPage myProductsPage1 = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        myProductsPage.clickProductByName(productName).clickReleases().selectInitialRelease();
        for (int i = 0; i < 5000; i++) {
            if (releaseInfoPage.getStatusValue().equals(TextConstants.submitToLotcheck)) {
                break;
            }
            WaitUtils.idle(3000);
            releaseInfoPage.nav().clickFeatures();
            releaseInfoPage.nav().clickReleaseInfo();
        }
        assertEquals(TextConstants.submitToLotcheck, releaseInfoPage.getStatusValue(), TextConstants.submitSuccess);
    }

    @Test
    @Order(2)
    public void reportABugForATestCaseInTestrailThenPublishBugToPartner_Step2(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN, testReport);
        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailDashboardPage testRailDashboardPage = testRailLoginPage.signInTestRail(testrailUser);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
        testRailManagerDashboardPage.waitingLoadingTestPlan()
                .actionFill()
                .enterTestPlanNameFilter(initialCode)
                .selectStatusUntested()
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .clickOnTheFirstTestSuite()
                .clickOnTheFirstTestRun();
        TestRailSubmitBugDialog submitBugDialog = testRailManagerDashboardPage.clickOnTheFirstTestcase().clickSubmitBug();
        submitBugDialog.addTitle("Submit Bug 1")
                .enterDescription(TextConstants.enterSubmitBug).clickSubmit();
        String messageSuccess = submitBugDialog.getMessageSuccessfully();
        submitBugDialog.clickOk().clickClose();
        assertEquals("Your Issue has been submitted successfully.", messageSuccess, "Verify submit bug successfully");
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReport);
        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickOverallLotcheck(LotcheckRegion.NCL);
        lotcheckQueuePage.searchByInitialCode(initialCode)
                .clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview reportSubmissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTestplanByTargetDelivery("DL");
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails = reportSubmissionOverview.navBar()
                .clickLotcheckIssues()
                .clickMenuOption()
                .selectViewIssueDetails();
        submissionLotcheckIssueDetails.clickMenu()
                .selectPublishToPublic()
                .clickPublish();
        String partnerThreadStatus = submissionLotcheckIssueDetails.getPartnerThreadStatus();
        WaitUtils.idle(3000);
        assertEquals("NEEDS RESPONSE", partnerThreadStatus, "Verify partner Thread Status after publish");
    }

    @Test
    @Order(3)
    public void createAProblemDescriptionAndReportABugOnThat_Step3(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport);
        TestRailDashboardPage testRailDashboardPage = (TestRailDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
        testRailManagerDashboardPage.waitingLoadingTestPlan()
                .actionFill()
                .enterTestPlanNameFilter(initialCode)
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite();
        TestRailAddProblemDescriptionDialog testRailAddProblemDescriptionDialog = testRailManagerDashboardPage.clickOnAddProblemDescriptionButton();
        testRailAddProblemDescriptionDialog.inputCaseTitle("Test")
                .clickSubmitButton();
        browser.refresh();
        testRailManagerDashboardPage.waitingLoadingTestPlan()
                .actionFill()
                .enterTestPlanNameFilter(initialCode)
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .clickOnTheProblemDescriptionTestSuite()
                .clickOnAllTestCases();
        TestRailSubmitBugDialog submitBugDialog = testRailManagerDashboardPage.clickOnTheTestcaseInProblemDescription().clickSubmitBug();
        submitBugDialog.addTitle("Submit Bug 2")
                .enterDescription(TextConstants.enterSubmitBug).clickSubmit();
        String messageSuccess = submitBugDialog.getMessageSuccessfully();
        submitBugDialog.clickOk().clickClose();
        assertEquals("Your Issue has been submitted successfully.", messageSuccess, "Verify submit bug successfully");
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickOverallLotcheck(LotcheckRegion.NCL);
        lotcheckQueuePage.searchByInitialCode(initialCode)
                .clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview reportSubmissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTestplanByTargetDelivery("DL");
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails = reportSubmissionOverview.navBar()
                .clickLotcheckIssues()
                .clickMenuOption()
                .selectViewIssueDetails();
        submissionLotcheckIssueDetails.clickMenu()
                .selectPublishToPublic()
                .clickPublish();
        String partnerThreadStatus = submissionLotcheckIssueDetails.getPartnerThreadStatus();
        WaitUtils.idle(3000);
        assertEquals("NEEDS RESPONSE", partnerThreadStatus, "Verify partner Thread Status after publish");
    }

    @Test
    @Order(4)
    public void loginAsThePartnerAndResolveBothIssueAsWillFixInRom_Step4(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage ndpMyProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPReleaseInfoIssuePage ndpReleaseInfoIssuePage = ndpMyProductsPage.clickProductByName(productName).clickReleases().selectInitialRelease().nav().clickIssues();
        ndpReleaseInfoIssuePage.expandUnresolve();
        int numUnresolved = ndpReleaseInfoIssuePage.getNumberOfCurrentUnresolvedLotcheckIssue();
        for (int i = 0; i < numUnresolved; i++) {
            ndpReleaseInfoIssuePage.clickExpandTestCase()
                    .clickSendResolutionButton();
            if (i < numUnresolved -1) {
                ndpReleaseInfoIssuePage.expandUnresolve();
            }
        }
        currentPage = ndpReleaseInfoIssuePage;
    }

    @Test
    @Order(5)
    public void cancelTheSubmission_Step5(IJUnitTestReporter testReport) {
        NDPReleaseInfoIssuePage ndpReleaseInfoIssuePage = (NDPReleaseInfoIssuePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpReleaseInfoIssuePage.nav().clickReleaseInfo();
        NDPConfirmationDialog ndpConfirmationDialog = ndpReleaseInfoPage.nav().clickCancelSubmitButton();
        ndpConfirmationDialog.clickOKInConfirm();
    }

    @Test
    @Order(6)
    public void submitReleaseToLotcheckAgain_Step6(IJUnitTestReporter testReport) throws Exception {
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
        //resubmit
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.enterDeveloperComment).selectSubmitButton().openNewTabAndCloseCurrentTab();

        NDPMyProductsPage myProductsPage1 = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        myProductsPage.clickProductByName(productName).clickReleases().selectInitialRelease();
        for (int i = 0; i < 5000; i++) {
            if (ndpReleaseInfoPage.getStatusValue().equals(TextConstants.submitToLotcheck)) {
                break;
            }
            WaitUtils.idle(3000);
            ndpReleaseInfoPage.nav().clickFeatures();
            ndpReleaseInfoPage.nav().clickReleaseInfo();
        }
        assertEquals(TextConstants.submitToLotcheck, ndpReleaseInfoPage.getStatusValue(), TextConstants.submitSuccess);
    }

    @Test
    @Order(7)
    public void openSubmissionSearchPageInInitialCodeFilterFieldInputWithSpaceCharacter_Step7(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickOverallLotcheck(LotcheckRegion.NCL);
        lotcheckQueuePage.inputInitialCodeFilter(" " + initialCode + " ");
        valueInitialCode = lotcheckQueuePage.getValueInputInSearchInitialCode();
        currentPage = lotcheckQueuePage;
    }

    @Test
    @Order(8)
    public void verifyInitialCodeFilterFieldIsNotSupportedSpace_Expected7(IJUnitTestReporter testReport) {
        LotcheckQueuePage lotcheckQueuePage = (LotcheckQueuePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertEquals(valueInitialCode, initialCode, "Initial Code filter field is NOT supported spaces so user can not input spaces");
    }

    @Test
    @Order(9)
    public void inputInitialCodeWithNoSpaceCharacter_Step8(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickOverallLotcheck(LotcheckRegion.NCL);
        lotcheckQueuePage.searchByInitialCode(initialCode)
                .clickPlannedTestDateNotSet();
        initialCodeInSubmission = lotcheckQueuePage.getTestPlanName().substring(6, 11);
        currentPage = lotcheckQueuePage;
    }

    @Test
    @Order(10)
    public void verifyUserCanInputAndShowSubmissionWithInitialCodeMatching_Expected8(IJUnitTestReporter testReport) {
        LotcheckQueuePage lotcheckQueuePage = (LotcheckQueuePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertEquals(initialCodeInSubmission, initialCode, "User can input initial code and show submission with initial code to what inputted");
    }

    @Test
    @Order(11)
    public void viewTheLotcheckIssuesTabOfTheLotcheckReport_Step9(IJUnitTestReporter testReport) {
        LotcheckQueuePage lotcheckQueuePage = (LotcheckQueuePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckReportSubmissionOverview reportSubmissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTestplanByTargetDelivery("DL");
        LotcheckReportSubmissionLotcheckIssues lotcheckIssues = reportSubmissionOverview.navBar().clickLotcheckIssues();
        lotcheckIssues.clickXpandVersion();
        currentPage = lotcheckIssues;
    }

    @Test
    @Order(12)
    public void verifyBothIssueAreDisplayedInProductResolutionArchiveIssue_Expected9(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues lotcheckIssues = (LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertEquals(2, lotcheckIssues.getNumberOfLotcheckIssueInProductResolutionArchiveIssues(), "Both Issues are displayed in the Product Resolution Archive Issues");
    }

    @Test
    @Order(13)
    public void reportANewBugInTestrailThenPublishBugToPartner_Step10(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport);
        TestRailDashboardPage testRailDashboardPage = (TestRailDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
        testRailManagerDashboardPage.waitingLoadingTestPlan()
                .actionFill()
                .enterTestPlanNameFilter(initialCode)
                .selectStatusUntested()
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .clickOnTheFirstTestSuite()
                .clickOnTheFirstTestRun();
        TestRailSubmitBugDialog submitBugDialog = testRailManagerDashboardPage.clickOnTheFirstTestcase().clickSubmitBug();
        submitBugDialog.addTitle("Submit Bug 3")
                .enterDescription(TextConstants.enterSubmitBug).clickSubmit();
        String messageSuccess = submitBugDialog.getMessageSuccessfully();
        submitBugDialog.clickOk().clickClose();
        assertEquals("Your Issue has been submitted successfully.", messageSuccess, "Verify submit bug successfully");
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickOverallLotcheck(LotcheckRegion.NCL);
        lotcheckQueuePage.searchByInitialCode(initialCode)
                .clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview reportSubmissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTestplanByTargetDelivery("DL");
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails = reportSubmissionOverview.navBar()
                .clickLotcheckIssues()
                .clickMenuOption()
                .selectViewIssueDetails();
        submissionLotcheckIssueDetails.clickMenu()
                .selectPublishToPublic()
                .clickPublish();
        WaitUtils.idle(3000);
        currentPage = submissionLotcheckIssueDetails;
    }

    @Test
    @Order(14)
    public void checkTheLotcheckIssuesTabOfTheLotcheckReport_Step11(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails = (LotcheckReportSubmissionLotcheckIssueDetails) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckReportSubmissionLotcheckIssues submissionLotcheckIssues = submissionLotcheckIssueDetails.clickBackToLotcheckIssue();
        submissionLotcheckIssues.clickXpandVersion();
        currentPage = submissionLotcheckIssues;
    }

    @Test
    @Order(15)
    public void verifyVariousIssueTypesReportedByLotcheck_Expected11(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues submissionLotcheckIssues = (LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertAll(() -> assertEquals(1, submissionLotcheckIssues.getNumberOfLotcheckIssueUnresolved(), "Various Issue types reported by Lotcheck put a new entry in the current issues dropdown"),
                () -> assertEquals(2, submissionLotcheckIssues.getNumberOfLotcheckIssueResolvedSuccess(), "Various Issue types reported by Lotcheck do not remove earlier versions of the same issue from the product resolution archive"));
    }

    @Test
    @Order(16)
    public void reportABugOnAnotherTestcaseInTestrailThenPublishBugToPartner_Step12(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport);
        TestRailDashboardPage testRailDashboardPage = (TestRailDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
        testRailManagerDashboardPage.waitingLoadingTestPlan()
                .actionFill()
                .enterTestPlanNameFilter(initialCode)
                .selectStatusUntested()
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .clickOnTheFirstTestSuite()
                .clickOnTheFirstTestRun();
        TestRailSubmitBugDialog submitBugDialog = testRailManagerDashboardPage.clickOnTheSecondTestcase().clickSubmitBug();
        submitBugDialog.addTitle("Submit Bug 4")
                .enterDescription(TextConstants.enterSubmitBug).clickSubmit();
        String messageSuccess = submitBugDialog.getMessageSuccessfully();
        submitBugDialog.clickOk().clickClose();
        assertEquals("Your Issue has been submitted successfully.", messageSuccess, "Verify submit bug successfully");
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickOverallLotcheck(LotcheckRegion.NCL);
        lotcheckQueuePage.searchByInitialCode(initialCode)
                .clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview reportSubmissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTestplanByTargetDelivery("DL");
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails = reportSubmissionOverview.navBar()
                .clickLotcheckIssues()
                .clickMenuOption()
                .selectViewIssueDetails();
        submissionLotcheckIssueDetails.clickMenu()
                .selectPublishToPublic()
                .clickPublish();
        WaitUtils.idle(3000);
        currentPage = submissionLotcheckIssueDetails;
    }

    @Test
    @Order(17)
    public void checkTheLotcheckIssuesTabOfTheLotcheckReport_Step13(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails = (LotcheckReportSubmissionLotcheckIssueDetails) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckReportSubmissionLotcheckIssues submissionLotcheckIssues = submissionLotcheckIssueDetails.clickBackToLotcheckIssue();
        submissionLotcheckIssues.clickXpandVersion();
        currentPage = submissionLotcheckIssues;
    }

    @Test
    @Order(18)
    public void verifyVariousIssueTypesReportedByLotcheck_Expected13(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues submissionLotcheckIssues = (LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertAll(() -> assertEquals(2, submissionLotcheckIssues.getNumberOfLotcheckIssueUnresolved(), "Various Issue types reported by Lotcheck put a new entry in the current issues dropdown"),
                () -> assertEquals(2, submissionLotcheckIssues.getNumberOfLotcheckIssueResolvedSuccess(), "Various Issue types reported by Lotcheck do not remove earlier versions of the same issue from the product resolution archive"));
    }

    @Test
    @Order(19)
    public void makeThisSubmissionPassLotcheck_Step14(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport);
        TestRailDashboardPage testRailDashboardPage = (TestRailDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
        testRailManagerDashboardPage.waitingLoadingTestPlan().clickOkInErrorPopup();
        //CommonAction.setPassedTestCaseOnTestrail(managerDashboardPage,initialCode);
        CommonAction.setStatusOnTestrailWithoutPercentComplete(testRailManagerDashboardPage, initialCode, TextConstants.passedStatus, TextConstants.OK, false);
        browser.refresh();
        testRailManagerDashboardPage.waitingLoadingTestPlan().clickOkInErrorPopup();
        //CommonAction.setPassedTestCaseOnTestrail(managerDashboardPage,initialCode);
        CommonAction.setStatusOnTestrailWithoutPercentComplete(testRailManagerDashboardPage, initialCode, TextConstants.passedStatus, TextConstants.OK, false);
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToJudgmentApprovalQueue(LotcheckRegion.NCL.toString());
        lotcheckQueuePage.searchByInitialCode(initialCode);
        LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
        lotcheckTestPlanRow.selectApproveJudgment().clickConfirm();
        WaitUtils.idle(3000);
        lotcheckLandingPage.navigationToJudgmentApprovalQueue(LotcheckRegion.NCL.toString());
        lotcheckQueuePage.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        lotcheckTestPlanRow.selectApproveJudgment().clickConfirm();
        WaitUtils.idle(3000);
    }

    @Test
    @Order(20)
    public void loginAsThePartnerAndCreateANewReleaseAsUPPRelease_Step15(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(TextConstants.lic_pwr_user_NCL, TextConstants.nintendo_approval_Password);
        NDPInternalPage internalPage = developmentHome.clickInternalTab();
        NDPProductSearchPage ndpProductSearchPage = internalPage.clickProductSearchButton();
        NDPViewProductDashboardPage ndpViewProductDashboardPage = ndpProductSearchPage.waitingLoadingValueSearch().clickSearchContent(initialCode)
                .waitingLoadingValueSearchTable2()
                .selectElementFromDropDownTable2();
        NDPSettingTab ndpSettingTab = ndpViewProductDashboardPage.clickSettingTab();
        ndpSettingTab.selectUppUpdate()
                .clickSaveButton();
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser).clickMyProducts();
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPCreateReleasePage ndpCreateReleasePage = ndpProductDashboardReleasesTab.clickCreateNewReleaseButton();
        ndpCreateReleasePage
                .selectReleaseType(TextConstants.upp_Update_Release);
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpCreateReleasePage.selectExpectedSubmissionDate(2)
                .selectExpectedReleaseDate(70)
                .clickCreateButton();
        ndpReleaseInfoPage.nav().clickReleaseInfo();
        currentPage = ndpReleaseInfoPage;
    }

    @Test
    @Order(21)
    public void declareTheRequiredInformationThenSubmitReleaseToLotcheck_Step16(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpReleaseInfoPage.nav().clickGuidelines().selectCompliesWithAllOfTheAboveGuidelines().clickOnSaveButton();
        int i = 0;
        while (i < 5) {
            if (!ndpReleaseInfoPage.nav().romUploadLockDisplayed()) {
                break;
            }
            browser.refresh();
            i++;
        }
        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
        CommonAction.enterReasonForRequest(issuePage);
        ndpReleaseInfoPage.nav().clickOnUserNameMenu().clickOnSignOutButton();
        //Approval Issue Task Requested
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReport).clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, gameCode);
        NDPViewTaskPage ndpViewTaskPage = browser.getPage(NDPViewTaskPage.class, testReport);
        ndpViewTaskPage.clickUserMenuDropdownList()
                .clickSignOutButton()
                .clickSignInPage()
                .signIn(ndpUser)
                .clickMyProducts()
                .clickProductByName(productName)
                .clickReleases()
                .selectRelease(TextConstants.upp_Update_Release);
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.enterDeveloperComment).selectSubmitButton().openNewTabAndCloseCurrentTab();

        NDPMyProductsPage myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        myProductsPage.clickProductByName(productName).clickReleases().selectRelease(TextConstants.upp_Update_Release);
        for (int j = 0; j < 5000; j++) {
            if (ndpReleaseInfoPage.getStatusValue().equals(TextConstants.submitToLotcheck)) {
                break;
            }
            WaitUtils.idle(3000);
            ndpReleaseInfoPage.nav().clickFeatures();
            ndpReleaseInfoPage.nav().clickReleaseInfo();
        }
        assertEquals(TextConstants.submitToLotcheck, ndpReleaseInfoPage.getStatusValue(), TextConstants.submitSuccess);
        WaitUtils.idle(3000);
    }

    @Test
    @Order(22)
    public void loginToTestrailAsTradminAssignedTestPlanToUser_Step17(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport);
        TestRailDashboardPage testRailDashboardPage = (TestRailDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
        testRailManagerDashboardPage.waitingLoadingTestPlan()
                .actionFill()
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
        currentPage = testRailManagerDashboardPage;
    }

    @Test
    @Order(23)
    public void loginToTestrailAsTradminOpenMyTodoThenSetFilterToDisplayTestAssignToUser_Step18(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailMyToDoPage testRailMyToDoPage = testRailManagerDashboardPage.topNav()
                .navigateToMyToDo();
        testRailMyToDoPage.actionFill()
                .enterTestPlanNameFilter(initialCode)
                .clickOnApplyFiltersButton();
        currentPage = testRailMyToDoPage.navTestPlanTable()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite();
    }

    @Test
    @Order(24)
    public void verifyMyTodoPageWillBeDisplayedWithTestPlanListed_Expected18(IJUnitTestReporter testReport) {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        boolean isTestPlanDisplayed = !testPlanTable.testplanIsNotVisible(gameCode);
        assertAll(() -> assertEquals("My Todo", testPlanTable.getPageTitle(), "Verify the My Todo page is displayed"),
                () -> assertTrue(isTestPlanDisplayed, "Verify test plan is displayed"));
    }

    @Test
    @Order(25)
    public void fullyExpandTheUPPReleaseTestplanThenSelectTheTitleOfAnUntestedTestcase_Step19(IJUnitTestReporter testReport) {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        testPlanTable.expandAllTestSuites()
                .clickOnTheFirstTestRun();
        testcase_01 = testPlanTable.getTestcaseName(1);
        currentPage = testPlanTable.openTestcaseDetail(1);
    }

    @Test
    @Order(26)
    public void verifyTheTestCaseViewModalWindowForTheTestCaseIsDisplayed_Expected19(IJUnitTestReporter testReport) {
        TestRailDetailTestcaseDialog detailTestcaseDialog = (TestRailDetailTestcaseDialog) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertTrue(detailTestcaseDialog.isDetailTestcaseModalOfTestcaseDisplayed(testcase_01), "Verify the Testcase View Modal is displayed");
    }

    @Test
    @Order(27)
    public void selectAndSubmitBug_Step20(IJUnitTestReporter testReport) {
        TestRailDetailTestcaseDialog detailTestcaseDialog = (TestRailDetailTestcaseDialog) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailSubmitBugDialog submitBugDialog = detailTestcaseDialog.clickSubmitBug();
        submitBugDialog.addTitle("Submit Bug 5")
                .enterDescription(TextConstants.enterSubmitBug)
                .checkNeedsTranslation()
                .clickSubmit();
        messageSubmitBugSuccess = submitBugDialog.getMessageSuccessfully();
        currentPage = submitBugDialog.clickOk().clickClose();
    }

    @Test
    @Order(28)
    public void verifyANewIssueWillBeCreatedForTheUPPRelease_Expected20(IJUnitTestReporter testReport) {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertAll(() -> assertTrue(testPlanTable.isTestCaseSubmittedBug(testcase_01), "Verify a new issue is created successfully"),
                () -> assertEquals("Your Issue has been submitted successfully.", messageSubmitBugSuccess, "Verify submit bug successfully"));
    }

    @Test
    @Order(29)
    public void navigateToTheLotcheckIssueAndPublishBugToPublic_Step21(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickOverallLotcheck(LotcheckRegion.NCL);
        lotcheckQueuePage.searchByInitialCode(initialCode)
                .clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview reportSubmissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTestplan("101");
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails = reportSubmissionOverview.navBar()
                .clickLotcheckIssues()
                .clickMenuOption()
                .selectViewIssueDetails();
        submissionLotcheckIssueDetails.clickMenu()
                .selectPublishToPublic()
                .clickPublish();
        WaitUtils.idle(3000);
        currentPage = submissionLotcheckIssueDetails;
    }

    @Test
    @Order(30)
    public void verifyThePartnerIsNowAbleToSeeTheIssue_Expected21(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails = (LotcheckReportSubmissionLotcheckIssueDetails) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        String partnerThreadStatus = submissionLotcheckIssueDetails.getPartnerThreadStatus();
        assertEquals("NEEDS RESPONSE", partnerThreadStatus, "Verify partner is now able to see the issue");
    }

    @Test
    @Order(31)
    public void loginToNDPAsProductAdminThenSelectTheProductTheUPPReleaseBelongTo_Step22(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        currentPage = myProductsPage.clickProductByName(productName);
    }

    @Test
    @Order(32)
    public void verifyTheProductDashboardWillBeDisplayed_Expected22(IJUnitTestReporter testReport) {
        NDPProductDashboardPage ndpProductDashboardPage = (NDPProductDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertEquals(ndpProductDashboardPage.getPageTitle(),"Dashboard - Nintendo Developer Portal", "Verify The Product Dashboard is displayed");
    }

    @Test
    @Order(33)
    public void navigateToMessageTabOpenIssueThatHasJustPublished_Step23(IJUnitTestReporter testReport) {
        NDPProductDashboardPage ndpProductDashboardPage = (NDPProductDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardMessagesTab ndpProductDashboardMessagesTab = ndpProductDashboardPage.clickMessagesTab();
        currentPage = ndpProductDashboardMessagesTab.selectViewThreadInTheFirstThread();
    }

    @Test
    @Order(34)
    public void inputImageUrl_Step24(IJUnitTestReporter testReport) {
        NDPMessageBoardPage ndpMessageBoardPage = (NDPMessageBoardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        String linkImage = currentURL + "noa-theme/images/bkg/nintendo.png";
        ndpMessageBoardPage.enterDescription(TextConstants.enterDescription)
                .addImage(linkImage);
    }

    @Test
    @Order(35)
    public void inFieldAlignmentChooseValueLeftThenSelectPostButton_Step25(IJUnitTestReporter testReport) {
        NDPMessageBoardPage ndpMessageBoardPage = (NDPMessageBoardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpMessageBoardPage.selectAlignment("Left")
                .clickOKButton()
                .clickPost();
        widthOfImageInNDP = ndpMessageBoardPage.getWidthImage("nintendo.png");
        heightOfImageInNDP = ndpMessageBoardPage.getHeightImage("nintendo.png");
    }

    @Test
    @Order(36)
    public void loginSLCMSAsLCAdminNavigateToSubmissionLevel_Step26(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickOverallLotcheck(LotcheckRegion.NCL);
        lotcheckQueuePage.searchByInitialCode(initialCode)
                .clickPlannedTestDateNotSet();
        currentPage = lotcheckQueuePage.navigateToSubmissionLevelInTestplan("101");
    }

    @Test
    @Order(37)
    public void navigateToPartnerThreadTab_Step27(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionOverview reportSubmissionOverview = (LotcheckReportSubmissionOverview) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        currentPage = reportSubmissionOverview.navBar()
                .clickLotcheckIssues()
                .clickMenuOption()
                .selectViewIssueDetails()
                .clickPartnerThreadTab();
    }

    @Test
    @Order(38)
    public void selectTwoLanguageDisplay_Step28(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssuePartnerThread reportSubmissionLotcheckIssuePartnerThread = (LotcheckReportSubmissionLotcheckIssuePartnerThread) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        WaitUtils.idle(5000);
        reportSubmissionLotcheckIssuePartnerThread.clickTwoLanguageDisplayButton();
    }

    @Test
    @Order(39)
    public void checkImageComment_Step29(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssuePartnerThread reportSubmissionLotcheckIssuePartnerThread = (LotcheckReportSubmissionLotcheckIssuePartnerThread) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        imageIsDisplayed = reportSubmissionLotcheckIssuePartnerThread.imageIsDisplayed("nintendo.png");
        widthOfImageInSLCMS = reportSubmissionLotcheckIssuePartnerThread.getWidthImage("nintendo.png");
        heightOfImageInSLCMS = reportSubmissionLotcheckIssuePartnerThread.getHeightImage("nintendo.png");
    }

    @Test
    @Order(40)
    public void verifyDisplayedImage_Expected29(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssuePartnerThread reportSubmissionLotcheckIssuePartnerThread = (LotcheckReportSubmissionLotcheckIssuePartnerThread) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertAll(() -> assertTrue(imageIsDisplayed, "Verify image is displayed at correct size and without losing part of the image"),
                () -> assertEquals(widthOfImageInSLCMS, widthOfImageInNDP, "Verify image is displayed at correct size and without losing part of the image"),
                () -> assertEquals(heightOfImageInSLCMS, heightOfImageInNDP, "Verify image is displayed at correct size and without losing part of the image"));
    }

    @Test
    @Order(41)
    public void inputImageUrl_Step30(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssuePartnerThread reportSubmissionLotcheckIssuePartnerThread = (LotcheckReportSubmissionLotcheckIssuePartnerThread) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        String linkImage = currentURL + "documents/23933/747462/banner-hyrule.png";
        reportSubmissionLotcheckIssuePartnerThread.addImage(linkImage);
        currentPage = reportSubmissionLotcheckIssuePartnerThread;
    }

    @Test
    @Order(42)
    public void inFieldAlignmentChooseValueRightThenClickOKButton_Step31(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssuePartnerThread reportSubmissionLotcheckIssuePartnerThread = (LotcheckReportSubmissionLotcheckIssuePartnerThread) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        reportSubmissionLotcheckIssuePartnerThread.selectAlignment("Right")
                .clickOKButton();
    }

    @Test
    @Order(43)
    public void clickReplyToPartnerButton_Step32(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssuePartnerThread reportSubmissionLotcheckIssuePartnerThread = (LotcheckReportSubmissionLotcheckIssuePartnerThread) currentPage;
        reportSubmissionLotcheckIssuePartnerThread.selectReplyToPartner();
        WaitUtils.idle(3000);
    }

    @Test
    @Order(44)
    public void checkImageComment_Step33(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssuePartnerThread reportSubmissionLotcheckIssuePartnerThread = (LotcheckReportSubmissionLotcheckIssuePartnerThread) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        imageUploadIsDisplayed = reportSubmissionLotcheckIssuePartnerThread.imageUploadIsDisplayed("banner-hyrule.png");
        widthOfImageUploadInSLCMS = reportSubmissionLotcheckIssuePartnerThread.getWidthImageUpload("banner-hyrule.png");
        heightOfImageUploadInSLCMS = reportSubmissionLotcheckIssuePartnerThread.getHeightImageUpload("banner-hyrule.png");
    }

    @Test
    @Order(45)
    public void verifyDisplayedImage_Expected33(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssuePartnerThread reportSubmissionLotcheckIssuePartnerThread = (LotcheckReportSubmissionLotcheckIssuePartnerThread) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertAll(() -> assertTrue(imageUploadIsDisplayed, "Verify image is displayed at correct size and without losing part of the image"),
                () -> assertEquals(widthOfImageUploadInSLCMS, "1200px", "Verify image is displayed at correct size and without losing part of the image"),
                () -> assertEquals(heightOfImageUploadInSLCMS, "675px", "Verify image is displayed at correct size and without losing part of the image"));
    }

    @Test
    @Order(46)
    public void openReleaseTabThenSelectCreateNewRelease_Step34(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        currentPage = ndpProductDashboardReleasesTab.clickCreateNewReleaseButton();
    }

    @Test
    @Order(47)
    public void verifyCreateNewReleasePageWillBeDisplayed_Expected34(IJUnitTestReporter testReport) {
        NDPCreateReleasePage ndpCreateReleasePage = (NDPCreateReleasePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertEquals(ndpCreateReleasePage.getPageName(), "Create New Release", "Verify The Create New Release is displayed");
    }

    @Test
    @Order(48)
    public void createNewReleasePatch_Step35(IJUnitTestReporter testReport) {
        NDPCreateReleasePage ndpCreateReleasePage = (NDPCreateReleasePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpCreateReleasePage.enterDisplayVersion("1.0.1")
                .selectExpectedSubmissionDate(2)
                .selectExpectedReleaseDate(70)
                .selectFreeToPlay(TextConstants.no_Upper_Value)
                .enterDescribeTheFeaturesPlanned(TextConstants.enterDescribeTheFeaturesPlanned)
                .enterDescribeTheBugsIssuesPlanned(TextConstants.enterDescribeTheBugsIssuesPlanned);
        currentPage = ndpCreateReleasePage.clickCreateButton();
    }

    @Test
    @Order(49)
    public void verifyANewPatchWillBeCreatedForTheProduct_Expected35(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        String releaseVersion = ndpReleaseInfoPage.getReleaseVersionValue();
        assertTrue(releaseVersion.equalsIgnoreCase("01"), "A new patch release will be created for the product");
    }

    @Test
    @Order(50)
    public void completeAllRequiredSectionThenSubmitToLotcheck_Step36(IJUnitTestReporter testReport) throws Exception {
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
        releaseFeatures.clickContinue();
        //Create GuildLine
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = ndpReleaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoGuidelinesPage.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();
        //create ROM
        String originalApp = "Application_" + applicationID + "_v00_multi-11.nsp";
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
                .selectMPACheckbox()
                .selectNumberOfProgram("11")
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
        fileRomNamePatch = romCreateRom.getRomNameTable("Patch_" + applicationID);
        romCreateRom.clickOnROMFileOnROMTable("Patch_" + applicationID);
        WaitUtils.idle(10000);
        romCreateRom.clickOnUserNameMenu().clickOnLogoutButton();
        BrowserManager.closeBrowser(romBrowser);
        //upload ROM
        NDPReleaseInfoReleaseListNav nav = browser.getPage(NDPReleaseInfoReleaseListNav.class, testReport);
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = nav.clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomNamePatch);
        ndpReleaseInfoROMUpload.deleteFile(fileRomNamePatch);
        //Issues
        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
        CommonAction.enterReasonForRequest(issuePage);
        nav.clickOnUserNameMenu().clickOnSignOutButton();
        //Approval Issue TaskRequested
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(licUser).clickOnMyTaskMenu();
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, gameCode);
        NDPViewTaskPage ndpViewTaskPage = browser.getPage(NDPViewTaskPage.class, testReport);
        ndpViewTaskPage.clickUserMenuDropdownList().clickSignOutButton();
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser).clickMyProducts();
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        //enter test scenarios
        myProductsPage.clickProductByName(productName).clickReleases().selectPatchRelease().nav().clickTestScenarios().expandAllSections()
                .expandAllTestScenes()
                .enterSubjectForProgramSpecificationsHowToStartUpTheIncludedProgramsIncluded(TextConstants.commentTestScenario, 0)
                .enterExplainForProgramSpecificationsHowToStartUpTheIncludedProgramsIncluded(TextConstants.commentExplainProgram, 0)
                .clickOnSaveButton();
        WaitUtils.idle(5000);
        ndpReleaseInfoPage.nav().clickIssues();
        //submit product
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.enterDeveloperComment).selectSubmitButton().openNewTabAndCloseCurrentTab();

        NDPMyProductsPage myProductsPage1 = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        myProductsPage.clickProductByName(productName).clickReleases().selectPatchRelease();
        for (int i = 0; i < 5000; i++) {
            if (ndpReleaseInfoPage.getStatusValue().equals(TextConstants.submitToLotcheck)) {
                break;
            }
            WaitUtils.idle(3000);
            ndpReleaseInfoPage.nav().clickFeatures();
            ndpReleaseInfoPage.nav().clickReleaseInfo();
        }
        currentPage = ndpReleaseInfoPage;
    }

    @Test
    @Order(51)
    public void verifyThePatchReleaseWillBeSubmittedToLotcheck_Expected36(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertEquals(TextConstants.submitToLotcheck, ndpReleaseInfoPage.getStatusValue(), TextConstants.submitSuccess);
    }

    @Test
    @Order(52)
    public void openTheSubmissionLevelLotcheckReportForThePatchRelease_Step37(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickOverallLotcheck(LotcheckRegion.NCL);
        lotcheckQueuePage.searchByInitialCode(initialCode)
                .clickPlannedTestDateNotSet();
        currentPage = lotcheckQueuePage.navigateToSubmissionLevelInTestplan("01");
    }

    @Test
    @Order(53)
    public void verifyTheSubmissionLevelLotcheckReportWillBeDisplayed_Expected37(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionOverview reportSubmissionOverview = (LotcheckReportSubmissionOverview) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertAll(() -> assertEquals("Overview Submission: LCR - LCMS", reportSubmissionOverview.getPageTitle(), "VerifyThe Submission-Level Lotcheck Report will be displayed"),
                () -> assertEquals("01", reportSubmissionOverview.getReleaseVersionOfSubmission(), "Verify The Submission-Level Lotcheck Report will be displayed for the patch release"),
                () -> assertEquals("00", reportSubmissionOverview.getSubmissionVersionOfSubmission(), "Verify The Submission-Level Lotcheck Report will be displayed for the patch release"));
    }

    @Test
    @Order(54)
    public void openLotcheckIssuesThenExpandProductResolutionArchiveIssue_Step38(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionOverview reportSubmissionOverview = (LotcheckReportSubmissionOverview) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckReportSubmissionLotcheckIssues lotcheckIssues = reportSubmissionOverview.navBar().clickLotcheckIssues();
        lotcheckIssues.expandCollapsedIssueInArchive();
        currentPage = lotcheckIssues;
    }

    @Test
    @Order(55)
    public void verifyTheIssueReportedInTheUPPReleaseWillBeDisplayed_Expected38(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues lotcheckIssues = (LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertTrue(lotcheckIssues.issueIsDisplayed("Submit Bug 5"), "Verify The Issue Reported In UPP Release is displayed");
    }
}
