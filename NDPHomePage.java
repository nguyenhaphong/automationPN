package net.nintendo.automation.ui.models.ndp;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NDPHomePage extends CommonUIComponent {
    private static final By uniqueId = By.cssSelector("div.nin-global-breadcrumb");
    private static final String page_title = "Homepage - Nintendo Developer Portal";
    private static By lnkMyProducts = By.cssSelector("a[href*='group/development/products']");
    private final By btnRegister = By.xpath("//div[contains(@class,'nin-sign-in')]//a[@href='/register']");

    public NDPHomePage(WebDriver driver) {
        super(driver, uniqueId);
    }

    public NDPHomePage acceptCookie() {
        waitForAndClickElement(By.cssSelector("a.cookie-accept-button"));
        extentLogger.logInfo("");
        this.waitIsLoaded();
        extentLogger.logInfo("");
        return this;
    }

    public NDPSignInPage clickSignInPage() {
        waitForAndClickElement(By.cssSelector("a.sign-in"));
        if (!elementNotExists(By.cssSelector("a.sign-in"))) {
            waitForAndClickElement(By.cssSelector("a.sign-in"));
        }
        extentLogger.logInfo("");
        return getNewPage(NDPSignInPage.class);
    }

    public NDPRegisterPage clickRegisterPage() {
        waitForAndClickElement(btnRegister);
        extentLogger.logInfo("");
        return getNewPage(NDPRegisterPage.class);
    }

    public NDPMyProductsPage clickMyProducts() {
        waitForAndClickElement(lnkMyProducts);
        extentLogger.logInfo("");
        return getNewPage(NDPMyProductsPage.class);
    }

    public String getURL() {
        String url = driver.getCurrentUrl();
        extentLogger.logInfo("url");
        return url;
    }

}
