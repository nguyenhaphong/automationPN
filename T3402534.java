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
import net.nintendo.automation.ui.data.features.enums.PlayModesEnum;
import net.nintendo.automation.ui.data.features.enums.ProgramSpecificationsEnum;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.lcms.LotcheckEditAccountPreferences;
import net.nintendo.automation.ui.models.lcms.LotcheckHomePage;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.ui.models.lcms.LotcheckQueuePage;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportNav;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionFiles;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionTestScenarios;
import net.nintendo.automation.ui.models.ndp.NDPCreateProductPage;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPLicensingWaiverRequestsTab;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardProductInfoTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardReleasesTab;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoFeaturesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoIssuePage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoROMUpload;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoTestScenarios;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPReleaseInfoFeaturesInputDevicesDialog;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPReleaseInfoFeaturesLotcheckRemotePresenceDialog;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPReleaseInfoFeaturesPlayModesDialog;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPReleaseInfoFeaturesProgramSpecsDialog;
import net.nintendo.automation.ui.models.rom.ROMCreateRom;
import net.nintendo.automation.ui.models.rom.ROMLoginPage;
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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classname: T3402534
 * Version: 1.0.0
 * Purpose:
 */
@Tag("T3402534")
@NDP
@SLCMS
//@ScenarioTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class T3402534 extends CommonBaseTest {
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
    private static String fileName1 = "Test_excel.xlsx";
    private static String fileName2 = "1KB.txt";
    private static String fileName3 = "10KB.txt";
    private static String fileName4 = "500KB.txt";
    private static String fileName5 = "100MB.txt";
    private static String fileName6 = "500MB.txt";
    private static String fileName7 = "1GB.txt";
    private static String fileName8 = "test1.txt";
    private static String fileName9 = "test2.txt";
    private static String fileName10 = "test3.txt";
    private static String fileName11 = "10MB.txt";
    private static String getProductName;
    private static String nameOfSection;
    private static String statusOfSection;
    private static int numberOfMethodBeforeSave;
    private static int numberOfMethodAfterSave;
    private static boolean howToCheckTouchScreen;
    private static boolean howToCheckNintendoSwitchProController;

    private static CommonUIComponent currentPage;
    private static TestLogger logger;

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.d4c_prdcv_PhongNguyen);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
        slcmsUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);
        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);

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
    }

    @BeforeEach
    public void each(IJUnitTestReporter testReport) {
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
    }

    @Test
    @Order(1)
    public void loginWithAPartnerAccountAndCreateANewProduct_Step1(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReport);
        NDPHomePage homePage = (NDPHomePage) currentPage;
        NDPMyProductsPage myProductsPage = homePage.acceptCookie().clickSignInPage().signIn(ndpUser).clickMyProducts();
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPCreateProductPage createProductPage = myProductsPage.createNewProduct();
        createProductPage.selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(TextConstants.third_Party_Type)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalChinaSalesRegion()
                .selectProductName(productName)
                .clickCreateButton();
        getProductName = myProductsPage.getProductNameInRow(1);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        CommonAction.issueGameCode(ndpProductDashboardPage, 60, "NO");
        assertEquals("A Game Code has been successfully added to the product.", ndpProductDashboardPage.getIssueGameCodeSuccessMessage(), "Issue Game Code Successfully");
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        ndpReleaseInfoPage.selectSubmissionType("Precheck")
                .selectCardSize("2 GB")
                .selectTypeOfPrecheck("Precheck version")
                .selectRomsThatDifferBetweenRegion(TextConstants.selectRomSameAllRegion)
                .selectLanguageUsedForTextEntry(TextConstants.english)
                .save()
                .waitSaveButtonEnable();
        currentPage = ndpReleaseInfoPage;
    }

    @Test
    @Order(2)
    public void verifyProductCreatesWithoutIssueAndAppearsInMyProducts_Expected1(IJUnitTestReporter testReport) {
        assertEquals(productName, getProductName, "The product creates without issue and appears in My Products");
    }

    @Test
    @Order(3)
    public void selectFeatureForRightNav_Step2(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPReleaseInfoFeaturesPage ndpReleaseInfoFeaturesPage = ndpReleaseInfoPage.nav().clickFeatures();
        currentPage = ndpReleaseInfoFeaturesPage;
    }

    @Test
    @Order(4)
    public void verifyTheFeatureSectionsAppear_Expected2(IJUnitTestReporter testReport) {
        NDPReleaseInfoFeaturesPage ndpReleaseInfoFeaturesPage = (NDPReleaseInfoFeaturesPage) currentPage;
        assertTrue(ndpReleaseInfoFeaturesPage.isLabelFeatureDisplayed(), "The feature sections appear");
    }

    @Test
    @Order(5)
    public void editInputDevicesSelectYesForAtLeastSomeFields_Step3(IJUnitTestReporter testReport) {
        NDPReleaseInfoFeaturesPage ndpReleaseInfoFeaturesPage = (NDPReleaseInfoFeaturesPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        HashMap<ProgramSpecificationsEnum, String> programSpecificationsSettings = FeatureSettingsManager.getProgramSpecificationsSettings(TextConstants.allOff_China);
        HashMap<InputDevicesEnum, String> inputDeviceSettings = FeatureSettingsManager.getInputDevicesSettings("T3402534");
        HashMap<PlayModesEnum, String> playModesSettings = FeatureSettingsManager.getPlayModesSettings(TextConstants.allOff);
        HashMap<LotcheckRemotePresenceEnum, String> lotcheckSettings = FeatureSettingsManager.getLotcheckPresenceSettings("Confirm");
        NDPReleaseInfoFeaturesProgramSpecsDialog progSpecDialog = ndpReleaseInfoFeaturesPage.clickEditProgramSpecifications();
        progSpecDialog.setFeatures(programSpecificationsSettings).clickSave();
        assertTrue(ndpReleaseInfoFeaturesPage.isProgramSpecificationsComplete());
        NDPReleaseInfoFeaturesInputDevicesDialog inputDeviceDialog = ndpReleaseInfoFeaturesPage.clickEditInputDevices();
        inputDeviceDialog.setFeatures(inputDeviceSettings).clickSave();
        assertTrue(ndpReleaseInfoFeaturesPage.isInputDevicesComplete(), "The Input devices section will be marked as Complete");
        NDPReleaseInfoFeaturesPlayModesDialog playModesDialog = ndpReleaseInfoFeaturesPage.clickEditPlayModes();
        WaitUtils.idle(3000);
        playModesDialog.setFeatures(playModesSettings).clickSave();
        assertTrue(ndpReleaseInfoFeaturesPage.isPlayModeComplete());
        NDPReleaseInfoFeaturesLotcheckRemotePresenceDialog lotcheckDialog = ndpReleaseInfoFeaturesPage.clickEditLotcheckRemotePresence();
        lotcheckDialog.setFeatures(lotcheckSettings).clickSave();
        assertTrue(ndpReleaseInfoFeaturesPage.isLotcheckRemotePresenceComplete());
        currentPage = ndpReleaseInfoFeaturesPage;
    }

    @Test
    @Order(6)
    public void verifyTheInputDevicesSectionWillBeMarksAsComplete_Expected3(IJUnitTestReporter testReport) {
        NDPReleaseInfoFeaturesPage ndpReleaseInfoFeaturesPage = (NDPReleaseInfoFeaturesPage) currentPage;
        assertTrue(ndpReleaseInfoFeaturesPage.isInputDevicesComplete(), "The Input devices section will be marked as Complete");
    }

    @Test
    @Order(7)
    public void selectTestScenariosAndFillInUniqueInformationAndUploadFileForEachTestScenario_Step4(IJUnitTestReporter testReport) {
        NDPReleaseInfoFeaturesPage ndpReleaseInfoFeaturesPage = (NDPReleaseInfoFeaturesPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPReleaseInfoTestScenarios ndpReleaseInfoTestScenarios = ndpReleaseInfoFeaturesPage.nav().clickTestScenarios();
        WaitUtils.idle(6000);
        nameOfSection = ndpReleaseInfoTestScenarios.getNameOfSection();
        System.out.println(nameOfSection);
        ndpReleaseInfoTestScenarios.setFocusedTestScene("Input Devices", "How to check touch screen")
                .expandCategory()
                .expandTestScene()
                .enterNameThisMethod(TextConstants.enterSameNameMethod, 1)
                .selectFileToAdd(new File("src/test/resources/DataFile/Test_excel.xlsx"), 1)
                .enterExplainHowToDemonstrateTheMethod(TextConstants.explanation, 1)
                .setFocusedTestScene("Input Devices", "How to check Nintendo Switch Pro Controller")
                .expandTestScene()
                .enterNameThisMethod(TextConstants.enterSameNameMethod, 1)
                .selectFileToAdd(new File("src/test/resources/DataFile/Test_excel.xlsx"), 1)
                .enterExplainHowToDemonstrateTheMethod(TextConstants.explanation, 1)
                .clickOnSaveButton();
        WaitUtils.idle(5000);
        ndpReleaseInfoTestScenarios.setFocusedTestScene("Input Devices", "How to check touch screen")
                .expandCategory()
                .expandTestScene();
        WaitUtils.idle(5000);
        statusOfSection = ndpReleaseInfoTestScenarios.getStatusOfSection();
        currentPage = ndpReleaseInfoTestScenarios;
    }

    @Test
    @Order(8)
    public void verifyTestScenario_Step4(IJUnitTestReporter testReport) {
        NDPReleaseInfoTestScenarios ndpReleaseInfoTestScenarios = (NDPReleaseInfoTestScenarios) currentPage;
        assertAll(() -> assertEquals("Input Devices", nameOfSection, "An Input Devices section should appear with Test Scenarios"),
                () -> assertEquals("Complete", statusOfSection, "The Test Scenario section is marked as complete"),
                () -> assertTrue(ndpReleaseInfoTestScenarios.isSaveDataDisplayed(fileName1), "A File is attached to Test Scenario"));
    }

    @Test
    @Order(9)
    public void expandTheSectionAndSubSectionToViewTheTestMethod_Step5(IJUnitTestReporter testReport) {
        NDPReleaseInfoTestScenarios ndpReleaseInfoTestScenarios = (NDPReleaseInfoTestScenarios) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        currentPage = ndpReleaseInfoTestScenarios;
    }

    @Test
    @Order(10)
    public void verifyEachSectionWillIncludeAnAddMethodOption_Expected5(IJUnitTestReporter testReport) {
        NDPReleaseInfoTestScenarios ndpReleaseInfoTestScenarios = (NDPReleaseInfoTestScenarios) currentPage;
        assertTrue(ndpReleaseInfoTestScenarios.isAddMethodButtonDisplayed(), "Section will include an Add Method option");
    }

    @Test
    @Order(11)
    public void selectAddMethodAndRepeatThisProcessSoNewTestScenarioMethodsAdded_Step6(IJUnitTestReporter testReport) {
        NDPReleaseInfoTestScenarios ndpReleaseInfoTestScenarios = (NDPReleaseInfoTestScenarios) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        int numberOfMethodAdd = 10;
        for (int i = 1; i <= numberOfMethodAdd; i++) {
            ndpReleaseInfoTestScenarios.clickAddMethod();
            WaitUtils.idle(5000);
            ndpReleaseInfoTestScenarios.enterNameThisMethod(TextConstants.enterSameNameMethod, 1)
                    .enterExplainHowToDemonstrateTheMethod(TextConstants.explanation, 1);
        }
        ndpReleaseInfoTestScenarios.clickAddMethod();
        WaitUtils.idle(3000);
        numberOfMethodBeforeSave = ndpReleaseInfoTestScenarios.getActualNumberOfMethod();
        System.out.println(numberOfMethodBeforeSave);
        ndpReleaseInfoTestScenarios.clickOnSaveButton();
        WaitUtils.idle(15000);
        ndpReleaseInfoTestScenarios.setFocusedTestScene("Input Devices", "How to check touch screen")
                .expandCategory()
                .expandTestScene();
        numberOfMethodAfterSave = ndpReleaseInfoTestScenarios.getActualNumberOfMethod();
        System.out.println(numberOfMethodAfterSave);
        currentPage = ndpReleaseInfoTestScenarios;
    }

    @Test
    @Order(12)
    public void verifyAddMethod_Step6(IJUnitTestReporter testReport) {
        assertNotEquals(numberOfMethodBeforeSave, numberOfMethodAfterSave, "If the required fields are not saved it will automatically be removed from Test Scenarios");
    }

    @Test
    @Order(13)
    public void add1KBFileToOneOfTheTestScenarioMethodsAndSave_Step7(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoTestScenarios ndpReleaseInfoTestScenarios = (NDPReleaseInfoTestScenarios) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpReleaseInfoTestScenarios.createNewFile(fileName2, 1024);
        ndpReleaseInfoTestScenarios.selectFileToAdd(new File("src/test/resources/DataFile/1KB.txt"), 1)
                .clickOnSaveButton();
        WaitUtils.idle(5000);
        ndpReleaseInfoTestScenarios.setFocusedTestScene("Input Devices", "How to check touch screen")
                .expandCategory()
                .expandTestScene();
        WaitUtils.idle(5000);
        ndpReleaseInfoTestScenarios.deleteFile(fileName2);
        assertTrue(ndpReleaseInfoTestScenarios.isSaveDataDisplayed(fileName2), "The file is successfully added to the Test Scenario.");
        currentPage = ndpReleaseInfoTestScenarios;
    }

    @Test
    @Order(14)
    public void verifyTheFileIsSuccessfullyAddedToTheTestScenario_Expected7(IJUnitTestReporter testReport) {
        NDPReleaseInfoTestScenarios ndpReleaseInfoTestScenarios = (NDPReleaseInfoTestScenarios) currentPage;
        assertTrue(ndpReleaseInfoTestScenarios.isSaveDataDisplayed(fileName2), "The file is successfully added to the Test Scenario.");
    }

    @Test
    @Order(15)
    public void add10KBFileToOneOfTheTestScenarioMethodsAndSave_Step8(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoTestScenarios ndpReleaseInfoTestScenarios = (NDPReleaseInfoTestScenarios) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpReleaseInfoTestScenarios.createNewFile(fileName3, 10240);
        ndpReleaseInfoTestScenarios.selectFileToAdd(new File("src/test/resources/DataFile/10KB.txt"), 2)
                .clickOnSaveButton();
        WaitUtils.idle(5000);
        ndpReleaseInfoTestScenarios.setFocusedTestScene("Input Devices", "How to check touch screen")
                .expandCategory()
                .expandTestScene();
        WaitUtils.idle(5000);
        ndpReleaseInfoTestScenarios.deleteFile(fileName3);
        currentPage = ndpReleaseInfoTestScenarios;
    }

    @Test
    @Order(16)
    public void verifyTheFileIsSuccessfullyAddedToTheTestScenario_Expected8(IJUnitTestReporter testReport) {
        NDPReleaseInfoTestScenarios ndpReleaseInfoTestScenarios = (NDPReleaseInfoTestScenarios) currentPage;
        assertTrue(ndpReleaseInfoTestScenarios.isSaveDataDisplayed(fileName3), "The file is successfully added to the Test Scenario.");
    }

    @Test
    @Order(17)
    public void add500KBFileToOneOfTheTestScenarioMethodsAndSave_Step9(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoTestScenarios ndpReleaseInfoTestScenarios = (NDPReleaseInfoTestScenarios) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpReleaseInfoTestScenarios.createNewFile(fileName4, 512000);
        ndpReleaseInfoTestScenarios.selectFileToAdd(new File("src/test/resources/DataFile/500KB.txt"), 3)
                .clickOnSaveButton();
        WaitUtils.idle(5000);
        ndpReleaseInfoTestScenarios.setFocusedTestScene("Input Devices", "How to check touch screen")
                .expandCategory()
                .expandTestScene();
        WaitUtils.idle(5000);
        ndpReleaseInfoTestScenarios.deleteFile(fileName4);
        currentPage = ndpReleaseInfoTestScenarios;
    }

    @Test
    @Order(18)
    public void verifyTheFileIsSuccessfullyAddedToTheTestScenario_Expected9(IJUnitTestReporter testReport) {
        NDPReleaseInfoTestScenarios ndpReleaseInfoTestScenarios = (NDPReleaseInfoTestScenarios) currentPage;
        assertTrue(ndpReleaseInfoTestScenarios.isSaveDataDisplayed(fileName4), "The file is successfully added to the Test Scenario.");
    }

    @Test
    @Order(19)
    public void add100MBFileToOneOfTheTestScenarioMethodsAndSave_Step10(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoTestScenarios ndpReleaseInfoTestScenarios = (NDPReleaseInfoTestScenarios) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpReleaseInfoTestScenarios.createNewFile(fileName5, 104857600);
        ndpReleaseInfoTestScenarios.selectFileToAdd(new File("src/test/resources/DataFile/100MB.txt"), 4)
                .clickOnSaveButton();
        WaitUtils.idle(10000);
        ndpReleaseInfoTestScenarios.setFocusedTestScene("Input Devices", "How to check touch screen")
                .expandCategory()
                .expandTestScene();
        WaitUtils.idle(5000);
        ndpReleaseInfoTestScenarios.deleteFile(fileName5);
        currentPage = ndpReleaseInfoTestScenarios;
    }

    @Test
    @Order(20)
    public void verifyTheFileIsSuccessfullyAddedToTheTestScenario_Expected10(IJUnitTestReporter testReport) {
        NDPReleaseInfoTestScenarios ndpReleaseInfoTestScenarios = (NDPReleaseInfoTestScenarios) currentPage;
        assertTrue(ndpReleaseInfoTestScenarios.isSaveDataDisplayed(fileName5), "The file is successfully added to the Test Scenario.");
    }

    @Test
    @Order(21)
    public void add500MBFileToOneOfTheTestScenarioMethodsAndSave_Step11(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoTestScenarios ndpReleaseInfoTestScenarios = (NDPReleaseInfoTestScenarios) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpReleaseInfoTestScenarios.createNewFile(fileName6, 524288000);
        ndpReleaseInfoTestScenarios.selectFileToAdd(new File("src/test/resources/DataFile/500MB.txt"), 5)
                .clickOnSaveButton();
        WaitUtils.idle(1000);
        ndpReleaseInfoTestScenarios.setFocusedTestScene("Input Devices", "How to check touch screen")
                .expandCategory()
                .expandTestScene();
        WaitUtils.idle(5000);
        ndpReleaseInfoTestScenarios.deleteFile(fileName6);
        currentPage = ndpReleaseInfoTestScenarios;
    }

    @Test
    @Order(22)
    public void verifyTheFileIsSuccessfullyAddedToTheTestScenario_Expected11(IJUnitTestReporter testReport) {
        NDPReleaseInfoTestScenarios ndpReleaseInfoTestScenarios = (NDPReleaseInfoTestScenarios) currentPage;
        assertTrue(ndpReleaseInfoTestScenarios.isSaveDataDisplayed(fileName6), "The file is successfully added to the Test Scenario.");
    }

    @Test
    @Order(23)
    public void add1GBFileToOneOfTheTestScenarioMethodsAndSave_Step12(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoTestScenarios ndpReleaseInfoTestScenarios = (NDPReleaseInfoTestScenarios) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpReleaseInfoTestScenarios.createNewFile(fileName7, 1073741824);
        ndpReleaseInfoTestScenarios.selectFileToAdd(new File("src/test/resources/DataFile/1GB.txt"), 6)
                .clickOnSaveButton();
        WaitUtils.idle(10000);
        ndpReleaseInfoTestScenarios.setFocusedTestScene("Input Devices", "How to check touch screen")
                .expandCategory()
                .expandTestScene();
        WaitUtils.idle(5000);
        ndpReleaseInfoTestScenarios.deleteFile(fileName7);
        currentPage = ndpReleaseInfoTestScenarios;
    }

    @Test
    @Order(24)
    public void verifyTheFileIsSuccessfullyAddedToTheTestScenario_Expected12(IJUnitTestReporter testReport) {
        NDPReleaseInfoTestScenarios ndpReleaseInfoTestScenarios = (NDPReleaseInfoTestScenarios) currentPage;
        assertTrue(ndpReleaseInfoTestScenarios.isSaveDataDisplayed(fileName7), "The file is successfully added to the Test Scenario.");
    }

    @Test
    @Order(25)
    public void addARandomAssortmentOfFilesToTheRemaining3AddedTestMethodsAndSave_Step13(IJUnitTestReporter testReport) {
        NDPReleaseInfoTestScenarios ndpReleaseInfoTestScenarios = (NDPReleaseInfoTestScenarios) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpReleaseInfoTestScenarios.selectFileToAdd(new File("src/test/resources/DataFile/test1.txt"), 7)
                .clickOnSaveButton();
        WaitUtils.idle(5000);
        ndpReleaseInfoTestScenarios.setFocusedTestScene("Input Devices", "How to check touch screen")
                .expandCategory()
                .expandTestScene()
                .selectFileToAdd(new File("src/test/resources/DataFile/test2.txt"), 8)
                .clickOnSaveButton();
        ndpReleaseInfoTestScenarios.setFocusedTestScene("Input Devices", "How to check touch screen")
                .expandCategory()
                .expandTestScene()
                .selectFileToAdd(new File("src/test/resources/DataFile/test3.txt"), 9)
                .clickOnSaveButton();
        ndpReleaseInfoTestScenarios.setFocusedTestScene("Input Devices", "How to check touch screen")
                .expandCategory()
                .expandTestScene();
        WaitUtils.idle(5000);
        currentPage = ndpReleaseInfoTestScenarios;
    }

    @Test
    @Order(26)
    public void verifyTheFileAreSuccessfullyAddedToTheTestScenario_Expected13(IJUnitTestReporter testReport) {
        NDPReleaseInfoTestScenarios ndpReleaseInfoTestScenarios = (NDPReleaseInfoTestScenarios) currentPage;
        assertAll(() -> assertTrue(ndpReleaseInfoTestScenarios.isSaveDataDisplayed(fileName8), "The file is successfully added to the Test Scenario."),
                () -> assertTrue(ndpReleaseInfoTestScenarios.isSaveDataDisplayed(fileName9), "The file is successfully added to the Test Scenario."),
                () -> assertTrue(ndpReleaseInfoTestScenarios.isSaveDataDisplayed(fileName10), "The file is successfully added to the Test Scenario."));
    }

    @Test
    @Order(27)
    public void forOneOfTheExistingTestScenarioAddA10MBFileAndSave_Step14(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoTestScenarios ndpReleaseInfoTestScenarios = (NDPReleaseInfoTestScenarios) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpReleaseInfoTestScenarios.createNewFile(fileName11, 10485760);
        ndpReleaseInfoTestScenarios.nav().clickTestScenarios()
                .setFocusedTestScene("Input Devices", "How to check Nintendo Switch Pro Controller")
                .expandCategory()
                .expandTestScene()
                .enterNameThisMethod(TextConstants.enterSameNameMethod, 1)
                .selectFileToAdd(new File("src/test/resources/DataFile/10MB.txt"), 1)
                .enterExplainHowToDemonstrateTheMethod(TextConstants.explanation, 1)
                .clickOnSaveButton();
        WaitUtils.idle(5000);
        ndpReleaseInfoTestScenarios.setFocusedTestScene("Input Devices", "How to check Nintendo Switch Pro Controller")
                .expandCategory()
                .expandTestScene();
        ndpReleaseInfoTestScenarios.deleteFile(fileName11);
        WaitUtils.idle(5000);
    }

    @Test
    @Order(28)
    public void verifyTheFileIsSuccessfullyAddedToTheTestScenario_Expected14(IJUnitTestReporter testReport) {
        NDPReleaseInfoTestScenarios ndpReleaseInfoTestScenarios = (NDPReleaseInfoTestScenarios) currentPage;
        assertTrue(ndpReleaseInfoTestScenarios.isSaveDataDisplayed(fileName11), "The file is successfully added to the Test Scenario.");
    }

    @Test
    @Order(29)
    public void completeTheRestOfTheReleaseDashboardAndThenSubmitToLotcheck_Step15(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();
        applicationID = ndpProductDashboardProductInfoTab.getApplicationID();
        gameCode = ndpProductDashboardProductInfoTab.getGameCode();
        initialCode = ndpProductDashboardProductInfoTab.getInitialCode();

        //Create file ROM
        romBrowser = BrowserManager.getNewInstance();
        ROMLoginPage romLoginPage = romBrowser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN);
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom.selectRequester(TextConstants.test_User)
                .selectType(TextConstants.initial_Release)
                .enterProductName(productName)
                .enterApplicationId(applicationID)
                .selectPublishingRelationShip(TextConstants.third_Party_Type)
                .enterDisplayVersion("1.0.0")
                .enterReleaseVersion("00")
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .clickOnCreateButton()
                .waitIsLoaded();
        fileRomName = romCreateRom.getRomName();
        romCreateRom.clickOnROMFileOnROMTable(applicationID);
        WaitUtils.idle(8000);
        romCreateRom.clickOnUserNameMenu().clickOnLogoutButton();
        BrowserManager.closeBrowser(romBrowser);

        //Upload ROM
        NDPMyProductsPage myProductsPage1 = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN);
        NDPProductDashboardPage ndpProductDashboardPage1 = myProductsPage1.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage1.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = ndpReleaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
        ndpReleaseInfoROMUpload.deleteFile(fileRomName);

        // Issue
        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
        issuePage.openRequiredApprovalTab()
                .expandFirstUnresolvedRequiredApprovals()
                .selectDesiredApprovalTypeAtSecondUnresolvedRequiredApproval()
                .enterReasonForRequest(TextConstants.enterReasonForRequest)
                .clickOnSendResolutionButton()
                .openRequiredApprovalTab();
        CommonAction.enterReasonForRequest(issuePage);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();

        //Approval Issue Task Requested
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(licUser).clickOnMyTaskMenu();
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, gameCode);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();

        //Submit product
        ndpReleaseInfoPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN)
                .clickSignInPage()
                .signIn(ndpUser)
                .clickMyProducts()
                .clickProductByName(productName)
                .clickReleases()
                .selectInitialRelease();
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.enterDeveloperComment).clickSubmit();
        WaitUtils.idle(3000);
        currentPage = ndpReleaseInfoPage;
    }

    @Test
    @Order(30)
    public void verifyAddedFilesStillAppearInTheTestScenarioSectionPostSubmission_Expected15(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPReleaseInfoTestScenarios ndpReleaseInfoTestScenarios = ndpReleaseInfoPage.nav().clickTestScenarios();
        LotcheckEditAccountPreferences editAccountPreferences = ndpReleaseInfoTestScenarios.navBar().clickMyAccount();
        if (editAccountPreferences.isCurrentLanguageEnglish()) {
            editAccountPreferences.clickCancelButton();
        } else {
            editAccountPreferences.selectLanguage(TextConstants.english)
                    .clickSaveButton();
            ndpReleaseInfoTestScenarios.waitPageLoaded();
        }
        ndpReleaseInfoTestScenarios.setFocusedTestScene("Input Devices", "How to check touch screen")
                .expandCategory()
                .expandTestScene();
        WaitUtils.idle(5000);
        assertAll(() -> assertTrue(ndpReleaseInfoTestScenarios.isSaveDataDisplayed(fileName1), "The file is still appear in the Test Scenario."),
                () -> assertTrue(ndpReleaseInfoTestScenarios.isSaveDataDisplayed(fileName2), "The file is still appear in the Test Scenario."),
                () -> assertTrue(ndpReleaseInfoTestScenarios.isSaveDataDisplayed(fileName3), "The file is still appear in the Test Scenario."),
                () -> assertTrue(ndpReleaseInfoTestScenarios.isSaveDataDisplayed(fileName4), "The file is still appear in the Test Scenario."),
                () -> assertTrue(ndpReleaseInfoTestScenarios.isSaveDataDisplayed(fileName5), "The file is still appear in the Test Scenario."),
                () -> assertTrue(ndpReleaseInfoTestScenarios.isSaveDataDisplayed(fileName6), "The file is still appear in the Test Scenario."),
                () -> assertTrue(ndpReleaseInfoTestScenarios.isSaveDataDisplayed(fileName7), "The file is still appear in the Test Scenario."),
                () -> assertTrue(ndpReleaseInfoTestScenarios.isSaveDataDisplayed(fileName8), "The file is still appear in the Test Scenario."),
                () -> assertTrue(ndpReleaseInfoTestScenarios.isSaveDataDisplayed(fileName9), "The file is still appear in the Test Scenario."),
                () -> assertTrue(ndpReleaseInfoTestScenarios.isSaveDataDisplayed(fileName10), "The file is still appear in the Test Scenario."));
        NDPReleaseInfoTestScenarios ndpReleaseInfoTestScenarios1 = ndpReleaseInfoPage.nav().clickTestScenarios();
        ndpReleaseInfoTestScenarios1.setFocusedTestScene("Input Devices", "How to check Nintendo Switch Pro Controller")
                .expandCategory()
                .expandTestScene();
        WaitUtils.idle(3000);
        assertTrue(ndpReleaseInfoTestScenarios.isSaveDataDisplayed(fileName11), "The file is still appear in the Test Scenario.");
    }

    @Test
    @Order(31)
    public void loginAsLotcheckUserLocateTheSubmittedTestPlanAndViewItsLotcheckReport_Step16(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckLandingPage lotcheckLandingPage = lotcheckHomePage.continueToLogin().loginWithOvdLink(slcmsUser);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickOverallLotcheck(LotcheckRegion.NCL);
        lotcheckQueuePage.searchByInitialCode(initialCode)
                .clickPlannedTestDateNotSet();
        lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
    }

    @Test
    @Order(32)
    public void verifyTheLotcheckUserIsTakenToTheSubmissionLevelOfTheLotcheckReport_Expected16(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = browser.getPage(LotcheckReportSubmissionOverview.class, testReport);
        String titleOverviewPage = lotcheckReportSubmissionOverview.getTitlePage();
        assertEquals("Overview", titleOverviewPage, "The Submission level of Lotcheck report is displayed");
    }

    @Test
    @Order(33)
    public void selectTheTestScenariosTab_Step17(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = browser.getPage(LotcheckReportSubmissionOverview.class, testReport);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        lotcheckReportSubmissionOverview.navBar().clickTestScenarios();
    }

    @Test
    @Order(34)
    public void verifyLotcheckUserCanSeeAllOfTheTestScenarioThatWereAddedFormPartner_Expected17(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionTestScenarios lotcheckReportSubmissionTestScenarios = browser.getPage(LotcheckReportSubmissionTestScenarios.class, testReport);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckEditAccountPreferences editAccountPreferences = lotcheckReportSubmissionTestScenarios.navBar().clickMyAccount();
        if (editAccountPreferences.isCurrentLanguageEnglish()) {
            editAccountPreferences.clickCancelButton();
        } else {
            editAccountPreferences.selectLanguage(TextConstants.english)
                    .clickSaveButton();
            lotcheckReportSubmissionTestScenarios.waitPageLoaded();
        }
        howToCheckTouchScreen = lotcheckReportSubmissionTestScenarios.howToCheckTouchScreenIsDisplay();
        howToCheckNintendoSwitchProController = lotcheckReportSubmissionTestScenarios.howToCheckNintendoSwitchProControllerIsDisplay();
        assertAll(
                () -> assertTrue(howToCheckTouchScreen, "The Lotcheck user can see the Test Scenarios that were added from the partner"),
                () -> assertTrue(howToCheckNintendoSwitchProController, "The Lotcheck user can see the Test Scenarios that were added from the partner")
        );
    }

    @Test
    @Order(35)
    public void selectTheFilesTab_Step18(IJUnitTestReporter testReport) {
        LotcheckReportNav lotcheckReportNav = browser.getPage(LotcheckReportNav.class, testReport);
        lotcheckReportNav.clickFiles();
    }

    @Test
    @Order(36)
    public void verifyAllSubFoldersWithinTheTestScenesFolderAreNamesBasedOnTheTestScenarioMethod_Expected18(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionFiles lotcheckReportSubmissionFiles = browser.getPage(LotcheckReportSubmissionFiles.class, testReport);
        LotcheckEditAccountPreferences editAccountPreferences = lotcheckReportSubmissionFiles.navBar().clickMyAccount();
        if (editAccountPreferences.isCurrentLanguageEnglish()) {
            editAccountPreferences.clickCancelButton();
        } else {
            editAccountPreferences.selectLanguage(TextConstants.english)
                    .clickSaveButton();
            lotcheckReportSubmissionFiles.waitPageLoaded();
        }
        String subFolder1 = lotcheckReportSubmissionFiles.getNameOfFolder(6);
        String subFolder2 = lotcheckReportSubmissionFiles.getNameOfFolder(7);
        String subFolder3 = lotcheckReportSubmissionFiles.getNameOfFolder(8);
        String subFolder4 = lotcheckReportSubmissionFiles.getNameOfFolder(9);
        String subFolder5 = lotcheckReportSubmissionFiles.getNameOfFolder(10);
        String subFolder6 = lotcheckReportSubmissionFiles.getNameOfFolder(11);
        String subFolder7 = lotcheckReportSubmissionFiles.getNameOfFolder(12);
        String subFolder8 = lotcheckReportSubmissionFiles.getNameOfFolder(13);
        String subFolder9 = lotcheckReportSubmissionFiles.getNameOfFolder(14);
        String subFolder10 = lotcheckReportSubmissionFiles.getNameOfFolder(15);
        String subFolder11 = lotcheckReportSubmissionFiles.getNameOfFolder(16);
        String subFolder12 = lotcheckReportSubmissionFiles.getNameOfFolder(17);
        String subFolder13 = lotcheckReportSubmissionFiles.getNameOfFolder(18);
        assertAll(
                () -> assertEquals("How to check Nintendo Switch Pro Controller", subFolder1, "All subfolders within the test_scenes folder are named based on the Test Scenarios method"),
                () -> assertEquals(TextConstants.enterSameNameMethod, subFolder2, "All subfolders within the test_scenes folder are named based on the Test Scenarios method"),
                () -> assertEquals("How to check touch screen", subFolder3, "All subfolders within the test_scenes folder are named based on the Test Scenarios method"),
                () -> assertEquals(TextConstants.enterSameNameMethod, subFolder4),
                () -> assertEquals(TextConstants.enterSameNameMethod, subFolder5, "All subfolders within the test_scenes folder are named based on the Test Scenarios method"),
                () -> assertEquals(TextConstants.enterSameNameMethod, subFolder6, "All subfolders within the test_scenes folder are named based on the Test Scenarios method"),
                () -> assertEquals(TextConstants.enterSameNameMethod, subFolder7, "All subfolders within the test_scenes folder are named based on the Test Scenarios method"),
                () -> assertEquals(TextConstants.enterSameNameMethod, subFolder8, "All subfolders within the test_scenes folder are named based on the Test Scenarios method"),
                () -> assertEquals(TextConstants.enterSameNameMethod, subFolder9, "All subfolders within the test_scenes folder are named based on the Test Scenarios method"),
                () -> assertEquals(TextConstants.enterSameNameMethod, subFolder10, "All subfolders within the test_scenes folder are named based on the Test Scenarios method"),
                () -> assertEquals(TextConstants.enterSameNameMethod, subFolder11, "All subfolders within the test_scenes folder are named based on the Test Scenarios method"),
                () -> assertEquals(TextConstants.enterSameNameMethod, subFolder12, "All subfolders within the test_scenes folder are named based on the Test Scenarios method"),
                () -> assertEquals(TextConstants.enterSameNameMethod, subFolder13, "All subfolders within the test_scenes folder are named based on the Test Scenarios method")
        );
    }

    @Test
    @Order(37)
    public void ensureThatTheFilesCanBeViewedAndDownloadedByLotcheckUser_Step19(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionFiles lotcheckReportSubmissionFiles = browser.getPage(LotcheckReportSubmissionFiles.class, testReport);
        lotcheckReportSubmissionFiles.selectMethodOfTestScenario(TextConstants.enterSameNameMethod, 1);
        assertTrue(lotcheckReportSubmissionFiles.isFileNameIsDisplayed(fileName11), "The file is displayed");
        lotcheckReportSubmissionFiles.clickDownloadFile(fileName11);
        assertNotEquals(0L, lotcheckReportSubmissionFiles.getFileSizeFromPath(fileName11), "Verify download file successfully");
    }

}
