package net.nintendo.automation.ui.models.ndp;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NDPConfirmationPage extends CommonUIComponent {
    private static final By uniqueElement = By.className("journal-content-article");
    private static final String pageTitle = "Confirmation - Nintendo Developer Portal";
    private final By btnSignIn = By.cssSelector("a.sign-in");

    public NDPConfirmationPage(WebDriver driver) {
        super(driver, uniqueElement, pageTitle);
    }

    public NDPSignInPage clickSignInPage() {
        waitForAndClickElement(btnSignIn);
        extentLogger.logInfo("");
        return getNewPage(NDPSignInPage.class);
    }
}
