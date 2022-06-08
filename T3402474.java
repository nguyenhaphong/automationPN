package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthPage;
import net.nintendo.automation.ui.models.lcms.LotcheckHomePage;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.ui.models.lcms.LotcheckQueuePage;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssueDetails;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPLicensingWaiverRequestsTab;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.NDPSignInPage;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardProductInfoTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardReleasesTab;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoAgeRating;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoGuidelinesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoIssuePage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoROMUpload;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoReleaseListNav;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPConfirmationDialog;
import net.nintendo.automation.ui.models.rom.ROMCreateRom;
import net.nintendo.automation.ui.models.rom.ROMLoginPage;
import net.nintendo.automation.ui.models.testrail.TestRailDashboardPage;
import net.nintendo.automation.ui.models.testrail.TestRailLoginPage;
import net.nintendo.automation.ui.models.testrail.TestRailManagerDashboardPage;
import net.nintendo.automation.ui.models.testrail.TestRailMyToDoPage;
import net.nintendo.automation.ui.models.testrail.TestRailSubmitBugDialog;
import net.nintendo.automation.ui.users.User;
import net.nintendo.automation.ui.users.UserManager;
import net.nintendo.automation.utils.WaitUtils;
import net.nintendo.automation_core.common_client.enums.ServiceType;
import net.nintendo.automation_core.common_client.enums.UserType;
import net.nintendo.automation_core.common_utils.annotations.services.NDP;
import net.nintendo.automation_core.common_utils.annotations.services.SLCMS;
import net.nintendo.automation_core.common_utils.enums.LotcheckRegion;
import net.nintendo.automation_core.extent_reports.extensions.IJUnitTestReporter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classname: T3402474
 * Version: 1.0.0
 * Purpose:
 */

@NDP
@SLCMS
@Tag("T3402474")
//@ScenarioTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3402474 extends CommonBaseTest {
    private static Browser browser;
    private static User ndpUser;
    private static Browser slcmsBrowser;
    private static User slcmsUser;
    private static Browser romBrowser;
    private static User romUser;
    private static User licUser;
    private static User testRailUser;
    private static String productName = "T3402474_Auto_Test_" + java.time.LocalDate.now();
    private static String applicationID;
    private static String initialCode;
    private static String gameCode;
    private static String fileRomName;
    private static String titleIssue;

    @AfterAll
    static void terminateBrowser() {
        BrowserManager.closeBrowser(browser);
        UserManager.releaseUser(ServiceType.NDP, ndpUser);
        UserManager.releaseUser(ServiceType.SLCMS, slcmsUser);
        UserManager.releaseUser(ServiceType.SEARCH, romUser);
        UserManager.releaseUser(ServiceType.NDP, licUser);
        UserManager.releaseUser(ServiceType.PMMS, testRailUser);
    }

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.ADMIN_USER);
        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
        slcmsUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);
        testRailUser = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER);

        NDPHomePage ndpHomePage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
        ndpHomePage.acceptCookie();
        NDPSignInPage signInPage = ndpHomePage.clickSignInPage();
        signInPage.signIn(ndpUser);

    }


    //Todo: Manual support create product by product name is "T3402474_Auto_Test_+ date" - date is the run testcase date and execute test testcase from step 1 to step 4
    @Test()
    @Order(5)
    public void fillDashboardAndSubmitProduct_Step(IJUnitTestReporter testReport) throws Exception {
        NDPMyProductsPage myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
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
                .selectRatingValue("A")
                .selectDescriptorAndNotice("Sexual Content")
                .uploadCertificateAttachment(new File("src/test/resources/DataFile/ab.jpg"))
                .clickOnSaveButtonInEditStandardRating();

        //create file ROM
        romBrowser = BrowserManager.getNewInstance();
        ROMLoginPage romLoginPage = romBrowser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN);
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom
                .selectRequester(TextConstants.test_User)
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
        BrowserManager.closeBrowser(romBrowser);

        //Upload ROM
        NDPReleaseInfoReleaseListNav releaseListNav = browser.getPage(NDPReleaseInfoReleaseListNav.class, testReport);
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = releaseListNav.clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
        //Issues
        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
        CommonAction.enterReasonForRequest(issuePage);
        releaseListNav.clickOnUserNameMenu().clickOnSignOutButton();
        //Approval Issue TaskRequested
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, gameCode);
        releaseListNav.clickOnUserNameMenu().clickOnSignOutButton();
        //submit product
        ndpReleaseInfoPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN)
                .clickSignInPage()
                .signIn(ndpUser)
                .clickMyProducts()
                .clickProductByName(productName)
                .clickReleases()
                .selectInitialRelease();
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.enterDeveloperComment).clickSubmit();
        WaitUtils.idle(3000);
    }

    @Test()
    @Order(6)
    public void verifyTheInitialReleaseSubmitted_Expected(IJUnitTestReporter testReport) throws InterruptedException {
        //verify in SLCMS
        slcmsBrowser = BrowserManager.getNewInstance();
        LotcheckHomePage lotcheckHomePage = slcmsBrowser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.signIn(TextConstants.noa_VanN_Email, TextConstants.noa_VanN_Password);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickTesterAssignment(LotcheckRegion.NCL);
        CommonAction.findProductInLCQueue(lotcheckLandingPage, 30, LotcheckRegion.NCL.toString(), initialCode);
        lotcheckQueuePage.clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        String testPlanStatus = lotcheckReportSubmissionOverview.matrix().getTestPlanStatus(0);
        assertTrue(testPlanStatus.equalsIgnoreCase("Assign Testers"), "Test plan status is displayed correctly");
        //verify in Testrail
        TestRailLoginPage testRailLoginPage = lotcheckReportSubmissionOverview.navBar().switchToTestRailLoginPage();
        TestRailManagerDashboardPage managerDashboardPage = testRailLoginPage.signInTestRail(TextConstants.trAdminNCL_Email, TextConstants.trAdmin_Password)
                .navTopTestrail()
                .navigateToManagerDashboard()
                .waitingLoadingTestPlan()
                .clickOkInErrorPopup()
                .actionFill()
                .enterTestPlanNameFilter(initialCode)
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan();
        int num = managerDashboardPage.collectNumberTestPlan();
        assertTrue(num == 1, "No duplication of application test plan");
    }


    //Cancel Submission
    @Test()
    @Order(7)
    public void cancelSubmission_Step(IJUnitTestReporter testReport) throws InterruptedException {
        NDPReleaseInfoPage ndpReleaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        NDPConfirmationDialog ndpConfirmationDialog = ndpReleaseInfoPage.nav().clickCancelSubmitButton();
        ndpConfirmationDialog.clickOKInConfirm();
    }

    @Test()
    @Order(8)
    public void verifyCancelSubmission_Expected(IJUnitTestReporter testReport) throws InterruptedException {
        NDPReleaseInfoPage ndpReleaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        String messageValue = ndpReleaseInfoPage.nav().getMessageOnTheRight();
        String statusValue = ndpReleaseInfoPage.getStatusValue();
        assertAll(() -> assertTrue(messageValue.equalsIgnoreCase("Your submission is currently pending cancellation."), TextConstants.messageCorrect),
                () -> assertTrue(statusValue.equalsIgnoreCase("Cancel Pending"), "status is displayed Cancel Pending"));
        int i = 0;
        while (i <= 300) {
            browser.refresh();
            String newStatusValue = ndpReleaseInfoPage.getStatusValue();
            if (newStatusValue.equalsIgnoreCase("In Development") && ndpReleaseInfoPage.isCardSizeEnabled()) {
                testReport.logInfo("The submission is cancelled");
                break;
            }
        }
        String expectedLotCheckSubmissionDate = ndpReleaseInfoPage.getExpectedLotcheckSubmissionDateValue();
        assertTrue(expectedLotCheckSubmissionDate.equalsIgnoreCase(""), "The Expected Lotcheck Submission Date field is cleared");
    }

    @Test()
    @Order(9)
    public void refillDashboardAndSubmitAgain_Step(IJUnitTestReporter testReport) throws Exception {
        //complete release info
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        releaseInfoPage.selectExpectedSubmissionDate().chooseDateInCalender(2);
        releaseInfoPage.save();
        //complete guideline
        NDPReleaseInfoGuidelinesPage guidelinesPage = releaseInfoPage.nav().clickGuidelines();
        guidelinesPage.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();
        //complete Rom upload
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = guidelinesPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
        //Delete file Rom in folder
        ndpReleaseInfoROMUpload.deleteFile(fileRomName);
        releaseInfoPage.nav().clickIssues();
        //resubmit
        releaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.enterDeveloperComment).clickSubmit();
        WaitUtils.idle(3000);
    }

    @Test()
    @Order(10)
    public void verifySubmitProductAgainInLotCheckQueue_Expected(IJUnitTestReporter testReport) throws Exception {
        //verify in SLCMS
        LotcheckLandingPage lotcheckLandingPage = slcmsBrowser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickTesterAssignment(LotcheckRegion.NCL);
        CommonAction.findProductInLCQueue(lotcheckLandingPage, 30, LotcheckRegion.NCL.toString(), initialCode);
    }

    @Test()
    @Order(11)
    public void locateTestPlanInTestRail_Step(IJUnitTestReporter testReport) {
        TestRailDashboardPage dashboardPage = slcmsBrowser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport);
        TestRailManagerDashboardPage managerDashboardPage = dashboardPage.navTopTestrail()
                .navigateToManagerDashboard()
                .waitingLoadingTestPlan()
                .clickOkInErrorPopup()
                .actionFill()
                .enterTestPlanNameFilter(initialCode)
                .selectStatusUntested()
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan();
    }

    @Test()
    @Order(12)
    public void verifyTestPlanExitsInTestRail_Expected(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage managerDashboardPage = slcmsBrowser.getPage(TestRailManagerDashboardPage.class, testReport);
        int num = managerDashboardPage.collectNumberTestPlan();
        assertTrue(num == 1, "Test plans for the submission exist in Testrail.");
    }

    @Test()
    @Order(13)
    public void assignATestCaseToCurrentUser_Step(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage managerDashboardPage = slcmsBrowser.getPage(TestRailManagerDashboardPage.class, testReport);
        managerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .expandAllTestRuns()
                .selectCheckBoxOfFirstTestCase()
                .clickAssignTo()
                .selectSetValueAssignToRadio()
                .selectUser("Me")
                .clickOk()
                .waitingLoadingTestSuite();
    }

    @Test()
    @Order(14)
    public void verifyTestcaseAppearsOnMyTodoTab_Expected(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage managerDashboardPage = slcmsBrowser.getPage(TestRailManagerDashboardPage.class, testReport);
        TestRailMyToDoPage myTodoPage = managerDashboardPage.topNav()
                .navigateToMyTodo()
                .waitingLoadingTestPlan()
                .clickOkInErrorPopup()
                .actionFill()
                .enterTestPlanNameFilter(initialCode)
                .clickOnApplyFiltersButtonToMyTodoPage()
                .waitingLoadingTestPlan();
        int num = myTodoPage.collectNumberTestPlan();
        assertTrue(num == 1, "Test plans for the submission exist in Testrail.");
    }

    @Test()
    @Order(15)
    public void submitBugAndPublishBugToPartner_Step(IJUnitTestReporter testReport) {
        //submit bug
        TestRailMyToDoPage myTodoPage = slcmsBrowser.getPage(TestRailMyToDoPage.class, testReport);
        TestRailSubmitBugDialog submitBugDialog = myTodoPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .expandAllTestRuns()
                .clickOnTheFirstTestcase()
                .clickSubmitBug();
        submitBugDialog.enterDescription(TextConstants.enterSubmitBug).clickSubmit();
        String messageSuccess = submitBugDialog.getMessageSuccessfully();
        assertEquals("Your Issue has been submitted successfully.", messageSuccess, "Verify submit bug successfully");
        submitBugDialog.clickOk().clickCloseMyTodo();
        //Publish bug to partner
        LotcheckLandingPage lotcheckLandingPage = slcmsBrowser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN,testReport);
        LotcheckReportSubmissionOverview submissionOverview = lotcheckLandingPage.searchProduct(initialCode)
                .selectSubmission(gameCode, "00", "01");
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails = submissionOverview.navBar()
                .clickLotcheckIssues()
                .clickMenuOption()
                .selectViewIssueDetails();
        submissionLotcheckIssueDetails.clickMenu()
                .selectPublishToPublic()
                .selectLciType(TextConstants.submissionIssue)
                .clickPublish();
        titleIssue = submissionLotcheckIssueDetails.getTitleOfIssue();
        String partnerThreadStatus = submissionLotcheckIssueDetails.getPartnerThreadStatus();
        WaitUtils.idle(3000);
        assertEquals("NEEDS RESPONSE", partnerThreadStatus, "Verify partner Thread Status after publish");

    }

    @Test()
    @Order(16)
    public void navigateLotCheckIssueByPartner_Step(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        NDPReleaseInfoIssuePage issuePage = releaseInfoPage.nav().clickIssues()
                .clickOnLotcheckIssuesTab()
                .expandUnresolve()
                .expandIssue();
        assertEquals(titleIssue.substring(0, 37), issuePage.getIssueTitle(), "Verify issue published on slcms is displayed");
    }

    @Test()
    @Order(17)
    public void selectWillFixInFeaturesAndResubmit_Step(IJUnitTestReporter testReport) {
        NDPReleaseInfoIssuePage issuePage = browser.getPage(NDPReleaseInfoIssuePage.class, testReport);
        issuePage.selectResolution(TextConstants.willFixInFeature)
                .clickOnSendResolutionButton()
                .expandResolve();
        assertAll(() -> assertEquals(TextConstants.willFixInFeature, issuePage.getStatusOfIssue(), "Verify status of issue after send Will Fix In Features resolution"),
                () -> assertTrue(issuePage.nav().submitEditedInformationButtonIsClickable(), "Verify Submit Edited Information button is display and enable"));
        //re-submit submission
        NDPReleaseInfoPage releaseInfoPage = issuePage.nav().clickSubmitEditedInformation().clickSubmit();
        WaitUtils.idle(3000);

    }

    @Test()
    @Order(18)
    public void verifyReSubmittedSuccess_Expected(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        //Confirm submission version
        String status = releaseInfoPage.getStatus();
        String submissionVersion = releaseInfoPage.getSubmissionVersion();
        assertAll(() -> assertTrue(status.equalsIgnoreCase(TextConstants.submitToLotcheck), "The release is resubmitted"),
                () -> assertTrue(submissionVersion.equalsIgnoreCase("01"), "The Submission Version is not incremented"),
                () -> assertTrue(releaseInfoPage.isExpectedLotcheckSubmissionDateNotEditable(), "The Expected Lotcheck Submission Date field is not cleared"));

    }
}
