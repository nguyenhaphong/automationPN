package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.junit.TestLogger;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.data.DataGen;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.email.EmailDevPage;
import net.nintendo.automation.ui.models.email.EmailPage;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthPage;
import net.nintendo.automation.ui.models.lcms.LotcheckHomePage;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.ui.models.lcms.LotcheckQueuePage;
import net.nintendo.automation.ui.models.lcms.LotcheckSubmissionSearchPage;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionRequiredApproval;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionRequiredApprovalPartnerThread;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPLicensingWaiverRequestsTab;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.NDPViewTaskPage;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardMembersTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardProductInfoTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardReleasesTab;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPMessageBoardPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoFeaturesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoGuidelinesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoIssuePage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoPage;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPConfirmationDialog;
import net.nintendo.automation.ui.models.rom.ROMCreateRom;
import net.nintendo.automation.ui.models.rom.ROMLoginPage;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@NDP
@SLCMS
@Tag("T3404146")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3404146 extends CommonBaseTest {
    private static Browser browser;
    private static Browser firefoxBrowser;
    private static User romUser;
    private static User ndpUser;
    private static User slcmsUser;
    private static User testrailUser;
    private static User licUser;

    private static String gameCode;
    private static String applicationId;
    private static String rom_ApplicationName;
    private static String initialCode;
    private static String productName;
    private static String publishingRelationship;
    private static String issue0050ConfirmProhibitionOfSpecifications;

    private static CommonUIComponent currentPage;
    private static CommonUIComponent currentFFPage;
    private static TestLogger logger;

    private static String hardWare1 = "0050: Prohibition of specifications that give the user reason to dock/undock the system";
    private static String fileAttach = "testdemo.pdf";
    private static String message = "thuydungtestdemo";
    private static List<String> listAccCheckMailMP35 = Arrays.asList(TextConstants.prdcv_lic_admin_NCL, TextConstants.prdcv_lic_admin_NOA, TextConstants.prdcv_lic_admin_NOE);
    private static List<String> listAccCheckMailMP36 = Arrays.asList(TextConstants.lc_Director_NCL, TextConstants.lc_Director_NOA, TextConstants.lc_Director_NOE,
            TextConstants.lc_oversea_coord_NCL, TextConstants.lc_oversea_coord_NOA, TextConstants.lc_oversea_coord_NOE,
            TextConstants.lc_Coordinator_NCL, TextConstants.lc_Coordinator_NOA, TextConstants.lc_Coordinator_NOE);


    @BeforeEach
    public void each(IJUnitTestReporter testReport) {
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
    }

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_DungTest2);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
        slcmsUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);

        testrailUser = new User();
        testrailUser.setUserType(UserType.PARTNER_USER); //need request noa add user type of testrail to replace
        testrailUser.setUserName(TextConstants.trAdminNCL_Email);
        testrailUser.setPassword(TextConstants.trAdmin_Password);

        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);
        publishingRelationship = TextConstants.third_Party_Type;

        productName = DataGen.getRandomProductName();
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);

    }

    @AfterAll
    public static void terminateBrowser() {
        BrowserManager.closeBrowser(browser);
        UserManager.releaseUser(ServiceType.NDP, ndpUser);
        UserManager.releaseUser(ServiceType.SLCMS, slcmsUser);
        UserManager.releaseUser(ServiceType.NDP, licUser);
        UserManager.releaseUser(ServiceType.SEARCH, romUser);

    }

    private static void createRom_MPA_ROM10(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        ROMLoginPage romLoginPage = (ROMLoginPage) currentPage;
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom
                .selectType(TextConstants.initial_Release)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .enterProductName(productName)
                .enterApplicationId(applicationId)
                .selectPublishingRelationShip(publishingRelationship)
                .enterDisplayVersion("1.0.0")
                .enterReleaseVersion("00")
                .selectMPACheckbox()
                .selectNumberOfProgram("10")
                .selectCERORating("CERO: Not required")
                .selectRequester(TextConstants.test_User)
                .clickOnCreateButton()
                .waitIsLoaded();
        rom_ApplicationName = romCreateRom.getRomNameTable(applicationId);
        romCreateRom.clickOnROMFileOnROMTable(rom_ApplicationName);
        WaitUtils.idle(5000);
    }

    @Test()
    @Order(1)
    public void createProduct_step1(IJUnitTestReporter testReport) {
        NDPHomePage homePage = (NDPHomePage) currentPage;
        NDPMyProductsPage myProductsPage = homePage.acceptCookie().clickSignInPage().signIn(ndpUser).clickMyProducts();
        myProductsPage.createNewProduct()
                .selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(publishingRelationship)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalJapanSalesRegion()
                .selectProductName(productName)
                .selectProductNameKana(TextConstants.productKana)
                .clickCreateButton();
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        CommonAction.issueGameCode(ndpProductDashboardPage, 30,"NO");
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();
        applicationId = ndpProductDashboardProductInfoTab.getApplicationID();
        gameCode = ndpProductDashboardProductInfoTab.getGameCode();
        initialCode = ndpProductDashboardProductInfoTab.getInitialCode();
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        ndpReleaseInfoPage.nav().clickOnUserNameMenu().clickOnSignOutButton();

        createRom_MPA_ROM10(testReport);

        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReport);
        homePage = (NDPHomePage) currentPage;
        myProductsPage = homePage.clickSignInPage().signIn(ndpUser).clickMyProducts();
        ndpReleaseInfoPage = myProductsPage.clickProductByGameCode(initialCode).clickReleases().selectInitialRelease();
        ndpReleaseInfoPage.nav().clickROMUpload().closeRequiredStepModal().selectRomFileButton()
                .selectFileInPC(rom_ApplicationName)
                .clickUpload()
                .clickUploadNow().waitForUploadComplete(TextConstants.romUpload);
        currentPage = ndpReleaseInfoPage;

    }

    @Test()
    @Order(2)
    public void createIssueGuideline_step2(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentPage;
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = ndpReleaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoGuidelinesPage.selectCannotConform0050ConfirmProhibitionOfSpecifications()
                .selectCompliesWithAllOfTheAboveGuidelines().clickOnSaveButton();
        ndpReleaseInfoPage.nav().clickReleaseInfo();
    }

    @Test()
    @Order(3)
    public void navigateToPendingWaivers_step3(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentPage;
        ndpReleaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoPage.nav().clickAgeRating();
        ndpReleaseInfoPage.nav().clickFeatures();
        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();
        issuePage.openRequiredApprovalTab();
        currentPage = issuePage;
    }

    @Test()
    @Order(4)
    public void verifyIssueGuidelineDisplayInIssueTab_expect3(IJUnitTestReporter testReport) {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        WaitUtils.idle(5000);
        String nameOfIssue = issuePage.getNameOf0050ConfirmProhibitionOfSpecifications();
        currentPage = issuePage;
        assertTrue(nameOfIssue.contains(hardWare1));

    }

    @Test()
    @Order(5)
    public void sendResolution_step4(IJUnitTestReporter testReport) {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        issuePage.expandIssue0050ConfirmProhibitionOfSpecifications()
                .selectDesiredApprovalTypeAtFirstUnresolvedRequiredApproval()
                .enterReasonForRequest_true(TextConstants.enterReasonForRequest_1000kanji)
                .clickOnSendResolutionButton_true();
        currentPage = issuePage;
    }

    @Test()
    @Order(6)
    public void verifyWaiverRequestSubmitted_expect4(IJUnitTestReporter testReport) {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        issuePage.openRequiredApprovalTab();
        String statusRequest = issuePage.getStatusIssue0050ConfirmProhibitionOfSpecifications();
        issuePage.nav().clickOnUserNameMenu().clickOnSignOutButton();
        assertEquals("Temporary Waiver Requested", statusRequest, "Request sent fail");


    }

    @Test()
    @Order(7)
    public void loginSLCMSWithDirectorLC_step5(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(TextConstants.lc_Director_NCL, TextConstants.lc_Director_NCL_Password);
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = lotcheckLandingPage.searchProduct(initialCode);
        currentPage = lotcheckSubmissionSearchPage;

    }

    @Test()
    @Order(8)
    public void NavigateToPartnerThreads_step6(IJUnitTestReporter testReport) {
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = (LotcheckSubmissionSearchPage) currentPage;
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = lotcheckSubmissionSearchPage.selectSubmission(gameCode, "00", "00");
        LotcheckReportSubmissionRequiredApproval lotcheckReportSubmissionRequiredApproval = lotcheckReportSubmissionOverview.clickRequiredApprovalTab().clickMenuJS();
        LotcheckReportSubmissionRequiredApprovalPartnerThread lotcheckRequiredApprovalPartnerThread = lotcheckReportSubmissionRequiredApproval.clickViewPartnerThread();
        lotcheckRequiredApprovalPartnerThread.enterDescription(message);
        lotcheckRequiredApprovalPartnerThread.clickAttachFile().selectFileInPC(fileAttach);
        WaitUtils.idle(20000);
        currentPage = lotcheckRequiredApprovalPartnerThread;
        String attach = lotcheckRequiredApprovalPartnerThread.getFileBeforeReply();
        assertEquals("testdemo.pdf", attach, "Attach fail");
    }

    @Test()
    @Order(9)
    public void clickReplyPartner_step7(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionRequiredApprovalPartnerThread lotcheckRequiredApprovalPartnerThread = (LotcheckReportSubmissionRequiredApprovalPartnerThread) currentPage;
        lotcheckRequiredApprovalPartnerThread.clickReplyToPartner();
        WaitUtils.idle(20000);
        currentPage = lotcheckRequiredApprovalPartnerThread;
    }

    @Test()
    @Order(10)
    public void verifyReplyPartner_expect7(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionRequiredApprovalPartnerThread lotcheckRequiredApprovalPartnerThread = (LotcheckReportSubmissionRequiredApprovalPartnerThread) currentPage;
        currentPage = lotcheckRequiredApprovalPartnerThread;
        String getMessage = lotcheckRequiredApprovalPartnerThread.getMessage();
        String getAttachFileName = lotcheckRequiredApprovalPartnerThread.getFileAfterReply();

        assertAll(() -> assertEquals("testdemo.pdf", getAttachFileName, "Verify display Attach file name"),
                () -> assertEquals(message, getMessage, "Verify display message "),
                () -> assertTrue(lotcheckRequiredApprovalPartnerThread.displayImgFile(), "Verify display img attached file")
        );

    }

    @Test()
    @Order(11)
    public void checkMP35_step8(IJUnitTestReporter testReport) {
        //MP35: prdcv_lic_admin_ncl, prdcv_lic_pwr_user_ncl
        EmailPage emailPage = browser.openURL(EmailPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        String emailName = "[Approvals][" + gameCode + "][Temporary Waiver Requested](" + productName + ") Hardware";
        for (String region : listAccCheckMailMP35) {
            emailPage.signInEmail(region).waitListMailDisplay();
            boolean mailIsSent = emailPage.confirmEmailIsSent(emailName);
            assertAll(() -> assertTrue(mailIsSent, "Mail MP35 is not sent to " + region));
        }
    }

    @Test()
    @Order(12)
    public void rejectWaiverRequest_step9(IJUnitTestReporter testReport) {
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        licensingWaiverRequestsTab.waitingForResultTableLoaded()
                .enterSearchingBox(initialCode)
                .clickOnMagnifyingIconButton()
                .waitingForResultTableLoaded();
        NDPViewTaskPage ndpViewTaskPage = licensingWaiverRequestsTab.openLicensingWaiverRequestByPendingStatus()
                .selectAssignToMeButton()
                .selectOKButtonInAssignToMeModal()
                .selectRejectButtonInApproveRequestModal()
                .selectOKButtonInApproveRequestModal()
                .waitingForApproveSuccessful();
        currentPage = ndpViewTaskPage;
    }

    @Test()
    @Order(13)
    public void verifyRequestIsRejected_expect9(IJUnitTestReporter testReport) {
        NDPViewTaskPage ndpViewTaskPage = (NDPViewTaskPage) currentPage;
        WaitUtils.idle(5000);
        String resolutionStatus = ndpViewTaskPage.getStatus();
        NDPDevelopmentHome developmentHome = ndpViewTaskPage.clickUserMenuDropdownList()
                .clickSignOutButton().clickSignInPage().signIn(ndpUser);
        currentPage = developmentHome;
        assertEquals("Temporary Waiver Requested", resolutionStatus, "The waiver request is rejected");

    }

    @Test()
    @Order(14)
    public void clickViewThread_step10(IJUnitTestReporter testReport) {
        NDPDevelopmentHome developmentHome = (NDPDevelopmentHome) currentPage;
        NDPReleaseInfoPage ndpReleaseInfoPage = developmentHome.clickMyProducts().clickProductByName(productName).clickReleases().selectInitialRelease();
        NDPReleaseInfoIssuePage ndpReleaseInfoIssuePage = ndpReleaseInfoPage.nav().clickIssues().openRequiredApprovalTab();
        ndpReleaseInfoIssuePage.expandIssue0050ConfirmProhibitionOfSpecifications();
        NDPMessageBoardPage ndpMessageBoardPage = ndpReleaseInfoIssuePage.clickViewThread();
        currentPage = ndpMessageBoardPage;
    }

    @Test()
    @Order(15)
    public void VerifyNavigateMessageThread_expect10(IJUnitTestReporter testReport) {
        NDPMessageBoardPage ndpMessageBoardPage = (NDPMessageBoardPage) currentPage;
        assertEquals("Message Board - Nintendo Developer Portal", ndpMessageBoardPage.getPageTitle(), "Navigate incorrect to the message thread for the waiver request.");
    }

    @Test()
    @Order(16)
    public void repeatStep3To6_step11(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN);
        NDPMyProductsPage ndpMyProductsPage = (NDPMyProductsPage) currentPage;
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpMyProductsPage.clickProductByName(productName).clickReleases().selectInitialRelease();
        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues().openRequiredApprovalTab();
        issuePage.expandIssue0050ConfirmProhibitionOfSpecifications()
                .selectDesiredApprovalTypeAtFirstUnresolvedRequiredApproval()
                .enterReasonForRequest_true(TextConstants.enterReasonForRequest_1000kanji)
                .clickOnSendResolutionButton_true().waitDisplayAlertSuccessResolvedIssues0050();
        NDPHomePage ndpHomePage = ndpMyProductsPage.clickOnUserNameMenu().clickOnSignOutButton();
        currentPage = ndpHomePage;
    }

    @Test()
    @Order(17)
    public void verifyWaiverRequestApproved_expect11(IJUnitTestReporter testReport) {
        NDPHomePage ndpHomePage = (NDPHomePage) currentPage;
        NDPDevelopmentHome developmentHome = ndpHomePage.clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        licensingWaiverRequestsTab.waitingForResultTableLoaded()
                .enterSearchingBox(initialCode)
                .clickOnMagnifyingIconButton()
                .waitingForResultTableLoaded();
        NDPViewTaskPage ndpViewTaskPage = licensingWaiverRequestsTab.openLicensingWaiverRequestByPendingStatus()
                .selectAssignToMeButton()
                .selectOKButtonInAssignToMeModal()
                .selectApproveRequestButton()
                .selectOKButtonInApproveRequestModal()
                .waitingForApproveSuccessful()
                .refresh();
        WaitUtils.idle(5000);
        currentPage = ndpViewTaskPage;
        String resolutionStatus = ndpViewTaskPage.getStatus();
        assertEquals("Temporary Waiver Approved", resolutionStatus, "The waiver request is Approval");
    }


    @Test()
    @Order(18)
    public void submitProdByUserB_dungB_step12(IJUnitTestReporter testReport) {
        NDPViewTaskPage ndpViewTaskPage = (NDPViewTaskPage) currentPage;
        NDPMyProductsPage ndpMyProductsPage = ndpViewTaskPage.clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn("dungB", "nintendo").clickMyProducts();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpMyProductsPage.clickProductByName(productName).clickReleases().selectInitialRelease();
        NDPReleaseInfoPage releaseInfoPage = ndpReleaseInfoPage.nav().clickReleaseInfo();
        CommonAction.selectROMSameAllRegion(releaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);
        NDPReleaseInfoFeaturesPage releaseInfoFeaturesPage = releaseInfoPage.nav().clickFeatures();

        CommonAction.getFeatureOptionData(releaseInfoPage, TextConstants.allOff,
                TextConstants.allOff, TextConstants.allOff, false, "", false, "");
        releaseInfoFeaturesPage.clickContinue();

        ndpReleaseInfoPage.nav().clickTestScenarios().expandAllSections()
                .expandAllTestScenes()
                .enterNameHowToCheckDLC(TextConstants.enterNameHowToCheckDLC)
                .enterExplainHowToCheckDLC(TextConstants.enterExplainHowToCheckDLC)
                .clickOnSaveButton();

        ndpReleaseInfoPage.nav().clickGuidelines().selectCompliesWithAllOfTheAboveGuidelines().clickOnSaveButton();

        releaseInfoPage.nav().clickAgeRating().clickOnAddStandardRatingButton()
                .selectStandardAgeRating("CERO")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectRatingValue("Not required")
                .clickOnSaveButtonInEditStandardRating();
        releaseInfoPage.nav().clickIssues().openRequiredApprovalTab().performAllIssueAtApprovalTab(false);
        NDPDevelopmentHome developmentHome = ndpReleaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, initialCode);

        developmentHome = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReport).clickUserMenuDropdownList().clickSignOutButton()
                .clickSignInPage().signIn(ndpUser);
        developmentHome.clickMyProducts().clickProductByGameCode(initialCode).clickReleases().selectInitialRelease();
        releaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.enterDeveloperComment)
                .clickSubmit();

    }

    @Test()
    @Order(20)
    public void checkMailMP36(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage lotcheckLandingPage = (LotcheckLandingPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = lotcheckLandingPage.searchProduct(initialCode);
        lotcheckSubmissionSearchPage.selectSubmission(gameCode, "00", "00")
                .clickRequiredApprovalTab().clickMenuHardware().clickViewPartnerThreadHardware()
                .enterDescription2nd("enter message to click reply partner check mail 36")
                .clickAttachFile().selectFileInPC(fileAttach).clickReplyToPartner();
        WaitUtils.idle(15000);


        EmailDevPage emailDevPage = browser.openURL(EmailDevPage.class, PageState.PRE_LOGIN);
        String emailName = "[Approvals][" + gameCode + "][Temporary Waiver Approved] Hardware > Attaching and detaching devices";
        for (String region : listAccCheckMailMP36) {
            emailDevPage.enterMailBox(region).waitDisplayListMail();
            emailDevPage.searchEmail(emailName);
            WaitUtils.idle(5000);
            boolean mailIsSent = emailDevPage.confirmEmailIsSent(emailName);
            WaitUtils.idle(10000);
            assertAll(() -> assertTrue(mailIsSent, "Mail MP36 is not sent to " + region));

        }

    }

    @Test()
    @Order(21)
    public void clickCancelSubmission_step13(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        WaitUtils.idle(30000);
        NDPReleaseInfoPage releaseInfoPage = myProductsPage.clickProductByName(productName).clickReleases().selectInitialRelease();
        NDPConfirmationDialog ndpConfirmationDialog = releaseInfoPage.nav().clickCancelSubmitButton();
        ndpConfirmationDialog.clickOKInConfirm();
    }

    @Test()
    @Order(22)
    public void verifySubmissionIs01_expect13(IJUnitTestReporter testReport) {
        NDPMyProductsPage myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndproductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        String submissionVersion = ndproductDashboardReleasesTab.getSubmissionVersion();
        currentPage = ndproductDashboardReleasesTab;
        assertEquals("01", submissionVersion, "Submission version incorrect");
    }

    @Test()
    @Order(23)
    public void completeAllRequired_step14(IJUnitTestReporter testReport) {
        NDPProductDashboardReleasesTab ndproductDashboardReleasesTab = (NDPProductDashboardReleasesTab) currentPage;
        NDPReleaseInfoPage ndpReleaseInfoPage = ndproductDashboardReleasesTab.selectInitialRelease();
        ndpReleaseInfoPage.nav().clickROMUpload().closeRequiredStepModal().selectRomFileButton().selectFileInPC(rom_ApplicationName).clickUpload()
                .clickUploadNow().waitForUploadComplete(TextConstants.romUpload);
        ndpReleaseInfoPage.nav().clickReleaseInfo().setExpectedLotcheckSubmissionDate().save();
        ndpReleaseInfoPage.nav().clickGuidelines().selectCompliesWithAllOfTheAboveGuidelines().clickOnSaveButton();
        ndpReleaseInfoPage.nav().clickIssues();
        ndpReleaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoPage.nav().clickAgeRating();
        ndpReleaseInfoPage.nav().clickTestScenarios();
        boolean submitButtonEnable = ndpReleaseInfoPage.nav().submitButtonEnable();
        ndpReleaseInfoPage.nav().clickReleaseInfo();
        currentPage = ndpReleaseInfoPage;
        assertTrue(submitButtonEnable, "Submit button not enable");
    }

    @Test()
    @Order(24)
    public void openOtherBrowserAndLoginUserA_test2_dung_step15(IJUnitTestReporter testReport) {
        firefoxBrowser = BrowserManager.getNewInstance(true);
        currentFFPage = firefoxBrowser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReport);
        logger = new TestLogger(testReport);
        currentFFPage.setExtentLogger(logger);
        NDPHomePage ndpHomePage = (NDPHomePage) currentFFPage;
        NDPProductDashboardPage ndpProductDashboardPage = ndpHomePage.acceptCookie().clickSignInPage().signIn(ndpUser)
                .clickMyProducts().clickProductByGameCode(initialCode);
        currentFFPage = ndpProductDashboardPage;

    }

    @Test()
    @Order(25)
    public void navigateMembersTab_step16(IJUnitTestReporter testReport) {
        NDPProductDashboardPage ndpProductDashboardPage = (NDPProductDashboardPage) currentFFPage;
        NDPProductDashboardMembersTab ndpProductDashboardMembersTab = ndpProductDashboardPage.clickMembersTab();
        currentFFPage = ndpProductDashboardMembersTab;
    }

    @Test()
    @Order(26)
    public void changeRolesBToProductObserver_step17(IJUnitTestReporter testReport) {
        NDPProductDashboardMembersTab ndpProductDashboardMembersTab = (NDPProductDashboardMembersTab) currentFFPage;
        currentFFPage = ndpProductDashboardMembersTab;
        ndpProductDashboardMembersTab.selectAddUserButton().selectMyCompanyUsers().selectInternalUsers("dungb").selectAccessRole("Observer").clickAddUserButton();
        BrowserManager.closeBrowser(firefoxBrowser);
    }

    //@Test()
    public void step18to20_skip(IJUnitTestReporter testReport) {  //To Do G3PTDEV4NX-29465
    }

    @Test()
    @Order(27)
    public void submitProduct_step21(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage ndpReleaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        currentPage = ndpReleaseInfoPage;
        NDPProductDashboardMembersTab ndpProductDashboardMembersTab = ndpReleaseInfoPage.clickBackReleaseDashboard().clickMembersTab();
        ndpProductDashboardMembersTab.selectAddUserButton().selectMyCompanyUsers().selectInternalUsers("dungb").selectAccessRole("Product Admin").clickAddUserButton();
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardMembersTab.clickReleases();
        ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.enterDeveloperComment).clickSubmit();//submit second
        WaitUtils.idle(3000);
    }

    @Test()
    @Order(28)
    public void verifySubmitSuccess_expect21(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        NDPReleaseInfoPage releaseInfoPage = myProductsPage.clickProductByName(productName).clickReleases().selectInitialRelease();
        releaseInfoPage.nav().clickReleaseInfo();
        currentPage = releaseInfoPage;
        assertTrue(releaseInfoPage.nav().isCancelSubmissionButtonActived(), "Verify submit Cancel Submission Button Active, Submit success");
    }


    @Test()
    @Order(29)
    public void clickCancelSubmission_step22(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentPage;
        NDPConfirmationDialog ndpConfirmationDialog = ndpReleaseInfoPage.nav().clickCancelSubmitButton();
        ndpConfirmationDialog.clickOKInConfirm();
        WaitUtils.idle(10000);

    }

    @Test()
    @Order(30)
    public void VerifySubmissionIs02_expect22(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage ndpReleaseInfoPage = (NDPReleaseInfoPage) currentPage;
        NDPProductDashboardReleasesTab ndproductDashboardReleasesTab = ndpReleaseInfoPage.clickBackReleaseLink();
        String submissionVersion = ndproductDashboardReleasesTab.getSubmissionVersion();
        currentPage = ndproductDashboardReleasesTab;
        assertEquals("02", submissionVersion, "Submission version incorrect");

    }

    @Test()
    @Order(31)
    public void completeAllRequired_step23(IJUnitTestReporter testReport) {
        NDPProductDashboardReleasesTab ndproductDashboardReleasesTab = (NDPProductDashboardReleasesTab) currentPage;
        NDPReleaseInfoPage ndpReleaseInfoPage = ndproductDashboardReleasesTab.selectInitialRelease();
        ndpReleaseInfoPage.nav().clickROMUpload().closeRequiredStepModal().selectRomFileButton().selectFileInPC(rom_ApplicationName).clickUpload()
                .clickUploadNow().waitForUploadComplete(TextConstants.romUpload);
        ndpReleaseInfoPage.nav().clickReleaseInfo().setExpectedLotcheckSubmissionDate().save()
                .nav().clickGuidelines().selectCompliesWithAllOfTheAboveGuidelines().clickOnSaveButton();
        ndpReleaseInfoPage.nav().clickIssues();
        ndpReleaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoPage.nav().clickTestScenarios();
        ndpReleaseInfoPage.nav().clickReleaseInfo();
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.enterDeveloperComment).clickSubmit();
        WaitUtils.idle(3000);
    }

    @Test()
    @Order(32)
    public void judgmentNGSubmission02_step24(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailLoginPage.signInTestRail(testrailUser).navTopTestRail().navigateToManagerDashboard();
        CommonAction.setStatusOnTestrailIncludeFailed(testRailManagerDashboardPage, gameCode, TextConstants.failedStatus, TextConstants.percentCompleted, TextConstants.NG);

        currentPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckLandingPage landingPage = (LotcheckLandingPage) currentPage;
        landingPage.clickOnUserNameMenu().clickOnSignoutButton();
        browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN).continueToLogin().loginWithOvdLink(slcmsUser);
        LotcheckQueuePage nclLotCheckQueue = landingPage.clickJudgment(LotcheckRegion.NCL);
        CommonAction.approvalJudgmentStatus(nclLotCheckQueue, initialCode, 300, TextConstants.NG, true, "1");


    }

    @Test()
    @Order(33)
    public void submitVer03_step25(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReport);
        NDPDevelopmentHome developmentHome = (NDPDevelopmentHome) currentPage;
        NDPMyProductsPage ndpMyProductsPage = developmentHome.clickMyProducts();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpMyProductsPage.clickProductByName(productName).clickReleases().selectInitialRelease();
        ndpReleaseInfoPage.setExpectedLotcheckSubmissionDate().save()
                .nav().clickGuidelines().selectCompliesWithAllOfTheAboveGuidelines().clickOnSaveButton();
        ndpReleaseInfoPage.nav().clickROMUpload().closeRequiredStepModal().selectRomFileButton().selectFileInPC(rom_ApplicationName).clickUpload()
                .clickUploadNow().waitForUploadComplete(TextConstants.romUpload);
        ndpReleaseInfoPage.nav().clickReleaseInfo();
        ndpReleaseInfoPage.nav().clickSubmitButton().enterDeveloperComment(TextConstants.enterDeveloperComment).clickSubmit();
        WaitUtils.idle(3000);
        currentPage = ndpReleaseInfoPage;
    }

    @Test()
    @Order(34)
    public void submitToLCSuccessful_expect25(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReport);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage landingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
        LotcheckQueuePage lotcheckQueuePage = landingPage.navigationToTesterAssignmentQueue(LotcheckRegion.NCL.toString());
        lotcheckQueuePage.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        String numberTesPlanLC = lotcheckQueuePage.numberTestPlanDisplay();
        assertNotEquals("0", numberTesPlanLC, "Product is display in LC");
    }
}
