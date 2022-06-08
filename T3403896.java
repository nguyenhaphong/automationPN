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
import net.nintendo.automation.ui.models.iarc.IARCQuestionnaireFinalRatingPage;
import net.nintendo.automation.ui.models.iarc.IARCQuestionnairePage;
import net.nintendo.automation.ui.models.lcms.LotcheckHomePage;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.ui.models.lcms.LotcheckQueuePage;
import net.nintendo.automation.ui.models.lcms.LotcheckTestPlanRow;
import net.nintendo.automation.ui.models.ndp.*;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoAgeRating;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoFeaturesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoROMUpload;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPConfirmIARCCertificateImport;
import net.nintendo.automation.ui.models.rom.ROMCreateRom;
import net.nintendo.automation.ui.models.rom.ROMLoginPage;
import net.nintendo.automation.ui.models.testrail.*;
import net.nintendo.automation.ui.users.User;
import net.nintendo.automation.ui.users.UserManager;
import net.nintendo.automation.utils.WaitUtils;
import net.nintendo.automation_core.common_client.enums.ServiceType;
import net.nintendo.automation_core.common_client.enums.UserType;
import net.nintendo.automation_core.common_utils.enums.LotcheckRegion;
import net.nintendo.automation_core.extent_reports.extensions.IJUnitTestReporter;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("T3403896")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3403896 extends CommonBaseTest {
    private static Browser browser;
    private static User romUser;
    private static User ndpUser;
    private static User licUser;
    private static User slcmsUser;
    private static User trAdminUser;
    private static User trDataManagerUser;
    private static CommonUIComponent currentPage;
    private static TestLogger logger;
    private static String publishingRelationship;
    private static String productName;
    private static String applicationId;
    private static String initialCode;
    private static String romInitialName;
    private static String gameCode;
    private static String remarkMemoCaseName;
    private static String remarkTestcaseName;
    private static String addProblemDescriptionName;
    private static String submitBugModalTile_Step18;
    private static boolean isNeedsTranslationUnChecked_step18;
    private static boolean isNeedsTranslationUnChecked_Step19;
    private static boolean testcaseDetailModalIsDisplayed;
    private static String certificateId;
    private static String certificateId_TestCondition;
    private static String productNameOfSourceIarc;
    private static boolean isNeedsTranslationUnChecked_Step21;
    private static String submitBugModalTile_Step21;
    private static String agencyNames;
    private static String iCons_Expected;
    private static String ratingValues_Expected;
    private static String descriptions_Expected;
    private static String acbRatingData;
    private static String ceroRatingData;
    private static String pegiRatingData;
    private static String russianRatingData;
    private static String uskRatingData;

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_NgaDTN);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
        slcmsUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);
        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);

        trDataManagerUser = new User();
        trDataManagerUser.setUserName(TextConstants.trDataManager_Email);
        trDataManagerUser.setPassword(TextConstants.trAdmin_Password);

        trAdminUser = new User();
        trAdminUser.setUserName(TextConstants.trAdminNCL_Email);
        trAdminUser.setPassword(TextConstants.trAdmin_Password);

        remarkMemoCaseName = "Remark memo";
        remarkTestcaseName = "Remark testcase";
        addProblemDescriptionName = "Add Problem Description case";

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
        UserManager.releaseUser(ServiceType.SEARCH, romUser);
    }

    @Test()
    @Order(1)
    public void createInitialRelease_TestCondition(IJUnitTestReporter testReport) {
        NDPHomePage homePage = (NDPHomePage) currentPage;
        currentPage = homePage.acceptCookie().clickSignInPage().signIn(ndpUser);
        NDPDevelopmentHome developmentHome = (NDPDevelopmentHome) currentPage;
        productNameOfSourceIarc = DataGen.getRandomProductName();
        productName = DataGen.getRandomProductName();
        NDPMyProductsPage myProductsPage = developmentHome.clickMyProducts().createNewProduct()
                .selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(publishingRelationship)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalJapanSalesRegion()
                .selectProductName(productNameOfSourceIarc)
                .selectProductNameKana(TextConstants.productKana)
                .clickCreateButton();
        NDPProductDashboardPage productDashboardPage = myProductsPage.clickProductByName(productNameOfSourceIarc);
        CommonAction.issueGameCode(productDashboardPage, 90, TextConstants.no_Upper_Value);
        productDashboardPage.clickReleases().selectInitialRelease().nav().clickAgeRating().clickOnAddIARCRatingButton().enterContactEmailAddress(ndpUser.getUserName() + "@inbucket.noa.com")
                .enterPublicEmailAddress(ndpUser.getUserName() + TextConstants.inbucket_Suffix)
                .selectIARCRequestNewIARCCertificate()
                .clickRequestNewIarcContinue();
        WaitUtils.idle(6000);
        browser.switchToTabByTitle(IARCQuestionnairePage.pageTitle);
        IARCQuestionnaireFinalRatingPage iarcQuestionnaireFinalRatingPage = browser.getPage(IARCQuestionnairePage.class, testReport)
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
        certificateId_TestCondition = iarcQuestionnaireFinalRatingPage.getIARCCertID();
        agencyNames = iarcQuestionnaireFinalRatingPage.getListRatingSystem();
        iCons_Expected = iarcQuestionnaireFinalRatingPage.getListRatingCategoryIcons();
        ratingValues_Expected = iarcQuestionnaireFinalRatingPage.getListRatingCategoryValue();
        descriptions_Expected = iarcQuestionnaireFinalRatingPage.getListDescriptions();
        acbRatingData = iarcQuestionnaireFinalRatingPage.getAgeRatingData("ACB");
        pegiRatingData = iarcQuestionnaireFinalRatingPage.getAgeRatingData("PEGI");
        uskRatingData = iarcQuestionnaireFinalRatingPage.getAgeRatingData("USK");
        russianRatingData = iarcQuestionnaireFinalRatingPage.getAgeRatingData("Russia");
        ceroRatingData = iarcQuestionnaireFinalRatingPage.getAgeRatingData("Generic");
        if (acbRatingData.equals("ACB, General, G, General")) {
            acbRatingData = "ACB\n" +
                    "Iarc, G, G, General";
        }
        if (ceroRatingData.equals("Generic, , 3, ")) {
            ceroRatingData = "CERO/Generic\n" +
                    "Iarc, 3+, 3, ";
        }
        if (russianRatingData.equals("Russia, , 0, ")) {
            russianRatingData = "Russian\n" +
                    "Iarc, 0, 0, ";
        }
        if (uskRatingData.equals("USK, USK ab 0, 0, ")) {
            uskRatingData = "USK\n" +
                    "Iarc, USK ab 0 Jahren, 0, ";
        }
        if (pegiRatingData.equals("PEGI, PEGI 3, 3, ")) {
            pegiRatingData = "PEGI\n" +
                    "Iarc, PEGI 3, 3, ";
        }
        if (agencyNames.equals("ACB, ClassInd, ESRB, GRAC, PEGI, USK, Russia, Generic")) {
            agencyNames = "ACB\n" +
                    "Iarc, ClassInd\n" +
                    "Iarc, ESRB\n" +
                    "Iarc, GRAC\n" +
                    "Iarc, PEGI\n" +
                    "Iarc, USK\n" +
                    "Iarc, RARS\n" +
                    "Iarc, CERO/Generic\n" +
                    "Iarc";
        }
        if (ratingValues_Expected.equals("General, Livre, Everyone, 전체이용가, PEGI 3, USK ab 0, , ")) {
            ratingValues_Expected = "G, Classificação Livre, Everyone, All, PEGI 3, USK ab 0 Jahren, 0, 3+";
        }
        browser.closeTabByTitle(IARCQuestionnairePage.pageTitle);

        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        myProductsPage = (NDPMyProductsPage) currentPage;

        myProductsPage.createNewProduct().selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(publishingRelationship)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalJapanSalesRegion()
                .selectDigitalEuropeSalesRegion()
                .selectDigitalAsiaSalesRegion()
                .selectProductName(productName)
                .selectProductNameKana(TextConstants.productKana)
                .clickCreateButton();
        productDashboardPage = myProductsPage.clickProductByName(productName);
        CommonAction.issueGameCode(productDashboardPage, 90, TextConstants.no_Upper_Value);
        initialCode = productDashboardPage.clickProductInfo().getInitialCode();
        gameCode = productDashboardPage.clickProductInfo().getGameCode();
        applicationId = productDashboardPage.clickProductInfo().getApplicationID();
        currentPage = productDashboardPage;
    }

    @Test()
    @Order(2)
    public void createPatchRelease_Step1() {
        NDPProductDashboardPage productDashboardPage = (NDPProductDashboardPage) currentPage;
        currentPage = productDashboardPage.clickReleases().clickCreateNewReleaseButton()
                .enterDisplayVersion("1.0.1")
                .selectExpectedSubmissionDate(10)
                .selectExpectedReleaseDate(90)
                .enterDescribeTheFeaturesPlanned(TextConstants.enterDescribeTheFeaturesPlanned)
                .enterDescribeTheBugsIssuesPlanned(TextConstants.enterDescribeTheBugsIssuesPlanned)
                .clickCreateButton();
    }

    @Test()
    @Order(3)
    public void navigateToAgeRatingScreenAndAddStandardRatingForGracRating_Step2() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        currentPage = releaseInfoPage.nav().clickAgeRating().clickOnAddStandardRatingButton()
                .selectStandardAgeRating("GRAC")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectRatingValue("Not required")
                .clickOnSaveButtonInEditStandardRating();
    }

    @Test()
    @Order(4)
    public void clickAddIarcRating_Step3() {
        NDPReleaseInfoAgeRating ageRating = (NDPReleaseInfoAgeRating) currentPage;
        currentPage = ageRating.clickOnAddIARCRatingButton();
    }

    @Test()
    @Order(5)
    public void verifyTheRequestNewIarcRatingDisplay_Expected3() {
        NDPReleaseInfoAgeRating ageRating = (NDPReleaseInfoAgeRating) currentPage;
        assertEquals("Request New IARC Ratings", ageRating.getRequestNewIarcModalName(), "Verify The \"Request New IARC Ratings\" form is appear");
    }

    //Remove step4
    @Test()
    @Order(6)
    public void inputValueOnRequestNewIarcRatingForm_Step5() {
        NDPReleaseInfoAgeRating ageRating = (NDPReleaseInfoAgeRating) currentPage;
        ageRating.enterContactEmailAddress(ndpUser.getUserName() + TextConstants.inbucket_Suffix)
                .enterPublicEmailAddress(ndpUser.getUserName() + TextConstants.inbucket_Suffix)
                .selectImportExistingIARCCertificate();
    }

    @Test()
    @Order(7)
    public void inputIarcCertificateId_Step6() {
        NDPReleaseInfoAgeRating ageRating = (NDPReleaseInfoAgeRating) currentPage;
        ageRating.enterCertificateID_Iarc(certificateId_TestCondition);
    }

    @Test()
    @Order(8)
    public void clickContinueButton_Step7() {
        NDPReleaseInfoAgeRating ageRating = (NDPReleaseInfoAgeRating) currentPage;
        currentPage = ageRating.clickOnContinueButton();
    }

    @Test()
    @Order(9)
    public void verifyNavigateToConfirmIARCCertificateImportScreen_Expected7() {
        NDPConfirmIARCCertificateImport confirmIARCCertificateImport = (NDPConfirmIARCCertificateImport) currentPage;
        assertAll(() -> assertEquals("Age Rating - Nintendo Developer Portal", confirmIARCCertificateImport.getPageTitle(), "Verify Confirm IARC Certificate Import screen displayed"),
                () -> assertEquals(agencyNames, confirmIARCCertificateImport.getAgeRatingAgencyName(), "Verify agency age rating"),
                () -> assertEquals(descriptions_Expected, confirmIARCCertificateImport.getDescriptions(), "Verify description age rating"),
                () -> assertEquals(ratingValues_Expected, confirmIARCCertificateImport.getRatingValues(), "Verify rating value"),
                () -> assertEquals(iCons_Expected, confirmIARCCertificateImport.getIcons(), "Verify icon age rating"),
                () -> assertTrue(confirmIARCCertificateImport.isAcceptImportDisplayed(), "Verify display accept import button"),
                () -> assertTrue(confirmIARCCertificateImport.isCancelImportDisplayed(), "Verify display cancel import button"));
    }

    @Test()
    @Order(10)
    public void checkDataOnConfirmIARCCertificateImportScreen_Step8() {
        NDPConfirmIARCCertificateImport confirmIARCCertificateImport = (NDPConfirmIARCCertificateImport) currentPage;
        certificateId = confirmIARCCertificateImport.getCertificateId();
        productNameOfSourceIarc = confirmIARCCertificateImport.getProductName();
    }

    @Test()
    @Order(11)
    public void verifyDataOnConfirmIARCCertificateImportScreen_Expected8() {
        assertAll(() -> assertEquals(certificateId_TestCondition, certificateId),
                () -> assertEquals(productNameOfSourceIarc, productNameOfSourceIarc));
    }

    @Test()
    @Order(12)
    public void clickAcceptImportButton_Step9() {
        NDPConfirmIARCCertificateImport confirmIARCCertificateImport = (NDPConfirmIARCCertificateImport) currentPage;
        currentPage = confirmIARCCertificateImport.clickAcceptImport();
    }

    @Test()
    @Order(13)
    public void verifyDisplayAgeRating_Expected9() {
        NDPReleaseInfoAgeRating ageRating = (NDPReleaseInfoAgeRating) currentPage;
        String garcRatingData = "GRAC\n" +
                "Standard, Not required, EXEMPT, ";
        assertAll(() -> assertEquals(acbRatingData, ageRating.getAgeRatingData("ACB"), "Verify ACB rating"),
                () -> assertEquals(ceroRatingData, ageRating.getAgeRatingData("CERO"), "Verify CERO rating"),
                () -> assertEquals(pegiRatingData, ageRating.getAgeRatingData("PEGI"), "Verify PEGI rating"),
                () -> assertEquals(russianRatingData, ageRating.getAgeRatingData("Russia"), "Verify Russia rating"),
                () -> assertEquals(uskRatingData, ageRating.getAgeRatingData("USK"), "Verify USK rating"),
                () -> assertEquals(garcRatingData, ageRating.getAgeRatingData("GRAC"), "Verify GRAC rating"));

    }

    @Test()
    @Order(14)
    public void submitInitialToLotcheck_Step10(IJUnitTestReporter testReport) {
        NDPReleaseInfoAgeRating ageRating = (NDPReleaseInfoAgeRating) currentPage;
        NDPReleaseInfoPage releaseInfoPage = ageRating.nav().clickBackToProductDashboard().clickReleases().selectInitialRelease();
        CommonAction.selectROMSameAllRegion(releaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);

        NDPReleaseInfoFeaturesPage releaseInfoFeaturesPage = releaseInfoPage.nav().clickFeatures();

        CommonAction.getFeatureOptionData(releaseInfoPage, TextConstants.allOff, TextConstants.allOff, TextConstants.allOff, false, "", false, "");
        releaseInfoFeaturesPage.clickContinue();
        releaseInfoPage.nav().clickGuidelines().selectCompliesWithAllOfTheAboveGuidelines().clickOnSaveButton();
        releaseInfoPage.nav().clickAgeRating().clickOnAddStandardRatingButton()
                .selectStandardAgeRating("CERO").clickOnNextButtonInAddStandardAgeRating().selectRatingValue("Not required").clickOnSaveButtonInEditStandardRating()
                .clickOnAddStandardRatingButton().selectStandardAgeRating("BBFC").clickOnNextButtonInAddStandardAgeRating().selectRatingValue("Not required").clickOnSaveButtonInEditStandardRating()
                .clickOnAddStandardRatingButton().selectStandardAgeRating("PEGI").clickOnNextButtonInAddStandardAgeRating().selectRatingValue("Not required").clickOnSaveButtonInEditStandardRating()
                .clickOnAddStandardRatingButton().selectStandardAgeRating("ACB").clickOnNextButtonInAddStandardAgeRating().selectRatingValue("Not required").clickOnSaveButtonInEditStandardRating()
                .clickOnAddStandardRatingButton().selectStandardAgeRating("USK").clickOnNextButtonInAddStandardAgeRating().selectRatingValue("Not required").clickOnSaveButtonInEditStandardRating()
                .clickOnAddStandardRatingButton().selectStandardAgeRating("OFLC").clickOnNextButtonInAddStandardAgeRating().selectRatingValue("Not required").clickOnSaveButtonInEditStandardRating()
                .clickOnAddStandardRatingButton().selectStandardAgeRating("Russian").clickOnNextButtonInAddStandardAgeRating().selectRatingValue("Not required").clickOnSaveButtonInEditStandardRating()
                .clickOnAddStandardRatingButton().selectStandardAgeRating("GSRMR").clickOnNextButtonInAddStandardAgeRating().selectRatingValue("Not required").clickOnSaveButtonInEditStandardRating()
                .clickOnAddStandardRatingButton().selectStandardAgeRating("GRAC").clickOnNextButtonInAddStandardAgeRating().selectRatingValue("Not required").clickOnSaveButtonInEditStandardRating();

        createRom(testReport);
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        releaseInfoPage = myProductsPage.clickProductByGameCode(initialCode).clickReleases().selectInitialRelease();

        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = releaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, romInitialName);
        releaseInfoPage.nav().clickIssues().openRequiredApprovalTab().performAllIssueAtApprovalTab(false);

        NDPDevelopmentHome developmentHome = releaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
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
                .clickMyProducts().clickProductByGameCode(gameCode).clickReleases().selectInitialRelease().nav().clickSubmitButton()
                .clickSubmit();
        WaitUtils.idle(3000);
        assertEquals(TextConstants.submitToLotcheck, releaseInfoPage.getStatusValue(), TextConstants.initialSubmitSuccess);
    }

    @Test()
    @Order(15)
    public void pushLotcheckStatusToInTesting_Step11(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(slcmsUser);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickTesterAssignment(LotcheckRegion.NCL);
        CommonAction.findProductInLCQueue(lotcheckLandingPage, 50, LotcheckRegion.NCL.toString(), initialCode);
        LotcheckTestPlanRow lotcheckTestPlanRow = lotcheckQueuePage.clickPlannedTestDateNotSet();
        lotcheckTestPlanRow.selectAssignmentComplete().clickConfirm();

        lotcheckLandingPage.navigationToTestSetupReadyQueue(LotcheckRegion.NCL.toString());
        lotcheckQueuePage.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        lotcheckTestPlanRow.selectStartTestSetupProd().clickConfirm();

        lotcheckLandingPage.navigationToInTestSetupQueue(LotcheckRegion.NCL.toString());
        lotcheckQueuePage.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
        lotcheckTestPlanRow.selectSetupCompleteProd().clickConfirm();

        lotcheckLandingPage.navigationToInTestingQueue(LotcheckRegion.NCL.toString());
        lotcheckQueuePage.searchByInitialCode(initialCode).clickPlannedTestDateNotSet();
    }

    @Test()
    @Order(16)
    public void loginTestRailByTrDataManager_Step12(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(TestRailLoginPage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

        TestRailLoginPage testRailLoginPage = (TestRailLoginPage) currentPage;
        currentPage = testRailLoginPage.signInTestRail(trDataManagerUser)
                .navTopTestrail().navigateToManagerDashboard().checkErrorPopup();
    }

    @Test()
    @Order(17)
    public void assignTestcaseOfProductAsConditionForTrAdmin_Step13() {
        TestRailManagerDashboardPage managerDashboardPage = (TestRailManagerDashboardPage) currentPage;
        managerDashboardPage.actionFill().enterTestPlanNameFilter(gameCode)
                .selectStatusUntested();
        managerDashboardPage.actionFill().clickOnApplyFiltersButton();
        managerDashboardPage.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .selectCheckboxOfTheFirstTestPlan()
                .clickAssignTo()
                .selectSetValueAssignToRadio()
                .selectUser("TRAdmin English (NCL)")
                .clickOk()
                .waitingLoadingTestSuite();
        currentPage = managerDashboardPage.topNav().clickOnUserNameMenu().clickOnLogoutButton();
    }

    @Test()
    @Order(18)
    public void loginTestRailByTrAdmin_Step14() {
        TestRailLoginPage loginPage = (TestRailLoginPage) currentPage;
        currentPage = loginPage.signInTestRail(trAdminUser);
    }

    @Test()
    @Order(19)
    public void selectMyToDoTab_Step15() {
        TestRailDashboardPage dashboardPage = (TestRailDashboardPage) currentPage;
        currentPage = dashboardPage.navTopTestrail().navigateToMyToDo();
    }

    @Test()
    @Order(20)
    public void verifyMyToDoTabDisplayed_Expected15() {
        TestRailMyToDoPage myToDoPage = (TestRailMyToDoPage) currentPage;
        assertEquals("My Todo", myToDoPage.getPageTitle(), "Verify My todo tab is displayed");
    }

    @Test()
    @Order(21)
    public void selectTestPlanOnTestCondition_Step16() {
        TestRailMyToDoPage myToDoPage = (TestRailMyToDoPage) currentPage;
        myToDoPage.actionFill()
                .enterTestPlanNameFilter(initialCode);
        myToDoPage.actionFill().clickOnApplyFiltersButton();
        currentPage = myToDoPage.navTestPlanTable()
                .clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandCollapseTestSuite("HAC_LSチェック_")
                .clickOnTheFirstTestRun();
    }

    @Test()
    @Order(22)
    public void selectAnyTestCaseOfTestPlan_Step17() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        currentPage = testPlanTable.openTestcaseDetail(1);
    }

    @Test()
    @Order(23)
    public void selectSubmitBug_Step18() {
        TestRailDetailTestcaseDialog testcaseDialog = (TestRailDetailTestcaseDialog) currentPage;
        currentPage = testcaseDialog.clickSubmitBug();
    }

    @Test()
    @Order(24)
    public void verifyDisplayOfSubmitBugScreen_Expected18() {
        TestRailSubmitBugDialog submitBugDialog = (TestRailSubmitBugDialog) currentPage;
        assertAll(() -> assertEquals("Submit Bug", submitBugDialog.getModalTitle(), "Verify submit bug dialog displayed"),
                () -> assertTrue(submitBugDialog.isNeedsTranslationUnChecked(), "Verify the checkbox of \"Needs Translation\" is un-checked"));
    }

    @Test()
    @Order(25)
    public void checkNeedsTranslationCheckbox_Step19() {
        TestRailSubmitBugDialog submitBugDialog = (TestRailSubmitBugDialog) currentPage;
        submitBugDialog.checkNeedsTranslation();
    }

    @Test()
    @Order(26)
    public void verifyNeedsTranslationCheckboxChecked_Expected19() {
        TestRailSubmitBugDialog submitBugDialog = (TestRailSubmitBugDialog) currentPage;
        assertFalse(submitBugDialog.isNeedsTranslationUnChecked(), "Verify the checkbox of \"Needs Translation\" is checked");
    }

    @Test()
    @Order(27)
    public void selectCancel_Step20() {
        TestRailSubmitBugDialog submitBugDialog = (TestRailSubmitBugDialog) currentPage;
        currentPage = submitBugDialog.clickCancel();
    }

    @Test()
    @Order(28)
    public void verifyBackToTestCaseDialog_Expected20() {
        TestRailDetailTestcaseDialog testcaseDialog = (TestRailDetailTestcaseDialog) currentPage;
        assertTrue(testcaseDialog.testcaseDetailModalIsDisplayed(), "Verify testcase modal is displayed");
    }

    @Test()
    @Order(29)
    public void selectSubmitBugAgain_Step21() {
        TestRailDetailTestcaseDialog testcaseDialog = (TestRailDetailTestcaseDialog) currentPage;
        currentPage = testcaseDialog.clickSubmitBug();
    }

    @Test()
    @Order(30)
    public void verifyNeedsTranslationUnChecked_Expected21() {
        TestRailSubmitBugDialog submitBugDialog = (TestRailSubmitBugDialog) currentPage;
        assertAll(() -> assertEquals("Submit Bug", submitBugDialog.getModalTitle(), "Verify submit bug dialog displayed"),
                () -> assertTrue(submitBugDialog.isNeedsTranslationUnChecked(), "Verify the checkbox of \"Needs Translation\" is un-checked"));
    }

    @Test()
    @Order(31)
    public void selectCancel_Step22() {
        TestRailSubmitBugDialog submitBugDialog = (TestRailSubmitBugDialog) currentPage;
        currentPage = submitBugDialog.clickCancel().clickClose();
    }

    @Test()
    @Order(32)
    public void selectAddRemark_Step23() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        currentPage = testPlanTable.expandCollapseTestSuite("HAC_LSチェック_").clickAddRemark();
    }

    @Test()
    @Order(33)
    public void verifyDisplayAddRemarkScreen_Expected23() {
        TestRailAddRemarkDialog addRemarkDialog = (TestRailAddRemarkDialog) currentPage;
        assertEquals("Add Remark", addRemarkDialog.getModalName(), "Verify the Add Remark modal window is displayed");
    }

    @Test()
    @Order(34)
    public void enterLegalInformationAndCaseTypeIsMemo_Step24() {
        TestRailAddRemarkDialog addRemarkDialog = (TestRailAddRemarkDialog) currentPage;
        addRemarkDialog.enterTitle(remarkMemoCaseName)
                .selectMemoType();
    }

    @Test()
    @Order(35)
    public void selectSubmit_Step25() {
        TestRailAddRemarkDialog addRemarkDialog = (TestRailAddRemarkDialog) currentPage;
        currentPage = addRemarkDialog.clickSubmit();
    }

    @Test()
    @Order(36)
    public void verifyDisplayTestcaseAddRemarkCreated_Expected25() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        testPlanTable.expandCollapseTestSuite("Remarks").expandCollapseTestRun("All Test Cases");
        boolean isRemarkTestcaseDisplayed = testPlanTable.isTestcaseDisplayed("Remarks", remarkMemoCaseName);
        assertTrue(isRemarkTestcaseDisplayed, "Verify testcase remark added is displayed");
    }

    @Test()
    @Order(37)
    public void selectAddRemark_Step26() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        currentPage = testPlanTable.clickAddRemark();
    }

    @Test()
    @Order(38)
    public void verifyDisplayAddRemarkScreen_Expected26() {
        TestRailAddRemarkDialog addRemarkDialog = (TestRailAddRemarkDialog) currentPage;
        assertEquals("Add Remark", addRemarkDialog.getModalName(), "Verify the Add Remark modal window is displayed");
    }

    @Test()
    @Order(39)
    public void enterLegalInformationAndCaseTypeIsTestcase_Step27() {
        TestRailAddRemarkDialog addRemarkDialog = (TestRailAddRemarkDialog) currentPage;
        addRemarkDialog.enterTitle(remarkTestcaseName)
                .selectTestcaseType()
                .enterEstimateTestingTime("10");
    }

    @Test()
    @Order(40)
    public void clickSubmit_Step28() {
        TestRailAddRemarkDialog addRemarkDialog = (TestRailAddRemarkDialog) currentPage;
        currentPage = addRemarkDialog.clickSubmit();
    }

    @Test()
    @Order(41)
    public void verifyDisplayRemarkTestcase_Expected28() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        testPlanTable.actionFill().selectUnAssignedCheckbox();
        testPlanTable.actionFill().clickOnApplyFiltersButton();
        testPlanTable.clickOnPlannedTestDateNotSetBar()
                .clickOnTheFirstTestPlanBar()
                .waitingLoadingTestSuite()
                .expandCollapseTestSuite("Remarks")
                .expandCollapseTestRun("All Test Cases");
        boolean isRemarkTestcaseDisplayed = testPlanTable.isTestcaseDisplayed("Remarks", remarkTestcaseName);
        assertTrue(isRemarkTestcaseDisplayed, "Verify testcase remark added is displayed");
    }

    @Test()
    @Order(42)
    public void selectAddProblemDescription_Step29() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        currentPage = testPlanTable.clickAddProblemDescription();
    }

    @Test()
    @Order(43)
    public void verifyDisplayAddProblemDescriptionScreen_Expected29() {
        TestRailAddProblemDescriptionDialog addProblemDescriptionDialog = (TestRailAddProblemDescriptionDialog) currentPage;
        assertEquals("Add Problem Description", addProblemDescriptionDialog.getModalName(), "Verify displaying Add Problem Description screen");
    }

    @Test()
    @Order(44)
    public void enterLegalInformation_Step30() {
        TestRailAddProblemDescriptionDialog addProblemDescriptionDialog = (TestRailAddProblemDescriptionDialog) currentPage;
        addProblemDescriptionDialog.enterTitle(addProblemDescriptionName);
    }

    @Test()
    @Order(45)
    public void clickSubmit_Step31() {
        TestRailAddProblemDescriptionDialog addProblemDescriptionDialog = (TestRailAddProblemDescriptionDialog) currentPage;
        currentPage = addProblemDescriptionDialog.clickSubmit();
    }

    @Test()
    @Order(46)
    public void verifyDisplayAddProblemDescriptionTestcase_Expected31() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        testPlanTable.expandCollapseTestSuite("Problem Description")
                .expandCollapseTestRun("All Test Cases");
        boolean isProblemDescriptionTestcaseDisplayed = testPlanTable.isTestcaseDisplayed("Problem Description", addProblemDescriptionName);
        testPlanTable.expandCollapseTestSuite("Problem Description");
        assertTrue(isProblemDescriptionTestcaseDisplayed, "Verify problem description testcase added is displayed");
    }

    @Test()
    @Order(47)
    public void selectTestcaseInStep25_Step32() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        currentPage = testPlanTable.openTestCaseDetail("Remarks", remarkMemoCaseName);
    }

    @Test()
    @Order(48)
    public void reExecutingStep18To22_Step33() {
        TestRailDetailTestcaseDialog testcaseDialog = (TestRailDetailTestcaseDialog) currentPage;
        TestRailSubmitBugDialog submitBugDialog = testcaseDialog.clickSubmitBug();
        submitBugModalTile_Step18 = submitBugDialog.getModalTitle();
        isNeedsTranslationUnChecked_step18 = submitBugDialog.isNeedsTranslationUnChecked();
        submitBugDialog.checkNeedsTranslation();
        isNeedsTranslationUnChecked_Step19 = submitBugDialog.isNeedsTranslationUnChecked();
        submitBugDialog.clickCancel();
        testcaseDetailModalIsDisplayed = testcaseDialog.testcaseDetailModalIsDisplayed();
        testcaseDialog.clickSubmitBug();
        submitBugModalTile_Step21 = submitBugDialog.getModalTitle();
        isNeedsTranslationUnChecked_Step21 = submitBugDialog.isNeedsTranslationUnChecked();
        currentPage = submitBugDialog.clickCancel().clickClose();
    }

    @Test()
    @Order(49)
    public void verifyDisplay_Expected33() {
        assertAll(() -> assertEquals("Submit Bug", submitBugModalTile_Step18, "Verify submit bug dialog displayed"),
                () -> assertTrue(isNeedsTranslationUnChecked_step18, "Verify the checkbox of \"Needs Translation\" is un-checked"),
                () -> assertFalse(isNeedsTranslationUnChecked_Step19, "Verify the checkbox of \"Needs Translation\" is checked"),
                () -> assertTrue(testcaseDetailModalIsDisplayed, "Verify display testcase detail modal"),
                () -> assertEquals("Submit Bug", submitBugModalTile_Step21, "Verify submit bug dialog displayed"),
                () -> assertTrue(isNeedsTranslationUnChecked_Step21, "Verify the checkbox of \"Needs Translation\" is un-checked"));
    }

    @Test()
    @Order(50)
    public void selectTestcaseInStep25_Step34() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        currentPage = testPlanTable.openTestCaseDetail("Remarks", remarkTestcaseName);
    }

    @Test()
    @Order(51)
    public void reExecutingStep18To22_Step35() {
        TestRailDetailTestcaseDialog testcaseDialog = (TestRailDetailTestcaseDialog) currentPage;
        TestRailSubmitBugDialog submitBugDialog = testcaseDialog.clickSubmitBug();
        submitBugModalTile_Step18 = submitBugDialog.getModalTitle();
        isNeedsTranslationUnChecked_step18 = submitBugDialog.isNeedsTranslationUnChecked();
        submitBugDialog.checkNeedsTranslation();
        isNeedsTranslationUnChecked_Step19 = submitBugDialog.isNeedsTranslationUnChecked();
        submitBugDialog.clickCancel();
        testcaseDetailModalIsDisplayed = testcaseDialog.testcaseDetailModalIsDisplayed();
        testcaseDialog.clickSubmitBug();
        submitBugModalTile_Step21 = submitBugDialog.getModalTitle();
        isNeedsTranslationUnChecked_Step21 = submitBugDialog.isNeedsTranslationUnChecked();
        currentPage = submitBugDialog.clickCancel().clickClose();
    }

    @Test()
    @Order(52)
    public void verifyDisplay_Expected35() {
        assertAll(() -> assertEquals("Submit Bug", submitBugModalTile_Step18, "Verify submit bug dialog displayed"),
                () -> assertTrue(isNeedsTranslationUnChecked_step18, "Verify the checkbox of \"Needs Translation\" is un-checked"),
                () -> assertFalse(isNeedsTranslationUnChecked_Step19, "Verify the checkbox of \"Needs Translation\" is checked"),
                () -> assertTrue(testcaseDetailModalIsDisplayed, "Verify display testcase detail modal"),
                () -> assertEquals("Submit Bug", submitBugModalTile_Step21, "Verify submit bug dialog displayed"),
                () -> assertTrue(isNeedsTranslationUnChecked_Step21, "Verify the checkbox of \"Needs Translation\" is un-checked"));
    }

    @Test()
    @Order(53)
    public void selectTestcaseInStep25_Step36() {
        TestRailTestPlanTable testPlanTable = (TestRailTestPlanTable) currentPage;
        currentPage = testPlanTable.expandCollapseTestSuite("Problem Description").openTestCaseDetail("Problem Description", addProblemDescriptionName);
    }

    @Test()
    @Order(54)
    public void reExecutingStep18To22_Step37() {
        TestRailDetailTestcaseDialog testcaseDialog = (TestRailDetailTestcaseDialog) currentPage;
        TestRailSubmitBugDialog submitBugDialog = testcaseDialog.clickSubmitBug();
        submitBugModalTile_Step18 = submitBugDialog.getModalTitle();
        isNeedsTranslationUnChecked_step18 = submitBugDialog.isNeedsTranslationUnChecked();
        submitBugDialog.checkNeedsTranslation();
        isNeedsTranslationUnChecked_Step19 = submitBugDialog.isNeedsTranslationUnChecked();
        submitBugDialog.clickCancel();
        testcaseDetailModalIsDisplayed = testcaseDialog.testcaseDetailModalIsDisplayed();
        testcaseDialog.clickSubmitBug();
        submitBugModalTile_Step21 = submitBugDialog.getModalTitle();
        isNeedsTranslationUnChecked_Step21 = submitBugDialog.isNeedsTranslationUnChecked();
        currentPage = submitBugDialog.clickCancel().clickClose();
    }

    @Test()
    @Order(55)
    public void verifyDisplay_Expected37() {
        assertAll(() -> assertEquals("Submit Bug", submitBugModalTile_Step18, "Verify submit bug dialog displayed"),
                () -> assertTrue(isNeedsTranslationUnChecked_step18, "Verify the checkbox of \"Needs Translation\" is un-checked"),
                () -> assertFalse(isNeedsTranslationUnChecked_Step19, "Verify the checkbox of \"Needs Translation\" is checked"),
                () -> assertTrue(testcaseDetailModalIsDisplayed, "Verify display testcase detail modal"),
                () -> assertEquals("Submit Bug", submitBugModalTile_Step21, "Verify submit bug dialog displayed"),
                () -> assertTrue(isNeedsTranslationUnChecked_Step21, "Verify the checkbox of \"Needs Translation\" is un-checked"));
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
                .selectCERORating("CERO: Not required")
                .selectOFLCRating("OFLC: Not required")
                .selectBBFCRating("BBFC: Not required")
                .selectGSRMRRating("GSRMR: Not required")
                .selectPEGIRating("PEGI: Not required")
                .selectUSKRating("USK: Not required")
                .selectRussiaRating("Russian: Not required")
                .selectACBRating("ACB: Not required")
                .selectGRACGCRBRating("GRACGCRB: Not required")
                .clickOnCreateButton()
                .waitIsLoaded();
        romInitialName = romCreateRom.getRomNameTable(applicationId);
        romCreateRom.clickOnROMFileOnROMTable(romInitialName);
        WaitUtils.idle(5000);
    }

}

