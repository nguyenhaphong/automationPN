package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.junit.TestLogger;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.data.DataGen;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.lcms.LotcheckHomePage;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.ui.models.lcms.LotcheckQueuePage;
import net.nintendo.automation.ui.models.lcms.LotcheckTestPlanRow;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.ndp.NDPCreateProductPage;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPLicensingWaiverRequestsTab;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.NDPViewTaskPage;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_internal.NDPInternalPage;
import net.nintendo.automation.ui.models.ndp.existing_internal.NDPSponsorQueueDetailPage;
import net.nintendo.automation.ui.models.ndp.existing_internal.NDPSponsorQueuePage;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardProductInfoTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardReleasesTab;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoAgeRating;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoGuidelinesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoIssuePage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoROMUpload;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoReleaseListNav;
import net.nintendo.automation.ui.models.rom.ROMCreateRom;
import net.nintendo.automation.ui.models.rom.ROMLoginPage;
import net.nintendo.automation.ui.models.testrail.TestRailAddProblemDescriptionDialog;
import net.nintendo.automation.ui.models.testrail.TestRailAddRemarkDialog;
import net.nintendo.automation.ui.models.testrail.TestRailDashboardPage;
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

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classname: T3403972
 * Version: 1.0.0
 * Purpose:
 */
@Tag("T3403972")
//@ScenarioTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3403972 extends CommonBaseTest {
    private static Browser browser;
    private static Browser romBrowser;
    private static User romUser;
    private static User ndpUser;
    private static User slcmsUser;
    private static User testrailUser;
    private static User licUser;
    private static User testrailOverseasCoordinatorUser;
    private static CommonUIComponent currentPage;
    private static TestLogger logger;
    private static String gameCode;
    private static String applicationID;
    private static String initialCode;
    private static String lotcheckDueDate;
    private static String fileRomName;
    private static String remainingTaskInTestRail;
    private static String remainingTimeInTestRail;
    private static String remainingTaskInTestPlan;
    private static String remainingTimeInTestPlan;
    private static String remainingTimeInLCR;
    private static String remainingTaskInTestRail1;
    private static String remainingTimeInTestRail1;
    private static String remainingTaskInTestPlan1;
    private static String remainingTimeInTestPlan1;
    private static String remainingTaskInTestRail2;
    private static String remainingTimeInTestRail2;
    private static String remainingTaskInTestPlan2;
    private static String remainingTimeInTestPlan2;
    private static String remainingTaskInTestRail3;
    private static String remainingTimeInTestRail3;
    private static String remainingTaskInTestPlan3;
    private static String remainingTimeInTestPlan3;
    private static int numberTestPlan;
    private static String remainingTimeInLCR1;
    private static String remainingTimeInLCR2;
    private static String remainingTimeInLCR3;
    private static String remainingTaskInTestRail4;
    private static String remainingTimeInTestRail4;
    private static String remainingTaskInTestPlan4;
    private static String remainingTimeInTestPlan4;
    private static String remainingTimeInLCR4;
    private static String remainingTaskInTestPlan5;
    private static String remainingTimeInTestPlan5;

    private static String productName;

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_NguyenPhong);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
        slcmsUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);
        testrailUser = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER);
        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);
        testrailOverseasCoordinatorUser = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER, TextConstants.trOverseasNCL_Email);

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
        UserManager.releaseUser(ServiceType.PMMS, testrailUser);
    }

    @BeforeEach
    public void each(IJUnitTestReporter testReport) {
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
    }

    @Test
    @Order(1)
    public void loginAsPartnerAndCreateProductAndRelease(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReport);
        NDPHomePage homePage = (NDPHomePage) currentPage;
        NDPMyProductsPage myProductsPage = homePage.acceptCookie().clickSignInPage().signIn(ndpUser).clickMyProducts();
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPCreateProductPage createProductPage = myProductsPage.createNewProduct();
        createProductPage.selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(TextConstants.third_Party_Type)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalJapanSalesRegion()
                .selectProductName(productName)
                .selectProductNameKana(TextConstants.productKana)
                .clickCreateButton();
        NDPProductDashboardPage ndpDashboardPage = myProductsPage.clickProductByName(productName);
        CommonAction.issueGameCode(ndpDashboardPage, 60, "NO");
        assertEquals("A Game Code has been successfully added to the product.", ndpDashboardPage.getIssueGameCodeSuccessMessage(), "Issue Game Code Successfully");
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpDashboardPage.clickProductInfo();
        initialCode = ndpProductDashboardProductInfoTab.getInitialCode();
        ndpDashboardPage.clickBackToMyProducts()
                .clickOnUserNameMenu()
                .clickOnSignOutButton();
    }

    @Test
    @Order(2)
    public void loginAsLicensingMemberAndNavigateToSponsorQueue(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReport);
        NDPHomePage homePage = (NDPHomePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPDevelopmentHome developmentHome = homePage.clickSignInPage().signIn(TextConstants.lic_pwr_user_NCL, "nintendo");
        NDPInternalPage internalPage = developmentHome.clickInternalTab();
        NDPSponsorQueuePage sponsorQueuePage = internalPage.clickSponsorQueueButton();
        assertEquals("Sponsor Queue - Nintendo Developer Portal", sponsorQueuePage.getPageTitle(), "Sponsor Queue page display correct");
        WaitUtils.idle(5000);
        currentPage = sponsorQueuePage;
    }

    @Test
    @Order(3)
    public void searchingForThenSetLCDueDateForRelease(IJUnitTestReporter testReport) {
        NDPSponsorQueuePage sponsorQueuePage = (NDPSponsorQueuePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        sponsorQueuePage.clickSearchContent(initialCode)
                .clickSearchIcon()
                .waitingLoadingValueSearch();
        WaitUtils.idle(15000);
        assertEquals(initialCode, sponsorQueuePage.getInitialCodeInRow(1), "Release display correct");
        NDPSponsorQueueDetailPage sponsorQueueDetailPage = sponsorQueuePage.selectItemInTableResult();
        WaitUtils.idle(10000);
        sponsorQueueDetailPage.clickEditDetailButton();
        WaitUtils.idle(5000);
        sponsorQueueDetailPage.setLotCheckDueDate(10)
                .clickSaveChangeButton();
        lotcheckDueDate = sponsorQueuePage.getLotcheckDueDateInRow(1).substring(0, 10);
        System.out.println(lotcheckDueDate);
        sponsorQueuePage.clickOnUserNameMenu()
                .clickOnSignOutButton();
    }

    @Test
    @Order(4)
    public void submitReleaseToLotcheck(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReport);
        NDPHomePage homePage = (NDPHomePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPMyProductsPage myProductsPage = homePage.clickSignInPage().signIn(ndpUser).clickMyProducts();
        NDPProductDashboardPage productDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = productDashboardPage.clickProductInfo();
        applicationID = ndpProductDashboardProductInfoTab.getApplicationID();
        gameCode = ndpProductDashboardProductInfoTab.getGameCode();
        initialCode = ndpProductDashboardProductInfoTab.getInitialCode();
        NDPProductDashboardReleasesTab productDashboardReleasesTab = productDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = productDashboardReleasesTab.selectInitialRelease();
        //create release info
        NDPReleaseInfoPage releaseInfoPage = ndpReleaseInfoPage.nav().clickReleaseInfo();
        CommonAction.selectROMSameAllRegion(releaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);
        // //create Feature
        CommonAction.getFeatureOptionData(releaseInfoPage, TextConstants.allOff, TextConstants.allOff, TextConstants.allOff, false, "", false, "");
        //Create GuildLine
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = ndpReleaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoGuidelinesPage.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();
        //Create Test Scenarios
        //Create Age Rating
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = ndpReleaseInfoPage.nav().clickAgeRating();
        ndpReleaseInfoAgeRating.clickOnAddStandardRatingButton()
                .selectStandardAgeRating("CERO")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectRatingValue("A")
                .uploadCertificateAttachment(new File("src/test/resources/DataFile/ab.jpg"))
                .clickOnSaveButtonInEditStandardRating();

        //create file ROM
        romBrowser = BrowserManager.getNewInstance();
        ROMLoginPage romLoginPage = romBrowser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN, testReport);
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
                .selectCERORating("CERO: A")
                .clickOnCreateButton()
                .waitIsLoaded();
        fileRomName = romCreateRom.getRomName();
        romCreateRom.clickOnROMFileOnROMTable(applicationID);
        WaitUtils.idle(8000);
        romCreateRom.clickOnUserNameMenu().clickOnLogoutButton();
        BrowserManager.closeBrowser(romBrowser);

        //Upload ROM
        NDPReleaseInfoReleaseListNav nav = browser.getPage(NDPReleaseInfoReleaseListNav.class, testReport);
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = nav.clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
        //Issues
        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
        CommonAction.enterReasonForRequest(issuePage);
        nav.clickOnUserNameMenu().clickOnSignOutButton();
        //Approval Issue TaskRequested
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReport).clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, gameCode);
        //nav.clickOnUserNameMenu().clickOnSignOutButton();
        NDPViewTaskPage viewTaskPage = browser.getPage(NDPViewTaskPage.class, testReport);
        viewTaskPage.clickUserMenuDropdownList().clickSignOutButton();
        //submit product
        ndpReleaseInfoPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReport)
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
    @Order(5)
    public void verifyPartnerCanNotViewLotcheckDueDateAtReleaseInfo(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentPage;
        assertFalse(ndpReleaseInfoPage.isLotcheckDueDateDisplayed(), "Lotcheck Due Date is not displayed in Release Info");
    }

    @Test
    @Order(6)
    public void asALotcheckLoginToSLCMS(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckLandingPage lotcheckLandingPage = lotcheckHomePage.continueToLogin().loginWithOvdLink(slcmsUser);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickOverallLotcheck(LotcheckRegion.NCL);
        lotcheckQueuePage.searchByInitialCode(initialCode)
                .clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        currentPage = lotcheckReportSubmissionOverview;
    }

    @Test
    @Order(7)
    public void verifyLotcheckAdminCanViewLotcheckDueDateInLCR(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = (LotcheckReportSubmissionOverview) currentPage;
        boolean isTestDueDateDisplayed = lotcheckReportSubmissionOverview.isTestDueDateDisplayed();
        assertTrue(isTestDueDateDisplayed, "Verify lotcheck admin can view Lotcheck Due Date in LCR");
    }

    @Test
    @Order(8)
    public void navigateToTestPlanRegionOverallLotcheck(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickOverallLotcheck(LotcheckRegion.NCL);
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
        numberTestPlan = lotcheckQueuePage.getNumberTestPlanDisplay();
        LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
        currentPage = lotcheckTestPlanRow;
    }

    @Test
    @Order(9)
    public void verifyTheOverallLotcheckQueueWillBeDisplayed(IJUnitTestReporter testReport) {
        LotcheckTestPlanRow lotcheckTestPlanRow = (LotcheckTestPlanRow) currentPage;
        boolean isTestPlanDisplayed = lotcheckTestPlanRow.isTestPlanDisplayed(gameCode);
        assertAll(() -> assertEquals(1, numberTestPlan, "Verify test plan is displayed in the overall lotcheck queue"),
                () -> assertTrue(isTestPlanDisplayed, "Verify test plan is displayed in the overall lotcheck queue"));
    }

    @Test
    @Order(10)
    public void loginTestRailAsATradminThenLocateTheTestPlanThatHadTheLotcheckDueDate(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailLoginPage.signInTestRail(TextConstants.trAdminNCL_Email, TextConstants.trAdmin_Password)
                .navTopTestRail()
                .navigateToManagerDashboard()
                .waitingLoadingTestPlan()
                .clickOkInErrorPopup()
                .actionFill()
                .enterTestPlanNameFilter(initialCode)
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan()
                .clickOnPlannedTestDateNotSetBar();
        currentPage = testRailManagerDashboardPage;
    }

    @Test
    @Order(11)
    public void verifyTheSubmissionOfTestPlanWillHaveLotcheckDueDateDisplayed(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        int numberTestPlanInTR = testRailManagerDashboardPage.collectNumberTestPlan();
        boolean isPlanDueDateDisplayed = testRailManagerDashboardPage.isPlanDueDateDisplayed();
        assertAll(() -> assertTrue(numberTestPlanInTR == 1, "Test plans for the submission exist in Testrail."),
                () -> assertTrue(isPlanDueDateDisplayed, "Lotcheck Due Date is displayed on the right side of test plan header"));
    }

    @Test
    @Order(12)
    public void verifyTheDisplayOfRemainingTimeAndRemainingTaskInTR(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        testRailManagerDashboardPage.topNav()
                .navigateToManagerDashboard()
                .waitingLoadingTestPlan()
                .clickOkInErrorPopup()
                .actionFill()
                .enterTestPlanNameFilter(initialCode)
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan()
                .clickOnPlannedTestDateNotSetBar();
        remainingTaskInTestRail = testRailManagerDashboardPage.getRemainingTask();
        remainingTimeInTestRail = testRailManagerDashboardPage.getRemainingTime();
        System.out.println(remainingTaskInTestRail);
        System.out.println(remainingTimeInTestRail);
        currentPage = testRailManagerDashboardPage;
    }

    @Test
    @Order(13)
    public void verifyTheRemainingTimeAndRemainingTaskWillAppear(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        boolean isRemainingTaskDisplayed = testRailManagerDashboardPage.isRemainingTaskDisplayed();
        boolean isRemainingTimeDisplayed = testRailManagerDashboardPage.isRemainingTimeDisplayed();
        assertAll(() -> assertTrue(isRemainingTaskDisplayed, "The remaining task is displayed"),
                () -> assertTrue(isRemainingTimeDisplayed, "The remaining time is displayed"));
    }

    @Test
    @Order(14)
    public void asTheSLCMSNavigateToLCQueueOverallQueue(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickOverallLotcheck(LotcheckRegion.NCL);
        lotcheckQueuePage.searchByInitialCode(initialCode)
                .clickPlannedTestDateNotSet();
        remainingTaskInTestPlan = lotcheckQueuePage.getTestPlanRemaining().substring(22, 26);
        remainingTimeInTestPlan = lotcheckQueuePage.getTestPlanRemaining().substring(15, 22);
        System.out.println(remainingTaskInTestPlan);
        System.out.println(remainingTimeInTestPlan);
        currentPage = lotcheckQueuePage;
    }

    @Test
    @Order(15)
    public void verifyTheDisplayOfRemainingTimeAndRemainingTaskInLCQueue(IJUnitTestReporter testReport) {
        LotcheckQueuePage lotcheckQueuePage = (LotcheckQueuePage) currentPage;
        boolean isRemainingTaskInTestPlanDisplayed = lotcheckQueuePage.isRemainingTaskInTestPlanDisplayed();
        boolean isRemainingTimeInTestPlanDisplayed = lotcheckQueuePage.isRemainingTimeInTestPlanDisplayed();
        assertAll(() -> assertTrue(isRemainingTaskInTestPlanDisplayed, "The remaining task is displayed in LC Queue"),
                () -> assertTrue(isRemainingTimeInTestPlanDisplayed, "The remaining time is displayed in LC Queue"));
    }

    @Test
    @Order(16)
    public void asTheSLCMSNavigateToLCR(IJUnitTestReporter testReport) {
        LotcheckQueuePage lotcheckQueuePage = (LotcheckQueuePage) currentPage;
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        remainingTimeInLCR = lotcheckReportSubmissionOverview.getRemainingTime().substring(15, 21);
        System.out.println(remainingTimeInLCR);
        currentPage = lotcheckReportSubmissionOverview;
    }

    @Test
    @Order(17)
    public void verifyTheDisplayOfRemainingTimeAndRemainingTaskInLCR(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = (LotcheckReportSubmissionOverview) currentPage;
        boolean isRemainingTimeInLCRDisplayed = lotcheckReportSubmissionOverview.isRemainingTimeDisplayed();
        assertTrue(isRemainingTimeInLCRDisplayed, "The remaining time is displayed in LCR and Remaining task is not displayed");
    }

    @Test
    @Order(18)
    public void asTheTestrailPerformAssignTheTestCaseToOtherUser(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN);
        TestRailDashboardPage testRailDashboardPage = (TestRailDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestRail().navigateToManagerDashboard();
        testRailManagerDashboardPage.waitingLoadingTestPlan()
                .clickOkInErrorPopup()
                .actionFill()
                .enterTestPlanNameFilter(initialCode)
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .clickOnTheFirstTestSuite()
                .clickOnTheFirstTestRun()
                .selectCheckBoxOfSecondTestCase()
                .clickAssignTo()
                .selectSetValueAssignToRadio()
                .selectUser("Overseas Coordinator (NCL)")
                .clickOk()
                .waitingLoadingTestSuite();
        currentPage = testRailManagerDashboardPage;
    }

    @Test
    @Order(19)
    public void repeatTheSteps8And9And10_Step12(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        testRailManagerDashboardPage.topNav()
                .navigateToManagerDashboard()
                .waitingLoadingTestPlan()
                .clickOkInErrorPopup()
                .actionFill()
                .enterTestPlanNameFilter(initialCode)
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan()
                .clickOnPlannedTestDateNotSetBar();
        remainingTaskInTestRail1 = testRailManagerDashboardPage.getRemainingTask();
        remainingTimeInTestRail1 = testRailManagerDashboardPage.getRemainingTime();
        System.out.println(remainingTaskInTestRail1);
        System.out.println(remainingTimeInTestRail1);
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickOverallLotcheck(LotcheckRegion.NCL);
        WaitUtils.idle(30000);
        lotcheckQueuePage.searchByInitialCode(initialCode)
                .clickPlannedTestDateNotSet();
        remainingTaskInTestPlan1 = lotcheckQueuePage.getTestPlanRemaining().substring(22, 26);
        remainingTimeInTestPlan1 = lotcheckQueuePage.getTestPlanRemaining().substring(15, 22);
        System.out.println(remainingTaskInTestPlan1);
        System.out.println(remainingTimeInTestPlan1);
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        remainingTimeInLCR1 = lotcheckReportSubmissionOverview.getRemainingTime().substring(15, 21);
        System.out.println(remainingTimeInLCR1);

    }

    @Test
    @Order(20)
    public void verifyExpected_Step12(IJUnitTestReporter testReport) {
        assertAll(() -> assertEquals(remainingTaskInTestRail, remainingTaskInTestRail1, "The Remaining Task will not change the value on Testrail"),
                () -> assertEquals(remainingTimeInTestRail, remainingTimeInTestRail1, "The Remaining Time will not change the value on Testrail"),
                () -> assertEquals(remainingTaskInTestPlan1, remainingTaskInTestPlan, "The Remaining Task will not change the value on SLCMS"),
                () -> assertNotEquals(remainingTimeInTestPlan1, remainingTimeInTestPlan, "The Remaining Time will change the value on SLCMS"),
                () -> assertNotEquals(remainingTimeInLCR1, remainingTimeInLCR, "The Remaining Time will change the value on SLCMS"));
    }

    @Test
    @Order(21)
    public void loginIntoTheNewlyAssignedUser(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN);
        TestRailDashboardPage testRailDashboardPage = (TestRailDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestRail().navigateToManagerDashboard();
        testRailManagerDashboardPage.logOut().signInTestRail(TextConstants.trOverseasNCL_Email, TextConstants.trAdmin_Password);
        TestRailMyToDoPage testRailMyToDoPage = testRailManagerDashboardPage.topNav().navigateToMyToDo()
                .waitingLoadingTestPlan()
                .actionFill()
                .enterTestPlanNameFilter(initialCode)
                .clickOnApplyFiltersButtonToMyTodoPage()
                .waitingLoadingTestPlan()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar();
        TestRailAddRemarkDialog testRailAddRemarkDialog = testRailMyToDoPage.clickOnAddRemarkButton();
        testRailAddRemarkDialog.selectSetValueTestCaseRadio()
                .inputCaseTitle("Test")
                .inputEstimatedTestingTime("10m")
                .clickSubmitButton();
        testRailMyToDoPage.logOut();
    }

    @Test
    @Order(22)
    public void repeatTheSteps8And9And10_Step14(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailLoginPage.signInTestRail(TextConstants.trAdminNCL_Email, TextConstants.trAdmin_Password)
                .navTopTestRail()
                .navigateToManagerDashboard()
                .waitingLoadingTestPlan()
                .clickOkInErrorPopup()
                .actionFill()
                .enterTestPlanNameFilter(initialCode)
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan()
                .clickOnPlannedTestDateNotSetBar();
        remainingTaskInTestRail2 = testRailManagerDashboardPage.getRemainingTask();
        remainingTimeInTestRail2 = testRailManagerDashboardPage.getRemainingTime();
        System.out.println(remainingTaskInTestRail2);
        System.out.println(remainingTimeInTestRail2);
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickOverallLotcheck(LotcheckRegion.NCL);
        WaitUtils.idle(30000);
        lotcheckQueuePage.searchByInitialCode(initialCode)
                .clickPlannedTestDateNotSet();
        remainingTaskInTestPlan2 = lotcheckQueuePage.getTestPlanRemaining().substring(22, 27).trim();
        remainingTimeInTestPlan2 = lotcheckQueuePage.getTestPlanRemaining().substring(15, 22).trim();
        System.out.println(remainingTaskInTestPlan2);
        System.out.println(remainingTimeInTestPlan2);
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        remainingTimeInLCR2 = lotcheckReportSubmissionOverview.getRemainingTime().substring(15, 22);
        System.out.println(remainingTimeInLCR2);

    }

    @Test
    @Order(23)
    public void verifyExpected_Step14(IJUnitTestReporter testReport) {
        assertAll(() -> assertNotEquals(remainingTaskInTestRail1, remainingTaskInTestRail2, "The Remaining Task will change the value on Testrail"),
                () -> assertNotEquals(remainingTimeInTestRail1, remainingTimeInTestRail2, "The Remaining Time will change the value on Testrail"),
                () -> assertEquals("0/55", remainingTaskInTestPlan2, "The Remaining Task increases by 1 on SCLMS"),
                () -> assertEquals("11h 15m", remainingTimeInTestPlan2, "The Remaining Time will increases by N on SLCMS (highest Remaining Time for any individual user with assigned Test Cases in " +
                        "TestRail)"),
                () -> assertEquals("11h 15m", remainingTimeInLCR2, "The Remaining Time will increases by N on SLCMS (highest Remaining Time for any individual user with assigned Test Cases in " +
                        "TestRail)"));
    }

    @Test
    @Order(24)
    public void asTheTestrailPerformAddProblemDescription(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN);
        TestRailDashboardPage testRailDashboardPage = (TestRailDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestRail()
                .navigateToManagerDashboard()
                .waitingLoadingTestPlan()
                .clickOkInErrorPopup()
                .actionFill()
                .enterTestPlanNameFilter(initialCode)
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite();
        TestRailAddProblemDescriptionDialog testRailAddProblemDescriptionDialog = testRailManagerDashboardPage.clickOnAddProblemDescriptionButton();
        testRailAddProblemDescriptionDialog.inputCaseTitle("Test")
                .inputEstimatedTestingTime("1h")
                .clickSubmitButton();
        WaitUtils.idle(30000);
        currentPage = testRailManagerDashboardPage;
    }

    @Test
    @Order(25)
    public void repeatTheSteps8And9And10_Step16(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        remainingTaskInTestRail3 = testRailManagerDashboardPage.getRemainingTask();
        remainingTimeInTestRail3 = testRailManagerDashboardPage.getRemainingTime();
        System.out.println(remainingTaskInTestRail3);
        System.out.println(remainingTimeInTestRail3);
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickOverallLotcheck(LotcheckRegion.NCL);
        WaitUtils.idle(30000);
        lotcheckQueuePage.searchByInitialCode(initialCode)
                .clickPlannedTestDateNotSet();
        remainingTaskInTestPlan3 = lotcheckQueuePage.getTestPlanRemaining().substring(22, 27).trim();
        remainingTimeInTestPlan3 = lotcheckQueuePage.getTestPlanRemaining().substring(15, 22).trim();
        System.out.println(remainingTaskInTestPlan3);
        System.out.println(remainingTimeInTestPlan3);
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        remainingTimeInLCR3 = lotcheckReportSubmissionOverview.getRemainingTime().substring(15, 22);
        System.out.println(remainingTimeInLCR3);
    }

    @Test
    @Order(26)
    public void verifyExpected_Step16(IJUnitTestReporter testReport) {
        assertAll(() -> assertNotEquals(remainingTaskInTestRail2, remainingTaskInTestRail3, "The Remaining Task will change the value on Testrail"),
                () -> assertNotEquals(remainingTimeInTestRail2, remainingTimeInTestRail3, "The Remaining Time will change the value on Testrail"),
                () -> assertEquals("0/56", remainingTaskInTestPlan3, "The Remaining Task increases by 1 on SCLMS"),
                () -> assertEquals("11h 15m", remainingTimeInTestPlan3, "The Remaining Time will increases by N on SLCMS (highest Remaining Time for any individual user with assigned Test Cases in TestRail)"),
                () -> assertEquals("11h 15m", remainingTimeInLCR3, "The Remaining Time will increases by N on SLCMS (highest Remaining Time for any individual user with assigned Test Cases in TestRail)"));
    }

    @Test
    @Order(27)
    public void asTheTestrailPerformSettingTheTestCaseStatusIsFailed(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN);
        TestRailDashboardPage testRailDashboardPage = (TestRailDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestRail()
                .navigateToManagerDashboard()
                .waitingLoadingTestPlan()
                .clickOkInErrorPopup()
                .actionFill()
                .enterTestPlanNameFilter(initialCode)
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan()
                .clickOnSetStatusButton()
                .selectStatusOnSetStatusModal("Failed")
                .clickOnUpdateStatusButtonOnSetStatusModal()
                .waitingLoadingTestSuite();
        WaitUtils.idle(15000);
        currentPage = testRailManagerDashboardPage;
    }

    @Test
    @Order(28)
    public void repeatTheSteps8And9And10_Step18(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage testRailManagerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        remainingTaskInTestRail4 = testRailManagerDashboardPage.getRemainingTask();
        remainingTimeInTestRail4 = testRailManagerDashboardPage.getRemainingTime();
        System.out.println(remainingTaskInTestRail4);
        System.out.println(remainingTimeInTestRail4);
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickOverallLotcheck(LotcheckRegion.NCL);
        WaitUtils.idle(90000);
        lotcheckQueuePage.searchByInitialCode(initialCode)
                .clickPlannedTestDateNotSet();
        remainingTaskInTestPlan4 = lotcheckQueuePage.getTestPlanRemaining().substring(21, 30).trim();
        remainingTimeInTestPlan4 = lotcheckQueuePage.getTestPlanRemaining().substring(15, 21).trim();
        System.out.println(remainingTaskInTestPlan4);
        System.out.println(remainingTimeInTestPlan4);
        assertAll(() -> assertEquals("1596/1596", remainingTaskInTestPlan4, "The Remaining Task will be displayed all task"),
                () -> assertEquals("0h 0m", remainingTimeInTestPlan4, "The Remaining Time will be displayed 0h 0m"));
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        remainingTimeInLCR4 = lotcheckReportSubmissionOverview.getRemainingTime().substring(16, 22).trim();
        System.out.println(remainingTimeInLCR4);
    }

    @Test
    @Order(29)
    public void verifyExpected_Step18(IJUnitTestReporter testReport) {
        assertAll(() -> assertEquals("0", remainingTaskInTestRail4, "The Remaining Task will be displayed 0 "),
                () -> assertEquals("0h 0m", remainingTimeInTestRail4, "The Remaining Time will be displayed 0h 0m"),
                () -> assertEquals("1596/1596", remainingTaskInTestPlan4, "The Remaining Task will be displayed all task"),
                () -> assertEquals("0h 0m", remainingTimeInTestPlan4, "The Remaining Time will be displayed 0h 0m"),
                () -> assertEquals("0h 0m", remainingTimeInLCR4, "The Remaining Time will be displayed 0h 0m"));
    }

    @Test
    @Order(30)
    public void asTheTestrailPerformJudgmentForTestPlan(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN);
        TestRailDashboardPage testRailDashboardPage = (TestRailDashboardPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestRail()
                .navigateToManagerDashboard()
                .waitingLoadingTestPlan()
                .clickOkInErrorPopup()
                .actionFill()
                .enterTestPlanNameFilter(initialCode)
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .clickOnPadlockButtonOfTheFirstTestPlan()
                .selectTestPlanJudgment(TextConstants.Suspended)
                .clickOnYesButtonOnConfirmationModal()
                .actionFill().clickOnApplyFiltersButton();
        boolean testPlanIsNotVisible = testRailManagerDashboardPage.testplanIsNotVisible(gameCode);
        assertTrue(testPlanIsNotVisible, TextConstants.testplanIsNotVisible);
    }

    @Test
    @Order(31)
    public void repeatTheSteps8And9And10_Step20(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickOverallLotcheck(LotcheckRegion.NCL);
        WaitUtils.idle(90000);
        lotcheckQueuePage.searchByInitialCode(initialCode)
                .clickPlannedTestDateNotSet();
        remainingTaskInTestPlan5 = lotcheckQueuePage.getTestPlanRemaining().substring(21, 30).trim();
        remainingTimeInTestPlan5 = lotcheckQueuePage.getTestPlanRemaining().substring(15, 21).trim();
        System.out.println(remainingTaskInTestPlan5);
        System.out.println(remainingTimeInTestPlan5);
    }

    @Test
    @Order(32)
    public void verifyExpected_Step20(IJUnitTestReporter testReport) {
        assertAll(() -> assertEquals("1596/1596", remainingTaskInTestPlan5, "The Remaining Task will be displayed all task"),
                () -> assertEquals("0h 0m", remainingTimeInTestPlan5, "The Remaining Time will be displayed 0h 0m"));
    }
}
