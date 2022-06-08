package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthLogoutPage;
import net.nintendo.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class LotcheckReportSubmissionLotcheckIssues extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@data-testid='lotcheck-issues-container']");
    private static final String pageTitle = "Lotcheck Issues Submission: LCR - LCMS";
    //private final By btnMenu = By.xpath("//div[@data-testid=\"issues-details-container-action-menu\"]//preceding::button[@id='dropdownMenuLink']");
    private final By btnMenu = By.xpath("//div[@id='collapseApprovals1']//div[@class='card mb-3'][1]//button[@id='dropdownMenuLink']");
    private final By btnMenuOption = By.xpath("//button[@id='dropdownMenuLink']");
    private final By lnkViewIssueDetails = By.xpath("//a[contains(@href,'/details')]");
    private final By lnkViewIssueDetail = By.xpath("//a[contains(text(),'View Issue Details')]");
    private final By btnPublishToPublic = By.xpath("//button[@data-testid=\"publish-button\"]");
    private final By lblMessageSuccess = By.xpath("//div[@role='alert']");
    private final By lblTitleOfIssue = By.xpath("//h2[@class='mb-3 pre-line']");
    private final By lblInnerTitleOfIssue = By.xpath("//span[@class='link-inner pre-line']");

    private final By lnkPartnerThreadStatus = By.xpath("//span[@class='text-uppercase badge badge-warning']");
    private final By lnkPartnerThreadTab = By.xpath("//a[contains(@href,'partner')]");
    private final By btnCloseOnAlert = By.xpath("//button[@class='Toastify__close-button Toastify__close-button--success']");
    private final By lnkNavUser = By.xpath("//div[contains(@class,'nav-user')]//span");
    private final By lnkSignOut = By.xpath("//i[@class='fas fa-sign-out-alt mr-1']");
    private final By btnExpandCurrentIssues = By.xpath("//div[@data-testid='current-submission-issues']//button[@aria-expanded='false']");
    private final By pnlRecordIsDisplayProductResolutionArchiveIssues = By.xpath("//div[@id='collapseArchiveVersionVER0000']//div[@class='card mb-3']");
    private final By pnlRecordIsDisplay = By.xpath("//div[@data-testid='current-submission-issues-content']//div[@class='card mb-3']");
    private final By btnExpandIssue = By.xpath("//button[@class='btn text-left issue-button-issue collapsed']");
    private final By iconPrivate = By.xpath("//i[@class='fas fa-eye-slash text-muted pr-2']");
    private final By lblDescription = By.xpath("//p[@class='pre-line']");
    private final By lblTitle = By.xpath("//span[@class='link-inner pre-line']");
    private final By lblVisibleTo = By.xpath("//ul[@class='attributes']//strong[contains(text(),'Visible To:')]/..");
    private final By lblProperties = By.xpath("//div[@class='issue-thread-container']");
    private final By lnkViewIssueThread = By.xpath("//a[contains(text(),'View Lotcheck Thread')]");
    private final By btnExpandVersion = By.xpath("//button[@data-target='#collapseArchiveVersionVER0000']//i");
    private final By btnExpandIssueInArchive = By.xpath("//button[@class='btn text-left issue-button-archive collapsed']");
    private final By lstStatusWillFixInROm = By.xpath("//span[@class='text-uppercase badge badge-danger']");
    private final By lstStatusNeedResponse = By.xpath("//span[@class='text-uppercase badge badge-warning']");
    private final By lstStatusSuccess = By.xpath("//span[@class='text-uppercase badge badge-success']");
    private final By lblResponseForApprovalOrRejection = By.xpath("//div[@class='card-body accordion-content collapse-all-archive false collapse show']//h6/../p[3]");
    private final By lblCurrentSubmissionResponseForApprovalOrRejection = By.xpath("//div[contains(@class,'accordion-content collapse-all')]//h6/../p[3]");

    private final By lblReasonForRequest = By.xpath("//div[@class='card-body accordion-content collapse-all-archive false collapse show']//h6/../p[2]");
    private final By lblCurrentSubmissionReasonForRequest = By.xpath("//div[contains(@class,'accordion-content collapse-all')]//h6/../p[2]");
    private final By lblTranslationStatus = By.xpath("//*[@id='myTab']//..//div[@class='accordion-content'][1]//dd[3]");
    private final By viewIssueDetailsLink = By.xpath("//a[contains(@href,'/details')]");
    private final By lstIssueInCurrentSubmissionIssues = By.xpath("//div[@data-testid='current-submission-issues']//span[@class='link-inner pre-line']");
    private final By lstIssueInProductResolutionArchiveIssues = By.xpath("//div[@class='collapse accordion-content show']//span[@class='link-inner pre-line']");
    private final By btnExpandUPPVersion = By.xpath("collapseArchiveVersionVER00101");
    private final By pnlRecordIsDisplayCurrentSubmissionIssues = By.xpath("//div[@data-testid='current-submission-issues-content']//div[@class='card mb-3']");
    private final By statusOfIssueOfFirstSubmissionInProductArchiveIssue = By.xpath("//div[@id='collapseArchiveVersionVER0000']//div[@class='card mb-3']//span[@class='text-uppercase badge " +
            "badge-success']");
    private final By statusOfIssueOfSecondSubmissionInProductArchiveIssue = By.xpath("//div[@id='collapseArchiveVersionVER0001']//div[@class='card mb-3']//span[@class='text-uppercase badge " +
            "badge-success']");
    private final By pnlRecordIsDisplayedProductResolutionArchiveIssuesInSecondSubmission = By.xpath("//div[@id='collapseArchiveVersionVER0001']//div[@class='card mb-3']");
    private final By lblPublishedTitle = By.xpath("//div[@data-testid='issues-details']//dl//dd[3]//div");
    private final By lblPublishedDescription = By.xpath("//div[@data-testid='issues-details']//dl//dd[4]//div");

    private final By lblPublishedDescription2 = By.xpath("//div[@id='card1']//p[1]");

    private final By lblInnerPublishedTitle = By.xpath("//span[@class='link-inner pre-line']");

    public LotcheckReportSubmissionLotcheckIssues(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public LotcheckReportNav navBar() {
        return getNewPage(LotcheckReportNav.class);
    }

    public LotcheckReportMatrix matrix() {
        return getNewPage(LotcheckReportMatrix.class);
    }

    public LotcheckReportSubmissionLotcheckIssues clickMenu() {
        for (int i = 0; i < 50; i++) {
            if (elementNotExists(btnMenu)) {
                driver.navigate().refresh();
                waitForElementVisible(uniqueElement, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
                WaitUtils.idle(3000);
            } else {
                break;
            }
        }
        waitForAndClickElement(btnMenu);
        extentLogger.logInfo("");
        return this;

    }


    public LotcheckReportSubmissionLotcheckIssues clickMenuJS() {
        for (int i = 0; i < 50; i++) {
            if (elementNotExists(btnMenu)) {
                driver.navigate().refresh();
                waitForElementVisible(uniqueElement, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
                WaitUtils.idle(3000);
            } else {
                break;
            }
        }
        WebElement element = driver.findElement(btnMenu);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", element);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssues clickMenuOption() {
        for (int i = 0; i < 50; i++) {
            if (elementNotExists(btnMenuOption)) {
                driver.navigate().refresh();
                waitForElementVisible(uniqueElement, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
                WaitUtils.idle(3000);
            } else {
                break;
            }
        }
        waitForAndClickElement(btnMenuOption);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssueDetails selectViewIssueDetails() {
        waitForAndClickElement(lnkViewIssueDetails);
        waitForElementVisible(By.xpath("//div[@data-testid=\"spinner-loading\"]"), WaitTimes.WAIT_FOR_DOCUMENT_READY_TIMEOUT_SECS);
        waitForElementNotVisible(By.xpath("//div[@data-testid=\"spinner-loading\"]"), WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionLotcheckIssueDetails.class);
    }

    public LotcheckReportSubmissionLotcheckIssues clickMenuAction(String nameTestCase) {
        WaitUtils.idle(1000);
        waitForAndClickElement(By.xpath(String.format("//div[@class='card-header bg-warning']//span[contains(text(),'%s')]/ancestor::button/following::div[@data-testid='issue-action-menu']", nameTestCase)));
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssueDetails clickViewIssueDetail() {
        waitForAndClickElement(lnkViewIssueDetail);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionLotcheckIssueDetails.class);
    }

    public LotcheckReportSubmissionLotcheckIssues clickMenuOfIssue(String testcase) {
        for (int i = 0; i < 50; i++) {
            if (elementNotExists(btnMenuOption)) {
                driver.navigate().refresh();
                waitForElementVisible(uniqueElement, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
                WaitUtils.idle(3000);
            } else {
                break;
            }
        }
        waitForAndClickElement(By.xpath(String.format("//span[contains(text(),'%s')]/ancestor::button/following::div[@data-testid=\"issue-action-menu\"]", testcase)));
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssueDetails selectViewIssueDetailsOfIssue(String testcase) {
        waitForAndClickElement(By.xpath(String.format("//span[contains(text(),'%s')]/ancestor::button/following::div[@data-testid=\"issue-action-menu\"]//a[contains(@href,'/details')]", testcase)));
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionLotcheckIssueDetails.class);
    }

    public HyruleAuthLogoutPage logOut() {
        openLinkInSameTab(lnkNavUser);
        extentLogger.logInfo("");
        waitForAndClickElement(lnkSignOut);
        extentLogger.logInfo("");
        return getNewPage(HyruleAuthLogoutPage.class);
    }

    public LotcheckPublishToPublicDialog selectPublishToPublic() {
        waitForAndClickElement(btnPublishToPublic);
        extentLogger.logInfo("");
        return getNewPage(LotcheckPublishToPublicDialog.class);
    }

    public String getMessageSuccessfully() {
        String value = getText(lblMessageSuccess);
        extentLogger.logInfo(value);
        return value;
    }

    public String getTitleOfIssue() {
        String value = getText(lblTitleOfIssue);
        extentLogger.logInfo(value);
        return value;
    }

    public String getInnerTitleOfIssue() {
        String value = getText(lblInnerTitleOfIssue);
        extentLogger.logInfo(value);
        return value;
    }

    public String getPartnerThreadStatus() {
        String value = getText(lnkPartnerThreadStatus);
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckReportSubmissionLotcheckIssuePartnerThread clickPartnerThreadTab() {
        waitForAndClickElement(lnkPartnerThreadTab);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionLotcheckIssuePartnerThread.class);
    }

    public LotcheckReportSubmissionLotcheckIssues waitLoadingLotcheckIssue() {
        waitForElementVisible(btnExpandCurrentIssues);
        extentLogger.logInfo("");
        return this;

    }

    public boolean displayFieldOnBottomOfTheThreadTile(String fieldName) {
        boolean value = true;
        if (elementNotExists(By.xpath("//ul[@class='attributes']//strong[contains(text(),'" + fieldName + ":')]"))) {
            value = false;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public LotcheckReportSubmissionLotcheckIssues refreshPage() {
        for (int i = 0; i < 50; i++) {
            if (elementNotExists(btnMenuOption)) {
                extentLogger.logInfo("");
                driver.navigate().refresh();
                waitForElementVisible(uniqueElement, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
            } else {
                break;
            }
        }
        return this;
    }

    public boolean recordIsDisplayInCurrentSubmissionIssue() {
        boolean value = true;
        if (elementNotExists(pnlRecordIsDisplay)) {
            value = false;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public LotcheckReportSubmissionLotcheckIssues expandCollapsedIssue() {
        waitForAndClickElement(btnExpandIssue);
        extentLogger.logInfo("");
        return this;
    }


    public boolean iconPrivateIsDisplay() {
        boolean value = true;
        if (elementNotExists(iconPrivate)) {
            value = false;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public String getTitle() {
        String title = getText(lblTitle);
        extentLogger.logInfo(title);
        return title;
    }

    public String getDescription() {
        String description = getText(lblDescription);
        extentLogger.logInfo(description);
        return description;
    }

    public String getValueFieldOnBottomOfTheThreadTile(String fieldName) {
        String value = getText(By.xpath("//ul[@class='attributes']//strong[contains(text(),'" + fieldName + "')]/.."));
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckReportSubmissionLotcheckIssues clickExpandVersion() {
        waitForAndClickElement(btnExpandVersion);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssues clickMenuBugSecond(String nameBug) {
        for (int i = 0; i < 50; i++) {
            if (elementNotExists(By.xpath("//span[@class='link-inner pre-line' and contains(text(),'" + nameBug + "')]"))) {
                driver.navigate().refresh();
                waitForElementVisible(uniqueElement, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
                WaitUtils.idle(3000);
            } else {
                break;
            }
        }
        WebElement element = driver.findElement(By.xpath("//div[@id='collapseApprovals1']//div[@class='card mb-3'][1]//button[@id='dropdownMenuLink']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", element);
        extentLogger.logInfo("");
        return this;

    }

    public LotcheckReportSubmissionLotcheckIssues expandCollapsedIssueInArchive() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnExpandIssueInArchive);
        return this;
    }

    public boolean recordIsDisplayInProductResolutionArchiveIssues() {
        boolean value = true;
        if (elementNotExists(pnlRecordIsDisplayProductResolutionArchiveIssues)) {
            value = false;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public String getBackgroundLotcheckThread() {
        String valueType = checkBackgroundColor(lblProperties);
        extentLogger.logInfo(valueType);
        return valueType;
    }

    public LotcheckReportSubmissionLotcheckIssueThread selectViewLotcheckThread() {
        waitForAndClickElement(lnkViewIssueThread);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionLotcheckIssueThread.class);
    }

    public boolean getElementAreNotClickableLinks() {
        String href = getElement(lblVisibleTo);
        boolean value = true;
        if (href == null) {
            return true;
        }
        extentLogger.logInfo(String.valueOf("getElementAreNotClickableLinks: " + value));
        return value = false;
    }

    public int getNumberOfLotcheckIssueResolvedSuccess() {
        List<WebElement> success = driver.findElements(lstStatusSuccess);
        int num = success.size();
        extentLogger.logInfo(String.valueOf(num));
        return num;
    }

    public int getNumberOfLotcheckIssueResolvedWillFixInRom() {
        List<WebElement> willFixInRom = driver.findElements(lstStatusWillFixInROm);
        int num = willFixInRom.size();
        extentLogger.logInfo(String.valueOf(num));
        return num;
    }

    public int getNumberOfLotcheckIssueUnresolved() {
        List<WebElement> unresolved = driver.findElements(lstStatusNeedResponse);
        int num = unresolved.size();
        extentLogger.logInfo(String.valueOf(num));
        return num;
    }

    public String getStatusOfIssueByName(String IssueName) {
        String value = getText(By.xpath(String.format("//span[contains(text(),'%s')]/..//span[contains(@class,'text-uppercase badge')]", IssueName)));
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckReportSubmissionLotcheckIssues clickMenuOfIssueName(String IssueName) {
        for (int i = 0; i < 50; i++) {
            if (elementNotExists(By.xpath(String.format("//span[contains(text(),'%s')]/../../button/..//div[@data-testid='issue-action-menu']", IssueName)))) {
                driver.navigate().refresh();
                waitForElementVisible(uniqueElement, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
                WaitUtils.idle(3000);
            } else {
                break;
            }
        }
        waitForAndClickElement(By.xpath(String.format("//span[contains(text(),'%s')]/../../button/..//div[@data-testid='issue-action-menu']", IssueName)));
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssueDetails clickViewIssueDetailOfIssueName(String IssueName) {
        waitForAndClickElement(By.xpath(String.format("//span[contains(text(),'%s')]/../../button/..//div[@data-testid='issue-action-menu']//a[contains(text(),'View Issue Details')]", IssueName)));
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionLotcheckIssueDetails.class);
    }

    public int getNumberArchiveIssues(String partnerThreadStatus) {
        By issueSelector = By.xpath(String.format("//div[@class='collapse-all-archive undefined collapse show']//span[@class='ml-2']//span[contains(text(),'%s')]", partnerThreadStatus));
        List<WebElement> issue = findElements(issueSelector);
        int num = issue.size();
        extentLogger.logInfo("Number Of Issues:" + num);
        return num;
    }

    public LotcheckReportSubmissionLotcheckIssues expandCollapsedIssueByIssueName(int number, String issueName) {
        extentLogger.logInfo("expand Issues:" + issueName);
        waitForAndClickElement(By.xpath(String.format("//div[@class='card mb-3'][" + number + "]//span[contains(text(),'%s')]/../../button", issueName)));
        return this;
    }

    public String getReasonForRequest() {
        String reasonForRequest = getText(lblReasonForRequest);
        extentLogger.logInfo(reasonForRequest);
        return reasonForRequest;
    }
    public String getCurrentSubmissionReasonForRequest() {
        String reasonForRequest = getText(lblCurrentSubmissionReasonForRequest);
        extentLogger.logInfo(reasonForRequest);
        return reasonForRequest;
    }
    public String getResponseForApprovalOrRejection() {
        String responseForApprovalOrRejection = getText(lblResponseForApprovalOrRejection);
        extentLogger.logInfo(responseForApprovalOrRejection);
        return responseForApprovalOrRejection;
    }
    public String getCurrentSubmissionResponseForApprovalOrRejection() {
        String responseForApprovalOrRejection = getText(lblCurrentSubmissionResponseForApprovalOrRejection);
        extentLogger.logInfo(responseForApprovalOrRejection);
        return responseForApprovalOrRejection;
    }

    public String getTranslationStatus() {
        String value = getText(lblTranslationStatus);
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckReportSubmissionLotcheckIssues clickXpandVersion() {
        waitForAndClickElement(btnExpandVersion);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssues clickXpandUPPVersion() {
        waitForAndClickElement(btnExpandUPPVersion);
        extentLogger.logInfo("");
        return this;
    }

    public int getNumberOfLotcheckIssueInProductResolutionArchiveIssues() {
        List<WebElement> lotcheckIssueInProductResolutionArchive = driver.findElements(lstIssueInProductResolutionArchiveIssues);
        int num = lotcheckIssueInProductResolutionArchive.size();
        extentLogger.logInfo(String.valueOf(num));
        return num;
    }

    public boolean issueIsDisplayed(String issueName) {
        WebElement issueElement = driver.findElement(By.xpath(String.format("//span[contains(text(),'%s')]", issueName)));
        boolean isDisplayed = issueElement.isDisplayed();
        extentLogger.logInfo(String.valueOf(isDisplayed));
        return isDisplayed;
    }
    public int getNumberOfLotcheckIssueInCurrentSubmissionIssues() {
        List<WebElement> lotcheckIssueInCurrentSubmission = driver.findElements(lstIssueInCurrentSubmissionIssues);
        int num = lotcheckIssueInCurrentSubmission.size();
        extentLogger.logInfo(String.valueOf(num));
        return num;
    }


    public boolean recordIsDisplayInProductResolutionArchiveIssuesInSecondSubmission() {
        boolean value = true;
        if (elementNotExists(pnlRecordIsDisplayedProductResolutionArchiveIssuesInSecondSubmission)) {
            value = false;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public boolean recordIsDisplayInCurrentSubmissionIssues() {
        boolean value = true;
        if (elementNotExists(pnlRecordIsDisplayCurrentSubmissionIssues)) {
            value = false;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public String getTitleOfIssue(String issueName) {
        WebElement issueElement = driver.findElement(By.xpath(String.format("//span[contains(text(),'%s')]", issueName)));
        String value = getText(issueElement);
        extentLogger.logInfo(value);
        return value;
    }

    public String getIssueStatusOfFirstSubmissionInProductArchiveIssue() {
        String value = getText(statusOfIssueOfFirstSubmissionInProductArchiveIssue);
        extentLogger.logInfo(value);
        return value;
    }

    public String getIssueStatusOfSecondSubmissionInProductArchiveIssue() {
        String value = getText(statusOfIssueOfSecondSubmissionInProductArchiveIssue);
        extentLogger.logInfo(value);
        return value;
    }

    public String getPublishedTitle() {
        String value = getText(lblPublishedTitle);
        extentLogger.logInfo(value);
        return value;
    }
    public String getInnerPublishedTitle() {
        String value = getText(lblInnerPublishedTitle);
        extentLogger.logInfo(value);
        return value;
    }

    public String getPublishedDescription() {
        String value = getText(lblPublishedDescription);
        extentLogger.logInfo(value);
        return value;
    }
    public String getPublishedDescriptionCurrentSubmission() {
        String value = getText(lblPublishedDescription2);
        extentLogger.logInfo(value);
        return value;
    }

}
