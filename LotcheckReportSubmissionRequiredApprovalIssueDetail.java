package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LotcheckReportSubmissionRequiredApprovalIssueDetail extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//ul[@id='tabIssuesDetails']");
    private final By iconAction = By.xpath("//div[@class='issue-details-container']/div[@class='dropdown '][1]//i");
    private final By twoLanguageDisplayed = By.xpath("//button[@data-testid='display-translations-button']");
    private final By partnerThreadTab = By.xpath("//a[contains(@href,'partner')]");
    private final By viewIssueDetailsLink = By.xpath("//a[contains(@href,'/details')]");

    public LotcheckReportSubmissionRequiredApprovalIssueDetail(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public LotcheckReportSubmissionRequiredApprovalIssueDetail selectTwoLanguageDisplayed() {
        waitForAndClickElement(iconAction);
        extentLogger.logInfo("");
        waitForAndClickElement(twoLanguageDisplayed);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionRequiredApprovalPartnerThread clickPartnerThreadTab() {
        waitForAndClickElement(partnerThreadTab);
        extentLogger.logInfo("");
        //waitForElementNotVisible(iconLoading);
        return getNewPage(LotcheckReportSubmissionRequiredApprovalPartnerThread.class);
    }

    public LotcheckReportSubmissionLotcheckIssueDetails selectViewIssueDetails() {
        waitForAndClickElement(viewIssueDetailsLink);
        waitForElementVisible(By.xpath("//div[@data-testid=\"spinner-loading\"]"), WaitTimes.WAIT_FOR_DOCUMENT_READY_TIMEOUT_SECS);
        waitForElementNotVisible(By.xpath("//div[@data-testid=\"spinner-loading\"]"), WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionLotcheckIssueDetails.class);
    }
}
