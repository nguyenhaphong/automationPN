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
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthPage;
import net.nintendo.automation.ui.models.lcms.LotcheckHomePage;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.ui.models.lcms.LotcheckQueuePage;
import net.nintendo.automation.ui.models.lcms.LotcheckSubmissionSearchPage;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionContents;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssueDetails;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssuePartnerThread;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssues;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionMetadata;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPLicensingWaiverRequestsTab;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.NDPViewTaskPage;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPCreateDLCReleaseDialog;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardDownloadableContentTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardProductInfoTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardReleasesTab;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPMessageBoardPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoFeaturesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoIssuePage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoROMUpload;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoReleaseListNav;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPConfirmationDialog;
import net.nintendo.automation.ui.models.rom.ROMCreateRom;
import net.nintendo.automation.ui.models.rom.ROMLoginPage;
import net.nintendo.automation.ui.models.testrail.TestRailConfirmImportedContentDialog;
import net.nintendo.automation.ui.models.testrail.TestRailDashboardPage;
import net.nintendo.automation.ui.models.testrail.TestRailDetailTestcaseDialog;
import net.nintendo.automation.ui.models.testrail.TestRailLoginPage;
import net.nintendo.automation.ui.models.testrail.TestRailManagerDashboardPage;
import net.nintendo.automation.ui.models.testrail.TestRailProjectTestSuitesPage;
import net.nintendo.automation.ui.models.testrail.TestRailProjectTestSuitesTestcasePage;
import net.nintendo.automation.ui.models.testrail.TestRailSubmitBugDialog;
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

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testcase: T3404487
 * Version:
 * Author:
 * Purpose:
 */
@Tag("T3404487")
//@ScenarioTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3404487 extends CommonBaseTest {
    private static Browser browser;
    private static User romUser;
    private static User ndpUser;
    private static User slcmsUser;
    private static User testrailAdminUser_EN;
    private static User testrailAdminUser_JP;
    private static User testrailAdminUser_NOA;
    private static User testrailOverseasCoordinatorUser;
    private static User licUser;
    private static String productName;
    private static String initialCode;
    private static String applicationId;
    private static String gameCode_AOC;
    private static String romAOCName;
    private static String digestId;
    private static String romInitialName;
    private static String publishingRelationship;
    private static String titleIssue;
    private static String gameCode_Initial;
    private static String importCsvFileName_import;
    private static String importCsvFileName;
    private static CommonUIComponent currentPage;
    private static TestLogger logger;

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_NgaDTN);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
        slcmsUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);

        testrailAdminUser_EN = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER, TextConstants.trAdminNCL_EN);
        testrailAdminUser_JP = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER, TextConstants.trAdminNCL_JP);
        testrailAdminUser_NOA = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER, TextConstants.trAdminNOA_Email);
        testrailOverseasCoordinatorUser = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER, TextConstants.trOverseasNCL_Email);
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
        UserManager.releaseUser(ServiceType.NDP, licUser);
        UserManager.releaseUser(ServiceType.SLCMS, slcmsUser);
        UserManager.releaseUser(ServiceType.PMMS, testrailAdminUser_JP);
        UserManager.releaseUser(ServiceType.PMMS, testrailAdminUser_EN);
        UserManager.releaseUser(ServiceType.PMMS, testrailOverseasCoordinatorUser);
        UserManager.releaseUser(ServiceType.PMMS, testrailAdminUser_NOA);
        UserManager.releaseUser(ServiceType.SEARCH, romUser);
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
                .selectCERORating("CERO: A")
                .clickOnCreateButton()
                .waitIsLoaded();
        romInitialName = romCreateRom.getRomNameTable(applicationId);
        romCreateRom.clickOnROMFileOnROMTable(romInitialName);
        WaitUtils.idle(5000);

        romCreateRom.selectRequester(TextConstants.test_User)
                .selectType(TextConstants.aoc_Release)
                .enterApplicationId(applicationId)
                .enterReleaseVersion("0")
                .enterIndex("1")
                .enterReqAppVersion("0")
                .enterTagName("Tag name")
                .clickOnCreateButton()
                .waitIsLoaded();
        romAOCName = romCreateRom.getRomNameTable(applicationId);
        romCreateRom.clickOnROMFileOnROMTable(romAOCName);
        WaitUtils.idle(5000);

    }

    @Test()
    @Order(1)
    public void createProductAndIssueGameCode_TestCondition() {
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
        CommonAction.issueGameCode(myProductDashBoard, 60,"NO");
    }

    @Test()
    @Order(2)
    public void submitInitialToLotCheck(IJUnitTestReporter testReport) {
        NDPProductDashboardPage ndpProductDashboardPage = (NDPProductDashboardPage) currentPage;
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();
        applicationId = ndpProductDashboardProductInfoTab.getApplicationID();
        gameCode_Initial = ndpProductDashboardProductInfoTab.getGameCode();
        initialCode = ndpProductDashboardProductInfoTab.getInitialCode();
        gameCode_AOC = "HAC-M-" + initialCode;

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
        NDPHomePage homePage = (NDPHomePage) currentPage;
        NDPMyProductsPage myProductsPage = homePage.clickSignInPage().signIn(ndpUser).clickMyProducts();
        ndpProductDashboardReleasesTab = myProductsPage.clickProductByGameCode(initialCode).clickReleases();
        releaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = releaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, romInitialName);
        releaseInfoPage.nav().clickIssues().openRequiredApprovalTab().performAllIssueAtApprovalTab(false);

        NDPDevelopmentHome developmentHome = ndpReleaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        licensingWaiverRequestsTab.waitingForResultTableLoaded()
                .enterSearchingBox(gameCode_Initial)
                .clickOnMagnifyingIconButton()
                .waitingForResultTableLoaded();
        int numberOfRequestByPendingStatus = licensingWaiverRequestsTab.collectNumberOfRequestByPendingStatus();
        for (int i = 0; i < numberOfRequestByPendingStatus; i++) {
            developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton().clickOnLicensingWaiverRequestsTab();
            licensingWaiverRequestsTab.waitingForResultTableLoaded()
                    .enterSearchingBox(gameCode_Initial)
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
                .clickMyProducts().clickProductByGameCode(gameCode_Initial).clickReleases().selectInitialRelease().nav().clickSubmitButton()
                .clickSubmit();
        assertEquals(TextConstants.submitToLotcheck, ndpReleaseInfoPage.getStatusValue(), TextConstants.initialSubmitSuccess);

    }

    @Test()
    @Order(3)
    public void verifyEnglishText_ExportCSV(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailLoginPage.signInTestRail(testrailAdminUser_NOA).navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        //testRailManagerDashboardPage.clickManageAccount();
        //CommonAction.navigateToManagerDashboardWithLanguage(testRailManagerDashboardPage, TextConstants.english);
        testRailManagerDashboardPage.clickExportCSVButton();
        currentPage = testRailManagerDashboardPage;
        assertAll(() -> assertEquals(TextConstants.titleConfirm, testRailManagerDashboardPage.getTitle(), TextConstants.verifyTitleText),
                () -> assertEquals(TextConstants.notifyText, testRailManagerDashboardPage.getNotify(), TextConstants.verifyNotify),
                () -> assertEquals(TextConstants.export_from, testRailManagerDashboardPage.getExport_from(), TextConstants.verifyExportText),
                () -> assertEquals(TextConstants.version, testRailManagerDashboardPage.getVersion(), TextConstants.verifyVersionIncorrect),
                () -> assertEquals(TextConstants.suite_name, testRailManagerDashboardPage.getSuite_name(), TextConstants.verifySuiteNameIncorrect),
                () -> assertEquals(TextConstants.suite_name_value_en, testRailManagerDashboardPage.getSuite_name_value_en(), TextConstants.verifySuiteNameENIncorrect),
                () -> assertEquals(TextConstants.suite_name_value_jp, testRailManagerDashboardPage.getSuite_name_value_jp(), TextConstants.verifySuiteNameJPIncorrect),
                () -> assertEquals(TextConstants.select_language, testRailManagerDashboardPage.getSelect_language(), TextConstants.verifySelectLanguageIncorrect),
                () -> assertEquals(TextConstants.select_language_en, testRailManagerDashboardPage.getSelect_language_en(), TextConstants.verifySelectENIncorrect),
                () -> assertEquals(TextConstants.select_language_jp, testRailManagerDashboardPage.getSelect_language_jp(), TextConstants.verifySelectJPIncorrect),
                () -> assertEquals(TextConstants.export_target, testRailManagerDashboardPage.getExport_target(), TextConstants.verifyExportTargetIncorrect),
                () -> assertEquals(TextConstants.export_target_value_onlyTest, testRailManagerDashboardPage.getExport_target_value_onlyTest(), TextConstants.verifyExportOnlyTestIncorrect),
                () -> assertEquals(TextConstants.export_target_value_allTest, testRailManagerDashboardPage.getExport_target_value_allTest(), TextConstants.verifyExportAllTestIncorrect),
                () -> assertEquals(TextConstants.cancel, testRailManagerDashboardPage.getCancel(), TextConstants.verifyCancelIncorrect),
                () -> assertEquals(TextConstants.OK, testRailManagerDashboardPage.getOK(), TextConstants.verifyOKIncorrect));
    }

    @Test()
    @Order(4)
    public void checkEnglishDefaultValue_ExportCSV() {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        boolean checkDefaultSelectLanguage_English = testRailManagerDashboardPage.checkDefaultSelectLanguage_English();
        boolean checkDefaultExportTarget_onlyTest = testRailManagerDashboardPage.checkDefaultExportTarget_onlyTest();
        currentPage = testRailManagerDashboardPage.clickCancelExport().logOut();
        assertAll(() -> assertTrue(checkDefaultSelectLanguage_English, TextConstants.selectLanguageIncorrect),
                () -> assertTrue(checkDefaultExportTarget_onlyTest, TextConstants.exportTargetIncorrect));

    }

    @Test()
    @Order(5)
    public void verifyJapaneseText_ExportCSV() {
        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailLoginPage.signInTestRail(testrailAdminUser_JP).navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        //testRailManagerDashboardPage.clickManageAccount();
        //CommonAction.navigateToManagerDashboardWithLanguage(testRailManagerDashboardPage, TextConstants.japanese);
        testRailManagerDashboardPage.clickExportCSVButton();
        currentPage = testRailManagerDashboardPage;

        assertAll(() -> assertEquals(TextConstants.titleConfirm_JP, testRailManagerDashboardPage.getTitle(), TextConstants.verifyTitleText),
                () -> assertEquals(TextConstants.notifyText_JP, testRailManagerDashboardPage.getNotify(), TextConstants.verifyNotify),
                () -> assertEquals(TextConstants.export_from_JP, testRailManagerDashboardPage.getExport_from(), TextConstants.verifyExportText),
                () -> assertEquals(TextConstants.version_JP, testRailManagerDashboardPage.getVersion(), TextConstants.verifyVersionIncorrect),
                () -> assertEquals(TextConstants.suite_name_JP, testRailManagerDashboardPage.getSuite_name(), TextConstants.verifySuiteNameIncorrect),
                () -> assertEquals(TextConstants.suite_name_value_en_JP, testRailManagerDashboardPage.getSuite_name_value_en(), TextConstants.verifySuiteNameENIncorrect),
                () -> assertEquals(TextConstants.suite_name_value_jp_JP, testRailManagerDashboardPage.getSuite_name_value_jp(), TextConstants.verifySuiteNameJPIncorrect),
                () -> assertEquals(TextConstants.select_language_JP, testRailManagerDashboardPage.getSelect_language(), TextConstants.verifySelectLanguageIncorrect),
                () -> assertEquals(TextConstants.select_language_en_JP, testRailManagerDashboardPage.getSelect_language_en(), TextConstants.verifySelectENIncorrect),
                () -> assertEquals(TextConstants.select_language_jp_JP, testRailManagerDashboardPage.getSelect_language_jp(), TextConstants.verifySelectJPIncorrect),
                () -> assertEquals(TextConstants.export_target_JP, testRailManagerDashboardPage.getExport_target(), TextConstants.verifyExportTargetIncorrect),
                () -> assertEquals(TextConstants.export_target_value_onlyTest_JP, testRailManagerDashboardPage.getExport_target_value_onlyTest(), TextConstants.verifyExportOnlyTestIncorrect),
                () -> assertEquals(TextConstants.export_target_value_allTest_JP, testRailManagerDashboardPage.getExport_target_value_allTest(), TextConstants.verifyExportAllTestIncorrect),
                () -> assertEquals(TextConstants.cancel_JP, testRailManagerDashboardPage.getCancel(), TextConstants.verifyCancelIncorrect),
                () -> assertEquals(TextConstants.OK, testRailManagerDashboardPage.getOK(), TextConstants.verifyOKIncorrect));
    }

    @Test()
    @Order(6)
    public void checkJapaneseDefaultValue_ExportCSV() {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        boolean checkDefaultSelectLanguage_Japanese = testRailManagerDashboardPage.checkDefaultSelectLanguage_Japanese();
        boolean checkDefaultExportTarget_onlyTest = testRailManagerDashboardPage.checkDefaultExportTarget_onlyTest();
        currentPage = testRailManagerDashboardPage.clickCancelExport().logOut();
        assertAll(() -> assertTrue(checkDefaultSelectLanguage_Japanese, "Verify initial selection of Select Language field: incorrect "),
                () -> assertTrue(checkDefaultExportTarget_onlyTest, "Verify default value of Export Target field: incorrect "));
    }

    @Test()
    @Order(7)
    public void exportCSVSuccess() {
        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailLoginPage.signInTestRail(testrailAdminUser_EN).navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        currentPage = testRailManagerDashboardPage;
        testRailManagerDashboardPage.clickExportCSVButton();
        String numberVersion = testRailManagerDashboardPage.getNumberVersion();
        String fileName = numberVersion + "_all.csv";
        testRailManagerDashboardPage.selectAllTest().clickOKExport(fileName);
        WaitUtils.idle(3000);
        assertAll(() -> assertNotEquals(0L, testRailManagerDashboardPage.getFileSizeFromPath(fileName), "Verify download export file successfully"),
                () -> assertNotEquals(true, testRailManagerDashboardPage.confirmScreenIsDisplay()));
    }

    @Test()
    @Order(8)
    public void setStatusForTCsOfInitialTestPlan() {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(gameCode_Initial)
                .selectStatusUntested().selectStatusFailed().selectStatusPassed()
                .selectStatusNotPerformed().selectStatusReviewing();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun()
                .selectTestcaseCheckbox(1)
                .clickOnSetStatusButton()
                .selectStatusOnSetStatusModal("Passed")
                .clickOnUpdateStatusButtonOnSetStatusModal()
                .selectTestcaseCheckbox(2)
                .clickOnSetStatusButton()
                .selectStatusOnSetStatusModal("Failed")
                .clickOnUpdateStatusButtonOnSetStatusModal()
                .selectTestcaseCheckbox(3)
                .clickOnSetStatusButton()
                .selectStatusOnSetStatusModal("Reviewing")
                .clickOnUpdateStatusButtonOnSetStatusModal()
                .selectTestcaseCheckbox(4)
                .clickOnSetStatusButton()
                .selectStatusOnSetStatusModal("Not Performed")
                .clickOnUpdateStatusButtonOnSetStatusModal();
    }

    @Test()
    @Order(9)
    public void getIdOfTestcaseAndEditCsvFile() {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        String testcaseName_01 = testRailManagerDashboardPage.getTestcaseName(1);
        String testcaseName_02 = testRailManagerDashboardPage.getTestcaseName(2);
        String testcaseName_03 = testRailManagerDashboardPage.getTestcaseName(3);
        String testcaseName_04 = testRailManagerDashboardPage.getTestcaseName(4);
        String testSuiteName = testRailManagerDashboardPage.getTestSuiteName(testcaseName_01); //check if testcase no same test suite
        TestRailDashboardPage testRailDashboardPage = testRailManagerDashboardPage.topNav().navigateToDashboard();
        TestRailProjectTestSuitesPage testSuitesPage = testRailDashboardPage.openProject(gameCode_Initial)
                .openTestSuite(testSuiteName);
        TestRailProjectTestSuitesTestcasePage testcasePage = testSuitesPage.openTestCase(testcaseName_01);
        String idTestcase_01 = testcasePage.getTestcaseID();
        String idTestcase_02 = testcasePage.backToTestSuite().openTestCase(testcaseName_02).getTestcaseID();
        String idTestcase_03 = testcasePage.backToTestSuite().openTestCase(testcaseName_03).getTestcaseID();
        String idTestcase_04 = testcasePage.backToTestSuite().openTestCase(testcaseName_04).getTestcaseID();
        testRailManagerDashboardPage = testcasePage.backToDashboard().navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
        importCsvFileName = "Checklist Export " + gameCode_Initial + " - v00_00.csv";
        importCsvFileName_import = "\"Checklist Export " + gameCode_Initial + " - v00_00.csv\"";
        testRailManagerDashboardPage.editCsvFile(importCsvFileName, idTestcase_01, idTestcase_02, idTestcase_03, idTestcase_04);
    }


    @Test()
    @Order(10)
    public void tickOnCheckBoxOfTestPlan() {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(gameCode_Initial)
                .selectStatusUntested().selectStatusFailed().selectStatusPassed()
                .selectStatusNotPerformed().selectStatusReviewing();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan();
    }

    @Test()
    @Order(11)
    public void importCVSFromLocal() {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        TestRailConfirmImportedContentDialog confirmImportedContentDialog = testRailManagerDashboardPage.clickImportCsv()
                .selectFileInPC(importCsvFileName_import)
                .clickOK();
        currentPage = confirmImportedContentDialog;
        assertTrue(confirmImportedContentDialog.valueOfResultsIsRedText(), "Verify Import Results table shown data in red text for testcase above");
    }

    @Test()
    @Order(12)
    public void completeTheRemainingStepsOfImportCsv() {
        TestRailConfirmImportedContentDialog confirmImportedContentDialog = (TestRailConfirmImportedContentDialog) currentPage;
        TestRailManagerDashboardPage testRailManagerDashboardPage = confirmImportedContentDialog.clickOk().clickOK();
        currentPage = testRailManagerDashboardPage;
        testRailManagerDashboardPage.expandAllTestSuites().clickOnTheFirstTestRun();
        TestRailDetailTestcaseDialog detailTestcaseDialog = testRailManagerDashboardPage.openTestcaseDetail(1);
        boolean importHistoryIsNotDisplayed_01 = detailTestcaseDialog.importHistoryIsNotDisplayed(gameCode_Initial);
        String status_01 = detailTestcaseDialog.getCurrentStatusValue();
        String systemUsed_01 = detailTestcaseDialog.getCurrentSystemUsedValue();
        String mediaType_01 = detailTestcaseDialog.getCurrentMediaTypeValue();
        detailTestcaseDialog.clickClose().openTestcaseDetail(2);
        boolean importHistoryIsNotDisplayed_02 = detailTestcaseDialog.importHistoryIsNotDisplayed(gameCode_Initial);
        String status_02 = detailTestcaseDialog.getCurrentStatusValue();
        String systemUsed_02 = detailTestcaseDialog.getCurrentSystemUsedValue();
        String mediaType_02 = detailTestcaseDialog.getCurrentMediaTypeValue();
        detailTestcaseDialog.clickClose().openTestcaseDetail(3);
        boolean importHistoryIsNotDisplayed_03 = detailTestcaseDialog.importHistoryIsNotDisplayed(gameCode_Initial);
        String status_03 = detailTestcaseDialog.getCurrentStatusValue();
        String systemUsed_03 = detailTestcaseDialog.getCurrentSystemUsedValue();
        String mediaType_03 = detailTestcaseDialog.getCurrentMediaTypeValue();
        detailTestcaseDialog.clickClose().openTestcaseDetail(4);
        boolean importHistoryIsNotDisplayed_04 = detailTestcaseDialog.importHistoryIsNotDisplayed(gameCode_Initial);
        String status_04 = detailTestcaseDialog.getCurrentStatusValue();
        String systemUsed_04 = detailTestcaseDialog.getCurrentSystemUsedValue();
        String mediaType_04 = detailTestcaseDialog.getCurrentMediaTypeValue();
        detailTestcaseDialog.clickClose();
        assertAll(() -> assertTrue(importHistoryIsNotDisplayed_01, "Verify import history is not displayed"),
                () -> assertTrue(importHistoryIsNotDisplayed_02, "Verify import history is not displayed"),
                () -> assertTrue(importHistoryIsNotDisplayed_03, "Verify import history is not displayed"),
                () -> assertTrue(importHistoryIsNotDisplayed_04, "Verify import history is not displayed"),
                () -> assertEquals("Passed", status_01, "Verify Data of Result will not reflected to corresponding the test case"),
                () -> assertEquals("Optional", systemUsed_01, "Verify Data of System Used will not reflected to corresponding the test case"),
                () -> assertEquals("Optional", mediaType_01, "Verify Data of Media Type will not reflected to corresponding the test case"),
                () -> assertEquals("Failed", status_02, "Verify Data of Result will not reflected to corresponding the test case"),
                () -> assertEquals("Optional", systemUsed_02, "Verify Data of System Used will not reflected to corresponding the test case"),
                () -> assertEquals("Optional", mediaType_02, "Verify Data of Media Type will not reflected to corresponding the test case"),
                () -> assertEquals("Reviewing", status_03, "Verify Data of Result will not reflected to corresponding the test case"),
                () -> assertEquals("Optional", systemUsed_03, "Verify Data of System Used will not reflected to corresponding the test case"),
                () -> assertEquals("Optional", mediaType_03, "Verify Data of Media Type will not reflected to corresponding the test case"),
                () -> assertEquals("Not Performed", status_04, "Verify Data of Result will not reflected to corresponding the test case"),
                () -> assertEquals("Optional", systemUsed_04, "Verify Data of System Used will not reflected to corresponding the test case"),
                () -> assertEquals("Optional", mediaType_04, "Verify Data of Media Type will not reflected to corresponding the test case"));

    }


    @Test()
    @Order(13)
    public void performImportCsv_Reviewing() {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .expandAllTestRuns();
        String testSuiteName = "HAC_実機互換確認";
        String testcaseName = testRailManagerDashboardPage.getTestCaseNameOfHacCompatibilitySuite(testSuiteName, 0);
        String testRunName = testRailManagerDashboardPage.getTestRunName(testSuiteName);
        TestRailDashboardPage testRailDashboardPage = testRailManagerDashboardPage.topNav().navigateToDashboard();
        TestRailProjectTestSuitesTestcasePage testcasePage = testRailDashboardPage.openProject(gameCode_Initial)
                .openTestSuite(testSuiteName)
                .openTestCase_Compatibility(testRunName, testcaseName);
        String testcaseId = testcasePage.getTestcaseID();
        testRailManagerDashboardPage = testcasePage.backToDashboard().navTopTestrail().navigateToManagerDashboard()
                .checkErrorPopup();
        testRailManagerDashboardPage.editCsvFile_Compatibility(importCsvFileName, testcaseId, "Reviewing");
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(gameCode_Initial)
                .selectStatusUntested().selectStatusFailed().selectStatusPassed()
                .selectStatusNotPerformed().selectStatusReviewing();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan()
                .clickImportCsv()
                .selectFileInPC(importCsvFileName_import)
                .clickOK()
                .clickOk()
                .clickOK();
    }

    @Test()
    @Order(14)
    public void openTestCaseImportedAndVerifyHistory_Reviewing() {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        testRailManagerDashboardPage.clickOnTheFirstTestPlanBar().waitingLoadingTestPlan().expandAllTestSuites().expandAllTestRuns();
        String testSuiteName = "HAC_実機互換確認";
        TestRailDetailTestcaseDialog detailTestcaseDialog = testRailManagerDashboardPage.clickOnTestcase(testSuiteName, 0);
        String newMediaType_EN = detailTestcaseDialog.getNewMediaTypeValue(gameCode_Initial);
        String oldMediaType_EN = detailTestcaseDialog.getOldMediaTypeValue(gameCode_Initial);
        String newSystemUsed_EN = detailTestcaseDialog.getNewSystemUsedValue(gameCode_Initial);
        String oldSystemUsed_EN = detailTestcaseDialog.getOldSystemUsedValue(gameCode_Initial);
        detailTestcaseDialog.clickClose_Old();
        //CommonAction.navigateToManagerDashboardWithLanguage(testRailManagerDashboardPage, TextConstants.japanese);
        testRailManagerDashboardPage.logOut().signInTestRail(testrailAdminUser_JP).navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(gameCode_Initial)
                .selectStatusUntested().selectStatusFailed().selectStatusPassed()
                .selectStatusNotPerformed().selectStatusReviewing();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar().waitingLoadingTestSuite().expandAllTestSuites().expandAllTestRuns();
        detailTestcaseDialog = testRailManagerDashboardPage.clickOnTestcase(testSuiteName, 0);
        String newMediaType_JP = detailTestcaseDialog.getNewMediaTypeValue(gameCode_Initial);
        String oldMediaType_JP = detailTestcaseDialog.getOldMediaTypeValue(gameCode_Initial);
        String newSystemUsed_JP = detailTestcaseDialog.getNewSystemUsedValue(gameCode_Initial);
        String oldSystemUsed_JP = detailTestcaseDialog.getOldSystemUsedValue(gameCode_Initial);
        detailTestcaseDialog.clickClose_Old();
        testRailManagerDashboardPage.logOut().signInTestRail(testrailAdminUser_EN).navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        // CommonAction.navigateToManagerDashboardWithLanguage(testRailManagerDashboardPage, TextConstants.english);
        assertAll(() -> assertEquals("Test_Media_Type_01", newMediaType_JP, "Verify New value (JP) of Media type is displayed follow csv data"),
                () -> assertEquals("eShop", oldMediaType_JP, "Verify Old value (JP) of Media type from before the import"),
                () -> assertEquals("Test_System_Used_01", newSystemUsed_JP, "Verify New value (JP) of System used is displayed follow csv data"),
                () -> assertEquals("1", oldSystemUsed_JP, "Verify Old value (JP) of System used from before the import"),
                () -> assertEquals("Test_Media_Type_01", newMediaType_EN, "Verify New value (EN) of Media type is displayed follow csv data"),
                () -> assertEquals("eShop", oldMediaType_EN, "Verify Old value (EN) of Media type from before the import"),
                () -> assertEquals("Test_System_Used_01", newSystemUsed_EN, "Verify New value (JP) of System used is displayed follow csv data"),
                () -> assertEquals("1", oldSystemUsed_EN, "Verify Old value (EN) of System used from before the import"));
    }


    @Test()
    @Order(15)
    public void performImportCsv_Untested() {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(gameCode_Initial)
                .selectStatusUntested().selectStatusFailed().selectStatusPassed()
                .selectStatusNotPerformed().selectStatusReviewing();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite().expandAllTestSuites().expandAllTestRuns();
        String testSuiteName = "HAC_実機互換確認";
        String testcaseName = testRailManagerDashboardPage.getTestCaseNameOfHacCompatibilitySuite(testSuiteName, 1);
        String testRunName = testRailManagerDashboardPage.getTestRunName(testSuiteName); //edit
        testRailManagerDashboardPage.selectTestcaseCheckbox_Compatibility(testSuiteName, 2)
                .clickOnSetStatusButton()
                .selectStatusOnSetStatusModal("Passed")
                .clickOnUpdateStatusButtonOnSetStatusModal();
        TestRailDashboardPage testRailDashboardPage = testRailManagerDashboardPage.topNav().navigateToDashboard();
        TestRailProjectTestSuitesTestcasePage testcasePage = testRailDashboardPage.openProject(gameCode_Initial)
                .openTestSuite(testSuiteName)
                .openTestCase_Compatibility(testRunName, testcaseName);
        String testcaseId = testcasePage.getTestcaseID();
        testRailManagerDashboardPage = testcasePage.backToDashboard().navTopTestrail().navigateToManagerDashboard()
                .checkErrorPopup();
        testRailManagerDashboardPage.editCsvFile_Compatibility(importCsvFileName, testcaseId, "Untested");
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(gameCode_Initial)
                .selectStatusUntested().selectStatusFailed().selectStatusPassed()
                .selectStatusNotPerformed().selectStatusReviewing();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan()
                .clickImportCsv_previousImported().clickOK()
                .selectFileInPC(importCsvFileName_import)
                .clickOK()
                .clickOk()
                .clickOK();
    }

    @Test()
    @Order(16)
    public void openTestCaseImportedAndVerifyHistory_Untested() {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        testRailManagerDashboardPage.clickOnTheFirstTestPlanBar().expandAllTestSuites().expandAllTestRuns();
        String testSuiteName = "HAC_実機互換確認";
        TestRailDetailTestcaseDialog detailTestcaseDialog = testRailManagerDashboardPage.clickOnTestcase(testSuiteName, 0);
        String newMediaType_EN = detailTestcaseDialog.getNewMediaTypeValue(gameCode_Initial);
        String oldMediaType_EN = detailTestcaseDialog.getOldMediaTypeValue(gameCode_Initial);
        String newSystemUsed_EN = detailTestcaseDialog.getNewSystemUsedValue(gameCode_Initial);
        String oldSystemUsed_EN = detailTestcaseDialog.getOldSystemUsedValue(gameCode_Initial);
        detailTestcaseDialog.clickClose_Old();
        testRailManagerDashboardPage.logOut().signInTestRail(testrailAdminUser_JP).navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();

        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(gameCode_Initial)
                .selectStatusUntested().selectStatusFailed().selectStatusPassed()
                .selectStatusNotPerformed().selectStatusReviewing();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar().waitingLoadingTestSuite().expandAllTestSuites().expandAllTestRuns();
        detailTestcaseDialog = testRailManagerDashboardPage.clickOnTestcase(testSuiteName, 0);
        String newMediaType_JP = detailTestcaseDialog.getNewMediaTypeValue(gameCode_Initial);
        String oldMediaType_JP = detailTestcaseDialog.getOldMediaTypeValue(gameCode_Initial);
        String newSystemUsed_JP = detailTestcaseDialog.getNewSystemUsedValue(gameCode_Initial);
        String oldSystemUsed_JP = detailTestcaseDialog.getOldSystemUsedValue(gameCode_Initial);
        detailTestcaseDialog.clickClose_Old();
        //CommonAction.navigateToManagerDashboardWithLanguage(testRailManagerDashboardPage, TextConstants.english);

        testRailManagerDashboardPage.logOut();
        assertAll(() -> assertEquals("Test_Media_Type_01", newMediaType_JP, "Verify New value (JP) of Media type is displayed follow csv data"),
                () -> assertEquals("eShop", oldMediaType_JP, "Verify Old value (JP) of Media type from before the import"),
                () -> assertEquals("Test_System_Used_01", newSystemUsed_JP, "Verify New value (JP) of System used is displayed follow csv data"),
                () -> assertEquals("1", oldSystemUsed_JP, "Verify Old value (JP) of System used from before the import"),
                () -> assertEquals("Test_Media_Type_01", newMediaType_EN, "Verify New value (EN) of Media type is displayed follow csv data"),
                () -> assertEquals("eShop", oldMediaType_EN, "Verify Old value (EN) of Media type from before the import"),
                () -> assertEquals("Test_System_Used_01", newSystemUsed_EN, "Verify New value (JP) of System used is displayed follow csv data"),
                () -> assertEquals("1", oldSystemUsed_EN, "Verify Old value (EN) of System used from before the import"));
    }

    @Test()
    @Order(17)
    public void createAOCArchive(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByGameCode(initialCode);
        NDPProductDashboardDownloadableContentTab downloadableContentTab = ndpProductDashboardPage.clickDownloadableContent();
        NDPCreateDLCReleaseDialog createDLCReleaseDialog = downloadableContentTab.clickCreateDLCArchive();
        createDLCReleaseDialog.selectExpectedLotcheckSubmissionDate(15)
                .selectExpectedReleaseDate(90)
                .clickCreate();
        currentPage = downloadableContentTab;
        assertEquals("The DLC archive was created successfully", downloadableContentTab.getMessageSuccess(), "Verify create an AOC archive successfully");
    }

    @Test()
    @Order(18)
    public void fillOutInformationAndSubmitAocRelease() {
        NDPProductDashboardDownloadableContentTab downloadableContentTab = (NDPProductDashboardDownloadableContentTab) currentPage;
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
                .enterNameHowToCheckDLC(TextConstants.enterNameHowToCheckDLC)
                .enterExplainHowToCheckDLC(TextConstants.enterExplainHowToCheckDLC)
                .clickOnSaveButton();
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = ndpReleaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, romAOCName);
        ndpReleaseInfoPage.nav().clickIssues().openRequiredApprovalTab().performAllIssueAtApprovalTab(false);

        NDPDevelopmentHome developmentHome = ndpReleaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        licensingWaiverRequestsTab.waitingForResultTableLoaded()
                .enterSearchingBox(gameCode_Initial)
                .clickOnMagnifyingIconButton()
                .waitingForResultTableLoaded();
        int numberOfRequestByPendingStatus = licensingWaiverRequestsTab.collectNumberOfRequestByPendingStatus();
        for (int i = 0; i < numberOfRequestByPendingStatus; i++) {
            developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton().clickOnLicensingWaiverRequestsTab();
            licensingWaiverRequestsTab.waitingForResultTableLoaded()
                    .enterSearchingBox(gameCode_Initial)
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
                .clickMyProducts().clickProductByGameCode(gameCode_Initial).clickDownloadableContent().clickDLCReleaseByArchiveNo("00").nav().clickSubmitButton()
                .clickSubmit();
        assertEquals(TextConstants.submitToLotcheck, ndpReleaseInfoPage.getStatusAOCValue(), "Verify submit aoc successfully");

    }


    @Test()
    @Order(19)
    public void loginSLCMSAsLcAdminAndNavigateToLotCheckReport(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage landingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
        LotcheckSubmissionSearchPage submissionSearchPage = landingPage.searchProduct(initialCode);
        gameCode_AOC = "HAC-M-" + initialCode;
        LotcheckReportSubmissionOverview submissionOverview = submissionSearchPage.selectSubmission(gameCode_AOC, "00", "00");
        currentPage = submissionOverview;
        assertAll(() -> assertEquals("Overview Submission: LCR - LCMS", submissionOverview.getPageTitle(), "Verify access lcr successfully"),
                () -> assertEquals(gameCode_AOC, submissionOverview.getGameCodeOfSubmission(), "Verify access correct Lcr of aoc release created"));
    }

    @Test()
    @Order(20)
    public void openMetadataTabThenExpandXmlFileAndGetDigestID() {
        LotcheckReportSubmissionOverview submissionOverview = (LotcheckReportSubmissionOverview) currentPage;
        LotcheckReportSubmissionMetadata submissionMetadata = submissionOverview.navBar().clickMetadata();
        submissionMetadata.clickExpandXmlFile("cnmt.xml");
        digestId = submissionMetadata.getValueOnCnmtXmlFile("18");
    }

    @Test()
    @Order(21)
    public void openContentsTab() {
        LotcheckReportSubmissionOverview submissionOverview = (LotcheckReportSubmissionOverview) currentPage;
        LotcheckReportSubmissionContents submissionContents = submissionOverview.navBar().clickContents();
        currentPage = submissionContents;
        assertAll(() -> assertEquals("0", submissionContents.getLblReleaseVerValue(), "Verify release ver"),
                () -> assertEquals("0", submissionContents.getLblPrivateVerValue(), "Verify private ver"),
                () -> assertEquals("0 (Release: 0 Private: 0)", submissionContents.getLblRequiredApplicationVerValue(), "Verify required application ver"),
                () -> assertTrue(submissionContents.archiveNoColumnIsNotVisible(), "Verify Archive No. column is not displayed"));
    }

    @Test()
    @Order(22)
    public void verifyDigestIdOnContentTab() {
        LotcheckReportSubmissionContents submissionContents = (LotcheckReportSubmissionContents) currentPage;
        String romIdHashValue = submissionContents.getLblRomIdHashValue();
        assertEquals(digestId, romIdHashValue, "Verify the Digest ID appear for one of the AOC items in the list (same as romIdHash)");

    }

    @Test()
    @Order(23)
    public void loginTestRailAsLcAdminAndAssignAOCTestPlan(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailLoginPage.signInTestRail(testrailAdminUser_EN).navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(gameCode_AOC)
                .selectStatusUntested().selectStatusPassed();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan()
                .clickAssignTo()
                .selectSetValueAssignToRadio()
                .selectUser("Overseas Coordinator (NCL)")
                .clickOk()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .expandAllTestRuns();
        currentPage = testRailManagerDashboardPage;
        assertTrue(testRailManagerDashboardPage.allTestCasesAreAssignCorrectly("Overseas Coordinator (NCL)"), "Verify all testcases are assigned correctly");
    }

    @Test()
    @Order(24)
    public void loginTestRailAsOverseasCoordinatorNCL() {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        testRailManagerDashboardPage.logOut().signInTestRail(testrailOverseasCoordinatorUser).navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        assertEquals("Manager Dashboard", testRailManagerDashboardPage.getPageTitle(), "Verify Overseas Coordinator NCL login successfully");
    }

    @Test()
    @Order(25)
    public void submitBug() {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(gameCode_AOC)
                .selectStatusUntested().selectStatusPassed();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun();
        TestRailSubmitBugDialog submitBugDialog = testRailManagerDashboardPage.clickOnTheFirstTestcase().clickSubmitBug();
        submitBugDialog.enterDescription(TextConstants.enterDescription).clickSubmit();
        String messageSuccess = submitBugDialog.getMessageSuccessfully();
        submitBugDialog.clickOk().clickClose().logOut();
        assertEquals("Your Issue has been submitted successfully.", messageSuccess, "Verify submit bug successfully");
    }

    @Test()
    @Order(26)
    public void publicBugToPartnerWithTypeAsSubmissionIssue(IJUnitTestReporter testReport) {
        loginSLCMSAndNavigateToLotCheckReport(testReport);
        LotcheckReportSubmissionOverview submissionOverview = (LotcheckReportSubmissionOverview) currentPage;
        LotcheckReportSubmissionLotcheckIssues submissionLotcheckIssues = submissionOverview.navBar().clickLotcheckIssues();
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails = submissionLotcheckIssues.clickMenuOption()
                .selectViewIssueDetails();

        submissionLotcheckIssueDetails.performPublishIssue(TextConstants.submissionIssue);
        titleIssue = submissionLotcheckIssueDetails.getTitleOfIssueDetail();
        String partnerThreadStatus = submissionLotcheckIssueDetails.getPartnerThreadStatus();
        submissionLotcheckIssueDetails.refreshPageSLCMS().logOut();
        assertEquals("NEEDS RESPONSE", partnerThreadStatus, "Verify partner Thread Status after publish");
    }

    @Test()
    @Order(27)
    public void navigateToLotCheckIssuesInNDPSite(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        currentPage = myProductsPage.clickProductByGameCode(initialCode).clickDownloadableContent().clickDLCReleaseByArchiveNo("00").nav().clickIssues()
                .clickOnLotcheckIssuesTab();

    }

    @Test()
    @Order(28)
    public void viewThreadAndAddMessage() {
        NDPReleaseInfoIssuePage releaseInfoIssuePage = (NDPReleaseInfoIssuePage) currentPage;
        String linkImage = "http://spdlybra.nintendo.co.jp/jump.php?url=https%3A%2F%2Fcdn.discordapp.com%2Fattachments%2F326871955708051457%2F837429152373800970%2FIMG_20190720_133153__01.jpg";
        NDPMessageBoardPage messageBoardPage = releaseInfoIssuePage.expandUnresolve()
                .expandIssue()
                .clickViewThread()
                .enterDescription(TextConstants.enterDescription)
                .addImageOnDescription(linkImage)
                .clickPost();
        String textOnMessage = messageBoardPage.getContentTextOfMessage("01.jpg");
        boolean brokenImageIsDisplayed = messageBoardPage.brokenImageIsDisplayed("01.jpg");
        messageBoardPage.clickUserMenuDropdownList().clickSignOutButton();
        assertAll(() -> assertEquals("Test description partner", textOnMessage, "Verify the rest of the message should appear without issue"),
                () -> assertTrue(brokenImageIsDisplayed, "Verify display a broken image icon"));

    }

    @Test()
    @Order(29)
    public void verifyMessageOnSLCMS(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage landingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);

        LotcheckSubmissionSearchPage submissionSearchPage = landingPage.searchProduct(initialCode);
        LotcheckReportSubmissionOverview submissionOverview = submissionSearchPage.selectSubmission(gameCode_AOC, "00", "00");
        LotcheckReportSubmissionLotcheckIssues submissionLotcheckIssues = submissionOverview.navBar().clickLotcheckIssues();
        LotcheckReportSubmissionLotcheckIssuePartnerThread partnerThread = submissionLotcheckIssues.clickMenuOption().selectViewIssueDetails()
                .clickPartnerThreadTab();
        String textOnMessage = partnerThread.getContentTextOfMessage("01.jpg");
        boolean brokenImageIsDisplayed = partnerThread.brokenImageIsDisplayed("01.jpg");
        assertAll(() -> assertEquals("Test description partner", textOnMessage, "Verify the rest of the message should appear without issue"),
                () -> assertTrue(brokenImageIsDisplayed, "Verify display a broken image icon"));

    }

    @Test()
    @Order(30)
    public void loginNdpAndVerifyContentOnConfirmationPopup(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        NDPHomePage homePage = (NDPHomePage) currentPage;
        NDPMyProductsPage myProductsPage = homePage.clickSignInPage().signIn(ndpUser).clickMyProducts();
        NDPReleaseInfoPage releaseInfoPage = myProductsPage.clickProductByGameCode(initialCode).clickDownloadableContent().clickDLCReleaseByArchiveNo("00");
        NDPConfirmationDialog confirmationDialog = releaseInfoPage.nav().clickCancelSubmit();
        String cancelDialogMessage_EN = confirmationDialog.getCancelDialogMessage();
        confirmationDialog.clickClose().clickUserMenuDropdownList().selectJapan()
                .clickCancelSubmit();
        String cancelDialogMessage_JP = confirmationDialog.getCancelDialogMessage();
        currentPage = confirmationDialog;
        assertAll(() -> assertEquals("Are you sure you want to cancel?", cancelDialogMessage_EN, TextConstants.cancelDialogMessage_EN_actual),
                () -> assertEquals("本当に Cancel をしてよろしいですか？", cancelDialogMessage_JP, TextConstants.cancelDialogMessage_JP_actual));
    }

    @Test()
    @Order(31)
    public void confirmCloseConfirmationDialogSuccessfully() {
        NDPConfirmationDialog confirmationDialog = (NDPConfirmationDialog) currentPage;
        NDPReleaseInfoReleaseListNav listNav = confirmationDialog.clickClose();
        currentPage = listNav;
        assertTrue(listNav.confirmationDialogIsClosed(), "Verify close confirmation popup successfully");

    }

    @Test()
    @Order(32)
    public void navigateToLotCheckIssuesAndConfirmIssues() {
        NDPReleaseInfoReleaseListNav listNav = (NDPReleaseInfoReleaseListNav) currentPage;
        NDPReleaseInfoIssuePage issuePage = listNav.clickUserMenuDropdownList().selectEnglish()
                .clickIssues().clickOnLotcheckIssuesTab()
                .expandUnresolve()
                .expandIssue();
        assertEquals(titleIssue, issuePage.getIssueTitle(), "Verify issue published on slcms is displayed");

    }

    @Test()
    @Order(33)
    public void selectWillFixInFeaturesAndSendResolution() {
        NDPReleaseInfoReleaseListNav listNav = (NDPReleaseInfoReleaseListNav) currentPage;
        NDPReleaseInfoIssuePage issuePage = listNav.clickIssues().clickOnLotcheckIssuesTab()
                .expandUnresolve()
                .expandIssue()
                .selectResolution(TextConstants.willFixInFeature)
                .clickOnSendResolutionButton()
                .expandResolve();
        assertAll(() -> assertEquals(TextConstants.willFixInFeature, issuePage.getStatusOfIssue(), "Verify status of issue after send Will Fix In Features resolution"),
                () -> assertTrue(listNav.submitEditedInformationButtonIsClickable(), "Verify Submit Edited Information button is display and enable"));

    }

    @Test()
    @Order(34)
    public void clickCancelSubmissionAndConfirmContentOnConfirmationPopup() {
        NDPReleaseInfoReleaseListNav listNav = (NDPReleaseInfoReleaseListNav) currentPage;
        NDPConfirmationDialog confirmationDialog = listNav.clickCancelSubmit();
        String cancelDialogMessage_EN_actual = confirmationDialog.getCancelDialogMessage();
        confirmationDialog.clickClose();
        listNav.clickBackToProductDashboard().clickDownloadableContent().clickDLCReleaseByArchiveNo("00").nav().clickUserMenuDropdownList().selectJapan()
                .clickCancelSubmit();
        String cancelDialogMessage_JP_actual = confirmationDialog.getCancelDialogMessage();
        confirmationDialog.clickClose().clickUserMenuDropdownList().selectEnglish();
        listNav.clickUserMenuDropdownList().clickSignOutButton();
        assertAll(() -> assertEquals(TextConstants.cancelDialogMessage_EN_expected, cancelDialogMessage_EN_actual, TextConstants.cancelDialogMessage_EN_actual),
                () -> assertEquals(TextConstants.cancelDialogMessage_JP_expected, cancelDialogMessage_JP_actual, TextConstants.cancelDialogMessage_JP_actual));

    }

    @Test()
    @Order(35)
    public void changeLCOWithLCUser(IJUnitTestReporter testReport) {
        loginSLCMSAndNavigateToLotCheckReport(testReport);
        LotcheckReportSubmissionOverview submissionOverview = (LotcheckReportSubmissionOverview) currentPage;
        submissionOverview.clickMenu()
                .selectChangeLCO()
                .selectNewLCO(LotcheckRegion.NCL.toString()).clickSave();
        currentPage = submissionOverview;

    }

    @Test()
    @Order(36)
    public void confirmDisplayOfUserUpdatedLCO() {
        LotcheckReportSubmissionOverview submissionOverview = (LotcheckReportSubmissionOverview) currentPage;
        String user = submissionOverview.getLotcheckOwnerValue().split("\n")[1].split(",")[1].trim();
        submissionOverview.refreshPageSLCMS();
        assertEquals("SLCMS Admin NCL", user, "Verify the user name is displayed exactly");

    }

    @Test()
    @Order(37)
    public void loginNDPAndResubmitAOCRelease(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        NDPHomePage homePage = (NDPHomePage) currentPage;
        NDPMyProductsPage myProductsPage = homePage.clickSignInPage().signIn(ndpUser).clickMyProducts();
        NDPReleaseInfoPage releaseInfoPage = myProductsPage.clickProductByGameCode(initialCode).clickDownloadableContent().clickDLCReleaseByArchiveNo("00")
                .nav().clickSubmitEditedInformation().clickSubmit();
        String status = releaseInfoPage.getStatusAOCValue();
        releaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton();
        assertEquals(TextConstants.submitToLotcheck, status, TextConstants.resubmitAOCSuccess);
    }


    @Test()
    @Order(38)
    public void loginTestRailAsLcAdminAndAssignAOCTestPlanAgain(IJUnitTestReporter testReport) {
        loginTestRailAndFilterTestPlan(testReport, testrailAdminUser_EN);
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan()
                .clickAssignTo()
                .selectSetValueAssignToRadio()
                .selectUser(TextConstants.overseasCoordinatorNCL)
                .clickOk()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .expandAllTestRuns();
        currentPage = testRailManagerDashboardPage;
        Boolean allTestCasesAreAssignCorrectly = testRailManagerDashboardPage.allTestCasesAreAssignCorrectly(TextConstants.overseasCoordinatorNCL);
        testRailManagerDashboardPage.logOut();
        assertTrue(allTestCasesAreAssignCorrectly, TextConstants.allTestCasesAssigned);

    }

    @Test()
    @Order(39)
    public void loginTestRailAsOverseasCoordinatorNCLAndSetStatusNGMetadata(IJUnitTestReporter testReport) {
        loginTestRailAndFilterTestPlan(testReport, testrailOverseasCoordinatorUser);
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan()
                .clickOnSetStatusButton()
                .selectStatusOnSetStatusModal("Failed")
                .selectSubStatusOnSetStatusModal(TextConstants.Metadata)
                .clickOnUpdateStatusButtonOnSetStatusModal()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .expandAllTestRuns();
        String percentOfTestPlan = testRailManagerDashboardPage.getPercentOfTestPlan();
        Boolean checkStatusOfAllTestcases = testRailManagerDashboardPage.checkStatusOfAllTestcases("Failed");
        Boolean checkSubStatusOfAllTestcases = testRailManagerDashboardPage.checkSubStatusOfAllTestcases(TextConstants.Metadata);
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.logOut();
        assertAll(() -> assertEquals(TextConstants.percentCompleted, percentOfTestPlan, "Verify set status for all testcase successfully"),
                () -> assertTrue(checkStatusOfAllTestcases, "Verify all testcase set status is Failed"),
                () -> assertTrue(checkSubStatusOfAllTestcases, "Verify all testcase set sub status is Metadata"));
    }

    @Test()
    @Order(40)
    public void loginTestRailAsLcAdminAndSearchAOCTestPlanAgain(IJUnitTestReporter testReport) {
        loginTestRailAndFilterTestPlan(testReport, testrailAdminUser_EN);
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .expandAllTestRuns();
        assertAll(() -> assertTrue(testRailManagerDashboardPage.checkStatusOfAllTestcases("Failed"), "Verify all testcase set status is Failed"),
                () -> assertTrue(testRailManagerDashboardPage.checkSubStatusOfAllTestcases(TextConstants.Metadata), "Verify all testcase set sub status is Metadata"));
    }

    @Test()
    @Order(41)
    public void asTestRailAdminJudgmentMetadataNGForTestPlan() {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnPadlockButtonOfTheFirstTestPlan()
                .selectTestPlanJudgment(TextConstants.MetadataNG)
                .clickOnYesButtonOnConfirmationModal()
                .actionFill().clickOnApplyFiltersButton();
        boolean testPlanIsNotVisible = testRailManagerDashboardPage.testplanIsNotVisible(gameCode_AOC);
        testRailManagerDashboardPage.logOut();
        assertTrue(testPlanIsNotVisible, TextConstants.testplanIsNotVisible);
    }

    @Test()
    @Order(42)
    public void loginSLCMSAndRejectJudgment(IJUnitTestReporter testReport) {
        loginSLCMSAndNavigateToApproveQueue(testReport);
        LotcheckQueuePage lotcheckQueuePage = (LotcheckQueuePage) currentPage;
        CommonAction.rejectJudgmentStatus(lotcheckQueuePage, initialCode, 70, TextConstants.MetadataNG);
        lotcheckQueuePage.clickSearchIcon()
                .clickPlannedTestDateNotSet();
        String judgmentStatus = lotcheckQueuePage.getJudgmentStatus();
        Boolean testPlanStillDisplayed = lotcheckQueuePage.testplanStillDisplayedInJudgmentApprovalQueue(gameCode_AOC);
        lotcheckQueuePage.refreshPageSLCMS();
        assertAll(() -> assertTrue(testPlanStillDisplayed, "Verify test plan still displayed in Judgment Approval Queue"),
                () -> assertEquals("No Judgment", judgmentStatus, "Verify the Judgment becomes No Judgment"));
    }

    @Test()
    @Order(43)
    public void loginTestRailAsLcAdminAndJudgmentSuspended(IJUnitTestReporter testReport) {
        loginTestRailAndFilterTestPlan(testReport, testrailAdminUser_EN);
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .clickOnPadlockButtonOfTheFirstTestPlan()
                .selectTestPlanJudgment(TextConstants.Suspended)
                .clickOnYesButtonOnConfirmationModal()
                .actionFill().clickOnApplyFiltersButton();
        boolean testPlanIsNotVisible = testRailManagerDashboardPage.testplanIsNotVisible(gameCode_AOC);
        assertTrue(testPlanIsNotVisible, TextConstants.testplanIsNotVisible);
    }

    @Test()
    @Order(44)
    public void loginSLCMSAndApproveJudgment(IJUnitTestReporter testReport) {
        loginSLCMSAndNavigateToApproveQueue(testReport);
        LotcheckQueuePage lotcheckQueuePage = (LotcheckQueuePage) currentPage;

        CommonAction.approvalJudgmentStatus(lotcheckQueuePage, initialCode, 70, TextConstants.Suspended, false, "1");
        boolean testPlanNotDisplayed = !lotcheckQueuePage.testplanStillDisplayedInJudgmentApprovalQueue(gameCode_AOC);
        lotcheckQueuePage = lotcheckQueuePage.navLandingPage().clickFinish(LotcheckRegion.NCL);
        lotcheckQueuePage.searchByInitialCode(initialCode).clickSearchIcon();
        String judgmentStatus_finish = lotcheckQueuePage.getJudgmentStatus();
        assertAll(() -> assertTrue(testPlanNotDisplayed, "Verify test plan leave in Judgment Approval Queue"),
                () -> assertEquals(TextConstants.Suspended, judgmentStatus_finish, "Verify status of test plan after approve is Suspended"));
    }


    public void loginSLCMSAndNavigateToApproveQueue(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckLandingPage landingPage = (LotcheckLandingPage) currentPage;
        currentPage = landingPage.clickJudgment(LotcheckRegion.NCL);
    }

    public void loginSLCMSAndNavigateToLotCheckReport(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckLandingPage landingPage = (LotcheckLandingPage) currentPage;

        LotcheckSubmissionSearchPage submissionSearchPage = landingPage.searchProduct(initialCode);
        currentPage = submissionSearchPage.selectSubmission(gameCode_AOC, "00", "00");
    }

    public void loginTestRailAndFilterTestPlan(IJUnitTestReporter testReport, User user) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailLoginPage.signInTestRail(user).navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(gameCode_AOC)
                .selectStatusUntested().selectStatusPassed().selectStatusFailed();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        currentPage = testRailManagerDashboardPage;
    }


}
