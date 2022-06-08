package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.junit.TestLogger;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.data.DataGen;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.common.PageState;
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
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoROMUpload;
import net.nintendo.automation.ui.models.rom.ROMCreateRom;
import net.nintendo.automation.ui.models.rom.ROMLoginPage;
import net.nintendo.automation.ui.models.testrail.TestRailCompareResultsDiaLog;
import net.nintendo.automation.ui.models.testrail.TestRailDetailTestcaseDialog;
import net.nintendo.automation.ui.models.testrail.TestRailLoginPage;
import net.nintendo.automation.ui.models.testrail.TestRailManagerDashboardPage;
import net.nintendo.automation.ui.models.testrail.TestRailMyToDoPage;
import net.nintendo.automation.ui.models.testrail.TestRailTestPlanTable;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Classname: T3402575
 * Version: 1.0.0
 * Purpose:
 */
@Tag("T3402575")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3402575 extends CommonBaseTest {
    private static Browser browser;
    private static Browser romBrowser;
    private static User romUser;
    private static User ndpUser;
    private static User slcmsUser;
    private static User testrailUser;
    private static User licUser;
    private static CommonUIComponent currentPage;
    private static TestLogger logger;

    private static String productName;
    private static String publishingRelationship;
    private static String applicationID;
    private static String initialCode;
    private static String gameCode;
    private static String fileRomName;
    private static String productNameProductB;
    private static String applicationIDProductB;
    private static String initialCodeProductB;
    private static String gameCodeProductB;
    private static String fileRomNameProductB;
    private static String testcase_01;

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_Trang);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
        slcmsUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);
        testrailUser = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER);
        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);
        publishingRelationship = TextConstants.third_Party_Type;
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
    }

    @AfterAll
    static void terminateBrowser() {
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

    @Test()
    @Order(1)
    public void createAndIssueGameCodeProductA_Step(IJUnitTestReporter testReport) {
        productName = DataGen.getRandomProductName();
        NDPHomePage homePage = (NDPHomePage) currentPage;
        NDPMyProductsPage myProductsPage = homePage.acceptCookie().clickSignInPage().signIn(ndpUser).clickMyProducts();
        myProductsPage.createNewProduct()
                .selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(publishingRelationship)
                .selectTargetDeliveryFormat(TextConstants.both_Target_Type)
                .selectDigitalJapanSalesRegion()
                .selectPhysicalJapanSalesRegion()
                .selectProductName(productName)
                .selectProductNameKana(TextConstants.productKana)
                .clickCreateButton();
        NDPProductDashboardPage myProductDashBoard = myProductsPage.clickProductByName(productName);
        CommonAction.issueGameCode(myProductDashBoard, 60,"NO");
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = myProductDashBoard.clickProductInfo();
        applicationID = ndpProductDashboardProductInfoTab.getApplicationID();
        gameCode = ndpProductDashboardProductInfoTab.getGameCode();
        initialCode = ndpProductDashboardProductInfoTab.getInitialCode();
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = myProductDashBoard.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        NDPReleaseInfoPage releaseInfoPage = ndpReleaseInfoPage.nav().clickReleaseInfo();
        romBrowser = BrowserManager.getNewInstance();
        currentPage = romBrowser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ROMLoginPage romLoginPage = (ROMLoginPage) currentPage;
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom
                .selectRequester(TextConstants.test_User)
                .selectType(TextConstants.initial_Release)
                .selectTargetDeliveryFormat(TextConstants.both_Target_Type)
                .selectMPACheckbox()
                .selectNumberOfProgram("11")
                .enterProductName(productName)
                .enterApplicationId(applicationID)
                .selectPublishingRelationShip(TextConstants.third_Party_Type)
                .enterDisplayVersion("1.0.0")
                .enterReleaseVersion("00")
                .selectCERORating("CERO: Not required")
                .clickOnCreateButton()
                .waitIsLoaded();
        fileRomName = romCreateRom.getRomNameTable(applicationID);
        romCreateRom.clickOnROMFileOnROMTable(applicationID);
        WaitUtils.idle(20000);
        BrowserManager.closeBrowser(romBrowser);
        //navigate to My Product
        ndpReleaseInfoPage.navigateToProduct();
        currentPage = myProductsPage;
    }

    @Test()
    @Order(2)
    public void theProductAppearsOnTheListUnderMyProducts_Expected() {
        NDPMyProductsPage ndpMyProductsPage = (NDPMyProductsPage) currentPage;
        boolean productAAppears = ndpMyProductsPage.productAppearsOnTheListUnderMyProducts(productName);
        assertAll(() -> assertTrue(productAAppears, "The product appears on the list under My Products."));
    }

    @Test()
    @Order(3)
    public void createASecondProduct_Step() {
        productNameProductB = DataGen.getRandomProductName();
        NDPMyProductsPage ndpMyProductsPage = (NDPMyProductsPage) currentPage;
        ndpMyProductsPage.createNewProduct()
                .selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(publishingRelationship)
                .selectTargetDeliveryFormat(TextConstants.both_Target_Type)
                .selectDigitalJapanSalesRegion()
                .selectPhysicalJapanSalesRegion()
                .selectProductName(productNameProductB)
                .selectProductNameKana(TextConstants.productKana)
                .selectCommonProduct(" "+productName+" ("+initialCode+") "+" ")
                .clickCreateButton();
        currentPage = ndpMyProductsPage;
    }

    @Test()
    @Order(4)
    public void theSecondProductAppearsOnTheListUnderMyProducts_Expected() {
        NDPMyProductsPage ndpMyProductsPage = (NDPMyProductsPage) currentPage;
        boolean productBAppears = ndpMyProductsPage.productAppearsOnTheListUnderMyProducts(productNameProductB);
        assertAll(() -> assertTrue(productBAppears, "The second product appears on the list under My Products."));
    }

    @Test()
    @Order(5)
    public void issueAGameCodeForBothProductsB_Step() {
        NDPMyProductsPage ndpMyProductsPage = (NDPMyProductsPage) currentPage;
        NDPProductDashboardPage myProductDashBoard = ndpMyProductsPage.clickProductByName(productNameProductB);
        CommonAction.issueGameCode(myProductDashBoard, 60,"NO");
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = myProductDashBoard.clickProductInfo();
        applicationIDProductB = ndpProductDashboardProductInfoTab.getApplicationID();
        gameCodeProductB = ndpProductDashboardProductInfoTab.getGameCode();
        initialCodeProductB = ndpProductDashboardProductInfoTab.getInitialCode();
    }

    @Test()
    @Order(6)
    public void confirmInitialCodeAtProductAAndB_Expected() {
        assertAll(
                () -> assertEquals("A", initialCode.substring(4), "The fifth digit of the Initial Code for the first product is A"),
                () -> assertEquals("B", initialCodeProductB.substring(4), "The fifth digit of the Initial Code for the first second product is B")
        );
    }

    @Test()
    @Order(7)
    public void fillOurDashboardSubmitProductA_Step(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        NDPReleaseInfoPage releaseInfoPage = ndpReleaseInfoPage.nav().clickReleaseInfo();

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
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
        releaseInfoPage.nav().clickIssues().openRequiredApprovalTab().performAllIssueAtApprovalTab(false);

        NDPDevelopmentHome developmentHome = ndpReleaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, gameCode);
        WaitUtils.idle(10000);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();

        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser).clickMyProducts();
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        //enter test scenarios
        myProductsPage.clickProductByName(productName).clickReleases().selectInitialRelease().nav().clickTestScenarios().expandAllSections()
                .expandAllTestScenes()
                .enterSubjectForProgramSpecificationsHowToStartUpTheIncludedProgramsIncluded(TextConstants.commentTestScenario, 0)
                .enterExplainForProgramSpecificationsHowToStartUpTheIncludedProgramsIncluded(TextConstants.commentExplainProgram, 0)
                .clickOnSaveButton();
        WaitUtils.idle(30000);
        releaseInfoPage.nav().clickIssues();
        int i=0;
        while (i<10){
            releaseInfoPage.nav().refreshPageNDP();
            i++;
        if(releaseInfoPage.nav().isTestScenariosCompleted()==true){
            break;
            }
        }
        //submit
        releaseInfoPage.nav().clickSubmitButton()
                .clickSubmit();
        WaitUtils.idle(20000);
        assertEquals(TextConstants.submitToLotcheck, ndpReleaseInfoPage.getStatusValue(), TextConstants.initialSubmitSuccess);
        //submit Product B
    }

    @Test()
    @Order(8)
    public void fillOurDashboardSubmitProductB_Step(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productNameProductB);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        NDPReleaseInfoPage releaseInfoPage = ndpReleaseInfoPage.nav().clickReleaseInfo();
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
        //create ROM
        currentPage = browser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ROMLoginPage romLoginPage = (ROMLoginPage) currentPage;
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom
                .selectRequester(TextConstants.test_User)
                .selectType(TextConstants.initial_Release)
                .selectTargetDeliveryFormat(TextConstants.both_Target_Type)
                .enterProductName(productNameProductB)
                .enterApplicationId(applicationIDProductB)
                .selectPublishingRelationShip(publishingRelationship)
                .enterDisplayVersion("1.0.0")
                .enterReleaseVersion("00")
                .selectCERORating("CERO: Not required")
                .clickOnCreateButton()
                .waitIsLoaded();
        fileRomNameProductB = romCreateRom.getRomNameTable(applicationIDProductB);
        romCreateRom.clickOnROMFileOnROMTable(applicationIDProductB);
        WaitUtils.idle(10000);
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        myProductsPage.clickProductByName(productNameProductB);
        ndpProductDashboardPage.clickReleases();
        ndpProductDashboardReleasesTab.selectInitialRelease();
        //upload ROM
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = releaseInfoPage.nav().clickROMUpload();
        WaitUtils.idle(5000);
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomNameProductB);
        releaseInfoPage.nav().clickIssues().openRequiredApprovalTab().performAllIssueAtApprovalTab(false);

        NDPDevelopmentHome developmentHome = ndpReleaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, gameCodeProductB);
        WaitUtils.idle(10000);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser).clickMyProducts();
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        myProductsPage.clickProductByName(productNameProductB).clickReleases().selectInitialRelease().nav().clickSubmitButton()
                .clickSubmit();
        WaitUtils.idle(5000);
        assertEquals(TextConstants.submitToLotcheck, ndpReleaseInfoPage.getStatusValue(), TextConstants.initialSubmitSuccess);
    }

    @Test()
    @Order(9)
    public void loginTestRailAndNavToMyToDoAndFilterTestPlan_Step(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        TestRailManagerDashboardPage managerDashboardPage = testRailLoginPage.signInTestRail(testrailUser).navTopTestrail().navigateToManagerDashboard();
        managerDashboardPage.checkErrorPopup();
        managerDashboardPage.actionFill().enterTestPlanNameFilter(gameCode)
                .selectStatusUntested().selectStatusPassed();
        managerDashboardPage.actionFill().clickOnApplyFiltersButton();
        managerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .expandAllTestRuns()
                .selectCheckBoxOfFirstTestCase()
                .clickAssignTo()
                .selectSetValueAssignToRadio()
                .selectUser("Me")
                .clickOk()
                .waitingLoadingTestSuite();
        TestRailMyToDoPage myToDoPage = managerDashboardPage.topNav()
                .navigateToMyToDo();
        myToDoPage.actionFill()
                .enterTestPlanNameFilter(gameCode);
        myToDoPage.actionFill().clickOnApplyFiltersButton();
        currentPage = myToDoPage.navTestPlanTable()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite();
    }

    @Test()
    @Order(10)
    public void verifyTestPlanOnTestConditionAreDisplayed_Expected() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        boolean isTestPlanDisplayed = !testPlanTable.testplanIsNotVisible(gameCode);
        assertAll(() -> assertEquals("My Todo", testPlanTable.getPageTitle(), "Verify the My Todo page is displayed"),
                () -> assertTrue(isTestPlanDisplayed, "Verify test plan on test condition is displayed"));
    }

    @Test()
    @Order(11)
    public void fullyExpandATestPlanAndSelectATestCase_Step() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        testPlanTable.expandAllTestSuites()
                .clickOnTheFirstTestRun();
        testcase_01 = testPlanTable.getTestcaseName(1);
        currentPage = testPlanTable.openTestcaseDetail(1);
    }

    @Test()
    @Order(12)
    public void verifyTestcaseViewModalIsDisplayed_Expected() {
        TestRailDetailTestcaseDialog detailTestcaseDialog = (TestRailDetailTestcaseDialog) currentPage;
        assertTrue(detailTestcaseDialog.isDetailTestcaseModalOfTestcaseDisplayed(testcase_01), "Verify user can access the Test Case View dialog");
    }

    @Test()
    @Order(13)
    public void clickCompareResultsTab_Step() {
        TestRailDetailTestcaseDialog detailTestcaseDialog = (TestRailDetailTestcaseDialog) currentPage;
        currentPage = detailTestcaseDialog.clickCompareResultTab();
    }

    @Test()
    @Order(14)
    public void compareResultsTabOfTestCaseWillBeDisplayed_Step() {
        TestRailCompareResultsDiaLog compareResultsDiaLog = (TestRailCompareResultsDiaLog) currentPage;
        String title = compareResultsDiaLog.getTitlePlanRegion();
        String titleExpected = gameCode + " 00.00 (1) [NCL] @LCO <Card> " + productName;
        assertAll(
                () -> assertTrue(compareResultsDiaLog.confirmDisplayProductInOtherLotcheckNumbersAsTargetTest(gameCode), "Test case display In the Other Lotcheck Numbers as Target Test section"),
                () -> assertEquals(titleExpected, title, "confirm title plan region display")
        );
    }
}
