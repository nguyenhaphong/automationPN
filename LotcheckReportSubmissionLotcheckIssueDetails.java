package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthLogoutPage;
import net.nintendo.automation.utils.WaitUtils;
import org.openqa.selenium.*;

import java.util.List;

public class LotcheckReportSubmissionLotcheckIssueDetails extends CommonUIComponent {
    private final By lnkNavUser = By.xpath("//div[contains(@class,'nav-user')]//span");
    private final By lnkSignOut = By.xpath("//i[@class='fas fa-sign-out-alt mr-1']");
    private static final By uniqueElement = By.xpath("//ul[@id='tabIssuesDetails']");
    private final By btnMenuOption = By.xpath("//div[@data-testid=\"issues-details-container-action-menu\"]//preceding::button[@id='dropdownMenuLink']");
    private final By lnkPublishToPublic = By.xpath("//button[@data-testid=\"publish-button\"]");
    private final By lblMessageSuccess = By.xpath("//div[@role='alert']");
    private final By lblTitleOfIssue = By.xpath("//h2[@class='mb-3 pre-line']");
    private final By lblTitleDetail = By.xpath("//dd[1]//div[@class='issue-title-container pre-line']");
    private final By lblPartnerThreadStatus = By.xpath("//span[@class='text-uppercase badge badge-warning']");
    private final By lnkPartnerThreadTab = By.xpath("//a[contains(@href,'partner')]");
    private final By btnCloseOnAlert = By.xpath("//button[@class='Toastify__close-button Toastify__close-button--success']");
    private final By lblDescriptionBug = By.xpath("//dl[@class='row striped']//dd[2]//div");
    private final By lblNameTestCase = By.xpath("//dl[@class='row striped']//dd[1]//div");
    private final By lnkGoToProductInformation = By.xpath("//*[@class='go-to-product-level']");
    private final By iconLoading = By.xpath("//div[@data-testid=\"spinner-loading\"]");
    private final By lblTitleIssueValue = By.xpath("//dl[@class='row striped']//dd[1]");
    private final By lblDescriptionIssueValue = By.xpath("//dl[@class='row striped']//dd[2]");
    private final By btnTwoLanguage = By.xpath("//button[text()='Two Language Display']");
    private final By btnResetIssue = By.xpath("//button[@data-testid=\"reset-button\"]");
    private final By btnMenuAtIssueDetail = By.xpath("//div[@data-testid='issues-details']//button[@id='dropdownMenuLink']");
    private final By btnEditDetailsEN = By.xpath("//div[@data-testid='issues-details']//button[text()='Edit Details (EN)']");
    private final By btnEditDetailsJA = By.xpath("//div[@data-testid='issues-details']//button[text()='Edit Details (JA)']");

    private final By btnOpenTranslationTask = By.xpath("//button[@data-testid='open-tt-btn']");
    private final By lblFormName = By.xpath("//*[@class='modal-title  confirmation-title']");
    private final By lblFormMessage = By.xpath("//*[@class='modal-title confirmation-title']");
    private final By btnCancelTranslationTaskForm = By.xpath("//*[@class='btn btn-secondary mr-auto']");
    private final By btnConfirmTranslationTaskForm = By.xpath("//*[@data-testid='submit-button']");
    private final By lblAlert = By.xpath("//*[@role='alert']");
    private final By lnkOpenTranslationTaskForm = By.xpath("//*[@data-testid='action-confirmation-modal']");
    private final By lblTranslationStatus = By.xpath("//*[@id='card1']//..//..//ul//li[2]");
    private final By lnkBackLotcheckIssue = By.xpath("//a[@class='link-back']");
    private final By lblStatusOfIssue = By.xpath("//span[contains(@class,'text-uppercase badge')]");
    private final By lblWarningIssue = By.xpath("//div[@class='alert alert-warning']");
    private final By lnkLotcheckThreadTab = By.xpath("//ul[@id='tabIssuesDetails']//a[contains(text(),'Lotcheck Thread')]");
    private final By ddlLCIType = By.xpath("//select[@name='lciType']");
    private final By lstSubmit = By.xpath("//button[@data-testid=\"submit-button\"]");
    private final By pnlLotcheckView = By.xpath("//div[@data-testid='lotcheck-issues-container']");
    private final By lblPublishedTitle = By.xpath("//div[@data-testid='issues-details']//dl//dd[3]//div");
    private final By lblPublishedDescription = By.xpath("//div[@data-testid='issues-details']//dl//dd[4]//div");
    private final By lblResponseForApprovalOrRejection = By.xpath("//div[@data-testid='issues-details']//dl//dd[5]//div");
    private final By lblReasonForRequest = By.xpath("//div[@data-testid='issues-details']//dl//dd[5]//div");
    private final By lblPublishDescription = By.xpath("//dl[@class='row striped']//dd[4]//div");

    public LotcheckReportSubmissionLotcheckIssueDetails(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public HyruleAuthLogoutPage logOut() {
        openLinkInSameTab(lnkNavUser);
        extentLogger.logInfo("");
        waitForAndClickElement(lnkSignOut);
        extentLogger.logInfo("");
        return getNewPage(HyruleAuthLogoutPage.class);
    }

    public LotcheckReportSubmissionLotcheckIssueDetails clickMenu() {
        waitForAndClickElement(btnMenuOption);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckPublishToPublicDialog selectPublishToPublic() {
        waitForAndClickElement(lnkPublishToPublic);
        extentLogger.logInfo("");
        return getNewPage(LotcheckPublishToPublicDialog.class);
    }

    public String getMessageSuccessfully() {
        waitForElementVisible(lblMessageSuccess, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        String value = getText(lblMessageSuccess);
        extentLogger.logInfo(value);
        return value;
    }

    public String getTitleOfIssue() {
        String value = getText(lblTitleOfIssue);
        extentLogger.logInfo(value);
        return value;
    }

    public String getTitleOfIssueDetail() {
        waitForElementVisible(lblTitleDetail, WaitTimes.WAIT_FOR_DOCUMENT_READY_TIMEOUT_SECS);
        String value = getText(lblTitleDetail);
        extentLogger.logInfo(value);
        return value;
    }

    public String getPartnerThreadStatus() {
        waitForElementVisible(lblPartnerThreadStatus, WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        String value = getText(lblPartnerThreadStatus);
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckReportSubmissionLotcheckIssueDetails clickCloseAlertSuccess() {
        if (!elementNotExists(btnCloseOnAlert))
            waitForAndClickElement(btnCloseOnAlert);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssuePartnerThread clickPartnerThreadTab() {
        waitForAndClickElement(lnkPartnerThreadTab);
        extentLogger.logInfo("");
        waitDocumentReady();
        return getNewPage(LotcheckReportSubmissionLotcheckIssuePartnerThread.class);
    }

    public String getDescriptionBug() {
        String description = getText(lblDescriptionBug);
        extentLogger.logInfo("Description: " + description);
        return description;
    }

    public String getNameTestCaseSubmitBug() {
        String name = getText(lblNameTestCase);
        extentLogger.logInfo("Name test case: " + name);
        return name;
    }

    public LotcheckReportProductProductInformation gotoProductInformation() {
        extentLogger.logInfo("Opening Product Information");
        waitForAndClickElement(lnkGoToProductInformation);
        return getNewPage(LotcheckReportProductProductInformation.class);
    }

    public LotcheckReportSubmissionLotcheckIssueDetails refreshPageSLCMS() {
        extentLogger.logInfo("");
        driver.navigate().refresh();
        waitForElementVisible(iconLoading, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        waitForElementNotVisible(iconLoading, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        waitForElementVisible(lblPartnerThreadStatus, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssueDetails performPublishIssue(String type) {
        extentLogger.logInfo("");
        waitForAndClickElement(btnMenuOption);
        waitForAndClickElement(lnkPublishToPublic);
        WaitUtils.idle(5000);
        if (type.equals(TextConstants.submissionIssue)) {
            List<WebElement> elements = findElements(ddlLCIType);
            elements.get(0).sendKeys(Keys.ENTER);
            elements.get(0).sendKeys(Keys.ARROW_DOWN);
            elements.get(0).sendKeys(Keys.ENTER);
            elements.get(0).sendKeys(Keys.TAB);
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        List<WebElement> elements1 = findElements(lstSubmit);
        js.executeScript("arguments[0].click();", elements1.get(0));
        waitDocumentReady();
        return this;
    }

    public String getTitleIssueValue() {
        String value = getText(lblTitleIssueValue);
        extentLogger.logInfo(value);
        return value;
    }

    public String getDescriptionIssueValue() {
        String value = getText(lblDescriptionIssueValue);
        extentLogger.logInfo(value);
        return value;
    }

    public boolean checkTwoLanguageDisplay() {
        boolean value = true;
        if (elementNotExists(btnTwoLanguage)) {
            return false;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public LotcheckResetIssueDialog selectResetIssue() {
        waitForAndClickElement(btnResetIssue);
        extentLogger.logInfo("");
        return getNewPage(LotcheckResetIssueDialog.class);
    }

    public LotcheckReportSubmissionLotcheckIssueDetails clickTwoLanguageDisplay() {
        waitForAndClickElement(btnTwoLanguage);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssueDetails clickMenuAtIssueDetails() {
        waitForAndClickElement(btnMenuAtIssueDetail);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckEditDetailsDialog selectEditDetailsEN() {
        waitForAndClickElement(btnEditDetailsEN);
        extentLogger.logInfo("");
        return getNewPage(LotcheckEditDetailsDialog.class);
    }

    public LotcheckReportSubmissionLotcheckIssueDetails waitAlertDone() {
        waitForElementNotVisible(lblAlert);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssueDetails openTranslationTask() {
        waitForAndClickElement(btnOpenTranslationTask);
        extentLogger.logInfo("");
        return this;
    }

    public boolean openTranslationTasksIsEnable() {
        extentLogger.logInfo("");
        try {
            WebElement mail = driver.findElement(btnOpenTranslationTask);
            return mail.isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean openTranslationFormIsDisplay() {
        extentLogger.logInfo("");
        try {
            WebElement mail = driver.findElement(lnkOpenTranslationTaskForm);
            return mail.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean re_enableActionMenu() {
        extentLogger.logInfo("");
        try {
            WebElement mail = driver.findElement(btnMenuAtIssueDetail);
            return mail.isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getTranslationStatus() {
        extentLogger.logInfo("");
        WebElement e = driver.findElement(lblTranslationStatus);
        String value = e.getText().trim();
        List<WebElement> children = e.findElements(By.xpath("./*"));
        for (WebElement child : children) {
            value = value.replaceFirst(child.getText(), "").trim();
        }
        return value;
    }

    public LotcheckReportSubmissionLotcheckIssueDetails clickConfirmOpenTranslationTask() {
        waitForAndClickElement(btnConfirmTranslationTaskForm);
        extentLogger.logInfo("");
        waitForElementNotVisible(btnConfirmTranslationTaskForm);
        return this;
    }

    public String getFormCancelButton() {
        String value = getText(btnCancelTranslationTaskForm);
        extentLogger.logInfo(value);
        return value;
    }

    public String getFormConfirmButton() {
        String value = getText(btnConfirmTranslationTaskForm);
        extentLogger.logInfo(value);
        return value;
    }

    public String getFormName() {
        String value = getText(lblFormName);
        extentLogger.logInfo(value);
        return value;
    }

    public String getFormMessage() {
        String value = getText(lblFormMessage);
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckReportSubmissionLotcheckIssues clickBackToLotcheckIssue() {
        waitForAndClickElement(lnkBackLotcheckIssue);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionLotcheckIssues.class);
    }

    public String getStatusOfIssue() {
        String value = getText(lblStatusOfIssue);
        extentLogger.logInfo(value);
        return value;
    }

    public String getWarningIssue() {
        String value = getText(lblWarningIssue);
        extentLogger.logInfo(value);
        return value;
    }

    public boolean checkEditDetailsENDisplay() {
        boolean value = true;
        if (elementNotExists(btnEditDetailsEN)) {
            return false;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public boolean checkPublishToPublicDisplay() {
        boolean value = true;
        if (elementNotExists(lnkPublishToPublic)) {
            return false;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public boolean checkResetIssueDisplay() {
        boolean value = true;
        if (elementNotExists(btnResetIssue)) {
            return false;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public LotcheckReportSubmissionLotcheckIssueThread clickThreadIssueTab() {
        waitForAndClickElement(lnkLotcheckThreadTab);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionLotcheckIssueThread.class);
    }

    public String getPublishedTitle() {
        String value = getText(lblPublishedTitle);
        extentLogger.logInfo(value);
        return value;
    }

    public String getPublishedDescription() {
        String value = getText(lblPublishedDescription);
        extentLogger.logInfo(value);
        return value;
    }
    public String getReasonForRequest() {
        String reasonForRequest = getText(lblReasonForRequest);
        extentLogger.logInfo(reasonForRequest);
        return reasonForRequest;
    }

    public String getResponseForApprovalOrRejection() {
        String responseForApprovalOrRejection = getText(lblResponseForApprovalOrRejection);
        extentLogger.logInfo(responseForApprovalOrRejection);
        return responseForApprovalOrRejection;
    }

    public LotcheckEditDetailsDialog selectEditDetailsJA() {
        waitForAndClickElement(btnEditDetailsJA);
        extentLogger.logInfo("");
        return getNewPage(LotcheckEditDetailsDialog.class);
    }
    public String getPublishDescription() {
        String description = getText(lblPublishDescription).trim();
        extentLogger.logInfo("Description: " + description);
        return description;
    }



}
