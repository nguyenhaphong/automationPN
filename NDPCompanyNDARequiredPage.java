package net.nintendo.automation.ui.models.ndp;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NDPCompanyNDARequiredPage extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//span[contains(text(),'Company NDA Required')]");
    private static final String pageTitle = "Company NDA Required - Nintendo Developer Portal";

    private final By cboByClickingTheSubmit = By.xpath("//input[@id='_noaacceptglobalnda_WAR_noaomasportlet_viewAgreementCheckbox']");
    private final By btnSubmit = By.xpath("//button[@id='_noaacceptglobalnda_WAR_noaomasportlet_acceptButton']");
    private static By alertError = By.xpath("//div[@class='alert alert-error']");

    public NDPCompanyNDARequiredPage(WebDriver driver) {
        super(driver, uniqueElement, pageTitle);
    }

    public NDPCompanyNDARequiredPage selectByClickingTheSubmitButtonCheckbox() {
        selectCheckbox(cboByClickingTheSubmit, "true");
        extentLogger.logInfo("");
        return this;
    }

    public NDPAcceptanceConfirmationPage clickSubmitButton() {
        waitForAndClickElement(btnSubmit);
        extentLogger.logInfo("");
        return getNewPage(NDPAcceptanceConfirmationPage.class);
    }
    public boolean clickSubmitButtonWithError() {
        Boolean isError;
        waitForAndClickElement(btnSubmit);
        waitForElementNotVisible(btnSubmit, WaitTimes.WAIT_FOR_DOCUMENT_READY_TIMEOUT_SECS);
        if (elementNotExists(alertError)){
            isError=false;
        }
        else
        {
            isError=true;
        }
        extentLogger.logInfo(isError.toString());
        return isError;
    }
    public NDPAcceptanceConfirmationPage afterClickSubmitButtonNotError() {
        return getNewPage(NDPAcceptanceConfirmationPage.class);
    }

}
