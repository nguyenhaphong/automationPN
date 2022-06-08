package net.nintendo.automation.ui.models.admin;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminHomePage extends CommonUIComponent {
    private static final By uniqueId = By.cssSelector("div.nin-global-breadcrumb");
    private static final String page_title = "Homepage - Nintendo Developer Portal";

    private final By btnSignIn = By.xpath("//a[@class='sign-in']");
    //private final By btnSignIn = By.xpath("//a[contains(@class,'sign-in')]");

    private final By btnGetRegister = By.xpath("//div[contains(@class,'nin-sign-in')]//a[@href='/register']//span");
    private final By btnSubmit = By.cssSelector("button[type='submit']");

    public AdminHomePage(WebDriver driver) {
        super(driver, uniqueId, page_title);
    }

    public AdminHomePage acceptCookie() {
        waitForAndClickElement(By.cssSelector("a.cookie-accept-button"));
        extentLogger.logInfo("");
        this.waitIsLoaded();
        extentLogger.logInfo("");
        return this;
    }

    /* public AdminSignInPage clickSignInPage() {
          hoverMouse(btnSignIn);
          extentLogger.logInfo("");
          waitForAndClickElement(btnSignIn);
          return getNewPage(AdminSignInPage.class);
      }
    */
    public AdminSignInPage clickSignInPage() {
        hoverMouse(btnSignIn);
        extentLogger.logInfo("");
        waitForAndClickElement(btnSignIn);
        if (elementNotExists(btnSubmit)) {
            extentLogger.logInfo("");
            waitForAndClickElement(btnSignIn);
        } else {
            extentLogger.logInfo("");
        }
        return getNewPage(AdminSignInPage.class);
    }

    public boolean registerButtonIsTrue() {
        waitForElementVisible(btnGetRegister, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return true;
    }

    public boolean registerButtonIsFalse() {
        waitForElementNotVisible(btnGetRegister, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return true;
    }

    public boolean submitButtonDisplay() {
        waitForElementVisible(btnSubmit, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return true;
    }
}
