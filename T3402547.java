package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.junit.TestLogger;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.data.DataGen;
import net.nintendo.automation.ui.data.features.FeatureSettingsManager;
import net.nintendo.automation.ui.data.features.enums.PatchesEnum;
import net.nintendo.automation.ui.data.features.enums.ProgramSpecificationsEnum;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthPage;
import net.nintendo.automation.ui.models.lcms.LotcheckHomePage;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.ui.models.lcms.LotcheckQueuePage;
import net.nintendo.automation.ui.models.lcms.LotcheckTestPlanRow;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionFiles;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionProductInformation;
import net.nintendo.automation.ui.models.ndp.NDPCreateProductPage;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.NDPSignInPage;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPCreateReleasePage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardProductInfoTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardReleasesTab;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPCopyReleaseDataPopup;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoAgeRating;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoFeaturesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoGuidelinesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoIssuePage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoROMUpload;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPConfirmationDialog;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPReleaseInfoFeaturesPatchesDialog;
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
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classname: T3402547
 * Version: 1.0.0
 * Purpose:
 */

@NDP
@SLCMS
@Tag("T3402547")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3402547 extends CommonBaseTest {
    private static Browser browser;
    private static User ndpUser;
    private static User slcmsUser;
    private static Browser romBrowser;
    private static User romUser;
    private static User licUser;
    private static User testRailUser;

    private static CommonUIComponent currentNDPPage;
    private static String productName;
    private static CommonUIComponent currentPage;
    private static TestLogger logger;
    private static String applicationID;
    private static String initialCode;
    private static String gameCode;
    private static String fileRomName;
    private static ArrayList<String> RatingAgencyList = new ArrayList<>();
    private static ArrayList<String> RatingValueList = new ArrayList<>();

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
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_Trang);
        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.int_approval_coord_NCL);
        slcmsUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);
        testRailUser = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER);
        productName = DataGen.getRandomProductName();

        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
        NDPHomePage ndpHomePage = (NDPHomePage) currentPage;
        ndpHomePage.acceptCookie();
        NDPSignInPage signInPage = ndpHomePage.clickSignInPage();
        signInPage.signIn(ndpUser);

    }

    @BeforeEach
    public void each(IJUnitTestReporter testReport) {
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
    }

    @Test()
    @Order(1)
    public void createProduct(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        NDPCreateProductPage createProductPage = myProductsPage.createNewProduct();
        createProductPage.selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(TextConstants.first_Party_Type)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalEuropeSalesRegion()
                .selectDigitalJapanSalesRegion()
                .selectProductName(productName)
                .selectProductNameKana(TextConstants.productKana)
                .clickCreateButton();
        NDPProductDashboardPage ndpDashboardPage = myProductsPage.clickProductByName(productName);
        CommonAction.issueGameCode(ndpDashboardPage, 65,"NO");
    }

    @Test
    @Order(2)
    public void createRomUpload(IJUnitTestReporter testReport) throws InterruptedException {
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
        ROMLoginPage romLoginPage = romBrowser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN);
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom
                .selectRequester(TextConstants.test_User)
                .selectType(TextConstants.initial_Release)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .enterProductName(productName)
                .enterApplicationId(applicationID)
                .selectPublishingRelationShip(TextConstants.first_Party_Type)
                .enterDisplayVersion("1.0.0")
                .enterReleaseVersion("00")
                .selectCERORating("CERO: A")
                .selectPEGIRating("PEGI: PEGI 3")
                .clickOnCreateButton()
                .waitIsLoaded();
        fileRomName = romCreateRom.getRomName();
        romCreateRom.clickOnROMFileOnROMTable(applicationID);
        WaitUtils.idle(8000);
        romCreateRom.clickOnUserNameMenu().clickOnLogoutButton();
        BrowserManager.closeBrowser(romBrowser);
    }

    @Test()
    @Order(3)
    public void fillOurDashboard(IJUnitTestReporter testReport) throws Exception {
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
        CommonAction.selectROMSameAllRegion(releaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);
        // //create Feature
        CommonAction.getFeatureOptionData(releaseInfoPage, TextConstants.allOff_EU, TextConstants.allOff, TextConstants.allOff, false, "", false, "");
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
        ndpReleaseInfoAgeRating.clickOnAddStandardRatingButton()
                .selectStandardAgeRating("PEGI")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectRatingValue("PEGI 3")
                .uploadCertificateAttachment(new File("src/test/resources/DataFile/Test_excel.xlsx"))
                .clickOnSaveButtonInEditStandardRating();
        //Upload ROM
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = ndpReleaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
        //Delete file Rom in folder
        ndpReleaseInfoROMUpload.deleteFile(fileRomName);
        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
        issuePage.openRequiredApprovalTab();
        issuePage.performAllIssueAtApprovalTab(false);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
        //Approval Issue TaskRequested
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(licUser).clickOnMyTaskMenu();
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        CommonAction.approveInternalWaiverRequest(developmentHome, internalTasksPage, gameCode);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
    }

    //submit product
    @Test
    @Order(4)
    public void submitLotCheck(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser).clickMyProducts();
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.enterDeveloperComment).clickSubmit();
        WaitUtils.idle(3000);
    }

    //Navigate to Files tab in SLCMS
    @Test()
    @Order(5)
    public void verifyFilesTabInSLCMS(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.signIn(TextConstants.noa_VanN_Email, TextConstants.noa_VanN_Password);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickTesterAssignment(LotcheckRegion.NCL);
        CommonAction.findProductInLCQueue(lotcheckLandingPage, 30, LotcheckRegion.NCL.toString(), initialCode);
        lotcheckQueuePage.clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        LotcheckReportSubmissionProductInformation productInformation = lotcheckReportSubmissionOverview.navBar().clickProductInformation();
        LotcheckReportSubmissionFiles lotcheckReportSubmissionFiles = lotcheckReportSubmissionOverview.navBar().clickFiles();
        lotcheckReportSubmissionFiles.clickCERO();
        String getFileName = lotcheckReportSubmissionFiles.getFileName();
        String fileName = getFileName.substring(21);
        lotcheckReportSubmissionFiles.clickPEGI();
        String getFileNamePEGI = lotcheckReportSubmissionFiles.getFileName();
        String fileNamePEGI = getFileNamePEGI.substring(21);
        assertAll(
                () -> assertEquals("ab.jpg", fileName),
                () -> assertEquals("Test_excel.xlsx", fileNamePEGI),
                () -> assertNotEquals(fileName, fileNamePEGI)
        );
    }

    //Cancel Submission
    @Test()
    @Order(6)
    public void cancelSubmission(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        NDPConfirmationDialog ndpConfirmationDialog = ndpReleaseInfoPage.nav().clickCancelSubmitButton();
        ndpConfirmationDialog.clickOKInConfirm();
    }

    @Test()
    @Order(7)
    public void createRomSubmission01(IJUnitTestReporter testReport) throws InterruptedException {
        NDPMyProductsPage myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();
        applicationID = ndpProductDashboardProductInfoTab.getApplicationID();
        gameCode = ndpProductDashboardProductInfoTab.getGameCode();
        initialCode = ndpProductDashboardProductInfoTab.getInitialCode();
        //create ROM
        romBrowser = BrowserManager.getNewInstance();
        currentPage = romBrowser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN);
        ROMLoginPage romLoginPage = (ROMLoginPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom.reloadPage()
                .selectRequester(TextConstants.test_User)
                .selectType(TextConstants.initial_Release)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .enterProductName(productName)
                .enterApplicationId(applicationID)
                .selectPublishingRelationShip(TextConstants.first_Party_Type)
                .enterDisplayVersion("1.0.0")
                .enterReleaseVersion("00")
                .selectPEGIRating("PEGI: PEGI 3")
                .selectCERORating("CERO: A")
                .selectUSKRating("USK: 0")
                .clickOnCreateButton()
                .waitIsLoaded();
        fileRomName = romCreateRom.getRomName();
        romCreateRom.clickOnROMFileOnROMTable(applicationID);
        WaitUtils.idle(8000);
        romCreateRom.clickOnUserNameMenu().clickOnLogoutButton();
        BrowserManager.closeBrowser(romBrowser);
    }

    @Test()
    @Order(8)
    public void reSubmitRelease(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        int i = 0;
        while (i < 40) {
            String statusRelease = ndpProductDashboardReleasesTab.getStatusRelease();
            if (statusRelease.equals(TextConstants.pendingCancellation)) {
                ndpProductDashboardPage.clickProductInfo();
                ndpProductDashboardPage.clickReleases();
                i++;
            } else {
                break;
            }
        }
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();

        //create release infor
        NDPReleaseInfoPage releaseInfoPage = ndpReleaseInfoPage.nav().clickReleaseInfo();
        releaseInfoPage.setExpectedLotcheckSubmissionDate();
        CommonAction.selectROMSameAllRegion(releaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);
        //guideline
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = ndpReleaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoGuidelinesPage.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();
        //Create Age Rating
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = ndpReleaseInfoPage.nav().clickAgeRating();
        ndpReleaseInfoAgeRating.selectViewAgeRating("CERO")
                .uploadCertificateAttachment(new File("src/test/resources/DataFile/Test_abc.jpg"))
                .clickOnSaveButtonInEditStandardRating();
        ndpReleaseInfoAgeRating.clickOnAddStandardRatingButton()
                .selectStandardAgeRating("USK")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectRatingValue("0")
                .uploadCertificateAttachment(new File("src/test/resources/DataFile/test_doc.docx"))
                .clickOnSaveButtonInEditStandardRating();
        //upload ROM
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = ndpReleaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
        //Delete file Rom in folder
        ndpReleaseInfoROMUpload.deleteFile(fileRomName);

        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
        issuePage.openRequiredApprovalTab();
        issuePage.performAllIssueAtApprovalTab(false);
    }

    //submit product
    @Test
    @Order(9)
    public void submitLotCheckSubmission01(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.enterDeveloperComment).clickSubmit();
        WaitUtils.idle(3000);
    }

    // SLCMS
    @Test()
    @Order(10)
    public void verifyFilesTabAgeInSLCMS(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToTesterAssignmentQueue(LotcheckRegion.NCL.toString());

        CommonAction.findProductInLCQueue(lotcheckLandingPage, 30, LotcheckRegion.NCL.toString(), initialCode);
        lotcheckQueuePage.clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        LotcheckReportSubmissionProductInformation productInformation = lotcheckReportSubmissionOverview.navBar().clickProductInformation();
        LotcheckReportSubmissionFiles lotcheckReportSubmissionFiles = lotcheckReportSubmissionOverview.navBar().clickFiles();
        lotcheckReportSubmissionFiles.clickCERO();
        String getFileName = lotcheckReportSubmissionFiles.getFileName();
        String fileName = getFileName.substring(21);
        lotcheckReportSubmissionFiles.clickUSK();
        String getFileNameUSk = lotcheckReportSubmissionFiles.getFileName();
        String fileNameUSk = getFileNameUSk.substring(21);
        assertAll(
                () -> assertEquals("Test_abc.jpg", fileName),
                () -> assertEquals("test_doc.docx", fileNameUSk),
                () -> assertNotEquals(fileName, fileNameUSk),
                () -> assertNotEquals("ab.jpg", "Test_abc.jpg")
        );
        //TODO: Not display Age Rating folder in Lotcheck >Files Tab when partner perform add ratings using the Standard Method
    }

    //testRail
    @Test()
    @Order(11)
    public void setStatusTestPlanOnTestRail(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        TestRailDashboardPage testRailDashboardPage = testRailLoginPage.signInTestRail(testRailUser);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        CommonAction.setStatusOnTestrailWithoutPercentComplete(testRailManagerDashboardPage, initialCode, TextConstants.passedStatus, TextConstants.OK, true);
        testRailDashboardPage.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton();

        testRailLoginPage.signInTestRail(TextConstants.trAdminNOE_Email, TextConstants.trAdmin_Password);
        testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        CommonAction.setStatusOnTestrailWithoutPercentComplete(testRailManagerDashboardPage, initialCode, TextConstants.passedStatus, TextConstants.OK, true);
        testRailDashboardPage.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton();
    }

    //Approve
    @Test()
    @Order(12)
    public void approvalJudgmentOK(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToJudgmentApprovalQueue(LotcheckRegion.NCL.toString());
        lotcheckQueuePage.searchByInitialCode(initialCode);
        int i = 0;
        while (i <= 100) {
            lotcheckQueuePage.refreshPageSLCMS();
            lotcheckQueuePage.searchByInitialCode(initialCode);
            String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
            if (numberValue.equalsIgnoreCase("1")) {
                LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
                String judgmentStatus = lotcheckQueuePage.getJudgmentStatus();
                if (judgmentStatus.equals(TextConstants.OK)) {
                    WaitUtils.idle(10000);
                    lotcheckTestPlanRow.selectApproveJudgment().clickConfirm();
                    break;
                }
            }
            i++;
            WaitUtils.idle(15000);
            lotcheckQueuePage.refreshPageSLCMS();
        }
        WaitUtils.idle(10000);
        lotcheckLandingPage.clickOnUserNameMenu().clickOnSignoutButton();
        browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN).continueToLogin().signInOther(TextConstants.noa_TrangN_Email, TextConstants.noa_TrangN_Password);
        lotcheckLandingPage.navigationToJudgmentApprovalQueue(LotcheckRegion.NOE.toString());
        lotcheckQueuePage.searchByInitialCode(initialCode);
        int j = 0;
        while (j <= 100) {
            lotcheckQueuePage.refreshPageSLCMS();
            lotcheckQueuePage.searchByInitialCode(initialCode);
            String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
            if (numberValue.equalsIgnoreCase("1")) {
                LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
                String judgmentStatus = lotcheckQueuePage.getJudgmentStatus();
                if (judgmentStatus.equals(TextConstants.OK)) {
                    WaitUtils.idle(10000);
                    lotcheckTestPlanRow.selectApproveJudgment().clickConfirm();
                    break;
                }
            }
            j++;
            WaitUtils.idle(15000);
            lotcheckQueuePage.refreshPageSLCMS();
        }

    }

    //create patch
    @Test()
    @Order(13)
    public void createNewReleasePatch(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPCreateReleasePage ndpCreateReleasePage = ndpProductDashboardReleasesTab.clickCreateNewReleaseButton();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpCreateReleasePage
                .enterDisplayVersion("1.0.1")
                .selectExpectedSubmissionDate(5)
                .selectExpectedReleaseDate(20)
                .selectFreeToPlay("NO")
                .enterDescribeTheFeaturesPlanned(TextConstants.enterDescribeTheFeaturesPlanned)
                .enterDescribeTheBugsIssuesPlanned(TextConstants.enterDescribeTheBugsIssuesPlanned)
                .clickCreateButton();
    }

    @Test()
    @Order(14)
    public void addStandardRatingAndCopyData(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectPatchRelease();
        // agreeRating
        currentNDPPage = ndpReleaseInfoPage;
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = ndpReleaseInfoPage.nav().clickAgeRating();
        ndpReleaseInfoAgeRating.clickOnAddStandardRatingButton()
                .selectStandardAgeRating("BBFC")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectRatingValue("U")
                .uploadCertificateAttachment(new File("src/test/resources/DataFile/test_doc.docx"))
                .clickOnSaveButtonInEditStandardRating();
        int collectNumberRating = ndpReleaseInfoAgeRating.collectNumberRating();
        for (int i = 0; i < collectNumberRating; i++) {
            RatingAgencyList.add(ndpReleaseInfoAgeRating.getRatingAgency(i));
            RatingValueList.add(ndpReleaseInfoAgeRating.getRatingValue(i));
        }
        ndpReleaseInfoPage.nav().clickReleaseInfo();
        String selectProductName = productName + " " + "(" + initialCode + ")";
        NDPCopyReleaseDataPopup ndpCopyReleaseDataPopup = ndpReleaseInfoPage.nav().clickCopyReleaseData();
        ndpCopyReleaseDataPopup.selectProductNameBase(selectProductName)
                .selectReleaseVersion("00.01-Initial Release")
                .selectFeature()
                .selectTestScenarios()
                .selectAgeRatings()
                .clickConfirmButton();
        String confirmCopySuccessful = ndpReleaseInfoPage.getMessageCopyData();
        assertAll(
                () -> assertEquals(TextConstants.selectReleaseDataCopiedSuccess, confirmCopySuccessful, TextConstants.copyReleaseDataSuccess)
        );
        ndpReleaseInfoPage.nav().clickAgeRating();
        ArrayList<String> RatingValueListAddMethod = new ArrayList<>();
        ArrayList<String> RatingAgencyListAddMethod = new ArrayList<>();
        int collectNumberRating1 = ndpReleaseInfoAgeRating.collectNumberRating();
        for (int j = 0; j < collectNumberRating1; j++) {
            RatingValueListAddMethod.add(ndpReleaseInfoAgeRating.getRatingValue(j));
            RatingAgencyListAddMethod.add(ndpReleaseInfoAgeRating.getRatingAgency(j));
        }
        assertAll(
                () -> assertEquals(RatingValueList, RatingValueListAddMethod),
                () -> assertEquals(RatingAgencyList, RatingAgencyListAddMethod)
        );

        NDPReleaseInfoFeaturesPage releaseFeatures = ndpReleaseInfoPage.nav().clickFeatures();
        assertAll(
                () -> assertTrue(releaseFeatures.isProgramSpecificationsComplete(), TextConstants.programSpecificComplete),
                () -> assertTrue(releaseFeatures.isInputDevicesComplete(), TextConstants.inputDeviceComplete),
                () -> assertTrue(releaseFeatures.isPlayModeComplete(), TextConstants.playModeComplete),
                () -> assertTrue(releaseFeatures.isLotcheckRemotePresenceComplete(), TextConstants.lotcheckRemotePreseneComplete)
        );
    }

    @Test()
    @Order(15)
    public void changeStandardRating(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentNDPPage;
        logger = new TestLogger(testReport);
        currentNDPPage.setExtentLogger(logger);
        File certificateAttachment = new File("src/test/resources/DataFile/非到呵钱二七问.docx");
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = ndpReleaseInfoPage.nav().clickAgeRating();
        ndpReleaseInfoAgeRating.selectViewAgeRating("BBFC")
                .selectRatingValue("12")
                .uploadCertificateAttachment(certificateAttachment)
                .clickOnSaveButtonInEditStandardRating();
        ndpReleaseInfoAgeRating.selectViewAgeRating("CERO")
                .selectRatingValue("B")
                .uploadCertificateAttachment(certificateAttachment)
                .clickOnSaveButtonInEditStandardRating();
        ndpReleaseInfoAgeRating.selectViewAgeRating("PEGI")
                .selectRatingValue("PEGI 7")
                .uploadCertificateAttachment(certificateAttachment)
                .clickOnSaveButtonInEditStandardRating();
        ndpReleaseInfoAgeRating.selectViewAgeRating("USK")
                .selectRatingValue("6")
                .uploadCertificateAttachment(certificateAttachment)
                .clickOnSaveButtonInEditStandardRating();
        ArrayList<String> RatingValueListChange = new ArrayList<>();
        ArrayList<String> RatingAgencyListChange = new ArrayList<>();
        int collectNumberRating = ndpReleaseInfoAgeRating.collectNumberRating();
        for (int i = 0; i < collectNumberRating; i++) {
            RatingValueListChange.add(ndpReleaseInfoAgeRating.getRatingValue(i));
            RatingAgencyListChange.add(ndpReleaseInfoAgeRating.getRatingAgency(i));
        }
        assertAll(
                () -> assertNotEquals(RatingValueList, RatingValueListChange),
                () -> assertEquals(RatingAgencyList, RatingAgencyListChange)
        );
    }

    @Test()
    @Order(16)
    public void addStandardRatingAndCopyDataRepeat(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentNDPPage;
        // agreeRating
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = ndpReleaseInfoPage.nav().clickAgeRating();
        ndpReleaseInfoAgeRating.clickOnAddStandardRatingButton()
                .selectStandardAgeRating("OFLC")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectRatingValue("G—General")
                .uploadCertificateAttachment(new File("src/test/resources/DataFile/test_doc.docx"))
                .clickOnSaveButtonInEditStandardRating();
        ArrayList<String> RatingValueListRepeat = new ArrayList<>();
        ArrayList<String> RatingAgencyListRepeat = new ArrayList<>();
        int collectNumberRating = ndpReleaseInfoAgeRating.collectNumberRating();
        for (int i = 0; i < collectNumberRating; i++) {
            RatingAgencyListRepeat.add(ndpReleaseInfoAgeRating.getRatingAgency(i));
            RatingValueListRepeat.add(ndpReleaseInfoAgeRating.getRatingValue(i));
        }
        ndpReleaseInfoPage.nav().clickReleaseInfo();
        //        //Copy release
        String selectProductName = productName + " " + "(" + initialCode + ")";
        NDPCopyReleaseDataPopup ndpCopyReleaseDataPopup = ndpReleaseInfoPage.nav().clickCopyReleaseData();
        ndpCopyReleaseDataPopup.selectProductNameBase(selectProductName)
                .selectReleaseVersion("00.01-Initial Release")
                .selectFeature()
                .selectTestScenarios()
                .selectAgeRatings()
                .clickConfirmButton();
        String confirmCopySuccessful = ndpReleaseInfoPage.getMessageCopyData();
        assertAll(
                () -> assertEquals(TextConstants.selectReleaseDataCopiedSuccess, confirmCopySuccessful, TextConstants.copyReleaseDataSuccess)
        );
        ndpReleaseInfoPage.nav().clickAgeRating();
        ArrayList<String> RatingValueListRepeatChange = new ArrayList<>();
        ArrayList<String> RatingAgencyListRepeatChange = new ArrayList<>();
        int collectNumberRating1 = ndpReleaseInfoAgeRating.collectNumberRating();
        for (int j = 0; j < collectNumberRating1; j++) {
            RatingValueListRepeatChange.add(ndpReleaseInfoAgeRating.getRatingValue(j));
            RatingAgencyListRepeatChange.add(ndpReleaseInfoAgeRating.getRatingAgency(j));
        }
        assertAll(
                () -> assertNotEquals(RatingValueListRepeat, RatingValueListRepeatChange, TextConstants.ratingValue),
                () -> assertEquals(RatingAgencyListRepeat, RatingAgencyListRepeatChange, TextConstants.ratingAgency)
        );
        NDPReleaseInfoFeaturesPage releaseFeatures = ndpReleaseInfoPage.nav().clickFeatures();
        assertAll(
                () -> assertTrue(releaseFeatures.isProgramSpecificationsComplete(), TextConstants.programSpecificComplete),
                () -> assertTrue(releaseFeatures.isInputDevicesComplete(), TextConstants.playModeComplete),
                () -> assertTrue(releaseFeatures.isPlayModeComplete(), TextConstants.playModeComplete),
                () -> assertTrue(releaseFeatures.isLotcheckRemotePresenceComplete(), TextConstants.lotcheckRemotePreseneComplete)
        );

    }

    @Test()
    @Order(17)
    public void createRomPatch(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();
        applicationID = ndpProductDashboardProductInfoTab.getApplicationID();
        gameCode = ndpProductDashboardProductInfoTab.getGameCode();
        productName = ndpProductDashboardProductInfoTab.getProductNameValue();
        String originalApp = "Application_" + applicationID + "_v00.nsp";
        //create ROM
        romBrowser = BrowserManager.getNewInstance();
        ROMLoginPage romLoginPage = romBrowser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN);
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom.reloadPage()
                .selectRequester(TextConstants.test_User)
                .selectType(TextConstants.patch_Release)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .enterProductName(productName)
                .enterApplicationId(applicationID)
                .selectPublishingRelationShip(TextConstants.first_Party_Type)
                .enterDisplayVersion("1.0.1")
                .enterReleaseVersion("01")
                .enterOriginalApp(originalApp)
                .selectBBFCRating("BBFC: 12")
                .selectCERORating("CERO: B")
                .selectPEGIRating("PEGI: PEGI 7")
                .selectUSKRating("USK: 6")
                .selectOFLCRating("OFLC: G—General")
                .clickOnCreateButton()
                .waitIsLoaded();
        fileRomName = romCreateRom.getRomNameTable(applicationID);
        romCreateRom.clickOnROMFileOnROMTable(applicationID);
        WaitUtils.idle(8000);
        romCreateRom.clickOnUserNameMenu().clickOnLogoutButton();
        BrowserManager.closeBrowser(romBrowser);
    }

    @Test()
    @Order(18)
    public void fillOutPatch(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectPatchRelease();
        // agreeRating
        ndpReleaseInfoPage.nav().clickReleaseInfo();
        ndpReleaseInfoPage.nav().clickReleaseInfo();
        ndpReleaseInfoPage
                .selectRomsThatDifferBetweenRegion(TextConstants.selectRomSameAllRegion)
                .selectLanguageUsedForTextEntry(TextConstants.english)
                .save();

        //create Feature
        //create Feature
        HashMap<ProgramSpecificationsEnum, String> programSpecificationsSettings = FeatureSettingsManager.getProgramSpecificationsSettings(TextConstants.allOff);
        HashMap<PatchesEnum, String> patchesSettings = FeatureSettingsManager.getPatchesSettings(TextConstants.allOff);
        NDPReleaseInfoFeaturesPage releaseFeatures = ndpReleaseInfoPage.nav().clickFeatures();
        if (!releaseFeatures.isProgramSpecificationsComplete()) {
            NDPReleaseInfoFeaturesProgramSpecsDialog progSpecDialog = releaseFeatures.clickEditProgramSpecifications();
            progSpecDialog.setFeatures(programSpecificationsSettings).clickSave();
        }
        NDPReleaseInfoFeaturesPatchesDialog patchesDialog = releaseFeatures.clickEditPatches();
        patchesDialog.setFeatures(patchesSettings).clickSave();
        releaseFeatures.clickContinue();

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
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
        //Approval Issue TaskRequested
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(licUser).clickOnMyTaskMenu();
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        CommonAction.approveInternalWaiverRequest(developmentHome, internalTasksPage, gameCode);
        myProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
    }

    @Test
    @Order(19)
    public void submitButtonPatch(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser).clickMyProducts();
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectPatchRelease();
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.commentSubmission).clickSubmit();
        WaitUtils.idle(3000);
    }

    // SLCMS
    @Test()
    @Order(20)
    public void verifyFilesTabAgeInSLCMSOfPatch(IJUnitTestReporter testReport) throws InterruptedException {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToTesterAssignmentQueue(LotcheckRegion.NCL.toString());

        CommonAction.findProductInLCQueue(lotcheckLandingPage, 40, LotcheckRegion.NCL.toString(), initialCode);
        lotcheckQueuePage.clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        LotcheckReportSubmissionProductInformation productInformation = lotcheckReportSubmissionOverview.navBar().clickProductInformation();
        LotcheckReportSubmissionFiles lotcheckReportSubmissionFiles = lotcheckReportSubmissionOverview.navBar().clickFiles();
        lotcheckReportSubmissionFiles.clickCERO();
        String getFileName = lotcheckReportSubmissionFiles.getFileName();
        String fileName = getFileName.substring(21);
        lotcheckReportSubmissionFiles.clickUSK();
        String getFileNameUSk = lotcheckReportSubmissionFiles.getFileName();
        String fileNameUSk = getFileNameUSk.substring(21);
        lotcheckReportSubmissionFiles.clickPEGI();
        String getFileNamePEGI = lotcheckReportSubmissionFiles.getFileName();
        String fileNamePEGI = getFileNamePEGI.substring(21);
        lotcheckReportSubmissionFiles.clickOFLC();
        String getFileNameOFLC = lotcheckReportSubmissionFiles.getFileName();
        String fileNameOFLC = getFileNamePEGI.substring(21);
        assertAll(
                () -> assertNotEquals("非到呵钱二七问.docx", fileName),
                () -> assertNotEquals("非到呵钱二七问.docx", fileNameUSk),
                () -> assertNotEquals("非到呵钱二七问.docx", fileNamePEGI),
                () -> assertNotEquals("非到呵钱二七问.docx", fileNameOFLC)
        );
        //TODO:Exits bug G3PTDEV4NX-32493: Not display Age Rating folder in Lotcheck >Files Tab when partner perform add ratings using the Standard Method
    }
}
