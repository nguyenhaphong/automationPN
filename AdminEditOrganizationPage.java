package net.nintendo.automation.ui.models.admin;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AdminEditOrganizationPage extends CommonUIComponent {
    private static final By btnCustomFields = By.xpath("//a[contains(@id,'_customFieldsLink')]");
    private final By ddlChinaAgreementExempt = By.xpath("//select[@id='ozyz_null_null_china_2d_agreement_2d_exempt']");
    private final By ddlChinaAgreementExemptOptions = By.xpath("//select[@id='ozyz_null_null_china_2d_agreement_2d_exempt']//option");
    private final By chinaAgreementExemptField = By.xpath("//input[@value='china-agreement-exempt']/preceding-sibling::span[text()=' China Agreement Exempt ' ]");
    private final By btnSave = By.xpath("//button[@class='btn btn-primary']");

    public AdminEditOrganizationPage(WebDriver driver) {
        super(driver, btnCustomFields);
    }

    public AdminEditOrganizationPage clickCustomFields() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnCustomFields);
        return this;
    }

    public String getSelectedValueOnChinaAgreementExemptField() {
        String value = getSelectedValue(ddlChinaAgreementExempt).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public AdminEditOrganizationPage clickChinaAgreementExempt() {
        extentLogger.logInfo("");
        waitForAndClickElement(ddlChinaAgreementExempt);
        return this;
    }

    public String getOptionsOnChinaAgreementExemptDropdown() {
        String value = "";
        List<WebElement> elements = findElements(ddlChinaAgreementExemptOptions);
        int i = 1;
        for (WebElement element : elements) {
            if (i == elements.size()) {
                value = value + element.getText().trim();
            } else {
                value = value + element.getText().trim() + ", ";
                i++;
            }
        }
        extentLogger.logInfo(value);
        return value;
    }

    public boolean isChinaAgreementExemptDisplayed() {
        boolean value = !elementNotExists(chinaAgreementExemptField);
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public AdminEditOrganizationPage selectChinaAgreementExempt(String value) {
        selectValueOnDropDown(ddlChinaAgreementExempt, value);
        extentLogger.logInfo(value);
        return this;
    }

    public AdminEditOrganizationPage clickSave() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnSave);
        waitDocumentReady();
        return this;
    }
}
