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
import net.nintendo.automation.ui.models.lcms.LotcheckHomePage;
import net.nintendo.automation.ui.models.lcms.LotcheckIssueTranslationTasksPage;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.ui.models.lcms.submission_reports.SaveFilterPopup;
import net.nintendo.automation.ui.models.lcms.submission_reports.SelectChooseColumns;
import net.nintendo.automation.ui.models.ndp.NDPChooseColumnModal;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPLicensingWaiverRequestsTab;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classname: T2597474
 * Version: 1.0.0
 * Purpose:
 */
@Tag("T2597474")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T2597474 extends CommonBaseTest {
    private static Browser browser;
    private static User romUser;
    private static User ndpUser;
    private static User slcmsUser;
    private static User testrailUser;
    private static User licUser;
    private static CommonUIComponent currentPage;
    private static TestLogger logger;
    private static String valueSearch;
    private static String filterName;
    private static String initialCode = "B876A";
    private static String gameCode = "HAC-P-B876A";

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_Quynhpnt2);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);
        slcmsUser = UserManager.get(ServiceType.SLCMS, UserType.ADMIN_USER);
        testrailUser = UserManager.get(ServiceType.PMMS, UserType.TRANSLATION_USER, TextConstants.trAdminNCL_EN);
        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
    }

    @AfterAll
    static void terminateBrowser() {
        BrowserManager.closeBrowser(browser);
        UserManager.releaseUser(ServiceType.NDP, ndpUser);
        UserManager.releaseUser(ServiceType.SLCMS, slcmsUser);
        UserManager.releaseUser(ServiceType.SEARCH, romUser);
        UserManager.releaseUser(ServiceType.NDP, licUser);
        UserManager.releaseUser(ServiceType.PMMS, testrailUser);
    }

    @BeforeEach
    public void each(IJUnitTestReporter testReport) {
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
    }

    @Test()
    @Order(1)
    public void licUserSearchUsingThe5DigitUniqueCode_Step(IJUnitTestReporter testReport) {
        NDPHomePage homePage = (NDPHomePage) currentPage;
        NDPDevelopmentHome developmentHome = homePage.acceptCookie().clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        NDPChooseColumnModal chooseColumnModal = licensingWaiverRequestsTab.clickChooseColumn();
        int columnCheck = chooseColumnModal.collectNumberColumnSelect();
        for (int i = 1; i <= 30 - columnCheck; i++) {
            chooseColumnModal.checkColumn(columnCheck + i);
        }
        chooseColumnModal.clickSelectColumnsButton();
        licensingWaiverRequestsTab.waitingForResultTableLoaded()
                .enterSearchingBox(initialCode)
                .clickOnMagnifyingIconButton()
                .waitingForResultTableLoaded()
                .selectShowItemsPerPage("All")
                .waitingForResultTableLoaded();
        int numberValue = licensingWaiverRequestsTab.collectNumberOfRequestOnTable();
        for (int j = 0; j < numberValue; j++) {
            int z = 1;
            while (z <= 30) {
                valueSearch = licensingWaiverRequestsTab.getCompanyAtTable(j + 1, z).toUpperCase();
                z++;
                if (licensingWaiverRequestsTab.comparativeValue(valueSearch, initialCode) == true) {
                    break;
                }
            }
            assertAll(() -> assertTrue(licensingWaiverRequestsTab.comparativeValue(valueSearch, initialCode), "match with game code, product name"));
        }
        currentPage = licensingWaiverRequestsTab;
    }

    @Test()
    @Order(2)
    public void licUserSearchUsingThe4DigitUniqueCode_Step(IJUnitTestReporter testReport) {
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = (NDPLicensingWaiverRequestsTab) currentPage;
        for (int i = 0; i <= 1; i++) {
            String initialCode4Digit = initialCode.substring(i, i + 4);
            licensingWaiverRequestsTab.waitingForResultTableLoaded()
                    .enterSearchingBox(initialCode4Digit)
                    .clickOnMagnifyingIconButton()
                    .waitingForResultTableLoaded()
                    .selectShowItemsPerPage("All")
                    .waitingForResultTableLoaded();
            int numberValue = licensingWaiverRequestsTab.collectNumberOfRequestOnTable();
            for (int j = 0; j < numberValue; j++) {
                int z = 1;
                while (z <= 30) {
                    valueSearch = licensingWaiverRequestsTab.getCompanyAtTable(j + 1, z).toUpperCase();
                    z++;
                    if (licensingWaiverRequestsTab.comparativeValue(valueSearch, initialCode) == true) {
                        break;
                    }
                }
                assertAll(() -> assertTrue(licensingWaiverRequestsTab.comparativeValue(valueSearch, initialCode), "match with game code, product name"));
            }
        }
        currentPage = licensingWaiverRequestsTab;
    }

    @Test()
    @Order(3)
    public void licUserSearchUsingThe3DigitUniqueCode_Step(IJUnitTestReporter testReport) {
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = (NDPLicensingWaiverRequestsTab) currentPage;
        for (int i = 0; i <= 2; i++) {
            String initialCode3Digit = initialCode.substring(i, i + 3);
            licensingWaiverRequestsTab.waitingForResultTableLoaded()
                    .enterSearchingBox(initialCode3Digit)
                    .clickOnMagnifyingIconButton()
                    .waitingForResultTableLoaded()
                    .selectShowItemsPerPage("All")
                    .waitingForResultTableLoaded();
            int numberValue = licensingWaiverRequestsTab.collectNumberOfRequestOnTable();
            for (int j = 0; j < numberValue; j++) {
                int z = 1;
                while (z <= 30) {
                    valueSearch = licensingWaiverRequestsTab.getCompanyAtTable(j + 1, z).toUpperCase();
                    z++;
                    if (licensingWaiverRequestsTab.comparativeValue(valueSearch, initialCode3Digit) == true) {
                        break;
                    }
                }
                assertAll(() -> assertTrue(licensingWaiverRequestsTab.comparativeValue(valueSearch, initialCode3Digit), "match with game code, product name"));
            }
        }
        currentPage = licensingWaiverRequestsTab;
    }

    @Test()
    @Order(4)
    public void licUserSearchUsingThe7DigitUniqueCode_Step(IJUnitTestReporter testReport) {
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = (NDPLicensingWaiverRequestsTab) currentPage;
        for (int i = 0; i <= 4; i++) {
            String gameCode7Digit = gameCode.substring(i, i + 7);
            String firstCharacter = gameCode7Digit.substring(0, 1);
            String gameCodeConvertPToU = gameCode7Digit.replace("-P", "-U");
            String gameCodeConvertPToM = gameCode7Digit.replace("-P", "-M");
            licensingWaiverRequestsTab.waitingForResultTableLoaded()
                    .enterSearchingBox(gameCode7Digit)
                    .clickOnMagnifyingIconButton()
                    .waitingForResultTableLoaded();
            if (!firstCharacter.equals("-")) {
                licensingWaiverRequestsTab.selectShowItemsPerPage("All")
                        .waitingForResultTableLoaded();
                int numberValue = licensingWaiverRequestsTab.collectNumberOfRequestOnTable();
                for (int j = 0; j < numberValue; j++) {
                    int z = 1;
                    while (z <= 30) {
                        valueSearch = licensingWaiverRequestsTab.getCompanyAtTable(j + 1, z).toUpperCase();
                        z++;
                        if (licensingWaiverRequestsTab.comparativeValue(valueSearch, gameCode7Digit) == true) {
                            break;
                        } else if (licensingWaiverRequestsTab.comparativeValue(valueSearch, gameCodeConvertPToU) == true) {
                            break;
                        } else if (licensingWaiverRequestsTab.comparativeValue(valueSearch, gameCodeConvertPToM) == true) {
                            break;
                        }
                    }
                }
            } else {
                String value = licensingWaiverRequestsTab.getNoRecordsFounds();
                assertAll(() -> assertEquals(value, "No records founds"));
            }
        }
        currentPage = licensingWaiverRequestsTab;
    }

    @Test()
    @Order(5)
    public void licUserSearchUsingTheFullGameCode_Step(IJUnitTestReporter testReport) {
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = (NDPLicensingWaiverRequestsTab) currentPage;
        licensingWaiverRequestsTab.waitingForResultTableLoaded()
                .enterSearchingBox(gameCode)
                .clickOnMagnifyingIconButton()
                .waitingForResultTableLoaded()
                .selectShowItemsPerPage("All")
                .waitingForResultTableLoaded();
        int numberValue = licensingWaiverRequestsTab.collectNumberOfRequestOnTable();
        for (int i = 0; i < numberValue; i++) {
            int z = 1;
            while (z <= 30) {
                valueSearch = licensingWaiverRequestsTab.getCompanyAtTable(i + 1, z).toUpperCase();
                z++;
                if (licensingWaiverRequestsTab.comparativeValue(valueSearch, gameCode) == true) {
                    break;
                }
            }
            assertAll(() -> assertTrue(licensingWaiverRequestsTab.comparativeValue(valueSearch, gameCode), "match with game code, product name"));
        }
    }

    //------------------------------------------------------------------------------------------------
    @Test
    @Order(6)
    public void lcTranslatorSearcThe5DigitUniqueCode_Step(IJUnitTestReporter testReport) throws InterruptedException {
        filterName = DataGen.getSaveFilter();
        currentPage = browser.openURL(LotcheckHomePage.class, PageState.PRE_LOGIN);
        LotcheckHomePage lotcheckHomePage = (LotcheckHomePage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        HyruleAuthPage hyruleAuthPage = lotcheckHomePage.continueToLogin();
        LotcheckLandingPage lotcheckLandingPage = hyruleAuthPage.loginWithOvdLink(TextConstants.lc_Translator_NCL, TextConstants.lc_Translator_NCL_Password);
        LotcheckIssueTranslationTasksPage lotcheckIssueTranslationTasksPage = lotcheckLandingPage.navigationToAllTranslationTasks();
        if (lotcheckIssueTranslationTasksPage.displayColumn("Translation Task Type") == false) {
            lotcheckLandingPage.clickOnUserNameMenu().clickMyAccount().selectLanguage(TextConstants.english).clickSaveButton();
        }
        boolean value = lotcheckIssueTranslationTasksPage.displayColumn("Publisher");
        if (value == false) {
            SelectChooseColumns chooseColumns = lotcheckIssueTranslationTasksPage.clickActionMenu();
            WaitUtils.idle(1000);
            chooseColumns.selectPublisher().clickConfirmButton();
            SaveFilterPopup saveFilterPopup = lotcheckIssueTranslationTasksPage.clickSaveFilterMenu();
            saveFilterPopup.inputFilterName(filterName).selectSetAsDefault().clickSaveButton();
        }
        lotcheckIssueTranslationTasksPage.searchByInitialCodeProductNamePublisher(initialCode)
                .selectShowItemsPerPage("ALL rows");
        int numberValue = lotcheckIssueTranslationTasksPage.numberColumnValueOnTable();
        for (int j = 0; j < numberValue; j++) {
            String submission = lotcheckIssueTranslationTasksPage.getSubmissionColumnAtTable(j + 1).toUpperCase();
            if (lotcheckIssueTranslationTasksPage.comparativeValue(submission, initialCode) == false) {
                String publisher = lotcheckIssueTranslationTasksPage.getPublisherColumnAtTable(j + 1).toUpperCase();
                assertAll(() -> assertTrue(lotcheckIssueTranslationTasksPage.comparativeValue(publisher, initialCode)));
            }
            currentPage = lotcheckIssueTranslationTasksPage;
        }
    }

    @Test()
    @Order(7)
    public void lcTranslatorSearchThe4DigitUniqueCodeTheFirst_Step(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationTasksPage lotcheckIssueTranslationTasksPage = (LotcheckIssueTranslationTasksPage) currentPage;
        lotcheckIssueTranslationTasksPage.reloadPage();
        int i = 0;
        for (i = 0; i <= 1; i++) {
            String initialCode4Digit = initialCode.substring(i, i + 4);
            lotcheckIssueTranslationTasksPage.searchByInitialCodeProductNamePublisher(initialCode4Digit)
                    .selectShowItemsPerPage("ALL rows");
            int numberValue = lotcheckIssueTranslationTasksPage.numberColumnValueOnTable();
            for (int j = 0; j < numberValue; j++) {
                String submission = lotcheckIssueTranslationTasksPage.getSubmissionColumnAtTable(j + 1).toUpperCase();
                if (lotcheckIssueTranslationTasksPage.comparativeValue(submission, initialCode4Digit) == false) {
                    String publisher = lotcheckIssueTranslationTasksPage.getPublisherColumnAtTable(j + 1).toUpperCase();
                    assertAll(() -> assertTrue(lotcheckIssueTranslationTasksPage.comparativeValue(publisher, initialCode4Digit)));
                }
            }
            lotcheckIssueTranslationTasksPage.reloadPage();
        }
        currentPage = lotcheckIssueTranslationTasksPage;
    }

    @Test()
    @Order(8)
    public void lcTranslatorSearchUsingThe3DigitUniqueCode_Step(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationTasksPage lotcheckIssueTranslationTasksPage = (LotcheckIssueTranslationTasksPage) currentPage;
        for (int i = 0; i <= 2; i++) {
            String initialCode3Digit = initialCode.substring(i, i + 3);
            lotcheckIssueTranslationTasksPage.searchByInitialCodeProductNamePublisher(initialCode3Digit)
                    .selectShowItemsPerPage("ALL rows");
            int numberValue = lotcheckIssueTranslationTasksPage.numberColumnValueOnTable();
            for (int j = 0; j < numberValue; j++) {
                String submission = lotcheckIssueTranslationTasksPage.getSubmissionColumnAtTable(j + 1).toUpperCase();
                if (lotcheckIssueTranslationTasksPage.comparativeValue(submission, initialCode3Digit) == false) {
                    String publisher = lotcheckIssueTranslationTasksPage.getPublisherColumnAtTable(j + 1).toUpperCase();
                    assertAll(() -> assertTrue(lotcheckIssueTranslationTasksPage.comparativeValue(publisher, initialCode3Digit)));
                }
            }
            lotcheckIssueTranslationTasksPage.reloadPage();
        }
        currentPage = lotcheckIssueTranslationTasksPage;
    }

    @Test()
    @Order(9)
    public void lcTranslatorSearchUsingThe2DigitUniqueCodeTheFirst_Step(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationTasksPage lotcheckIssueTranslationTasksPage = (LotcheckIssueTranslationTasksPage) currentPage;
        for (int i = 0; i <= 3; i++) {
            String initialCode2Digit = initialCode.substring(i, i + 2);
            lotcheckIssueTranslationTasksPage.searchByInitialCodeProductNamePublisher(initialCode2Digit)
                    .selectShowItemsPerPage("ALL rows");
            int numberValue = lotcheckIssueTranslationTasksPage.numberColumnValueOnTable();
            for (int j = 0; j < numberValue; j++) {
                String submission = lotcheckIssueTranslationTasksPage.getSubmissionColumnAtTable(j + 1).toUpperCase();
                if (lotcheckIssueTranslationTasksPage.comparativeValue(submission, initialCode2Digit) == false) {
                    String publisher = lotcheckIssueTranslationTasksPage.getPublisherColumnAtTable(j + 1).toUpperCase();
                    assertAll(() -> assertTrue(lotcheckIssueTranslationTasksPage.comparativeValue(publisher, initialCode2Digit)));
                }
            }
            lotcheckIssueTranslationTasksPage.reloadPage();
        }
        currentPage = lotcheckIssueTranslationTasksPage;
    }

    @Test()
    @Order(10)
    public void lcTranslatorSearchUsingThe1DigitUniqueCode_Step(IJUnitTestReporter testReport) {
        LotcheckIssueTranslationTasksPage lotcheckIssueTranslationTasksPage = (LotcheckIssueTranslationTasksPage) currentPage;
        for (int i = 0; i <= 4; i++) {
            String initialCode1Digit = initialCode.substring(i, i + 1);
            lotcheckIssueTranslationTasksPage.searchByInitialCodeProductNamePublisher(initialCode1Digit)
                    .selectShowItemsPerPage("ALL rows");
            int numberValue = lotcheckIssueTranslationTasksPage.numberColumnValueOnTable();
            for (int j = 0; j < numberValue; j++) {
                String submission = lotcheckIssueTranslationTasksPage.getSubmissionColumnAtTable(j + 1).toUpperCase();
                if (lotcheckIssueTranslationTasksPage.comparativeValue(submission, initialCode1Digit) == false) {
                    String publisher = lotcheckIssueTranslationTasksPage.getPublisherColumnAtTable(j + 1).toUpperCase();
                    assertAll(() -> assertTrue(lotcheckIssueTranslationTasksPage.comparativeValue(publisher, initialCode1Digit)));
                }
            }
            lotcheckIssueTranslationTasksPage.reloadPage();
        }
        currentPage = lotcheckIssueTranslationTasksPage;
    }
}