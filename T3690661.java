package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.junit.TestLogger;
import net.nintendo.automation.junit.extensions.ScenarioTest;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.existing_internal.NDPInternalPage;
import net.nintendo.automation.ui.models.ndp.existing_internal.NDPSponsorQueuePage;
import net.nintendo.automation.ui.users.User;
import net.nintendo.automation.ui.users.UserManager;
import net.nintendo.automation.utils.WaitUtils;
import net.nintendo.automation_core.common_client.enums.ServiceType;
import net.nintendo.automation_core.common_client.enums.UserType;
import net.nintendo.automation_core.common_utils.enums.LotcheckRegion;
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

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Classname: T3690661
 * Version: 1.0.0
 * Purpose:
 */


@Tag("T3690661")
@ScenarioTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3690661 extends CommonBaseTest {
    private static Browser browser;
    private static User ndpUser;

    private static CommonUIComponent currentPage;
    private static TestLogger logger;

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_DungTest2);
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

    }

    @Test()
    @Order(1)
    public void loginNDP_step1(IJUnitTestReporter testReport) {
        NDPHomePage homePage = (NDPHomePage) currentPage;
        NDPDevelopmentHome developmentHome = homePage.acceptCookie().clickSignInPage().signIn(ndpUser);
        currentPage = developmentHome;
    }

    @Test()
    @Order(2)
    public void navigateInternal_SponsorQueue_step2(IJUnitTestReporter testReport) {
        NDPDevelopmentHome developmentHome = (NDPDevelopmentHome) currentPage;
        NDPInternalPage internalPage = developmentHome.clickInternalTab();
        NDPSponsorQueuePage sponsorQueuePage = internalPage.clickSponsorQueueButton();
        currentPage = sponsorQueuePage;
    }

    @Test()
    @Order(3)
    public void selectAdvanceSearch_step3(IJUnitTestReporter testReport) {
        NDPSponsorQueuePage sponsorQueuePage = (NDPSponsorQueuePage) currentPage;
        sponsorQueuePage.clickAdvanceSearch();
        currentPage = sponsorQueuePage;
    }

    @Test()
    @Order(4)
    public void selectDefaultFilter_step4(IJUnitTestReporter testReport) {
        NDPSponsorQueuePage sponsorQueuePage = (NDPSponsorQueuePage) currentPage;
        sponsorQueuePage.selectRunCriteriaOn(" Default Filter ");
        currentPage = sponsorQueuePage;
    }

    @Test()
    @Order(5)
    public void selectCheckboxInHighlighting_step5(IJUnitTestReporter testReport) {
        NDPSponsorQueuePage sponsorQueuePage = (NDPSponsorQueuePage) currentPage;
        sponsorQueuePage.selectTitlesInputtedSponsorComments().selectTitlesSponsorDepartmentBlockingWorkflow();
        currentPage = sponsorQueuePage;
    }

    @Test()
    @Order(6)
    public void selectProductType_step6(IJUnitTestReporter testReport) {
        NDPSponsorQueuePage sponsorQueuePage = (NDPSponsorQueuePage) currentPage;
        sponsorQueuePage.selectProductType(TextConstants.full_Product);
        currentPage = sponsorQueuePage;
    }

    @Test()
    @Order(7)
    public void selectLotcheckStatus_step7(IJUnitTestReporter testReport) {
        NDPSponsorQueuePage sponsorQueuePage = (NDPSponsorQueuePage) currentPage;
        sponsorQueuePage.selectLotcheckStatus(TextConstants.SUBMITTED);
        currentPage = sponsorQueuePage;
    }

    @Test()
    @Order(8)
    public void selectLotcheckOwner_step8(IJUnitTestReporter testReport) {
        NDPSponsorQueuePage sponsorQueuePage = (NDPSponsorQueuePage) currentPage;
        sponsorQueuePage.selectLotcheckOwner(LotcheckRegion.NCL.toString());
        currentPage = sponsorQueuePage;
    }

    @Test()
    @Order(9)
    public void clickSearch_step9(IJUnitTestReporter testReport) {
        NDPSponsorQueuePage sponsorQueuePage = (NDPSponsorQueuePage) currentPage;
        sponsorQueuePage.clickSearch();
        WaitUtils.idle(5000);
    }

    @Test()
    @Order(10)
    public void verifySearchResult_expect9(IJUnitTestReporter testReport) {// bug G3PTQASIT-3577 closed but not fix-> backlog
        NDPSponsorQueuePage sponsorQueuePage = (NDPSponsorQueuePage) currentPage;
        boolean noRecordMessageDisplay = sponsorQueuePage.recordIsDisplay();
        if (noRecordMessageDisplay) {
            sponsorQueuePage.showMessage();
        } else {
            sponsorQueuePage.addColumn(TextConstants.lotcheckOwner).addColumn("Product Type");
            int numberTesPlan = sponsorQueuePage.getNumberTestPlan();
            System.out.println("numberTesPlan = " + numberTesPlan);
            List<String> lstLotcheckOwner = sponsorQueuePage.getLotcheckOwner();
            List<String> lstProductType = sponsorQueuePage.getProductType();
            List<String> getSubmissionStatus = sponsorQueuePage.getSubmissionStatus();
            for (int i = 0; i < numberTesPlan; i++) {
                assertEquals(lstLotcheckOwner.get(i), "NCL", "Verify lotcheck owner at product " + i);
                assertEquals(lstProductType.get(i), TextConstants.full_Product, "Verify product Type at product " + i);
                assertEquals(getSubmissionStatus.get(i), "SUBMITTED", "Verify submission status at product " + i);

                sponsorQueuePage.clickTesPlan(i + 1);
                WaitUtils.idle(10000);
                String highLightReason = sponsorQueuePage.getReason();
                WaitUtils.idle(3000);
                sponsorQueuePage.clickClosePopup();
                System.out.println(highLightReason);
                assertThat("Verify at test plan name i",
                        highLightReason, anyOf(is(containsString("Title with sponsor comments")), is(containsString("This submission has pending issues"))));
            }
        }
    }
}
