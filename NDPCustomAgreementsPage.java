package net.nintendo.automation.ui.models.ndp;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.utils.WaitUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class NDPCustomAgreementsPage extends CommonUIComponent {
    private final By ddlPlatform = By.xpath("//select[@id='_noacustomagreementsportlet_WAR_noacompanyadminportlet_platform']");
    private final By ddlAgreementType = By.xpath("//select[@id='_noacustomagreementsportlet_WAR_noacompanyadminportlet_agreementType']");
    private final By ddlRegion = By.xpath("//select[@id='_noacustomagreementsportlet_WAR_noacompanyadminportlet_region']");
    private final By btnUpload = By.xpath("//label[@class='nin-btn nin-btn-upload']");
    private final By txtAgreementPdf = By.xpath("//input[@class='field nin-upload-filename nin-input-file']");
    private final By txtExecutionDate = By.xpath("//input[@id='_noacustomagreementsportlet_WAR_noacompanyadminportlet_executionDate']");
    private final By txtExpirationDate = By.xpath("//input[@id='_noacustomagreementsportlet_WAR_noacompanyadminportlet_expirationDate']");
    private final By ddlAgreementApprover = By.xpath("//select[@id='_noacustomagreementsportlet_WAR_noacompanyadminportlet_agreementApprover']");
    private final By btnSubmit = By.xpath("//div[@class='button-holder ']//button[@id='_noacustomagreementsportlet_WAR_noacompanyadminportlet_submitButton']");
    private final By lblAlertSuccess = By.xpath("//div[@class='alert alert-success']");
    private static final By searchIcon = By.xpath("//div[@class='nin-search-btn btn select-partner-button']//i[@class='icon-search']");

    public NDPCustomAgreementsPage(WebDriver driver) {
        super(driver, searchIcon);
    }

    public NDPFindPartnerModal clickSearchIcon() {
        waitForAndClickElement(searchIcon);
        extentLogger.logInfo("");
        return getNewPage(NDPFindPartnerModal.class);
    }

    public NDPCustomAgreementsPage selectPlatform(String platform) {
        selectValueOnDropDown(ddlPlatform, platform);
        extentLogger.logInfo(platform);
        return this;
    }

    public NDPCustomAgreementsPage selectAgreementType(String agreementType) {
        selectValueOnDropDown(ddlAgreementType, agreementType);
        extentLogger.logInfo(agreementType);
        return this;
    }

    public NDPCustomAgreementsPage selectRegion(String region) {
        selectValueOnDropDown(ddlRegion, region);
        extentLogger.logInfo(region);
        return this;
    }

    public NDPCustomAgreementsPage selectFileInPC(String fileName) {
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

    public NDPCustomAgreementsPage setExecutionDate(int numberDays) {
        waitForAndClickElement(txtExecutionDate);
        new CalendarModal(this.driver).chooseDateInCalender(numberDays);
        removeFocusFromElement();
        extentLogger.logInfo("Added (days): " + numberDays);
        return this;
    }

    public NDPCustomAgreementsPage setExpirationDate(int numberDays) {
        waitForAndClickElement(txtExpirationDate);
        new CalendarModal(this.driver).chooseDateInCalender(numberDays);
        removeFocusFromElement();
        extentLogger.logInfo("Added (days): " + numberDays);
        return this;
    }

    public NDPCustomAgreementsPage selectAgreementApprove() {
        List<WebElement> elements = findElements(ddlAgreementApprover);
        elements.get(0).sendKeys(Keys.ENTER);
        elements.get(0).sendKeys(Keys.ARROW_DOWN);
        elements.get(0).sendKeys(Keys.ENTER);
        extentLogger.logInfo("");
        return this;
    }

    public NDPCustomAgreementsPage clickSubmit() {
        waitForAndClickElement(btnSubmit);
        waitDocumentReady();
        extentLogger.logInfo("");
        return this;
    }

    public String getSuccessMessage() {
        waitForElementVisible(lblAlertSuccess, WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        String value = getText(lblAlertSuccess).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public NDPTopNavigationBar navTopNavigationBar() {
        extentLogger.logInfo("");
        return getNewPage(NDPTopNavigationBar.class);
    }

    public NDPCustomAgreementsPage clickUpload() {
        waitForAndClickElement(btnUpload);
        extentLogger.logInfo("");
        return this;
    }
}
