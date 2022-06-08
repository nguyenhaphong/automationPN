package net.nintendo.automation.ui.models.ndp;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.ndp.admin.NDPCompanyRegistrationPage;
import net.nintendo.automation.ui.models.ndp.admin.NDPUserManagementPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NDPInternalTasksPage extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//span[text()=' Internal Tasks ']");
    private static By lnkLicensingWaiverRequestsTab = By.xpath("//a[contains(@href,'group/development/tasks/licensing-waiver-requests')]");
    private final By lnkInternalWaiverRequestsTab = By.xpath("//a[contains(@href,'group/development/tasks/internal-waiver-requests')]");
    private static final String pageTitle = "";
    private final By lnkUserManagement = By.cssSelector("a[href*='internal/user-management']");
    private final By productTransferOwnerTabElement = By.xpath("//a[contains(@href,'group/development/tasks/product-transfer-owner')]");
    private final By userManagementMenu = By.cssSelector("a[href*='internal/user-management']");
    private final By lnkCompanyRegistration = By.cssSelector("a[href*='tasks/company-registration']");

    public NDPInternalTasksPage(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public NDPLicensingWaiverRequestsTab clickOnLicensingWaiverRequestsTab() {
        extentLogger.logInfo("clickOnLicensingWaiverRequestsTab");
        waitForAndClickElement(lnkLicensingWaiverRequestsTab);
        return getNewPage(NDPLicensingWaiverRequestsTab.class);
    }

    public NDPInternalWaiverRequestsTab clickOnInternalWaiverRequestsTab() {
        extentLogger.logInfo("");
        waitForAndClickElement(lnkInternalWaiverRequestsTab);
        return getNewPage(NDPInternalWaiverRequestsTab.class);
    }

    public NDPUserManagementPage clickUserManagement() {
        extentLogger.logInfo("");
        waitForAndClickElement(lnkUserManagement);
        return getNewPage(NDPUserManagementPage.class);
    }

    public NDPProductTransferOwnerTab clickOnProductTransferOwnerTab() {
        extentLogger.logInfo("");
        waitForAndClickElement(productTransferOwnerTabElement);
        return getNewPage(NDPProductTransferOwnerTab.class);
    }
    public NDPCompanyRegistrationPage clickCompanyRegistration() {
        extentLogger.logInfo("");
        waitForAndClickElement(lnkCompanyRegistration);
        return getNewPage(NDPCompanyRegistrationPage.class);
    }


}