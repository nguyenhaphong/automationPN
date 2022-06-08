package net.nintendo.automation.ui.models.ndp;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NDPAcceptanceConfirmationPage extends CommonUIComponent {
    private static final By uniqueElement = By.className("journal-content-article");
    private static final String pageTitle = "Acceptance Confirmation - Nintendo Developer Portal";

    private final By hereLink = By.cssSelector("a[href*='group/development/getting-started']");

    public NDPAcceptanceConfirmationPage(WebDriver driver) {
        super(driver, uniqueElement, pageTitle);
    }

    public NDPGettingStartedPage clickHereLink() {
        openLinkInSameTab(hereLink);
        extentLogger.logInfo("");
        return getNewPage(NDPGettingStartedPage.class);
    }
}