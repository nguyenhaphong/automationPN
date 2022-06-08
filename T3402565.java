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
import net.nintendo.automation.ui.models.email.EmailDevPage;
import net.nintendo.automation.ui.models.email.ViewHtmlInbucketPage;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthPage;
import net.nintendo.automation.ui.models.lcms.LotcheckHomePage;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.ui.models.lcms.LotcheckQueuePage;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssueDetails;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssues;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPLicensingWaiverRequestsTab;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.NDPViewTaskPage;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPCreateReleasePage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardProductInfoTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardReleasesTab;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoIssuePage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoROMUpload;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPUploadAdditionalFilesDialog;
import net.nintendo.automation.ui.models.rom.ROMCreateRom;
import net.nintendo.automation.ui.models.rom.ROMLoginPage;
import net.nintendo.automation.ui.models.testrail.TestRailDetailTestcaseDialog;
import net.nintendo.automation.ui.models.testrail.TestRailLoginPage;
import net.nintendo.automation.ui.models.testrail.TestRailManagerDashboardPage;
import net.nintendo.automation.ui.models.testrail.TestRailMyToDoPage;
import net.nintendo.automation.ui.models.testrail.TestRailSubmitBugDialog;
import net.nintendo.automation.ui.models.testrail.TestRailTestPlanTable;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("T3402565")
//@ScenarioTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3402565 extends CommonBaseTest {
    private static Browser browser;
    private static User romUser;
    private static User ndpUser;
    private static User slcmsUser;
    private static CommonUIComponent currentPage;
    private static TestLogger logger;
    private static String publishingRelationship;
    private static String productName;
    private static String initialCode;
    private static String gameCodeOfPatch;
    private static String applicationId;
    private static String romInitialName;
    private static User licUser;
    private static String gameCode_Initial;
    private static User testrailUser;
    private static boolean formatCodeHasU_LcDirectorNcl;
    private static boolean formatCodeHasU_LcOverseaCoordNcl;
    private static boolean emailIsNotDisplayed_Director_noa;
    private static boolean emailIsNotDisplayed_Director_noe;
    private static boolean emailIsNotDisplayed_overseaCoord_noa;
    private static boolean emailIsNotDisplayed_overseaCoord_noe;
    private static String titleIssue;
    private static String testcase_step15;
    private static String testcase1_step19;
    private static String version;
    private static String description;
    private static String type;

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_NgaDTN);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
        slcmsUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);

        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);
        testrailUser  = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER);
        publishingRelationship = TextConstants.third_Party_Type;

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
        UserManager.releaseUser(ServiceType.NDP, licUser);
        UserManager.releaseUser(ServiceType.SLCMS, slcmsUser);
        UserManager.releaseUser(ServiceType.SEARCH, romUser);
        UserManager.releaseUser(ServiceType.PMMS, testrailUser);
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
                .enterProductName(productName)
                .enterApplicationId(applicationId)
                .selectPublishingRelationShip(publishingRelationship)
                .enterDisplayVersion("1.0.0")
                .enterReleaseVersion("00")
                .selectCERORating("CERO: A")
                .clickOnCreateButton()
                .waitIsLoaded();
        romInitialName = romCreateRom.getRomNameTable(applicationId);
        romCreateRom.clickOnROMFileOnROMTable(romInitialName);
        WaitUtils.idle(5000);

    }

    @Test()
    @Order(1)
    public void createProductAndIssueGameCode_Step() {
        productName = DataGen.getRandomProductName();
        NDPHomePage homePage = (NDPHomePage) currentPage;
        NDPMyProductsPage myProductsPage = homePage
                .acceptCookie()
                .clickSignInPage().signIn(ndpUser).clickMyProducts();
        myProductsPage.createNewProduct()
                .selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(publishingRelationship)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalJapanSalesRegion()
                .selectProductName(productName)
                .selectProductNameKana(TextConstants.productKana)
                .clickCreateButton();
        NDPProductDashboardPage myProductDashBoard = myProductsPage.clickProductByName(productName);
        CommonAction.issueGameCode(myProductDashBoard, 65,"NO");


    }

    @Test()
    @Order(2)
    public void navigateToRomUploadPage_Step(IJUnitTestReporter testReport) {
        NDPProductDashboardPage myProductDashBoard = browser.getPage(NDPProductDashboardPage.class, testReport);
        initialCode = myProductDashBoard.clickProductInfo().getInitialCode();
        gameCodeOfPatch = "HAC-U-" + initialCode;
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = myProductDashBoard.clickReleases();
        NDPReleaseInfoPage releaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        currentPage = releaseInfoPage.nav().clickROMUpload();

    }

    @Test()
    @Order(3)
    public void clickUploadAdditionalFiles_Step() {
        NDPReleaseInfoROMUpload romUploadPage = (NDPReleaseInfoROMUpload) currentPage;
        currentPage = romUploadPage.clickAdvanced()
                .clickUploadAdditionalFile();

    }

    @Test()
    @Order(4)
    public void uploadFile_Step() {
        NDPUploadAdditionalFilesDialog additionalFilesDialog = (NDPUploadAdditionalFilesDialog) currentPage;
        currentPage = additionalFilesDialog.clickChooseFile("GetIARC.bnr")
                .clickUploadFile();

    }

    @Test()
    @Order(5)
    public void verifyUploadFileSuccessfully_Expected() {
        NDPReleaseInfoROMUpload romUploadPage = (NDPReleaseInfoROMUpload) currentPage;
        boolean checkFileUploadedIsDisplayed=romUploadPage.fileUploadedIsDisplayed("GetIARC.bnr");
        romUploadPage.removeAdditionFile().clickDeleteFile();
        assertTrue(checkFileUploadedIsDisplayed,"Verify upload successfully");
    }

    @Test()
    @Order(6)
    public void createNewPatch_Step() {
        NDPReleaseInfoROMUpload romUploadPage = (NDPReleaseInfoROMUpload) currentPage;
        NDPCreateReleasePage ndpCreateReleasePage = romUploadPage.nav().clickBackToProductDashboard().clickReleases().clickCreateNewReleaseButton();
        currentPage = ndpCreateReleasePage.enterDisplayVersion("01")
                .selectExpectedSubmissionDate(0)
                .selectExpectedReleaseDate(30)
                .enterDescribeTheFeaturesPlanned(TextConstants.enterDescribeTheFeaturesPlanned)
                .enterDescribeTheBugsIssuesPlanned(TextConstants.enterDescribeTheBugsIssuesPlanned)
                .clickCreateButton();

    }

    @Test()
    @Order(7)
    public void verifyCreatePatchSuccessfully_Expected() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        assertAll(() -> assertEquals("Patch", releaseInfoPage.getReleaseTypeValue(), "Verify create patch successfully"),
                () -> assertEquals("01", releaseInfoPage.getReleaseVersionValue(), "Verify create patch successfully"),
                () -> assertEquals("01", releaseInfoPage.getDisplayVersionValue(), "Verify create patch successfully"));

    }

    @Test()
    @Order(8)
    public void searchMailSentToLCDirectorAndLCOverseas_NCL_Step(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(EmailDevPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        EmailDevPage emailDevPage = (EmailDevPage) currentPage;
        emailDevPage.enterMailBox(TextConstants.lc_Director_NCL);
        emailDevPage.searchEmail(gameCodeOfPatch);
        formatCodeHasU_LcDirectorNcl = emailDevPage.formatCodeHasU(gameCodeOfPatch);

        emailDevPage.enterMailBox(TextConstants.lc_oversea_coord_NCL);
        emailDevPage.searchEmail(gameCodeOfPatch);
        formatCodeHasU_LcOverseaCoordNcl = emailDevPage.formatCodeHasU(gameCodeOfPatch);


    }

    @Test()
    @Order(9)
    public void verifyMailSentToLCDirectorAndLCOverseas_NCL_Expected() {
        assertAll(() -> assertTrue(formatCodeHasU_LcDirectorNcl, "Verify email notification sent to LC Director (NCL) has format code U"),
                () -> assertTrue(formatCodeHasU_LcOverseaCoordNcl, "Verify email notification sent to LC Overseas Coordinator (NCL) has format code U"));
    }

    @Test()
    @Order(10)
    public void searchMailNotSentToLCDirectorAndLCOverseas_NOA_NOE_Step() {
        EmailDevPage emailDevPage = (EmailDevPage) currentPage;
        emailDevPage.enterMailBox(TextConstants.lc_Director_NOA);
        emailDevPage.searchEmail(gameCodeOfPatch);
        emailIsNotDisplayed_Director_noa = emailDevPage.emailIsNotDisplayed();
        emailDevPage.enterMailBox(TextConstants.lc_Director_NOE);
        emailDevPage.searchEmail(gameCodeOfPatch);
        emailIsNotDisplayed_Director_noe = emailDevPage.emailIsNotDisplayed();
        emailDevPage.enterMailBox(TextConstants.lc_oversea_coord_NOA);
        emailDevPage.searchEmail(gameCodeOfPatch);
        emailIsNotDisplayed_overseaCoord_noa = emailDevPage.emailIsNotDisplayed();
        emailDevPage.enterMailBox(TextConstants.lc_oversea_coord_NOE);
        emailDevPage.searchEmail(gameCodeOfPatch);
        emailIsNotDisplayed_overseaCoord_noe = emailDevPage.emailIsNotDisplayed();

    }

    @Test()
    @Order(11)
    public void verifyMailNotSentToLCDirectorAndLCOverseas_NOA_NOE_Expected() {
        assertAll(() -> assertTrue(emailIsNotDisplayed_Director_noa, "Verify email NOT sent to LC Director (NOA)"),
                () -> assertTrue(emailIsNotDisplayed_Director_noe, "Verify email NOT sent to LC Director (NOE)"),
                () -> assertTrue(emailIsNotDisplayed_overseaCoord_noa, "Verify email NOT sent to LC Overseas Coordinator (NOA)"),
                () -> assertTrue(emailIsNotDisplayed_overseaCoord_noe, "Verify email NOT sent to LC Overseas Coordinator (NOE)"));
    }

    @Test()
    @Order(12)
    public void clickSourceTag_Step() {
        EmailDevPage emailDevPage = (EmailDevPage) currentPage;
        emailDevPage.enterMailBox(TextConstants.lc_Director_NCL);
        emailDevPage.searchEmail(gameCodeOfPatch);
        currentPage = emailDevPage.clickOpenEmail().clickSourceButton().selectSourceTag();
    }

    @Test()
    @Order(13)
    public void verifySourceTag_Expected() {
        ViewHtmlInbucketPage viewHtmlInbucketPage = (ViewHtmlInbucketPage) currentPage;
        assertAll(() -> assertTrue(viewHtmlInbucketPage.waitElementIsNotVisible("receipient"), "Verify no receipients are visible"),
                () -> assertTrue(viewHtmlInbucketPage.waitElementIsNotVisible("To:"), "Verify no \"To\" field is present in the email notification source"));

    }

    @Test()
    @Order(14)
    public void submitInitialToLotcheck_Step(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByGameCode(initialCode);
        NDPProductDashboardProductInfoTab productInfoTab = ndpProductDashboardPage.clickProductInfo();
        applicationId = productInfoTab.getApplicationID();
        gameCode_Initial = productInfoTab.getGameCode();

        createRom(testReport);

        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        myProductsPage = (NDPMyProductsPage) currentPage;
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = myProductsPage.clickProductByGameCode(gameCode_Initial).clickReleases();
        NDPReleaseInfoPage releaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
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
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = releaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, romInitialName);
        releaseInfoPage.nav().clickIssues().openRequiredApprovalTab().performAllIssueAtApprovalTab(false);

        NDPDevelopmentHome developmentHome = releaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        licensingWaiverRequestsTab.waitingForResultTableLoaded()
                .enterSearchingBox(gameCode_Initial)
                .clickOnMagnifyingIconButton()
                .waitingForResultTableLoaded();
        int numberOfRequestByPendingStatus = licensingWaiverRequestsTab.collectNumberOfRequestByPendingStatus();
        for (int i = 0; i < numberOfRequestByPendingStatus; i++) {
            developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton().clickOnLicensingWaiverRequestsTab();
            licensingWaiverRequestsTab.waitingForResultTableLoaded()
                    .enterSearchingBox(gameCode_Initial)
                    .clickOnMagnifyingIconButton()
                    .waitingForResultTableLoaded();
            currentPage = licensingWaiverRequestsTab.openLicensingWaiverRequestByPendingStatus().selectAssignToMeButton()
                    .selectOKButtonInAssignToMeModal()
                    .selectApproveRequestButton()
                    .selectOKButtonInApproveRequestModal()
                    .waitingForApproveSuccessful();

        }
        NDPViewTaskPage ndpViewTaskPage = (NDPViewTaskPage) currentPage;

        currentPage = ndpViewTaskPage.clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(ndpUser)
                .clickMyProducts().clickProductByGameCode(gameCode_Initial).clickReleases().selectInitialRelease().nav().clickSubmitButton()
                .clickSubmit();
        WaitUtils.idle(3000);
    }

    @Test()
    @Order(15)
    public void verifySubmitSuccessfully_Expected() {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentPage;
        assertEquals(TextConstants.submitToLotcheck, ndpReleaseInfoPage.getStatusValue(), "Verify submit initial successfully");
    }

    @Test()
    @Order(16)
    public void loginSLCMSByLotcheckUser_Step(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        currentPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
    }

    @Test()
    @Order(17)
    public void verifyLoginSLCMSByLotcheckUser_Expected() {
        LotcheckLandingPage landingPage = (LotcheckLandingPage) currentPage;
        assertEquals("Lotcheck - LCMS", landingPage.getPageTitle(), "Verify can login and access slcms");
    }

    @Test()
    @Order(18)
    public void goToOverallLotcheck_Step() {
        LotcheckLandingPage landingPage = (LotcheckLandingPage) currentPage;
        currentPage = landingPage.clickOverallLotcheck(LotcheckRegion.NCL);
    }

    @Test()
    @Order(19)
    public void verifyGoToOverallLotcheck_Expected() {
        LotcheckQueuePage lotcheckQueuePage = (LotcheckQueuePage) currentPage;
        assertEquals("NCL Overall Lotcheck - LCMS", lotcheckQueuePage.getPageTitle(), "Verify can access the Overall Lotcheck page");
    }

    @Test()
    @Order(20)
    public void locateAnyTestPlanForTheNewSubmission_Step() {
        LotcheckQueuePage lotcheckQueuePage = (LotcheckQueuePage) currentPage;
        lotcheckQueuePage.searchByInitialCode(initialCode);
        int i = 0;
        while (i <= 20) {
            lotcheckQueuePage.clickSearchIcon();
            String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
            if (numberValue.equalsIgnoreCase("2")) {
                break;
            }
            i++;
            WaitUtils.idle(3000);
        }
        lotcheckQueuePage.clickPlannedTestDateNotSet();
    }

    @Test()
    @Order(21)
    public void verifyLocateAnyTestPlanForTheNewSubmission_Expected() {
        LotcheckQueuePage lotcheckQueuePage = (LotcheckQueuePage) currentPage;
        assertAll(() -> assertTrue(lotcheckQueuePage.isDisplayedTestPlan(gameCode_Initial), "Verify can locate to Test Plan of initial"),
                () -> assertTrue(lotcheckQueuePage.isDisplayedTestPlan(gameCodeOfPatch), "Verify can locate to Test Plan of patch"));
    }

    @Test()
    @Order(22)
    public void selectTestRailHyperlink_Step() {
        LotcheckQueuePage lotcheckQueuePage = (LotcheckQueuePage) currentPage;
        lotcheckQueuePage.clickGoToTestRail();
    }

    @Test()
    @Order(23)
    public void verifyOpenTestRailHyperlink_Expected() {
        LotcheckQueuePage lotcheckQueuePage = (LotcheckQueuePage) currentPage;
        String currentPageName = lotcheckQueuePage.getPageTitle();
        TestRailLoginPage loginPage = lotcheckQueuePage.navigateToNewTab();
        currentPage = loginPage;
        assertAll(() -> assertEquals("NCL Overall Lotcheck - LCMS", currentPageName, "Verify open testrail on new tab"),
                () -> assertEquals("Login - TestRail", loginPage.getPageTitle(), "Verify open testrail on new tab"));
    }

    @Test()
    @Order(24)
    public void signInTestRail_Step() {
        TestRailLoginPage loginPage = (TestRailLoginPage) currentPage;
        currentPage = loginPage.signInTestRail(testrailUser).navTopTestrail().navigateToManagerDashboard();
    }

    @Test()
    @Order(25)
    public void verifySignInTestRail_Expected() {
        TestRailManagerDashboardPage managerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        assertEquals("Manager Dashboard", managerDashboardPage.getPageTitle(), "Verify login testrail successfully");
    }

    @Test()
    @Order(26)
    public void openAllTestcaseInMyToDo_Step() {
        TestRailManagerDashboardPage managerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        managerDashboardPage.checkErrorPopup();
        managerDashboardPage.actionFill().enterTestPlanNameFilter(gameCode_Initial)
                .selectStatusUntested().selectStatusPassed();
        managerDashboardPage.actionFill().clickOnApplyFiltersButton();
        managerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan()
                .clickAssignTo()
                .selectSetValueAssignToRadio()
                .selectUser("Me")
                .clickOk()
                .waitingLoadingTestSuite();
        TestRailMyToDoPage myToDoPage = managerDashboardPage.topNav()
                .navigateToMyToDo();
        myToDoPage.actionFill()
                .enterTestPlanNameFilter(gameCode_Initial);
        myToDoPage.actionFill().clickOnApplyFiltersButton();
        myToDoPage.navTestPlanTable()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .expandAllTestRuns();
        currentPage = myToDoPage;
    }

    @Test()
    @Order(27)
    public void verifyCanViewAllTestcaseInMyToDo_Expected() {
        TestRailMyToDoPage myToDoPage = (TestRailMyToDoPage) currentPage;
        assertTrue(myToDoPage.navTestPlanTable().checkCanViewTestcases(), "Verify can view testcases");
    }

    @Test()
    @Order(28)
    public void openAnyTestCase_Step() {
        TestRailMyToDoPage myToDoPage = (TestRailMyToDoPage) currentPage;
        myToDoPage.actionFill().clickOnApplyFiltersButton();
        currentPage = myToDoPage.navTestPlanTable().clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun()
                .openTestcaseDetail(1);
    }

    @Test()
    @Order(29)
    public void verifyCanAccessTestcaseViewScreen_Expected() {
        TestRailDetailTestcaseDialog detailTestcaseDialog = (TestRailDetailTestcaseDialog) currentPage;
        assertTrue(detailTestcaseDialog.testcaseDetailModalIsDisplayed(), "Verify user can access the Test Case View dialog");
    }

    @Test()
    @Order(30)
    public void selectSubmitBug_Step() {
        TestRailDetailTestcaseDialog detailTestcaseDialog = (TestRailDetailTestcaseDialog) currentPage;
        currentPage = detailTestcaseDialog.clickSubmitBug();
    }

    @Test()
    @Order(31)
    public void verifyCanAccessSubmitBugModal_Expected() {
        TestRailSubmitBugDialog submitBugDialog = (TestRailSubmitBugDialog) currentPage;
        assertTrue(submitBugDialog.submitBugDialogIsDisplayed(), "Verify user can access the submit bug dialog");
    }

    @Test()
    @Order(32)
    public void submitBug_Step() {
        TestRailSubmitBugDialog submitBugDialog = (TestRailSubmitBugDialog) currentPage;
        currentPage = submitBugDialog.enterDescription("Test submit bug")
                .clickSubmit();
    }

    @Test()
    @Order(33)
    public void verifySubmitBugSuccessfully_Expected() {
        TestRailSubmitBugDialog submitBugDialog = (TestRailSubmitBugDialog) currentPage;
        String message = submitBugDialog.getMessageSuccessfully();
        currentPage = submitBugDialog.clickOk().clickClose();
        assertEquals("Your Issue has been submitted successfully.", message, "Verify submit bug successfully");
    }

    @Test()
    @Order(34)
    public void submitBugForOtherTestcase_Step() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        testcase_step15 = testPlanTable.getTestcaseName(1);
        testcase1_step19 = testPlanTable.getTestcaseName(2);
        TestRailMyToDoPage myToDoPage = testPlanTable.navMyToDo();
        myToDoPage.actionFill().clickOnApplyFiltersButton();
        currentPage = myToDoPage.navTestPlanTable().clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun()
                .openTestcaseDetail(2)
                .clickSubmitBug()
                .enterDescription("Test submit bug for other testcase")
                .clickSubmit();
    }

    @Test()
    @Order(35)
    public void verifySubmitBugForOtherTestcaseSuccessfully_Expected() {
        TestRailSubmitBugDialog submitBugDialog = (TestRailSubmitBugDialog) currentPage;
        String message = submitBugDialog.getMessageSuccessfully();
        submitBugDialog.clickOk().clickClose()
                .logOut();
        assertEquals("Your Issue has been submitted successfully.", message, "Verify submit bug for other testcase successfully");
    }

    @Test()
    @Order(36)
    public void asLCMSNavigateToLotcheckIssueTab_Step(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckLandingPage landingPage = (LotcheckLandingPage) currentPage;
        LotcheckReportSubmissionOverview submissionOverview = landingPage.searchProduct(productName).selectSubmission(gameCode_Initial, "00", "00");
        currentPage = submissionOverview.navBar().clickLotcheckIssues();
    }

    @Test()
    @Order(37)
    public void verifyOpenLotcheckIssueTabSuccessfully_Expected() {
        LotcheckReportSubmissionLotcheckIssues lotcheckIssues = (LotcheckReportSubmissionLotcheckIssues) currentPage;
        assertEquals("Lotcheck Issues Submission: LCR - LCMS", lotcheckIssues.getPageTitle(), "Verify access lotcheck issue tab");
    }

    @Test()
    @Order(38)
    public void publishBug_Step() {
        LotcheckReportSubmissionLotcheckIssues lotcheckIssues = (LotcheckReportSubmissionLotcheckIssues) currentPage;
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails = lotcheckIssues.clickMenuOfIssue(testcase_step15)
                .selectViewIssueDetailsOfIssue(testcase_step15);
        titleIssue = submissionLotcheckIssueDetails.getTitleOfIssueDetail();
        currentPage = submissionLotcheckIssueDetails.performPublishIssue("Bug");
    }

    @Test()
    @Order(39)
    public void verifyPublishBugSuccessfully_Expected() {
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails = (LotcheckReportSubmissionLotcheckIssueDetails) currentPage;
        String partnerThreadStatus = submissionLotcheckIssueDetails.getPartnerThreadStatus();
        submissionLotcheckIssueDetails.refreshPageSLCMS().logOut();
        assertEquals("NEEDS RESPONSE", partnerThreadStatus, "Verify partner Thread Status after publish");
    }

    @Test()
    @Order(40)
    public void navigateToLotCheckIssuesInNDPSite_Step(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        currentPage = myProductsPage.clickProductByGameCode(initialCode).clickReleases().selectInitialRelease().nav().clickIssues()
                .clickOnLotcheckIssuesTab()
                .expandUnresolve()
                .expandIssue();
    }

    @Test()
    @Order(41)
    public void verifyUserCanViewIssuePublished_Expected() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        System.out.println("Actual Issue Title 1: " + titleIssue);
        System.out.println("Expect Issue Title: " + issuePage.getIssueTitle());
        assertEquals(titleIssue, issuePage.getIssueTitle(), "Verify issue published on slcms is displayed");
    }

    @Test()
    @Order(42)
    public void openIssueHistory_Step() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        issuePage.clickIssueHistory();
    }

    @Test()
    @Order(43)
    public void verifyViewIssueHistory_Expected() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        assertTrue(issuePage.issueHistoryTabIsDisplayed(), "Verify can view issue history");
    }

    @Test()
    @Order(44)
    public void checkAnyEntryInIssueHistory_Step() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        version = issuePage.getVersionValue();
        description = issuePage.getDescriptionValue();
        type = issuePage.getTypeValue();

    }

    @Test()
    @Order(45)
    public void checkAnyEntryInIssueHistory_Expected() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        assertAll(() -> assertEquals("00.00", version, "Verify version of issue"),
                () -> assertEquals("Test submit bug", description, "Verify description of issue"),
                () -> assertEquals("Needs Response", type, "Verify type of issue"),
                () -> assertTrue(issuePage.bugIsNotPublishIsNotDisplayed(testcase1_step19), "Verify bug is not public is not displayed to partner "));

    }
}
