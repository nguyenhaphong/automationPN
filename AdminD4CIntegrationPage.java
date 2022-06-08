package net.nintendo.automation.ui.models.admin;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminD4CIntegrationPage extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//a[@title='D4C Integration Admin']");

    private final By btnD4cIntegrationAdmin = By.xpath("//a[contains(@href,'noad4cintegrationadminportlet_WAR_noaromsubmissionportlet')]");
    private final By romIdSubmitROMCheckResults = By.xpath("//h5[text()='Submit ROM Check Results']/..//div//input");
    private final By submitButtonROMCheckResults = By.xpath("//h5[text()='Submit ROM Check Results']/..//section//button");

    public AdminD4CIntegrationPage(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public AdminD4CIntegrationPage submitROMCheckResults(String romId) {
        extentLogger.logInfo(romId);
        typeText(romIdSubmitROMCheckResults,romId);
        waitForAndClickElement(submitButtonROMCheckResults);
        extentLogger.logInfo("");
        driver.navigate().refresh();
        return this;
    }
}
