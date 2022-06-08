package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.junit.TestLogger;
import net.nintendo.automation.junit.extensions.ScenarioTest;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.data.DataGen;
import net.nintendo.automation.ui.models.admin.AdminHomePage;
import net.nintendo.automation.ui.models.admin.AdminUserDetailPage;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.ndidMS.ManagementSystemLogin;
import net.nintendo.automation.ui.models.ndp.*;
import net.nintendo.automation.ui.models.ndp.admin.NDPAdminPage;
import net.nintendo.automation.ui.users.User;
import net.nintendo.automation.ui.users.UserManager;
import net.nintendo.automation.utils.WaitUtils;
import net.nintendo.automation_core.common_utils.annotations.services.NDP;
import net.nintendo.automation_core.common_utils.annotations.services.SLCMS;

import net.nintendo.automation_core.common_client.enums.ServiceType;
import net.nintendo.automation_core.common_client.enums.UserType;
import net.nintendo.automation_core.extent_reports.extensions.IJUnitTestReporter;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classname: T3402545
 * Version: 1.0.0
 * Branch: tasks/#172976/T3402545
 * Purpose:
 */

@NDP
@SLCMS
@ScenarioTest
@Tag("T3402545")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3402545 extends CommonBaseTest {
    private static Browser browser;
    private static User ndpUser;
    private static User adminUser;
    private static User licApprovalUser;
    private static User licPowerUser;
    private static String validationMessage = "Only alphanumeric characters, half-width spaces, and '()+,-./:? are allowed.";


    private static CommonUIComponent currentPage;
    private static TestLogger logger;

    private static List<String> characters = Arrays.asList("\\", "!", "@", "#", "$", "%", "^", "&", "*", "_", "~", ";", "{}", "[]", "=", "<>");
    private static String alpha = "abcdefghijklmnopqrstuvwwxyz";
    private static String alphaUpperCase = alpha.toUpperCase();
    private static String digit = "0123456789";
    private static String specials = "'()+,-./:?";
    private static String alphaNumeric = alpha + alphaUpperCase + digit + specials;
    private static String accountHolderCityValue = "Account Holder City";
    private static String accountHolderPostalCodeValue = "Account Holder Postal Code";
    private static String branchOfficeNameValue = "Branch Office Name";
    private static String bicSwiftCodeValue = "AAAAAAAA";
    private static String ibanCodeValue = "AD1400080001001234567890";
    private static String accountHolderSuburbValue = "Account Holder Suburb";
    private static String bankNumberValue = "Bank Number";
    private static String branchOfficeNumberValue = "Branch Office Number";
    private static String successBankUpdateMessage = "Bank account info has been updated successfully";
    private static String currentAccountPrefix = "I confirm that the account currency is ";

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
        licApprovalUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approver_NCL);
        licPowerUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_pwr_user_NCL);
        adminUser = new User();
        adminUser.setPassword(TextConstants.nintendo_approval_Password);
        adminUser.setUserName(TextConstants.omniAdmin);

        ndpUser = new User();
        ndpUser.setPassword(TextConstants.nintendo_approval_Password);
        ndpUser.setUserName("prdcvdungdtt" + DataGen.getRandomUserName());

    }

    @BeforeEach
    public void each(IJUnitTestReporter testReport) {
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);


    }

    @AfterAll
    public static void terminateBrowser() {
        BrowserManager.closeBrowser(browser);
        UserManager.releaseUser(ServiceType.NDP, licPowerUser);
        UserManager.releaseUser(ServiceType.NDP, licApprovalUser);
    }


    @Test()
    @Order(1)
    public void createNewAcc_TestCondition(IJUnitTestReporter testReport) {
        //create organization
        String organizationName = DataGen.getRandomOrganizationName();
        NDPCreateAccountPage createAccountPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReport)
                .acceptCookie().clickRegisterPage().clickCreateAccountPage()
                .selectOrganizationRadiobutton().enterOrganizationName(organizationName)
                .enterAddressLine1("Address Line 1").enterCity("Ha Noi").selectCountry("Vietnam")
                .enterRegion("ASEAN").enterPostalCode("000010").enterFirstName(DataGen.getRandomUserName())
                .enterLastName("dung_" + DataGen.getRandomUserName())
                .enterNintendoDeveloperID(ndpUser.getUserName())
                .enterEmailAddress(ndpUser.getUserName() + TextConstants.inbucket_Suffix)
                .enterConfirmEmailAddress(ndpUser.getUserName() + TextConstants.inbucket_Suffix)
                .selectYouMustBeAtLeast18().clickReviewApplicationButton();
        WaitUtils.idle(5000);
        NDPConfirmationPage confirmationPage = createAccountPage.clickSubmitApplicationButton();
        // change password - user
        ManagementSystemLogin managementSystemLogin = browser.openURL(ManagementSystemLogin.class, PageState.PRE_LOGIN, testReport);
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
        NDPSignInPage signInPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReport).clickSignInPage();
        boolean result = signInPage.signInNewlyRegisteredOrganizationAndIsOrganizationApproved(ndpUser, true);
        if (result) {
            NDPCompanyNDARequiredPage companyNDARequiredPage = browser.getPage(NDPCompanyNDARequiredPage.class, testReport);
            companyNDARequiredPage.selectByClickingTheSubmitButtonCheckbox();
            if (companyNDARequiredPage.clickSubmitButtonWithError()) {
                browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReport).clickGettingStarted();
            } else {
                companyNDARequiredPage.afterClickSubmitButtonNotError().clickHereLink();
            }
        } else {
            //approve organization
            NDPReviewAndApproveNewPartner reviewAndApproveNewPartner = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReport)
                    .clickUserMenuDropdownList().clickSignOutButton()
                    .clickSignInPage()
                    .signIn(licApprovalUser)
                    .clickOnMyTaskMenu()
                    .clickOnViewAllTaskButton().clickCompanyRegistration()
                    .inputCompanyName(organizationName).clickSearchIcon().clickTaskCompanyRegistration(organizationName)
                    .selectAssignToMeButton()
                    .selectOKButtonInAssignToMeModal()
                    .selectApproveRequestButton()
                    .selectOKButtonInApproveRequestModal()
                    .waitingForApproveSuccessful();
            browser.refresh();
            reviewAndApproveNewPartner.clickUserMenuDropdownList().clickSignOutButton()
                    .clickSignInPage()
                    .signIn(ndpUser);
            //.clickGettingStarted();
            NDPCompanyNDARequiredPage companyNDARequiredPage = browser.getPage(NDPCompanyNDARequiredPage.class, testReport);
            companyNDARequiredPage.selectByClickingTheSubmitButtonCheckbox();
            if (companyNDARequiredPage.clickSubmitButtonWithError()) {
                browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReport).clickGettingStarted();
            } else {
                companyNDARequiredPage.afterClickSubmitButtonNotError().clickHereLink();
            }
        }
        //add agreement
        NDPDevelopmentHome developmentHome = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReport);
        String uuidOrg = developmentHome.navTopNavigation()
                .clickAdmin()
                .clickOrganizationInformation()
                .getOrganizationUUID();
        NDPCustomAgreementsPage customAgreementsPage = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReport)
                .clickUserMenu().clickLogout().clickSignInPage().signIn(licPowerUser)
                .clickInternalTab().clickCustomAgreements()
                .clickSearchIcon().enterOrganizationName(uuidOrg)
                .clickSearch().selectOrg(organizationName).clickChooseSelectedPartner()
                .selectPlatform("Nintendo Switch").selectAgreementType("Publisher Agreement (Digital)")
                .selectRegion("Global").clickUpload().selectFileInPC("testdemo.pdf")
                .setExecutionDate(0).setExpirationDate(100).selectAgreementApprove().clickSubmit();
        customAgreementsPage.navTopNavigationBar().clickUserMenuDropdownList().clickSignOutButton();
        //customAgreementsPage.clickUserMenuDropdownList().clickSignOutButton();

        // add role
        AdminHomePage adminHomePage = browser.openURL(AdminHomePage.class, PageState.PRE_LOGIN);
        AdminUserDetailPage userDetailPage = adminHomePage.acceptCookie().clickSignInPage().signIn(adminUser).clickMenuAdmin().clickControlPanel()
                .clickUsersAndOrganizations().clickAllUsersTab()
                .enterOrganizationName(ndpUser.getUserName())
                .clickSearch().clickAction().clickEdit();
        userDetailPage.clickRole()
                .clickSearchRegularRole()
                .enterRole("NX").clickSearch().clickChoose("Company NX Access").clickSearchRegularRole()
                .enterRole("Finance").clickSearch().clickChoose("Company Finance (MIRROR)")
                .clickSave();
        userDetailPage.clickUserMenu().clickSignOut();
        NDPMyAccountPage myAccountPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReport).clickSignInPage()
                .signIn(ndpUser).clickUserMenuDropdownList().clickManageAccount().clickEditProfileButton()
                .selectCompanyFinanceRole().selectCompanyNxUserRole()
                .clickSaveChangesButton();
        currentPage = myAccountPage;

    }

    @Test()
    @Order(2)
    public void openFinancialInformationScreen_Step1(IJUnitTestReporter testReport) {
        NDPMyAccountPage myAccountPage = (NDPMyAccountPage) currentPage;
        NDPAdminPage ndpAdminPage = myAccountPage.navTopNavigation().clickAdmin();
        NDPFinanceInformationPage ndpFinanceInformationPage = ndpAdminPage.clickFinanceInformationMenu();
        currentPage = ndpFinanceInformationPage;

    }

    @Test()
    @Order(3)
    public void checkDisplayFinancialInformation_Expect1(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        currentPage = ndpFinanceInformationPage;
        assertEquals("Financial Information - Nintendo Developer Portal", ndpFinanceInformationPage.getPageTitle(), "Verify Financial Information portlet is displayed.");
    }

    @Test()
    @Order(4)
    public void clickAddNewPayeeTax_Step2(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        currentPage = ndpFinanceInformationPage;
        ndpFinanceInformationPage.clickAddNewPayeeNintendoSwitch();
    }

    @Test()
    @Order(5)
    public void checkAddNewPayeeTaxForm_Expect2(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        currentPage = ndpFinanceInformationPage;
        assertTrue(ndpFinanceInformationPage.formFinanceInformationIsDisplay(), "Verify The payee form is displayed.");
    }

    @Test()
    @Order(6)
    public void templateA_SelectPayeeBankLocation_Step3(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        currentPage = ndpFinanceInformationPage;
        ndpFinanceInformationPage.selectCountryPayeeBank("Andorra");
    }

    @Test()
    @Order(7)
    public void verifyTemplateA_Expect3(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        currentPage = ndpFinanceInformationPage;
        assertAll(() -> assertEquals(currentAccountPrefix + "EUR", ndpFinanceInformationPage.getTextCurrency()),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderName(), TextConstants.displayVerify + "AccountHolderName "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderStreet1(), TextConstants.displayVerify + "AccountHolderStreet1 "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderStreet2(), TextConstants.displayVerify + "AccountHolderStreet2 "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderCity(), TextConstants.displayVerify + "AccountHolderCity "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderPostalCode(), TextConstants.displayVerify + "AccountHolderPostalCode "),
                () -> assertTrue(ndpFinanceInformationPage.displayCountryRegion(), TextConstants.displayVerify + "CountryRegion "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderState(), TextConstants.displayVerify + "AccountHolderState "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountCurrency(), TextConstants.displayVerify + "AccountCurrency "),
                () -> assertTrue(ndpFinanceInformationPage.displayIBANCode(), TextConstants.displayVerify + "IBANCode "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountType(), TextConstants.displayVerify + "AccountType "),
                () -> assertTrue(ndpFinanceInformationPage.displayBankName(), TextConstants.displayVerify + "BankName "),
                () -> assertTrue(ndpFinanceInformationPage.displayBICSwiftCode(), TextConstants.displayVerify + "BICSwiftCode ")
        );

    }

    @Test()
    @Order(8)
    public void enterInvalidCharacter_Step4(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        currentPage = ndpFinanceInformationPage;
    }


    @Test()
    @Order(9)
    public void verifyErrorMessage_Expect4(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        currentPage = ndpFinanceInformationPage;
        for (String character : characters) {
            ndpFinanceInformationPage.enterAccountHolderName(character).enterAccountHolderStreet1(character).enterAccountHolderStreet2(character).enterBankName(character);
            ndpFinanceInformationPage.clickNext();
            String validMessageAccountHolderName = ndpFinanceInformationPage.getMessageAccountHolderName();
            String validMessageAccountHolderStreet1 = ndpFinanceInformationPage.getMessageAccountHolderStreet1();
            String validMessageAccountHolderStreet2 = ndpFinanceInformationPage.getMessageAccountHolderStreet2();
            String validMessageBankName = ndpFinanceInformationPage.getMessageBankName();
            ndpFinanceInformationPage.clearTextAccountHolderName();
            ndpFinanceInformationPage.clearTextAccountHolderStreet1();
            ndpFinanceInformationPage.clearTextAccountHolderStreet2();
            ndpFinanceInformationPage.clearTextBankName();
            assertAll(() -> assertEquals(validationMessage, validMessageAccountHolderName, TextConstants.validationErrorMessage + "AccountHolderName "),
                    () -> assertEquals(validationMessage, validMessageAccountHolderStreet1, TextConstants.validationErrorMessage + "AccountHolderStreet1 "),
                    () -> assertEquals(validationMessage, validMessageAccountHolderStreet2, TextConstants.validationErrorMessage + "AccountHolderStreet2 "),
                    () -> assertEquals(validationMessage, validMessageBankName, TextConstants.validationErrorMessage + "BankName ")
            );
        }
    }

    @Test()
    @Order(10)
    public void enterValidValue_Step5(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        currentPage = ndpFinanceInformationPage;
        ndpFinanceInformationPage.enterAccountHolderName(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderStreet1(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderStreet2(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterBankName(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderCity(accountHolderCityValue).enterAccountHolderPostalCode(accountHolderPostalCodeValue).selectCountryRegion("Andorra")
                .selectAccountCurrency()
                .enterIBANCode(ibanCodeValue)
                .selectAccountType("Savings")
                .enterBicSwiftCode(bicSwiftCodeValue);

    }

    @Test()
    @Order(11)
    public void verifyNoErrorMessageAndSubmitSuccess_Expect5(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        ndpFinanceInformationPage.clickNext();
        WaitUtils.idle(3000);
        assertAll(() -> assertEquals(successBankUpdateMessage, ndpFinanceInformationPage.getMessageSubmitSuccess(), TextConstants.displayCorrectMessage),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderName(), TextConstants.validationErrorMessage + "AccountHolderName "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderStreet1(), TextConstants.validationErrorMessage + "AccountHolderStreet1 "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderStreet2(), TextConstants.validationErrorMessage + "AccountHolderStreet2 "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageBankName(), TextConstants.validationErrorMessage + "BankName ")
        );
    }

    @Test()
    @Order(12)
    public void  verify_TemplateB_Step6(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        ndpFinanceInformationPage.clickViewDetail().clickEdit();
        ndpFinanceInformationPage.selectCountryPayeeBank("China");
        //Verify The form displays the fields that correspond with payee template B.
        assertAll(() -> assertEquals(currentAccountPrefix + "USD", ndpFinanceInformationPage.getTextCurrency()),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderName(), TextConstants.displayVerify + "AccountHolderName "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderStreet1(), TextConstants.displayVerify + "AccountHolderStreet1 "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderStreet2(), TextConstants.displayVerify + "AccountHolderStreet2 "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderCity(), TextConstants.displayVerify + "AccountHolderCity "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderPostalCode(), TextConstants.displayVerify + "AccountHolderPostalCode "),
                () -> assertTrue(ndpFinanceInformationPage.displayCountryRegion(), TextConstants.displayVerify + "CountryRegion "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderState(), TextConstants.displayVerify + "AccountHolderState "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountCurrency(), TextConstants.displayVerify + "AccountCurrency "),
                () -> assertTrue(ndpFinanceInformationPage.displayBranchOfficeName(), TextConstants.displayVerify + "BranchOfficeName "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountType(), TextConstants.displayVerify + "AccountType "),
                () -> assertTrue(ndpFinanceInformationPage.displayBankName(), TextConstants.displayVerify + "BankName "),
                () -> assertTrue(ndpFinanceInformationPage.displayBICSwiftCode(), TextConstants.displayVerify + "BICSwiftCode "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountNumber(), TextConstants.displayVerify + "AccountNumber ")
        );

        //input invalid character and submit
        for (String character : characters) {
            ndpFinanceInformationPage.enterAccountHolderName(character).enterAccountHolderStreet1(character).enterAccountHolderStreet2(character).enterBankName(character);
            ndpFinanceInformationPage.clickNext();
            String validMessageAccountHolderName = ndpFinanceInformationPage.getMessageAccountHolderName();
            String validMessageAccountHolderStreet1 = ndpFinanceInformationPage.getMessageAccountHolderStreet1();
            String validMessageAccountHolderStreet2 = ndpFinanceInformationPage.getMessageAccountHolderStreet2();
            String validMessageBankName = ndpFinanceInformationPage.getMessageBankName();
            ndpFinanceInformationPage.clearTextAccountHolderName();
            ndpFinanceInformationPage.clearTextAccountHolderStreet1();
            ndpFinanceInformationPage.clearTextAccountHolderStreet2();
            ndpFinanceInformationPage.clearTextBankName();
            assertAll(() -> assertEquals(validationMessage, validMessageAccountHolderName, TextConstants.validationErrorMessage + "AccountHolderName "),
                    () -> assertEquals(validationMessage, validMessageAccountHolderStreet1, TextConstants.validationErrorMessage + "AccountHolderStreet1 "),
                    () -> assertEquals(validationMessage, validMessageAccountHolderStreet2, TextConstants.validationErrorMessage + "AccountHolderStreet2 "),
                    () -> assertEquals(validationMessage, validMessageBankName, TextConstants.validationErrorMessage + "BankName ")
            );
        }
        //input valid character and submit
        ndpFinanceInformationPage.enterAccountHolderName(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderStreet1(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderStreet2(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterBankName(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderCity(accountHolderCityValue).enterAccountHolderPostalCode(accountHolderPostalCodeValue).selectCountryRegion("China")
                .selectAccountCurrency()
                .enterAccountNumber(ndpFinanceInformationPage.randomDigits(digit, 75, 1))
                .selectAccountType("Savings")
                .enterBranchOfficeName(branchOfficeNameValue)
                .enterBicSwiftCode(bicSwiftCodeValue);
    }

    @Test()
    @Order(13)
    public void  verify_TemplateB_ValidAndInValidCharacter_Expect6(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        ndpFinanceInformationPage.clickNext();
        boolean errorMessage = ndpFinanceInformationPage.displayErrorMessage();
        if (errorMessage) {
            ndpFinanceInformationPage.selectAccountCurrency().clickNext();
        }
        WaitUtils.idle(3000);
        assertAll(() -> assertEquals(successBankUpdateMessage, ndpFinanceInformationPage.getMessageSubmitSuccess(), TextConstants.displayCorrectMessage),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderName(), errorMessage + "AccountHolderName "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderStreet1(), errorMessage + "AccountHolderStreet1 "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderStreet2(), errorMessage + "AccountHolderStreet2 "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageBankName(), errorMessage + "BankName ")
        );
    }

    @Test()
    @Order(14)
    public void  verify_TemplateD_Step7(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        ndpFinanceInformationPage.clickViewDetail().clickEdit();
        ndpFinanceInformationPage.selectCountryPayeeBank("Australia");
        //Verify The form displays the fields that correspond with payee template D.
        assertAll(() -> assertEquals(currentAccountPrefix + "AUD", ndpFinanceInformationPage.getTextCurrency()),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderName(), TextConstants.displayVerify + "AccountHolderName "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderStreet1(), TextConstants.displayVerify + "AccountHolderStreet1"),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderStreet2(), TextConstants.displayVerify + "AccountHolderStreet2 "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderSuburb(), TextConstants.validationErrorMessage + "AccountHolderSuburb "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderPostalCode(), TextConstants.validationErrorMessage + "HolderPostalCode "),
                () -> assertTrue(ndpFinanceInformationPage.displayCountryRegion(), TextConstants.validationErrorMessage + "CountryRegion "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderState(), TextConstants.validationErrorMessage + "AccountHolderState "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountCurrency(), TextConstants.validationErrorMessage + "AccountCurrency "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountType(), TextConstants.validationErrorMessage + "AccountType "),
                () -> assertTrue(ndpFinanceInformationPage.displayBankName(), TextConstants.validationErrorMessage + "BankName "),
                () -> assertTrue(ndpFinanceInformationPage.displayBICSwiftCode(), TextConstants.validationErrorMessage + "BICSwiftCode "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountNumber(), TextConstants.validationErrorMessage + "AccountNumber "),
                () -> assertTrue(ndpFinanceInformationPage.displayBSBNumber(), TextConstants.validationErrorMessage + "BSBNumber ")
        );

        //input invalid character and submit
        for (String character : characters) {
            ndpFinanceInformationPage.enterAccountHolderName(character).enterAccountHolderStreet1(character).enterAccountHolderStreet2(character).enterBankName(character);
            ndpFinanceInformationPage.clickNext();
            String validMessageAccountHolderName = ndpFinanceInformationPage.getMessageAccountHolderName();
            String validMessageAccountHolderStreet1 = ndpFinanceInformationPage.getMessageAccountHolderStreet1();
            String validMessageAccountHolderStreet2 = ndpFinanceInformationPage.getMessageAccountHolderStreet2();
            String validMessageBankName = ndpFinanceInformationPage.getMessageBankName();
            ndpFinanceInformationPage.clearTextAccountHolderName();
            ndpFinanceInformationPage.clearTextAccountHolderStreet1();
            ndpFinanceInformationPage.clearTextAccountHolderStreet2();
            ndpFinanceInformationPage.clearTextBankName();
            assertAll(() -> assertEquals(validationMessage, validMessageAccountHolderName, TextConstants.validationErrorMessage + "AccountHolderName "),
                    () -> assertEquals(validationMessage, validMessageAccountHolderStreet1, TextConstants.validationErrorMessage + "AccountHolderStreet1 "),
                    () -> assertEquals(validationMessage, validMessageAccountHolderStreet2, TextConstants.validationErrorMessage + "AccountHolderStreet2 "),
                    () -> assertEquals(validationMessage, validMessageBankName, TextConstants.validationErrorMessage + "BankName ")
            );
        }
        //input valid character and submit
        ndpFinanceInformationPage.enterAccountHolderName(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderStreet1(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderStreet2(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterBankName(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderCity(accountHolderCityValue).enterAccountHolderPostalCode(accountHolderPostalCodeValue)
                .enterAccountHolderSuburb(accountHolderSuburbValue).selectCountryRegion("Australia")
                .selectAccountCurrency()
                .enterAccountNumber(ndpFinanceInformationPage.randomDigits(digit, 9, 1))
                .selectAccountType("Savings")
                .enterBSBNumber(ndpFinanceInformationPage.randomDigits(digit, 6, 6))
                .enterBicSwiftCode(bicSwiftCodeValue);
    }

    @Test()
    @Order(15)
    public void  verify_TemplateD_ValidAndInValidCharacter_Expect7(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        ndpFinanceInformationPage.clickNext();
        boolean errorMessage = ndpFinanceInformationPage.displayErrorMessage();
        if (errorMessage) {
            ndpFinanceInformationPage.selectAccountCurrency().clickNext();
        }
        WaitUtils.idle(3000);
        assertAll(() -> assertEquals(successBankUpdateMessage, ndpFinanceInformationPage.getMessageSubmitSuccess(), TextConstants.displayCorrectMessage),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderName(), errorMessage + "AccountHolderName "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderStreet1(), errorMessage + "AccountHolderStreet1 "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderStreet2(), errorMessage + "AccountHolderStreet2 "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageBankName(), errorMessage + "BankName ")
        );
    }

    @Test()
    @Order(16)
    public void  verify_TemplateE_Step8(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        ndpFinanceInformationPage.clickViewDetail().clickEdit();
        ndpFinanceInformationPage.selectCountryPayeeBank("Canada");
        //Verify The form displays the fields that correspond with payee template E.
        assertAll(() -> assertEquals(currentAccountPrefix + "CAD", ndpFinanceInformationPage.getTextCurrency()),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderName(), TextConstants.displayVerify + "AccountHolderName "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderStreet1(), TextConstants.displayVerify + "AccountHolderStreet1 "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderStreet2(), TextConstants.displayVerify + "AccountHolderStreet2 "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderCity(), TextConstants.displayVerify + "AccountHolderCity "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderPostalCode(), TextConstants.displayVerify + "AccountHolderPostalCode "),
                () -> assertTrue(ndpFinanceInformationPage.displayCountryRegion(), TextConstants.displayVerify + "CountryRegion "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderState(), TextConstants.displayVerify + "AccountHolderState "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountNumber(), TextConstants.displayVerify + "AccountNumber "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountCurrency(), TextConstants.displayVerify + "AccountCurrency "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountType(), TextConstants.displayVerify + "AccountType "),
                () -> assertTrue(ndpFinanceInformationPage.displayBankName(), TextConstants.displayVerify + "BankName "),
                () -> assertTrue(ndpFinanceInformationPage.displayBranchOfficeName(), TextConstants.displayVerify + "BranchOfficeName "),
                () -> assertTrue(ndpFinanceInformationPage.displayBankRoutingNumber(), TextConstants.displayVerify + "BankRoutingNumber "),
                () -> assertTrue(ndpFinanceInformationPage.displayBICSwiftCode(), TextConstants.displayVerify + "BICSwiftCode ")
        );

        //input invalid character and submit
        for (String character : characters) {
            ndpFinanceInformationPage.enterAccountHolderName(character).enterAccountHolderStreet1(character).enterAccountHolderStreet2(character).enterBankName(character);
            ndpFinanceInformationPage.clickNext();
            String validMessageAccountHolderName = ndpFinanceInformationPage.getMessageAccountHolderName();
            String validMessageAccountHolderStreet1 = ndpFinanceInformationPage.getMessageAccountHolderStreet1();
            String validMessageAccountHolderStreet2 = ndpFinanceInformationPage.getMessageAccountHolderStreet2();
            String validMessageBankName = ndpFinanceInformationPage.getMessageBankName();
            ndpFinanceInformationPage.clearTextAccountHolderName();
            ndpFinanceInformationPage.clearTextAccountHolderStreet1();
            ndpFinanceInformationPage.clearTextAccountHolderStreet2();
            ndpFinanceInformationPage.clearTextBankName();
            assertAll(() -> assertEquals(validationMessage, validMessageAccountHolderName, TextConstants.validationErrorMessage + "AccountHolderName "),
                    () -> assertEquals(validationMessage, validMessageAccountHolderStreet1, TextConstants.validationErrorMessage + "AccountHolderStreet1 "),
                    () -> assertEquals(validationMessage, validMessageAccountHolderStreet2, TextConstants.validationErrorMessage + "AccountHolderStreet2 "),
                    () -> assertEquals(validationMessage, validMessageBankName, TextConstants.validationErrorMessage + "BankName ")
            );
        }
        //input valid character and submit
        ndpFinanceInformationPage.enterAccountHolderName(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderStreet1(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderStreet2(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterBankName(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderCity(accountHolderCityValue).enterAccountHolderPostalCode(accountHolderPostalCodeValue)
                .selectCountryRegion("Canada")
                .selectAccountCurrency()
                .enterAccountNumber(ndpFinanceInformationPage.randomDigits(digit, 12, 1))
                .selectAccountType("Savings")
                .enterBranchOfficeName(branchOfficeNameValue)
                .enterBankRoutingNumber(ndpFinanceInformationPage.randomDigits(digit, 9, 9))
                .enterBicSwiftCode(bicSwiftCodeValue);
    }

    @Test()
    @Order(17)
    public void  verify_TemplateE_ValidAndInvalidCharacterAndSubmitSuccess_Expect8(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        ndpFinanceInformationPage.clickNext();
        boolean errorMessage = ndpFinanceInformationPage.displayErrorMessage();
        if (errorMessage) {
            ndpFinanceInformationPage.selectAccountCurrency().clickNext();
        }
        WaitUtils.idle(3000);
        assertAll(() -> assertEquals(successBankUpdateMessage, ndpFinanceInformationPage.getMessageSubmitSuccess(), TextConstants.displayCorrectMessage),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderName(), errorMessage + "AccountHolderName "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderStreet1(), errorMessage + "AccountHolderStreet1 "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderStreet2(), errorMessage + "AccountHolderStreet2 "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageBankName(), errorMessage + "BankName ")
        );
    }

    @Test()
    @Order(18)
    public void  verify_TemplateF_Step9(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        ndpFinanceInformationPage.clickViewDetail().clickEdit();
        ndpFinanceInformationPage.selectCountryPayeeBank("United States");
        //Verify The form displays the fields that correspond with payee template E.
        assertAll(() -> assertEquals(currentAccountPrefix + "USD", ndpFinanceInformationPage.getTextCurrency()),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderName(), TextConstants.displayVerify + "AccountHolderName "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderStreet1(), TextConstants.displayVerify + "AccountHolderStreet1 "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderStreet2(), TextConstants.displayVerify + "AccountHolderStreet2 "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderCity(), TextConstants.displayVerify + "AccountHolderCity "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderPostalCode(), TextConstants.displayVerify + "AccountHolderPostalCode "),
                () -> assertTrue(ndpFinanceInformationPage.displayCountryRegion(), TextConstants.displayVerify + "CountryRegion "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderState(), TextConstants.displayVerify + "AccountHolderState "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountNumber(), TextConstants.displayVerify + "AccountNumber "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountCurrency(), TextConstants.displayVerify + "AccountCurrency "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountType(), TextConstants.displayVerify + "AccountType "),
                () -> assertTrue(ndpFinanceInformationPage.displayBankName(), TextConstants.displayVerify + "BankName "),
                () -> assertTrue(ndpFinanceInformationPage.displayBICSwiftCode(), TextConstants.displayVerify + "BICSwiftCode "),
                () -> assertTrue(ndpFinanceInformationPage.displayACHRoutingNumber(), TextConstants.displayVerify + "ACHRoutingNumber ")
        );

        //input invalid character and submit
        for (String character : characters) {
            ndpFinanceInformationPage.enterAccountHolderName(character).enterAccountHolderStreet1(character).enterAccountHolderStreet2(character)
                    .enterBankName(character);
            ndpFinanceInformationPage.clickNext();
            String validMessageAccountHolderName = ndpFinanceInformationPage.getMessageAccountHolderName();
            String validMessageAccountHolderStreet1 = ndpFinanceInformationPage.getMessageAccountHolderStreet1();
            String validMessageAccountHolderStreet2 = ndpFinanceInformationPage.getMessageAccountHolderStreet2();
            String validMessageBankName = ndpFinanceInformationPage.getMessageBankName();
            ndpFinanceInformationPage.clearTextAccountHolderName();
            ndpFinanceInformationPage.clearTextAccountHolderStreet1();
            ndpFinanceInformationPage.clearTextAccountHolderStreet2();
            ndpFinanceInformationPage.clearTextBankName();
            assertAll(() -> assertEquals(validationMessage, validMessageAccountHolderName, TextConstants.validationErrorMessage + "AccountHolderName "),
                    () -> assertEquals(validationMessage, validMessageAccountHolderStreet1, TextConstants.validationErrorMessage + "AccountHolderStreet1 "),
                    () -> assertEquals(validationMessage, validMessageAccountHolderStreet2, TextConstants.validationErrorMessage + "AccountHolderStreet2 "),
                    () -> assertEquals(validationMessage, validMessageBankName, TextConstants.validationErrorMessage + "BankName ")
            );
        }
        //input valid character and submit
        ndpFinanceInformationPage.enterAccountHolderName(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderStreet1(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderStreet2(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterBankName(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderCity(accountHolderCityValue).enterAccountHolderPostalCode(accountHolderPostalCodeValue)
                .selectCountryRegion("United States")
                .selectAccountCurrency()
                .enterAccountNumber(ndpFinanceInformationPage.randomDigits(digit, 17, 1))
                .selectAccountType("Savings")
                .enterACHRoutingNumber(ndpFinanceInformationPage.randomDigits(digit, 9, 9))
                .enterBicSwiftCode(bicSwiftCodeValue);
    }

    @Test()
    @Order(19)
    public void  verify_TemplateF_ValidAndInvalidCharacterAndSubmitSuccess_Expect9(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        ndpFinanceInformationPage.clickNext();
        boolean errorMessage = ndpFinanceInformationPage.displayErrorMessage();
        if (errorMessage) {
            ndpFinanceInformationPage.selectAccountCurrency().clickNext();
        }
        WaitUtils.idle(3000);
        assertAll(() -> assertEquals(successBankUpdateMessage, ndpFinanceInformationPage.getMessageSubmitSuccess(), TextConstants.displayCorrectMessage),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderName(), errorMessage + "AccountHolderName "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderStreet1(), errorMessage + "AccountHolderStreet1 "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderStreet2(), errorMessage + "AccountHolderStreet2 "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageBankName(), errorMessage + "BankName ")
        );
    }

    @Test()
    @Order(20)
    public void  verify_TemplateG_Step10(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        ndpFinanceInformationPage.clickViewDetail().clickEdit();
        ndpFinanceInformationPage.selectCountryPayeeBank("New Zealand");
        //Verify The form displays the fields that correspond with payee template E.
        assertAll(() -> assertEquals(currentAccountPrefix + "NZD", ndpFinanceInformationPage.getTextCurrency()),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderName(), TextConstants.displayVerify + "AccountHolderName "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderStreet1(), TextConstants.displayVerify + "AccountHolderStreet1 "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderStreet2(), TextConstants.displayVerify + "AccountHolderStreet2 "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderCity(), TextConstants.displayVerify + "AccountHolderCity "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderPostalCode(), TextConstants.displayVerify + "AccountHolderPostalCode "),
                () -> assertTrue(ndpFinanceInformationPage.displayCountryRegion(), TextConstants.displayVerify + "CountryRegion "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderState(), TextConstants.displayVerify + "AccountHolderState "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountNumber(), TextConstants.displayVerify + "AccountNumber "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountCurrency(), TextConstants.displayVerify + "AccountCurrency "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountType(), TextConstants.displayVerify + "AccountType "),
                () -> assertTrue(ndpFinanceInformationPage.displayBankName(), TextConstants.displayVerify + "BankName "),
                () -> assertTrue(ndpFinanceInformationPage.displayBankNumber(), TextConstants.displayVerify + "BankNumber "),
                () -> assertTrue(ndpFinanceInformationPage.displayBranchOfficeName(), TextConstants.displayVerify + "BranchOfficeName "),
                () -> assertTrue(ndpFinanceInformationPage.displayBranchOfficeNumber(), TextConstants.displayVerify + "BranchOfficeNumber "),
                () -> assertTrue(ndpFinanceInformationPage.displayBankRoutingNumber(), TextConstants.displayVerify + "BankRoutingNumber "),
                () -> assertTrue(ndpFinanceInformationPage.displayBICSwiftCode(), TextConstants.displayVerify + "BICSwiftCode ")
        );

        //input invalid character and submit
        for (String character : characters) {
            ndpFinanceInformationPage.enterAccountHolderName(character).enterAccountHolderStreet1(character).enterAccountHolderStreet2(character)
                    .enterBankName(character);
            ndpFinanceInformationPage.clickNext();
            String validMessageAccountHolderName = ndpFinanceInformationPage.getMessageAccountHolderName();
            String validMessageAccountHolderStreet1 = ndpFinanceInformationPage.getMessageAccountHolderStreet1();
            String validMessageAccountHolderStreet2 = ndpFinanceInformationPage.getMessageAccountHolderStreet2();
            String validMessageBankName = ndpFinanceInformationPage.getMessageBankName();
            ndpFinanceInformationPage.clearTextAccountHolderName();
            ndpFinanceInformationPage.clearTextAccountHolderStreet1();
            ndpFinanceInformationPage.clearTextAccountHolderStreet2();
            ndpFinanceInformationPage.clearTextBankName();
            assertAll(() -> assertEquals(validationMessage, validMessageAccountHolderName, TextConstants.validationErrorMessage + "AccountHolderName "),
                    () -> assertEquals(validationMessage, validMessageAccountHolderStreet1, TextConstants.validationErrorMessage + "AccountHolderStreet1 "),
                    () -> assertEquals(validationMessage, validMessageAccountHolderStreet2, TextConstants.validationErrorMessage + "AccountHolderStreet2 "),
                    () -> assertEquals(validationMessage, validMessageBankName, TextConstants.validationErrorMessage + "BankName ")
            );
        }
        //input valid character and submit
        ndpFinanceInformationPage.enterAccountHolderName(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderStreet1(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderStreet2(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterBankName(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderCity(accountHolderCityValue).enterAccountHolderPostalCode(accountHolderPostalCodeValue)
                .selectCountryRegion("New Zealand")
                .selectAccountCurrency()
                .enterAccountNumber(ndpFinanceInformationPage.randomDigits(digit, 10, 10))//10 digits
                .selectAccountType("Savings")
                .enterBankNumber(bankNumberValue)
                .enterBranchOfficeName(branchOfficeNameValue)
                .enterBranchOfficeNumber(branchOfficeNumberValue)
                .enterBankRoutingNumber(ndpFinanceInformationPage.randomDigits(digit, 6, 6))
                .enterBicSwiftCode(bicSwiftCodeValue);
    }

    @Test()
    @Order(21)
    public void  verify_TemplateG_ValidAndInvalidCharacterAndSubmitSuccess_Expect10(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        ndpFinanceInformationPage.clickNext();
        boolean errorMessage = ndpFinanceInformationPage.displayErrorMessage();
        if (errorMessage) {
            ndpFinanceInformationPage.selectAccountCurrency().clickNext();
        }
        WaitUtils.idle(3000);
        assertAll(() -> assertEquals(successBankUpdateMessage, ndpFinanceInformationPage.getMessageSubmitSuccess(), TextConstants.displayCorrectMessage),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderName(), errorMessage + "AccountHolderName "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderStreet1(), errorMessage + "AccountHolderStreet1 "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderStreet2(), errorMessage + "AccountHolderStreet2 "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageBankName(), errorMessage + "BankName ")
        );
    }


    @Test()
    @Order(22)
    public void  verify_TemplateH_Step11(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        ndpFinanceInformationPage.clickViewDetail().clickEdit();
        ndpFinanceInformationPage.selectCountryPayeeBank("United Kingdom");
        //Verify The form displays the fields that correspond with payee template E.
        assertAll(() -> assertEquals(currentAccountPrefix + "GBP", ndpFinanceInformationPage.getTextCurrency()),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderName(), TextConstants.displayVerify + "AccountHolderName "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderStreet1(), TextConstants.displayVerify + "AccountHolderStreet1 "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderStreet2(), TextConstants.displayVerify + "AccountHolderStreet2 "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderCity(), TextConstants.displayVerify + "AccountHolderCity "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderPostalCode(), TextConstants.displayVerify + "AccountHolderPostalCode "),
                () -> assertTrue(ndpFinanceInformationPage.displayCountryRegion(), TextConstants.displayVerify + "CountryRegion "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderState(), TextConstants.displayVerify + "AccountHolderState "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountNumber(), TextConstants.displayVerify + "AccountNumber "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountCurrency(), TextConstants.displayVerify + "AccountCurrency "),
                () -> assertTrue(ndpFinanceInformationPage.displayIBANCode(), TextConstants.displayVerify + "IBANCode "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountType(), TextConstants.displayVerify + "AccountType "),
                () -> assertTrue(ndpFinanceInformationPage.displayBankName(), TextConstants.displayVerify + "BankName "),
                () -> assertTrue(ndpFinanceInformationPage.displayBankSortCode(), TextConstants.displayVerify + "BankSortCode "),
                () -> assertTrue(ndpFinanceInformationPage.displayBICSwiftCode(), TextConstants.displayVerify + "BICSwiftCode ")
        );

        //input invalid character and submit
        for (String character : characters) {
            ndpFinanceInformationPage.enterAccountHolderName(character).enterAccountHolderStreet1(character).enterAccountHolderStreet2(character)
                    .enterBankName(character);
            ndpFinanceInformationPage.clickNext();
            String validMessageAccountHolderName = ndpFinanceInformationPage.getMessageAccountHolderName();
            String validMessageAccountHolderStreet1 = ndpFinanceInformationPage.getMessageAccountHolderStreet1();
            String validMessageAccountHolderStreet2 = ndpFinanceInformationPage.getMessageAccountHolderStreet2();
            String validMessageBankName = ndpFinanceInformationPage.getMessageBankName();
            ndpFinanceInformationPage.clearTextAccountHolderName();
            ndpFinanceInformationPage.clearTextAccountHolderStreet1();
            ndpFinanceInformationPage.clearTextAccountHolderStreet2();
            ndpFinanceInformationPage.clearTextBankName();
            assertAll(() -> assertEquals(validationMessage, validMessageAccountHolderName, TextConstants.validationErrorMessage + "AccountHolderName "),
                    () -> assertEquals(validationMessage, validMessageAccountHolderStreet1, TextConstants.validationErrorMessage + "AccountHolderStreet1 "),
                    () -> assertEquals(validationMessage, validMessageAccountHolderStreet2, TextConstants.validationErrorMessage + "AccountHolderStreet2 "),
                    () -> assertEquals(validationMessage, validMessageBankName, TextConstants.validationErrorMessage + "BankName ")
            );
        }
        //input valid character and submit
        ndpFinanceInformationPage.enterAccountHolderName(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderStreet1(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderStreet2(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterBankName(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderCity(accountHolderCityValue).enterAccountHolderPostalCode(accountHolderPostalCodeValue)
                .selectCountryRegion("United Kingdom")
                .selectAccountCurrency()
                .enterAccountNumber(ndpFinanceInformationPage.randomDigits(digit, 11, 8))//10 digits
                .enterIBANCode(ibanCodeValue)
                .selectAccountType("Savings")
                .enterBankSortCode(ndpFinanceInformationPage.randomDigits(digit, 6, 6))
                .enterBicSwiftCode(bicSwiftCodeValue);
    }

    @Test()
    @Order(23)
    public void  verify_TemplateH_ValidAndInvalidCharacterAndSubmitSuccess_Expect11(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        ndpFinanceInformationPage.clickNext();
        boolean errorMessage = ndpFinanceInformationPage.displayErrorMessage();
        if (errorMessage) {
            ndpFinanceInformationPage.selectAccountCurrency().clickNext();
        }
        WaitUtils.idle(3000);
        assertAll(() -> assertEquals(successBankUpdateMessage, ndpFinanceInformationPage.getMessageSubmitSuccess(), TextConstants.displayCorrectMessage),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderName(), errorMessage + "AccountHolderName "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderStreet1(), errorMessage + "AccountHolderStreet1 "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderStreet2(), errorMessage + "AccountHolderStreet2 "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageBankName(), errorMessage + "BankName ")
        );
    }

    @Test()
    @Order(24)
    public void  verify_TemplateI_Step12(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        ndpFinanceInformationPage.clickViewDetail().clickEdit();
        ndpFinanceInformationPage.selectCountryPayeeBank("Sweden");
        //Verify The form displays the fields that correspond with payee template E.
        assertAll(() -> assertEquals(currentAccountPrefix + "SEK", ndpFinanceInformationPage.getTextCurrency()),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderName(), TextConstants.displayVerify + "AccountHolderName "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderStreet1(), TextConstants.displayVerify + "AccountHolderStreet1 "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderStreet2(), TextConstants.displayVerify + "AccountHolderStreet2 "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderCity(), TextConstants.displayVerify + "AccountHolderCity "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderPostalCode(), TextConstants.displayVerify + "AccountHolderPostalCode "),
                () -> assertTrue(ndpFinanceInformationPage.displayCountryRegion(), TextConstants.displayVerify + "CountryRegion "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderState(), TextConstants.displayVerify + "AccountHolderState "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountNumber(), TextConstants.displayVerify + "AccountNumber "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountCurrency(), TextConstants.displayVerify + "AccountCurrency "),
                () -> assertTrue(ndpFinanceInformationPage.displayIBANCode(), TextConstants.displayVerify + "IBANCode "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountType(), TextConstants.displayVerify + "AccountType "),
                () -> assertTrue(ndpFinanceInformationPage.displayBankName(), TextConstants.displayVerify + "BankName "),
                () -> assertTrue(ndpFinanceInformationPage.displayBankgiroNumber(), TextConstants.displayVerify + "BankgiroNumber "),
                () -> assertTrue(ndpFinanceInformationPage.displayBICSwiftCode(), TextConstants.displayVerify + "BICSwiftCode ")
        );

        //input invalid character and submit
        for (String character : characters) {
            ndpFinanceInformationPage.enterAccountHolderName(character).enterAccountHolderStreet1(character).enterAccountHolderStreet2(character)
                    .enterBankName(character);
            ndpFinanceInformationPage.clickNext();
            String validMessageAccountHolderName = ndpFinanceInformationPage.getMessageAccountHolderName();
            String validMessageAccountHolderStreet1 = ndpFinanceInformationPage.getMessageAccountHolderStreet1();
            String validMessageAccountHolderStreet2 = ndpFinanceInformationPage.getMessageAccountHolderStreet2();
            String validMessageBankName = ndpFinanceInformationPage.getMessageBankName();
            ndpFinanceInformationPage.clearTextAccountHolderName();
            ndpFinanceInformationPage.clearTextAccountHolderStreet1();
            ndpFinanceInformationPage.clearTextAccountHolderStreet2();
            ndpFinanceInformationPage.clearTextBankName();
            assertAll(() -> assertEquals(validationMessage, validMessageAccountHolderName, TextConstants.validationErrorMessage + "AccountHolderName "),
                    () -> assertEquals(validationMessage, validMessageAccountHolderStreet1, TextConstants.validationErrorMessage + "AccountHolderStreet1 "),
                    () -> assertEquals(validationMessage, validMessageAccountHolderStreet2, TextConstants.validationErrorMessage + "AccountHolderStreet2 "),
                    () -> assertEquals(validationMessage, validMessageBankName, TextConstants.validationErrorMessage + "BankName ")
            );
        }
        //input valid character and submit
        ndpFinanceInformationPage.enterAccountHolderName(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderStreet1(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderStreet2(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterBankName(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderCity(accountHolderCityValue).enterAccountHolderPostalCode(accountHolderPostalCodeValue)
                .selectCountryRegion("Sweden")
                .selectAccountCurrency()
                .enterAccountNumber(ndpFinanceInformationPage.randomDigits(digit, 16, 16))//10 digits
                .enterIBANCode(ibanCodeValue)
                .selectAccountType("Savings")
                .enterBankgiroNumber(ndpFinanceInformationPage.randomDigits(digit, 10, 1))
                .enterBicSwiftCode(bicSwiftCodeValue);
    }

    @Test()
    @Order(25)
    public void  verify_TemplateI_ValidAndInvalidCharacterAndSubmitSuccess_Expect12(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        ndpFinanceInformationPage.clickNext();
        boolean errorMessage = ndpFinanceInformationPage.displayErrorMessage();
        if (errorMessage) {
            ndpFinanceInformationPage.selectAccountCurrency().clickNext();
        }
        WaitUtils.idle(3000);
        assertAll(() -> assertEquals(successBankUpdateMessage, ndpFinanceInformationPage.getMessageSubmitSuccess(), TextConstants.displayCorrectMessage),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderName(), errorMessage + "AccountHolderName "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderStreet1(), errorMessage + "AccountHolderStreet1 "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderStreet2(), errorMessage + "AccountHolderStreet2 "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageBankName(), errorMessage + "BankName ")
        );
    }

    @Test()
    @Order(26)
    public void  verify_TemplateJ_Step13(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        ndpFinanceInformationPage.clickViewDetail().clickEdit();
        ndpFinanceInformationPage.selectCountryPayeeBank("Russia");
        //Verify The form displays the fields that correspond with payee template E.
        assertAll(() -> assertEquals(currentAccountPrefix + "RUB", ndpFinanceInformationPage.getTextCurrency()),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderName(), TextConstants.displayVerify + "AccountHolderName"),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderStreet1(), TextConstants.displayVerify + "AccountHolderStreet1 "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderStreet2(), TextConstants.displayVerify + "AccountHolderStreet2 "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderCity(), TextConstants.displayVerify + "AccountHolderCity "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderPostalCode(), TextConstants.displayVerify + "AccountHolderPostalCode"),
                () -> assertTrue(ndpFinanceInformationPage.displayCountryRegion(), TextConstants.displayVerify + "CountryRegion "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderState(), TextConstants.displayVerify + "AccountHolderState "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountNumber(), TextConstants.displayVerify + "AccountNumber"),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountCurrency(), TextConstants.displayVerify + "AccountCurrency "),
                () -> assertTrue(ndpFinanceInformationPage.displayBankName(), TextConstants.displayVerify + "BankName "),
                () -> assertTrue(ndpFinanceInformationPage.displayBankBIKCode(), TextConstants.displayVerify + "BankBIKCode "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountNumberCentralBank(), TextConstants.displayVerify + "AccountNumberCentralBank"),
                () -> assertTrue(ndpFinanceInformationPage.displayINN(), TextConstants.displayVerify + "INN "),
                () -> assertTrue(ndpFinanceInformationPage.displayKPP(), TextConstants.displayVerify + "KPP "),
                () -> assertTrue(ndpFinanceInformationPage.displayBICSwiftCode(), TextConstants.displayVerify + "BICSwiftCode ")
        );

        //input invalid character and submit
        for (String character : characters) {
            ndpFinanceInformationPage.enterAccountHolderName(character).enterAccountHolderStreet1(character).enterAccountHolderStreet2(character)
                    .enterBankName(character);
            ndpFinanceInformationPage.clickNext();
            String validMessageAccountHolderName = ndpFinanceInformationPage.getMessageAccountHolderName();
            String validMessageAccountHolderStreet1 = ndpFinanceInformationPage.getMessageAccountHolderStreet1();
            String validMessageAccountHolderStreet2 = ndpFinanceInformationPage.getMessageAccountHolderStreet2();
            String validMessageBankName = ndpFinanceInformationPage.getMessageBankName();
            ndpFinanceInformationPage.clearTextAccountHolderName();
            ndpFinanceInformationPage.clearTextAccountHolderStreet1();
            ndpFinanceInformationPage.clearTextAccountHolderStreet2();
            ndpFinanceInformationPage.clearTextBankName();
            assertAll(() -> assertEquals(validationMessage, validMessageAccountHolderName, TextConstants.validationErrorMessage + "AccountHolderName "),
                    () -> assertEquals(validationMessage, validMessageAccountHolderStreet1, TextConstants.validationErrorMessage + "AccountHolderStreet1 "),
                    () -> assertEquals(validationMessage, validMessageAccountHolderStreet2, TextConstants.validationErrorMessage + "AccountHolderStreet2 "),
                    () -> assertEquals(validationMessage, validMessageBankName, TextConstants.validationErrorMessage + "BankName ")
            );
        }
        //input valid character and submit
        ndpFinanceInformationPage.enterAccountHolderName(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderStreet1(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderStreet2(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterBankName(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderCity(accountHolderCityValue).enterAccountHolderPostalCode(accountHolderPostalCodeValue)
                .selectCountryRegion("Russia")
                .selectAccountCurrency()
                .enterAccountNumber(ndpFinanceInformationPage.randomDigits(digit, 20, 20))//10 digits
                .enterBankBIKCode(ndpFinanceInformationPage.randomDigits(digit, 9, 9))
                .enterAccountNumberCentral(ndpFinanceInformationPage.randomDigits(digit, 20, 20))
                .enterINN(ndpFinanceInformationPage.randomDigits(digit, 12, 5))
                .enterBicSwiftCode(bicSwiftCodeValue);
    }

    @Test()
    @Order(27)
    public void  verify_TemplateJ_ValidAndInvalidCharacterAndSubmitSuccess_Expect13(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        ndpFinanceInformationPage.clickNext();
        boolean errorMessage = ndpFinanceInformationPage.displayErrorMessage();
        if (errorMessage) {
            ndpFinanceInformationPage.selectAccountCurrency().clickNext();
        }
        WaitUtils.idle(3000);
        assertAll(() -> assertEquals(successBankUpdateMessage, ndpFinanceInformationPage.getMessageSubmitSuccess(), TextConstants.displayCorrectMessage),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderName(), errorMessage + "AccountHolderName "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderStreet1(), errorMessage + "AccountHolderStreet1 "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderStreet2(), errorMessage + "AccountHolderStreet2 "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageBankName(), errorMessage + "BankName ")
        );
    }

    @Test()
    @Order(28)
    public void  verify_TemplateK_Step14(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        ndpFinanceInformationPage.clickViewDetail().clickEdit();
        ndpFinanceInformationPage.selectCountryPayeeBank("Norway");
        //Verify The form displays the fields that correspond with payee template K.
        assertAll(() -> assertEquals(currentAccountPrefix + "NOK", ndpFinanceInformationPage.getTextCurrency()),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderName(), TextConstants.displayVerify + "AccountHolderName "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderStreet1(), TextConstants.displayVerify + "AccountHolderStreet1 "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderStreet2(), TextConstants.displayVerify + "AccountHolderStreet2 "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderCity(), TextConstants.displayVerify + "displayAccountHolderCity"),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderPostalCode(), TextConstants.displayVerify + "AccountHolderPostalCode"),
                () -> assertTrue(ndpFinanceInformationPage.displayCountryRegion(), TextConstants.displayVerify + "CountryRegion"),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderState(), TextConstants.displayVerify + "AccountHolderState"),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountNumber(), TextConstants.displayVerify + "AccountNumber "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountCurrency(), TextConstants.displayVerify + "AccountCurrency "),
                () -> assertTrue(ndpFinanceInformationPage.displayIBANCode(), TextConstants.displayVerify + "IBANCode"),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountType(), TextConstants.displayVerify + "AccountType "),
                () -> assertTrue(ndpFinanceInformationPage.displayBankName(), TextConstants.displayVerify + "BankName "),
                () -> assertTrue(ndpFinanceInformationPage.displayBankgiroNumber(), TextConstants.displayVerify + "BankgiroNumber "),
                () -> assertTrue(ndpFinanceInformationPage.displayBICSwiftCode(), TextConstants.displayVerify + "BICSwiftCode ")
        );

        //input invalid character and submit
        for (String character : characters) {
            ndpFinanceInformationPage.enterAccountHolderName(character).enterAccountHolderStreet1(character).enterAccountHolderStreet2(character)
                    .enterBankName(character);
            ndpFinanceInformationPage.clickNext();
            String validMessageAccountHolderName = ndpFinanceInformationPage.getMessageAccountHolderName();
            String validMessageAccountHolderStreet1 = ndpFinanceInformationPage.getMessageAccountHolderStreet1();
            String validMessageAccountHolderStreet2 = ndpFinanceInformationPage.getMessageAccountHolderStreet2();
            String validMessageBankName = ndpFinanceInformationPage.getMessageBankName();
            ndpFinanceInformationPage.clearTextAccountHolderName();
            ndpFinanceInformationPage.clearTextAccountHolderStreet1();
            ndpFinanceInformationPage.clearTextAccountHolderStreet2();
            ndpFinanceInformationPage.clearTextBankName();
            assertAll(() -> assertEquals(validationMessage, validMessageAccountHolderName, TextConstants.validationErrorMessage + "AccountHolderName "),
                    () -> assertEquals(validationMessage, validMessageAccountHolderStreet1, TextConstants.validationErrorMessage + "AccountHolderStreet1 "),
                    () -> assertEquals(validationMessage, validMessageAccountHolderStreet2, TextConstants.validationErrorMessage + "AccountHolderStreet2 "),
                    () -> assertEquals(validationMessage, validMessageBankName, TextConstants.validationErrorMessage + "BankName ")
            );
        }
        //input valid character and submit
        ndpFinanceInformationPage.enterAccountHolderName(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderStreet1(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderStreet2(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterBankName(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderCity(accountHolderCityValue).enterAccountHolderPostalCode(accountHolderPostalCodeValue)
                .selectCountryRegion("Norway")
                .selectAccountCurrency()
                .enterAccountNumber(ndpFinanceInformationPage.randomDigits(digit, 11, 11))//10 digits
                .enterIBANCode(ibanCodeValue)
                .selectAccountType("Savings")
                .enterBankgiroNumber(ndpFinanceInformationPage.randomDigits(digit, 10, 1))
                .enterBicSwiftCode(bicSwiftCodeValue);
    }

    @Test()
    @Order(29)
    public void  verify_TemplateK_ValidAndInvalidCharacterAndSubmitSuccess_Expect14(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        ndpFinanceInformationPage.clickNext();
        boolean errorMessage = ndpFinanceInformationPage.displayErrorMessage();
        if (errorMessage) {
            ndpFinanceInformationPage.selectAccountCurrency().clickNext();
        }
        WaitUtils.idle(3000);
        assertAll(() -> assertEquals(successBankUpdateMessage, ndpFinanceInformationPage.getMessageSubmitSuccess(), TextConstants.displayCorrectMessage),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderName(), TextConstants.validationErrorMessage + "AccountHolderName "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderStreet1(), TextConstants.validationErrorMessage + "AccountHolderStreet1 "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderStreet2(), TextConstants.validationErrorMessage + "AccountHolderStreet2 "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageBankName(), TextConstants.validationErrorMessage + "BankName ")
        );
    }

    @Test()
    @Order(30)
    public void verify_TemplateL_Step15(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        ndpFinanceInformationPage.clickViewDetail().clickEdit();
        ndpFinanceInformationPage.selectCountryPayeeBank("Denmark");
        //Verify The form displays the fields that correspond with payee template K.
        assertAll(() -> assertEquals(currentAccountPrefix + "DKK", ndpFinanceInformationPage.getTextCurrency()),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderName(), TextConstants.validationErrorMessage + "AccountHolderName "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderStreet1(), TextConstants.validationErrorMessage + "AccountHolderStreet1 "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderStreet2(), TextConstants.validationErrorMessage + "AccountHolderStreet2 "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderCity(), TextConstants.validationErrorMessage + "AccountHolderCity "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderPostalCode(), TextConstants.validationErrorMessage + "AccountHolderPostalCode "),
                () -> assertTrue(ndpFinanceInformationPage.displayCountryRegion(), TextConstants.validationErrorMessage + "CountryRegion "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderState(), TextConstants.validationErrorMessage + "AccountHolderState "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountNumber(), TextConstants.validationErrorMessage + "AccountNumber "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountCurrency(), TextConstants.validationErrorMessage + "AccountCurrency "),
                () -> assertTrue(ndpFinanceInformationPage.displayIBANCode(), TextConstants.validationErrorMessage + "BANCode "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountType(), TextConstants.validationErrorMessage + "AccountType "),
                () -> assertTrue(ndpFinanceInformationPage.displayBankName(), TextConstants.validationErrorMessage + "BankName "),
                () -> assertTrue(ndpFinanceInformationPage.displayBankRoutingCode(), TextConstants.validationErrorMessage + "BankRoutingCode "),
                () -> assertTrue(ndpFinanceInformationPage.displayBICSwiftCode(), TextConstants.validationErrorMessage + "BICSwiftCode ")
        );

        //input invalid character and submit
        for (String character : characters) {
            ndpFinanceInformationPage.enterAccountHolderName(character).enterAccountHolderStreet1(character).enterAccountHolderStreet2(character)
                    .enterBankName(character);
            ndpFinanceInformationPage.clickNext();
            String validMessageAccountHolderName = ndpFinanceInformationPage.getMessageAccountHolderName();
            String validMessageAccountHolderStreet1 = ndpFinanceInformationPage.getMessageAccountHolderStreet1();
            String validMessageAccountHolderStreet2 = ndpFinanceInformationPage.getMessageAccountHolderStreet2();
            String validMessageBankName = ndpFinanceInformationPage.getMessageBankName();
            ndpFinanceInformationPage.clearTextAccountHolderName();
            ndpFinanceInformationPage.clearTextAccountHolderStreet1();
            ndpFinanceInformationPage.clearTextAccountHolderStreet2();
            ndpFinanceInformationPage.clearTextBankName();
            assertAll(() -> assertEquals(validationMessage, validMessageAccountHolderName, TextConstants.validationErrorMessage + "AccountHolderName "),
                    () -> assertEquals(validationMessage, validMessageAccountHolderStreet1, TextConstants.validationErrorMessage + "AccountHolderStreet1 "),
                    () -> assertEquals(validationMessage, validMessageAccountHolderStreet2, TextConstants.validationErrorMessage + "AccountHolderStreet2 "),
                    () -> assertEquals(validationMessage, validMessageBankName, TextConstants.validationErrorMessage + "BankName ")
            );
        }
        //input valid character and submit
        ndpFinanceInformationPage.enterAccountHolderName(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderStreet1(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderStreet2(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterBankName(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderCity(accountHolderCityValue).enterAccountHolderPostalCode(accountHolderPostalCodeValue)
                .selectCountryRegion("Denmark")
                .selectAccountCurrency()
                .enterAccountNumber(ndpFinanceInformationPage.randomDigits(digit, 14, 14))
                .enterIBANCode(ibanCodeValue)
                .selectAccountType("Savings")
                .enterBankRoutingCode(ndpFinanceInformationPage.randomDigits(digit, 4, 4))
                .enterBicSwiftCode(bicSwiftCodeValue);
    }

    @Test()
    @Order(31)
    public void verify_TemplateL_Expect15(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        ndpFinanceInformationPage.clickNext();
        boolean errorMessage = ndpFinanceInformationPage.displayErrorMessage();
        if (errorMessage) {
            ndpFinanceInformationPage.selectAccountCurrency().clickNext();
        }
        WaitUtils.idle(3000);
        assertAll(() -> assertEquals(successBankUpdateMessage, ndpFinanceInformationPage.getMessageSubmitSuccess(), TextConstants.displayCorrectMessage),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderName(), TextConstants.validationErrorMessage + "AccountHolderName "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderStreet1(), TextConstants.validationErrorMessage + "AccountHolderStreet1 "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderStreet2(), TextConstants.validationErrorMessage + "AccountHolderStreet2 "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageBankName(), TextConstants.validationErrorMessage + "BankName ")
        );
    }

    @Test()
    @Order(33)
    public void verify_TemplateM_Step16(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        ndpFinanceInformationPage.clickViewDetail().clickEdit();
        ndpFinanceInformationPage.selectCountryPayeeBank("Mexico");
        //Verify The form displays the fields that correspond with payee template K.
        assertAll(() -> assertEquals(currentAccountPrefix + "MXN", ndpFinanceInformationPage.getTextCurrency()),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderName(), TextConstants.validationErrorMessage + "AccountHolderName "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderStreet1(), TextConstants.validationErrorMessage + "AccountHolderStreet1 "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderStreet2(), TextConstants.validationErrorMessage + "AccountHolderStreet2 "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderCity(), TextConstants.validationErrorMessage + "AccountHolderCity "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderPostalCode(), TextConstants.validationErrorMessage + "AccountHolderPostalCode "),
                () -> assertTrue(ndpFinanceInformationPage.displayCountryRegion(), TextConstants.validationErrorMessage + "CountryRegion "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountHolderState(), TextConstants.validationErrorMessage + "AccountHolderState "),
                () -> assertTrue(ndpFinanceInformationPage.displayAccountCurrency(), TextConstants.validationErrorMessage + "AccountCurrency "),
                () -> assertTrue(ndpFinanceInformationPage.displayBankName(), TextConstants.validationErrorMessage + "BankName "),
                () -> assertTrue(ndpFinanceInformationPage.displayCLABENumber(), TextConstants.validationErrorMessage + "CLABENumber "),
                () -> assertTrue(ndpFinanceInformationPage.displayBICSwiftCode(), TextConstants.validationErrorMessage + "BICSwiftCode ")
        );

        //input invalid character and submit
        for (String character : characters) {
            ndpFinanceInformationPage.enterAccountHolderName(character).enterAccountHolderStreet1(character).enterAccountHolderStreet2(character)
                    .enterBankName(character);
            ndpFinanceInformationPage.clickNext();
            String validMessageAccountHolderName = ndpFinanceInformationPage.getMessageAccountHolderName();
            String validMessageAccountHolderStreet1 = ndpFinanceInformationPage.getMessageAccountHolderStreet1();
            String validMessageAccountHolderStreet2 = ndpFinanceInformationPage.getMessageAccountHolderStreet2();
            String validMessageBankName = ndpFinanceInformationPage.getMessageBankName();
            ndpFinanceInformationPage.clearTextAccountHolderName();
            ndpFinanceInformationPage.clearTextAccountHolderStreet1();
            ndpFinanceInformationPage.clearTextAccountHolderStreet2();
            ndpFinanceInformationPage.clearTextBankName();
            assertAll(() -> assertEquals(validationMessage, validMessageAccountHolderName, TextConstants.validationErrorMessage + "AccountHolderName "),
                    () -> assertEquals(validationMessage, validMessageAccountHolderStreet1, TextConstants.validationErrorMessage + "AccountHolderStreet1 "),
                    () -> assertEquals(validationMessage, validMessageAccountHolderStreet2, TextConstants.validationErrorMessage + "AccountHolderStreet2 "),
                    () -> assertEquals(validationMessage, validMessageBankName, TextConstants.validationErrorMessage + "BankName ")
            );
        }
        //input valid character and submit
        ndpFinanceInformationPage.enterAccountHolderName(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderStreet1(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderStreet2(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterBankName(ndpFinanceInformationPage.getRandomText(alphaNumeric));
        ndpFinanceInformationPage.enterAccountHolderCity(accountHolderCityValue).enterAccountHolderPostalCode(accountHolderPostalCodeValue)
                .selectCountryRegion("Mexico")
                .selectAccountCurrency()
                .enterCLABENumber(ndpFinanceInformationPage.randomDigits(digit, 18, 18))
                .enterBicSwiftCode(bicSwiftCodeValue);
    }

    @Test()
    @Order(34)
    public void verify_TemplateM_Expect16(IJUnitTestReporter testReport) {
        NDPFinanceInformationPage ndpFinanceInformationPage = (NDPFinanceInformationPage) currentPage;
        ndpFinanceInformationPage.clickNext();
        boolean errorMessage = ndpFinanceInformationPage.displayErrorMessage();
        if (errorMessage) {
            ndpFinanceInformationPage.selectAccountCurrency().clickNext();
        }
        WaitUtils.idle(3000);
        assertAll(() -> assertEquals(successBankUpdateMessage, ndpFinanceInformationPage.getMessageSubmitSuccess(), TextConstants.displayCorrectMessage),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderName(), TextConstants.validationErrorMessage + "AccountHolderName "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderStreet1(), TextConstants.validationErrorMessage + "AccountHolderStreet1 "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageAccountHolderStreet2(), TextConstants.validationErrorMessage + "AccountHolderStreet2 "),
                () -> assertFalse(ndpFinanceInformationPage.errorMessageBankName(), TextConstants.validationErrorMessage + "BankName ")
        );
    }

}
