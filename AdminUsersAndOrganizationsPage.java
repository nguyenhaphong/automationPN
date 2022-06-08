package net.nintendo.automation.ui.models.admin;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminUsersAndOrganizationsPage extends CommonUIComponent {
    private static final By allUsersTab = By.xpath("//a[@title='All Users']");
    private final By allOrganizationTab = By.xpath("//a[@title='All Organizations']");

    public AdminUsersAndOrganizationsPage(WebDriver driver) {
        super(driver, allUsersTab);
    }

    public AdminAllUsersTab clickAllUsersTab() {
        waitForAndClickElement(allUsersTab);
        extentLogger.logInfo("");
        return getNewPage(AdminAllUsersTab.class);
    }

    public AdminAllOrganizationsTab clickAllOrganizationsTab() {
        waitForAndClickElement(allOrganizationTab);
        extentLogger.logInfo("");
        return getNewPage(AdminAllOrganizationsTab.class);
    }

}
