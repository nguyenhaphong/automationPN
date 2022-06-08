package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LotcheckChangeSubmissionJudgment extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@data-testid='sort-test-plans-modal']");
    private final By btnConfirm = By.xpath("//form[@data-testid='set-planned-test-date-modal-form']//button[@data-testid='submit-button']");
    private final By btnCancel = By.xpath("//form[@data-testid='set-planned-test-date-modal-form']//button[@class='btn btn-secondary mr-auto']");
    private final By iconDdlNewJudgment = By.xpath("//i[@class='dropdown icon']");
    private final By selectedNewJudgment = By.xpath("//div[@class='text']");
    private final By modalTitle = By.xpath("//h4[@class='modal-title  additional-info']");

    public LotcheckChangeSubmissionJudgment(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public LotcheckReportMatrix clickConfirmButton() {
        waitForElementVisible(btnConfirm, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        waitForAndClickElement(btnConfirm);
        extentLogger.logInfo("");
        waitForElementNotVisible(btnCancel, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return getNewPage(LotcheckReportMatrix.class);
    }

    public String getModalTitle() {
        String value = getText(modalTitle).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckChangeSubmissionJudgment selectNewJudgment(String newJudgment) {
        waitForAndClickElement(iconDdlNewJudgment);
        waitForAndClickElement(By.xpath(String.format("//span[contains(text(),'%s')]", newJudgment)));
        extentLogger.logInfo(newJudgment);
        return this;
    }

    public String getSelectedNewJudgment() {
        String value = getText(selectedNewJudgment).trim();
        extentLogger.logInfo(value);
        return value;
    }


}
