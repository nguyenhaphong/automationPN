package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.junit.TestLogger;
import net.nintendo.automation.junit.extensions.ScenarioTest;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.data.DataGen;
import net.nintendo.automation.ui.data.features.FeatureSettingsManager;
import net.nintendo.automation.ui.data.features.enums.DemoEnum;
import net.nintendo.automation.ui.data.features.enums.InputDevicesEnum;
import net.nintendo.automation.ui.data.features.enums.LotcheckRemotePresenceEnum;
import net.nintendo.automation.ui.data.features.enums.PlayModesEnum;
import net.nintendo.automation.ui.data.features.enums.ProgramSpecificationsEnum;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthPage;
import net.nintendo.automation.ui.models.lcms.LotcheckEditAccountPreferences;
import net.nintendo.automation.ui.models.lcms.LotcheckHomePage;
import net.nintendo.automation.ui.models.lcms.LotcheckIssueTranslationTasksPage;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.ui.models.lcms.LotcheckQueuePage;
import net.nintendo.automation.ui.models.lcms.LotcheckTestPlanRow;
import net.nintendo.automation.ui.models.lcms.lotcheck_issue_translation.LotcheckIssueTranslationIssueDetails;
import net.nintendo.automation.ui.models.lcms.lotcheck_issue_translation.LotcheckIssueTranslationTaskDetails;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckChangeSubmissionJudgment;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckEditDetailsDialog;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssueDetails;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssueThread;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssues;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionProductInformation;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckResetIssueDialog;
import net.nintendo.automation.ui.models.ndp.NDPCreateProductPage;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPLicensingWaiverRequestsTab;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.NDPRequestGameCodePage;
import net.nintendo.automation.ui.models.ndp.NDPSignInPage;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_internal.NDPInternalPage;
import net.nintendo.automation.ui.models.ndp.existing_internal.NDPProductSearchPage;
import net.nintendo.automation.ui.models.ndp.existing_internal.view_product_dashboard.NDPSettingTab;
import net.nintendo.automation.ui.models.ndp.existing_internal.view_product_dashboard.NDPViewProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardProductInfoTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardReleasesTab;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoAgeRating;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoFeaturesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoGuidelinesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoIssuePage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoROMUpload;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPReleaseInfoFeaturesDemoDialog;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPReleaseInfoFeaturesInputDevicesDialog;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPReleaseInfoFeaturesLotcheckRemotePresenceDialog;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPReleaseInfoFeaturesPlayModesDialog;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPReleaseInfoFeaturesProgramSpecsDialog;
import net.nintendo.automation.ui.models.rom.ROMCreateRom;
import net.nintendo.automation.ui.models.rom.ROMLoginPage;
import net.nintendo.automation.ui.models.testrail.TestRailDashboardPage;
import net.nintendo.automation.ui.models.testrail.TestRailLoginPage;
import net.nintendo.automation.ui.models.testrail.TestRailManagerDashboardPage;
import net.nintendo.automation.ui.models.testrail.TestRailSubmitBugDialog;
import net.nintendo.automation.ui.users.User;
import net.nintendo.automation.ui.users.UserManager;
import net.nintendo.automation.utils.WaitUtils;
import net.nintendo.automation_core.common_client.enums.ServiceType;
import net.nintendo.automation_core.common_client.enums.UserType;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classname: T3402517
 * Version: 1.0.0
 * Purpose:
 */
@Tag("T3402517")
@ScenarioTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3402517 extends CommonBaseTest {
    private static Browser browser;
    private static User romUser;
    private static User ndpUser;
    private static User slcmsUser;
    private static User testrailUser;
    private static User licUser;
    private static Browser romBrowser;
    private static CommonUIComponent currentPage;
    private static TestLogger logger;

    private static String productName;
    private static String applicationID;
    private static String initialCode;
    private static String gameCode;
    private static String fileRomName;
    private static List<String> valueOption = new ArrayList<>();
    String productBase = "Selenium_TSYM (DK85A)";
    List<String> valueDemoType = Arrays.asList("Please Select, eShop Only, RID Kiosk and eShop, RID Kiosk Only");
    private static String demoTypeValue;
    private static boolean releaseVersions;
    private static boolean submissionVersions;
    private static boolean unlockAppInfoSheet;
    private static boolean recordIsDisplay;
    private static String titleIssue;
    private static String descriptionIssue;
    private static boolean iconPrivate;
    private static String titleIssueInDetail;
    private static String descriptionIssueInDetail;
    private static String visibleToValue;
    private static boolean visibleToValueNotLink;
    private static String titleThreadIssue;
    private static String backgroundColor;
    private static String publishedDescriptionTitle;
    private static String publishedDescriptionEN;
    private static String publishedDescriptionJP;
    private static boolean recordIsDisplay1;
    private static String titleIssue1;
    private static String descriptionIssue1;
    private static boolean iconPrivate1;
    private static String titleIssueInDetail1;
    private static String descriptionIssueInDetail1;
    private static String visibleToValue1;
    private static boolean recordIsDisplay2;
    private static String titleIssue2;
    private static String descriptionIssue2;
    private static boolean iconPrivate2;
    private static String titleIssueInDetail2;
    private static String descriptionIssueInDetail2;
    private static String visibleToValue2;
    private static boolean recordIsDisplay3;
    private static String titleIssue3;
    private static String descriptionIssue3;
    private static boolean iconPrivate3;
    private static String titleIssueInDetail3;
    private static String descriptionIssueInDetail3;
    private static String visibleToValue3;
    private static boolean recordIsDisplay4;
    private static String titleIssue4;
    private static String descriptionIssue4;
    private static boolean iconPrivate4;
    private static String titleIssueInDetail4;
    private static String descriptionIssueInDetail4;
    private static String visibleToValue4;
    private static boolean recordIsDisplay5;
    private static String titleIssue5;
    private static String descriptionIssue5;
    private static boolean iconPrivate5;
    private static String titleIssueInDetail5;
    private static String descriptionIssueInDetail5;
    private static String visibleToValue5;
    private static boolean recordIsDisplay6;
    private static String titleIssue6;
    private static String descriptionIssue6;
    private static boolean iconPrivate6;
    private static String titleIssueInDetail6;
    private static String descriptionIssueInDetail6;
    private static String visibleToValue6;
    //private static String productName = "Selenium_nqFc";

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_Trang);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
        slcmsUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);
        testrailUser = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER);
        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);

        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
        NDPHomePage ndpHomePage = (NDPHomePage) currentPage;
        ndpHomePage.acceptCookie();
        NDPSignInPage signInPage = ndpHomePage.clickSignInPage();
        signInPage.signIn(ndpUser);
        productName = DataGen.getRandomProductName();
    }

    @AfterAll
    static void terminateBrowser() {
        //BrowserManager.closeBrowser(browser);
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

    public void createRomUpload(IJUnitTestReporter testReport){
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
                .selectPublishingRelationShip(TextConstants.third_Party_Type)
                .enterDisplayVersion("1.0.0")
                .enterReleaseVersion("00")
                .selectClassIndRating("ClassInd: 10")
                .clickOnCreateButton()
                .waitIsLoaded();
        fileRomName = romCreateRom.getRomNameTable(applicationID);
        romCreateRom.clickOnROMFileOnROMTable(applicationID);
        WaitUtils.idle(20000);
        BrowserManager.closeBrowser(romBrowser);
    }

    @Test()
    @Order(1)
    public void createProductAndIssueGameCodeStep(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage)currentPage;
        NDPCreateProductPage createProductPage = myProductsPage.createNewProduct();
        createProductPage.selectProductType(TextConstants.demo_Product)
                .selectPublishingRelationship(TextConstants.third_Party_Type)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalAmericasSalesRegion()
                .selectProductName(productName)
                .selectBasedProduct(productBase)
                .clickCreateButton();
        NDPProductDashboardPage ndpDashboardPage = myProductsPage.clickProductByName(productName);
        NDPRequestGameCodePage requestGameCodePage = ndpDashboardPage.issueGameCode();
        NDPProductDashboardPage ndpProductDashboardPage = requestGameCodePage.setExpectedLotcheckSubmissionDate()
                .setExpectedReleaseDate(45)
                .clickIssue();
        createRomUpload(testReport);
    }

    @Test
    @Order(2)
    public void LoginToNDPWithLicensingPowerUserRole_Step(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage)currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn("prdcv_lic_pwr_user_ncl", "nintendo");
        currentPage = developmentHome;
    }

    @Test
    @Order(3)
    public void NavigateToINTERNAL_ProductSearch_Step(IJUnitTestReporter testReport) throws InterruptedException {
        NDPDevelopmentHome developmentHome = (NDPDevelopmentHome) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPInternalPage internalPage = developmentHome.clickInternalTab();
        NDPProductSearchPage ndpProductSearchPage = internalPage.clickProductSearchButton();
        currentPage =ndpProductSearchPage;
    }
    @Test
    @Order(4)
    public void SelectTheProductCreatedOnStep1_Step(IJUnitTestReporter testReport) throws InterruptedException {
        NDPProductSearchPage ndpProductSearchPage = (NDPProductSearchPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPViewProductDashboardPage ndpViewProductDashboardPage = ndpProductSearchPage.waitingLoadingValueSearch().clickSearchContent(initialCode)
                .waitingLoadingValueSearchTable2()
                .selectElementFromDropDownTable2();
        currentPage = ndpViewProductDashboardPage;
    }
    @Test
    @Order(5)
    public void NavigateToSettingsTab_Step(IJUnitTestReporter testReport) throws InterruptedException {
        NDPViewProductDashboardPage ndpViewProductDashboardPage = (NDPViewProductDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPSettingTab ndpSettingTab = ndpViewProductDashboardPage.clickSettingTab();
        currentPage = ndpSettingTab;
    }
    @Test
    @Order(6)
    public void OnKioskItemCheckOffTheCheckboxAllowRIDKioskSettingTheSaveTheSelection_Step(IJUnitTestReporter testReport) throws InterruptedException {
        NDPSettingTab ndpSettingTab = (NDPSettingTab) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpSettingTab.selectAllowRIDKioskSetting()
                .clickSaveButton();
        NDPMyProductsPage myProductsPage = ndpSettingTab.clickMyProduct();
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
    }

    @Test()
    @Order(7)
    public void LoginToNDPAgainWithProductAdminRole_Step(IJUnitTestReporter testReport) throws Exception {
        NDPDevelopmentHome ndpDevelopmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser);
        currentPage = ndpDevelopmentHome;
    }
    @Test()
    @Order(8)
    public void NavigateToMyProductsPage_Step(IJUnitTestReporter testReport) throws Exception {
        NDPDevelopmentHome ndpDevelopmentHome = (NDPDevelopmentHome) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPMyProductsPage ndpMyProductsPage = ndpDevelopmentHome.clickMyProducts();
        currentPage=ndpMyProductsPage;
    }
    @Test()
    @Order(9)
    public void SelectTheProductCreatedOnStep1_Step9(IJUnitTestReporter testReport) throws Exception {
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        currentPage=ndpProductDashboardPage;
    }
    @Test()
    @Order(10)
    public void NavigateToReleaseInfoPage_Step(IJUnitTestReporter testReport) throws Exception {
        NDPProductDashboardPage ndpProductDashboardPage = (NDPProductDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        currentPage = ndpReleaseInfoPage;
    }
    @Test()
    @Order(11)
    public void CheckTheRIDKioskDownloadSupportField_Step(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        //create release infor
        ndpReleaseInfoPage.nav().clickReleaseInfo();
        currentPage= ndpReleaseInfoPage;
        int number = ndpReleaseInfoPage.getNumberValueOptionInDemoTypeField();
        for (int i = 0; i < number; i++) {
            valueOption.add(ndpReleaseInfoPage.getValueOptionInDemoTypeField(i));
        }
    }
    @Test()
    @Order(12)
    public void RIDKioskDownloadSupportWillBeModified_Expected(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentPage;
        valueDemoType.equals(valueOption);
    }
    @Test()
    @Order(13)
    public void SelectRIDKioskAndEShop_Submit_Step(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        //WaitUtils.idle(20000);
        ndpReleaseInfoPage.selectCardSize("2 GB")
                .selectDemoType(" RID Kiosk and eShop")
                .selectRomsThatDifferBetweenRegion("Uses the same ROM across all regions")
                .selectLanguageUsedForTextEntry("English")
                .save();
        currentPage = ndpReleaseInfoPage;
    }
    @Test()
    @Order(14)
    public void FillOutAllOthersPageThenSubmitProductToLotcheck_Step(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentPage;
        ndpReleaseInfoPage.nav().clickFeatures();
        //create Feature
        HashMap<ProgramSpecificationsEnum, String> programSpecificationsSettings = FeatureSettingsManager.getProgramSpecificationsSettings("AllDemoAmericas");
        HashMap<InputDevicesEnum, String> inputDeviceSettings = FeatureSettingsManager.getInputDevicesSettings("AllOff");
        HashMap<PlayModesEnum, String> playModesSettings = FeatureSettingsManager.getPlayModesSettings("AllOff");
        HashMap<LotcheckRemotePresenceEnum, String> lotcheckSettings = FeatureSettingsManager.getLotcheckPresenceSettings("Confirm");
        HashMap<DemoEnum, String> demoSettings = FeatureSettingsManager.getDemoSettings("AllOff");
        NDPReleaseInfoFeaturesPage releaseFeatures = ndpReleaseInfoPage.nav().clickFeatures();
        //WaitUtils.idle(1000);
        NDPReleaseInfoFeaturesProgramSpecsDialog progSpecDialog = releaseFeatures.clickEditProgramSpecifications();
        progSpecDialog.setFeatures(programSpecificationsSettings).clickSave();
        //todo: have this in a separate test once we have framework support so this does not affect scenario testing
        // in case of failures.
        assertTrue(releaseFeatures.isProgramSpecificationsComplete());
        //WaitUtils.idle(1000);
        NDPReleaseInfoFeaturesInputDevicesDialog inputDeviceDialog = releaseFeatures.clickEditInputDevices();
        inputDeviceDialog.setFeatures(inputDeviceSettings).clickSave();
        //WaitUtils.idle(1000);
        NDPReleaseInfoFeaturesPlayModesDialog playModesDialog = releaseFeatures.clickEditPlayModes();
        playModesDialog.setFeatures(playModesSettings).clickSave();
        //WaitUtils.idle(1000);
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
                .selectStandardAgeRating("ClassInd")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectRatingValue("10")
                .uploadCertificateAttachment(new File("src/test/resources/DataFile/Test_a.xlsx"))
                .clickOnSaveButtonInEditStandardRating();
        //upload ROM
        //createRomUpload(testReport);
        // WaitUtils.idle(20000);
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = ndpReleaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
//        //Request Issue
        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
        //WaitUtils.idle(20000);
        issuePage.openRequiredApprovalTab();
        issuePage.performAllIssueAtApprovalTab(false);
        NDPMyProductsPage myProductsPage = ndpReleaseInfoPage.navigateToProduct();
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
        //Approval Issue TaskRequested
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn("prdcv_lic_approval_coord_ncl","nintendo").clickOnMyTaskMenu();
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, gameCode);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser).clickMyProducts();
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        ndpProductDashboardReleasesTab.selectInitialRelease();
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment("Comment submission").clickSubmit();
    }
    @Test()
    @Order(15)
    public void LoginToSLCMSWithLotcheckAdminRole_Step(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
        currentPage = lotcheckLandingPage;
    }
    @Test()
    @Order(16)
    public void NavigateToOverallLotcheckQueue_Step(IJUnitTestReporter testReport) throws InterruptedException {
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToOverallLotcheckQueue("NCL");
        currentPage = lotcheckLandingPage;
    }
    @Test()
    @Order(17)
    public void SelectTheProductSubmittedOnStep12_Step(IJUnitTestReporter testReport) throws InterruptedException {
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToOverallLotcheckQueue("NCL");
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        int i = 0;
        while (i < 40) {
            lotcheckLandingPage.navigationToOverallLotcheckQueue("NCL").searchByInitialCode(initialCode);
            String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
            i++;
            if (numberValue.equalsIgnoreCase("1")) {
                break;
            }
        }
        lotcheckQueuePage.clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        currentPage= lotcheckReportSubmissionOverview;
    }
    @Test()
    @Order(18)
    public void NavigateToLCRSubmissionProductInformationTabReleaseInformationOfThisProduct_Step(IJUnitTestReporter testReport) throws InterruptedException {
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = (LotcheckReportSubmissionOverview) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckReportSubmissionProductInformation productInformation = lotcheckReportSubmissionOverview.navBar().clickProductInformation();
        currentPage = productInformation;
        productInformation.expandReleaseInformation();
        demoTypeValue = productInformation.getDemoType();
    }
    @Test()
    @Order(19)
    public void checkDemoTypeInProductInfo_Expected(IJUnitTestReporter testReport) throws InterruptedException {
        LotcheckReportSubmissionProductInformation productInformation = (LotcheckReportSubmissionProductInformation) currentPage;
        assertEquals("RID Kiosk and eShop", demoTypeValue, "Check value demo type");
    }
    @Test()
    @Order(20)
    public void submitBugInTesTRail_Step(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailDashboardPage testRailDashboardPage = testRailLoginPage.signInTestRail("tradmin@test.ncl.nintendo.com", "password");
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(initialCode)
                .selectStatusUntested();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun();
        TestRailSubmitBugDialog submitBugDialog = testRailManagerDashboardPage.clickOnTheFirstTestcase().clickSubmitBug();
        submitBugDialog
                .enterDescription(TextConstants.inputDescription)
                .enterTitle(TextConstants.inputTile)
                .checkNeedsTranslation()
                .clickSubmit();
        submitBugDialog.clickOk().clickClose();
        testRailDashboardPage.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton();
    }
    @Test()
    @Order(21)
    public void checkFieldsAreNotExistingAtTheBottomOfTheThreadTile_Step(IJUnitTestReporter testReport) {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToTesterAssignmentQueue("NCL");
        int i=0;
        while (i<50){
            lotcheckLandingPage.navigationToTesterAssignmentQueue(("NCL")).searchByInitialCode(initialCode);
            String numberValue =lotcheckQueuePage.numberTestPlanDisplay();
            i++;
            if(numberValue.equalsIgnoreCase("1")) {
                break;
            }
        }
        LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview submissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        LotcheckReportSubmissionLotcheckIssues submissionLotcheckIssues = submissionOverview.navBar().clickLotcheckIssues();
        submissionLotcheckIssues.refreshPage();
        currentPage = submissionLotcheckIssues;
        releaseVersions = submissionLotcheckIssues.displayFieldOnBottomOfTheThreadTile("Release Versions");
        submissionVersions = submissionLotcheckIssues.displayFieldOnBottomOfTheThreadTile("Submission Versions");
        unlockAppInfoSheet = submissionLotcheckIssues.displayFieldOnBottomOfTheThreadTile("Unlock App Info Sheet");
    }
    @Test()
    @Order(22)
    public void confirmFieldsAreNotExistingAtTheBottomOfTheThreadTile_Expected(IJUnitTestReporter testReport) {
        assertAll(
                ()-> assertFalse(releaseVersions,"Release Versions field is Not exist"),
                ()-> assertFalse(submissionVersions, "Submission Versions field is Not exist"),
                ()-> assertFalse(unlockAppInfoSheet, "Unlock App Info Sheet field is Not exist")
        );
    }
    @Test()
    @Order(23)
    public void bugSubmitIsDisplayInCurrentSubmissionIssue(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        recordIsDisplay= reportSubmissionLotcheckIssues.recordIsDisplayInCurrentSubmissionIssue();
        assertAll(
                ()->assertTrue(recordIsDisplay, "bug submit is display In current Submission Issue")
        );
    }
    @Test()
    @Order(24)
    public void ForCurrentSubmissionVerifyTitleAndDescriptionAtLotcheckIssueTab_Step(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        reportSubmissionLotcheckIssues.expandCollapsedIssue();
        iconPrivate = reportSubmissionLotcheckIssues.iconPrivateIsDisplay();
        titleIssue = reportSubmissionLotcheckIssues.getTitle();
        descriptionIssue = reportSubmissionLotcheckIssues.getDescription();
    }
    @Test()
    @Order(25)
    public void checkTitleLotcheckIssue_Expected(IJUnitTestReporter testReport) {
        assertAll(
                ()-> assertTrue(iconPrivate,"icon private"),
                ()-> assertEquals(TextConstants.inputDescription,descriptionIssue, "verify description"),
                ()-> assertEquals(TextConstants.inputTile, titleIssue, "verify title")
        );
    }
    @Test()
    @Order(26)
    public void ForCurrentSubmissionNavigateToViewDetailIssueCheckTitleAndDescription_Step(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails = reportSubmissionLotcheckIssues.clickMenuOption()
                .selectViewIssueDetails();
        titleIssueInDetail = submissionLotcheckIssueDetails.getTitleIssueValue();
        descriptionIssueInDetail = submissionLotcheckIssueDetails.getDescriptionIssueValue();
    }
    @Test()
    @Order(27)
    public void checkTitleDescriptionInDetailLotcheckIssue_Expected(IJUnitTestReporter testReport) {
        assertAll(
                ()-> assertEquals(TextConstants.inputDescription,descriptionIssueInDetail, "verify description"),
                ()-> assertEquals(TextConstants.inputTile, titleIssueInDetail, "verify title in View Detail")
        );
    }
    @Test()
    @Order(28)
    public void checkVisibleToFiledLotcheckIssue_Step(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        reportSubmissionLotcheckIssues.navBar().clickLotcheckIssues();
        visibleToValue = reportSubmissionLotcheckIssues.getValueFieldOnBottomOfTheThreadTile("Visible To:").substring(12);
    }
    @Test()
    @Order(29)
    public void checkVisibleToFiledLotcheckIssue_Expected(IJUnitTestReporter testReport) {
        assertAll(
                ()-> assertEquals(visibleToValue,"Lotcheck", "verify value is Lotcheck"),
                ()-> assertNotEquals(visibleToValue, "Internal", "verify value is never displayed")
        );
    }

    @Test()
    @Order(30)
    public void checkVisibleToFieldThatAreNotClickableLinks_Step(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        reportSubmissionLotcheckIssues.navBar().clickLotcheckIssues();
        visibleToValueNotLink = reportSubmissionLotcheckIssues.getElementAreNotClickableLinks();
    }
    @Test()
    @Order(31)
    public void checkVisibleToFieldThatAreNotClickableLinks_Expected(IJUnitTestReporter testReport) {
        assertAll(
                ()-> assertTrue(visibleToValueNotLink,"Confirm for Visible to field: That are not clickable links")
        );
    }
    @Test()
    @Order(32)
    public void ViewLotcheckThread_Step(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckReportSubmissionLotcheckIssueThread lotcheckReportSubmissionLotcheckIssueThread = reportSubmissionLotcheckIssues.clickMenuOption().selectViewLotcheckThread();
        titleThreadIssue = lotcheckReportSubmissionLotcheckIssueThread.getTitleLotcheckThread();
        backgroundColor = reportSubmissionLotcheckIssues.getBackgroundLotcheckThread();
    }
    @Test()
    @Order(33)
    public void navigateToLotcheckThreadAndBackgroundColor_Expected(IJUnitTestReporter testReport) {
        assertAll(
                () -> assertEquals("Lotcheck Thread", titleThreadIssue, "Navigate to thread title"),
                () -> assertEquals("#F1F7FE", backgroundColor, "check background")
        );
    }
    @Test
    @Order(34)
    public void LCTranslator_Step(IJUnitTestReporter testReport) throws InterruptedException {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
        LotcheckHomePage lotcheckHomePage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        currentPage = hyruleAuthPage.loginWithOvdLink("lc_translator_ncl","AccuseAlthoughShutGuard7");
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckIssueTranslationTasksPage lotcheckIssueTranslationTasksPage = lotcheckLandingPage.navigationToLotcheckIssueTranslationTasks();
        String nameColumnStatus = lotcheckIssueTranslationTasksPage.getStatusNameColumn();
        System.out.println(nameColumnStatus);
        if(!nameColumnStatus.equals("Status")){
            lotcheckLandingPage.clickOnUserNameMenu().clickMyAccount().selectLanguage("English").clickSaveButton();
        }
        lotcheckIssueTranslationTasksPage.searchByInitialCode(initialCode);
        LotcheckIssueTranslationTaskDetails lotcheckIssueTranslationTaskDetails = lotcheckIssueTranslationTasksPage.clickViewTaskDetails();
        LotcheckIssueTranslationIssueDetails lotcheckIssueTranslationIssueDetails = lotcheckIssueTranslationTaskDetails.clickIssueDetail();
        String privateTilte = lotcheckIssueTranslationIssueDetails.getPrivateTitleEN().substring(8);
        String privateDescription = lotcheckIssueTranslationIssueDetails.getPrivateDescriptionEN();
        assertAll(
                () -> assertEquals(TextConstants.inputTile, privateTilte, "check private title"),
                () -> assertEquals(TextConstants.inputDescription, privateDescription, "check private description")
        );
    }
    @Test
    @Order(35)
    public void OnSLCMSLoginToLCAdminWithLanguageSettingEnglish_Step(IJUnitTestReporter testReport) throws InterruptedException {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        hyruleAuthPage.loginWithOvdLink(slcmsUser);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToTesterAssignmentQueue("NCL");
        LotcheckEditAccountPreferences editAccountPreferences = lotcheckQueuePage.clickOnUserNameMenu().clickManagerAccountButton();
        String language = editAccountPreferences.getTitleLanguage();
        if (!language.equals("Language")) {
            editAccountPreferences.selectLanguage("English").clickSaveButton();
        }else {
            editAccountPreferences.clickCancelButton();
        }
        int i=0;
        while (i<50){
            lotcheckLandingPage.navigationToTesterAssignmentQueue("NCL").searchByInitialCode(initialCode);
            String numberValue =lotcheckQueuePage.numberTestPlanDisplay();
            i++;
            if(numberValue.equalsIgnoreCase("1")) {
                break;
            }
        }
        LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview submissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        LotcheckReportSubmissionLotcheckIssues submissionLotcheckIssues = submissionOverview.navBar().clickLotcheckIssues();
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails = submissionLotcheckIssues.clickMenuOption().selectViewIssueDetails();
        currentPage = submissionLotcheckIssueDetails;
    }
    @Test
    @Order(36)
    public void PublishIssueToPublicWithLCITypeAsSubmissionIssue_Step(IJUnitTestReporter testReport) throws InterruptedException {
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails =(LotcheckReportSubmissionLotcheckIssueDetails)currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        submissionLotcheckIssueDetails.clickMenu()
                .selectPublishToPublic()
                .selectLciType(TextConstants.submissionIssue)
                .clickPublish();
    }
    @Test()
    @Order(37)
    public void PerformEditingTitleDescriptionForPublicIssue_Step(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails =(LotcheckReportSubmissionLotcheckIssueDetails) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckEditDetailsDialog lotcheckEditDetailsDialog = submissionLotcheckIssueDetails.clickMenuAtIssueDetails()
                .selectEditDetailsEN();
        lotcheckEditDetailsDialog.editTitleEN(TextConstants.editTitle)
                .editDescriptionEN(TextConstants.editDescription)
                .clickSaveButton();
    }
    @Test()
    @Order(38)
    public void checkTwoLanguageDisplayIsEnable_Step(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails =(LotcheckReportSubmissionLotcheckIssueDetails) currentPage;
        submissionLotcheckIssueDetails.clickMenu();
        assertAll(
                ()->assertTrue(submissionLotcheckIssueDetails.checkTwoLanguageDisplay(),"Option Two Language Display is enabled")
        );
        submissionLotcheckIssueDetails.clickTwoLanguageDisplay();
    }
    @Test()
    @Order(39)
    public void bugSelectResetIssue_Step(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails =(LotcheckReportSubmissionLotcheckIssueDetails) currentPage;
        LotcheckResetIssueDialog lotcheckResetIssueDialog = submissionLotcheckIssueDetails.clickMenu()
                .selectResetIssue();
        currentPage = lotcheckResetIssueDialog;
    }
    @Test()
    @Order(40)
    public void InResetIssuePopupCheckDisplayOfPublishedDescription_Step(IJUnitTestReporter testReport) {
        LotcheckResetIssueDialog lotcheckResetIssueDialog = (LotcheckResetIssueDialog) currentPage;
        publishedDescriptionTitle = lotcheckResetIssueDialog.getPublishedDescriptionTitle();
    }

    @Test()
    @Order(41)
    public void checkPublishedDescription_Expected(IJUnitTestReporter testReport) {
        assertAll(
                ()->assertEquals("Published Description",publishedDescriptionTitle, "Display Name")
        );
    }
    @Test()
    @Order(42)
    public void InResetIssuePopupCheckValueOfPublishedDescription_Step(IJUnitTestReporter testReport) {
        LotcheckResetIssueDialog lotcheckResetIssueDialog = (LotcheckResetIssueDialog) currentPage;
        publishedDescriptionEN = lotcheckResetIssueDialog.getPublishedDescriptionEN();
        publishedDescriptionJP = lotcheckResetIssueDialog.getPublishedDescriptionJP();
    }
    @Test()
    @Order(43)
    public void checkValueOfPublishedDescription_Expected(IJUnitTestReporter testReport) {
        LotcheckResetIssueDialog lotcheckResetIssueDialog = (LotcheckResetIssueDialog) currentPage;
        assertAll(
                ()->assertEquals(TextConstants.inputDescription,publishedDescriptionEN, "Published Description field will display values for both English"),
                ()->assertEquals(TextConstants.inputDescription,publishedDescriptionJP, "Published Description field will display values for both Japan"),
                ()-> assertNotNull(lotcheckResetIssueDialog.ScreenPopup(),"Doesn't display overflowing in case long text with all of the: Published Title, Published Description and Title, Description fields")
        );
    }
    @Test()
    @Order(44)
    public void SubmitEditedInformationInitialRelease_Step(IJUnitTestReporter testReport) throws Exception {
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
        //create release infor
        NDPReleaseInfoPage releaseInfoPage = ndpReleaseInfoPage.nav().clickReleaseInfo();
        NDPReleaseInfoIssuePage ndpReleaseInfoIssuePage = ndpReleaseInfoPage.nav().clickIssues();
        ndpReleaseInfoIssuePage.clickExpandUnresolved()
                .clickExpandTestCase()
                .clickSendResolutionButton();
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment("Comment App Info Sheet ").clickSubmit();
    }
    @Test()
    @Order(45)
    public void checkBugSubmitIsDisplayCurrentIssue_Step(IJUnitTestReporter testReport) {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToTesterAssignmentQueue("NCL");
        int i=0;
        while (i<50){
            lotcheckLandingPage.navigationToTesterAssignmentQueue("NCL").searchByInitialCode(initialCode);
            String numberValue =lotcheckQueuePage.numberTestPlanDisplay();
            i++;
            if(numberValue.equalsIgnoreCase("1")) {
                break;
            }
        }
        LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview submissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues = submissionOverview.navBar().clickLotcheckIssues();
        currentPage = reportSubmissionLotcheckIssues;
        recordIsDisplay1= reportSubmissionLotcheckIssues.recordIsDisplayInCurrentSubmissionIssue();
        assertAll(
                ()->assertTrue(recordIsDisplay1, "bug submit is display In current Submission Issue")
        );
    }
    @Test()
    @Order(46)
    public void checkTitleLotcheckIssueRepeat21_Step(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        reportSubmissionLotcheckIssues.expandCollapsedIssue();
        iconPrivate1 = reportSubmissionLotcheckIssues.iconPrivateIsDisplay();
        titleIssue1 = reportSubmissionLotcheckIssues.getTitle();
        descriptionIssue1 = reportSubmissionLotcheckIssues.getDescription();
    }
    @Test()
    @Order(47)
    public void checkTitleLotcheckIssueRepeat21_Expected(IJUnitTestReporter testReport) {
        assertAll(
                ()-> assertTrue(iconPrivate1,"icon private"),
                ()-> assertEquals(TextConstants.inputDescription,descriptionIssue1, "verify description"),
                ()-> assertEquals(TextConstants.inputTile, titleIssue1, "verify title")
        );
    }
    @Test()
    @Order(48)
    public void checkTitleDescriptionInDetailLotcheckIssueRepeat22_Step(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails = reportSubmissionLotcheckIssues.clickMenuOption().selectViewIssueDetails();
        titleIssueInDetail1 = submissionLotcheckIssueDetails.getTitleIssueValue();
        descriptionIssueInDetail1 = submissionLotcheckIssueDetails.getDescriptionIssueValue();
    }
    @Test()
    @Order(49)
    public void checkTitleDescriptionInDetailLotcheckIssueRepeat22_Expected(IJUnitTestReporter testReport) {
        assertAll(
                ()-> assertEquals(TextConstants.editDescription,descriptionIssueInDetail1, "verify description"),
                ()-> assertEquals(TextConstants.editTitle, titleIssueInDetail1, "verify title in View Detail")
        );
    }
    @Test()
    @Order(50)
    public void checkVisibleToFiledLotcheckIssueRepeat23_Step(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        reportSubmissionLotcheckIssues.navBar().clickLotcheckIssues();
        visibleToValue1 = reportSubmissionLotcheckIssues.getValueFieldOnBottomOfTheThreadTile("Visible To:").substring(12);
    }
    @Test()
    @Order(51)
    public void checkVisibleToFiledLotcheckIssueRepeat23_Expected(IJUnitTestReporter testReport) {
        assertAll(
                ()-> assertEquals(visibleToValue1,"Partner, Lotcheck", "verify value is Lotcheck"),
                ()-> assertNotEquals(visibleToValue1, "Internal", "verify value is never displayed")
        );
    }
    @Test()
    @Order(52)
    public void SetSubmissionJugdmentToNG_Step(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailDashboardPage testRailDashboardPage = testRailLoginPage.signInTestRail(testrailUser);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        for (int i = 1; i < 3; i++) {
            if (i == 2) {
                testRailManagerDashboardPage.logFail();
            }
            testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(initialCode)
                    .selectStatusUntested()
                    .selectStatusFailed();
            testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
            testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                    .clickOnTheFirstTestPlanBar()
                    .waitingLoadingTestSuite()
                    .selectCheckboxOfTheFirstTestPlan()
                    .clickOnSetStatusButton()
                    .selectStatusOnSetStatusModal("Failed")
                    .clickOnUpdateStatusButtonOnSetStatusModal()
                    .waitingLoadingTestSuite();
            if (!testRailManagerDashboardPage.getPercentOfTestPlan().equals("100% Complete")) {
                WaitUtils.idle(60000);
                testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
                testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar();
                if (testRailManagerDashboardPage.getPercentOfTestPlan().equals("100% Complete")) {
                    break;
                }
            } else {
                break;
            }
        }
        testRailManagerDashboardPage.clickOnPadlockButtonOfTheFirstTestPlan()
                .selectTestPlanJudgment(TextConstants.NG)
                .clickOnYesButtonOnConfirmationModal();
        testRailDashboardPage.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton();

        testRailLoginPage.signInTestRail("tradmin@test.noa.nintendo.com", "password");
        testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        for (int i = 1; i < 3; i++) {
            if (i == 2) {
                testRailManagerDashboardPage.logFail();
            }
            testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(initialCode)
                    .selectStatusUntested()
                    .selectStatusFailed();
            testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
            testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                    .clickOnTheFirstTestPlanBar()
                    .waitingLoadingTestSuite()
                    .selectCheckboxOfTheFirstTestPlan()
                    .clickOnSetStatusButton()
                    .selectStatusOnSetStatusModal("Failed")
                    .clickOnUpdateStatusButtonOnSetStatusModal()
                    .waitingLoadingTestSuite();
            if (!testRailManagerDashboardPage.getPercentOfTestPlan().equals("100% Complete")) {
                WaitUtils.idle(60000);
                testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
                testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar();
                if (testRailManagerDashboardPage.getPercentOfTestPlan().equals("100% Complete")) {
                    break;
                }
            } else {
                break;
            }
        }
        testRailManagerDashboardPage.clickOnPadlockButtonOfTheFirstTestPlan()
                .selectTestPlanJudgment(TextConstants.NG)
                .clickOnYesButtonOnConfirmationModal();
        testRailDashboardPage.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton();

        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToJudgmentApprovalQueue("NCL");
        lotcheckQueuePage.searchByInitialCode(initialCode);
        int i = 0;
        while (i <= 100) {
            lotcheckLandingPage.navigationToJudgmentApprovalQueue("NCL");
            lotcheckQueuePage.searchByInitialCode(initialCode);
            String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
            if (numberValue.equalsIgnoreCase("1")) {
                LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
                String judgmentStatus = lotcheckQueuePage.getJudgmentStatus();
                if (judgmentStatus.equals("NG")) {
                    WaitUtils.idle(5000);
                    lotcheckTestPlanRow.selectApproveJudgment().clickConfirm();
                    //lotcheckTestPlanRow.startAlert();
                    break;
                }
            }
            i++;
            WaitUtils.idle(15000);
            lotcheckQueuePage.refreshPageSLCMS();
        }
        WaitUtils.idle(20000);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
        //Judgment NOA
        browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN).continueToLogin().loginWithOvdLink("lc_admin_noa", "InterfereSurroundMaleClass3");
        lotcheckLandingPage.navigationToJudgmentApprovalQueue("NOA");
        lotcheckQueuePage.searchByInitialCode(initialCode);
        int z = 0;
        while (z <= 100) {
            lotcheckLandingPage.navigationToJudgmentApprovalQueue("NOA");
            lotcheckQueuePage.searchByInitialCode(initialCode);
            String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
            if (numberValue.equalsIgnoreCase("1")) {
                LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
                String judgmentStatus = lotcheckQueuePage.getJudgmentStatus();
                if (judgmentStatus.equals("NG")) {
                    WaitUtils.idle(5000);
                    lotcheckTestPlanRow.selectApproveJudgment().clickConfirm();
                    //lotcheckTestPlanRow.startAlert();
                    break;
                }
            }
            z++;
            WaitUtils.idle(15000);
            lotcheckQueuePage.refreshPageSLCMS();
        }
        WaitUtils.idle(20000);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
    }
    @Test()
    @Order(53)
    public void checkBugSubmitIsDisplayCurrentIssue_Step38(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToFinishedQueue("NCL")
                .searchByInitialCode(initialCode);
        LotcheckReportSubmissionOverview submissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues = submissionOverview.navBar().clickLotcheckIssues();
        currentPage = reportSubmissionLotcheckIssues;
        recordIsDisplay2= reportSubmissionLotcheckIssues.recordIsDisplayInCurrentSubmissionIssue();
        assertAll(
                ()->assertTrue(recordIsDisplay2, "bug submit is display In current Submission Issue")
        );
    }
    @Test()
    @Order(54)
    public void checkTitleLotcheckIssueRepeat21_Step39(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        reportSubmissionLotcheckIssues.expandCollapsedIssue();
        iconPrivate2 = reportSubmissionLotcheckIssues.iconPrivateIsDisplay();
        titleIssue2 = reportSubmissionLotcheckIssues.getTitle();
        descriptionIssue2= reportSubmissionLotcheckIssues.getDescription();
    }
    @Test()
    @Order(55)
    public void checkTitleLotcheckIssueRepeat21_Expected39(IJUnitTestReporter testReport) {
        assertAll(
                ()-> assertTrue(iconPrivate2,"icon private"),
                ()-> assertEquals(TextConstants.inputDescription,descriptionIssue2, "verify description"),
                ()-> assertEquals(TextConstants.inputTile, titleIssue2, "verify title")
        );
    }
    @Test()
    @Order(56)
    public void checkTitleDescriptionInDetailLotcheckIssueRepeat22_Step39(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails = reportSubmissionLotcheckIssues.clickMenuOption().selectViewIssueDetails();
        titleIssueInDetail2 = submissionLotcheckIssueDetails.getTitleIssueValue();
        descriptionIssueInDetail2 = submissionLotcheckIssueDetails.getDescriptionIssueValue();
    }
    @Test()
    @Order(57)
    public void checkTitleDescriptionInDetailLotcheckIssueRepeat22_Expected39(IJUnitTestReporter testReport) {
        assertAll(
                ()-> assertEquals(TextConstants.editDescription,descriptionIssueInDetail2, "verify description"),
                ()-> assertEquals(TextConstants.editTitle, titleIssueInDetail2, "verify title in View Detail")
        );
    }
    @Test()
    @Order(58)
    public void checkVisibleToFiledLotcheckIssueRepeat23_Step39(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        reportSubmissionLotcheckIssues.navBar().clickLotcheckIssues();
        visibleToValue2 = reportSubmissionLotcheckIssues.getValueFieldOnBottomOfTheThreadTile("Visible To:").substring(12);
    }
    @Test()
    @Order(59)
    public void checkVisibleToFiledLotcheckIssueRepeat23_Expected39(IJUnitTestReporter testReport) {
        assertAll(
                ()-> assertEquals(visibleToValue2,"Partner, Lotcheck", "verify value is Lotcheck"),
                ()-> assertNotEquals(visibleToValue2, "Internal", "verify value is never displayed")
        );
    }
    @Test()
    @Order(60)
    public void RevertToNoJugdment_Step40(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = reportSubmissionLotcheckIssues.navBar().clickOverview();
        LotcheckChangeSubmissionJudgment lotcheckChangeSubmissionJudgment = reportSubmissionLotcheckIssues.matrix().clickChangeJudgment();
        lotcheckChangeSubmissionJudgment.clickConfirmButton();
    }
    @Test()
    @Order(61)
    public void checkBugSubmitIsDisplayCurrentIssue_Step41(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        reportSubmissionLotcheckIssues.navBar().clickLotcheckIssues();
        currentPage = reportSubmissionLotcheckIssues;
        recordIsDisplay3= reportSubmissionLotcheckIssues.recordIsDisplayInCurrentSubmissionIssue();
        assertAll(
                ()->assertTrue(recordIsDisplay3, "bug submit is display In current Submission Issue")
        );
    }
    @Test()
    @Order(62)
    public void checkTitleLotcheckIssueRepeat21_Step42(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        reportSubmissionLotcheckIssues.expandCollapsedIssue();
        iconPrivate3 = reportSubmissionLotcheckIssues.iconPrivateIsDisplay();
        titleIssue3 = reportSubmissionLotcheckIssues.getTitle();
        descriptionIssue3= reportSubmissionLotcheckIssues.getDescription();
    }
    @Test()
    @Order(63)
    public void checkTitleLotcheckIssueRepeat21_Expected42(IJUnitTestReporter testReport) {
        assertAll(
                ()-> assertTrue(iconPrivate3,"icon private"),
                ()-> assertEquals(TextConstants.inputDescription,descriptionIssue3, "verify description"),
                ()-> assertEquals(TextConstants.inputTile, titleIssue3, "verify title")
        );
    }
    @Test()
    @Order(64)
    public void checkTitleDescriptionInDetailLotcheckIssueRepeat22_Step42(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails = reportSubmissionLotcheckIssues.clickMenuOption().selectViewIssueDetails();
        titleIssueInDetail3 = submissionLotcheckIssueDetails.getTitleIssueValue();
        descriptionIssueInDetail3 = submissionLotcheckIssueDetails.getDescriptionIssueValue();
    }
    @Test()
    @Order(65)
    public void checkTitleDescriptionInDetailLotcheckIssueRepeat22_Expected42(IJUnitTestReporter testReport) {
        assertAll(
                ()-> assertEquals(TextConstants.editDescription,descriptionIssueInDetail3, "verify description"),
                ()-> assertEquals(TextConstants.editTitle, titleIssueInDetail3, "verify title in View Detail")
        );
    }
    @Test()
    @Order(66)
    public void checkVisibleToFiledLotcheckIssueRepeat23_Step42(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        reportSubmissionLotcheckIssues.navBar().clickLotcheckIssues();
        visibleToValue3 = reportSubmissionLotcheckIssues.getValueFieldOnBottomOfTheThreadTile("Visible To:").substring(12);
    }
    @Test()
    @Order(67)
    public void checkVisibleToFiledLotcheckIssueRepeat23_Expected42(IJUnitTestReporter testReport) {
        assertAll(
                ()-> assertEquals(visibleToValue3,"Partner, Lotcheck", "verify value is Lotcheck"),
                ()-> assertNotEquals(visibleToValue3, "Internal", "verify value is never displayed")
        );
    }
    @Test()
    @Order(68)
    public void SetSubmissionJugdmentToOK_Step43(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailDashboardPage testRailDashboardPage = testRailLoginPage.signInTestRail(testrailUser);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        for (int i = 1; i < 3; i++) {
            if (i == 2) {
                testRailManagerDashboardPage.logFail();
            }
            testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(initialCode)
                    .selectStatusUntested()
                    .selectStatusPassed()
                    .selectStatusFailed();
            testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
            testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                    .clickOnTheFirstTestPlanBar()
                    .waitingLoadingTestSuite()
                    .selectCheckboxOfTheFirstTestPlan()
                    .clickOnSetStatusButton()
                    .selectStatusOnSetStatusModal("Passed")
                    .clickOnUpdateStatusButtonOnSetStatusModal()
                    .waitingLoadingTestSuite();
            if (!testRailManagerDashboardPage.getPercentOfTestPlan().equals("100% Complete")) {
                WaitUtils.idle(60000);
                testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
                testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar();
                if (testRailManagerDashboardPage.getPercentOfTestPlan().equals("100% Complete")) {
                    break;
                }
            } else {
                break;
            }
        }
        testRailManagerDashboardPage.clickOnPadlockButtonOfTheFirstTestPlan()
                .selectTestPlanJudgment("OK")
                .clickOnYesButtonOnConfirmationModal();
        testRailDashboardPage.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton();

        testRailLoginPage.signInTestRail("tradmin@test.noa.nintendo.com", "password");
        testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        for (int i = 1; i < 3; i++) {
            if (i == 2) {
                testRailManagerDashboardPage.logFail();
            }
            testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(initialCode)
                    .selectStatusUntested()
                    .selectStatusPassed()
                    .selectStatusFailed();
            testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
            testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                    .clickOnTheFirstTestPlanBar()
                    .waitingLoadingTestSuite()
                    .selectCheckboxOfTheFirstTestPlan()
                    .clickOnSetStatusButton()
                    .selectStatusOnSetStatusModal("Passed")
                    .clickOnUpdateStatusButtonOnSetStatusModal()
                    .waitingLoadingTestSuite();
            if (!testRailManagerDashboardPage.getPercentOfTestPlan().equals("100% Complete")) {
                WaitUtils.idle(60000);
                testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
                testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar();
                if (testRailManagerDashboardPage.getPercentOfTestPlan().equals("100% Complete")) {
                    break;
                }
            } else {
                break;
            }
        }
        testRailManagerDashboardPage.clickOnPadlockButtonOfTheFirstTestPlan()
                .selectTestPlanJudgment("OK")
                .clickOnYesButtonOnConfirmationModal();
        testRailDashboardPage.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton();

        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToJudgmentApprovalQueue("NCL");
        lotcheckQueuePage.searchByInitialCode(initialCode);
        int i = 0;
        while (i <= 100) {
            lotcheckLandingPage.navigationToJudgmentApprovalQueue("NCL");
            lotcheckQueuePage.searchByInitialCode(initialCode);
            String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
            if (numberValue.equalsIgnoreCase("1")) {
                LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
                String judgmentStatus = lotcheckQueuePage.getJudgmentStatus();
                if (judgmentStatus.equals("OK")) {
                    WaitUtils.idle(5000);
                    lotcheckTestPlanRow.selectApproveJudgment().clickConfirm();
                    //lotcheckTestPlanRow.startAlert();
                    break;
                }
            }
            i++;
            WaitUtils.idle(15000);
            lotcheckQueuePage.refreshPageSLCMS();
        }
        WaitUtils.idle(20000);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
        //Judgment NOA
        browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN).continueToLogin().loginWithOvdLink("lc_admin_noa", "InterfereSurroundMaleClass3");
        lotcheckLandingPage.navigationToJudgmentApprovalQueue("NOA");
        lotcheckQueuePage.searchByInitialCode(initialCode);
        int z = 0;
        while (z <= 100) {
            lotcheckLandingPage.navigationToJudgmentApprovalQueue("NOA");
            lotcheckQueuePage.searchByInitialCode(initialCode);
            String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
            if (numberValue.equalsIgnoreCase("1")) {
                LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
                String judgmentStatus = lotcheckQueuePage.getJudgmentStatus();
                if (judgmentStatus.equals("OK")) {
                    WaitUtils.idle(5000);
                    lotcheckTestPlanRow.selectApproveJudgment().clickConfirm();
                    //lotcheckTestPlanRow.startAlert();
                    break;
                }
            }
            z++;
            WaitUtils.idle(15000);
            lotcheckQueuePage.refreshPageSLCMS();
        }
        WaitUtils.idle(20000);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
    }
    @Test()
    @Order(69)
    public void checkBugSubmitIsDisplayCurrentIssue_Step46(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToFinishedQueue("NCL")
                .searchByInitialCode(initialCode);
        LotcheckReportSubmissionOverview submissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues = submissionOverview.navBar().clickLotcheckIssues();
        currentPage = reportSubmissionLotcheckIssues;
        recordIsDisplay4= reportSubmissionLotcheckIssues.recordIsDisplayInCurrentSubmissionIssue();
        assertAll(
                ()->assertTrue(recordIsDisplay4, "bug submit is display In current Submission Issue")
        );
    }
    @Test()
    @Order(70)
    public void checkTitleLotcheckIssueRepeat21_Step45(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        reportSubmissionLotcheckIssues.expandCollapsedIssue();
        iconPrivate4 = reportSubmissionLotcheckIssues.iconPrivateIsDisplay();
        titleIssue4 = reportSubmissionLotcheckIssues.getTitle();
        descriptionIssue4= reportSubmissionLotcheckIssues.getDescription();
    }
    @Test()
    @Order(71)
    public void checkTitleLotcheckIssueRepeat21_Expected45(IJUnitTestReporter testReport) {
        assertAll(
                ()-> assertTrue(iconPrivate4,"icon private"),
                ()-> assertEquals(TextConstants.inputDescription,descriptionIssue4, "verify description"),
                ()-> assertEquals(TextConstants.inputTile, titleIssue4, "verify title")
        );
    }
    @Test()
    @Order(72)
    public void checkTitleDescriptionInDetailLotcheckIssueRepeat22_Step45(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails = reportSubmissionLotcheckIssues.clickMenuOption().selectViewIssueDetails();
        titleIssueInDetail4 = submissionLotcheckIssueDetails.getTitleIssueValue();
        descriptionIssueInDetail4 = submissionLotcheckIssueDetails.getDescriptionIssueValue();
    }
    @Test()
    @Order(73)
    public void checkTitleDescriptionInDetailLotcheckIssueRepeat22_Expected45(IJUnitTestReporter testReport) {
        assertAll(
                ()-> assertEquals(TextConstants.editDescription,descriptionIssueInDetail4, "verify description"),
                ()-> assertEquals(TextConstants.editTitle, titleIssueInDetail4, "verify title in View Detail")
        );
    }
    @Test()
    @Order(74)
    public void checkVisibleToFiledLotcheckIssueRepeat23_Step45(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        reportSubmissionLotcheckIssues.navBar().clickLotcheckIssues();
        visibleToValue4 = reportSubmissionLotcheckIssues.getValueFieldOnBottomOfTheThreadTile("Visible To:").substring(12);
    }
    @Test()
    @Order(75)
    public void checkVisibleToFiledLotcheckIssueRepeat23_Expected45(IJUnitTestReporter testReport) {
        assertAll(
                ()-> assertEquals(visibleToValue4,"Partner, Lotcheck", "verify value is Lotcheck"),
                ()-> assertNotEquals(visibleToValue4, "Internal", "verify value is never displayed")
        );
    }
    @Test()
    @Order(76)
    public void RevertToNG_Step46(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = reportSubmissionLotcheckIssues.navBar().clickOverview();
        LotcheckChangeSubmissionJudgment lotcheckChangeSubmissionJudgment = reportSubmissionLotcheckIssues.matrix().clickChangeJudgment();
        lotcheckChangeSubmissionJudgment.clickConfirmButton();
    }

    @Test()
    @Order(77)
    public void checkBugSubmitIsDisplayCurrentIssue_Step47(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToOverallLotcheckQueue("NCL");
        lotcheckQueuePage.searchByInitialCode(initialCode);
        lotcheckQueuePage.clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview submissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues = submissionOverview.navBar().clickLotcheckIssues();
        reportSubmissionLotcheckIssues.clickExpandVersion();
        currentPage = reportSubmissionLotcheckIssues;
        recordIsDisplay5= reportSubmissionLotcheckIssues.recordIsDisplayInProductResolutionArchiveIssues();
        assertAll(
                ()->assertTrue(recordIsDisplay5, "bug submit is display In current Submission Issue")
        );
    }
    @Test()
    @Order(78)
    public void checkTitleLotcheckIssueRepeat21_Step48(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        reportSubmissionLotcheckIssues.navBar().clickLotcheckIssues();
        reportSubmissionLotcheckIssues.clickExpandVersion();
        reportSubmissionLotcheckIssues.expandCollapsedIssueInArchive();
        iconPrivate5 = reportSubmissionLotcheckIssues.iconPrivateIsDisplay();
        titleIssue5 = reportSubmissionLotcheckIssues.getTitle();
        descriptionIssue5= reportSubmissionLotcheckIssues.getDescription();
    }
    @Test()
    @Order(79)
    public void checkTitleLotcheckIssueRepeat21_Expected48(IJUnitTestReporter testReport) {
        assertAll(
                ()-> assertTrue(iconPrivate5,"icon private"),
                ()-> assertEquals(TextConstants.inputDescription,descriptionIssue5, "verify description"),
                ()-> assertEquals(TextConstants.inputTile, titleIssue5, "verify title")
        );
    }
    @Test()
    @Order(80)
    public void checkTitleDescriptionInDetailLotcheckIssueRepeat22_Step48(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails = reportSubmissionLotcheckIssues.clickMenuOption().selectViewIssueDetails();
        titleIssueInDetail5 = submissionLotcheckIssueDetails.getTitleIssueValue();
        descriptionIssueInDetail5 = submissionLotcheckIssueDetails.getDescriptionIssueValue();
    }
    @Test()
    @Order(81)
    public void checkTitleDescriptionInDetailLotcheckIssueRepeat22_Expected48(IJUnitTestReporter testReport) {
        assertAll(
                ()-> assertEquals(TextConstants.editDescription,descriptionIssueInDetail5, "verify description"),
                ()-> assertEquals(TextConstants.editTitle, titleIssueInDetail5, "verify title in View Detail")
        );
    }
    @Test()
    @Order(82)
    public void checkVisibleToFiledLotcheckIssueRepeat23_Step48(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        reportSubmissionLotcheckIssues.navBar().clickLotcheckIssues();
        reportSubmissionLotcheckIssues.clickExpandVersion();
        visibleToValue5 = reportSubmissionLotcheckIssues.getValueFieldOnBottomOfTheThreadTile("Visible To:").substring(12);
    }
    @Test()
    @Order(83)
    public void checkVisibleToFiledLotcheckIssueRepeat23_Expected48(IJUnitTestReporter testReport) {
        assertAll(
                ()-> assertEquals(visibleToValue5,"Partner, Lotcheck", "verify value is Lotcheck"),
                ()-> assertNotEquals(visibleToValue5, "Internal", "verify value is never displayed")
        );
    }

    @Test()
    @Order(84)
    public void FillOutAllNecessaryInformationAndSubmitSubSequenceSubmissionToLotcheck_Step49(IJUnitTestReporter testReport) throws Exception {
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
        //create release infor
        NDPReleaseInfoPage releaseInfoPage = ndpReleaseInfoPage.nav().clickReleaseInfo();
        releaseInfoPage.setExpectedLotcheckSubmissionDate();
        releaseInfoPage.save();
        //Create GuildLine
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = ndpReleaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoGuidelinesPage.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();
        //upload ROM
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = ndpReleaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
        ndpReleaseInfoROMUpload.deleteFile(fileRomName);
        //Request Issue
        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
        issuePage.openRequiredApprovalTab();
        issuePage.performAllIssueAtApprovalTab(false);
        ndpReleaseInfoPage.navigateToProduct();
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
        //Approval Issue TaskRequested
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn("prdcv_lic_approval_coord_ncl","nintendo").clickOnMyTaskMenu();
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, gameCode);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
        //submit release
        browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser).clickMyProducts();
        //NDPMyProductsPage myProductsPage = (NDPMyProductsPage)currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        myProductsPage.clickProductByName(productName);
        ndpProductDashboardPage.clickReleases();
        ndpProductDashboardReleasesTab.selectInitialRelease();
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment("Comment submission").clickSubmit();
    }
    @Test()
    @Order(85)
    public void checkBugSubmitIsDisplayCurrentIssue_Step50(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToOverallLotcheckQueue("NCL");
        int i = 0;
        while (i < 40) {
            lotcheckLandingPage.navigationToOverallLotcheckQueue("NCL").searchByInitialCode(initialCode);
            String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
            i++;
            if (numberValue.equalsIgnoreCase("1")) {
                break;
            }
        }
        lotcheckQueuePage.clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues = lotcheckReportSubmissionOverview.navBar().clickLotcheckIssues();
        reportSubmissionLotcheckIssues.clickExpandVersion();
        currentPage = reportSubmissionLotcheckIssues;
        recordIsDisplay6= reportSubmissionLotcheckIssues.recordIsDisplayInProductResolutionArchiveIssues();
        assertAll(
                ()->assertTrue(recordIsDisplay6, "bug submit is display In current Submission Issue")
        );
    }
    @Test()
    @Order(86)
    public void checkTitleLotcheckIssueRepeat21_Step51(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        reportSubmissionLotcheckIssues.navBar().clickLotcheckIssues();
        reportSubmissionLotcheckIssues.clickExpandVersion();
        reportSubmissionLotcheckIssues.expandCollapsedIssueInArchive();
        iconPrivate6 = reportSubmissionLotcheckIssues.iconPrivateIsDisplay();
        titleIssue6 = reportSubmissionLotcheckIssues.getTitle();
        descriptionIssue6= reportSubmissionLotcheckIssues.getDescription();
    }
    @Test()
    @Order(87)
    public void checkTitleLotcheckIssueRepeat21_Expected51(IJUnitTestReporter testReport) {
        assertAll(
                ()-> assertTrue(iconPrivate6,"icon private"),
                ()-> assertEquals(TextConstants.inputDescription,descriptionIssue6, "verify description"),
                ()-> assertEquals(TextConstants.inputTile, titleIssue6, "verify title")
        );
    }
    @Test()
    @Order(88)
    public void checkTitleDescriptionInDetailLotcheckIssueRepeat22_Step51(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        reportSubmissionLotcheckIssues.navBar().clickLotcheckIssues();
        reportSubmissionLotcheckIssues.clickExpandVersion();
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails = reportSubmissionLotcheckIssues.clickMenuOption().selectViewIssueDetails();
        titleIssueInDetail6 = submissionLotcheckIssueDetails.getTitleIssueValue();
        descriptionIssueInDetail6 = submissionLotcheckIssueDetails.getDescriptionIssueValue();
    }
    @Test()
    @Order(89)
    public void checkTitleDescriptionInDetailLotcheckIssueRepeat22_Expected51(IJUnitTestReporter testReport) {
        assertAll(
                ()-> assertEquals(TextConstants.editDescription,descriptionIssueInDetail6, "verify description"),
                ()-> assertEquals(TextConstants.editTitle, titleIssueInDetail6, "verify title in View Detail")
        );
    }
    @Test()
    @Order(90)
    public void checkVisibleToFiledLotcheckIssueRepeat23_Step51(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues reportSubmissionLotcheckIssues =(LotcheckReportSubmissionLotcheckIssues) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        reportSubmissionLotcheckIssues.navBar().clickLotcheckIssues();
        reportSubmissionLotcheckIssues.clickExpandVersion();
        visibleToValue6 = reportSubmissionLotcheckIssues.getValueFieldOnBottomOfTheThreadTile("Visible To:").substring(12);
    }
    @Test()
    @Order(91)
    public void checkVisibleToFiledLotcheckIssueRepeat23_Expected51(IJUnitTestReporter testReport) {
        assertAll(
                ()-> assertEquals(visibleToValue6,"Partner, Lotcheck", "verify value is Lotcheck"),
                ()-> assertNotEquals(visibleToValue6, "Internal", "verify value is never displayed")
        );
    }
}
