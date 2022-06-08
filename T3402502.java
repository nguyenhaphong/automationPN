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
import net.nintendo.automation.ui.models.lcms.LotcheckEditAccountPreferences;
import net.nintendo.automation.ui.models.lcms.LotcheckHomePage;
import net.nintendo.automation.ui.models.lcms.LotcheckIssueTranslationTasksPage;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.ui.models.lcms.LotcheckQueuePage;
import net.nintendo.automation.ui.models.lcms.lotcheck_issue_translation.LotcheckIssueTranslationIssueDetails;
import net.nintendo.automation.ui.models.lcms.lotcheck_issue_translation.LotcheckIssueTranslationTaskDetails;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssueDetails;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssuePartnerThread;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssueThread;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionLotcheckIssues;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionRequiredApprovalIssueDetail;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionRequiredApprovalPartnerThread;
import net.nintendo.automation.ui.models.ndp.NDPCreateProductPage;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPLicensingWaiverRequestsTab;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.NDPSignInPage;
import net.nintendo.automation.ui.models.ndp.NDPViewTaskPage;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardApprovalsTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardProductInfoTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardReleasesTab;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoAgeRating;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoFeaturesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoGuidelinesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoIssuePage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoROMUpload;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoReleaseListNav;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPConfirmationDialog;
import net.nintendo.automation.ui.models.rom.ROMCreateRom;
import net.nintendo.automation.ui.models.rom.ROMLoginPage;
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
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Classname: T3402502
 * Branch:tasks/#169899/T3402502
 * Version: 1.0.0
 * Purpose:
 */


@Tag("T3402502")
//@ScenarioTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3402502 extends CommonBaseTest {
    private static Browser browser;
    private static User romUser;
    private static Browser romBrowser;
    private static User ndpUser;
    private static User slcmsUser;
    private static User testrailUser;
    private static User licUser;
    private static CommonUIComponent currentPage;

    private static TestLogger logger;
    private static String gameCode;
    private static String initialCode;
    private static String applicationId;
    private static String fileRomName;

    private static String productName;
    private static int numberUnResolveIssue;
    private static int numberResolvedIssue;
    private static int numberProductApprovalTabBeforeCancel;
    private static int numberProductApprovalTab;
    private static String nameTestCaseSubmitBug;
    private static String nameTestCaseSubmitSecondBug;
    private static boolean messageDisplay;
    private static boolean messageNotDisplay;

    private static String fileAttach = "testdemo.pdf";

    private static String defaultFilterStatus = "Pending";
    private static String textRequired_JP = "必須";
    private static String textRequired_EN = "Required";
    private static String redColor = "#DC3545";
    private static String editPublishedTitleEN = "Edit Published Title EN";
    private static String editPublishedDescriptionJP = "Edit Published Description JP";
    private static String text4001Character = "Apart from counting words and characters, our online editor can help you to improve word choice and writing style, and, optionally, help you to detect grammar mistakes and plagiarism. To check word count, simply place your cursor into the text box above and start typing. You'll see the number of characters and words increase or decrease as you type, delete, and edit them. You can also copy and paste text from another program over into the online editor above. The Auto-Save feature will make sure you won't lose any changes while editing, even if you leave the site and come back later. Tip: Bookmark this page nowKnowing the word count of a text can be important. For example, if an author has to write a minimum or maximum amount of words for an article, essay, report, story, book, paper, you name it. WordCounter will help to make sure its word count reaches a specific requirement or stays within a certain limit.In addition, WordCounter shows you the top 10 keywords and keyword densityApart from counting words and characters, our online editor can help you to improve word choice and writing style, and, optionally, help you to detect grammar mistakes and plagiarism. To check word count, simply place your cursor into the text box above and start typing. You'll see the number of characters and words increase or decrease as you type, delete, and edit them. You can also copy and paste text from another program over into the online editor above. The Auto-Save feature will make sure you won't lose any changes while editing, even if you leave the site and come back later. Tip: Bookmark this page nowKnowing the word count of a text can be important. For example, if an author has to write a minimum or maximum amount of words for an article, essay, report, story, book, paper, you name it. WordCounter will help to make sure its word count reaches a specific requirement or stays within a certain limit.In addition, WordCounter shows you the top 10 keywords and keyword densityApart from counting words and characters, our online editor can help you to improve word choice and writing style, and, optionally, help you to detect grammar mistakes and plagiarism. To check word count, simply place your cursor into the text box above and start typing. You'll see the number of characters and words increase or decrease as you type, delete, and edit them. You can also copy and paste text from another program over into the online editor above. The Auto-Save feature will make sure you won't lose any changes while editing, even if you leave the site and come back later. Tip: Bookmark this page nowKnowing the word count of a text can be important. For example, if an author has to write a minimum or maximum amount of words for an article, essay, report, story, book, paper, you name it. WordCounter will help to make sure its word count reaches a specific requirement or stays within a certain limit.In addition, WordCounter shows you the top 10 keywords and keyword densityApart from counting words and characters, our online editor can help you to improve word choice and writing style, and, optionally, help you to detect grammar mistakes and plagiarism. To check word count, simply place your cursor into the text box above and start typing. You'll see the number of characters and words increase or decrease as you type, delete, and edit them. You can also copy and paste text from another program over into the online editor above. The Auto-Save feature will make sure you won't lose any changes while editing, even if you leave the site and come back later. Tip: Bookmark this page nowKnowing the word count of a text can be important. For example, if an author has to write a minimum or maximum amount of words for an article, essay, report, story, book, paper, you name it. WordCounter will help to make sure its word count reaches a specific requirement or stays within a certain limit.In addition, WordCounter shows you the top 10 keywords and keyword densityword count ff4001";


    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_DungDTT2);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
        slcmsUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);
        testrailUser = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER);
        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);

        productName = DataGen.getRandomProductName();

        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
        NDPHomePage ndpHomePage = (NDPHomePage) currentPage;
        ndpHomePage.acceptCookie();
        NDPSignInPage signInPage = ndpHomePage.clickSignInPage();
        signInPage.signIn(ndpUser);

    }

    @AfterAll
    static void terminateBrowser() {
        //BrowserManager.closeBrowser(browser);
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
    public void goProductPage_Step1(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
    }

    @Test()
    @Order(2)
    public void goProductPage_Expect1(IJUnitTestReporter testReport) throws Exception {
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        currentPage = myProductsPage;
        assertEquals(myProductsPage.getPageTitle(), "My Products - Nintendo Developer Portal", "Check title of My Product page");
    }

    @Test()
    @Order(3)
    public void createProduct_Step2(IJUnitTestReporter testReport) throws Exception {
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        NDPCreateProductPage createProductPage = myProductsPage.createNewProduct();
        createProductPage.selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(TextConstants.third_Party_Type)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalJapanSalesRegion()
                .selectProductName(productName)
                .selectProductNameKana(TextConstants.productKana)
                .clickCreateButton();
        NDPProductDashboardPage ndpDashboardPage = myProductsPage.clickProductByName(productName);
        currentPage = ndpDashboardPage;
    }

    @Test()
    @Order(4)
    public void createProduct_Expect2(IJUnitTestReporter testReport) throws Exception {
        NDPProductDashboardPage ndpDashboardPage = (NDPProductDashboardPage) currentPage;
        currentPage = ndpDashboardPage;
        assertEquals(productName, ndpDashboardPage.getProductName(), "The user is taken to the Product Dashboard");
    }

    @Test()
    @Order(5)
    public void goReleaseAndFillData_Step3(IJUnitTestReporter testReport) throws Exception {
        NDPProductDashboardPage ndpDashboardPage = (NDPProductDashboardPage) currentPage;
        CommonAction.issueGameCode(ndpDashboardPage, 70, "NO");
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();
        applicationId = ndpProductDashboardProductInfoTab.getApplicationID();
        gameCode = ndpProductDashboardProductInfoTab.getGameCode();
        initialCode = ndpProductDashboardProductInfoTab.getInitialCode();
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();

        //create release information
        NDPReleaseInfoPage releaseInfoPage = ndpReleaseInfoPage.nav().clickReleaseInfo();
        CommonAction.selectROMSameAllRegion(releaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);
        //create Feature
        NDPReleaseInfoFeaturesPage releaseInfoFeaturesPage = releaseInfoPage.nav().clickFeatures();
        CommonAction.getFeatureOptionData(releaseInfoPage, TextConstants.allOff, TextConstants.allOff, TextConstants.allOff, false, "", false, "");
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = releaseInfoFeaturesPage.clickContinue();

        //Create GuildLine
        ndpReleaseInfoGuidelinesPage.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();
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
        ROMLoginPage romLoginPage = romBrowser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN);
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom
                .selectRequester(TextConstants.test_User)
                .selectType(TextConstants.initial_Release)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .enterProductName(productName)
                .enterApplicationId(applicationId)
                .selectPublishingRelationShip(TextConstants.third_Party_Type)
                .enterDisplayVersion("1.0.0")
                .enterReleaseVersion("00")
                .selectCERORating("CERO: A")
                .clickOnCreateButton()
                .waitIsLoaded();
        fileRomName = romCreateRom.getRomName();
        System.out.println("ROM File:" + fileRomName);
        romCreateRom.clickOnROMFileOnROMTable(applicationId);
        WaitUtils.idle(8000);
        romCreateRom.clickOnUserNameMenu().clickOnLogoutButton();
        BrowserManager.closeBrowser(romBrowser);

        //Upload ROM
        NDPReleaseInfoReleaseListNav releaseListNav = browser.getPage(NDPReleaseInfoReleaseListNav.class, testReport);
        currentPage = releaseListNav;
    }

    @Test()
    @Order(6)
    public void createWaiverRequirement_Step4(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoReleaseListNav releaseListNav = (NDPReleaseInfoReleaseListNav) currentPage;
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = releaseListNav.clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
        releaseListNav.clickReleaseInfo();
        releaseListNav.clickFeatures();
        releaseListNav.clickGuidelines();
        releaseListNav.clickAgeRating();
        NDPReleaseInfoIssuePage issuePage = releaseListNav.clickIssues();
        currentPage = issuePage;
    }

    @Test()
    @Order(7)
    public void gotoIssuePageAndSelectRequiredApprovals_Step5(IJUnitTestReporter testReport) throws Exception {
        //Issues
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        numberUnResolveIssue = issuePage.getNumberOfCurrentUnresolvedRequiredApprovals();
    }

    @Test()
    @Order(8)
    public void submitWaiverRequest_Step6(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoIssuePage ndpReleaseInfoIssuePage = (NDPReleaseInfoIssuePage) currentPage;
        CommonAction.enterReasonForRequest(ndpReleaseInfoIssuePage);
        ndpReleaseInfoIssuePage.nav().clickOnUserNameMenu().clickOnSignOutButton();
    }

    @Test()
    @Order(9)
    public void approveWaiverWithLicensing_Step7(IJUnitTestReporter testReport) throws Exception {
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, gameCode);
        WaitUtils.idle(5000);
        NDPViewTaskPage viewTaskPage = browser.getPage(NDPViewTaskPage.class, testReport);
        WaitUtils.idle(5000);
        viewTaskPage.clickUserMenuDropdownList().clickSignOutButton();
    }

    @Test()
    @Order(10)
    public void goProductDashboardAndSelectApprovalsTab_Step8(IJUnitTestReporter testReport) throws Exception {
        NDPHomePage ndpHomePage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
        NDPSignInPage signInPage = ndpHomePage.clickSignInPage();
        NDPDevelopmentHome developmentHome = signInPage.signIn(ndpUser);
        NDPMyProductsPage myProductsPage = developmentHome.clickMyProducts();
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardApprovalsTab dashboardApprovalsTab = ndpProductDashboardPage.clickApprovalsTab();
        currentPage = dashboardApprovalsTab;
    }

    @Test()
    @Order(11)
    public void verifyDisplayAllApprovals_Expect8(IJUnitTestReporter testReport) throws Exception {
        NDPProductDashboardApprovalsTab dashboardApprovalsTab = (NDPProductDashboardApprovalsTab) currentPage;
        numberProductApprovalTab = dashboardApprovalsTab.getNumberItemInApprovalTab();
        numberProductApprovalTabBeforeCancel = numberProductApprovalTab;
        assertTrue(numberProductApprovalTab > 0, "The user can see all Approvals for the product.");
    }

    @Test()
    @Order(12)
    public void goBackDashboard_Step9(IJUnitTestReporter testReport) throws Exception {
        NDPProductDashboardApprovalsTab dashboardApprovalsTab = (NDPProductDashboardApprovalsTab) currentPage;
        NDPProductDashboardReleasesTab dashboardReleasesTab = dashboardApprovalsTab.clickReleaseTab();
        NDPReleaseInfoPage releaseInfoPage = dashboardReleasesTab.selectInitialRelease();
        currentPage = releaseInfoPage;
    }

    @Test()
    @Order(13)
    public void submitToLotCheck_Step10(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        //submit product
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.enterDeveloperComment).clickSubmit();
        WaitUtils.idle(20000);
    }

    @Test()
    @Order(14)
    public void cancelSubmission_Step11(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        NDPConfirmationDialog ndpConfirmationDialog = ndpReleaseInfoPage.nav().waitCancelSubmissionButtonEnable().clickCancelSubmitButton();
        //NDPConfirmationDialog ndpConfirmationDialog = ndpReleaseInfoPage.nav().clickCancelSubmitButton();
        ndpConfirmationDialog.clickOKInConfirm();
    }

    @Test()
    @Order(15)
    public void selectIssuesOnReleaseDashboard_Step12(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        // Goto Issue on Release Dashboard
        NDPReleaseInfoIssuePage ndpReleaseInfoIssuePage = ndpReleaseInfoPage.nav().clickIssues();
        ndpReleaseInfoIssuePage.openRequiredApprovalTab();
        numberUnResolveIssue = ndpReleaseInfoIssuePage.getNumberOfCurrentUnresolvedRequiredApprovals();
    }

    @Test()
    @Order(16)
    public void selectIssuesOnReleaseDashboard_Expect12(IJUnitTestReporter testReport) throws Exception {
        assertTrue(numberUnResolveIssue == 0, "All the approved waivers show on the Required Approvals tab.");
    }

    @Test()
    @Order(17)
    public void goApprovalTabOfProductDashboard_Step13(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardApprovalsTab ndpProductDashboardApprovalsTab = ndpProductDashboardPage.clickApprovalsTab();
        numberProductApprovalTab = ndpProductDashboardApprovalsTab.getNumberItemInApprovalTab();
    }

    @Test()
    @Order(18)
    public void goApprovalTabOfProductDashboard_Expect13(IJUnitTestReporter testReport) throws Exception {
        assertEquals(numberProductApprovalTab, numberProductApprovalTabBeforeCancel, "All the same Approvals as the Required Approvals tab are showing.");
    }

    @Test()
    @Order(19)
    public void goReleaseDashboardAndCreateWaiverRequirement_Step14_15(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        NDPReleaseInfoReleaseListNav releaseListNav = ndpReleaseInfoPage.nav();
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = releaseListNav.clickGuidelines();
        ndpReleaseInfoGuidelinesPage.selectCannotConform0011LimitsOnFrequencyAndAmountOfWriting();
        ndpReleaseInfoGuidelinesPage.selectCompliesWithAllOfTheAboveGuidelines().clickOnSaveButton();
        WaitUtils.idle(2000);
    }

    @Test()
    @Order(20)
    public void repeatStep12AndStep13_Step16(IJUnitTestReporter testReport) throws Exception {
        // Repeat step 12 & Step 13
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardApprovalsTab ndpProductDashboardApprovalsTab = ndpProductDashboardPage.clickApprovalsTab();
        numberProductApprovalTab = ndpProductDashboardApprovalsTab.getNumberItemInApprovalTab();
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        // Goto Issue on Release Dashboard
        NDPReleaseInfoIssuePage ndpReleaseInfoIssuePage = ndpReleaseInfoPage.nav().clickIssues();
        ndpReleaseInfoIssuePage.openRequiredApprovalTab();
        numberUnResolveIssue = ndpReleaseInfoIssuePage.getNumberOfCurrentUnresolvedRequiredApprovals();
    }

    @Test()
    @Order(21)
    public void repeatStep12AndStep13_Expect16(IJUnitTestReporter testReport) throws Exception {
        assertTrue(numberUnResolveIssue > 0, "All Approvals are showing on both tabs");
        assertTrue(numberProductApprovalTab == numberProductApprovalTabBeforeCancel, "All Approvals are showing on both tabs");
    }

    @Test()
    @Order(22)
    public void completeSubmissionProcess_Step17(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();
        applicationId = ndpProductDashboardProductInfoTab.getApplicationID();
        gameCode = ndpProductDashboardProductInfoTab.getGameCode();
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        //create release information
        NDPReleaseInfoPage releaseInfoPage = ndpReleaseInfoPage.nav().clickReleaseInfo();
        releaseInfoPage.setExpectedLotcheckSubmissionDate().save();
        CommonAction.selectROMSameAllRegion(releaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);
        //create Feature
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = releaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
        //Create GuildLine
        //Create Age Rating

        NDPReleaseInfoIssuePage issuePage = releaseInfoPage.nav().clickIssues();
        CommonAction.enterReasonForRequest(issuePage);
        releaseInfoPage.nav().clickOnUserNameMenu().clickOnSignOutButton();

        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, gameCode);
        WaitUtils.idle(3000);
        NDPViewTaskPage viewTaskPage = browser.getPage(NDPViewTaskPage.class, testReport);
        viewTaskPage.clickUserMenuDropdownList().clickSignOutButton();
    }

    // Repeat 3rd times
    @Test()
    @Order(23)
    public void repeatStep12AndStep13_Step18(IJUnitTestReporter testReport) throws Exception {
        // Repeat step 12 & Step 13
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(ndpUser);
        NDPDevelopmentHome ndpDevelopmentHome = (NDPDevelopmentHome) currentPage;
        NDPMyProductsPage myProductsPage = ndpDevelopmentHome.clickMyProducts();
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardApprovalsTab ndpProductDashboardApprovalsTab = ndpProductDashboardPage.clickApprovalsTab();
        numberProductApprovalTab = ndpProductDashboardApprovalsTab.getNumberItemInApprovalTab();
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        // Goto Issue on Release Dashboard
        NDPReleaseInfoIssuePage ndpReleaseInfoIssuePage = ndpReleaseInfoPage.nav().clickIssues();
        ndpReleaseInfoIssuePage.openRequiredApprovalTab();
        numberResolvedIssue = ndpReleaseInfoIssuePage.getNumberOfCurrentProductResolutionArchiveIssues();
        numberUnResolveIssue = ndpReleaseInfoIssuePage.getNumberOfCurrentUnresolvedRequiredApprovals();
        NDPReleaseInfoReleaseListNav ndpReleaseInfoReleaseListNav = ndpReleaseInfoPage.nav();
        currentPage = ndpReleaseInfoReleaseListNav;
    }

    @Test()
    @Order(24)
    public void repeatStep12AndStep13_Expect18(IJUnitTestReporter testReport) throws Exception {
        assertTrue(numberUnResolveIssue == 0, "All Approvals are showing on both tabs");
        assertTrue(numberProductApprovalTab > numberProductApprovalTabBeforeCancel, "All Approvals are showing on both tabs");
    }

    @Test()
    @Order(25)
    public void submitToLotCheck_Step19(IJUnitTestReporter testReport) throws Exception {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        //submit product
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.enterDeveloperComment).clickSubmit();
        WaitUtils.idle(3000);
    }


    @Test()
    @Order(26)
    public void selectTwoLanguageDisplay_Step20(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage landingPage = hyruleAuthPage.loginWithOvdLink(TextConstants.lc_Translator_NCL, TextConstants.lc_Translator_NCL_Password);
        LotcheckEditAccountPreferences editAccountPreferences = landingPage.clickOnUserNameMenu().clickMyAccount();
        String currentLanguage = editAccountPreferences.getCurrentLanguage();
        if (!currentLanguage.equalsIgnoreCase(TextConstants.english)) {
            editAccountPreferences.selectLanguage(TextConstants.english).clickSaveButton();
        } else {
            editAccountPreferences.clickCancelButton();
        }
        LotcheckReportSubmissionOverview submissionOverview = landingPage.searchProduct(productName).selectSubmission(gameCode, "00", "01");
        LotcheckReportSubmissionRequiredApprovalIssueDetail requiredApprovalIssues = submissionOverview.navBar().clickRequiredApprovalTab().clickMenu().selectViewIssueDetails();
        requiredApprovalIssues.selectTwoLanguageDisplayed();
        currentPage = requiredApprovalIssues;
    }

    @Test()
    @Order(27)
    public void attachFileAndReplyToPartner_Step21(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionRequiredApprovalIssueDetail requiredApprovalIssues = (LotcheckReportSubmissionRequiredApprovalIssueDetail) currentPage;
        LotcheckReportSubmissionRequiredApprovalPartnerThread requiredApprovalPartnerThread = requiredApprovalIssues.clickPartnerThreadTab();
        requiredApprovalPartnerThread.clickAttachFile().selectFileInPC("testdemo.pdf");
        WaitUtils.idle(10000);
        requiredApprovalPartnerThread.clickReplyToPartner();
        WaitUtils.idle(15000);
        currentPage = requiredApprovalPartnerThread;
    }

    @Test()
    @Order(28)
    public void clickEditPostLink_Step22(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionRequiredApprovalPartnerThread requiredApprovalPartnerThread = (LotcheckReportSubmissionRequiredApprovalPartnerThread) currentPage;
        requiredApprovalPartnerThread.clickEditPostEN();
        WaitUtils.idle(5000);
        requiredApprovalPartnerThread.enterMessageField4001Character(text4001Character);
    }

    @Test()
    @Order(29)
    public void confirmErrorMessage_Step23(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionRequiredApprovalPartnerThread requiredApprovalPartnerThread = (LotcheckReportSubmissionRequiredApprovalPartnerThread) currentPage;
        currentPage = requiredApprovalPartnerThread;
    }

    @Test()
    @Order(30)
    public void verifyErrorMessage_Expect23(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionRequiredApprovalPartnerThread requiredApprovalPartnerThread = (LotcheckReportSubmissionRequiredApprovalPartnerThread) currentPage;
        requiredApprovalPartnerThread.clickCloseEditPost();
        String errorMessage = requiredApprovalPartnerThread.getTextErrorMessage();
        requiredApprovalPartnerThread.navLanding().clickOnUserNameMenu().clickOnSignoutButton();
        assertEquals("Please enter no more than 4000 characters", errorMessage, "Verify error message when input more than 4000 character");
    }

    @Test()
    @Order(31)
    public void submitBugTranslationNeededChecked_Step24(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailLoginPage.signInTestRail(testrailUser).navTopTestrail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();

        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(gameCode)
                .selectStatusUntested().selectStatusPassed();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun();
        nameTestCaseSubmitBug = testRailManagerDashboardPage.getNameFistTestCaseSubmitBug();
        TestRailSubmitBugDialog submitBugDialog = testRailManagerDashboardPage.clickOnTheFirstTestcase().clickSubmitBug();
        submitBugDialog.enterDescription(TextConstants.enterDescription).checkNeedsTranslation().clickSubmit();
        String messageSuccess = submitBugDialog.getMessageSuccessfully();
        submitBugDialog.clickOk().clickClose().logOut();
        currentPage = testRailLoginPage;
        assertEquals("Your Issue has been submitted successfully.", messageSuccess, "Verify submit bug successfully");
    }


    @Test()
    @Order(32)
    public void loginLCTranslator_Step25(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage landingPage = hyruleAuthPage.loginWithOvdLink(TextConstants.lc_Translator_NCL, TextConstants.lc_Translator_NCL_Password);
        LotcheckEditAccountPreferences editAccountPreferences = landingPage.clickOnUserNameMenu().clickMyAccount();
        String currentLanguage = editAccountPreferences.getCurrentLanguage();
        if (!currentLanguage.equalsIgnoreCase(TextConstants.english)) {
            editAccountPreferences.selectLanguage(TextConstants.english).clickSaveButton();
        } else {
            editAccountPreferences.clickCancelButton();
        }
        currentPage = landingPage;
    }

    @Test()
    @Order(33)
    public void navigateTranslationTask_Step26(IJUnitTestReporter testReport) throws Exception {
        LotcheckLandingPage landingPage = (LotcheckLandingPage) currentPage;
        LotcheckIssueTranslationTasksPage translationTasksPage = landingPage.navigationToLotcheckIssueTranslationTasks();
        currentPage = translationTasksPage;
    }

    @Test()
    @Order(34)
    public void verifyDefaultValue_Expect26(IJUnitTestReporter testReport) throws Exception {
        LotcheckIssueTranslationTasksPage translationTasksPage = (LotcheckIssueTranslationTasksPage) currentPage;
        String defaultOptionForStatusFilter = translationTasksPage.defaultOptionForStatusFilter();
        currentPage = translationTasksPage;
        int number = translationTasksPage.getNumberTranslationTasks();
        for (int i = 0; i < number; i++) {
            String status = translationTasksPage.getValueStatusTranslationTasks(i);
            assertEquals(defaultFilterStatus, status, "Verify Translation task with Status is 'Pending'");
        }
        if (number >= 50) {
            assertEquals(50, number, "Translation task will display up to 50 Translation Tasks per page");
        }
        assertEquals("Status : Pending", defaultOptionForStatusFilter, " Verify the default option for the Status filter is \"Pending\"");
    }


    @Test()
    @Order(35)
    public void clickResultLineOfProduct_step27(IJUnitTestReporter testReport) throws Exception {
        LotcheckIssueTranslationTasksPage translationTasksPage = (LotcheckIssueTranslationTasksPage) currentPage;
        LotcheckIssueTranslationTaskDetails issueTranslationTaskDetails = translationTasksPage.clickViewTaskDetailsProd_addLoop(initialCode);
        currentPage = issueTranslationTaskDetails;
    }

    @Test()
    @Order(36)
    public void verifyOpenLotcheckIssueTranslationScreen_expect27(IJUnitTestReporter testReport) throws Exception {
        LotcheckIssueTranslationTaskDetails issueTranslationTaskDetails = (LotcheckIssueTranslationTaskDetails) currentPage;
        String titleScreen = issueTranslationTaskDetails.getTitleScreen();
        currentPage = issueTranslationTaskDetails;
        assertEquals("Lotcheck Issue Translation", titleScreen, "Verify open open lotcheck issue translation screen ");
    }


    @Test()
    @Order(37)
    public void assignToSelf_Step28(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationTaskDetails issueTranslationTaskDetails = (LotcheckIssueTranslationTaskDetails) currentPage;
        issueTranslationTaskDetails.clickMenuOverview();
        issueTranslationTaskDetails.clickAssignTask()
                .selectAssignTo(TextConstants.current_Translator_NCL).clickSave();
        WaitUtils.idle(2000);
        currentPage = issueTranslationTaskDetails;

    }


    @Test()
    @Order(38)
    public void verifyDisplaySuccessMessageAndDisappearAfter5s_expect28(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationTaskDetails issueTranslationTaskDetails = (LotcheckIssueTranslationTaskDetails) currentPage;
        currentPage = issueTranslationTaskDetails;
        assertTrue(issueTranslationTaskDetails.displaySuccessMessage(), "Verify display message assigned success ");
        WaitUtils.idle(10000);
        assertFalse(issueTranslationTaskDetails.displaySuccessMessage(), "Verify message assign success disappear in 5 seconds.");
    }


    @Test()
    @Order(39)
    public void checkPrivateTitleAndPrivateDescription_Step29(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationTaskDetails issueTranslationTaskDetails = (LotcheckIssueTranslationTaskDetails) currentPage;
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = issueTranslationTaskDetails.clickIssueDetail();
        currentPage = issueTranslationIssueDetails;
    }

    @Test()
    @Order(40)
    public void checkPrivateTitleAndPrivateDescriptionAndHighlightedIsRed_Expect29(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = (LotcheckIssueTranslationIssueDetails) currentPage;
        currentPage = issueTranslationIssueDetails;
        assertAll(() -> assertTrue(issueTranslationIssueDetails.getPrivateTitleEN().contains(nameTestCaseSubmitBug), "Verify title EN"),
                () -> assertTrue(issueTranslationIssueDetails.getPrivateTitleJP().contains(nameTestCaseSubmitBug), "Verify title EN"),
                () -> assertEquals(textRequired_EN, issueTranslationIssueDetails.getTitleRequired_EN(), "Verify text Title Required EN"),
                () -> assertEquals(textRequired_EN, issueTranslationIssueDetails.getTitleRequired_JP(), "Verify text Required JP"),
                () -> assertEquals(TextConstants.enterDescription, issueTranslationIssueDetails.getPrivateDescriptionEN(), "Verify Description EN"),
                () -> assertEquals(TextConstants.enterDescription, issueTranslationIssueDetails.getPrivateDescriptionJP(), "Verify Description JP"),
                () -> assertEquals(textRequired_EN, issueTranslationIssueDetails.getDescriptionTextRequired_EN(), "Verify text Description Required EN"),
                () -> assertEquals(textRequired_EN, issueTranslationIssueDetails.getDescriptionTextRequired_JP(), "Verify textDescription  Required JP"),
                () -> assertEquals(redColor, issueTranslationIssueDetails.getTitleTextRequiredColorHighlighted_EN(), "Verify color text Title  Required EN"),
                () -> assertEquals(redColor, issueTranslationIssueDetails.getTitleTextRequiredColorHighlighted_JP(), "Verify color text Title Required JP"),
                () -> assertEquals(redColor, issueTranslationIssueDetails.getDescriptionTextRequiredColorHighlighted_EN(), "Verify color text Description  Required EN"),
                () -> assertEquals(redColor, issueTranslationIssueDetails.getDescriptionTextRequiredColorHighlighted_JP(), "Verify color text Description  Required JP")
        );
    }

    @Test()
    @Order(41)
    public void changeLanguageToJP_step30(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = (LotcheckIssueTranslationIssueDetails) currentPage;
        LotcheckEditAccountPreferences editAccountPreferences = issueTranslationIssueDetails.navLandingPage().clickOnUserNameMenu().clickMyAccount();
        editAccountPreferences.selectLanguage(TextConstants.japanese).clickSaveButton();
        WaitUtils.idle(10000);
        editAccountPreferences.waitAssignTaskSuccess();
        currentPage = issueTranslationIssueDetails;
    }

    @Test()
    @Order(42)
    public void clickEditPost_step31(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = (LotcheckIssueTranslationIssueDetails) currentPage;
        issueTranslationIssueDetails.clickEditPost();
        currentPage = issueTranslationIssueDetails;
    }

    @Test()
    @Order(43)
    public void checkPrivateTitleAndPrivateDescriptionAndHighlightedIsRed_step32(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = (LotcheckIssueTranslationIssueDetails) currentPage;
        currentPage = issueTranslationIssueDetails;
    }

    @Test()
    @Order(44)
    public void checkPrivateTitleAndPrivateDescriptionAndHighlightedIsRed_expect32(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = (LotcheckIssueTranslationIssueDetails) currentPage;
        currentPage = issueTranslationIssueDetails;
        assertAll(() -> assertTrue(issueTranslationIssueDetails.editPostTitle_EN().contains(nameTestCaseSubmitBug), "Verify title EN"),
                () -> assertTrue(issueTranslationIssueDetails.editPostTitle_JP().contains(nameTestCaseSubmitBug), "Verify title EN"),
                () -> assertEquals(textRequired_JP, issueTranslationIssueDetails.editPostTitleTextRequired_EN(), "Verify text Title Required EN"),
                () -> assertEquals(textRequired_JP, issueTranslationIssueDetails.editPostTitleTextRequired_JP(), "Verify text Required JP"),
                () -> assertEquals(TextConstants.enterDescription, issueTranslationIssueDetails.editPostDescription_EN(), "Verify Description EN"),
                () -> assertEquals(TextConstants.enterDescription, issueTranslationIssueDetails.editPostDescription_JP(), "Verify Description JP"),
                () -> assertEquals(textRequired_JP, issueTranslationIssueDetails.editPostDescriptionTextRequired_EN(), "Verify text Description Required EN"),
                () -> assertEquals(textRequired_JP, issueTranslationIssueDetails.editPostDescriptionTextRequired_JP(), "Verify textDescription  Required JP"),
                () -> assertEquals(redColor, issueTranslationIssueDetails.getTitleTextRequiredColorHighlighted_EN(), "Verify color text Title  Required EN"),
                () -> assertEquals(redColor, issueTranslationIssueDetails.getTitleTextRequiredColorHighlighted_JP(), "Verify color text Title Required JP"),
                () -> assertEquals(redColor, issueTranslationIssueDetails.getDescriptionTextRequiredColorHighlighted_EN(), "Verify color text Description  Required EN"),
                () -> assertEquals(redColor, issueTranslationIssueDetails.getDescriptionTextRequiredColorHighlighted_JP(), "Verify color text Description  Required JP")
        );

    }

    @Test()
    @Order(45)
    public void pressingTranslationCompleteButton_step33(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = (LotcheckIssueTranslationIssueDetails) currentPage;
        issueTranslationIssueDetails.clickTranslationCompleteButton().enterCommentTranslationComplete(TextConstants.commentTranslationComplete);
        currentPage = issueTranslationIssueDetails;
    }

    @Test()
    @Order(46)
    public void clickSaveTranslationCompletePopup_step34(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = (LotcheckIssueTranslationIssueDetails) currentPage;
        issueTranslationIssueDetails.clickSaveComment();
        currentPage = issueTranslationIssueDetails;

    }

    @Test()
    @Order(47)
    public void verifyMessageSuccess_expect34(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = (LotcheckIssueTranslationIssueDetails) currentPage;
        currentPage = issueTranslationIssueDetails;
        assertTrue(issueTranslationIssueDetails.displaySuccessMessage(), "Verify display message assigned success ");
        WaitUtils.idle(6000);
        issueTranslationIssueDetails.closeWarningMessage();
        assertFalse(issueTranslationIssueDetails.displaySuccessMessage(), "Verify message assign success disappear in 5 seconds.");

    }


    @Test()
    @Order(48)
    public void checkDisplayPrivateTitleAndPrivateDescription_step35(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = (LotcheckIssueTranslationIssueDetails) currentPage;
        //issueTranslationIssueDetails.closeAlert();
        issueTranslationIssueDetails.closeWarningMessage();
        WaitUtils.idle(3000);
    }

    @Test()
    @Order(49)
    public void verifyHighlightIsRemoved_expect35(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = (LotcheckIssueTranslationIssueDetails) currentPage;
        boolean requiredHighlightIsRemovedTitleEN = issueTranslationIssueDetails.requiredHighlightIsRemovedTitleEN();
        boolean requiredHighlightIsRemovedTitleJP = issueTranslationIssueDetails.requiredHighlightIsRemovedTitleJP();
        boolean requiredHighlightIsRemovedDescriptionEN = issueTranslationIssueDetails.requiredHighlightIsRemovedDescriptionEN();
        boolean requiredHighlightIsRemovedDescriptionJP = issueTranslationIssueDetails.requiredHighlightIsRemovedDescriptionJP();
        LotcheckHomePage lotcheckHomePage = issueTranslationIssueDetails.navLandingPage().clickOnUserNameMenu().clickOnSignoutButton();
        currentPage = lotcheckHomePage;
        assertAll(() -> assertFalse(requiredHighlightIsRemovedTitleEN, "Verify title Required EN"),
                () -> assertFalse(requiredHighlightIsRemovedTitleJP, "Verify title Required JP"),
                () -> assertFalse(requiredHighlightIsRemovedDescriptionEN, "Verify description Required EN"),
                () -> assertFalse(requiredHighlightIsRemovedDescriptionJP, "Verify description Required JP")
        );
    }

    @Test()
    @Order(50)
    public void judgmentCurrentSubmissionNG_Step36(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailLoginPage.signInTestRail(testrailUser).navTopTestRail().navigateToManagerDashboard();
        CommonAction.setStatusOnTestrailIncludeFailed(testRailManagerDashboardPage, gameCode, TextConstants.failedStatus, TextConstants.percentCompleted, TextConstants.NG);
        WaitUtils.idle(15000);
        testRailManagerDashboardPage.logOut();

        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
        LotcheckQueuePage nclLotCheckQueue = lotcheckLandingPage.clickJudgment(LotcheckRegion.NCL);
        CommonAction.approvalJudgmentStatus(nclLotCheckQueue, initialCode, 300, TextConstants.NG, true, "1");
        currentPage = lotcheckHomePage;
    }

    @Test()
    @Order(51)
    public void submitNextSubmission_Step37(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN);
        NDPDevelopmentHome developmentHome = (NDPDevelopmentHome) currentPage;
        NDPMyProductsPage myProductsPage = developmentHome.clickMyProducts();
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = myProductsPage.clickProductByGameCode(gameCode).clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        ndpReleaseInfoPage.setExpectedLotcheckSubmissionDate().save()
                .nav().clickGuidelines().selectCompliesWithAllOfTheAboveGuidelines().clickOnSaveButton();
        ndpReleaseInfoPage.nav().clickROMUpload().closeRequiredStepModal().selectRomFileButton().selectFileInPC(fileRomName).clickUpload()
                .clickUploadNow().waitForUploadComplete(TextConstants.romUpload);
        ndpReleaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoPage.nav().clickTestScenarios();
        ndpReleaseInfoPage.nav().clickAgeRating();
        ndpReleaseInfoPage.nav().clickReleaseInfo();
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.enterDeveloperComment).clickSubmit();
        WaitUtils.idle(3000);
        currentPage = ndpReleaseInfoPage;
    }

    @Test()
    @Order(52)
    public void submitBugTranslationNeeded_Step38(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailLoginPage.signInTestRail(testrailUser).navTopTestRail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(gameCode)
                .selectStatusUntested().selectStatusPassed().selectStatusFailed();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun();
        nameTestCaseSubmitBug = testRailManagerDashboardPage.getNameFistTestCaseSubmitBug();
        TestRailSubmitBugDialog submitBugDialog = testRailManagerDashboardPage.clickOnTheFirstTestcase().clickSubmitBug();
        submitBugDialog.enterDescription(TextConstants.enterDescription).checkNeedsTranslation().clickSubmit();
        String messageSuccess = submitBugDialog.getMessageSuccessfully();
        submitBugDialog.clickOk().clickClose().logOut();
        currentPage = testRailLoginPage;
        assertEquals("Your Issue has been submitted successfully.", messageSuccess, "Verify submit bug successfully");

    }

    @Test()
    @Order(53)
    public void lcAdminPublishBugToPartner_Step39(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
        LotcheckReportSubmissionOverview submissionOverview = lotcheckLandingPage.searchProduct(productName).selectSubmission(gameCode, "00", "02");
        LotcheckReportSubmissionLotcheckIssues lotcheckIssues = submissionOverview.navBar().clickLotcheckIssues();
        LotcheckReportSubmissionLotcheckIssueDetails submissionLotcheckIssueDetails = lotcheckIssues.clickMenuJS().selectViewIssueDetails();
        submissionLotcheckIssueDetails.clickMenu()
                .selectPublishToPublic()
                .selectLciType("Bug")
                .clickPublish();
        String titleIssue = submissionLotcheckIssueDetails.getTitleOfIssue();
        String partnerThreadStatus = submissionLotcheckIssueDetails.getPartnerThreadStatus();
        WaitUtils.idle(3000);
        submissionLotcheckIssueDetails.logOut();
        assertEquals("NEEDS RESPONSE", partnerThreadStatus, "Verify partner Thread Status after publish");
    }

    @Test()
    @Order(54)
    public void lcTranslatorAssignToSelf_Step40(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage landingPage = hyruleAuthPage.loginWithOvdLink("lc_translator_ncl", "AccuseAlthoughShutGuard7");
        LotcheckIssueTranslationTasksPage translationTasksPage = landingPage.navigationToLotcheckIssueTranslationTasks();
        LotcheckIssueTranslationTaskDetails issueTranslationTaskDetails = translationTasksPage.clickViewTaskDetailsProd_addLoop(initialCode);
        issueTranslationTaskDetails.clickMenuOverview().clickAssignTask()
                .selectAssignTo(TextConstants.current_Translator_NCL).clickSave();
        currentPage = issueTranslationTaskDetails;
    }

    @Test()
    @Order(55)
    public void verifyMessageSuccess_expect40(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationTaskDetails issueTranslationTaskDetails = (LotcheckIssueTranslationTaskDetails) currentPage;
        currentPage = issueTranslationTaskDetails;
        assertTrue(issueTranslationTaskDetails.displaySuccessMessage(), "Verify display message assigned success ");
        WaitUtils.idle(6000);
        assertFalse(issueTranslationTaskDetails.displaySuccessMessage(), "Verify message assign success disappear in 5 seconds.");
    }

    @Test()
    @Order(56)
    public void checkDisplayPublishedTitleAndPublishedDescription_step41(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationTaskDetails issueTranslationTaskDetails = (LotcheckIssueTranslationTaskDetails) currentPage;
        currentPage = issueTranslationTaskDetails;
    }

    @Test()
    @Order(57)
    public void checkDisplayPublishedTitleAndPublishedDescription_expect41(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationTaskDetails issueTranslationTaskDetails = (LotcheckIssueTranslationTaskDetails) currentPage;
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = issueTranslationTaskDetails.clickIssueDetail();
        currentPage = issueTranslationIssueDetails;

        assertAll(() -> assertTrue(issueTranslationIssueDetails.getPublishedTitle_EN().contains(nameTestCaseSubmitBug), "Verify title EN"),
                () -> assertTrue(issueTranslationIssueDetails.publishedTitle_JP().contains(nameTestCaseSubmitBug), "Verify title EN"),
                () -> assertEquals(textRequired_JP, issueTranslationIssueDetails.publishedTitleTextRequired_EN(), "Verify text Title Required EN"),
                () -> assertEquals(textRequired_JP, issueTranslationIssueDetails.publishedTitleTextRequired_JP(), "Verify text Required JP"),
                () -> assertEquals(TextConstants.enterDescription, issueTranslationIssueDetails.publishedDescription_EN(), "Verify Description EN"),
                () -> assertEquals(TextConstants.enterDescription, issueTranslationIssueDetails.getPublishedDescription_JP(), "Verify Description JP"),
                () -> assertEquals(textRequired_JP, issueTranslationIssueDetails.publishedDescriptionTextRequired_EN(), "Verify text Description Required EN"),
                () -> assertEquals(textRequired_JP, issueTranslationIssueDetails.publishedDescriptionTextRequired_JP(), "Verify textDescription  Required JP"),
                () -> assertEquals(redColor, issueTranslationIssueDetails.getPublishedTitleTextRequiredColorHighlighted_EN(), "Verify color text Title  Required EN"),
                () -> assertEquals(redColor, issueTranslationIssueDetails.getPublishedTitleTextRequiredColorHighlighted_JP(), "Verify color text Title Required JP"),
                () -> assertEquals(redColor, issueTranslationIssueDetails.getPublishedDescriptionTextRequiredColorHighlighted_EN(), "Verify color text Description  Required EN"),
                () -> assertEquals(redColor, issueTranslationIssueDetails.getPublishedDescriptionTextRequiredColorHighlighted_JP(), "Verify color text Description  Required JP")
        );
    }

    @Test()
    @Order(58)
    public void changeLanguageToEN_step42(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = (LotcheckIssueTranslationIssueDetails) currentPage;
        LotcheckEditAccountPreferences editAccountPreferences = issueTranslationIssueDetails.navLandingPage().clickOnUserNameMenu().clickMyAccount();
        String currentLanguage = editAccountPreferences.getCurrentLanguage();
        if (!currentLanguage.equalsIgnoreCase(TextConstants.english)) {
            editAccountPreferences.selectLanguage(TextConstants.english).clickSaveButton();
        } else {
            editAccountPreferences.clickCancelButton();
        }
        WaitUtils.idle(10000);
        editAccountPreferences.waitAssignTaskSuccess();
        currentPage = issueTranslationIssueDetails;
    }

    @Test()
    @Order(59)
    public void checkDisplayPublishedTitleAndPublishedDescription_step43(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = (LotcheckIssueTranslationIssueDetails) currentPage;
        currentPage = issueTranslationIssueDetails;
    }


    @Test()
    @Order(60)
    public void checkDisplayPublishedTitleAndPublishedDescription_expect43(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = (LotcheckIssueTranslationIssueDetails) currentPage;
        currentPage = issueTranslationIssueDetails;

        assertAll(() -> assertTrue(issueTranslationIssueDetails.getPublishedTitle_EN().contains(nameTestCaseSubmitBug), "Verify title EN"),
                () -> assertTrue(issueTranslationIssueDetails.publishedTitle_JP().contains(nameTestCaseSubmitBug), "Verify title EN"),
                () -> assertEquals(textRequired_EN, issueTranslationIssueDetails.publishedTitleTextRequired_EN(), "Verify text Title Required EN"),
                () -> assertEquals(textRequired_EN, issueTranslationIssueDetails.publishedTitleTextRequired_JP(), "Verify text Required JP"),
                () -> assertEquals(TextConstants.enterDescription, issueTranslationIssueDetails.publishedDescription_EN(), "Verify Description EN"),
                () -> assertEquals(TextConstants.enterDescription, issueTranslationIssueDetails.getPublishedDescription_JP(), "Verify Description JP"),
                () -> assertEquals(textRequired_EN, issueTranslationIssueDetails.publishedDescriptionTextRequired_EN(), "Verify text Description Required EN"),
                () -> assertEquals(textRequired_EN, issueTranslationIssueDetails.publishedDescriptionTextRequired_JP(), "Verify textDescription  Required JP"),
                () -> assertEquals(redColor, issueTranslationIssueDetails.getPublishedTitleTextRequiredColorHighlighted_EN(), "Verify color text Title  Required EN"),
                () -> assertEquals(redColor, issueTranslationIssueDetails.getPublishedTitleTextRequiredColorHighlighted_JP(), "Verify color text Title Required JP"),
                () -> assertEquals(redColor, issueTranslationIssueDetails.getPublishedDescriptionTextRequiredColorHighlighted_EN(), "Verify color text Description  Required EN"),
                () -> assertEquals(redColor, issueTranslationIssueDetails.getPublishedDescriptionTextRequiredColorHighlighted_JP(), "Verify color text Description  Required JP")
        );
    }

    @Test()
    @Order(61)
    public void clickEditPost_step44(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = (LotcheckIssueTranslationIssueDetails) currentPage;
        issueTranslationIssueDetails.clickEditPost();
        currentPage = issueTranslationIssueDetails;
    }

    @Test()
    @Order(62)
    public void editPublishedTitleEN_step45(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = (LotcheckIssueTranslationIssueDetails) currentPage;
        issueTranslationIssueDetails.enterPublishedTitleEN(editPublishedTitleEN);
        currentPage = issueTranslationIssueDetails;
    }

    @Test()
    @Order(63)
    public void editPublishedDescriptionJP_step46(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = (LotcheckIssueTranslationIssueDetails) currentPage;
        issueTranslationIssueDetails.enterPublishedDescriptionJP(editPublishedDescriptionJP);
        currentPage = issueTranslationIssueDetails;
    }

    @Test()
    @Order(64)
    public void clickSaveEditButton_step47(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = (LotcheckIssueTranslationIssueDetails) currentPage;
        issueTranslationIssueDetails.clickSaveEditButton();
        currentPage = issueTranslationIssueDetails;
    }

    @Test()
    @Order(65)
    public void verifyMessage_expect47(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = (LotcheckIssueTranslationIssueDetails) currentPage;
        currentPage = issueTranslationIssueDetails;
        assertTrue(issueTranslationIssueDetails.displaySuccessMessage(), "Verify display message assigned success ");
        WaitUtils.idle(6000);
        issueTranslationIssueDetails.closeWarningMessage();
        assertFalse(issueTranslationIssueDetails.displaySuccessMessage(), "Verify message assign success disappear in 5 seconds.");
    }


    @Test()
    @Order(66)
    public void verifyDisplayPublishedTitleAndPublishedDescription_step48(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = (LotcheckIssueTranslationIssueDetails) currentPage;
        currentPage = issueTranslationIssueDetails;
    }

    @Test()
    @Order(67)
    public void verifyDisplayPublishedTitleAndPublishedDescription_expect48(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = (LotcheckIssueTranslationIssueDetails) currentPage;
        currentPage = issueTranslationIssueDetails;
        boolean requiredHighlightIsRemovedTitleEN = issueTranslationIssueDetails.requiredPublishHighlightIsRemovedTitleEN();
        boolean requiredHighlightIsRemovedTitleJP = issueTranslationIssueDetails.requiredPublishHighlightIsRemovedTitleJP();
        boolean requiredHighlightIsRemovedDescriptionEN = issueTranslationIssueDetails.requiredPublishHighlightIsRemovedDescriptionEN();
        boolean requiredHighlightIsRemovedDescriptionJP = issueTranslationIssueDetails.requiredPublishHighlightIsRemovedDescriptionJP();
        assertAll(() -> assertEquals(editPublishedTitleEN, issueTranslationIssueDetails.getPublishedTitle_EN(), "Verify edit published Title EN "),
                () -> assertEquals(editPublishedDescriptionJP, issueTranslationIssueDetails.getPublishedDescription_JP(), "Verify edit published Description JP "),
                () -> assertFalse(requiredHighlightIsRemovedTitleEN, "Verify published title Required EN"),
                () -> assertFalse(requiredHighlightIsRemovedTitleJP, "Verify published title Required JP"),
                () -> assertFalse(requiredHighlightIsRemovedDescriptionEN, "Verify published description Required EN"),
                () -> assertFalse(requiredHighlightIsRemovedDescriptionJP, "Verify published description Required JP")
        );
    }

    @Test()
    @Order(68)
    public void pressingTranslationCompleteButton_step49(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationIssueDetails issueTranslationIssueDetails = (LotcheckIssueTranslationIssueDetails) currentPage;
        WaitUtils.idle(5000);
        issueTranslationIssueDetails.clickTranslationCompleteButton()
                .enterCommentTranslationComplete(TextConstants.commentTranslationComplete).clickSaveComment();
        messageDisplay = issueTranslationIssueDetails.displaySuccessMessage();
        WaitUtils.idle(6000);
        messageNotDisplay = issueTranslationIssueDetails.displaySuccessMessage();
        LotcheckHomePage lotcheckHomePage = issueTranslationIssueDetails.navLandingPage().clickOnUserNameMenu().clickOnSignoutButton();
        currentPage = lotcheckHomePage;
    }

    @Test()
    @Order(69)
    public void verifyMessage_expect49(IJUnitTestReporter testReport) {
        assertTrue(messageDisplay, "Verify success message");
        assertFalse(messageNotDisplay, "Verify message disappear in 5 seconds.");

    }


    @Test()
    @Order(70)
    public void loginAsLCAdmin_step50(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
        currentPage = lotcheckLandingPage;
    }

    @Test()
    @Order(71)
    public void navigateViewLotcheckIssue_step51(IJUnitTestReporter testReport) {
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        LotcheckReportSubmissionLotcheckIssues lotcheckIssues = lotcheckLandingPage.searchProduct(productName).selectSubmission(gameCode, "00", "02")
                .clickLotchekIssueTab();
        WaitUtils.idle(10000);
        LotcheckReportSubmissionLotcheckIssues submissionlotcheckIssues = lotcheckIssues.expandCollapsedIssue();
        String descriptionBug = lotcheckIssues.getDescription();
        LotcheckReportSubmissionLotcheckIssueDetails lotcheckIssueDetails = lotcheckIssues.clickMenu().clickViewIssueDetail();
        currentPage = lotcheckIssueDetails;
        assertEquals(TextConstants.enterDescription, descriptionBug, "Verify Issue bug display in Lotcheck Issue");
    }

    @Test()
    @Order(72)
    public void selectActionMenu_step52(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssueDetails lotcheckIssueDetails = (LotcheckReportSubmissionLotcheckIssueDetails) currentPage;
        lotcheckIssueDetails.clickMenuAtIssueDetails();
        currentPage = lotcheckIssueDetails;
    }

    @Test()
    @Order(73)
    public void openTranslationTasksIsAvailable_expect52(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssueDetails lotcheckIssueDetails = (LotcheckReportSubmissionLotcheckIssueDetails) currentPage;
        boolean openTranslationTasksIsEnable = lotcheckIssueDetails.openTranslationTasksIsEnable();
        currentPage = lotcheckIssueDetails;
        assertTrue(openTranslationTasksIsEnable, " Verify The 'Open Translation Tasks' option is available in the Actions menu (…)");
    }

    @Test()
    @Order(74)
    public void clickOpenTranslationTasksIsAvailable_step53(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssueDetails lotcheckIssueDetails = (LotcheckReportSubmissionLotcheckIssueDetails) currentPage;
        lotcheckIssueDetails.openTranslationTask();
        currentPage = lotcheckIssueDetails;
    }

    @Test()
    @Order(75)
    public void verifyOpenTranslationTaskForm_expect53(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssueDetails lotcheckIssueDetails = (LotcheckReportSubmissionLotcheckIssueDetails) currentPage;
        currentPage = lotcheckIssueDetails;
        assertAll(() -> assertEquals("Open Translation Task", lotcheckIssueDetails.getFormName(), "Verify form name of Open Translation Task Form "),
                () -> assertEquals("Are you sure you want to open the Translation Task?", lotcheckIssueDetails.getFormMessage(), "Verify form message of Open Translation Task Form"),
                () -> assertEquals("Cancel", lotcheckIssueDetails.getFormCancelButton(), "Verify form cancel button of Open Translation Task Form "),
                () -> assertEquals("Confirm", lotcheckIssueDetails.getFormConfirmButton(), "Verify form confirm button of Open Translation Task Form")
        );
    }

    @Test()
    @Order(76)
    public void clickConfirmOpenTranslationTasksForm_step54(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssueDetails lotcheckIssueDetails = (LotcheckReportSubmissionLotcheckIssueDetails) currentPage;
        lotcheckIssueDetails.clickConfirmOpenTranslationTask();
        WaitUtils.idle(15000);
        currentPage = lotcheckIssueDetails;
    }

    @Test()
    @Order(77)
    public void confirmIssueDetailsPageAfterClickConfirmButton_step55(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssueDetails lotcheckIssueDetails = (LotcheckReportSubmissionLotcheckIssueDetails) currentPage;
        currentPage = lotcheckIssueDetails;
    }

    @Test()
    @Order(78)
    public void confirmIssueDetailsPageAfterClickConfirmButton_expect55(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssueDetails lotcheckIssueDetails = (LotcheckReportSubmissionLotcheckIssueDetails) currentPage;
        lotcheckIssueDetails.clickMenuAtIssueDetails();
        currentPage = lotcheckIssueDetails;
        assertAll(() -> assertFalse(lotcheckIssueDetails.openTranslationTasksIsEnable(), " Verify The 'Open Translation Tasks' option is not disable in the Actions menu (…)"),
                () -> assertFalse(lotcheckIssueDetails.openTranslationFormIsDisplay(), "Verify The Open Translation Tasks Form is not disable"),
                () -> assertTrue(lotcheckIssueDetails.re_enableActionMenu(), "Verify  re-enable the Action menu (…) ")
        );
    }

    @Test()
    @Order(79)
    public void confirmTranslationStatusOnIssueTitle_step56(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssueDetails lotcheckIssueDetails = (LotcheckReportSubmissionLotcheckIssueDetails) currentPage;
        currentPage = lotcheckIssueDetails;
    }

    @Test()
    @Order(80)
    public void verifyTranslationStatusIsPending_expect56(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssueDetails lotcheckIssueDetails = (LotcheckReportSubmissionLotcheckIssueDetails) currentPage;
        LotcheckReportSubmissionLotcheckIssues lotcheckIssues = lotcheckIssueDetails.clickBackToLotcheckIssue();
        String status = lotcheckIssues.getTranslationStatus();
        currentPage = lotcheckIssues;
        assertEquals("Pending", status, "Verify In Issue Tile, the Translation Status becomes to Pending");
    }

    @Test()
    @Order(81)
    public void gotoMyTaskPage_step57(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssues lotcheckIssue = (LotcheckReportSubmissionLotcheckIssues) currentPage;
        lotcheckIssue.logOut();

        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage landingPage = hyruleAuthPage.loginWithOvdLink(TextConstants.lc_Translator_NCL, TextConstants.lc_Translator_NCL_Password);
        LotcheckIssueTranslationTasksPage translationTasksPage = landingPage.navigationToLotcheckIssueTranslationTasks();
        LotcheckIssueTranslationTaskDetails issueTranslationTaskDetails = translationTasksPage.clickViewTaskDetailsProd_addLoop(gameCode);
        currentPage = issueTranslationTaskDetails;
    }

    @Test()
    @Order(82)
    public void verifyTranslationStatusIsPending_expect57(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationTaskDetails issueTranslationTaskDetails = (LotcheckIssueTranslationTaskDetails) currentPage;
        WaitUtils.idle(10000);
        String translationTaskType = issueTranslationTaskDetails.getTranslationTaskType();
        String translationStatus = issueTranslationTaskDetails.getTranslationStatus();
        LotcheckHomePage lotcheckHomePage = issueTranslationTaskDetails.navLandingPage().clickOnUserNameMenu().clickOnSignoutButton();
        currentPage = lotcheckHomePage;
        assertAll(() -> assertEquals("Issue Translation", translationTaskType, " Verify Translation Task Type is Issue Translation"),
                () -> assertEquals("Pending", translationStatus, "Verify Translation Status is Pending")
        );
    }

    @Test()
    @Order(83)
    public void submitBugTheSecondTestCase_step58(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN, testReport);
        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailLoginPage.signInTestRail(testrailUser).navTopTestRail().navigateToManagerDashboard();
        testRailManagerDashboardPage.checkErrorPopup();
        testRailManagerDashboardPage.actionFill().enterTestPlanNameFilter(gameCode)
                .selectStatusUntested().selectStatusPassed();
        testRailManagerDashboardPage.actionFill().clickOnApplyFiltersButton();
        testRailManagerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandAllTestSuites()
                .clickOnTheFirstTestRun();
        nameTestCaseSubmitSecondBug = testRailManagerDashboardPage.getNameSecondTestCaseSubmitBug();
        TestRailSubmitBugDialog submitBugDialog = testRailManagerDashboardPage.clickOnTheSecondTestcase().clickSubmitBug();
        submitBugDialog.enterDescription(TextConstants.enterDescription).checkNeedsTranslation().clickSubmit();
        String messageSuccess = submitBugDialog.getMessageSuccessNoSwitchDefault();
        submitBugDialog.clickOk().clickClose().logOut();
        currentPage = testRailLoginPage;
        assertEquals("Your Issue has been submitted successfully.", messageSuccess, "Verify submit bug successfully");

    }


    @Test()
    @Order(84)
    public void publishToPublicWithTypeIsBug_step59(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
        LotcheckReportSubmissionLotcheckIssues lotcheckIssues = lotcheckLandingPage.searchProduct(productName).selectSubmission(gameCode, "00", "02").clickLotchekIssueTab();
        WaitUtils.idle(10000);
        LotcheckReportSubmissionLotcheckIssueDetails lotcheckIssueDetails = lotcheckIssues.clickMenuBugSecond(nameTestCaseSubmitSecondBug).clickViewIssueDetail().performPublishIssue("Bug");
        WaitUtils.idle(15000);
        currentPage = lotcheckIssueDetails;
    }

    @Test()
    @Order(85)
    public void verifyEditPostNotVisible_expect59(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssueDetails lotcheckIssueDetails = (LotcheckReportSubmissionLotcheckIssueDetails) currentPage;
        LotcheckReportSubmissionLotcheckIssuePartnerThread issuePartnerThread = lotcheckIssueDetails.clickPartnerThreadTab();
        WaitUtils.idle(5000);
        currentPage = issuePartnerThread;
        assertFalse(issuePartnerThread.editPostButtonNotVisible(), "Verify Edit Post button is not visible for the post created after performing Publish to Public.");
    }

    @Test()
    @Order(86)
    public void attachFileAndReplyToPartner_step60(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssuePartnerThread issuePartnerThread = (LotcheckReportSubmissionLotcheckIssuePartnerThread) currentPage;
        issuePartnerThread.clickAttachFile().selectFileInPC(fileAttach);
        WaitUtils.idle(15000);
        issuePartnerThread.clickReplyToPartner();
        WaitUtils.idle(20000);
        currentPage = issuePartnerThread;
    }


    @Test()
    @Order(87)
    public void clickEditPostAndDeleteFile_step61(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssuePartnerThread lotcheckIssuePartnerThread = (LotcheckReportSubmissionLotcheckIssuePartnerThread) currentPage;
        lotcheckIssuePartnerThread.refreshPageSLCMS();
        lotcheckIssuePartnerThread.clickEditPost().clickRemoveFile().clickSaveEditPost();
        WaitUtils.idle(20000);
        currentPage = lotcheckIssuePartnerThread;
    }

    @Test()
    @Order(88)
    public void checkDisplayAutoPost_step62(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssuePartnerThread lotcheckIssuePartnerThread = (LotcheckReportSubmissionLotcheckIssuePartnerThread) currentPage;
    }

    @Test()
    @Order(89)
    public void checkDisplayAutoPost_expect62(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssuePartnerThread lotcheckIssuePartnerThread = (LotcheckReportSubmissionLotcheckIssuePartnerThread) currentPage;
        currentPage = lotcheckIssuePartnerThread;
        assertEquals("Attachment was deleted", lotcheckIssuePartnerThread.getMessage(), "Verify EN text: Attachment was deleted");
        assertEquals("testdemo.pdf", lotcheckIssuePartnerThread.getFileName(), "Verify EN: file name");


    }

    @Test()
    @Order(90)
    public void selectLCThreadTabAndChangeToClose_step63(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssuePartnerThread lotcheckIssuePartnerThread = (LotcheckReportSubmissionLotcheckIssuePartnerThread) currentPage;
        LotcheckReportSubmissionLotcheckIssueThread issueThread = lotcheckIssuePartnerThread.clickLotcheckThreadTab();
        issueThread.changeLotcheckThreadStatusToClose("Closed");
        currentPage = issueThread;
    }


    @Test()
    @Order(91)
    public void postCommentAndAttachFile_step64(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssueThread issueThread = (LotcheckReportSubmissionLotcheckIssueThread) currentPage;
        currentPage = issueThread;
        issueThread.enterMessage("enter message step 60").clickAttachFile().selectFileInPC(fileAttach);
        WaitUtils.idle(10000);
        issueThread.clickReplyToPartner();
        WaitUtils.idle(20000);
        //issueThread.waitAlertDone();
    }

    @Test()
    @Order(92)
    public void clickEditPostAndDeleteAttach_step65(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssueThread issueThread = (LotcheckReportSubmissionLotcheckIssueThread) currentPage;
        currentPage = issueThread;
        issueThread.clickEditPost().clickRemoveFile().clickSaveEditPost();
        WaitUtils.idle(20000);
        //issueThread.waitAlertDone();
    }

    @Test()
    @Order(93)
    public void checkDisplayAutoPost_step66(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssueThread issueThread = (LotcheckReportSubmissionLotcheckIssueThread) currentPage;
    }

    @Test()
    @Order(94)
    public void checkDisplayAutoPost_expect66(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionLotcheckIssueThread issueThread = (LotcheckReportSubmissionLotcheckIssueThread) currentPage;
        currentPage = issueThread;
        assertFalse(issueThread.fileIsDisplay(), "Attachment was deleted");
        assertEquals("Attachment was deleted", issueThread.getMessageAfterChangeLotcheckStatus(), "Verify EN text: Attachment was deleted");
        assertEquals("testdemo.pdf", issueThread.getFileNameAfterChangeLotcheckStatus(), "Verify EN: file name");

    }
}
