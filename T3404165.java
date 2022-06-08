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
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionTestScenarios;
import net.nintendo.automation.ui.models.ndp.NDPCreateProductPage;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPLicensingWaiverRequestsTab;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.NDPRequestGameCodePage;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_internal.NDPInternalPage;
import net.nintendo.automation.ui.models.ndp.existing_internal.view_product_dashboard.NDPSettingTab;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardProductInfoTab;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoAgeRating;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoFeaturesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoGuidelinesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoIssuePage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoManageIARCRatingsPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoROMUpload;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoReleaseListNav;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoTestScenarios;
import net.nintendo.automation.ui.models.rom.ROMCreateRom;
import net.nintendo.automation.ui.models.rom.ROMLoginPage;
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
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classname: T3404165
 * Version: 1.0.0
 * Purpose:
 */

@NDP
@SLCMS
//@ScenarioTest
@Tag("T3404165")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3404165 extends CommonBaseTest {
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
    private static String getProductName;
    private static String getInitialCode;
    private static String getProductType;
    private static String iarcCertID;
    private static String baseProductName;
    private static String baseInitialCode;

    @AfterAll
    static void terminateBrowser() {
        UserManager.releaseUser(ServiceType.NDP, ndpUser);
        UserManager.releaseUser(ServiceType.SLCMS, slcmsUser);
        UserManager.releaseUser(ServiceType.SEARCH, romUser);
        UserManager.releaseUser(ServiceType.NDP, licUser);
        UserManager.releaseUser(ServiceType.PMMS, testRailUser);
    }

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.ADMIN_USER);
        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
        slcmsUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);
        testRailUser = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER);
        productName = DataGen.getRandomProductName();
        baseProductName = DataGen.getRandomProductName()+"_Base";
    }
    @Test
    @Order(0)
    public void createBaseProduct_StepCondition(IJUnitTestReporter testReport) {
        NDPHomePage ndpHomePage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN,testReport);
        NDPMyProductsPage myProductsPage = ndpHomePage.acceptCookie()
                .clickSignInPage()
                .signIn(ndpUser)
                .clickMyProducts()
                .createNewProduct()
                .selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(TextConstants.third_Party_Type)
                .selectTargetDeliveryFormat(TextConstants.both_Target_Type)
                .selectDigitalChinaSalesRegion()
                .selectPhysicalChinaSalesRegion()
                .selectProductName(baseProductName)
                .clickCreateButton();
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(baseProductName);
        CommonAction.issueGameCode(ndpProductDashboardPage, 60, "NO");
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();
        baseInitialCode = ndpProductDashboardProductInfoTab.getInitialCode();
    }
    @Test()
    @Order(1)
    public void loginToNDPAsPartner_Step(IJUnitTestReporter testReport) {
        browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
    }


    @Test()
    @Order(2)
    public void createAProductAndSetDemoType_Step(IJUnitTestReporter testReport) {
        NDPMyProductsPage myProductsPage = browser.getPage(NDPMyProductsPage.class, testReport);
        NDPCreateProductPage createProductPage = myProductsPage.createNewProduct();
        createProductPage.selectProductType(TextConstants.demo_Product)
                .selectPublishingRelationship(TextConstants.third_Party_Type)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalJapanSalesRegion()
                .selectProductName(productName)
                .selectProductNameKana(TextConstants.productKana)
                .selectBasedProduct(baseProductName + " (" + baseInitialCode + ")")
                .clickCreateButton();
        getProductName = myProductsPage.getProductNameInRow(1);
        getInitialCode = myProductsPage.getGameCodeInRow(1);
        getProductType = myProductsPage.getProductTypeInRow(1);

        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPRequestGameCodePage issueGameCode = ndpProductDashboardPage.issueGameCode();
        issueGameCode.setExpectedLotcheckSubmissionDate()
                .setExpectedReleaseDate(47)
                .clickIssue();
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();
        applicationID = ndpProductDashboardProductInfoTab.getApplicationID();
        gameCode = ndpProductDashboardProductInfoTab.getGameCode();
        initialCode = ndpProductDashboardProductInfoTab.getInitialCode();

        NDPDevelopmentHome developmentHome = ndpProductDashboardPage.clickBackToMyProducts()
                .clickOnUserNameMenu()
                .clickOnSignOutButton()
                .clickSignInPage()
                .signIn(TextConstants.lic_pwr_user_NCL, TextConstants.nintendo_approval_Password);
        NDPInternalPage internalPage = developmentHome.clickInternalTab();
        NDPSettingTab settingTab = internalPage.clickProductSearchButton()
                .waitingLoadingValueSearch()
                .clickSearchContent(productName)
                .selectElementFromDropDownTable2()
                .clickSettingTab()
                .selectAllowRIDKioskSetting()
                .clickSaveButton();

        //create release info - set Demo Type
        NDPReleaseInfoPage releaseInfoPage = myProductsPage.clickOnUserNameMenu()
                .clickOnSignOutButton()
                .clickSignInPage()
                .signIn(ndpUser)
                .clickMyProducts()
                .clickProductByName(productName)
                .clickReleases()
                .selectInitialRelease();
        releaseInfoPage.selectDemoType(" RID Kiosk and eShop");
        CommonAction.selectROMSameAllRegion(releaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);
    }

    @Test()
    @Order(3)
    public void verifyProductIsCreatedSuccessfully_Expected(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        assertAll("New Product is created successfully",
                () -> assertEquals(productName, getProductName, "Product Name should be correct"),
                () -> assertEquals("--", getInitialCode, "Game Code should be correct"),
                () -> assertEquals("Demo", getProductType, "Product Type should be correct"));
    }

    @Test()
    @Order(4)
    public void navigateAgeRatingScreen_Step(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        releaseInfoPage.nav().clickAgeRating();
    }

    @Test()
    @Order(5)
    public void verifyUIOfAgeRatingScreen_Expected(IJUnitTestReporter testReport) {
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReport);
        ndpReleaseInfoAgeRating.verifyAddIARCRatingButtonClickable()
                .verifyAddStandardRatingButtonClickable();
    }

    @Test()
    @Order(6)
    public void addNOARegion_Step(IJUnitTestReporter testReport) {
        NDPReleaseInfoReleaseListNav nav = browser.getPage(NDPReleaseInfoReleaseListNav.class, testReport);
        NDPReleaseInfoPage releaseInfoPage = nav.clickReleaseInfo();
        releaseInfoPage.selectDigitalAmericasSalesRegion().save();
        releaseInfoPage.save();
    }

    @Test()
    @Order(7)
    public void verifyRequestIARCRatingEnable_Expected(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        releaseInfoPage.nav().clickAgeRating()
                .verifyAddIARCRatingButtonClickable();
    }

    @Test()
    @Order(8)
    public void pressRequestIARCRating_Step(IJUnitTestReporter testReport) {
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReport);
        ndpReleaseInfoAgeRating.clickOnAddIARCRatingButton();
    }

    @Test()
    @Order(9)
    public void verifyRequestRatingForm_Expected(IJUnitTestReporter testReport) {
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReport);
        ndpReleaseInfoAgeRating.verifyRequestNewIARCCertificateClickable()
                .verifyImportExistingIARCCertificateClickable();
        assertAll(() -> assertTrue(ndpReleaseInfoAgeRating.isContactEmailAddressRequiredFieldDisplay(), "The Contact Email Address is asterisk field remark for required field"),
                () -> assertTrue(ndpReleaseInfoAgeRating.isPublicEmailAddressRequiredFieldDisplay(), "The Public Email Address is asterisk field remark for required field"),
                () -> assertFalse(ndpReleaseInfoAgeRating.isContinueButtonEnabled(), "The Continue Button is Disabled"));
    }

    @Test()
    @Order(10)
    public void selectRequestNewIARCRatingAndFillOutForm_Step(IJUnitTestReporter testReport) {
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReport)
                .enterContactEmailAddress(ndpUser.getUserName() + TextConstants.inbucket_Suffix)
                .enterPublicEmailAddress(ndpUser.getUserName() + TextConstants.inbucket_Suffix)
                .selectIARCRequestNewIARCCertificate();
        assertTrue(ndpReleaseInfoAgeRating.isContinueButtonEnabled(), "The Continue Button is Enabled");
        ndpReleaseInfoAgeRating.clickRequestNewIarcContinue();
    }

    @Test()
    @Order(11)
    public void verifyRequestNewIARCRating_Expected(IJUnitTestReporter testReport) {
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReport);
        assertTrue(ndpReleaseInfoAgeRating.isViewIARCAgeRatingsButtonDisplayed(), "The Screen navigate to Background Tab in this window");
        WaitUtils.idle(6000);
        browser.switchToTabByTitle(IARCQuestionnairePage.pageTitle);
    }

    @Test()
    @Order(12)
    public void fillOutTheIARCQuestionnaires_Step(IJUnitTestReporter testReport) {
        IARCQuestionnaireFinalRatingPage questionnaireFinalRating =
                browser.getPage(IARCQuestionnairePage.class, testReport)
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
        iarcCertID = questionnaireFinalRating.getIARCCertID();
        browser.closeTabByTitle(IARCQuestionnairePage.pageTitle); // Close IARC tab because currently, closeBrowser only closes single tab

        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReport);
        ndpReleaseInfoAgeRating.clickViewIARCAgeRatings();
    }

    @Test()
    @Order(13)
    public void verifyManageIARCRatingsEnabled_Expected(IJUnitTestReporter testReport) {
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReport);
        assertTrue(ndpReleaseInfoAgeRating.isManageIARCRatingsEnabled(), "The Manage IARC Ratings link will be enable");
    }

    @Test()
    @Order(14)
    public void selectManageIARCRatingsLink_Step(IJUnitTestReporter testReport) {
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReport);
        ndpReleaseInfoAgeRating.selectManageIARCRatingsLink();
    }

    @Test()
    @Order(15)
    public void verifyManageIARCRatingPage_Expected(IJUnitTestReporter testReport) {
        NDPReleaseInfoManageIARCRatingsPage manageIARCRatingsPage = browser.getPage(NDPReleaseInfoManageIARCRatingsPage.class, testReport);
        assertAll(() -> assertEquals(productName, manageIARCRatingsPage.getProductName(), "Product Name is displayed correct"),
                () -> assertEquals(iarcCertID, manageIARCRatingsPage.getIARCCertID(), "IARC CertID is displayed correct"),
                () -> assertEquals(ndpUser.getUserName() + TextConstants.inbucket_Suffix, manageIARCRatingsPage.getContactEmailAddress(), "Contact Email Address is displayed correct"),
                () -> assertEquals(ndpUser.getUserName() + TextConstants.inbucket_Suffix, manageIARCRatingsPage.getPublicEmailAddress(), "Public Email Address is displayed correct"),
                () -> assertEquals("", manageIARCRatingsPage.getLastUpdate(), "Last update is displayed null"),
                () -> assertTrue(manageIARCRatingsPage.isUpdateIARCDataLinkEnabled(), "Update IARC Data Behavior is Enabled"),
                () -> assertTrue(manageIARCRatingsPage.isIARCHistoryTabDisplayed(), "IARC History tab is displayed"),
                () -> assertTrue(manageIARCRatingsPage.isDateInformationDisplayed(), "Date Information field is displayed"),
                () -> assertTrue(manageIARCRatingsPage.isChangeDetailsInformationDisplayed(), "Change Details Information field is displayed"));
    }

    @Test()
    @Order(16)
    public void selectUpdateIARCDataBehavior_Step(IJUnitTestReporter testReport) {
        NDPReleaseInfoManageIARCRatingsPage manageIARCRatingsPage = browser.getPage(NDPReleaseInfoManageIARCRatingsPage.class, testReport);
        manageIARCRatingsPage.selectUpdateIARCDataBehavior();
    }

    @Test()
    @Order(17)
    public void verifyTwoBehaviorDisplayed_Expected(IJUnitTestReporter testReport) {
        NDPReleaseInfoManageIARCRatingsPage manageIARCRatingsPage = browser.getPage(NDPReleaseInfoManageIARCRatingsPage.class, testReport);
        assertAll(() -> assertTrue(manageIARCRatingsPage.isRequestNewIARCRatingButtonDisplayed(), "Request New IARC Rating Button is displayed"),
                () -> assertTrue(manageIARCRatingsPage.isEditContactEmailsButtonDisplayed(), "Edit Contact Emails Button is displayed"));
    }

    @Test()
    @Order(18)
    public void navigateToReleaseInfoAndSelectEshopOnlyDemoType_Step(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoManageIARCRatingsPage.class, testReport)
                .selectBackToRatingContent()
                .nav()
                .clickReleaseInfo();
        assertTrue(releaseInfoPage.isKioskEshopDemoTypeSelected(), "Value is selected correct");
        releaseInfoPage.selectDemoType(" eShop Only");
    }

    @Test()
    @Order(19)
    public void navigateToReleaseInfoAndSelectKioskOnlyDemoType_Step(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        releaseInfoPage.selectDemoType(" RID Kiosk Only");
    }

    @Test()
    @Order(20)
    public void navigateToFeatureTabAndSetSupportJoyCon_Step(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage releaseInfoPage = browser.getPage(NDPReleaseInfoPage.class, testReport);
        releaseInfoPage.nav().clickFeaturesAndCloseAlert();
        CommonAction.getFeatureOptionData(releaseInfoPage, "T3404165", "T3404165", TextConstants.allOff, false, "", true, TextConstants.allOff);

    }

    @Test()
    @Order(21)
    public void navigateToScenariosTabAndPerformEnterText_Step(IJUnitTestReporter testReport) {
        NDPReleaseInfoTestScenarios testScenarios = browser.getPage(NDPReleaseInfoFeaturesPage.class, testReport).nav().clickTestScenarios();
        WaitUtils.idle(6000);
        //set Joy-con(R) field
        testScenarios.setFocusedTestScene("Input Devices", "How to check vibration feature on Joy-Con (R)")
                .expandCategory()
                .expandTestScene()
                .enterNameThisMethod("Text1", 1)
                .enterExplainHowToDemonstrateTheMethod("Text2", 1)
                //set Joy-con(L) field
                .setFocusedTestScene("Input Devices", "How to check vibration feature on Joy-Con (L)")
                .expandTestScene()
                .enterNameThisMethod("Text3", 1)
                .enterExplainHowToDemonstrateTheMethod("Text4", 1);
    }

    @Test()
    @Order(22)
    public void performSaveTestScenariosPage_Step(IJUnitTestReporter testReport) {
        NDPReleaseInfoTestScenarios testScenarios = browser.getPage(NDPReleaseInfoTestScenarios.class, testReport);
        testScenarios.clickOnSaveAnContinueButton();
    }

    @Test()
    @Order(23)
    public void navigateBackToTestScenariosPage_Step(IJUnitTestReporter testReport) {
        NDPReleaseInfoAgeRating ageRating = browser.getPage(NDPReleaseInfoAgeRating.class, testReport);
        ageRating.nav().clickTestScenarios();
        WaitUtils.idle(6000);
    }

    @Test()
    @Order(24)
    public void confirmMethodNameOfTestScenarios_Step(IJUnitTestReporter testReport) {
        NDPReleaseInfoTestScenarios testScenarios = browser.getPage(NDPReleaseInfoTestScenarios.class, testReport);
        testScenarios.setFocusedTestScene("Input Devices", "How to check vibration feature on Joy-Con (R)")
                .expandCategory()
                .expandTestScene();
        assertAll(() -> assertEquals("Text1", testScenarios.getNameThisMethod(1), "The name method of How to check vibration feature on Joy-Con (R) should remain unchanged"),
                () -> assertEquals("Text2", testScenarios.getExplainHowToDemonstrateTheMethod(1), "The Explain How To Demonstrate of How to check vibration feature on Joy-Con (R) should remain unchanged"));

        testScenarios.setFocusedTestScene("Input Devices", "How to check vibration feature on Joy-Con (L)")
                .expandTestScene();
        assertAll(() -> assertEquals("Text3", testScenarios.getNameThisMethod(1), "The name method of How to check vibration feature on Joy-Con (L) should remain unchanged"),
                () -> assertEquals("Text4", testScenarios.getExplainHowToDemonstrateTheMethod(1), "The Explain How To Demonstrate of How to check vibration feature on Joy-Con (L) should remain unchanged"));

    }

    @Test()
    @Order(25)
    public void submitReleaseToLotcheck_Step(IJUnitTestReporter testReport) {
        NDPReleaseInfoTestScenarios testScenarios = browser.getPage(NDPReleaseInfoTestScenarios.class, testReport);
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelinesPage = testScenarios.nav().clickGuidelines();
        ndpReleaseInfoGuidelinesPage.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();
        //create file ROM
        romBrowser = BrowserManager.getNewInstance();
        ROMLoginPage romLoginPage = romBrowser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN);
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom
                .selectRequester(TextConstants.test_User)
                .selectType(TextConstants.initial_Release)
                .selectDemo("Yes")
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .enterProductName(productName)
                .enterApplicationId(applicationID)
                .selectPublishingRelationShip(TextConstants.third_Party_Type)
                .enterDisplayVersion("1.0.0")
                .enterReleaseVersion("00")
                .selectCERORating("CERO IARC: 3+")
                .selectClassIndRating("ClassInd: Livre (General Audiences)")
                .selectESRBRating("ESRB: EVERYONE")
                .clickOnCreateButton()
                .waitIsLoaded();
        fileRomName = romCreateRom.getRomName();
        romCreateRom.clickOnROMFileOnROMTable(applicationID);
        WaitUtils.idle(8000);
        romCreateRom.clickOnUserNameMenu().clickOnLogoutButton();
        BrowserManager.closeBrowser(romBrowser);

        //Upload ROM
        NDPReleaseInfoReleaseListNav releaseListNav = browser.getPage(NDPReleaseInfoReleaseListNav.class, testReport);
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = releaseListNav.clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
        //Issues
        NDPReleaseInfoIssuePage issuePage = releaseListNav.clickIssues();
        //WaitUtils.idle(20000);
        CommonAction.enterReasonForRequest(issuePage);
        releaseListNav.clickOnUserNameMenu().clickOnSignOutButton();
        //Approval Issue TaskRequested
        NDPDevelopmentHome developmentHome = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN).clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, gameCode);
        releaseListNav.clickOnUserNameMenu().clickOnSignOutButton();
        //submit product
        NDPReleaseInfoPage ndpReleaseInfoPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN)
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
    @Order(26)
    public void openTestScenariosTabOnLotcheck_Step(IJUnitTestReporter testReport) {
        LotcheckHomePage lotcheckHomePage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.signIn(TextConstants.noa_VanN_Email, TextConstants.noa_VanN_Password);
        LotcheckQueuePage lotcheckQueuePage = lotcheckLandingPage.clickTesterAssignment(LotcheckRegion.NCL);
        CommonAction.findProductInLCQueue(lotcheckLandingPage, 30, LotcheckRegion.NCL.toString(), initialCode);
        lotcheckQueuePage.clickPlannedTestDateNotSet();
        LotcheckReportSubmissionOverview lotcheckReportSubmissionOverview = lotcheckQueuePage.navigateToSubmissionLevelInTesplan("00");
        String testPlanStatus = lotcheckReportSubmissionOverview.matrix().getTestPlanStatus(0);
        assertTrue(testPlanStatus.equalsIgnoreCase("Assign Testers"), "Test plan status is displayed correctly");
        lotcheckReportSubmissionOverview.navBar().clickTestScenarios();
    }

    @Test()
    @Order(27)
    public void verifyTestScenariosOnLotcheck_Expected(IJUnitTestReporter testReport) {
        LotcheckReportSubmissionTestScenarios testScenarios = browser.getPage(LotcheckReportSubmissionTestScenarios.class, testReport);
        testScenarios.selectExpandAllCollapseAllButton();
        String methodNameJoyConR = testScenarios.getMethodNameOfTestScenario("How to check vibration feature on Joy-Con (R)");
        String explainJoyConR = testScenarios.getExplainHowToDemonstrateThisMethod("How to check vibration feature on Joy-Con (R)");
        String methodNameJoyConL = testScenarios.getMethodNameOfTestScenario("How to check vibration feature on Joy-Con (L)");
        String explainJoyConL = testScenarios.getExplainHowToDemonstrateThisMethod("How to check vibration feature on Joy-Con (L)");
        assertAll(() -> assertEquals("Text1", methodNameJoyConR, "SLCMS: The name method of How to check vibration feature on Joy-Con (R) should remain unchanged."),
                () -> assertEquals("Text2", explainJoyConR, "SLCMS: The Explain How To Demonstrate of How to check vibration feature on Joy-Con (R) should remain unchanged"),
                () -> assertEquals("Text3", methodNameJoyConL, "SLCMS: The name method of How to check vibration feature on Joy-Con (L) should remain unchanged."),
                () -> assertEquals("Text4", explainJoyConL, "SLCMS: The Explain How To Demonstrate of How to check vibration feature on Joy-Con (L) should remain unchanged"));

    }
}
