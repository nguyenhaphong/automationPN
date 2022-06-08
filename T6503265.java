package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.junit.TestLogger;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.data.DataGen;
import net.nintendo.automation.ui.models.admin.AdminAllOrganizationsTab;
import net.nintendo.automation.ui.models.admin.AdminDevelopmentHome;
import net.nintendo.automation.ui.models.admin.AdminEditOrganizationPage;
import net.nintendo.automation.ui.models.admin.AdminHomePage;
import net.nintendo.automation.ui.models.admin.AdminUserDetailPage;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.ndidMS.ManagementSystemLogin;
import net.nintendo.automation.ui.models.ndp.NDPCompanyNDARequiredPage;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPLicensingWaiverRequestsTab;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.NDPRequestGameCodePage;
import net.nintendo.automation.ui.models.ndp.NDPSignInPage;
import net.nintendo.automation.ui.models.ndp.NDPViewTaskPage;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardProductInfoTab;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoIssuePage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoROMUpload;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoReleaseListNav;
import net.nintendo.automation.ui.models.rom.ROMCreateRom;
import net.nintendo.automation.ui.models.rom.ROMLoginPage;
import net.nintendo.automation.ui.users.User;
import net.nintendo.automation.ui.users.UserManager;
import net.nintendo.automation.utils.WaitUtils;
import net.nintendo.automation_core.common_client.enums.ServiceType;
import net.nintendo.automation_core.common_client.enums.UserType;
import net.nintendo.automation_core.extent_reports.extensions.IJUnitTestReporter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("T6503265")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T6503265 extends CommonBaseTest {
    private static Browser browser;
    private static User ndpUser;
    private static User adminUser;
    private static User romUser;
    private static String applicationId;
    private static String romInitialName;
    private static String organizationName;
    private static String randomDataForOrgName;
    private static User licPowerUser;
    private static User licApproverUser;
    private static User licApprovalCoordUser;
    private static CommonUIComponent currentPage;
    private static boolean isChinaTargetSalesRegionsDigitalChecked;
    private static boolean isChinaTargetSalesRegionsPhysicalChecked;
    private static String productName;
    private static String initialCode;
    private static TestLogger logger;
    private static String freeToPlayValue;

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = new User();
        ndpUser.setPassword(TextConstants.nintendo_approval_Password);
        ndpUser.setUserName(DataGen.getRandomUserName());

        adminUser = new User();
        adminUser.setPassword(TextConstants.nintendo_approval_Password);
        adminUser.setUserName(TextConstants.omniAdmin);
        licPowerUser = new User();
        licPowerUser.setUserName(TextConstants.lic_pwr_user_NCL);
        licPowerUser.setPassword(TextConstants.nintendo_approval_Password);
        licApprovalCoordUser = new User();
        licApprovalCoordUser.setUserName(TextConstants.lic_approval_coord_NCL);
        licApprovalCoordUser.setPassword(TextConstants.nintendo_approval_Password);
        licApproverUser = new User();
        licApproverUser.setUserName(TextConstants.lic_approval_NCL);
        licApproverUser.setPassword(TextConstants.nintendo_approval_Password);
        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
    }

    @AfterAll
    static void terminateBrowser() {
        BrowserManager.closeBrowser(browser);
    }

    @BeforeEach
    public void each(IJUnitTestReporter testReport) {
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);

    }

    @Test()
    @Order(1)
    public void registerOrganizationsAndAssignCompanyNxUserRole_TestCondition(IJUnitTestReporter testReporter) {
        randomDataForOrgName = DataGen.getRandomData();
        organizationName = randomDataForOrgName + " Company";
        NDPHomePage homePage = (NDPHomePage) currentPage;
        homePage.acceptCookie().clickRegisterPage()
                .clickCreateAccountPage()
                .selectOrganizationRadiobutton()
                .enterOrganizationName(organizationName)
                .enterAddressLine1("Address Line 1")
                .enterCity("Ha Noi")
                .selectCountry("Bahamas")
                .enterRegion("ASEAN")
                .enterPostalCode("000010")
                .enterFirstName(DataGen.getRandomUserName())
                .enterLastName(DataGen.getRandomUserName())
                .enterNintendoDeveloperID(ndpUser.getUserName())
                .enterEmailAddress(ndpUser.getUserName() + TextConstants.inbucket_Suffix)
                .enterConfirmEmailAddress(ndpUser.getUserName() + TextConstants.inbucket_Suffix)
                .selectYouMustBeAtLeast18()
                .clickReviewApplicationButton()
                .clickSubmitApplicationButton();
        ManagementSystemLogin managementSystemLogin = browser.openURL(ManagementSystemLogin.class, PageState.PRE_LOGIN, testReporter);
        managementSystemLogin.signIn(adminUser)
                .clickManageUsers()
                .enterNintendoDeveloperID(ndpUser.getUserName())
                .clickSearchButton()
                .clickUserNameInTableByNintendoID(ndpUser.getUserName())
                .clickChangePassword()
                .enterYourPassword(ndpUser.getPassword())
                .enterNewPassword(ndpUser.getPassword())
                .enterConfirmNewPassword(ndpUser.getPassword())
                .selectOkButton();
        browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReporter);
        NDPSignInPage signInPage = homePage.clickSignInPage();
        if (signInPage.signInNewlyRegisteredOrganizationAndIsOrganizationApproved(ndpUser, true)) {
            NDPCompanyNDARequiredPage companyNDARequiredPage = browser.getPage(NDPCompanyNDARequiredPage.class, testReporter);
            companyNDARequiredPage.selectByClickingTheSubmitButtonCheckbox();
            if (companyNDARequiredPage.clickSubmitButtonWithError()) {
                currentPage = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter).clickGettingStarted();
            } else {
                currentPage = companyNDARequiredPage.afterClickSubmitButtonNotError().clickHereLink();
            }
        } else {
            NDPViewTaskPage viewTaskPage = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter)
                    .clickUserMenuDropdownList()
                    .clickSignOutButton()
                    .clickSignInPage()
                    .signIn(licApproverUser)
                    .clickOnMyTaskMenu()
                    .clickOnTask("Global NDA")
                    .selectAssignToMeButton()
                    .selectOKButtonInAssignToMeModal()
                    .selectRetryRequestButton()
                    .selectOKButtonInRetryRequestModal()
                    .waitingForApproveSuccessful();
            browser.refresh();
            currentPage = viewTaskPage.clickUserMenuDropdownList()
                    .clickSignOutButton()
                    .clickSignInPage()
                    .signIn(ndpUser)
                    .clickGettingStarted();
        }

        //assign company nx user role
        currentPage = browser.openURL(AdminHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        AdminHomePage adminHomePage = (AdminHomePage) currentPage;
        AdminUserDetailPage userDetailPage = adminHomePage.acceptCookie().clickSignInPage().signIn(adminUser).clickMenuAdmin().clickControlPanel()
                .clickUsersAndOrganizations().clickAllUsersTab()
                .enterOrganizationName(ndpUser.getUserName())
                .clickSearch()
                .clickAction()
                .clickEdit();
        userDetailPage.clickRole()
                .clickSearchRegularRole()
                .enterRole("NX")
                .clickSearch()
                .clickChoose("Company NX Access")
                .clickSave();
        userDetailPage.clickUserMenu().clickSignOut();

        currentPage = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);

        NDPDevelopmentHome developmentHome = (NDPDevelopmentHome) currentPage;
        developmentHome.clickUserMenuDropdownList().clickManageAccount().clickEditProfileButton().selectCompanyNxUserRole()
                .clickSaveChangesButton();
        developmentHome.clickUserMenuDropdownList().clickSignOutButton();
    }

    @Test()
    @Order(2)
    public void loginWebmasterAsOmniadminRoleAndSearchOrgAtTestCondition_Step1(IJUnitTestReporter testReporter) {
        currentPage = browser.openURL(AdminHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        AdminHomePage adminHomePage = (AdminHomePage) currentPage;
        currentPage = adminHomePage
                .clickSignInPage().signIn(adminUser).clickMenuAdmin().clickControlPanel()
                .clickUsersAndOrganizations()
                .clickAllOrganizationsTab()
                .enterOrganization(randomDataForOrgName)
                .clickSearch();
    }

    @Test()
    @Order(3)
    public void verifyOrganizationAtConditionIsDisplayed_Expected1() {
        AdminAllOrganizationsTab allOrganizationsTab = (AdminAllOrganizationsTab) currentPage;
        assertTrue(allOrganizationsTab.isOrganizationDisplayed(organizationName), "Verify the organization at condition will display");
    }

    @Test()
    @Order(4)
    public void selectCustomFields_Step2() {
        AdminAllOrganizationsTab allOrganizationsTab = (AdminAllOrganizationsTab) currentPage;
        currentPage = allOrganizationsTab.clickAction().selectEdit().clickCustomFields();
    }

    @Test()
    @Order(5)
    public void verifyChinaAgreementExemptField_Expected2() {
        AdminEditOrganizationPage editOrganizationPage = (AdminEditOrganizationPage) currentPage;
        assertAll(() -> assertEquals("False", editOrganizationPage.getSelectedValueOnChinaAgreementExemptField(), "Verify the value default is False"),
                () -> assertTrue(editOrganizationPage.isChinaAgreementExemptDisplayed(), "Verify The China Agreement Exempt flag is added into Custom Fields"));
    }

    @Test()
    @Order(6)
    public void clickOnChinaAgreementExemptDropdown_Step3() {
        AdminEditOrganizationPage editOrganizationPage = (AdminEditOrganizationPage) currentPage;
        editOrganizationPage.clickChinaAgreementExempt();
    }

    @Test()
    @Order(7)
    public void verifyOptionsOnChinaAgreementExemptDropdown_Expected3() {
        AdminEditOrganizationPage editOrganizationPage = (AdminEditOrganizationPage) currentPage;
        assertEquals("True, False", editOrganizationPage.getOptionsOnChinaAgreementExemptDropdown(), "Verify display with two options: True, False");
    }

    @Test()
    @Order(8)
    public void loginNdpByPartner_Step4(IJUnitTestReporter testReporter) {
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        NDPHomePage homePage = (NDPHomePage) currentPage;
        currentPage = homePage.clickSignInPage()
                .signIn(ndpUser);
    }

    @Test()
    @Order(9)
    public void createProduct_Step5() {
        NDPDevelopmentHome developmentHome = (NDPDevelopmentHome) currentPage;
        productName = DataGen.getRandomProductName();
        currentPage = developmentHome.clickMyProducts().createNewProduct()
                .selectProductType(TextConstants.full_Product)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalChinaSalesRegion()
                .selectProductName(productName)
                .clickCreateButton();
    }

    @Test()
    @Order(10)
    public void navigateToReleaseInfoPage_Step6() {
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        currentPage = myProductsPage.clickProductByName(productName).clickReleases().selectInitialRelease();
    }

    @Test()
    @Order(11)
    public void updateCardSize_Step7() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        releaseInfoPage.selectCardSize("2 GB")
                .selectFreeToPlay("Please Select");

    }

    @Test()
    @Order(12)
    public void selectSave_Step8() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        releaseInfoPage.save();
    }

    @Test()
    @Order(13)
    public void verifySaveSuccessfully_Expected8() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        assertAll(() -> assertEquals("2 GB", releaseInfoPage.getSelectedCardSize(), "Verify save successfully"),
                () -> assertEquals("Please Select", releaseInfoPage.getSelectedFreeToPlay(), "Verify save successfully"));
    }

    @Test()
    @Order(14)
    public void selectNoFreeToPlay_Step9() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        releaseInfoPage.selectFreeToPlay(TextConstants.no_Upper_Value);
    }

    @Test()
    @Order(15)
    public void fillOutOtherRequiredAndSave_Step10() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        releaseInfoPage.selectRomsThatDifferBetweenRegion(TextConstants.selectRomSameAllRegion)
                .selectLanguageUsedForTextEntry(TextConstants.english)
                .setExpectedLotcheckSubmissionDate(3)
                .setExpectedReleaseDate(90)
                .save();
    }

    @Test()
    @Order(16)
    public void getValueOfFreeToPlayField_Step11() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        freeToPlayValue = releaseInfoPage.getSelectedFreeToPlay();
    }

    @Test()
    @Order(17)
    public void verifyValueIsNo_Expected11() {
        assertEquals(TextConstants.no_Upper_Value, freeToPlayValue, "Verify value of Free to Play fields: NO");//Update expected No->NO
    }

    @Test()
    @Order(18)
    public void selectIssueGameCode_Step12() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        NDPProductDashboardPage productDashboardPage = releaseInfoPage.nav().clickBackToProductDashboard();
        currentPage = productDashboardPage.issueGameCode();
    }

    @Test()
    @Order(19)
    public void changeFreeToPlayToYes_Step13() {
        NDPRequestGameCodePage requestGameCodePage = (NDPRequestGameCodePage) currentPage;
        currentPage = requestGameCodePage.selectFreeToPlay(TextConstants.yes_Upper_Value).clickIssue();

    }

    @Test()
    @Order(20)
    public void checkFreeToPlayValueOnReleaseInfoPage_Step14() {
        NDPProductDashboardPage productDashboardPage = (NDPProductDashboardPage) currentPage;
        initialCode = productDashboardPage.clickProductInfo().getInitialCode();
        applicationId = productDashboardPage.clickProductInfo().getApplicationID();
        NDPReleaseInfoPage releaseInfoPage = productDashboardPage.clickReleases().selectInitialRelease();
        freeToPlayValue = releaseInfoPage.getSelectedFreeToPlay();
        currentPage = releaseInfoPage;

    }

    @Test()
    @Order(21)
    public void verifyFreeToPlayValueOnReleaseInfoPage_Expected14() {
        assertEquals(TextConstants.yes_Upper_Value, freeToPlayValue, "Verify Value of Free to Play fields: YES");//Update expect Yes to YES
    }

    @Test()
    @Order(22)
    public void changeTargetDeliveryFormatAndSelectTargetSalesRegionsPhysical_Step15() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        releaseInfoPage.selectTargetDeliveryFormat(" Physical and Digital ");

    }

    //Remove step 16 17
    @Test()
    @Order(23)
    public void checkTargetSaleRegionsField_Step18() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        isChinaTargetSalesRegionsDigitalChecked = releaseInfoPage.isTargetSalesRegionsChecked("china", "Digital");
        isChinaTargetSalesRegionsPhysicalChecked = releaseInfoPage.isTargetSalesRegionDisable("china", "Physical");
    }

    @Test()
    @Order(24)
    public void verifySaleRegionSelectedWhenCreateProductWillAutoChecked_Expected18() {
        assertAll(() -> assertTrue(isChinaTargetSalesRegionsDigitalChecked, "Verify China target sale regions digital is checked"),
                () -> assertTrue(isChinaTargetSalesRegionsPhysicalChecked, "Verify China target sale regions physical is checked"));
    }

    @Test()
    @Order(25)
    public void tryUncheckTargetSalesRegionsAreSelected_Step19() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        releaseInfoPage.unSelectDigitalChinaSalesRegion().save();
        assertAll(() -> assertFalse(releaseInfoPage.isTargetSalesRegionsClickable("china", "Digital"), "Verify China target sale region digital isn't clickable"),
                () -> assertTrue(releaseInfoPage.isTargetSalesRegionsChecked("china", "Digital"), "Verify China Region are checked"));

    }

    @Test()
    @Order(26)
    public void verifyCheckboxOfAllTargetSaleRegions_Expected19() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        assertAll(() -> assertTrue(releaseInfoPage.isTargetSalesRegionsChecked("china", "Physical"), "Verify sale region china Physical checked"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsChecked("europe", "Physical"), "Verify sale region europe Physical not checked"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsChecked("americas", "Physical"), "Verify sale region americas Physical not checked"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsChecked("japan", "Physical"), "Verify sale region japan Physical not checked"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsChecked("hong-kong-taiwan-korea", "Physical"), "Verify sale region hong-kong Physical not checked"),
                () -> assertTrue(releaseInfoPage.isTargetSalesRegionsChecked("china", "Digital"), "Verify sale region china Digital checked"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsChecked("europe", "Digital"), "Verify sale region europe Digital not checked"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsChecked("americas", "Digital"), "Verify sale region americas Digital not checked"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsChecked("japan", "Digital"), "Verify sale region japan Digital not checked"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsChecked("hong-kong-taiwan-korea", "Digital"), "Verify sale region hong-kong Digital not checked"),
                () -> assertTrue(releaseInfoPage.isTargetSalesRegionDisable("europe", "Physical"), "Verify sale region europe Physical is disable"),
                () -> assertTrue(releaseInfoPage.isTargetSalesRegionDisable("china", "Physical"), "Verify sale region china Physical is disable"),
                () -> assertTrue(releaseInfoPage.isTargetSalesRegionDisable("americas", "Physical"), "Verify sale region americas Physical is disable"),
                () -> assertTrue(releaseInfoPage.isTargetSalesRegionDisable("hong-kong-taiwan-korea", "Physical"), "Verify sale region hong-kong Physical is disable"),
                () -> assertTrue(releaseInfoPage.isTargetSalesRegionDisable("japan", "Physical"), "Verify sale region japan Physical is disable"),
                () -> assertTrue(releaseInfoPage.isTargetSalesRegionDisable("europe", "Digital"), "Verify sale region europe Digital is disable"),
                () -> assertTrue(releaseInfoPage.isTargetSalesRegionDisable("china", "Digital"), "Verify sale region china Digital is disable"),
                () -> assertTrue(releaseInfoPage.isTargetSalesRegionDisable("americas", "Digital"), "Verify sale region americas Digital is disable"),
                () -> assertTrue(releaseInfoPage.isTargetSalesRegionDisable("hong-kong-taiwan-korea", "Digital"), "Verify sale region hong-kong-taiwan-korea Digital is disable"),
                () -> assertTrue(releaseInfoPage.isTargetSalesRegionDisable("japan", "Digital"), "Verify sale region japan Digital is disable"));
    }

    @Test()
    @Order(27)
    public void fillOutTheFollowingFields_Step20() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        releaseInfoPage.selectSubmissionType("Lotcheck")
                .selectTargetDeliveryFormat(" Physical and Digital ")
                .save();
    }

    @Test()
    @Order(28)
    public void verifyDisplayOfTerraSalesRegions_Expected20() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        releaseInfoPage.nav().clickUserMenuDropdownList().selectJapan();
        String chinaDigital_JP = releaseInfoPage.getChinaDigitalCheckboxText();
        String chinaPhysical_JP = releaseInfoPage.getChinaPhysicalCheckboxText();
        releaseInfoPage.nav().clickUserMenuDropdownList().selectEnglish();
        String terraMessage_expected = "Note: For Asia, one other region (Americas, Europe+Australia or Japan) must be first selected.\n" +
                "For China, all other regions must first be unselected. China cannot be selected or unselected after the Game Code is issued.";
        String lotcheckMsg_expected = "Changes cannot be made after Lotcheck completion.";
        assertAll(() -> assertEquals(terraMessage_expected, releaseInfoPage.getTerraDigitalMessage(), "Verify message Terra Digital"),
                () -> assertEquals(terraMessage_expected, releaseInfoPage.getTerraPhysicalMessage(), "Verify message Terra Physical"),
                () -> assertEquals(lotcheckMsg_expected, releaseInfoPage.getLotcheckMessageDigital(), "Verify Lotcheck message digital"),
                () -> assertEquals(lotcheckMsg_expected, releaseInfoPage.getLotcheckMessagePhysical(), "Verify Lotcheck message physical"),
                () -> assertEquals("中国", chinaDigital_JP, "Verify text China sale region digital"),
                () -> assertEquals("中国", chinaPhysical_JP, "Verify text China sale region physical"));

    }

    @Test()
    @Order(29)
    public void selectNonChinaTargetSaleRegionsDigital_Step21() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        releaseInfoPage.selectDigitalAmericasSalesRegion()
                .selectTargetSaleRegionEurope()
                .selectDigitalJapanSaleRegion()
                .save();
    }

    @Test()
    @Order(30)
    public void verifyNonChinaTargetSaleRegionsDigitalCanNotSelected_Expected21() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        assertAll(() -> assertFalse(releaseInfoPage.isTargetSalesRegionsChecked("europe", "Digital"), "Verify non china can not selected"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsChecked("americas", "Digital"), "Verify non china can not selected"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsChecked("japan", "Digital"), "Verify non china can not selected"));
    }

    @Test()
    @Order(31)
    public void selectChinaSaleRegionDigital_Step22() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        releaseInfoPage.selectDigitalChinaSalesRegion()
                .save();
    }

    @Test()
    @Order(32)
    public void verifyChinaSaleRegionDigialIsNotClickable_Expected22() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        assertAll(() -> assertTrue(releaseInfoPage.isTargetSalesRegionsChecked("china", "Digital"), "Verify can not unselect china region"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsClickable("china", "Digital"), "Verify can not click china region checkbox"));
    }

    @Test()
    @Order(33)
    public void selectChinaSaleRegionPhysical_Step23() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        releaseInfoPage.selectPhysicalChinaSalesRegion()
                .save();
    }

    @Test()
    @Order(34)
    public void verifyChinaSaleRegionPhysicalIsNotClickable_Expected23() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        assertFalse(releaseInfoPage.isTargetSalesRegionsClickable("china", "Physical"), "Verify China physical sale region not be clickable ");
    }

    @Test()
    @Order(35)
    public void uncheckChinaSaleRegionPhysical_Step24() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        releaseInfoPage.unSelectPhysicalChinaSalesRegion()
                .save();
    }

    @Test()
    @Order(36)
    public void verifyChinaSaleRegionPhysicalCanNotUnselected_Expected24() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        assertTrue(releaseInfoPage.isTargetSalesRegionsChecked("china", "Physical"), "Verify China cannot be unselected");
    }

    @Test()
    @Order(37)
    public void selectNonChinaSaleRegionPhysical_Step25() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        releaseInfoPage.selectPhysicalEuropeSalesRegion()
                .selectPhysicalAmericasSalesRegion()
                .selectPhysicalJapanSalesRegion()
                .save();
    }

    @Test()
    @Order(38)
    public void verifyNonChinaSaleRegionPhysicalCanNotBeClickable_Expected25() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        assertAll(() -> assertFalse(releaseInfoPage.isTargetSalesRegionsChecked("europe", "Physical"), "Verify non china sale region physical can not checked"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsChecked("americas", "Physical"), "Verify non china sale region physical can not checked"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsChecked("japan", "Physical"), "Verify non china sale region physical can not checked"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsClickable("europe", "Physical"), "Verify non china sale region physical be not clickable"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsClickable("americas", "Physical"), "Verify non china sale region physical be not clickable"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsClickable("japan", "Physical"), "Verify non china sale region physical be not clickable"));
    }

    @Test()
    @Order(39)
    public void selectChinaSaleRegionPhysical_Step26() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        releaseInfoPage.selectPhysicalChinaSalesRegion().save();
    }

    @Test()
    @Order(40)
    public void verifyChinaSaleRegionPhysicalCanNotBeClickable_Expected26() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        assertFalse(releaseInfoPage.isTargetSalesRegionsClickable("china", "Physical"), "Verify China sale region physical cannot be clicked");
    }

    @Test()
    @Order(41)
    public void goToRequiredReleaseErrorTab_Step27() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        currentPage = releaseInfoPage.nav().clickIssues().openReleaseErrorsTab();
    }

    @Test()
    @Order(42)
    public void verifyIssueDisplayOnReleaseErrorsTab_Expected27() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        List<String> listErrors = issuePage.getListErrors();
        String error_01 = "You need to get the appropriate publishing agreements before you can submit:\n" +
                "Publisher Agreement (Digital) Global";
        String error_02 = "You need to get the appropriate publishing agreements before you can submit:\n" +
                "Publisher Agreement (Digital) China";
        String error_03 = "You need to get the appropriate publishing agreements before you can submit:\n" +
                "Publisher Agreement (Packaged Goods) China";
        assertAll(() -> assertEquals(error_01, listErrors.get(0), "Verify display the release error about missing Global Digital agreement"),
                () -> assertEquals(error_02, listErrors.get(1), "Verify display the release error about missing China Digital agreement"),
                () -> assertEquals(error_03, listErrors.get(2), "Verify display the release error about missing China Physical agreement"));
    }

    @Test()
    @Order(43)
    public void navigateToRequiredApprovedTab_Step28() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        issuePage.openRequiredApprovalTab();
    }

    @Test()
    @Order(44)
    public void verifyDisplayMissingChinaPayeeIssue_Expected28() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        assertTrue(issuePage.isIssueDisplayed("Nintendo Switch China Payee is not set up"), "Verify display issue missing China Payee");
    }

    @Test()
    @Order(45)
    public void performSetChinaAgreementExemptToTrue_Step29(IJUnitTestReporter testReporter) {
        currentPage = browser.openURL(AdminDevelopmentHome.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        AdminDevelopmentHome developmentHome = (AdminDevelopmentHome) currentPage;
        developmentHome.clickMenuAdmin().clickControlPanel()
                .clickUsersAndOrganizations()
                .clickAllOrganizationsTab()
                .enterOrganization(randomDataForOrgName)
                .clickSearch()
                .clickAction()
                .selectEdit()
                .clickCustomFields()
                .selectChinaAgreementExempt("True")
                .clickSave();
    }

    @Test()
    @Order(46)
    public void goToRequiredReleaseErrorTab_Step30(IJUnitTestReporter testReporter) {
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        currentPage = myProductsPage.clickProductByName(productName).clickReleases().selectInitialRelease().nav().clickIssues().openReleaseErrorsTab();
    }

    @Test()
    @Order(47)
    public void verifyIssueDisplayOnReleaseErrorsTab_Expected30() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        List<String> listErrors = issuePage.getListErrors();
        String error = "You need to get the appropriate publishing agreements before you can submit:\n" +
                "Publisher Agreement (Digital) Global";
        assertAll(() -> assertEquals(error, listErrors.get(0), "Verify display the release error about missing Global Digital agreement"),
                () -> assertEquals(1, listErrors.size(), "Verify display only the release error about missing Global Digital agreement"));

    }

    @Test()
    @Order(48)
    public void navigateToRequiredApprovedTab_Step31() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        issuePage.openRequiredApprovalTab();
    }

    @Test()
    @Order(49)
    public void verifyNotDisplayMissingChinaPayeeIssue_Expected31() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        assertFalse(issuePage.isIssueDisplayed("Nintendo Switch China Payee is not set up"), "Verify display issue missing China Payee");
    }

    @Test()
    @Order(50)
    public void resolveRemainingReleaseErrorAndRequiredApproval_Step32() {
        NDPReleaseInfoIssuePage issuePage = (NDPReleaseInfoIssuePage) currentPage;
        NDPReleaseInfoPage releaseInfoPage = issuePage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licPowerUser).clickInternalTab()
                .clickCustomAgreements()
                .clickSearchIcon()
                .enterOrganizationName(randomDataForOrgName)
                .clickSearch()
                .selectOrg(organizationName).clickChooseSelectedPartner()
                .selectPlatform("Nintendo Switch")
                .selectAgreementType("Publisher Agreement (Digital)")
                .selectRegion("Global")
                .clickUpload()
                .selectFileInPC("Judgement.pdf")
                .setExecutionDate(0)
                .setExpirationDate(100)
                .selectAgreementApprove()
                .clickSubmit()
                .navTopNavigationBar().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(ndpUser)
                .clickMyProducts().clickProductByName(productName).clickReleases().selectInitialRelease();
        releaseInfoPage.nav().clickIssues().openRequiredApprovalTab().performAllIssueAtApprovalTab(true);

        NDPDevelopmentHome developmentHome = releaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licApprovalCoordUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        licensingWaiverRequestsTab.waitingForResultTableLoaded()
                .enterSearchingBox(initialCode)
                .clickOnMagnifyingIconButton()
                .waitingForResultTableLoaded();
        int numberOfRequestByPendingStatus = licensingWaiverRequestsTab.collectNumberOfRequestByPendingStatus();
        for (int i = 0; i < numberOfRequestByPendingStatus; i++) {
            developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton().clickOnLicensingWaiverRequestsTab();
            licensingWaiverRequestsTab.waitingForResultTableLoaded()
                    .enterSearchingBox(initialCode)
                    .clickOnMagnifyingIconButton()
                    .waitingForResultTableLoaded();
            currentPage = licensingWaiverRequestsTab.openLicensingWaiverRequestByPendingStatus().selectAssignToMeButton()
                    .selectOKButtonInAssignToMeModal()
                    .selectApproveRequestButton()
                    .selectOKButtonInApproveRequestModal()
                    .waitingForApproveSuccessful();

        }
        NDPViewTaskPage ndpViewTaskPage = (NDPViewTaskPage) currentPage;

        currentPage = ndpViewTaskPage.clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(ndpUser);
    }

    @Test()
    @Order(51)
    public void fillOutAllRequiredInformationAndSubmitReleaseToLotcheck_Step33(IJUnitTestReporter testReport) {
        NDPDevelopmentHome developmentHome = (NDPDevelopmentHome) currentPage;
        NDPReleaseInfoPage releaseInfoPage = developmentHome.clickMyProducts().clickProductByName(productName).clickReleases().selectInitialRelease();
        CommonAction.getFeatureOptionData(releaseInfoPage, TextConstants.allOff_China, TextConstants.allOff, TextConstants.allOff, false, "", false, "");
        releaseInfoPage.nav().clickGuidelines().selectCompliesWithAllOfTheAboveGuidelines().clickOnSaveButton();
        createRom(testReport);
        currentPage = browser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        releaseInfoPage = myProductsPage.clickProductByGameCode(initialCode).clickReleases().selectInitialRelease();
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = releaseInfoPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, romInitialName);
        releaseInfoPage.nav().clickIssues().openRequiredApprovalTab().performAllIssueAtApprovalTab(true);

        developmentHome = releaseInfoPage.nav().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licApprovalCoordUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        licensingWaiverRequestsTab.waitingForResultTableLoaded()
                .enterSearchingBox(initialCode)
                .clickOnMagnifyingIconButton()
                .waitingForResultTableLoaded();
        int numberOfRequestByPendingStatus = licensingWaiverRequestsTab.collectNumberOfRequestByPendingStatus();
        for (int i = 0; i < numberOfRequestByPendingStatus; i++) {
            developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton().clickOnLicensingWaiverRequestsTab();
            licensingWaiverRequestsTab.waitingForResultTableLoaded()
                    .enterSearchingBox(initialCode)
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
                .clickMyProducts().clickProductByName(productName).clickReleases().selectInitialRelease().nav().clickSubmitButton()
                .clickSubmit();
        WaitUtils.idle(4000);
    }

    @Test()
    @Order(52)
    public void verifySubmitSuccessfully_Expected33() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        assertEquals(TextConstants.submitToLotcheck, releaseInfoPage.getStatusValue(), TextConstants.initialSubmitSuccess);
    }

    @Test()
    @Order(53)
    public void goToMyProductsPage_Step34() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        currentPage = releaseInfoPage.topNavigation().clickMyProducts();
    }

    @Test()
    @Order(54)
    public void verifyDisplayOfSaleRegion_Step34() {
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        assertAll(() -> assertEquals("China", myProductsPage.getSaleRegion(), "Verify displayed selected sale region and sale region display below product name"),
                () -> assertEquals("rgba(0, 0, 0, 1)", myProductsPage.getTextColorOfSaleRegion(), "Verify text color of China region is black "));

    }

    @Test()
    @Order(55)
    public void goToProductInfoTab_Step35() {
        NDPMyProductsPage myProductsPage = (NDPMyProductsPage) currentPage;
        currentPage = myProductsPage.clickProductByName(productName).clickProductInfo();
        WaitUtils.idle(2000);
    }

    @Test()
    @Order(56)
    public void verifyDisplaySaleRegion_Expected35() {
        NDPProductDashboardProductInfoTab productInfoTab = (NDPProductDashboardProductInfoTab) currentPage;
        assertEquals("China", productInfoTab.getSaleRegion(), "Verify display selected sale region");
    }

    @Test()
    @Order(57)
    public void cancelSubmission_Step36() {
        NDPProductDashboardProductInfoTab productInfoTab = (NDPProductDashboardProductInfoTab) currentPage;
        NDPReleaseInfoReleaseListNav ndpReleaseInfoNav = productInfoTab.navProductDashboard().clickReleases().selectInitialRelease().nav();
        browser.refresh();
        currentPage = ndpReleaseInfoNav.clickCancelSubmitButton().clickOKInConfirm();

    }

    @Test()
    @Order(58)
    public void verifyDisplaySaleRegionOnNewSubmission_Expected36() {
        NDPReleaseInfoPage releaseInfoPage = (NDPReleaseInfoPage) currentPage;
        assertAll(() -> assertTrue(releaseInfoPage.isTargetSalesRegionsChecked("china", "Physical"), "Verify sale region china Physical checked"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsChecked("europe", "Physical"), "Verify sale region europe Physical not checked"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsChecked("americas", "Physical"), "Verify sale region americas Physical not checked"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsChecked("japan", "Physical"), "Verify sale region japan Physical not checked"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsChecked("hong-kong-taiwan-korea", "Physical"), "Verify sale region hong-kong Physical not checked"),
                () -> assertTrue(releaseInfoPage.isTargetSalesRegionsChecked("china", "Digital"), "Verify sale region china Digital checked"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsChecked("europe", "Digital"), "Verify sale region europe Digital not checked"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsChecked("americas", "Digital"), "Verify sale region americas Digital not checked"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsChecked("japan", "Digital"), "Verify sale region japan Digital not checked"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsChecked("hong-kong-taiwan-korea", "Digital"), "Verify sale region hong-kong Digital not checked"),
                () -> assertTrue(releaseInfoPage.isTargetSalesRegionDisable("europe", "Physical"), "Verify sale region europe Physical is disable"),
                () -> assertTrue(releaseInfoPage.isTargetSalesRegionDisable("china", "Physical"), "Verify sale region china Physical is disable"),
                () -> assertTrue(releaseInfoPage.isTargetSalesRegionDisable("americas", "Physical"), "Verify sale region americas Physical is disable"),
                () -> assertTrue(releaseInfoPage.isTargetSalesRegionDisable("hong-kong-taiwan-korea", "Physical"), "Verify sale region hong-kong Physical is disable"),
                () -> assertTrue(releaseInfoPage.isTargetSalesRegionDisable("japan", "Physical"), "Verify sale region japan Physical is disable"),
                () -> assertTrue(releaseInfoPage.isTargetSalesRegionDisable("europe", "Digital"), "Verify sale region europe Digital is disable"),
                () -> assertTrue(releaseInfoPage.isTargetSalesRegionDisable("china", "Digital"), "Verify sale region china Digital is disable"),
                () -> assertTrue(releaseInfoPage.isTargetSalesRegionDisable("americas", "Digital"), "Verify sale region americas Digital is disable"),
                () -> assertTrue(releaseInfoPage.isTargetSalesRegionDisable("hong-kong-taiwan-korea", "Digital"), "Verify sale region hong-kong-taiwan-korea Digital is disable"),
                () -> assertTrue(releaseInfoPage.isTargetSalesRegionDisable("japan", "Digital"), "Verify sale region japan Digital is disable"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsClickable("europe", "Physical"), "Verify sale region europe physical not clickable"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsClickable("china", "Physical"), "Verify sale region china physical not clickable"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsClickable("americas", "Physical"), "Verify sale region americas physical not clickable"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsClickable("hong-kong-taiwan-korea", "Physical"), "Verify sale region hong-kong physical not clickable"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsClickable("japan", "Physical"), "Verify sale region japan physical not clickable"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsClickable("europe", "Digital"), "Verify sale region europe Digital not clickable"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsClickable("china", "Digital"), "Verify sale region china Digital not clickable"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsClickable("americas", "Digital"), "Verify sale region americas Digital not clickable"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsClickable("hong-kong-taiwan-korea", "Digital"), "Verify sale region hong-kong Digital not clickable"),
                () -> assertFalse(releaseInfoPage.isTargetSalesRegionsClickable("japan", "Digital"), "Verify sale region japan Digital not clickable"));
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
                .enterDisplayVersion("1.0.0")
                .enterReleaseVersion("00")
                .selectPublishingRelationShip(TextConstants.third_Party_Type)
                .clickOnCreateButton()
                .waitIsLoaded();
        romInitialName = romCreateRom.getRomNameTable(applicationId);
        romCreateRom.clickOnROMFileOnROMTable(romInitialName);
        WaitUtils.idle(5000);
    }

    //36 step + 23 expected - 2 step(16,17)=57 order
}

