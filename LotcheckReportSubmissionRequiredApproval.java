package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LotcheckReportSubmissionRequiredApproval extends CommonUIComponent {

    private static final String pageTitle = "Required Approvals Submission: LCR - LCMS";
    private static final By uniqueElement = By.xpath("//div[@data-testid='lotcheck-issues-container']//h2[text()='Required Approvals']");

    private final By iconMenu = By.xpath("//button[@id='dropdownMenuLink']");
    private final By lnkViewPartnerThread = By.xpath("//a[contains(@href,'/partner')]");
    private final By iconMenuHardware = By.xpath("//span[contains(text(),'Hardware')]//..//..//button[@id='dropdownMenuLink']");
    private final By lnkViewPartnerThreadHardware = By.xpath("//span[contains(text(),'Hardware')]//..//..//a[contains(@href,'/partner')]");
    private final By viewIssueDetailsLink = By.xpath("//a[contains(@href,'/details')]");
    private final By menuIcon = By.xpath("//button[@id='dropdownMenuLink']");

    public LotcheckReportSubmissionRequiredApproval(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public LotcheckReportSubmissionRequiredApproval clickMenu() {
        for (int i = 0; i < 50; i++) {
            if (elementNotExists(menuIcon)) {
                driver.navigate().refresh();
                waitForElementVisible(uniqueElement, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
                WaitUtils.idle(3000);
            } else {
                break;
            }
        }
        WaitUtils.idle(3000);

        WebElement element = driver.findElement(menuIcon);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", element);
        //waitForAndClickElement(menuIcon);
        extentLogger.logInfo("");
        return this;
    }



    public LotcheckReportSubmissionRequiredApproval clickMenuJS() {
        for (int i = 0; i < 50; i++) {
            if (elementNotExists(iconMenu)) {
                driver.navigate().refresh();
                waitForElementVisible(uniqueElement, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
                WaitUtils.idle(3000);
            } else {
                break;
            }
        }
        WaitUtils.idle(3000);
        waitForAndClickElement(iconMenu);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionRequiredApproval clickMenuHardware() {
        waitForAndClickElement(iconMenuHardware);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionRequiredApprovalPartnerThread clickViewPartnerThread() {
        waitForAndClickElement(lnkViewPartnerThread);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionRequiredApprovalPartnerThread.class);
    }

    public LotcheckReportSubmissionRequiredApprovalPartnerThread clickViewPartnerThreadHardware() {
        waitForAndClickElement(lnkViewPartnerThreadHardware);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionRequiredApprovalPartnerThread.class);
    }

    public LotcheckReportSubmissionRequiredApprovalIssueDetail selectViewIssueDetails() {
        waitForAndClickElement(viewIssueDetailsLink);
        waitForElementVisible(By.xpath("//div[@data-testid=\"spinner-loading\"]"), WaitTimes.WAIT_FOR_DOCUMENT_READY_TIMEOUT_SECS);
        waitForElementNotVisible(By.xpath("//div[@data-testid=\"spinner-loading\"]"), WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionRequiredApprovalIssueDetail.class);
    }

}
