package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LotcheckReportProductMessage extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@data-testid='messages-container']");
    private static final String pageTitle = "Messages Product: LCR - LCMS";
    private final By lblMessageNewThread = By.xpath("//*[@data-testid='message-thread-row']//td[1]");
    private final By btnViewThread = By.xpath("//*[@data-testid='message-thread-row']//td[4]//a");

    public LotcheckReportProductMessage(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public String getMessageInProductMessageTab() {
        String message = getText(lblMessageNewThread);
        extentLogger.logInfo("Message display in Product Message Tab: " + message);
        return message;
    }

    public LotcheckReportProductViewThreadMesage clickViewThread() {
        waitForAndClickElement(btnViewThread);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportProductViewThreadMesage.class);
    }
}

