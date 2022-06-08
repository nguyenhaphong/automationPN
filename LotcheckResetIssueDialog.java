package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LotcheckResetIssueDialog extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@data-testid='action-confirmation-modal']");
    private final By lblPublishedDescriptionTitle = By.xpath("//div[@class='reset-issue-modal-title'][2]");
    private final By lblPublishedDescriptionEN = By.xpath("//div[@class='reset-issue-modal-value second']//li[@class='en']");
    private final By lblPublishedDescriptionJP = By.xpath("//div[@class='reset-issue-modal-value second']//li[@class='ja']");

    public LotcheckResetIssueDialog(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public String getPublishedDescriptionTitle() {
        String value = getText(lblPublishedDescriptionTitle);
        extentLogger.logInfo(value);
        return value;
    }

    public String getPublishedDescriptionEN() {
        String value = getText(lblPublishedDescriptionEN);
        extentLogger.logInfo(value);
        return value;
    }

    public String getPublishedDescriptionJP() {
        String value = getText(lblPublishedDescriptionJP);
        extentLogger.logInfo(value);
        return value;
    }
    public LotcheckResetIssueDialog ScreenPopup (){
        String parentHandle = driver.getWindowHandle();
        waitForElementVisible(uniqueElement);
        driver.switchTo().window(parentHandle);
        //driver.close();
        for (String winHandle : driver.getWindowHandles())
            driver.switchTo().window(winHandle);
        extentLogger.logInfoWithScreenshot("Manual check content");
        return this;
    }
}
