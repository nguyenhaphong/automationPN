package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.junit.TestLogger;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.data.DataGen;
import net.nintendo.automation.ui.data.xml.XMLReader;
import net.nintendo.automation.ui.data.xml.XMLWriter;
import net.nintendo.automation.ui.models.admin.AdminControlPanelPage;
import net.nintendo.automation.ui.models.admin.AdminD4CIntegrationPage;
import net.nintendo.automation.ui.models.admin.AdminDevelopmentHome;
import net.nintendo.automation.ui.models.admin.AdminHomePage;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.d4c.D4CRomPublishSuit;
import net.nintendo.automation.ui.models.d4c.D4CRomTab;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthPage;
import net.nintendo.automation.ui.models.iarc.IARCQuestionnaireFinalRatingPage;
import net.nintendo.automation.ui.models.iarc.IARCQuestionnairePage;
import net.nintendo.automation.ui.models.lcms.LotcheckEditAccountPreferences;
import net.nintendo.automation.ui.models.lcms.LotcheckHomePage;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.ui.models.lcms.LotcheckQueuePage;
import net.nintendo.automation.ui.models.lcms.LotcheckSubmissionSearchPage;
import net.nintendo.automation.ui.models.lcms.LotcheckTestPlanRow;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionFeatures;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionProductInformation;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionTestScenarios;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPLicensingWaiverRequestsTab;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.NDPRequestGameCodePage;
import net.nintendo.automation.ui.models.ndp.NDPViewTaskPage;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPCreateReleasePage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardProductInfoTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardReleasesTab;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoAgeRating;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoFeaturesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoGuidelinesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoIssuePage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoROMUpload;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoTestScenarios;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPSubmissionReviewPage;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPEditIarcRatingDialog;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPEditStandardRatingDialog;
import net.nintendo.automation.ui.models.rom.ROMCreateRom;
import net.nintendo.automation.ui.models.rom.ROMLoginPage;
import net.nintendo.automation.ui.models.testrail.TestRailConfirmCsvFileToImportDialog;
import net.nintendo.automation.ui.models.testrail.TestRailConfirmImportedContentDialog;
import net.nintendo.automation.ui.models.testrail.TestRailDashboardPage;
import net.nintendo.automation.ui.models.testrail.TestRailDetailTestcaseDialog;
import net.nintendo.automation.ui.models.testrail.TestRailLoginPage;
import net.nintendo.automation.ui.models.testrail.TestRailManagerDashboardPage;
import net.nintendo.automation.ui.models.testrail.TestRailMessageDialog;
import net.nintendo.automation.ui.models.testrail.TestRailMyToDoPage;
import net.nintendo.automation.ui.models.testrail.TestRailPlanViewPage;
import net.nintendo.automation.ui.models.testrail.TestRailProjectTestSuitesPage;
import net.nintendo.automation.ui.models.testrail.TestRailProjectTestSuitesTestcasePage;
import net.nintendo.automation.ui.models.testrail.TestRailSearchPlansDialog;
import net.nintendo.automation.ui.models.testrail.TestRailTestPlanTable;
import net.nintendo.automation.ui.models.testrail.TestRailViewElapsedTimeDialog;
import net.nintendo.automation.ui.users.User;
import net.nintendo.automation.ui.users.UserManager;
import net.nintendo.automation.utils.WaitUtils;
import net.nintendo.automation_core.common_client.enums.ServiceType;
import net.nintendo.automation_core.common_client.enums.UserType;
import net.nintendo.automation_core.common_utils.enums.LotcheckRegion;
import net.nintendo.automation_core.extent_reports.extensions.IJUnitTestReporter;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("T3402573")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3402573 extends CommonBaseTest {
    private static Browser browser;
    private static Browser browser2;
    private static User romUser;
    private static User ndpUser;
    private static User lcAdminUser;
    private static User lcCoordinatorUser;
    private static User lcTranslatorUser;
    private static User licUser;
    private static User trAdminUser;
    private static User trDirectorUser;
    private static User trDataManagerUser;
    private static String fileRomNamePatch;
    private static String newSystemUsed;
    private static String newMediaType;
    private static String newMediaType_actual;
    private static String comment;
    private static String gameCodePatch;
    private static String testcaseStatus;
    private static boolean isTestPlanDisplayedCorrect;
    private static String importCsvFileName;
    private static String importCsvFileName_import;
    private static String testcaseName_01;
    private static String testcaseName_02;
    private static String testcaseName_03;
    private static String testcaseName_04;
    private static String testcaseName_05;
    private static String idTestcase_02;
    private static String idTestcase_03;
    private static String idTestcase_04;
    private static String idTestcase_05;
    private static boolean isTestcase02DisplayedOnWarningCondition;
    private static boolean isTestcase04DisplayedOnWarningCondition;
    private static CommonUIComponent currentPage;
    private static TestLogger logger;
    private static String publishingRelationship;
    private static String productName;
    private static String applicationId;
    private static String initialCode;
    private static String romInitialName;
    private static String romID;
    private static String gameCode;
    private static String ratingValue;
    private static boolean isYesImportedCsvFileCheckboxChecked;
    private static boolean isNoImportedCsvFileCheckboxChecked;
    private static boolean isImportedCsvFileCheckboxChecked;
    private static String ratingIcon;
    private static String descriptors;
    private static String freeToPlayValue;
    private static String freeToPlayFieldName;
    private static String exportFileName_OV;
    private static String exportFileName_FS;
    private static String exportFileName_FS_JA;
    private static String exportFileName_TS;
    private static String exportFileName_TS_JA;
    private static boolean isNotAnyTargetDisplayed;
    private static boolean isSourceDisplayed;
    private static String message_OV_importTS;
    private static String message_OV_importFS;
    private static String message_TS_importOV;
    private static String message_TS_importFS;
    private static String message_FS_importTS;
    private static XMLReader xmlFile;
    private static String developerCommentTargetValue;

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_NgaDTN);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
        lcAdminUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);
        lcCoordinatorUser = UserManager.get(ServiceType.SLCMS, UserType.TRANSLATION_USER);

        trDataManagerUser = new User();
        trDataManagerUser.setUserName("data_manager@test.ncl.nintendo.com");
        trDataManagerUser.setPassword("password");

        trDirectorUser = new User();
        trDirectorUser.setUserName("director@test.ncl.nintendo.com");
        trDirectorUser.setPassword("password");

        lcTranslatorUser = new User();
        lcTranslatorUser.setUserName("lc_translator_ncl");
        lcTranslatorUser.setPassword("AccuseAlthoughShutGuard7");

        trAdminUser = new User();
        trAdminUser.setUserName("tradmin-en@test.ncl.nintendo.com");
        trAdminUser.setPassword("password");

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
        UserManager.releaseUser(ServiceType.SLCMS, lcAdminUser);
        UserManager.releaseUser(ServiceType.SLCMS, lcCoordinatorUser);
        UserManager.releaseUser(ServiceType.SEARCH, romUser);
    }

    @Test()
    @Order(1)
    public void loginNdpAsPartner_Step1() {
        NDPHomePage homePage = (NDPHomePage) currentPage;
        currentPage = homePage.acceptCookie().clickSignInPage().signIn(ndpUser);
    }

    @Test()
    @Order(2)
    public void createProductAndCreateRomMpa_Step2(IJUnitTestReporter testReport) {
        NDPDevelopmentHome developmentHome = (NDPDevelopmentHome) currentPage;
        productName = DataGen.getRandomProductName();
        NDPMyProductsPage myProductsPage = developmentHome.clickMyProducts().createNewProduct()
                .selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(publishingRelationship)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalJapanSalesRegion()
                .selectProductName(productName)
                .selectProductNameKana(TextConstants.productKana)
                .clickCreateButton();
        NDPProductDashboardProductInfoTab productInfoTab = myProductsPage.clickProductByName(productName).clickProductInfo();
        applicationId = productInfoTab.getApplicationID();

        currentPage = browser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        ROMLoginPage romLoginPage = (ROMLoginPage) currentPage;
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom.selectRequester(TextConstants.test_User)
                .selectType(TextConstants.initial_Release)
                .selectMPACheckbox()
                .selectNumberOfProgram("11")
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .enterProductName(productName)
                .enterApplicationId(applicationId)
                .selectPublishingRelationShip(publishingRelationship)
                .enterDisplayVersion("1.0.0")
                .enterReleaseVersion("00")
                .selectCERORating("CERO: Not required")
                .clickOnCreateButton()
                .waitIsLoaded();
        romInitialName = romCreateRom.getRomNameTable(applicationId);
        romCreateRom.clickOnROMFileOnROMTable(romInitialName);
        WaitUtils.idle(15000);
        romCreateRom.clickOnUserNameMenu().clickOnLogoutButton();

    }

    @Test()
    @Order(3)
    public void issueGameCodeWithFreeToPlayYes_Step3(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        NDPRequestGameCodePage issueGameCode = myProductsPage.clickProductByName(productName).issueGameCode();
        currentPage = issueGameCode.setExpectedLotcheckSubmissionDate() //Need add condition for issue game code
                .setExpectedReleaseDate(90)
                .selectFreeToPlay(TextConstants.yes_Upper_Value)
                .clickIssue();
    }

    @Test()
    @Order(4)
    public void navigateToReleaseInfoIssuesAndCompleteFreeToPlayIssue_Step4() {
        NDPProductDashboardPage productDashboardPage = (NDPProductDashboardPage) currentPage;
        initialCode = productDashboardPage.clickProductInfo().getInitialCode();
        gameCode = productDashboardPage.clickProductInfo().getGameCode();
        gameCodePatch = "HAC-U-" + initialCode;
        NDPReleaseInfoPage releaseInfoPage = productDashboardPage.clickReleases().selectInitialRelease();
        releaseInfoPage.nav().clickIssues().openRequiredApprovalTab().clickOnFirstUnresolvedRequiredApproval()
                .selectDesiredApprovalTypeAtTemporaryApprovalRequiredApproval()
                .enterCrossPlatformDetails("Cross Platform Details")
                .enterInGameEventDetails("In Game Event Details")
                .enterPatchSubmissionSchedule("Patch Submission Schedule")
                .enterMonetizationDetails("Monetization Details")
                .enterEstimatedMonthlyUserCount("Estimated Monthly User Count")
                .clickOnSendResolutionF2pButton();
        NDPDevelopmentHome developmentHome = releaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
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
    @Order(5)
    public void completeAllSectionAndSubmitInitialReleaseToLotcheck_Step5(IJUnitTestReporter testReport) {
        NDPViewTaskPage ndpViewTaskPage = browser.getPage(NDPViewTaskPage.class, testReport);
        ndpViewTaskPage.refreshPage();
        NDPReleaseInfoPage releaseInfoPage = ndpViewTaskPage.clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(ndpUser).clickMyProducts().clickProductByName(productName)
                .clickReleases().selectInitialRelease();
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
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = releaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, romInitialName);

        releaseInfoPage.nav().clickTestScenarios().expandAllSections()
                .expandAllTestScenes()
                .enterSubjectForProgramSpecificationsHowToStartUpTheIncludedPrograms("Name How to start up the included programs", 0)
                .enterExplainForProgramSpecificationsHowToStartUpTheIncludedPrograms("Explain How to start up the included programs", 0)
                .clickOnSaveButton();
        releaseInfoPage.nav().clickIssues().openRequiredApprovalTab()
                .performAllIssueAtApprovalTab(false);

        NDPDevelopmentHome developmentHome = releaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        licensingWaiverRequestsTab.waitingForResultTableLoaded()
                .enterSearchingBox(initialCode)
                .clickOnMagnifyingIconButton()
                .waitingForResultTableLoaded();
        int numberOfRequestByPendingStatus = licensingWaiverRequestsTab.collectNumberOfRequestByPendingStatus();
        for (int i = 0; i < numberOfRequestByPendingStatus; i++) {
            developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton().clickOnLicensingWaiverRequestsTab();
            licensingWaiverRequestsTab.waitingForResultTableLoaded()
                    .enterSearchingBox(initialCode)
                    .clickOnMagnifyingIconButton()
                    .waitingForResultTableLoaded();
            currentPage = licensingWaiverRequestsTab.openLicensingWaiverRequestByPendingStatus().selectAssignToMeButton()
                    .selectOKButtonInAssignToMeModal()
                    .selectApproveRequestButton()
                    .selectOKButtonInApproveRequestModal()
                    .waitingForApproveSuccessful();

        }
        ndpViewTaskPage.clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(ndpUser)
                .clickMyProducts().clickProductByGameCode(gameCode).clickReleases().selectInitialRelease().nav().clickSubmitButton().enterDeveloperComment("Test comment")
                .clickSubmitReview().openNewTabAndCloseCurrentTab();


        NDPMyProductsPage myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
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
    }

    @Test()
    @Order(6)
    public void loginTestRailAsTrAdminAndJudgmentOkForTestPlan_Step6(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        TestRailDashboardPage testRailDashboardPage = testRailLoginPage.signInTestRail(trAdminUser);
        WaitUtils.idle(3000);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(initialCode)
                .selectStatusUntested()
                .selectStatusPassed();
        for (int i = 1; i < 3; i++) {
            if (i == 2) {
                testRailManagerDashboardPage.logFail();
            }
            testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
            testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                    .clickOnTheFirstTestPlanBar()
                    .waitingLoadingTestSuite()
                    .selectCheckboxOfTheFirstTestPlan()
                    .clickOnSetStatusButton()
                    .selectStatusOnSetStatusModal(TextConstants.passedStatus)
                    .clickOnUpdateStatusButtonOnSetStatusModal()
                    .waitingLoadingTestSuite();
            if (!testRailManagerDashboardPage.getPercentOfTestPlan().equals(TextConstants.percentCompleted)) {
                WaitUtils.idle(60000);
                testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
                testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar();
                if (testRailManagerDashboardPage.getPercentOfTestPlan().equals(TextConstants.percentCompleted)) {
                    break;
                }
            } else {
                break;
            }
        }
        WaitUtils.idle(6000);
        testRailManagerDashboardPage.clickOnPadlockButtonOfTheFirstTestPlan()
                .selectTestPlanJudgment(TextConstants.OK)
                .clickOnYesButtonOnConfirmationModal();
        testRailDashboardPage.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton();
    }

    @Test()
    @Order(7)
    public void loginSlcmAsLcAdminAndApproveOkJudgmentForTestPlan_Step7(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(lcAdminUser);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickJudgment(LotcheckRegion.NCL);
        lotcheckQueuePage.searchByInitialCode(initialCode); //Test4: comment
        int i = 0;
        while (i <= 300) {
            lotcheckQueuePage.clickSearchIcon(); //Test4:  comment
            String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
            if (numberValue.equalsIgnoreCase("1")) {
                LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
                String judgmentStatus = lotcheckQueuePage.getJudgmentStatus();
                if (judgmentStatus.equals(TextConstants.OK)) {
                    WaitUtils.idle(2000);
                    lotcheckTestPlanRow.performAction("Approve Judgment");
                    break;
                }
            }
            i++;
            WaitUtils.idle(3000);
        }
        lotcheckQueuePage.refreshPageSLCMS().logOut();

    }

    @Test()
    @Order(8)
    public void onNdpCreateAddPhysicalRelease_Step8(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPCreateReleasePage ndpCreateReleasePage = ndpProductDashboardReleasesTab.clickCreateNewReleaseButton();
        currentPage = ndpCreateReleasePage
                .selectReleaseType(TextConstants.add_Physical_Update)
                .selectExpectedSubmissionDate(5)
                .selectExpectedReleaseDate(90)
                .clickCreateButton();
    }

    @Test()
    @Order(9)
    public void completeAllSectionAndSubmitAddPhysicalReleaseToLotcheck_Step9(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentPage;
        ndpReleaseInfoPage.nav().clickReleaseInfo();
        CommonAction.selectROMNoCard(ndpReleaseInfoPage, TextConstants.selectRomSameAllRegion, TextConstants.english);

        currentPage = browser.openURL(D4CRomPublishSuit.class, PageState.PRE_LOGIN);
        D4CRomPublishSuit d4cRom = (D4CRomPublishSuit) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        String idApplication = applicationId.substring(2);
        D4CRomTab d4cRomTab = d4cRom.clickTabRom();
        romID = d4cRomTab.enterApplicationID(idApplication).clickSearchButton().getRomId();


        currentPage = browser.openURL(AdminHomePage.class, PageState.PRE_LOGIN);
        AdminHomePage adminHomePage = (AdminHomePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        AdminDevelopmentHome adminDevelopmentHome = adminHomePage.acceptCookie()
                .clickSignInPage()
                .signIn(TextConstants.omniAdmin, TextConstants.omniAdmin_Password);
        AdminControlPanelPage adminControlPanelPage = adminDevelopmentHome.clickMenuAdmin().clickControlPanel();
        AdminD4CIntegrationPage adminD4CIntegrationPage = adminControlPanelPage.clickD4CIntegrationAdminButton();
        adminD4CIntegrationPage.submitROMCheckResults(romID);
        adminDevelopmentHome.logOutPage();

        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        ndpReleaseInfoPage = myProductsPage.clickProductByName(productName).clickReleases().selectReleaseType(TextConstants.add_Physical_Update_Release);
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = ndpReleaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoGuidelinesPage.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();
        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
        issuePage.openRequiredApprovalTab();
        issuePage.performAllIssueAtApprovalTab(false);

        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();

        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(licUser).clickOnMyTaskMenu();

        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, gameCode);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();

        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser).clickMyProducts();

        ndpReleaseInfoPage = myProductsPage.clickProductByName(productName).clickReleases().selectReleaseType(TextConstants.add_Physical_Update_Release);

        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.commentSubmission).clickSubmitReview().openNewTabAndCloseCurrentTab();

        myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        myProductsPage.clickProductByGameCode(gameCode).clickReleases().selectReleaseType(TextConstants.add_Physical_Update_Release);
        for (int i = 0; i < 300; i++) {
            if (ndpReleaseInfoPage.getStatusValue().equals(TextConstants.submitToLotcheck)) {
                break;
            }
            WaitUtils.idle(3000);
            ndpReleaseInfoPage.nav().clickFeatures();
            ndpReleaseInfoPage.nav().clickReleaseInfo();
        }
        assertEquals(TextConstants.submitToLotcheck, ndpReleaseInfoPage.getStatusValue(), TextConstants.submitSuccess);

    }

    @Test()
    @Order(10)
    public void loginTestRailAsTrAdminAndJudgmentMetadataNGForTestPlan_Step10(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        TestRailDashboardPage testRailDashboardPage = testRailLoginPage.signInTestRail(trAdminUser);
        WaitUtils.idle(3000);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(initialCode)
                .selectStatusUntested()
                .selectStatusFailed();
        for (int i = 1; i < 3; i++) {
            if (i == 2) {
                testRailManagerDashboardPage.logFail();
            }
            testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
            testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                    .clickOnTheFirstTestPlanBar()
                    .waitingLoadingTestSuite()
                    .selectCheckboxOfTheFirstTestPlan()
                    .clickOnSetStatusButton()
                    .selectStatusOnSetStatusModal(TextConstants.failedStatus)
                    .selectSubStatusOnSetStatusModal(TextConstants.Metadata)
                    .clickOnUpdateStatusButtonOnSetStatusModal()
                    .waitingLoadingTestSuite();
            if (!testRailManagerDashboardPage.getPercentOfTestPlan().equals(TextConstants.percentCompleted)) {
                WaitUtils.idle(60000);
                testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
                testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar();
                if (testRailManagerDashboardPage.getPercentOfTestPlan().equals(TextConstants.percentCompleted)) {
                    break;
                }
            } else {
                break;
            }
        }
        WaitUtils.idle(6000);
        testRailManagerDashboardPage.clickOnPadlockButtonOfTheFirstTestPlan()
                .selectTestPlanJudgment(TextConstants.MetadataNG)
                .clickOnYesButtonOnConfirmationModal();
        testRailDashboardPage.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton();
    }

    @Test()
    @Order(11)
    public void loginSlcmsAsLcAdminAndApproveMetadataNGJudgmentForTestPlan_Step11(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(lcAdminUser);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickJudgment(LotcheckRegion.NCL);
        lotcheckQueuePage.searchByInitialCode(initialCode); //test2: remove comment
        int i = 0;
        while (i <= 300) {
            lotcheckQueuePage.clickSearchIcon();//test2 : remove  comment
            String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
            if (numberValue.equalsIgnoreCase("1")) {
                LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
                String judgmentStatus = lotcheckQueuePage.getJudgmentStatus();
                if (judgmentStatus.equals(TextConstants.MetadataNG)) {
                    WaitUtils.idle(2000);
                    lotcheckTestPlanRow.performAction("Approve Judgment");
                    break;
                }
            }
            i++;
            WaitUtils.idle(3000);
        }
        lotcheckQueuePage.refreshPageSLCMS();
        currentPage = lotcheckQueuePage;
    }

    @Test()
    @Order(12)
    public void verifySubmissionVersion_Expected11() {
        LotcheckQueuePage lotcheckQueuePage = (LotcheckQueuePage) currentPage;
        LotcheckSubmissionSearchPage submissionSearchPage = lotcheckQueuePage.navLandingPage().searchProduct(initialCode);
        boolean isSubmissionDisplayedCorrect = submissionSearchPage.isSubmissionDisplayedCorrect(gameCode, "00", "100");
        submissionSearchPage.logOut();
        assertTrue(isSubmissionDisplayedCorrect, "Verify Submission version is still 00.100");
    }

    @Test()
    @Order(13)
    public void onNdpCreateNewPatchRelease_Step12(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        currentPage = myProductsPage.clickProductByName(productName).clickReleases().clickCreateNewReleaseButton()
                .enterDisplayVersion("1.0.1")
                .selectExpectedSubmissionDate(10)
                .selectExpectedReleaseDate(90)
                .enterDescribeTheFeaturesPlanned(TextConstants.enterDescribeTheFeaturesPlanned)
                .enterDescribeTheBugsIssuesPlanned(TextConstants.enterDescribeTheBugsIssuesPlanned)
                .clickCreateButton()
                .clickBackToRelease();

    }

    @Test()
    @Order(14)
    public void verifyPatchReleaseIsCreatedAndDisplayedOnReleaseTab_Expected12() {
        NDPProductDashboardReleasesTab releasesTab = (NDPProductDashboardReleasesTab) currentPage;
        assertTrue(releasesTab.isPatchReleaseDisplayed(TextConstants.patch_Release, "01", "1.0.1"));
    }

    @Test()
    @Order(15)
    public void navigateToReleaseInfoAndSelectYesFreeToPlay_Step13() {
        NDPProductDashboardReleasesTab releasesTab = (NDPProductDashboardReleasesTab) currentPage;
        currentPage = releasesTab.selectPatchRelease().nav().clickReleaseInfo().selectFreeToPlay(TextConstants.yes_Upper_Value).save();
    }

    @Test()
    @Order(16)
    public void navigateRequiredApprovalsTab_Step14() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        currentPage = releaseInfoPage.nav().clickIssues().openRequiredApprovalTab();
    }

    @Test()
    @Order(17)
    public void verifyDisplayedOfFreeToPlayIssue_Expected14() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        String issueTitle_EN = issuePage.getIssueTitle();
        issuePage.nav().clickUserMenuDropdownList().selectJapan();
        issuePage.openRequiredApprovalTab();
        String issueTitle_JP = issuePage.getIssueTitle();
        issuePage.nav().clickUserMenuDropdownList().selectEnglish();
        assertAll(() -> assertEquals("Free To Play Request", issueTitle_EN, "Verify The name EN of the issue "),
                () -> assertEquals("Free to Playソフトの申請", issueTitle_JP, "Verify The name JP of the issue "),
                () -> assertTrue(issuePage.isFreeToPlayIssueCreatedOnIssuesBlockingLotcheckSubmission("Free To Play Request"), "Verify Free to Play issue created on Issues Blocking Lotcheck Submission section"));
    }

    @Test()
    @Order(18)
    public void backToReleaseInfoTabAndSelectNoFreeToPlay_Step15() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        currentPage = issuePage.nav().clickReleaseInfo().selectFreeToPlay("NO").save();
    }

    @Test()
    @Order(19)
    public void backToRequiredApprovalsTab_Step16() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        currentPage = releaseInfoPage.nav().clickIssues().openRequiredApprovalTab();
    }

    @Test()
    @Order(20)
    public void verifyIssueFreeToPlayWillNotCreated_Expected16() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        assertTrue(!issuePage.isIssueNotRequestedDisplayed("Free To Play Request"), "Verify issue Free to Play will not be created");
    }

    @Test()
    @Order(21)
    public void backToReleaseInfoTabAndSelectYesFreeToPlay_Step17() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        currentPage = issuePage.nav().clickReleaseInfo().selectFreeToPlay(TextConstants.yes_Upper_Value).save();
    }

    @Test()
    @Order(22)
    public void backToRequiredApprovalsTabAgain_Step18() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        currentPage = releaseInfoPage.nav().clickIssues().openRequiredApprovalTab();
    }

    @Test()
    @Order(23)
    public void verifyDisplayOfFreeToPlayIssue_Expected18() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        assertTrue(issuePage.isFreeToPlayIssueCreatedOnIssuesBlockingLotcheckSubmission("Free To Play Request"), "Verify Free to Play issue created on Issues Blocking Lotcheck Submission section");
    }

    @Test()
    @Order(24)
    public void clickIssueName_Step19() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        issuePage.clickOnIssueName();
    }

    @Test()
    @Order(25)
    public void verifyIssueExpandedAndSendResolutionButtonIsDisplayed_Expected19() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        assertAll(() -> assertTrue(issuePage.isIssueExpanded(), "Verify the issue will be expanded the issue"),
                () -> assertTrue(issuePage.isSendResolutionF2pButtonDisplayed(), "Verify Send resolution button is displayed"));

    }

    @Test()
    @Order(26)
    public void clickIssueNameAgain_Step20() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        issuePage.clickOnIssueName();
    }

    @Test()
    @Order(27)
    public void verifyIssueCollapsed_Expected20() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        assertTrue(issuePage.isIssueCollapsed(), "Verify the issue will be collapsed the issue");
    }

    @Test()
    @Order(28)
    public void editPublishingRelationshipTo2ndParty_Step21() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        NDPProductDashboardPage productDashboardPage = issuePage.nav().clickBackToProductDashboard();
        currentPage = productDashboardPage;
        productDashboardPage.clickProductInfo().clickEditPublishing().selectPublishingRelationship(" Second Party Type ")
                .clickSave();
    }

    @Test()
    @Order(29)
    public void navigateToRequiredApprovalsTab_Step22() {
        NDPProductDashboardPage productDashboardPage = (NDPProductDashboardPage) currentPage;
        currentPage = productDashboardPage.clickReleases().selectPatchRelease().nav().clickIssues().openRequiredApprovalTab();
    }

    @Test()
    @Order(30)
    public void verifyIssueFreeToPlayIsDisappeared_Expected22() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        assertTrue(!issuePage.isIssueNotRequestedDisplayed("Free To Play Request"));
    }

    @Test()
    @Order(31)
    public void editPublishingRelationshipTo3ndParty_Step23() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        NDPProductDashboardPage productDashboardPage = issuePage.nav().clickBackToProductDashboard();
        currentPage = productDashboardPage;
        productDashboardPage.clickProductInfo().clickEditPublishing().selectPublishingRelationship(" Third Party Type ")
                .clickSave();
    }

    @Test()
    @Order(32)
    public void navigateToReleaseInfoAndSelectNoFreeToPlay_Step24() {
        NDPProductDashboardPage productDashboardPage = (NDPProductDashboardPage) currentPage;
        productDashboardPage.clickReleases().selectPatchRelease().selectFreeToPlay("NO").save();
    }

    @Test()
    @Order(33)
    public void loginSlcmsByLcCoordinatorAndNavigateToReleaseInformationOfInitialRelease_Step25(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage landingPage = hyruleAuthPage.loginWithOvdLink(lcCoordinatorUser);

        LotcheckSubmissionSearchPage submissionSearchPage = landingPage.searchProduct(initialCode);
        LotcheckReportSubmissionOverview submissionOverview = submissionSearchPage.selectSubmission(gameCode, "00", "00");
        currentPage = submissionOverview.navBar().clickProductInformation().expandReleaseInformation();
    }

    @Test()
    @Order(34)
    public void checkDisplayOfFreeToPlayField_Step26() {
        LotcheckReportSubmissionProductInformation productInformation = (LotcheckReportSubmissionProductInformation) currentPage;
        LotcheckEditAccountPreferences editAccountPreferences = productInformation.navBar().clickMyAccount();
        if (editAccountPreferences.isCurrentLanguageEnglish()) {
            editAccountPreferences.clickCancelButton();
        } else {
            editAccountPreferences.selectLanguage(TextConstants.english)
                    .clickSaveButton();
            productInformation.waitPageLoaded();
            productInformation.expandReleaseInformation();
        }
        freeToPlayValue = productInformation.getFreeToPlay();
        freeToPlayFieldName = productInformation.getFreeToPlayFieldName();
    }

    @Test()
    @Order(35)
    public void verifyDisplayOfFreeToPlayFieldOfInitial_Expected26() {
        assertAll(() -> assertEquals(TextConstants.yes_Value, freeToPlayValue, "Verify Value of Free To Play field is display No"),
                () -> assertEquals(TextConstants.free2Play, freeToPlayFieldName, "Verify Display Name (EN) \"Free To Play\""));
    }

    @Test()
    @Order(36)
    public void openReleaseInformationOfPatchRelease_Step27() {
        LotcheckReportSubmissionProductInformation productInformation = (LotcheckReportSubmissionProductInformation) currentPage;
        currentPage = productInformation.navLandingPage().searchProduct(initialCode).selectSubmission(gameCodePatch, "01", "00")
                .navBar().clickProductInformation().expandReleaseInformation();
    }

    @Test()
    @Order(37)
    public void checkDisplayOfFreeToPlayFieldOfPatch_Step28() {
        LotcheckReportSubmissionProductInformation productInformation = (LotcheckReportSubmissionProductInformation) currentPage;
        LotcheckEditAccountPreferences editAccountPreferences = productInformation.navBar().clickMyAccount();
        if (editAccountPreferences.isCurrentLanguageEnglish()) {
            editAccountPreferences.selectLanguage(TextConstants.japanese)
                    .clickSaveButton();
            productInformation.waitPageLoaded();
            productInformation.expandReleaseInformation();
        } else {
            editAccountPreferences.clickCancelButton();
        }
        freeToPlayValue = productInformation.getFreeToPlay();
        freeToPlayFieldName = productInformation.getFreeToPlayFieldName();
    }

    @Test()
    @Order(38)
    public void verifyDisplayOfFreeToPlayFieldOfPatch_Expected28() {
        LotcheckReportSubmissionProductInformation productInformation = (LotcheckReportSubmissionProductInformation) currentPage;
        productInformation.navBar().clickMyAccount().selectLanguage(TextConstants.english).clickSaveButton();
        productInformation.navBar().logOut();
        assertAll(() -> assertEquals("No", freeToPlayValue, "Verify Value(JP) of Free To Play field is display No"),
                () -> assertEquals("Free to Play", freeToPlayFieldName, "Verify Display Name (JP) \"Free To Play\""));
    }


    @Test()
    @Order(39)
    public void onNdpNavigateToAgeRatingSection_Step29(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        currentPage = myProductsPage.clickProductByGameCode(gameCode).clickReleases().selectPatchRelease().nav().clickAgeRating();
    }

    // Remove step 30
    @Test()
    @Order(40)
    public void requestIarcRating_Step31(IJUnitTestReporter testReport) {
        NDPReleaseInfoAgeRating ageRating = (NDPReleaseInfoAgeRating) currentPage;
        ageRating.clickOnAddIARCRatingButton().enterContactEmailAddress(ndpUser.getUserName() + TextConstants.inbucket_Suffix)
                .enterPublicEmailAddress(ndpUser.getUserName() + TextConstants.inbucket_Suffix)
                .selectIARCRequestNewIARCCertificate()
                .clickRequestNewIarcContinue();
        WaitUtils.idle(6000);
        browser.switchToTabByTitle(IARCQuestionnairePage.pageTitle);
        IARCQuestionnaireFinalRatingPage iarcQuestionnaireFinalRatingPage = browser.getPage(IARCQuestionnairePage.class, testReport)
                .selectGame()
                .selectViolenceOrBloodNoDoesTheGameContain()
                .selectFearNoDoesTheGameContain()
                .selectSexualityNoDoesTheGameContain()
                .selectSimulatedGamblingNoDoesTheGameContain()
                .selectLanguageNoDoesTheGameContain()
                .selectControlledSubstanceNoDoesTheGameContain()
                .selectCrudeHumorNoDoesTheGameContain()
                .selectMiscellaneousYesDoesTheGameNativelyAllowUsersToInteract()
                .selectMiscellaneousYesDoesTheGameShareTheUsers()
                .selectMiscellaneousNoDoesTheGameAllowUsersToPurchase()
                .selectMiscellaneousYesDoesTheGameContainAnySwatikas()
                .selectMiscellaneousYesDoesTheGameContainAnyContent()
                .selectMiscellaneousYesDoesTheGameContainDetailedDescriptions()
                .selectMiscellaneousYesDoesTheGameAdvocate()
                .clickArrowIconNext()
                .clickNext();
        ratingIcon = iarcQuestionnaireFinalRatingPage.getRatingIcon("Generic");
        if (ratingIcon.equals("Generic_18_68.png")) {
            ratingIcon = "NIN_GENERIC_18.png";
        }
        ratingValue = ratingIcon.split("_")[2].substring(0, 2) + "+";
        descriptors = iarcQuestionnaireFinalRatingPage.getRatingDescriptors("Generic");
        if (descriptors.equals("Criminal Technique Instructions")) {
            descriptors = "Criminal Technique Instructions, Users Interact, Shares Location";
        }
        browser.closeTabByTitle(IARCQuestionnairePage.pageTitle);
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReport);
        currentPage = ndpReleaseInfoAgeRating.clickViewIARCAgeRatings();
    }

    @Test()
    @Order(41)
    public void clickTheAgencyOnRatingTable_Step32() {
        NDPReleaseInfoAgeRating ageRating = (NDPReleaseInfoAgeRating) currentPage;
        currentPage = ageRating.clickOnStandardAgeRating("CERO");
    }

    @Test()
    @Order(42)
    public void clickDelete_Step33() {
        NDPEditStandardRatingDialog standardRatingDialog = (NDPEditStandardRatingDialog) currentPage;
        currentPage = standardRatingDialog.clickDelete().clickApply();
    }

    @Test()
    @Order(43)
    public void verifyStandardCeroIsReplacedByIarcCero_Expected33() {
        NDPReleaseInfoAgeRating ageRating = (NDPReleaseInfoAgeRating) currentPage;
        assertEquals("CERO/Generic\n" +
                "Iarc", ageRating.getRatingAgency(), "Verify Standard CERO is replaced by IARC CERO aka Generic CERO");
    }

    @Test()
    @Order(44)
    public void clickTheAgencyOnRatingTableAgain_Step34() {
        NDPReleaseInfoAgeRating ageRating = (NDPReleaseInfoAgeRating) currentPage;
        currentPage = ageRating.clickOnIarcAgeRating("CERO");
    }

    @Test()
    @Order(45)
    public void verifyInformationOfIarcRatingDialog_Expected34() {
        NDPEditIarcRatingDialog editIarcRatingDialog = (NDPEditIarcRatingDialog) currentPage;
        assertAll(() -> assertEquals("Save", editIarcRatingDialog.getSaveButtonText(), "Verify display of Save button"),
                () -> assertEquals("Cancel", editIarcRatingDialog.getCancelButtonText(), "Verify display of Cancel button"),
                () -> assertEquals("Edit IARC Rating", editIarcRatingDialog.getModalTitle(), "Verify display of modal title "),
                () -> assertEquals("Change to Standard", editIarcRatingDialog.getChangeToStandardLinkText(), "Verify display of \"Change to Standard\" link"),
                () -> assertEquals("IARC", editIarcRatingDialog.getRatingType(), "Verify display of Rating Type"),
                () -> assertEquals("IARC Generic", editIarcRatingDialog.getAgencyValue(), "Verify display of Agency"),
                () -> assertEquals(ratingIcon, editIarcRatingDialog.getRatingIcon(), "Verify display of Rating Icon"),
                () -> assertEquals(ratingValue, editIarcRatingDialog.getRatingValue(), "Verify display of Rating Value"),
                () -> assertEquals(descriptors, editIarcRatingDialog.getDescriptorsAndNotices(), "Verify display of Descriptors")); //confirm descriptors
    }


    @Test()
    @Order(46)
    public void clickChangeToStandard_Step35() {
        NDPEditIarcRatingDialog editIarcRatingDialog = (NDPEditIarcRatingDialog) currentPage;
        currentPage = editIarcRatingDialog.clickChangeToStandard();
    }

    @Test()
    @Order(47)
    public void verifySwitchToEditStandardRatingDialog_Expected35() {
        NDPEditStandardRatingDialog editStandardRatingDialog = (NDPEditStandardRatingDialog) currentPage;
        assertEquals("Edit Standard Rating", editStandardRatingDialog.getModalTitle(), "Verify Switching to Edit Standard Rating screen");
    }

    @Test()
    @Order(48)
    public void clickSave_Step36() {
        NDPEditStandardRatingDialog editStandardRatingDialog = (NDPEditStandardRatingDialog) currentPage;
        editStandardRatingDialog.clickSaveWithInvalidData();
    }

    @Test()
    @Order(49)
    public void verifyRatingValueRequiredAlertDisplayed_Expected36() {
        NDPEditStandardRatingDialog editStandardRatingDialog = (NDPEditStandardRatingDialog) currentPage;
        assertEquals("This field is required.", editStandardRatingDialog.getRatingValueRequiredAlert(), "Verify Display error message about required field");
    }

    @Test()
    @Order(50)
    public void selectARatingValueThenClickSaveWithoutAddingFile_Step37() {
        NDPEditStandardRatingDialog editStandardRatingDialog = (NDPEditStandardRatingDialog) currentPage;
        editStandardRatingDialog.selectRatingValue("A").clickSaveWithInvalidData();
    }

    @Test()
    @Order(51)
    public void verifyCertificateRequiredAlertDisplayed_Expected37() {
        NDPEditStandardRatingDialog editStandardRatingDialog = (NDPEditStandardRatingDialog) currentPage;
        assertEquals("This field is required.", editStandardRatingDialog.getCertificateRequiredAlert(), "Verify Display error message about required field");

    }

    @Test()
    @Order(52)
    public void selectFileThenInputInvalidCertificateId_Step38() {
        NDPEditStandardRatingDialog editStandardRatingDialog = (NDPEditStandardRatingDialog) currentPage;
        editStandardRatingDialog.selectCertificateFile("Judgement.pdf")
                .enterCertificateId("Test test 1234567789123456778912345677891234567789123456778912344")
                .clickSaveWithInvalidData();
    }

    @Test()
    @Order(53)
    public void verifyInvalidCertificateIdAlertDisplayed_Expected38() {
        NDPEditStandardRatingDialog editStandardRatingDialog = (NDPEditStandardRatingDialog) currentPage;
        assertEquals("Invalid Certificate ID", editStandardRatingDialog.getCertificateIdInvalidAlert(), "Verify Display error message about invalid value");
    }

    @Test()
    @Order(54)
    public void addMoreThanSevenSelections_Step39() {
        NDPEditStandardRatingDialog editStandardRatingDialog = (NDPEditStandardRatingDialog) currentPage;
        editStandardRatingDialog.clickOnDescriptorsAndNotices().selectDescriptorsAndNotices("Gambling")
                .selectDescriptorsAndNotices("Sexual Content")
                .selectDescriptorsAndNotices("Violence")
                .selectDescriptorsAndNotices("Drugs")
                .selectDescriptorsAndNotices("Crime")
                .selectDescriptorsAndNotices("Bad Language")
                .selectDescriptorsAndNotices("Horror");
    }

    @Test()
    @Order(55)
    public void verifyDisplayMessagePreventSelectMoreThanSevenSelections_Expected39() {
        NDPEditStandardRatingDialog editStandardRatingDialog = (NDPEditStandardRatingDialog) currentPage;
        assertEquals("Max 7 selections", editStandardRatingDialog.getDescriptorsAndNoticesWarningMessage(), "Verify Prevent select more than 7 selection");
    }

    @Test()
    @Order(56)
    public void selectRatingValueAgain_Step40() {
        NDPEditStandardRatingDialog editStandardRatingDialog = (NDPEditStandardRatingDialog) currentPage;
        editStandardRatingDialog.selectRatingValue("B");
    }

    @Test()
    @Order(57)
    public void addLessThanSevenSelections_Step41() {
        NDPEditStandardRatingDialog editStandardRatingDialog = (NDPEditStandardRatingDialog) currentPage;
        editStandardRatingDialog.deleteDescriptorsAndNotices("Violence");
    }

    @Test()
    @Order(58)
    public void inputValidCertificateField_Step42() {
        NDPEditStandardRatingDialog editStandardRatingDialog = (NDPEditStandardRatingDialog) currentPage;
        editStandardRatingDialog.selectCertificateFile("Test_abc.jpg");
    }

    @Test()
    @Order(59)
    public void inputSpecialCharactersForCertificateId_Step43() {
        NDPEditStandardRatingDialog editStandardRatingDialog = (NDPEditStandardRatingDialog) currentPage;
        editStandardRatingDialog.enterCertificateId("!@#$%");
    }

    @Test()
    @Order(60)
    public void clickCancelButton_Step44() {
        NDPEditStandardRatingDialog editStandardRatingDialog = (NDPEditStandardRatingDialog) currentPage;
        currentPage = editStandardRatingDialog.clickCancel();
    }

    @Test()
    @Order(61)
    public void verifyAllAgeRatingInformationDisplayedWithoutChanging_Expected44() {
        NDPReleaseInfoAgeRating releaseInfoAgeRating = (NDPReleaseInfoAgeRating) currentPage;
        assertAll(() -> assertEquals("CERO/Generic\n" +
                        "Iarc", releaseInfoAgeRating.getRatingAgency(), "Verify rating type not changed"),
                () -> assertEquals(ratingValue, releaseInfoAgeRating.getRatingValue(0), "Verify rating value not changed"),
                () -> assertEquals(descriptors, releaseInfoAgeRating.getRatingDescriptors(0), "Verify descriptors not changed"),
                () -> assertEquals(ratingIcon, releaseInfoAgeRating.getRatingIcon(0), "Verify rating icon not changed"));
    }


    @Test()
    @Order(62)
    public void navigateToReleaseInfoThenEnterTextInToTextBoxFieldAndSave_Step45() {
        NDPReleaseInfoAgeRating releaseInfoAgeRating = (NDPReleaseInfoAgeRating) currentPage;
        currentPage = releaseInfoAgeRating.nav()
                .clickReleaseInfoWithAlert()
                .enterProvisionalTitle("Provisional title test").save();
    }

    @Test()
    @Order(63)
    public void verifyReleaseInfoPageSaved_Expected45() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        assertEquals("Provisional title test", releaseInfoPage.getProvisionalTitle(), "Verify Provisional title saved");
    }

    @Test()
    @Order(64)
    public void navigateToFeatureSection_Step46() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        currentPage = releaseInfoPage.nav().clickFeatures();
    }

    @Test()
    @Order(65)
    public void verifyFeaturesSectionDisplayed_Expected46() {
        NDPReleaseInfoFeaturesPage featuresPage = (NDPReleaseInfoFeaturesPage) currentPage;
        assertEquals("Features - Nintendo Developer Portal", featuresPage.getPageTitle(), "Verify feature section is displayed");
    }

    @Test()
    @Order(66)
    public void fillOutFeaturesInformation_Step47() {
        NDPReleaseInfoFeaturesPage featuresPage = (NDPReleaseInfoFeaturesPage) currentPage;
        CommonAction.getPatchFeatures(featuresPage, TextConstants.allOff, TextConstants.bothYesNo, TextConstants.allOff, TextConstants.allOff);

    }

    @Test()
    @Order(67)
    public void verifyFeaturesSectionIsCompletedAndTestScenariosAreCreated_Expected47() {
        NDPReleaseInfoFeaturesPage featuresPage = (NDPReleaseInfoFeaturesPage) currentPage;
        NDPReleaseInfoTestScenarios testScenarios = featuresPage.clickContinue().nav().clickTestScenarios();
        currentPage = testScenarios;
        assertAll(() -> assertTrue(testScenarios.nav().isFeaturesCompleted(), "Verify The Features section is completed"),
                () -> assertEquals("2", testScenarios.getCompletedRequired(), "Verify test scenarios are created"),
                () -> assertFalse(testScenarios.nav().isTestScenariosCompleted(), "Verify The Test scenarios section is not completed"));
    }

    @Test()
    @Order(68)
    public void fillOutRequiredInformationForTestScenarios_Step48() {
        NDPReleaseInfoTestScenarios testScenarios = (NDPReleaseInfoTestScenarios) currentPage;
        testScenarios.expandAllSections()
                .expandAllTestScenes()
                .enterAllMethod("Method")
                .enterAllExplain("Explain")
                .clickOnSaveButton();
    }


    @Test()
    @Order(69)
    public void verifyTestScenariosSectionIsCompleted_Expected48() {
        NDPReleaseInfoTestScenarios testScenarios = (NDPReleaseInfoTestScenarios) currentPage;
        assertTrue(testScenarios.nav().isTestScenariosCompleted(), "Verify test scenarios section is completed");
    }

    @Test()
    @Order(70)
    public void fillOutAllRequiredInformationAndClickSubmit_Step49(IJUnitTestReporter testReport) {
        NDPReleaseInfoTestScenarios testScenarios = (NDPReleaseInfoTestScenarios) currentPage;
        NDPReleaseInfoPage releaseInfoPage = testScenarios.nav().clickReleaseInfo();
        CommonAction.selectROMNoCard(releaseInfoPage, TextConstants.selectRomSameAllRegion, TextConstants.english);
        releaseInfoPage.nav().clickGuidelines().selectCompliesWithAllOfTheAboveGuidelines().clickOnSaveButton();

        String originalApp = "Application_" + applicationId + "_v00_multi-11.nsp";
        currentPage = browser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        ROMLoginPage romLoginPage = (ROMLoginPage) currentPage;
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom.reloadPage()
                .selectRequester(TextConstants.test_User)
                .selectType(TextConstants.patch_Release)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .enterProductName(productName)
                .enterApplicationId(applicationId)
                .selectPublishingRelationShip(TextConstants.third_Party_Type)
                .enterDisplayVersion("1.0.1")
                .enterReleaseVersion("01")
                .enterOriginalApp(originalApp)
                .selectCERORating("CERO IARC: 18+")
                .selectMPACheckbox()
                .selectNumberOfProgram("11")
                .clickOnCreateButton()
                .waitIsLoaded();
        fileRomNamePatch = romCreateRom.getRomNameTable("Patch_" + applicationId);
        romCreateRom.clickOnROMFileOnROMTable("Patch_" + applicationId);
        WaitUtils.idle(5000);
        romCreateRom.clickOnUserNameMenu().clickOnLogoutButton();

        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = myProductsPage.clickProductByGameCode(gameCode).clickReleases().selectPatchRelease().nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomNamePatch);
        releaseInfoPage.nav().clickTestScenarios().expandAllSections()
                .expandAllTestScenes()
                .enterAllMethod("Method")
                .enterAllExplain("Explain")
                .clickOnSaveButton();
        releaseInfoPage.nav().clickIssues().openRequiredApprovalTab().performAllIssueAtApprovalTab(false);

        NDPDevelopmentHome developmentHome = releaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        licensingWaiverRequestsTab.waitingForResultTableLoaded()
                .enterSearchingBox(initialCode)
                .clickOnMagnifyingIconButton()
                .waitingForResultTableLoaded();
        int numberOfRequestByPendingStatus = licensingWaiverRequestsTab.collectNumberOfRequestByPendingStatus();
        for (int i = 0; i < numberOfRequestByPendingStatus; i++) {
            developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton().clickOnLicensingWaiverRequestsTab();
            licensingWaiverRequestsTab.waitingForResultTableLoaded()
                    .enterSearchingBox(initialCode)
                    .clickOnMagnifyingIconButton()
                    .waitingForResultTableLoaded();
            currentPage = licensingWaiverRequestsTab.openLicensingWaiverRequestByPendingStatus().selectAssignToMeButton()
                    .selectOKButtonInAssignToMeModal()
                    .selectApproveRequestButton()
                    .selectOKButtonInApproveRequestModal()
                    .waitingForApproveSuccessful();

        }
        NDPViewTaskPage ndpViewTaskPage = (NDPViewTaskPage) currentPage;
        currentPage = ndpViewTaskPage.clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(ndpUser)
                .clickMyProducts().clickProductByGameCode(gameCode).clickReleases().selectPatchRelease().nav().clickSubmitButton();
    }

    @Test()
    @Order(71)
    public void verifyDisplayedSubmissionReviewPage_Expected49() {
        NDPSubmissionReviewPage submissionReviewPage = (NDPSubmissionReviewPage) currentPage;
        assertEquals("Submission Review - Nintendo Developer Portal", submissionReviewPage.getPageTitle(), "Verify Submission Review page is displayed");

    }

    @Test()
    @Order(72)
    public void enterMultipleLineTextOnOtherCommentsForLotcheckFieldAndClickSubmit_Step50(IJUnitTestReporter testReport) {
        NDPSubmissionReviewPage submissionReviewPage = (NDPSubmissionReviewPage) currentPage;
        submissionReviewPage.enterDeveloperComment("Test comment\n" +
                "Developer Comments");
        submissionReviewPage.clickSubmitReview().openNewTabAndCloseCurrentTab();
        NDPMyProductsPage myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPReleaseInfoPage releaseInfoPage = myProductsPage.clickProductByGameCode(gameCode).clickReleases().selectPatchRelease();
        for (int i = 0; i < 300; i++) {
            if (releaseInfoPage.getStatusValue().equals(TextConstants.submitToLotcheck)) {
                break;
            }
            WaitUtils.idle(3000);
            releaseInfoPage.nav().clickFeatures();
            releaseInfoPage.nav().clickReleaseInfo();
        }
        currentPage = releaseInfoPage;
        assertEquals(TextConstants.submitToLotcheck, releaseInfoPage.getStatusValue(), TextConstants.submitSuccess);

    }

    @Test()
    @Order(73)
    public void verifySubmitSuccessfully_Expected50() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        assertEquals("Submitted to Lotcheck", releaseInfoPage.getStatusValue(), "Verify submit to lotcheck Successfully");
    }

//NDP: 74 - STEP 30=73 order

    @Test()
    @Order(74)
    public void loginSlcmsAsLcAdmin_Step51(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        currentPage = hyruleAuthPage.loginWithOvdLink(lcAdminUser);
    }

    @Test()
    @Order(75)
    public void navigateToProductInfoTab_Step52() {
        LotcheckLandingPage landingPage = (LotcheckLandingPage) currentPage;
        LotcheckSubmissionSearchPage submissionSearchPage = landingPage.searchProduct(initialCode);
        LotcheckReportSubmissionOverview submissionOverview = submissionSearchPage.selectSubmission(gameCodePatch, "01", "00");
        currentPage = submissionOverview.navBar().clickProductInformation();
    }

    @Test()
    @Order(76)
    public void checkAgeRating_Step53() {
        LotcheckReportSubmissionProductInformation productInformation = (LotcheckReportSubmissionProductInformation) currentPage;
        productInformation.expandAgeRating();
    }

    @Test()
    @Order(77)
    public void verifyAgeRating_Expected53() {
        LotcheckReportSubmissionProductInformation productInformation = (LotcheckReportSubmissionProductInformation) currentPage;
        assertAll(() -> assertEquals("IARC Generic", productInformation.getAgeRatingsRatingAgency(0), "Verify rating agency"),
                () -> assertEquals(ratingValue, productInformation.getRating(0), "Verify "),
                () -> assertEquals(descriptors, productInformation.getRatingDescriptors(0), "Verify"),
                () -> assertEquals("IARC", productInformation.getRatingMethod(0), "Verify rating method"),
                () -> assertEquals("", productInformation.getAgeRatingCertificate(0), "Verify certificate"),
                () -> assertEquals(ratingIcon, productInformation.getSrcIconAgeRating(), "Verify rating icon"));
    }

    @Test()
    @Order(78)
    public void loginSlcmsByLcTranslatorAndNavigateToLotcheckReport_Step54(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionProductInformation productInformation = (LotcheckReportSubmissionProductInformation) currentPage;
        productInformation.navBar().logOut();
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage landingPage = hyruleAuthPage.loginWithOvdLink(lcTranslatorUser);
        LotcheckSubmissionSearchPage submissionSearchPage = landingPage.searchProduct(initialCode);
        currentPage = submissionSearchPage.selectSubmission(gameCodePatch, "01", "00");
    }

    @Test()
    @Order(79)
    public void verifyLotcheckReportIsDisplayed_Expected54() {
        LotcheckReportSubmissionOverview submissionOverview = (LotcheckReportSubmissionOverview) currentPage;
        LotcheckEditAccountPreferences editAccountPreferences = submissionOverview.navBar().clickMyAccount();
        if (editAccountPreferences.isCurrentLanguageEnglish()) {
            editAccountPreferences.clickCancelButton();
        } else {
            editAccountPreferences.selectLanguage(TextConstants.english)
                    .clickSaveButton();
            submissionOverview.waitPageLoaded();
        }
        assertEquals("Overview Submission: LCR - LCMS", submissionOverview.getPageTitle(), "Verify Lotcheck Report is displayed.");
    }

    @Test()
    @Order(80)
    public void navigateToOverviewTab_Step55() {
        LotcheckReportSubmissionOverview submissionOverview = (LotcheckReportSubmissionOverview) currentPage;
        currentPage = submissionOverview.navBar().clickOverview();
    }

    @Test()
    @Order(81)
    public void verifyDeveloperCommentsField_Expected55() {
        LotcheckReportSubmissionOverview overview = (LotcheckReportSubmissionOverview) currentPage;
        overview.checkTargetAndSourceLanguageDisplayed();
        assertAll(() -> assertEquals("rgba(255, 193, 7, 1)", overview.getColorOfDeveloperCommentsTranslateStatus(), "Verify color off Developer Comments Translate Status"),
                () -> assertEquals("Translate", overview.getDeveloperCommentsTranslateStatus(), "Verify Developer Comments Translate Status"),
                () -> assertEquals("Source:", overview.getDeveloperCommentsSourceFieldName(), "Verify Source fieldName"),
                () -> assertEquals("Target:", overview.getDeveloperCommentsTargetFieldName(), "Verify Target fieldName"),
                () -> assertEquals("Test comment Developer Comments", overview.getDeveloperCommentsSourceValue(), "Verify Source value"), //G3PTDEV4NX-28847
                () -> assertEquals("", overview.getDeveloperCommentsTargetValue(), "Verify Target value"));
    }


    @Test()
    @Order(82)
    public void clickExportAllButtonThenSetEnglishToJapaneseAndExport_Step56() {
        LotcheckReportSubmissionOverview overview = (LotcheckReportSubmissionOverview) currentPage;
        exportFileName_OV = "OV_" + gameCodePatch + "_v01.00_en.xlf";
        overview.clickMenu().clickExportAll().selectLanguage("English to Japanese").clickExport(exportFileName_OV);
    }

    @Test()
    @Order(83)
    public void verifyFileNameExportAndTheContentOfFile_Expected56() throws ParserConfigurationException, IOException, SAXException {
        LotcheckReportSubmissionOverview overview = (LotcheckReportSubmissionOverview) currentPage;
        long size = overview.getFileSizeFromPath(exportFileName_OV);
        xmlFile = getXflFile(exportFileName_OV);
        List<Node> nodes = xmlFile.findNodesWith("trans-unit", Pair.of("resname", "developerComments"));
        assertAll(() -> assertEquals("en", xmlFile.getAttributeValueInTagName("file", "source-language"), "source-language"),
                () -> assertEquals("ja", xmlFile.getAttributeValueInTagName("file", "target-language"), "target-language"),
                () -> assertEquals("Test comment\r\nDeveloper Comments", xmlFile.GetNodesInParentByTagName(nodes.get(0), "source").get(0).getTextContent()), //G3PTDEV4NX-28847 //G3PTDEV4NX-33036
                () -> assertNotEquals("0L", size));

    }

    @Test()
    @Order(84)
    public void updateDataOnExportFile_Step57() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + exportFileName_OV;
        File file = new File(path);
        xmlFile = new XMLReader(file);
        List<Node> nodes = xmlFile.findNodesWith("trans-unit", Pair.of("resname", "developerComments"));
        List<Node> targetNode = xmlFile.GetNodesInParentByTagName(nodes.get(0), "target");
        targetNode.get(0).setTextContent("Test translator");
        Node br = xmlFile.getDocument().createElement("br");
        br.setTextContent("Developer comments test");
        targetNode.get(0).appendChild(br);
        XMLWriter xmlWriter = new XMLWriter(xmlFile.getDocument());
        xmlWriter.save(file);
        logger.logInfo("Text translate: " + "Test translator\n" +
                "Developer comments test");
    }

    @Test()
    @Order(85)
    public void performImportAllFileUpdatedAbove_Step58() {
        LotcheckReportSubmissionOverview overview = (LotcheckReportSubmissionOverview) currentPage;
        overview.clickMenu().clickImportAll()
                .clickSelectFile()
                .selectFileInPC(exportFileName_OV)
                .clickImport();
    }

    @Test()
    @Order(86)
    public void checkDeveloperCommentsValue_Step59() {
        LotcheckReportSubmissionOverview overview = (LotcheckReportSubmissionOverview) currentPage;
        overview.checkTargetAndSourceLanguageDisplayed();
        developerCommentTargetValue = overview.getDeveloperCommentsTargetValue();
    }

    @Test()
    @Order(87)
    public void verifyDeveloperCommentsValue_Expected59() {
        LotcheckReportSubmissionOverview overview = (LotcheckReportSubmissionOverview) currentPage;
        assertAll(() -> assertEquals("Test comment&#13; Developer Comments", overview.getDeveloperCommentsSourceValue(), "Verify Source value after import"), //G3PTDEV4NX-28847 //G3PTDEV4NX-33036
                () -> assertEquals("Test translator Developer comments test", developerCommentTargetValue, "Verify Target value after import")); //G3PTDEV4NX-28847
    }

    @Test()
    @Order(88)
    public void navigateToFeaturesTabAndPerformImportExportFileAbove_Step60() {
        LotcheckReportSubmissionOverview overview = (LotcheckReportSubmissionOverview) currentPage;
        exportFileName_OV = "OV_" + gameCodePatch + "_v01.00_en.xlf";
        currentPage = overview.navBar()
                .clickFeatures()
                .clickMenu().clickImportAll().clickSelectFile().selectInvalidFileInPC(exportFileName_OV);
    }

    @Test()
    @Order(89)
    public void verifyErrorIsDisplayed_Expected60() {
        LotcheckReportSubmissionFeatures features = (LotcheckReportSubmissionFeatures) currentPage;
        String errorMessage_Expected = "A file exported using the Overview tab has been specified. Files exported using other tabs cannot be imported.";
        assertEquals(errorMessage_Expected, features.getErrorMessage());

    }

    @Test()
    @Order(90)
    public void inFeaturesTabCheckTranslateStatusOfAllSections_Step61() {
        LotcheckReportSubmissionFeatures features = (LotcheckReportSubmissionFeatures) currentPage;
        features.clickCloseDialog();
    }

    @Test()
    @Order(91)
    public void verifyTranslateStatusOfAllSections_Expected61() {
        LotcheckReportSubmissionFeatures features = (LotcheckReportSubmissionFeatures) currentPage;
        features.checkTargetAndSourceLanguageDisplayed();
        assertAll(() -> assertTrue(features.isAllTargetValueBlank(), "Verify value of all target field is blank"),
                () -> assertTrue(features.isFieldStructureCorrect(), "Verify Field structure is correct"),
                () -> assertEquals("rgba(255, 193, 7, 1)", features.getColorOfTranslateField("Play Modes"), "Verify Translate (Yellow)"),
                () -> assertEquals("rgba(255, 193, 7, 1)", features.getColorOfTranslateField("Patches"), "Verify Translate (Yellow)"),
                () -> assertEquals("rgba(40, 167, 69, 1)", features.getColorOfTranslateField("Program Specifications"), "Verify Translated (Green)"),
                () -> assertEquals("rgba(40, 167, 69, 1)", features.getColorOfTranslateField("Input Devices"), "Verify Translated (Green)"),
                () -> assertEquals("rgba(40, 167, 69, 1)", features.getColorOfTranslateField("Lotcheck Remote Presence"), "Verify Translated (Green)"),
                () -> assertEquals("Translate", features.getTranslateText("Play Modes"), "Verify translate text"),
                () -> assertEquals("Translated", features.getTranslateText("Program Specifications"), "Verify translate text"),
                () -> assertEquals("Translated", features.getTranslateText("Input Devices"), "Verify translate text"),
                () -> assertEquals("Translated", features.getTranslateText("Lotcheck Remote Presence"), "Verify translate text"),
                () -> assertEquals("Translate", features.getTranslateText("Patches"), "Verify translate text"),
                () -> assertEquals("Selenium Test Automation", features.expandFeature("Patches").getSourceValue("Patches", "Languages added or removed"), "Verify Source field: inputted value (identically to NDP)"),
                () -> assertEquals("Selenium Test Automation", features.collapseFeature("Patches").expandFeature("Play Modes").getSourceValue("Play Modes", "Core content"), "Verify Source field: inputted value (identically to NDP)"));

    }

    @Test()
    @Order(92)
    public void clickActionMenu_Step62() {
        LotcheckReportSubmissionFeatures features = (LotcheckReportSubmissionFeatures) currentPage;
        features.collapseFeature("Play Modes");
        features.clickMenu();
    }

    @Test()
    @Order(93)
    public void verifyDisplayOptions_Expected62() {
        LotcheckReportSubmissionFeatures features = (LotcheckReportSubmissionFeatures) currentPage;
        assertAll(() -> assertEquals("Export", features.getMenuOptions(1)),
                () -> assertEquals("Import", features.getMenuOptions(2)),
                () -> assertEquals("Source Language Display", features.getMenuOptions(3)),
                () -> assertEquals("Target Language Display", features.getMenuOptions(4)));
    }

    @Test()
    @Order(94)
    public void selectSourceLanguageDisplayThenSelectTargetLanguageDisplay_Step63() {
        LotcheckReportSubmissionFeatures features = (LotcheckReportSubmissionFeatures) currentPage;
        features.clickSourceLanguageDisplay();
        isNotAnyTargetDisplayed = features.isNotAnyTargetFieldDisplayed();
        isSourceDisplayed = !features.isNotAnySourceFieldDisplayed();
        features.clickMenu().clickTargetLanguageDisplay();

    }

    @Test()
    @Order(95)
    public void verifyDisplayOfSourceAndTargetField_Expected63() {
        LotcheckReportSubmissionFeatures features = (LotcheckReportSubmissionFeatures) currentPage;
        assertAll(() -> assertTrue(isNotAnyTargetDisplayed),
                () -> assertTrue(isSourceDisplayed),
                () -> assertTrue(!features.isNotAnyTargetFieldDisplayed()),
                () -> assertTrue(features.isNotAnySourceFieldDisplayed()),
                () -> assertEquals("Selenium Test Automation", features.expandFeature("Patches").getTargetValue("Patches", "Languages added or removed"), "Verify target field: Display value of \"Source\" field"),
                () -> assertEquals("Selenium Test Automation", features.collapseFeature("Patches").expandFeature("Play Modes").getTargetValue("Play Modes", "Core content"), "Verify target field: Display value of \"Source\" field"));
    }

    @Test()
    @Order(96)
    public void performReloadPage_Step64() {
        LotcheckReportSubmissionFeatures features = (LotcheckReportSubmissionFeatures) currentPage;
        features.refreshPage();
    }

    @Test()
    @Order(97)
    public void verifyDisplayTargetFieldSameWithStep63_Expected64() {
        LotcheckReportSubmissionFeatures features = (LotcheckReportSubmissionFeatures) currentPage;
        assertAll(() -> assertTrue(!features.isNotAnyTargetFieldDisplayed()),
                () -> assertTrue(features.isNotAnySourceFieldDisplayed()));
    }


    @Test()
    @Order(98)
    public void performExportEnglishToJapanese_Step65() {
        LotcheckReportSubmissionFeatures features = (LotcheckReportSubmissionFeatures) currentPage;
        features.checkTargetAndSourceLanguageDisplayed();
        exportFileName_FS = "FS_" + gameCodePatch + "_v01.00_en.xlf";
        features.clickMenu().clickExportAll().selectLanguage("English to Japanese").clickExport_FeaturesTab(exportFileName_FS);
    }

    @Test()
    @Order(99)
    public void verifyXlfFileName_Expected65() {
        LotcheckReportSubmissionFeatures features = (LotcheckReportSubmissionFeatures) currentPage;
        assertNotEquals("0L", features.getFileSizeFromPath(exportFileName_FS));
    }

    @Test()
    @Order(100)
    public void getSourceAndTargetDataOnExportFile_Step66() throws ParserConfigurationException, IOException, SAXException {
        xmlFile = getXflFile(exportFileName_FS);
    }

    @Test()
    @Order(101)
    public void verifySourceTargetValueOnExportFile_Expected66() {
        List<Node> nodes = xmlFile.findNodesWith("trans-unit", Pair.of("resname", "FS_features_j7_1191"));
        List<Node> nodes2 = xmlFile.findNodesWith("trans-unit", Pair.of("resname", "FS_features_j7_2205"));
        assertAll(() -> assertEquals("Selenium Test Automation", xmlFile.GetNodesInParentByTagName(nodes.get(0), "source").get(0).getTextContent()),
                () -> assertEquals("", xmlFile.GetNodesInParentByTagName(nodes.get(0), "target").get(0).getTextContent()),
                () -> assertEquals("Selenium Test Automation", xmlFile.GetNodesInParentByTagName(nodes2.get(0), "source").get(0).getTextContent()),
                () -> assertEquals("", xmlFile.GetNodesInParentByTagName(nodes2.get(0), "target").get(0).getTextContent()));
    }

    @Test()
    @Order(102)
    public void enterTranslationContentAndImportEnglishToJapanese_Step67() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        LotcheckReportSubmissionFeatures features = (LotcheckReportSubmissionFeatures) currentPage;
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + exportFileName_FS;
        File file = new File(path);
        xmlFile = new XMLReader(file);
        List<Node> nodes = xmlFile.findNodesWith("trans-unit", Pair.of("resname", "FS_features_j7_1191"));
        List<Node> targetNode = xmlFile.GetNodesInParentByTagName(nodes.get(0), "target");
        targetNode.get(0).setTextContent("Test Translate play modes");

        List<Node> nodes2 = xmlFile.findNodesWith("trans-unit", Pair.of("resname", "FS_features_j7_2205"));
        List<Node> targetNode2 = xmlFile.GetNodesInParentByTagName(nodes2.get(0), "target");
        targetNode2.get(0).setTextContent("Test Translate patches");

        XMLWriter xmlWriter = new XMLWriter(xmlFile.getDocument());
        xmlWriter.save(file);
        logger.logInfo("Text translate play mode: " + "Test Translate play modes");
        logger.logInfo("Text Translate patches: " + "Test Translate patches");
        features.clickMenu().clickImportAll().clickSelectFile().selectFileInPC(exportFileName_FS).clickImport_Features();
    }

    @Test()
    @Order(103)
    public void checkSourceAndTargetValueInSlcms_Step68() {
        LotcheckReportSubmissionFeatures features = (LotcheckReportSubmissionFeatures) currentPage;
        features.checkTargetAndSourceLanguageDisplayed();
        features.expandFeature("Patches");

    }

    @Test()
    @Order(104)
    public void verifySourceAndTargetValueInSlcms_Expected68() {
        LotcheckReportSubmissionFeatures features = (LotcheckReportSubmissionFeatures) currentPage;
        assertAll(() -> assertEquals("Selenium Test Automation", features.getSourceValue("Patches", "Languages added or removed")),
                () -> assertEquals("Test Translate patches", features.getTargetValue("Patches", "Languages added or removed")),
                () -> assertEquals("Selenium Test Automation", features.collapseFeature("Patches").expandFeature("Play Modes").getSourceValue("Play Modes", "Core content")),
                () -> assertEquals("Test Translate play modes", features.getTargetValue("Play Modes", "Core content")));
    }

    @Test()
    @Order(105)
    public void exportJapaneseToEnglish_Step69() {
        LotcheckReportSubmissionFeatures features = (LotcheckReportSubmissionFeatures) currentPage;
        features.collapseFeature("Play Modes");
        exportFileName_FS_JA = "FS_" + gameCodePatch + "_v01.00_ja.xlf";
        features.clickMenu()
                .clickExportAll().selectLanguage("Japanese to English").clickExport_FeaturesTab(exportFileName_FS_JA);
    }

    @Test()
    @Order(106)
    public void verifyXlfFileName_Expected69() {
        LotcheckReportSubmissionFeatures features = (LotcheckReportSubmissionFeatures) currentPage;
        assertNotEquals("0L", features.getFileSizeFromPath(exportFileName_FS_JA));
    }

    @Test()
    @Order(107)
    public void getSourceTargetValueInExportFile_Step70() throws ParserConfigurationException, IOException, SAXException {
        xmlFile = getXflFile(exportFileName_FS_JA);
    }

    @Test()
    @Order(108)
    public void verifySourceTagetValueInExportFile_Expected70() {
        List<Node> nodes = xmlFile.findNodesWith("trans-unit", Pair.of("resname", "FS_features_j7_1191"));
        List<Node> nodes2 = xmlFile.findNodesWith("trans-unit", Pair.of("resname", "FS_features_j7_2205"));
        assertAll(() -> assertEquals("Selenium Test Automation", xmlFile.GetNodesInParentByTagName(nodes.get(0), "source").get(0).getTextContent()),
                () -> assertEquals("Test Translate play modes", xmlFile.GetNodesInParentByTagName(nodes.get(0), "target").get(0).getTextContent()),
                () -> assertEquals("Selenium Test Automation", xmlFile.GetNodesInParentByTagName(nodes2.get(0), "source").get(0).getTextContent()),
                () -> assertEquals("Test Translate patches", xmlFile.GetNodesInParentByTagName(nodes2.get(0), "target").get(0).getTextContent()));
    }

    @Test()
    @Order(109)
    public void navigateToTestScenariosTab_Step71() {
        LotcheckReportSubmissionFeatures features = (LotcheckReportSubmissionFeatures) currentPage;
        currentPage = features.navBar().clickTestScenarios();
    }

    @Test()
    @Order(110)
    public void verifyTranslationStatusForAllSection_Expected71() {
        LotcheckReportSubmissionTestScenarios testScenarios = (LotcheckReportSubmissionTestScenarios) currentPage;
        testScenarios.checkTargetAndSourceLanguageDisplayed();
        testScenarios.clickExpandAllCollapseAll();
        assertAll(() -> assertTrue(testScenarios.isAllTargetValueBlank(), "Verify value of all target field is blank"),
                () -> assertTrue(testScenarios.isFieldStructureCorrect(), "Verify Field structure is correct"),
                () -> assertEquals("rgba(255, 193, 7, 1)", testScenarios.getColorOfTranslateField("How to start up the included programs"), "Verify Translate (Yellow)"),
                () -> assertEquals("rgba(255, 193, 7, 1)", testScenarios.getColorOfTranslateField("How to check touch screen"), "Verify Translate (Yellow)"),
                () -> assertEquals("rgba(255, 193, 7, 1)", testScenarios.getColorOfTranslateField("How to check accelerometer on system"), "Verify Translate (Yellow)"),
                () -> assertEquals("Translate", testScenarios.getTranslateText("How to start up the included programs"), "Verify translate text"),
                () -> assertEquals("Translate", testScenarios.getTranslateText("How to check touch screen"), "Verify translate text"),
                () -> assertEquals("Translate", testScenarios.getTranslateText("How to check accelerometer on system"), "Verify translate text"),
                () -> assertEquals("Method", testScenarios.getSourceValue("How to start up the included programs", "Name This Method"), "Verify Source field: inputted value (identically to NDP)"),
                () -> assertEquals("Explain", testScenarios.getSourceValue("How to start up the included programs", "Explain How to Demonstrate This Method"), "Verify Source field: inputted value (identically to NDP)"),
                () -> assertEquals("MethodMethod", testScenarios.getSourceValue("How to check touch screen", "Name This Method"), "Verify Source field: inputted value (identically to NDP)"),
                () -> assertEquals("ExplainExplain", testScenarios.getSourceValue("How to check touch screen", "Explain How to Demonstrate This Method"), "Verify Source field: inputted value (identically to NDP)"),
                () -> assertEquals("MethodMethod", testScenarios.getSourceValue("How to check accelerometer on system", "Name This Method"), "Verify Source field: inputted value (identically to NDP)"),
                () -> assertEquals("ExplainExplain", testScenarios.getSourceValue("How to check accelerometer on system", "Explain How to Demonstrate This Method"), "Verify Source field: inputted value (identically to NDP)"));

    }

    @Test()
    @Order(111)
    public void clickActionMenu_Step72() {
        LotcheckReportSubmissionTestScenarios testScenarios = (LotcheckReportSubmissionTestScenarios) currentPage;
        testScenarios.clickExpandAllCollapseAll();
        testScenarios.clickMenu();
    }

    @Test()
    @Order(112)
    public void verifyOptionOnMenuAction_Expected72() {
        LotcheckReportSubmissionTestScenarios testScenarios = (LotcheckReportSubmissionTestScenarios) currentPage;
        assertAll(() -> assertEquals("Export", testScenarios.getMenuOptions(1)),
                () -> assertEquals("Import", testScenarios.getMenuOptions(2)),
                () -> assertEquals("Source Language Display", testScenarios.getMenuOptions(3)),
                () -> assertEquals("Target Language Display", testScenarios.getMenuOptions(4)));
    }

    @Test()
    @Order(113)
    public void selectSourceLanguageDisplayThenSelectTargetLanguageDisplay_Step73() {
        LotcheckReportSubmissionTestScenarios testScenarios = (LotcheckReportSubmissionTestScenarios) currentPage;
        testScenarios.clickSourceLanguageDisplay();
        isNotAnyTargetDisplayed = testScenarios.isNotAnyTargetFieldDisplayed();
        testScenarios.clickMenu()
                .clickTargetLanguageDisplay();

    }

    @Test()
    @Order(114)
    public void verifyDisplayOfSourceAndTargetField_Expected73() {
        LotcheckReportSubmissionTestScenarios testScenarios = (LotcheckReportSubmissionTestScenarios) currentPage;
        assertAll(() -> assertTrue(isNotAnyTargetDisplayed),
                () -> assertTrue(testScenarios.isNotAnySourceFieldDisplayed()),
                () -> assertEquals("Method", testScenarios.clickExpandAllCollapseAll().getTargetValue("How to start up the included programs", "Name This Method"), "Verify target field: Display value of \"Source\" field"),
                () -> assertEquals("Explain", testScenarios.getTargetValue("How to start up the included programs", "Explain How to Demonstrate This Method"), "Verify target field: Display value of \"Source\" field"),
                () -> assertEquals("MethodMethod", testScenarios.getTargetValue("How to check touch screen", "Name This Method"), "Verify target field: Display value of \"Source\" field"),
                () -> assertEquals("ExplainExplain", testScenarios.getTargetValue("How to check touch screen", "Explain How to Demonstrate This Method"), "Verify target field: Display value of \"Source\" field"),
                () -> assertEquals("MethodMethod", testScenarios.getTargetValue("How to check accelerometer on system", "Name This Method"), "Verify target field: Display value of \"Source\" field"),
                () -> assertEquals("ExplainExplain", testScenarios.getTargetValue("How to check accelerometer on system", "Explain How to Demonstrate This Method"), "Verify target field: Display value of \"Source\" field"));
    }

    @Test()
    @Order(115)
    public void performReloadPage_Step74() {
        LotcheckReportSubmissionTestScenarios testScenarios = (LotcheckReportSubmissionTestScenarios) currentPage;
        testScenarios.refreshPage();
    }

    @Test()
    @Order(116)
    public void verifyDisplayTargetFieldSameWithStep73_Expected74() {
        LotcheckReportSubmissionTestScenarios testScenarios = (LotcheckReportSubmissionTestScenarios) currentPage;
        assertAll(() -> assertTrue(isNotAnyTargetDisplayed),
                () -> assertTrue(testScenarios.isNotAnySourceFieldDisplayed()));
    }


    @Test()
    @Order(117)
    public void performExportEnglishToJapanese_Step75() {
        LotcheckReportSubmissionTestScenarios testScenarios = (LotcheckReportSubmissionTestScenarios) currentPage;
        testScenarios.checkTargetAndSourceLanguageDisplayed();
        exportFileName_TS = "TS_" + gameCodePatch + "_v01.00_en.xlf";
        testScenarios.clickMenu().clickExportAll().selectLanguage("English to Japanese").clickExport_TestScenariosTab(exportFileName_TS);
    }

    @Test()
    @Order(118)
    public void verifyXlfFileName_Expected75() {
        LotcheckReportSubmissionTestScenarios testScenarios = (LotcheckReportSubmissionTestScenarios) currentPage;
        assertNotEquals("0L", testScenarios.getFileSizeFromPath(exportFileName_TS));
    }

    @Test()
    @Order(119)
    public void getExportFile_Step76() throws ParserConfigurationException, IOException, SAXException {
        xmlFile = getXflFile(exportFileName_TS);
    }


    @Test()
    @Order(120)
    public void verifySourceTargetValueOnExportFile_Expected76() {
        Node node = xmlFile.findNodeList("mrk", "Input Devices/How to check touch screen/Method Name");
        Node node2 = xmlFile.findNodeList("mrk", "Input Devices/How to check touch screen/Method Explanation");
        Node node3 = xmlFile.findNodeList("mrk", "Input Devices/How to check accelerometer on system/Method Name");
        Node node4 = xmlFile.findNodeList("mrk", "Input Devices/How to check accelerometer on system/Method Explanation");
        Node node5 = xmlFile.findNodeList("mrk", "Program Specifications/How to start up the included programs/Method Name");
        Node node6 = xmlFile.findNodeList("mrk", "Program Specifications/How to start up the included programs/Method Explanation");
        assertAll(() -> assertEquals("MethodMethod", xmlFile.GetNodesInParentByTagName(node.getParentNode(), "source").get(0).getTextContent()),
                () -> assertEquals("", xmlFile.GetNodesInParentByTagName(node.getParentNode(), "target").get(0).getTextContent()),
                () -> assertEquals("ExplainExplain", xmlFile.GetNodesInParentByTagName(node2.getParentNode(), "source").get(0).getTextContent()),
                () -> assertEquals("", xmlFile.GetNodesInParentByTagName(node2.getParentNode(), "target").get(0).getTextContent()),
                () -> assertEquals("MethodMethod", xmlFile.GetNodesInParentByTagName(node3.getParentNode(), "source").get(0).getTextContent()),
                () -> assertEquals("", xmlFile.GetNodesInParentByTagName(node3.getParentNode(), "target").get(0).getTextContent()),
                () -> assertEquals("ExplainExplain", xmlFile.GetNodesInParentByTagName(node4.getParentNode(), "source").get(0).getTextContent()),
                () -> assertEquals("", xmlFile.GetNodesInParentByTagName(node4.getParentNode(), "target").get(0).getTextContent()),
                () -> assertEquals("Method", xmlFile.GetNodesInParentByTagName(node5.getParentNode(), "source").get(0).getTextContent()),
                () -> assertEquals("", xmlFile.GetNodesInParentByTagName(node5.getParentNode(), "target").get(0).getTextContent()),
                () -> assertEquals("Explain", xmlFile.GetNodesInParentByTagName(node6.getParentNode(), "source").get(0).getTextContent()),
                () -> assertEquals("", xmlFile.GetNodesInParentByTagName(node6.getParentNode(), "target").get(0).getTextContent()));
    }

    @Test()
    @Order(121)
    public void enterTranslationContentAndImportEnglishToJapanese_Step77() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        LotcheckReportSubmissionTestScenarios testScenarios = (LotcheckReportSubmissionTestScenarios) currentPage;
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + exportFileName_TS;
        File file = new File(path);
        xmlFile = new XMLReader(file);
        Node node = xmlFile.findNodeList("mrk", "Input Devices/How to check touch screen/Method Name");
        Node node2 = xmlFile.findNodeList("mrk", "Input Devices/How to check touch screen/Method Explanation");
        Node node3 = xmlFile.findNodeList("mrk", "Input Devices/How to check accelerometer on system/Method Name");
        Node node4 = xmlFile.findNodeList("mrk", "Input Devices/How to check accelerometer on system/Method Explanation");
        Node node5 = xmlFile.findNodeList("mrk", "Program Specifications/How to start up the included programs/Method Name");
        Node node6 = xmlFile.findNodeList("mrk", "Program Specifications/How to start up the included programs/Method Explanation");

        List<Node> targetNode = xmlFile.GetNodesInParentByTagName(node.getParentNode(), "target");
        List<Node> targetNode2 = xmlFile.GetNodesInParentByTagName(node2.getParentNode(), "target");
        List<Node> targetNode3 = xmlFile.GetNodesInParentByTagName(node3.getParentNode(), "target");
        List<Node> targetNode4 = xmlFile.GetNodesInParentByTagName(node4.getParentNode(), "target");
        List<Node> targetNode5 = xmlFile.GetNodesInParentByTagName(node5.getParentNode(), "target");
        List<Node> targetNode6 = xmlFile.GetNodesInParentByTagName(node6.getParentNode(), "target");
        targetNode.get(0).setTextContent("How to check touch screen name");
        targetNode2.get(0).setTextContent("How to check touch screen explain");
        targetNode3.get(0).setTextContent("How to check accelerometer on system name");
        targetNode4.get(0).setTextContent("How to check accelerometer on system explain");
        targetNode5.get(0).setTextContent("How to start up the included programs name");
        targetNode6.get(0).setTextContent("How to start up the included programs explain");

        XMLWriter xmlWriter = new XMLWriter(xmlFile.getDocument());
        xmlWriter.save(file);
        logger.logInfo("Text translate How to check touch screen name: " + "How to check touch screen name");
        logger.logInfo("Text translate How to check touch screen explain: " + "How to check touch screen explain");
        logger.logInfo("Text translate How to check accelerometer on system name: " + "How to check accelerometer on system name");
        logger.logInfo("Text translate How to check accelerometer on system explain: " + "How to check accelerometer on system explain");
        logger.logInfo("Text translate How to start up the included programs name: " + "How to start up the included programs name");
        logger.logInfo("Text translate How to start up the included programs explain: " + "How to start up the included programs explain");

        testScenarios.clickMenu().clickImportAll().clickSelectFile().selectFileInPC(exportFileName_TS).clickImport_TestScenarios();
    }

    @Test()
    @Order(122)
    public void checkSourceAndTargetValueInSlcms_Step78() {
        LotcheckReportSubmissionTestScenarios testScenarios = (LotcheckReportSubmissionTestScenarios) currentPage;
        testScenarios.checkTargetAndSourceLanguageDisplayed();
        testScenarios.clickExpandAllCollapseAll();

    }

    @Test()
    @Order(123)
    public void verifySourceAndTargetValueInSlcms_Expected78() {
        LotcheckReportSubmissionTestScenarios testScenarios = (LotcheckReportSubmissionTestScenarios) currentPage;
        assertAll(() -> assertEquals("Method", testScenarios.getSourceValue("How to start up the included programs", "Name This Method"), "Verify Source field: inputted value (identically to NDP)"),
                () -> assertEquals("Explain", testScenarios.getSourceValue("How to start up the included programs", "Explain How to Demonstrate This Method"), "Verify Source field: inputted value (identically to NDP)"),
                () -> assertEquals("MethodMethod", testScenarios.getSourceValue("How to check touch screen", "Name This Method"), "Verify Source field: inputted value (identically to NDP)"),
                () -> assertEquals("ExplainExplain", testScenarios.getSourceValue("How to check touch screen", "Explain How to Demonstrate This Method"), "Verify Source field: inputted value (identically to NDP)"),
                () -> assertEquals("MethodMethod", testScenarios.getSourceValue("How to check accelerometer on system", "Name This Method"), "Verify Source field: inputted value (identically to NDP)"),
                () -> assertEquals("ExplainExplain", testScenarios.getSourceValue("How to check accelerometer on system", "Explain How to Demonstrate This Method"), "Verify Source field: inputted value (identically to NDP)"),
                () -> assertEquals("How to start up the included programs name", testScenarios.getTargetValue("How to start up the included programs", "Name This Method"), "Verify Source field: inputted value (identically to NDP)"),
                () -> assertEquals("How to start up the included programs explain", testScenarios.getTargetValue("How to start up the included programs", "Explain How to Demonstrate This Method"), "Verify Source field: inputted value (identically to NDP)"),
                () -> assertEquals("How to check touch screen name", testScenarios.getTargetValue("How to check touch screen", "Name This Method"), "Verify Source field: inputted value (identically to NDP)"),
                () -> assertEquals("How to check touch screen explain", testScenarios.getTargetValue("How to check touch screen", "Explain How to Demonstrate This Method"), "Verify Source field: inputted value (identically to NDP)"),
                () -> assertEquals("How to check accelerometer on system name", testScenarios.getTargetValue("How to check accelerometer on system", "Name This Method"), "Verify Source field: inputted value (identically to NDP)"),
                () -> assertEquals("How to check accelerometer on system explain", testScenarios.getTargetValue("How to check accelerometer on system", "Explain How to Demonstrate This Method"), "Verify Source field: inputted value (identically to NDP)"));
    }

    @Test()
    @Order(124)
    public void exportJapaneseToEnglish_Step79() {
        LotcheckReportSubmissionTestScenarios testScenarios = (LotcheckReportSubmissionTestScenarios) currentPage;
        testScenarios.clickExpandAllCollapseAll();
        exportFileName_TS_JA = "TS_" + gameCodePatch + "_v01.00_ja.xlf";
        testScenarios.clickMenu().clickExportAll().selectLanguage("Japanese to English").clickExport_TestScenariosTab(exportFileName_TS_JA);
    }

    @Test()
    @Order(125)
    public void verifyXlfFileName_Expected79() {
        LotcheckReportSubmissionTestScenarios testScenarios = (LotcheckReportSubmissionTestScenarios) currentPage;
        assertNotEquals("0L", testScenarios.getFileSizeFromPath(exportFileName_TS_JA));
    }

    @Test()
    @Order(126)
    public void getSourceTargetValueInExportFile_Step80() throws ParserConfigurationException, IOException, SAXException {
        xmlFile = getXflFile(exportFileName_TS_JA);
    }

    @Test()
    @Order(127)
    public void verifySourceTagetValueInExportFile_Expected80() {
        Node node = xmlFile.findNodeList("mrk", "入力デバイス/タッチスクリーンの確認手順/メソッド名");
        Node node2 = xmlFile.findNodeList("mrk", "入力デバイス/タッチスクリーンの確認手順/メソッドの説明");
        Node node3 = xmlFile.findNodeList("mrk", "入力デバイス/本体加速度センサーの確認手順/メソッド名");
        Node node4 = xmlFile.findNodeList("mrk", "入力デバイス/本体加速度センサーの確認手順/メソッドの説明");
        Node node5 = xmlFile.findNodeList("mrk", "プログラム仕様/内包プログラムの起動方法/メソッド名");
        Node node6 = xmlFile.findNodeList("mrk", "プログラム仕様/内包プログラムの起動方法/メソッドの説明");
        xmlFile.GetNodesInParentByTagName(node.getParentNode(), "source").get(0).getTextContent();
        assertAll(() -> assertEquals("MethodMethod", xmlFile.GetNodesInParentByTagName(node.getParentNode(), "source").get(0).getTextContent()),
                () -> assertEquals("How to check touch screen name", xmlFile.GetNodesInParentByTagName(node.getParentNode(), "target").get(0).getTextContent()),
                () -> assertEquals("ExplainExplain", xmlFile.GetNodesInParentByTagName(node2.getParentNode(), "source").get(0).getTextContent()),
                () -> assertEquals("How to check touch screen explain", xmlFile.GetNodesInParentByTagName(node2.getParentNode(), "target").get(0).getTextContent()),
                () -> assertEquals("MethodMethod", xmlFile.GetNodesInParentByTagName(node3.getParentNode(), "source").get(0).getTextContent()),
                () -> assertEquals("How to check accelerometer on system name", xmlFile.GetNodesInParentByTagName(node3.getParentNode(), "target").get(0).getTextContent()),
                () -> assertEquals("ExplainExplain", xmlFile.GetNodesInParentByTagName(node4.getParentNode(), "source").get(0).getTextContent()),
                () -> assertEquals("How to check accelerometer on system explain", xmlFile.GetNodesInParentByTagName(node4.getParentNode(), "target").get(0).getTextContent()),
                () -> assertEquals("Method", xmlFile.GetNodesInParentByTagName(node5.getParentNode(), "source").get(0).getTextContent()),
                () -> assertEquals("How to start up the included programs name", xmlFile.GetNodesInParentByTagName(node5.getParentNode(), "target").get(0).getTextContent()),
                () -> assertEquals("Explain", xmlFile.GetNodesInParentByTagName(node6.getParentNode(), "source").get(0).getTextContent()),
                () -> assertEquals("How to start up the included programs explain", xmlFile.GetNodesInParentByTagName(node6.getParentNode(), "target").get(0).getTextContent()));
    }

    @Test()
    @Order(128)
    public void navigateLcrOverviewTabAndExportAgain_Step82() {
        LotcheckReportSubmissionTestScenarios testScenarios = (LotcheckReportSubmissionTestScenarios) currentPage;
        currentPage = testScenarios.navBar().clickOverview().clickMenu().clickExportAll()
                .selectLanguage("English to Japanese").clickExport(exportFileName_OV);
    }

    @Test()
    @Order(129)
    public void verifyExportSuccessfully_Expected82() {
        LotcheckReportSubmissionOverview overview = (LotcheckReportSubmissionOverview) currentPage;
        assertNotEquals("0L", overview.getFileSizeFromPath(exportFileName_OV));
    }

    @Test()
    @Order(130)
    public void onFeaturesTabPerformImportExportFileOfOverviewTab_Step83() {
        LotcheckReportSubmissionOverview overview = (LotcheckReportSubmissionOverview) currentPage;
        LotcheckReportSubmissionFeatures features = overview.navBar().clickFeatures();
        currentPage = features;
        LotcheckEditAccountPreferences editAccountPreferences = features.navBar().clickMyAccount();
        if (editAccountPreferences.isCurrentLanguageEnglish()) {
            editAccountPreferences.clickCancelButton();
        } else {
            editAccountPreferences.selectLanguage(TextConstants.english)
                    .clickSaveButton();
            features.waitPageLoaded();
        }
        features.clickMenu().clickImportAll().clickSelectFile().selectFileInPC(exportFileName_OV);
    }

    @Test()
    @Order(131)
    public void verifyErrorMessageWhenImportExportFileOfOverviewTab_Expected83() {
        LotcheckReportSubmissionFeatures features = (LotcheckReportSubmissionFeatures) currentPage;
        String message = features.getErrorMessage();
        String messageExpected_ImportOV = "A file exported using the Overview tab has been specified. Files exported using other tabs cannot be imported.";
        assertEquals(messageExpected_ImportOV, message);
    }

    @Test()
    @Order(132)
    public void repeatStep83WithOverviewAndTestScenariosTabs_Step84() {
        LotcheckReportSubmissionFeatures features = (LotcheckReportSubmissionFeatures) currentPage;
        features.clickCloseDialog().clickMenu().clickImportAll().clickSelectFile().selectFileInPC(exportFileName_TS);
        message_FS_importTS = features.getErrorMessage();
        features.clickCloseDialog();

        LotcheckReportSubmissionTestScenarios testScenarios = features.navBar().clickTestScenarios();
        LotcheckEditAccountPreferences editAccountPreferences = testScenarios.navBar().clickMyAccount();
        if (editAccountPreferences.isCurrentLanguageEnglish()) {
            editAccountPreferences.clickCancelButton();
        } else {
            editAccountPreferences.selectLanguage(TextConstants.english)
                    .clickSaveButton();
            testScenarios.waitPageLoaded();
        }
        testScenarios.clickMenu().clickImportAll().clickSelectFile().selectFileInPC(exportFileName_OV);
        message_TS_importOV = testScenarios.getErrorMessage();
        testScenarios.clickCloseDialog().clickMenu().clickImportAll().clickSelectFile().selectFileInPC(exportFileName_FS);
        message_TS_importFS = testScenarios.getErrorMessage();
        testScenarios.clickCloseDialog();

        LotcheckReportSubmissionOverview overview = testScenarios.navBar().clickOverview();
        editAccountPreferences = overview.navBar().clickMyAccount();
        if (editAccountPreferences.isCurrentLanguageEnglish()) {
            editAccountPreferences.clickCancelButton();
        } else {
            editAccountPreferences.selectLanguage(TextConstants.english)
                    .clickSaveButton();
            overview.waitPageLoaded();
        }
        overview.clickMenu().clickImportAll().clickSelectFile().selectFileInPC(exportFileName_FS);
        message_OV_importFS = overview.getErrorMessage();
        overview.clickCloseDialog().clickMenu().clickImportAll().clickSelectFile().selectFileInPC(exportFileName_TS);
        message_OV_importTS = overview.getErrorMessage();
        overview.clickCloseDialog();
        currentPage = overview;
    }

    @Test()
    @Order(133)
    public void verifyErrorMessage_Expected84() {
        String messageOV = "A file exported using the Overview tab has been specified. Files exported using other tabs cannot be imported.";
        String messageFS = "A file exported using the Features tab has been specified. Files exported using other tabs cannot be imported.";
        String messageTS = "A file exported using the Test Scenarios tab has been specified. Files exported using other tabs cannot be imported.";
        assertAll(() -> assertEquals(messageTS, message_FS_importTS),
                () -> assertEquals(messageFS, message_TS_importFS),
                () -> assertEquals(messageOV, message_TS_importOV),
                () -> assertEquals(messageFS, message_OV_importFS),
                () -> assertEquals(messageTS, message_OV_importTS));

    }


    @Test()
    @Order(134)
    public void editFileExportFromOverviewTab_Step85() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + exportFileName_OV;
        File file = new File(path);
        xmlFile = new XMLReader(file);
        List<Node> nodes = xmlFile.findNodesWith("trans-unit", Pair.of("resname", "developerComments"));
        List<Node> targetNode = xmlFile.GetNodesInParentByTagName(nodes.get(0), "target");
        targetNode.get(0).setTextContent("Test translator");
        Node br = xmlFile.getDocument().createElement("br");
        br.setTextContent("Developer comments test import again");
        targetNode.get(0).appendChild(br);
        XMLWriter xmlWriter = new XMLWriter(xmlFile.getDocument());
        xmlWriter.save(file);
        logger.logInfo("Text translate again: " + "Test translator\n" +
                "Developer comments test import again");
    }

    @Test()
    @Order(135)
    public void importFile_Step86() {
        LotcheckReportSubmissionOverview overview = (LotcheckReportSubmissionOverview) currentPage;
        overview.clickMenu().clickImportAll().clickSelectFile().selectFileInPC(exportFileName_OV).clickImport();
    }

    @Test()
    @Order(136)
    public void verifyTranslateDataUpdatedOnSlcms_Expected86() {
        LotcheckReportSubmissionOverview overview = (LotcheckReportSubmissionOverview) currentPage;
        overview.checkTargetAndSourceLanguageDisplayed();
        assertAll(() -> assertEquals("Test comment&#13; Developer Comments", overview.getDeveloperCommentsSourceValue(), "Verify Source value after import"), //G3PTDEV4NX-33036
                () -> assertEquals("Test translator Developer comments test import again", overview.getDeveloperCommentsTargetValue(), "Verify Target value after import")); //G3PTDEV4NX-28847
    }
    //TestRail: Remove step 101, 103

    @Test()
    @Order(137)
    public void loginTestRailAsTrAdmin_Step87(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailLoginPage.signInTestRail(trAdminUser).navTopTestrail().navigateToManagerDashboard();
        currentPage = testRailManagerDashboardPage.checkErrorPopup();
    }

    @Test()
    @Order(138)
    public void chooseATestcaseOfTestPlanInPreCondition_Step88() {
        TestRailManagerDashboardPage managerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        managerDashboardPage.actionFill().enterTestPlanNameFilter(gameCodePatch)
                .selectStatusUntested();
        managerDashboardPage.actionFill().clickOnApplyFiltersButton();
        managerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun();
        testcaseName_01 = managerDashboardPage.getTestcaseName(1);
        testcaseName_02 = managerDashboardPage.getTestcaseName(2);
        testcaseName_03 = managerDashboardPage.getTestcaseName(3);
        testcaseName_04 = managerDashboardPage.getTestcaseName(4);
        testcaseName_05 = managerDashboardPage.getTestcaseName(5);
        managerDashboardPage.selectTestcaseCheckbox(testcaseName_01);
    }

    @Test()
    @Order(139)
    public void selectSetStatus_Step89() {
        TestRailManagerDashboardPage managerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        currentPage = managerDashboardPage.clickOnSetStatusButton();
    }

    @Test()
    @Order(140)
    public void setStatusIsNotPerformedAndSubStatusIsBlank_Step90() {
        TestRailManagerDashboardPage managerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        managerDashboardPage.selectStatusOnSetStatusModal(TextConstants.notPerformedStatus);
    }

    @Test()
    @Order(141)
    public void clickUpdateStatusButton_Step91() {
        TestRailManagerDashboardPage managerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        managerDashboardPage.clickOnUpdateStatusButtonOnSetStatusModal()
                .waitingLoadingTestSuite();
    }

    @Test()
    @Order(142)
    public void getStatusOfTestcaseAbove_Step92() {
        TestRailManagerDashboardPage managerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        managerDashboardPage.actionFill().selectStatusNotPerformed();
        managerDashboardPage.actionFill().clickOnApplyFiltersButton();
        managerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun();
        testcaseStatus = managerDashboardPage.navTestPlanTable().getStatusValue(1);
    }

    @Test()
    @Order(143)
    public void verifyStatusOfTestcaseAboveIsDisplayedClearly_Expected92() {
        assertEquals("Not Performed", testcaseStatus, "Verify the text 'Not Performed' displays clearly");
    }

    @Test()
    @Order(144)
    public void navigateToMyToDoAndCheckDisplayStatusOfTestcase_Step93() {
        TestRailManagerDashboardPage managerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        managerDashboardPage.selectTestcaseCheckbox(testcaseName_01)
                .clickAssignTo()
                .selectSetValueAssignToRadio()
                .selectUser("Me")
                .clickOk()
                .waitingLoadingTestSuite();
        TestRailMyToDoPage myToDoPage = managerDashboardPage.topNav()
                .navigateToMyToDo();
        myToDoPage.actionFill()
                .enterTestPlanNameFilter(gameCodePatch);
        myToDoPage.actionFill().clickOnApplyFiltersButton();
        myToDoPage.navTestPlanTable()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun();
        currentPage = myToDoPage;
        testcaseStatus = myToDoPage.navTestPlanTable().getStatusValue(testcaseName_01);
    }

    @Test()
    @Order(145)
    public void verifyStatusOfTestcaseAboveOnMyToDoIsDisplayedClearly_Expected93() {
        assertEquals("Not Performed", testcaseStatus, "Verify the text 'Not Performed' on My ToDo page displays clearly");
    }

    @Test()
    @Order(146)
    public void navigateToPlanViewTabAndCheckDisplayStatusOfTestcaseAbove_Step94() {
        TestRailMyToDoPage myToDoPage = (TestRailMyToDoPage) currentPage;
        TestRailPlanViewPage planViewPage = myToDoPage.clickPlanView();
        TestRailTestPlanTable testPlanTable = planViewPage.clickHereToSearchForPlansLink()
                .enterTestPlan(gameCodePatch)
                .clickSearch()
                .selectTestPlan()
                .clickSelectCheckedButton()
                .clickView()
                .expandTestSuites(1)
                .clickOnTheFirstTestRun();
        currentPage = planViewPage;
        testcaseStatus = testPlanTable.getStatusValue(testcaseName_01);
    }

    @Test()
    @Order(147)
    public void verifyStatusOfTestcaseAboveOnPlanViewIsDisplayedClearly_Expected94() {
        assertEquals("Not Performed", testcaseStatus, "Verify the text 'Not Performed' on Plan View page displays clearly");
    }

    @Test()
    @Order(148)
    public void inPlanViewPerformSearchTestPlan_Step95() {
        TestRailPlanViewPage planViewPage = (TestRailPlanViewPage) currentPage;
        currentPage = planViewPage.clickSearchPlansButton().enterTestPlan(gameCodePatch).clickSearch();
    }

    @Test()
    @Order(149)
    public void checkListTestPlanOnRightSiteOfSearchPlansScreen_Step96() {
        TestRailSearchPlansDialog searchPlansDialog = (TestRailSearchPlansDialog) currentPage;
        String testPlanName = gameCodePatch + " 01.00 (1) [NCL] @LCO <DL> " + productName;
        isTestPlanDisplayedCorrect = searchPlansDialog.isTestPlanDisplayedCorrectly(testPlanName);
    }

    @Test()
    @Order(150)
    public void verifyDisplayOfListTestPlanOnRightSite_Expected96() {
        assertTrue(isTestPlanDisplayedCorrect, "Verify list of plans is displayed without getting cut off");
    }

    @Test()
    @Order(151)
    public void prepareCsvFileForImport_Step97(IJUnitTestReporter testReport) {
        TestRailSearchPlansDialog searchPlansDialog = (TestRailSearchPlansDialog) currentPage;
        TestRailManagerDashboardPage managerDashboardPage = searchPlansDialog.clickCancel().navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
        importCsvFileName = "Checklist Export " + gameCodePatch + " - v01_00.csv";
        importCsvFileName_import = "\"Checklist Export " + gameCodePatch + " - v01_00.csv\"";

        //Import testcase one time
        managerDashboardPage.actionFill().enterTestPlanNameFilter(gameCodePatch)
                .selectStatusUntested();
        managerDashboardPage.actionFill().clickOnApplyFiltersButton();
        managerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun();
        String testSuiteName = managerDashboardPage.getTestSuiteName(testcaseName_02);
        TestRailDashboardPage testRailDashboardPage = managerDashboardPage.topNav().navigateToDashboard();
        TestRailProjectTestSuitesPage testSuitesPage = testRailDashboardPage.openProject(initialCode)
                .openTestSuite(testSuiteName);
        TestRailProjectTestSuitesTestcasePage testcasePage = testSuitesPage.openTestCase(testcaseName_02);
        idTestcase_02 = testcasePage.getTestcaseID();
        idTestcase_03 = testcasePage.backToTestSuite().openTestCase(testcaseName_03).getTestcaseID();
        idTestcase_04 = testcasePage.backToTestSuite().openTestCase(testcaseName_04).getTestcaseID();
        idTestcase_05 = testcasePage.backToTestSuite().openTestCase(testcaseName_05).getTestcaseID();
        managerDashboardPage = testcasePage.backToDashboard().navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
        managerDashboardPage.editCsvFile(importCsvFileName, idTestcase_02, "systemUser_01", "mediaType_01");
        managerDashboardPage.actionFill().enterTestPlanNameFilter(gameCodePatch)
                .selectStatusUntested().selectStatusFailed().selectStatusPassed()
                .selectStatusNotPerformed().selectStatusReviewing();
        managerDashboardPage.actionFill().clickOnApplyFiltersButton();
        managerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan();
        managerDashboardPage.clickImportCsv()
                .selectFileInPC(importCsvFileName_import)
                .clickOK()
                .clickOk()
                .clickOK();

        //Assign testcase for data manager user
        managerDashboardPage.clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun();
        managerDashboardPage.selectTestcaseCheckbox(testcaseName_02)
                .clickAssignTo()
                .selectSetValueAssignToRadio()
                .selectUser("Data Manager (NCL)")
                .clickOk()
                .waitingLoadingTestSuite();


        //Login testrail by data manager and open testcase
        browser2 = BrowserManager.getNewInstance();
        currentPage = browser2.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        managerDashboardPage = testRailLoginPage.signInTestRail(trDataManagerUser)
                .navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
        TestRailMyToDoPage myToDoPage = managerDashboardPage.topNav().navigateToMyToDo();
        myToDoPage.actionFill().enterTestPlanNameFilter(gameCodePatch);
        myToDoPage.actionFill().clickOnApplyFiltersButton();
        myToDoPage.navTestPlanTable().clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun()
                .openTestcaseDetail(testcaseName_02);

        //Update csv file and import again
        managerDashboardPage = browser.getPage(TestRailManagerDashboardPage.class, testReport);
        comment = "ウィキ comment DL";
        String testcaseName = "Test case name";
        newMediaType = "日付と番号の形式を決定する。eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee";
        newSystemUsed = "日付と番号の形式を決定する。eeeeeeeeeeeeeeeeeeeeeeeeee";
        managerDashboardPage.updateCsvFile(importCsvFileName, idTestcase_02, "", newSystemUsed, newMediaType, "2h 2m 0s", comment, testcaseName);
        managerDashboardPage.selectCheckboxOfTheFirstTestPlan()
                .clickImportCsv_previousImported().clickOK()
                .selectFileInPC(importCsvFileName_import)
                .clickOK()
                .clickOk()
                .clickOK();
        currentPage = managerDashboardPage;
    }

    @Test()
    @Order(152)
    public void navigateToManageDashboardAndSelectTestcaseImportedAbove_Step98() {
        TestRailManagerDashboardPage managerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        currentPage = managerDashboardPage.clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun()
                .openTestcaseDetail(testcaseName_02);
    }

    @Test()
    @Order(153)
    public void getInformationInTestInformationTab_Step99() {
        TestRailDetailTestcaseDialog detailTestcaseDialog = (TestRailDetailTestcaseDialog) currentPage;
        newMediaType_actual = detailTestcaseDialog.getNewMediaTypeValue();
    }

    @Test()
    @Order(154)
    public void verifyInformationInTestInformationTab_Expected99() {
        TestRailDetailTestcaseDialog detailTestcaseDialog = (TestRailDetailTestcaseDialog) currentPage;
        assertAll(() -> assertEquals(newMediaType, newMediaType_actual, "Verify new media type value"),
                () -> assertEquals("mediaType_01", detailTestcaseDialog.getOldMediaTypeValue(), "Verify old media type value"),
                () -> assertEquals(newSystemUsed, detailTestcaseDialog.getNewSystemUsedValue(), "Verify new system used value"),
                () -> assertEquals("systemUser_01", detailTestcaseDialog.getOldSystemUsedValue(), "Verify old system used value"),
                () -> assertEquals("Untested", detailTestcaseDialog.getNewStatusValue(), "Verify new status"),
                () -> assertEquals("Untested", detailTestcaseDialog.getOldStatusValue(), "Verify old status"),
                () -> assertEquals(comment, detailTestcaseDialog.getCommentValue(), "Verify comment value"),
                () -> assertEquals(importCsvFileName, detailTestcaseDialog.getNameOfImportCsvFile(), "Verify file name imported"),
                () -> assertTrue(detailTestcaseDialog.isNotTestcaseNameUpdatedDisplayed("Test case name"), " Verify testcase name entered in CSV data will not displayed"),
                () -> assertEquals("TRAdmin English (NCL)", detailTestcaseDialog.getUpdatedUser(), "Verify name of user imported"));

    }

    @Test()
    @Order(155)
    public void clickViewTextLink_Step100() {
        TestRailDetailTestcaseDialog detailTestcaseDialog = (TestRailDetailTestcaseDialog) currentPage;
        currentPage = detailTestcaseDialog.clickViewElapsedHistory();
        BrowserManager.closeBrowser(browser2);

    }

    @Test()
    @Order(156)
    public void verifyViewElapsedTime_Expected100() {
        TestRailViewElapsedTimeDialog viewElapsedTimeDialog = (TestRailViewElapsedTimeDialog) currentPage;
        assertAll(() -> assertEquals("2h 2m 0s", viewElapsedTimeDialog.getTotalElapsedTimeValue(), "Verify Total Elapsed Time"),
                () -> assertEquals("2h 2m 0s", viewElapsedTimeDialog.getElapsedValue(), "Verify Elapsed time field"),
                () -> assertEquals("Stopped", viewElapsedTimeDialog.getPausedValue(), "Verify Paused field"),
                () -> assertEquals("-", viewElapsedTimeDialog.getStartOnValue(), "Verify Started on field"),
                () -> assertEquals("TRAdmin English (NCL)", viewElapsedTimeDialog.getNameValue(), "Verify user who perform importing"));

    }

    //remove step 101
    @Test()
    @Order(157)
    public void loginTestRailByTrDirector_Step102() {
        TestRailViewElapsedTimeDialog viewElapsedTimeDialog = (TestRailViewElapsedTimeDialog) currentPage;
        currentPage = viewElapsedTimeDialog.clickClose().clickClose().logOut().signInTestRail(trDirectorUser)
                .navTopTestrail().navigateToManagerDashboard().checkErrorPopup();


    }


    //remove step 103
    @Test()
    @Order(158)
    public void navigateToPlanViewTabAndSearchTestPlan_Step104() {
        TestRailManagerDashboardPage managerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        TestRailPlanViewPage planViewPage = managerDashboardPage.topNav().clickPlanView();
        currentPage = planViewPage.clickSearchPlansButton().enterTestPlan(initialCode).clickSearch().clickCheckAll().clickSelectCheckedButton().clickView();
    }

    @Test()
    @Order(159)
    public void checkDisplayOfImportedCsvFileField_Step105() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        isImportedCsvFileCheckboxChecked = testPlanTable.isImportedCsvFileCheckboxChecked();
        isNoImportedCsvFileCheckboxChecked = testPlanTable.isNoImportedCsvFileCheckboxChecked();
        isYesImportedCsvFileCheckboxChecked = testPlanTable.isYesImportedCsvFileCheckboxChecked();
    }

    @Test()
    @Order(160)
    public void verifyDisplayOfImportedCsvFileField_Expected105() {
        assertAll(() -> assertTrue(!isYesImportedCsvFileCheckboxChecked, "Verify Yes checkbox is not checked"),
                () -> assertTrue(!isImportedCsvFileCheckboxChecked, "Verify \"Imported CSV File?\" checkbox is not checked"),
                () -> assertTrue(!isNoImportedCsvFileCheckboxChecked, "Verify No checkbox is not checked"));
    }

    @Test()
    @Order(161)
    public void clickApplyFilters_Step106() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        testPlanTable.actionFill().clickOnApplyFiltersButton();
    }

    @Test()
    @Order(162)
    public void verifySearchResult_Expected106() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        assertAll(() -> assertTrue(testPlanTable.isTestPlanDisplayed(gameCode + " 00.00"), "Verify display test plan of initial release"),
                () -> assertTrue(testPlanTable.isTestPlanDisplayed(gameCode + " 00.100"), "Verify display test plan of add physical release"),
                () -> assertTrue(testPlanTable.isTestPlanDisplayed(gameCodePatch + " 01.00"), "Verify display test plan of patch release"));
    }

    @Test()
    @Order(163)
    public void selectYesImportedCsvFileCheckbox_Step107() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        testPlanTable.selectYesImportCsvFile();
    }

    @Test()
    @Order(164)
    public void verifyDisplayOfImportedCsvFileFieldAfterSelectYes_Expected107() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        assertAll(() -> assertTrue(testPlanTable.isYesImportedCsvFileCheckboxChecked(), "Verify Yes checkbox is checked"),
                () -> assertTrue(!testPlanTable.isImportedCsvFileCheckboxChecked(), "Verify Imported Csv File checkbox is not checked"),
                () -> assertTrue(!testPlanTable.isNoImportedCsvFileCheckboxChecked(), "Verify No checkbox is not checked"));
    }

    @Test()
    @Order(165)
    public void clickApplyFiltersAfterSelectYes_Step108() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        testPlanTable.actionFill().clickOnApplyFiltersButton();
    }

    @Test()
    @Order(166)
    public void verifyResultAfterSelectYesAndSearch_Expected108() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        assertAll(() -> assertTrue(!testPlanTable.isTestPlanDisplayed(gameCode + " 00.00"), "Verify not display test plan of initial release"),
                () -> assertTrue(!testPlanTable.isTestPlanDisplayed(gameCode + " 00.100"), "Verify not display test plan of add physical release"),
                () -> assertTrue(testPlanTable.isTestPlanDisplayed(gameCodePatch + " 01.00"), "Verify display test plan of patch release"));
    }


    @Test()
    @Order(167)
    public void selectNoCheckBoxAndUncheckYesCheckBox_Step109() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        testPlanTable.selectImportCsvFile();
    }

    @Test()
    @Order(168)
    public void verifyOfImportedCsvFileFieldAgain_Expected109() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        assertAll(() -> assertTrue(testPlanTable.isImportedCsvFileCheckboxChecked(), "Verify Yes checkbox is checked"),
                () -> assertTrue(testPlanTable.isNoImportedCsvFileCheckboxChecked(), "Verify \"Imported CSV File?\" checkbox is checked"),
                () -> assertTrue(testPlanTable.isYesImportedCsvFileCheckboxChecked(), "Verify No checkbox is checked"));
    }

    @Test()
    @Order(169)
    public void clickApplyFiltersAgain_Step110() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        testPlanTable.actionFill().clickOnApplyFiltersButton();
    }

    @Test()
    @Order(170)
    public void verifySearchResultAgain_Expected110() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        assertAll(() -> assertTrue(testPlanTable.isTestPlanDisplayed(gameCode + " 00.00"), "Verify display test plan of initial release"),
                () -> assertTrue(testPlanTable.isTestPlanDisplayed(gameCode + " 00.100"), "Verify display test plan of add physical release"),
                () -> assertTrue(testPlanTable.isTestPlanDisplayed(gameCodePatch + " 01.00"), "Verify display test plan of patch release"));
    }

    @Test()
    @Order(171)
    public void loginTestRailByTrAdminThenNavigateToManageDashboardAndSelectTestPlan_Step111() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        TestRailManagerDashboardPage managerDashboardPage = testPlanTable.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton()
                .signInTestRail(trAdminUser).navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
        managerDashboardPage.actionFill().enterTestPlanNameFilter(gameCodePatch).selectStatusUntested();
        managerDashboardPage.actionFill().clickOnApplyFiltersButton();
        managerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan();
        currentPage = managerDashboardPage;
    }

    @Test()
    @Order(172)
    public void prepareCsvFileForImport_Step112() {
        TestRailManagerDashboardPage managerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        managerDashboardPage.updateCsvFile(importCsvFileName, idTestcase_03, idTestcase_04, idTestcase_05);

    }

    @Test()
    @Order(173)
    public void selectImportAndSelectFileToImport_Step113() {
        TestRailManagerDashboardPage managerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        currentPage = managerDashboardPage.clickImportCsv_previousImported().clickOK()
                .selectFileInPC(importCsvFileName_import);

    }

    @Test()
    @Order(174)
    public void clickOkToCheckFileAndOkToImportFile_Step114() {
        TestRailConfirmCsvFileToImportDialog confirmCsvFileToImportDialog = (TestRailConfirmCsvFileToImportDialog) currentPage;
        TestRailConfirmImportedContentDialog confirmImportedContentDialog = confirmCsvFileToImportDialog.clickOK();
        isTestcase02DisplayedOnWarningCondition = confirmImportedContentDialog.isTestcaseDisplayedOnWarningCondition(idTestcase_03);
        isTestcase04DisplayedOnWarningCondition = confirmImportedContentDialog.isTestcaseDisplayedOnWarningCondition(idTestcase_05);
        currentPage = confirmImportedContentDialog.clickOk()
                .clickOK();

    }

    @Test()
    @Order(175)
    public void openTestPlanToCheckResultAfterImported_Step115() {
        TestRailManagerDashboardPage managerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        managerDashboardPage.clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun();
    }

    @Test()
    @Order(176)
    public void verifyResultAfterImport_Expected115() {
        TestRailManagerDashboardPage managerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        managerDashboardPage.actionFill().selectStatusNotPerformed();
        managerDashboardPage.actionFill().clickOnApplyFiltersButton();
        managerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun();
        TestRailDetailTestcaseDialog detailTestcaseDialog = managerDashboardPage.openTestcaseDetail(testcaseName_04);
        String systemUsed_testcase04 = detailTestcaseDialog.getCurrentSystemUsedValue();
        String mediaType_testcase04 = detailTestcaseDialog.getCurrentMediaTypeValue();
        String status_testcase04 = detailTestcaseDialog.getCurrentStatusValue();
        detailTestcaseDialog.clickClose().openTestcaseDetail(testcaseName_03);
        String systemUsed_testcase03 = detailTestcaseDialog.getCurrentSystemUsedValue();
        String mediaType_testcase03 = detailTestcaseDialog.getCurrentMediaTypeValue();
        detailTestcaseDialog.clickClose().openTestcaseDetail(testcaseName_05);
        String systemUsed_testcase05 = detailTestcaseDialog.getCurrentSystemUsedValue();
        String mediaType_testcase05 = detailTestcaseDialog.getCurrentMediaTypeValue();
        detailTestcaseDialog.clickClose();
        assertAll(() -> assertTrue(isTestcase02DisplayedOnWarningCondition, "Verify TC that change status Untest to \"-\" is in warning conditions"),
                () -> assertTrue(isTestcase04DisplayedOnWarningCondition, "Verify TC that change status Untest to \"-\" is in warning conditions"),
                () -> assertEquals("", systemUsed_testcase04, "Verify system used after import"),
                () -> assertEquals("", mediaType_testcase04, "Verify media type after import"),
                () -> assertEquals("Not Performed", status_testcase04, "Verify status after import"),
                () -> assertEquals("System Used 02", systemUsed_testcase05, "Verify system used after import"),
                () -> assertEquals("Media Type 02", mediaType_testcase05, "Verify media type after import"),
                () -> assertEquals("", systemUsed_testcase03, "Verify system used after import"),
                () -> assertEquals("", mediaType_testcase03, "Verify media type after import"));

    }


    @Test()
    @Order(177)
    public void importTestcaseHasSystemUsedInvalid_Step116() {
        TestRailManagerDashboardPage managerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        String systemUsed = "There is no feature to undo after import There is no feature to undo after import There is no feature to undo after import There is no feature to undo after import There is no feature to undo after import There is no feature to undo after import  impo";
        managerDashboardPage.updateCsvFile(importCsvFileName, idTestcase_02, "Not Performed", systemUsed, "", "", "", "");
        currentPage = managerDashboardPage.selectCheckboxOfTheFirstTestPlan().clickImportCsv_previousImported().clickOK()
                .selectFileInPC(importCsvFileName_import)
                .clickOk_Error();
    }

    @Test()
    @Order(178)
    public void verifyErrorMessage_Expected116() {
        TestRailMessageDialog messageDialog = (TestRailMessageDialog) currentPage;
        String errorMessage_actual = messageDialog.getErrorMessage();
        messageDialog.clickOK();
        String errorMessage_expected = "Contains data that exceeds the maximum number of characters.\n" +
                "(Row 2,System used DL)\n" +
                "Please try again.";
        assertEquals(errorMessage_expected, errorMessage_actual, "Verify Error message is show when import system used more than 250 characters");

    }

    public static XMLReader getXflFile(String fileName) throws ParserConfigurationException, IOException, SAXException {
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + fileName;
        File file = new File(path);
        XMLReader xml = new XMLReader(file);
        logger.logInfo(fileName);
        return xml;
    }


// (116+68) - step 30 - step and expect 81 - step and expected 101 - step 103 = 178 orders

}
