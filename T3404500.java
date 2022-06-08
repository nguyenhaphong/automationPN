package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.data.DataGen;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.ndidMS.ManagementSystemLogin;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPMyAccountPage;
import net.nintendo.automation.ui.models.ndp.NDPNintendo3DSForumsEnglishSupportPage;
import net.nintendo.automation.ui.models.ndp.admin.NDPUserManagementPage;
import net.nintendo.automation.ui.models.ndp.admin.NDPViewUserPage;
import net.nintendo.automation.ui.users.User;
import net.nintendo.automation.ui.users.UserManager;
import net.nintendo.automation_core.common_client.enums.ServiceType;
import net.nintendo.automation_core.common_client.enums.UserType;
import net.nintendo.automation_core.extent_reports.extensions.IJUnitTestReporter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Classname: T3404500
 * Version: 1.0.0
 * Purpose:
 */
@Tag("T3404500")
//@ScenarioTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3404500 extends CommonBaseTest {
    private static Browser browser;
    private static User ndpUser;
    private static User ndpUserAdd;
    private static User adminUser;
    private static String firstName;
    private static String lastName;
    private static String nickName;
    private static User gptExternalSupportUser;
    private static String subject;
    private static String orgName;

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.ADMIN_USER);
        gptExternalSupportUser = new User();
        gptExternalSupportUser.setPassword(TextConstants.nintendo_approval_Password);
        gptExternalSupportUser.setUserName(TextConstants.partner_AnhBTK_Auto);
        ndpUserAdd = new User();
        ndpUserAdd.setPassword(TextConstants.nintendo_approval_Password);
        ndpUserAdd.setUserName(DataGen.getRandomUserName().toLowerCase(Locale.ROOT) + "_add");
        adminUser = new User();
        adminUser.setPassword(TextConstants.nintendo_approval_Password);
        adminUser.setUserName(TextConstants.omniAdmin);
        firstName = DataGen.getRandomString() + "_First";
        lastName = DataGen.getRandomString() + "_Last";
        nickName = DataGen.getRandomString() + "_Nick";
        subject = "Subject" + DataGen.getRandomString();
    }

    @AfterAll
    static void terminateBrowser() {
        BrowserManager.closeBrowser(browser);
        UserManager.releaseUser(ServiceType.NDP, ndpUser);
        UserManager.releaseUser(ServiceType.NDP, ndpUserAdd);
        UserManager.releaseUser(ServiceType.NDP, adminUser);
        UserManager.releaseUser(ServiceType.NDP, gptExternalSupportUser);
    }

    @Test
    @Order(1)
    public void loginNDPAsCompanyAdmin_Step1(IJUnitTestReporter testReporter) {
        NDPHomePage homePage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReporter);
        homePage.acceptCookie().clickSignInPage().signIn(ndpUser);
    }

    @Test
    @Order(2)
    public void navigateToAddNewUser_Step2(IJUnitTestReporter testReporter) {
        NDPDevelopmentHome developmentHome = browser.getPage(NDPDevelopmentHome.class, testReporter);
        developmentHome.navTopNavigation()
                .clickAdmin()
                .clickUserManagementMenu()
                .clickAddNewUserButton();
    }

    @Test
    @Order(3)
    public void verifyAddNewUserPage_Expected2(IJUnitTestReporter testReporter) {
        NDPViewUserPage viewUserPage = browser.getPage(NDPViewUserPage.class, testReporter);
        assertEquals("New User", viewUserPage.getNewUserFormTitle(), "Add New User page is visible");
    }

    @Test
    @Order(4)
    public void enterAllRequiredFields_Step3(IJUnitTestReporter testReporter) {
        NDPViewUserPage viewUserPage = browser.getPage(NDPViewUserPage.class, testReporter);
        viewUserPage.enterFirstName(firstName)
                .enterLastName(lastName)
                .enterNintendoDeveloperID(ndpUserAdd.getUserName())
                .enterNickName(nickName)
                .enterEmailAddress(ndpUserAdd.getUserName() + TextConstants.inbucket_Suffix);
    }

    @Test
    @Order(5)
    public void selectTimezonePreferences_Step4(IJUnitTestReporter testReporter) {
        NDPViewUserPage viewUserPage = browser.getPage(NDPViewUserPage.class, testReporter);
        viewUserPage.selectTimezone("(UTC +09:00) Japan Standard Time");
    }

    @Test
    @Order(6)
    public void selectLanguagePreferences_Step5(IJUnitTestReporter testReporter) {
        NDPViewUserPage viewUserPage = browser.getPage(NDPViewUserPage.class, testReporter);
        viewUserPage.selectLanguage(" Japanese (Japan) ");
    }

    @Test
    @Order(7)
    public void clickSaveNewUser_Step6(IJUnitTestReporter testReporter) {
        NDPViewUserPage viewUserPage = browser.getPage(NDPViewUserPage.class, testReporter);
        viewUserPage.selectConfirmedCheckbox()
                .clickSaveNewUserButton();
    }

    @Test
    @Order(8)
    public void verifyNewUserSaved_Expected6(IJUnitTestReporter testReporter) {
        NDPViewUserPage viewUserPage = browser.getPage(NDPViewUserPage.class, testReporter);
        assertAll(() -> assertEquals("Your request completed successfully.", viewUserPage.getSuccessAlert(), TextConstants.messageCorrect),
                () -> assertEquals(firstName, viewUserPage.getFirstName(), "The first name is displayed correct"),
                () -> assertEquals(lastName, viewUserPage.getLastName(), "The last name is displayed correct"),
                () -> assertEquals(nickName, viewUserPage.getNickName(), "The nick name is displayed correct"),
                () -> assertEquals(ndpUserAdd.getUserName(), viewUserPage.getNintendoID(), "The nintendoID  is displayed correct"),
                () -> assertEquals(ndpUserAdd.getUserName() + TextConstants.inbucket_Suffix, viewUserPage.getEmailAddress(), "The email address is displayed correct"));
    }

    @Test
    @Order(9)
    public void navigateToUserManagement_Step7(IJUnitTestReporter testReporter) {
        NDPViewUserPage viewUserPage = browser.getPage(NDPViewUserPage.class, testReporter);
        viewUserPage.clickBackToUserManagementPage();
    }

    @Test
    @Order(10)
    public void clickOnSearchButton_Step8(IJUnitTestReporter testReporter) {
        NDPUserManagementPage userManagementPage = browser.getPage(NDPUserManagementPage.class, testReporter);
        userManagementPage.clickSearchIcon();
    }

    @Test
    @Order(11)
    public void verifyUserListIsDisplayed_Expected8(IJUnitTestReporter testReporter) {
        NDPUserManagementPage userManagementPage = browser.getPage(NDPUserManagementPage.class, testReporter);
        assertTrue(userManagementPage.isUserListTableDisplayed(), "User list is displayed");
    }

    @Test
    @Order(12)
    public void clickAnExistingUser_Step9(IJUnitTestReporter testReporter) {
        NDPUserManagementPage userManagementPage = browser.getPage(NDPUserManagementPage.class, testReporter);
        userManagementPage.searchByKeyword(ndpUserAdd.getUserName())
                .selectCompanyUserByNDID(ndpUserAdd.getUserName());
    }

    @Test
    @Order(13)
    public void clickEditProfile_Step10(IJUnitTestReporter testReporter) {
        NDPViewUserPage viewUserPage = browser.getPage(NDPViewUserPage.class, testReporter);
        viewUserPage.clickEditProfileButton();
    }

    @Test
    @Order(14)
    public void updateFirstName_Step11(IJUnitTestReporter testReporter) {
        NDPViewUserPage viewUserPage = browser.getPage(NDPViewUserPage.class, testReporter);
        firstName = firstName + "_1";
        viewUserPage.enterFirstName(firstName);
    }

    @Test
    @Order(15)
    public void updateLastName_Step12(IJUnitTestReporter testReporter) {
        NDPViewUserPage viewUserPage = browser.getPage(NDPViewUserPage.class, testReporter);
        lastName = lastName + "_1";
        viewUserPage.enterLastName(lastName);
    }

    @Test
    @Order(16)
    public void updateNickName_Step13(IJUnitTestReporter testReporter) {
        NDPViewUserPage viewUserPage = browser.getPage(NDPViewUserPage.class, testReporter);
        nickName = nickName + "_1";
        viewUserPage.enterNickName(nickName);
    }

    @Test
    @Order(17)
    public void updateJobTitle_Step14(IJUnitTestReporter testReporter) {
        NDPViewUserPage viewUserPage = browser.getPage(NDPViewUserPage.class, testReporter);
        viewUserPage.enterJobTitle("JobTitle");
    }

    @Test
    @Order(18)
    public void updatePreferences_Step15(IJUnitTestReporter testReporter) {
        NDPViewUserPage viewUserPage = browser.getPage(NDPViewUserPage.class, testReporter);
        viewUserPage.selectTimezone("(UTC -04:00) Eastern Daylight Time")
                .selectLanguage(" English (United States) ");
    }

    @Test
    @Order(19)
    public void updatePermissions_Step16(IJUnitTestReporter testReporter) {
        NDPViewUserPage viewUserPage = browser.getPage(NDPViewUserPage.class, testReporter);
        viewUserPage.selectCompany3DSUser()
                .selectCompanyWiiUUser();
    }

    @Test
    @Order(20)
    public void clickSaveChangesButton_Step17(IJUnitTestReporter testReporter) {
        NDPViewUserPage viewUserPage = browser.getPage(NDPViewUserPage.class, testReporter);
        viewUserPage.clickSaveChangesButton();
    }

    @Test
    @Order(21)
    public void verifyAfterChangesSaved_Expected17(IJUnitTestReporter testReporter) {
        NDPViewUserPage viewUserPage = browser.getPage(NDPViewUserPage.class, testReporter);
        assertAll(() -> assertEquals("Your request completed successfully.", viewUserPage.getSuccessAlert(), TextConstants.messageCorrect),
                () -> assertEquals(firstName, viewUserPage.getFirstName(), "The first name is displayed correct"),
                () -> assertEquals(lastName, viewUserPage.getLastName(), "The last name is displayed correct"),
                () -> assertEquals(nickName, viewUserPage.getNickName(), "The nick name is displayed correct"),
                () -> assertEquals(ndpUserAdd.getUserName(), viewUserPage.getNintendoID(), "The nintendoID  is displayed correct"),
                () -> assertEquals(ndpUserAdd.getUserName() + TextConstants.inbucket_Suffix, viewUserPage.getEmailAddress(), "The email address is displayed correct"),
                () -> assertEquals("JobTitle", viewUserPage.getJobTitle(), "The email address is displayed correct"));
    }

    @Test
    @Order(22)
    public void loginNDPAsCompanyUserJustCreated_Step18(IJUnitTestReporter testReporter) {
        ManagementSystemLogin managementSystemLogin = browser.openURL(ManagementSystemLogin.class, PageState.PRE_LOGIN, testReporter);
        managementSystemLogin.signIn(adminUser)
                .clickManageUsers()
                .enterNintendoDeveloperID(ndpUserAdd.getUserName())
                .clickSearchButton()
                .clickUserNameInTableByNintendoID(ndpUserAdd.getUserName())
                .clickChangePassword()
                .enterYourPassword(ndpUserAdd.getPassword())
                .enterNewPassword(ndpUserAdd.getPassword())
                .enterConfirmNewPassword(ndpUserAdd.getPassword())
                .selectOkButton();
        NDPDevelopmentHome developmentHome = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter);
        developmentHome.clickUserMenuDropdownList()
                .clickSignOutButton()
                .clickSignInPage()
                .signInNewlyAddUser(ndpUserAdd, true);
    }

    @Test
    @Order(23)
    public void createSomePostOnForum_Step19(IJUnitTestReporter testReporter) {
        NDPDevelopmentHome developmentHome = browser.getPage(NDPDevelopmentHome.class, testReporter);
        NDPNintendo3DSForumsEnglishSupportPage forumsEnglishSupportPage = developmentHome.clickDeveloperSupportTab()
                .clickNintendo3DS()
                .selectForumsEnglishForNintendo3DS();
        int i = 0;
        while (i < 5) {
            browser.refresh();
            if (forumsEnglishSupportPage.isCreateThreadButtonVisible()) {
                break;
            }
            i++;
        }
        forumsEnglishSupportPage.selectCreateThread()
                .enterSubject(subject)
                .enterDevelopmentEnvironment("Development")
                .enterComment("Comment for Development Environment")
                .clickOkButton();
        assertEquals("Your request completed successfully.", forumsEnglishSupportPage.getSuccessAlert(), TextConstants.messageCorrect);
    }

    @Test
    @Order(24)
    public void navigateToMyAccountPage_Step20(IJUnitTestReporter testReporter) {
        NDPDevelopmentHome developmentHome = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter);
        developmentHome.clickUserMenuDropdownList()
                .clickManageAccount();
    }

    @Test
    @Order(25)
    public void updateAvatar_Step21(IJUnitTestReporter testReporter) {
        NDPMyAccountPage myAccountPage = browser.getPage(NDPMyAccountPage.class, testReporter);
        orgName = myAccountPage.getOrganizationName();
        myAccountPage.selectChangeAvatar()
                .enterAvatarFile(new File("src/test/resources/DataFile/Test_abc.jpg"))
                .selectSaveButton();
    }

    @Test
    @Order(26)
    public void selectMenuIcon_Step22(IJUnitTestReporter testReporter) {
        NDPMyAccountPage myAccountPage = browser.getPage(NDPMyAccountPage.class, testReporter);
        myAccountPage.selectMenuIcon();
    }

    @Test
    @Order(27)
    public void verifyTheAdvancedOptions_Expected22(IJUnitTestReporter testReporter) {
        NDPMyAccountPage myAccountPage = browser.getPage(NDPMyAccountPage.class, testReporter);
        assertAll("Only displayed Edit My Profile option",
                () -> assertTrue(myAccountPage.isEditProfileOptionDisplayed()),
                () -> assertFalse(myAccountPage.isRequestMyInfoOptionDisplayed()),
                () -> assertFalse(myAccountPage.isDeleteUserOptionDisplayed()));
    }

    @Test
    @Order(28)
    public void selectEditProfileOptions_Step23(IJUnitTestReporter testReporter) {
        NDPMyAccountPage myAccountPage = browser.getPage(NDPMyAccountPage.class, testReporter);
        myAccountPage.selectEditProfileOption();
    }

    @Test
    @Order(29)
    public void updateSomeFieldsAndSave_Step24(IJUnitTestReporter testReporter) {
        NDPMyAccountPage myAccountPage = browser.getPage(NDPMyAccountPage.class, testReporter);
        myAccountPage.enterFirstName(firstName + "_2")
                .enterLastName(lastName + "_2")
                .enterNickName(nickName + "_2")
                .clickSaveChangesButton();
    }

    @Test
    @Order(30)
    public void verifyUpdateInfoSuccessfully_Expected24(IJUnitTestReporter testReporter) {
        NDPMyAccountPage myAccountPage = browser.getPage(NDPMyAccountPage.class, testReporter);
        assertAll(() -> assertEquals("Your request completed successfully.", myAccountPage.getSuccessAlert(), TextConstants.messageCorrect),
                () -> assertEquals(firstName + "_2", myAccountPage.getFirstName(), "The first name is displayed correct"),
                () -> assertEquals(lastName + "_2", myAccountPage.getLastName(), "The last name is displayed correct"),
                () -> assertEquals(nickName + "_2", myAccountPage.getNickName(), "The nick name is displayed correct"));
    }

    @Test
    @Order(31)
    public void loginNDPByRegularUserAndViewPost_Step25(IJUnitTestReporter testReporter) {
        NDPDevelopmentHome developmentHome = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter);
        developmentHome.clickUserMenuDropdownList()
                .clickSignOutButton()
                .clickSignInPage()
                .signIn(ndpUser);
        NDPNintendo3DSForumsEnglishSupportPage forumsEnglishSupportPage = developmentHome.clickDeveloperSupportTab()
                .clickNintendo3DS()
                .selectForumsEnglishForNintendo3DS();
        int i = 0;
        while (i < 5) {
            browser.refresh();
            if (forumsEnglishSupportPage.isCreateThreadButtonVisible()) {
                break;
            }
            i++;
        }
        forumsEnglishSupportPage.selectPostBySubject(subject);
    }

    @Test
    @Order(32)
    public void verifyInfoOfPostByRegularUser_Expected25(IJUnitTestReporter testReporter) {
        NDPNintendo3DSForumsEnglishSupportPage forumsEnglishSupportPage = browser.getPage(NDPNintendo3DSForumsEnglishSupportPage.class, testReporter);
        boolean isImageDisplayedCorrect = forumsEnglishSupportPage.getSRCImage().contains("gts-message-boards-portlet/images/default_unread");
        assertAll(() -> assertEquals(nickName, forumsEnglishSupportPage.getNickNameOnPost(), "Nick name will not update and still display as normal"),
                () -> assertTrue(isImageDisplayedCorrect, "Avatar will not update and still display as normal"),
                () -> assertEquals("1", forumsEnglishSupportPage.getNumberOfPosts(), "Number of Posts will displayed correct"));
    }

    @Test
    @Order(33)
    public void loginNDPByGPTExternalSupportUserAndViewPost_Step26(IJUnitTestReporter testReporter) {
        NDPDevelopmentHome developmentHome = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter);
        developmentHome.clickUserMenuDropdownList()
                .clickSignOutButton()
                .clickSignInPage()
                .signIn(gptExternalSupportUser);
        NDPNintendo3DSForumsEnglishSupportPage forumsEnglishSupportPage = developmentHome.clickDeveloperSupportTab()
                .clickNintendo3DS()
                .selectForumsEnglishForNintendo3DS();
        int i = 0;
        while (i < 5) {
            browser.refresh();
            if (forumsEnglishSupportPage.isCreateThreadButtonVisible()) {
                break;
            }
            i++;
        }
        forumsEnglishSupportPage.selectPostBySubject(subject);
    }

    @Test
    @Order(34)
    public void verifyInfoOfPostByGPTExternalSupportUser_Expected26(IJUnitTestReporter testReporter) {
        NDPNintendo3DSForumsEnglishSupportPage forumsEnglishSupportPage = browser.getPage(NDPNintendo3DSForumsEnglishSupportPage.class, testReporter);
        boolean isImageDisplayedCorrect = forumsEnglishSupportPage.getSRCImage().contains("gts-message-boards-portlet/images/default_unread");
        assertAll(() -> assertEquals(nickName, forumsEnglishSupportPage.getNickNameOnPost(), "Nick name will not update and still display as normal"),
                () -> assertEquals(firstName + " " + lastName + " / " + orgName, forumsEnglishSupportPage.getNameOrganization(), "Full name and Organization will not update and still display as normal"),
                () -> assertTrue(isImageDisplayedCorrect, "Avatar will not update and still display as normal"),
                () -> assertEquals("1", forumsEnglishSupportPage.getNumberOfPosts(), "Number of Posts will displayed corect"));
    }

    @Test
    @Order(35)
    public void loginNDPAsCompanyAdminUser_Step27(IJUnitTestReporter testReporter) {
        NDPDevelopmentHome developmentHome = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter);
        developmentHome.clickUserMenuDropdownList()
                .clickSignOutButton()
                .clickSignInPage()
                .signIn(ndpUser);
    }

    @Test
    @Order(36)
    public void navigateToMyAccountPageAndSelectMenuAction_Step28(IJUnitTestReporter testReporter) {
        NDPDevelopmentHome developmentHome = browser.getPage(NDPDevelopmentHome.class, testReporter);
        developmentHome.clickUserMenuDropdownList()
                .clickManageAccount().selectMenuIcon();
    }

    @Test
    @Order(37)
    public void verifyTheAdvancedOptions_Expected28(IJUnitTestReporter testReporter) {
        NDPMyAccountPage myAccountPage = browser.getPage(NDPMyAccountPage.class, testReporter);
        assertAll("Only displayed Edit My Profile option",
                () -> assertTrue(myAccountPage.isEditProfileOptionDisplayed()),
                () -> assertFalse(myAccountPage.isRequestMyInfoOptionDisplayed()),
                () -> assertFalse(myAccountPage.isDeleteUserOptionDisplayed()));
    }
}
