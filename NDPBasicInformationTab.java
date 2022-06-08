package net.nintendo.automation.ui.models.ndp;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NDPBasicInformationTab extends CommonUIComponent {
    private static final By organizationUUID = By.xpath("//input[@id='_noacompanyinfoportlet_WAR_noacompanyadminportlet_organizationUUID']");

    public NDPBasicInformationTab(WebDriver driver) {
        super(driver, organizationUUID);
    }

    public String getOrganizationUUID() {
        String value = getValueAttributes(organizationUUID).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public NDPTopNavigationBar navTopNavigationBar() {
        extentLogger.logInfo("");
        return getNewPage(NDPTopNavigationBar.class);
    }
}
