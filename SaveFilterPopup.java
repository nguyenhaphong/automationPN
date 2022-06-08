package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SaveFilterPopup extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@data-testid='choose-columns-modal']");
    private final By txtFilterName = By.xpath("//input[@data-testid='filter-name']");
    private final By cbSetAsDefault = By.xpath("//input[@id='setAsDefault']");
    private final By btSave = By.xpath("//button[@class='btn btn-primary float-right']");

    public SaveFilterPopup(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public SaveFilterPopup inputFilterName(String productName) {
        waitForAndClickElement(txtFilterName);
        typeText(txtFilterName, productName);
        typeTab(txtFilterName);
        extentLogger.logInfo(productName);
        return this;
    }

    public SaveFilterPopup selectSetAsDefault() {
        selectCheckbox(cbSetAsDefault, "true");
        extentLogger.logInfo("select Set As Default");
        return this;
    }

    public SaveFilterPopup clickSaveButton() {
        waitForAndClickElement(btSave);
        return this;
    }
}
