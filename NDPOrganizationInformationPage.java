package net.nintendo.automation.ui.models.ndp;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.ndp.admin.existing_adminTab.NDPAdminOrganizationRelationshipsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NDPOrganizationInformationPage extends CommonUIComponent {
    private static final By organizationRelationshipsTab = By.xpath("//ul[@class='nav nav-tabs ']//li[3]");

    private final By tabOrganizationRelationships = By.xpath("//a[contains(text(),'Organization Relationships')]");
    private final By messageSuccess = By.xpath("//div[@class='alert alert-success']");


    public NDPOrganizationInformationPage(WebDriver driver) {
        super(driver, organizationRelationshipsTab);
    }

    public NDPBasicInformationTab navBasicInformationTab() {
        extentLogger.logInfo("");
        return getNewPage(NDPBasicInformationTab.class);
    }

    public NDPOrganizationRelationshipsTab clickOrganizationRelationshipsTab() {
        waitForAndClickElement(organizationRelationshipsTab);
        extentLogger.logInfo("");
        return getNewPage(NDPOrganizationRelationshipsTab.class);
    }

    public NDPAdminOrganizationRelationshipsPage clickAdminOrganizationRelationshipsTab() {
        waitForAndClickElement(tabOrganizationRelationships);
        extentLogger.logInfo("");
        return getNewPage(NDPAdminOrganizationRelationshipsPage.class);
    }
}
