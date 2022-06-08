package net.nintendo.automation.ui.models.d4c;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class D4CRomDetails extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//i[@class='fa fa-external-link-square']");
    private final By btnSubmit = By.xpath("//button[@class='btn btn-outline-primary']//span[text()='Submit']");
    private final By btnReject = By.xpath("//button[@class='btn btn-outline-danger']//span[text()='Reject']");
    private final By txtProductCode = By.xpath("//input[@id='productCode']");
    private final By btnConfirmInROMSubmitRequestForm = By.xpath("//button[@type='submit']");
    private final By btnOkInRomSubmitRequestForm = By.xpath("//div[@class='modal-footer']//button[@type='submit']");
    private final By btnOkInRomApprovalRequestForm = By.xpath("//button[@type='submit']");
    private final By pleaseWaitBlock = By.xpath("//div[@class='blockUI blockMsg blockPage']//h1");

    public D4CRomDetails(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public D4CRomDetails clickSubmitButton() {
        waitForAndClickElement(btnSubmit);
        extentLogger.logInfo("Submit ROM");
        return this;
    }

    public D4CRomDetails clickRejectButton() {
        waitForAndClickElement(btnReject);
        extentLogger.logInfo("Reject ROM");
        return this;
    }

    public D4CRomDetails enterProductCodeTextbox(String enterProductCode) {
        waitForAndClickElement(txtProductCode);
        typeText(txtProductCode, enterProductCode);
        typeTab(txtProductCode);
        extentLogger.logInfo("Enter Product Code");
        return this;
    }

    public D4CRomDetails clickConfirmButtonInROMSubmitRequestForm() {
        waitForAndClickElement(btnConfirmInROMSubmitRequestForm);
        extentLogger.logInfo("Click Confirm Button");
        return this;
    }

    public D4CRomDetails clickOKButtonInROMSubmitRequestForm() {
        waitForAndClickElement(btnOkInRomSubmitRequestForm);
        extentLogger.logInfo("Click OK Button");
        return this;
    }

    public D4CRomDetails clickOKButtonInROMApprovalRequestForm() {
        waitForAndClickElement(btnOkInRomApprovalRequestForm);
        extentLogger.logInfo("Click OK Button");
        return this;
    }

    public D4CRomDetails waitingForSubmitRequestFormLoad() {
        extentLogger.logInfo("");
        waitForElementNotVisible(pleaseWaitBlock, Duration.ofMinutes(10));
        return this;
    }

}

