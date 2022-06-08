package net.nintendo.automation.ui.models.admin;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminDevelopmentHome extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//a[contains(@href, 'home/announcements')]");
    private static final String page_title = "HOME - Nintendo Developer Portal";

    private final By lnkMenuAdmin = By.xpath("//a[@title='Admin']");
    private final By lnkControlPanel = By.xpath("//a[contains(@title,'Control Panel')]");
    private final By lnkMenuUser = By.xpath("//a[@class='user-avatar-link dropdown-toggle']");
    private final By lnkLogout = By.xpath("//a[contains(@href,'logout')]");

    public AdminDevelopmentHome(WebDriver driver) {
        super(driver, uniqueElement, page_title);
    }

    public AdminDevelopmentHome clickMenuAdmin() {
        waitForAndClickElement(lnkMenuAdmin);
        extentLogger.logInfo("");
        return this;
    }

    public AdminControlPanelPage clickControlPanel(){
        waitForAndClickElement(lnkControlPanel);
        extentLogger.logInfo("");
        return getNewPage(AdminControlPanelPage.class);
    }

    public AdminHomePage logOutPage(){
        waitForAndClickElement(lnkMenuUser);
        extentLogger.logInfo("");
        waitForAndClickElement(lnkLogout);
        return getNewPage(AdminHomePage.class);
    }
}
