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
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportProductMessage;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportProductProductInformation;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportProductViewThreadMesage;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssueDetails;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssues;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.ndp.NDPCreateNewThreadMessage;
import net.nintendo.automation.ui.models.ndp.NDPCreateProductPage;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_internal.NDPInternalPage;
import net.nintendo.automation.ui.models.ndp.existing_internal.NDPProductSearchPage;
import net.nintendo.automation.ui.models.ndp.existing_internal.view_product_dashboard.NDPViewProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPCreateDLCReleaseDialog;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardDownloadableContentTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardMessagesTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardProductInfoTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardReleasesTab;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoAgeRating;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoFeaturesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoGuidelinesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoROMUpload;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoTestScenarios;
import net.nintendo.automation.ui.models.rom.ROMCreateRom;
import net.nintendo.automation.ui.models.rom.ROMLoginPage;
import net.nintendo.automation.ui.models.testrail.TestRailDashboardPage;
import net.nintendo.automation.ui.models.testrail.TestRailDetailTestcaseDialog;
import net.nintendo.automation.ui.models.testrail.TestRailLoginPage;
import net.nintendo.automation.ui.models.testrail.TestRailManagerDashboardPage;
import net.nintendo.automation.ui.models.testrail.TestRailMyToDoPage;
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

@Tag("T3402568")
//@ScenarioTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3402568 extends CommonBaseTest {
    private static Browser browser;
    private static User romUser;
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
    private static String productName_BaseProduct;
    private static String productName_OncardAOC;
    private static String publishingRelationship_BaseProduct;
    private static String inputTopicMessage;
    private static String developmentROMIDHashValue_BaseProduct;
    private static String gameCode_OncardAOC;
    private static String initialCode_OncardAOC;
    private static String nameTestCaseSubmitBug;
    private static String descriptionBug = "newbugsubmit";

    private static CommonUIComponent currentPage;
    private static TestLogger logger;


    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_DungDTT2);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.int_approval_coord_NCL);
        slcmsUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);
        testrailUser = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER);
        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);
        publishingRelationship_BaseProduct = TextConstants.first_Party_Type;

        productName_OncardAOC = DataGen.getRandomProductName();
        productName_BaseProduct = DataGen.getRandomProductName();
        inputTopicMessage = DataGen.getRandomText();
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
        UserManager.releaseUser(ServiceType.SEARCH, romUser);
        UserManager.releaseUser(ServiceType.PMMS, testrailUser);
        UserManager.releaseUser(ServiceType.NDP, licUser);
        UserManager.releaseUser(ServiceType.SLCMS, slcmsUser);

    }

    public static void submitInitialToLotCheckAndCreateOnCardRom(IJUnitTestReporter testReport) throws Exception {
        NDPHomePage homePage = (NDPHomePage) currentPage;
        NDPMyProductsPage myProductsPage = homePage.acceptCookie().clickSignInPage().signIn(ndpUser).clickMyProducts();
        myProductsPage.createNewProduct()
                .selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(publishingRelationship_BaseProduct)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalChinaSalesRegion()
                .selectProductName(productName_BaseProduct)
                .clickCreateButton();
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName_BaseProduct);
        CommonAction.issueGameCode(ndpProductDashboardPage, 30,"NO");
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();
        applicationId_BaseProduct = ndpProductDashboardProductInfoTab.getApplicationID();
        gameCode_BaseProduct = ndpProductDashboardProductInfoTab.getGameCode();
        initialCode_BaseProduct = ndpProductDashboardProductInfoTab.getInitialCode();

        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        NDPReleaseInfoPage releaseInfoPage = ndpReleaseInfoPage.nav().clickReleaseInfo();
        CommonAction.selectROMSameAllRegion(releaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);
        NDPReleaseInfoFeaturesPage releaseInfoFeaturesPage = releaseInfoPage.nav().clickFeatures();

        CommonAction.getFeatureOptionData(releaseInfoPage, TextConstants.allOff_China,
                TextConstants.allOff, TextConstants.allOff, false, "", false, "");
        releaseInfoFeaturesPage.clickContinue();
        releaseInfoPage.nav().clickGuidelines().selectCompliesWithAllOfTheAboveGuidelines().clickOnSaveButton();
        releaseInfoPage.nav().clickAgeRating();
        releaseInfoPage.nav().clickTestScenarios();
        releaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton();

        createRom(testReport);

        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        homePage = (NDPHomePage) currentPage;
        myProductsPage = homePage.clickSignInPage().signIn(ndpUser).clickMyProducts();
        ndpProductDashboardReleasesTab = myProductsPage.clickProductByGameCode(initialCode_BaseProduct).clickReleases();
        releaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        releaseInfoPage.nav().clickROMUpload().closeRequiredStepModal().selectRomFileButton().selectFileInPC(rom_BasedApplicationName).clickUpload().clickUploadNow().waitForUploadComplete(TextConstants.romUpload);
        releaseInfoPage.nav().clickIssues().openRequiredApprovalTab().performAllIssueAtApprovalTab(false);

        NDPDevelopmentHome developmentHome = releaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        CommonAction.approveInternalWaiverRequest(developmentHome, internalTasksPage, gameCode_BaseProduct);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton().clickSignInPage().signIn(ndpUser).clickMyProducts()
                .clickProductByGameCode(gameCode_BaseProduct).clickReleases().selectInitialRelease().nav().clickSubmitButton()
                .clickSubmit();
        ;
        WaitUtils.idle(3000);
    }

    private static void testRailJudgment(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailLoginPage.signInTestRail(testrailUser).navTopTestrail().navigateToManagerDashboard();
        CommonAction.setStatusOnTestrail(testRailManagerDashboardPage, initialCode_BaseProduct, "Passed", TextConstants.percentCompleted, TextConstants.OK);
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
        CommonAction.approvalJudgmentStatus(nclLotCheckQueue, initialCode_BaseProduct, 300, TextConstants.OK, true, "1");
    }

    private static void createRom(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        ROMLoginPage romLoginPage = (ROMLoginPage) currentPage;
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom
                .selectType(TextConstants.initial_Release)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .enterProductName(productName_BaseProduct)
                .enterApplicationId(applicationId_BaseProduct)
                .selectPublishingRelationShip(publishingRelationship_BaseProduct)
                .enterDisplayVersion("1.0.0")
                .enterReleaseVersion("00")
                .selectRequester(TextConstants.test_User)
                .clickOnCreateButton()
                .waitIsLoaded();
        rom_BasedApplicationName = romCreateRom.getRomNameTable(applicationId_BaseProduct);
        romCreateRom.clickOnROMFileOnROMTable(rom_BasedApplicationName);
        WaitUtils.idle(5000);

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
        WaitUtils.idle(5000);

        romCreateRom.selectRequester(TextConstants.test_User)
                .selectType(TextConstants.onCardDownloadContent)
                .selectBasedApplicationOrPatchFile(rom_BasedApplicationName)
                .selectBasedAOCFiles(rom_BasedAOCName)
                .clickOnCreateButton()
                .waitIsLoaded();
        rom_OncardAOCName = romCreateRom.getRomName();
        romCreateRom.clickOnROMFileOnROMTable(rom_OncardAOCName);
        WaitUtils.idle(5000);
    }

    private static void submitDLCReleaseToLotCheck(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN);
        NDPDevelopmentHome ndpDevelopmentHome = (NDPDevelopmentHome) currentPage;
        NDPMyProductsPage myProductsPage = ndpDevelopmentHome.clickMyProducts();

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
        HashMap<LotcheckRemotePresenceEnum, String> lotCheckSettings = FeatureSettingsManager.getLotcheckPresenceSettings("Confirm");
        HashMap<DownloadableContentEnum, String> downloadableContentSettings = FeatureSettingsManager.getDownloadableContentSettings("Confirm");
        NDPReleaseInfoFeaturesPage releaseFeatures = ndpReleaseInfoPage.nav().clickFeatures()
                .clickEditLotcheckRemotePresence()
                .setFeatures(lotCheckSettings).clickSave()
                .clickEditDownloadableContent()
                .setFeatures(downloadableContentSettings).clickSave();
        ndpReleaseInfoPage.nav().clickGuidelines().selectCompliesWithAllOfTheAboveGuidelines().clickOnSaveButton();
        ndpReleaseInfoPage.nav().clickTestScenarios().expandAllSections()
                .expandAllTestScenes()
                .enterNameHowToCheckDLC(TextConstants.enterNameHowToCheckDLC)
                .enterExplainHowToCheckDLC(TextConstants.enterExplainHowToCheckDLC)
                .clickOnSaveButton();
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = ndpReleaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, rom_BasedAOCName);
        ndpReleaseInfoPage.nav().clickIssues().openRequiredApprovalTab().performAllIssueAtApprovalTab(false);
        NDPDevelopmentHome developmentHome = ndpReleaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        CommonAction.approveInternalWaiverRequest(developmentHome, internalTasksPage, gameCode_BaseProduct);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton().clickSignInPage().signIn(ndpUser).clickMyProducts()
                .clickProductByGameCode(gameCode_BaseProduct).clickDownloadableContent().clickDLCReleaseByArchiveNo("00").nav().clickSubmitButton()
                .clickSubmit();
        ;
        WaitUtils.idle(3000);
    }


    @Test()
    @Order(1)
    public void submitInitialReleaseAndCreateOnCardRom_TestCondition(IJUnitTestReporter testReport) throws Exception {
        submitInitialToLotCheckAndCreateOnCardRom(testReport); //Rom OnCard AOC created on this method
    }

    @Test()
    @Order(2)
    public void judgmentOkForInitialRelease_TestCondition(IJUnitTestReporter testReport) {
        testRailJudgment(testReport);
        slcmsApproveJudgment(testReport);
    }

    @Test()
    @Order(3)
    public void submitAOCRelease_TestCondition(IJUnitTestReporter testReport) throws Exception {
        submitDLCReleaseToLotCheck(testReport);
    }

    @Test()
    @Order(4)
    public void judgmentOkForAOCRelease_TestCondition(IJUnitTestReporter testReport) {
        testRailJudgment(testReport);
        slcmsApproveJudgment(testReport);
    }

    @Test()
    @Order(5)
    public void createOnCardAOCProduct_Step1(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN);
        NDPDevelopmentHome ndpPage = (NDPDevelopmentHome) currentPage;
        NDPMyProductsPage myProductsPage = ndpPage.clickMyProducts();

        NDPCreateProductPage createProductPage = myProductsPage.createNewProduct()
                .selectProductType(TextConstants.onCardDownloadContent)
                .selectPublishingRelationship(publishingRelationship_BaseProduct)
                .selectProductName(productName_OncardAOC)
                .selectBaseProduct(productName_BaseProduct + " (" + initialCode_BaseProduct + ")")
                .selectCopyDataFrom("00.00-Initial Release");

        myProductsPage = createProductPage.clickCreateButton();
        currentPage = myProductsPage;
    }

    @Test()
    @Order(6)
    public void createOnCardAOCProduct_Expect1(IJUnitTestReporter testReport) {
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        currentPage = myProductsPage;
        assertTrue(myProductsPage.onCardAOCProductIsDisplayed(productName_OncardAOC, initialCode_BaseProduct, TextConstants.onCardDownloadContent), TextConstants.verifyLOncardAocProduct);
    }

    @Test()
    @Order(7)
    public void completeAllTabsAndSubmit_Step2(IJUnitTestReporter testReport) throws Exception {
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        NDPProductDashboardPage productDashboardPage = myProductsPage.clickProductByName(productName_OncardAOC);
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = productDashboardPage.clickProductInfo();
        gameCode_OncardAOC = ndpProductDashboardProductInfoTab.getGameCode();
        initialCode_OncardAOC = ndpProductDashboardProductInfoTab.getInitialCode();
        NDPReleaseInfoPage ndpReleaseInfoPage = productDashboardPage.clickReleases().selectRelease(TextConstants.onCardDownloadContentRelease);
        NDPReleaseInfoPage releaseInfoPage = ndpReleaseInfoPage.nav().clickReleaseInfo();
        releaseInfoPage.selectExpectedSubmissionDate().chooseDateInCalender(5);
        releaseInfoPage.selectExpectedReleaseDate().chooseDateInCalender(65);
        CommonAction.selectROMSameAllRegion(releaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);

        //create Feature
        NDPReleaseInfoFeaturesPage featuresPage = releaseInfoPage.nav().clickFeatures();
        HashMap<DownloadableContentEnum, String> downloadableContentSettings = FeatureSettingsManager.getDownloadableContentSettings("Confirm");
        featuresPage.clickEditDownloadableContent()
                .setFeatures(downloadableContentSettings).clickSave().clickContinue();

        //Create GuildLine
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = ndpReleaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoGuidelinesPage.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();

        //Create Age Rating
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = ndpReleaseInfoPage.nav().clickAgeRating();

        //Test Scenario
        NDPReleaseInfoTestScenarios ndpReleaseInfoTestScenarios = ndpReleaseInfoPage.nav().clickTestScenarios();

        //ROM Upload
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = releaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, rom_OncardAOCName);
        WaitUtils.idle(3000);

        //Issue & submit
        ndpReleaseInfoPage.nav().clickIssues().openRequiredApprovalTab().performAllIssueAtApprovalTab(false);
        NDPDevelopmentHome developmentHome = releaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        CommonAction.approveInternalWaiverRequest(developmentHome, internalTasksPage, gameCode_OncardAOC);
        WaitUtils.idle(2000);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton()
                .clickSignInPage()
                .signIn(ndpUser)
                .clickMyProducts().clickProductByGameCode(gameCode_OncardAOC).clickReleases().selectOnCardDLC();
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.enterDeveloperComment).clickSubmit();
        WaitUtils.idle(3000);

    }

    @Test()
    @Order(8)
    public void VerifyProdDisplayInSLCMSAndTR_expect2(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN, testReport);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailLoginPage.signInTestRail(testrailUser).navTopTestRail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(initialCode_OncardAOC)
                .selectStatusUntested().selectStatusPassed();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite();
        int num = testRailManagerDashboardPage.collectNumberTestPlan();
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage landingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
        WaitUtils.idle(10000);
        LotcheckQueuePage lotcheckQueuePage = landingPage.navigationToTesterAssignmentQueue(LotcheckRegion.NCL.toString());
        lotcheckQueuePage.searchByInitialCode(initialCode_OncardAOC).clickPlannedTestDateNotSet();
        String numberTesPlanLC = lotcheckQueuePage.numberTestPlanDisplay();

        assertAll(() -> assertNotEquals("0", numberTesPlanLC, "Verify product is display in LC"),
                () -> assertTrue(num == 1, "Verify test case display in TR")
        );

    }

    @Test()
    @Order(9)
    public void createNewThread_Step3(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        NDPProductDashboardMessagesTab ndpProductDashboardMessagesTab = myProductsPage.clickProductByName(productName_OncardAOC).clickMessagesTab();
        NDPCreateNewThreadMessage ndpCreateNewThreadMessage = ndpProductDashboardMessagesTab.clickNewThreadButtonClickable();
        ndpCreateNewThreadMessage.inputTopicThread(inputTopicMessage).selectDeveloperSupportCheckbox().selectLotcheckCheckbox().selectLicensingCheckbox()
                .clickPostButton().waitingForRequestSuccessful();
        currentPage = ndpCreateNewThreadMessage;
    }

    @Test()
    @Order(10)
    public void newThreadDisplayInList_Expect3(IJUnitTestReporter testReport) {
        NDPCreateNewThreadMessage ndpCreateNewThreadMessage = (NDPCreateNewThreadMessage) currentPage;
        NDPProductDashboardMessagesTab ndpProductDashboardMessagesTab = ndpCreateNewThreadMessage.clickReturnMessageTab();
        assertEquals(inputTopicMessage, ndpProductDashboardMessagesTab.getTextTopic(), "New thread is not display in message list");
        currentPage = ndpProductDashboardMessagesTab;
    }

    @Test()
    @Order(11)
    public void loginTR_Step4(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN);
        TestRailDashboardPage testRailDashboardPage = (TestRailDashboardPage) currentPage;
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(gameCode_OncardAOC)
                .selectStatusUntested().selectStatusPassed();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan();
        currentPage = testRailManagerDashboardPage;
    }

    @Test()
    @Order(12)
    public void assignCurrentUser_Step5(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        testRailManagerDashboardPage.clickAssignTo().selectSetValueAssignToRadio().selectUser("Me").clickOk().waitingLoadingTestSuite();
        WaitUtils.idle(5000);
        TestRailMyToDoPage tesRailMyTodoTab = testRailManagerDashboardPage.topNav().navigateToMyTodo();
        tesRailMyTodoTab.checkErrorPopup();
        tesRailMyTodoTab.actionFill().enterTestPlanNameFilter(gameCode_OncardAOC);
        tesRailMyTodoTab.actionFill().clickOnApplyFiltersButton();
        currentPage = tesRailMyTodoTab;
    }

    @Test()
    @Order(13)
    public void testPlanDisplayInMyTodoTab_Expect5(IJUnitTestReporter testReport) {
        TestRailMyToDoPage testRailMyTodoTab = (TestRailMyToDoPage) currentPage;
        testRailMyTodoTab
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .expandAllTestRuns();
        nameTestCaseSubmitBug = testRailMyTodoTab.getNameFistTestCaseSubmitBug();
        assertTrue(testRailMyTodoTab.checkTestPlanAppearInTestRailMyToDoTab(), "Test case is not display in TR My To do Tab");
    }

    @Test()
    @Order(14)
    public void submitBugFirstTC_Step6(IJUnitTestReporter testReport) {
        TestRailMyToDoPage tesRailMyTodoTab = (TestRailMyToDoPage) currentPage;
        TestRailDetailTestcaseDialog testRailDetailTestcaseDialog = tesRailMyTodoTab.clickOnTheFirstTestRun()
                .openTestcaseDetail(1);
        testRailDetailTestcaseDialog.clickSubmitBug().enterDescription(descriptionBug).clickSubmit().clickOk();
        tesRailMyTodoTab = testRailDetailTestcaseDialog.clickCloseMyTodo();
        currentPage = tesRailMyTodoTab;


    }

    @Test()
    @Order(16)
    public void navigateSubmissionLCReport_Step7(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = lotcheckLandingPage.searchProduct(initialCode_OncardAOC);
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = lotcheckSubmissionSearchPage.selectActionMenu(gameCode_OncardAOC).selectDropdownItem();
        currentPage = lotcheckReportSubmissionOverview;
    }

    @Test()
    @Order(17)
    public void submissionLotCheckReportDisplayed_Expect7(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = (LotcheckReportSubmissionOverview) currentPage;
        assertEquals("Overview Submission: LCR - LCMS", lotcheckReportSubmissionOverview.getPageTitle(), "Submission Lotcheck Report is not display");
    }

    @Test()
    @Order(18)
    public void viewIssueInLCIssue_Step8(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = (LotcheckReportSubmissionOverview) currentPage;
        LotcheckReportSubmissionLotcheckIssues lotcheckReportSubmissionLotcheckIssues = lotcheckReportSubmissionOverview.clickLotchekIssueTab().clickMenuJS();
        LotcheckReportSubmissionLotcheckIssueDetails lotcheckReportSubmissionLotcheckIssueDetails = lotcheckReportSubmissionLotcheckIssues.clickViewIssueDetail();
        currentPage = lotcheckReportSubmissionLotcheckIssueDetails;
    }

    @Test()
    @Order(19)
    public void verifyCanViewIssue_Expect8(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssueDetails lotcheckReportSubmissionLotcheckIssueDetails = (LotcheckReportSubmissionLotcheckIssueDetails) currentPage;
        assertEquals(descriptionBug, lotcheckReportSubmissionLotcheckIssueDetails.getDescriptionBug(), "Description display incorrect, user can not view detail issue");
        assertTrue(lotcheckReportSubmissionLotcheckIssueDetails.getNameTestCaseSubmitBug().contains(nameTestCaseSubmitBug), "Name test case display incorrect, user can not view detail issue");
    }

    @Test()
    @Order(20)
    public void navigateProductLevel_Step9(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssueDetails lotcheckReportSubmissionLotcheckIssueDetails = (LotcheckReportSubmissionLotcheckIssueDetails) currentPage;
        LotcheckReportProductProductInformation lotcheckReportProductProductInfomation = lotcheckReportSubmissionLotcheckIssueDetails.gotoProductInformation();
        currentPage = lotcheckReportProductProductInfomation;
    }

    @Test()
    @Order(21)
    public void productLevelDisplay_Expect9(IJUnitTestReporter testReport) {
        LotcheckReportProductProductInformation lotcheckReportProductProductInfomation = (LotcheckReportProductProductInformation) currentPage;
        assertEquals("Product Information Product: LCR - LCMS", lotcheckReportProductProductInfomation.getPageTitle(), "Product report navigate fail");
    }

    @Test()
    @Order(22)
    public void openMessageTab_Step10(IJUnitTestReporter testReport) {
        LotcheckReportProductProductInformation lotcheckReportProductProductInfomation = (LotcheckReportProductProductInformation) currentPage;
        LotcheckReportProductMessage lotcheckReportProductMessage = lotcheckReportProductProductInfomation.clickMessageTab();
        currentPage = lotcheckReportProductMessage;
    }

    @Test()
    @Order(23)
    public void checkDisplayMessageInMessageTab_Expect10(IJUnitTestReporter testReport) {
        LotcheckReportProductMessage lotcheckReportProductMessage = (LotcheckReportProductMessage) currentPage;
        String messageNewThread = lotcheckReportProductMessage.getMessageInProductMessageTab();
        assertEquals(inputTopicMessage, messageNewThread, "Message new thread display incorrect in Product Message tab");
    }

    @Test()
    @Order(24)
    public void clickViewThreadButton_Step11(IJUnitTestReporter testReport) {
        LotcheckReportProductMessage lotcheckReportProductMessage = (LotcheckReportProductMessage) currentPage;
        LotcheckReportProductViewThreadMesage lotcheckReportProductViewThreadMesage = lotcheckReportProductMessage.clickViewThread();
        currentPage = lotcheckReportProductViewThreadMesage;
    }

    @Test()
    @Order(25)
    public void verifyMessageCanViewed_Expect11(IJUnitTestReporter testReport) {
        LotcheckReportProductViewThreadMesage lotcheckReportProductViewThreadMesage = (LotcheckReportProductViewThreadMesage) currentPage;
        assertEquals("View Thread Messages Product: LCR - LCMS", lotcheckReportProductViewThreadMesage.getPageTitle(), "Message new thread display incorrect in Product Message tab");
        assertEquals(inputTopicMessage, lotcheckReportProductViewThreadMesage.getMessage(), "Message new thread display incorrect in View Thread Product Message");
        currentPage = lotcheckReportProductViewThreadMesage;
    }

    @Test()
    @Order(26)
    public void loginNDPProductSearch_Step12(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN);
        NDPDevelopmentHome developmentHome = (NDPDevelopmentHome) currentPage;
        NDPInternalPage ndpInternalPage = developmentHome.clickInternalTab();
        NDPProductSearchPage ndpProductSearchPage = ndpInternalPage.clickProductSearchButton();
        currentPage = ndpProductSearchPage;
    }

    @Test()
    @Order(27)
    public void productSearchPageDisplay_Expect12(IJUnitTestReporter testReport) {
        NDPProductSearchPage ndpProductSearchPage = (NDPProductSearchPage) currentPage;
        assertEquals("Product Search - Nintendo Developer Portal", ndpProductSearchPage.getPageTitle(), "Product Search display incorrect");
    }

    @Test()
    @Order(28)
    public void clickViewProdDashboard_Step13(IJUnitTestReporter testReport) {
        NDPProductSearchPage ndpProductSearchPage = (NDPProductSearchPage) currentPage;
        NDPViewProductDashboardPage ndpViewProductDashboardPage = ndpProductSearchPage.clickSearchContent(initialCode_OncardAOC)
                .waitingLoadingValueSearch()
                .selectElementFromDropDown();
        currentPage = ndpViewProductDashboardPage;
    }

    @Test()
    @Order(29)
    public void verifyNavigateProdDashboard_Expect13(IJUnitTestReporter testReport) {
        NDPViewProductDashboardPage ndpViewProductDashboardPage = (NDPViewProductDashboardPage) currentPage;
        assertEquals("Dashboard - Nintendo Developer Portal", ndpViewProductDashboardPage.getPageTitle(), "Product Dashboard display incorrect");
        assertEquals(gameCode_OncardAOC, ndpViewProductDashboardPage.getGameCode(), "GameCode product incorrect");
    }

    @Test()
    @Order(30)
    public void navigateMessageTabOfProductDashboard_Step14(IJUnitTestReporter testReport) {
        NDPViewProductDashboardPage ndpViewProductDashboardPage = (NDPViewProductDashboardPage) currentPage;
        NDPProductDashboardMessagesTab ndpProductDashboardMessagesTab = ndpViewProductDashboardPage.clickMessagesTab();
        currentPage = ndpProductDashboardMessagesTab;
    }

    @Test()
    @Order(31)
    public void verifyDisplayMessage_Expect14(IJUnitTestReporter testReport) {
        NDPProductDashboardMessagesTab ndpProductDashboardMessagesTab = (NDPProductDashboardMessagesTab) currentPage;
        assertEquals(inputTopicMessage, ndpProductDashboardMessagesTab.getTextTopic(), "New thread is not display in message list");
    }
}
