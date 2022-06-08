package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.junit.extensions.ScenarioTest;
import net.nintendo.automation.junit.TestLogger;
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
import net.nintendo.automation.ui.models.d4c.D4CLogin;
import net.nintendo.automation.ui.models.d4c.D4CRomDetails;
import net.nintendo.automation.ui.models.d4c.D4CRomPublishSuit;
import net.nintendo.automation.ui.models.d4c.D4CRomTab;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthPage;
import net.nintendo.automation.ui.models.lcms.LotcheckHomePage;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.ui.models.lcms.LotcheckQueuePage;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.ndp.NDPCreateProductPage;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPInternalWaiverRequestsTab;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.NDPRequestGameCodePage;
import net.nintendo.automation.ui.models.ndp.NDPSignInPage;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardMembersTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardProductInfoTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardReleasesTab;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoAgeRating;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoFeaturesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoGuidelinesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoIssuePage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoROMUpload;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPSubmissionReviewPage;
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
import net.nintendo.automation.ui.users.User;
import net.nintendo.automation.ui.users.UserManager;
import net.nintendo.automation.utils.WaitUtils;
import net.nintendo.automation_core.common_client.enums.ServiceType;
import net.nintendo.automation_core.common_client.enums.UserType;
import net.nintendo.automation_core.common_utils.annotations.services.NDP;
import net.nintendo.automation_core.common_utils.annotations.services.SLCMS;
import net.nintendo.automation_core.common_utils.enums.LotcheckRegion;
import net.nintendo.automation_core.extent_reports.extensions.IJUnitTestReporter;
import org.junit.jupiter.api.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@NDP
@SLCMS
@Tag("T3404490")
@ScenarioTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3404490 extends CommonBaseTest {
    private static Browser browser;
    private static User ndpUser;
    private static User slcmsUser;
    private static Browser romBrowser;
    private static Browser d4cBrowser;
    private static User romUser;
    private static User licUser;
    private static User testRailUser;

    private static String productName;
    private static String applicationID;
    private static String initialCode;
    private static String gameCode;
    private static String fileRomName;
    private static String romID;
    private static String statusEnglishInReleaseInfoPage;
    private static String statusEnglishInDashboardReleasesTab;
    private static String statusJapaneseInReleaseInfoPage;
    private static String statusJapaneseInDashboardReleasesTab;
    private static String descriptionInUserTabExpectedEN;
    private static String descriptionInUserTabActualEN;
    private static String descriptionInUserTabExpectedJP;
    private static String descriptionInUserTabActualJP;
    private static String accessRole;
    private static String userName;
    private static String productNameCreate;
    private static String addGroupSuccessMessage;
    private static String colourRemoveButton;
    private static Boolean isRemoveButtonDisplayed;
    private static CommonUIComponent currentPage;
    private static TestLogger logger;

    String productBase = "Test1234 (DLA5A)";

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
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_NguyenPhong);
        NDPHomePage ndpHomePage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
        ndpHomePage.acceptCookie();
        NDPSignInPage signInPage = ndpHomePage.clickSignInPage();
        signInPage.signIn(ndpUser);
        productName = DataGen.getRandomProductName();
        slcmsUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);
        testRailUser = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER);
        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER);
    }

    @Test()
    @Order(1)
    public void loginNDPAsPartnerCreateProduct_Step1(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        NDPCreateProductPage createProductPage = myProductsPage.createNewProduct();
        createProductPage.selectProductType(TextConstants.demo_Product)
                .selectPublishingRelationship(TextConstants.third_Party_Type)
                .selectDigitalJapanSalesRegion()
                .selectProductName(productName)
                .selectProductNameKana(TextConstants.productKata)
                .selectBasedProduct(productBase)
                .clickCreateButton();
        productNameCreate = myProductsPage.getProductNameInRow(1);
        NDPProductDashboardPage ndpDashboardPage = myProductsPage.clickProductByName(productName);
        NDPRequestGameCodePage requestGameCodePage = ndpDashboardPage.issueGameCode();
        NDPProductDashboardPage ndpProductDashboardPage = requestGameCodePage.setExpectedLotcheckSubmissionDate()
                .setExpectedReleaseDate(65)
                .clickIssue();
        String issueGameSuccessful = ndpProductDashboardPage.navProductInfoDashboard().getMessageIssueGameCodeSuccessful();
        assertEquals("A Game Code has been successfully added to the product.", issueGameSuccessful);
        currentPage = ndpProductDashboardPage;
    }

    @Test
    @Order(2)
    public void verifyProductIsCreatedSuccessfully_Expected1(IJUnitTestReporter testReport) {
        assertEquals(productName, productNameCreate, "Verify product is created successfully");
    }

    @Test()
    @Order(3)
    public void atProductInfoEditPublishingAndSave_Step2(IJUnitTestReporter testReport) {
        NDPProductDashboardPage ndpProductDashboardPage = (NDPProductDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();
        ndpProductDashboardProductInfoTab.clickOnEditPublishingButton()
                .selectPublishingRelationship(TextConstants.second_Party_Type)
                .clickSaveButton();
        WaitUtils.idle(5000);
        NDPMyProductsPage ndpMyProductsPage = ndpProductDashboardPage.clickBackToMyProducts();
        NDPProductDashboardPage ndpDashboardPage = ndpMyProductsPage.clickProductByName(productName);
        currentPage = ndpDashboardPage.clickProductInfo();
    }

    @Test
    @Order(4)
    public void verifyInPublishingDisplays_Expected2(IJUnitTestReporter testReport) {
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = (NDPProductDashboardProductInfoTab) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        String salesRegions = ndpProductDashboardProductInfoTab.getSalesRegionsValue();
        String productType = ndpProductDashboardProductInfoTab.getProductTypeValue();
        String publishingRelationship = ndpProductDashboardProductInfoTab.getPublishingRelationshipValue();
        assertAll(
                () -> assertEquals("Japan", salesRegions, "Verify Sale Regions is Japan"),
                () -> assertEquals("Demo", productType, "Verify Product Type is Demo"),
                () -> assertEquals("Second Party Type", publishingRelationship, "Verify Publishing Relationship is Second Party Type"));
    }

    @Test()
    @Order(5)
    public void createRomUpload_Step3(IJUnitTestReporter testReport) {
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
        romCreateRom.selectRequester(TextConstants.test_User)
                .selectType(TextConstants.initial_Release)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .enterProductName(productName)
                .enterApplicationId(applicationID)
                .selectPublishingRelationShip(TextConstants.second_Party_Type)
                .enterDisplayVersion("1.0.0")
                .enterReleaseVersion("00")
                .selectCERORating("CERO: Not required")
                .clickOnCreateButton()
                .waitIsLoaded();
        fileRomName = romCreateRom.getRomNameTable(applicationID);
        romCreateRom.clickOnROMFileOnROMTable(applicationID);
        WaitUtils.idle(5000);
        romCreateRom.clickOnUserNameMenu().clickOnLogoutButton();
        BrowserManager.closeBrowser(romBrowser);
    }

    @Test
    @Order(6)
    public void declareTheRequiredInformation_Step4(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        NDPReleaseInfoPage releaseInfoPage = ndpReleaseInfoPage.nav().clickReleaseInfo();
        WaitUtils.idle(5000);
        releaseInfoPage.selectCardSize("2 GB")
                .selectDemoType("RID Kiosk Only")
                .selectRomsThatDifferBetweenRegion(TextConstants.selectRomSameAllRegion)
                .selectLanguageUsedForTextEntry(TextConstants.english)
                .save()
                .waitSaveButtonEnable();
        assertTrue(ndpReleaseInfoPage.nav().isReleaseInfoCompleted());
        HashMap<ProgramSpecificationsEnum, String> programSpecificationsSettings = FeatureSettingsManager.getProgramSpecificationsSettings(TextConstants.allOff_DemoNoPromotion);
        HashMap<InputDevicesEnum, String> inputDeviceSettings = FeatureSettingsManager.getInputDevicesSettings(TextConstants.allOff);
        HashMap<PlayModesEnum, String> playModesSettings = FeatureSettingsManager.getPlayModesSettings(TextConstants.allOff);
        HashMap<LotcheckRemotePresenceEnum, String> lotcheckSettings = FeatureSettingsManager.getLotcheckPresenceSettings("Confirm");
        HashMap<DemoEnum, String> demoSettings = FeatureSettingsManager.getDemoSettings(TextConstants.allOff);
        NDPReleaseInfoFeaturesPage releaseFeatures = ndpReleaseInfoPage.nav().clickFeatures();
        WaitUtils.idle(5000);
        NDPReleaseInfoFeaturesProgramSpecsDialog progSpecDialog = releaseFeatures.clickEditProgramSpecifications();
        progSpecDialog.setFeatures(programSpecificationsSettings).clickSave();
        assertTrue(releaseFeatures.isProgramSpecificationsComplete());
        NDPReleaseInfoFeaturesInputDevicesDialog inputDeviceDialog = releaseFeatures.clickEditInputDevices();
        inputDeviceDialog.setFeatures(inputDeviceSettings).clickSave();
        assertTrue(releaseFeatures.isProgramSpecificationsComplete());
        NDPReleaseInfoFeaturesPlayModesDialog playModesDialog = releaseFeatures.clickEditPlayModes();
        playModesDialog.setFeatures(playModesSettings).clickSave();
        assertTrue(releaseFeatures.isPlayModeComplete());
        NDPReleaseInfoFeaturesDemoDialog demoDialog = releaseFeatures.clickEditDemo();
        demoDialog.setFeatures(demoSettings).clickSave();
        assertTrue(releaseFeatures.isDemoComplete());
        NDPReleaseInfoFeaturesLotcheckRemotePresenceDialog lotcheckDialog = releaseFeatures.clickEditLotcheckRemotePresence();
        lotcheckDialog.setFeatures(lotcheckSettings).clickSave();
        assertTrue(releaseFeatures.isLotcheckRemotePresenceComplete());
        assertTrue(releaseFeatures.isAccountSpecificationsNotApplicable());
        releaseFeatures.clickContinue();
        assertTrue(ndpReleaseInfoPage.nav().isFeaturesCompleted());
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = ndpReleaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoGuidelinesPage.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton()
                .waitSaveButtonEnable();
        assertTrue(ndpReleaseInfoPage.nav().isFeaturesCompleted());
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = ndpReleaseInfoPage.nav().clickAgeRating();
        ndpReleaseInfoAgeRating.clickOnAddStandardRatingButton()
                .selectStandardAgeRating("CERO")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectRatingValue("Not required")
                .clickOnSaveButtonInEditStandardRating();
        WaitUtils.idle(5000);
        assertTrue(ndpReleaseInfoPage.nav().isAgeRatingCompleted());
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = ndpReleaseInfoPage.nav().clickROMUpload();
        ndpReleaseInfoROMUpload
                .closeRequiredStepModal()
                .selectRomFileButton()
                .selectFileInPC(fileRomName);
        ndpReleaseInfoROMUpload.clickUpload()
                .clickUploadNow()
                .waitForUploadComplete(TextConstants.romUpload);
        ndpReleaseInfoROMUpload.deleteFile(fileRomName);
        assertTrue(ndpReleaseInfoPage.nav().isROMUploadCompleted());
        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
        issuePage.openRequiredApprovalTab();
        issuePage.performAllIssueAtApprovalTab(false);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
        WaitUtils.idle(4000);
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(licUser);

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
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser).clickMyProducts();
    }

    @Test
    @Order(7)
    public void accessHighwayTool_Step5(IJUnitTestReporter testReport) {
        d4cBrowser = BrowserManager.getNewInstance();
        currentPage = d4cBrowser.openURL(D4CLogin.class, PageState.PRE_LOGIN, testReport);
        D4CLogin d4clogin = (D4CLogin) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        currentPage = d4clogin.signIn(TextConstants.d4c_prdcv_PhongNguyen, "nintendo");
    }

    @Test
    @Order(8)
    public void searchingForApplicableProduct_Step6(IJUnitTestReporter testReport) {
        D4CRomPublishSuit d4CRomPublishSuit = (D4CRomPublishSuit) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        String idApplication = applicationID.substring(2);
        D4CRomTab d4CRomTab = d4CRomPublishSuit.clickTabRom();
        d4CRomTab.enterApplicationID(idApplication)
                .clickSearchButton();
        currentPage = d4CRomTab;
    }

    @Test
    @Order(9)
    public void selectUploadROMFile_Step7(IJUnitTestReporter testReport) {
        D4CRomTab d4CRomTab = (D4CRomTab) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        romID = d4CRomTab.getRomId();
        currentPage = d4CRomTab.clickROMByID(romID);
    }

    @Test
    @Order(10)
    public void submitThisRomViaHighwayTool_Step8(IJUnitTestReporter testReport) {
        D4CRomDetails d4CRomDetails = (D4CRomDetails) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        d4CRomDetails.clickSubmitButton()
                .enterProductCodeTextbox(gameCode)
                .clickConfirmButtonInROMSubmitRequestForm()
                .waitingForSubmitRequestFormLoad()
                .clickOKButtonInROMSubmitRequestForm();
        WaitUtils.idle(15000);
        BrowserManager.closeBrowser(d4cBrowser);
    }

    @Test
    @Order(11)
    public void submitReleaseToLotcheck_Step9(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        NDPSubmissionReviewPage ndpSubmissionReviewPage = ndpReleaseInfoPage.nav().clickSubmitButton();
        ndpSubmissionReviewPage.enterDeveloperComment(TextConstants.enterDeveloperComment)
                .clickSubmitWithError()
                .refreshCurrentPage();
        WaitUtils.idle(4000);
        currentPage = ndpSubmissionReviewPage;
    }

    @Test
    @Order(12)
    public void navigateToReleaseInfoSection_Step10(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        statusEnglishInReleaseInfoPage = ndpReleaseInfoPage.getStatus();
        ndpReleaseInfoPage.clickOnUserNameMenu().clickJapanButton().waitIsLoaded();
        statusJapaneseInReleaseInfoPage = ndpReleaseInfoPage.getStatusInJapan();
        currentPage = ndpReleaseInfoPage;
    }

    @Test
    @Order(13)
    public void verifyTheStatus_Expected10(IJUnitTestReporter testReport) {
        assertAll(() -> assertEquals(statusEnglishInReleaseInfoPage, "Submitting to Lotcheck", "Verify Status in EN is correct"),
                () -> assertEquals(statusJapaneseInReleaseInfoPage, "ロットチェックへ提出中", " Verify Status in JP is correct"));
    }

    @Test
    @Order(14)
    public void navigateToReleaseTab_Step11(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpReleaseInfoPage.clickOnUserNameMenu().clickENLanguage().waitIsLoaded();
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpReleaseInfoPage.clickBackToRelease();
        statusEnglishInDashboardReleasesTab = ndpProductDashboardReleasesTab.getStatusValue();
        ndpProductDashboardReleasesTab.clickOnUserNameMenu().clickJapanButton().waitIsLoaded();
        statusJapaneseInDashboardReleasesTab = ndpProductDashboardReleasesTab.getStatusValue();
    }

    @Test
    @Order(15)
    public void verifyTheStatus_Expected11(IJUnitTestReporter testReport) {
        assertAll(() -> assertEquals(statusEnglishInDashboardReleasesTab, "Submitting to Lotcheck", "Verify Status in EN is correct"),
                () -> assertEquals(statusJapaneseInDashboardReleasesTab, "ロットチェックへ提出中", " Verify Status in JP is correct"));
    }

    @Test
    @Order(16)
    public void navigatesToMembersTab_Step12(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        myProductsPage.clickOnUserNameMenu().clickEnglishButton().waitIsLoaded();
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardMembersTab ndpProductDashboardMembersTab = ndpProductDashboardPage.clickMembersTab();
        descriptionInUserTabActualEN = ndpProductDashboardMembersTab.getDescriptionInUsersTabDisplay();
        descriptionInUserTabExpectedEN = "Product Admins can add or remove users and groups who have access to the product.\n" +
                "To add a user who is outside your organization, a Company Admin or Company Manager must perform additional procedures in advance.";
        ndpProductDashboardMembersTab.clickOnUserNameMenu().clickJapanButton().waitIsLoaded();
        descriptionInUserTabActualJP = ndpProductDashboardMembersTab.getDescriptionInUsersTabDisplay();
        descriptionInUserTabExpectedJP = "Product Admin は、プロダクトにアクセスできるユーザーやグループの追加・削除を行うことができます。\n" +
                "組織外のユーザーを追加する場合は、Company Admin もしくは Company Manager が事前に追加の手続きを行う必要があります。";
    }

    @Test
    @Order(17)
    public void verifyDescriptionIsDisplayed_Expected12(IJUnitTestReporter testReport) {
        assertAll(() -> assertEquals(descriptionInUserTabExpectedEN, descriptionInUserTabActualEN, "Verify description in EN is displayed correct"),
                () -> assertEquals(descriptionInUserTabExpectedJP, descriptionInUserTabActualJP, "Verify description in JP is display correct"));
    }

    @Test
    @Order(18)
    public void navigateToMembersTabAndClickOnAddUserButton_Step13(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        myProductsPage.clickOnUserNameMenu().clickEnglishButton().waitIsLoaded();
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardMembersTab ndpProductDashboardMembersTab = ndpProductDashboardPage.clickMembersTab();
        ndpProductDashboardMembersTab.clickAddUserButton();
        currentPage = ndpProductDashboardMembersTab;
    }

    @Test
    @Order(19)
    public void verifyAddUserPageIsDisplayed_Expected13(IJUnitTestReporter testReport) {
        NDPProductDashboardMembersTab ndpProductDashboardMembersTab = (NDPProductDashboardMembersTab) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertAll(() -> assertTrue(ndpProductDashboardMembersTab.isHeaderAddUserDisplayed(), "Verify Add Users Page is displayed"),
                () -> assertTrue(ndpProductDashboardMembersTab.isMyCompanyRadioEnabled(), "Verify user can select My Company"),
                () -> assertTrue(ndpProductDashboardMembersTab.isExternalCompanyEnabled(), "Verify user can select External Company"));
    }

    @Test
    @Order(20)
    public void selectMyCompanyAndViewTheListOfMembers_Step14(IJUnitTestReporter testReport) {
        NDPProductDashboardMembersTab ndpProductDashboardMembersTab = (NDPProductDashboardMembersTab) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpProductDashboardMembersTab.selectMyCompanyRadioButton()
                .selectInternalUser()
                .selectAccessRoleInMembersTab(TextConstants.contributor_User)
                .clickAddUserButtonInPopup();
        currentPage = ndpProductDashboardMembersTab;
    }

    @Test
    @Order(21)
    public void verifyTheListMembersDisplayed_Expected14(IJUnitTestReporter testReport) {
        NDPProductDashboardMembersTab ndpProductDashboardMembersTab = (NDPProductDashboardMembersTab) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertAll(() -> assertTrue(ndpProductDashboardMembersTab.isUserNameDisplayed(), "Verify member displayed with User Name information"),
                () -> assertTrue(ndpProductDashboardMembersTab.isFirstNameDisplayed(), "Verify member displayed with First Name information"),
                () -> assertTrue(ndpProductDashboardMembersTab.isLastNameDisplayed(), "Verify member displayed with Last Name information"));
    }

    @Test
    @Order(22)
    public void selectUserAndAccessRoleAndSelectedUser_Step15(IJUnitTestReporter testReport) {
        NDPProductDashboardMembersTab ndpProductDashboardMembersTab = (NDPProductDashboardMembersTab) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpProductDashboardMembersTab.clickAddUserButton()
                .selectInternalUser()
                .selectAccessRoleInMembersTab(TextConstants.contributor_User)
                .clickAddUserButtonInPopup();
        userName = ndpProductDashboardMembersTab.getUserName();
        accessRole = ndpProductDashboardMembersTab.getAccessRole();
        colourRemoveButton = ndpProductDashboardMembersTab.getColourRemoveButton();
        isRemoveButtonDisplayed = ndpProductDashboardMembersTab.removeButtonIsDisplayed();
    }

    @Test
    @Order(23)
    public void verifyExpected_Expected15(IJUnitTestReporter testReport) {
        NDPProductDashboardMembersTab ndpProductDashboardMembersTab = (NDPProductDashboardMembersTab) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPMyProductsPage ndpMyProductsPage = ndpProductDashboardMembersTab.clickUserMenuDropdownList()
                .clickSignOutButton()
                .clickSignInPage()
                .signIn(userName, "nintendo")
                .clickMyProducts();
        productNameCreate = ndpMyProductsPage.getProductNameInRow(1);
        ndpMyProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser);
        assertAll(() -> assertEquals(accessRole, TextConstants.contributor_User, "Verify Access of user added is Contributor"),
                () ->  assertTrue(isRemoveButtonDisplayed, "Verify Remove Button is displayed"),
                () -> assertEquals("#FF0000", colourRemoveButton, "Verify Remove button has red colour"),
                () -> assertEquals(productName, productNameCreate, "Verify newly-added Product Member can access the Product"));
    }

    @Test
    @Order(24)
    public void navigatesToProductClickRemoveLinkAtUserAndConfirmRemove_Step16(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardMembersTab ndpProductDashboardMembersTab = ndpProductDashboardPage.clickMembersTab();
        ndpProductDashboardMembersTab.clickRemoveButton()
                .clickConfirmRemoveButton().waitIsLoaded();
        WaitUtils.idle(5000);
        currentPage = ndpProductDashboardMembersTab;
    }

    @Test
    @Order(25)
    public void verifyTheUserIsRemovedFromListMembers_Expected16(IJUnitTestReporter testReport) {
        NDPProductDashboardMembersTab ndpProductDashboardMembersTab = (NDPProductDashboardMembersTab) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertFalse(ndpProductDashboardMembersTab.isUserNameDisplayed(), "Verify User is removed from list members");
    }

    @Test
    @Order(26)
    public void clickOnAddGroupButton_Step17(IJUnitTestReporter testReport) {
        NDPProductDashboardMembersTab ndpProductDashboardMembersTab = (NDPProductDashboardMembersTab) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpProductDashboardMembersTab.clickUserGroupsTab()
                .clickAddGroupButton();
    }

    @Test
    @Order(27)
    public void verifyNavigatesToAddGroupsPage_Expected17(IJUnitTestReporter testReport) {
        NDPProductDashboardMembersTab ndpProductDashboardMembersTab = (NDPProductDashboardMembersTab) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertAll(() -> assertTrue(ndpProductDashboardMembersTab.isHeaderAddGroupDisplayed(), "Verify Add Groups Page is displayed"),
                () -> assertTrue(ndpProductDashboardMembersTab.isListGroupInAddGroupDialogDisplayed(), "Verify list of groups within their company are displayed"));
    }

    @Test
    @Order(28)
    public void selectGroupFromTheList_Step18(IJUnitTestReporter testReport) {
        NDPProductDashboardMembersTab ndpProductDashboardMembersTab = (NDPProductDashboardMembersTab) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpProductDashboardMembersTab.selectGroupName();
    }

    @Test
    @Order(29)
    public void verifyGroupNameCheckboxIsChecked_Expected18(IJUnitTestReporter testReport) {
        NDPProductDashboardMembersTab ndpProductDashboardMembersTab = (NDPProductDashboardMembersTab) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertTrue(ndpProductDashboardMembersTab.isGroupNameCheckboxSelected(), "Verify Group name checkbox is selected");
    }

    @Test
    @Order(30)
    public void selectAccessRoleAndClickAddGroupButton_Step19(IJUnitTestReporter testReport) {
        NDPProductDashboardMembersTab ndpProductDashboardMembersTab = (NDPProductDashboardMembersTab) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpProductDashboardMembersTab.selectAccessRole(TextConstants.contributor_User)
                .clickAddGroupButtonInPopup();
        addGroupSuccessMessage = ndpProductDashboardMembersTab.getAddMemberAndGroupSuccessMessage();
    }

    @Test
    @Order(31)
    public void verifyGroupIsAddedSuccessfully_Expected19(IJUnitTestReporter testReport) {
        NDPProductDashboardMembersTab ndpProductDashboardMembersTab = (NDPProductDashboardMembersTab) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpProductDashboardMembersTab.clickUserGroupsTab();
        assertAll(() -> assertEquals(addGroupSuccessMessage, "Group membership(s) added succesfully.", "Verify add group success message is displayed"),
                () -> assertTrue(ndpProductDashboardMembersTab.isGroupNameDisplayed(), "Verify group is added successfully"));
    }

    @Test
    @Order(32)
    public void clickOnRemoveButtonOfAGroupAndConfirmRemove_Step20(IJUnitTestReporter testReport) {
        NDPProductDashboardMembersTab ndpProductDashboardMembersTab = (NDPProductDashboardMembersTab) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpProductDashboardMembersTab.clickRemoveButton()
                .clickConfirmRemoveButton()
                .waitIsLoaded();
        WaitUtils.idle(5000);
    }

    @Test
    @Order(33)
    public void verifyGroupIsRemoveSuccessfully_Expected20(IJUnitTestReporter testReport) {
        NDPProductDashboardMembersTab ndpProductDashboardMembersTab = (NDPProductDashboardMembersTab) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertFalse(ndpProductDashboardMembersTab.isGroupNameDisplayed(), "Verify group is remove successfully");
    }

    @Test
    @Order(34)
    public void navigateToExternalCompaniesTab_Step21(IJUnitTestReporter testReport) {
        NDPProductDashboardMembersTab ndpProductDashboardMembersTab = (NDPProductDashboardMembersTab) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpProductDashboardMembersTab.clickExternalCompaniesTab();
    }

    @Test
    @Order(35)
    public void verifyDescription_Expected21(IJUnitTestReporter testReport) {
        NDPProductDashboardMembersTab ndpProductDashboardMembersTab = (NDPProductDashboardMembersTab) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        String descriptionInExternalCompaniesTabActualEN = ndpProductDashboardMembersTab.getDescriptionInExternalCompaniesDisplay();
        String descriptionInExternalCompaniesTabExpectedEN = "Only a Company Admin or a Company Manager can add external users and companies.";
        NDPMyProductsPage ndpMyProductsPage = ndpProductDashboardMembersTab.clickBackToMyProductsButton();
        ndpMyProductsPage.clickOnUserNameMenu().clickJapanButton().waitIsLoaded();
        NDPProductDashboardMembersTab ndpProductDashboardMembersTab1 = ndpMyProductsPage.clickProductByName(productName).clickMembersTab().clickExternalCompaniesTab();
        String descriptionInExternalCompaniesTabActualJP = ndpProductDashboardMembersTab1.getDescriptionInExternalCompaniesDisplay();
        String descriptionInExternalCompaniesTabExpectedJP = "外部ユーザー・会社を追加することができるのは、Company Admin と Company Manager となります。";
        assertAll(() -> assertEquals(descriptionInExternalCompaniesTabExpectedEN, descriptionInExternalCompaniesTabActualEN, "Verify description in EN is correct"),
                () -> assertEquals(descriptionInExternalCompaniesTabExpectedJP, descriptionInExternalCompaniesTabActualJP, "Verify description in JP is correct"));
    }

    @Test
    @Order(36)
    public void logInSLCMSAsLotcheckUser_Step22(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        currentPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
    }

    @Test
    @Order(37)
    public void navigateToOverallLotcheck_Step23(IJUnitTestReporter testReport) {
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        currentPage = lotcheckLandingPage.clickOverallLotcheck(LotcheckRegion.NCL);
    }

    @Test
    @Order(38)
    public void searchingForSubmittedRelease_Step24(IJUnitTestReporter testReport) {
        LotcheckQueuePage lotcheckQueuePage = (LotcheckQueuePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        lotcheckQueuePage.searchByInitialCode(initialCode)
                .clickPlannedTestDateNotSet();
    }

    @Test
    @Order(39)
    public void verifyExpected_Expected24(IJUnitTestReporter testReport) {
        LotcheckQueuePage lotcheckQueuePage = (LotcheckQueuePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertAll(() -> assertTrue(lotcheckQueuePage.isDisplayedTestPlan(gameCode), "Verify there is corresponding submission"),
                () -> assertEquals("Not Submitted", lotcheckQueuePage.getTestPlanStatus(13), "Verify the status is submitted"));
    }

    @Test
    @Order(40)
    public void navigateToLCR_Step25(IJUnitTestReporter testReport) {
        LotcheckQueuePage lotcheckQueuePage = (LotcheckQueuePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        lotcheckReportSubmissionOverview.waitingForIconNotVisible();
        currentPage = lotcheckReportSubmissionOverview;
    }

    @Test
    @Order(41)
    public void verifyStatus_Expected25(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = (LotcheckReportSubmissionOverview) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        HashMap<LotcheckReportSubmissionOverview.SubmissionOverviewAttribute, String> attributes = lotcheckReportSubmissionOverview.getOverviewAttributes();
        String internalStatus = attributes.get(LotcheckReportSubmissionOverview.SubmissionOverviewAttribute.InternalStatus);
        assertEquals("SUBMITTING", internalStatus, "Verify the status is Submitting");
    }

    @Test
    @Order(42)
    public void navigateToTesterAssignmentQueue_Step26(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        currentPage = lotcheckLandingPage.clickTesterAssignment(LotcheckRegion.NCL);
    }

    @Test
    @Order(43)
    public void searchingForSubmittedRelease_Step27(IJUnitTestReporter testReport) {
        LotcheckQueuePage lotcheckQueuePage = (LotcheckQueuePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        lotcheckQueuePage.searchByInitialCode(initialCode)
                .clickPlannedTestDateNotSetIfNull();
    }

    @Test
    @Order(44)
    public void verifyNoCorrespondingSubmission_Expected27(IJUnitTestReporter testReport) {
        LotcheckQueuePage lotcheckQueuePage = (LotcheckQueuePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertFalse(lotcheckQueuePage.isDisplayedTestPlan(gameCode), "Verify there is no corresponding submission");
    }

    @Test
    @Order(45)
    public void logInTestrailAsAdmin_Step28(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        currentPage = testRailLoginPage.signInTestRail(testRailUser);
    }

    @Test
    @Order(46)
    public void navigateToManagerDashboardTab_Step29(IJUnitTestReporter testReport) {
        TestRailDashboardPage testRailDashboardPage = (TestRailDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        currentPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
    }

    @Test
    @Order(47)
    public void searchingForSubmittedRelease_Step30(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        testRailManagerDashboardPage.waitingLoadingTestPlan()
                .actionFill()
                .enterTestPlanNameFilter(initialCode)
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan();
    }

    @Test
    @Order(48)
    public void verifyNoCorrespondingTestPlan_Expected30(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertFalse(testRailManagerDashboardPage.isTestPlanDisplayed(gameCode), "Verify there is no corresponding test plan");
    }

    @Test
    @Order(49)
    public void accessHighwayTool_Step31(IJUnitTestReporter testReport) {
        d4cBrowser = BrowserManager.getNewInstance();
        currentPage = d4cBrowser.openURL(D4CLogin.class, PageState.PRE_LOGIN, testReport);
        D4CLogin d4clogin = (D4CLogin) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        currentPage = d4clogin.signIn(TextConstants.d4c_prdcv_PhongNguyen, "nintendo");
    }

    @Test
    @Order(50)
    public void searchingForApplicableProduct_Step32(IJUnitTestReporter testReport) {
        D4CRomPublishSuit d4CRomPublishSuit = (D4CRomPublishSuit) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        String idApplication = applicationID.substring(2);
        D4CRomTab d4CRomTab = d4CRomPublishSuit.clickTabRom();
        d4CRomTab.enterApplicationID(idApplication)
                .clickSearchButton();
        currentPage = d4CRomTab;
    }

    @Test
    @Order(51)
    public void selectUploadROMFile_Step33(IJUnitTestReporter testReport) {
        D4CRomTab d4CRomTab = (D4CRomTab) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        romID = d4CRomTab.getRomId();
        currentPage = d4CRomTab.clickROMByID(romID);
    }

    @Test
    @Order(52)
    public void selectRejectButtonThenRejectThisROM_Step34(IJUnitTestReporter testReport) {
        D4CRomDetails d4CRomDetails = (D4CRomDetails) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        d4CRomDetails.clickRejectButton()
                .clickOKButtonInROMApprovalRequestForm().waitIsLoaded();
        WaitUtils.idle(15000);
        BrowserManager.closeBrowser(d4cBrowser);
    }


    @Test
    @Order(53)
    public void submitApplicableReleaseToLotcheck_Step35(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        myProductsPage.clickOnUserNameMenu().clickEnglishButton().waitIsLoaded();
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        NDPSubmissionReviewPage ndpSubmissionReviewPage = ndpReleaseInfoPage.nav().clickSubmitButton();
        ndpSubmissionReviewPage.enterDeveloperComment(TextConstants.enterDeveloperComment)
                .clickSubmit();
        WaitUtils.idle(5000);
    }

    @Test
    @Order(54)
    public void logInSLCMSAsLotcheckUser_Step36(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        WaitUtils.idle(90000);
        currentPage = lotcheckLandingPage;
    }

    @Test
    @Order(55)
    public void navigateToOverallLotcheck_Step37(IJUnitTestReporter testReport) {
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        currentPage = lotcheckLandingPage.clickOverallLotcheck(LotcheckRegion.NCL);
    }

    @Test
    @Order(56)
    public void searchingForSubmittedRelease_Step38(IJUnitTestReporter testReport) {
        LotcheckQueuePage lotcheckQueuePage = (LotcheckQueuePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        lotcheckQueuePage.searchByInitialCode(initialCode)
                .clickPlannedTestDateNotSet();
    }

    @Test
    @Order(57)
    public void verifyExpected_Expected38(IJUnitTestReporter testReport) {
        LotcheckQueuePage lotcheckQueuePage = (LotcheckQueuePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertAll(() -> assertTrue(lotcheckQueuePage.isDisplayedTestPlan(gameCode), "Verify there is corresponding submission"),
                () -> assertEquals("Assign Testers", lotcheckQueuePage.getTestPlanStatus(14), "Verify the status is Assign Tester"));
    }

    @Test
    @Order(58)
    public void navigateToLCR_Step39(IJUnitTestReporter testReport) {
        LotcheckQueuePage lotcheckQueuePage = (LotcheckQueuePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        lotcheckReportSubmissionOverview.waitingForIconNotVisible();
        currentPage = lotcheckReportSubmissionOverview;
    }

    @Test
    @Order(59)
    public void verifyStatus_Expected39(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = (LotcheckReportSubmissionOverview) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        HashMap<LotcheckReportSubmissionOverview.SubmissionOverviewAttribute, String> attributes = lotcheckReportSubmissionOverview.getOverviewAttributes();
        String internalStatus = attributes.get(LotcheckReportSubmissionOverview.SubmissionOverviewAttribute.InternalStatus);
        assertEquals("SUBMITTED", internalStatus, "Verify the status is Submitted");
    }

    @Test
    @Order(60)
    public void navigateToTesterAssignmentQueue_Step40(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        currentPage = lotcheckLandingPage.clickTesterAssignment(LotcheckRegion.NCL);
    }

    @Test
    @Order(61)
    public void searchingForSubmittedRelease_Step41(IJUnitTestReporter testReport) {
        LotcheckQueuePage lotcheckQueuePage = (LotcheckQueuePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        lotcheckQueuePage.searchByInitialCode(initialCode)
                .clickPlannedTestDateNotSetIfNull();
    }

    @Test
    @Order(62)
    public void verifyThereIsCorrespondingSubmission_Expected41(IJUnitTestReporter testReport) {
        LotcheckQueuePage lotcheckQueuePage = (LotcheckQueuePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertTrue(lotcheckQueuePage.isDisplayedTestPlan(gameCode), "Verify there is corresponding submission");
    }

    @Test
    @Order(63)
    public void navigateToManagerDashboardTab_Step43(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN);
        TestRailDashboardPage testRailDashboardPage = (TestRailDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        currentPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
    }

    @Test
    @Order(64)
    public void searchingForSubmittedRelease_Step44(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        testRailManagerDashboardPage.waitingLoadingTestPlan()
                .actionFill()
                .enterTestPlanNameFilter(initialCode)
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan();
    }

    @Test
    @Order(65)
    public void verifyThereIsCorrespondingTestPlan_Expected44(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertTrue(testRailManagerDashboardPage.isTestPlanDisplayed(gameCode), "Verify there is no corresponding test plan");
    }
}
