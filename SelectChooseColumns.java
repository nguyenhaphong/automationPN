package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SelectChooseColumns extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@data-testid='choose-columns-modal']");
    private final By cbPublisher = By.xpath("//input[@id='publisherName.Publisher']");
    private final By btConfirm = By.xpath("//button[@class='btn btn-primary float-right']");

    public SelectChooseColumns(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public SelectChooseColumns selectPublisher() {
        selectCheckbox(cbPublisher, "true");
        extentLogger.logInfo("select Publisher checkbox");
        return this;
    }

    public SelectChooseColumns clickConfirmButton() {
        waitForAndClickElement(btConfirm);
        return this;
    }

}
