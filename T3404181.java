package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.junit.TestLogger;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.data.DataGen;
import net.nintendo.automation.ui.data.features.FeatureSettingsManager;
import net.nintendo.automation.ui.data.features.enums.InputDevicesEnum;
import net.nintendo.automation.ui.data.features.enums.LotcheckRemotePresenceEnum;
import net.nintendo.automation.ui.data.features.enums.PatchesEnum;
import net.nintendo.automation.ui.data.features.enums.PlayModesEnum;
import net.nintendo.automation.ui.data.features.enums.ProgramSpecificationsEnum;
import net.nintendo.automation.ui.models.admin.AdminControlPanelPage;
import net.nintendo.automation.ui.models.admin.AdminD4CIntegrationPage;
import net.nintendo.automation.ui.models.admin.AdminDevelopmentHome;
import net.nintendo.automation.ui.models.admin.AdminHomePage;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.d4c.D4CRomPublishSuit;
import net.nintendo.automation.ui.models.d4c.D4CRomTab;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthPage;
import net.nintendo.automation.ui.models.lcms.LotcheckHomePage;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.ui.models.lcms.LotcheckQueuePage;
import net.nintendo.automation.ui.models.lcms.LotcheckTestPlanRow;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionMetadata;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.ndp.NDPCreateProductPage;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPInternalWaiverRequestsTab;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
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

@NDP
@SLCMS
@Tag("T3404181")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3404181 extends CommonBaseTest {
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
    private static CommonUIComponent currentPage;
    private static TestLogger logger;
    private static boolean cardExist;
    private static boolean DLExist;
    private static String fileNameRomInfo;
    private static String fileNameEncryptionROMInfoCard;
    private static String baseReleaseValue;
    private static String romID;
    private static String ronIDAddPhy;

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
    public void submitInitialRelease_Step(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        NDPCreateProductPage createProductPage = myProductsPage.createNewProduct();
        createProductPage.selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(TextConstants.first_Party_Type)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalJapanSalesRegion()
                .selectDigitalAmericasSalesRegion()
                .selectDigitalEuropeSalesRegion()
                .selectDigitalAsiaSalesRegion()
                .selectProductName(productName)
                .selectProductNameKana(TextConstants.productKana)
                .clickCreateButton();
        NDPProductDashboardPage ndpDashboardPage = myProductsPage.clickProductByName(productName);
        CommonAction.issueGameCode(ndpDashboardPage, 60, "NO");
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        myProductsPage.clickProductByName(productName);
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpDashboardPage.clickProductInfo();
        applicationID = ndpProductDashboardProductInfoTab.getApplicationID();
        gameCode = ndpProductDashboardProductInfoTab.getGameCode();
        initialCode = ndpProductDashboardProductInfoTab.getInitialCode();
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        //create release infor
        NDPReleaseInfoPage releaseInfoPage = ndpReleaseInfoPage.nav().clickReleaseInfo();
        CommonAction.selectROMSameAllRegion(releaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);
        //create Feature
        HashMap<ProgramSpecificationsEnum, String> programSpecificationsSettings = FeatureSettingsManager.getProgramSpecificationsSettings(TextConstants.allOff_EU);
        HashMap<InputDevicesEnum, String> inputDeviceSettings = FeatureSettingsManager.getInputDevicesSettings(TextConstants.allOff);
        HashMap<PlayModesEnum, String> playModesSettings = FeatureSettingsManager.getPlayModesSettings(TextConstants.allOff);
        HashMap<LotcheckRemotePresenceEnum, String> lotcheckSettings = FeatureSettingsManager.getLotcheckPresenceSettings("Confirm");
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
        //create ROM
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
        WaitUtils.idle(5000);
        romCreateRom.clickOnUserNameMenu().clickOnLogoutButton();
        BrowserManager.closeBrowser(romBrowser);
        //upload ROM
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = ndpReleaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
        WaitUtils.idle(30000);
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
        browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser).clickMyProducts();
        myProductsPage.clickProductByName(productName);
        ndpDashboardPage.clickReleases();
        ndpProductDashboardReleasesTab.selectInitialRelease();
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.commentSubmission).clickSubmit();
        WaitUtils.idle(3000);
    }

    @Test()
    @Order(2)
    public void setStatusTestPlanOnTestRail_Step(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        //set Passed testcase in testrail for NCL
        TestRailDashboardPage testRailDashboardPage = testRailLoginPage.signInTestRail(testRailUser);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        CommonAction.setStatusOnTestrail(testRailManagerDashboardPage, initialCode, "Passed", TextConstants.percentCompleted, TextConstants.OK);
        testRailDashboardPage.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton();
        WaitUtils.idle(2000);
        //set Passed testcase in testrail for NOE
        testRailLoginPage.signInTestRail(TextConstants.trAdminNOE_Email, TextConstants.trAdmin_Password);
        testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        CommonAction.setStatusOnTestrail(testRailManagerDashboardPage, initialCode, "Passed", TextConstants.percentCompleted, TextConstants.OK);
        testRailDashboardPage.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton();
        WaitUtils.idle(2000);
        //set Passed testcase in testrail for NOA
        testRailLoginPage.signInTestRail(TextConstants.trAdminNOA_Email, TextConstants.trAdmin_Password);
        testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        CommonAction.setStatusOnTestrail(testRailManagerDashboardPage, initialCode, "Passed", TextConstants.percentCompleted, TextConstants.OK);
        testRailDashboardPage.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton();
        WaitUtils.idle(2000);
    }

    @Test()
    @Order(3)
    public void approvalJudgmentOK_Step(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
        //approve on region NCL
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickJudgment(LotcheckRegion.NCL);
        lotcheckLandingPage.navigationToJudgmentApprovalQueue(LotcheckRegion.NCL.toString());
        CommonAction.approvalJudgmentStatusInRegion(lotcheckLandingPage, lotcheckQueuePage, initialCode, 100, TextConstants.OK, LotcheckRegion.NCL.toString());
        WaitUtils.idle(3000);
        browser.openURL(LotcheckLandingPage.class,PageState.POST_LOGIN);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
        //approve on region NOE
        browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN).continueToLogin().loginWithOvdLink(TextConstants.lc_Admin_NOE, TextConstants.lc_Admin_NOE_Password);
        lotcheckLandingPage.navigationToJudgmentApprovalQueue(LotcheckRegion.NOE.toString());
        CommonAction.approvalJudgmentStatusInRegion(lotcheckLandingPage, lotcheckQueuePage, initialCode, 100, TextConstants.OK, LotcheckRegion.NOE.toString());
        WaitUtils.idle(3000);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
        //approve on region NOA
        browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN).continueToLogin().loginWithOvdLink(TextConstants.lc_Admin_NOA, TextConstants.lc_Admin_NOA_Password);
        lotcheckLandingPage.navigationToJudgmentApprovalQueue(LotcheckRegion.NOA.toString());
        CommonAction.approvalJudgmentStatusInRegion(lotcheckLandingPage, lotcheckQueuePage, initialCode, 100, TextConstants.OK, LotcheckRegion.NOA.toString());
        WaitUtils.idle(3000);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
    }

    //create Add Physical
    @Test()
    @Order(4)
    public void createNewReleaseAddPhysical_Step(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPCreateReleasePage ndpCreateReleasePage = ndpProductDashboardReleasesTab.clickCreateNewReleaseButton();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpCreateReleasePage
                .selectReleaseType("Add Physical (Update)")
                .selectExpectedSubmissionDate(5)
                .selectExpectedReleaseDate(20)
                .clickCreateButton();
        ndpReleaseInfoPage.nav().clickReleaseInfo();
        ndpReleaseInfoPage
                .selectRomsThatDifferBetweenRegion(TextConstants.selectRomSameAllRegion)
                .selectLanguageUsedForTextEntry(TextConstants.english)
                .save();
        //Create GuildLine
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = ndpReleaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoGuidelinesPage.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();
        //submit ROM
        currentPage = browser.openURL(D4CRomPublishSuit.class, PageState.PRE_LOGIN);
        D4CRomPublishSuit d4cRom = (D4CRomPublishSuit) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        String idApplication = applicationID.substring(2);
        D4CRomTab d4cRomTab = d4cRom.clickTabRom();
        romID = d4cRomTab.enterApplicationID(idApplication).clickSearchButton().getRomId();
        //d4cRom.clickLogOut();
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
        browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        myProductsPage.clickProductByName(productName);
        ndpProductDashboardPage.clickReleases();
        ndpProductDashboardReleasesTab.selectReleaseType("Add Physical(Update)");
        //Request Issue
        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
        issuePage.openRequiredApprovalTab();
        issuePage.performAllIssueAtApprovalTab(false);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
        //Approval Issue TaskRequested
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(licUser).clickOnMyTaskMenu();
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPInternalWaiverRequestsTab internalWaiverRequestsTab = internalTasksPage.clickOnInternalWaiverRequestsTab();
        internalWaiverRequestsTab.waitingForResultTableLoadedInInternal()
                .enterSearchingBoxInternal(gameCode)
                .clickOnMagnifyingIconButtonInternal()
                .waitingForResultTableLoadedInInternal();
        int numberOfRequestByPendingStatus = internalWaiverRequestsTab.collectNumberOfRequestByPendingStatusInternal();
        System.out.println(numberOfRequestByPendingStatus);
        for (int i = 0; i < numberOfRequestByPendingStatus; i++) {
            developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
            internalTasksPage.clickOnInternalWaiverRequestsTab();
            internalWaiverRequestsTab.waitingForResultTableLoadedInInternal()
                    .enterSearchingBoxInternal(gameCode)
                    .clickOnMagnifyingIconButtonInternal()
                    .waitingForResultTableLoadedInInternal()
                    .openLicensingWaiverRequestByPendingStatusInternal()
                    .selectAssignToMeButton()
                    .selectOKButtonInAssignToMeModal()
                    .selectApproveRequestButton()
                    .selectOKButtonInApproveRequestModal()
                    .waitingForApproveSuccessful();
        }
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
        //submit product
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser).clickMyProducts();
        myProductsPage.clickProductByName(productName);
        ndpProductDashboardPage.clickReleases();
        ndpProductDashboardReleasesTab.selectReleaseType("Add Physical(Update)");
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.commentSubmission).clickSubmit();
        WaitUtils.idle(3000);
    }

    @Test()
    @Order(5)
    public void navigateToSubmissionLevelInSLCMS_Step(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(TextConstants.lc_Admin_NOA, TextConstants.lc_Admin_NOA_Password);
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
        LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview submissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("100");
        cardExist = submissionOverview.matrix().testPlanForExists(TextConstants.card_Type);
        DLExist = submissionOverview.matrix().testPlanForNotExist(TextConstants.dl_Type);
    }

    @Test()
    @Order(6)
    public void confirmOnlyTestPlanForCardExists_Expected(IJUnitTestReporter testReport) throws InterruptedException {
        assertTrue(cardExist, "testplan card exist");
        assertTrue(DLExist, "testplan DL is Not exist");
    }

    @Test()
    @Order(7)
    public void changeStatusToInQueueAndCheckEncryption_Step(IJUnitTestReporter testReport) throws InterruptedException {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickTesterAssignment(LotcheckRegion.NCL);
        int i = 0;
        while (i < 50) {
            lotcheckLandingPage.navigationToTesterAssignmentQueue("NOA").searchByInitialCode(initialCode);
            String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
            i++;
            if (numberValue.equalsIgnoreCase("1")) {
                break;
            }
        }
        LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
        lotcheckTestPlanRow.selectAssignmentComplete().clickConfirm();
        lotcheckLandingPage.navigationToTestSetupReadyQueue("NOA");
        lotcheckQueuePage.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        String encryptionStatus = lotcheckQueuePage.getEncryptionStatus();
        String judgmentStatus = lotcheckQueuePage.getTestPlanStatus();
        assertAll(
                () -> assertEquals("Prod Encryption Complete", encryptionStatus, "Encryption status is Prod Encryption Complete"),
                () -> assertEquals("In Queue", judgmentStatus, "Judgment Status is In Queue")
        );

    }

    @Test()
    @Order(8)
    public void openMetadataTabOfTheLotCheckReport_Step(IJUnitTestReporter testReport) throws InterruptedException {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToTestSetupReadyQueue(LotcheckRegion.NOA.toString());
        LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview submissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("100");
        LotcheckReportSubmissionMetadata lotcheckReportSubmissionMetadata = submissionOverview.navBar().clickMetadata();
        int i = 0;
        while (i <= 50) {
            lotcheckQueuePage.refreshPageSLCMS();
            WaitUtils.idle(10000);
            int lengthValue = lotcheckReportSubmissionMetadata.getFileNameAtRomInfo().length();
            System.out.println(lengthValue);
            if (lengthValue != 0) {
                break;
            }
            i++;
        }
        fileNameRomInfo = lotcheckReportSubmissionMetadata.getFileNameAtRomInfo();
        fileNameEncryptionROMInfoCard = lotcheckReportSubmissionMetadata.getFileNameAtEncryptionROMInfoCard();
    }

    @Test()
    @Order(9)
    public void verifyThatBothFileNames_Expected(IJUnitTestReporter testReport) {
        assertAll(
                () -> assertEquals("HAC-P-" + initialCode + "_00-100_" + romID + ".nsp", fileNameRomInfo, "file name in Rom Info"),
                () -> assertEquals("HAC-P-" + initialCode + "_00-100_" + romID + "_prod.xcie", fileNameEncryptionROMInfoCard, "file name in Encryption ROM Info Card")
        );
    }

    @Test()
    @Order(10)
    public void setStatusTestPlanOnTestRailForAddPhysical_Step(IJUnitTestReporter testReport) throws InterruptedException {
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

    @Test()
    @Order(11)
    public void approvalJudgmentOKForAddPhysical_Step(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        //approve on region NOA
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToJudgmentApprovalQueue(LotcheckRegion.NOA.toString());
        CommonAction.approvalJudgmentStatusInRegion(lotcheckLandingPage, lotcheckQueuePage, initialCode, 100, TextConstants.OK, LotcheckRegion.NOA.toString());
        browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
        //approve on region NOE
        browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN).continueToLogin().loginWithOvdLink(TextConstants.lc_Admin_NOE, TextConstants.lc_Admin_NOE_Password);
        lotcheckLandingPage.navigationToJudgmentApprovalQueue(LotcheckRegion.NOE.toString());
        CommonAction.approvalJudgmentStatusInRegion(lotcheckLandingPage, lotcheckQueuePage, initialCode, 100, TextConstants.OK, LotcheckRegion.NOE.toString());
        browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
        //approve on region NCL
        browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN).continueToLogin().loginWithOvdLink(slcmsUser);
        lotcheckLandingPage.navigationToJudgmentApprovalQueue(LotcheckRegion.NCL.toString());
        CommonAction.approvalJudgmentStatusInRegion(lotcheckLandingPage, lotcheckQueuePage, initialCode, 100, TextConstants.OK, LotcheckRegion.NCL.toString());
    }

    @Test
    @Order(12)
    public void settingUppUpdate_Step(IJUnitTestReporter testReport) throws InterruptedException {
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
        ndpSettingTab.selectUppUpdate()
                .clickSaveButton();
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
    }

    @Test()
    @Order(13)
    public void createNewReleaseUppUpdate_Step(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser).clickMyProducts();
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPCreateReleasePage ndpCreateReleasePage = ndpProductDashboardReleasesTab.clickCreateNewReleaseButton();
        ndpCreateReleasePage
                .selectReleaseType(TextConstants.upp_Update_Release);
        baseReleaseValue = ndpCreateReleasePage.getBaseReleaseToUpdate();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpCreateReleasePage.selectExpectedSubmissionDate(5)
                .selectExpectedReleaseDate(20)
                .clickCreateButton();
        ndpReleaseInfoPage.nav().clickReleaseInfo();
    }

    @Test()
    @Order(14)
    public void confirmBaseReleaseToUpdateIsAddPhysical_Expected(IJUnitTestReporter testReport) throws Exception {
        assertEquals("00 (Add Physical (Update))", baseReleaseValue);
    }

    //create patch
    @Test()
    @Order(15)
    public void createNewReleasePatchSubmissionTypeLotCheck_Step(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPCreateReleasePage ndpCreateReleasePage = ndpProductDashboardReleasesTab.clickCreateNewReleaseButton();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpCreateReleasePage
                .enterDisplayVersion("1.0.1")
                .selectExpectedSubmissionDate(10)
                .selectExpectedReleaseDate(20)
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
        romCreateRom.clickOnROMFileOnROMTable(applicationID);
        WaitUtils.idle(5000);
        romCreateRom.clickOnUserNameMenu().clickOnLogoutButton();
        BrowserManager.closeBrowser(romBrowser);
        //create Release Info
        browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        myProductsPage.clickProductByName(productName);
        ndpProductDashboardPage.clickReleases();
        ndpProductDashboardReleasesTab.selectDisplayVersion("1.0.1");
        ndpReleaseInfoPage.nav().clickReleaseInfo();
        ndpReleaseInfoPage
                .selectSubmissionType(TextConstants.lotcheck)
                .selectRomsThatDifferBetweenRegion(TextConstants.selectRomSameAllRegion)
                .selectLanguageUsedForTextEntry(TextConstants.english)
                .save();
        ndpReleaseInfoPage.nav().clickFeatures();
        //create Feature
        HashMap<ProgramSpecificationsEnum, String> programSpecificationsSettings = FeatureSettingsManager.getProgramSpecificationsSettings(TextConstants.allOff_EU);
        HashMap<InputDevicesEnum, String> inputDeviceSettings = FeatureSettingsManager.getInputDevicesSettings(TextConstants.allOff);
        HashMap<PlayModesEnum, String> playModesSettings = FeatureSettingsManager.getPlayModesSettings(TextConstants.allOff);
        HashMap<LotcheckRemotePresenceEnum, String> lotcheckSettings = FeatureSettingsManager.getLotcheckPresenceSettings("Confirm");
        HashMap<PatchesEnum, String> patchesSettings = FeatureSettingsManager.getPatchesSettings(TextConstants.allOff);
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
        ndpProductDashboardReleasesTab.selectDisplayVersion("1.0.1");
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.commentSubmission).clickSubmit();
        WaitUtils.idle(3000);
    }

    @Test()
    @Order(16)
    public void setStatusTestPlanOnTestRailPatchSubmission01_Step(IJUnitTestReporter testReport) throws InterruptedException {
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

    @Test()
    @Order(17)
    public void approvalJudgmentOKPatchSubmission01_Step(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        //approve on region ncl
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToJudgmentApprovalQueue(LotcheckRegion.NCL.toString());
        CommonAction.approvalJudgmentStatusInRegion(lotcheckLandingPage, lotcheckQueuePage, initialCode, 100, TextConstants.OK, LotcheckRegion.NCL.toString());
        browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
        //approve on region noe
        browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN).continueToLogin().loginWithOvdLink(TextConstants.lc_Admin_NOE, TextConstants.lc_Admin_NOE_Password);
        lotcheckLandingPage.navigationToJudgmentApprovalQueue(LotcheckRegion.NOE.toString());
        CommonAction.approvalJudgmentStatusInRegion(lotcheckLandingPage, lotcheckQueuePage, initialCode, 100, TextConstants.OK, LotcheckRegion.NOE.toString());
        browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
        //approve on region noa
        browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN).continueToLogin().loginWithOvdLink(TextConstants.lc_Admin_NOA, TextConstants.lc_Admin_NOA_Password);
        lotcheckLandingPage.navigationToJudgmentApprovalQueue(LotcheckRegion.NOA.toString());
        CommonAction.approvalJudgmentStatusInRegion(lotcheckLandingPage, lotcheckQueuePage, initialCode, 100, TextConstants.OK, LotcheckRegion.NOA.toString());
    }

    //create Add Physical
    @Test()
    @Order(18)
    public void createAndSubmitNewReleaseAddPhysicalBasePatch_Step(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPCreateReleasePage ndpCreateReleasePage = ndpProductDashboardReleasesTab.clickCreateNewReleaseButton();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpCreateReleasePage
                .selectReleaseType(TextConstants.add_Physical_Update)
                .selectExpectedSubmissionDate(5)
                .selectExpectedReleaseDate(20)
                .clickCreateButton();
        ndpReleaseInfoPage.nav().clickReleaseInfo();
        CommonAction.selectROMSameAllRegion(ndpReleaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);
        //Create GuildLine
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = ndpReleaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoGuidelinesPage.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();
        //submit ROM in D4C
        currentPage = browser.openURL(D4CRomPublishSuit.class, PageState.PRE_LOGIN);
        D4CRomPublishSuit d4cRom = (D4CRomPublishSuit) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        String idApplication = applicationID.substring(2);
        D4CRomTab d4cRomTab = d4cRom.clickTabRom();
        ronIDAddPhy = d4cRomTab.enterApplicationID(idApplication).clickSearchButton().getRomId();
        WaitUtils.idle(4000);
        currentPage = browser.openURL(AdminHomePage.class, PageState.PRE_LOGIN);
        AdminHomePage adminHomePage = (AdminHomePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        AdminDevelopmentHome adminDevelopmentHome = adminHomePage.clickSignInPage().signIn(TextConstants.omniAdmin, TextConstants.omniAdmin_Password);
        AdminControlPanelPage adminControlPanelPage = adminDevelopmentHome.clickMenuAdmin().clickControlPanel();
        AdminD4CIntegrationPage adminD4CIntegrationPage = adminControlPanelPage.clickD4CIntegrationAdminButton();
        adminD4CIntegrationPage.submitROMCheckResults(ronIDAddPhy);
        browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        myProductsPage.clickProductByName(productName);
        ndpProductDashboardPage.clickReleases();
        ndpProductDashboardReleasesTab.selectRelease(TextConstants.add_Physical_Update_Release, "1.0.1");
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
        //submit product
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser).clickMyProducts();
        myProductsPage.clickProductByName(productName);
        ndpProductDashboardPage.clickReleases();
        ndpProductDashboardReleasesTab.selectRelease(TextConstants.add_Physical_Update_Release, "1.0.1");
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.commentSubmission).clickSubmit();
        WaitUtils.idle(3000);
    }

    @Test()
    @Order(19)
    public void navigateToSubmissionLevelInSLCMSCheckTestPlanCard_Step(IJUnitTestReporter testReport) throws InterruptedException {
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
        LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview submissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("100");
        cardExist = submissionOverview.matrix().testPlanForExists(TextConstants.card_Type);
        DLExist = submissionOverview.matrix().testPlanForNotExist(TextConstants.dl_Type);
    }

    @Test()
    @Order(20)
    public void confirmOnlyTestPlanForCardExistsForAddPhysical_Expected(IJUnitTestReporter testReport) throws InterruptedException {
        assertTrue(cardExist, "testplan card exist");
        assertTrue(DLExist, "testplan DL is Not exist");
    }

    @Test()
    @Order(21)
    public void checkEncryptionStatus_Step(IJUnitTestReporter testReport) throws InterruptedException {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickTesterAssignment(LotcheckRegion.NCL);
        int i = 0;
        while (i < 50) {
            lotcheckLandingPage.navigationToTesterAssignmentQueue(LotcheckRegion.NOA.toString()).searchByInitialCode(initialCode);
            String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
            i++;
            if (numberValue.equalsIgnoreCase("1")) {
                break;
            }
        }
        LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
        String encryptionStatus = lotcheckQueuePage.getEncryptionStatus();
        assertEquals("Prod Encryption Complete", encryptionStatus, "Encryption status is Prod Encryption Complete");
    }

    @Test()
    @Order(22)
    public void openMetadataTabInLotCheckReportSubmission_Step(IJUnitTestReporter testReport) throws InterruptedException {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToTesterAssignmentQueue(LotcheckRegion.NOA.toString());
        LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview submissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("100");
        LotcheckReportSubmissionMetadata lotcheckReportSubmissionMetadata = submissionOverview.navBar().clickMetadata();
        int i = 0;
        while (i <= 50) {
            lotcheckQueuePage.refreshPageSLCMS();
            WaitUtils.idle(10000);
            int lengthValueRomName = lotcheckReportSubmissionMetadata.getFileNameAtRomInfo().length();
            System.out.println(lengthValueRomName);
            if (lengthValueRomName != 0) {
                break;
            }
            i++;
        }
        fileNameRomInfo = lotcheckReportSubmissionMetadata.getFileNameAtRomInfo();
        fileNameEncryptionROMInfoCard = lotcheckReportSubmissionMetadata.getFileNameAtEncryptionROMInfoCard();
    }

    @Test()
    @Order(23)
    public void verifyThatBothFileNamesInMetadataExpected(IJUnitTestReporter testReport) {
        assertAll(
                () -> assertEquals("HAC-U-" + initialCode + "_01-100_" + ronIDAddPhy + ".nsp", fileNameRomInfo, "file name in Rom Info"),
                () -> assertEquals("HAC-P-" + initialCode + "_01-100_" + ronIDAddPhy + "_prod.xcie", fileNameEncryptionROMInfoCard, "file name in Encryption ROM Info Card")
        );
    }

}
