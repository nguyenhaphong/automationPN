package net.nintendo.automation.ui.models.lcms;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Stack;

public class LotcheckTestPlanActionConfirmationModal extends CommonUIComponent {
    private static final By uniqueElement = By.cssSelector("div.modal-content.modal-width");
    private static final String pageTitle = "";
    private final By btnCancel = By.cssSelector("button.btn.btn-secondary.mr-auto");
    private final By lblTitle = By.cssSelector("h5.modal-title.confirmation-title");
    private final By lblProductInfo = By.cssSelector("div.product-info");
    private final By btnConfirm = By.xpath("//div[@data-testid='action-confirmation-modal']//button[@data-testid='submit-button']");
    private static By lblWarningMessage = By.xpath("//div[@data-testid='lotcheck-issue-danger']");
    private final By lblWarningMessageWhenCancelledSubmission = By.xpath("//div[@class='alert alert-danger']");

    public LotcheckTestPlanActionConfirmationModal(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public Stack<String> getModalText() {
        extentLogger.logInfo("");
        Stack<String> textValues = new Stack<>();
        String header = getText(lblTitle);
        String body = getText(lblProductInfo);

        textValues.push(header);
        textValues.push(body);

        return textValues;
    }

    public LotcheckTestPlanRow clickCancel() {
        waitForAndClickElement(btnCancel);
        extentLogger.logInfo("");
        return getNewPage(LotcheckTestPlanRow.class);
    }

    public LotcheckQueuePage clickConfirm() {
        waitForElementClickable(btnConfirm, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        this.waitForAndClickElement(btnConfirm);
        extentLogger.logInfo("");
        waitForElementNotVisible(btnCancel, WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        return getNewPage(LotcheckQueuePage.class);
    }

    public String getWarningMessage() {
        String warningMessageValue = getText(lblWarningMessage);
        extentLogger.logInfo(warningMessageValue);
        return warningMessageValue;
    }

    public boolean isConfirmButtonClickable() {
        waitForElementVisible(btnConfirm, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        Boolean isClickable = driver.findElement(btnConfirm).isEnabled();
        extentLogger.logInfo(isClickable.toString());
        return isClickable;
    }
    public boolean isWarningMessageDisplayed() {
        boolean isDisplayed = driver.findElement(lblWarningMessage).isDisplayed();
        extentLogger.logInfo(String.valueOf(isDisplayed));
        return isDisplayed;
    }

    public boolean isWarningMessageWhenCancelledSubmissionDisplayed() {
        boolean isDisplayed = driver.findElement(lblWarningMessageWhenCancelledSubmission).isDisplayed();
        extentLogger.logInfo(String.valueOf(isDisplayed));
        return isDisplayed;
    }

    public String getWarningMessageWhenCancelledSubmission() {
        String warningMessageValue = getText(lblWarningMessageWhenCancelledSubmission);
        extentLogger.logInfo(warningMessageValue);
        return warningMessageValue;
    }

}
