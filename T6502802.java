package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.data.DataGen;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.email.EmailPage;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthPage;
import net.nintendo.automation.ui.models.lcms.LotcheckHomePage;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.ui.models.lcms.LotcheckQueuePage;
import net.nintendo.automation.ui.models.lcms.LotcheckSubmissionSearchPage;
import net.nintendo.automation.ui.models.lcms.LotcheckTestPlanRow;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportNumberOverview;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportProductProductInformation;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportTestPlanOverview;
import net.nintendo.automation.ui.models.ndp.NDPCreateProductPage;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPLicensingWaiverRequestsTab;
import net.nintendo.automation.ui.models.ndp.NDPMyAccountPage;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.NDPSignInPage;
import net.nintendo.automation.ui.models.ndp.NDPViewTaskPage;
import net.nintendo.automation.ui.models.ndp.admin.NDPAdminPage;
import net.nintendo.automation.ui.models.ndp.admin.NDPAgreementPage;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_internal.NDPCustomAgreementsPage;
import net.nintendo.automation.ui.models.ndp.existing_internal.NDPInternalPage;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPCreateReleasePage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPEditChinaRegionDataDialog;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Classname: T6502802
 * Version: 1.0.0
 * Purpose:
 */
@Tag("T6502802")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@ScenarioTest
public class T6502802 extends CommonBaseTest {
    private static Browser browser;
    private static Browser romBrowser;
    private static User romUser;
    private static User ndpUser;
    private static String waveUserName = "testingio277";
    private static User slcmsDataManagerUser;
    private static User slcmsUser;
    private static User testrailUser;
    private static User licUser;
    private static User licPowerUser;

    private static String productName;
    private static String applicationID;
    private static String initialCode;
    private static String gameCode;
    private static String fileRomName;
    private static String nameOrg;
    private static String uuidOrg;
    private static String content = "CONTENTLESSTHAN30";
    private static String isbnNumber = "ISBN Number";
    private static String gappNumber = "GAPP Number";
    private static String publishingEntity = "Publishing Entity";
    private static String operatingEntity = "Operating Entity";
    private static String copyrightRegisterNumber = "Copyright Register Number";
    private static String fileName = "csvResults.csv";
    private static String numberTestPlan;


    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_AnhBTK2);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
        slcmsUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);
        slcmsDataManagerUser = UserManager.get(ServiceType.SLCMS, UserType.LOTCHECK_ADMIN_NCL, TextConstants.lc_data_manager_NCL);
        testrailUser = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER);
        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);
        licPowerUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_pwr_user_NCL);
        productName = DataGen.getRandomProductName();

        NDPHomePage ndpHomePage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
        ndpHomePage.acceptCookie();
        NDPSignInPage signInPage = ndpHomePage.clickSignInPage();
        signInPage.signIn(ndpUser);
    }

    @AfterAll
    static void terminateBrowser() {
        BrowserManager.closeBrowser(browser);
        BrowserManager.closeBrowser(romBrowser);
        UserManager.releaseUser(ServiceType.NDP, ndpUser);
        UserManager.releaseUser(ServiceType.SLCMS, slcmsUser);
        UserManager.releaseUser(ServiceType.SLCMS, slcmsDataManagerUser);
        UserManager.releaseUser(ServiceType.SEARCH, romUser);
        UserManager.releaseUser(ServiceType.NDP, licUser);
        UserManager.releaseUser(ServiceType.NDP, licPowerUser);
        UserManager.releaseUser(ServiceType.PMMS, testrailUser);
    }

    @Test
    @Order(1)
    public void createProduct_Step(IJUnitTestReporter testReport) {
        NDPMyProductsPage myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPCreateProductPage createProductPage = myProductsPage.createNewProduct();
        createProductPage.selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(TextConstants.third_Party_Type)
                .selectTargetDeliveryFormat(TextConstants.both_Target_Type)
                .selectDigitalChinaSalesRegion()
                .selectPhysicalChinaSalesRegion()
                .selectProductName(productName)
                .clickCreateButton();
    }

    @Test
    @Order(2)
    public void performIssueGameCode_Step(IJUnitTestReporter testReport) {
        NDPMyProductsPage myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        CommonAction.issueGameCode(ndpProductDashboardPage, 60, TextConstants.no_Upper_Value);
    }

    @Test
    @Order(3)
    public void inputEightFieldsForChinaRegionData_Step(IJUnitTestReporter testReport) {
        NDPProductDashboardProductInfoTab productInfoTabPage = browser.getPage(NDPProductDashboardProductInfoTab.class, testReport);
        NDPEditChinaRegionDataDialog editChinaRegionDataDialog = productInfoTabPage.clickEditChinaData();
        editChinaRegionDataDialog.enterFileNoticeISBN(new File("src/test/resources/DataFile/Judgement.pdf"))
                .enterNumberISBN(isbnNumber)
                .enterNumberGAPP(gappNumber)
                .enterPublishingEntity(publishingEntity)
                .enterOperatingEntity(operatingEntity)
                .enterCopyrightOutsideChina(TextConstants.yes_Value)
                .enterReplyToCopyrightFile(new File("src/test/resources/DataFile/test_doc.docx"))
                .enterCopyrightRegisterNumber(copyrightRegisterNumber)
                .clickSave();
    }

    @Test
    @Order(4)
    public void completeReleaseDashboardAndSubmitToLotcheck_Step(IJUnitTestReporter testReport) {
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = browser.getPage(NDPProductDashboardProductInfoTab.class, testReport);
        applicationID = ndpProductDashboardProductInfoTab.getApplicationID();
        gameCode = ndpProductDashboardProductInfoTab.getGameCode();
        initialCode = ndpProductDashboardProductInfoTab.getInitialCode();
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = browser.getPage(NDPProductDashboardPage.class, testReport).clickReleases();
        NDPReleaseInfoPage ndpReleaseInfoPage = ndpProductDashboardReleasesTab.selectInitialRelease();
        //create release info
        NDPReleaseInfoPage releaseInfoPage = ndpReleaseInfoPage.nav().clickReleaseInfo();
        CommonAction.selectROMSameAllRegion(releaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);
        // //create Feature
        CommonAction.getFeatureOptionData(releaseInfoPage, TextConstants.allOff_China, TextConstants.allOff, TextConstants.allOff, false, "", false, "");
        //Create GuildLine
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = ndpReleaseInfoPage.nav().clickGuidelines();
        ndpReleaseInfoGuidelinesPage.selectCompliesWithAllOfTheAboveGuidelines().clickOnSaveButton();
        //Create Age Rating
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = ndpReleaseInfoPage.nav().clickAgeRating();
        //create file ROM
        romBrowser = BrowserManager.getNewInstance();
        ROMLoginPage romLoginPage = romBrowser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN, testReport);
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom
                .selectType(TextConstants.initial_Release)
                .selectTargetDeliveryFormat(TextConstants.both_Target_Type)
                .enterProductName(productName)
                .enterApplicationId(applicationID)
                .selectPublishingRelationShip(TextConstants.third_Party_Type)
                .enterDisplayVersion("1.0.0")
                .enterReleaseVersion("00")
                .selectRequester(TextConstants.test_User)
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
    }

    @Test()
    @Order(5)
    public void verifySubmitToLotCheck_Expected(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage ndpReleaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        assertEquals(TextConstants.submitToLotcheck, ndpReleaseInfoPage.getStatusValue(), TextConstants.submitSuccess);
    }

    @Test
    @Order(6)
    public void accessToInbucket_Step(IJUnitTestReporter testReport) {
        NDPMyAccountPage ndpMyAccountPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport).clickOnUserNameMenu().clickManagerAccountButton();
        nameOrg = ndpMyAccountPage.getOrganizationName();
        EmailPage emailPage = browser.openURL(EmailPage.class, PageState.PRE_LOGIN, testReport);
        emailPage.signInEmail(waveUserName);
    }

    @Test
    @Order(7)
    public void checkEmailSentToWaveUser_Step(IJUnitTestReporter testReport) {
        EmailPage emailPage = browser.getPage(EmailPage.class, testReport);
        String emailName = "[NDP][Submission] " + gameCode + " 00.00 " + productName + " (" + nameOrg + ")";
        System.out.println(emailName);
        int collectNumber = emailPage.collectNumberEmail();
        for (int i = collectNumber - 1; i < 0; i--) {
            if (emailPage.getValueEmail(i) == emailName) {
                break;
            }
        }
        emailPage.clickEmailByName(emailName).selectHTML();
    }

    @Test
    @Order(8)
    public void logOutPartnerAndLogInLotcheck_Step(IJUnitTestReporter testReport) {
        browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport)
                .clickOnUserNameMenu()
                .clickOnSignOutButton();
        LotcheckHomePage lotcheckHomePage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        hyruleAuthPage.loginWithOvdLink(slcmsUser);
    }

    @Test
    @Order(9)
    public void navigateToSubmissionSearch_Step(IJUnitTestReporter testReport) {
        LotcheckLandingPage lotcheckLandingPage = browser.getPage(LotcheckLandingPage.class, testReport);
        lotcheckLandingPage.navigateToSubmissionSearch();
    }

    @Test
    @Order(10)
    public void verifySubmissionSearchPage_Expected(IJUnitTestReporter testReport) {
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = browser.getPage(LotcheckSubmissionSearchPage.class, testReport);
        String title = lotcheckSubmissionSearchPage.getTitle();
        assertEquals("Submission Search - LCMS", title, "In the Submission Search page is accessible.");
    }

    @Test
    @Order(11)
    public void selectFilter_Step(IJUnitTestReporter testReport) {
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = browser.getPage(LotcheckSubmissionSearchPage.class, testReport);
        lotcheckSubmissionSearchPage.selectSearchIcon().selectOptionOfFilterByFilterName("Submission Status", "OK");
    }

    @Test
    @Order(12)
    public void verifySelectFilter_Expected(IJUnitTestReporter testReport) {
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = browser.getPage(LotcheckSubmissionSearchPage.class, testReport);
        assertEquals(": OK", lotcheckSubmissionSearchPage.currentFilterValue("Submission Status"));
        boolean submissionStatus = false;
        for (int i = 1; i < 51; i++) {
            String valueSubmissionStatus = lotcheckSubmissionSearchPage.getValueOfSubmissionTable(i, 9);
            if (!valueSubmissionStatus.equalsIgnoreCase(TextConstants.OK)) {
                submissionStatus = true;
                break;
            }
        }
        assertTrue(submissionStatus, "The filter conditions configured do not apply");
    }

    @Test
    @Order(13)
    public void selectSearchIcon_Step(IJUnitTestReporter testReport) {
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = browser.getPage(LotcheckSubmissionSearchPage.class, testReport);
        lotcheckSubmissionSearchPage.selectSearchIcon();
    }

    @Test
    @Order(14)
    public void verifyAfterSelectSearchIcon_Expected(IJUnitTestReporter testReport) {
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = browser.getPage(LotcheckSubmissionSearchPage.class, testReport);
        boolean submissionStatus = true;
        for (int i = 1; i < 51; i++) {
            String valueSubmissionStatus = lotcheckSubmissionSearchPage.getValueOfSubmissionTable(i, 9);
            if (!valueSubmissionStatus.equalsIgnoreCase("OK")) {
                submissionStatus = false;
                break;
            }
        }
        assertTrue(submissionStatus, "The filter conditions configured do not apply until the \"run search\" button is clicked");
    }

    @Test
    @Order(15)
    public void performRefreshPage_Step(IJUnitTestReporter testReport) {
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = browser.getPage(LotcheckSubmissionSearchPage.class, testReport);
        browser.refresh();
    }

    @Test
    @Order(16)
    public void VerifyAfterRefreshPage_Expected(IJUnitTestReporter testReport) {
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = browser.getPage(LotcheckSubmissionSearchPage.class, testReport);
        assertEquals(": All", lotcheckSubmissionSearchPage.currentFilterValue("Submission Status"));
    }

    @Test
    @Order(17)
    public void selectAddSubmissionJudgementDateFilter_Step(IJUnitTestReporter testReport) {
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = browser.getPage(LotcheckSubmissionSearchPage.class, testReport);
        lotcheckSubmissionSearchPage.selectFilterByName("More")
                .chooseFilterOnMoreByName("Submission Judgment Date")
                .typeTabFilter("More");
    }

    @Test
    @Order(18)
    public void enterDateValueForSubmissionJudgementDateFilterAndSearch_Step(IJUnitTestReporter testReport) {
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = browser.getPage(LotcheckSubmissionSearchPage.class, testReport);
        lotcheckSubmissionSearchPage.selectFromDateAndToDateByFilterName("Submission Judgment Date", 2, 10)
                .clickSearchIcon();
    }

    @Test
    @Order(19)
    public void verifyAfterSubmissionJudgementDateFilterAndSearch_Expected(IJUnitTestReporter testReport) {
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = browser.getPage(LotcheckSubmissionSearchPage.class, testReport);
        assertAll(() -> assertNotEquals("All ", lotcheckSubmissionSearchPage.currentFilterValueOfDateFilter("Submission Judgment Date")),
                () -> assertTrue(lotcheckSubmissionSearchPage.isNoResultFound(), "Submission Date filters will be applied and display results."));
    }

    @Test
    @Order(20)
    public void navigateToOverallQueue_Step(IJUnitTestReporter testReport) {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        lotcheckLandingPage.navigationToOverallLotcheckQueue(LotcheckRegion.NCL.toString());
    }

    @Test
    @Order(21)
    public void enterValueOfDateFieldAndSearch_Step(IJUnitTestReporter testReport) {
        LotcheckQueuePage lotcheckQueuePage = browser.getPage(LotcheckQueuePage.class, testReport);
        numberTestPlan = lotcheckQueuePage.numberTotalTestPlanDisplayInTodayTab();
        lotcheckQueuePage.selectFromDateAndToDateByFilterName("Planned Test Date", 1, 5)
                .clickSearchIcon();
    }

    @Test
    @Order(22)
    public void verifyResultSearch_Expected(IJUnitTestReporter testReport) {
        LotcheckQueuePage lotcheckQueuePage = browser.getPage(LotcheckQueuePage.class, testReport);
        String num = lotcheckQueuePage.numberTestPlanDisplayInTodayTab();
        assertTrue(!numberTestPlan.equalsIgnoreCase(num), "Date filters will be applied and display results");
    }

    @Test
    @Order(23)
    public void selectExportAllColumns_Step(IJUnitTestReporter testReport) throws Exception {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = lotcheckLandingPage.navigateToSubmissionSearch();
        lotcheckSubmissionSearchPage.selectSearchIcon()
                .clickActionMenu()
                .selectExportAllColumns(fileName);

    }

    @Test
    @Order(24)
    public void verifyValueOnCSVFileAfterExportAllColumns_Step(IJUnitTestReporter testReport) {
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = browser.getPage(LotcheckSubmissionSearchPage.class, testReport);
        ArrayList<String> gameCodeListSLCMS = new ArrayList<>();
        ArrayList<String> gameCodeListCSV = new ArrayList<>();
        for (int i = 1; i < 51; i++) {
            String gameCode = lotcheckSubmissionSearchPage.getValueOfSubmissionTable(i, 2);
            gameCodeListSLCMS.add(gameCode);
        }
        gameCodeListCSV = lotcheckSubmissionSearchPage.getGameCodeData(fileName, 51);
        assertEquals(gameCodeListSLCMS, gameCodeListCSV, "the values in the exported CSV file is displayed");
    }

    @Test
    @Order(25)
    public void verifyValueOfFreeToPlayOnCSVFile_Expected(IJUnitTestReporter testReport) {
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = browser.getPage(LotcheckSubmissionSearchPage.class, testReport);
        lotcheckSubmissionSearchPage.selectSearchIcon()
                .clickActionMenu()
                .selectChooseColumns()
                .addFreeToPlayColumn()
                .clickConfirmButton()
                .selectFilterByName("More")
                .chooseFilterOnMoreByName(TextConstants.free2Play)
                .typeTabFilter("More")
                .selectOptionOfFilterByFilterName(TextConstants.free2Play, TextConstants.yes_Value)
                .clickSearchIcon();
        if (!lotcheckSubmissionSearchPage.isNoResultFound()) {
            String gameCodeSubmissionYes = lotcheckSubmissionSearchPage.getValueOfSubmissionTable(1, 2);
            String freeToPlaySubmissionYes = lotcheckSubmissionSearchPage.getValueOfSubmissionTable(1, 11);
            String freeToPlayValueOnCSVFile = lotcheckSubmissionSearchPage.getFreeToPlayData(fileName, gameCodeSubmissionYes);
            System.out.println(freeToPlayValueOnCSVFile);
            assertEquals(freeToPlaySubmissionYes, freeToPlayValueOnCSVFile, "The Free To Play value on CSV file is correct");
        }
        lotcheckSubmissionSearchPage.uncheckOptionOfFilterByFilterName(TextConstants.free2Play, TextConstants.yes_Value)
                .selectOptionOfFilterByFilterName(TextConstants.free2Play, TextConstants.no_Value)
                .clickSearchIcon();
        if (!lotcheckSubmissionSearchPage.isNoResultFound()) {
            String gameCodeSubmissionNo = lotcheckSubmissionSearchPage.getValueOfSubmissionTable(1, 2);
            String freeToPlaySubmissionNo = lotcheckSubmissionSearchPage.getValueOfSubmissionTable(1, 11);

            String freeToPlayValueOnCSVFile = lotcheckSubmissionSearchPage.getFreeToPlayData(fileName, gameCodeSubmissionNo);
            System.out.println(freeToPlayValueOnCSVFile);
            assertEquals(freeToPlaySubmissionNo, freeToPlayValueOnCSVFile, "The Free To Play value on CSV file is correct");
        }
        lotcheckSubmissionSearchPage.uncheckOptionOfFilterByFilterName(TextConstants.free2Play, TextConstants.no_Value)
                .selectOptionOfFilterByFilterName(TextConstants.free2Play, TextConstants.none_Value)
                .clickSearchIcon();
        if (!lotcheckSubmissionSearchPage.isNoResultFound()) {
            String gameCodeSubmissionNone = lotcheckSubmissionSearchPage.getValueOfSubmissionTable(1, 2);
            String freeToPlaySubmissionNone = lotcheckSubmissionSearchPage.getValueOfSubmissionTable(1, 11);
            String freeToPlayValueOnCSVFile = lotcheckSubmissionSearchPage.getFreeToPlayData(fileName, gameCodeSubmissionNone);
            System.out.println(freeToPlayValueOnCSVFile);
            assertEquals(freeToPlaySubmissionNone, freeToPlayValueOnCSVFile, "The Free To Play value on CSV file is correct");
        }

    }

    @Test
    @Order(26)
    public void selectExportVisibleColumns_Step(IJUnitTestReporter testReport) throws Exception {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = lotcheckLandingPage.navigateToSubmissionSearch();
        lotcheckSubmissionSearchPage.clickSearchIcon().clickActionMenu()
                .selectExportVisibleColumns(fileName);

    }

    @Test
    @Order(27)
    public void verifyValueOnCSVFileAfterExportVisibleColumns_Expected(IJUnitTestReporter testReport) {
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = browser.getPage(LotcheckSubmissionSearchPage.class, testReport);
        ArrayList<String> gameCodeListSLCMS = new ArrayList<>();
        ArrayList<String> gameCodeListCSV = new ArrayList<>();
        for (int i = 1; i < 51; i++) {
            String gameCode = lotcheckSubmissionSearchPage.getValueOfSubmissionTable(i, 2);
            gameCodeListSLCMS.add(gameCode);
        }
        gameCodeListCSV = lotcheckSubmissionSearchPage.getGameCodeDataExportVisibleColumnFile(fileName, 51);
        assertEquals(gameCodeListSLCMS, gameCodeListCSV, "the values in the exported CSV file is displayed");
    }

    @Test
    @Order(28)
    public void checkOffAllCheckboxesOnMoreMenu_Step(IJUnitTestReporter testReport) throws Exception {
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = browser.getPage(LotcheckSubmissionSearchPage.class, testReport);
        lotcheckSubmissionSearchPage.selectFilterByName("More")
                .chooseFilterOnMoreByName("Priority")
                .chooseFilterOnMoreByName("Application ID")
                .chooseFilterOnMoreByName("Submission Type")
                .chooseFilterOnMoreByName("Product Type")
                .chooseFilterOnMoreByName("Release Type")
                .chooseFilterOnMoreByName("Product Name (Japanese)")
                .chooseFilterOnMoreByName("Product Name (Kana)")
                .chooseFilterOnMoreByName("Due Date")
                .chooseFilterOnMoreByName("Expected Submission Date")
                .chooseFilterOnMoreByName("Expected Release Date")
                .chooseFilterOnMoreByName("Submission Judgment")
                .chooseFilterOnMoreByName("Submission Judgment Date")
                .chooseFilterOnMoreByName("Lotcheck Owner (Original)")
                .chooseFilterOnMoreByName("Home Region (Organization)")
                .chooseFilterOnMoreByName("Home Region (Product)")
                .chooseFilterOnMoreByName("Delivery Format")
                .chooseFilterOnMoreByName("Sales Regions (Digital)")
                .chooseFilterOnMoreByName("Sales Regions (Physical)")
                .chooseFilterOnMoreByName("Compacted App Included")
                .typeTabFilter("More");
    }

    @Test
    @Order(29)
    public void checkValueOfFieldFilter_Step(IJUnitTestReporter testReport) {
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = browser.getPage(LotcheckSubmissionSearchPage.class, testReport);
        ArrayList<String> priorityOptions = new ArrayList<>();
        priorityOptions.add(TextConstants.yes_Value);
        priorityOptions.add(TextConstants.no_Value);
        ArrayList<String> platformCodeOptions = new ArrayList<>();
        platformCodeOptions.add("HAC");
        ArrayList<String> formatCodeOptions = new ArrayList<>();
        formatCodeOptions.add("P");
        formatCodeOptions.add("U");
        formatCodeOptions.add("M");
        ArrayList<String> lotcheckOwnerOptions = new ArrayList<>();
        lotcheckOwnerOptions.add(LotcheckRegion.NCL.toString());
        lotcheckOwnerOptions.add(LotcheckRegion.NOA.toString());
        lotcheckOwnerOptions.add(LotcheckRegion.NOE.toString());
        ArrayList<String> homeRegionOrganizationOptions = lotcheckOwnerOptions;
        ArrayList<String> homeRegionProductOptions = lotcheckOwnerOptions;
        assertAll("The values in the option list value of Filter field are displayed correct ",
                () -> assertEquals(priorityOptions, lotcheckSubmissionSearchPage.getOptionsValueOfFilter("Priority")),
                () -> assertEquals(platformCodeOptions, lotcheckSubmissionSearchPage.getOptionsValueOfFilter("Platform Code")),
                () -> assertEquals(formatCodeOptions, lotcheckSubmissionSearchPage.getOptionsValueOfFilter("Format Code")),
                () -> assertEquals(lotcheckOwnerOptions, lotcheckSubmissionSearchPage.getOptionsValueOfFilter("Lotcheck Owner")),
                () -> assertEquals(homeRegionProductOptions, lotcheckSubmissionSearchPage.getOptionsValueOfFilter("Home Region (Product)")),
                () -> assertEquals(homeRegionOrganizationOptions, lotcheckSubmissionSearchPage.getOptionsValueOfFilter("Home Region (Organization)")));

    }

    @Test
    @Order(30)
    public void selectOptionsFilter_Expected(IJUnitTestReporter testReport) {
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = browser.getPage(LotcheckSubmissionSearchPage.class, testReport);
        lotcheckSubmissionSearchPage.selectOptionOfFilterByFilterName("Priority", TextConstants.yes_Value)
                .selectOptionOfFilterByFilterName("Platform Code", "HAC")
                .selectOptionOfFilterByFilterName("Format Code", "P")
                .selectOptionOfFilterByFilterName("Lotcheck Owner", LotcheckRegion.NCL.toString())
                .selectOptionOfFilterByFilterName("Home Region (Product)", LotcheckRegion.NOE.toString())
                .selectOptionOfFilterByFilterName("Home Region (Organization)", LotcheckRegion.NOA.toString());
        assertAll("all filter changes are automatically applied on filter field",
                () -> assertEquals(": " + TextConstants.yes_Value, lotcheckSubmissionSearchPage.currentFilterValue("Priority")),
                () -> assertEquals(": HAC", lotcheckSubmissionSearchPage.currentFilterValue("Platform Code")),
                () -> assertEquals(": P", lotcheckSubmissionSearchPage.currentFilterValue("Format Code")),
                () -> assertEquals(": NCL", lotcheckSubmissionSearchPage.currentFilterValue("Lotcheck Owner")),
                () -> assertEquals(": NOA", lotcheckSubmissionSearchPage.currentFilterValue("Home Region (Organization)")),
                () -> assertEquals(": NOE", lotcheckSubmissionSearchPage.currentFilterValue("Home Region (Product)")));
    }

    @Test
    @Order(31)
    public void selectOptionsWithValueLessThanThirtyCharacter_Step(IJUnitTestReporter testReport) {
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = browser.getPage(LotcheckSubmissionSearchPage.class, testReport);
        lotcheckSubmissionSearchPage.enterValueFilter("Product Name (English)", content)
                .enterValueFilter("Initial Code", content);
    }

    @Test
    @Order(32)
    public void verifyValueOfFilterWithValueLessThanThirtyCharacter_Expected(IJUnitTestReporter testReport) {
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = browser.getPage(LotcheckSubmissionSearchPage.class, testReport);
        assertAll(() -> assertEquals(content, lotcheckSubmissionSearchPage.currentFilterValueOfSearchFilter("Product Name (English)")),
                () -> assertEquals(content, lotcheckSubmissionSearchPage.currentFilterValueOfSearchFilter("Initial Code")));
    }

    @Test
    @Order(33)
    public void navigateToProductInformationOfCreatedProduct_Step(IJUnitTestReporter testReport) {
        LotcheckSubmissionSearchPage lotcheckSubmissionSearchPage = browser.getPage(LotcheckSubmissionSearchPage.class, testReport);
        browser.refresh();
        LotcheckReportSubmissionOverview overview = lotcheckSubmissionSearchPage.clickSearchIcon()
                .waitToLoading()
                .enterValueFilter("Initial Code", initialCode)
                .selectSearchIcon()
                .selectSubmission(gameCode, "00", "00");
        overview.matrix().waitToLoading().clickGoToProductLevel();
    }

    @Test
    @Order(34)
    public void verifyStatusOfChinaRegionData_step(IJUnitTestReporter testReport) {
        LotcheckReportProductProductInformation productInformation = browser.getPage(LotcheckReportProductProductInformation.class, testReport);
    }

    @Test
    @Order(35)
    public void verifyStatusOfChinaRegionData_Expected(IJUnitTestReporter testReport) {
        LotcheckReportProductProductInformation productInformation = browser.getPage(LotcheckReportProductProductInformation.class, testReport);
        String status = productInformation.getChinaDataStatus();
        assertEquals("Complete", status, "Status correct");

    }

    @Test
    @Order(36)
    public void verifyDataOfChinaRegionData_Step(IJUnitTestReporter testReport) {
        LotcheckReportProductProductInformation productInformation = browser.getPage(LotcheckReportProductProductInformation.class, testReport);
        assertAll("All text type fields will display the value entered for that field.",
                () -> assertEquals(isbnNumber, productInformation.getNumberISBN()),
                () -> assertEquals(gappNumber, productInformation.getNumberGAPP()),
                () -> assertEquals(publishingEntity, productInformation.getPublishingEntity()),
                () -> assertEquals(operatingEntity, productInformation.getOperatingEntity()),
                () -> assertEquals(TextConstants.yes_Upper_Value, productInformation.getCopyrightOutsideChina()),
                () -> assertEquals(copyrightRegisterNumber, productInformation.getCopyrightContractNumber())
        );
    }

    @Test
    @Order(37)
    public void verifyNoticeOfISBNApprovalField_Step(IJUnitTestReporter testReport) {
        LotcheckReportProductProductInformation productInformation = browser.getPage(LotcheckReportProductProductInformation.class, testReport);
        assertEquals("Judgement.pdf", productInformation.getNoticeISBN(), "This field include download links");
    }

    @Test
    @Order(38)
    public void verifyReplyToCopyrightContactRegistration_Step(IJUnitTestReporter testReport) {
        LotcheckReportProductProductInformation productInformation = browser.getPage(LotcheckReportProductProductInformation.class, testReport);
        assertEquals("test_doc.docx", productInformation.getReplyToCopyright(), "This field include download links");
    }

    @Test
    @Order(39)
    public void performDownloadFileOfNoticeOfISBNApprovalField_Step(IJUnitTestReporter testReport) {
        LotcheckReportProductProductInformation productInformation = browser.getPage(LotcheckReportProductProductInformation.class, testReport);
        productInformation.deleteFile("Judgement.pdf");
        productInformation.downloadNoticeNumber();
        WaitUtils.idle(3000);
    }

    @Test
    @Order(40)
    public void downloadFileOfNoticeOfISBNApprovalFieldSuccessfully_Expected(IJUnitTestReporter testReport) {
        LotcheckReportProductProductInformation productInformation = browser.getPage(LotcheckReportProductProductInformation.class, testReport);
        assertNotEquals(0L, productInformation.getFileSizeFromPath("Judgement.pdf"), "Download file successfully");
    }

    @Test
    @Order(41)
    public void performDownloadFileOfNoticeOfReplyToCopyrightContactRegistration_Step(IJUnitTestReporter testReport) {
        LotcheckReportProductProductInformation productInformation = browser.getPage(LotcheckReportProductProductInformation.class, testReport);
        productInformation.deleteFile("test_doc.docx");
        productInformation.downloadCopyrightRegistration();
        WaitUtils.idle(3000);
    }

    @Test
    @Order(42)
    public void downloadFileOfNoticeOfReplyToCopyrightContactRegistrationSuccessfully_Expected(IJUnitTestReporter testReport) {
        LotcheckReportProductProductInformation productInformation = browser.getPage(LotcheckReportProductProductInformation.class, testReport);
        assertNotEquals(0L, productInformation.getFileSizeFromPath("test_doc.docx"), "Download file successfully");
    }

    @Test
    @Order(43)
    public void performSubmissionPassedLotcheck_Step(IJUnitTestReporter testReport) {
        TestRailManagerDashboardPage managerDashboardPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN, testReport)
                .signInTestRail(testrailUser)
                .navTopTestRail()
                .navigateToManagerDashboard()
                .waitingLoadingTestPlan()
                .clickOkInErrorPopup();
        CommonAction.setStatusOnTestrailWithoutPercentComplete(managerDashboardPage, initialCode, TextConstants.passedStatus, TextConstants.OK, false);
        browser.refresh();
        managerDashboardPage.waitingLoadingTestPlan().clickOkInErrorPopup();
        CommonAction.setStatusOnTestrailWithoutPercentComplete(managerDashboardPage, initialCode, TextConstants.passedStatus, TextConstants.OK, false);

        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToJudgmentApprovalQueue(LotcheckRegion.NCL.toString());
        lotcheckQueuePage.searchByInitialCode(initialCode);
        int i = 0;
        while (i <= 30) {
            lotcheckQueuePage.refreshPageSLCMS();
            lotcheckQueuePage.searchByInitialCode(initialCode);
            String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
            if (numberValue.equalsIgnoreCase("2")) {
                LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
                String judgmentStatus = lotcheckQueuePage.getJudgmentStatus();
                if (judgmentStatus.equals(TextConstants.OK)) {
                    WaitUtils.idle(10000);
                    lotcheckTestPlanRow.selectApproveJudgment().clickConfirm();
                    lotcheckTestPlanRow.selectApproveJudgment().clickConfirm();
                    WaitUtils.idle(10000);
                    break;
                }
            }
        }
    }

    @Test
    @Order(44)
    public void loginNDPPartnerUser_Step(IJUnitTestReporter testReport) {
        browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReport)
                .clickSignInPage()
                .signIn(ndpUser)
                .clickMyProducts();
    }

    @Test
    @Order(45)
    public void createAPatchRelease_Step(IJUnitTestReporter testReport) {
        NDPMyProductsPage myProductsPage = browser.getPage(NDPMyProductsPage.class, testReport);
        NDPCreateReleasePage ndpCreateReleasePage = myProductsPage.clickProductByName(productName)
                .clickReleases()
                .clickCreateNewReleaseButton();
        NDPReleaseInfoPage releaseInfoPage = ndpCreateReleasePage.enterDisplayVersion("1.0.1")
                .selectExpectedSubmissionDate(5)
                .selectExpectedReleaseDate(35)
                .selectFreeToPlay(TextConstants.no_Upper_Value)
                .enterDescribeTheFeaturesPlanned(TextConstants.enterDescribeTheFeaturesPlanned)
                .enterDescribeTheBugsIssuesPlanned(TextConstants.enterDescribeTheBugsIssuesPlanned)
                .clickCreateButton();
        releaseInfoPage.selectTargetDeliveryFormat(TextConstants.both_Target_Type).save();
    }

    @Test
    @Order(46)
    public void loginSlCMSAsLotcheckDataManager_Step(IJUnitTestReporter testReport) {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        lotcheckLandingPage.clickOnUserNameMenu()
                .clickOnSignoutButton();
        browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN, testReport)
                .continueToLogin()
                .loginWithOvdLink(slcmsDataManagerUser);
    }

    @Test
    @Order(47)
    public void navigateToOverallNCL_Step(IJUnitTestReporter testReport) {
        LotcheckLandingPage lotcheckLandingPage = browser.getPage(LotcheckLandingPage.class, testReport);
        lotcheckLandingPage.navigationToOverallLotcheckQueue(LotcheckRegion.NCL.toString());
    }

    @Test
    @Order(48)
    public void searchAndSelectProduct_Step(IJUnitTestReporter testReport) {
        LotcheckLandingPage lotcheckLandingPage = browser.openURL(LotcheckLandingPage.class, PageState.POST_LOGIN, testReport);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.navigationToOverallLotcheckQueue(LotcheckRegion.NCL.toString());
        int i = 0;
        while (i < 20) {
            lotcheckLandingPage.navigationToOverallLotcheckQueue(LotcheckRegion.NCL.toString()).searchByInitialCode(initialCode);
            String numberValue = lotcheckQueuePage.numberTestPlanDisplay();
            i++;
            if (numberValue.equalsIgnoreCase("2")) {
                break;
            }
        }
        lotcheckQueuePage.clickPlannedTestDateNotSet();
    }

    @Test
    @Order(49)
    public void verifyProductInLotcheckQueue_Expected(IJUnitTestReporter testReport) {
        LotcheckTestPlanRow testPlanRow = browser.getPage(LotcheckTestPlanRow.class, testReport);
        ArrayList<String> targetDelivery = new ArrayList<>();
        targetDelivery.add(TextConstants.card_Type);
        targetDelivery.add(TextConstants.dl_Type);
        assertEquals(targetDelivery, testPlanRow.getTargetDeliveryFormat(), "DL and Card Test Plan are created and displayed in the Lotcheck Queues");
    }

    @Test
    @Order(50)
    public void navigateToLotcheckSubmissionReport_Step(IJUnitTestReporter testReport) {
        LotcheckQueuePage lotcheckQueuePage = browser.getPage(LotcheckQueuePage.class, testReport);
        lotcheckQueuePage.navigateToSubmissionLevelOnFirstTestPlan();
    }

    @Test
    @Order(51)
    public void verifyProductInLotcheckReportSubmission_Expected(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionOverview submissionOverview = browser.getPage(LotcheckReportSubmissionOverview.class, testReport);
        assertAll("DL and Card Test Plan are created and displayed  in the Lotcheck Report: Submission",
                () -> assertTrue(submissionOverview.matrix().testPlanForExists(TextConstants.card_Type)),
                () -> assertTrue(submissionOverview.matrix().testPlanForExists(TextConstants.dl_Type)));
    }

    @Test
    @Order(52)
    public void navigateToLotcheckReportProductLevel_Step(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionOverview submissionOverview = browser.getPage(LotcheckReportSubmissionOverview.class, testReport);
        submissionOverview.matrix().waitToLoading().clickGoToProductLevel();
    }

    @Test
    @Order(53)
    public void verifyProductInLotcheckReportProductLevel_Expected(IJUnitTestReporter testReport) {
        LotcheckReportProductProductInformation productInformation = browser.getPage(LotcheckReportProductProductInformation.class, testReport);
        assertAll("DL and Card Test Plan are created and displayed  in the Lotcheck Report: Product level",
                () -> assertTrue(productInformation.testPlanForExists("HAC-U-" + initialCode, TextConstants.card_Type)),
                () -> assertTrue(productInformation.testPlanForExists("HAC-U-" + initialCode, TextConstants.dl_Type)));
    }

    @Test
    @Order(54)
    public void navigateToLotcheckReportTestPlanLevel_Step(IJUnitTestReporter testReport) {
        LotcheckReportProductProductInformation productInformation = browser.getPage(LotcheckReportProductProductInformation.class, testReport);
        LotcheckReportSubmissionOverview overview = productInformation.viewSubmissionOverviewByGameCode("HAC-U-" + initialCode);
        browser.refresh();
        overview.matrix().waitToLoading().clickGoToTestPlanReport();
    }

    @Test
    @Order(55)
    public void verifyProductInLotcheckReportTestPlanLevel_Expected(IJUnitTestReporter testReport) {
        LotcheckReportTestPlanOverview testPlanOverview = browser.getPage(LotcheckReportTestPlanOverview.class, testReport);
        assertAll("DL and Card Test Plan are created and displayed  in the Lotcheck Report: Test Plan",
                () -> assertTrue(testPlanOverview.matrix().testPlanForExists(TextConstants.card_Type)),
                () -> assertTrue(testPlanOverview.matrix().testPlanForExists(TextConstants.dl_Type)));
    }

    @Test
    @Order(56)
    public void navigateToLotcheckReportNumberLevel_Step(IJUnitTestReporter testReport) {
        LotcheckReportTestPlanOverview testPlanOverview = browser.getPage(LotcheckReportTestPlanOverview.class, testReport);
        browser.refresh();
        testPlanOverview.matrix().waitToLoading().clickGoToNumberReport();
    }

    @Test
    @Order(57)
    public void verifyProductInLotcheckReportNumberLevel_Expected(IJUnitTestReporter testReport) {
        LotcheckReportNumberOverview numberOverView = browser.getPage(LotcheckReportNumberOverview.class, testReport);
        assertAll("DL and Card Test Plan are created and displayed  in the Lotcheck Report: Number",
                () -> assertTrue(numberOverView.matrix().testPlanForExists(TextConstants.card_Type)),
                () -> assertTrue(numberOverView.matrix().testPlanForExists(TextConstants.dl_Type)));
    }

    @Test
    @Order(58)
    public void loginNDPAsLicPowerUserAndNavigateAddCustomerAgreement_Step(IJUnitTestReporter testReport) {
        NDPDevelopmentHome developmentHome = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReport);
        uuidOrg = developmentHome.topNavigation()
                .clickAdmin()
                .clickOrganizationInformation()
                .getOrganizationUUID();
        NDPMyProductsPage myProductsPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPInternalPage internalPage = myProductsPage.clickOnUserNameMenu()
                .clickOnSignOutButton()
                .clickSignInPage()
                .signIn(licPowerUser)
                .clickInternalTab();
        internalPage.clickCustomAgreementsButton();
    }

    @Test
    @Order(59)
    public void selectPartnerField_Step(IJUnitTestReporter testReport) {
        NDPCustomAgreementsPage customAgreementsPage = browser.getPage(NDPCustomAgreementsPage.class, testReport);
        customAgreementsPage.clickPartnerSearchIcon()
                .enterPartnerSearchInput(uuidOrg)
                .clickSearchIconOnPartnerSearchModal()
                .selectPartner()
                .clickChooseSelectedPartnerButton();
    }

    @Test
    @Order(60)
    public void selectPlatformFieldWithNintendoSwitchValue_Step(IJUnitTestReporter testReport) {
        NDPCustomAgreementsPage customAgreementsPage = browser.getPage(NDPCustomAgreementsPage.class, testReport);
        customAgreementsPage.selectPlatform("Nintendo Switch");
    }

    @Test
    @Order(61)
    public void selectAgreementType_Step(IJUnitTestReporter testReport) {
        NDPCustomAgreementsPage customAgreementsPage = browser.getPage(NDPCustomAgreementsPage.class, testReport);
        customAgreementsPage.selectAgreementType("Publisher Agreement (Digital)");
    }

    @Test
    @Order(62)
    public void selectRegionField_Step(IJUnitTestReporter testReport) {
        NDPCustomAgreementsPage customAgreementsPage = browser.getPage(NDPCustomAgreementsPage.class, testReport);
        customAgreementsPage.selectRegionField("China");
    }

    @Test
    @Order(63)
    public void selectActionField_Step(IJUnitTestReporter testReport) {
        NDPCustomAgreementsPage customAgreementsPage = browser.getPage(NDPCustomAgreementsPage.class, testReport);
        customAgreementsPage.selectActionAmendment();
    }

    @Test
    @Order(64)
    public void selectActionAmendmentField_Step(IJUnitTestReporter testReport) {
        NDPCustomAgreementsPage customAgreementsPage = browser.getPage(NDPCustomAgreementsPage.class, testReport);
        customAgreementsPage.selectActionTemplateAmendment();
    }

    @Test
    @Order(65)
    public void fillOutTheRequiredFieldsAndSubmit_Step(IJUnitTestReporter testReport) {
        NDPCustomAgreementsPage customAgreementsPage = browser.getPage(NDPCustomAgreementsPage.class, testReport);
        customAgreementsPage.selectAmendmentType("Americas - 3DS - Amendment Agreement")
                .clickSubmitButton();
        String message = customAgreementsPage.getAlertContent();
        assertEquals("Agreement has been saved.", message, "Submit successfully");

    }

    @Test
    @Order(66)
    public void loginNDPAsCompanyAdmin_Step(IJUnitTestReporter testReport) {
        NDPAdminPage adminPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport)
                .clickOnUserNameMenu()
                .clickOnSignOutButton()
                .clickSignInPage()
                .signIn(ndpUser)
                .topNavigation()
                .clickAdmin();
        adminPage.clickAgreement();
    }

    @Test
    @Order(67)
    public void selectAgreementAmendmentAndAccept_Step(IJUnitTestReporter testReport) {
        NDPAgreementPage agreement = browser.getPage(NDPAgreementPage.class, testReport);
        agreement.viewAgreementByTypeAndRegion("Publisher Agreement (Digital)", "China");
        int i = 0;
        while (i < 100) {
            if (!agreement.isReviewAndAcceptVisible()) {
                browser.refresh();
                i++;
            } else break;
        }
        agreement.clickReviewAndAccept()
                .clickViewAgreementCheckbox()
                .clickAcceptButton();
        browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReport)
                .topNavigation()
                .clickAdmin()
                .clickAgreement()
                .viewAgreementByTypeAndRegion("Publisher Agreement (Digital)", "China");
        i = 0;
        while (i < 500) {
            if (agreement.isAgreementStatusPending()) {
                browser.refresh();
                i++;
            } else break;
        }
    }

    @Test
    @Order(68)
    public void accessToInbucketAgain_step(IJUnitTestReporter testReport) {
        EmailPage emailPage = browser.openURL(EmailPage.class, PageState.PRE_LOGIN, testReport);
        emailPage.signInEmail(waveUserName);
    }

    @Test
    @Order(69)
    public void checkEmailPA58_WAVESentToWaveUser_Step(IJUnitTestReporter testReport) {
        EmailPage emailPage = browser.getPage(EmailPage.class, testReport);
        String emailName = "Nintendo Developer Portal - China - " + nameOrg + " has accepted Amendment for Nintendo Switch Digital Publisher Agreement";
        System.out.println(emailName);
        int collectNumber = emailPage.collectNumberEmail();
        for (int i = collectNumber - 1; i < 0; i--) {
            if (emailPage.getValueEmail(i) == emailName) {
                break;
            }
        }
        emailPage.clickEmailByName(emailName).selectHTML();
    }
}
