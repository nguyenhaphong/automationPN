package net.nintendo.automation.ui.models.admin;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminControlPanelPage extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@class='brand']");
    private static final String page_title = "Control Panel - Nintendo Developer Portal";
    private final By usersAndOrganizations = By.xpath("//a[@id='_190_controlPanelPortletLink_125']");

   private final By btnD4cIntegrationAdmin = By.xpath("//a[contains(@href,'noad4cintegrationadminportlet_WAR_noaromsubmissionportlet')]");

    public AdminControlPanelPage(WebDriver driver) {
        super(driver, uniqueElement, page_title);
    }

    public AdminD4CIntegrationPage clickD4CIntegrationAdminButton() {
        waitForAndClickElement(btnD4cIntegrationAdmin);
        extentLogger.logInfo("");
        return getNewPage(AdminD4CIntegrationPage.class);
    }
    public AdminUsersAndOrganizationsPage clickUsersAndOrganizations() {
        waitForAndClickElement(usersAndOrganizations);
        waitDocumentReady();
        extentLogger.logInfo("");
        return getNewPage(AdminUsersAndOrganizationsPage.class);
    }


}
