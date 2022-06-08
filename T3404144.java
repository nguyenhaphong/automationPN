package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.junit.TestLogger;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.data.DataGen;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthPage;
import net.nintendo.automation.ui.models.lcms.LotcheckHomePage;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.ui.models.lcms.LotcheckQueuePage;
import net.nintendo.automation.ui.models.lcms.LotcheckSubmissionSearchPage;
import net.nintendo.automation.ui.models.lcms.LotcheckTestPlanRow;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssueDetails;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssues;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionMetadata;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.ndp.NDPCreateProductPage;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPLicensingWaiverRequestsTab;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.NDPViewTaskPage;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardMessagesTab;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoFeaturesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoGuidelinesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoIssuePage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoROMUpload;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("T3404144")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3404144 extends CommonBaseTest {
    private static Browser browser;
    private static User romUser;
    private static User ndpUser;
    private static User slcmsAdminUser;
    private static User slcmsOverseaCoordinatorUser;
    private static User testrailUser;
    private static User licUser;
    private static String productName;
    private static String initialCode;
    private static String gameCode;
    private static String applicationId;
    private static String publishingRelationship;
    private static String romFileName;
    private static boolean productCreatedIsDisplayed;
    private static CommonUIComponent currentPage;
    private static Integer numberOfIssue;
    private static TestLogger logger;

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_Quynhpnt2);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
        slcmsAdminUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);

        testrailUser = new User();
        testrailUser.setUserType(UserType.PARTNER_USER); //need request noa add user type of testrail to replace
        testrailUser.setUserName(TextConstants.trAdminNCL_EN);
        testrailUser.setPassword(TextConstants.trAdmin_Password);

        slcmsOverseaCoordinatorUser = new User();
        slcmsOverseaCoordinatorUser.setUserType(UserType.TRANSLATION_USER); //need request noa add user type of testrail to replace
        slcmsOverseaCoordinatorUser.setUserName(TextConstants.lc_oversea_coord_NCL);
        slcmsOverseaCoordinatorUser.setPassword(TextConstants.lc_oversea_coord_NCL_Password);

        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);

        publishingRelationship = TextConstants.third_Party_Type;
        productName = DataGen.getRandomProductName();
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).acceptCookie().clickSignInPage().signIn(ndpUser);
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
        UserManager.releaseUser(ServiceType.SLCMS, slcmsAdminUser);
    }

    @Test()
    @Order(1)
    public void createProductAndIssueGameCode_Step() {
        NDPDevelopmentHome developmentHome = (NDPDevelopmentHome) currentPage;
        NDPMyProductsPage myProductsPage = developmentHome.clickMyProducts();
        NDPCreateProductPage createProductPage = myProductsPage.createNewProduct();
        createProductPage.selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(publishingRelationship)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalJapanSalesRegion()
                .selectProductName(productName)
                .selectProductNameJp(productName)
                .selectProductNameKana(TextConstants.productKata)
                .clickCreateButton();

        productCreatedIsDisplayed = myProductsPage.productCreatedIsDisplayed(productName);
        NDPProductDashboardPage myProductDashBoard = myProductsPage.clickProductByName(productName);
        CommonAction.issueGameCode(myProductDashBoard, 90, "NO");
        applicationId = myProductDashBoard.clickProductInfo().getApplicationID();
        gameCode = myProductDashBoard.clickProductInfo().getGameCode();
        initialCode = myProductDashBoard.clickProductInfo().getInitialCode();
        currentPage = myProductDashBoard;

    }

    @Test()
    @Order(2)
    public void verifyCreateProductAndIssueGameCode_Expected() {
        NDPProductDashboardPage myProductDashBoard = (NDPProductDashboardPage) currentPage;
        assertAll(() -> assertTrue(productCreatedIsDisplayed, "Verify create product successfully"),
                () -> assertNotEquals("--", myProductDashBoard.clickProductInfo().getGameCode(), "Verify issue game code successfully"));
    }

    @Test()
    @Order(3)
    public void triggerASpecialApproveFromFeaturePage_Step() {
        NDPProductDashboardPage myProductDashBoard = (NDPProductDashboardPage) currentPage;
        NDPReleaseInfoPage releaseInfoPage = myProductDashBoard.clickReleases().selectInitialRelease();
        CommonAction.selectROMSameAllRegion(releaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);
        CommonAction.getFeatureOptionData(releaseInfoPage, TextConstants.allOff,
                TextConstants.allOff, TextConstants.allOff, false, "", false, "");
        NDPReleaseInfoFeaturesPage releaseFeatures = releaseInfoPage.nav().clickFeatures();
        NDPReleaseInfoFeaturesProgramSpecsDialog progSpecDialog = releaseFeatures.clickEditProgramSpecifications();
        progSpecDialog.selectSupportToyCoy(TextConstants.yes_Value);
        currentPage = progSpecDialog;
    }

    @Test()
    @Order(4)
    public void verifyAWaiverWarningAppears_Expected() {
        NDPReleaseInfoFeaturesProgramSpecsDialog progSpecDialog = (NDPReleaseInfoFeaturesProgramSpecsDialog) currentPage;
        assertTrue(progSpecDialog.isWaiverWarningDisplayed(), "Verify a waiver warning appears for the feature in question");
    }

    @Test()
    @Order(5)
    public void completeFeaturePageAndSelectGuideline_Step() {
        NDPReleaseInfoFeaturesProgramSpecsDialog programSpecDialog = (NDPReleaseInfoFeaturesProgramSpecsDialog) currentPage;
        currentPage = programSpecDialog.selectToyCoyRcCar().clickSave().nav().clickGuidelines();
    }

    @Test()
    @Order(6)
    public void verifyStatusFeatureSectionAndNumberOfIssue_Expected() {
        NDPReleaseInfoGuidelinesPage guidelinesPage = (NDPReleaseInfoGuidelinesPage) currentPage;
        assertAll(() -> assertFalse(guidelinesPage.nav().isFeaturesCompleted(), "Verify features NOT checks off"),
                () -> assertEquals("1", guidelinesPage.nav().getNumberOfIssue(), "Verify require waivers appear in the issue"));

    }

    @Test()
    @Order(7)
    public void selectAFewGuideline_Step() {
        NDPReleaseInfoGuidelinesPage guidelinesPage = (NDPReleaseInfoGuidelinesPage) currentPage;
        guidelinesPage.selectCannotConform0003ProhibitionOfSupporting3DTelevisions()
                .selectCannotConform0011LimitsOnFrequencyAndAmountOfWriting()
                .selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();

    }

    @Test()
    @Order(8)
    public void verifyGuidelineStateAndNumberOfIssue_Expected() {
        NDPReleaseInfoGuidelinesPage guidelinesPage = (NDPReleaseInfoGuidelinesPage) currentPage;
        assertAll(() -> assertTrue(guidelinesPage.nav().isGuidelinesCompleted(), "Verify guidelines checks off"),
                () -> assertEquals("3", guidelinesPage.nav().clickIssues().nav().getNumberOfIssue(), "Verify require waivers appear in the issue"));

    }

    @Test()
    @Order(9)
    public void createMpaRom_Step(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        ROMLoginPage romLoginPage = (ROMLoginPage) currentPage;
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom.selectRequester(TextConstants.test_User)
                .selectType(TextConstants.initial_Release)
                .selectMPACheckbox()
                .selectNumberOfProgram("11")
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .enterProductName(productName)
                .enterApplicationId(applicationId)
                .selectPublishingRelationShip(publishingRelationship)
                .enterDisplayVersion("1.0.0")
                .enterReleaseVersion("00")
                .selectCERORating("CERO: Not required")
                .clickOnCreateButton()
                .waitIsLoaded();
        romFileName = romCreateRom.getRomNameTable(applicationId);
        romCreateRom.clickOnROMFileOnROMTable(romFileName);
        WaitUtils.idle(5000);
    }

    @Test()
    @Order(10)
    public void uploadMpaRom_Step(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        NDPReleaseInfoROMUpload romUpload = myProductsPage.clickProductByName(productName).clickReleases().selectInitialRelease().nav().clickROMUpload();
        //CommonAction.selectUploadROM(romUpload, romFileName);
        romUpload.selectRomFileButton()
                .selectFileInPC(romFileName).clickUpload().clickUploadNow().waitForUploadComplete("ROM upload and verification complete");
        currentPage = romUpload;
    }

    @Test()
    @Order(11)
    public void selectIssueSection_Step() {
        NDPReleaseInfoROMUpload romUpload = (NDPReleaseInfoROMUpload) currentPage;
        NDPReleaseInfoIssuePage issuesPage = romUpload.nav().clickIssues();
        issuesPage.openRequiredApprovalTab()
                .performRequestACombinationOfTemporaryAndPermanentWaivers(1);
        currentPage = issuesPage;
    }

    @Test()
    @Order(12)
    public void verifyWaiverIssue_Expected() {
        NDPReleaseInfoIssuePage issuesPage = (NDPReleaseInfoIssuePage) currentPage;
        issuesPage.openRequiredApprovalTab();
        numberOfIssue = issuesPage.getNumberOfIssue();
        assertAll(() -> assertTrue(issuesPage.isIssueDisplayed("Supports Toy-Con")),
                () -> assertTrue(issuesPage.isIssueDisplayed("0011: Limits on frequency and amount of writing to storage media")),
                () -> assertTrue(issuesPage.isIssueDisplayed("0003: Prohibition of supporting 3D televisions")));

    }

    @Test()
    @Order(13)
    public void moveToMessageTab_Step() {
        NDPReleaseInfoIssuePage issuesPage = (NDPReleaseInfoIssuePage) currentPage;
        currentPage = issuesPage.nav().clickBackToProductDashboard().clickMessagesTab();

    }

    @Test()
    @Order(14)
    public void verifyDisplayOfMessageThread_Expected() {
        NDPProductDashboardMessagesTab messagesTab = (NDPProductDashboardMessagesTab) currentPage;
        assertEquals(numberOfIssue, messagesTab.getNumberOfThreadMessages());

    }

    @Test()
    @Order(15)
    public void approveTheWaiverAndSubmitReleaseToLotcheck_Step(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        NDPProductDashboardPage productDashboardPage = myProductsPage.clickProductByName(productName);
        NDPReleaseInfoPage releaseInfoPage = productDashboardPage.clickReleases().selectInitialRelease();

        releaseInfoPage.nav().clickAgeRating().clickOnAddStandardRatingButton()
                .selectStandardAgeRating("CERO")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectRatingValue("Not required")
                .clickOnSaveButtonInEditStandardRating();

        releaseInfoPage.nav().clickTestScenarios()
                .expandAllSections()
                .expandAllTestScenes()
                .enterAllMethod("Method")
                .enterAllExplain("Explain")
                .clickOnSaveButton();

        NDPDevelopmentHome developmentHome = releaseInfoPage.nav()
                .clickUserMenuDropdownList()
                .clickSignOutButton()
                .clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        licensingWaiverRequestsTab.waitingForResultTableLoaded()
                .enterSearchingBox(gameCode)
                .clickOnMagnifyingIconButton()
                .waitingForResultTableLoaded();
        int numberOfRequestByPendingStatus = licensingWaiverRequestsTab.collectNumberOfRequestByPendingStatus();
        for (int i = 0; i < numberOfRequestByPendingStatus; i++) {
            developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton().clickOnLicensingWaiverRequestsTab();
            licensingWaiverRequestsTab.waitingForResultTableLoaded()
                    .enterSearchingBox(gameCode)
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
                .clickMyProducts().clickProductByGameCode(initialCode).clickReleases().selectInitialRelease().nav().clickSubmitButton()
                .clickSubmitReview().openNewTabAndCloseCurrentTab();


        myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        myProductsPage.clickProductByGameCode(gameCode).clickReleases().selectInitialRelease();
        for (int i = 0; i < 300; i++) {
            if (releaseInfoPage.getStatusValue().equals(TextConstants.submitToLotcheck)) {
                break;
            }
            WaitUtils.idle(3000);
            releaseInfoPage.nav().clickFeatures();
            releaseInfoPage.nav().clickReleaseInfo();
        }
        assertEquals(TextConstants.submitToLotcheck, releaseInfoPage.getStatusValue(), TextConstants.submitSuccess);
    }

    @Test()
    @Order(16)
    public void verifyReleaseAppearOnTestRailAndSLCMS_Expected(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(slcmsAdminUser);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickTesterAssignment(LotcheckRegion.NCL);
        lotcheckQueuePage.searchByInitialCode(initialCode);
        int i = 0;
        while (i <= 200) {
            if (lotcheckQueuePage.getNumberTestPlanDisplay() == 1) {
                break;
            }
            WaitUtils.idle(3000);
            i++;
            lotcheckQueuePage.clickSearchIcon();
        }
        int numberTestPlan = lotcheckQueuePage.getNumberTestPlanDisplay();
        LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
        boolean isTestPlanDisplayed = lotcheckTestPlanRow.isTestPlanDisplayed(gameCode);
        lotcheckQueuePage.logOut();

        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        TestRailDashboardPage testRailDashboardPage = testRailLoginPage.signInTestRail(testrailUser);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestRail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(initialCode)
                .selectStatusUntested()
                .selectStatusPassed();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar();
        boolean isTestPlanDisplayed_TestRail = testRailManagerDashboardPage.isTestPlanDisplayed(gameCode);
        testRailManagerDashboardPage.logOut();
        assertAll(() -> assertEquals(1, numberTestPlan, "Verify test plan appears in the tester assignment queue"),
                () -> assertTrue(isTestPlanDisplayed, "Verify test plan appears in the tester assignment queue"),
                () -> assertTrue(isTestPlanDisplayed_TestRail, "Verify test plan appears in testrail"));
    }

    @Test()
    @Order(17)
    public void onNdpMoveToMessageTab_Step(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        currentPage = myProductsPage.clickProductByName(productName).clickMessagesTab();
    }

    @Test()
    @Order(18)
    public void verifyNumberOfMessageThread_Expected() {
        NDPProductDashboardMessagesTab messagesTab = (NDPProductDashboardMessagesTab) currentPage;
        assertEquals(numberOfIssue, messagesTab.getNumberOfThreadMessages());

    }

    @Test()
    @Order(19)
    public void loginSlcmsWithLCOverseasCoordinatorAndMoveToSubProgramXMLFilesSection_Step(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage landingPage = hyruleAuthPage.loginWithOvdLink(slcmsOverseaCoordinatorUser);
        LotcheckSubmissionSearchPage submissionSearchPage = landingPage.searchProduct(initialCode);
        LotcheckReportSubmissionOverview submissionOverview = submissionSearchPage.selectSubmission(gameCode, "00", "00");
        currentPage = submissionOverview.navBar().clickMetadata();
    }

    @Test()
    @Order(20)
    public void verifyDisplayOfSubProgramXMLFilesSection_Expected() {
        LotcheckReportSubmissionMetadata metadata = (LotcheckReportSubmissionMetadata) currentPage;
        int numberOfSubProgram = metadata.getNumberOfSubProgram();
        int numberOfApplicationControlPropertyXmlFiles = metadata.getNumberOfXmlFiles(".nacp.xml");
        int numberOfProgramInfoXmlFiles = metadata.getNumberOfXmlFiles(".programinfo.xml");
        String initialStateOfOverallSubProgram = metadata.getStateOfOverallSubProgram();
        metadata.clickOnSubProgramXmlFileButton();
        String stateOfOverallSubProgramAfterExpand = metadata.getStateOfOverallSubProgram();
        metadata.clickOnSubProgramXmlFileButton();
        String stateOfOverallSubProgramAfterCollapse = metadata.getStateOfOverallSubProgram();
        metadata.clickExpandXmlFile("cnmt.xml");
        boolean isIdOffsetOfControlTypeIsAscending = metadata.isIdOffsetOfControlTypeIsAscending();
        boolean isIdOffsetOfProgramTypeIsAscending = metadata.isIdOffsetOfProgramTypeIsAscending();
        ArrayList<String> listXmlFileOfSubProgram = metadata.getListXmlFileOfSubProgram();
        metadata.refreshPageSLCMS();
        metadata.clickDownloadAllFiles();
        assertAll(() -> assertEquals(10, numberOfSubProgram),
                () -> assertEquals(10, numberOfApplicationControlPropertyXmlFiles),
                () -> assertEquals(10, numberOfProgramInfoXmlFiles),
                () -> assertEquals("true", stateOfOverallSubProgramAfterCollapse),
                () -> assertEquals("false", stateOfOverallSubProgramAfterExpand),
                () -> assertEquals("true", initialStateOfOverallSubProgram),
                () -> assertTrue(isIdOffsetOfControlTypeIsAscending),
                () -> assertTrue(isIdOffsetOfProgramTypeIsAscending),
                () -> assertNotEquals(0L, metadata.getFileSizeFromPath(listXmlFileOfSubProgram.get(0))),
                () -> assertNotEquals(0L, metadata.getFileSizeFromPath(listXmlFileOfSubProgram.get(1))),
                () -> assertNotEquals(0L, metadata.getFileSizeFromPath(listXmlFileOfSubProgram.get(2))),
                () -> assertNotEquals(0L, metadata.getFileSizeFromPath(listXmlFileOfSubProgram.get(3))),
                () -> assertNotEquals(0L, metadata.getFileSizeFromPath(listXmlFileOfSubProgram.get(4))),
                () -> assertNotEquals(0L, metadata.getFileSizeFromPath(listXmlFileOfSubProgram.get(5))),
                () -> assertNotEquals(0L, metadata.getFileSizeFromPath(listXmlFileOfSubProgram.get(6))),
                () -> assertNotEquals(0L, metadata.getFileSizeFromPath(listXmlFileOfSubProgram.get(7))),
                () -> assertNotEquals(0L, metadata.getFileSizeFromPath(listXmlFileOfSubProgram.get(8))),
                () -> assertNotEquals(0L, metadata.getFileSizeFromPath(listXmlFileOfSubProgram.get(9))));

    }

    @Test()
    @Order(21)
    public void loginTestRailByTrAdmin_Step(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        currentPage = testRailLoginPage.signInTestRail(testrailUser);
    }

    @Test()
    @Order(22)
    public void findTestPlan_Step() {
        TestRailDashboardPage testRailDashboardPage = (TestRailDashboardPage) currentPage;
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestRail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(initialCode)
                .selectStatusUntested()
                .selectStatusPassed();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar();
        currentPage = testRailManagerDashboardPage;
    }

    @Test()
    @Order(23)
    public void submitABug_Step() {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        testRailManagerDashboardPage.clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun();
        TestRailSubmitBugDialog submitBugDialog = testRailManagerDashboardPage.clickOnTheFirstTestcase().clickSubmitBug();
        submitBugDialog.enterDescription("Test description").clickSubmit();
        submitBugDialog.clickOk().clickClose().logOut();
    }

    @Test()
    @Order(24)
    public void publishBugWithLciTypeIsSubmissionIssue_Step(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckLandingPage landingPage = (LotcheckLandingPage) currentPage;
        LotcheckSubmissionSearchPage submissionSearchPage = landingPage.searchProduct(initialCode);
        LotcheckReportSubmissionOverview submissionOverview = submissionSearchPage.selectSubmission(gameCode, "00", "00");
        LotcheckReportSubmissionLotcheckIssues submissionLotcheckIssues = submissionOverview.navBar().clickLotcheckIssues();
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails = submissionLotcheckIssues.clickMenuOption()
                .selectViewIssueDetails();
        submissionLotcheckIssueDetails.clickMenu()
                .selectPublishToPublic()
                .selectLciType("Submission Issue")
                .clickPublish();
        submissionLotcheckIssueDetails.refreshPageSLCMS();
        submissionLotcheckIssueDetails.logOut();
    }

    @Test()
    @Order(25)
    public void onNdpMoveToLotcheckIssue_Step(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        currentPage = myProductsPage.clickProductByName(productName).clickReleases().selectInitialRelease().nav().clickIssues()
                .clickOnLotcheckIssuesTab();
    }

    @Test()
    @Order(26)
    public void resolvedIssueWithResolutionIsWillFixInFeature_Step() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        issuePage.expandUnresolve()
                .expandIssue()
                .selectResolution("Will Fix in Features")
                .clickOnSendResolutionButton();
    }

    @Test()
    @Order(27)
    public void resubmitTheApplicationInformationSheet_Step(IJUnitTestReporter testReport) {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        issuePage.nav().clickSubmitEditedInformation().clickSubmitReview().openNewTabAndCloseCurrentTab();

        NDPMyProductsPage myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPReleaseInfoPage releaseInfoPage = myProductsPage.clickProductByGameCode(gameCode).clickReleases().selectInitialRelease();
        for (int i = 0; i < 300; i++) {
            if (releaseInfoPage.getStatusValue().equals(TextConstants.submitToLotcheck)) {
                break;
            }
            WaitUtils.idle(3000);
            releaseInfoPage.nav().clickFeatures();
            releaseInfoPage.nav().clickReleaseInfo();
        }
        currentPage = releaseInfoPage;

    }

    @Test()
    @Order(28)
    public void verifyResubmitTheApplicationInformationSheet_Expected(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        assertEquals("Submitted to Lotcheck", releaseInfoPage.getStatusValue(), "Verify resubmit successfully");
    }

    @Test()
    @Order(29)
    public void loginSlcmsWithLcAdminAndMoveToSubProgramXMLFilesSection_Step(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage landingPage = hyruleAuthPage.loginWithOvdLink(slcmsAdminUser);
        LotcheckSubmissionSearchPage submissionSearchPage = landingPage.searchProduct(initialCode);
        LotcheckReportSubmissionOverview submissionOverview = submissionSearchPage.selectSubmission(gameCode, "00", "00");
        currentPage = submissionOverview.navBar().clickMetadata();
    }

    @Test()
    @Order(30)
    public void verifyDisplayOfSubProgramXMLFilesSectionWithAdminSite_Expected() {
        LotcheckReportSubmissionMetadata metadata = (LotcheckReportSubmissionMetadata) currentPage;
        int numberOfSubProgram = metadata.getNumberOfSubProgram();
        int numberOfApplicationControlPropertyXmlFiles = metadata.getNumberOfXmlFiles(".nacp.xml");
        int numberOfProgramInfoXmlFiles = metadata.getNumberOfXmlFiles(".programinfo.xml");
        String initialStateOfOverallSubProgram = metadata.getStateOfOverallSubProgram();
        metadata.clickOnSubProgramXmlFileButton();
        String stateOfOverallSubProgramAfterExpand = metadata.getStateOfOverallSubProgram();
        metadata.clickOnSubProgramXmlFileButton();
        String stateOfOverallSubProgramAfterCollapse = metadata.getStateOfOverallSubProgram();
        metadata.clickExpandXmlFile("cnmt.xml");
        boolean isIdOffsetOfControlTypeIsAscending = metadata.isIdOffsetOfControlTypeIsAscending();
        boolean isIdOffsetOfProgramTypeIsAscending = metadata.isIdOffsetOfProgramTypeIsAscending();
        ArrayList<String> listXmlFileOfSubProgram = metadata.getListXmlFileOfSubProgram();
        metadata.refreshPageSLCMS();
        metadata.clickDownloadAllFiles();
        assertAll(() -> assertEquals(10, numberOfSubProgram),
                () -> assertEquals(10, numberOfApplicationControlPropertyXmlFiles),
                () -> assertEquals(10, numberOfProgramInfoXmlFiles),
                () -> assertEquals("true", stateOfOverallSubProgramAfterCollapse),
                () -> assertEquals("false", stateOfOverallSubProgramAfterExpand),
                () -> assertEquals("true", initialStateOfOverallSubProgram),
                () -> assertTrue(isIdOffsetOfControlTypeIsAscending),
                () -> assertTrue(isIdOffsetOfProgramTypeIsAscending),
                () -> assertNotEquals(0L, metadata.getFileSizeFromPath(listXmlFileOfSubProgram.get(0))),
                () -> assertNotEquals(0L, metadata.getFileSizeFromPath(listXmlFileOfSubProgram.get(1))),
                () -> assertNotEquals(0L, metadata.getFileSizeFromPath(listXmlFileOfSubProgram.get(2))),
                () -> assertNotEquals(0L, metadata.getFileSizeFromPath(listXmlFileOfSubProgram.get(3))),
                () -> assertNotEquals(0L, metadata.getFileSizeFromPath(listXmlFileOfSubProgram.get(4))),
                () -> assertNotEquals(0L, metadata.getFileSizeFromPath(listXmlFileOfSubProgram.get(5))),
                () -> assertNotEquals(0L, metadata.getFileSizeFromPath(listXmlFileOfSubProgram.get(6))),
                () -> assertNotEquals(0L, metadata.getFileSizeFromPath(listXmlFileOfSubProgram.get(7))),
                () -> assertNotEquals(0L, metadata.getFileSizeFromPath(listXmlFileOfSubProgram.get(8))),
                () -> assertNotEquals(0L, metadata.getFileSizeFromPath(listXmlFileOfSubProgram.get(9))));
    }
}
