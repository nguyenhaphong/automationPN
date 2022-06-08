package net.nintendo.automation.TestCase;


import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.junit.TestLogger;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.data.DataGen;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPSignInPage;
import net.nintendo.automation.ui.models.ndp.admin.NDPAdminPage;
import net.nintendo.automation.ui.models.ndp.admin.NDPUserGroup;
import net.nintendo.automation.ui.models.ndp.admin.NDPUserManagementPage;
import net.nintendo.automation.ui.models.ndp.admin.NDPViewUserPage;
import net.nintendo.automation_core.extent_reports.extensions.IJUnitTestReporter;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classname: T3404501
 * Version: 1.0.0
 * Purpose:
 */
@Tag("T3404501")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3404501 extends CommonBaseTest {
    private static CommonUIComponent currentPage;
    private static Browser browser;
    private static String userGroupName;
    private static TestLogger logger;


    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        NDPHomePage ndpHomePage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
        ndpHomePage.acceptCookie();
        NDPSignInPage signInPage = ndpHomePage.clickSignInPage();
        signInPage.signIn(TextConstants.partner_Hiendt, TextConstants.nintendo_approval_Password);
        userGroupName = DataGen.getRandomProductName();
    }

    @AfterAll
    static void terminateBrowser() {
        BrowserManager.closeBrowser(browser);
    }

    @BeforeEach
    public void each(IJUnitTestReporter testReporter) {
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
    }

    @Test
    @Order(1)
    public void goToAddNewGroupUserWithCompanyAdmin_Step_1(IJUnitTestReporter testReporter) throws Exception {
        currentPage = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter);
        NDPDevelopmentHome developmentHome = (NDPDevelopmentHome) currentPage;
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        NDPAdminPage ndpAdminPage = developmentHome.navTopNavigation().clickAdmin();
        NDPUserGroup ndpUserGroup = ndpAdminPage.clickNdpUserGroup();
        currentPage = ndpUserGroup.clickAddNewGroup();
    }

    @Test
    @Order(2)
    public void verifyAddNewUserGroupPageVisible_Expected_1(IJUnitTestReporter testReporter) throws Exception {
        NDPUserGroup ndpUserGroup = (NDPUserGroup) currentPage;
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        assertTrue(ndpUserGroup.isAddNewGroupDisplay());
    }

    @Test
    @Order(3)
    public void createNewUserGroup_Step_2(IJUnitTestReporter testReporter) throws Exception {
        NDPUserGroup ndpUserGroup = (NDPUserGroup) currentPage;
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        ndpUserGroup.enterUserGroupName(userGroupName);
        ndpUserGroup.enterUserGroupDescription("Enter group description");
        ndpUserGroup.clickSaveNewGroup();
    }

    @Test
    @Order(4)
    public void verifyNewGroupCreated_Expected_2(IJUnitTestReporter testReporter) throws Exception {
        NDPUserGroup ndpUserGroup = (NDPUserGroup) currentPage;
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        assertTrue(ndpUserGroup.isNewGroupCreatedDisplay());
    }

    @Test
    @Order(5)
    public void goToUserManagementWithCompanyManager_Step_3(IJUnitTestReporter testReporter) throws Exception {
        currentPage = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter);
        NDPDevelopmentHome developmentHome = (NDPDevelopmentHome) currentPage;
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        NDPAdminPage ndpAdminPage = developmentHome.navTopNavigation().clickAdmin();
        currentPage = ndpAdminPage.clickUserManagementMenu();
    }

    @Test
    @Order(6)
    public void searchLockUserByEmail_Step_4(IJUnitTestReporter testReporter) throws Exception {
        NDPUserManagementPage ndpUserManagementPage = (NDPUserManagementPage) currentPage;
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        logger.logInfo("Search User locked by email");
        ndpUserManagementPage.searchLockUserByEmail(TextConstants.email_LockUser);
    }

    @Test
    @Order(7)
    public void verifyUserDisplay_Expected_4(IJUnitTestReporter testReporter) throws Exception {
        NDPUserManagementPage ndpUserManagementPage = (NDPUserManagementPage) currentPage;
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        assertTrue(ndpUserManagementPage.verifySearchUserDisplay());
    }

    @Test
    @Order(8)
    public void selectLockedUser_Step_5(IJUnitTestReporter testReporter) throws Exception {
        NDPUserManagementPage ndpUserManagementPage = (NDPUserManagementPage) currentPage;
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        logger.logInfo("Search User locked by email");
        ndpUserManagementPage.searchLockUserByEmail(TextConstants.email_LockUser);
        currentPage = ndpUserManagementPage.clickViewUser();
    }

    @Test
    @Order(9)
    public void verifyViewUser_Expected_5(IJUnitTestReporter testReporter) throws Exception {
        NDPViewUserPage ndpViewUserPage = (NDPViewUserPage) currentPage;
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        ndpViewUserPage.viewUserPageDisplayNormal();
    }

    @Test
    @Order(10)
    public void verifyEditAvatarInvisible_Expected_6(IJUnitTestReporter testReporter) throws Exception {
        NDPViewUserPage ndpViewUserPage = (NDPViewUserPage) currentPage;
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        assertTrue(ndpViewUserPage.verifyEditAvatarInvisible());
    }

    @Test
    @Order(11)
    public void verifyDeleteAvatarInvisible_Expected_7(IJUnitTestReporter testReporter) throws Exception {
        NDPViewUserPage ndpViewUserPage = (NDPViewUserPage) currentPage;
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        assertTrue(ndpViewUserPage.verifyDeleteAvatarInvisible());
    }

    @Test
    @Order(12)
    public void verifyEditProfileInvisible_Expected_8(IJUnitTestReporter testReporter) throws Exception {
        NDPViewUserPage ndpViewUserPage = (NDPViewUserPage) currentPage;
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        assertTrue(ndpViewUserPage.verifyEditProfileInvisible());
    }

    @Test
    @Order(13)
    public void clickUserOptionIcon_Step_9(IJUnitTestReporter testReporter) {
        NDPViewUserPage ndpViewUserPage = (NDPViewUserPage) currentPage;
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        ndpViewUserPage.clickUserOptionIcon();
    }

    @Test
    @Order(14)
    public void verifyUnlockUserOptionDisplay_Expected_9(IJUnitTestReporter testReporter) throws Exception {
        NDPViewUserPage ndpViewUserPage = (NDPViewUserPage) currentPage;
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        assertTrue(ndpViewUserPage.verifyUnlockUserOption());
    }

    @Test
    @Order(15)
    public void selectOptionUnlockUser_Step_10(IJUnitTestReporter testReporter) throws Exception {
        NDPViewUserPage ndpViewUserPage = (NDPViewUserPage) currentPage;
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        ndpViewUserPage.selectUnlockUserOption();
    }

    @Test
    @Order(16)
    public void verifyPopupConfirmUnlockUser_Expected_10(IJUnitTestReporter testReporter) throws Exception {
        NDPViewUserPage ndpViewUserPage = (NDPViewUserPage) currentPage;
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        assertTrue(ndpViewUserPage.verifyConfirmUnlockUser());
        assertTrue(ndpViewUserPage.verifyMessageText());
    }

    @Test
    @Order(17)
    public void selectUnlockUser_Step_11(IJUnitTestReporter testReporter) throws Exception {
        NDPViewUserPage ndpViewUserPage = (NDPViewUserPage) currentPage;
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        ndpViewUserPage.confirmUnlockUser();
    }

    @Test
    @Order(18)
    public void verifyUnlockUser_Expected_11(IJUnitTestReporter testReporter) throws Exception {
        NDPViewUserPage ndpViewUserPage = (NDPViewUserPage) currentPage;
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        assertTrue(ndpViewUserPage.verifyUnlockUser());
    }

    @Test
    @Order(19)
    public void clickUserOptionIcon_Step_12(IJUnitTestReporter testReporter) {
        NDPViewUserPage ndpViewUserPage = (NDPViewUserPage) currentPage;
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        ndpViewUserPage.clickUserOptionIcon();
    }

    @Test
    @Order(20)
    public void verifyUserOption_Expected_12(IJUnitTestReporter testReporter) throws Exception {
        NDPViewUserPage ndpViewUserPage = (NDPViewUserPage) currentPage;
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        assertTrue(ndpViewUserPage.verifyUserOption());
    }

    @Test
    @Order(21)
    public void goToLisUserInOrganization_Step_13(IJUnitTestReporter testReporter) {
        NDPViewUserPage ndpViewUserPage = (NDPViewUserPage) currentPage;
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        currentPage = ndpViewUserPage.clickViewUnlockUserInList();
    }

    @Test
    @Order(22)
    public void verifyStatusUnlockedUser_Expected_13(IJUnitTestReporter testReporter) throws Exception {
        NDPUserManagementPage ndpUserManagementPage = (NDPUserManagementPage) currentPage;
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        logger.logInfo("Search User unlocked by email");
        ndpUserManagementPage.searchUserUnlockedByEmail(TextConstants.email_LockUser);
        ndpUserManagementPage.verifyStatusUnlockedUser();
    }

    @Test
    @Order(23)
    public void loginNDPByUnlockUser_Step_14(IJUnitTestReporter testReporter) throws Exception {
        currentPage = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN);
        NDPDevelopmentHome ndpPage = (NDPDevelopmentHome) currentPage;
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        NDPHomePage ndpHomePage = ndpPage.clickMenuAccount().clickLogout();
        NDPSignInPage signInPage = ndpHomePage.clickSignInPage();
        logger.logInfo("Login NDP with user unlocked");
        signInPage.signIn(TextConstants.partner_Unlocked, TextConstants.nintendo_approval_Password);
    }

    @Test
    @Order(24)
    public void unlockedUserLoginNDP_Expected_14(IJUnitTestReporter testReporter) throws Exception {
        currentPage = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN);
        NDPDevelopmentHome ndpPage = (NDPDevelopmentHome) currentPage;
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        logger.logInfo("Login NDP with user unlocked successful");
    }

    @Test
    @Order(25)
    public void viewUnlockedUserByOtherCompanyAdmin_Step_15(IJUnitTestReporter testReporter) throws Exception {
        currentPage = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN);
        NDPDevelopmentHome ndpPage = (NDPDevelopmentHome) currentPage;
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        NDPHomePage ndpHomePage = ndpPage.clickMenuAccount().clickLogout();
        NDPSignInPage signInPage = ndpHomePage.clickSignInPage();
        logger.logInfo("Login With other Company Admin and View user unlocked");
        signInPage.signIn(TextConstants.partner_CompanyAdmin, TextConstants.nintendo_approval_Password);
        NDPDevelopmentHome developmentHome = browser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReporter);
        NDPAdminPage ndpAdminPage = developmentHome.navTopNavigation().clickAdmin();
        NDPUserManagementPage ndpUserManagementPage = ndpAdminPage.clickUserManagementMenu();
        ndpUserManagementPage.searchLockUserByEmail(TextConstants.email_LockUser);
        currentPage = ndpUserManagementPage.clickViewUser();
    }

    @Test
    @Order(26)
    public void verifyViewUser_Expected_15(IJUnitTestReporter testReporter) throws Exception {
        NDPViewUserPage ndpViewUserPage = (NDPViewUserPage) currentPage;
        logger = new TestLogger(testReporter);
        currentPage.setExtentLogger(logger);
        ndpViewUserPage.viewUserPageDisplayNormal();
    }
}
