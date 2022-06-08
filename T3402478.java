package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.junit.extensions.ScenarioTest;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.data.DataGen;
import net.nintendo.automation.ui.data.features.FeatureSettingsManager;
import net.nintendo.automation.ui.data.features.enums.NPLNEnum;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.d4c.D4CRomPublishSuit;
import net.nintendo.automation.ui.models.d4c.D4CRomTab;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthPage;
import net.nintendo.automation.ui.models.lcms.*;
import net.nintendo.automation.ui.models.lcms.submission_reports.*;
import net.nintendo.automation.ui.models.ndp.*;
import net.nintendo.automation.ui.models.ndp.admin.NDPAdminControlPanelPage;
import net.nintendo.automation.ui.models.ndp.admin.NDPAdminD4CIntegrationPage;
import net.nintendo.automation.ui.models.ndp.admin.NDPAdminDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.admin.NDPAdminHomePage;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_internal.view_product_dashboard.NDPSettingTab;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPAddUserModal;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPCreateReleasePage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardProductInfoTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardReleasesTab;
import net.nintendo.automation.ui.models.ndp.existing_release.*;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPReleaseInfoFeaturesNPLNDialog;
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
import net.nintendo.automation_core.common_utils.annotations.services.NDP;
import net.nintendo.automation_core.common_utils.annotations.services.SLCMS;
import net.nintendo.automation_core.common_utils.enums.LotcheckRegion;
import net.nintendo.automation_core.extent_reports.extensions.IJUnitTestReporter;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classname: T3402478
 * Version: 1.0.0
 * Purpose:
 */

@NDP
@SLCMS
@Tag("T3402478")
@ScenarioTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3402478 extends CommonBaseTest {
    private static Browser browser;
    private static User ndpUser;
    private static Browser slcmsBrowser;
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
    private static String titleIssue;
    private static ArrayList<String> RatingAgencyList = new ArrayList<>();
    private static ArrayList<String> RatingValueList = new ArrayList<>();
    private static User ndpObserverUser;
    private static String userNameAdded;
    private static String romID;
    private static String status;
    private static boolean howToCheckNPLNFriendListTrue;
    private static boolean howToCheckNPLNFriendPresenceInformationTrue;
    String fileName = "test_doc.docx";
    private static String messageError;
    private static boolean alertJudgmentComplete;
    private static boolean confirmButtonIsEnable;
    private static String warningMessageDisplay;
    private static String commentTextDisplay;

    @AfterAll
    static void terminateBrowser() {
        BrowserManager.closeBrowser(browser);
        BrowserManager.closeBrowser(slcmsBrowser);
        BrowserManager.closeBrowser(romBrowser);
        UserManager.releaseUser(ServiceType.NDP, ndpUser);
        UserManager.releaseUser(ServiceType.SLCMS, slcmsUser);
        UserManager.releaseUser(ServiceType.SEARCH, romUser);
        UserManager.releaseUser(ServiceType.NDP, licUser);
        UserManager.releaseUser(ServiceType.PMMS, testRailUser);
    }

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.admin_Partner_Anh3);
        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
        slcmsUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);
        testRailUser = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER);
        productName = DataGen.getRandomProductName();

        NDPHomePage ndpHomePage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
        ndpHomePage.acceptCookie();
        NDPSignInPage signInPage = ndpHomePage.clickSignInPage();
        signInPage.signIn(ndpUser);
    }


    @Test()
    @Order(1)
    public void submitInitialReleaseToLotcheck_Step1(IJUnitTestReporter testReport) {
        NDPMyProductsPage myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPCreateProductPage createProductPage = myProductsPage.createNewProduct();
        createProductPage.selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(TextConstants.third_Party_Type)
                .selectTargetDeliveryFormat(TextConstants.both_Target_Type)
                .selectDigitalJapanSalesRegion()
                .selectPhysicalJapanSalesRegion()
                .selectProductName(productName)
                .selectProductNameKana(TextConstants.productKana)
                .clickCreateButton();
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        CommonAction.issueGameCode(ndpProductDashboardPage, 47, "NO");

        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();
        applicationID = ndpProductDashboardProductInfoTab.getApplicationID();
        gameCode = ndpProductDashboardProductInfoTab.getGameCode();
        initialCode = ndpProductDashboardProductInfoTab.getInitialCode();
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
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
                .selectTargetDeliveryFormat(TextConstants.both_Target_Type)
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
    }

    @Test()
    @Order(2)
    public void locateTestPlanInTestRailAndSubmitABug_Step2(IJUnitTestReporter testReport) {
        slcmsBrowser = BrowserManager.getNewInstance();
        LotcheckHomePage lotcheckHomePage = slcmsBrowser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReport);
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.signIn(TextConstants.noa_VanN_Email, TextConstants.noa_VanN_Password);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickTesterAssignment(LotcheckRegion.NCL);
        CommonAction.findTestPlanInLCQueue(lotcheckLandingPage, 30, LotcheckRegion.NCL.toString(), initialCode, "2");
        lotcheckQueuePage.clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        String testPlanStatus = lotcheckReportSubmissionOverview.matrix().getTestPlanStatus(0);
        assertTrue(testPlanStatus.equalsIgnoreCase("Assign Testers"), "Test plan status is displayed correctly");
        //verify in Testrail
        TestRailLoginPage testRailLoginPage = lotcheckReportSubmissionOverview.navBar().switchToTestRailLoginPage();
        TestRailManagerDashboardPage managerDashboardPage = testRailLoginPage.signInTestRail("tradmin@test.ncl.nintendo.com", "password")
                .navTopTestrail()
                .navigateToManagerDashboard()
                .waitingLoadingTestPlan()
                .clickOkInErrorPopup()
                .actionFill()
                .enterTestPlanNameFilter(initialCode)
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan();
        int num = managerDashboardPage.collectNumberTestPlan();
        assertTrue(num == 2, "Display test plan number correct");
        //submit bug
        slcmsBrowser.refresh();
        managerDashboardPage.waitingLoadingTestPlan()
                .clickOkInErrorPopup();
        CommonAction.submitBug(managerDashboardPage, initialCode);
    }

    @Test()
    @Order(3)
    public void setStatusAndJudgmentOkForTestPlan_Step3(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage managerDashboardPage = slcmsBrowser.getPage(TestRailManagerDashboardPage.class, testReport);
        slcmsBrowser.refresh();
        managerDashboardPage.waitingLoadingTestPlan()
                .clickOkInErrorPopup();
        CommonAction.setStatusOnTestrailWithoutPercentComplete(managerDashboardPage, initialCode, TextConstants.passedStatus, TextConstants.OK, false);

        slcmsBrowser.refresh();
        managerDashboardPage.waitingLoadingTestPlan()
                .clickOkInErrorPopup();
        CommonAction.setStatusOnTestrailWithoutPercentComplete(managerDashboardPage, initialCode, TextConstants.passedStatus, TextConstants.OK, false);

    }

    @Test()
    @Order(4)
    public void navigateApproveJudgmentQueueAndSelectApproveJudgment_Step4(IJUnitTestReporter testReport) {
        LotcheckLandingPage lotcheckLandingPage = slcmsBrowser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToJudgmentApprovalQueue("NCL");
        lotcheckQueuePage.searchByInitialCode(initialCode);
        int i = 0;
        while (i <= 20) {
            i++;
            lotcheckQueuePage.refreshPageSLCMS();
            lotcheckQueuePage.searchByInitialCode(initialCode);
            String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
            if (numberValue.equalsIgnoreCase("2")) {
                LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
                String judgmentStatus = lotcheckQueuePage.getJudgmentStatus();
                if (judgmentStatus.equals(TextConstants.OK)) {
                    WaitUtils.idle(10000);
                    lotcheckTestPlanRow.selectApproveJudgment().clickConfirm();
                    lotcheckTestPlanRow.selectApproveJudgment();
                    WaitUtils.idle(10000);
                    break;
                }
            }
        }

    }

    @Test()
    @Order(5)
    public void verifyWarningMessageWhenSelectApprovalJudgment_Expected4(IJUnitTestReporter testReport) {
        LotcheckTestPlanActionConfirmationModal confirmationModal = slcmsBrowser.getPage(LotcheckTestPlanActionConfirmationModal.class, testReport);
        String warningMessage = confirmationModal.getWarningMessage();
        assertAll(() -> assertTrue(confirmationModal.isConfirmButtonClickable(), "Confirm button is enable"),
                () -> assertTrue(warningMessage.equalsIgnoreCase("There are still unpublished Lotcheck Issues.\n" + "Click the Cancel button to check the status of Lotcheck Issues."), "Warning message is displayed correct"));

    }

    @Test()
    @Order(6)
    public void selectConfirmButtonToJudgmentOK_Step5(IJUnitTestReporter testReport) {
        LotcheckTestPlanActionConfirmationModal confirmationModal = slcmsBrowser.getPage(LotcheckTestPlanActionConfirmationModal.class, testReport);
        confirmationModal.clickConfirm();
    }

    @Test()
    @Order(7)
    public void verifyInitialPassLotcheck_Expected5(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        browser.refresh();
        String status = releaseInfoPage.getStatus();
        assertTrue(status.equalsIgnoreCase(TextConstants.passedLotCheck), "Initial release is passed lotcheck");
    }

    @Test()
    @Order(8)
    public void createNewOnCardPatch_Step6(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = releaseInfoPage.selectBackToProductDashboardPage().clickReleases();
        NDPCreateReleasePage ndpCreateReleasePage = ndpProductDashboardReleasesTab.clickCreateNewReleaseButton();
        ndpCreateReleasePage.enterDisplayVersion("1.0.1")
                .selectExpectedSubmissionDate(5)
                .selectExpectedReleaseDate(35)
                .selectFreeToPlay("NO")
                .enterDescribeTheFeaturesPlanned(TextConstants.enterDescribeTheFeaturesPlanned)
                .enterDescribeTheBugsIssuesPlanned(TextConstants.enterDescribeTheBugsIssuesPlanned)
                .clickCreateButton();
    }

    @Test()
    @Order(9)
    public void verifyDeliveryType_Expected6(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        String releaseVersion = releaseInfoPage.getReleaseVersionValue();
        assertTrue(releaseVersion.equalsIgnoreCase("01"), "Create new patch release successful");
    }

    @Test()
    @Order(10)
    public void changeTargetDeliveryFormat_Step7(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        releaseInfoPage.selectTargetDeliveryFormat(TextConstants.both_Target_Type);
    }

    @Test()
    @Order(11)
    public void selectAsiaCheckboxUnderSaleRegionPhysical_Step8(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        releaseInfoPage.selectPhysicalAsiaSalesRegion();
    }

    @Test()
    @Order(12)
    public void verifyPhysicalJapanSaleRegion_Expected8(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        assertAll(() -> assertTrue(releaseInfoPage.isPhysicalJapanSaleRegionSelected(), "Physical japan sales region is already selected."),
                () -> assertFalse(releaseInfoPage.isPhysicalJapanSaleRegionEnabled(), "Physical japan sales region is already locked."));
    }

    @Test()
    @Order(13)
    public void fillOutRequiredFieldInReleaseInfo_Step9(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        CommonAction.selectROMSameAllRegion(releaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);
    }

    @Test()
    @Order(14)
    public void verifyCompleteReleaseInfo_Expected9(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        assertTrue(releaseInfoPage.nav().isReleaseInfoCompleted(), "Release Info should now be marked as finished in the navigation menu.");
    }

    @Test()
    @Order(15)
    public void navigateToFeatureSectionAndSelectYesForSupportsNPLN_Step10(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        CommonAction.getFeatureOptionData(releaseInfoPage, TextConstants.npln_Yes, TextConstants.allOff, TextConstants.allOff, true, TextConstants.allOff, false, "");

    }

    @Test()
    @Order(16)
    public void fillOutAllInformationOnNPLNSection_Step11(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoFeaturesPage featuresPage = browser.getPage(NDPReleaseInfoFeaturesPage.class, testReport);
        HashMap<NPLNEnum, String> nplnSettings = FeatureSettingsManager.getNPLNSettings(TextConstants.allOn);
        NDPReleaseInfoFeaturesNPLNDialog nplnDialog = featuresPage.clickEditNPLN();
        nplnDialog.setFeatures(nplnSettings).clickSave();
    }

    @Test()
    @Order(17)
    public void fillOutRequiredFieldToCompleteFeatures_Step12(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoFeaturesPage featuresPage = browser.getPage(NDPReleaseInfoFeaturesPage.class, testReport);
        NDPReleaseInfoGuidelinesPage guidelinesPage = featuresPage.clickContinue();
    }

    @Test()
    @Order(18)
    public void verifyCompleteFeatures_Expected12(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoGuidelinesPage guidelinesPage = browser.getPage(NDPReleaseInfoGuidelinesPage.class, testReport);
        assertTrue(guidelinesPage.nav().isFeaturesCompleted(), "Features should now be marked as finished in the navigation menu.");
    }

    @Test()
    @Order(19)
    public void fillOutRequiredFieldToCompleteGuidelines_Step13(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoGuidelinesPage guidelinesPage = browser.getPage(NDPReleaseInfoGuidelinesPage.class, testReport);
        guidelinesPage.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();

    }

    @Test()
    @Order(20)
    public void verifyCompleteGuidelines_Expected13(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoGuidelinesPage guidelinesPage = browser.getPage(NDPReleaseInfoGuidelinesPage.class, testReport);
        WaitUtils.idle(3000);
        browser.refresh();
        assertTrue(guidelinesPage.nav().isGuidelinesCompleted(), "Guidelines should now be marked as finished in the navigation menu.");

    }

    @Test()
    @Order(21)
    public void goToTestScenariosPage_Step14(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoGuidelinesPage guidelinesPage = browser.getPage(NDPReleaseInfoGuidelinesPage.class, testReport);
        NDPReleaseInfoTestScenarios testScenarios = guidelinesPage.nav().clickTestScenarios();
        //add method for TS1
        testScenarios.setFocusedTestScene(TextConstants.NPLN, "How to check NPLN friend list")
                .expandCategory()
                .expandTestScene()
                .selectAddMethod()
                .enterNameThisMethod(TextConstants.enterSameNameMethod, 1)
                .selectFileToAdd(new File("src/test/resources/DataFile/Test_excel.xlsx"), 1)
                .enterExplainHowToDemonstrateTheMethod(TextConstants.explanation, 1)
                //Add information for NPLN TS
                .setFocusedTestScene(TextConstants.NPLN, "How to check NPLN friend presence information")
                .expandTestScene()
                .enterNameThisMethod(TextConstants.enterNPLNNameMethod, 1)
                .enterExplainHowToDemonstrateTheMethod(TextConstants.explanation, 1);
    }

    @Test()
    @Order(22)
    public void addANewMethod_Step15(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoTestScenarios testScenarios = browser.getPage(NDPReleaseInfoTestScenarios.class, testReport);
        //add a new method for TS1
        testScenarios.setFocusedTestScene(TextConstants.NPLN, "How to check NPLN friend list")
                .selectAddMethod()
                .enterNameThisMethod(TextConstants.enterSameNameMethod, 1)
                .enterExplainHowToDemonstrateTheMethod(TextConstants.explanation, 1)
                .selectFileToAdd(new File("src/test/resources/DataFile/test_doc.docx"), 1);
    }

    @Test()
    @Order(23)
    public void completeTestScenarios_Step16(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoTestScenarios testScenarios = browser.getPage(NDPReleaseInfoTestScenarios.class, testReport);
        testScenarios.setFocusedTestScene(TextConstants.NPLN, "How to check NPLN friend list")
                .enterNameThisMethod(TextConstants.enterSameNameMethod, 3)
                .enterExplainHowToDemonstrateTheMethod(TextConstants.explanation, 3)
                .clickOnSaveButton();

    }

    @Test()
    @Order(24)
    public void verifyCompleteTestScenarios_Expected16(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoTestScenarios testScenarios = browser.getPage(NDPReleaseInfoTestScenarios.class, testReport);
        WaitUtils.idle(6000);
        browser.refresh();
        assertTrue(testScenarios.nav().isTestScenariosCompleted(), "Test Scenarios should now be marked as finished in the navigation menu.");

    }

    @Test()
    @Order(25)
    public void completeAgeRating_Step17(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoTestScenarios testScenarios = browser.getPage(NDPReleaseInfoTestScenarios.class, testReport);
        NDPReleaseInfoAgeRating ageRating = testScenarios.nav().clickAgeRating();
        ageRating.clickOnAddStandardRatingButton()
                .selectStandardAgeRating("GRAC")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectRatingValue("Not required")
                .clickOnSaveButtonInEditStandardRating();
    }

    @Test()
    @Order(26)
    public void verifyCompleteAgeRating_Expected17(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoAgeRating ageRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReport);
        assertTrue(ageRating.nav().isAgeRatingCompleted(), "Age Rating should now be marked as finished in the navigation menu.");
    }

    @Test()
    @Order(27)
    public void createRomUploadFileAndPerformCompleteRomUpload_Step18(IJUnitTestReporter testReport) throws Exception {
        //create file ROM
        romBrowser = BrowserManager.getNewInstance();
        ROMLoginPage romLoginPage = romBrowser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN, testReport);
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom
                .selectRequester(TextConstants.test_User)
                .selectType(TextConstants.patch_Release)
                .selectTargetDeliveryFormat(TextConstants.both_Target_Type)
                .enterProductName(productName)
                .enterApplicationId(applicationID)
                .selectPublishingRelationShip(TextConstants.first_Party_Type)
                .enterDisplayVersion("1.0.1")
                .enterReleaseVersion("01")
                .enterOriginalApp(fileRomName)
                .selectCERORating("CERO: A")
                .selectGRACGCRBRating("GRACGCRB: Not required")
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
    }

    @Test()
    @Order(28)
    public void verifyCompleteRomUpload_Expected18(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoReleaseListNav nav = browser.getPage(NDPReleaseInfoReleaseListNav.class, testReport);
        assertTrue(nav.isROMUploadCompleted(), "ROM Upload should now be marked as finished in the navigation menu.");
    }

    @Test()
    @Order(29)
    public void completeIssues_Step19(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoReleaseListNav nav = browser.getPage(NDPReleaseInfoReleaseListNav.class, testReport);
        //Issues
        NDPReleaseInfoIssuePage issuePage = nav.clickIssues();
        CommonAction.enterReasonForRequest(issuePage);
        nav.clickOnUserNameMenu().clickOnSignOutButton();
        //Approval Issue TaskRequested
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReport).clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, gameCode);
        NDPViewTaskPage viewTaskPage = browser.getPage(NDPViewTaskPage.class, testReport);
        viewTaskPage.clickUserMenuDropdownList().clickSignOutButton();
        NDPReleaseInfoPage ndpReleaseInfoPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReport)
                .clickSignInPage()
                .signIn(ndpUser)
                .clickMyProducts()
                .clickProductByName(productName)
                .clickReleases()
                .selectPatchRelease();
        // Fix Release Errors Issue
        ndpReleaseInfoPage.nav().clickIssues()
                .openReleaseErrorsTab();
        assertAll(() -> assertEquals(TextConstants.targetSalesRegionNotConfiguredForDigitalVersionOnReleaseErrorTab, issuePage.getReleaseErrorsMessage(), TextConstants.verifyIssueOnReleaseErrorTab));
        ndpReleaseInfoPage.nav().clickReleaseInfo()
                .selectDigitalAsiaSalesRegion()
                .save();
    }

    @Test()
    @Order(30)
    public void verifyCompleteIssues_Expected19(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoPage ndpReleaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        assertTrue(ndpReleaseInfoPage.nav().isIssuesCompleted(), "Issues should now be marked as finished in the navigation menu.");
    }

    @Test()
    @Order(31)
    public void selectSubmitSubmission_Step20(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoPage ndpReleaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        NDPSubmissionReviewPage submissionReviewPage = ndpReleaseInfoPage.nav().clickSubmitButton();
    }

    @Test()
    @Order(32)
    public void verifyAccessSubmissionReviewPage_Expected20(IJUnitTestReporter testReport) throws Exception {
        NDPSubmissionReviewPage submissionReviewPage = browser.getPage(NDPSubmissionReviewPage.class, testReport);
        assertTrue(submissionReviewPage.isSubmitReviewDisplayed(), "User can access the Submission Review page and Submission form");
    }

    @Test()
    @Order(33)
    public void selectSubmitToLotcheck_Step21(IJUnitTestReporter testReport) throws Exception {
        NDPSubmissionReviewPage submissionReviewPage = browser.getPage(NDPSubmissionReviewPage.class, testReport);
        submissionReviewPage.enterDeveloperComment(TextConstants.enterDeveloperComment).clickSubmit();
        WaitUtils.idle(3000);
    }

    @Test()
    @Order(34)
    public void verifySubmitToLotcheck_Expected21(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoPage ndpReleaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        assertEquals(TextConstants.submitToLotcheck, ndpReleaseInfoPage.getStatusValue(), TextConstants.submitSuccess);
    }

    @Test()
    @Order(35)
    public void openTheLotcheckReportTestScenariosTab_Step22(IJUnitTestReporter testReport) throws InterruptedException {
        LotcheckLandingPage lotcheckLandingPage = slcmsBrowser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToTesterAssignmentQueue("NCL");
        int i = 0;
        while (i < 20) {
            lotcheckLandingPage.navigationToTesterAssignmentQueue(LotcheckRegion.NCL.toString()).searchByInitialCode(initialCode);
            String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
            i++;
            if (numberValue.equalsIgnoreCase("2")) {
                break;
            }
        }
        LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview submissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        LotcheckReportSubmissionTestScenarios lotcheckReportSubmissionTestScenarios = submissionOverview.navBar().clickTestScenarios();
        howToCheckNPLNFriendListTrue = lotcheckReportSubmissionTestScenarios.howToCheckNPLNFriendListTestScenariosIsDisplay();
        howToCheckNPLNFriendPresenceInformationTrue = lotcheckReportSubmissionTestScenarios.howToCheckNPLNFriendPresenceInformationIsDisplay();
    }

    @Test()
    @Order(36)
    public void verifyNPLNisDisplay_Expected22(IJUnitTestReporter testReport) {
        assertAll(
                () -> assertTrue(howToCheckNPLNFriendListTrue, "How To Check NPLN Friend List is Display"),
                () -> assertTrue(howToCheckNPLNFriendPresenceInformationTrue, "How To Check NPLN Friend Presence Information Is Display")
        );
    }

    @Test()
    @Order(37)
    public void clickFileTabInSubmission_Step23(IJUnitTestReporter testReport) {
        LotcheckReportNav navBar = slcmsBrowser.getPage(LotcheckReportNav.class, testReport);
        navBar.clickFiles();
    }

    @Test()
    @Order(38)
    public void selectOnlyATestScenario_Step24(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionFiles lotcheckReportSubmissionFiles = slcmsBrowser.getPage(LotcheckReportSubmissionFiles.class, testReport);
        lotcheckReportSubmissionFiles.selectMethodOfTestScenario("same name method", 1);
    }

    @Test()
    @Order(39)
    public void verifyOnlyATestScenarioSelected_Expected24(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionFiles lotcheckReportSubmissionFiles = slcmsBrowser.getPage(LotcheckReportSubmissionFiles.class, testReport);
        int num = lotcheckReportSubmissionFiles.getNumberTestScenarioSelected();
        assertTrue(num == 1, "Only one Test Scenarios is selected");
    }


    @Test()
    @Order(40)
    public void clickUploadFileInFileTab_Step25(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionFiles lotcheckReportSubmissionFiles = slcmsBrowser.getPage(LotcheckReportSubmissionFiles.class, testReport);
        slcmsBrowser.refresh();
        lotcheckReportSubmissionFiles.clickUploadFile();
    }

    @Test()
    @Order(41)
    public void SelectAExcelFileXLSMAndClickUpload_Step26(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionFiles lotcheckReportSubmissionFiles = slcmsBrowser.getPage(LotcheckReportSubmissionFiles.class, testReport);
        lotcheckReportSubmissionFiles.selectUploadFileAtPopup()
                .selectFileInPC("Auto_Anis.xlsm")
                .clickUploadButtonAtPopupUploadFile();
        messageError = lotcheckReportSubmissionFiles.getErrorMessageAtAlert();
        System.out.println(messageError);
    }

    @Test()
    @Order(42)
    public void verifyMessageErrorWhenUploadFileXlsm_Expected26(IJUnitTestReporter testReport) {
        assertEquals("Could not upload file: Error: Invalid File extension not found (SLCMS0455)", messageError, "The error message is displayed");
    }

    @Test()
    @Order(43)
    public void submitBugInTesTRail_Step27(IJUnitTestReporter testReport) {
        TestRailDashboardPage testRailDashboardPage = slcmsBrowser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestRail().navigateToManagerDashboard();

        testRailManagerDashboardPage.waitingLoadingTestPlan()
                .clickOkInErrorPopup();
        //CommonAction.submitBug(testRailManagerDashboardPage, initialCode);
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(initialCode)
                .selectStatusUntested().selectStatusPassed().selectStatusFailed();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        TestRailSubmitBugDialog submitBugDialog = testRailManagerDashboardPage
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun()
                .clickOnTheFirstTestcase()
                .clickSubmitBug();
        submitBugDialog.enterDescription(TextConstants.enterSubmitBug).clickSubmit();
        String messageSuccess = submitBugDialog.getMessageSuccessfully();
        assertEquals("Your Issue has been submitted successfully.", messageSuccess, "Verify submit bug successfully");
        submitBugDialog.clickOk().clickClose();
    }

    @Test()
    @Order(44)
    public void publicBugToPartnerWithTypeAsSubmissionIssue_Step28(IJUnitTestReporter testReport) {
        LotcheckLandingPage lotcheckLandingPage = slcmsBrowser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToTesterAssignmentQueue("NCL");
        int i = 0;
        while (i < 20) {
            lotcheckLandingPage.navigationToTesterAssignmentQueue("NCL").searchByInitialCode(initialCode);
            String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
            i++;
            if (numberValue.equalsIgnoreCase("2")) {
                break;
            }
        }
        LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview submissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        LotcheckReportSubmissionLotcheckIssues submissionLotcheckIssues = submissionOverview.navBar().clickLotcheckIssues();
        slcmsBrowser.refresh(); //Todo: Fix bug: Menu icon element click intercepted - Other element would receive the click
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails = submissionLotcheckIssues.waitLoadingLotcheckIssue()
                .clickMenu()
                .selectViewIssueDetails();
        submissionLotcheckIssueDetails.clickMenu()
                .selectPublishToPublic()
                .selectLciType("Bug")
                .clickPublish();
    }

    @Test()
    @Order(45)
    public void sendWillFixInRom_Step29(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage ndpReleaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        NDPReleaseInfoIssuePage ndpReleaseInfoIssuePage = ndpReleaseInfoPage.nav().clickIssues();
        ndpReleaseInfoIssuePage.clickExpandUnresolved().clickExpandTestCase().clickSendResolutionButton();
    }

    @Test()
    @Order(46)
    public void setStatusTestPlanOnTestRail_Step30(IJUnitTestReporter testReport) throws InterruptedException {
        TestRailDashboardPage testRailDashboardPage = slcmsBrowser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport);
        TestRailManagerDashboardPage managerDashboardPage = testRailDashboardPage.navTopTestRail().navigateToManagerDashboard();
        managerDashboardPage.waitingLoadingTestPlan()
                .clickOkInErrorPopup();
        //CommonAction.setPassedTestCaseOnTestrail(managerDashboardPage, initialCode);
        CommonAction.setStatusOnTestrailWithoutPercentComplete(managerDashboardPage, initialCode, TextConstants.passedStatus, TextConstants.OK, false);
        slcmsBrowser.refresh();
        managerDashboardPage.waitingLoadingTestPlan()
                .clickOkInErrorPopup();
        //CommonAction.setPassedTestCaseOnTestrail(managerDashboardPage, initialCode);
        CommonAction.setStatusOnTestrailWithoutPercentComplete(managerDashboardPage, initialCode, TextConstants.passedStatus, TextConstants.OK, false);
    }

    @Test()
    @Order(47)
    public void navigateApproveJudgmentQueueAndSelectApproveJudgmentPatch_Step31(IJUnitTestReporter testReport) {
        LotcheckLandingPage lotcheckLandingPage = slcmsBrowser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToJudgmentApprovalQueue("NCL");
        lotcheckQueuePage.searchByInitialCode(initialCode);
        int i = 0;
        while (i <= 20) {
            i++;
            lotcheckQueuePage.refreshPageSLCMS();
            lotcheckQueuePage.searchByInitialCode(initialCode);
            String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
            if (numberValue.equalsIgnoreCase("2")) {
                LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
                String judgmentStatus = lotcheckQueuePage.getJudgmentStatus();
                if (judgmentStatus.equals(TextConstants.OK)) {
                    WaitUtils.idle(10000);
                    lotcheckTestPlanRow.selectApproveJudgment().clickConfirm();
                    lotcheckTestPlanRow.selectApproveJudgment();
                    WaitUtils.idle(10000);
                    break;
                }
            }
        }
    }

    @Test()
    @Order(48)
    public void verifyWarningMessageOnApprovalPopup_Expected31(IJUnitTestReporter testReport) {
        LotcheckTestPlanActionConfirmationModal confirmationModal = slcmsBrowser.getPage(LotcheckTestPlanActionConfirmationModal.class, testReport);
        String warningMessage = confirmationModal.getWarningMessage();
        assertAll(() -> assertTrue(confirmationModal.isConfirmButtonClickable(), "Confirm button is enable"),
                () -> assertTrue(warningMessage.equalsIgnoreCase("There are still unresolved Lotcheck Issues.\n" + "Click the Cancel button to check the status of Lotcheck Issues."), "Warning message is displayed correct"));

    }

    @Test()
    @Order(49)
    public void approvalIsPassLotcheck_Step32(IJUnitTestReporter testReport) {
        LotcheckTestPlanActionConfirmationModal confirmationModal = slcmsBrowser.getPage(LotcheckTestPlanActionConfirmationModal.class, testReport);
        confirmationModal.clickConfirm();
    }

    @Test()
    @Order(50)
    public void verifyJudgmentSuccessfully_Expected32(IJUnitTestReporter testReport) {
        NDPReleaseInfoIssuePage issuePage = browser.getPage(NDPReleaseInfoIssuePage.class, testReport);
        NDPReleaseInfoPage releaseInfoPage = issuePage.nav().clickReleaseInfo();
        String status = releaseInfoPage.getStatus();
        assertTrue(status.equalsIgnoreCase("Passed Lotcheck"), "Initial release is passed lotcheck");
    }

    //missing 1 step-33 - will remove this step
    @Test()
    @Order(51)
    public void loginNdpByPartner_Step33(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        NDPSettingTab settingTab = releaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton()
                .clickSignInPage()
                .signIn(TextConstants.lic_pwr_user_NCL, TextConstants.nintendo_approval_Password).clickInternalTab()
                .clickProductSearchButton()
                .waitingLoadingValueSearch()
                .clickSearchContent(productName)
                .selectElementFromDropDown()
                .clickSettingTab()
                .selectUppUpdate()
                .clickSaveButton();
        settingTab.clickUserMenuDropdownList().clickSignOutButton();
        NDPHomePage homePage = browser.getPage(NDPHomePage.class, testReport);
        homePage.clickSignInPage().signIn(ndpUser);
    }

    @Test()
    @Order(52)
    public void verifyLoginNdpByPartner_Expected33(IJUnitTestReporter testReport) {
        NDPDevelopmentHome developmentHome = browser.getPage(NDPDevelopmentHome.class, testReport);
        assertEquals("HOME - Nintendo Developer Portal", developmentHome.getPageTitle(), "Verify login and access the private homepage successfully");
    }

    @Test()
    @Order(53)
    public void goToMyProducts_Step34(IJUnitTestReporter testReport) {
        NDPDevelopmentHome developmentHome = browser.getPage(NDPDevelopmentHome.class, testReport);
        developmentHome.clickMyProducts();
    }

    @Test()
    @Order(54)
    public void verifyAccessMyProducts_Expected34(IJUnitTestReporter testReporter) {
        NDPMyProductsPage myProductsPage = browser.getPage(NDPMyProductsPage.class, testReporter);
        assertEquals("My Products - Nintendo Developer Portal", myProductsPage.getPageTitle(), "Verify access the MY PRODUCTS page successfully");
    }

    @Test()
    @Order(55)
    public void selectCreatedProduct_Step35(IJUnitTestReporter testReporter) {
        NDPMyProductsPage myProductsPage = browser.getPage(NDPMyProductsPage.class, testReporter);
        myProductsPage.clickProductByGameCode(gameCode);
    }

    @Test()
    @Order(56)
    public void verifyAccessProductDashboard_Expected35(IJUnitTestReporter testReporter) {
        NDPProductDashboardPage productDashboardPage = browser.getPage(NDPProductDashboardPage.class, testReporter);
        assertAll(() -> assertEquals("Game Code : " + gameCode, productDashboardPage.getGameCode(), "Verify access correct product dashboard of created product"),
                () -> assertEquals("Dashboard - Nintendo Developer Portal", productDashboardPage.getPageTitle(), "Verify access Product Dashboard"));
    }

    @Test()
    @Order(57)
    public void goToReleaseTab_Step36(IJUnitTestReporter testReporter) {
        NDPProductDashboardPage productDashboardPage = browser.getPage(NDPProductDashboardPage.class, testReporter);
        productDashboardPage.clickReleases();
    }

    @Test()
    @Order(58)
    public void verifyOpenReleaseTab_Expected36(IJUnitTestReporter testReporter) {
        NDPProductDashboardReleasesTab releasesTab = browser.getPage(NDPProductDashboardReleasesTab.class, testReporter);
        assertTrue(releasesTab.releaseTabIsDisplayed(), "Verify access release tab");
    }

    @Test()
    @Order(59)
    public void selectCreateNewRelease_Step37(IJUnitTestReporter testReporter) {
        NDPProductDashboardReleasesTab releasesTab = browser.getPage(NDPProductDashboardReleasesTab.class, testReporter);
        releasesTab.clickCreateNewReleaseButton();
    }

    @Test()
    @Order(60)
    public void verifyAccessCreateReleaseForm_Expected37(IJUnitTestReporter testReporter) {
        NDPCreateReleasePage createReleasePage = browser.getPage(NDPCreateReleasePage.class, testReporter);
        // add observer user
        ndpObserverUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_AnhBTK);
        userNameAdded = ndpObserverUser.getUserName();
        String pageTitle = createReleasePage.getPageTitle();
        NDPAddUserModal addUserModal = createReleasePage.clickCancel(false).clickMembersTab()
                .selectAddUserButton();
        addUserModal.selectMyCompanyUsers()
                .selectInternalUsers(userNameAdded)
                .selectAccessRole(TextConstants.observerUser)
                .clickAddUserButton()
                .navProductDashboard()
                .clickUserMenuDropdownList()
                .clickSignOutButton()

                //Login as Observer user
                .clickSignInPage()
                .signIn(ndpObserverUser)
                .clickUserMenuDropdownList()
                .selectEnglishAnyWay();
        NDPProductDashboardReleasesTab releasesTab = browser.getPage(NDPDevelopmentHome.class, testReporter)
                .clickMyProducts()
                .clickProductByGameCode(gameCode)
                .clickReleases();
        boolean isNotCreateNewReleaseButtonClickable = releasesTab.isNotCreateNewReleaseButtonClickable();
        // login as product admin user
        releasesTab.navProductDashboard()
                .clickUserMenuDropdownList()
                .clickSignOutButton()
                .clickSignInPage()
                .signIn(ndpUser)
                .clickMyProducts()
                .clickProductByGameCode(gameCode)
                .clickReleases()
                .clickCreateNewReleaseButton();

        assertAll(() -> assertTrue(isNotCreateNewReleaseButtonClickable, "Verify Create Release button is disable for users with the Product Observer role"),
                () -> assertEquals("Create Release - Nintendo Developer Portal", pageTitle, "Verify access create release form"));
    }

    @Test()
    @Order(61)
    public void selectReleaseType_Step38(IJUnitTestReporter testReporter) {
        NDPCreateReleasePage createReleasePage = browser.getPage(NDPCreateReleasePage.class, testReporter);
        createReleasePage.selectReleaseType(" UPP Update ");

    }

    @Test()
    @Order(62)
    public void verifyCreateNewUppUpdateReleaseForm_Expected38(IJUnitTestReporter testReporter) {
        NDPCreateReleasePage createReleasePage = browser.getPage(NDPCreateReleasePage.class, testReporter);
        assertAll(() -> assertTrue(createReleasePage.baseReleaseToUpdateFieldIsDisplayed(), "Verify Base Release to Update field is displayed"),
                () -> assertTrue(createReleasePage.expectedSubmissionDateFieldIsDisplayed(), "Verify Expected Submission Date field is displayed"),
                () -> assertTrue(createReleasePage.expectedReleaseDateFieldIsDisplayed(), "Verify Expected Release Date field is displayed"),
                () -> assertEquals("UPP Update", createReleasePage.getReleaseTypeSelected(), "Verify can set Release Type to UPP Update"));

    }

    @Test()
    @Order(63)
    public void selectBaseReleaseToUpdate_Step39(IJUnitTestReporter testReporter) {
        NDPCreateReleasePage createReleasePage = browser.getPage(NDPCreateReleasePage.class, testReporter);
        createReleasePage.selectReleaseBase("01 (Patch)");

    }

    @Test()
    @Order(64)
    public void verifyBaseReleaseToUpdate_Expected39(IJUnitTestReporter testReporter) {
        NDPCreateReleasePage createReleasePage = browser.getPage(NDPCreateReleasePage.class, testReporter);
        String releaseBaseSelected = createReleasePage.getReleaseBaseSelected();
        createReleasePage.clickCancel(true)
                .clickReleases()
                .clickCreateNewReleaseButton()
                .enterDisplayVersion("1.0.2")
                .selectExpectedSubmissionDate(2)
                .selectExpectedReleaseDate(70)
                .enterDescribeTheFeaturesPlanned(TextConstants.enterDescribeTheFeaturesPlanned)
                .enterDescribeTheBugsIssuesPlanned(TextConstants.enterDescribeTheBugsIssuesPlanned)
                .clickCreateButton()
                .selectBackToProductDashboardPage()
                .clickReleases()
                .clickCreateNewReleaseButton()
                .selectReleaseType(" UPP Update ");
        boolean checkDisplayOfBaseReleaseToUpdate = createReleasePage.checkDisplayOfBaseReleaseToUpdate();
        createReleasePage.selectReleaseBase("01 (Patch)");
        assertAll(() -> assertTrue(checkDisplayOfBaseReleaseToUpdate, "Verify display of base release to update field"),
                () -> assertEquals("01 (Patch)", releaseBaseSelected, "Verify can select On-Card Patch release"));

    }

    @Test()
    @Order(65)
    public void setExpectedSubmissionDateAndExpectedReleaseDate_Step40(IJUnitTestReporter testReporter) {
        NDPCreateReleasePage createReleasePage = browser.getPage(NDPCreateReleasePage.class, testReporter);
        createReleasePage.selectExpectedSubmissionDate(5)
                .selectExpectedReleaseDate(90);
    }

    @Test()
    @Order(66)
    public void verifySetExpectedSubmissionDateAndExpectedReleaseDate_Expected40(IJUnitTestReporter testReporter) {
        NDPCreateReleasePage createReleasePage = browser.getPage(NDPCreateReleasePage.class, testReporter);
        createReleasePage.refreshPage().selectReleaseType(" UPP Update ")
                .selectReleaseBase("01 (Patch)");
        createReleasePage.clickOnExpectedSubmissionDate();
        String message_1 = createReleasePage.getErrorMessageExpectedSubmissionDateBlank();
        createReleasePage.clickOnExpectedReleaseDate();
        String message_2 = createReleasePage.getErrorMessageExpectedReleaseDateBlank();
        createReleasePage.selectExpectedSubmissionDate(3)
                .selectExpectedReleaseDate(1);
        String message_3 = createReleasePage.getErrorMessageWhenEXDMoreThanERD();
        createReleasePage.selectExpectedReleaseDate(62);
        String message_4 = createReleasePage.getErrorMessageWhenERDLessThan30DaysAfterEXD();
        createReleasePage.selectExpectedReleaseDate(90);
        assertAll(() -> assertEquals("This field is required.", message_1, "Verify error message when Expected Lotcheck Submission Date is blank  "),
                () -> assertEquals("This field is required.", message_2, "Verify error message when Expected Release Date is blank"),
                () -> assertEquals("You must submit to Lotcheck before your Expected Release Date.", message_3, "Verify error message when"),
                () -> assertEquals("Warning: The Expected Release Date must be at least 60 days later than the Expected Lotcheck Submission Date. Please update the dates accordingly.", message_4, "Verify error message when Expected Release Date is LESS THAN 30 days AFTER Expected Lotcheck Submission Date"));

    }

    @Test()
    @Order(67)
    public void clickCreate_Step41(IJUnitTestReporter testReporter) {
        NDPCreateReleasePage createReleasePage = browser.getPage(NDPCreateReleasePage.class, testReporter);
        createReleasePage.clickCreateButton();
    }

    @Test()
    @Order(68)
    public void verifyCreateSuccessfully_Expected41(IJUnitTestReporter testReporter) {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReporter);
        assertEquals(TextConstants.upp_Update_Release, releaseInfoPage.getReleaseTypeOfUppUpdate(), "Verify can submit the Create Release form and create a new UPP Update release.");
    }

    @Test()
    @Order(69)
    public void completeTheGuidelinesTasks_Step42(IJUnitTestReporter testReporter) {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReporter);
        releaseInfoPage.nav().clickGuidelines().selectCompliesWithAllOfTheAboveGuidelines().clickOnSaveButton();
        //submit ROM
        D4CRomPublishSuit d4cRom = browser.openURL(D4CRomPublishSuit.class, PageState.PRE_LOGIN, testReporter);
        String idApplication = applicationID.substring(2);
        D4CRomTab d4cRomTab = d4cRom.clickTabRom();
        romID = d4cRomTab.enterApplicationID(idApplication).clickSearchButton().getRomId();
        //d4cRom.clickLogOut();
        NDPAdminHomePage adminHomePage = browser.openURL(NDPAdminHomePage.class, PageState.PRE_LOGIN, testReporter);
        NDPAdminDevelopmentHome adminDevelopmentHome = adminHomePage.acceptCookie().clickSignInPage().signIn("prdcvdev1_omniadmin01", "nintendo");
        NDPAdminControlPanelPage adminControlPanelPage = adminDevelopmentHome.clickMenuAdmin().clickControlPanel();
        NDPAdminD4CIntegrationPage adminD4CIntegrationPage = adminControlPanelPage.clickD4CIntegrationAdminButton();
        adminD4CIntegrationPage.submitROMCheckResults(romID);
        adminDevelopmentHome.logOutPage();

        browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReporter)
                .clickProductByGameCode(gameCode)
                .clickReleases()
                .selectRelease(TextConstants.upp_Update_Release);
        int i = 0;
        while (i < 5) {
            if (!releaseInfoPage.nav().romUploadLockDisplayed()) {
                break;
            }
            browser.refresh();
            i++;
        }
        NDPReleaseInfoIssuePage issuePage = releaseInfoPage.nav().clickIssues();
        CommonAction.enterReasonForRequest(issuePage);
        releaseInfoPage.nav().clickOnUserNameMenu().clickOnSignOutButton();
        //Approval Issue TaskRequested
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReporter).clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, gameCode);
        NDPViewTaskPage ndpViewTaskPage = browser.getPage(NDPViewTaskPage.class, testReporter);
        ndpViewTaskPage.clickUserMenuDropdownList()
                .clickSignOutButton()
                .clickSignInPage()
                .signIn(ndpUser)
                .clickMyProducts()
                .clickProductByGameCode(gameCode)
                .clickReleases()
                .selectRelease(TextConstants.upp_Update_Release);

    }

    @Test()
    @Order(70)
    public void verifyCompleteTheGuidelinesTasks_Expected42(IJUnitTestReporter testReporter) {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReporter);
        assertAll(() -> assertTrue(releaseInfoPage.nav().isReleaseInfoCompleted(), "Verify release info completed"),
                () -> assertTrue(releaseInfoPage.nav().isFeaturesCompleted(), "Verify feature completed"),
                () -> assertTrue(releaseInfoPage.nav().isGuidelinesCompleted(), "Verify guidelines completed"),
                () -> assertTrue(releaseInfoPage.nav().isTestScenariosCompleted(), "Verify test scenario completed"),
                () -> assertTrue(releaseInfoPage.nav().isAgeRatingCompleted(), "Verify age rating completed"),
                () -> assertTrue(releaseInfoPage.nav().isIssuesCompleted(), "Verify issues completed"),
                () -> assertTrue(releaseInfoPage.nav().isSubmitButtonClickable(), "Verify submit button is clickable"));
    }

    @Test()
    @Order(71)
    public void submitUppUpdateToLotcheck_Step43(IJUnitTestReporter testReporter) {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReporter);
        releaseInfoPage.nav().clickSubmitButton().clickSubmit();
    }

    @Test()
    @Order(72)
    public void verifySubmitUppUpdateToLotcheck_Expected43(IJUnitTestReporter testReporter) {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReporter);
        assertEquals(TextConstants.submitToLotcheck, releaseInfoPage.getStatusValue(), TextConstants.submitSuccess);
    }

    @Test()
    @Order(73)
    public void signInTestRailAndNavToManageDashboard_Step44(IJUnitTestReporter testReport) {
        TestRailDashboardPage dashboardPage = slcmsBrowser.openURL(TestRailDashboardPage.class, PageState.POST_LOGIN, testReport);
        dashboardPage.navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
    }

    @Test()
    @Order(74)
    public void verifyTestPlanOfUppUpdateIsDisplayed_Expected44(IJUnitTestReporter testReporter) {
        TestRailManagerDashboardPage testRailManagerDashboardPage = slcmsBrowser.getPage(TestRailManagerDashboardPage.class, testReporter);
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(gameCode)
                .selectStatusUntested()
                .selectStatusPassed()
                .selectStatusFailed();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar();
        boolean isTestPlanOfUppRelease = testRailManagerDashboardPage.getTestPlanName().contains("2000");
        assertTrue(isTestPlanOfUppRelease, "Verify The UPP Update testplan is listed in the Manager Dashboard");
    }

    @Test()
    @Order(75)
    public void setStatusToFailed_Step45(IJUnitTestReporter testReporter) throws InterruptedException {
        TestRailManagerDashboardPage testRailManagerDashboardPage = slcmsBrowser.getPage(TestRailManagerDashboardPage.class, testReporter);
        testRailManagerDashboardPage.clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan()
                .clickOnSetStatusButton()
                .selectStatusOnSetStatusModal(TextConstants.failedStatus)
                .clickOnUpdateStatusButtonOnSetStatusModal()
                .waitingLoadingTestSuite();
        WaitUtils.idle(50000);
    }


    @Test()
    @Order(76)
    public void verifySetStatusToFailed_Expected45(IJUnitTestReporter testReporter) {
        TestRailManagerDashboardPage testRailManagerDashboardPage = slcmsBrowser.getPage(TestRailManagerDashboardPage.class, testReporter);
        testRailManagerDashboardPage.clickOnPadlockButtonOfTheFirstTestPlan();
        assertTrue(testRailManagerDashboardPage.checkJudgementOption(TextConstants.NG), "Verify This will allow for an NG judgment");

    }

    @Test()
    @Order(77)
    public void judgmentNGForTestPlan_Step46(IJUnitTestReporter testReporter) {
        TestRailManagerDashboardPage testRailManagerDashboardPage = slcmsBrowser.getPage(TestRailManagerDashboardPage.class, testReporter);
        testRailManagerDashboardPage.selectTestPlanJudgment(TextConstants.NG)
                .clickOnYesButtonOnConfirmationModal();
    }

    @Test()
    @Order(78)
    public void verifyJudgmentNGForTestPlan_Expected46(IJUnitTestReporter testReporter) {
        TestRailManagerDashboardPage testRailManagerDashboardPage = slcmsBrowser.getPage(TestRailManagerDashboardPage.class, testReporter);
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        Boolean testplanIsNotVisible = testRailManagerDashboardPage.testplanIsNotVisible(gameCode);
        assertTrue(testplanIsNotVisible, "Verify testplan leave in Manager Dashboard");
    }

    @Test()
    @Order(79)
    public void loginSLCMSAndApproveJudgment_Step47(IJUnitTestReporter testReport) {
        LotcheckLandingPage lotcheckLandingPage = slcmsBrowser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickJudgment(LotcheckRegion.NCL);
        lotcheckQueuePage.searchByInitialCode(initialCode);
        int i = 0;
        while (i <= 20) {
            lotcheckQueuePage.clickSearchIcon();
            String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
            if (numberValue.equalsIgnoreCase("1")) {
                LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
                String judgmentStatus = lotcheckQueuePage.getJudgmentStatus();
                if (judgmentStatus.equals(TextConstants.NG)) {
                    WaitUtils.idle(2000);
                    lotcheckTestPlanRow.selectApproveJudgment().clickConfirm();
                    break;
                }
            }
            i++;
            WaitUtils.idle(3000);
        }
        lotcheckQueuePage.refreshPageSLCMS();
    }

    @Test()
    @Order(80)
    public void verifyJudgmentStatusOfTestPlan_Expected47(IJUnitTestReporter testReporter) {
        LotcheckQueuePage lotcheckQueuePage = slcmsBrowser.getPage(LotcheckQueuePage.class, testReporter);
        lotcheckQueuePage = lotcheckQueuePage.navLandingPage().clickFinish(LotcheckRegion.NCL);
        lotcheckQueuePage.searchByInitialCode(initialCode).clickSearchIcon();
        String judgmentStatus = lotcheckQueuePage
                .getJudgmentStatusUpp(5);
        assertEquals(TextConstants.NG, judgmentStatus, "Verify test plan is listed with an NG Judgment");
    }

    @Test()
    @Order(81)
    public void signInNdpAsPartnerAndNavigateToReleaseTab_Step48(IJUnitTestReporter testReporter) {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReporter);
        NDPProductDashboardReleasesTab releasesTab = releaseInfoPage.selectBackToProductDashboardPage().clickReleases();
        browser.refresh();
        status = releasesTab.getStatusOfRelease(TextConstants.upp_Update_Release);
        releasesTab.selectRelease(TextConstants.upp_Update_Release);

    }

    @Test()
    @Order(82)
    public void signInNdpAsPartnerAndNavigateToReleaseTab_Expected48(IJUnitTestReporter testReporter) {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReporter);
        assertAll(() -> assertTrue(releaseInfoPage.nav().isSubmitButtonInActived(), "Verify UPP Update cannot be resubmitted to Lotcheck"),
                () -> assertEquals(TextConstants.failedLotCheck, status, "Verify status of UPP update"));
    }
}
