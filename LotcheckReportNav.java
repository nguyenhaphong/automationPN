package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthLogoutPage;
import net.nintendo.automation.ui.models.lcms.LotcheckEditAccountPreferences;
import net.nintendo.automation.ui.models.testrail.TestRailLoginPage;
import net.nintendo.automation.utils.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class LotcheckReportNav extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//ul[@role='tablist']");
    private static final String pageTitle = "";

    private static By lnkSwitchToTestrail = By.xpath("//a[@data-testid='testrail-link']");
    private final By lnkMetadataTab = By.xpath("//a[contains(@href,'#/lotcheck-report-submission-level/metadata')]");
    private final By lnkFileTab = By.xpath("//ul[@role='tablist']//a[contains(@href,'/lotcheck-report-submission-level/files/')]");
    private final By lnkFeaturesTab = By.xpath("//ul[@role='tablist']//a[contains(@href,'/lotcheck-report-submission-level/features/')]");
    private final By lnkProductInfoTab = By.xpath("//ul[@role='tablist']//a[contains(@href,'/lotcheck-report-submission-level/product-information/')]");
    private final By lnkContentsTab = By.xpath("//a[contains(@href,'content')]");
    private final By lnkLotcheckIssues = By.xpath("//a[contains(@href,'lotcheck-issues')]");
    private final By lnkNavUser = By.xpath("//div[contains(@class,'nav-user')]//span");
    private final By btnSignOut = By.xpath("//i[@class='fas fa-sign-out-alt mr-1']");
    private final By lnkTestScenariosTab = By.xpath("//ul[@role='tablist']//a[contains(@href,'/lotcheck-report-submission-level/test-scenarios/')]");
    private final By lnkOverviewTab = By.xpath("//ul[@role='tablist']//a[contains(@href,'/lotcheck-report-submission-level/overview/')]");
    private final By navUser = By.xpath("//div[contains(@class,'nav-user')]//span");
    private final By myAccountButtonInUserNameMenu = By.xpath("//*[@id='hyrule-nav']//button[@data-testid='add-to-schedule-group-button']");
    private final By lnkLotcheckRequiredApprovalTab = By.xpath("//a[contains(@href,'required-approvals')]");

    public LotcheckReportNav(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public static JavascriptExecutor javascriptExecutor;

    public LotcheckReportSubmissionFeatures clickFeatures() {
        extentLogger.logInfo("");
        waitForAndClickElement(lnkFeaturesTab);
        return getNewPage(LotcheckReportSubmissionFeatures.class);
    }

    public LotcheckReportSubmissionMetadata clickMetadata() {
        //waitForAndClickElement(lnkMetadataTab);
        WebElement element = driver.findElement(lnkMetadataTab);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionMetadata.class);
    }

    public LotcheckReportSubmissionProductInformation clickProductInformation() {
        extentLogger.logInfo("");
        waitForAndClickElement(lnkProductInfoTab);
        return getNewPage(LotcheckReportSubmissionProductInformation.class);
    }

    public TestRailLoginPage switchToTestRailLoginPage() {
        extentLogger.logInfo("Switch To Testrail");
        List<WebElement> testPlan = findElements(lnkSwitchToTestrail);
        String parentHandle = driver.getWindowHandle(); // get the current window handle
        /*String anchorURL = testPlan.get(0).getAttribute("href"); */ //Assuming you are clicking on a link which opens a new browser window
        testPlan.get(0).click();
        driver.switchTo().window(parentHandle);
        driver.close();//Clicking on this window
        for (String winHandle : driver.getWindowHandles())  //Gets the new window handle
            driver.switchTo().window(winHandle);
        return getNewPage(TestRailLoginPage.class);
    }

    public LotcheckReportSubmissionFiles clickFiles() {
        extentLogger.logInfo("");
        waitForAndClickElement(lnkFileTab);
        return getNewPage(LotcheckReportSubmissionFiles.class);
    }

    public LotcheckReportSubmissionContents clickContents() {
        waitForAndClickElement(lnkContentsTab);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionContents.class);
    }

    public LotcheckReportSubmissionLotcheckIssues clickLotcheckIssues() {
        waitForAndClickElement(lnkLotcheckIssues);
        waitDocumentReady();
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionLotcheckIssues.class);
    }

    public HyruleAuthLogoutPage logOut() {
        openLinkInSameTab(lnkNavUser);
        extentLogger.logInfo("");
        waitForAndClickElement(btnSignOut);
        extentLogger.logInfo("");
        return getNewPage(HyruleAuthLogoutPage.class);
    }

    public void waitForReleaseSubmitted() {
        for (int i = 0; i < 300; i++) {
            List<WebElement> testPlan = findElements(lnkSwitchToTestrail);
            if (testPlan.isEmpty()) {
                WaitUtils.idle(3000);
                driver.navigate().refresh();
                waitForElementVisible(uniqueElement, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
            } else {
                break;
            }
            extentLogger.logInfo("");
        }
    }

    public LotcheckReportSubmissionTestScenarios clickTestScenarios() {
        waitForAndClickElement(lnkTestScenariosTab);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionTestScenarios.class);
    }

    public LotcheckReportSubmissionOverview clickOverview() {
        extentLogger.logInfo("");
        waitForAndClickElement(lnkOverviewTab);
        return getNewPage(LotcheckReportSubmissionOverview.class);
    }
    public LotcheckEditAccountPreferences clickMyAccount() {
        openLinkInSameTab(navUser);
        extentLogger.logInfo("");
        waitForAndClickElement(myAccountButtonInUserNameMenu);
        extentLogger.logInfo("");
        return getNewPage(LotcheckEditAccountPreferences.class);
    }
    public LotcheckReportSubmissionRequiredApproval clickRequiredApprovalTab() {
        waitForAndClickElement(lnkLotcheckRequiredApprovalTab);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionRequiredApproval.class);
    }


}