package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.email.EmailReferenceManager;
import net.nintendo.automation.email.EmailTemplate;
import net.nintendo.automation.email.MailBoxManager;
import net.nintendo.automation.email.models.EmailReference;
import net.nintendo.automation.email.models.InbucketEmail;
import net.nintendo.automation.junit.TestLogger;
import net.nintendo.automation.junit.extensions.ScenarioTest;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.data.DataGen;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.email.EmailDevPage;
import net.nintendo.automation.ui.models.email.EmailPage;
import net.nintendo.automation.ui.models.email.ViewHtmlInbucketPage;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthPage;
import net.nintendo.automation.ui.models.lcms.*;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckChangeSubmissionJudgment;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportMatrix;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.ndp.*;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPCreateReleasePage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardProductInfoTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardReleasesTab;
import net.nintendo.automation.ui.models.ndp.existing_release.*;
import net.nintendo.automation.ui.models.rom.ROMCreateRom;
import net.nintendo.automation.ui.models.rom.ROMLoginPage;
import net.nintendo.automation.ui.models.testrail.*;
import net.nintendo.automation.ui.users.User;
import net.nintendo.automation.ui.users.UserManager;
import net.nintendo.automation.utils.WaitUtils;
import net.nintendo.automation_core.common_client.enums.ServiceType;
import net.nintendo.automation_core.common_client.enums.UserType;
import net.nintendo.automation_core.extent_reports.extensions.IJUnitTestReporter;
import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.Wait;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Classname: T3402483
 * Version: 1.0.0
 * Purpose:
 */
@Tag("T3402483")
@ScenarioTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3402483 extends CommonBaseTest {
    private static Browser browser;
    private static Browser romBrowser;
    private static User romUser;
    private static User ndpUser;
    private static User slcmsUser;
    private static User testrailUser;
    private static User licUser;
    private static CommonUIComponent currentPage;
    private static TestLogger logger;

    private static String productName = "Selenium_NwDW";
    private static String productNameJapanese = "Selenium_Exxs";
    private static String applicationID = "0x010001203aa1a000";
    private static String gameCode = "HAC-P-DSUYA";
    private static String initialCode = "DSUYA";
    private static String fileRomName;
    private static String productURL = "https://test2.developer.nintendo.com/group/6583984a-5525-487b-84e6-99fdf1bc791d";
    private static boolean productCreatedIsDisplayed;
    private static String statusOfInitialRelease;
    private static String expectedSubmissionDate = "2022-06-13";
    private static String scheduledLotcheckSubmissionDate;
    private static String expectedReleaseDate = "2022-08-22";
    private static String scheduledReleaseDate;
    private static String subjectEmail = "[NDP][REVERT] "+gameCode+" 00.00 "+productName+" (PhongTest1234) [DL]";
    private static boolean isToFieldIsNotPresentInLCDataManagerEmail;
    private static boolean isReceipientsIsNotVisibleInLCDataManagerEmail;
    private static boolean isToFieldIsNotPresentInLCDirectorEmail;
    private static boolean isReceipientsIsNotVisibleInLCDirectorEmail;
    private static boolean isToFieldIsNotPresentInLCOverseaCoordinatorEmail;
    private static boolean isReceipientsIsNotVisibleInLCOverseaCoordinatorEmail;
    private static boolean isToFieldIsNotPresentInLCCoordinatorEmail;
    private static boolean isReceipientsIsNotVisibleInLCCoordinatorEmail;


    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_NguyenPhong);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
        slcmsUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);
        testrailUser = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER);
        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);

//        productName = DataGen.getRandomProductName();
//        productNameJapanese = DataGen.getRandomProductName();
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

//    @Test
//    @Order(1)
//    public void signInAsAPartnerAndCreateANewProduct_Step1(IJUnitTestReporter testReport) {
//        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
//        NDPMyProductsPage ndpMyProductsPage = (NDPMyProductsPage) currentPage;
//        logger = new TestLogger(testReport);
//        currentPage.setExtentLogger(logger);
//        NDPCreateProductPage createProductPage = ndpMyProductsPage.createNewProduct();
//        createProductPage.selectProductType(TextConstants.full_Product)
//                .selectPublishingRelationship(TextConstants.third_Party_Type)
//                .selectTargetDeliveryFormat(TextConstants.digital_Type)
//                .selectDigitalJapanSalesRegion()
//                .selectProductName(productName)
//                .selectProductNameJp(productNameJapanese)
//                .selectProductNameKana(TextConstants.productKana)
//                .clickCreateButton();
//        productCreatedIsDisplayed = ndpMyProductsPage.productCreatedIsDisplayed(productName);
//        NDPProductDashboardPage ndpProductDashboardPage = ndpMyProductsPage.clickProductByName(productName);
//        productURL = "https://test2.developer.nintendo.com/group/" + ndpProductDashboardPage.getNumberURL();
//        CommonAction.issueGameCode(ndpProductDashboardPage, 80,"NO");
//        currentPage = ndpProductDashboardPage;
//    }
//
//    @Test
//    @Order(2)
//    public void verifyProductAppearOnTheListUnderMyProducts_Expected1() {
//        assertTrue(productCreatedIsDisplayed, "Verify product is displayed on the list under My Products");
//    }
//
//    @Test
//    @Order(3)
//    public void fillOutAllRequiredInformationAndSubmitTheInitialReleaseToLotcheck_Step2(IJUnitTestReporter testReport) {
//        NDPProductDashboardPage ndpProductDashboardPage = (NDPProductDashboardPage) currentPage;
//        logger = new TestLogger(testReport);
//        currentPage.setExtentLogger(logger);
//        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();
//        applicationID = ndpProductDashboardProductInfoTab.getApplicationID();
//        gameCode = ndpProductDashboardProductInfoTab.getGameCode();
//        initialCode = ndpProductDashboardProductInfoTab.getInitialCode();
//        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
//        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
//        //create release info
//        NDPReleaseInfoPage releaseInfoPage = ndpReleaseInfoPage.nav().clickReleaseInfo();
//        CommonAction.selectROMSameAllRegion(releaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);
//        // //create Feature
//        CommonAction.getFeatureOptionData(releaseInfoPage, TextConstants.allOff, TextConstants.allOff, TextConstants.allOff, false, "", false, "");
//        //Create GuildLine
//        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = ndpReleaseInfoPage.nav().clickGuidelines();
//        ndpReleaseInfoGuidelinesPage.selectCompliesWithAllOfTheAboveGuidelines()
//                .clickOnSaveButton();
//        //Create Age Rating
//        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = ndpReleaseInfoPage.nav().clickAgeRating();
//        ndpReleaseInfoAgeRating.clickOnAddStandardRatingButton()
//                .selectStandardAgeRating("CERO")
//                .clickOnNextButtonInAddStandardAgeRating()
//                .selectRatingValue("Not required")
//                .clickOnSaveButtonInEditStandardRating();
//        //create file ROM
//        romBrowser = BrowserManager.getNewInstance();
//        ROMLoginPage romLoginPage = romBrowser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN, testReport);
//        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
//        romCreateRom
//                .selectRequester(TextConstants.test_User)
//                .selectType(TextConstants.initial_Release)
//                .selectTargetDeliveryFormat(TextConstants.digital_Type)
//                .enterProductName(productName)
//                .enterApplicationId(applicationID)
//                .selectPublishingRelationShip(TextConstants.third_Party_Type)
//                .enterDisplayVersion("1.0.0")
//                .enterReleaseVersion("00")
//                .selectCERORating("CERO: Not required")
//                .clickOnCreateButton()
//                .waitIsLoaded();
//        fileRomName = romCreateRom.getRomName();
//        romCreateRom.clickOnROMFileOnROMTable(applicationID);
//        WaitUtils.idle(8000);
//        romCreateRom.clickOnUserNameMenu().clickOnLogoutButton();
//        BrowserManager.closeBrowser(romBrowser);
//        //Upload ROM
//        NDPReleaseInfoReleaseListNav nav = browser.getPage(NDPReleaseInfoReleaseListNav.class, testReport);
//        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = nav.clickROMUpload();
//        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
//        //Issues
//        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
//        CommonAction.enterReasonForRequest(issuePage);
//        nav.clickOnUserNameMenu().clickOnSignOutButton();
//        //Approval Issue TaskRequested
//        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReport).clickSignInPage().signIn(licUser);
//        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
//        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
//        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, gameCode);
//        NDPViewTaskPage ndpViewTaskPage = browser.getPage(NDPViewTaskPage.class, testReport);
//        ndpViewTaskPage.clickUserMenuDropdownList().clickSignOutButton();
//        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser)
//                .clickMyProducts()
//                .clickProductByName(productName)
//                .clickReleases()
//                .selectInitialRelease();
//        //submit product
//        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.enterDeveloperComment).clickSubmit();
//        WaitUtils.idle(3000);
//        expectedSubmissionDate = ndpReleaseInfoPage.getExpectedLotcheckSubmissionDate().substring(0,10);
//        expectedReleaseDate = ndpReleaseInfoPage.getExpectedReleaseDate().substring(0,10);
//    }
//
//    @Test
//    @Order(4)
//    public void verifyTheReleaseIsSubmittedAndTestPlansArePushToTheTesterAssignmentLotcheckQueue_Expected2(IJUnitTestReporter testReport) {
//        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
//        NDPMyProductsPage ndpMyProductsPage = (NDPMyProductsPage) currentPage;
//        logger = new TestLogger(testReport);
//        currentPage.setExtentLogger(logger);
//        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpMyProductsPage.clickProductByName(productName)
//                .clickReleases();
//        statusOfInitialRelease = ndpProductDashboardReleasesTab.getStatusRelease();
//        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReport);
//        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
//        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
//        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
//        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToTesterAssignmentQueue("NCL");
//        lotcheckQueuePage.searchByInitialCode(initialCode);
//        int iteration = 0;
//        while (iteration <= 20) {
//            iteration++;
//            lotcheckQueuePage.refreshPageSLCMS();
//            lotcheckQueuePage.searchByInitialCode(initialCode);
//            String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
//            if (numberValue.equalsIgnoreCase("1")) {
//                lotcheckQueuePage.clickPlannedTestDateNotSet();
//                break;
//            }
//        }
//        assertAll(() -> assertEquals("Submitted to Lotcheck", statusOfInitialRelease, "Verify the release is submitted"),
//                () -> assertTrue(lotcheckQueuePage.isDisplayedTestPlan(gameCode), "Verify Test plan in Tester Assignment Queue is displayed "));
//    }
//
//    @Test
//    @Order(5)
//    public void logInToTestrailAndLocateTestPlansForTheSubmission_Step3(IJUnitTestReporter testReport) {
//        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
//        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
//        logger = new TestLogger(testReport);
//        currentPage.setExtentLogger(logger);
//        TestRailDashboardPage testRailDashboardPage = testRailLoginPage.signInTestRail(testrailUser);
//        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
//        testRailManagerDashboardPage.waitingLoadingTestPlan()
//                .actionFill()
//                .enterTestPlanNameFilter(initialCode)
//                .clickOnApplyFiltersButton()
//                .waitingLoadingTestPlan();
//        currentPage = testRailManagerDashboardPage;
//    }
//
//    @Test
//    @Order(6)
//    public void verifyTestPlansForTheSubmissionExistInTestrail_Expected3(IJUnitTestReporter testReport) {
//        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
//        logger = new TestLogger(testReport);
//        currentPage.setExtentLogger(logger);
//        assertTrue(testRailManagerDashboardPage.isTestPlanDisplayed(gameCode), "Verify Test plan for the submission is displayed");
//    }
//
//    @Test
//    @Order(7)
//    public void selectAllTestCasesSetStatusPassed_Step4(IJUnitTestReporter testReport) {
//        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
//        logger = new TestLogger(testReport);
//        currentPage.setExtentLogger(logger);
//        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
//                .clickOnTheFirstTestPlanBar()
//                .waitingLoadingTestSuite()
//                .selectCheckboxOfTheFirstTestPlan()
//                .clickOnSetStatusButton()
//                .selectStatusOnSetStatusModal(TextConstants.passedStatus)
//                .clickOnUpdateStatusButtonOnSetStatusModal()
//                .waitingLoadingTestSuite();
//        browser.refresh();
//        currentPage = testRailManagerDashboardPage;
//        testRailManagerDashboardPage.waitingLoadingTestPlan()
//                .actionFill()
//                .enterTestPlanNameFilter(initialCode)
//                .clickOnApplyFiltersButton()
//                .waitingLoadingTestPlan()
//                .clickOnPlannedTestDateNotSetBar()
//                .clickOnTheFirstTestPlanBar()
//                .waitingLoadingTestSuite()
//                .expandAllTestSuites()
//                .expandAllTestRuns();
//    }
//
//    @Test
//    @Order(8)
//    public void verifyAllTestCasesAreMarkedAsPassed_Expected4(IJUnitTestReporter testReport) {
//        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
//        logger = new TestLogger(testReport);
//        currentPage.setExtentLogger(logger);
//        assertTrue(testRailManagerDashboardPage.checkStatusOfAllTestcases("Passed"), "Verify status of all test cases are marked as passed");
//    }
//
//    @Test
//    @Order(9)
//    public void submitATestPlanJudgmentOKForTheTestPlans_Step5(IJUnitTestReporter testReport) {
//        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
//        logger = new TestLogger(testReport);
//        currentPage.setExtentLogger(logger);
//        testRailManagerDashboardPage.clickOnPadlockButtonOfTheFirstTestPlan()
//                .selectTestPlanJudgment("OK")
//                .clickOnYesButtonOnConfirmationModal();
//        WaitUtils.idle(5000);
//    }
//
//    @Test
//    @Order(10)
//    public void verifyTestPlansForTheSubmissionMovedToTheJudgmentApprovalQueue_Expected5(IJUnitTestReporter testReport) {
//        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN);
//        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
//        logger = new TestLogger(testReport);
//        currentPage.setExtentLogger(logger);
//        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToJudgmentApprovalQueue("NCL");
//        WaitUtils.idle(30000);
//        lotcheckQueuePage.searchByInitialCode(initialCode);
//        int iteration = 0;
//        while (iteration <= 20) {
//            iteration++;
//            lotcheckQueuePage.refreshPageSLCMS();
//            lotcheckQueuePage.searchByInitialCode(initialCode);
//            String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
//            if (numberValue.equalsIgnoreCase("1")) {
//                break;
//            }
//        }
//        LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
//        assertTrue(lotcheckQueuePage.isDisplayedTestPlan(gameCode), "Verify Test plan for the submission moved to Judgment Approval Queue");
//        currentPage = lotcheckTestPlanRow;
//    }
//
//    // Remove step 6 + step 8
//
//    @Test
//    @Order(11)
//    public void approveAllTestPlanJudgmentForTheSubmission_Step7(IJUnitTestReporter testReport) {
//        LotcheckTestPlanRow lotcheckTestPlanRow = (LotcheckTestPlanRow) currentPage;
//        logger = new TestLogger(testReport);
//        currentPage.setExtentLogger(logger);
//        lotcheckTestPlanRow.selectApproveJudgment().clickConfirm();
//        WaitUtils.idle(5000);
//    }
//
//    @Test
//    @Order(12)
//    public void verifyTheStatusOfTheApprovedTestPlansChangeToOK_Expected7(IJUnitTestReporter testReport) {
//        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN);
//        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
//        logger = new TestLogger(testReport);
//        currentPage.setExtentLogger(logger);
//        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToFinishedQueue("NCL");
//        lotcheckQueuePage.searchByInitialCode(initialCode);
//        assertEquals("OK", lotcheckQueuePage.getJudgmentStatus(), "Verify status of test plan change to OK");
//    }
//
//    @Test
//    @Order(13)
//    public void checkTheEmailInboxOfThePartnerUser_Step9(IJUnitTestReporter testReport) throws ParseException {
//        currentPage = browser.openURL(EmailPage.class, PageState.PRE_LOGIN);
//        EmailPage emailPage = (EmailPage) currentPage;
//        logger = new TestLogger(testReport);
//        currentPage.setExtentLogger(logger);
//        scheduledLotcheckSubmissionDate = emailPage.modifyDateLayout(expectedSubmissionDate);
//        System.out.println(scheduledLotcheckSubmissionDate);
//        scheduledReleaseDate = emailPage.modifyDateLayout(expectedReleaseDate);
//        System.out.println(scheduledReleaseDate);
//    }
//
//    @Test
//    @Order(14)
//    public void verifyAEmailIsReceivedByPartnerThatTheSubmissionJudgmentHasBeenSetOK_Expected10(IJUnitTestReporter testReport) {
//        Map<String, String> data = new HashMap<>();
//        data.put("SUBMISSION_JUDGMENT", "OK");
//        data.put("GAME_CODE", gameCode);
//        data.put("REMASTER_VERSION", "00");
//        data.put("SUBMISSION_VERSION", "00");
//        data.put("PRODUCT_TITLE", productName);
//        data.put("PUBLISHER", "PhongTest1234");
//        data.put("DISTRIBUTION_TYPE", "DL");
//        data.put("PRODUCT_TITLE_JP", productNameJapanese);
//        data.put("PRODUCT_TITLE_KANA", TextConstants.productKana);
//        data.put("PRODUCT_TYPE", "Full Product (製品版)");
//        data.put("VIRTUAL_CONSOLE_PLATFORM", "N/A");
//        data.put("PLATFORM", "Nintendo Switch");
//        data.put("DISPLAY_VERSION", "1.0.0");
//        data.put("RELEASE_TYPE", "Initial Release (初回リリース)");
//        data.put("SUBMISSION_PURPOSE", "Lotcheck (ロットチェック)");
//        data.put("EXPECTED_SUBMISSION_DATE", scheduledLotcheckSubmissionDate);
//        data.put("DATE_PARAMETER", scheduledReleaseDate);
//        data.put("DELIVERY", "Digital (ダウンロード版)");
//        data.put("PHYSICAL_SALES_REGIONS", "N/A (N/A)");
//        data.put("DIGITAL_SALES_REGIONS", "Japan (日本)");
//        data.put("CARD_SIZE", "2GB (2GB)");
//        data.put("ROMS_DIFFER_BETWEEN_REGIONS", "Uses the same ROM across all regions (全地域共通のROMを使用)");
//        data.put("PRODUCT_HOME_REGION", "NCL");
//        data.put("LOTCHECK_OWNER", "NCL");
//        data.put("SUBMISSION_COMMENTS", TextConstants.enterDeveloperComment);
//        data.put("TARGET_URL", productURL);
//        EmailReference emailSubmissionJudgmentSetOK = EmailReferenceManager.getInstance().compose(EmailTemplate.TEMPLATE_EMAIL_LOTCHECK_QUEUE_SUBMISSION_JUDGEMENT_SET_OK_FOR_PARTNER, data);
//        InbucketEmail emailSubmissionJudgmentSetOKToPartner = MailBoxManager.getInstance().getFirstInbucketEmail(TextConstants.partner_NguyenPhong,
//                "[NDP][OK] "+gameCode+" 00.00 "+productName+" (PhongTest1234) [DL]");
//        System.out.println(emailSubmissionJudgmentSetOK.getBodyHtml().getEn());
//        System.out.println("===========================");
//        System.out.println(emailSubmissionJudgmentSetOKToPartner.getBody().getHtml());
//        String subjectEmailTemplate = emailSubmissionJudgmentSetOK.getSubject().getEn();
//        String subjectInbucketEmailSendToPartner = emailSubmissionJudgmentSetOKToPartner.getSubject().getText();
//        String bodyEmailTemplate = emailSubmissionJudgmentSetOK.getBodyHtml().getEn();
//        String bodyInbucketEmailSendToPartner = emailSubmissionJudgmentSetOKToPartner.getBody().getHtml();
//        assertAll(() -> assertEquals(subjectEmailTemplate, subjectInbucketEmailSendToPartner, "Verify subject email send to Partner"),
//                () -> assertEquals(bodyEmailTemplate, bodyInbucketEmailSendToPartner, "Verify body email send to Partner"));
//    }
//
//    @Test
//    @Order(15)
//    public void clickChangeLinkAndSetNewJudgmentToNG_Step11(IJUnitTestReporter testReport) {
//        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN);
//        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
//        logger = new TestLogger(testReport);
//        currentPage.setExtentLogger(logger);
//        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = lotcheckLandingPage.navigateToSubmissionSearch().waitToLoading();
//        lotcheckSubmissionSearchPage.enterValueFilter("Initial Code", initialCode)
//                .selectSearchIcon()
//                .waitToLoading();
//        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = lotcheckSubmissionSearchPage.selectSubmission(initialCode, "00", "00");
//        LotcheckChangeSubmissionJudgment lotcheckChangeSubmissionJudgment = lotcheckReportSubmissionOverview.matrix().clickChangeJudgment();
//        currentPage = lotcheckChangeSubmissionJudgment.clickConfirmButton();
//    }
//
//    @Test
//    @Order(16)
//    public void verifySubmissionJudgmentIsChangedToNG_Expected11(IJUnitTestReporter testReport) {
//        LotcheckReportMatrix lotcheckReportMatrix = (LotcheckReportMatrix) currentPage;
//        logger = new TestLogger(testReport);
//        currentPage.setExtentLogger(logger);
//        assertEquals("NG", lotcheckReportMatrix.getJudgmentResult(), "Verify submission judgment is changed to NG");
//    }
//
//    @Test
//    @Order(17)
//    public void checkTheEmailOfPartnerUser_Step12(IJUnitTestReporter testReport) {
//        currentPage = browser.openURL(EmailPage.class, PageState.PRE_LOGIN);
//        EmailPage emailPage = (EmailPage) currentPage;
//        logger = new TestLogger(testReport);
//        currentPage.setExtentLogger(logger);
//        currentPage = emailPage;
//    }
//
//    @Test
//    @Order(18)
//    public void verifyTwoNotificationsAreReceivedToPartner_Expected12(IJUnitTestReporter testReport) {
//        Map<String, String> data = new HashMap<>();
//        data.put("SUBMISSION_JUDGMENT", "NG");
//        data.put("GAME_CODE", gameCode);
//        data.put("REMASTER_VERSION", "00");
//        data.put("SUBMISSION_VERSION", "00");
//        data.put("PRODUCT_TITLE", productName);
//        data.put("PUBLISHER", "PhongTest1234");
//        data.put("DISTRIBUTION_TYPE", "DL");
//        data.put("PRODUCT_TITLE_JP", productNameJapanese);
//        data.put("PRODUCT_TITLE_KANA", TextConstants.productKana);
//        data.put("PRODUCT_TYPE", "Full Product (製品版)");
//        data.put("VIRTUAL_CONSOLE_PLATFORM", "N/A");
//        data.put("PLATFORM", "Nintendo Switch");
//        data.put("DISPLAY_VERSION", "1.0.0");
//        data.put("RELEASE_TYPE", "Initial Release (初回リリース)");
//        data.put("SUBMISSION_PURPOSE", "Lotcheck (ロットチェック)");
//        data.put("EXPECTED_SUBMISSION_DATE", scheduledLotcheckSubmissionDate);
//        data.put("DATE_PARAMETER", scheduledReleaseDate);
//        data.put("DELIVERY", "Digital (ダウンロード版)");
//        data.put("PHYSICAL_SALES_REGIONS", "N/A (N/A)");
//        data.put("DIGITAL_SALES_REGIONS", "Japan (日本)");
//        data.put("CARD_SIZE", "2GB (2GB)");
//        data.put("ROMS_DIFFER_BETWEEN_REGIONS", "Uses the same ROM across all regions (全地域共通のROMを使用)");
//        data.put("PRODUCT_HOME_REGION", "NCL");
//        data.put("LOTCHECK_OWNER", "NCL");
//        data.put("SUBMISSION_COMMENTS", TextConstants.enterDeveloperComment);
//        data.put("TARGET_URL", productURL);
//        // Email template LC101_2
//        EmailReference emailSubmissionJudgmentNotOK = EmailReferenceManager.getInstance().compose(EmailTemplate.TEMPLATE_EMAIL_LOTCHECK_QUEUE_SUBMISSION_JUDGEMENT_SET_NOT_OK_FOR_PARTNER, data);
//        InbucketEmail emailSubmissionJudgmentNotOKToPartner = MailBoxManager.getInstance().getFirstInbucketEmail(TextConstants.partner_NguyenPhong,
//                "[NDP][NG] "+gameCode+" 00.00 "+productName+" (PhongTest1234) [DL]");
//        System.out.println(emailSubmissionJudgmentNotOK.getBodyHtml().getEn());
//        System.out.println("===========================");
//        System.out.println(emailSubmissionJudgmentNotOKToPartner.getBody().getHtml());
//        String subjectEmailSubmissionJudgmentNotOKTemplate = emailSubmissionJudgmentNotOK.getSubject().getEn();
//        String subjectInbucketEmailSubmissionJudgmentNotOKSendToPartner = emailSubmissionJudgmentNotOKToPartner.getSubject().getText();
//        String bodyEmailSubmissionJudgmentNotOKTemplate = emailSubmissionJudgmentNotOK.getBodyHtml().getEn();
//        String bodyInbucketEmailSubmissionJudgmentNotOKSendToPartner = emailSubmissionJudgmentNotOKToPartner.getBody().getHtml();
//
//        //Email template LC201_2
//        EmailReference emailJudgmentRevert = EmailReferenceManager.getInstance().compose(EmailTemplate.TEMPLATE_EMAIL_PARTNER_JUDGMENT_REVERT, data);
//        InbucketEmail emailJudgmentRevertToPartner = MailBoxManager.getInstance().getFirstInbucketEmail(TextConstants.partner_NguyenPhong,
//                "[NDP][REVERT] "+gameCode+" 00.00 "+productName+" (PhongTest1234) [DL]");
//        System.out.println(emailSubmissionJudgmentNotOK.getBodyHtml().getEn());
//        System.out.println("===========================");
//        System.out.println(emailSubmissionJudgmentNotOKToPartner.getBody().getHtml());
//        String subjectEmailJudgmentRevertTemplate = emailSubmissionJudgmentNotOK.getSubject().getEn();
//        String subjectInbucketEmailJudgmentRevertSendToPartner = emailSubmissionJudgmentNotOKToPartner.getSubject().getText();
//        String bodyEmailJudgmentRevertTemplate = emailSubmissionJudgmentNotOK.getBodyHtml().getEn();
//        String bodyInbucketEmailJudgmentRevertSendToPartner = emailSubmissionJudgmentNotOKToPartner.getBody().getHtml();
//        assertAll(() -> assertEquals(subjectEmailSubmissionJudgmentNotOKTemplate, subjectInbucketEmailSubmissionJudgmentNotOKSendToPartner, "Verify subject email send to Partner"),
//                () -> assertEquals(bodyEmailSubmissionJudgmentNotOKTemplate, bodyInbucketEmailSubmissionJudgmentNotOKSendToPartner, "Verify body email send to Partner"),
//                () -> assertEquals(subjectEmailJudgmentRevertTemplate, subjectInbucketEmailJudgmentRevertSendToPartner, "Verify subject email send to Partner"),
//                () -> assertEquals(bodyEmailJudgmentRevertTemplate, bodyInbucketEmailJudgmentRevertSendToPartner, "Verify body email send to Partner"));
//    }
//
//    @Test
//    @Order(19)
//    public void verifyEmail_Step13(IJUnitTestReporter testReport) {
//        currentPage = browser.openURL(EmailPage.class, PageState.PRE_LOGIN);
//        EmailPage emailPage = (EmailPage) currentPage;
//        logger = new TestLogger(testReport);
//        currentPage.setExtentLogger(logger);
//        Map<String, String> data = new HashMap<>();
//        data.put("SUBMISSION_JUDGMENT", "NG");
//        data.put("GAME_CODE", gameCode);
//        data.put("REMASTER_VERSION", "00");
//        data.put("SUBMISSION_VERSION", "00");
//        data.put("PRODUCT_TITLE", productName);
//        data.put("PUBLISHER", "PhongTest1234");
//        data.put("MEDIA_TYPE", "DL");
//        data.put("PRODUCT_TITLE_JP", productNameJapanese);
//        data.put("PRODUCT_TITLE_KANA", TextConstants.productKana);
//        data.put("PRODUCT_TYPE", "Full Product (製品版)");
//        data.put("VIRTUAL_CONSOLE_PLATFORM", "N/A");
//        data.put("PLATFORM", "Nintendo Switch");
//        data.put("DISPLAY_VERSION", "1.0.0");
//        data.put("RELEASE_TYPE", "Initial Release (初回リリース)");
//        data.put("SUBMISSION_PURPOSE", "Lotcheck (ロットチェック)");
//        data.put("EXPECTED_SUBMISSION_DATE", scheduledLotcheckSubmissionDate);
//        data.put("DATE_PARAMETER", scheduledReleaseDate);
//        data.put("DELIVERY", "Digital (ダウンロード版)");
//        data.put("PHYSICAL_SALES_REGIONS", "N/A (N/A)");
//        data.put("DIGITAL_SALES_REGIONS", "Japan (日本)");
//        data.put("CARD_SIZE", "2GB (2GB)");
//        data.put("ROMS_DIFFER_BETWEEN_REGIONS", "Uses the same ROM across all regions (全地域共通のROMを使用)");
//        data.put("PRODUCT_HOME_REGION", "NCL");
//        data.put("LOTCHECK_OWNER", "NCL");
//        data.put("DUE_DATE", scheduledLotcheckSubmissionDate);
//        data.put("APPLICATION_ID", applicationID);
//        data.put("CARD_PRODUCTION_VERSION", "N/A");
//        data.put("DL_TITLE_VERSION", "N/A");
//        // Email template LC210 sale admin
//        EmailReference emailSaleJudgmentRevert = EmailReferenceManager.getInstance().compose(EmailTemplate.TEMPLATE_EMAIL_SALES_JUDGMENT_REVERT, data);
//        InbucketEmail emailSaleJudgmentRevertToAdmin = MailBoxManager.getInstance().getInbucketEmail(TextConstants.partner_NguyenPhong,
//                "[NDP][REVERT] "+gameCode+" 00.00 "+productName+" (PhongTest1234) [DL]");
//        System.out.println(emailSaleJudgmentRevert.getBodyHtml().getEn());
//        System.out.println("===========================");
//        System.out.println(emailSaleJudgmentRevertToAdmin.getBody().getHtml());
//        String subjectEmailSaleJudgmentRevertTemplate = emailSaleJudgmentRevert.getSubject().getEn();
//        String subjectInbucketEmailSaleJudgmentRevertSendToAdmin = emailSaleJudgmentRevertToAdmin.getSubject().getText();
//        String bodyEmailSaleJudgmentRevertTemplate = emailSaleJudgmentRevert.getBodyHtml().getEn();
//        String bodyInbucketEmailSaleJudgmentRevertSendToAdmin = emailSaleJudgmentRevertToAdmin.getBody().getHtml();
//        // Email template LC210 sale user
//        InbucketEmail emailSaleJudgmentRevertToUser = MailBoxManager.getInstance().getInbucketEmail("prdcv_nguyenphong01",
//                "[NDP][REVERT] "+gameCode+" 00.00 "+productName+" (PhongTest1234) [DL]");
//        System.out.println(emailSaleJudgmentRevert.getBodyHtml().getEn());
//        System.out.println("===========================");
//        System.out.println(emailSaleJudgmentRevertToUser.getBody().getHtml());
//        String subjectInbucketEmailSaleJudgmentRevertSendToUser = emailSaleJudgmentRevertToAdmin.getSubject().getText();
//        String bodyInbucketEmailSaleJudgmentRevertSendToUser = emailSaleJudgmentRevertToAdmin.getBody().getHtml();
//        assertAll(() -> assertEquals(subjectEmailSaleJudgmentRevertTemplate, subjectInbucketEmailSaleJudgmentRevertSendToAdmin, "Verify subject email send to Admin"),
//                () -> assertEquals(bodyEmailSaleJudgmentRevertTemplate, bodyInbucketEmailSaleJudgmentRevertSendToAdmin, "Verify body email send to Admin"),
//                () -> assertEquals(subjectEmailSaleJudgmentRevertTemplate, subjectInbucketEmailSaleJudgmentRevertSendToUser, "Verify subject email send to User"),
//                () -> assertEquals(bodyEmailSaleJudgmentRevertTemplate, bodyInbucketEmailSaleJudgmentRevertSendToUser, "Verify body email send to User"));
//
//        currentPage = browser.openURL(EmailDevPage.class, PageState.PRE_LOGIN);
//        EmailDevPage emailDevPage = (EmailDevPage) currentPage;
//        logger = new TestLogger(testReport);
//        currentPage.setExtentLogger(logger);
//        subjectEmail = "[NDP][REVERT] "+gameCode+" 00.00 "+productName+" (PhongTest1234) [DL]";
//        emailDevPage.enterMailBox("lc_data_manager_ncl");
//        WaitUtils.idle(5000);
//        emailDevPage.searchEmail(subjectEmail);
//        emailDevPage.clickOpenEmail().checkTemplateEmail();
//        currentPage = browser.openURL(EmailDevPage.class, PageState.PRE_LOGIN, testReport);
//        emailDevPage.enterMailBox("lc_director_ncl");
//        WaitUtils.idle(5000);
//        emailDevPage.searchEmail(subjectEmail);
//        emailDevPage.clickOpenEmail().checkTemplateEmail();
//        currentPage = browser.openURL(EmailDevPage.class, PageState.PRE_LOGIN, testReport);
//        emailDevPage.enterMailBox("lc_oversea_coord_ncl");
//        WaitUtils.idle(5000);
//        emailDevPage.searchEmail(subjectEmail);
//        emailDevPage.clickOpenEmail().checkTemplateEmail();
//        currentPage = browser.openURL(EmailDevPage.class, PageState.PRE_LOGIN, testReport);
//        emailDevPage.enterMailBox("lc_coordinator_ncl");
//        WaitUtils.idle(5000);
//        emailDevPage.searchEmail(subjectEmail);
//        emailDevPage.clickOpenEmail().checkTemplateEmail();
//    }
//
//    @Test
//    @Order(20)
//    public void clickToHTMLTag_Step14(IJUnitTestReporter testReport) {
//        currentPage = browser.openURL(EmailDevPage.class, PageState.PRE_LOGIN);
//        EmailDevPage emailDevPage = (EmailDevPage) currentPage;
//        logger = new TestLogger(testReport);
//        currentPage.setExtentLogger(logger);
//        currentPage = emailDevPage;
//    }
//
//    @Test
//    @Order(21)
//    public void verifyExpected_Expected14(IJUnitTestReporter testReport) {
//        EmailDevPage emailDevPage = (EmailDevPage) currentPage;
//        logger = new TestLogger(testReport);
//        currentPage.setExtentLogger(logger);
//        emailDevPage.enterMailBox("lc_data_manager_ncl");
//        WaitUtils.idle(5000);
//        emailDevPage.searchEmail(subjectEmail);
//        emailDevPage.clickOpenEmail().clickRawHTML();
//        currentPage = browser.openURL(EmailDevPage.class, PageState.PRE_LOGIN, testReport);
//        emailDevPage.enterMailBox("lc_director_ncl");
//        WaitUtils.idle(5000);
//        emailDevPage.searchEmail(subjectEmail);
//        emailDevPage.clickOpenEmail().clickRawHTML();
//        currentPage = browser.openURL(EmailDevPage.class, PageState.PRE_LOGIN, testReport);
//        emailDevPage.enterMailBox("lc_oversea_coord_ncl");
//        WaitUtils.idle(5000);
//        emailDevPage.searchEmail(subjectEmail);
//        emailDevPage.clickOpenEmail().clickRawHTML();
//        currentPage = browser.openURL(EmailDevPage.class, PageState.PRE_LOGIN, testReport);
//        emailDevPage.enterMailBox("lc_coordinator_ncl");
//        WaitUtils.idle(5000);
//        emailDevPage.clickOpenEmail().clickRawHTML();
//    }
//
//    @Test
//    @Order(22)
//    public void clickSourceTag_Step15(IJUnitTestReporter testReport) {
//        currentPage = browser.openURL(EmailDevPage.class, PageState.PRE_LOGIN);
//        EmailDevPage emailDevPage = (EmailDevPage) currentPage;
//        logger = new TestLogger(testReport);
//        currentPage.setExtentLogger(logger);
//        emailDevPage.enterMailBox("lc_data_manager_ncl");
//        WaitUtils.idle(5000);
//        emailDevPage.searchEmail(subjectEmail);
//        ViewHtmlInbucketPage viewHtmlInbucketPage =  emailDevPage.clickOpenEmail().selectSourceTag();
//        isToFieldIsNotPresentInLCDataManagerEmail = viewHtmlInbucketPage.waitElementIsNotVisible("receipient");
//        isReceipientsIsNotVisibleInLCDataManagerEmail = viewHtmlInbucketPage.waitElementIsNotVisible("To:");
//        currentPage = browser.openURL(EmailDevPage.class, PageState.PRE_LOGIN, testReport);
//        emailDevPage.enterMailBox("lc_director_ncl");
//        WaitUtils.idle(5000);
//        emailDevPage.searchEmail(subjectEmail);
//        viewHtmlInbucketPage =  emailDevPage.clickOpenEmail().selectSourceTag();
//        isToFieldIsNotPresentInLCDirectorEmail = viewHtmlInbucketPage.waitElementIsNotVisible("receipient");
//        isReceipientsIsNotVisibleInLCDirectorEmail = viewHtmlInbucketPage.waitElementIsNotVisible("To:");
//        currentPage = browser.openURL(EmailDevPage.class, PageState.PRE_LOGIN, testReport);
//        emailDevPage.enterMailBox("lc_oversea_coord_ncl");
//        WaitUtils.idle(5000);
//        emailDevPage.searchEmail(subjectEmail);
//        viewHtmlInbucketPage =  emailDevPage.clickOpenEmail().selectSourceTag();
//        isToFieldIsNotPresentInLCOverseaCoordinatorEmail = viewHtmlInbucketPage.waitElementIsNotVisible("receipient");
//        isReceipientsIsNotVisibleInLCOverseaCoordinatorEmail = viewHtmlInbucketPage.waitElementIsNotVisible("To:");
//        currentPage = browser.openURL(EmailDevPage.class, PageState.PRE_LOGIN, testReport);
//        emailDevPage.enterMailBox("lc_coordinator_ncl");
//        WaitUtils.idle(5000);
//        emailDevPage.searchEmail(subjectEmail);
//        viewHtmlInbucketPage =  emailDevPage.clickOpenEmail().selectSourceTag();
//        isToFieldIsNotPresentInLCCoordinatorEmail = viewHtmlInbucketPage.waitElementIsNotVisible("receipient");
//        isReceipientsIsNotVisibleInLCCoordinatorEmail = viewHtmlInbucketPage.waitElementIsNotVisible("To:");
//    }
//
//    @Test
//    @Order(23)
//    public void verifyNoToFieldIsPresentAndNoReceipientsAreVisble_Expected15(IJUnitTestReporter testReport) {
//        assertAll(() -> assertTrue(isToFieldIsNotPresentInLCDataManagerEmail , "Verify no To Field is present in the email notification"),
//                () -> assertTrue(isReceipientsIsNotVisibleInLCDataManagerEmail, "Verify no receipients are visible"),
//                () -> assertTrue(isToFieldIsNotPresentInLCDirectorEmail, "Verify no To Field is present in the email notification"),
//                () -> assertTrue(isReceipientsIsNotVisibleInLCDirectorEmail, "Verify no receipients are visible"),
//                () -> assertTrue(isToFieldIsNotPresentInLCOverseaCoordinatorEmail, "Verify no To Field is present in the email notification"),
//                () -> assertTrue(isReceipientsIsNotVisibleInLCOverseaCoordinatorEmail, "Verify no receipients are visible"),
//                () -> assertTrue(isToFieldIsNotPresentInLCCoordinatorEmail, "Verify no To Field is present in the email notification"),
//                () -> assertTrue(isReceipientsIsNotVisibleInLCCoordinatorEmail, "Verify no receipients are visible"));
//    }
//
//    @Test
//    @Order(24)
//    public void logInAsNCLDirectorAndNavigateToLotcheckReport_Step16(IJUnitTestReporter testReport) {
//        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN);
//        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
//        logger = new TestLogger(testReport);
//        currentPage.setExtentLogger(logger);
//        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
//        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
//        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
//        logger = new TestLogger(testReport);
//        currentPage.setExtentLogger(logger);
//        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
//        lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink("lc_director_ncl", "BasinConnectionLoadGame2");
//        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = lotcheckLandingPage.navigateToSubmissionSearch().waitToLoading();
//        lotcheckSubmissionSearchPage.enterValueFilter("Initial Code", initialCode)
//                                .selectSearchIcon()
//                                .waitToLoading();
//        currentPage = lotcheckSubmissionSearchPage.selectSubmission(gameCode,"00", "00");
//    }
//
//    @Test
//    @Order(25)
//    public void verifyTheLotcheckReportForTheSubmissionIsDisplayed_Expected16(IJUnitTestReporter testReport) {
//        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = (LotcheckReportSubmissionOverview) currentPage;
//        logger = new TestLogger(testReport);
//        currentPage.setExtentLogger(logger);
//        assertTrue(lotcheckReportSubmissionOverview.isLotcheckReportSubmissionDisplayed(), "Verify Lotcheck report submission is displayed");
//    }
//
//    @Test
//    @Order(26)
//    public void setTheNewJudgmentToNoJudgment_Step17(IJUnitTestReporter testReport) {
//        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = (LotcheckReportSubmissionOverview) currentPage;
//        logger = new TestLogger(testReport);
//        currentPage.setExtentLogger(logger);
//        LotcheckChangeSubmissionJudgment lotcheckChangeSubmissionJudgment = lotcheckReportSubmissionOverview.matrix().clickChangeJudgment();
//        currentPage = lotcheckChangeSubmissionJudgment.clickConfirmButton();
//    }
//
//    @Test
//    @Order(27)
//    public void verifySubmissionJudgmentIsChangedToNoJudgment_Expected17(IJUnitTestReporter testReport) {
//        LotcheckReportMatrix lotcheckReportMatrix = (LotcheckReportMatrix) currentPage;
//        logger = new TestLogger(testReport);
//        currentPage.setExtentLogger(logger);
//        assertEquals("No Judgment", lotcheckReportMatrix.getJudgmentResult(), "Verify submission judgment is changed to NG");
//        lotcheckReportMatrix.navLandingPage().clickOnUserNameMenu().clickOnSignoutButton();
//    }

    @Test
    @Order(28)
    public void logInAsALotcheckAdminApproveJudgmentForTheSubmission_Step18(IJUnitTestReporter testReport) {
//        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
//        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
//        logger = new TestLogger(testReport);
//        currentPage.setExtentLogger(logger);
//        TestRailDashboardPage testRailDashboardPage = testRailLoginPage.signInTestRail(testrailUser);
        currentPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN);
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
                .clickOnPadlockButtonOfTheFirstTestPlan()
                .selectTestPlanJudgment("OK")
                .clickOnYesButtonOnConfirmationModal();
        WaitUtils.idle(3000);
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToJudgmentApprovalQueue("NCL");
        WaitUtils.idle(30000);
        lotcheckQueuePage.searchByInitialCode(initialCode);
        LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
        lotcheckTestPlanRow.selectApproveJudgment().clickConfirm();
        WaitUtils.idle(5000);
    }

    @Test
    @Order(29)
    public void verifyTheStatusOfTheApprovedTestPlansChangeToOK_Expected18(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToFinishedQueue("NCL");
        lotcheckQueuePage.searchByInitialCode(initialCode);
        assertEquals("OK", lotcheckQueuePage.getJudgmentStatus(), "Verify status of test plan change to OK");
    }

    @Test
    @Order(30)
    public void createNewPatch_Step19(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage ndpMyProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPCreateReleasePage ndpCreateReleasePage = ndpMyProductsPage.clickProductByName(productName)
                .clickReleases()
                .clickCreateNewReleaseButton();
        ndpCreateReleasePage.enterDisplayVersion("1.0.1")
                .selectExpectedSubmissionDate(5)
                .selectExpectedReleaseDate(60)
                .selectFreeToPlay("NO")
                .enterDescribeTheFeaturesPlanned(TextConstants.enterDescribeTheFeaturesPlanned)
                .enterDescribeTheBugsIssuesPlanned(TextConstants.enterDescribeTheBugsIssuesPlanned);
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpCreateReleasePage.clickCreateButton();
        ndpReleaseInfoPage.selectTargetDeliveryFormat(TextConstants.both_Target_Type)
                .selectDigitalAmericasSalesRegion()
                .selectPhysicalAmericasSalesRegion()
                .selectCardSize("2 GB")
                .selectRomsThatDifferBetweenRegion(TextConstants.selectRomSameAllRegion)
                .selectLanguageUsedForTextEntry(TextConstants.english)
                .save();
        currentPage = ndpReleaseInfoPage;
    }

    @Test
    @Order(31)
    public void verifyCreateNewPatchSuccessful_Expected19(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertAll(() -> assertEquals("Patch", ndpReleaseInfoPage.getReleaseTypeValue(), "Verify create patch successfully"),
                () -> assertEquals("01", ndpReleaseInfoPage.getReleaseVersionValue(), "Verify create patch successfully"),
                () -> assertEquals("01", ndpReleaseInfoPage.getDisplayVersionValue(), "Verify create patch successfully"));
    }

    @Test
    @Order(32)
    public void navigateToAgeRating_Step20(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        currentPage = ndpReleaseInfoPage.nav().clickAgeRating();
    }

    @Test
    @Order(33)
    public void selectAddStandardRatingButton_Step21(IJUnitTestReporter testReport) {
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = (NDPReleaseInfoAgeRating) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpReleaseInfoAgeRating.clickOnAddStandardRatingButton();
    }
}
