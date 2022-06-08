package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.junit.TestLogger;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.data.DataGen;
import net.nintendo.automation.ui.data.features.FeatureSettingsManager;
import net.nintendo.automation.ui.data.features.enums.DemoEnum;
import net.nintendo.automation.ui.data.features.enums.InputDevicesEnum;
import net.nintendo.automation.ui.data.features.enums.LotcheckRemotePresenceEnum;
import net.nintendo.automation.ui.data.features.enums.PatchesEnum;
import net.nintendo.automation.ui.data.features.enums.PlayModesEnum;
import net.nintendo.automation.ui.data.features.enums.ProgramSpecificationsEnum;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.email.EmailDevPage;
import net.nintendo.automation.ui.models.email.EmailPage;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthPage;
import net.nintendo.automation.ui.models.lcms.LotcheckHomePage;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.ui.models.lcms.LotcheckQueuePage;
import net.nintendo.automation.ui.models.lcms.LotcheckSubmissionSearchPage;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionProductInformation;
import net.nintendo.automation.ui.models.ndp.NDPCreateProductPage;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPMyAccountPage;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.NDPRequestGameCodePage;
import net.nintendo.automation.ui.models.ndp.NDPSignInPage;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_internal.NDPInternalPage;
import net.nintendo.automation.ui.models.ndp.existing_internal.NDPProductSearchPage;
import net.nintendo.automation.ui.models.ndp.existing_internal.view_product_dashboard.NDPSettingTab;
import net.nintendo.automation.ui.models.ndp.existing_internal.view_product_dashboard.NDPViewProductDashboardPage;
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
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPEnterJudgmentDialog;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPReleaseInfoFeaturesDemoDialog;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPReleaseInfoFeaturesInputDevicesDialog;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPReleaseInfoFeaturesLotcheckRemotePresenceDialog;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPReleaseInfoFeaturesPatchesDialog;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPReleaseInfoFeaturesPlayModesDialog;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPReleaseInfoFeaturesProgramSpecsDialog;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@NDP
@SLCMS
@Tag("T3404488")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3404488 extends CommonBaseTest {
    private static Browser browser;
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
    private static CommonUIComponent currentNDPPage;
    private static CommonUIComponent currentPage;
    private static TestLogger logger;
    //String productBase = "Selenium_rmUI (DKG3A)";
    String productBase = "NuLT_TEST4_Org_0322_02 (C58QA)";
    String mailUserPartner = "huyentrang";
    public static String emailName;

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
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.int_approval_coord_NCL);
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
        createProductPage.selectProductType(TextConstants.demo_Product)
                .selectPublishingRelationship(TextConstants.first_Party_Type)
                .selectDigitalJapanSalesRegion()
                .selectDigitalAmericasSalesRegion()
                .selectDigitalEuropeSalesRegion()
                .selectDigitalAsiaSalesRegion()
                .selectProductName(productName)
                .selectProductNameKana(TextConstants.productKana)
                .selectBasedProduct(productBase)
                .clickCreateButton();
        NDPProductDashboardPage ndpDashboardPage = myProductsPage.clickProductByName(productName);
        NDPRequestGameCodePage requestGameCodePage = ndpDashboardPage.issueGameCode();
        NDPProductDashboardPage ndpProductDashboardPage = requestGameCodePage.setExpectedLotcheckSubmissionDate()
                .setExpectedReleaseDate(60)
                //.selectFreeToPlay("NO")
                .clickIssue();
        String issueGameSuccessful = ndpProductDashboardPage.navProductInfoDashboard().getMessageIssueGameCodeSuccessful();
        assertEquals(TextConstants.issueGameCodeSuccess, issueGameSuccessful);
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
                .selectClassIndRating("ClassInd: 10")
                .selectPEGIRating("PEGI: PEGI 3")
                .selectUSKRating("USK: 0")
                .selectGRACGCRBRating("GRACGCRB: 12")
                .clickOnCreateButton()
                .waitIsLoaded();
        fileRomName = romCreateRom.getRomNameTable(applicationID);
        romCreateRom.clickOnROMFileOnROMTable(applicationID);
        WaitUtils.idle(8000);
        BrowserManager.closeBrowser(romBrowser);
    }

    @Test
    @Order(3)
    public void settingSkipLotCheck(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(TextConstants.int_pwr_user_NCL, TextConstants.nintendo_approval_Password);
        NDPInternalPage internalPage = developmentHome.clickInternalTab();
        NDPProductSearchPage ndpProductSearchPage = internalPage.clickProductSearchButton();
        NDPViewProductDashboardPage ndpViewProductDashboardPage = ndpProductSearchPage.clickSearchContent(initialCode)
                .waitingLoadingValueSearch()
                .selectElementFromDropDown();
        NDPSettingTab ndpSettingTab = ndpViewProductDashboardPage.clickSettingTab();
        ndpSettingTab.selectSkipLotcheckForPatch()
                .clickSaveButton();
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
    }

    @Test()
    @Order(4)
    public void fillOurDashboard(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser).clickMyProducts();
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
        //create release infor
        NDPReleaseInfoPage releaseInfoPage = ndpReleaseInfoPage.nav().clickReleaseInfo();
        CommonAction.selectROMWithDemoType(releaseInfoPage, "2 GB", " RID Kiosk Only",
                TextConstants.selectRomSameAllRegion, TextConstants.english);
        //create Feature
        HashMap<ProgramSpecificationsEnum, String> programSpecificationsSettings = FeatureSettingsManager.getProgramSpecificationsSettings(TextConstants.allOff_Demo);
        HashMap<InputDevicesEnum, String> inputDeviceSettings = FeatureSettingsManager.getInputDevicesSettings(TextConstants.allOff);
        HashMap<PlayModesEnum, String> playModesSettings = FeatureSettingsManager.getPlayModesSettings(TextConstants.allOff);
        HashMap<LotcheckRemotePresenceEnum, String> lotcheckSettings = FeatureSettingsManager.getLotcheckPresenceSettings("Confirm");
        HashMap<DemoEnum, String> demoSettings = FeatureSettingsManager.getDemoSettings(TextConstants.allOff);
        NDPReleaseInfoFeaturesPage releaseFeatures = ndpReleaseInfoPage.nav().clickFeatures();
        NDPReleaseInfoFeaturesProgramSpecsDialog progSpecDialog = releaseFeatures.clickEditProgramSpecifications();
        progSpecDialog.setFeatures(programSpecificationsSettings).clickSave();
        //todo: have this in a separate test once we have framework support so this does not affect scenario testing
        // in case of failures.
        assertTrue(releaseFeatures.isProgramSpecificationsComplete());
        NDPReleaseInfoFeaturesInputDevicesDialog inputDeviceDialog = releaseFeatures.clickEditInputDevices();
        inputDeviceDialog.setFeatures(inputDeviceSettings).clickSave();
        NDPReleaseInfoFeaturesPlayModesDialog playModesDialog = releaseFeatures.clickEditPlayModes();
        playModesDialog.setFeatures(playModesSettings).clickSave();
        NDPReleaseInfoFeaturesDemoDialog demoDialog = releaseFeatures.clickEditDemo();
        demoDialog.setFeatures(demoSettings).clickSave();
        NDPReleaseInfoFeaturesLotcheckRemotePresenceDialog lotcheckDialog = releaseFeatures.clickEditLotcheckRemotePresence();
        lotcheckDialog.setFeatures(lotcheckSettings).clickSave();
        assertTrue(releaseFeatures.isAccountSpecificationsNotApplicable());
        // refresh or click continue is required to see features as completed
        releaseFeatures.clickContinue();
        assertTrue(ndpReleaseInfoPage.nav().isFeaturesCompleted());

        //Create GuildLine
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = ndpReleaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoGuidelinesPage.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();
        //Create Age Rating
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = ndpReleaseInfoPage.nav().clickAgeRating();
        ndpReleaseInfoAgeRating.clickOnAddStandardRatingButton()
                .selectStandardAgeRating("CERO")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectRatingValue("A")
                .uploadCertificateAttachment(new File("src/test/resources/DataFile/ab.jpg"))
                .clickOnSaveButtonInEditStandardRating()
                .clickOnAddStandardRatingButton()
                .selectStandardAgeRating("ClassInd")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectRatingValue("10")
                .uploadCertificateAttachment(new File("src/test/resources/DataFile/Test_a.xlsx"))
                .clickOnSaveButtonInEditStandardRating()
                .clickOnAddStandardRatingButton()
                .selectStandardAgeRating("PEGI")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectRatingValue("PEGI 3")
                .uploadCertificateAttachment(new File("src/test/resources/DataFile/Test_abc.jpg"))
                .clickOnSaveButtonInEditStandardRating()
                .clickOnAddStandardRatingButton()
                .selectStandardAgeRating("USK")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectRatingValue("0")
                .uploadCertificateAttachment(new File("src/test/resources/DataFile/test_doc.docx"))
                .clickOnSaveButtonInEditStandardRating()
                .clickOnAddStandardRatingButton()
                .selectStandardAgeRating("GRAC")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectRatingValue("12")
                .uploadCertificateAttachment(new File("src/test/resources/DataFile/Test_excel.xlsx"))
                .clickOnSaveButtonInEditStandardRating();
        //upload ROM
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = ndpReleaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
        ndpReleaseInfoROMUpload.deleteFile(fileRomName);
        //Request Issue
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
    @Order(5)
    public void submitToLotCheck(IJUnitTestReporter testReport) {
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
    @Order(6)
    public void setStatusTestPlanOnTestRail(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailDashboardPage testRailDashboardPage = testRailLoginPage.signInTestRail(testRailUser);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        CommonAction.setStatusOnTestrail(testRailManagerDashboardPage, initialCode, "Passed", TextConstants.percentCompleted, TextConstants.OK);
        testRailDashboardPage.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton();

        testRailLoginPage.signInTestRail(TextConstants.trAdminNOE_Email, TextConstants.trAdmin_Password);
        testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        CommonAction.setStatusOnTestrail(testRailManagerDashboardPage, initialCode, "Passed", TextConstants.percentCompleted, TextConstants.OK);
        testRailDashboardPage.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton();

        testRailLoginPage.signInTestRail(TextConstants.trAdminNOA_Email, TextConstants.trAdmin_Password);
        testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        CommonAction.setStatusOnTestrail(testRailManagerDashboardPage, initialCode, "Passed", TextConstants.percentCompleted, TextConstants.OK);
        testRailDashboardPage.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton();
    }

    //Approve judgement in SLCMS
    @Test()
    @Order(7)
    public void approvalJudgmentOK(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickJudgment(LotcheckRegion.NCL);
        lotcheckLandingPage.navigationToJudgmentApprovalQueue(LotcheckRegion.NCL.toString());
        CommonAction.approvalJudgmentStatusInRegion(lotcheckLandingPage, lotcheckQueuePage, initialCode, 100, "OK", LotcheckRegion.NCL.toString());
        WaitUtils.idle(10000);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
        //aprover on noe region
        browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN).continueToLogin().loginWithOvdLink(TextConstants.lc_Admin_NOE, TextConstants.lc_Admin_NOE_Password);
        lotcheckLandingPage.navigationToJudgmentApprovalQueue(LotcheckRegion.NOE.toString());
        CommonAction.approvalJudgmentStatusInRegion(lotcheckLandingPage, lotcheckQueuePage, initialCode, 100, "OK", LotcheckRegion.NOE.toString());
        WaitUtils.idle(10000);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
        //aprover on noe region
        browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN).continueToLogin().loginWithOvdLink(TextConstants.lc_Admin_NOA, TextConstants.lc_Admin_NOA_Password);
        lotcheckLandingPage.navigationToJudgmentApprovalQueue(LotcheckRegion.NOA.toString());
        CommonAction.approvalJudgmentStatusInRegion(lotcheckLandingPage, lotcheckQueuePage, initialCode, 100, "OK", LotcheckRegion.NOA.toString());
    }

    //create patch
    @Test()
    @Order(8)
    public void createNewReleasePatchAndConfirmRequiredApprovalsTab(IJUnitTestReporter testReport) throws Exception {
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
                .enterDescribeTheFeaturesPlanned(TextConstants.enterDescribeTheFeaturesPlanned)
                .enterDescribeTheBugsIssuesPlanned(TextConstants.enterDescribeTheBugsIssuesPlanned)
                .clickCreateButton();
        ndpReleaseInfoPage.nav().clickReleaseInfo();
        ndpReleaseInfoPage
                .selectSubmissionType("Skip Lotcheck")
                .selectRomsThatDifferBetweenRegion(TextConstants.selectRomSameAllRegion)
                .selectLanguageUsedForTextEntry(TextConstants.english)
                .save();
        NDPReleaseInfoIssuePage ndpReleaseInfoIssuePage = ndpReleaseInfoPage.nav().clickIssues();
        String generateAtRequiredApprovals = ndpReleaseInfoIssuePage.getTextRequiredApprovalsTab();
        assertEquals("Required Approvals (0)", generateAtRequiredApprovals, "This will NOT generate an entry in Issues > Required Approvals");
    }

    @Test()
    @Order(9)
    public void fillOutFeatureAndConfirmRequiredApprovalsTab(IJUnitTestReporter testReport) throws InterruptedException {
        NDPMyProductsPage myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);

        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectPatchRelease();
        currentNDPPage = ndpReleaseInfoPage;
        logger = new TestLogger(testReport);
        currentNDPPage.setExtentLogger(logger);
        ndpReleaseInfoPage.nav().clickFeatures();
        //create Feature
        HashMap<ProgramSpecificationsEnum, String> programSpecificationsSettings = FeatureSettingsManager.getProgramSpecificationsSettings(TextConstants.allOff_SetValue);
        HashMap<InputDevicesEnum, String> inputDeviceSettings = FeatureSettingsManager.getInputDevicesSettings(TextConstants.allOff);
        HashMap<PlayModesEnum, String> playModesSettings = FeatureSettingsManager.getPlayModesSettings(TextConstants.allOff);
        HashMap<LotcheckRemotePresenceEnum, String> lotcheckSettings = FeatureSettingsManager.getLotcheckPresenceSettings("Confirm");
        HashMap<PatchesEnum, String> patchesSettings = FeatureSettingsManager.getPatchesSettings(TextConstants.allOff);
        HashMap<DemoEnum, String> demoSettings = FeatureSettingsManager.getDemoSettings(TextConstants.allOff);
        NDPReleaseInfoFeaturesPage releaseFeatures = ndpReleaseInfoPage.nav().clickFeatures();
        NDPReleaseInfoFeaturesProgramSpecsDialog progSpecDialog = releaseFeatures.clickEditProgramSpecifications();
        progSpecDialog.setFeatures(programSpecificationsSettings).clickSave();
        //todo: have this in a separate test once we have framework support so this does not affect scenario testing
        // in case of failures.
        NDPReleaseInfoFeaturesInputDevicesDialog inputDeviceDialog = releaseFeatures.clickEditInputDevices();
        inputDeviceDialog.setFeatures(inputDeviceSettings).clickSave();
        NDPReleaseInfoFeaturesPlayModesDialog playModesDialog = releaseFeatures.clickEditPlayModes();
        playModesDialog.setFeatures(playModesSettings).clickSave();
        NDPReleaseInfoFeaturesPatchesDialog patchesDialog = releaseFeatures.clickEditPatches();
        patchesDialog.setFeatures(patchesSettings).clickSave();
        NDPReleaseInfoFeaturesDemoDialog demoDialog = releaseFeatures.clickEditDemo();
        demoDialog.setFeatures(demoSettings).clickSave();
        NDPReleaseInfoFeaturesLotcheckRemotePresenceDialog lotcheckDialog = releaseFeatures.clickEditLotcheckRemotePresence();
        lotcheckDialog.setFeatures(lotcheckSettings).clickSave();
        // refresh or click continue is required to see features as completed
        releaseFeatures.clickContinue();

        NDPReleaseInfoIssuePage ndpReleaseInfoIssuePage = ndpReleaseInfoPage.nav().clickIssues();
        String generateAtRequiredApprovals = ndpReleaseInfoIssuePage.getTextRequiredApprovalsTab();
        Assertions.assertAll(
                () -> assertTrue(ndpReleaseInfoPage.nav().isFeaturesCompleted()),
                () -> assertEquals("Required Approvals (1)", generateAtRequiredApprovals, TextConstants.noGenerateIssueRequireApproval)
        );
    }

    @Test()
    @Order(10)
    public void checkGuidelinesAndConfirmRequiredApprovalsTab(IJUnitTestReporter testReport) throws InterruptedException {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentNDPPage;
        //Create GuildLine
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = ndpReleaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoGuidelinesPage
                .selectCannotConformToThisGuideline()
                .selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();
        NDPReleaseInfoIssuePage ndpReleaseInfoIssuePage = ndpReleaseInfoPage.nav().clickIssues();
        String generateAtRequiredApprovals = ndpReleaseInfoIssuePage.getTextRequiredApprovalsTab();
        Assertions.assertAll(
                () -> assertEquals("Required Approvals (2)", generateAtRequiredApprovals, TextConstants.noGenerateIssueRequireApproval)
        );
    }

    @Test()
    @Order(11)
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
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ROMLoginPage romLoginPage = (ROMLoginPage) currentPage;
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
                .selectCERORating("CERO: A")
                .selectClassIndRating("ClassInd: 10")
                .selectPEGIRating("PEGI: PEGI 3")
                .selectGRACGCRBRating("GRACGCRB: 12")
                .clickOnCreateButton()
                .waitIsLoaded();
        fileRomName = romCreateRom.getRomNameTable(applicationID);
        romCreateRom.clickOnROMFileOnROMTable("Patch_" + applicationID + "_v01.nsp");
        WaitUtils.idle(5000);
        romCreateRom.clickOnUserNameMenu().clickOnLogoutButton();
        BrowserManager.closeBrowser(romBrowser);
    }

    @Test()
    @Order(12)
    public void checkingTheIssuesBlockingSkipLotcheckSubmission(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectPatchRelease();
        //enter test scenarios
        NDPReleaseInfoTestScenarios ndpReleaseInfoTestScenarios = ndpReleaseInfoPage.nav().clickTestScenarios();
        ndpReleaseInfoTestScenarios.expandAllSections()
                .expandAllTestScenes()
                .enterSubjectForProgramSpecificationsHowToCheckCommunicationWithPlatformsOtherThanNintendoSwitch(TextConstants.commentTestScenario, 0)
                .enterExplainForProgramSpecificationsHowToCheckCommunicationWithPlatformsOtherThanNintendoSwitch(TextConstants.commentExplainProgram, 0)
                .clickOnSaveButton();
        //Create Age Rating
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = ndpReleaseInfoPage.nav().clickAgeRating();
        //Upload ROM
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = ndpReleaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
        //confirm at Issue Tab
        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
        String generateAtRequiredApprovals = issuePage.getTextRequiredApprovalsTab();
        issuePage.openRequiredApprovalTab();
        String contentOfSection = issuePage.getIssuesBlockingSkipLotcheckSubmission();
        String messageDisplayedBelowTheIssuesBlocking = issuePage.getMessageBelowIssuesBlockingSkipLotcheckSubmission().substring(41);
        myProductsPage.clickOnUserNameMenu().clickJapanButton();
        ndpReleaseInfoPage.nav().clickIssues();
        issuePage.openRequiredApprovalTab();
        String contentOfSection1 = issuePage.getIssuesBlockingSkipLotcheckSubmission();
        String messageDisplayedBelowTheIssuesBlocking1 = issuePage.getMessageBelowIssuesBlockingSkipLotcheckSubmission().substring(25);
        Assertions.assertAll(
                () -> assertEquals("Issues Blocking Skip-Lotcheck Submission", contentOfSection, TextConstants.confirmEnglishSection),
                () -> assertEquals(TextConstants.messageMustBeResolved, messageDisplayedBelowTheIssuesBlocking, TextConstants.messageDisplayBelowIssueBlocking),
                () -> assertEquals("ロットチェック省略の申請をブロックする要対応項目", contentOfSection1, TextConstants.confirmJapaneseSection),
                () -> assertEquals(TextConstants.issueApprovalByNintendo_JP, messageDisplayedBelowTheIssuesBlocking1, TextConstants.messageDisplayBelowIssueBlocking),
                () -> assertNotEquals("Required Approvals (1)", generateAtRequiredApprovals, TextConstants.noGenerateIssueRequireApproval)
        );

    }

    @Test()
    @Order(13)
    public void ConfirmTheContentOfMessageDisplayedBelowTheResolvedIssues(IJUnitTestReporter testReport) throws InterruptedException {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentNDPPage;
        each(testReport);
        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
        issuePage.openRequiredApprovalTab();
        String messageBelowResolvedIssues = issuePage.getMessageBelowResolvedIssues().substring(11);
        String contentOfSeeAll = issuePage.getContentOfSeeAllButton().substring(0, 6);
        NDPMyProductsPage myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        myProductsPage.clickOnUserNameMenu().clickEnglishButton();
        ndpReleaseInfoPage.navProductPage().clickProductByName(productName)
                .clickReleases()
                .selectPatchRelease();
        ndpReleaseInfoPage.nav().clickIssues();
        issuePage.openRequiredApprovalTab();
        String messageBelowResolvedIssues1 = issuePage.getMessageBelowResolvedIssues().substring(16);
        String contentOfSeeAll1 = issuePage.getContentOfSeeAllButton().substring(0, 7);
        System.out.println(contentOfSeeAll);
        Assertions.assertAll(
                () -> assertEquals("すべてを参照", contentOfSeeAll, "Confirm the content of section on Japan"),
                () -> assertEquals("これらの要対応項目には既に特別承認依頼が出されており、任天堂によって承認されています。", messageBelowResolvedIssues, "message displayed below the Issues Blocking Skip-Lotcheck"),
                () -> assertEquals("These issues have already had a special approval requested that has been approved by Nintendo.", messageBelowResolvedIssues1, "message displayed below the Issues Blocking Skip-Lotcheck"),
                () -> assertEquals("See all", contentOfSeeAll1, "Confirm the content of section on English")
        );
    }


    @Test()
    @Order(14)
    public void confirmFillOutReleasePatch01(IJUnitTestReporter testReport) throws InterruptedException {
        //NDPMyProductsPage myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectPatchRelease();
        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
        issuePage.openRequiredApprovalTab();
        int numUnresolved = issuePage.getNumberOfCurrentUnresolvedRequiredApprovals();
        for (int i = 0; i < numUnresolved - 3; i++) {
            issuePage.openRequiredApprovalTab();
            issuePage.clickOnFirstUnresolvedRequiredApproval()
                    .enterReasonForRequest(TextConstants.enterReasonForRequest)
                    .selectDesiredApprovalTypeAtTemporaryApprovalRequiredApproval().clickOnSendResolutionButton();
        }
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
        //Approval Issue TaskRequested
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(licUser).clickOnMyTaskMenu();
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        CommonAction.approveInternalWaiverRequest(developmentHome, internalTasksPage, gameCode);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
        browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser).clickMyProducts();
        myProductsPage.clickProductByName(productName);
        ndpProductDashboardPage.clickReleases();
        ndpProductDashboardReleasesTab.selectPatchRelease();
    }


    @Test()
    @Order(15)
    public void confirmDisplayedTheResolvedIssues(IJUnitTestReporter testReport) throws InterruptedException {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentNDPPage;
        logger = new TestLogger(testReport);
        currentNDPPage.setExtentLogger(logger);
        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
        issuePage.openRequiredApprovalTab();
        String seeAllIssueDefault = issuePage.getContentOfSeeAllButton().trim().substring(0, 7);
        System.out.println(seeAllIssueDefault);
        String numberIssueApproval = issuePage.getContentOfSeeAllButton().trim().substring(8, 9);
        System.out.println(numberIssueApproval);
        int numberIssueApprovalConvert = Integer.parseInt(numberIssueApproval);
        if (seeAllIssueDefault.equals("See all")) {
            assertTrue(issuePage.waitIsNOTDisplayResolvedIssues());
        }
        issuePage.clickSeeAllButton();
        String hideAllIssue = issuePage.getContentOfHideAllButton().trim().substring(0, 8);
        int numberResolvedIssues = issuePage.getNumberOfCurrentProductResolutionArchiveIssues();
        issuePage.clickHideAllButton();
        String seeAllIssue = issuePage.getContentOfSeeAllButton().trim().substring(0, 7);
        Assertions.assertAll(
                () -> assertEquals("Hide all", hideAllIssue, "Action hide All"),
                () -> assertEquals(numberIssueApprovalConvert, numberResolvedIssues, "number Resolved Issue"),
                () -> assertEquals("See all", seeAllIssue, "action See All")
        );
    }

    @Test
    @Order(16)
    public void submitButtonPatch01(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentNDPPage;
        assertTrue(ndpReleaseInfoPage.nav().isSubmitButtonActivated());
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.enterDeveloperComment).clickSubmit();
        WaitUtils.idle(4000);
    }

    @Test()
    @Order(17)
    public void confirmEmailIntPwrUser(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPMyAccountPage ndpMyAccountPage = myProductsPage.clickOnUserNameMenu().clickManagerAccountButton();
        String nameOrg = ndpMyAccountPage.getOrganizationName();
        System.out.println(nameOrg);
        EmailPage emailPage = browser.openURL(EmailPage.class, PageState.PRE_LOGIN);
        emailPage.signInEmail("prdcv_int_pwr_user_ncl");
        emailName = "[NDP][SkipLC][Submission] " + gameCode + " 01.00 " + productName + " (" + nameOrg + ")";
        int collectNumber = emailPage.collectNumberEmail();
        for (int i = 0; i < collectNumber; i++) {
            if (emailPage.getValueEmail(i) != emailName) {
                System.out.println(emailPage.getValueEmail(i));
            } else if (emailPage.getValueEmail(i) == emailName) {
                break;
            }
            assertTrue(emailPage.confirmDisplayEmail(emailName));
        }
    }

    @Test()
    @Order(18)
    public void confirmEmailPartner(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(EmailPage.class, PageState.PRE_LOGIN);
        EmailPage emailPage = (EmailPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        emailPage.signInEmail(mailUserPartner);
        int collectNumber1 = emailPage.collectNumberEmail();
        for (int j = 0; j < collectNumber1; j++) {
            if (emailPage.getValueEmail(j) != emailName) {
                System.out.println(emailPage.getValueEmail(j));
            } else if (emailPage.getValueEmail(j) == emailName) {
                break;
            }
            assertTrue(emailPage.confirmDisplayEmail(emailName));
        }
    }

    @Test()
    @Order(19)
    public void confirmEmailDev(IJUnitTestReporter testReport) throws InterruptedException, IOException {
        //EmailDevPage emailDevPage = browser.openURL(EmailDevPage.class,PageState.PRE_LOGIN);
        currentPage = browser.openURL(EmailDevPage.class, PageState.PRE_LOGIN);
        EmailDevPage emailDevPage = (EmailDevPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        emailDevPage.enterMailBox("lc_translator_ncl");
        emailDevPage.searchEmail(gameCode);
        emailDevPage.clickOpenEmail().clickSourceButton().selectSourceTag();
        assertTrue(emailDevPage.waitElementIsNotVisible("receipient"), "receipient is Not visible");
        assertTrue(emailDevPage.waitElementIsNotVisible("To:"), "To: is Not visible");
    }

    @Test()
    @Order(20)
    public void confirmDisplayPatchInSubmissionSearch(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckSubmissionSearchPage submissionSearchPage = lotcheckLandingPage.searchProduct(initialCode);
        submissionSearchPage.searchByReleaseVersion("01");
        submissionSearchPage.clickSearchButton();
        LotcheckReportSubmissionOverview submissionOverview = submissionSearchPage.selectMenuForGameCode().
                selectDropdownItem();
        String valueSkipTestPlan = submissionOverview.matrix().getSkipTestPlan();
        assertEquals(TextConstants.SKIP_LOTCHECK, valueSkipTestPlan);
    }

    @Test()
    @Order(21)
    public void setNGJudgementTestPlan(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectPatchRelease();
        NDPEnterJudgmentDialog ndpEnterJudgmentDialog = ndpReleaseInfoPage.nav().clickEnterJudgmentButton();
        ndpEnterJudgmentDialog.selectNoGood().clickSubmitJudgment();
        assertTrue(ndpReleaseInfoPage.nav().enterJudgmentButtonIsDisable());
    }

    @Test()
    @Order(22)
    public void confirmSubmissionVersion(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckSubmissionSearchPage submissionSearchPage = lotcheckLandingPage.searchProduct(initialCode);
        submissionSearchPage.searchBySubmissionVersion("01");
        submissionSearchPage.clickSearchButton();
        LotcheckReportSubmissionOverview submissionOverview = submissionSearchPage.selectMenuForGameCode().selectDropdownItem();
        LotcheckReportSubmissionProductInformation productInformation = submissionOverview.navBar().clickProductInformation();
        productInformation.expandReleaseInformation();
        String submissionVersionSLCMS = productInformation.getSubmissionVersion();
        NDPMyProductsPage myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectPatchRelease();
        String submissionVersionNDP = ndpReleaseInfoPage.getSubmissionVersion().substring(0, 2);

        Assertions.assertAll(
                () -> assertEquals("01", submissionVersionNDP, TextConstants.statusSubmissionNDP),
                () -> assertEquals("01", submissionVersionSLCMS, TextConstants.statusSubmissionSLCMS)
        );
    }

    @Test()
    @Order(23)
    public void reSubmitPatchSubmission01(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectPatchRelease();
        //create release information
        NDPReleaseInfoPage releaseInfoPage = ndpReleaseInfoPage.nav().clickReleaseInfo();
        releaseInfoPage
                .setExpectedLotcheckSubmissionDate()
                .setExpectedReleaseDate()
                .save();
        //guideline
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = ndpReleaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoGuidelinesPage.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();
        //upload ROM
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = ndpReleaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
        //Delete file Rom in folder
        ndpReleaseInfoROMUpload.deleteFile(fileRomName);
        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
        //submit patch submission 01
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.commentSubmission).clickSubmit();
        WaitUtils.idle(3000);
    }

    @Test()
    @Order(24)
    public void confirmTestPlanIsDisplayInSLCMS(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckSubmissionSearchPage submissionSearchPage = lotcheckLandingPage.searchProduct(initialCode);
        submissionSearchPage.searchBySubmissionVersion("01");
        submissionSearchPage.clickSearchButton();
        LotcheckReportSubmissionOverview submissionOverview = submissionSearchPage.selectMenuForGameCode().
                selectDropdownItem();
        String valueSkipTestPlan = submissionOverview.matrix().getSkipTestPlan();
        System.out.println(valueSkipTestPlan);
        assertEquals(TextConstants.SKIP_LOTCHECK, valueSkipTestPlan);
    }

    @Test()
    @Order(25)
    public void setOKJudgementPatchSubmission01(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectPatchRelease();
        NDPEnterJudgmentDialog ndpEnterJudgmentDialog = ndpReleaseInfoPage.nav().clickEnterJudgmentButton();
        ndpEnterJudgmentDialog.selectApproved().clickSubmitJudgment();
        assertTrue(ndpReleaseInfoPage.nav().enterJudgmentButtonIsDisable());
    }

    //create patch
    @Test()
    @Order(26)
    public void createNewReleasePatchSubmissionTypeLotcheck(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPCreateReleasePage ndpCreateReleasePage = ndpProductDashboardReleasesTab.clickCreateNewReleaseButton();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpCreateReleasePage
                .enterDisplayVersion("1.0.2")
                .selectExpectedSubmissionDate(10)
                .selectExpectedReleaseDate(20)
                //.selectFreeToPlay("NO")
                .enterDescribeTheFeaturesPlanned(TextConstants.enterDescribeTheFeaturesPlanned)
                .enterDescribeTheBugsIssuesPlanned(TextConstants.enterDescribeTheBugsIssuesPlanned)
                .clickCreateButton();
        //create ROM
        browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        myProductsPage.clickProductByName(productName);
        //ndpProductDashboardPage.clickReleases();
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();
        applicationID = ndpProductDashboardProductInfoTab.getApplicationID();
        gameCode = ndpProductDashboardProductInfoTab.getGameCode();
        productName = ndpProductDashboardProductInfoTab.getProductNameValue();
        String originalApp = "Application_" + applicationID + "_v00.nsp";
        String previousPatch = "Patch_" + applicationID + "_v01.nsp";
        //create ROM
        romBrowser = BrowserManager.getNewInstance();
        ROMLoginPage romLoginPage = romBrowser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN);
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom.reloadPage()
                .selectRequester(TextConstants.test_User)
                .selectType(TextConstants.patch_Release)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .enterProductName(productName)
                .enterApplicationId(applicationID)
                .selectPublishingRelationShip(TextConstants.first_Party_Type)
                .enterDisplayVersion("1.0.2")
                .enterReleaseVersion("02")
                .enterOriginalApp(originalApp)
                .enterPreviousPatch(previousPatch)
                .selectCERORating("CERO: A")
                .selectClassIndRating("ClassInd: 10")
                .selectPEGIRating("PEGI: PEGI 3")
                .selectGRACGCRBRating("GRACGCRB: 12")
                .clickOnCreateButton()
                .waitIsLoaded();
        fileRomName = romCreateRom.getRomNameTable(applicationID);
        romCreateRom.clickOnROMFileOnROMTable(applicationID);
        WaitUtils.idle(5000);
        romCreateRom.clickOnUserNameMenu().clickOnLogoutButton();
        BrowserManager.closeBrowser(romBrowser);
        //create Release Info
        browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        myProductsPage.clickProductByName(productName);
        ndpProductDashboardPage.clickReleases();
        ndpProductDashboardReleasesTab.selectDisplayVersion("1.0.2");
        ndpReleaseInfoPage.nav().clickReleaseInfo();
        CommonAction.selectROMWithSubmissionType(ndpReleaseInfoPage, TextConstants.lotcheck, TextConstants.selectRomSameAllRegion, TextConstants.english);
        ndpReleaseInfoPage.nav().clickFeatures();
        //create Feature
        HashMap<ProgramSpecificationsEnum, String> programSpecificationsSettings = FeatureSettingsManager.getProgramSpecificationsSettings(TextConstants.allOff_Demo);
        HashMap<InputDevicesEnum, String> inputDeviceSettings = FeatureSettingsManager.getInputDevicesSettings(TextConstants.allOff);
        HashMap<PlayModesEnum, String> playModesSettings = FeatureSettingsManager.getPlayModesSettings(TextConstants.allOff);
        HashMap<LotcheckRemotePresenceEnum, String> lotcheckSettings = FeatureSettingsManager.getLotcheckPresenceSettings("Confirm");
        HashMap<PatchesEnum, String> patchesSettings = FeatureSettingsManager.getPatchesSettings(TextConstants.allOff);
        HashMap<DemoEnum, String> demoSettings = FeatureSettingsManager.getDemoSettings(TextConstants.allOff);
        NDPReleaseInfoFeaturesPage releaseFeatures = ndpReleaseInfoPage.nav().clickFeatures();
        NDPReleaseInfoFeaturesProgramSpecsDialog progSpecDialog = releaseFeatures.clickEditProgramSpecifications();
        progSpecDialog.setFeatures(programSpecificationsSettings).clickSave();
        //todo: have this in a separate test once we have framework support so this does not affect scenario testing
        // in case of failures.
        NDPReleaseInfoFeaturesInputDevicesDialog inputDeviceDialog = releaseFeatures.clickEditInputDevices();
        inputDeviceDialog.setFeatures(inputDeviceSettings).clickSave();
        NDPReleaseInfoFeaturesPlayModesDialog playModesDialog = releaseFeatures.clickEditPlayModes();
        playModesDialog.setFeatures(playModesSettings).clickSave();
        NDPReleaseInfoFeaturesPatchesDialog patchesDialog = releaseFeatures.clickEditPatches();
        patchesDialog.setFeatures(patchesSettings).clickSave();
        NDPReleaseInfoFeaturesDemoDialog demoDialog = releaseFeatures.clickEditDemo();
        demoDialog.setFeatures(demoSettings).clickSave();
        NDPReleaseInfoFeaturesLotcheckRemotePresenceDialog lotcheckDialog = releaseFeatures.clickEditLotcheckRemotePresence();
        lotcheckDialog.setFeatures(lotcheckSettings).clickSave();
        releaseFeatures.clickContinue();
        //Create GuildLine
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = ndpReleaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoGuidelinesPage
                .selectCannotConformToThisGuideline()
                .selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();
        //Upload ROM
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = ndpReleaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
        //Delete file Rom in folder
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
        //submit release patch 02
        browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser).clickMyProducts();
        myProductsPage.clickProductByName(productName);
        ndpProductDashboardPage.clickReleases();
        ndpProductDashboardReleasesTab.selectDisplayVersion("1.0.2");
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.commentSubmission).clickSubmit();
        WaitUtils.idle(3000);
    }

    @Test()
    @Order(27)
    public void setStatusTestPlanOnTestRailPatchSubmission01(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailDashboardPage testRailDashboardPage = testRailLoginPage.signInTestRail(testRailUser);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        CommonAction.setStatusOnTestrail(testRailManagerDashboardPage, initialCode, "Passed", TextConstants.percentCompleted, TextConstants.OK);
        testRailDashboardPage.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton();

        testRailLoginPage.signInTestRail(TextConstants.trAdminNOE_Email, TextConstants.trAdmin_Password);
        testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        CommonAction.setStatusOnTestrail(testRailManagerDashboardPage, initialCode, "Passed", TextConstants.percentCompleted, TextConstants.OK);
        testRailDashboardPage.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton();

        testRailLoginPage.signInTestRail(TextConstants.trAdminNOA_Email, TextConstants.trAdmin_Password);
        testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        CommonAction.setStatusOnTestrail(testRailManagerDashboardPage, initialCode, "Passed", TextConstants.percentCompleted, TextConstants.OK);
        testRailDashboardPage.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton();
    }

    //approve for patch 2
    @Test()
    @Order(28)
    public void approvalJudgmentOKPatchSubmission01(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        //approve on noa region
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToJudgmentApprovalQueue("NOA");
        CommonAction.approvalJudgmentStatus(lotcheckQueuePage, initialCode, 100, TextConstants.OK, false, "1");

        WaitUtils.idle(20000);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
        //approve on noe region
        browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN).continueToLogin().loginWithOvdLink(TextConstants.lc_Admin_NOE, TextConstants.lc_Admin_NOE_Password);
        lotcheckLandingPage.navigationToJudgmentApprovalQueue("NOE");
        CommonAction.approvalJudgmentStatus(lotcheckQueuePage, initialCode, 100, TextConstants.OK, false, "1");
        WaitUtils.idle(20000);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
        //approve on ncl region
        browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN).continueToLogin().loginWithOvdLink(slcmsUser);
        lotcheckLandingPage.navigationToJudgmentApprovalQueue("NCL");
        CommonAction.approvalJudgmentStatus(lotcheckQueuePage, initialCode, 100, TextConstants.OK, false, "1");
    }

    //create patch
    @Test()
    @Order(29)
    public void createNewReleasePatchSubmissionTypeSkipLotcheck03(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPCreateReleasePage ndpCreateReleasePage = ndpProductDashboardReleasesTab.clickCreateNewReleaseButton();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpCreateReleasePage
                .enterDisplayVersion("1.0.3")
                .selectExpectedSubmissionDate(10)
                .selectExpectedReleaseDate(20)
                //.selectFreeToPlay("NO")
                .enterDescribeTheFeaturesPlanned(TextConstants.enterDescribeTheFeaturesPlanned)
                .enterDescribeTheBugsIssuesPlanned(TextConstants.enterDescribeTheBugsIssuesPlanned)
                .clickCreateButton();
        //create ROM
        browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        myProductsPage.clickProductByName(productName);
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();
        applicationID = ndpProductDashboardProductInfoTab.getApplicationID();
        gameCode = ndpProductDashboardProductInfoTab.getGameCode();
        productName = ndpProductDashboardProductInfoTab.getProductNameValue();
        String originalApp = "Application_" + applicationID + "_v00.nsp";
        String previousPatch = "Patch_" + applicationID + "_v02.nsp";
        //create ROM
        romBrowser = BrowserManager.getNewInstance();
        ROMLoginPage romLoginPage = romBrowser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN);
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom.reloadPage()
                .selectRequester(TextConstants.test_User)
                .selectType(TextConstants.patch_Release)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .enterProductName(productName)
                .enterApplicationId(applicationID)
                .selectPublishingRelationShip(TextConstants.first_Party_Type)
                .enterDisplayVersion("1.0.3")
                .enterReleaseVersion("03")
                .enterOriginalApp(originalApp)
                .enterPreviousPatch(previousPatch)
                .selectCERORating("CERO: A")
                .selectClassIndRating("ClassInd: 10")
                .selectPEGIRating("PEGI: PEGI 3")
                .selectGRACGCRBRating("GRACGCRB: 12")
                .clickOnCreateButton()
                .waitIsLoaded();
        fileRomName = romCreateRom.getRomNameTable(applicationID);
        romCreateRom.clickOnROMFileOnROMTable(applicationID);
        WaitUtils.idle(5000);
        romCreateRom.clickOnUserNameMenu().clickOnLogoutButton();
        BrowserManager.closeBrowser(romBrowser);
        //create Release Info
        browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        myProductsPage.clickProductByName(productName);
        ndpProductDashboardPage.clickReleases();
        ndpProductDashboardReleasesTab.selectDisplayVersion("1.0.3");
        ndpReleaseInfoPage.nav().clickReleaseInfo();
        CommonAction.selectROMWithSubmissionType(ndpReleaseInfoPage, TextConstants.skipLotcheck, TextConstants.selectRomSameAllRegion, TextConstants.english);
        ndpReleaseInfoPage.nav().clickFeatures();
        //create Feature
        HashMap<ProgramSpecificationsEnum, String> programSpecificationsSettings = FeatureSettingsManager.getProgramSpecificationsSettings(TextConstants.allOff_Demo);
        HashMap<InputDevicesEnum, String> inputDeviceSettings = FeatureSettingsManager.getInputDevicesSettings(TextConstants.allOff);
        HashMap<PlayModesEnum, String> playModesSettings = FeatureSettingsManager.getPlayModesSettings(TextConstants.allOff);
        HashMap<LotcheckRemotePresenceEnum, String> lotcheckSettings = FeatureSettingsManager.getLotcheckPresenceSettings("Confirm");
        HashMap<PatchesEnum, String> patchesSettings = FeatureSettingsManager.getPatchesSettings(TextConstants.allOff);
        HashMap<DemoEnum, String> demoSettings = FeatureSettingsManager.getDemoSettings(TextConstants.allOff);
        NDPReleaseInfoFeaturesPage releaseFeatures = ndpReleaseInfoPage.nav().clickFeatures();
        NDPReleaseInfoFeaturesProgramSpecsDialog progSpecDialog = releaseFeatures.clickEditProgramSpecifications();
        progSpecDialog.setFeatures(programSpecificationsSettings).clickSave();
        //todo: have this in a separate test once we have framework support so this does not affect scenario testing
        // in case of failures.
        NDPReleaseInfoFeaturesInputDevicesDialog inputDeviceDialog = releaseFeatures.clickEditInputDevices();
        inputDeviceDialog.setFeatures(inputDeviceSettings).clickSave();
        NDPReleaseInfoFeaturesPlayModesDialog playModesDialog = releaseFeatures.clickEditPlayModes();
        playModesDialog.setFeatures(playModesSettings).clickSave();
        NDPReleaseInfoFeaturesPatchesDialog patchesDialog = releaseFeatures.clickEditPatches();
        patchesDialog.setFeatures(patchesSettings).clickSave();
        NDPReleaseInfoFeaturesDemoDialog demoDialog = releaseFeatures.clickEditDemo();
        demoDialog.setFeatures(demoSettings).clickSave();
        NDPReleaseInfoFeaturesLotcheckRemotePresenceDialog lotcheckDialog = releaseFeatures.clickEditLotcheckRemotePresence();
        lotcheckDialog.setFeatures(lotcheckSettings).clickSave();
        releaseFeatures.clickContinue();

        //Create GuildLine
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = ndpReleaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoGuidelinesPage
                .selectCannotConformToThisGuideline()
                .selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();
        //Upload ROM
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = ndpReleaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);

        //Delete file Rom in folder
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
        //submit release patch 03
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
        browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser).clickMyProducts();
        myProductsPage.clickProductByName(productName);
        ndpProductDashboardPage.clickReleases();
        ndpProductDashboardReleasesTab.selectDisplayVersion("1.0.3");
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.enterDeveloperComment).clickSubmit();
        WaitUtils.idle(3000);
    }
}
