package net.nintendo.automation.ui.models.admin;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminUserDetailPage extends CommonUIComponent {
    private static final By roleLink = By.xpath("//a[@id='_125_rolesLink']");
    private final By btnSave = By.xpath("//button[contains(text(),'Save')]");
    private final By alertSuccess = By.xpath(" //div[@class='alert alert-success']");
    private final By backIcon = By.xpath("//a[@id='_125_TabsBack']");
    private final By userMenu = By.xpath("//a[@class='user-avatar-link dropdown-toggle']");
    private final By logoutIcon = By.xpath("//i[@class='icon-off']");
    private final By searchRegularRoleIcon = By.xpath("//a[@id='_125_selectRegularRoleLink']//i[@class='icon-search']");

    public AdminUserDetailPage(WebDriver driver) {
        super(driver, roleLink);
    }

    public AdminUserDetailPage clickRole() {
        waitForAndClickElement(roleLink);
        waitDocumentReady();
        extentLogger.logInfo("");
        return this;
    }

    public AdminSelectRegularRoleModal clickSearchRegularRole() {
        waitForAndClickElement(searchRegularRoleIcon);
        extentLogger.logInfo("");
        return getNewPage(AdminSelectRegularRoleModal.class);
    }

    public AdminUserDetailPage clickSave() {
        waitForAndClickElement(btnSave);
        waitForElementVisible(alertSuccess, WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        extentLogger.logInfo("");
        return this;
    }

    public AdminAllUsersTab clickBack() {
        waitForAndClickElement(backIcon);
        extentLogger.logInfo("");
        return getNewPage(AdminAllUsersTab.class);
    }

    public AdminUserDetailPage clickUserMenu() {
        waitForAndClickElement(userMenu);
        extentLogger.logInfo("");
        return this;
    }

    public AdminHomePage clickSignOut() {
        waitForAndClickElement(logoutIcon);
        extentLogger.logInfo("");
        return getNewPage(AdminHomePage.class);
    }
}
