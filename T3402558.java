package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.data.DataGen;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthPage;
import net.nintendo.automation.ui.models.iarc.IARCQuestionnaireFinalRatingPage;
import net.nintendo.automation.ui.models.iarc.IARCQuestionnairePage;
import net.nintendo.automation.ui.models.lcms.LotcheckHomePage;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.ui.models.lcms.LotcheckQueuePage;
import net.nintendo.automation.ui.models.lcms.LotcheckTestPlanRow;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionProductInformation;
import net.nintendo.automation.ui.models.ndp.NDPCreateProductPage;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPLicensingWaiverRequestsTab;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.NDPSignInPage;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardProductInfoTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardReleasesTab;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoAgeRating;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoFeaturesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoGuidelinesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoIssuePage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoROMUpload;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoTestScenarios;
import net.nintendo.automation.ui.models.rom.ROMCreateRom;
import net.nintendo.automation.ui.models.rom.ROMLoginPage;
import net.nintendo.automation.ui.models.testrail.TestRailLoginPage;
import net.nintendo.automation.ui.models.testrail.TestRailManagerDashboardPage;
import net.nintendo.automation.ui.users.User;
import net.nintendo.automation.ui.users.UserManager;
import net.nintendo.automation.utils.WaitUtils;
import net.nintendo.automation_core.common_client.enums.ServiceType;
import net.nintendo.automation_core.common_client.enums.UserType;
import net.nintendo.automation_core.common_utils.enums.LotcheckRegion;
import net.nintendo.automation_core.extent_reports.extensions.IJUnitTestReporter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classname: T3402558
 * Version: 1.0.0
 * Purpose:
 */
@Tag("T3402558")
//@ScenarioTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3402558 extends CommonBaseTest {
    private static Browser browser;
    private static User ndpUser;
    private static User licUser;
    private static User slcmsUser;
    private static User romUser;
    private static String productName;
    private static String savedGameCode;
    private static String initialCode;
    private static String applicationID;
    private static ArrayList<String> RatingAgencyList = new ArrayList<>();
    private static ArrayList<String> RatingValueList = new ArrayList<>();
    private static ArrayList<String> RatingDescriptorsList = new ArrayList<>();
    private static List<String> region = Arrays.asList(LotcheckRegion.NOA.toString(), LotcheckRegion.NOE.toString());


    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.ADMIN_USER);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);
        slcmsUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);

        NDPHomePage ndpHomePage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
        ndpHomePage.acceptCookie();
        NDPSignInPage signInPage = ndpHomePage.clickSignInPage();
        signInPage.signIn(ndpUser);
        productName = DataGen.getRandomProductName();

    }

    @AfterAll
    static void terminateBrowser() {
        BrowserManager.closeBrowser(browser);
        UserManager.releaseUser(ServiceType.NDP, ndpUser);
        UserManager.releaseUser(ServiceType.NDP, licUser);
        UserManager.releaseUser(ServiceType.SLCMS, slcmsUser);
        UserManager.releaseUser(ServiceType.SEARCH, romUser);
    }

    @Test
    @Order(1)
    public void createProduct(IJUnitTestReporter testReport) {
        NDPDevelopmentHome developmentHome = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = developmentHome.clickMyProducts();
        NDPCreateProductPage createProductPage = myProductsPage.createNewProduct();
        createProductPage.selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(TextConstants.third_Party_Type)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalAmericasSalesRegion()
                .selectDigitalJapanSalesRegion()
                .selectDigitalEuropeSalesRegion()
                .selectProductName(productName)
                .selectProductNameJp(productName)
                .selectProductNameKana(TextConstants.productKana)
                .clickCreateButton();
    }

    @Test
    @Order(2)
    public void issueGameCode(IJUnitTestReporter testReport) {
        NDPMyProductsPage myProductsPage = browser.getPage(NDPMyProductsPage.class, testReport);
        NDPProductDashboardPage myProductDashBoard = myProductsPage.clickProductByName(productName);
        CommonAction.issueGameCode(myProductDashBoard, 65,"NO");
        NDPProductDashboardProductInfoTab productInfoTab = myProductDashBoard.clickProductInfo();
        savedGameCode = productInfoTab.getGameCode();
        initialCode = productInfoTab.getInitialCode();
    }

    @Test()
    @Order(3)
    public void selectAgeRatingFromTheRightNav(IJUnitTestReporter testReport) {
        NDPProductDashboardPage myProductDashBoard = browser.getPage(NDPProductDashboardPage.class, testReport);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = myProductDashBoard.clickReleases();
        NDPReleaseInfoPage releaseInfoDashboard = ndpProductDashboardReleasesTab.selectInitialRelease();
    }

    @Test()
    @Order(4)
    public void completeAddIARCRating(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage releaseInfoDashboard = browser.getPage(NDPReleaseInfoPage.class, testReport);
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = releaseInfoDashboard.nav().clickAgeRating();
        ndpReleaseInfoAgeRating.clickOnAddIARCRatingButton()
                .enterContactEmailAddress(ndpUser.getUserName()+TextConstants.inbucket_Suffix)
                .enterPublicEmailAddress(ndpUser.getUserName()+TextConstants.inbucket_Suffix)
                .selectIARCRequestNewIARCCertificate()
                .clickRequestNewIarcContinue();
        WaitUtils.idle(6000);
        browser.switchToTabByTitle(IARCQuestionnairePage.pageTitle);
        IARCQuestionnaireFinalRatingPage questionnaireFinalRating = browser.getPage(IARCQuestionnairePage.class, testReport)
                        .selectGame()
                        .selectViolenceOrBloodNoDoesTheGameContain()
                        .selectFearNoDoesTheGameContain()
                        .selectSexualityNoDoesTheGameContain()
                        .selectSimulatedGamblingNoDoesTheGameContain()
                        .selectLanguageNoDoesTheGameContain()
                        .selectControlledSubstanceNoDoesTheGameContain()
                        .selectCrudeHumorNoDoesTheGameContain()
                        .selectMiscellaneousNoDoesTheGameNativelyAllowUsersToInteract()
                        .selectMiscellaneousNoDoesTheGameShareTheUsers()
                        .selectMiscellaneousNoDoesTheGameAllowUsersToPurchase()
                        .selectMiscellaneousNoDoesTheGameContainAnySwatikas()
                        .selectMiscellaneousNoDoesTheGameContainAnyContent()
                        .selectMiscellaneousNoDoesTheGameContainDetailedDescriptions()
                        .selectMiscellaneousNoDoesTheGameAdvocate()
                        .clickArrowIconNext()
                        .clickNext();
        browser.closeTabByTitle(IARCQuestionnairePage.pageTitle);
        ndpReleaseInfoAgeRating.clickViewIARCAgeRatings();
    }

    @Test()
    @Order(5)
    public void completeAddStandardRating(IJUnitTestReporter testReport) {
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReport);
        File certificateAttachmentFile = new File("src/test/resources/DataFile/Judgement.pdf");
        String certificateId = "12345678";
        ndpReleaseInfoAgeRating.clickOnAddStandardRatingButton()
                .selectStandardAgeRating("BBFC")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectRatingValue("U")
                .uploadCertificateAttachment(certificateAttachmentFile)
                .enterCertificateID(certificateId)
                .clickOnSaveButtonInEditStandardRating();
        ndpReleaseInfoAgeRating.clickOnAddStandardRatingButton()
                .selectStandardAgeRating("OFLC")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectRatingValue("G—General")
                .selectDescriptorAndNotice("Dark Themes")
                .uploadCertificateAttachment(certificateAttachmentFile)
                .enterCertificateID(certificateId)
                .clickOnSaveButtonInEditStandardRating()
                .verifyAddStandardRatingButtonDisabled();

        int collectNumberRating = ndpReleaseInfoAgeRating.collectNumberRating();
        for (int i = 0; i < collectNumberRating; i++) {
            RatingAgencyList.add(ndpReleaseInfoAgeRating.getRatingAgency(i));
            RatingValueList.add(ndpReleaseInfoAgeRating.getRatingValue(i));
            RatingDescriptorsList.add(ndpReleaseInfoAgeRating.getRatingDescriptors(i));
        }
    }

    @Test()
    @Order(6)
    public void fillOutReleaseDashBoard(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReport);
        // Complete ReleaseInfo
        NDPReleaseInfoPage releaseInfoPage = ndpReleaseInfoAgeRating.nav().clickReleaseInfo();
        CommonAction.selectROMSameAllRegion(releaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);

        // Complete ReleaseInfo Features
        NDPReleaseInfoFeaturesPage releaseInfoFeaturesPage = releaseInfoPage.nav().clickFeatures();
        CommonAction.getFeatureOptionData(releaseInfoPage, TextConstants.allOff_EU, TextConstants.allOff,
                TextConstants.allOff, false, "", false, "");

        //Complete ReleaseInfo Guideline
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelines = releaseInfoFeaturesPage.clickContinue();
        ndpReleaseInfoGuidelines.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();

        // Complete ReleaseInfo ROM upload
        NDPMyProductsPage myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByGameCode(savedGameCode);
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();
        applicationID = ndpProductDashboardProductInfoTab.getApplicationID();

        ROMLoginPage romLoginPage = browser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN, testReport);
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom
                .selectRequester(TextConstants.test_User)
                .selectType(TextConstants.initial_Release)
                .selectMPACheckbox()
                .selectNumberOfProgram("11")
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .enterProductName(productName)
                .enterApplicationId(applicationID)
                .selectPublishingRelationShip(TextConstants.third_Party_Type)
                .enterDisplayVersion("1.0.0")
                .enterReleaseVersion("00")
                .selectCERORating("CERO IARC: 3+")
                .selectClassIndRating("ClassInd: Livre (General Audiences)")
                .selectESRBRating("ESRB: EVERYONE")
                .selectPEGIRating("PEGI: PEGI 3")
                .selectRussiaRating("Russian: 0+")
                .selectUSKRating("USK: 0")
                .selectACBRating("ACB: G—General")
                .selectBBFCRating("BBFC: U")
                .selectOFLCRating("OFLC: G—General")
                .clickOnCreateButton()
                .waitIsLoaded();
        String fileRomName = romCreateRom.getRomNameTable(applicationID);
        romCreateRom.clickOnROMFileOnROMTable(fileRomName);
        WaitUtils.idle(5000);

        browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = myProductsPage.clickProductByGameCode(savedGameCode)
                .clickReleases()
                .selectInitialRelease()
                .nav()
                .clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);

        //Complete ReleaseInfo Test Scenarios
        NDPReleaseInfoTestScenarios testScenarios = ndpReleaseInfoROMUpload.nav().clickTestScenarios();
        testScenarios.expandAllSections()
                .expandAllTestScenes()
                .enterSubjectForProgramSpecificationsHowToStartUpTheIncludedPrograms("Subject", 0)
                .enterExplainForProgramSpecificationsHowToStartUpTheIncludedPrograms("Explain", 0)
                .clickOnSaveButton();

        // Complete ReleaseInfo Issues
        NDPReleaseInfoIssuePage issuePage = releaseInfoPage.nav().clickIssues();
        CommonAction.enterReasonForRequest(issuePage);

        //Approval Issue TaskRequested
        NDPDevelopmentHome developmentHome = releaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, savedGameCode);
        developmentHome = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReport).clickUserMenuDropdownList().clickSignOutButton()
                .clickSignInPage().signIn(ndpUser);
        //Confirm submit button is actived
        developmentHome.clickMyProducts().clickProductByGameCode(savedGameCode).clickReleases().selectInitialRelease();
        assertAll(() -> assertTrue(releaseInfoPage.nav().isReleaseInfoCompleted()),
                () -> assertTrue(releaseInfoPage.nav().isFeaturesCompleted()),
                () -> assertTrue(releaseInfoPage.nav().isGuidelinesCompleted()),
                () -> assertTrue(releaseInfoPage.nav().isAgeRatingCompleted()),
                () -> assertTrue(releaseInfoPage.nav().isTestScenariosCompleted()),
                () -> assertTrue(releaseInfoPage.nav().isROMUploadCompleted()),
                () -> assertTrue(releaseInfoPage.nav().isIssuesCompleted()),
                () -> assertTrue(releaseInfoPage.nav().isSubmitButtonActivated()));
        releaseInfoPage.nav().selectSubmitButton()
                .clickSubmitReview().openNewTabAndCloseCurrentTab();
        myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        myProductsPage.clickProductByGameCode(savedGameCode).clickReleases().selectInitialRelease();
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

    @Test
    @Order(8)
    public void locateSubmissionLevelOfLotCheckReport(IJUnitTestReporter testReport) {
        LotcheckHomePage lotcheckHomePage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage landingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
        LotcheckQueuePage lotcheckQueuePage = landingPage.clickTesterAssignment(LotcheckRegion.NCL);
        CommonAction.findProductInLCQueue(landingPage, 30, LotcheckRegion.NCL.toString(), initialCode);
        lotcheckQueuePage.clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
    }

    @Test
    @Order(9)
    public void viewAgeRatingOnProductInformationTab(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = browser.getPage(LotcheckReportSubmissionOverview.class, testReport);
        LotcheckReportSubmissionProductInformation productInformation = lotcheckReportSubmissionOverview.navBar().clickProductInformation();
        WaitUtils.idle(3000);
        productInformation.navBar().waitForReleaseSubmitted();
        ArrayList<String> SLCMSRatingValueList = new ArrayList<>();
        ArrayList<String> SLCMSRatingDescriptorsList = new ArrayList<>();
        int collectNumberRating = productInformation.expandAgeRating().collectNumberRating();
        WaitUtils.idle(3000);
        for (int i = 0; i < collectNumberRating; i++) {
            SLCMSRatingValueList.add(productInformation.getRatingValue(i));
            SLCMSRatingDescriptorsList.add(productInformation.getRatingDescriptors(i));
        }
        assertEquals(RatingValueList, SLCMSRatingValueList);
        assertEquals(RatingDescriptorsList, SLCMSRatingDescriptorsList);
        productInformation.selectDownloadAgeRatingCertificate();
    }

    @Test()
    @Order(10)
    public void judgmentFinalSuspendedForSubmission(IJUnitTestReporter testReport) throws InterruptedException {
        LotcheckReportSubmissionProductInformation productInformation = browser.getPage(LotcheckReportSubmissionProductInformation.class, testReport);
        TestRailLoginPage testRailLoginPage = productInformation.navBar().switchToTestRailLoginPage();
        TestRailManagerDashboardPage managerDashboardPage = testRailLoginPage.signInTestRail(TextConstants.trAdminNCL_Email, TextConstants.trAdmin_Password)
                .navTopTestrail()
                .navigateToManagerDashboard()
                .waitingLoadingTestPlan()
                .clickOkInErrorPopup()
                .actionFill()
                .enterTestPlanNameFilter(initialCode)
                .selectTestPlanRegionFilter(LotcheckRegion.NOA.toString())
                .selectTestPlanRegionFilter(LotcheckRegion.NOE.toString())
                .clickOnApplyFiltersButton()
                .waitingLoadingTestPlan()
                .clickOnPlannedTestDateNotSetBar();
        int num = managerDashboardPage.collectNumberTestPlan();

        for (int i = 0; i < num; i++) {
            managerDashboardPage.actionFill().clickOnApplyFiltersButton()
                    .waitingLoadingTestPlan()
                    .clickOnPlannedTestDateNotSetBar()
                    .clickOnPadlockButtonOfTheFirstTestPlan()
                    .clickOnYesButtonOnConfirmationModal();
        }
        managerDashboardPage.topNav().clickOnUserNameMenu().clickOnLogoutButton();
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckQueuePage lotcheckQueueNCLPage = lotcheckLandingPage.clickJudgment(LotcheckRegion.NCL);
        CommonAction.approvalJudgmentInQueueErrorPopup(lotcheckQueueNCLPage, initialCode, 300, TextConstants.Suspended);
        lotcheckQueueNCLPage.refreshPageSLCMS().clickOnUserNameMenu().clickOnLogoutButton();

        LotcheckHomePage lotcheckHomePage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReport);
        lotcheckHomePage.continueToLogin().loginWithOvdLink(TextConstants.lc_Admin_NOA, TextConstants.lc_Admin_NOA_Password);
        LotcheckQueuePage lotcheckQueueNOAPage = lotcheckLandingPage.clickJudgment(LotcheckRegion.NOA);
        CommonAction.approvalJudgmentInQueueErrorPopup(lotcheckQueueNOAPage, initialCode, 300, TextConstants.Suspended);
        lotcheckQueueNOAPage.refreshPageSLCMS().clickOnUserNameMenu().clickOnLogoutButton();

        lotcheckHomePage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReport);
        lotcheckHomePage.continueToLogin().loginWithOvdLink(TextConstants.lc_Admin_NOE, TextConstants.lc_Admin_NOE_Password);
        LotcheckQueuePage lotcheckQueueNOEPage = lotcheckLandingPage.clickJudgment(LotcheckRegion.NOE);
        CommonAction.approvalJudgmentInQueueErrorPopup(lotcheckQueueNOEPage, initialCode, 300, TextConstants.Suspended);
        lotcheckQueueNOEPage.refreshPageSLCMS();
    }

    @Test()
    @Order(11)
    public void gotoFinishQueueInAnyRegion(IJUnitTestReporter testReport) {
        LotcheckQueuePage noeLotCheckQueue = browser.getPage(LotcheckQueuePage.class, testReport);
        LotcheckLandingPage lotcheckLandingPage = noeLotCheckQueue.navLandingPage();
        noeLotCheckQueue = lotcheckLandingPage.navigationToFinishedQueue(LotcheckRegion.NOE.toString());
        LotcheckTestPlanRow noeRowTestPlan = noeLotCheckQueue.searchByInitialCode(initialCode).clickSearchIcon().waitUntilTableVisible();
        assertAll(() -> assertTrue(noeRowTestPlan.verifyTestPlanNumberDisplay(1)));

        LotcheckQueuePage noaLotCheckQueue = lotcheckLandingPage.navigationToFinishedQueue(LotcheckRegion.NOA.toString());
        LotcheckTestPlanRow noaRowTestPlan = noaLotCheckQueue.searchByInitialCode(initialCode).clickSearchIcon().waitUntilTableVisible();
        assertAll(() -> assertTrue(noaRowTestPlan.verifyTestPlanNumberDisplay(1)));

        LotcheckQueuePage nclLotCheckQueue = lotcheckLandingPage.navigationToFinishedQueue(LotcheckRegion.NCL.toString());
        LotcheckTestPlanRow nclRowTestPlan = nclLotCheckQueue.searchByInitialCode(initialCode).clickSearchIcon().waitUntilTableVisible();
        assertAll(() -> assertTrue(nclRowTestPlan.verifyTestPlanNumberDisplay(1)));

        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = nclLotCheckQueue.navigateToSubmissionLevelOnFirstTestPlan();
    }

    @Test()
    @Order(12)
    public void verifyDisplayAgeRatingOnProductInformation(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = browser.getPage(LotcheckReportSubmissionOverview.class, testReport);
        LotcheckReportSubmissionProductInformation productInformation = lotcheckReportSubmissionOverview.navBar().clickProductInformation();
        WaitUtils.idle(3000);
        ArrayList<String> SLCMSRatingValueList = new ArrayList<>();
        ArrayList<String> SLCMSRatingDescriptorsList = new ArrayList<>();
        int collectNumberRating = productInformation.expandAgeRating().collectNumberRating();
        WaitUtils.idle(3000);
        for (int i = 0; i < collectNumberRating; i++) {
            SLCMSRatingValueList.add(productInformation.getRatingValue(i));
            SLCMSRatingDescriptorsList.add(productInformation.getRatingDescriptors(i));
        }
        assertAll(() -> assertEquals(RatingValueList, SLCMSRatingValueList),
                () -> assertEquals(RatingDescriptorsList, SLCMSRatingDescriptorsList));

        productInformation.selectDownloadAgeRatingCertificate();
    }

}
