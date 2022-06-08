package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.data.DataGen;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.lcms.*;
import net.nintendo.automation.ui.models.lcms.lotcheck_issue_translation.LotcheckIssueTranslationIssueDetails;
import net.nintendo.automation.ui.models.lcms.lotcheck_issue_translation.LotcheckIssueTranslationTaskDetails;
import net.nintendo.automation.ui.models.lcms.submission_reports.*;
import net.nintendo.automation.ui.models.ndp.*;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardProductInfoTab;
import net.nintendo.automation.ui.models.ndp.existing_release.*;
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

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Classname: T3403869
 * Version: 1.0.0
 * Purpose:
 */
@Tag("T3403869")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@ScenarioTest
public class T3403869 extends CommonBaseTest {
    private static Browser browser;
    private static User romUser;
    private static User ndpUser;
    private static User licUser;
    private static User slcmsLCAdmin;
    private static User testrailUser;

    private static String productName;
    private static String applicationID;
    private static String initialCode;
    private static String gameCode;
    private static String fileRomName;
    private static String commentString;
    private static String bugDescription = "Edit Published Description EN";
    private static String error;
    private static String testcase_2;
    private static boolean isSaveButtonEnabled;
    private static boolean isCancelButtonEnabled;
    private static String responseForApprovalOrReject = "Comment for Approval or Reject";

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.ADMIN_USER);
        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
        testrailUser = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER);
        slcmsLCAdmin = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);
        productName = DataGen.getRandomProductName();

    }

    @AfterAll
    static void terminateBrowser() {
        BrowserManager.closeBrowser(browser);
        UserManager.releaseUser(ServiceType.NDP, ndpUser);
        UserManager.releaseUser(ServiceType.NDP, licUser);
        UserManager.releaseUser(ServiceType.SEARCH, romUser);
        UserManager.releaseUser(ServiceType.SLCMS, slcmsLCAdmin);
        UserManager.releaseUser(ServiceType.PMMS, testrailUser);
    }

    @Test
    @Order(0)
    public void createTestPlan_TestPrecondition(IJUnitTestReporter testReporter) {
        NDPHomePage ndpHomePage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReporter);
        NDPMyProductsPage myProductsPage = ndpHomePage.acceptCookie()
                .clickSignInPage()
                .signIn(ndpUser)
                .clickMyProducts();
        myProductsPage.createNewProduct()
                .selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(TextConstants.third_Party_Type)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalJapanSalesRegion()
                .selectProductName(productName)
                .selectProductNameJp(productName)
                .selectProductNameKana(TextConstants.productKana)
                .clickCreateButton();
        NDPProductDashboardPage myProductDashBoard = myProductsPage.clickProductByName(productName);
        CommonAction.issueGameCode(myProductDashBoard, 65, "NO");
        NDPProductDashboardProductInfoTab productInfoTab = myProductDashBoard.clickProductInfo();
        gameCode = productInfoTab.getGameCode();
        initialCode = productInfoTab.getInitialCode();
        applicationID = productInfoTab.getApplicationID();
        NDPReleaseInfoPage releaseInfoPage = myProductDashBoard.clickReleases().selectInitialRelease();
        //create release info
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
                .uploadCertificateAttachment(new File("src/test/resources/DataFile/ab.jpg"))
                .clickOnSaveButtonInEditStandardRating();

        //create file ROM
        ROMLoginPage romLoginPage = browser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN, testReporter);
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom
                .selectRequester(TextConstants.test_User)
                .selectType(TextConstants.initial_Release)
                .selectTargetDeliveryFormat(TextConstants.both_Target_Type)
                .enterProductName(productName)
                .enterApplicationId(applicationID)
                .selectPublishingRelationShip(TextConstants.third_Party_Type)
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

        browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReporter);
        myProductsPage.clickProductByName(productName).clickReleases().selectInitialRelease();
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = releaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
        //Issues
        NDPReleaseInfoIssuePage issuePage = releaseInfoPage.nav().clickIssues();
        CommonAction.enterReasonForRequest(issuePage);
        issuePage.nav().clickOnUserNameMenu().clickOnSignOutButton();
        //Approval Issue TaskRequested
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReporter).clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, gameCode);
        browser.refresh();
        NDPViewTaskPage viewTaskPage = browser.getPage(NDPViewTaskPage.class, testReporter);
        viewTaskPage.clickUserMenuDropdownList().clickSignOutButton();
        //submit product
        browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReporter)
                .clickSignInPage()
                .signIn(ndpUser)
                .clickMyProducts()
                .clickProductByName(productName)
                .clickReleases()
                .selectInitialRelease();
        releaseInfoPage.nav().selectSubmitButton()
                .clickSubmitReview().openNewTabAndCloseCurrentTab();
        myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReporter);
        myProductsPage.clickProductByGameCode(gameCode).clickReleases().selectInitialRelease();
        for (int i = 0; i < 300; i++) {
            if (releaseInfoPage.getStatusValue().equals(TextConstants.submitToLotcheck)) {
                break;
            }
            WaitUtils.idle(3000);
            releaseInfoPage.nav().clickFeatures();
            releaseInfoPage.nav().clickReleaseInfo();
        }
        assertEquals(TextConstants.submitToLotcheck, releaseInfoPage.getStatusValue(), TextConstants.submitSuccess);
        releaseInfoPage.nav().clickOnUserNameMenu().clickOnSignOutButton();
        //verify in SLCMS
        LotcheckHomePage lotcheckHomePage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReporter);
        LotcheckLandingPage lotcheckLandingPage = lotcheckHomePage.continueToLogin().loginWithOvdLink(slcmsLCAdmin);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickTesterAssignment(LotcheckRegion.NCL);
        CommonAction.findTestPlanInLCQueue(lotcheckLandingPage, 30, LotcheckRegion.NCL.toString(), initialCode, "1");
        lotcheckQueuePage.clickPlannedTestDateNotSet();
        lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00").navBar().switchToTestRailLoginPage();
    }

    @Test
    @Order(1)
    public void loginTestRailAsTestAdmin_Step1(IJUnitTestReporter testReporter) {
        TestRailLoginPage testRailLoginPage = browser.getPage(TestRailLoginPage.class, testReporter);
        testRailLoginPage.signInTestRail(testrailUser);
    }

    @Test
    @Order(2)
    public void switchToManagerDashboardTab_Step2(IJUnitTestReporter testReporter) {
        TestRailDashboardPage dashboardPage = browser.getPage(TestRailDashboardPage.class, testReporter);
        dashboardPage.navTopTestrail().navigateToManagerDashboard()
                .waitingLoadingTestPlan()
                .clickOkInErrorPopup();
    }

    @Test
    @Order(3)
    public void clickOnATestPlanAndSelectTestCase_Step3(IJUnitTestReporter testReporter) {
        TestRailManagerDashboardPage managerDashboardPage = browser.getPage(TestRailManagerDashboardPage.class, testReporter);
        TestRailTestPlanTable testPlanTable = managerDashboardPage.actionFill()
                .enterTestPlanNameFilter(initialCode)
                .selectStatusUntested()
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan()
                .navTestPlanTable()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun();
        testPlanTable.openTestcaseDetail(1);
    }

    @Test()
    @Order(4)
    public void verifyCanAccessTestcaseViewScreen_Expected3(IJUnitTestReporter testReporter) {
        TestRailDetailTestcaseDialog detailTestcaseDialog = browser.getPage(TestRailDetailTestcaseDialog.class, testReporter);
        assertTrue(detailTestcaseDialog.testcaseDetailModalIsDisplayed(), "Verify user can access the Test Case View dialog");
    }

    @Test()
    @Order(5)
    public void clickAddCommentButton_Step4(IJUnitTestReporter testReporter) {
        TestRailDetailTestcaseDialog detailTestcaseDialog = browser.getPage(TestRailDetailTestcaseDialog.class, testReporter);
        detailTestcaseDialog.clickAddComment();
    }

    @Test()
    @Order(6)
    public void enterTextAndClickAddCommentButton_Step5(IJUnitTestReporter testReporter) {
        TestRailAddCommentDialog addCommentDialog = browser.getPage(TestRailAddCommentDialog.class, testReporter);
        commentString = "Comment1";
        addCommentDialog.enterComment(commentString).clickAddComment();
    }

    @Test()
    @Order(7)
    public void verifyAfterAddComment_Expected5(IJUnitTestReporter testReporter) {
        TestRailDetailTestcaseDialog detailTestcaseDialog = browser.getPage(TestRailDetailTestcaseDialog.class, testReporter);
        WaitUtils.idle(5000);
        assertEquals(commentString, detailTestcaseDialog.getFirstChangeContent(), "Comment is added successful");
    }

    @Test()
    @Order(8)
    public void submitBugForTestCase_Step6(IJUnitTestReporter testReporter) {
        TestRailDetailTestcaseDialog detailTestcaseDialog = browser.getPage(TestRailDetailTestcaseDialog.class, testReporter);
        detailTestcaseDialog.clickSubmitBug().enterDescription(TextConstants.enterBugWithoutTranslation).clickSubmit().clickOk();
    }

    @Test()
    @Order(9)
    public void loginSLCMSAsLCDirector_Step7(IJUnitTestReporter testReporter) {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReporter);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
        browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReporter)
                .continueToLogin()
                .loginWithOvdLink(TextConstants.lc_Director_NCL, TextConstants.lc_Director_NCL_Password);
    }

    @Test()
    @Order(10)
    public void setLanguageToEnglish_Step8(IJUnitTestReporter testReporter) {
        LotcheckLandingPage lotcheckLandingPage = browser.getPage(LotcheckLandingPage.class, testReporter);
        LotcheckEditAccountPreferences editAccountPreferences = lotcheckLandingPage.clickOnUserNameMenu().clickMyAccount();
        String currentLanguage = editAccountPreferences.getCurrentLanguage();
        if (!currentLanguage.equalsIgnoreCase(TextConstants.english)) {
            editAccountPreferences.selectLanguage(TextConstants.english).clickSaveButton();
        } else {
            editAccountPreferences.clickCancelButton();
        }
    }

    @Test()
    @Order(11)
    public void goToLCRSubmissionAndViewIssue_Step9(IJUnitTestReporter testReporter) {
        LotcheckLandingPage lotcheckLandingPage = browser.getPage(LotcheckLandingPage.class, testReporter);
        lotcheckLandingPage.navigateToSubmissionSearch().enterValueFilter("Initial Code", initialCode)
                .selectSearchIcon()
                .selectSubmission(gameCode, "00", "00")
                .navBar()
                .clickLotcheckIssues()
                .clickMenuOption()
                .selectViewIssueDetails();
    }

    @Test()
    @Order(12)
    public void performPublishToPublicToPartner_Step10(IJUnitTestReporter testReporter) {
        LotcheckReportSubmissionLotcheckIssueDetails issueDetails = browser.getPage(LotcheckReportSubmissionLotcheckIssueDetails.class, testReporter);
        issueDetails.clickMenu()
                .selectPublishToPublic()
                .selectLciType("Bug")
                .clickPublish();
    }

    @Test()
    @Order(13)
    public void clickOnEditDetailENButton_Step11(IJUnitTestReporter testReporter) {
        LotcheckReportSubmissionLotcheckIssueDetails issueDetails = browser.getPage(LotcheckReportSubmissionLotcheckIssueDetails.class, testReporter);
        issueDetails.clickMenuAtIssueDetails().selectEditDetailsEN();
    }

    @Test()
    @Order(14)
    public void inputNewTitleAndDescriptionEnglishForBug_Step12(IJUnitTestReporter testReporter) {
        LotcheckEditDetailsDialog editDetailsDialog = browser.getPage(LotcheckEditDetailsDialog.class, testReporter);
        editDetailsDialog.editTitleEN("New bug title")
                .editDescriptionEN("New Bug with Need Translation is unchecked");
    }

    @Test()
    @Order(15)
    public void setLCThreadTranslationNeededIsTrue_Step13(IJUnitTestReporter testReporter) {
        LotcheckEditDetailsDialog editDetailsDialog = browser.getPage(LotcheckEditDetailsDialog.class, testReporter);
        editDetailsDialog.selectLCThreadTranslationNeededCheckbox("true");
    }

    @Test()
    @Order(16)
    public void setPartnerThreadTranslationNeededIsFalse_Step14(IJUnitTestReporter testReporter) {
        LotcheckEditDetailsDialog editDetailsDialog = browser.getPage(LotcheckEditDetailsDialog.class, testReporter);
        editDetailsDialog.selectPartnerThreadTranslationNeededCheckbox("false");
    }

    @Test()
    @Order(17)
    public void clickOnSaveButton_Step15(IJUnitTestReporter testReporter) {
        LotcheckEditDetailsDialog editDetailsDialog = browser.getPage(LotcheckEditDetailsDialog.class, testReporter);
        editDetailsDialog.clickSaveButton();
    }

    @Test()
    @Order(18)
    public void loginSLCMSByLCTranslation_Step16(IJUnitTestReporter testReporter) {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReporter);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
        browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReporter)
                .continueToLogin()
                .loginWithOvdLink(TextConstants.lc_Translator_NCL, TextConstants.lc_Translator_NCL_Password);
    }

    @Test()
    @Order(19)
    public void navigateToLCIssueTranslationTasks_Step17(IJUnitTestReporter testReporter) {
        LotcheckLandingPage lotcheckLandingPage = browser.getPage(LotcheckLandingPage.class, testReporter);
        lotcheckLandingPage.navigationToLotcheckIssueTranslationTasks();
    }

    @Test()
    @Order(20)
    public void clickOnNewTranslationTasksCreated_Step18(IJUnitTestReporter testReporter) {
        LotcheckIssueTranslationTasksPage translationTasksPage = browser.getPage(LotcheckIssueTranslationTasksPage.class, testReporter);
        translationTasksPage.clickViewTaskDetailsProd_addLoop(initialCode);
    }

    @Test()
    @Order(21)
    public void verifyLCIssueTranslationTaskDetailDisplayed_Expected18(IJUnitTestReporter testReporter) {
        LotcheckIssueTranslationTaskDetails translationTaskDetails = browser.getPage(LotcheckIssueTranslationTaskDetails.class, testReporter);
        String titleScreen = translationTaskDetails.getTitleScreen();
        assertEquals("Lotcheck Issue Translation", titleScreen, "Lotcheck Issue Translation Tasks detail is displayed ");
    }

    @Test()
    @Order(22)
    public void assignToMe_Step19(IJUnitTestReporter testReporter) {
        LotcheckIssueTranslationTaskDetails translationTaskDetails = browser.getPage(LotcheckIssueTranslationTaskDetails.class, testReporter);
        WaitUtils.idle(120000);
        translationTaskDetails.clickMenuOverview()
                .clickAssignTask()
                .selectAssignTo(TextConstants.current_Translator_NCL)
                .clickSave();
        assertTrue(translationTaskDetails.assignToUserSuccess("SLCMS Translator NCL"), "Verify display message assigned success");
    }

    @Test()
    @Order(23)
    public void assignToOtherUser_Step20(IJUnitTestReporter testReporter) {
        LotcheckIssueTranslationTaskDetails translationTaskDetails = browser.getPage(LotcheckIssueTranslationTaskDetails.class, testReporter);
        translationTaskDetails.clickMenuOverview()
                .clickAssignTask()
                .selectAssignTo(TextConstants.user_NguyenNgocLinh)
                .clickSave();
        error = translationTaskDetails.getAlertError();
        translationTaskDetails.clickCloseButtonOnAlert();

    }

    @Test()
    @Order(24)
    public void assignToOtherUser_Expected20(IJUnitTestReporter testReporter) {
        LotcheckIssueTranslationTaskDetails translationTaskDetails = browser.getPage(LotcheckIssueTranslationTaskDetails.class, testReporter);
        WaitUtils.idle(180000);
        translationTaskDetails.clickMenuOverview()
                .clickAssignTask()
                .selectAssignTo(TextConstants.user_NguyenNgocLinh)
                .clickSave();
        boolean success = translationTaskDetails.assignToUserSuccess(TextConstants.user_NguyenNgocLinh);
        String error_expected = "Problems assigning translation task: Re-assigning a task takes a while, please try later. (WFMS0008)";
        assertAll(() -> assertEquals(error_expected, error, "Display error (WFMS0008)when trying to immediately reassign the task"),
                () -> assertTrue(success, "waiting about a minute then trying Assign task again and resulted in a success."));
    }

    @Test()
    @Order(25)
    public void completeTranslationTask_Step21(IJUnitTestReporter testReporter) {
        LotcheckIssueTranslationTaskDetails translationTaskDetails = browser.getPage(LotcheckIssueTranslationTaskDetails.class, testReporter);
        WaitUtils.idle(180000);
        translationTaskDetails.clickMenuOverview()
                .clickAssignTask()
                .selectAssignTo(TextConstants.current_Translator_NCL)
                .clickSave();
        LotcheckIssueTranslationIssueDetails issueDetails = translationTaskDetails.clickIssueDetail()
                .clickEditPost()
                .enterPublishedTitleEN(bugDescription)
                .enterNewPublishedDescriptionJP("Edit Published Description JP")
                .clickSaveEditButton();
        browser.refresh();
        WaitUtils.idle(120000);
        issueDetails.clickTranslationCompleteButton()
                .enterCommentTranslationComplete(TextConstants.commentTranslationComplete)
                .clickSaveComment();
    }

    @Test()
    @Order(26)
    public void loginSLCMSByLCDirectorAndSetLanguageToJapanese_Step22(IJUnitTestReporter testReporter) {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReporter);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
        browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReporter)
                .continueToLogin()
                .loginWithOvdLink(TextConstants.lc_Director_NCL, TextConstants.lc_Director_NCL_Password);
        LotcheckEditAccountPreferences editAccountPreferences = lotcheckLandingPage.clickOnUserNameMenu().clickMyAccount();
        String currentLanguage = editAccountPreferences.getCurrentLanguage();
        if (currentLanguage.equalsIgnoreCase(TextConstants.english)) {
            editAccountPreferences.selectLanguage(TextConstants.japanese).clickSaveButton();
        } else {
            editAccountPreferences.clickCancelButton();
        }
    }

    @Test()
    @Order(27)
    public void goToLCRSubmissionAndViewIssue_Step23(IJUnitTestReporter testReporter) {
        LotcheckLandingPage lotcheckLandingPage = browser.getPage(LotcheckLandingPage.class, testReporter);
        lotcheckLandingPage.navigateToSubmissionSearch()
                .enterValueFilter("Initial Code", initialCode)
                .selectSearchIcon()
                .selectSubmission(gameCode, "00", "00")
                .navBar()
                .clickLotcheckIssues()
                .clickMenuOption()
                .selectViewIssueDetails();
    }

    @Test()
    @Order(28)
    public void clickOnEditDetailJAButton_Step24(IJUnitTestReporter testReporter) {
        LotcheckReportSubmissionLotcheckIssueDetails issueDetails = browser.getPage(LotcheckReportSubmissionLotcheckIssueDetails.class, testReporter);
        issueDetails.clickMenuAtIssueDetails().selectEditDetailsJA();
    }

    @Test()
    @Order(29)
    public void inputNewTitleAndDescriptionJapanese_Step25(IJUnitTestReporter testReporter) {
        LotcheckEditDetailsDialog editDetailsDialog = browser.getPage(LotcheckEditDetailsDialog.class, testReporter);
        editDetailsDialog.editTitleJA("New bug title - Japan")
                .editDescriptionJA("New Bug with Need Translation is unchecked - Japan");
    }

    @Test()
    @Order(30)
    public void clickOnSaveButton_Step26(IJUnitTestReporter testReporter) {
        LotcheckEditDetailsDialog editDetailsDialog = browser.getPage(LotcheckEditDetailsDialog.class, testReporter);
        editDetailsDialog.clickSaveButton();
    }

    @Test()
    @Order(31)
    public void loginSLCMSByLCTranslation_Step27(IJUnitTestReporter testReporter) {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReporter);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
        browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReporter)
                .continueToLogin()
                .loginWithOvdLink(TextConstants.lc_Translator_NCL, TextConstants.lc_Translator_NCL_Password);
    }

    @Test()
    @Order(32)
    public void navigateToLCIssueTranslationTasks_Step28(IJUnitTestReporter testReporter) {
        LotcheckLandingPage lotcheckLandingPage = browser.getPage(LotcheckLandingPage.class, testReporter);
        lotcheckLandingPage.navigationToLotcheckIssueTranslationTasks();
    }

    @Test()
    @Order(33)
    public void clickOnNewTranslationTasksCreated_Step29(IJUnitTestReporter testReporter) {
        LotcheckIssueTranslationTasksPage translationTasksPage = browser.getPage(LotcheckIssueTranslationTasksPage.class, testReporter);
        translationTasksPage.clickViewTaskDetailsProd_addLoop(initialCode);
    }

    @Test()
    @Order(34)
    public void verifyLCIssueTranslationTaskDetailDisplayed_Expected29(IJUnitTestReporter testReporter) {
        LotcheckIssueTranslationTaskDetails translationTaskDetails = browser.getPage(LotcheckIssueTranslationTaskDetails.class, testReporter);
        String titleScreen = translationTaskDetails.getTitleScreen();
        assertEquals("Lotcheck Issue Translation", titleScreen, "Lotcheck Issue Translation Tasks detail is displayed ");
    }

    @Test
    @Order(35)
    public void asTheTestrailAndSubmitBugForAAnotherTestCase_Step30(IJUnitTestReporter testReporter) {
        TestRailDashboardPage dashboardPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReporter);
        TestRailTestPlanTable testPlanTable = dashboardPage.navTopTestrail()
                .navigateToManagerDashboard()
                .waitingLoadingTestPlan()
                .clickOkInErrorPopup()
                .actionFill()
                .enterTestPlanNameFilter(initialCode)
                .selectStatusUntested()
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan()
                .navTestPlanTable()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun();
        testcase_2 = testPlanTable.getTestcaseName(2);
        TestRailDetailTestcaseDialog detailTestcaseDialog = testPlanTable.openTestcaseDetail(2);
        detailTestcaseDialog.clickSubmitBug().enterDescription("Bug with Need Translation is unchecked- 2nd").clickSubmit().clickOk();
    }

    @Test()
    @Order(36)
    public void loginSLCMSByLCAdmin_Step31(IJUnitTestReporter testReporter) {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReporter);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
        browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReporter)
                .continueToLogin()
                .loginWithOvdLink(slcmsLCAdmin);
    }

    @Test()
    @Order(37)
    public void goToLCRSubmissionAndPublishToPublicWithBugType_Step32(IJUnitTestReporter testReporter) {
        LotcheckLandingPage lotcheckLandingPage = browser.getPage(LotcheckLandingPage.class, testReporter);
        lotcheckLandingPage.navigateToSubmissionSearch()
                .enterValueFilter("Initial Code", initialCode)
                .selectSearchIcon()
                .selectSubmission(gameCode, "00", "00")
                .navBar()
                .clickLotcheckIssues()
                .clickMenuOfIssue(testcase_2)
                .selectViewIssueDetailsOfIssue(testcase_2)
                .clickMenu()
                .selectPublishToPublic()
                .selectLciType("Bug");
    }

    @Test()
    @Order(38)
    public void performPublishToPublicWithPartnerThreadTranslationNeededChecked_Step33(IJUnitTestReporter testReporter) {
        LotcheckPublishToPublicDialog publishToPublicDialog = browser.getPage(LotcheckPublishToPublicDialog.class, testReporter);
        publishToPublicDialog.selectPartnerThreadTranslationNeededCheckbox("true")
                .clickPublish();
    }

    @Test()
    @Order(39)
    public void loginSLCMSByLCTranslation_Step34(IJUnitTestReporter testReporter) {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReporter);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
        browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReporter)
                .continueToLogin()
                .loginWithOvdLink(TextConstants.lc_Translator_NCL, TextConstants.lc_Translator_NCL_Password);
    }

    @Test()
    @Order(40)
    public void navigateToLCIssueTranslationTaskDetail_Step35(IJUnitTestReporter testReporter) {
        LotcheckLandingPage lotcheckLandingPage = browser.getPage(LotcheckLandingPage.class, testReporter);
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = lotcheckLandingPage.navigationToLotcheckIssueTranslationTasks()
                .clickViewTaskDetailsProd_addLoop(initialCode, 1)
                .clickIssueDetail();
        int i = 0;
        while (i < 20) {
            boolean isRequired = issueTranslationIssueDetails.requiredPublishHighlightIsRemovedTitleEN();
            if (isRequired) {
                break;
            }
            browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReporter)
                    .navigationToLotcheckIssueTranslationTasks()
                    .clickViewTaskDetailsProd_addLoop(initialCode, 1)
                    .clickIssueDetail();
            i++;
        }
    }

    @Test()
    @Order(41)
    public void verifyTranslationTaskDetailDisplayed_Expected35(IJUnitTestReporter testReporter) {
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = browser.getPage(LotcheckIssueTranslationIssueDetails.class, testReporter);
        boolean requiredHighlightIsRemovedTitleEN = issueTranslationIssueDetails.requiredPublishHighlightIsRemovedTitleEN();
        boolean requiredHighlightIsRemovedTitleJP = issueTranslationIssueDetails.requiredPublishHighlightIsRemovedTitleJP();
        boolean requiredHighlightIsRemovedDescriptionEN = issueTranslationIssueDetails.requiredPublishHighlightIsRemovedDescriptionEN();
        boolean requiredHighlightIsRemovedDescriptionJP = issueTranslationIssueDetails.requiredPublishHighlightIsRemovedDescriptionJP();
        assertAll(() -> assertTrue(requiredHighlightIsRemovedTitleEN, "Published title EN should Marked as Required"),
                () -> assertTrue(requiredHighlightIsRemovedTitleJP, "Published title JP should Marked as Required"),
                () -> assertTrue(requiredHighlightIsRemovedDescriptionEN, "Published description EN should Marked as Required"),
                () -> assertTrue(requiredHighlightIsRemovedDescriptionJP, "Published description JP should Marked as Required"));
    }

    @Test()
    @Order(42)
    public void asPartnerPerformSendResolutionForLotcheckIssue_Step36(IJUnitTestReporter testReporter) {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReporter);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
        NDPHomePage homePage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReporter);
        NDPReleaseInfoIssuePage issuePage = homePage.clickSignInPage().signIn(ndpUser)
                .clickMyProducts()
                .clickProductByName(productName)
                .clickReleases()
                .selectInitialRelease()
                .nav()
                .clickIssues()
                .clickOnLotcheckIssuesTab()
                .expandUnresolve()
                .expandUnresolvedLotcheckIssueByName(testcase_2)
                .selectResolution(" Request Waiver ")
                .selectDesiredApprovalTypeAtSecondUnresolvedRequiredApproval()
                .enterReasonForLotcheckIssue(TextConstants.enterReasonForRequest)
                .clickSendResolutionOfFirstLotcheckIssue();
        issuePage.nav().clickOnUserNameMenu().clickOnSignOutButton();
    }

    @Test()
    @Order(43)
    public void loginSLCMSByLCTranslation_Step37(IJUnitTestReporter testReporter) {
        LotcheckHomePage homePage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReporter);
        homePage.continueToLogin()
                .loginWithOvdLink(TextConstants.lc_Translator_NCL, TextConstants.lc_Translator_NCL_Password);
    }

    @Test()
    @Order(44)
    public void navigateToLCIssueTranslationTaskDetail_Step38(IJUnitTestReporter testReporter) {
        LotcheckLandingPage lotcheckLandingPage = browser.getPage(LotcheckLandingPage.class, testReporter);
        lotcheckLandingPage.navigationToLotcheckIssueTranslationTasks()
                .clickViewTaskDetailsProd_addLoop(initialCode, 1)
                .clickIssueDetail();
    }

    @Test()
    @Order(45)
    public void verifyTranslationTaskDetailDisplayed_Expected38(IJUnitTestReporter testReporter) {
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = browser.getPage(LotcheckIssueTranslationIssueDetails.class, testReporter);
        boolean requiredHighlightIsRemovedTitleEN = issueTranslationIssueDetails.requiredPublishHighlightIsRemovedTitleEN();
        boolean requiredHighlightIsRemovedTitleJP = issueTranslationIssueDetails.requiredPublishHighlightIsRemovedTitleJP();
        boolean requiredHighlightIsRemovedDescriptionEN = issueTranslationIssueDetails.requiredPublishHighlightIsRemovedDescriptionEN();
        boolean requiredHighlightIsRemovedDescriptionJP = issueTranslationIssueDetails.requiredPublishHighlightIsRemovedDescriptionJP();
        boolean requiredHighlightIsDisplayedReasonForRequestEN = issueTranslationIssueDetails.requiredPublishHighlightIsDisplayedReasonForRequestEN();
        boolean requiredHighlightIsDisplayedReasonForRequestJP = issueTranslationIssueDetails.requiredPublishHighlightIsDisplayedReasonForRequestJP();
        assertAll(() -> assertTrue(requiredHighlightIsRemovedTitleEN, "Published title EN should Marked as Required"),
                () -> assertTrue(requiredHighlightIsRemovedTitleJP, "Published title JP should Marked as Required"),
                () -> assertTrue(requiredHighlightIsRemovedDescriptionEN, "Published description EN should Marked as Required"),
                () -> assertTrue(requiredHighlightIsRemovedDescriptionJP, "Published description JP should Marked as Required"),
                () -> assertTrue(requiredHighlightIsDisplayedReasonForRequestEN, "Reason For Request EN should Marked as Required"),
                () -> assertTrue(requiredHighlightIsDisplayedReasonForRequestJP, "Reason For Request JP should Marked as Required"));
    }

    @Test()
    @Order(46)
    public void loginSLCMSByLCTranslationNOE_Step39(IJUnitTestReporter testReporter) {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReporter);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
        browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReporter)
                .continueToLogin()
                .loginWithOvdLink(TextConstants.lc_Translator_NOE, TextConstants.lc_Translator_NOE_Password);
        LotcheckEditAccountPreferences editAccountPreferences = lotcheckLandingPage.clickOnUserNameMenu().clickMyAccount();
        String currentLanguage = editAccountPreferences.getCurrentLanguage();
        if (!currentLanguage.equalsIgnoreCase(TextConstants.english)) {
            editAccountPreferences.selectLanguage(TextConstants.english).clickSaveButton();
        } else {
            editAccountPreferences.clickCancelButton();
        }
    }

    @Test()
    @Order(47)
    public void navigateToLCIssueTranslationTaskPage_Step40(IJUnitTestReporter testReporter) {
        LotcheckLandingPage lotcheckLandingPage = browser.getPage(LotcheckLandingPage.class, testReporter);
        lotcheckLandingPage.navigationToLotcheckIssueTranslationTasks();
    }

    @Test()
    @Order(48)
    public void openTranslationTaskAndAssignForCurrentUser_Step41(IJUnitTestReporter testReporter) {
        LotcheckIssueTranslationTasksPage translationTasksPage = browser.getPage(LotcheckIssueTranslationTasksPage.class, testReporter);
        WaitUtils.idle(120000);
        translationTasksPage.clickViewTaskDetailsProd_addLoop(initialCode, 1)
                .clickMenuOverview()
                .clickAssignTask()
                .selectAssignTo(TextConstants.current_Translator_NOE)
                .clickSave()
                .clickIssueDetail();
    }

    @Test()
    @Order(49)
    public void SelectEditButton_Step42(IJUnitTestReporter testReporter) {
        LotcheckIssueTranslationIssueDetails issueDetails = browser.getPage(LotcheckIssueTranslationIssueDetails.class, testReporter);
        issueDetails.clickEditPost();
    }

    @Test()
    @Order(50)
    public void enterDataInLotcheckIsueDescriptionField_Step43(IJUnitTestReporter testReporter) {
        LotcheckIssueTranslationIssueDetails issueDetails = browser.getPage(LotcheckIssueTranslationIssueDetails.class, testReporter);
        issueDetails.enterPublishedDescriptionEN(bugDescription)
                .enterNewPublishedDescriptionJP("Edit Published Description JP");
    }

    @Test()
    @Order(51)
    public void verifyDataInputtedInLotcheckIsueDescriptionField_Expected43(IJUnitTestReporter testReporter) {
        LotcheckIssueTranslationIssueDetails issueDetails = browser.getPage(LotcheckIssueTranslationIssueDetails.class, testReporter);
        String publishedDescriptionEN = issueDetails.getPublishedDescriptionENValue();
        String publishedDescriptionJP = issueDetails.getPublishedDescriptionJPValue();
        assertAll(() -> assertEquals(bugDescription, publishedDescriptionEN, "Data is inputted in Published Description EN field"),
                () -> assertEquals("Edit Published Description JP", publishedDescriptionJP, "Data is inputted in Published Description JP field"));
    }

    @Test()
    @Order(52)
    public void clickOnSaveButton_Step44(IJUnitTestReporter testReporter) {
        LotcheckIssueTranslationIssueDetails issueDetails = browser.getPage(LotcheckIssueTranslationIssueDetails.class, testReporter);
        issueDetails.onlyClickSaveEditButton();
        //Disable the Save Cancel button and all fields on the Edit screen
        isSaveButtonEnabled = issueDetails.isSaveButtonEnabledAfterClickSaveEditButton();
        isCancelButtonEnabled = issueDetails.isCancelButtonEnabledAfterClickSaveEditButton();
    }

    @Test()
    @Order(53)
    public void verifyAfterClickOnSaveButton_Expected44(IJUnitTestReporter testReporter) {
        LotcheckIssueTranslationIssueDetails issueDetails = browser.getPage(LotcheckIssueTranslationIssueDetails.class, testReporter);
        WaitUtils.idle(5000);
        //2. Update data in this Translation task
        String publishedDescriptionEN = issueDetails.publishedDescriptionENAfterEdit();
        String publishedDescriptionJP = issueDetails.publishedDescriptionJPAfterEdit();

        //3. Translation Required label is hide
        boolean requiredHighlightIsRemovedDescriptionEN = issueDetails.requiredPublishHighlightIsRemovedDescriptionEN();
        boolean requiredHighlightIsRemovedDescriptionJP = issueDetails.requiredPublishHighlightIsRemovedDescriptionJP();

        //4. "Save" and "Cancel" buttons are hided for the current tab
        boolean isSaveButtonVisible = issueDetails.isSaveEditButtonVisible();
        boolean isCancelButtonVisible = issueDetails.isCancelEditButtonVisible();

        //5. All English and Japanese translations for editing on the current tab are locked
        boolean isPublishTitleENInputVisible = issueDetails.isPublishTitleENInputVisible();
        boolean isPublishTitleJPInputVisible = issueDetails.isPublishTitleJPInputVisible();
        boolean isPublishDescriptionENInputVisible = issueDetails.isPublishDescriptionENInputVisible();
        boolean isPublishDescriptionJPInputVisible = issueDetails.isPublishDescriptionJPInputVisible();
        boolean isReasonForRequestENInputVisible = issueDetails.isReasonForRequestENInputVisible();
        boolean isReasonForRequestJPInputVisible = issueDetails.isReasonForRequestJPInputVisible();

        //6. "Edit" button is displayed for the current tab
        boolean isEditButtonVisible = issueDetails.isEditButtonVisible();
        //7. "Edit button is enabled
        boolean isEditButtonEnabled = issueDetails.isEditButtonEnable();
        //8.All updated data (new value) is displayed on  LCR submission Lotcheck Issue page, Issue in NDP
        LotcheckReportSubmissionLotcheckIssueDetails lotcheckIssueDetails = issueDetails.navLandingPage().navigateToSubmissionSearch()
                .enterValueFilter("Initial Code", initialCode)
                .selectSearchIcon()
                .selectSubmission(gameCode, "00", "00")
                .navBar()
                .clickLotcheckIssues()
                .clickMenuOfIssue(testcase_2)
                .selectViewIssueDetailsOfIssue(testcase_2);
        String publishedDescriptionOnLCR = lotcheckIssueDetails.getPublishDescription();
        NDPHomePage homePage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReporter);
        NDPReleaseInfoIssuePage issuePage = homePage.clickSignInPage().signIn(ndpUser)
                .clickMyProducts()
                .clickProductByName(productName)
                .clickReleases()
                .selectInitialRelease()
                .nav()
                .clickIssues()
                .clickOnLotcheckIssuesTab()
                .expandUnresolve()
                .expandUnresolvedLotcheckIssueByName(testcase_2);
        String descriptionOnNDP = issuePage.getDescriptionByIssueName(testcase_2);
        assertAll(() -> assertFalse(isSaveButtonEnabled, "Disable the Save button"),
                () -> assertFalse(isCancelButtonEnabled, "Disable the Cancel button"),
                () -> assertEquals(bugDescription, publishedDescriptionEN, "Data is edited in Published Description EN field"),
                () -> assertEquals("Edit Published Description JP", publishedDescriptionJP, "Data is edited in Published Description JP field"),
                () -> assertFalse(requiredHighlightIsRemovedDescriptionEN, "Translation Required label for Published Description EN is hide"),
                () -> assertFalse(requiredHighlightIsRemovedDescriptionJP, "Translation Required label for Published Description JP is hide"),
                () -> assertFalse(isSaveButtonVisible, "the Save button is hide"),
                () -> assertFalse(isCancelButtonVisible, "the Cancel button is hide"),
                () -> assertFalse(isPublishTitleENInputVisible, "Publish Title EN are locked"),
                () -> assertFalse(isPublishTitleJPInputVisible, "Publish Title JP are locked"),
                () -> assertFalse(isPublishDescriptionENInputVisible, "Publish Description EN are locked"),
                () -> assertFalse(isPublishDescriptionJPInputVisible, "Publish Description JP are locked"),
                () -> assertFalse(isReasonForRequestENInputVisible, "Reason For Request EN are locked"),
                () -> assertFalse(isReasonForRequestJPInputVisible, "Reason For Request JP are locked"),
                () -> assertTrue(isEditButtonVisible, "Edit button is displayed"),
                () -> assertTrue(isEditButtonEnabled, "Edit button is enabled"),
                () -> assertEquals(bugDescription, publishedDescriptionOnLCR, "The updated data is displayed on LCR submission Lotcheck Issue page"),
                () -> assertEquals(bugDescription, descriptionOnNDP, "The updated data is displayed on Issue in NDP")
        );

    }

    @Test()
    @Order(54)
    public void asApprovalCoordinatorPerformRejectForWaiverRequest_Step45(IJUnitTestReporter testReporter) {
        NDPReleaseInfoIssuePage issuePage = browser.getPage(NDPReleaseInfoIssuePage.class, testReporter);
        issuePage.nav().clickOnUserNameMenu().clickOnSignOutButton();
        //Reject Issue Task Requested
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
                    .selectDenyRequestButton()
                    .selectOKButtonInModal()
                    .waitingForApproveSuccessful();
        }
        browser.refresh();
        NDPViewTaskPage viewTaskPage = browser.getPage(NDPViewTaskPage.class, testReporter);
        viewTaskPage.clickUserMenuDropdownList().clickSignOutButton();
    }

    @Test
    @Order(55)
    public void loginSLCMSAsLCTranslator_Step46(IJUnitTestReporter testReporter) {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReporter);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
        browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReporter)
                .continueToLogin()
                .loginWithOvdLink(TextConstants.lc_Translator_NCL, TextConstants.lc_Translator_NCL_Password);
    }

    @Test()
    @Order(56)
    public void navigateToLCIssueTranslationTaskDetail_Step47(IJUnitTestReporter testReporter) {
        LotcheckLandingPage lotcheckLandingPage = browser.getPage(LotcheckLandingPage.class, testReporter);
        lotcheckLandingPage.navigationToLotcheckIssueTranslationTasks()
                .clickViewTaskDetailsProd_addLoop(initialCode, 1)
                .clickIssueDetail();
    }

    @Test()
    @Order(57)
    public void verifyTranslationTaskDetailDisplayed_Expected47(IJUnitTestReporter testReporter) {
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = browser.getPage(LotcheckIssueTranslationIssueDetails.class, testReporter);
        boolean requiredHighlightIsDisplayedResponseForApprovalOrRejectionEN = issueTranslationIssueDetails.requiredPublishHighlightIsDisplayedResponseForApprovalOrRejectionEN();
        boolean requiredHighlightIsDisplayedResponseForApprovalOrRejectionJP = issueTranslationIssueDetails.requiredPublishHighlightIsDisplayedResponseForApprovalOrRejectionJP();
        assertAll(() -> assertTrue(requiredHighlightIsDisplayedResponseForApprovalOrRejectionEN, "Response For Approval Or Rejection EN should Marked as Required"),
                () -> assertTrue(requiredHighlightIsDisplayedResponseForApprovalOrRejectionJP, "Response For Approval Or Rejection JP should Marked as Required"));
    }
}
