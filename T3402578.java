package net.nintendo.automation.TestCase;

import net.nintendo.automation.CommonBaseTest;
import net.nintendo.automation.browser.Browser;
import net.nintendo.automation.browser.BrowserManager;
import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.data.DataGen;
import net.nintendo.automation.ui.models.common.PageState;
import net.nintendo.automation.ui.models.ndp.NDPCreateProductPage;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPLicensingWaiverRequestsTab;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.ndp.NDPSignInPage;
import net.nintendo.automation.ui.models.ndp.common.CommonAction;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPAddUserModal;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardMembersTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardProductInfoTab;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardReleasesTab;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoAgeRating;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoFeaturesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoGuidelinesPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoIssuePage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoPage;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoROMUpload;
import net.nintendo.automation.ui.models.ndp.existing_release.NDPReleaseInfoReleaseListNav;
import net.nintendo.automation.ui.models.ndp.existing_release.dialogs.NDPHelpTroubleshooting;
import net.nintendo.automation.ui.models.rom.ROMCreateRom;
import net.nintendo.automation.ui.models.rom.ROMLoginPage;
import net.nintendo.automation.ui.users.User;
import net.nintendo.automation.ui.users.UserManager;
import net.nintendo.automation.utils.WaitUtils;
import net.nintendo.automation_core.common_client.enums.ServiceType;
import net.nintendo.automation_core.common_client.enums.UserType;
import net.nintendo.automation_core.common_utils.annotations.services.NDP;
import net.nintendo.automation_core.common_utils.annotations.services.SLCMS;
import net.nintendo.automation_core.extent_reports.extensions.IJUnitTestReporter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classname: T3402578
 * Version: 1.0.0
 * Purpose:
 */
@NDP
@SLCMS
@Tag("T3402578")
//@ScenarioTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3402578 extends CommonBaseTest {
    private static Browser ndpBrowser;
    private static User ndpUser;
    private static Browser ndpObserverBrowser;
    private static User ndpObserverUser;
    private static Browser licBrowser;
    private static User licUser;
    private static Browser romBrowser;
    private static User romUser;
    private static String productName;
    private static String savedGameCode;
    private static String userNameAdded;
    private static String fileRomName;

    @AfterAll
    static void terminateBrowser() {
        BrowserManager.closeBrowser(ndpBrowser);
        UserManager.releaseUser(ServiceType.NDP, ndpUser);
        BrowserManager.closeBrowser(ndpObserverBrowser);
        UserManager.releaseUser(ServiceType.NDP, ndpObserverUser);
        BrowserManager.closeBrowser(licBrowser);
        UserManager.releaseUser(ServiceType.NDP, licUser);
        BrowserManager.closeBrowser(romBrowser);
        UserManager.releaseUser(ServiceType.SEARCH, romUser);
    }

    @BeforeAll
    public static void initiateBrowser() {
        ndpBrowser = BrowserManager.getNewInstance();
        licBrowser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.ADMIN_USER);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approval_coord_NCL);

        NDPHomePage ndpHomePage = ndpBrowser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
        ndpHomePage.acceptCookie();
        NDPSignInPage signInPage = ndpHomePage.clickSignInPage();
        signInPage.signIn(ndpUser);
        productName = DataGen.getRandomProductName();

        NDPHomePage licHomePage = licBrowser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
        licHomePage.acceptCookie();
        NDPSignInPage licSignInPage = licHomePage.clickSignInPage();
        licSignInPage.signIn(licUser);
    }

    @Test
    @Order(1)
    public void createProduct(IJUnitTestReporter testReport) {
        NDPDevelopmentHome developmentHome = ndpBrowser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReport);
        NDPMyProductsPage myProductsPage = developmentHome.clickMyProducts();
        NDPCreateProductPage createProductPage = myProductsPage.createNewProduct();
        createProductPage.selectProductType(TextConstants.full_Product)
                .selectPublishingRelationship(TextConstants.third_Party_Type)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .selectDigitalAmericasSalesRegion()
                .selectProductName(productName)
                .clickCreateButton();
    }

    @Test
    @Order(2)
    public void issueGameCode(IJUnitTestReporter testReport) {
        NDPMyProductsPage myProductsPage = ndpBrowser.getPage(NDPMyProductsPage.class, testReport);
        NDPProductDashboardPage myProductDashBoard = myProductsPage.clickProductByName(productName);
        CommonAction.issueGameCode(myProductDashBoard, 60,"NO");
        savedGameCode = myProductDashBoard.clickProductInfo().getGameCode();
    }

    @Test
    @Order(3)
    public void addObserverUser(IJUnitTestReporter testReport) {
        NDPProductDashboardPage myProductDashBoard = ndpBrowser.getPage(NDPProductDashboardPage.class, testReport);
        NDPProductDashboardMembersTab productDashboardMembersTab = myProductDashBoard.clickMembersTab();
        NDPAddUserModal addUserModal = productDashboardMembersTab.selectAddUserButton();
        ndpObserverUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_AnhBTK);
        userNameAdded = ndpObserverUser.getUserName();
        addUserModal.selectMyCompanyUsers()
                .selectInternalUsers(userNameAdded)
                .selectAccessRole(TextConstants.observerUser)
                .clickAddUserButton();
        String verifySuccessNotification = productDashboardMembersTab.getSuccessNotificationText();
        String verifyUserName = productDashboardMembersTab.getUserNameText(2);
        String verifyAccessText = productDashboardMembersTab.getAccessText(2);
        System.out.println(verifyUserName);
        System.out.println(verifyAccessText);
        assertTrue(verifySuccessNotification.equalsIgnoreCase(TextConstants.addMemberSuccess));
        assertTrue(verifyUserName.equalsIgnoreCase(userNameAdded));
        assertTrue(verifyAccessText.equalsIgnoreCase(TextConstants.observerUser));
    }

    @Test
    @Order(4)
    public void openNewBrowser(IJUnitTestReporter testReporter) {
        ndpObserverBrowser = BrowserManager.getNewInstance();
        NDPHomePage ndpHomePage = ndpObserverBrowser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
    }

    @Test
    @Order(5)
    public void loginNDPAsObserverUser(IJUnitTestReporter testReporter) {
        NDPHomePage ndpHomePage = ndpObserverBrowser.getPage(NDPHomePage.class, testReporter);
        ndpHomePage.acceptCookie();
        NDPSignInPage signInPage = ndpHomePage.clickSignInPage();
        signInPage.signIn(ndpObserverUser).clickUserMenuDropdownList()
                .selectEnglishAnyway();
        NDPDevelopmentHome developmentHome = ndpObserverBrowser.getPage(NDPDevelopmentHome.class, testReporter);
        NDPMyProductsPage myProductsPage = developmentHome.clickMyProducts();
        NDPProductDashboardPage myProductDashBoard = myProductsPage.clickProductByName(productName);
        NDPProductDashboardReleasesTab productDashboardReleasesTab = myProductDashBoard.clickReleases();
        NDPReleaseInfoPage releaseInfoDashboard = productDashboardReleasesTab.selectInitialRelease();
    }

    @Test()
    @Order(6)
    public void fillOutReleaseDashboard(IJUnitTestReporter testReport) throws Exception {
        NDPProductDashboardPage myProductDashboard = ndpBrowser.getPage(NDPProductDashboardPage.class, testReport);
        NDPProductDashboardReleasesTab ndpProductDashboardReleasesTab = myProductDashboard.clickReleases();
        NDPReleaseInfoPage releaseInfoDashboard = ndpProductDashboardReleasesTab.selectInitialRelease();

        // Complete ReleaseInfo
        NDPReleaseInfoPage releaseInfoPage = releaseInfoDashboard.nav().clickReleaseInfo();
        CommonAction.selectROMSameAllRegion(releaseInfoPage, TextConstants.selectRomSameAllRegion, "2 GB", TextConstants.english);
        // Complete ReleaseInfo Features
        NDPReleaseInfoFeaturesPage releaseInfoFeaturesPage = releaseInfoDashboard.nav().clickFeatures();
        CommonAction.getFeatureOptionData(releaseInfoPage, TextConstants.allOff, TextConstants.allOff, TextConstants.allOff, false, "", false, "");
        //assertTrue(releaseInfoFeaturesPage.isAccountSpecificationsNotApplicable());

        //Complete ReleaseInfo Guideline
        NDPReleaseInfoGuidelinesPage ndpReleaseInfoGuidelines = releaseInfoFeaturesPage.clickContinue();
        ndpReleaseInfoGuidelines.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();

        //Complete ReleaseInfo Age Rating
        NDPReleaseInfoAgeRating ndpReleaseInfoAgeRating = releaseInfoDashboard.nav().clickAgeRating();
        ndpReleaseInfoAgeRating.clickOnAddStandardRatingButton()
                .selectStandardAgeRating("ESRB")
                .clickOnNextButtonInAddStandardAgeRating()
                .selectDescriptorAndNotice("Blood")
                .selectRatingValue("TEEN")
                .uploadCertificateAttachment(new File("src/test/resources/DataFile/Judgement.pdf"))
                .enterCertificateID("12345678")
                .clickOnSaveButtonInEditStandardRating();
        //Complete ReleaseInfo Test Scenarios
        // Complete ReleaseInfo ROM upload
        NDPMyProductsPage myProductsPage = ndpBrowser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPProductDashboardPage ndpProductDashboardPage = myProductsPage.clickProductByName(productName);
        NDPProductDashboardProductInfoTab ndpProductDashboardProductInfoTab = ndpProductDashboardPage.clickProductInfo();
        String applicationID = ndpProductDashboardProductInfoTab.getApplicationID();

        romBrowser = BrowserManager.getNewInstance();
        romUser = UserManager.get(ServiceType.SEARCH, UserType.UNAUTHENTICATED_USER);
        ROMLoginPage romLoginPage = romBrowser.openURL(ROMLoginPage.class, PageState.PRE_LOGIN, testReport);
        ROMCreateRom romCreateRom = romLoginPage.signInROM(romUser);
        romCreateRom
                .selectRequester(TextConstants.test_User)
                .selectType(TextConstants.initial_Release)
                .selectTargetDeliveryFormat(TextConstants.digital_Type)
                .enterProductName(productName)
                .enterApplicationId(applicationID)
                .selectPublishingRelationShip(TextConstants.third_Party_Type)
                .enterDisplayVersion("1.0.0")
                .enterReleaseVersion("00")
                .selectESRBRating("ESRB: TEEN")
                .clickOnCreateButton()
                .waitIsLoaded();
        fileRomName = romCreateRom.getRomNameTable(applicationID);
        System.out.println(fileRomName);
        romCreateRom.clickOnFirstROMFileOnROMTable();
        WaitUtils.idle(8000);
        BrowserManager.closeBrowser(romBrowser);

        ndpBrowser.openURL(NDPMyProductsPage.class, PageState.POST_LOGIN, testReport);
        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = myProductsPage.clickProductByName(productName)
                .clickReleases()
                .selectInitialRelease()
                .nav()
                .clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
        // Complete ReleaseInfo Issues
        NDPReleaseInfoIssuePage issuePage = releaseInfoDashboard.nav().clickIssues();
        CommonAction.enterReasonForRequest(issuePage);

        //Approval Issue TaskRequested
        NDPDevelopmentHome developmentHome = licBrowser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReport);
        NDPInternalTasksPage internalTasksPage = developmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPLicensingWaiverRequestsTab licensingWaiverRequestsTab = internalTasksPage.clickOnLicensingWaiverRequestsTab();
        CommonAction.approveLicensingWaiverRequests(developmentHome, licensingWaiverRequestsTab, savedGameCode);
        BrowserManager.closeBrowser(licBrowser);

        //Confirm submit button is actived
        NDPDevelopmentHome developmentHome1 = ndpBrowser.openURL(NDPDevelopmentHome.class, PageState.POST_LOGIN, testReport);
        developmentHome1.clickMyProducts().clickProductByName(productName).clickReleases().selectInitialRelease();
        assertAll(() -> assertTrue(releaseInfoDashboard.nav().isReleaseInfoCompleted(), TextConstants.releaseInfoComplete),
                () -> assertTrue(releaseInfoDashboard.nav().isFeaturesCompleted(), TextConstants.featureComplete),
                () -> assertTrue(releaseInfoDashboard.nav().isGuidelinesCompleted(), TextConstants.guideComplete),
                () -> assertTrue(releaseInfoDashboard.nav().isAgeRatingCompleted(), TextConstants.ageRatingComplete),
                () -> assertTrue(releaseInfoDashboard.nav().isTestScenariosCompleted(), TextConstants.testScenarioComplete),
                () -> assertTrue(releaseInfoPage.nav().isROMUploadCompleted(), TextConstants.romUploadComplete),
                () -> assertTrue(releaseInfoDashboard.nav().isIssuesCompleted(), TextConstants.issueComplete),
                () -> assertTrue(releaseInfoDashboard.nav().isSubmitButtonActivated(), TextConstants.submitActive));
    }

    @Test
    @Order(7)
    public void viewReleaseDashboardCompletedByObserverUser(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage releaseInfoPage = ndpObserverBrowser.getPage(NDPReleaseInfoPage.class, testReport);
        ndpObserverBrowser.refresh();
        assertAll(() -> assertTrue(releaseInfoPage.nav().isReleaseInfoCompleted(), TextConstants.releaseInfoComplete),
                () -> assertTrue(releaseInfoPage.nav().isFeaturesCompleted(), TextConstants.featureComplete),
                () -> assertTrue(releaseInfoPage.nav().isGuidelinesCompleted(), TextConstants.guideComplete),
                () -> assertTrue(releaseInfoPage.nav().isAgeRatingCompleted(), TextConstants.ageRatingComplete),
                () -> assertTrue(releaseInfoPage.nav().isTestScenariosCompleted(), TextConstants.testScenarioComplete),
                () -> assertTrue(releaseInfoPage.nav().isROMUploadCompleted(), TextConstants.romUploadComplete),
                () -> assertTrue(releaseInfoPage.nav().isIssuesCompleted(), TextConstants.issueComplete),
                () -> assertTrue(releaseInfoPage.nav().isSubmitButtonInActivated(), TextConstants.submitInActive));
        String messageNotPermissions = releaseInfoPage.nav().getListMessage();
        System.out.println(messageNotPermissions);
        assertAll(() -> assertTrue(messageNotPermissions.equalsIgnoreCase(TextConstants.noPermission)),
                () -> assertTrue(releaseInfoPage.isSubmissionTypeDisabled()));
    }

    @Test
    @Order(8)
    public void productAdminSubmitToLotCheck(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage releaseInfoPage = ndpBrowser.getPage(NDPReleaseInfoPage.class, testReport);
        releaseInfoPage.nav().selectSubmitButton()
                .clickSubmit();
        WaitUtils.idle(3000);
    }

    @Test
    @Order(9)
    public void verifyCancelSubmissionButtonByProductAdmin(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage releaseInfoPage = ndpBrowser.getPage(NDPReleaseInfoPage.class, testReport);
        assertTrue(releaseInfoPage.nav().isCancelSubmissionButtonActivated(), TextConstants.cancelSubmitActive);
    }

    @Test
    @Order(10)
    public void verifyCancelSubmissionButtonByObserver(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage releaseInfoPage = ndpObserverBrowser.getPage(NDPReleaseInfoPage.class, testReport);
        ndpObserverBrowser.refresh();
        assertTrue(releaseInfoPage.nav().isCancelSubmissionButtonInActivated(), TextConstants.cancelSubmitInactive);
    }

    @Test
    @Order(11)
    public void selectCancelSubmissionByProductAdmin(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage releaseInfoPage = ndpBrowser.getPage(NDPReleaseInfoPage.class, testReport);
        releaseInfoPage.nav()
                .clickCancelSubmit()
                .clickOKInConfirm();
        String messageValue = releaseInfoPage.nav().getMessageOnTheRight();
        String statusValue = releaseInfoPage.getStatusValue();
        assertAll(() -> assertTrue(messageValue.equalsIgnoreCase("Your submission is currently pending cancellation."), TextConstants.messageCorrect),
                () -> assertTrue(statusValue.equalsIgnoreCase("Cancel Pending"), "status is displayed Cancel Pending"));
    }

    @Test
    @Order(12)
    public void changeAuthorityOfObserverUserByProductAdmin(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage releaseInfoPage = ndpBrowser.getPage(NDPReleaseInfoPage.class, testReport);
        NDPProductDashboardMembersTab membersTab = releaseInfoPage.selectBackToProductDashboardPage().clickMembersTab();
        membersTab.selectEditIcon()
                .selectAccessRole(TextConstants.productAdmin)
                .selectSaveButton();
        String verifyUserName = membersTab.getUserNameText(2);
        String verifyAccessText = membersTab.getAccessTextAfterEdit();
        System.out.println(verifyUserName + " /and/ " + verifyAccessText + " /and/ " + userNameAdded);
        assertAll(() -> assertTrue(verifyUserName.equalsIgnoreCase(userNameAdded), TextConstants.userNameCorrect),
                () -> assertTrue(verifyAccessText.equalsIgnoreCase(TextConstants.productAdmin), TextConstants.accessRoleCorrect));
    }

    @Test
    @Order(13)
    public void navigateReleaseDashboardByNewProductAdmin(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage releaseInfoPage = ndpObserverBrowser.getPage(NDPReleaseInfoPage.class, testReport);
        int i = 0;
        while (i <= 300) {
            ndpObserverBrowser.refresh();
            String statusValue = releaseInfoPage.getStatusValue();
            if (statusValue.equalsIgnoreCase("In Development")) {
                if (releaseInfoPage.isExpectedSubmissionDateClickable()) {
                    break;
                }
            }
        }
        assertAll(() -> assertFalse(releaseInfoPage.nav().isReleaseInfoCompleted(), TextConstants.releaseInComplete),
                () -> assertFalse(releaseInfoPage.nav().isGuidelinesCompleted(), TextConstants.guideInComplete),
                () -> assertFalse(releaseInfoPage.nav().isROMUploadCompleted(), TextConstants.romUploadInComplete));

    }

    @Test
    @Order(14)
    public void completeReleaseDashboardByNewProductAdmin(IJUnitTestReporter testReport) throws Exception {
        NDPReleaseInfoPage releaseInfoPage = ndpObserverBrowser.getPage(NDPReleaseInfoPage.class, testReport);
        releaseInfoPage.selectExpectedSubmissionDate().chooseDateInCalender(2);
        releaseInfoPage.save();

        NDPReleaseInfoGuidelinesPage guidelinesPage = releaseInfoPage.nav().clickGuidelines();
        guidelinesPage.selectCompliesWithAllOfTheAboveGuidelines()
                .clickOnSaveButton();

        NDPReleaseInfoROMUpload ndpReleaseInfoROMUpload = guidelinesPage.nav().clickROMUpload();
        CommonAction.selectUploadROM(ndpReleaseInfoROMUpload, fileRomName);
        releaseInfoPage.nav().clickIssues();
        assertAll(() -> assertTrue(releaseInfoPage.nav().isReleaseInfoCompleted(), TextConstants.releaseInfoComplete),
                () -> assertTrue(releaseInfoPage.nav().isFeaturesCompleted(), TextConstants.featureComplete),
                () -> assertTrue(releaseInfoPage.nav().isGuidelinesCompleted(), TextConstants.guideComplete),
                () -> assertTrue(releaseInfoPage.nav().isAgeRatingCompleted(), TextConstants.ageRatingComplete),
                () -> assertTrue(releaseInfoPage.nav().isTestScenariosCompleted(), TextConstants.testScenarioComplete),
                () -> assertTrue(releaseInfoPage.nav().isROMUploadCompleted(), TextConstants.romUploadComplete),
                () -> assertTrue(releaseInfoPage.nav().isIssuesCompleted(), TextConstants.issueComplete),
                () -> assertTrue(releaseInfoPage.nav().isSubmitButtonActivated(), TextConstants.submitActive));
    }

    @Test
    @Order(15)
    public void NavigateMemberTabByProductAdminBrowser(IJUnitTestReporter testReport) {
        NDPProductDashboardMembersTab membersTab = ndpBrowser.getPage(NDPProductDashboardMembersTab.class, testReport);
    }

    @Test
    @Order(16)
    public void changeAuthorityToObserverOfNewProductAdmin(IJUnitTestReporter testReport) {
        NDPProductDashboardMembersTab membersTab = ndpBrowser.getPage(NDPProductDashboardMembersTab.class, testReport);
        membersTab.selectEditIcon()
                .selectAccessRole(TextConstants.observerUser)
                .selectSaveButton();
        String verifyUserName = membersTab.getUserNameText(2);
        String verifyAccessText = membersTab.getAccessTextAfterEdit();
        assertAll(() -> assertTrue(verifyUserName.equalsIgnoreCase(userNameAdded), TextConstants.userNameCorrect),
                () -> assertTrue(verifyAccessText.equalsIgnoreCase(TextConstants.observerUser), TextConstants.accessRoleCorrect));
    }

    @Test
    @Order(17)
    public void inCompleteTheReleaseByProductAdmin(IJUnitTestReporter testReport) {
        NDPProductDashboardPage dashboardPage = ndpBrowser.getPage(NDPProductDashboardPage.class, testReport);
        NDPReleaseInfoPage releaseInfoPage = dashboardPage.clickReleases().selectInitialRelease();
        releaseInfoPage.selectLanguageUsedForTextEntry("Please Select").save();
        releaseInfoPage.save();
        assertAll(() -> assertFalse(releaseInfoPage.nav().isReleaseInfoCompleted(), TextConstants.releaseInComplete),
                () -> assertTrue(releaseInfoPage.nav().isSubmitButtonInActivated(), TextConstants.submitButtonDisable));
    }

    @Test
    @Order(18)
    public void selectSubmitButtonByObserverUser(IJUnitTestReporter testReport) {
        NDPReleaseInfoIssuePage issuePage = ndpObserverBrowser.getPage(NDPReleaseInfoIssuePage.class, testReport);
        issuePage.nav().selectSubmitButtonBeGetError();
    }

    @Test
    @Order(19)
    public void verifyErrorAfterSelectSubmitButtonByObserverUser(IJUnitTestReporter testReport) {
        NDPReleaseInfoReleaseListNav nav = ndpObserverBrowser.getPage(NDPReleaseInfoReleaseListNav.class, testReport);
        String alertErrorValue = nav.getAlertError();
        String messageValue = nav.getMessageOnTheRight();
        assertAll(() -> assertTrue(alertErrorValue.equalsIgnoreCase(TextConstants.message_EN_01_401) || alertErrorValue.equalsIgnoreCase(TextConstants.message_EN_02_401), TextConstants.messageCorrect),
                () -> assertTrue(messageValue.equalsIgnoreCase(TextConstants.message_EN_01_401) || messageValue.equalsIgnoreCase(TextConstants.message_EN_02_401), TextConstants.messageCorrect));
        ndpObserverBrowser.refresh();
    }

    @Test
    @Order(20)
    public void changeLanguageToJapaneseByObserverUser(IJUnitTestReporter testReport) {
        NDPReleaseInfoReleaseListNav nav = ndpObserverBrowser.getPage(NDPReleaseInfoReleaseListNav.class, testReport);
        nav.clickUserMenuDropdownList().selectJapan();
        String alertErrorValue = nav.getAlertError();
        String messageValue = nav.getMessageOnTheRight();
        assertAll(() -> assertTrue(alertErrorValue.equalsIgnoreCase(TextConstants.message_JP_01_401) || alertErrorValue.equalsIgnoreCase(TextConstants.message_JP_02_401), TextConstants.messageCorrect),
                () -> assertTrue(messageValue.equalsIgnoreCase(TextConstants.message_JP_01_401) || messageValue.equalsIgnoreCase(TextConstants.message_JP_02_401), TextConstants.messageCorrect));
        //TODO: Exits bug G3PTDEV4NX-29599: No display release dashboard when user changed language is Japanese
    }

    @Test
    @Order(21)
    public void selectSeeHelpLinkByObserverUser(IJUnitTestReporter testReport) {
        NDPReleaseInfoIssuePage issuePage = ndpObserverBrowser.getPage(NDPReleaseInfoIssuePage.class, testReport);
        NDPHelpTroubleshooting helpTroubleshooting = issuePage.nav().selectSeeHelpPage();
        ndpObserverBrowser.refresh();
    }

    @Test
    @Order(22)
    public void verifyEnglishHelpPageText(IJUnitTestReporter testReport) {
        NDPHelpTroubleshooting helpTroubleshooting = ndpObserverBrowser.getPage(NDPHelpTroubleshooting.class, testReport);
        helpTroubleshooting.clickUserMenuDropdownList().selectEnglishAnyway();
        String contentError = helpTroubleshooting.getContentError("01-401");
        assertTrue(contentError.equalsIgnoreCase(TextConstants.message_01_401_Help_Page), TextConstants.contentErrorCorrect);
        //TODO: Exits bug G3PTDEV4NX-30414: Error Code 01-401 is not displayed on Help Page
    }

    @Test
    @Order(23)
    public void submitToLotCheckByProductAdmin(IJUnitTestReporter testReport) {
        NDPReleaseInfoPage releaseInfoPage = ndpBrowser.getPage(NDPReleaseInfoPage.class, testReport);
        releaseInfoPage.selectLanguageUsedForTextEntry(TextConstants.english)
                .save();
        releaseInfoPage.nav().selectSubmitButton().clickSubmit();
    }
}
