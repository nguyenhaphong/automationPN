package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LotcheckReportProductViewThreadMesage extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@data-testid='message-thread-container']");
    private static final String pageTitle = "View Thread Messages Product: LCR - LCMS";
    private static final By lblPageTitle = By.xpath("//title");
    private static final By lblMessage = By.xpath("//*[@data-testid='message-thread-container']//h2");

    public LotcheckReportProductViewThreadMesage(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public String getPageTitle() {
        extentLogger.logInfo("");
        WebElement element = driver.findElement(lblPageTitle);
        String pageTitle = element.getAttribute("innerHTML");
        return pageTitle;
    }

    public String getMessage() {
        String value = getText(lblMessage);
        extentLogger.logInfo(value);
        return value;
    }
}
