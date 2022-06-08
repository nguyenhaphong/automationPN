package net.nintendo.automation.ui.models.lcms;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LotcheckEditAccountPreferences extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//h5[@class='modal-title  additional-info']");
    private final By ddlTimeZone = By.xpath("//label[text()='Time Zone']/..//select[@id='dropdown']");
    private final By btnSave = By.xpath("//div[@data-testid='account-modal']//button[@type='submit']");
    private final By btnCancel = By.xpath("//div[@data-testid='account-modal']//button[@data-testid='set-planned-test-date-modal-cancel']");
    private final By ddlLanguage = By.xpath("//div[@class='modal-body']//div[@class='form-group row'][1]//select[@id='dropdown']");
    private final By lblAlert = By.xpath("//*[@role='alert']");
    private final By ddlJapanLanguage = By.xpath("//label[text()='言語設定']/..//select[@id='dropdown']");
    private final By titleLanguage = By.xpath("//label[@for='scheduleList' and text()='Language' or text()='言語設定']");

    public LotcheckEditAccountPreferences(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public LotcheckEditAccountPreferences selectTimeZone(String time) {
        selectValueOnDropDown(ddlTimeZone, time);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckEditAccountPreferences clickSaveButton() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnSave);
        return this;
    }

    public LotcheckEditAccountPreferences clickCancelButton() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnCancel);
        return this;
    }

    public LotcheckEditAccountPreferences selectLanguage(String language) {
        selectValueOnDropDown(ddlLanguage, language);
        extentLogger.logInfo("");
        return this;
    }

    public String getCurrentLanguage() {
        String currentLanguage = getSelectedValue(ddlLanguage);
        extentLogger.logInfo(currentLanguage);
        return currentLanguage;
    }

    public LotcheckEditAccountPreferences waitAssignTaskSuccess() {
        waitForElementNotVisible(lblAlert);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckEditAccountPreferences selectLanguageJP(String language) {
        selectValueOnDropDown(ddlJapanLanguage, language);
        extentLogger.logInfo(language);
        return this;
    }

    public LotcheckEditAccountPreferences selectUserLanguage(String language) {
        selectValueOnDropDown(ddlJapanLanguage, language);
        extentLogger.logInfo(language);
        return this;
    }

    public String getTitleLanguage() {
        String value = getText(titleLanguage);
        extentLogger.logInfo(value);
        return value;
    }

    public boolean isCurrentLanguageEnglish() {
        boolean value = false;
        if (getSelectedValue(ddlLanguage).equals("English")) {
            value = true;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public boolean isCurrentTimeZone(String timeZone) {
        boolean value = false;
        if (getSelectedValue(ddlTimeZone).equals(timeZone)) {
            value = true;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }


}
