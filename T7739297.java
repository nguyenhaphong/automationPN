package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.junit.extensions.ScenarioTest;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.data.DataGen;
import net.nintendo.automation.ui.data.features.FeatureSettingsManager;
import net.nintendo.automation.ui.data.features.enums.LotcheckRemotePresenceEnum;
import net.nintendo.automation.ui.data.features.enums.ProgramSpecificationsEnum;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthPage;
import net.nintendo.automation.ui.models.lcms.LotcheckHomePage;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.ui.models.lcms.LotcheckQueuePage;
import net.nintendo.automation.ui.models.ndp.NDPCreateProductPage;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPLicensingWaiverRequestsTab;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.NDPRequestGameCodePage;
import net.nintendo.automation.ui.models.ndp.NDPSignInPage;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPCreateReleasePage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPEditIncludesApplicationModal;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardProductInfoTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardReleasesTab;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoAgeRating;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoFeaturesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoGuidelinesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoIssuePage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoROMUpload;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoTestScenarios;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPReleaseInfoFeaturesLotcheckRemotePresenceDialog;
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
import net.nintendo.automation_core.common_utils.annotations.services.PMMS;
import net.nintendo.automation_core.common_utils.annotations.services.SLCMS;
import net.nintendo.automation_core.common_utils.enums.LotcheckRegion;
import net.nintendo.automation_core.extent_reports.extensions.IJUnitTestReporter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classname: T7739297
 * Version: 1.0.0
 * Purpose:
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@NDP
@PMMS
@SLCMS
@Tag("T7739297")
@ScenarioTest
public class T7739297 extends CommonBaseTest {
    private static Browser ndpBrowser;
    private static User ndpUser;
    private static Browser slcmsBrowser;
    private static User slcmsUser;
    private static Browser licBrowser;
    private static User licUser;
    private static Browser romBrowser;
    private static User romUser;
    private static Browser testRailBrowser;
    private static User testRailUser;
    private static String productName;
    private static String productNameBase;
    private static String savedGameCode;
    private static String savedGameCodeBase;
    private static String initialCode;
    private static String initialCodeBase;
    private static CommonUIComponent currentNDPPage;
    private static String applicationIDBase;
    private static String fileRomNameBase;

    @AfterAll
    static void terminateBrowser() {
        BrowserManager.closeBrowser(ndpBrowser);
        BrowserManager.closeBrowser(slcmsBrowser);
        BrowserManager.closeBrowser(romBrowser);
        BrowserManager.closeBrowser(licBrowser);
        BrowserManager.closeBrowser(testRailBrowser);
        UserManager.releaseUser(ServiceType.NDP, ndpUser);
        UserManager.releaseUser(ServiceType.SLCMS, slcmsUser);
        UserManager.releaseUser(ServiceType.PMMS, testRailUser);
        UserManager.releaseUser(ServiceType.SEARCH, romUser);
        UserManager.releaseUser(ServiceType.NDP, licUser);
    }

    @BeforeAll
    public static void initiateBrowser() {
        ndpBrowser = BrowserManager.getNewInstance();
        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);
        testRailUser = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_DungB);
        NDPHomePage ndpHomePage = ndpBrowser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
        ndpHomePage.acceptCookie();
        NDPSignInPage signInPage = ndpHomePage.clickSignInPage();
        signInPage.signIn(ndpUser);

        productName = DataGen.getRandomProductName();
        productNameBase = DataGen.getRandomProductName();
    }

    @Test
    @Order(1)
    public void createProductBaseAndIssueGameCodeBase_TestCondition(IJUnitTestReporter testReport) {
        NDPDevelopmentHome developmentHome = ndpBrowser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = developmentHome.clickMyProducts();
        NDPCreateProductPage createProductPage = myProductsPage.createNewProduct();
        currentNDPPage = myProductsPage;
        createProductPage.selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(TextConstants.third_Party_Type)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalChinaSalesRegion()
                .selectProductName(productNameBase)
                .clickCreateButton();
        NDPProductDashboardPage myProductDashBoard = myProductsPage.clickProductByName(productNameBase);
        CommonAction.issueGameCode(myProductDashBoard, 85,TextConstants.no_Upper_Value);
        currentNDPPage = myProductDashBoard;

    }

    @Test()
    @Order(2)
    public void fillOutDashboardBase_TestCondition(IJUnitTestReporter testReport) throws Exception {
        NDPProductDashboardPage myProductDashBoard = (NDPProductDashboardPage) currentNDPPage;
        NDPReleaseInfoPage ndpReleaseInfoPage = myProductDashBoard.clickReleases().selectInitialRelease();
        NDPReleaseInfoPage releaseInfoPage = ndpReleaseInfoPage.nav().clickReleaseInfo();
        CommonAction.selectROMSameAllRegion(releaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);
        //create Feature
        NDPReleaseInfoFeaturesPage releaseInfoFeaturesPage = releaseInfoPage.nav().clickFeatures();
        CommonAction.getFeatureOptionData(ndpReleaseInfoPage, TextConstants.allOff_China, TextConstants.allOff, TextConstants.allOff, false, "", false, "");
        assertTrue(releaseInfoFeaturesPage.isAccountSpecificationsNotApplicable());

        //Create GuildLine
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = ndpReleaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoGuidelinesPage.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();

        //Create Age Rating
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = ndpReleaseInfoPage.nav().clickAgeRating();
        currentNDPPage = ndpReleaseInfoPage;

        //upload ROM
        NDPMyProductsPage myProductsPage = ndpBrowser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productNameBase);
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();

        applicationIDBase = ndpProductDashboardProductInfoTab.getApplicationID();
        savedGameCodeBase = ndpProductDashboardProductInfoTab.getGameCode();
        initialCodeBase = ndpProductDashboardProductInfoTab.getInitialCode();
        System.out.println("InitialCodeBase: " + initialCodeBase);

        romBrowser = BrowserManager.getNewInstance();
        ROMLoginPage romLoginPage = romBrowser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN, testReport);
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom
                .selectType(TextConstants.initial_Release)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .enterProductName(productNameBase)
                .enterApplicationId(applicationIDBase)
                .selectPublishingRelationShip(TextConstants.third_Party_Type)
                .enterDisplayVersion("1.0.0")
                .enterReleaseVersion("00")
                .selectRequester(TextConstants.test_User)
                .clickOnCreateButton()
                .waitIsLoaded();

        fileRomNameBase = romCreateRom.getRomFileName();
        romCreateRom.clickOnROMFileOnROMTable(applicationIDBase);
        WaitUtils.idle(5000);
        romCreateRom.clickOnUserNameMenu().clickOnLogoutButton();

        ndpBrowser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = myProductsPage.clickProductByName(productNameBase)
                .clickReleases()
                .selectInitialRelease()
                .nav()
                .clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomNameBase);
        //Request Issue
        NDPReleaseInfoIssuePage issuePage = ndpReleaseInfoPage.nav().clickIssues();

        CommonAction.enterReasonForRequest(issuePage);
        //Approval Issue TaskRequested
        licBrowser = BrowserManager.getNewInstance();
        NDPHomePage licHomePage = licBrowser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
        licHomePage.acceptCookie();
        NDPSignInPage signInPageLIC = licHomePage.clickSignInPage();
        NDPDevelopmentHome developmentHome = signInPageLIC.signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();

        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, savedGameCodeBase);
        developmentHome.clickUserMenu().clickSignOut();
    }

    @Test
    @Order(3)
    public void submitLotCheckBase_TestCondition(IJUnitTestReporter testReport) throws InterruptedException {
        NDPMyProductsPage myProductsPage = ndpBrowser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productNameBase);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        assertAll(() -> assertTrue(ndpReleaseInfoPage.nav().isReleaseInfoCompleted()),
                () -> assertTrue(ndpReleaseInfoPage.nav().isFeaturesCompleted()),
                () -> assertTrue(ndpReleaseInfoPage.nav().isGuidelinesCompleted()),
                () -> assertTrue(ndpReleaseInfoPage.nav().isAgeRatingCompleted()),
                () -> assertTrue(ndpReleaseInfoPage.nav().isTestScenariosCompleted()),
                () -> assertTrue(ndpReleaseInfoPage.nav().isROMUploadCompleted()),
                () -> assertTrue(ndpReleaseInfoPage.nav().isIssuesCompleted()),
                () -> assertTrue(ndpReleaseInfoPage.nav().isSubmitButtonActivated())
        );
        ndpReleaseInfoPage.nav().clickSubmitButton()
                .clickSubmit();
        WaitUtils.idle(3000);
    }

    @Test()
    @Order(4)
    public void approvedPassBase_TestCondition(IJUnitTestReporter testReport) throws InterruptedException {
        testRailBrowser = BrowserManager.getNewInstance();
        TestRailLoginPage testRailLoginPage = testRailBrowser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        TestRailDashboardPage testRailDashboardPage = testRailLoginPage.signInTestRail(testRailUser);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        CommonAction.setStatusRegionTestPlanOnTestRail(testRailManagerDashboardPage, initialCodeBase, LotcheckRegion.NCL.toString(), "Passed", TextConstants.OK);
        WaitUtils.idle(3000);
        testRailDashboardPage.navTopTestrail().clickOnUserNameMenu().clickOnLogoutButton();

        //approve judgment
        slcmsBrowser = BrowserManager.getNewInstance();
        slcmsUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);
        LotcheckHomePage lotcheckHomePage = slcmsBrowser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
        WaitUtils.idle(5000);
        LotcheckQueuePage lotcheckQueueNCLPage = lotcheckLandingPage.clickJudgment(LotcheckRegion.NCL);
        CommonAction.approvalJudgmentInQueueErrorPopup(lotcheckQueueNCLPage, initialCodeBase, 300, TextConstants.OK);
        WaitUtils.idle(2000);
        //lotcheckQueueNCLPage.closeMessage();
        lotcheckQueueNCLPage.clickOnUserNameMenu().clickOnLogoutButton();
    }

    @Test()
    @Order(5)
    public void createProduct(IJUnitTestReporter testReport) {
        ndpBrowser = BrowserManager.getNewInstance();
        NDPHomePage ndpHomePage = ndpBrowser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
        ndpHomePage.acceptCookie();
        NDPSignInPage signInPage = ndpHomePage.clickSignInPage();
        signInPage.signIn(ndpUser);
        NDPDevelopmentHome developmentHome = ndpBrowser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = developmentHome.clickMyProducts();
        NDPCreateProductPage createProductPage = myProductsPage.createNewProduct();
        currentNDPPage = myProductsPage;
        createProductPage.selectProductType(TextConstants.mac_Product)
                .selectPublishingRelationship(TextConstants.third_Party_Type)
                .selectProductName(productName)
                .selectPhysicalChinaSalesRegion()
                .clickCreateButton();
    }

    @Test
    @Order(6)
    public void verifyText_EN(IJUnitTestReporter testReport) {
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentNDPPage;
        NDPProductDashboardPage ndpDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab releasesTab = ndpDashboardPage.clickReleases();
        NDPReleaseInfoPage releaseInfoPage = releasesTab.selectRelease();
        releaseInfoPage.clickAccount().clickENLanguage().waitIsLoaded();
        String verifyIncludedAOC = releaseInfoPage.getTextIncludeAOC();
        String includedAOC_en = "Includes DLC *";
        NDPEditIncludesApplicationModal editIncludesApplicationModal = releaseInfoPage.clickEditIncludedApplications();
        String verifyTextNoticeEditIncludeApplication = editIncludesApplicationModal.getTextIncludeNoteEditIncludeApplication();
        String noticeEditIncludeApplication_en = "The packaged version of the application to include must have passed Lotcheck.\n" +
                "If the packaged version has not passed Lotcheck, then Lotcheck will fail.\n" +
                "It will also fail even if you specify a patch version, which does not have package support.";
        assertEquals(includedAOC_en, verifyIncludedAOC, "Display Include DLC by EN correct");
        assertTrue(verifyTextNoticeEditIncludeApplication.contains(noticeEditIncludeApplication_en), "Display notify Edit Include Application by EN correct");
        currentNDPPage = editIncludesApplicationModal;
    }

    @Test
    @Order(7)
    public void verifyText_JP(IJUnitTestReporter testReport) {
        NDPEditIncludesApplicationModal editIncludesApplicationModal = (NDPEditIncludesApplicationModal) currentNDPPage;
        NDPReleaseInfoPage releaseInfoPage = editIncludesApplicationModal.clickClose();
        releaseInfoPage.clickAccount().clickJPLanguage().waitIsLoaded();
        String verifyIncludedAOC = releaseInfoPage.getTextIncludeAOC();
        String includedAOC_jp = "追加コンテンツを含みますか？ *";
        String noticeEditIncludeApplication_jp = "含めるアプリケーションは、パッケージ版としてロットチェックに合格している必要があります。\n" +
                "パッケージ版としてロットチェックに合格していない場合、ロットチェックが不合格となります。\n" +
                "また、パッケージ対応を行っていないパッチバージョンを指定しても不合格となります。";
        releaseInfoPage.clickEditIncludedApplications();
        String verifyTextNoticeEditIncludeApplication = editIncludesApplicationModal.getTextIncludeNoteEditIncludeApplication();
        assertEquals(includedAOC_jp, verifyIncludedAOC, "Display Include DLC by JP incorrect");
        assertTrue(verifyTextNoticeEditIncludeApplication.contains(noticeEditIncludeApplication_jp), "Display notify Edit Include Application by JP incorrect");
        editIncludesApplicationModal.clickClose();
        releaseInfoPage.clickAccount().clickENLanguage().waitIsLoaded();
        currentNDPPage = releaseInfoPage;
    }

    @Test
    @Order(8)
    public void issueGameCode(IJUnitTestReporter testReport) {
        NDPMyProductsPage myProductsPage = ndpBrowser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPProductDashboardPage releaseInfoPage = myProductsPage.clickProductByName(productName);
        NDPRequestGameCodePage issueGameCode = releaseInfoPage.issueGameCode();
        issueGameCode.setExpectedLotcheckSubmissionDate()
                .setExpectedReleaseDate(85);
        NDPEditIncludesApplicationModal editIncludesApplicationModal = issueGameCode.clickEditIncludedApplications();
        editIncludesApplicationModal.clickNoIncludesDLC()
                .clickYesIncludesChinaApplications()
                .clickPleaseSelectProducts(initialCodeBase)
                .clickPleaseSelectVersion()
                .clickAdd()
                .clickConfirm();
        issueGameCode.clickIssue();
        savedGameCode = releaseInfoPage.clickProductInfo().getGameCode();
        currentNDPPage = releaseInfoPage;
    }

    @Test
    @Order(9)
    public void fillOutReleaseDashboard(IJUnitTestReporter testReport) throws Exception {
        NDPMyProductsPage myProductsPage = ndpBrowser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPProductDashboardPage ndpDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab releasesTab = ndpDashboardPage.clickReleases();
        NDPReleaseInfoPage releaseInfoPage = releasesTab.selectRelease();
        currentNDPPage = releaseInfoPage;

        CommonAction.selectROMSameAllRegion(releaseInfoPage, "TBD", "2 GB", TextConstants.english);
        // Complete ReleaseInfo Features
        NDPReleaseInfoFeaturesPage releaseInfoFeaturesPage = releaseInfoPage.nav().clickFeatures();
        //assertTrue(releaseInfoPage.nav().isReleaseInfoCompleted());
        HashMap<ProgramSpecificationsEnum, String> programSpecificationsSettings = FeatureSettingsManager.getProgramSpecificationsSettings(TextConstants.allOff_Multi);
        HashMap<LotcheckRemotePresenceEnum, String> presenceSettings = FeatureSettingsManager.getLotcheckPresenceSettings("Confirm");
        NDPReleaseInfoFeaturesProgramSpecsDialog programSpecDialog = releaseInfoFeaturesPage.clickEditProgramSpecifications();
        programSpecDialog.setMacFeatures(programSpecificationsSettings).clickSave();

        NDPReleaseInfoFeaturesLotcheckRemotePresenceDialog presenceDialog = releaseInfoFeaturesPage.clickEditLotcheckRemotePresence();
        presenceDialog.setFeatures(presenceSettings).clickSave();
        assertTrue(releaseInfoFeaturesPage.isAccountSpecificationsNotApplicable());

        //Complete ReleaseInfo Guideline
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelines = releaseInfoFeaturesPage.clickContinue();
        //assertTrue(releaseInfoPage.nav().isFeaturesCompleted());
        ndpReleaseInfoGuidelines.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();

        //Complete ReleaseInfo Age Rating
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = releaseInfoPage.nav().clickAgeRating();

        //Complete ReleaseInfo Test Scenarios
        NDPReleaseInfoTestScenarios ndpReleaseInfoTestScenarios = releaseInfoPage.nav().clickTestScenarios();

        // Complete ReleaseInfo ROM upload
        NDPMyProductsPage myProductPage = ndpBrowser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();
        String applicationID = ndpProductDashboardProductInfoTab.getApplicationID();
        savedGameCode = ndpProductDashboardProductInfoTab.getGameCode();
        initialCode = ndpProductDashboardProductInfoTab.getInitialCode();
        String basedApplication = "Application_" + applicationIDBase + "_v00.nsp";

        ROMLoginPage romLoginPage = romBrowser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN, testReport);
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom
                .selectType(TextConstants.mac_Product)
                .enterApplicationId(applicationID)
                .selectRequester(TextConstants.test_User)
                .selectBasedApplicationOrPatchFile(basedApplication)
                .clickOnCreateButton()
                .waitIsLoaded();
        String fileRomName = romCreateRom.getRomFileName();
        System.out.println(fileRomName);
        romCreateRom.clickOnFirstROMFileOnROMTable();
        WaitUtils.idle(8000);

        ndpBrowser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = myProductsPage.clickProductByName(productName)
                .clickReleases()
                .selectRelease()
                .nav()
                .clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);

        // Complete ReleaseInfo Issues
        NDPReleaseInfoIssuePage issuePage = releaseInfoPage.nav().clickIssues();
        CommonAction.enterReasonForRequest(issuePage);

        //Approval Issue TaskRequested
        NDPHomePage licHomePage = licBrowser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
        NDPSignInPage signInPageLIC = licHomePage.clickSignInPage();
        NDPDevelopmentHome developmentHome = signInPageLIC.signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, savedGameCode);
    }

    @Test
    @Order(10)
    public void submitToLotCheck(IJUnitTestReporter testReport) {
        NDPMyProductsPage myProductsPage = ndpBrowser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectRelease();
        assertAll(() -> assertTrue(ndpReleaseInfoPage.nav().isReleaseInfoCompleted()),
                () -> assertTrue(ndpReleaseInfoPage.nav().isFeaturesCompleted()),
                () -> assertTrue(ndpReleaseInfoPage.nav().isGuidelinesCompleted()),
                () -> assertTrue(ndpReleaseInfoPage.nav().isAgeRatingCompleted()),
                () -> assertTrue(ndpReleaseInfoPage.nav().isTestScenariosCompleted()),
                () -> assertTrue(ndpReleaseInfoPage.nav().isROMUploadCompleted()),
                () -> assertTrue(ndpReleaseInfoPage.nav().isIssuesCompleted()),
                () -> assertTrue(ndpReleaseInfoPage.nav().isSubmitButtonActivated())
        );
        ndpReleaseInfoPage.nav().clickSubmitButton()
                .clickSubmit();
        WaitUtils.idle(3000);
    }

    @Test()
    @Order(11)
    public void setStatusTestPlanOnTestRail(IJUnitTestReporter testReport) throws InterruptedException {
        TestRailLoginPage testRailLoginPage = testRailBrowser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        TestRailDashboardPage testRailDashboardPage = testRailLoginPage.signInTestRail(testRailUser);
        TestRailManagerDashboardPage testRailManagerDashboardPage = testRailDashboardPage.navTopTestrail().navigateToManagerDashboard();
        CommonAction.setStatusRegionTestPlanOnTestRail(testRailManagerDashboardPage, initialCode, LotcheckRegion.NCL.toString(), "Passed", TextConstants.OK);
        //approve judgment
        LotcheckHomePage lotcheckHomePage = slcmsBrowser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
        WaitUtils.idle(5000);
        LotcheckQueuePage nclLotCheckQueue = lotcheckLandingPage.clickJudgment(LotcheckRegion.NCL);
        CommonAction.approvalJudgmentStatus(nclLotCheckQueue, initialCode, 300, TextConstants.OK, false, "1");
    }

    @Test
    @Order(12)
    public void createNewRelease(IJUnitTestReporter testReport) {
        NDPMyProductsPage myProductsPage = ndpBrowser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = ndpProductDashboardPage.clickReleases();
        NDPCreateReleasePage ndpCreateReleasePage = ndpProductDashboardReleasesTab.clickCreateNewReleaseButton();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpCreateReleasePage
                .enterDisplayVersion("1.0.1")
                .selectExpectedSubmissionDate(5)
                .selectExpectedReleaseDate(25)
                .enterDescribeTheFeaturesPlanned(TextConstants.enterDescribeTheFeaturesPlanned)
                .enterDescribeTheBugsIssuesPlanned(TextConstants.enterDescribeTheBugsIssuesPlanned)
                .clickCreateButton();
        currentNDPPage = ndpReleaseInfoPage;
    }

    @Test
    @Order(13)
    public void verifyYesOptionLocked(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentNDPPage;
        NDPEditIncludesApplicationModal editIncludesApplicationModal = releaseInfoPage.clickEditIncludedApplications();
        assertAll(() -> assertTrue(editIncludesApplicationModal.isYesOptionIsSelected()));
        assertAll(() -> assertTrue(editIncludesApplicationModal.isNoOptionIsDisable()));
        editIncludesApplicationModal.clickYesIncludesChinaApplications();
        assertAll(() -> assertTrue(editIncludesApplicationModal.isYesOptionIsSelected()));
        assertAll(() -> assertTrue(editIncludesApplicationModal.isNoOptionIsDisable()));
    }
}
