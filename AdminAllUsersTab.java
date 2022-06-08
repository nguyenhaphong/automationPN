package net.nintendo.automation.ui.models.admin;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminAllUsersTab extends CommonUIComponent {
    private static final By txtOrganizationName = By.xpath("//input[@id='toggle_id_users_admin_user_searchkeywords']");
    private final By searchIcon = By.xpath("//button[@type='submit']");
    private final By actionIcon = By.xpath("//a[@title='Actions']//i");
    private final By searchResultShow = By.xpath("//small[@class='search-results']/preceding::small[@class='search-results']");
    private final By editOption = By.xpath("//img[contains(@id,'__menu__edit')]");

    public AdminAllUsersTab(WebDriver driver) {
        super(driver, txtOrganizationName);
    }

    public AdminAllUsersTab enterOrganizationName(String orgName) {
        waitForAndClickElement(txtOrganizationName);
        typeText(txtOrganizationName, orgName);
        typeTab(txtOrganizationName);
        extentLogger.logInfo(orgName);
        return this;
    }

    public AdminAllUsersTab clickSearch() {
        waitForAndClickElement(searchIcon);
        waitDocumentReady();
        extentLogger.logInfo("");
        return this;
    }

    public AdminAllUsersTab clickAction() {
        waitForElementNotVisible(searchResultShow, WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        waitForAndClickElement(actionIcon);
        extentLogger.logInfo("");
        return this;
    }

    public AdminUserDetailPage clickEdit() {
        waitForAndClickElement(editOption);
        waitDocumentReady();
        extentLogger.logInfo("");
        return getNewPage(AdminUserDetailPage.class);
    }

}
