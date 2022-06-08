package net.nintendo.automation.ui.models.ndp;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NDPGettingStartedPage extends CommonUIComponent {
    private final static By uniqueElement = By.xpath("//a[@class='dropdown-toggle' and contains(@href,'group/development/getting-started')]");
    private final static String pageTitle = "Getting Started - Nintendo Developer Portal";
    private By userMenu = By.xpath("//li[@class='nin-user-menu dropdown']//i[@class='icon-caret-down visible-desktop']");
    private final By btnSignOut = By.cssSelector("div[id='wrapper']  a[href*='/logout']");

    public NDPGettingStartedPage(WebDriver driver) {
        super(driver, uniqueElement, pageTitle);
    }

    public NDPGettingStartedPage clickUserMenuDropdownList() {
        waitForAndClickElement(userMenu);
        extentLogger.logInfo("");
        return this;
    }

    public NDPHomePage clickSignOutButton() {
        waitForAndClickElement(btnSignOut);
        extentLogger.logInfo("");
        return getNewPage(NDPHomePage.class);
    }

    public NDPTopNavigation navTopNavigation(){
        extentLogger.logInfo("");
        return getNewPage(NDPTopNavigation.class);
    }


}
