package net.nintendo.automation.ui.models.d4c;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class D4CRomTab extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//input[@id='applicationId']");
    private final By txtApplicationID = By.xpath("//input[@id='applicationId']");
    private final By btnSearch = By.xpath("//button[@class='btn btn-primary']");
    private final By rowTable = By.xpath("//tr[@class='odd']");
    private final By statusElement = By.xpath("//tr[@class='odd']//td[3]");
    private final By romIdElement = By.xpath("//tr[@class='odd']//td[1]");

    public D4CRomTab(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public D4CRomTab enterApplicationID(String valueSearch) {
        extentLogger.logInfo(valueSearch);
        waitForAndClickElement(txtApplicationID);
        typeText(txtApplicationID, valueSearch);
        typeTab(txtApplicationID);
        return this;
    }

    public D4CRomTab clickSearchButton() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnSearch);
        waitForElementVisible(rowTable, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return this;
    }

    public String getStatusROM() {
        String status = getText(statusElement);
        extentLogger.logInfo(status);
        return status;
    }

    public String getRomId() {
        String romID = getText(romIdElement);
        extentLogger.logInfo(romID);
        return romID;
    }

    public D4CRomDetails clickROMByID(String romID) {
        String selector = String.format("//tr[@class='odd']//td[1]/a[text()='%s']", romID);
        waitForAndClickElement(By.xpath(selector));
        extentLogger.logInfo(romID);
        return getNewPage(D4CRomDetails.class);

    }
}
