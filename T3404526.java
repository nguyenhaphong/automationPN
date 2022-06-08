package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.junit.TestLogger;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.data.DataGen;
import net.nintendo.automation.ui.data.features.FeatureSettingsManager;
import net.nintendo.automation.ui.data.features.enums.DownloadableContentEnum;
import net.nintendo.automation.ui.data.features.enums.InputDevicesEnum;
import net.nintendo.automation.ui.data.features.enums.LotcheckRemotePresenceEnum;
import net.nintendo.automation.ui.data.features.enums.PlayModesEnum;
import net.nintendo.automation.ui.data.features.enums.ProgramSpecificationsEnum;
import net.nintendo.automation.ui.data.features.enums.ProgramSpecificationsOnCardAOCEnum;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthPage;
import net.nintendo.automation.ui.models.lcms.LotcheckHomePage;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.ui.models.lcms.LotcheckQueuePage;
import net.nintendo.automation.ui.models.lcms.LotcheckSubmissionSearchPage;
import net.nintendo.automation.ui.models.lcms.LotcheckTestPlanRow;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportProductProductInformation;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionMetadata;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.ndp.NDPCreateProductPage;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPLicensingWaiverRequestsTab;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.NDPViewTaskPage;
import net.nintendo.automation.ui.models.ndp.admin.NDPOrganizationManagement;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_internal.NDPProductSearchPage;
import net.nintendo.automation.ui.models.ndp.existing_internal.NDPSponsorQueuePage;
import net.nintendo.automation.ui.models.ndp.existing_internal.view_product_dashboard.NDPSettingTab;
import net.nintendo.automation.ui.models.ndp.existing_internal.view_product_dashboard.NDPViewProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPCreateDLCReleaseDialog;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPEditPublishingDialog;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardDownloadableContentTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardProductInfoTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardReleasesTab;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoFeaturesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoIssuePage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoROMUpload;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPReleaseInfoFeaturesInputDevicesDialog;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPReleaseInfoFeaturesLotcheckRemotePresenceDialog;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPReleaseInfoFeaturesPlayModesDialog;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPReleaseInfoFeaturesProgramSpecsOncardAOCDialog;
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

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Tag("T3404526")
//@ScenarioTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3404526 extends CommonBaseTest {
    private static Browser browser;
    private static User romUser;
    private static Browser newbrowser;
    private static User ndpUser;
    private static User slcmsUser;
    private static User testrailUser;
    private static User licUser;

    private static String gameCode_BaseProduct;
    private static String applicationId_BaseProduct;
    private static String rom_BasedAOCName;
    private static String rom_BasedApplicationName;
    private static String rom_OncardAOCName;
    private static String initialCode_BaseProduct;
    private static String initialCode_OncardAOC;
    private static String productName_BaseProduct;
    private static String productName_OncardAOC;
    private static String publishingRelationship_BaseProduct;
    private static String developmentROMIDHashValue_BaseProduct;
    private static String gameCode_OncardAOC;
    private static CommonUIComponent currentPage;
    private static TestLogger logger;
    private static String orgName;
    private static Browser newBrowser;
    private static CommonUIComponent currentNewPage;
    private static String fileName;
    private static String currentVersion;
    private static int preVersion;
    private static int numberTCCurrentVersion;
    private static int numberTCPreviousVersion;
    private static int numberTCAdded;
    private static int numberTCGuidelineAdded;
    private static String testPlanNameCurrentVersion;
    private static String testPlanNamePreviousVersion;
    private static int numberTCCurrentInHACGuideline;
    private static int numberTCPreviousInHACGuideline;
    private static String regionProduct;

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_DungDTT);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
        slcmsUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);

        //testrailUser = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER,TextConstants.trAdminNCL_Email);
        testrailUser = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER);
        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);
        publishingRelationship_BaseProduct = TextConstants.third_Party_Type;
        productName_OncardAOC = DataGen.getRandomProductName();
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);

    }

    @AfterAll
    public static void terminateBrowser() {
        BrowserManager.closeBrowser(browser);
        BrowserManager.closeBrowser(newbrowser);
        UserManager.releaseUser(ServiceType.NDP, ndpUser);
        UserManager.releaseUser(ServiceType.SLCMS, slcmsUser);
        UserManager.releaseUser(ServiceType.SLCMS, testrailUser);
        UserManager.releaseUser(ServiceType.SLCMS, romUser);
    }

    public static void submitInitialToLotcheckAndCreateOnCardRom(IJUnitTestReporter testReport) {
        productName_BaseProduct = DataGen.getRandomProductName();
        NDPHomePage homePage = (NDPHomePage) currentPage;
        NDPMyProductsPage myProductsPage = homePage.acceptCookie().clickSignInPage().signIn(ndpUser).clickMyProducts();
        myProductsPage.createNewProduct()
                .selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(publishingRelationship_BaseProduct)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalJapanSalesRegion()
                .selectProductName(productName_BaseProduct)
                .selectProductNameKana(TextConstants.productKana)
                .clickCreateButton();
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName_BaseProduct).issueGameCode()
                .setExpectedLotcheckSubmissionDate()
                .setExpectedReleaseDate()
                .selectFreeToPlay("NO")
                .clickIssue();
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();
        applicationId_BaseProduct = ndpProductDashboardProductInfoTab.getApplicationID();
        gameCode_BaseProduct = ndpProductDashboardProductInfoTab.getGameCode();
        initialCode_BaseProduct = ndpProductDashboardProductInfoTab.getInitialCode();

        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        NDPReleaseInfoPage releaseInfoPage = ndpReleaseInfoPage.nav().clickReleaseInfo();
        CommonAction.selectROMSameAllRegion(releaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);
        HashMap<ProgramSpecificationsEnum, String> programSpecificationsSettings = FeatureSettingsManager.getProgramSpecificationsSettings(TextConstants.allOff);
        HashMap<InputDevicesEnum, String> inputDeviceSettings = FeatureSettingsManager.getInputDevicesSettings(TextConstants.allOff);
        HashMap<PlayModesEnum, String> playModesSettings = FeatureSettingsManager.getPlayModesSettings(TextConstants.allOff);
        HashMap<LotcheckRemotePresenceEnum, String> lotcheckSettings = FeatureSettingsManager.getLotcheckPresenceSettings("Confirm");
        releaseInfoPage.nav().clickFeatures()
                .clickEditProgramSpecifications()
                .setFeatures(programSpecificationsSettings)
                .clickSave()
                .clickEditInputDevices()
                .setFeatures(inputDeviceSettings).clickSave()
                .clickEditPlayModes()
                .setFeatures(playModesSettings).clickSave()
                .clickEditLotcheckRemotePresence()
                .setFeatures(lotcheckSettings).clickSave()
                .clickContinue();
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
        homePage = (NDPHomePage) currentPage;
        myProductsPage = homePage.clickSignInPage().signIn(ndpUser).clickMyProducts();
        ndpProductDashboardReleasesTab = myProductsPage.clickProductByGameCode(initialCode_BaseProduct).clickReleases();
        releaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = releaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, rom_BasedApplicationName);
        releaseInfoPage.nav().clickIssues().openRequiredApprovalTab().performAllIssueAtApprovalTab(false);

        NDPDevelopmentHome developmentHome = ndpReleaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        licensingWaiverRequestsTab.waitingForResultTableLoaded()
                .enterSearchingBox(initialCode_BaseProduct)
                .clickOnMagnifyingIconButton()
                .waitingForResultTableLoaded();
        int numberOfRequestByPendingStatus = licensingWaiverRequestsTab.collectNumberOfRequestByPendingStatus();
        for (int i = 0; i < numberOfRequestByPendingStatus; i++) {
            developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton().clickOnLicensingWaiverRequestsTab();
            licensingWaiverRequestsTab.waitingForResultTableLoaded()
                    .enterSearchingBox(initialCode_BaseProduct)
                    .enterSearchingBox(initialCode_BaseProduct)
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
                .clickMyProducts().clickProductByGameCode(gameCode_BaseProduct).clickReleases().selectInitialRelease().nav().clickSubmitButton()
                .clickSubmit();
        WaitUtils.idle(3000);
    }

    private static void testRailJudgment(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        TestRailDashboardPage testRailDashboardPage = testRailLoginPage.signInTestRail(testrailUser);
        WaitUtils.idle(3000);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        for (int i = 1; i < 3; i++) {
            if (i == 2) {
                testRailManagerDashboardPage.logFail();
            }
            testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(initialCode_BaseProduct)
                    .selectStatusUntested()
                    .selectStatusPassed();
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

    private static void approveJudgmentInSLCMS(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickJudgment(LotcheckRegion.NCL);
        lotcheckQueuePage.searchByInitialCode(initialCode_BaseProduct);
        int i = 0;
        while (i <= 70) {
            lotcheckQueuePage.clickSearchIcon();
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

    private static void createRom(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        ROMLoginPage romLoginPage = (ROMLoginPage) currentPage;
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom.selectRequester(TextConstants.test_User)
                .selectType(TextConstants.initial_Release)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .enterProductName(productName_BaseProduct)
                .enterApplicationId(applicationId_BaseProduct)
                .selectPublishingRelationShip(publishingRelationship_BaseProduct)
                .enterDisplayVersion("1.0.0")
                .enterReleaseVersion("00")
                .selectCERORating("CERO: A")
                .clickOnCreateButton()
                .waitIsLoaded();
        rom_BasedApplicationName = romCreateRom.getRomNameTable(applicationId_BaseProduct);
        romCreateRom.clickOnROMFileOnROMTable(rom_BasedApplicationName);

        romCreateRom.selectRequester(TextConstants.test_User)
                .selectType(TextConstants.aoc_Release)
                .enterApplicationId(applicationId_BaseProduct)
                .enterReleaseVersion("0")
                .enterIndex("1")
                .enterReqAppVersion("0")
                .enterTagName("Tag name")
                .clickOnCreateButton()
                .waitIsLoaded();
        rom_BasedAOCName = romCreateRom.getRomNameTable(applicationId_BaseProduct);
        romCreateRom.clickOnROMFileOnROMTable(rom_BasedAOCName);

        romCreateRom.selectRequester(TextConstants.test_User)
                .selectType(TextConstants.onCard_Product)
                .selectBasedApplicationOrPatchFile(rom_BasedApplicationName)
                .selectBasedAOCFiles(rom_BasedAOCName)
                .clickOnCreateButton()
                .waitIsLoaded();
        rom_OncardAOCName = romCreateRom.getRomNameTable(applicationId_BaseProduct);
        romCreateRom.clickOnROMFileOnROMTable(rom_OncardAOCName);
    }

    private static void submitDLCReleaseToLotcheck(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;

        NDPProductDashboardDownloadableContentTab downloadableContentTab = myProductsPage.clickProductByGameCode(initialCode_BaseProduct).clickDownloadableContent();
        NDPCreateDLCReleaseDialog createDLCReleaseDialog = downloadableContentTab.clickCreateDLCArchive();
        createDLCReleaseDialog.selectExpectedLotcheckSubmissionDate(15)
                .selectExpectedReleaseDate(90)
                .clickCreate();
        NDPReleaseInfoPage ndpReleaseInfoPage = downloadableContentTab.clickDLCReleaseByArchiveNo("00")
                .nav().clickReleaseInfo()
                .selectRomsThatDifferBetweenRegion(TextConstants.selectRomSameAllRegion)
                .selectLanguageUsedForTextEntry(TextConstants.english)
                .save();
        HashMap<LotcheckRemotePresenceEnum, String> lotcheckSettings = FeatureSettingsManager.getLotcheckPresenceSettings("Confirm");
        HashMap<DownloadableContentEnum, String> downloadableContentSettings = FeatureSettingsManager.getDownloadableContentSettings("Confirm");
        NDPReleaseInfoFeaturesPage releaseFeatures = ndpReleaseInfoPage.nav().clickFeatures()
                .clickEditLotcheckRemotePresence()
                .setFeatures(lotcheckSettings).clickSave()
                .clickEditDownloadableContent()
                .setFeatures(downloadableContentSettings).clickSave();
        ndpReleaseInfoPage.nav().clickGuidelines().selectCompliesWithAllOfTheAboveGuidelines().clickOnSaveButton();
        ndpReleaseInfoPage.nav().clickTestScenarios().expandAllSections()
                .expandAllTestScenes()
                .enterNameHowToCheckDLC("Name How To Check DLC")
                .enterExplainHowToCheckDLC("Explain How To Check DLC")
                .clickOnSaveButton();
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = ndpReleaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, rom_BasedAOCName);
        ndpReleaseInfoPage.nav().clickIssues().openRequiredApprovalTab().performAllIssueAtApprovalTab(false);
        NDPDevelopmentHome developmentHome = ndpReleaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        licensingWaiverRequestsTab.waitingForResultTableLoaded()
                .enterSearchingBox(initialCode_BaseProduct)
                .clickOnMagnifyingIconButton()
                .waitingForResultTableLoaded();
        int numberOfRequestByPendingStatus = licensingWaiverRequestsTab.collectNumberOfRequestByPendingStatus();
        for (int i = 0; i < numberOfRequestByPendingStatus; i++) {
            developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton().clickOnLicensingWaiverRequestsTab();
            licensingWaiverRequestsTab.waitingForResultTableLoaded()
                    .enterSearchingBox(initialCode_BaseProduct)
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
                .clickMyProducts().clickProductByGameCode(gameCode_BaseProduct).clickDownloadableContent().clickDLCReleaseByArchiveNo("00").nav().clickSubmitButton()
                .clickSubmit();
        WaitUtils.idle(3000);
    }

    @BeforeEach
    public void each(IJUnitTestReporter testReport) {
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
    }

    @Test()
    @Order(1)
    public void submitInitialReleaseAndCreateOnCardRom_TestCondition(IJUnitTestReporter testReport) {
        submitInitialToLotcheckAndCreateOnCardRom(testReport); //Rom Oncard AOC created on this method
    }

    @Test()
    @Order(2)
    public void judgmentOkForInitialRelease_TestCondition(IJUnitTestReporter testReport) {
        testRailJudgment(testReport);
        approveJudgmentInSLCMS(testReport);
    }

    @Test()
    @Order(3)
    public void submitAOCRelease_TestCondition(IJUnitTestReporter testReport) {
        submitDLCReleaseToLotcheck(testReport);
    }

    @Test()
    @Order(4)
    public void judgmentOkForAOCRelease_TestCondition(IJUnitTestReporter testReport) {
        testRailJudgment(testReport);
        approveJudgmentInSLCMS(testReport);
    }

    @Test()
    @Order(5)
    public void createOnCardAOCProduct(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        currentPage = myProductsPage.createNewProduct();
    }

    @Test()
    @Order(6)
    public void selectProductType() {
        NDPCreateProductPage createProductPage = (NDPCreateProductPage) currentPage;
        createProductPage.selectProductType("On-Card Downloadable Content");
    }

    @Test()
    @Order(7)
    public void selectBaseProduct() {
        NDPCreateProductPage createProductPage = (NDPCreateProductPage) currentPage;
        createProductPage.selectBaseProduct(productName_BaseProduct + " (" + initialCode_BaseProduct + ")");
        currentPage = createProductPage;
        assertAll(() -> assertTrue(createProductPage.baseProductAtTestConditionIsDisplayed(productName_BaseProduct, initialCode_BaseProduct), "Verify base product is displayed on list based product"),
                () -> assertTrue(createProductPage.baseProductAtTestConditionIsSelected(productName_BaseProduct, initialCode_BaseProduct), "Verify base product is selected"));
    }

    @Test()
    @Order(8)
    public void selectCopyFromData() {
        NDPCreateProductPage createProductPage = (NDPCreateProductPage) currentPage;
        createProductPage.selectCopyDataFrom("00.00-Initial Release");
        assertEquals("00.00-Initial Release", createProductPage.getSelectedCopyDataFromValue(), "Verify format of initial release");
    }

    @Test()
    @Order(9)
    public void fillOutOtherRequiredFieldAndFinishCreateProduct() {
        NDPCreateProductPage createProductPage = (NDPCreateProductPage) currentPage;
        createProductPage.selectPublishingRelationship(publishingRelationship_BaseProduct)
                .selectProductName(productName_OncardAOC);
        NDPMyProductsPage myProductsPage = createProductPage.clickCreateButton();
        currentPage = myProductsPage;
        assertTrue(myProductsPage.onCardAOCProductIsDisplayed(productName_OncardAOC, initialCode_BaseProduct, "On-Card Downloadable Content"), "Verify oncard aoc product is displayed on my product");
    }

    @Test()
    @Order(10)
    public void openProductInfoTab() {
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        NDPProductDashboardPage productDashboardPage = myProductsPage.clickProductByName(productName_OncardAOC);
        NDPProductDashboardProductInfoTab productInfoTab = productDashboardPage.clickProductInfo();
        String productType = productInfoTab.getProductTypeValue();
        NDPEditPublishingDialog editPublishingDialog = productInfoTab.clickEditPublishing();
        editPublishingDialog.enterProductNameKana(TextConstants.productKana).clickSave();
        currentPage = productDashboardPage;
        gameCode_OncardAOC = productInfoTab.getGameCode();
        initialCode_OncardAOC = productInfoTab.getInitialCode();
        assertAll(() -> assertTrue(productDashboardPage.downloadableContentTabNotExists(), "Verify download content tab does not exist"),
                () -> assertEquals("On-Card Downloadable Content", productType, "Verify product type"),
                () -> assertEquals(publishingRelationship_BaseProduct, productInfoTab.getPublishingRelationshipValue(), "Verify publishing relationship"), //confirm expect
                () -> assertEquals(initialCode_BaseProduct.substring(0, 4), productInfoTab.getGameCode().substring(6, 10), "Verify game code"),
                () -> assertEquals(applicationId_BaseProduct, productInfoTab.getApplicationID(), "Verify application ID"),
                () -> assertEquals(productName_OncardAOC, productInfoTab.getProductNameValue(), "Verify product name english"));
    }

    @Test()
    @Order(11)
    public void verifyDisplayOfDLCTab() {
        NDPProductDashboardPage productDashboardPage = (NDPProductDashboardPage) currentPage;
        assertTrue(productDashboardPage.issueGameCodeButtonNotExists(), "Verify issue game code button does not exist");

    }

    @Test()
    @Order(12)
    public void confirmDisplayOnReleasesTab() {
        NDPProductDashboardPage productDashboardPage = (NDPProductDashboardPage) currentPage;
        NDPProductDashboardReleasesTab releasesTab = productDashboardPage.clickReleases();
        String releaseType = releasesTab.getReleaseTypeValue();
        String releaseVersion = releasesTab.getReleaseVersionValue();
        String displayVersion = releasesTab.getDisplayVersionValue();
        NDPReleaseInfoPage releaseInfoPage = releasesTab.selectRelease("On-Card Downloadable Content Release")
                .clickEditDisplayVersion().enterNewDisplayVersion("1.0.1").clickSave();
        String displayVersion_AfterEdit = releaseInfoPage.getDisplayVersionValue();
        currentPage = releaseInfoPage;
        assertAll(() -> assertEquals("On-Card Downloadable Content Release", releaseType, "Verify release type"),
                () -> assertEquals("00", releaseVersion, "Verify release version"),
                () -> assertEquals("1.0.0", displayVersion, "Verify display version"),
                () -> assertEquals("1.0.1", displayVersion_AfterEdit, "Verify new display version"));

    }

    @Test()
    @Order(13)
    public void verifyReleaseInfoSection() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        assertAll(() -> assertEquals("1.0.1", releaseInfoPage.getDisplayVersionValue(), "Verify display version"),
                () -> assertEquals("On-Card Downloadable Content Release", releaseInfoPage.getReleaseTypeValue(), "Verify release type"),
                () -> assertEquals("00", releaseInfoPage.getReleaseVersionValue(), "Verify release version"),
                () -> assertEquals("00", releaseInfoPage.getReleaseSubmissionValue(), "Verify release submission"));
    }

    @Test()
    @Order(14)
    public void verifyFeaturesSection() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        NDPReleaseInfoFeaturesPage releaseInfoFeaturesPage = releaseInfoPage.nav().clickFeatures();

        HashMap<ProgramSpecificationsOnCardAOCEnum, String> expectProgramSpecificationsOncardAOC = FeatureSettingsManager.getProgramSpecificationsOncardAOCSettings(TextConstants.allOff);
        HashMap<InputDevicesEnum, String> expectInputDevice = FeatureSettingsManager.getInputDevicesSettings(TextConstants.allOff);
        HashMap<PlayModesEnum, String> expectPlayModes = FeatureSettingsManager.getPlayModesSettings(TextConstants.allOff);
        HashMap<LotcheckRemotePresenceEnum, String> expectLotcheckRemotePresence = FeatureSettingsManager.getLotcheckPresenceSettings("Confirm");

        NDPReleaseInfoFeaturesProgramSpecsOncardAOCDialog programSpecsOncardAOCDialog = releaseInfoFeaturesPage.clickEditProgramSpecificationsOncardAOC();
        HashMap<ProgramSpecificationsOnCardAOCEnum, String> actualProgramSpecificationsOncardAOC = programSpecsOncardAOCDialog.getFeatures(ProgramSpecificationsOnCardAOCEnum.allValues());

        NDPReleaseInfoFeaturesInputDevicesDialog inputDevicesDialog = programSpecsOncardAOCDialog.clickClose().clickEditInputDevices();
        HashMap<InputDevicesEnum, String> actualInputDevice = inputDevicesDialog.getFeatures(InputDevicesEnum.allValues());

        NDPReleaseInfoFeaturesPlayModesDialog playModesDialog = inputDevicesDialog.clickClose().clickEditPlayModes();
        HashMap<PlayModesEnum, String> actualPlayModes = playModesDialog.getFeatures(PlayModesEnum.allValues());

        NDPReleaseInfoFeaturesLotcheckRemotePresenceDialog lotcheckRemotePresenceDialog = playModesDialog.clickClose().clickEditLotcheckRemotePresence();
        HashMap<LotcheckRemotePresenceEnum, String> actualLotcheckRemotePresence = lotcheckRemotePresenceDialog.getFeatures(LotcheckRemotePresenceEnum.allValues());
        lotcheckRemotePresenceDialog.clickClose();
        currentPage = releaseInfoPage;

        HashMap<ProgramSpecificationsOnCardAOCEnum, String> programSpecificationsOncardAOCSettings = new HashMap<>(expectProgramSpecificationsOncardAOC);
        programSpecificationsOncardAOCSettings.put(ProgramSpecificationsOnCardAOCEnum.Input_field_for_notes, "Test edit program specifications ");
        HashMap<InputDevicesEnum, String> inputDeviceSettings = new HashMap<>(expectInputDevice);
        inputDeviceSettings.put(InputDevicesEnum.Input_field_for_notes, "Test edit input devices");
        HashMap<PlayModesEnum, String> playModesSettings = new HashMap<>(expectPlayModes);
        playModesSettings.put(PlayModesEnum.Core_content, "Test edit play modes");
        HashMap<LotcheckRemotePresenceEnum, String> lotcheckSettings = new HashMap<>(expectLotcheckRemotePresence);
        lotcheckSettings.put(LotcheckRemotePresenceEnum.Do_you_agree_to_have_this_release_be_tested_under_the_Lotcheck_Remote_Presence_Guideline, "DECLINE");
        HashMap<DownloadableContentEnum, String> downloadableContentSettings = FeatureSettingsManager.getDownloadableContentSettings("Confirm");
        releaseInfoFeaturesPage.clickEditProgramSpecificationsOncardAOC()
                .setFeatures(programSpecificationsOncardAOCSettings)
                .clickSave();
        releaseInfoFeaturesPage.clickEditInputDevices()
                .setFeatures(inputDeviceSettings).clickSave()
                .clickEditPlayModes()
                .setFeatures(playModesSettings).clickSave()
                .clickEditLotcheckRemotePresence()
                .setFeatures(lotcheckSettings).clickSave()
                .clickEditDownloadableContent()
                .setFeatures(downloadableContentSettings).clickSave().clickContinue();

        assertAll(() -> assertEquals(expectInputDevice, actualInputDevice, "Verify data on Input Devices dialog of oncard aoc and base product"),
                () -> assertEquals(expectProgramSpecificationsOncardAOC, actualProgramSpecificationsOncardAOC, "Verify data on Program Specifications dialog of oncard aoc and base product"),
                () -> assertEquals(expectLotcheckRemotePresence, actualLotcheckRemotePresence, "Verify data on Lotcheck Remote Presence dialog of oncard aoc and base product"),
                () -> assertEquals(expectPlayModes, actualPlayModes, "Verify data on Play Mode dialog of oncard aoc and base product"),
                () -> assertTrue(releaseInfoPage.nav().isFeaturesCompleted(), "Verify that features section is completed"));


    }

    @Test()
    @Order(15)
    public void uploadRom() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        releaseInfoPage.nav().clickReleaseInfo().selectExpectedSubmissionDate().chooseDateInCalender(5);
        releaseInfoPage.selectExpectedReleaseDate().chooseDateInCalender(100);
        releaseInfoPage.selectCardSize("2 GB")
                .selectRomsThatDifferBetweenRegion(TextConstants.selectRomSameAllRegion)
                .selectLanguageUsedForTextEntry(TextConstants.english)
                .save();

        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = releaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, rom_OncardAOCName);
        NDPReleaseInfoIssuePage issuePage = releaseInfoPage.nav().clickIssues().openReleaseErrorsTab();
        assertAll(() -> assertEquals("There are no Release Errors to display", issuePage.getReleaseErrorsMessage(), "Verify no issue on Release Errors tab"),
                () -> assertTrue(releaseInfoPage.nav().isROMUploadCompleted(), "Verify rom upload section is completed"));
    }

    @Test()
    @Order(16)
    public void submitToLotcheck() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        if(!releaseInfoPage.nav().isIssuesCompleted()){
            releaseInfoPage.nav().clickIssues().openRequiredApprovalTab().performAllIssueAtApprovalTab(false);
            NDPDevelopmentHome developmentHome = releaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
            NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
            NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
            licensingWaiverRequestsTab.waitingForResultTableLoaded()
                    .enterSearchingBox(initialCode_OncardAOC)
                    .clickOnMagnifyingIconButton()
                    .waitingForResultTableLoaded();
            int numberOfRequestByPendingStatus = licensingWaiverRequestsTab.collectNumberOfRequestByPendingStatus();
            for (int i = 0; i < numberOfRequestByPendingStatus; i++) {
                developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton().clickOnLicensingWaiverRequestsTab();
                licensingWaiverRequestsTab.waitingForResultTableLoaded()
                        .enterSearchingBox(initialCode_OncardAOC)
                        .clickOnMagnifyingIconButton()
                        .waitingForResultTableLoaded();
                currentPage = licensingWaiverRequestsTab.openLicensingWaiverRequestByPendingStatus().selectAssignToMeButton()
                        .selectOKButtonInAssignToMeModal()
                        .selectApproveRequestButton()
                        .selectOKButtonInApproveRequestModal()
                        .waitingForApproveSuccessful();

            }
            NDPViewTaskPage ndpViewTaskPage = (NDPViewTaskPage) currentPage;
            releaseInfoPage=ndpViewTaskPage.clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(ndpUser)
                    .clickMyProducts().clickProductByGameCode(gameCode_OncardAOC).clickReleases().selectRelease("On-Card Downloadable Content Release");
            releaseInfoPage.nav().clickGuidelines().selectCompliesWithAllOfTheAboveGuidelines().clickOnSaveButton();
            releaseInfoPage.nav().clickAgeRating().clickOnAddStandardRatingButton()
                    .selectStandardAgeRating("CERO")
                    .clickOnNextButtonInAddStandardAgeRating()
                    .selectRatingValue("Not required")
                    .clickOnSaveButtonInEditStandardRating();
            releaseInfoPage.nav().clickSubmitButton().clickSubmit();
        }else{
            releaseInfoPage.nav().clickGuidelines().selectCompliesWithAllOfTheAboveGuidelines().clickOnSaveButton();
            releaseInfoPage.nav().clickAgeRating().clickOnAddStandardRatingButton()
                    .selectStandardAgeRating("CERO")
                    .clickOnNextButtonInAddStandardAgeRating()
                    .selectRatingValue("Not required")
                    .clickOnSaveButtonInEditStandardRating();
            releaseInfoPage.nav().clickSubmitButton().clickSubmit();
        }
        assertEquals(TextConstants.submitToLotcheck, releaseInfoPage.getStatusValue(), "Verify submit successfully");
    }

    @Test()
    @Order(17)//step13
    public void loginTestRailAndExportCSV(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailLoginPage.signInTestRail(testrailUser).navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        testRailManagerDashboardPage.clickExportCSVButton();
        currentVersion = testRailManagerDashboardPage.getNumberVersion();//string
        preVersion = Integer.parseInt(currentVersion) - 1;//int
        testPlanNameCurrentVersion = "version" + currentVersion;
        testPlanNamePreviousVersion = "version" + preVersion;
        currentPage = testRailManagerDashboardPage;
    }


    @Test()
    @Order(18)//step14
    public void VerifyTextOnConfirmationScreenPopup() {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        String title = testRailManagerDashboardPage.getTitle();
        String notify = testRailManagerDashboardPage.getNotify();
        String export_from = testRailManagerDashboardPage.getExport_from();
        String version = testRailManagerDashboardPage.getVersion();
        String suite_name = testRailManagerDashboardPage.getSuite_name();
        String suite_name_value_en = testRailManagerDashboardPage.getSuite_name_value_en();
        String suite_name_value_jp = testRailManagerDashboardPage.getSuite_name_value_jp();
        String select_language = testRailManagerDashboardPage.getSelect_language();
        String select_language_en = testRailManagerDashboardPage.getSelect_language_en();
        String select_language_jp = testRailManagerDashboardPage.getSelect_language_jp();
        String export_target = testRailManagerDashboardPage.getExport_target();
        String export_target_value_onlyTest = testRailManagerDashboardPage.getExport_target_value_onlyTest();
        String export_target_value_allTest = testRailManagerDashboardPage.getExport_target_value_allTest();

        testRailManagerDashboardPage.clickCancelExport();

        testRailManagerDashboardPage.logOut().signInTestRail(TextConstants.trAdminNCL_JP, TextConstants.trAdmin_Password).navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        testRailManagerDashboardPage.clickExportCSVButton();

        assertAll(() -> assertEquals("Confirm", title, "Verify text of title incorrect  "),
                () -> assertEquals("Export the latest Test Suite Version test cases in CSV.", notify, "Verify text of notify "),
                () -> assertEquals("Export from", export_from, "Verify text of export from incorrect  "),
                () -> assertEquals("Version", version, "Verify text of version incorrect  "),
                () -> assertEquals("Suite name", suite_name, "Verify text of suit name incorrect "),
                () -> assertEquals("English", suite_name_value_en, "Verify text of suit name value English incorrect  "),
                () -> assertEquals("Japanese", suite_name_value_jp, "Verify text of suit name value Japanese incorrect "),
                () -> assertEquals("Select language *", select_language, "Verify text of select language incorrect  "),
                () -> assertEquals("English", select_language_en, "Verify text of select language English incorrect "),
                () -> assertEquals("Japanese", select_language_jp, "Verify text of select language Japanese incorrect "),
                () -> assertEquals("Export target *", export_target, "Verify text of export target incorrect "),
                () -> assertEquals("Only test cases added in the latest version", export_target_value_onlyTest, "Verify text of export target value onlyTest incorrect  "),
                () -> assertEquals("All test cases", export_target_value_allTest, "Verify text of export target value allTest incorrect "),
                () -> assertEquals("確認", testRailManagerDashboardPage.getTitle(), "Verify text of title incorrect  "),
                () -> assertEquals("最新のTest Suite VersionのテストケースをCSVエクスポートします。", testRailManagerDashboardPage.getNotify(), "Verify text of notify "),
                () -> assertEquals("エクスポート元", testRailManagerDashboardPage.getExport_from(), "Verify text of export from incorrect  "),
                () -> assertEquals("バージョン", testRailManagerDashboardPage.getVersion(), "Verify text of version incorrect  "),
                () -> assertEquals("Suite名", testRailManagerDashboardPage.getSuite_name(), "Verify text of suit name incorrect "),
                () -> assertEquals("英語", testRailManagerDashboardPage.getSuite_name_value_en(), "Verify text of suit name value English incorrect  "),
                () -> assertEquals("日本語", testRailManagerDashboardPage.getSuite_name_value_jp(), "Verify text of suit name value Japanese incorrect "),
                () -> assertEquals("言語選択 *", testRailManagerDashboardPage.getSelect_language(), "Verify text of select language incorrect  "),
                () -> assertEquals("英語", testRailManagerDashboardPage.getSelect_language_en(), "Verify text of select language English incorrect "),
                () -> assertEquals("日本語", testRailManagerDashboardPage.getSelect_language_jp(), "Verify text of select language Japanese incorrect "),
                () -> assertEquals("エクスポート対象 *", testRailManagerDashboardPage.getExport_target(), "Verify text of export target incorrect "),
                () -> assertEquals("最新バージョンで追加されたテストケースのみ", testRailManagerDashboardPage.getExport_target_value_onlyTest(), "Verify text of export target value onlyTest incorrect  "),
                () -> assertEquals("すべてのテストケース", testRailManagerDashboardPage.getExport_target_value_allTest(), "Verify text of export target value allTest incorrect "));

    }

    @Test()
    @Order(19)//step15
    public void selectOnlyTestcasesAddedAndClickOk() {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        testRailManagerDashboardPage.clickCancelExport();
        TestRailLoginPage testRailLoginPage = testRailManagerDashboardPage.logOut();
        testRailManagerDashboardPage = testRailLoginPage.signInTestRail(testrailUser).navTopTestrail().navigateToManagerDashboard().checkErrorPopup().clickExportCSVButton();
        testRailManagerDashboardPage
                .clickEnglish()
                .clickOKExport();
        currentPage = testRailManagerDashboardPage;
    }

    public int getTCCurrentAndTCPre() {
        newbrowser = BrowserManager.getNewInstance();
        currentNewPage = newbrowser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentNewPage;
        TestRailDashboardPage testRailDashboardPage = testRailLoginPage.signInTestRail(testrailUser).navTopTestRail().navigateToDashboard();
        WaitUtils.idle(10000);
        TestRailHACTemplate testRailHACTemplate = testRailDashboardPage.clickHAC_Template();
        boolean testPlanNameCurrentVersionAdded = testRailHACTemplate.checkAddedTestPlan(testPlanNameCurrentVersion);
        boolean testPlanNamePreviousVersionAdded = testRailHACTemplate.checkAddedTestPlan(testPlanNamePreviousVersion);
        if (!testPlanNameCurrentVersionAdded) {
            testRailDashboardPage = testRailHACTemplate.clickReturnDashboard();
            TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestRail().navigateToManagerDashboard().checkErrorPopup();
            testRailManagerDashboardPage.clickViewTemplate().addTemplatePlan().enterTemplatePlanName(testPlanNameCurrentVersion)
                    .selectVersion(currentVersion)
                    .addPlanButtonPopup();
            WaitUtils.idle(2000);
            testRailDashboardPage = testRailManagerDashboardPage.navTopTestrail().navigateToDashboard();
            testRailHACTemplate = testRailDashboardPage.clickHAC_Template();
        }
        numberTCCurrentVersion = testRailHACTemplate.clickTestPlan(testPlanNameCurrentVersion).getNumberTCUntested();
        numberTCCurrentInHACGuideline = testRailHACTemplate.clickHACGuideline().getNumberTCUntested();
        testRailHACTemplate.waitingLoadingTestPlan();
        testRailHACTemplate.clickOverviewTab();

        if (!testPlanNamePreviousVersionAdded) {
            testRailDashboardPage = testRailHACTemplate.clickReturnDashboard();
            TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestRail().navigateToManagerDashboard().checkErrorPopup();
            testRailManagerDashboardPage.clickViewTemplate().addTemplatePlan().enterTemplatePlanName(testPlanNamePreviousVersion)
                    .selectVersion(String.valueOf(preVersion))
                    .addPlanButtonPopup();
            WaitUtils.idle(2000);
            testRailDashboardPage = testRailManagerDashboardPage.navTopTestrail().navigateToDashboard();
            testRailHACTemplate = testRailDashboardPage.clickHAC_Template();
        }

        numberTCPreviousVersion = testRailHACTemplate.clickTestPlan(testPlanNamePreviousVersion).getNumberTCUntested();
        numberTCPreviousInHACGuideline = testRailHACTemplate.clickHACGuideline().getNumberTCUntested();
        numberTCAdded = numberTCCurrentVersion - numberTCPreviousVersion;
        numberTCGuidelineAdded = numberTCCurrentInHACGuideline - numberTCPreviousInHACGuideline;
        return numberTCAdded;

    }

    @Test()
    @Order(20)//step16
    public void verifyErrorMessage(IJUnitTestReporter testReport) {
        int numberTCAdded = getTCCurrentAndTCPre();
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        if (numberTCAdded == 0 && numberTCGuidelineAdded != 0) {
            String errorMessage_EN = "*The test case to be exported does not exist.";
            String errorMessage_JP = "*エクスポート対象のテストケースが存在しません。";
            String getErrorMessageEN = testRailManagerDashboardPage.getErrorMessage();
            testRailManagerDashboardPage.clickCancelExport();
            TestRailLoginPage testRailLoginPage = testRailManagerDashboardPage.logOut();
            testRailManagerDashboardPage = testRailLoginPage.signInTestRail(TextConstants.trAdminNCL_JP, TextConstants.trAdmin_Password).navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
            testRailManagerDashboardPage.clickExportCSVButton();
            testRailManagerDashboardPage.clickEnglish().clickOKExport();
            String getErrorMessageJP = testRailManagerDashboardPage.getErrorMessage();
            assertAll(() -> assertEquals(errorMessage_EN, getErrorMessageEN, "Error message EN display incorrect"),
                    () -> assertEquals(errorMessage_JP, getErrorMessageJP, "Error message JP display incorrect"));
        } else {
            testRailManagerDashboardPage.notDisplayMessage();
        }
    }

    @Test()
    @Order(21)//step17+18
    public void verifyNameOfTheExportedFile() {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        fileName = currentVersion + "_added.csv";
        testRailManagerDashboardPage.checkTcIsAdded(numberTCAdded);
        if (numberTCAdded != 0 && numberTCGuidelineAdded == 0) {
            assertNotEquals(0L, testRailManagerDashboardPage.getFileSizeFromPath(fileName), "Verify download export file successfully, file name incorrect");
        } else {
            testRailManagerDashboardPage.notExportFile();
        }
    }


    @Test()
    @Order(22)
    public void openLCRProductLevel_BaseProduct(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage landingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
        LotcheckSubmissionSearchPage submissionSearchPage = landingPage.searchProduct(initialCode_BaseProduct);
        LotcheckReportSubmissionOverview submissionOverview = submissionSearchPage.selectSubmission(gameCode_BaseProduct, "00", "00");
        developmentROMIDHashValue_BaseProduct = submissionOverview.navBar().clickMetadata().getDevelopmentROMIDHash();
        LotcheckReportProductProductInformation productProductInformation = submissionOverview.matrix()
                .clickGoToProductLevel();
        currentPage = productProductInformation;
    }

    @Test()
    @Order(23)
    public void verifyRelatedSubmissionInformation_ApplicationTable() {
        LotcheckReportProductProductInformation productProductInformation = (LotcheckReportProductProductInformation) currentPage;
        assertAll(() -> assertEquals("1.0.0", productProductInformation.getDisplayVersionValue(), "Verify Display Version"),
                () -> assertEquals("Initial Release", productProductInformation.getReleaseTypeValue(), "Verify Release Type"),
                () -> assertEquals("00", productProductInformation.getReleaseVersionValue(), "Verify Release Version"),
                () -> assertEquals("00", productProductInformation.getSubmissionVersionValue(), "Verify Submission Version"),
                () -> assertEquals(TextConstants.OK, productProductInformation.getSubmissionJudgmentValue(), "Verify Submission Judgment "),
                () -> assertEquals(TextConstants.dl_Type, productProductInformation.getMediaTypeValue(), "Verify Media Type"),
                () -> assertEquals(TextConstants.no_Value, productProductInformation.getCompactedAppIncludeValue(), "Verify Compacted AppInclude"));

    }

    @Test()
    @Order(24)
    public void verifyRelatedSubmissionInformation_DLCTable() {
        LotcheckReportProductProductInformation productProductInformation = (LotcheckReportProductProductInformation) currentPage;
        assertAll(() -> assertEquals("00", productProductInformation.getArchiveNoAOCValue(), "Verify Archive No AOC"),
                () -> assertEquals("00", productProductInformation.getSubmissionVersionAOCValue(), "Verify Submission Version AOC"),
                () -> assertEquals("OK", productProductInformation.getSubmissionJudgmentAOCValue(), "Verify Submission Judgment AOC"));

    }

    @Test()
    @Order(25)
    public void verifyRelatedSubmissionInformation_OnCardDownloadableContentTable() {
        LotcheckReportProductProductInformation productProductInformation = (LotcheckReportProductProductInformation) currentPage;
        String gameCodeOncardAOC_actual = productProductInformation.getGameCodeOnCardAOCValue();
        String releaseVersionOnCardAOC_actual = productProductInformation.getReleaseVersionOnCardAOCValue();
        String submissionVersionOnCardAOC_actual = productProductInformation.getSubmissionVersionOnCardAOCValue();
        String submissionJudgmentOnCardAOC = productProductInformation.getSubmissionJudgmentOnCardAOCValue();
        String viewSubmissionOnCardAOC = productProductInformation.getViewSubmissionOnCardAOCValue();
        LotcheckReportSubmissionOverview submissionOverview = productProductInformation.clickViewSubmissionOnCardAOC();
        currentPage = submissionOverview;
        assertAll(() -> assertEquals(gameCode_OncardAOC, gameCodeOncardAOC_actual, "Verify GameCode OnCard AOC"),
                () -> assertEquals("00", releaseVersionOnCardAOC_actual, "Verify Release Version OnCard AOC"),
                () -> assertEquals("00", submissionVersionOnCardAOC_actual, "Verify Submission Version OnCard AOC"),
                () -> assertEquals("No Judgment", submissionJudgmentOnCardAOC, "Verify Submission Judgment OnCard AOC"),
                () -> assertEquals("View Submission", viewSubmissionOnCardAOC, "Verify View Submission OnCard AOC"),
                () -> assertEquals("Overview Submission: LCR - LCMS", submissionOverview.getPageTitle()),
                () -> assertEquals(gameCode_OncardAOC, submissionOverview.getGameCodeOfSubmission(), "Verify open the Submission Report of the applicale On Card AOC"));

    }

    @Test()
    @Order(26)
    public void navigateToMetadataTabAndDownloadFile() {
        LotcheckReportSubmissionOverview submissionOverview = (LotcheckReportSubmissionOverview) currentPage;
        LotcheckReportSubmissionMetadata submissionMetadata = submissionOverview.navBar().clickMetadata();
        currentPage = submissionMetadata;
        submissionMetadata.clickDownloadProdXcieFile("prod.xcie.result.xml");
        submissionMetadata.refreshPageSLCMS();
        submissionMetadata.clickDownloadOncardaocXmlFile("oncardaoc.xml");
        submissionMetadata.refreshPageSLCMS();
        assertAll(() -> assertTrue(submissionMetadata.prodXcieFileIsDisplayed(), "Verify prod.xcie.result.xml is displayed "),
                () -> assertNotEquals(0L, submissionMetadata.getFileSizeFromPath("prod.xcie.result.xml"), "Verify download prod.xcie.result.xml file successfully"),
                () -> assertTrue(submissionMetadata.oncardaocXmlFileIsDisplayed(), "Verify oncardaoc.xml is displayed"),
                () -> assertNotEquals(0L, submissionMetadata.getFileSizeFromPath("oncardaoc.xml"), "Verify download oncardaoc.xml file successfully"));

    }

    @Test()
    @Order(27)
    public void verifyInformationInRomInfo_ContentMeta() {
        LotcheckReportSubmissionMetadata submissionMetadata = (LotcheckReportSubmissionMetadata) currentPage;
        String developmentROMIDHash_actual = submissionMetadata.getDevelopmentROMIDHash();
        submissionMetadata.clickExpandXmlFile("cnmt.xml");
        assertAll(() -> assertEquals("1", submissionMetadata.getValueOnCnmtXmlFile("23"), "Verify Index"),
                () -> assertEquals("Tag name1", submissionMetadata.getValueOnCnmtXmlFile("22"), "Verify Tag name"),
                () -> assertEquals("0", submissionMetadata.getValueOnCnmtXmlFile("2"), "Verify Version"),
                () -> assertEquals("0", submissionMetadata.getValueOnCnmtXmlFile("20"), "Verify Required Application Version"),
                () -> assertEquals(applicationId_BaseProduct, submissionMetadata.getValueOnCnmtXmlFile("21"), "Verify ApplicationId"),
                () -> assertEquals(developmentROMIDHashValue_BaseProduct, developmentROMIDHash_actual, "Verify Development ROM ID Hash of base product and oncard aoc product"));


    }

    @Test()
    @Order(28)
    public void performDownloadAndVerifyDataOnOncardaocXmlFile() {
        LotcheckReportSubmissionMetadata submissionMetadata = (LotcheckReportSubmissionMetadata) currentPage;
        submissionMetadata.clickCollapseXmlFile("cnmt.xml")
                .clickExpandXmlFile("oncardaoc.xml");
        String type_OriginalAddOnContentMeta = submissionMetadata.getValueOnOncardaocXml("Type_AOC", "0");
        String id_OriginalAddOnContentMeta = submissionMetadata.getValueOnOncardaocXml("Id_AOC", "1");
        String version_OriginalAddOnContentMeta = submissionMetadata.getValueOnOncardaocXml("Version_AOC", "2");
        String digest_OriginalAddOnContentMeta = submissionMetadata.getValueOnOncardaocXml("Digest_AOC", "3");
        String tag_OriginalAddOnContentMeta = submissionMetadata.getValueOnOncardaocXml("Tag_AOC", "4");
        String type_Application = submissionMetadata.getValueOnOncardaocXml("Type_Application", "0");
        String id_Application = submissionMetadata.getValueOnOncardaocXml("Id_Application", "1");
        String version_Application = submissionMetadata.getValueOnOncardaocXml("Version_Application", "2");
        String digest_Application = submissionMetadata.getValueOnOncardaocXml("Digest_Application", "3");

        submissionMetadata.clickDownloadOncardaocXmlFile("oncardaoc.xml");

        Map<String, String> oncardaocXmlDataDownloaded = submissionMetadata.getXmlData("oncardaoc.xml");
        assertAll(() -> assertTrue(submissionMetadata.oncardaocXmlFileIsDisplayed(), "Verify oncardaoc.xml is displayed"),
                () -> assertNotEquals(0L, submissionMetadata.getFileSizeFromPath("oncardaoc.xml"), "Verify download oncardaoc.xml file successfully"),
                () -> assertEquals(oncardaocXmlDataDownloaded.get("Type_AOC"), type_OriginalAddOnContentMeta, "Verify type_OriginalAddOnContentMeta"),
                () -> assertEquals(oncardaocXmlDataDownloaded.get("Id_AOC"), id_OriginalAddOnContentMeta, "Verify id_OriginalAddOnContentMeta"),
                () -> assertEquals(oncardaocXmlDataDownloaded.get("Version_AOC"), version_OriginalAddOnContentMeta, "Verify version_OriginalAddOnContentMeta"),
                () -> assertEquals(oncardaocXmlDataDownloaded.get("Digest_AOC"), digest_OriginalAddOnContentMeta, "Verify digest_OriginalAddOnContentMeta"),
                () -> assertEquals(oncardaocXmlDataDownloaded.get("Tag_AOC"), tag_OriginalAddOnContentMeta, "Verify tag_OriginalAddOnContentMeta"),
                () -> assertEquals(oncardaocXmlDataDownloaded.get("Type_Application"), type_Application, "Verify type_Application"),
                () -> assertEquals(oncardaocXmlDataDownloaded.get("Id_Application"), id_Application, "Verify id_Application"),
                () -> assertEquals(oncardaocXmlDataDownloaded.get("Version_Application"), version_Application, "Verify version_Application"),
                () -> assertEquals(oncardaocXmlDataDownloaded.get("Digest_Application"), digest_Application, "Verify digest_Application"));
    }

    @Test()
    @Order(29)
    public void getOrgName_TestCondition(IJUnitTestReporter testReport) {
//        NDPHomePage homePage = (NDPHomePage) currentPage;
//        NDPDevelopmentHome ndpPage = homePage.acceptCookie().clickSignInPage().signIn(ndpUser);


        currentPage = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        NDPDevelopmentHome ndpPage = (NDPDevelopmentHome) currentPage;
        ndpPage.clickMenuAccount().clickManagerAccountLink();
        orgName = ndpPage.getOrgName();
        System.out.println("Org name: " + orgName);
        currentPage = ndpPage.clickMenuAccount().clickLogout();

    }

    @Test()
    @Order(30)
    public void loginNdpByLicensingPowerUser() {
        NDPHomePage homePage = (NDPHomePage) currentPage;
        currentPage = homePage.clickSignInPage().signIn(TextConstants.lic_pwr_user_NCL, TextConstants.nintendo_approval_Password);
    }

    @Test()
    @Order(31)
    public void changeHomeRegionProduct() {
        NDPDevelopmentHome internal = (NDPDevelopmentHome) currentPage;
        NDPOrganizationManagement organizationManagement = internal.clickInternalTab().clickOrganizationManagementTab();
        System.out.println("Org name: " + orgName);
        organizationManagement.enterOrgNameInput(orgName).clickIconSearch(orgName);
        organizationManagement.clickOrgRecord(orgName);
        String regionOrg = organizationManagement.homeRegion();
        System.out.println("Region org: " + regionOrg);
        NDPProductSearchPage productSearchPage = organizationManagement.clickProductSearchButton();
        NDPViewProductDashboardPage ndpViewProductDashboardPage = productSearchPage
                .waitingLoadingValueSearch()
                .clickSearchContent(initialCode_OncardAOC).selectElementFromDropDown();
        NDPSettingTab settingTab = ndpViewProductDashboardPage.clickSettingTab()
                .selectProductHomeRegionDiffOrg(regionOrg)
                .clickSaveButton();
        regionProduct = settingTab.getRegionProduct();
        NDPSponsorQueuePage sponsorQueuePage = settingTab.navTopNavigation().clickInternalTab().clickSponsorQueueButton();
        WaitUtils.idle(7000);
        currentPage = sponsorQueuePage;
    }

    @Test()
    @Order(32)
    public void checkBackgroundSponsorQueueListIsRed() { //TODO G3PTDEV4NX-31514
        NDPSponsorQueuePage sponsorQueue = (NDPSponsorQueuePage) currentPage;
        sponsorQueue.clearTextSearch().inputInitialCode(initialCode_OncardAOC)
                .clickSearch(initialCode_OncardAOC).waitUpdateRegion(regionProduct, initialCode_OncardAOC);
        currentPage = sponsorQueue;
        String redColor = "#fee5e2";
        String getColor = sponsorQueue.checkBackgroundColorIsRed();
        assertEquals(regionProduct,sponsorQueue.getRegionProduct(),"Verify change home region product");
        assertEquals(redColor, getColor, "Background color is not red");
    }

    @Test()
    @Order(33)
    public void checkBackgroundReasonIsYellow() {
        NDPSponsorQueuePage sponsorQueue = (NDPSponsorQueuePage) currentPage;
        sponsorQueue.clickRecord();
        String yellowColor = "#fffbdc";
        WaitUtils.idle(20000);
        assertTrue(sponsorQueue.checkDisplayHeader(), "Message header EN is not display");
        assertTrue(sponsorQueue.checkDisplayContent(), "Message content EN is not display");
        String getColor = sponsorQueue.checkBackgroundReasonColorIsYellow();
        assertEquals(yellowColor, getColor, "Background color reason is not yellow");
    }

    @Test()
    @Order(34)
    public void checkContentReason() {
        NDPSponsorQueuePage sponsorQueue = (NDPSponsorQueuePage) currentPage;
        String contentReason_EN = "This product is highlighted due to the following reasons:" + "\n"
                + "Product Home Region has been changed manually";
        String contentReason_JP = "下記の理由でこのプロダクトが強調表示されています。" + "\n"
                + "プロダクトのホームリージョンが手動で変更されています。";
        sponsorQueue.clickCloseRecord().selectENLanguage();
        sponsorQueue.clearTextSearch().inputInitialCode(initialCode_OncardAOC).clickSearch(initialCode_OncardAOC);
        sponsorQueue.clickRecord();
        WaitUtils.idle(5000);
        assertTrue(sponsorQueue.checkDisplayHeader(), "Message header EN is not display");
        assertTrue(sponsorQueue.checkDisplayContent(), "Message content EN is not display");
        String getContentReason_EN = sponsorQueue.getHeader();
        sponsorQueue.clickCloseRecord().selectJPLanguage();
        WaitUtils.idle(3000);
        sponsorQueue.clearTextSearch().inputInitialCode(initialCode_OncardAOC).clickSearch(initialCode_OncardAOC);
        sponsorQueue.clickRecord();
        WaitUtils.idle(5000);
        assertTrue(sponsorQueue.checkDisplayHeader(), "Message header JP is not display");
        assertTrue(sponsorQueue.checkDisplayContent(), "Message content JP is not display");
        String getContentReason_JP = sponsorQueue.getHeader();

        assertAll(() -> assertEquals(contentReason_JP, getContentReason_JP, "Content reason by JP incorrect"),
                () -> assertEquals(contentReason_EN, getContentReason_EN, "Content reason by EN incorrect"));
    }
}