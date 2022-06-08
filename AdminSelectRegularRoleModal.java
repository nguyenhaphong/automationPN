package net.nintendo.automation.ui.models.admin;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminSelectRegularRoleModal extends CommonUIComponent {
    private static final By uniqueElement=By.xpath("//div[@id='_125_selectRegularRole']");
    private final By txtRole = By.xpath("//input[@id='_125_keywords']");
    private final By txtSearch = By.xpath("//button[@type='submit']");

    public AdminSelectRegularRoleModal(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public AdminSelectRegularRoleModal enterRole(String role) {
        driver.switchTo().frame("_125_selectRegularRole_iframe_");
        waitForAndClickElement(txtRole);
        typeText(txtRole, role);
        typeTab(txtRole);
        extentLogger.logInfo(role);
        return this;
    }

    public AdminSelectRegularRoleModal clickSearch() {
        waitForAndClickElement(txtSearch);
        extentLogger.logInfo("");
        return this;
    }

    public AdminUserDetailPage clickChoose(String role) {
        waitForAndClickElement(By.xpath(String.format("//button[@data-roletitle='%s']", role)));
        extentLogger.logInfo(role);
        driver.switchTo().defaultContent();
        return getNewPage(AdminUserDetailPage.class);
    }
}
