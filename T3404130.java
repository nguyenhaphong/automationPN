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
import net.nintendo.automation.ui.models.d4c.D4CRomPublishSuit;
import net.nintendo.automation.ui.models.d4c.D4CRomTab;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthPage;
import net.nintendo.automation.ui.models.lcms.LotcheckHomePage;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.ui.models.lcms.LotcheckQueuePage;
import net.nintendo.automation.ui.models.lcms.LotcheckTestPlanRow;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionProductInformation;
import net.nintendo.automation.ui.models.ndp.NDPCreateProductPage;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.NDPSignInPage;
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
import net.nintendo.automation.ui.models.omas.OmasNDIDAuthorization;
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
import net.nintendo.automation_core.common_utils.annotations.services.NDP;
import net.nintendo.automation_core.common_utils.annotations.services.SLCMS;
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

import java.io.File;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classname: T3404130
 * Version: 1.0.0
 * Purpose:
 */
@Tag("T3404130")
@NDP
@SLCMS
@ScenarioTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class T3404130 extends CommonBaseTest {
    private static net.nintendo.automation.browser.Browser browser;
    private static User ndpUser;
    private static User slcmsUser;
    private static Browser romBrowser;
    private static User romUser;
    private static User licUser;
    private static User testRailUser;

    private static String productName;
    private static String applicationID;
    private static String initialCode;
    private static String gameCode;
    private static String fileRomName;

    private static CommonUIComponent currentPage;
    private static TestLogger logger;

    @AfterAll
    static void terminateBrowser() {
        UserManager.releaseUser(ServiceType.NDP, ndpUser);
        UserManager.releaseUser(ServiceType.SLCMS, slcmsUser);
        UserManager.releaseUser(ServiceType.SEARCH, romUser);
        UserManager.releaseUser(ServiceType.NDP, licUser);
        UserManager.releaseUser(ServiceType.PMMS, testRailUser);
    }

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_Trang);
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
        NDPHomePage ndpHomePage = (NDPHomePage) currentPage;
        ndpHomePage.acceptCookie();
        NDPSignInPage signInPage = ndpHomePage.clickSignInPage();
        signInPage.signIn(ndpUser);
        productName = DataGen.getRandomProductName();
        slcmsUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);
        testRailUser = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER);
        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER);
    }

    @BeforeEach
    public void each(IJUnitTestReporter testReport) {
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
    }

    @Test()
    @Order(1)
    public void createProduct(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        NDPCreateProductPage createProductPage = myProductsPage.createNewProduct();
        createProductPage.selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(TextConstants.first_Party_Type)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalJapanSalesRegion()
                .selectProductName(productName)
                .selectProductNameKana(TextConstants.productKana)
                .clickCreateButton();
        NDPProductDashboardPage ndpDashboardPage = myProductsPage.clickProductByName(productName);
        CommonAction.issueGameCode(ndpDashboardPage, 60,"NO");
    }

    @Test
    @Order(2)
    public void createRomUpload(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();
        applicationID = ndpProductDashboardProductInfoTab.getApplicationID();
        gameCode = ndpProductDashboardProductInfoTab.getGameCode();
        initialCode = ndpProductDashboardProductInfoTab.getInitialCode();
        romBrowser = BrowserManager.getNewInstance();
        currentPage = romBrowser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ROMLoginPage romLoginPage = (ROMLoginPage) currentPage;
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
        fileRomName = romCreateRom.getRomNameTable(applicationID);
        romCreateRom.clickOnROMFileOnROMTable(applicationID);
        WaitUtils.idle(20000);
        romCreateRom.clickOnUserNameMenu().clickOnLogoutButton();
        BrowserManager.closeBrowser(romBrowser);
    }

    @Test()
    @Order(3)
    public void fillOurDashboard(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();
        applicationID = ndpProductDashboardProductInfoTab.getApplicationID();
        gameCode = ndpProductDashboardProductInfoTab.getGameCode();
        initialCode = ndpProductDashboardProductInfoTab.getInitialCode();
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        //create release information
        NDPReleaseInfoPage releaseInfoPage = ndpReleaseInfoPage.nav().clickReleaseInfo();
        CommonAction.selectROMSameAllRegion(releaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);

        //create Feature
        NDPReleaseInfoFeaturesPage releaseInfoFeaturesPage = releaseInfoPage.nav().clickFeatures();
        CommonAction.getFeatureOptionData(releaseInfoPage, TextConstants.allOff, TextConstants.allOff, TextConstants.allOff, false, "", false, "");
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = releaseInfoFeaturesPage.clickContinue();

        //Create GuildLine
        ndpReleaseInfoGuidelinesPage.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();
        //Create Age Rating
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = ndpReleaseInfoPage.nav().clickAgeRating();
        ndpReleaseInfoAgeRating.clickOnAddStandardRatingButton()
                .selectStandardAgeRating("CERO")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectRatingValue("A")
                .uploadCertificateAttachment(new File("src/test/resources/DataFile/ab.jpg"))
                .clickOnSaveButtonInEditStandardRating();
        //upload ROM
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = ndpReleaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
        //Request Issue
        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
        issuePage.openRequiredApprovalTab();
        issuePage.performAllIssueAtApprovalTab(false);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
        //Approval Issue TaskRequested
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(licUser).clickOnMyTaskMenu();
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        CommonAction.approveInternalWaiverRequest(developmentHome, internalTasksPage, gameCode);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
    }

    @Test
    @Order(4)
    public void submitLotCheck(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser).clickMyProducts();
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.commentSubmission).clickSubmit();
        WaitUtils.idle(3000);
    }

    @Test()
    @Order(5)
    public void performActionQueueInSLCMS(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.signIn(TextConstants.noa_VanN_Email, TextConstants.noa_VanN_Password);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickTesterAssignment(LotcheckRegion.NCL);
        CommonAction.findProductInLCQueue(lotcheckLandingPage, 50, LotcheckRegion.NCL.toString(), initialCode);
        LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
        lotcheckTestPlanRow.selectAssignmentComplete().clickConfirm();
        //lotcheckTestPlanRow.startAlert();
        lotcheckLandingPage.navigationToTestSetupReadyQueue(LotcheckRegion.NCL.toString());
        lotcheckQueuePage.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        lotcheckTestPlanRow.selectStartTestSetupProd().clickConfirm();
        //lotcheckTestPlanRow.startAlert();
        lotcheckLandingPage.navigationToInTestSetupQueue(LotcheckRegion.NCL.toString());
        lotcheckQueuePage.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        lotcheckTestPlanRow.selectSetupCompleteProd().clickConfirm();
        //lotcheckTestPlanRow.startAlert();
        lotcheckLandingPage.navigationToInTestingQueue(LotcheckRegion.NCL.toString());
        lotcheckQueuePage.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
    }

    @Test()
    @Order(6)
    public void setStatusTestPlanOnTestRail(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailDashboardPage testRailDashboardPage = testRailLoginPage.signInTestRail(testRailUser);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        CommonAction.setStatusOnTestrail(testRailManagerDashboardPage, initialCode, "Passed", TextConstants.percentCompleted, TextConstants.OK);
    }

    @Test()
    @Order(7)
    public void approvalJudgmentOK(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickJudgment(LotcheckRegion.NCL);
        CommonAction.approvalJudgmentStatus(lotcheckQueuePage, initialCode, 100, TextConstants.OK, false, "1");
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------
    @Test()
    @Order(8)
    public void checkSubmissionStatusInSLCMS(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToFinishedQueue(LotcheckRegion.NCL.toString());
        lotcheckQueuePage.searchByInitialCode(initialCode);
        String judgmentStatus = lotcheckQueuePage.getJudgmentStatus();
        LotcheckReportSubmissionOverview submissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        WaitUtils.idle(15000);
        HashMap<LotcheckReportSubmissionOverview.SubmissionOverviewAttribute, String> attributes = submissionOverview.getOverviewAttributes();
        String internalStatus = attributes.get(LotcheckReportSubmissionOverview.SubmissionOverviewAttribute.InternalStatus);
        assertAll(
                () -> assertEquals(TextConstants.OK, judgmentStatus),
                () -> assertEquals(TextConstants.OK, internalStatus)
        );
    }

    @Test()
    @Order(9)
    public void checkSubmissionStatusInNDP(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        String getStatusEN = ndpProductDashboardReleasesTab.getStatusRelease();
        myProductsPage.clickOnUserNameMenu().clickJapanButton();
        String getStatusJP = ndpProductDashboardReleasesTab.getStatusRelease();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        String internalStatusNDPJP = ndpReleaseInfoPage.getStatusJP();
        String messageJP = ndpReleaseInfoPage.nav().getMessageOnTheRight();
        myProductsPage.clickOnUserNameMenu().clickEnglishButton();
        String internalStatusNDPEN = ndpReleaseInfoPage.getStatus();
        String messageEN = ndpReleaseInfoPage.nav().getMessageOnTheRight();
        assertAll(
                () -> assertEquals(TextConstants.passedLotCheck, getStatusEN, TextConstants.statusENReleaseTab),
                () -> assertEquals(TextConstants.passedLotCheck_JP, getStatusJP, TextConstants.statusJPReleaseTab),
                () -> assertEquals(TextConstants.passedLotCheck, internalStatusNDPEN, TextConstants.statusENReleaseDashboard),
                () -> assertEquals(TextConstants.passedLotCheck_JP, internalStatusNDPJP, TextConstants.statusJPReleaseDashboard),
                () -> assertEquals(TextConstants.yourSubmissionPassed, messageEN, TextConstants.msgENReleaseTab),
                () -> assertEquals(TextConstants.yourSubmissionPassed_JP, messageJP, TextConstants.msgJPReleaseTab),
                () -> assertTrue(ndpReleaseInfoPage.nav().cancelSubmitButtonIsNotDisplay())
        );
    }

    @Test()
    @Order(10)
    public void checkStatusOfReleaseInOMAS(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(OmasNDIDAuthorization.class, PageState.POST_LOGIN);
        OmasNDIDAuthorization omasNDIDAuthorization = (OmasNDIDAuthorization) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        omasNDIDAuthorization.clickProductAndReleaseButton();
        omasNDIDAuthorization.inputProductSearch(initialCode).clickSearchButton();
        String statusOmas = omasNDIDAuthorization.getStatusProduct();
        assertEquals("In Service", statusOmas);

    }

    @Test()
    @Order(11)
    public void checkStatusD4C(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(D4CRomPublishSuit.class, PageState.PRE_LOGIN);
        D4CRomPublishSuit d4cRom = (D4CRomPublishSuit) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        String idApplication = applicationID.substring(2);
        String errorPage = d4cRom.getTitleConnect();
        if (errorPage.equals(TextConstants.yourConnectNotPrivate)) {
            d4cRom.clickAdvanceButton().clickProceedLink();
        }
        D4CRomTab d4cRomTab = d4cRom.clickTabRom();
        String statusRom = d4cRomTab.enterApplicationID(idApplication).clickSearchButton().getStatusROM();
        System.out.println(statusRom);
        assertEquals("APPROVED", statusRom);
    }

    //---------------------------------------------------------------------------
    @Test()
    @Order(12)
    public void createNewReleasePatch(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPCreateReleasePage ndpCreateReleasePage = ndpProductDashboardReleasesTab.clickCreateNewReleaseButton();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpCreateReleasePage
                .enterDisplayVersion("1.0.1")
                .selectExpectedSubmissionDate(5)
                .selectExpectedReleaseDate(20)
                .selectFreeToPlay("NO")
                .enterDescribeTheFeaturesPlanned(TextConstants.enterDescribeTheFeaturesPlanned)
                .enterDescribeTheBugsIssuesPlanned(TextConstants.enterDescribeTheBugsIssuesPlanned)
                .clickCreateButton();
    }

    @Test()
    @Order(13)
    public void createRomPatch(IJUnitTestReporter testReport) throws InterruptedException {
        NDPMyProductsPage myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();
        applicationID = ndpProductDashboardProductInfoTab.getApplicationID();
        gameCode = ndpProductDashboardProductInfoTab.getGameCode();
        productName = ndpProductDashboardProductInfoTab.getProductNameValue();
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
                .selectPublishingRelationShip(TextConstants.first_Party_Type)
                .enterDisplayVersion("1.0.1")
                .enterReleaseVersion("01")
                .enterOriginalApp(originalApp)
                .selectCERORating("CERO: B")
                .clickOnCreateButton()
                .waitIsLoaded();
        fileRomName = romCreateRom.getRomNameTable(applicationID);
        romCreateRom.clickOnROMFileOnROMTable(applicationID);
        WaitUtils.idle(20000);
        romCreateRom.clickOnUserNameMenu().clickOnLogoutButton();
        BrowserManager.closeBrowser(romBrowser);
    }

    @Test()
    @Order(14)
    public void fillOutNewReleaseProduct(IJUnitTestReporter testReport) throws Exception {
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
        //Edit Age Rating
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = ndpReleaseInfoPage.nav().clickAgeRating();
        ndpReleaseInfoAgeRating.selectViewAgeRating("CERO")
                .selectRatingValue("B")
                .uploadCertificateAttachment(new File("src/test/resources/DataFile/ab.jpg"))
                .clickOnSaveButtonInEditStandardRating();
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
        CommonAction.approveInternalWaiverRequest(developmentHome, internalTasksPage, gameCode);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
    }

    @Test
    @Order(15)
    public void submitLotCheckPatch01(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser).clickMyProducts();
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectPatchRelease();
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.enterDeveloperComment).clickSubmit();
        WaitUtils.idle(3000);
    }

    @Test
    @Order(16)
    public void verifyConfirmData(IJUnitTestReporter testReport) throws InterruptedException {
        //NDPMyProductsPage myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectPatchRelease();
        ndpReleaseInfoPage.nav().clickReleaseInfo();
        String expectedLotcheckSubmissionDateOnNDP = ndpReleaseInfoPage.getExpectedLotcheckSubmissionDate();
        String expectedReleaseDateOnNDP = ndpReleaseInfoPage.getExpectedReleaseDate();
        //convert date NDP
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
        //SLCMS
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        lotcheckLandingPage.clickOnUserNameMenu().clickMyAccount();
        String labelLanguage = lotcheckLandingPage.getLabelLanguage();
        lotcheckLandingPage.clickCancelButtonOnEditAccountPreferences();
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickTesterAssignment(LotcheckRegion.NCL);
        lotcheckQueuePage.clickPlannedTestDateNotSet();
        String timeZone = lotcheckQueuePage.getPlanTestDate().substring(17).trim();
        System.out.println(timeZone);
        if (!timeZone.equals("UTC")) {
            lotcheckLandingPage.clickOnUserNameMenu().clickMyAccount().selectTimeZone("(GMT+00:00) UTC").clickSaveButton();
        }
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
        LotcheckReportSubmissionOverview submissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        WaitUtils.idle(50000);
        String titleOverviewPage = submissionOverview.getTitlePage();
        HashMap<LotcheckReportSubmissionOverview.SubmissionOverviewAttribute, String> attributes = submissionOverview.getOverviewAttributes();
        String internalStatus = attributes.get(LotcheckReportSubmissionOverview.SubmissionOverviewAttribute.InternalStatus);
        String submittedBy = attributes.get(LotcheckReportSubmissionOverview.SubmissionOverviewAttribute.SubmittedBy);
        String developerComments = attributes.get(LotcheckReportSubmissionOverview.SubmissionOverviewAttribute.DeveloperComments_Translate);
        //date in Overview
        String plannedSubmissionDate = attributes.get(LotcheckReportSubmissionOverview.SubmissionOverviewAttribute.PlannedSubmissionDate);
        String plannedReleaseDate = attributes.get(LotcheckReportSubmissionOverview.SubmissionOverviewAttribute.PlannedReleaseDate);
        // value at ProductInfo
        LotcheckReportSubmissionProductInformation productInformation = submissionOverview.navBar().clickProductInformation();
        String titlePageProductInfo = productInformation.getTitleProductInfoPage().trim();
        //Age Rating
        productInformation.expandAgeRatings();
        productInformation.waitOpenAgeRatings();
        String ratingAgency = productInformation.getAgeRatingsRatingAgency(0);
        String rating = productInformation.getRating(0);
        String ratingMethod = productInformation.getRatingMethod(0);
        //Release Info
        String submissionType = productInformation.expandReleaseInformation().getSubmissionType();
        String targetDeliveryFormatDigital = productInformation.getTargetSalesRegionsDigital();
        String status = productInformation.getStatus();
        String displayVersion = productInformation.getDisplayVersion();
        String releaseVersion = productInformation.getReleaseVersion();
        String submissionVersion = productInformation.getSubmissionVersion();
        String releaseType = productInformation.getReleaseType();
        //convert date in ProductInfo
        String expectedSubmissionDate = productInformation.getExpectedSubmissionDate();
        String expectedReleaseDate = productInformation.getExpectedReleaseDate();
        String FreeToPlay = productInformation.getFreeToPlay();

        if (labelLanguage == "Language") {
            String useOfROMsThatDifferBetweenRegions = productInformation.getUseOfROMsThatDifferBetweenRegions(TextConstants.selectRomDifferBetweenRegion);
            String languageUsedForTextEntryInFeatures = productInformation.getLanguageUsedForTextEntryInFeatures(TextConstants.textEntryLanguage);
            assertAll(
                    () -> assertEquals(TextConstants.overviewText, titleOverviewPage, TextConstants.pageHeader),
                    () -> assertEquals(TextConstants.SUBMITTED, internalStatus, TextConstants.internalStatus),
                    () -> assertEquals("Tran_edit1 123234_edit1", submittedBy, TextConstants.submittedBy),
                    () -> assertEquals(TextConstants.enterDeveloperComment, developerComments, TextConstants.developerComments),
                    () -> assertEquals(expectedLotcheckSubmissionDateOnNDP, plannedSubmissionDate, TextConstants.expectedSubmissionDateOverview),
                    () -> assertEquals(expectedReleaseDateOnNDP, plannedReleaseDate, TextConstants.expectedReleaseDateOverview),
                    () -> assertEquals(TextConstants.productInformation, titlePageProductInfo, TextConstants.headerPageProductInfo),
                    () -> assertEquals("Lotcheck", submissionType, TextConstants.submissionType),
                    () -> assertEquals("Japan", targetDeliveryFormatDigital, TextConstants.targetDeliveryFormatDigital),
                    () -> assertEquals(TextConstants.submitToLotcheck, status, TextConstants.statusField),
                    () -> assertEquals("1.0.1", displayVersion, TextConstants.displayVersion),
                    () -> assertEquals("01", releaseVersion, TextConstants.releaseVersion),
                    () -> assertEquals("00", submissionVersion, TextConstants.submissionVersion),
                    () -> assertEquals("Patch", releaseType, TextConstants.releaseType),
                    () -> assertEquals(expectedLotcheckSubmissionDateOnNDP, expectedSubmissionDate, TextConstants.expectedSubmissionDateProductInfo),
                    () -> assertEquals(expectedReleaseDateOnNDP, expectedReleaseDate, TextConstants.expectedReleaseDateProductInfo),
                    () -> assertEquals("No", FreeToPlay, TextConstants.free2Play),
                    () -> assertEquals(TextConstants.selectRomDifferBetweenRegion, useOfROMsThatDifferBetweenRegions, TextConstants.compareRomDifferBetweenRegion),
                    () -> assertEquals("English", languageUsedForTextEntryInFeatures, TextConstants.languageUseEntryFeature),
                    () -> assertEquals(TextConstants.computerCERO, ratingAgency, TextConstants.ratingAgency),
                    () -> assertEquals("B", rating, TextConstants.rating),
                    () -> assertEquals("STANDARD", ratingMethod, TextConstants.ratingMethod)
            );
        } else if (labelLanguage == "言語設定") {
            String useOfROMsThatDifferBetweenRegionsJP = productInformation.getUseOfROMsThatDifferBetweenRegions(TextConstants.selectRomDifferBetweenRegion_JP);
            String languageUsedForTextEntryInFeaturesJP = productInformation.getLanguageUsedForTextEntryInFeatures(TextConstants.textEntryLanguage_JP);
            assertAll(
                    () -> assertEquals(TextConstants.overviewText, titleOverviewPage, TextConstants.pageHeader),
                    () -> assertEquals(TextConstants.SUBMITTED, internalStatus, TextConstants.internalStatus),
                    () -> assertEquals("Tran_edit1 123234_edit1", submittedBy, TextConstants.submittedBy),
                    () -> assertEquals(TextConstants.enterDeveloperComment, developerComments, TextConstants.developerComments),
                    () -> assertEquals(expectedLotcheckSubmissionDateOnNDP, plannedSubmissionDate, TextConstants.expectedSubmissionDateOverview),
                    () -> assertEquals(expectedReleaseDateOnNDP, plannedReleaseDate, TextConstants.expectedReleaseDateOverview),
                    () -> assertEquals(TextConstants.productInformation, titlePageProductInfo, TextConstants.headerPageProductInfo),
                    () -> assertEquals("ロットチェック", submissionType, TextConstants.submissionType),
                    () -> assertEquals("日本", targetDeliveryFormatDigital, TextConstants.targetDeliveryFormatDigital),
                    () -> assertEquals(TextConstants.submitToLotcheck_JP, status, TextConstants.statusField),
                    () -> assertEquals("1.0.1", displayVersion, TextConstants.displayVersion),
                    () -> assertEquals("01", releaseVersion, TextConstants.releaseVersion),
                    () -> assertEquals("00", submissionVersion, TextConstants.submissionVersion),
                    () -> assertEquals("パッチ", releaseType, TextConstants.releaseType),
                    () -> assertEquals(expectedLotcheckSubmissionDateOnNDP, expectedSubmissionDate, TextConstants.expectedSubmissionDateProductInfo),
                    () -> assertEquals(expectedReleaseDateOnNDP, expectedReleaseDate, TextConstants.expectedReleaseDateProductInfo),
                    () -> assertEquals("No", FreeToPlay, TextConstants.free2Play),
                    () -> assertEquals(TextConstants.romDifferBetweenRegion, useOfROMsThatDifferBetweenRegionsJP, TextConstants.compareRomDifferBetweenRegion),
                    () -> assertEquals("英語", languageUsedForTextEntryInFeaturesJP, TextConstants.languageUseEntryFeature),
                    () -> assertEquals(TextConstants.computerCERO, ratingAgency, TextConstants.ratingAgency),
                    () -> assertEquals("B", rating, TextConstants.rating),
                    () -> assertEquals("STANDARD", ratingMethod, TextConstants.ratingMethod)
            );
        }
    }
}
