package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.junit.TestLogger;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPSignInPage;
import net.nintendo.automation.ui.models.ndp.existing_internal.NDPInternalPage;
import net.nintendo.automation.ui.models.ndp.existing_internal.NDPUserManagementTabInInternalPage;
import net.nintendo.automation.utils.WaitUtils;
import net.nintendo.automation_core.extent_reports.extensions.IJUnitTestReporter;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Classname: T2597782
 * Version: 1.0.0
 * Purpose:
 */
@Tag("T2597782")
//@ScenarioTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T2597782 extends CommonBaseTest {
    private static Browser browser;
    private static CommonUIComponent currentPage;
    private static TestLogger logger;

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
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

    @Test
    @Order(1)
    public void loginAsLicensingPowerUserAndNavigateToUserManagement(IJUnitTestReporter testReport) {
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN, testReport);
        NDPHomePage ndpHomePage = (NDPHomePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpHomePage.acceptCookie();
        NDPSignInPage signInPage = ndpHomePage.clickSignInPage();
        NDPDevelopmentHome ndpDevelopmentHome = signInPage.signIn(TextConstants.lic_pwr_user_NCL, TextConstants.nintendo_approval_Password);
        NDPInternalPage ndpInternalPage = ndpDevelopmentHome.clickInternalTab();
        NDPUserManagementTabInInternalPage ndpUserManagementTabInInternalPage = ndpInternalPage.clickUserManagementTab();
        currentPage = ndpUserManagementTabInInternalPage;
    }

    @Test
    @Order(2)
    public void verifyUserSearchScreenDisplay(IJUnitTestReporter testReport) {
        NDPUserManagementTabInInternalPage ndpUserManagementTabInInternalPage = (NDPUserManagementTabInInternalPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpUserManagementTabInInternalPage.clickAdvancedButton();
        assertAll(() -> assertTrue(ndpUserManagementTabInInternalPage.titleDetailedUserSearchIsDisplayed(), "Verify Detailed User Search screen is displayed"),
                () -> assertTrue(ndpUserManagementTabInInternalPage.nintendoDeveloperIDFieldIsDisplayed(), "Verify Nintendo Developer ID field is displayed"),
                () -> assertTrue(ndpUserManagementTabInInternalPage.emailAddressFieldIsDisplayed(), "Verify Email Address field is displayed"));
        currentPage = ndpUserManagementTabInInternalPage;

    }

    @Test
    @Order(3)
    public void inputNintendoUserAndEmail(IJUnitTestReporter testReport) {
        NDPUserManagementTabInInternalPage ndpUserManagementTabInInternalPage = (NDPUserManagementTabInInternalPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpUserManagementTabInInternalPage.inputNintendoDeveloperID(TextConstants.partner_NguyenPhong)
                .inputEmailAddress(TextConstants.email_NguyenPhong)
                .clickSearchIconButtonInDetailUserSearch()
                .clickSearchIconButton();
        WaitUtils.idle(5000);
        currentPage = ndpUserManagementTabInInternalPage;
    }

    @Test
    @Order(4)
    public void confirmUserIDAndEmailUserSearchPage(IJUnitTestReporter testReport) {
        NDPUserManagementTabInInternalPage ndpUserManagementTabInInternalPage = (NDPUserManagementTabInInternalPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        assertAll(() -> assertEquals(TextConstants.partner_NguyenPhong, ndpUserManagementTabInInternalPage.getNintendoDeveloperID(), "Verify Nintendo Developer ID displayed correctly in search result"),
                () -> assertEquals(TextConstants.email_NguyenPhong, ndpUserManagementTabInInternalPage.getEmailAddress(), "Verify Email displayed correctly in search result"));
    }
}
