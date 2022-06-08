package net.nintendo.automation.ui.models.ndp;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.utils.WaitUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NDPFinanceInformationPage extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@id='p_p_id_56_INSTANCE_y8W1Doi0waHe_']");
    private final By btnAddNewPayee_NintendoSwitchPayee = By.xpath("//button[@class='btn btn-primary']/preceding::button[@class='btn btn-primary']");
    private final By payeeCreateForm = By.xpath("//div[@id='p_p_id_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_']");
    private final By ddlCountryPayeeBank = By.xpath("//select[@class='country-code country-input country-template']");
    private final By txtAccountName = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_accountHolderName']");
    private final By txtAccountHolderStreet1 = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_accountHolderAddressStreet1']");
    private final By txtAccountHolderCity = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_accountHolderAddressCity']");
    private final By txtAccountHolderPostalCode = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_accountHolderAddressZip']");
    private final By ddlCountryAccountHolder = By.xpath("//select[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_accountHolderCountryId']");
    private final By txtAccountNumber = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_accountNumber']");
    private final By cboAccountCurrency = By.xpath("//input[@id='account-currency-cb']");
    private final By ddlAccountType = By.xpath("//select[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_accountType']");
    private final By txtBankName = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_bankName']");
    private final By txtBranchOfficeName = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_branchOfficeName']");
    private final By txtBICSwiftCode = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_swiftCode']");
    private final By btnNext = By.xpath("//button[@class='btn pull-right margin-left nin-btn-primary payee-submit-button edit-mode-only']");
    private final By lblAlertSuccess = By.xpath("//div[@class='alert alert-success']");
    private final By txtAccountHolderNameChina = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_accountHolderNameChina']");
    private final By txtBankNameChina = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_bankNameChina']");
    private final By txtBankOfficeNameChina = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_branchOfficeNameChina']");


    private final By txtContactNameForInvoicing = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_contactName']");
    private final By txtContactEmailAddressForInvoicing = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_contactEmail']");
    private final By txtBusinessLicenseCertificate = By.xpath("//input[@class='field nin-upload-filename nin-input-file']/following::input[@class='field nin-upload-filename nin-input-file']");
    private final By txtAccountOpeningCertificate = By.xpath("//input[@class='field nin-upload-filename nin-input-file']/preceding::input[@class='field nin-upload-filename nin-input-file']");
    private final By btnCancel = By.xpath("//a[contains(@class,'btn-cancel')]");
    private final By payeeViewDetail = By.xpath("//div[@id='p_p_id_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_']//h2[text()='Financial Information']");
    private final By btnAddNewPayee_Nintendo3DSWiiU = By.xpath("//h5[contains(text(),'Nintendo 3DS / Wii U Payees')]/../..//button[@class='btn btn-primary']");

    private final By iconRequiredBankName = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_bankName']/../span/span[@class='label-required']");
    private final By txtBankOfficeNumber = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_branchOfficeNumber']");
    private final By iconRequiredBankOfficeNumber = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_branchOfficeNumber']/../span/span[@class='label-required']");
    private final By iconRequiredBICSwiftCode = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_swiftCode']/../span/span[@class='label-required']");
    private final By lnkViewDetailNintendoSwitchPayee = By.xpath("//a[contains(@href,'_payeeRegionType=GLOBAL')]");
    private final By btnEdit = By.xpath("//button[contains(@class,'payee-edit-button')]");
    private final By btnSubmit = By.xpath("//button[contains(@class,'payee-submit-button')]");
    private final By iconRequiredAccountHolderName = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_accountHolderName']/../span/span[@class='label-required']");
    private final By iconRequiredAccountHolderStreet1 = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_accountHolderAddressStreet1']/../span/span[@class='label-required']");
    private final By txtAccountHolderStreet2 = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_accountHolderAddressStreet2']");

    private final By iconRequiredAccountHolderStreet2 = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_accountHolderAddressStreet2']/../span/span[@class='label-required']");
    private final By iconRequiredAccountHolderCity = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_accountHolderAddressCity']/../span/span[@class='label-required']");
    private final By iconRequiredAccountHolderPostalCode = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_accountHolderAddressZip']/../span/span[@class='label-required']");
    private final By iconRequiredCountryAccountHolder = By.xpath("//select[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_accountHolderCountryId']/../span/span[@class='label-required']");
    private final By txtAccountHolderStateProvinceRegion = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_accountHolderAddressStreet3']");
    private final By iconRequiredAccountHolderStateProvinceRegion = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_accountHolderAddressStreet3']/../span/span[@class='label-required']");
    private final By txtIBANCode = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_ibanCode']");
    private final By iconRequiredIBANCode = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_ibanCode']/../span/span[@class='label-required']");
    private final By accountTypeOptions = By.xpath("//select[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_accountType']/option[@label]");
    private final By iconAccountTypeRequire = By.xpath("//select[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_accountType']/../span/span[@class='label-required']");
    private final By businessLicenseCertificate = By.xpath("//div[contains(@class,' account-opening-certificate account-opening-certificate-field')]//label[contains(@class,'nin-btn-upload')]");
    private final By accountOpeningCertificate = By.xpath("//div[contains(@class,'business-license-certificate business-license-certificate-field')]//label[contains(@class,'nin-btn-upload')]");
    private final By elmCountryRegion = By.xpath("//div[contains(@class,'account-holder-address-country-field')]");
    private final By elmAccountHolderState = By.xpath("//div[contains(@class,'account-holder-address-region-field')]");
    private final By txtConfirmAccountCurrency = By.xpath("//span[@class='hide-in-edit']");
    private final By elmIBANCode = By.xpath("//div[contains(@class,'iban-code-field')]");
    private final By txtAccountHolderSuburb = By.name("_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_accountHolderAddressCity");
    private final By txtBSBNumber = By.name("_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_bsbNumber");
    private final By txtBankRoutingNumber = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_bankRoutingNumber']");
    private final By txtACHRoutingNumber = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_achRoutingNumber']");
    private final By txtBankNumber = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_bankNumber']");
    private final By txtBranchOfficeNumber = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_branchOfficeNumber']");
    private final By txtBankSortCode = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_bankSortCode']");
    private final By txtBankgiroNumber = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_bankgiroNumber']");
    private final By txtBankBIKCode = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_bankBIKCode']");
    private final By txtAccountNumberCentralBank = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_accountNumberWithCentralBank']");
    private final By txtINN = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_inn']");
    private final By txtKPP = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_kpp']");
    private final By txtBankRoutingCode = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_bankRoutingCode']");
    private final By txtCLABENumber = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_clabeNumber']");
    private final By txtAccountHolderName = By.xpath("//input[@name='_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_accountHolderName']");
    private final By formFinanceInformation = By.xpath("//form[contains(@action,'financial-information')]");
    private final By txtErrorMessageAccountHolderName = By.xpath("//div[contains(@class,'account-holder-name-field error')]//div[@role='alert']");
    private final By txtErrorMessageAccountHolderStreet1 = By.xpath("//div[contains(@class,'account-holder-address account-holder-address-street1-field error')]//div[@role='alert']");
    private final By txtErrorMessageAccountHolderStreet2 = By.xpath("//div[contains(@class,'account-holder-address account-holder-address-street2-field error')]//div[@role='alert']");
    private final By txtErrorMessageBankName = By.xpath("//div[contains(@class,'bank-name-field error')]//div[@role='alert']");
    private final By ddlCountryRegion = By.name("_noacompanyfinancialsportlet_WAR_noacompanyadminportlet_accountHolderCountryId");
    private final By txtMessageSuccess = By.xpath("//div[@class='alert alert-success']");
    private final By txtMessageError = By.xpath("//div[@class='alert alert-error']");
    private final By lnkViewDetails = By.xpath("//div[@class='nin-more-link']//a");

    public NDPFinanceInformationPage(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public NDPFinanceInformationPage clickAddNewPayeeNintendoSwitch() {
        waitForAndClickElement(btnAddNewPayee_NintendoSwitchPayee);
        extentLogger.logInfo("");
        return this;
    }

    public boolean isThePayeeCreateFormDisplayed() {
        boolean value = false;
        if (!elementNotExists(payeeCreateForm)) {
            value = true;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public NDPFinanceInformationPage selectCountryPayeeBank(String country) {
        selectValueOnDropDown(ddlCountryPayeeBank, country);
        extentLogger.logInfo(country);
        return this;
    }

    public NDPFinanceInformationPage enterAccountName(String accountName) {
        waitForAndClickElement(txtAccountName);
        typeText(txtAccountName, accountName);
        typeTab(txtAccountName);
        extentLogger.logInfo(accountName);
        return this;
    }

    public NDPFinanceInformationPage enterAccountHolderNameChina(String accountName) {
        waitForAndClickElement(txtAccountHolderNameChina);
        typeText(txtAccountHolderNameChina, accountName);
        typeTab(txtAccountHolderNameChina);
        extentLogger.logInfo(accountName);
        return this;
    }

    public NDPFinanceInformationPage enterBankNameChina(String bankName) {
        waitForAndClickElement(txtBankNameChina);
        typeText(txtBankNameChina, bankName);
        typeTab(txtBankNameChina);
        extentLogger.logInfo(bankName);
        return this;
    }

    public NDPFinanceInformationPage enterContactNameChina(String contactName) {
        waitForAndClickElement(txtContactNameForInvoicing);
        typeText(txtContactNameForInvoicing, contactName);
        typeTab(txtContactNameForInvoicing);
        extentLogger.logInfo(contactName);
        return this;
    }

    public NDPFinanceInformationPage enterContactEmailAddressChina(String contactEmail) {
        waitForAndClickElement(txtContactEmailAddressForInvoicing);
        typeText(txtContactEmailAddressForInvoicing, contactEmail);
        typeTab(txtContactEmailAddressForInvoicing);
        extentLogger.logInfo(contactEmail);
        return this;
    }

    public NDPFinanceInformationPage chooseAccountOpeningCertificate(String fileName) {
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + fileName;
        typeText(txtAccountOpeningCertificate, path);
        extentLogger.logInfo(path);
        return this;
    }

    public NDPFinanceInformationPage chooseBusinessLicenseCertificate(String fileName) {
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + fileName;
        typeText(txtBusinessLicenseCertificate, path);
        extentLogger.logInfo(path);
        return this;
    }

    public NDPFinanceInformationPage enterBankOfficeNameChina(String bankOfficeNameChina) {
        waitForAndClickElement(txtBankOfficeNameChina);
        typeText(txtBankOfficeNameChina, bankOfficeNameChina);
        typeTab(txtBankOfficeNameChina);
        extentLogger.logInfo(bankOfficeNameChina);
        return this;
    }


    public NDPFinanceInformationPage enterAccountHolderStreet1(String accountHolderStreet1) {
        waitForAndClickElement(txtAccountHolderStreet1);
        typeText(txtAccountHolderStreet1, accountHolderStreet1);
        typeTab(txtAccountHolderStreet1);
        extentLogger.logInfo(accountHolderStreet1);
        return this;
    }

    public NDPFinanceInformationPage enterAccountHolderCity(String accountHolderCity) {
        waitForAndClickElement(txtAccountHolderCity);
        typeText(txtAccountHolderCity, accountHolderCity);
        typeTab(txtAccountHolderCity);
        extentLogger.logInfo(accountHolderCity);
        return this;
    }

    public NDPFinanceInformationPage enterAccountHolderPostalCode(String accountHolderPostalCode) {
        waitForAndClickElement(txtAccountHolderPostalCode);
        typeText(txtAccountHolderPostalCode, accountHolderPostalCode);
        typeTab(txtAccountHolderPostalCode);
        extentLogger.logInfo(accountHolderPostalCode);
        return this;
    }

    public NDPFinanceInformationPage selectCountryAccountHolder(String country) {
        waitForElementVisible(ddlCountryAccountHolder, WaitTimes.WAIT_FOR_SUBMIT_PROCESSING_SECS);
        selectValueOnDropDown(ddlCountryAccountHolder, country);
        extentLogger.logInfo(country);
        return this;
    }

    public NDPFinanceInformationPage enterAccountNumber(String accountNumber) {
        waitForAndClickElement(txtAccountNumber);
        typeText(txtAccountNumber, accountNumber);
        typeTab(txtAccountNumber);
        extentLogger.logInfo(accountNumber);
        return this;
    }

    public NDPFinanceInformationPage selectAccountCurrency() {
        selectCheckbox(cboAccountCurrency);
        extentLogger.logInfo("");
        return this;
    }

    public NDPFinanceInformationPage selectAccountType(String accountType) {
        selectValueOnDropDown(ddlAccountType, accountType);
        extentLogger.logInfo(accountType);
        return this;
    }

    public String getSuccessMessage() {
        String value = getText(lblAlertSuccess).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public boolean isPayeeCreated(String payeeName) {
        boolean value = false;
        if (!elementNotExists(By.xpath(String.format("//div[contains(text(),'%s')]", payeeName)))) {
            value = true;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public NDPTopNavigationBar navTopNavigationBar() {
        extentLogger.logInfo("");
        return getNewPage(NDPTopNavigationBar.class);
    }

    public NDPFinanceInformationPage clickCancelButtonAndCloseAlert() {
        waitForAndClickElement(btnCancel);
        extentLogger.logInfo("");
        driver.switchTo().alert().accept();
        waitDocumentReady();
        return this;
    }

    public NDPFinanceInformationPage clickAddNewPayeeNintendo3DSWiiU() {
        waitForAndClickElement(btnAddNewPayee_Nintendo3DSWiiU);
        extentLogger.logInfo("");
        return this;
    }

    public boolean isAddNewPayeeNintendoSwitchButtonVisible() {
        Boolean isVisible;
        if (elementNotExists(btnAddNewPayee_NintendoSwitchPayee)) {
            isVisible = false;
        } else isVisible = true;
        extentLogger.logInfo(isVisible.toString());
        return isVisible;
    }


    public boolean isTheViewDetailPayeeDisplayed() {
        boolean value = false;
        if (!elementNotExists(payeeViewDetail)) {
            value = true;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public boolean isAccountHolderNameDisplayTextField() {
        Boolean isDisplayTextField = false;
        if (driver.findElement(txtAccountName).isDisplayed()) {
            if (getAttributes(txtAccountName, "type").equalsIgnoreCase("text")) {
                isDisplayTextField = true;
            }
        }
        extentLogger.logInfo(isDisplayTextField.toString());
        return isDisplayTextField;
    }

    public NDPFinanceInformationPage enterBankName(String bankName) {
        /*waitForAndClickElement(txtBankName);
        typeText(txtBankName, bankName);
        typeTab(txtBankName);
        extentLogger.logInfo(bankName);
        return this;*/
        WebElement element = driver.findElement(txtBankName);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", element);
        typeText(txtBankName, bankName);
        typeTab(txtBankName);
        extentLogger.logInfo(bankName);
        return this;
    }

    public boolean isBankNameDisplayTextField() {
        Boolean isDisplayTextField = false;
        if (driver.findElement(txtBankName).isDisplayed()) {
            if (getAttributes(txtBankName, "type").equalsIgnoreCase("text")) {
                isDisplayTextField = true;
            }
        }
        extentLogger.logInfo(isDisplayTextField.toString());
        return isDisplayTextField;
    }

    public boolean isBankNameDisplayRequiredField() {
        Boolean isDisplayRequiredField = false;
        if (!elementNotExists(iconRequiredBankName)) {
            isDisplayRequiredField = true;
        }
        extentLogger.logInfo(isDisplayRequiredField.toString());
        return isDisplayRequiredField;
    }

    public boolean isBankNumberDisplayTextField() {
        Boolean isDisplayTextField = false;
        if (driver.findElement(txtBankName).isDisplayed()) {
            if (getAttributes(txtBankName, "type").equalsIgnoreCase("text")) {
                isDisplayTextField = true;
            }
        }
        extentLogger.logInfo(isDisplayTextField.toString());
        return isDisplayTextField;
    }

    public boolean isBankNumberDisplayRequiredField() {
        Boolean isDisplayRequiredField = false;
        if (!elementNotExists(iconRequiredBankName)) {
            isDisplayRequiredField = true;
        }
        extentLogger.logInfo(isDisplayRequiredField.toString());
        return isDisplayRequiredField;
    }

    public boolean isBankOfficeNumberDisplayTextField() {
        Boolean isDisplayTextField = false;
        if (driver.findElement(txtBankOfficeNumber).isDisplayed()) {
            if (getAttributes(txtBankOfficeNumber, "type").equalsIgnoreCase("text")) {
                isDisplayTextField = true;
            }
        }
        extentLogger.logInfo(isDisplayTextField.toString());
        return isDisplayTextField;
    }

    public boolean isBankOfficeNumberDisplayRequiredField() {
        Boolean isDisplayRequiredField = false;
        if (!elementNotExists(iconRequiredBankOfficeNumber)) {
            isDisplayRequiredField = true;
        }
        extentLogger.logInfo(isDisplayRequiredField.toString());
        return isDisplayRequiredField;
    }

    public NDPFinanceInformationPage enterBranchOfficeName(String branchOfficeName) {
        waitForAndClickElement(txtBranchOfficeName);
        typeText(txtBranchOfficeName, branchOfficeName);
        typeTab(txtBranchOfficeName);
        extentLogger.logInfo(branchOfficeName);
        return this;
    }

    public NDPFinanceInformationPage enterBicSwiftCode(String bicSwiftCode) {
        waitForAndClickElement(txtBICSwiftCode);
        typeText(txtBICSwiftCode, bicSwiftCode);
        typeTab(txtBICSwiftCode);
        extentLogger.logInfo(bicSwiftCode);
        return this;
    }

    public boolean isBicSwiftCodeDisplayTextField() {
        Boolean isDisplayTextField = false;
        if (driver.findElement(txtBICSwiftCode).isDisplayed()) {
            if (getAttributes(txtBICSwiftCode, "type").equalsIgnoreCase("text")) {
                isDisplayTextField = true;
            }
        }
        extentLogger.logInfo(isDisplayTextField.toString());
        return isDisplayTextField;
    }

    public boolean isBicSwiftCodeDisplayRequiredField() {
        Boolean isDisplayRequiredField = false;
        if (!elementNotExists(iconRequiredBICSwiftCode)) {
            isDisplayRequiredField = true;
        }
        extentLogger.logInfo(isDisplayRequiredField.toString());
        return isDisplayRequiredField;
    }

    public boolean isFieldByNameDisplayed(String fieldName) {
        Boolean isDisplayed = false;
        By field = By.xpath(String.format("//div[@class='nin-form']/section/div[contains(@class,'control-group form-inline field-wrapper')]/span[contains(text(),'%s')]", fieldName));
        if (!elementNotExists(field)) {
            if (driver.findElement(field).isDisplayed()) {
                isDisplayed = true;
            }
        }
        extentLogger.logInfo(isDisplayed.toString());
        return isDisplayed;
    }

    public NDPFinanceInformationPage clickViewDetailNintendoSwitchPayee() {
        waitForAndClickElement(lnkViewDetailNintendoSwitchPayee);
        extentLogger.logInfo("");
        return this;
    }

    public NDPFinanceInformationPage clickEditButton() {
        waitForAndClickElement(btnEdit);
        extentLogger.logInfo("");
        return this;
    }

    public boolean isAddNewPayeeNintendoSwitchButtonVisibled() {
        Boolean isVisible;
        if (elementNotExists(btnAddNewPayee_NintendoSwitchPayee)) {
            isVisible = false;
        } else isVisible = true;
        extentLogger.logInfo(isVisible.toString());
        return isVisible;
    }

    public boolean isAccountHolderNameDisplayRequiredField() {
        Boolean isDisplayRequiredField = false;
        if (!elementNotExists(iconRequiredAccountHolderName)) {
            isDisplayRequiredField = true;
        }
        extentLogger.logInfo(isDisplayRequiredField.toString());
        return isDisplayRequiredField;
    }

    public boolean isAccountHolderStreet1DisplayTextField() {
        Boolean isDisplayTextField = false;
        if (driver.findElement(txtAccountHolderStreet1).isDisplayed()) {
            if (getAttributes(txtAccountHolderStreet1, "type").equalsIgnoreCase("text")) {
                isDisplayTextField = true;
            }
        }
        extentLogger.logInfo(isDisplayTextField.toString());
        return isDisplayTextField;
    }

    public boolean isAccountHolderStreet1DisplayRequiredField() {
        Boolean isDisplayRequiredField = false;
        if (!elementNotExists(iconRequiredAccountHolderStreet1)) {
            isDisplayRequiredField = true;
        }
        extentLogger.logInfo(isDisplayRequiredField.toString());
        return isDisplayRequiredField;
    }

    public boolean isAccountHolderStreet2DisplayTextField() {
        Boolean isDisplayTextField = false;
        if (driver.findElement(txtAccountHolderStreet2).isDisplayed()) {
            if (getAttributes(txtAccountHolderStreet2, "type").equalsIgnoreCase("text")) {
                isDisplayTextField = true;
            }
        }
        extentLogger.logInfo(isDisplayTextField.toString());
        return isDisplayTextField;
    }

    public boolean isAccountHolderStreet2DisplayRequiredField() {
        Boolean isDisplayRequiredField = false;
        if (!elementNotExists(iconRequiredAccountHolderStreet2)) {
            isDisplayRequiredField = true;
        }
        extentLogger.logInfo(isDisplayRequiredField.toString());
        return isDisplayRequiredField;
    }

    public boolean isAccountHolderCityDisplayTextField() {
        Boolean isDisplayTextField = false;
        if (driver.findElement(txtAccountHolderCity).isDisplayed()) {
            if (getAttributes(txtAccountHolderCity, "type").equalsIgnoreCase("text")) {
                isDisplayTextField = true;
            }
        }
        extentLogger.logInfo(isDisplayTextField.toString());
        return isDisplayTextField;
    }

    public boolean isAccountHolderCityDisplayRequiredField() {
        Boolean isDisplayRequiredField = false;
        if (!elementNotExists(iconRequiredAccountHolderCity)) {
            isDisplayRequiredField = true;
        }
        extentLogger.logInfo(isDisplayRequiredField.toString());
        return isDisplayRequiredField;
    }

    public boolean isAccountHolderPostalCodeDisplayTextField() {
        Boolean isDisplayTextField = false;
        if (driver.findElement(txtAccountHolderPostalCode).isDisplayed()) {
            if (getAttributes(txtAccountHolderPostalCode, "type").equalsIgnoreCase("text")) {
                isDisplayTextField = true;
            }
        }
        extentLogger.logInfo(isDisplayTextField.toString());
        return isDisplayTextField;
    }

    public boolean isAccountHolderPostalCodeDisplayRequiredField() {
        Boolean isDisplayRequiredField = false;
        if (!elementNotExists(iconRequiredAccountHolderPostalCode)) {
            isDisplayRequiredField = true;
        }
        extentLogger.logInfo(isDisplayRequiredField.toString());
        return isDisplayRequiredField;
    }

    public boolean isCountryAccountHolderDisplayRequiredField() {
        Boolean isDisplayRequiredField = false;
        if (!elementNotExists(iconRequiredCountryAccountHolder)) {
            isDisplayRequiredField = true;
        }
        extentLogger.logInfo(isDisplayRequiredField.toString());
        return isDisplayRequiredField;
    }

    public boolean isAccountHolderStateProvinceRegionDisplayTextField() {
        Boolean isDisplayTextField = false;
        if (driver.findElement(txtAccountHolderStateProvinceRegion).isDisplayed()) {
            if (getAttributes(txtAccountHolderStateProvinceRegion, "type").equalsIgnoreCase("text")) {
                isDisplayTextField = true;
            }
        }
        extentLogger.logInfo(isDisplayTextField.toString());
        return isDisplayTextField;
    }

    public boolean isAccountHolderStateProvinceRegionDisplayRequiredField() {
        Boolean isDisplayRequiredField = false;
        if (!elementNotExists(iconRequiredAccountHolderStateProvinceRegion)) {
            isDisplayRequiredField = true;
        }
        extentLogger.logInfo(isDisplayRequiredField.toString());
        return isDisplayRequiredField;
    }

    public boolean isIBANCodeDisplayTextField() {
        Boolean isDisplayTextField = false;
        if (driver.findElement(txtIBANCode).isDisplayed()) {
            if (getAttributes(txtIBANCode, "type").equalsIgnoreCase("text")) {
                isDisplayTextField = true;
            }
        }
        extentLogger.logInfo(isDisplayTextField.toString());
        return isDisplayTextField;
    }

    public boolean isIBANCodeDisplayRequiredField() {
        Boolean isDisplayRequiredField = false;
        if (!elementNotExists(iconRequiredIBANCode)) {
            isDisplayRequiredField = true;
        }
        extentLogger.logInfo(isDisplayRequiredField.toString());
        return isDisplayRequiredField;
    }

    public ArrayList<String> getAccountTypeOptions() {
        ArrayList<String> options = new ArrayList<>();
        List<WebElement> selectors = driver.findElements(accountTypeOptions);
        for (WebElement selector : selectors) {
            options.add(getText(selector).trim());
        }
        extentLogger.logInfo(options.toString());
        return options;
    }

    public boolean isCountryAccountTypeRequiredField() {
        Boolean isDisplayRequiredField = false;
        if (!elementNotExists(iconAccountTypeRequire)) {
            isDisplayRequiredField = true;
        }
        extentLogger.logInfo(isDisplayRequiredField.toString());
        return isDisplayRequiredField;
    }

    public NDPFinanceInformationPage clickSubmitButton() {
        waitForAndClickElement(btnSubmit);
        extentLogger.logInfo("");
        return this;
    }

    public NDPFinanceInformationPage clickCancelButton() {
        waitForAndClickElement(btnCancel);
        extentLogger.logInfo("");
        return this;
    }

    public NDPFinanceInformationPage chooseAccountOpeningCertificate() {
        waitForAndClickElement(businessLicenseCertificate);
        extentLogger.logInfo("");
        return this;
    }

    public NDPFinanceInformationPage selectFileInPC(String fileName) {
        extentLogger.logInfo("");
        String path = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator;
        try {
            Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\Autoit\\FileUpload2.exe" + " " + System.getProperty("user.dir") + path + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        WaitUtils.idle(10000);
        return this;
    }

    public NDPFinanceInformationPage chooseBusinessLicenseCertificate() {
        waitForAndClickElement(accountOpeningCertificate);
        extentLogger.logInfo("");
        return this;
    }

    public boolean displayAccountHolderName() {
        extentLogger.logInfo("");
        return displayElement(txtAccountHolderName);
    }

    public boolean displayAccountHolderStreet1() {
        extentLogger.logInfo("");
        return displayElement(txtAccountHolderStreet1);
    }

    public boolean displayAccountHolderStreet2() {
        extentLogger.logInfo("");
        return displayElement(txtAccountHolderStreet2);
    }

    public boolean displayAccountHolderCity() {
        extentLogger.logInfo("");
        return displayElement(txtAccountHolderCity);
    }

    public boolean displayElement(By elm) {
        extentLogger.logInfo("");
        try {
            WebElement element = driver.findElement(elm);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean errorMessageAccountHolderStreet1() {
        extentLogger.logInfo("");
        try {
            WebElement mail = driver.findElement(txtErrorMessageAccountHolderStreet1);
            return mail.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean errorMessageAccountHolderStreet2() {
        extentLogger.logInfo("");
        try {
            WebElement mail = driver.findElement(txtErrorMessageAccountHolderStreet2);
            return mail.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean errorMessageBankName() {
        extentLogger.logInfo("");
        try {
            WebElement mail = driver.findElement(txtErrorMessageBankName);
            return mail.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean errorMessageAccountHolderName() {
        extentLogger.logInfo("");
        try {
            WebElement mail = driver.findElement(txtErrorMessageAccountHolderName);
            return mail.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }

    }

    public NDPFinanceInformationPage selectCountryRegion(String country) {
        selectValueOnDropDown(ddlCountryRegion, country);
        extentLogger.logInfo(country);
        return this;
    }


    public String getMessageSubmitSuccess() {
        String text = getText(txtMessageSuccess);
        extentLogger.logInfo(text);
        return text;
    }


    public NDPFinanceInformationPage enterIBANCode(String ibanCode) {
        waitForAndClickElement(txtIBANCode);
        typeText(txtIBANCode, ibanCode);
        typeTab(txtIBANCode);
        extentLogger.logInfo(ibanCode);
        return this;
    }

    public NDPFinanceInformationPage enterCLABENumber(String ibanCode) {
        waitForAndClickElement(txtCLABENumber);
        typeText(txtCLABENumber, ibanCode);
        typeTab(txtCLABENumber);
        extentLogger.logInfo(ibanCode);
        return this;
    }

    public NDPFinanceInformationPage enterBankRoutingCode(String ibanCode) {
        waitForAndClickElement(txtBankRoutingCode);
        typeText(txtBankRoutingCode, ibanCode);
        typeTab(txtBankRoutingCode);
        extentLogger.logInfo(ibanCode);
        return this;
    }

    public NDPFinanceInformationPage enterBankgiroNumber(String ibanCode) {
        waitForAndClickElement(txtBankgiroNumber);
        typeText(txtBankgiroNumber, ibanCode);
        typeTab(txtBankgiroNumber);
        extentLogger.logInfo(ibanCode);
        return this;
    }

    public NDPFinanceInformationPage enterBankSortCode(String ibanCode) {
        waitForAndClickElement(txtBankSortCode);
        typeText(txtBankSortCode, ibanCode);
        typeTab(txtBankSortCode);
        extentLogger.logInfo(ibanCode);
        return this;
    }


    public NDPFinanceInformationPage enterAccountHolderStreet2(String accountHolderStreet2) {
        waitForAndClickElement(txtAccountHolderStreet2);
        typeText(txtAccountHolderStreet2, accountHolderStreet2);
        typeTab(txtAccountHolderStreet2);
        extentLogger.logInfo(accountHolderStreet2);
        return this;
    }

    public NDPFinanceInformationPage enterAccountHolderSuburb(String accountHolderCity) {
        waitForAndClickElement(txtAccountHolderSuburb);
        typeText(txtAccountHolderSuburb, accountHolderCity);
        typeTab(txtAccountHolderSuburb);
        extentLogger.logInfo(accountHolderCity);
        return this;
    }

    public NDPFinanceInformationPage enterBankBIKCode(String accountNumber) {
        waitForAndClickElement(txtBankBIKCode);
        typeText(txtBankBIKCode, accountNumber);
        typeTab(txtBankBIKCode);
        extentLogger.logInfo(accountNumber);
        return this;
    }

    public NDPFinanceInformationPage enterAccountNumberCentral(String accountNumber) {
        waitForAndClickElement(txtAccountNumberCentralBank);
        typeText(txtAccountNumberCentralBank, accountNumber);
        typeTab(txtAccountNumberCentralBank);
        extentLogger.logInfo(accountNumber);
        return this;
    }

    public NDPFinanceInformationPage enterINN(String accountNumber) {
        waitForAndClickElement(txtINN);
        typeText(txtINN, accountNumber);
        typeTab(txtINN);
        extentLogger.logInfo(accountNumber);
        return this;
    }

    public NDPFinanceInformationPage enterBSBNumber(String accountNumber) {
        waitForAndClickElement(txtBSBNumber);
        typeText(txtBSBNumber, accountNumber);
        typeTab(txtBSBNumber);
        extentLogger.logInfo(accountNumber);
        return this;
    }

    public boolean displayErrorMessage() {
        extentLogger.logInfo("");
        if (!elementNotExists(txtMessageError)) {
            return true;
        } else {
            return false;
        }
    }

    public NDPFinanceInformationPage enterBankNumber(String branchOfficeName) {
        waitForAndClickElement(txtBankNumber);
        typeText(txtBankNumber, branchOfficeName);
        typeTab(txtBankNumber);
        extentLogger.logInfo(branchOfficeName);
        return this;
    }

    public NDPFinanceInformationPage enterBranchOfficeNumber(String branchOfficeName) {
        waitForAndClickElement(txtBranchOfficeNumber);
        typeText(txtBranchOfficeNumber, branchOfficeName);
        typeTab(txtBranchOfficeNumber);
        extentLogger.logInfo(branchOfficeName);
        return this;
    }


    public NDPFinanceInformationPage enterBankRoutingNumber(String branchOfficeName) {
        WebElement element = driver.findElement(txtBankRoutingNumber);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", element);

        //waitForAndClickElement(txtBankRoutingNumber);
        typeText(txtBankRoutingNumber, branchOfficeName);
        typeTab(txtBankRoutingNumber);
        extentLogger.logInfo(branchOfficeName);
        return this;
    }

    public NDPFinanceInformationPage enterACHRoutingNumber(String bicSwiftCode) {
        WebElement element = driver.findElement(txtACHRoutingNumber);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", element);
        //waitForAndClickElement(txtACHRoutingNumber);
        typeText(txtACHRoutingNumber, bicSwiftCode);
        typeTab(txtACHRoutingNumber);
        extentLogger.logInfo(bicSwiftCode);
        return this;
    }

    public NDPFinanceInformationPage clickNext() {
        WebElement element = driver.findElement(btnNext);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", element);
        extentLogger.logInfo("");
        return this;
    }

    public boolean displayAccountHolderPostalCode() {
        extentLogger.logInfo("");
        return displayElement(txtAccountHolderPostalCode);
    }

    public boolean displayCountryRegion() {
        extentLogger.logInfo("");
        return displayElement(elmCountryRegion);
    }

    public boolean displayAccountHolderState() {
        extentLogger.logInfo("");
        return displayElement(elmAccountHolderState);
    }

    public boolean displayAccountHolderSuburb() {
        extentLogger.logInfo("");
        return displayElement(txtAccountHolderSuburb);
    }

    public boolean displayBSBNumber() {
        extentLogger.logInfo("");
        return displayElement(txtBSBNumber);
    }

    public boolean displayAccountCurrency() {
        extentLogger.logInfo("");
        return displayElement(cboAccountCurrency);
    }

    public boolean displayIBANCode() {
        extentLogger.logInfo("");
        return displayElement(elmIBANCode);
    }

    public boolean displayAccountType() {
        extentLogger.logInfo("");
        return displayElement(ddlAccountType);
    }

    public boolean displayBankName() {
        extentLogger.logInfo("");
        return displayElement(txtBankName);
    }

    public boolean displayCLABENumber() {
        extentLogger.logInfo("");
        return displayElement(txtCLABENumber);
    }

    public boolean displayBankRoutingCode() {
        extentLogger.logInfo("");
        return displayElement(txtBankRoutingCode);
    }

    public boolean displayBankBIKCode() {
        extentLogger.logInfo("");
        return displayElement(txtBankBIKCode);
    }

    public boolean displayBICSwiftCode() {
        extentLogger.logInfo("");
        return displayElement(txtBICSwiftCode);
    }

    public boolean displayACHRoutingNumber() {
        extentLogger.logInfo("");
        return displayElement(txtACHRoutingNumber);
    }


    public NDPFinanceInformationPage clearTextAccountHolderName() {
        clearTextValue(txtAccountHolderName);
        extentLogger.logInfo("");
        return this;
    }

    public NDPFinanceInformationPage clearTextAccountHolderStreet1() {
        clearTextValue(txtAccountHolderStreet1);
        extentLogger.logInfo("");
        return this;
    }

    public NDPFinanceInformationPage clearTextAccountHolderStreet2() {
        clearTextValue(txtAccountHolderStreet2);
        extentLogger.logInfo("");
        return this;
    }

    public NDPFinanceInformationPage clearTextBankName() {
        clearTextValue(txtBankName);
        extentLogger.logInfo("");
        return this;
    }

    public NDPFinanceInformationPage enterAccountHolderName(String accountName) {
        waitForAndClickElement(txtAccountHolderName);
        typeText(txtAccountHolderName, accountName);
        typeTab(txtAccountHolderName);
        extentLogger.logInfo(accountName);
        return this;
    }

    public String getMessageAccountHolderName() {
        String text = getText(txtErrorMessageAccountHolderName);
        extentLogger.logInfo(text);
        return text;
    }

    public String getMessageAccountHolderStreet1() {
        String text = getText(txtErrorMessageAccountHolderStreet1);
        extentLogger.logInfo(text);
        return text;
    }

    public String getMessageAccountHolderStreet2() {
        String text = getText(txtErrorMessageAccountHolderStreet2);
        extentLogger.logInfo(text);
        return text;
    }

    public String getMessageBankName() {
        String text = getText(txtErrorMessageBankName);
        extentLogger.logInfo(text);
        return text;
    }
    public boolean displayAccountNumberCentralBank() {
        extentLogger.logInfo("");
        return displayElement(txtAccountNumberCentralBank);
    }

    public boolean displayINN() {
        extentLogger.logInfo("");
        return displayElement(txtINN);
    }


    public boolean displayKPP() {
        extentLogger.logInfo("");
        return displayElement(txtKPP);
    }

    public boolean displayBankgiroNumber() {
        extentLogger.logInfo("");
        return displayElement(txtBankgiroNumber);
    }

    public boolean displayBankSortCode() {
        extentLogger.logInfo("");
        return displayElement(txtBankSortCode);
    }

    public boolean displayBankNumber() {
        extentLogger.logInfo("");
        return displayElement(txtBankNumber);
    }

    public boolean displayBranchOfficeNumber() {
        extentLogger.logInfo("");
        return displayElement(txtBranchOfficeNumber);
    }
    public boolean displayBankRoutingNumber() {
        extentLogger.logInfo("");
        return displayElement(txtBankRoutingNumber);
    }


    public boolean displayBranchOfficeName() {
        extentLogger.logInfo("");
        return displayElement(txtBranchOfficeName);
    }

    public boolean displayAccountNumber() {
        extentLogger.logInfo("");
        return displayElement(txtAccountNumber);
    }

    public NDPFinanceInformationPage clickViewDetail() {
        waitForAndClickElement(lnkViewDetails);
        extentLogger.logInfo("");
        return this;
    }

    public NDPFinanceInformationPage clickEdit() {
        waitForAndClickElement(btnEdit);
        extentLogger.logInfo("");
        return this;
    }

    public String getRandomText(String stringChar) {
        Random gen = new Random();
        Random numberCharacterInput = new Random();
        StringBuilder sb = new StringBuilder();
        int number = numberCharacterInput.nextInt((75 - 1) + 1) + 1; //chọn random chieu dai input text trong khoang tu 0-75 ki tu
        extentLogger.logInfo("Random chieu dai cua inputText: " + number);
        for (int i = 0; i < number; i++) {
            int nb = gen.nextInt(stringChar.length() - 1);
            char ch = stringChar.charAt(nb);
            sb.append(ch);
        }
        return sb.toString();
    }

    public String randomDigits(String stringChar, int max, int min) {//6 digits
        Random gen = new Random();
        Random numberCharacterInput = new Random();
        StringBuilder sb = new StringBuilder();
        int number = numberCharacterInput.nextInt((max - min) + 1) + min; //chọn random chieu dai input text trong khoang tu min-max ki tu
        extentLogger.logInfo("Random of length inputText: " + number);
        for (int i = 0; i < number; i++) {
            int nb = gen.nextInt(stringChar.length() - 1);
            char ch = stringChar.charAt(nb);
            sb.append(ch);
        }
        return sb.toString();
    }
    public String getTextCurrency() {
        String text = getText(txtConfirmAccountCurrency);
        extentLogger.logInfo(text);
        return text;
    }
    public boolean formFinanceInformationIsDisplay() {
        extentLogger.logInfo("");
        try {
            WebElement mail = driver.findElement(formFinanceInformation);
            return mail.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
