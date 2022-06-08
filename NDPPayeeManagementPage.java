package net.nintendo.automation.ui.models.ndp;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.ndp.admin.NDPOrganizationManagement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NDPPayeeManagementPage extends CommonUIComponent {
    private static final By uniqueId = By.xpath("//a[contains(@href,'payee')]//i");
    private final By tblEmpty = By.xpath("//td[@class='dataTables_empty']");
    private final By tblPayee = By.xpath("//table[@id='_noainternalpayeeportlet_WAR_noacompanyadminportlet_payeesWithoutBmsCodesTable']");
    private final By lnkOrganizationManagementTab = By.cssSelector("a[href*='group/development/internal/organization-management'].nin-nav-full-width");
    private final By lblAlertSuccess = By.xpath("//div[@class='alert alert-success']");
    private final By btnSaveBMSCode = By.xpath("//button[contains(@class,'save-bms-code')]");
    private final By btnYesButton = By.xpath("//button[@name='submitButton']");
    private final By alertError = By.xpath("//div[@class='alert alert-error']");
    private final By alertSuccess = By.xpath("//div[@class='alert alert-success']");
    private final By txtOrganizationName = By.id("_noainternalpayeeportlet_WAR_noacompanyadminportlet_altOrganizationName");
    private final By txtBMSCode = By.id("_noainternalpayeeportlet_WAR_noacompanyadminportlet_payeeBmsCode");
    private final By txtSearch = By.xpath("//input[contains(@class,'payee-search-field')]");

    public NDPPayeeManagementPage(WebDriver driver) {
        super(driver, uniqueId);
    }

    public NDPPayeeManagementPage enterPayee(String accountHolderName) {
        waitForElementVisible(tblPayee, WaitTimes.WAIT_FOR_DOCUMENT_READY_TIMEOUT_SECS);
        waitForAndClickElement(txtSearch);
        typeText(txtSearch, accountHolderName);
        extentLogger.logInfo(accountHolderName);
        return this;
    }

    public boolean isPayeeNotDisplayed() {
        waitDocumentReady();
        boolean value = false;
        if (!elementNotExists(tblEmpty)) {
            value = true;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public NDPTopNavigation navTopNavigation() {
        extentLogger.logInfo("");
        return getNewPage(NDPTopNavigation.class);
    }

    public NDPOrganizationManagement clickOrganizationManagementTab() {
        waitForAndClickElement(lnkOrganizationManagementTab);
        extentLogger.logInfo("");
        return getNewPage(NDPOrganizationManagement.class);
    }

    public String getSuccessMessage() {
        waitForElementVisible(lblAlertSuccess, WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        String value = getText(lblAlertSuccess).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public String getErrorMessage() {
        String value = getText(alertError).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public NDPPayeeManagementPage clickSaveBMSCodeButton() {
        waitForAndClickElement(btnSaveBMSCode);
        extentLogger.logInfo("");
        return this;
    }

    public NDPPayeeManagementPage clickYesButtonOnConfirmModal() {
        waitForAndClickElement(btnYesButton);
        extentLogger.logInfo("");
        return getNewPage(NDPPayeeManagementPage.class);
    }

    public boolean isPayeeDisplayed(String payeeName) {
        waitDocumentReady();
        Boolean isDisplayed = false;
        By payee = By.xpath(String.format("//table[@id='_noainternalpayeeportlet_WAR_noacompanyadminportlet_payeesWithoutBmsCodesTable']//tr/td[contains(text(),'%s')]", payeeName));
        if (!elementNotExists(payee)) {
            isDisplayed = true;
        }
        extentLogger.logInfo(isDisplayed.toString());
        return isDisplayed;
    }

    public NDPPayeeManagementPage selectPayeeByName(String payeeName) {
        By payee = By.xpath(String.format("//table[@id='_noainternalpayeeportlet_WAR_noacompanyadminportlet_payeesWithoutBmsCodesTable']//tr/td[contains(text(),'%s')]", payeeName));
        waitForAndClickElement(payee);
        extentLogger.logInfo("");
        return this;
    }

    public String getTitleNameInPayeeDetailByNo(int number) {
        By title = By.xpath(String.format("//form[@id='_noainternalpayeeportlet_WAR_noacompanyadminportlet_fm']/div/div[@class='nin-form-title'][%d]", number));
        String value = getText(title).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public String getOrganizationName() {
        String value = getValueAttributes(txtOrganizationName).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public NDPPayeeManagementPage enterBMSCode(String bmsCode) {
        waitForAndClickElement(txtBMSCode);
        typeText(txtBMSCode, bmsCode);
        typeTab(txtBMSCode);
        extentLogger.logInfo(bmsCode);
        return this;
    }

    public NDPPayeeManagementPage enterOrganizationName(String organizationName) {
        waitForAndClickElement(txtOrganizationName);
        typeText(txtOrganizationName, organizationName);
        typeTab(txtOrganizationName);
        extentLogger.logInfo(organizationName);
        return this;
    }
}