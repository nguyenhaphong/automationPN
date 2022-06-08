package net.nintendo.automation.ui.models.ndp;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NDPActionBar extends CommonUIComponent {
    private final static By uniqueElement = By.id("wrapper");
    private final By lnkAccountManage = By.cssSelector("div[id='wrapper']  a[href*='/development/account']");
    private final By btnSignOut = By.cssSelector("div[id='wrapper']  a[href*='/logout']");
    private final By mnuUser = By.xpath("//li[@class='nin-user-menu dropdown']//i[@class='icon-caret-down visible-desktop']");

    public NDPActionBar(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public NDPActionBar clickUserMenuDropdownList() {
        waitForAndClickElement(mnuUser);
        extentLogger.logInfo("");
        return getNewPage(NDPActionBar.class);
    }

    public NDPHomePage clickSignOutButton() {
        waitForAndClickElement(btnSignOut);
        extentLogger.logInfo("");
        return getNewPage(NDPHomePage.class);
    }
}
