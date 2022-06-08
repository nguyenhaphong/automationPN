package net.nintendo.automation.ui.models.admin;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminAllOrganizationsTab extends CommonUIComponent {
    private static final By txtSearch = By.xpath("//input[@id='toggle_id_users_admin_organization_searchkeywords']");
    private final By btnSearch = By.xpath("//button[@type='submit']");
    private final By btnAction = By.xpath("//a[@id='_125_organizationsSearchContainer_1_menu']");
    private final By btnEdit = By.xpath("//ul[@role='menu']//a[contains(@id,'edit')]//span");

    public AdminAllOrganizationsTab(WebDriver driver) {
        super(driver, txtSearch);
    }

    public AdminAllOrganizationsTab enterOrganization(String organizationName) {
        extentLogger.logInfo(organizationName);
        waitForAndClickElement(txtSearch);
        typeText(txtSearch, organizationName);
        typeTab(txtSearch);
        return this;
    }

    public AdminAllOrganizationsTab clickSearch() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnSearch);
        waitDocumentReady();
        return this;
    }

    public boolean isOrganizationDisplayed(String organizationName) {
        boolean value = false;
        if (!elementNotExists(By.xpath(String.format("//table[contains(@data-searchcontainerid,'organizationsSearchContainer')]//td[2]//a[contains(text(),'%s')]", organizationName)))) {
            value = true;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public AdminAllOrganizationsTab clickAction() {
        waitDocumentReady();
        extentLogger.logInfo("");
        WaitUtils.idle(2000);
        waitForAndClickElement(btnAction);
        return this;
    }

    public AdminEditOrganizationPage selectEdit() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnEdit);
        waitDocumentReady();
        return getNewPage(AdminEditOrganizationPage.class);
    }
}
