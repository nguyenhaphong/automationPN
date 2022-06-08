package net.nintendo.automation.ui.models.ndp;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.ndp.admin.NDPAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import net.nintendo.automation.ui.models.ndp.existing_internal.NDPInternalPage;
import org.openqa.selenium.WebElement;

public class NDPDevelopmentHome extends CommonUIComponent {
    // private static final By uniqueElement = By.xpath("//a[@class='dropdown-toggle' and contains(@href,'group/development')]");
    //private static final By uniqueElement = By.xpath("//a[contains(@href,'group/development/home') and @class='mario']");
    private static final By uniqueElement = By.xpath("//a[contains(@href,'group/development/') and @class='mario']");
    private static final String page_title = "HOME - Nintendo Developer Portal";
    private static By lnkMyProducts = By.cssSelector("a[href*='group/development/products']");
    private static By lnkMyTaskMenu = By.xpath("//li[@class='nin-task-menu dropdown']");
    private static By btnViewAllTask = By.xpath("//footer/a[contains(@href,'/group/development/tasks')]");
    private static By lnkInternalTab = By.xpath("//a[contains(@href,'/group/development/internal')]");
    private final String lnkUserMenuDropDown = "//li[@class='nin-user-menu dropdown']//i[@class='icon-caret-down visible-desktop']";
    private static By lnkUserMenu = By.xpath("//*[@class='nin-user-menu dropdown']");
    private final By internalTab = By.cssSelector("a[href*='group/development/internal']");
    private final By ddlMenuAccount = By.xpath("//*[@class='nin-user-menu dropdown']//a[@class='dropdown-toggle']");
    private final By lnkManageAccount = By.cssSelector("a[href*='group/development/account']");
    private final By lblOrgName = By.id("_noacompanyusersportlet_WAR_noacompanyadminportlet_organization");
    //private final By btnSignOut = By.cssSelector("a[href*='/c/portal/logout']");
    private final By btnSignOut = By.cssSelector("div[id='wrapper']  a[href*='/logout']");
    private static By lnkGettingStarted = By.xpath("//a[contains(@href,'group/development/getting-started')]");
    private static By lnkDeveloperSupportTab = By.xpath("//a[contains(@href,'/group/development/developer-support')]");
    private static By btAdmin = By.xpath("//li[@role='presentation']//a[contains(@href,'group/development/admin')]");
    private static By liOrganizationRelationshipRequest = By.xpath("//li[1]//a[contains(@href,'group/development/tasks/view-task')]//h4[contains(text(),'Organization Relationship Request')]");
    private static By liTransferProductOwner = By.xpath("//li[1]//a[contains(@href,'group/development/tasks/view-task')]//h4[contains(text(),'Transfer Product Ownership Request')]");

    public NDPDevelopmentHome(WebDriver driver) {
        super(driver,uniqueElement);
    }

    public NDPTopNavigation topNavigation(){return getNewPage(NDPTopNavigation.class);}

    public NDPDevelopmentHome acceptCookie() {
        waitForAndClickElement(By.cssSelector("a.cookie-accept-button"));
        extentLogger.logInfo("");
        this.waitIsLoaded();
        extentLogger.logInfo("");
        return this;
    }
    public NDPMyProductsPage clickMyProducts() {
        waitForAndClickElement(lnkMyProducts);
        extentLogger.logInfo("");
        return getNewPage(NDPMyProductsPage.class);
    }

    public NDPDevelopmentHome clickOnMyTaskMenu() {
        extentLogger.logInfo("clickOnMyTaskMenu");
        driver.navigate().refresh();
        waitForAndClickElement(lnkMyTaskMenu);
        return this;
    }

    public NDPInternalTasksPage clickOnViewAllTaskButton() {
        extentLogger.logInfo("clickOnViewAllTaskButton");
        waitForAndClickElement(btnViewAllTask);
        return getNewPage(NDPInternalTasksPage.class);
    }

    public NDPInternalPage clickInternalTab() {
        extentLogger.logInfo("");
        waitForAndClickElement(lnkInternalTab);
        return getNewPage(NDPInternalPage.class);
    }

    public NDPTopNavigationBar clickUserMenuDropdownList() {
        waitForElementVisible(By.xpath(lnkUserMenuDropDown), WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        waitForAndClickElement(By.xpath(lnkUserMenuDropDown));
        extentLogger.logInfo("");
        return getNewPage(NDPTopNavigationBar.class);
    }

    public NDPDevelopmentHome clickUserMenu() {
        extentLogger.logInfo("clickUserMenu");
        waitForAndClickElement(lnkUserMenu);
        return this;
    }

    public NDPDevelopmentHome clickSignOut() {
        extentLogger.logInfo("clickSignOut");
        waitForAndClickElement(btnSignOut);
        return this;
    }

    public NDPDevelopmentHome clickMenuAccount() {
        extentLogger.logInfo("");
        waitForAndClickElement(ddlMenuAccount);
        return this;
    }

    public NDPDevelopmentHome clickManagerAccountLink() {
        extentLogger.logInfo("");
        waitForAndClickElement(lnkManageAccount);
        return this;
    }

    public String getOrgName() {
        WebElement element = driver.findElement(lblOrgName);
        String getOrgName = element.getAttribute("value").trim();
        extentLogger.logInfo("Org Name: " + getOrgName);
        return getOrgName;
    }

    public NDPHomePage clickLogout() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnSignOut);
        return getNewPage(NDPHomePage.class);
    }
    public NDPTopNavigation navTopNavigation(){
        extentLogger.logInfo("");
        return getNewPage(NDPTopNavigation.class);
    }
    public NDPGettingStartedPage clickGettingStarted() {
        waitForAndClickElement(lnkGettingStarted);
        extentLogger.logInfo("");
        return getNewPage(NDPGettingStartedPage.class);
    }
    public NDPViewTaskPage clickOnTask(String task){
        waitForAndClickElement(By.xpath(String.format("//li[@class='nin-user-task-listing']//h4[text()=' %s '][1]",task)));
        extentLogger.logInfo(task);
        waitDocumentReady();
        return getNewPage(NDPViewTaskPage.class);
    }
    public NDPDeveloperSupportPage clickDeveloperSupportTab() {
        extentLogger.logInfo("");
        waitForAndClickElement(lnkDeveloperSupportTab);
        return getNewPage(NDPDeveloperSupportPage.class);
    }
    public NDPAdminPage clickAdmin() {
        waitForAndClickElement(btAdmin);
        extentLogger.logInfo("");
        return getNewPage(NDPAdminPage.class);
    }
    public NDPViewTaskPage clickOrganizationRelationshipRequestContent(){
        waitForAndClickElement(liOrganizationRelationshipRequest);
        extentLogger.logInfo("");
        return getNewPage(NDPViewTaskPage.class);
    }

    public NDPViewTaskPage clickTransferProductOwnerContent(){
        waitForAndClickElement(liTransferProductOwner);
        extentLogger.logInfo("");
        return getNewPage(NDPViewTaskPage.class);
    }

}
