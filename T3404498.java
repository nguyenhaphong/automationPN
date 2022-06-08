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
import net.nintendo.automation.ui.models.ndp.NDPInternalTasksPage;
import net.nintendo.automation.ui.models.ndp.NDPProductTransferOwnerTab;
import net.nintendo.automation.ui.models.ndp.NDPViewTaskPage;
import net.nintendo.automation.ui.models.ndp.admin.NDPAdminPage;
import net.nintendo.automation.ui.models.ndp.admin.NDPTransferProductOwnershipPage;
import net.nintendo.automation.ui.models.ndp.admin.existing_adminTab.NDPAdminAddOrganizationRelationshipPage;
import net.nintendo.automation.ui.models.ndp.admin.existing_adminTab.NDPAdminOrganizationInformationPage;
import net.nintendo.automation.ui.models.ndp.admin.existing_adminTab.NDPAdminOrganizationRelationshipsPage;
import net.nintendo.automation.ui.models.ndp.admin.existing_adminTab.dialog.NDPDeleteRelationshipDialog;
import net.nintendo.automation.ui.models.ndp.existing_internal.NDPOrganizationManagementPage;
import net.nintendo.automation.ui.users.User;
import net.nintendo.automation.ui.users.UserManager;
import net.nintendo.automation_core.common_client.enums.ServiceType;
import net.nintendo.automation_core.common_client.enums.UserType;
import net.nintendo.automation_core.common_utils.annotations.services.NDP;
import net.nintendo.automation_core.common_utils.annotations.services.SLCMS;
import net.nintendo.automation_core.extent_reports.extensions.IJUnitTestReporter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@NDP
@SLCMS
//@ScenarioTest
@Tag("T3404498")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3404498 extends CommonBaseTest {
    private static Browser browser;
    private static Browser browserApproval;
    private static User ndpUser;
    private static User licUser;
    private static User licAdmin;
    private static CommonUIComponent currentPage;
    private static TestLogger logger;
    private static String nameOrgFrom = "PRDCV Le Ngoc 111";
    private static String uuidOrgParent = "8a948b8d7b0fcc87017b381c49fc00ff";
    private static String bmsCodeOrgFrom = "PRDCVLENGOC111";
    private static String uuidOrg = "8a948b927f77343f017fd44b15de0374";
    private static String nameOrg = "prdcv.test2";
    private static String contactName = "prdcv thuynt 2022.1";
    private static String phone = "0978556223";
    private static String email = "prdcv_partner_thuynt_2022.03@inbucket.com";
    private static String relatedOrg = " Sub-Organization (child)";
    private static String relatedOrgParent = " Top-Tier Organization (parent)";
    private static String message;
    private static String messageRemoveSuccess;
    private static String messageSuccessful = "Your request is being reviewed. You will be notified by email when the request has been completed.";
    private static String getMessageRemoveSuccessful = "Your request is being reviewed. You will be notified by email when the request has been completed.";
    private static boolean orgRelationshipDisplay;
    private static String bmsCodeToOrg;
    private static boolean checkDisplayAddRelationshipButton;
    private static boolean orgListIsDisplayed;
    private static String ndidOrgChild = "prdcv_partner_thuynt_2022.03";
    private static String passOrgChild = "nintendo";

    @AfterAll
    static void terminateBrowser() {
        BrowserManager.closeBrowser(browser);
        UserManager.releaseUser(ServiceType.NDP, ndpUser);
        UserManager.releaseUser(ServiceType.NDP, licUser);
        UserManager.releaseUser(ServiceType.NDP, licAdmin);
    }

    @BeforeAll
    public static void initiateBrowser() {
        browser = BrowserManager.getNewInstance();
        ndpUser = UserManager.get(ServiceType.NDP, UserType.PARTNER_USER, TextConstants.partner_LeNgoc);
        licUser = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_approver_NCL);
        licAdmin = UserManager.get(ServiceType.NDP, UserType.LICENSING_APPROVER, TextConstants.lic_admin);
        currentPage = browser.openURL(NDPHomePage.class, PageState.PRE_LOGIN);
    }

    @BeforeEach
    public void each(IJUnitTestReporter testReport) {
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
    }

    @Test()
    @Order(1)
    public void logInTo3PTAsAUserWithTheCompanyAdminRole_Step(IJUnitTestReporter testReport) throws Exception {
        NDPHomePage homePage = (NDPHomePage) currentPage;
        NDPDevelopmentHome ndpDevelopmentHome = homePage.acceptCookie().clickSignInPage().signIn(ndpUser);
        currentPage = ndpDevelopmentHome;
    }

    @Test()
    @Order(2)
    public void navigateAddRelationship_Step2(IJUnitTestReporter testReport) throws Exception {
        NDPDevelopmentHome ndpDevelopmentHome = (NDPDevelopmentHome) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPAdminPage ndpAdminPage = ndpDevelopmentHome.clickAdmin();
        NDPAdminAddOrganizationRelationshipPage addOrganizationRelationshipPage = ndpAdminPage.clickOrganizationInformationButton()
                .clickAdminOrganizationRelationshipsTab()
                .clickAddRelationshipButton();
        currentPage = addOrganizationRelationshipPage;
    }

    @Test()
    @Order(3)
    public void submitOrganizationRelationships_Step3(IJUnitTestReporter testReport) throws Exception {
        NDPAdminAddOrganizationRelationshipPage addOrganizationRelationshipPage = (NDPAdminAddOrganizationRelationshipPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPAdminOrganizationInformationPage organizationInformationPage = addOrganizationRelationshipPage.inputRelatedOrganizationUUID(uuidOrg)
                .selectRelatedOrganizationRelationshipDropdown(relatedOrg)
                .clickSubmitButton();
        message = organizationInformationPage.getMessageSuccess().trim();
        currentPage = organizationInformationPage;
    }

    @Test()
    @Order(4)
    public void messageDisplayWhenCreatedSuccessful_Expected3(IJUnitTestReporter testReport) {
        assertAll(() -> assertEquals(messageSuccessful, message, "A message is displayed that the relationship was created successful and wait for Approved"));
    }

    @Test()
    @Order(5)
    public void logInTo3PTAsLicensingApprover_Step4(IJUnitTestReporter testReport) throws Exception {
        NDPAdminOrganizationInformationPage organizationInformationPage = (NDPAdminOrganizationInformationPage) currentPage;
        NDPDevelopmentHome ndpDevelopmentHome = organizationInformationPage.navi().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPViewTaskPage ndpViewTaskPage = ndpDevelopmentHome.clickOnMyTaskMenu().clickOrganizationRelationshipRequestContent();
        ndpViewTaskPage.selectAssignToMeButton()
                .selectOKButtonInAssignToMeModal()
                .selectApproveRequestButton()
                .selectOKButtonInApproveRequestModal()
                .waitingForApproveSuccessful();
        currentPage = ndpViewTaskPage;
    }

    @Test()
    @Order(6)
    public void checkDisplayOrganizationRelationship_Step5(IJUnitTestReporter testReport) throws Exception {
        NDPViewTaskPage ndpViewTaskPage = (NDPViewTaskPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPDevelopmentHome ndpDevelopmentHome = ndpViewTaskPage.clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(ndpUser);
        NDPAdminPage ndpAdminPage = ndpDevelopmentHome.clickAdmin();
        NDPAdminOrganizationRelationshipsPage ndpOrganizationRelationshipsPage = ndpAdminPage.clickAdminOrganizationInformationButton()
                .clickOrganizationRelationshipsTab();
        orgRelationshipDisplay = ndpOrganizationRelationshipsPage.checkOrgRelationshipIsDisplayOnRelationshipList();
        currentPage = ndpOrganizationRelationshipsPage;
    }

    @Test()
    @Order(7)
    public void organizationRelationshipsDisplayWhenAddSuccessful_Expected5(IJUnitTestReporter testReport) {
        assertAll(() -> assertTrue(orgRelationshipDisplay, "Organization Relationship (on step 7) is removed form relationship list"));
    }

    @Test()
    @Order(8)
    public void selectXButtonOnAnExistingRelationShip_Step6(IJUnitTestReporter testReport) throws Exception {
        NDPAdminOrganizationRelationshipsPage ndpOrganizationRelationshipsPage = (NDPAdminOrganizationRelationshipsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPDeleteRelationshipDialog deleteRelationshipDialog = ndpOrganizationRelationshipsPage.clickRemoveIcon();
        currentPage = deleteRelationshipDialog;
    }

    @Test()
    @Order(9)
    public void selectYesButtonOnConfirmationWindowDialog_Step7(IJUnitTestReporter testReport) throws Exception {
        NDPDeleteRelationshipDialog deleteRelationshipDialog = (NDPDeleteRelationshipDialog) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPAdminOrganizationInformationPage organizationInformationPage = deleteRelationshipDialog.clickYesButtonOnDeleteRelationshipDialog();
        messageRemoveSuccess = organizationInformationPage.getMessageSuccess();
        currentPage = organizationInformationPage;
    }

    @Test()
    @Order(10)
    public void messageDisplayWhenRemoveSuccessful_Expected7(IJUnitTestReporter testReport) {
        assertAll(() -> assertEquals(getMessageRemoveSuccessful, messageRemoveSuccess, "A message is displayed"));
    }

    @Test()
    @Order(11)
    public void logInTo3PTAsLicensingApprover_Step8(IJUnitTestReporter testReport) throws Exception {
        NDPAdminOrganizationInformationPage organizationInformationPage = (NDPAdminOrganizationInformationPage) currentPage;
        NDPDevelopmentHome ndpDevelopmentHome = organizationInformationPage.navi().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPViewTaskPage ndpViewTaskPage = ndpDevelopmentHome.clickOnMyTaskMenu().clickOrganizationRelationshipRequestContent();
        ndpViewTaskPage.selectAssignToMeButton()
                .selectOKButtonInAssignToMeModal()
                .selectApproveRequestButton()
                .selectOKButtonInApproveRequestModal()
                .waitingForApproveSuccessful();
        currentPage = ndpViewTaskPage;
    }

    @Test()
    @Order(12)
    public void checkOrganizationRelationshipIsRemoved_Step9(IJUnitTestReporter testReport) throws Exception {
        NDPViewTaskPage ndpViewTaskPage = (NDPViewTaskPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPDevelopmentHome ndpDevelopmentHome = ndpViewTaskPage.clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(ndidOrgChild, passOrgChild);
        NDPAdminPage ndpAdminPage = ndpDevelopmentHome.clickAdmin();
        NDPAdminOrganizationRelationshipsPage ndpOrganizationRelationshipsPage = ndpAdminPage.clickAdminOrganizationInformationButton()
                .clickOrganizationRelationshipsTab();
        orgRelationshipDisplay = ndpOrganizationRelationshipsPage.checkOrgRelationshipIsDisplayOnRelationshipList();
        currentPage = ndpOrganizationRelationshipsPage;
    }

    @Test()
    @Order(13)
    public void organizationRelationshipsDisplayWhenRemoveSuccessful_Expected9(IJUnitTestReporter testReport) {
        assertAll(() -> assertFalse(orgRelationshipDisplay, "Organization Relationship (on step 7) is removed form relationship list"));
    }

    @Test()
    @Order(14)
    public void selectAddRelationshipButton_Step10(IJUnitTestReporter testReport) throws Exception {
        NDPAdminOrganizationRelationshipsPage ndpOrganizationRelationshipsPage = (NDPAdminOrganizationRelationshipsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPAdminAddOrganizationRelationshipPage ndpAddOrganizationRelationshipPage = ndpOrganizationRelationshipsPage.clickAddRelationshipButton();
        currentPage = ndpAddOrganizationRelationshipPage;
    }

    @Test()
    @Order(15)
    public void submitOrganizationRelationships_Step11(IJUnitTestReporter testReport) throws Exception {
        NDPAdminAddOrganizationRelationshipPage addOrganizationRelationshipPage = (NDPAdminAddOrganizationRelationshipPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPAdminOrganizationInformationPage organizationInformationPage = addOrganizationRelationshipPage.inputRelatedOrganizationUUID(uuidOrgParent)
                .selectRelatedOrganizationRelationshipDropdown(relatedOrgParent)
                .selectSharedPublishingAgreementsRadio()
                .clickSubmitButton();
        message = organizationInformationPage.getMessageSuccess().trim();
        currentPage = organizationInformationPage;
    }

    @Test()
    @Order(16)
    public void messageDisplayWhenCreatedSuccessful_Expected11(IJUnitTestReporter testReport) {
        assertAll(() -> assertEquals(messageSuccessful, message, "A message is displayed that the relationship was created successful and wait for Approved"));
    }

    @Test()
    @Order(17)
    public void logInTo3PTAsLicensingApprove_Step12(IJUnitTestReporter testReport) throws Exception {
        NDPAdminOrganizationInformationPage organizationInformationPage = (NDPAdminOrganizationInformationPage) currentPage;
        NDPDevelopmentHome ndpDevelopmentHome = organizationInformationPage.navi().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPViewTaskPage ndpViewTaskPage = ndpDevelopmentHome.clickOnMyTaskMenu().clickOrganizationRelationshipRequestContent();
        ndpViewTaskPage.selectAssignToMeButton()
                .selectOKButtonInAssignToMeModal()
                .selectApproveRequestButton()
                .selectOKButtonInApproveRequestModal()
                .waitingForApproveSuccessful();
        currentPage = ndpViewTaskPage;

    }

    @Test()
    @Order(18)
    public void createTransferProduct_Step13(IJUnitTestReporter testReport) throws Exception {
        NDPViewTaskPage ndpViewTaskPage = (NDPViewTaskPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPDevelopmentHome ndpDevelopmentHome = ndpViewTaskPage.clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(ndpUser);
        NDPAdminPage ndpAdminPage = ndpDevelopmentHome.clickAdmin();
        NDPTransferProductOwnershipPage ndpTransferProductOwnershipPage = ndpAdminPage.clickTransferProductOwnershipButton()
                .clickAdminTransferProductButton()
                .inputOrganizationName(nameOrg)
                .inputOrganizationUUID(uuidOrg)
                .inputContactName(contactName)
                .inputContactPhoneNo(phone)
                .inputContactEmail(email)
                .selectTransferEffectiveDateDropdown()
                .selectAttachmentsFile(new File("src/test/resources/DataFile/test_doc.docx"))
                .selectProductsCheckbox()
                .clickReviewSubmissionButton()
                .clickSubmitButton();
        currentPage = ndpTransferProductOwnershipPage;
        boolean messageTransferSuccess = ndpTransferProductOwnershipPage.checkCreateTransferFail();
        assertAll(() -> assertTrue(messageTransferSuccess));
    }

    @Test()
    @Order(19)
    public void navigateToProductTransferOwnership_Step14(IJUnitTestReporter testReport) throws Exception {
        NDPTransferProductOwnershipPage ndpTransferProductOwnershipPage = (NDPTransferProductOwnershipPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPDevelopmentHome ndpDevelopmentHome = ndpTransferProductOwnershipPage.navDevelopmentHome().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
        NDPInternalTasksPage internalTasksPage = ndpDevelopmentHome.clickOnMyTaskMenu().clickOnViewAllTaskButton();
        NDPProductTransferOwnerTab productTransferOwnerTab = internalTasksPage.clickOnProductTransferOwnerTab();
        productTransferOwnerTab.waitingForResultTableLoaded()
                .enterSearchingBox(nameOrg)
                .clickOnMagnifyingIconButton()
                .waitingForResultTableLoaded();
        currentPage = productTransferOwnerTab;
    }

    @Test()
    @Order(20)
    public void verifyValueAtFieldTransferToBMSCode_Step15(IJUnitTestReporter testReport) throws Exception {
        NDPProductTransferOwnerTab productTransferOwnerTab = (NDPProductTransferOwnerTab) currentPage;
        bmsCodeToOrg = productTransferOwnerTab.getTransferToBMSCode();
        currentPage = productTransferOwnerTab;
    }

    @Test()
    @Order(21)
    public void verifyValueAtFieldTransferToBMSCode_Expected15(IJUnitTestReporter testReport) {
        assertAll(() -> assertEquals(bmsCodeOrgFrom, bmsCodeToOrg, "Transfer To BMS Code should be the same as BMS Code of Second Organization"));
    }

    @Test()
    @Order(22)
    public void checkOrganizationRelationshipsAndAddRelationshipButton_Step16(IJUnitTestReporter testReport) throws Exception {
        NDPProductTransferOwnerTab productTransferOwnerTab = (NDPProductTransferOwnerTab) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPDevelopmentHome ndpDevelopmentHome = productTransferOwnerTab.navDevelopmentHome().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(ndidOrgChild, passOrgChild);
        NDPAdminPage ndpAdminPage = ndpDevelopmentHome.clickAdmin();
        NDPAdminOrganizationRelationshipsPage ndpOrganizationRelationshipsPage = ndpAdminPage.clickAdminOrganizationInformationButton()
                .clickOrganizationRelationshipsTab();
        orgRelationshipDisplay = ndpOrganizationRelationshipsPage.checkOrgRelationshipIsDisplayOnRelationshipList();
        checkDisplayAddRelationshipButton = ndpOrganizationRelationshipsPage.checkAddRelationshipButtonAvailable();
        currentPage = ndpOrganizationRelationshipsPage;
    }

    @Test()
    @Order(23)
    public void checkOrganizationRelationshipsAndAddRelationshipButton_Expected16(IJUnitTestReporter testReport) {
        assertAll(
                () -> assertTrue(orgRelationshipDisplay, "Organization Relationship submitted on step 11 is displayed on relationship list"),
                () -> assertFalse(checkDisplayAddRelationshipButton, "Add Relationship button NOT available")
        );
    }

    @Test()
    @Order(24)
    public void selectXButtonOnAnExistingRelationShip_Step17(IJUnitTestReporter testReport) throws Exception {
        NDPAdminOrganizationRelationshipsPage ndpOrganizationRelationshipsPage = (NDPAdminOrganizationRelationshipsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPDeleteRelationshipDialog deleteRelationshipDialog = ndpOrganizationRelationshipsPage.clickRemoveIcon();
        currentPage = deleteRelationshipDialog;
    }

    @Test()
    @Order(25)
    public void selectYesButtonOnConfirmationWindowDialog_Step18(IJUnitTestReporter testReport) throws Exception {
        NDPDeleteRelationshipDialog deleteRelationshipDialog = (NDPDeleteRelationshipDialog) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPAdminOrganizationInformationPage organizationInformationPage = deleteRelationshipDialog.clickYesButtonOnDeleteRelationshipDialog();
        messageRemoveSuccess = organizationInformationPage.getMessageSuccess();
        currentPage = organizationInformationPage;
    }

    @Test()
    @Order(26)
    public void messageDisplayWhenRemoveSuccessful_Expected18(IJUnitTestReporter testReport) {
        assertAll(() -> assertEquals(getMessageRemoveSuccessful, messageRemoveSuccess, "A message is displayed"));
    }

    @Test()
    @Order(27)
    public void logInTo3PTAsLicensingApprover_Step19(IJUnitTestReporter testReport) throws Exception {
        NDPAdminOrganizationInformationPage organizationInformationPage = (NDPAdminOrganizationInformationPage) currentPage;
        NDPDevelopmentHome ndpDevelopmentHome = organizationInformationPage.navi().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPViewTaskPage ndpViewTaskPage = ndpDevelopmentHome.clickOnMyTaskMenu().clickOrganizationRelationshipRequestContent();
        ndpViewTaskPage.selectAssignToMeButton()
                .selectOKButtonInAssignToMeModal()
                .selectApproveRequestButton()
                .selectOKButtonInApproveRequestModal()
                .waitingForApproveSuccessful();
        currentPage = ndpViewTaskPage;
    }

    @Test()
    @Order(28)
    public void checkOrganizationRelationshipIsRemoved_Step20(IJUnitTestReporter testReport) throws Exception {
        NDPViewTaskPage ndpViewTaskPage = (NDPViewTaskPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPDevelopmentHome ndpDevelopmentHome = ndpViewTaskPage.clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(ndpUser);
        NDPAdminPage ndpAdminPage = ndpDevelopmentHome.clickAdmin();
        NDPAdminOrganizationRelationshipsPage ndpOrganizationRelationshipsPage = ndpAdminPage.clickAdminOrganizationInformationButton()
                .clickOrganizationRelationshipsTab();
        orgRelationshipDisplay = ndpOrganizationRelationshipsPage.checkOrgRelationshipIsDisplayOnRelationshipList();
        currentPage = ndpOrganizationRelationshipsPage;
    }

    @Test()
    @Order(29)
    public void organizationRelationshipsDisplayWhenRemoveSuccessful_Expected20(IJUnitTestReporter testReport) {
        assertAll(() -> assertFalse(orgRelationshipDisplay, "Organization Relationship (on step 15) is removed form relationship list"));
    }

    @Test()
    @Order(30)
    public void logInTo3PTAsAUserWithTheLicensingAdminRole_Step21(IJUnitTestReporter testReport) throws Exception {
        NDPAdminOrganizationRelationshipsPage organizationRelationshipsPage = (NDPAdminOrganizationRelationshipsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPDevelopmentHome ndpDevelopmentHome = organizationRelationshipsPage.navDevelopmentHome().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licAdmin);
        currentPage = ndpDevelopmentHome;
    }

    @Test()
    @Order(31)
    public void goToInternalNavigateToOrganizationInformation_Step22(IJUnitTestReporter testReport) throws Exception {
        NDPDevelopmentHome ndpDevelopmentHome = (NDPDevelopmentHome) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPOrganizationManagementPage ndpOrganizationManagementPage = ndpDevelopmentHome.clickInternalTab().clickOrganizationManagementButton();
        currentPage = ndpOrganizationManagementPage;
    }

    @Test()
    @Order(32)
    public void selectSearchButtonOnSearchContent_Step23(IJUnitTestReporter testReport) throws Exception {
        NDPOrganizationManagementPage ndpOrganizationManagementPage = (NDPOrganizationManagementPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        ndpOrganizationManagementPage.waitingForResultTableLoaded()
                .enterSearchingBox(nameOrgFrom)
                .clickOnManifyingIconButton()
                .waitingForResultTableLoaded();
        currentPage = ndpOrganizationManagementPage;
        orgListIsDisplayed = ndpOrganizationManagementPage.OrganizationListIsDisplayed();
    }

    @Test()
    @Order(33)
    public void checkOrganizationListIsDisplayed_Expected23(IJUnitTestReporter testReport) {
        assertAll(() -> assertTrue(orgListIsDisplayed, "Organization list is displayed"));
    }

    @Test()
    @Order(34)
    public void selectAnExistingOrganizationThenSelectOrganizationRelationshipsButton_Step24(IJUnitTestReporter testReport) throws Exception {
        NDPOrganizationManagementPage ndpOrganizationManagementPage = (NDPOrganizationManagementPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPAdminOrganizationRelationshipsPage ndpOrganizationRelationshipsPage = ndpOrganizationManagementPage.clickViewDetailOrganization()
                .clickOrganizationRelationshipsTab();
        currentPage = ndpOrganizationRelationshipsPage;
    }

    @Test()
    @Order(35)
    public void selectAddRelationshipButton_Step25(IJUnitTestReporter testReport) throws Exception {
        NDPAdminOrganizationRelationshipsPage ndpOrganizationRelationshipsPage = (NDPAdminOrganizationRelationshipsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPAdminAddOrganizationRelationshipPage addOrganizationRelationshipPage = ndpOrganizationRelationshipsPage.clickAddRelationshipButton();
        currentPage = addOrganizationRelationshipPage;
    }

    @Test()
    @Order(36)
    public void submitOrganizationRelationships_Step26(IJUnitTestReporter testReport) throws Exception {
        NDPAdminAddOrganizationRelationshipPage addOrganizationRelationshipPage = (NDPAdminAddOrganizationRelationshipPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPAdminOrganizationInformationPage organizationInformationPage = addOrganizationRelationshipPage.inputRelatedOrganizationUUID(uuidOrg)
                .selectRelatedOrganizationRelationshipDropdown(relatedOrg)
                .clickSubmitButton();
        message = organizationInformationPage.getMessageSuccess().trim();
        currentPage = organizationInformationPage;
    }

    @Test()
    @Order(37)
    public void messageDisplayWhenCreatedSuccessful_Expected26(IJUnitTestReporter testReport) {
        assertAll(() -> assertEquals(messageSuccessful, message, "A message is displayed that the relationship was created successful and wait for Approved"));
    }

    @Test()
    @Order(38)
    public void logInTo3PTAsLicensingApprove_Step27(IJUnitTestReporter testReport) throws Exception {
        NDPAdminOrganizationInformationPage organizationInformationPage = (NDPAdminOrganizationInformationPage) currentPage;
        NDPDevelopmentHome ndpDevelopmentHome = organizationInformationPage.navi().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPViewTaskPage ndpViewTaskPage = ndpDevelopmentHome.clickOnMyTaskMenu().clickOrganizationRelationshipRequestContent();
        ndpViewTaskPage.selectAssignToMeButton()
                .selectOKButtonInAssignToMeModal()
                .selectApproveRequestButton()
                .selectOKButtonInApproveRequestModal()
                .waitingForApproveSuccessful();
        currentPage = ndpViewTaskPage;

    }

    @Test()
    @Order(39)
    public void checkDisplayOrganizationRelationship_Step28(IJUnitTestReporter testReport) throws Exception {
        NDPViewTaskPage ndpViewTaskPage = (NDPViewTaskPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPDevelopmentHome ndpDevelopmentHome = ndpViewTaskPage.clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(ndpUser);
        NDPAdminPage ndpAdminPage = ndpDevelopmentHome.clickAdmin();
        NDPAdminOrganizationRelationshipsPage ndpOrganizationRelationshipsPage = ndpAdminPage.clickAdminOrganizationInformationButton()
                .clickOrganizationRelationshipsTab();
        orgRelationshipDisplay = ndpOrganizationRelationshipsPage.checkOrgRelationshipIsDisplayOnRelationshipList();
        currentPage = ndpOrganizationRelationshipsPage;
    }

    @Test()
    @Order(40)
    public void checkOrganizationRelationshipsAndAddRelationshipButton_Expected28(IJUnitTestReporter testReport) {
        assertAll(
                () -> assertTrue(orgRelationshipDisplay, "Organization Relationship submitted on step 23 is displayed on relationship list")
        );
    }

    @Test()
    @Order(41)
    public void selectXButtonOnAnExistingRelationShip_Step29(IJUnitTestReporter testReport) throws Exception {
        NDPAdminOrganizationRelationshipsPage ndpOrganizationRelationshipsPage = (NDPAdminOrganizationRelationshipsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPDeleteRelationshipDialog deleteRelationshipDialog = ndpOrganizationRelationshipsPage.clickRemoveIcon();
        currentPage = deleteRelationshipDialog;
    }

    @Test()
    @Order(42)
    public void selectYesButtonOnConfirmationWindowDialog_Step30(IJUnitTestReporter testReport) throws Exception {
        NDPDeleteRelationshipDialog deleteRelationshipDialog = (NDPDeleteRelationshipDialog) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPAdminOrganizationInformationPage organizationInformationPage = deleteRelationshipDialog.clickYesButtonOnDeleteRelationshipDialog();
        messageRemoveSuccess = organizationInformationPage.getMessageSuccess();
        currentPage = organizationInformationPage;
    }

    @Test()
    @Order(43)
    public void messageDisplayWhenRemoveSuccessful_Expected30(IJUnitTestReporter testReport) {
        assertAll(() -> assertEquals(getMessageRemoveSuccessful, messageRemoveSuccess, "A message is displayed"));
    }

    @Test()
    @Order(44)
    public void logInTo3PTAsLicensingApprover_Step31(IJUnitTestReporter testReport) throws Exception {
        NDPAdminOrganizationInformationPage organizationInformationPage = (NDPAdminOrganizationInformationPage) currentPage;
        NDPDevelopmentHome ndpDevelopmentHome = organizationInformationPage.navi().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPViewTaskPage ndpViewTaskPage = ndpDevelopmentHome.clickOnMyTaskMenu().clickOrganizationRelationshipRequestContent();
        ndpViewTaskPage.selectAssignToMeButton()
                .selectOKButtonInAssignToMeModal()
                .selectApproveRequestButton()
                .selectOKButtonInApproveRequestModal()
                .waitingForApproveSuccessful();
        currentPage = ndpViewTaskPage;
    }

    @Test()
    @Order(45)
    public void checkOrganizationRelationshipIsRemoved_Step32(IJUnitTestReporter testReport) throws Exception {
        NDPViewTaskPage ndpViewTaskPage = (NDPViewTaskPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPDevelopmentHome ndpDevelopmentHome = ndpViewTaskPage.clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(ndidOrgChild, passOrgChild);
        NDPAdminPage ndpAdminPage = ndpDevelopmentHome.clickAdmin();
        NDPAdminOrganizationRelationshipsPage ndpOrganizationRelationshipsPage = ndpAdminPage.clickAdminOrganizationInformationButton()
                .clickOrganizationRelationshipsTab();
        orgRelationshipDisplay = ndpOrganizationRelationshipsPage.checkOrgRelationshipIsDisplayOnRelationshipList();
        currentPage = ndpOrganizationRelationshipsPage;
    }

    @Test()
    @Order(46)
    public void organizationRelationshipsDisplayWhenRemoveSuccessful_Expected32(IJUnitTestReporter testReport) {
        assertAll(() -> assertFalse(orgRelationshipDisplay, "Organization Relationship (on step 29) is removed form relationship list"));
    }

    @Test()
    @Order(47)
    public void selectAddRelationshipButton_Step33(IJUnitTestReporter testReport) throws Exception {
        NDPAdminOrganizationRelationshipsPage ndpOrganizationRelationshipsPage = (NDPAdminOrganizationRelationshipsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPAdminAddOrganizationRelationshipPage ndpAddOrganizationRelationshipPage = ndpOrganizationRelationshipsPage.clickAddRelationshipButton();
        currentPage = ndpAddOrganizationRelationshipPage;
    }

    @Test()
    @Order(48)
    public void submitOrganizationRelationships_Step34(IJUnitTestReporter testReport) throws Exception {
        NDPAdminAddOrganizationRelationshipPage addOrganizationRelationshipPage = (NDPAdminAddOrganizationRelationshipPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPAdminOrganizationInformationPage organizationInformationPage = addOrganizationRelationshipPage.inputRelatedOrganizationUUID(uuidOrgParent)
                .selectRelatedOrganizationRelationshipDropdown(relatedOrgParent)
                .selectSharedPublishingAgreementsRadio()
                .clickSubmitButton();
        message = organizationInformationPage.getMessageSuccess().trim();
        currentPage = organizationInformationPage;
    }

    @Test()
    @Order(49)
    public void messageDisplayWhenCreatedSuccessful_Expected34(IJUnitTestReporter testReport) {
        assertAll(() -> assertEquals(messageSuccessful, message, "A message is displayed that the relationship was created successful and wait for Approved"));
    }

    @Test()
    @Order(50)
    public void logInTo3PTAsLicensingApprove_Step35(IJUnitTestReporter testReport) throws Exception {
        NDPAdminOrganizationInformationPage organizationInformationPage = (NDPAdminOrganizationInformationPage) currentPage;
        NDPDevelopmentHome ndpDevelopmentHome = organizationInformationPage.navi().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPViewTaskPage ndpViewTaskPage = ndpDevelopmentHome.clickOnMyTaskMenu().clickOrganizationRelationshipRequestContent();
        ndpViewTaskPage.selectAssignToMeButton()
                .selectOKButtonInAssignToMeModal()
                .selectApproveRequestButton()
                .selectOKButtonInApproveRequestModal()
                .waitingForApproveSuccessful();
        currentPage = ndpViewTaskPage;
    }

    @Test()
    @Order(51)
    public void checkOrganizationRelationshipsAndAddRelationshipButton_Step36(IJUnitTestReporter testReport) throws Exception {
        NDPViewTaskPage ndpViewTaskPage = (NDPViewTaskPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPDevelopmentHome ndpDevelopmentHome = ndpViewTaskPage.clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(ndidOrgChild, passOrgChild);
        NDPAdminPage ndpAdminPage = ndpDevelopmentHome.clickAdmin();
        NDPAdminOrganizationRelationshipsPage ndpOrganizationRelationshipsPage = ndpAdminPage.clickAdminOrganizationInformationButton()
                .clickOrganizationRelationshipsTab();
        orgRelationshipDisplay = ndpOrganizationRelationshipsPage.checkOrgRelationshipIsDisplayOnRelationshipList();
        checkDisplayAddRelationshipButton = ndpOrganizationRelationshipsPage.checkAddRelationshipButtonAvailable();
        currentPage = ndpOrganizationRelationshipsPage;
    }

    @Test()
    @Order(52)
    public void checkOrganizationRelationshipsAndAddRelationshipButton_Expected36(IJUnitTestReporter testReport) {
        assertAll(
                () -> assertTrue(orgRelationshipDisplay, "Organization Relationship submitted on step 11 is displayed on relationship list"),
                () -> assertFalse(checkDisplayAddRelationshipButton, "Add Relationship button NOT available")
        );
    }

    @Test()
    @Order(53)
    public void selectXButtonOnAnExistingRelationShip_Step37(IJUnitTestReporter testReport) throws Exception {
        NDPAdminOrganizationRelationshipsPage ndpOrganizationRelationshipsPage = (NDPAdminOrganizationRelationshipsPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPDeleteRelationshipDialog deleteRelationshipDialog = ndpOrganizationRelationshipsPage.clickRemoveIcon();
        currentPage = deleteRelationshipDialog;
    }

    @Test()
    @Order(54)
    public void selectYesButtonOnConfirmationWindowDialog_Step38(IJUnitTestReporter testReport) throws Exception {
        NDPDeleteRelationshipDialog deleteRelationshipDialog = (NDPDeleteRelationshipDialog) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPAdminOrganizationInformationPage organizationInformationPage = deleteRelationshipDialog.clickYesButtonOnDeleteRelationshipDialog();
        messageRemoveSuccess = organizationInformationPage.getMessageSuccess();
        currentPage = organizationInformationPage;
    }

    @Test()
    @Order(55)
    public void messageDisplayWhenRemoveSuccessful_Expected38(IJUnitTestReporter testReport) {
        assertAll(() -> assertEquals(getMessageRemoveSuccessful, messageRemoveSuccess, "A message is displayed"));
    }

    @Test()
    @Order(56)
    public void logInTo3PTAsLicensingApprove_Step39(IJUnitTestReporter testReport) throws Exception {
        NDPAdminOrganizationInformationPage organizationInformationPage = (NDPAdminOrganizationInformationPage) currentPage;
        NDPDevelopmentHome ndpDevelopmentHome = organizationInformationPage.navi().clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(licUser);
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPViewTaskPage ndpViewTaskPage = ndpDevelopmentHome.clickOnMyTaskMenu().clickOrganizationRelationshipRequestContent();
        ndpViewTaskPage.selectAssignToMeButton()
                .selectOKButtonInAssignToMeModal()
                .selectApproveRequestButton()
                .selectOKButtonInApproveRequestModal()
                .waitingForApproveSuccessful();
        currentPage = ndpViewTaskPage;
    }

    @Test()
    @Order(57)
    public void checkOrganizationRelationshipIsRemoved_Step40(IJUnitTestReporter testReport) throws Exception {
        NDPViewTaskPage ndpViewTaskPage = (NDPViewTaskPage) currentPage;
        logger = new TestLogger(testReport);
        currentPage.setExtentLogger(logger);
        NDPDevelopmentHome ndpDevelopmentHome = ndpViewTaskPage.clickUserMenuDropdownList().clickSignOutButton().clickSignInPage().signIn(ndpUser);
        NDPAdminPage ndpAdminPage = ndpDevelopmentHome.clickAdmin();
        NDPAdminOrganizationRelationshipsPage ndpOrganizationRelationshipsPage = ndpAdminPage.clickAdminOrganizationInformationButton()
                .clickOrganizationRelationshipsTab();

        orgRelationshipDisplay = ndpOrganizationRelationshipsPage.checkOrgRelationshipIsDisplayOnRelationshipList();
        currentPage = ndpOrganizationRelationshipsPage;
    }

    @Test()
    @Order(58)
    public void organizationRelationshipsDisplayWhenRemoveSuccessful_Expected40(IJUnitTestReporter testReport) {
        assertAll(() -> assertFalse(orgRelationshipDisplay, "Organization Relationship (on step 36) is removed form relationship list"));
    }

}
