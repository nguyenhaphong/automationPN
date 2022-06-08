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
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportMatrix;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportNumberOverview;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportProductProductInformation;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportTestPlanOverview;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPLicensingWaiverRequestsTab;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPCreateReleasePage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardProductInfoTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardReleasesTab;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoFeaturesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoIssuePage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoROMUpload;
import net.nintendo.automation.ui.models.rom.ROMCreateRom;
import net.nintendo.automation.ui.models.rom.ROMLoginPage;
import net.nintendo.automation.ui.models.testrail.TestRailDashboardPage;
import net.nintendo.automation.ui.models.testrail.TestRailLoginPage;
import net.nintendo.automation.ui.models.testrail.TestRailManagerDashboardPage;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classname: T3391387
 * Version: 1.0.0
 * Branch: tasks/#170822/T3391387
 * Purpose:
 */

@Tag("T3391387")
@ScenarioTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3391387 extends CommonBaseTest {

    private static Browser browser;
    private static User romUser;
    private static User ndpUser;
    private static User slcmsUser;
    private static User testrailUser;
    private static User licUser;
    private static CommonUIComponent currentPage;
    private static TestLogger logger;

    private static String productName;

    private static String gameCode;
    private static String initialCode;
    private static String applicationId;
    private static String romInitialReleaseName;
    private static String romPatchName;
    private static String publishingRelationship;
    private static String whiteColor = "#FFFFFF";

    private static boolean noaOverallQueue;
    private static boolean noaTesterAssignmentQueue;
    private static boolean noaTestSetupReady;
    private static boolean noaInTestSetup;
    private static boolean noaInTesting;
    private static boolean noaJudgment;
    private static boolean noaPendingSolution;
    private static boolean noaFinish;
    private static boolean noeOverallQueue;
    private static boolean noeTesterAssignmentQueue;
    private static boolean noeTestSetupReady;
    private static boolean noeInTestSetup;
    private static boolean noeInTesting;
    private static boolean noeJudgment;
    private static boolean noePendingSolution;
    private static boolean noeFinish;

    private static ArrayList<HashMap<String, String>> listOverall = new ArrayList<HashMap<String, String>>();
    private static ArrayList<HashMap<String, String>> listAssignTester = new ArrayList<HashMap<String, String>>();
    private static ArrayList<HashMap<String, String>> listTestSetupReady = new ArrayList<HashMap<String, String>>();
    private static ArrayList<HashMap<String, String>> listInTestSetup = new ArrayList<HashMap<String, String>>();
    private static ArrayList<HashMap<String, String>> listInTesting = new ArrayList<HashMap<String, String>>();
    private static ArrayList<HashMap<String, String>> listJudgementApproval = new ArrayList<HashMap<String, String>>();
    private static ArrayList<HashMap<String, String>> listPendingSolution = new ArrayList<HashMap<String, String>>();
    private static ArrayList<HashMap<String, String>> listFinished = new ArrayList<HashMap<String, String>>();
    private static ArrayList<String[]> regions = new ArrayList<String[]>();
    String[] nclCondition = new String[]{TextConstants.NCL};
    String[] noaCondition = new String[]{TextConstants.NOA};
    String[] noeCondition = new String[]{TextConstants.NOE};
    String[] notSetCondition = new String[]{"Not Set"};
    String[] nclAndNOECondition = new String[]{TextConstants.NCL, TextConstants.NOE};
    String[] threeRegionCondition = new String[]{TextConstants.NCL, TextConstants.NOA, TextConstants.NOE};
    String[] fullOptionCondition = new String[]{TextConstants.NCL, TextConstants.NOA, TextConstants.NOE, "Not Set"};
    private static boolean verifyLCOOverall;
    private static boolean verifyLCOAssignTesterQueue;
    private static boolean verifyLCOTestSetupReady;
    private static boolean verifyLCOInTestSetup;
    private static boolean verifyLCOInTesting;
    private static boolean verifyLCOJudgementApproval;
    private static boolean verifyLCOPendingSolution;
    private static boolean verifyLCOFinished;


    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_DungCV);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
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

    public static void submitInitialToLotCheckAndCreateOnCardRom(IJUnitTestReporter testReport) {
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
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        CommonAction.issueGameCode(ndpProductDashboardPage, 30, "NO");
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();
        applicationId = ndpProductDashboardProductInfoTab.getApplicationID();
        gameCode = ndpProductDashboardProductInfoTab.getGameCode();
        initialCode = ndpProductDashboardProductInfoTab.getInitialCode();

        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        NDPReleaseInfoPage releaseInfoPage = ndpReleaseInfoPage.nav().clickReleaseInfo();
        CommonAction.selectROMSameAllRegion(releaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);
        NDPReleaseInfoFeaturesPage releaseInfoFeaturesPage = releaseInfoPage.nav().clickFeatures();

        CommonAction.getFeatureOptionData(releaseInfoPage, TextConstants.allOff,
                TextConstants.allOff, TextConstants.allOff, false, "", false, "");
        releaseInfoFeaturesPage.clickContinue();
        releaseInfoPage.nav().clickGuidelines().selectCompliesWithAllOfTheAboveGuidelines().clickOnSaveButton();
        releaseInfoPage.nav().clickAgeRating().clickOnAddStandardRatingButton()
                .selectStandardAgeRating("CERO")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectRatingValue("Not required")
                .clickOnSaveButtonInEditStandardRating();
        ndpReleaseInfoPage.nav().clickTestScenarios();
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
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, romInitialReleaseName);
        releaseInfoPage.nav().clickReleaseInfo();
        releaseInfoPage.nav().clickGuidelines();
        releaseInfoPage.nav().clickAgeRating();
        releaseInfoPage.nav().clickIssues().openRequiredApprovalTab().performAllIssueAtApprovalTab(false);

        NDPDevelopmentHome developmentHome = releaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, initialCode);
        WaitUtils.idle(5000);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton().clickSignInPage().signIn(ndpUser).clickMyProducts()
                .clickProductByGameCode(gameCode).clickReleases().selectInitialRelease().nav().clickSubmitButton()
                .clickSubmit();
        WaitUtils.idle(3000);
    }

    private static void testRailJudgment(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailLoginPage.signInTestRail(testrailUser).navTopTestrail().navigateToManagerDashboard();
        CommonAction.setStatusOnTestrail(testRailManagerDashboardPage, initialCode, TextConstants.passedStatus, TextConstants.percentCompleted, TextConstants.OK);
        testRailManagerDashboardPage.logOut();
    }

    private static void slcmsApproveJudgment(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
        LotcheckQueuePage nclLotCheckQueue = lotcheckLandingPage.clickJudgment(LotcheckRegion.NCL);
        CommonAction.approvalJudgmentStatus(nclLotCheckQueue, initialCode, 300, TextConstants.OK, true, "1");
    }

    private static void createRom(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        //Rom initial release
        ROMLoginPage romLoginPage = (ROMLoginPage) currentPage;
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom
                .selectType(TextConstants.initial_Release)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .enterProductName(productName)
                .enterApplicationId(applicationId)
                .selectPublishingRelationShip(publishingRelationship)
                .enterDisplayVersion("1.0.0")
                .selectCERORating("CERO: Not required")
                .enterReleaseVersion("00")
                .selectRequester(TextConstants.test_User)
                .clickOnCreateButton()
                .waitIsLoaded();
        romInitialReleaseName = romCreateRom.getRomNameTable(applicationId);
        romCreateRom.clickOnROMFileOnROMTable(romInitialReleaseName);
        WaitUtils.idle(5000);

        //Rom patch
        String originalApp = "Application_" + applicationId + "_v00.nsp";
        romCreateRom.selectRequester(TextConstants.test_User)
                .selectRequester(TextConstants.test_User)
                .selectType(TextConstants.patch_Release)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .enterProductName(productName)
                .enterApplicationId(applicationId)
                .selectPublishingRelationShip(publishingRelationship)
                .enterDisplayVersion("1.0.1")
                .enterReleaseVersion("01")
                .enterOriginalApp(originalApp)
                .selectCERORating("CERO: Not required")
                .selectClassIndRating("ClassInd: Not required")
                .selectUSKRating("USK: Not required")
                .clickOnCreateButton()
                .waitIsLoaded();
        romPatchName = romCreateRom.getRomNameTable(applicationId);
        romCreateRom.clickOnROMFileOnROMTable(romPatchName);
        WaitUtils.idle(5000);

    }

    @Test()
    @Order(1)
    public void initialPassLC_TestCondition(IJUnitTestReporter testReport) {
        submitInitialToLotCheckAndCreateOnCardRom(testReport);
        testRailJudgment(testReport);
        slcmsApproveJudgment(testReport);
    }


    @Test()
    @Order(2)
    public void loginNDP_step1(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN);
        NDPDevelopmentHome ndpPage = (NDPDevelopmentHome) currentPage;
        NDPMyProductsPage myProductsPage = ndpPage.clickMyProducts();
        currentPage = myProductsPage;
    }


    @Test()
    @Order(3)
    public void createPatchRelease_step2(IJUnitTestReporter testReport) {
        NDPMyProductsPage myProductsPage = browser.getPage(NDPMyProductsPage.class, testReport);
        NDPProductDashboardReleasesTab releasesTab = myProductsPage.clickProductByName(productName).clickReleases();
        NDPCreateReleasePage ndpCreateReleasePage = releasesTab.clickCreateNewReleaseButton();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpCreateReleasePage
                .enterDisplayVersion("1.0.1")
                .selectExpectedSubmissionDate(5)
                .selectExpectedReleaseDate(25)
                .enterDescribeTheFeaturesPlanned(TextConstants.enterDescribeTheFeaturesPlanned)
                .enterDescribeTheBugsIssuesPlanned(TextConstants.enterDescribeTheBugsIssuesPlanned)
                .clickCreateButton();
        ndpReleaseInfoPage.nav().clickReleaseInfo();
        CommonAction.selectROMWithSubmissionType(ndpReleaseInfoPage, TextConstants.preCheck, TextConstants.selectRomSameAllRegion, TextConstants.english);
        ndpReleaseInfoPage.selectTargetSaleRegionAmericas().selectTargetSaleRegionEurope().save();
    }

    @Test()
    @Order(4)
    public void submitPatchToLC_step3(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage ndpReleaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        NDPReleaseInfoFeaturesPage releaseInfoFeaturesPage = ndpReleaseInfoPage.nav().clickFeatures();
        CommonAction.getPatchFeatures(ndpReleaseInfoPage, TextConstants.allOffPrecheck, TextConstants.allOff, TextConstants.allOff, TextConstants.allOff);
        releaseInfoFeaturesPage.clickContinue();
        ndpReleaseInfoPage.nav().clickTestScenarios();
        ndpReleaseInfoPage.nav().clickGuidelines().selectCompliesWithAllOfTheAboveGuidelines().clickOnSaveButton();
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = ndpReleaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, romPatchName);
        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
        issuePage.openRequiredApprovalTab();
        issuePage.expandFirstUnresolvedRequiredApprovals()
                .selectDesiredApprovalTypeAtPermanentApprovalRequiredApproval()
                .enterReasonForRequest(TextConstants.enterReasonForRequest)
                .clickOnSendResolutionButton();
        CommonAction.enterReasonForRequest(issuePage);
        NDPDevelopmentHome developmentHome = ndpReleaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, initialCode);

        developmentHome.clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(ndpUser)
                .clickMyProducts().clickProductByGameCode(initialCode).clickReleases().selectPatchRelease();
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.enterDeveloperComment).clickSubmit();
        WaitUtils.idle(10000);
    }

    @Test()
    @Order(5)
    public void loginLCAdminNCL_step4(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
    }

    @Test()
    @Order(6)
    public void accessNCLQueue_step5(IJUnitTestReporter testReport) {
        LotcheckLandingPage lotcheckLandingPage = browser.getPage(LotcheckLandingPage.class, testReport);
        lotcheckLandingPage.clickOverallLotcheck(LotcheckRegion.NCL);
    }

    @Test()
    @Order(7)
    public void verifyLCOCharacter_expect5(IJUnitTestReporter testReport) {
        LotcheckQueuePage nclLotcheckQueue = browser.getPage(LotcheckQueuePage.class, testReport);
        LotcheckTestPlanRow lotcheckTestPlanRow = nclLotcheckQueue.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        verifyLCOOverall = lotcheckTestPlanRow.iconLCOIsDisplay();
        assertTrue(verifyLCOOverall, "Verify The character string @LCO is added to the end of the TestPlan game code");

    }

    @Test()
    @Order(8)
    public void addLotcheckOwnerCondition_step6(IJUnitTestReporter testReport) {
        LotcheckQueuePage nclLotcheckQueue = browser.getPage(LotcheckQueuePage.class, testReport);
        nclLotcheckQueue.refreshPageSLCMS();
        nclLotcheckQueue.selectMoreFilter(TextConstants.lotcheckOwner);
    }

    @Test()
    @Order(9)
    public void searchConditionLotcheckOwner_step7(IJUnitTestReporter testReport) {
        LotcheckQueuePage nclLotcheckQueue = browser.getPage(LotcheckQueuePage.class, testReport);
        nclLotcheckQueue.clickLotcheckOwnerCondition();
        HashMap<String, String> regionOverall = new HashMap<>();
        regions.add(nclCondition);
        regions.add(noaCondition);
        regions.add(noeCondition);
        regions.add(notSetCondition);
        regions.add(nclAndNOECondition);
        regions.add(threeRegionCondition);
        regions.add(fullOptionCondition);

        listOverall = addTestPlanLotCheckOwnerForRegion(nclLotcheckQueue, regionOverall, true);
        currentPage = nclLotcheckQueue;

    }

    private ArrayList<HashMap<String, String>> addTestPlanLotCheckOwnerForRegion(LotcheckQueuePage nclLotcheckQueue, HashMap<String, String> arrRegion, Boolean refresh) {
        ArrayList<HashMap<String, String>> listTestPlanOwner = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < regions.size(); i++) {
            String[] strings = regions.get(i);
            for (int k = 0; k < strings.length; k++) {
                System.out.println(strings[k]);
                nclLotcheckQueue.selectConditionRegion(strings[k]);
            }
            for (int j = 1; j <= 6; j++) { //loop 6 tab, each tab 3 testplan
                nclLotcheckQueue.clickTab(j);
                if (!nclLotcheckQueue.getNumberTestPlan(j).equalsIgnoreCase("0")) {
                    arrRegion = nclLotcheckQueue.getTestPlan();//hashmap: key: testplanName, value: lotcheckOwner
                }
            }
            listTestPlanOwner.add(arrRegion);
            if (refresh)
                nclLotcheckQueue.refreshPageSLCMS();
            else
                nclLotcheckQueue.unselectMoreFilter("Lotcheck Owner");
            nclLotcheckQueue.selectMoreFilter(TextConstants.lotcheckOwner).clickLotcheckOwnerCondition();
        }
        return listTestPlanOwner;
    }

    @Test()
    @Order(10)
    public void verifySearchResult_expect7(IJUnitTestReporter testReport) {
        LotcheckQueuePage nclLotcheckQueue = browser.getPage(LotcheckQueuePage.class, testReport);
        currentPage = nclLotcheckQueue;
    }

    @Test()
    @Order(11)
    public void assignTesterQueue_step8(IJUnitTestReporter testReport) {
        LotcheckQueuePage nclLotcheckQueue = browser.getPage(LotcheckQueuePage.class, testReport);
        LotcheckLandingPage lotcheckLandingPage = nclLotcheckQueue.navLandingPage();
        nclLotcheckQueue = lotcheckLandingPage.clickTesterAssignment(LotcheckRegion.NCL);
        LotcheckTestPlanRow lotcheckTestPlanRow = nclLotcheckQueue.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        verifyLCOAssignTesterQueue = lotcheckTestPlanRow.iconLCOIsDisplay();
        nclLotcheckQueue.refreshPageSLCMS();
        nclLotcheckQueue.selectMoreFilter(TextConstants.lotcheckOwner).clickLotcheckOwnerCondition();
        HashMap<String, String> regionAssignTester = new HashMap<>();

        listAssignTester = addTestPlanLotCheckOwnerForRegion(nclLotcheckQueue, regionAssignTester, false);
        currentPage = nclLotcheckQueue;

    }

    @Test()
    @Order(12)
    public void assignTesterQueue_expect8_1(IJUnitTestReporter testReport) {
        LotcheckQueuePage nclLotcheckQueue = browser.getPage(LotcheckQueuePage.class, testReport);
        currentPage = nclLotcheckQueue;

        assertTrue(verifyLCOAssignTesterQueue, "Verify display LCO");
    }

    @Test()
    @Order(13)
    public void testSetupReady_step9(IJUnitTestReporter testReport) {
        LotcheckQueuePage nclLotcheckQueue = browser.getPage(LotcheckQueuePage.class, testReport);
        LotcheckLandingPage lotcheckLandingPage = nclLotcheckQueue.navLandingPage();
        nclLotcheckQueue = lotcheckLandingPage.clickTesterAssignment(LotcheckRegion.NCL);
        LotcheckTestPlanRow lotcheckTestPlanRow = nclLotcheckQueue.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        lotcheckTestPlanRow.selectAssignmentComplete().clickConfirm();
        nclLotcheckQueue.refreshPageSLCMS();
        nclLotcheckQueue.selectQueue(TextConstants.testSetReady, LotcheckRegion.NCL);
        lotcheckTestPlanRow = nclLotcheckQueue.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        verifyLCOTestSetupReady = lotcheckTestPlanRow.iconLCOIsDisplay();
        nclLotcheckQueue.refreshPageSLCMS();
        nclLotcheckQueue.selectMoreFilter(TextConstants.lotcheckOwner).clickLotcheckOwnerCondition();
        HashMap<String, String> regionTestSetupReady = new LinkedHashMap<String, String>();

        listTestSetupReady = addTestPlanLotCheckOwnerForRegion(nclLotcheckQueue, regionTestSetupReady, false);
        currentPage = nclLotcheckQueue;
    }

    @Test()
    @Order(14)
    public void testSetupReady_expect9(IJUnitTestReporter testReport) {
        LotcheckQueuePage nclLotcheckQueue = browser.getPage(LotcheckQueuePage.class, testReport);
        currentPage = nclLotcheckQueue;
        assertTrue(verifyLCOTestSetupReady, " Verify LCO");
    }


    @Test()
    @Order(15)
    public void inTestSetup_step10(IJUnitTestReporter testReport) {
        LotcheckQueuePage nclLotcheckQueue = browser.getPage(LotcheckQueuePage.class, testReport);
        LotcheckLandingPage lotcheckLandingPage = nclLotcheckQueue.navLandingPage();
        nclLotcheckQueue = lotcheckLandingPage.clickTestSetupReady(LotcheckRegion.NCL);
        LotcheckTestPlanRow lotcheckTestPlanRow = nclLotcheckQueue.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        lotcheckTestPlanRow.selectStartTestSetupDev().clickConfirm();
        nclLotcheckQueue.refreshPageSLCMS();
        nclLotcheckQueue.selectQueue(TextConstants.inTestSetup, LotcheckRegion.NCL);
        lotcheckTestPlanRow = nclLotcheckQueue.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        verifyLCOInTestSetup = lotcheckTestPlanRow.iconLCOIsDisplay();
        nclLotcheckQueue.refreshPageSLCMS();
        nclLotcheckQueue.selectMoreFilter(TextConstants.lotcheckOwner).clickLotcheckOwnerCondition();
        HashMap<String, String> regionInTestSetup = new LinkedHashMap<String, String>();
        listAssignTester = addTestPlanLotCheckOwnerForRegion(nclLotcheckQueue, regionInTestSetup, false);
        currentPage = nclLotcheckQueue;
    }

    @Test()
    @Order(16)
    public void inTestSetup_expect10(IJUnitTestReporter testReport) {
        LotcheckQueuePage nclLotcheckQueue = browser.getPage(LotcheckQueuePage.class, testReport);
        currentPage = nclLotcheckQueue;
        assertTrue(verifyLCOInTestSetup, "Verify LCO");
    }

    @Test()
    @Order(17)
    public void inTesting_step11(IJUnitTestReporter testReport) {
        LotcheckQueuePage nclLotcheckQueue = browser.getPage(LotcheckQueuePage.class, testReport);
        LotcheckLandingPage lotcheckLandingPage = nclLotcheckQueue.navLandingPage();
        nclLotcheckQueue = lotcheckLandingPage.clickInTestSetup(LotcheckRegion.NCL);
        LotcheckTestPlanRow lotcheckTestPlanRow = nclLotcheckQueue.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        lotcheckTestPlanRow.selectSetupCompleteDev().clickConfirm();
        nclLotcheckQueue.refreshPageSLCMS();
        nclLotcheckQueue.selectQueue(TextConstants.inTesting, LotcheckRegion.NCL);
        lotcheckTestPlanRow = nclLotcheckQueue.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        verifyLCOInTesting = lotcheckTestPlanRow.iconLCOIsDisplay();
        nclLotcheckQueue.selectMoreFilter(TextConstants.lotcheckOwner).clickLotcheckOwnerCondition();
        HashMap<String, String> regionInTesting = new HashMap<>();
        listInTesting = addTestPlanLotCheckOwnerForRegion(nclLotcheckQueue, regionInTesting, false);
        currentPage = nclLotcheckQueue;
    }

    @Test()
    @Order(18)
    public void inTesting_expect11(IJUnitTestReporter testReport) {
        LotcheckQueuePage nclLotcheckQueue = browser.getPage(LotcheckQueuePage.class, testReport);
        currentPage = nclLotcheckQueue;
        assertTrue(verifyLCOInTesting, "Verify LCO");
    }

    @Test()
    @Order(19)
    public void judgmentApproval_step12(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN, testReport);
        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailDashboardPage testRailDashboardPage = testRailLoginPage.signInTestRail(TextConstants.trAdminNCL_EN, TextConstants.trAdmin_Password);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        CommonAction.setStatusOnTestrail(testRailManagerDashboardPage, initialCode, TextConstants.passedStatus, TextConstants.percentCompleted, "Suspended");
        WaitUtils.idle(5000);
        testRailDashboardPage.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton();

        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        LotcheckQueuePage nclLotcheckQueue = lotcheckLandingPage.clickJudgment(LotcheckRegion.NCL);

        CommonAction.findTestPlanInQueue(30, LotcheckRegion.NCL.toString(), initialCode, "1", nclLotcheckQueue);
        nclLotcheckQueue.clickPlannedTestDateNotSet();
        verifyLCOJudgementApproval = nclLotcheckQueue.iconLCOIsDisplay();
        nclLotcheckQueue.refreshPageSLCMS();
        nclLotcheckQueue.selectMoreFilter(TextConstants.lotcheckOwner).clickLotcheckOwnerCondition();
        HashMap<String, String> regionJudgmentApproval = new HashMap<>();

        listJudgementApproval = addTestPlanLotCheckOwnerForRegion(nclLotcheckQueue, regionJudgmentApproval, false);
        currentPage = nclLotcheckQueue;

    }

    @Test()
    @Order(20)
    public void judgmentApproval_expect12(IJUnitTestReporter testReport) {
        LotcheckQueuePage nclLotcheckQueue = browser.getPage(LotcheckQueuePage.class, testReport);
        currentPage = nclLotcheckQueue;
        assertTrue(verifyLCOJudgementApproval, "Verify LCO");
    }

    @Test()
    @Order(21)
    public void pendingSolution_step13(IJUnitTestReporter testReport) {
        LotcheckQueuePage nclLotcheckQueue = browser.getPage(LotcheckQueuePage.class, testReport);
        LotcheckLandingPage lotcheckLandingPage = nclLotcheckQueue.navLandingPage();
        nclLotcheckQueue = lotcheckLandingPage.clickJudgment(LotcheckRegion.NCL);
        LotcheckTestPlanRow lotcheckTestPlanRow = nclLotcheckQueue.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        lotcheckTestPlanRow.selectRejectJudgment().clickConfirm();

        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN, testReport);
        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailDashboardPage testRailDashboardPage = testRailLoginPage.signInTestRail(TextConstants.trAdminNCL_EN, TextConstants.trAdmin_Password);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(initialCode)
                .selectStatusUntested()
                .selectStatusPassed().selectStatusFailed();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .expandAllTestRuns()
                .clickOnTheFirstCheckboxTestcase()
                .clickOnSetStatusButton().selectStatusOnSetStatusModal(TextConstants.reviewingStatus)
                .clickOnUpdateStatusButtonOnSetStatusModal()
                .waitingLoadingTestSuite();
        WaitUtils.idle(50000);
        testRailDashboardPage.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton();

        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        nclLotcheckQueue = lotcheckLandingPage.clickPendingResolution(LotcheckRegion.NCL);
        CommonAction.findTestPlanInQueue(30, LotcheckRegion.NCL.toString(), initialCode, "1", nclLotcheckQueue);
        nclLotcheckQueue.clickPlannedTestDateNotSet();
        verifyLCOPendingSolution = lotcheckTestPlanRow.iconLCOIsDisplay();
        nclLotcheckQueue.refreshPageSLCMS();
        nclLotcheckQueue.selectMoreFilter(TextConstants.lotcheckOwner).clickLotcheckOwnerCondition();
        HashMap<String, String> regionPendingSolution = new HashMap<>();


        listPendingSolution = addTestPlanLotCheckOwnerForRegion(nclLotcheckQueue, regionPendingSolution, false);
        currentPage = nclLotcheckQueue;

    }

    @Test()
    @Order(22)
    public void pendingSolution_expect13(IJUnitTestReporter testReport) {
        LotcheckQueuePage nclLotcheckQueue = browser.getPage(LotcheckQueuePage.class, testReport);
        currentPage = nclLotcheckQueue;
        assertTrue(verifyLCOPendingSolution, "Verify LCO");
    }

    @Test()
    @Order(23)
    public void finish_step14(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN, testReport);
        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailDashboardPage testRailDashboardPage = testRailLoginPage.signInTestRail(TextConstants.trAdminNCL_EN, TextConstants.trAdmin_Password);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(initialCode).selectStatusReviewing().selectStatusPassed();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .expandAllTestRuns()
                .clickOnTheFirstCheckboxTestcase()
                .clickOnSetStatusButton().selectStatusOnSetStatusModal("Passed")
                .clickOnUpdateStatusButtonOnSetStatusModal()
                .waitingLoadingTestSuite();
        testRailManagerDashboardPage.clickOnPadlockButtonOfTheFirstTestPlan()
                .selectTestPlanJudgment("Suspended")
                .clickOnYesButtonOnConfirmationModal();
        WaitUtils.idle(5000);
        testRailDashboardPage.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton();

        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        LotcheckQueuePage nclLotcheckQueue = lotcheckLandingPage.clickFinish(LotcheckRegion.NCL);
        int index = 0;
        while (index < 20) {
            nclLotcheckQueue.refreshPageSLCMS();
            nclLotcheckQueue.searchByInitialCode(initialCode);
            String numberValue = nclLotcheckQueue.numberTestPlanDisplayInFinishQueue();
            index++;
            if (numberValue.equalsIgnoreCase("1")) {
                break;
            }
        }
        verifyLCOFinished = nclLotcheckQueue.iconLCOIsDisplay();

        nclLotcheckQueue.refreshPageSLCMS();
        nclLotcheckQueue.selectMoreFilter("Lotcheck Owner").clickLotcheckOwnerCondition();
        HashMap<String, String> regionFinish = new HashMap<>();

        for (int i = 0; i < regions.size(); i++) {
            String[] strings = regions.get(i);
            for (int k = 0; k < strings.length; k++) {
                System.out.println(strings[k]);
                nclLotcheckQueue.selectConditionRegion(strings[k]);
            }
            if (!nclLotcheckQueue.numberTestPlanDisplayInFinishQueue().equalsIgnoreCase("0")) {
                regionFinish = nclLotcheckQueue.getTestPlan();//hashmap: key: testplanName, value: lotcheckOwner
            }

            listFinished.add(regionFinish);
            nclLotcheckQueue.unselectMoreFilter("Lotcheck Owner");
            nclLotcheckQueue.selectMoreFilter("Lotcheck Owner").clickLotcheckOwnerCondition();
        }
        currentPage = nclLotcheckQueue;

    }

    @Test()
    @Order(24)
    public void finish_expect14(IJUnitTestReporter testReport) {
        LotcheckQueuePage nclLotcheckQueue = browser.getPage(LotcheckQueuePage.class, testReport);
        currentPage = nclLotcheckQueue;
        nclLotcheckQueue.logOut();
        assertTrue(verifyLCOFinished, "Verify LCO");
    }
    @Test()
    @Order(25)
    public void CompleteProductPerQueueNOA_step15(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReport);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(TextConstants.lc_Admin_NOA, TextConstants.lc_Admin_NOA_Password);


        LotcheckQueuePage noaLotcheckQueue = lotcheckLandingPage.clickOverallLotcheck(LotcheckRegion.NOA);
        noaLotcheckQueue.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        noaOverallQueue = noaLotcheckQueue.iconLCOIsDisplay();

        lotcheckLandingPage = noaLotcheckQueue.navLandingPage();
        noaLotcheckQueue = lotcheckLandingPage.clickTesterAssignment(LotcheckRegion.NOA);
        LotcheckTestPlanRow lotcheckTestPlanRow = noaLotcheckQueue.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        noaTesterAssignmentQueue = noaLotcheckQueue.iconLCOIsDisplay();
        lotcheckTestPlanRow.performAction("Assignment Complete");

        lotcheckLandingPage = noaLotcheckQueue.navLandingPage();
        noaLotcheckQueue = lotcheckLandingPage.clickTestSetupReady(LotcheckRegion.NOA);
        lotcheckTestPlanRow = noaLotcheckQueue.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        noaTestSetupReady = noaLotcheckQueue.iconLCOIsDisplay();
        lotcheckTestPlanRow.performAction("Start Test Setup (DEV)");

        lotcheckLandingPage = noaLotcheckQueue.navLandingPage();
        noaLotcheckQueue = lotcheckLandingPage.clickInTestSetup(LotcheckRegion.NOA);
        lotcheckTestPlanRow = noaLotcheckQueue.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        noaInTestSetup = noaLotcheckQueue.iconLCOIsDisplay();
        lotcheckTestPlanRow.performAction("Setup Complete (DEV)");

        lotcheckLandingPage = noaLotcheckQueue.navLandingPage();
        noaLotcheckQueue = lotcheckLandingPage.clickInTesting(LotcheckRegion.NOA);
        noaLotcheckQueue.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        noaInTesting = noaLotcheckQueue.iconLCOIsDisplay();

        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN, testReport);
        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        TestRailDashboardPage testRailDashboardPage = testRailLoginPage.signInTestRail("tradmin@test.noa.nintendo.com", "password");
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        CommonAction.setStatusOnTestrail(testRailManagerDashboardPage, initialCode, TextConstants.passedStatus, TextConstants.percentCompleted, "Suspended");
        WaitUtils.idle(5000);

        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        lotcheckLandingPage.clickJudgment(LotcheckRegion.NOA);
        CommonAction.findTestPlanInQueue(30, LotcheckRegion.NOA.toString(), initialCode, "1", noaLotcheckQueue);
        noaLotcheckQueue.clickPlannedTestDateNotSet();
        noaJudgment = noaLotcheckQueue.iconLCOIsDisplay();
        lotcheckTestPlanRow.performAction("Reject Judgment");

        currentPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport);
        testRailDashboardPage = (TestRailDashboardPage) currentPage;
        testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(initialCode)
                .selectStatusUntested()
                .selectStatusPassed().selectStatusFailed();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .expandAllTestRuns()
                .clickOnTheFirstCheckboxTestcase()
                .clickOnSetStatusButton().selectStatusOnSetStatusModal("Reviewing")
                .clickOnUpdateStatusButtonOnSetStatusModal()
                .waitingLoadingTestSuite();
        WaitUtils.idle(50000);

        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        noaLotcheckQueue = lotcheckLandingPage.clickPendingResolution(LotcheckRegion.NOA);
        CommonAction.findTestPlanInQueue(30, LotcheckRegion.NOA.toString(), initialCode, "1", noaLotcheckQueue);
        noaLotcheckQueue.clickPlannedTestDateNotSet();
        noaPendingSolution = noaLotcheckQueue.iconLCOIsDisplay();

        currentPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport);
        testRailDashboardPage = (TestRailDashboardPage) currentPage;
        testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(initialCode).selectStatusReviewing().selectStatusPassed();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .expandAllTestRuns()
                .clickOnTheFirstCheckboxTestcase()
                .clickOnSetStatusButton().selectStatusOnSetStatusModal("Passed")
                .clickOnUpdateStatusButtonOnSetStatusModal()
                .waitingLoadingTestSuite();
        testRailManagerDashboardPage.clickOnPadlockButtonOfTheFirstTestPlan()
                .selectTestPlanJudgment("Suspended")
                .clickOnYesButtonOnConfirmationModal();
        WaitUtils.idle(5000);
        testRailDashboardPage.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton();
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        noaLotcheckQueue = lotcheckLandingPage.clickFinish(LotcheckRegion.NOA);
        int i = 0;
        while (i < 30) {
            noaLotcheckQueue.refreshPageSLCMS();
            noaLotcheckQueue.searchByInitialCode(initialCode);
            String numberValue = noaLotcheckQueue.numberTestPlanDisplayInFinishQueue();
            i++;
            if (numberValue.equalsIgnoreCase("1")) {
                break;
            }
        }
        noaFinish = noaLotcheckQueue.iconLCOIsDisplay();
        currentPage = noaLotcheckQueue.clickOnUserNameMenu().clickOnLogoutButton();

    }

    @Test()
    @Order(26)
    public void VerifyLCOInNOA_expect15(IJUnitTestReporter testReport) {
        assertAll(() -> assertFalse(noaOverallQueue, "NOA Queue Overall: Verify The character string @LCO is added to the end of the TestPlan game code"),
                () -> assertFalse(noaTesterAssignmentQueue, "NOA Queue TesterAssignment: Verify The character string @LCO is added to the end of the TestPlan game code"),
                () -> assertFalse(noaTestSetupReady, "NOA Queue TestSetupReady: Verify The character string @LCO is added to the end of the TestPlan game code"),
                () -> assertFalse(noaInTestSetup, "NOA Queue InTestSetup: Verify The character string @LCO is added to the end of the TestPlan game code"),
                () -> assertFalse(noaInTesting, "NOA Queue InTesting: Verify The character string @LCO is added to the end of the TestPlan game code"),
                () -> assertFalse(noaJudgment, "NOA Queue Judgment : Verify The character string @LCO is added to the end of the TestPlan game code"),
                () -> assertFalse(noaPendingSolution, "NOA Queue Judgment : Verify The character string @LCO is added to the end of the TestPlan game code"),
                () -> assertFalse(noaFinish, "NOA Queue Finish : Verify The character string @LCO is added to the end of the TestPlan game code")
        );
    }

    @Test()
    @Order(27)
    public void CompleteProductPerQueueNOE_step16(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReport);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink("lc_admin_noe", "FurnitureWorldNextScorn0");

        LotcheckQueuePage noeLotcheckQueue = lotcheckLandingPage.clickOverallLotcheck(LotcheckRegion.NOE);
        noeLotcheckQueue.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        noeOverallQueue = noeLotcheckQueue.iconLCOIsDisplay();

        lotcheckLandingPage = noeLotcheckQueue.navLandingPage();
        noeLotcheckQueue = lotcheckLandingPage.clickTesterAssignment(LotcheckRegion.NOE);
        LotcheckTestPlanRow lotcheckTestPlanRow = noeLotcheckQueue.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        noeTesterAssignmentQueue = noeLotcheckQueue.iconLCOIsDisplay();
        lotcheckTestPlanRow.performAction("Assignment Complete");

        lotcheckLandingPage = noeLotcheckQueue.navLandingPage();
        noeLotcheckQueue = lotcheckLandingPage.clickTestSetupReady(LotcheckRegion.NOE);
        lotcheckTestPlanRow = noeLotcheckQueue.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        noeTestSetupReady = noeLotcheckQueue.iconLCOIsDisplay();
        lotcheckTestPlanRow.performAction("Start Test Setup (DEV)");

        lotcheckLandingPage = noeLotcheckQueue.navLandingPage();
        noeLotcheckQueue = lotcheckLandingPage.clickInTestSetup(LotcheckRegion.NOE);
        lotcheckTestPlanRow = noeLotcheckQueue.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        noeInTestSetup = noeLotcheckQueue.iconLCOIsDisplay();
        lotcheckTestPlanRow.performAction("Setup Complete (DEV)");

        lotcheckLandingPage = noeLotcheckQueue.navLandingPage();
        noeLotcheckQueue = lotcheckLandingPage.clickInTesting(LotcheckRegion.NOE);
        noeLotcheckQueue.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        noeInTesting = noeLotcheckQueue.iconLCOIsDisplay();

        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN, testReport);
        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        TestRailDashboardPage testRailDashboardPage = testRailLoginPage.signInTestRail("tradmin@test.noe.nintendo.com", "password");
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        CommonAction.setStatusOnTestrail(testRailManagerDashboardPage, initialCode, TextConstants.passedStatus, TextConstants.percentCompleted, "Suspended");
        WaitUtils.idle(5000);

        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        noeLotcheckQueue = lotcheckLandingPage.clickJudgment(LotcheckRegion.NOE);
        CommonAction.findTestPlanInQueue(30, LotcheckRegion.NOE.toString(), initialCode, "1", noeLotcheckQueue);
        noeLotcheckQueue.clickPlannedTestDateNotSet();
        noeJudgment = noeLotcheckQueue.iconLCOIsDisplay();
        lotcheckTestPlanRow.performAction("Reject Judgment");

        currentPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport);
        testRailDashboardPage = (TestRailDashboardPage) currentPage;
        testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(initialCode)
                .selectStatusUntested()
                .selectStatusPassed().selectStatusFailed();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .expandAllTestRuns()
                .clickOnTheFirstCheckboxTestcase()
                .clickOnSetStatusButton().selectStatusOnSetStatusModal("Reviewing")
                .clickOnUpdateStatusButtonOnSetStatusModal()
                .waitingLoadingTestSuite();

        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        noeLotcheckQueue = lotcheckLandingPage.clickPendingResolution(LotcheckRegion.NOE);
        CommonAction.findTestPlanInQueue(30, LotcheckRegion.NOA.toString(), initialCode, "1", noeLotcheckQueue);
        noeLotcheckQueue.clickPlannedTestDateNotSet();
        noePendingSolution = noeLotcheckQueue.iconLCOIsDisplay();


        currentPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport);
        testRailDashboardPage = (TestRailDashboardPage) currentPage;
        testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(initialCode).selectStatusReviewing().selectStatusPassed();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .expandAllTestRuns()
                .clickOnTheFirstCheckboxTestcase()
                .clickOnSetStatusButton().selectStatusOnSetStatusModal("Passed")
                .clickOnUpdateStatusButtonOnSetStatusModal()
                .waitingLoadingTestSuite();
        testRailManagerDashboardPage.clickOnPadlockButtonOfTheFirstTestPlan()
                .selectTestPlanJudgment("Suspended")
                .clickOnYesButtonOnConfirmationModal();
        WaitUtils.idle(5000);
        testRailDashboardPage.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton();

        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        noeLotcheckQueue = lotcheckLandingPage.clickFinish(LotcheckRegion.NOE);
        int i = 0;
        while (i < 30) {
            noeLotcheckQueue.refreshPageSLCMS();
            noeLotcheckQueue.searchByInitialCode(initialCode);
            String numberValue = noeLotcheckQueue.numberTestPlanDisplayInFinishQueue();
            i++;
            if (numberValue.equalsIgnoreCase("1")) {
                break;
            }
        }
        noeFinish = noeLotcheckQueue.iconLCOIsDisplay();
        currentPage = noeLotcheckQueue.navLandingPage().clickOnUserNameMenu().clickOnSignoutButton();

    }


    @Test()
    @Order(28)
    public void verifyLCOInNOE_expect16(IJUnitTestReporter testReport) {
        assertAll(() -> assertFalse(noeOverallQueue, "NOA Queue Overall: Verify The character string @LCO is added to the end of the TestPlan game code"),
                () -> assertFalse(noeTesterAssignmentQueue, "NOA Queue TesterAssignment: Verify The character string @LCO is added to the end of the TestPlan game code"),
                () -> assertFalse(noeTestSetupReady, "NOA Queue TestSetupReady: Verify The character string @LCO is added to the end of the TestPlan game code"),
                () -> assertFalse(noeInTestSetup, "NOA Queue InTestSetup: Verify The character string @LCO is added to the end of the TestPlan game code"),
                () -> assertFalse(noeInTesting, "NOA Queue InTesting: Verify The character string @LCO is added to the end of the TestPlan game code"),
                () -> assertFalse(noeJudgment, "NOA Queue Judgment : Verify The character string @LCO is added to the end of the TestPlan game code"),
                () -> assertFalse(noePendingSolution, "NOA Queue Judgment : Verify The character string @LCO is added to the end of the TestPlan game code"),
                () -> assertFalse(noeFinish, "NOA Queue Finish : Verify The character string @LCO is added to the end of the TestPlan game code")
        );
    }

    @Test()
    @Order(29)
    public void openSubmissionReportNCL_step17(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReport);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
        LotcheckReportSubmissionOverview submissionOverview = lotcheckLandingPage.searchProduct(productName).selectSubmission(initialCode, "01", "00");
        currentPage = submissionOverview;
    }

    @Test()
    @Order(30)
    public void verifySubmissionReportNCL_expect17(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionOverview submissionOverview = (LotcheckReportSubmissionOverview) currentPage;
        LotcheckReportMatrix reportMatrix = submissionOverview.matrix();
        boolean isDisplayLCONCL = reportMatrix.displayLCONCL();
        String backColorSubmissionMatrixIsWhite = reportMatrix.backColorMatrixIsWhite();
        submissionOverview = reportMatrix.clickOverviewTabSubmissionReport();
        String lotcheckOwner = submissionOverview.getLotcheckOwnerValue();
        String lotcheckOwnerOriginal = submissionOverview.getLotcheckOwnerOriginal();
        String homeRegionProduct = submissionOverview.getHomeRegionProduct();
        String homeRegionOrganization = submissionOverview.getHomeRegionOrganization();
        currentPage = submissionOverview;
        assertAll(() -> assertTrue(isDisplayLCONCL, "Verify LC Submission Matrix: @LCO is added after the region information of NCL region"),
                () -> assertEquals(whiteColor, backColorSubmissionMatrixIsWhite, "Verify LC Submission Matrix: background color is white"),
                () -> assertEquals(TextConstants.NCL, lotcheckOwnerOriginal, "Verify Lotcheck Owner (Original): NCL"),
                () -> assertEquals("(NCL)", lotcheckOwner, "Verify Lotcheck Owner: (NCL)"),
                () -> assertEquals(TextConstants.NCL, homeRegionProduct, "Verify Home Region (Product): NCL"),
                () -> assertEquals(TextConstants.NCL, homeRegionOrganization, "Verify Home Region (Organization): NCL")
        );
    }

    @Test()
    @Order(31)
    public void navigateNumberReportNCL_step18(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionOverview submissionOverview = (LotcheckReportSubmissionOverview) currentPage;
        LotcheckReportMatrix reportMatrix = submissionOverview.matrix();
        LotcheckReportNumberOverview reportNumberOverview = reportMatrix.clickTestPlanLink();
        reportMatrix = reportNumberOverview.matrix();
        currentPage = reportMatrix;
    }

    @Test()
    @Order(32)
    public void verifyNumberReportNCL_expect18(IJUnitTestReporter testReport) {
        LotcheckReportMatrix reportMatrix = (LotcheckReportMatrix) currentPage;
        assertAll(() -> assertTrue(reportMatrix.displayLCONCL(), "Verify LC Number Matrix: @LCO is added after the region information of NCL region"),
                () -> assertEquals(whiteColor, reportMatrix.backColorMatrixIsWhite(), "Verify LC Number Matrix: background color is white")
        );
    }

    @Test()
    @Order(33)
    public void navigateTestPlanReportNCL_step19(IJUnitTestReporter testReport) {
        LotcheckReportMatrix reportMatrix = (LotcheckReportMatrix) currentPage;
        LotcheckReportTestPlanOverview reportTestPlanOverview = reportMatrix.clickStatusTestPlanNCL();
        currentPage = reportTestPlanOverview;
    }

    @Test()
    @Order(34)
    public void verifyTestPlanReportNCL_expect19(IJUnitTestReporter testReport) {
        LotcheckReportTestPlanOverview reportTestPlanOverview = (LotcheckReportTestPlanOverview) currentPage;
        LotcheckReportMatrix reportMatrix = reportTestPlanOverview.matrix();
        currentPage = reportMatrix;
        assertAll(() -> assertTrue(reportMatrix.displayLCONCL(), "Verify LC TestPlan Report Matrix: @LCO is added after the region information of NCL region"),
                () -> assertEquals(whiteColor, reportMatrix.backColorMatrixIsWhite(), "Verify LC TestPlan Report Matrix: background color is white")
        );
    }


    @Test()
    @Order(35)
    public void navigateProductReportNCL_step20(IJUnitTestReporter testReport) {
        LotcheckReportMatrix reportMatrix = (LotcheckReportMatrix) currentPage;
        LotcheckReportProductProductInformation reportProductProductInformation = reportMatrix.clickGoToProductLevel();
        currentPage = reportProductProductInformation;
    }

    @Test()
    @Order(36)
    public void verifyProductReportNCL_expect20(IJUnitTestReporter testReport) {
        LotcheckReportProductProductInformation reportProductProductInformation = (LotcheckReportProductProductInformation) currentPage;
        LotcheckReportMatrix reportMatrix = reportProductProductInformation.matrix();
        currentPage = reportMatrix;
        assertAll(() -> assertTrue(reportMatrix.displayLCONCL(), "Verify LC Product Level History Report Matrix: @LCO is added after the region information of NCL region"),
                () -> assertEquals(whiteColor, reportMatrix.backColorMatrixIsWhite(), "Verify LC Product Level History Report Report Matrix: background color is white")
        );
    }
}
