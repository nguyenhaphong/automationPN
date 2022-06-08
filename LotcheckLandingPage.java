package net.nintendo.automation.ui.models.lcms;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation_core.common_utils.enums.LotcheckRegion;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LotcheckLandingPage extends CommonUIComponent {
    private static final By uniqueElement = By.id("navbarNav");
    private static final String pageTitle = "Lotcheck - LCMS";

    private final By mnuUserMenu = By.xpath("//*[@id='hyrule-nav']//li[contains(@class,'notification')]//div[contains(@class,'nav-link dropdown-toggle')]");
    private final By btnSignOuInUserMenu = By.xpath("//*[@id='hyrule-nav']//..//button[contains(text(),'Log Out')]");
    private final By btnMyAccountInUserMenu = By.xpath("//*[@id='hyrule-nav']//button[@data-testid='add-to-schedule-group-button']");
    private final By lblLanguage = By.xpath("//form[@data-testid='set-planned-test-date-modal-form']//div[@class='form-group row'][1]//label[1]");
    private final By btnCancel = By.xpath("//button[@data-testid='set-planned-test-date-modal-cancel']");
    private final By nclSelector = By.cssSelector(".col-4:nth-child(1) li:nth-child(2) a");
    private final By nclJudgmentSelector = By.cssSelector(".col-4:nth-child(1) li:nth-child(6) a");
    private final By nclInTestSelector = By.cssSelector(".col-4:nth-child(1) li:nth-child(4) a");
    private final By noaInTestSelector = By.cssSelector(".col-4:nth-child(2) li:nth-child(4) a");
    private final By nclTestSetUpSelector = By.cssSelector(".col-4:nth-child(1) li:nth-child(3) a");
    private final By noaTestSetUpSelector = By.cssSelector(".col-4:nth-child(2) li:nth-child(3) a");
    private final By nclInTestingSelector = By.cssSelector(".col-4:nth-child(1) li:nth-child(5) a");
    private final By noaInTestingSelector = By.cssSelector(".col-4:nth-child(2) li:nth-child(5) a");
    private final By noaJudgmentSelector = By.cssSelector(".col-4:nth-child(2) li:nth-child(6) a");
    private final By noeJudgmentSelector = By.cssSelector(".col-4:nth-child(3) li:nth-child(6) a");
    private final By noaSelector = By.cssSelector(".col-4:nth-child(2) li:nth-child(2) a");
    private final By iconLoadPage = By.xpath("//div[@data-testid='hyruleAppContent']//div[@data-testid='spinner-loading']");
    private static By submissionSearch = By.xpath("//a[contains(@href,'#/submission-search')]");
    private final By nclPendingReSolutionSelector = By.cssSelector(".col-4:nth-child(1) li:nth-child(7) a");
    private final By noaPendingReSolutionSelector = By.cssSelector(".col-4:nth-child(2) li:nth-child(7) a");
    private final By noePendingReSolutionSelector = By.cssSelector(".col-4:nth-child(3) li:nth-child(7) a");
    private final By noeInTestSelector = By.cssSelector(".col-4:nth-child(3) li:nth-child(4) a");
    private final By noeTestSetUpSelector = By.cssSelector(".col-4:nth-child(3) li:nth-child(3) a");
    private final By noeInTestingSelector = By.cssSelector(".col-4:nth-child(3) li:nth-child(5) a");
    private final By noeSelector = By.cssSelector(".col-4:nth-child(3) li:nth-child(2) a");
    private final By ddTranslationTasks = By.xpath("//*[@id='navbarNav']//span[.='Translation Tasks']");
    private final By btnAllTranslationTasks = By.xpath("//*[@id='navbarNav']//a[contains(@href,'all-translation-tasks')]");
    private final By btnRequiredApprovalTranslationTasks = By.xpath("//*[@id='navbarNav']//a[contains(@href,'required-approval-translation-tasks')]");


    public LotcheckLandingPage(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public LotcheckQueuePage clickTesterAssignment(LotcheckRegion locale) {
        switch (locale) {
            case NCL:
                waitForAndClickElement(nclSelector);
                break;
            case NOA:
                this.waitForAndClickElement(noaSelector);
                break;
            case NOE:
                this.waitForAndClickElement(noeSelector);
                break;
            default:
                //nothing
        }

        return getNewPage(LotcheckQueuePage.class);
    }


    public LotcheckQueuePage clickTestSetupReady(LotcheckRegion locale) {
        switch (locale) {
            case NCL:
                waitForAndClickElement(nclTestSetUpSelector);
                break;
            case NOA:
                this.waitForAndClickElement(noaTestSetUpSelector);
                break;
            case NOE:
                this.waitForAndClickElement(noeTestSetUpSelector);
                break;
            default:
                //nothing
        }

        return getNewPage(LotcheckQueuePage.class);
    }


    public LotcheckQueuePage clickInTestSetup(LotcheckRegion locale) {
        switch (locale) {
            case NCL:
                this.waitForAndClickElement(nclInTestSelector);
                break;
            case NOA:
                this.waitForAndClickElement(noaInTestSelector);
                break;
            case NOE:
                this.waitForAndClickElement(noeInTestSelector);
                break;
            default:
                //nothing
        }

        return getNewPage(LotcheckQueuePage.class);
    }


    public LotcheckQueuePage clickInTesting(LotcheckRegion locale) {
        switch (locale) {
            case NCL:
                this.waitForAndClickElement(nclInTestingSelector);
                break;
            case NOA:
                this.waitForAndClickElement(noaInTestingSelector);
                break;
            case NOE:
                this.waitForAndClickElement(noeInTestingSelector);
                break;
            default:
                //nothing
        }

        return getNewPage(LotcheckQueuePage.class);
    }


    public LotcheckQueuePage clickJudgment(LotcheckRegion locale) {
        switch (locale) {
            case NCL:
                this.waitForAndClickElement(nclJudgmentSelector);
                break;
            case NOA:
                this.waitForAndClickElement(noaJudgmentSelector);
                break;
            case NOE:
                this.waitForAndClickElement(noeJudgmentSelector);
                break;
            default:
        }

        return getNewPage(LotcheckQueuePage.class);
    }

    public LotcheckSubmissionSearchPage searchProduct(String searchText) {
        extentLogger.logInfo("Searching for Product: " + searchText);
        waitForAndClickElement(By.xpath("//nav//input[@name='searchInput']"));
        typeText(By.xpath("//nav//input[@name='searchInput']"), searchText);
        waitForAndClickElement(By.xpath("//nav//i[contains(@class,'fa-search')]"));
        waitForElementVisible(By.xpath("//div[@data-testid=\"spinner-loading\"]"), WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        waitForElementNotVisible(By.xpath("//div[@data-testid=\"spinner-loading\"]"), WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        waitDocumentReady();
        return getNewPage(LotcheckSubmissionSearchPage.class);

    }

    public LotcheckQueuePage navigationToTesterAssignmentQueue(String region) {
        extentLogger.logInfo("Navigate To Tester Assignment Queue : " + region);
        waitForAndClickElement(By.xpath("//*[@id='navbarNav']//span[.='" + region + "']"));
        waitForAndClickElement(By.xpath("//*[@id='navbarNav']//a[contains(@href,'tester-assignment') and contains(@href,'" + region + "')]"));
        return getNewPage(LotcheckQueuePage.class);
    }

    public LotcheckQueuePage navigationToTestSetupReadyQueue(String region) {
        extentLogger.logInfo("Navigate To Test Setup Ready Queue : " + region);
        waitForAndClickElement(By.xpath("//*[@id='navbarNav']//span[.='" + region + "']"));
        waitForAndClickElement(By.xpath("//*[@id='navbarNav']//a[contains(@href,'test-setup-ready') and contains(@href,'" + region + "')]"));
        return getNewPage(LotcheckQueuePage.class);
    }

    public LotcheckQueuePage navigationToInTestSetupQueue(String region) {
        extentLogger.logInfo("Navigate To In Test Setup Queue : " + region);
        waitForAndClickElement(By.xpath("//*[@id='navbarNav']//span[.='" + region + "']"));
        waitForAndClickElement(By.xpath("//*[@id='navbarNav']//a[contains(@href,'in-test-setup') and contains(@href,'" + region + "')]"));
        return getNewPage(LotcheckQueuePage.class);
    }

    public LotcheckQueuePage navigationToInTestingQueue(String region) {
        extentLogger.logInfo("Navigate To In Testing Queue : " + region);
        waitForAndClickElement(By.xpath("//*[@id='navbarNav']//span[.='" + region + "']"));
        waitForAndClickElement(By.xpath("//*[@id='navbarNav']//a[contains(@href,'in-test/') and contains(@href,'" + region + "')]"));
        return getNewPage(LotcheckQueuePage.class);
    }

    public LotcheckQueuePage navigationToPendingResolutionQueue(String region) {
        extentLogger.logInfo("Navigate To Pending Resolution Queue : " + region);
        waitForAndClickElement(By.xpath("//*[@id='navbarNav']//span[.='" + region + "']"));
        waitForAndClickElement(By.xpath("//*[@id='navbarNav']//a[contains(@href,'pending-resolution') and contains(@href,'" + region + "')]"));
        return getNewPage(LotcheckQueuePage.class);
    }

    public LotcheckQueuePage navigationToJudgmentApprovalQueue(String region) {
        extentLogger.logInfo("Navigate To Judgment Approval Queue : " + region);
        waitForAndClickElement(By.xpath("//*[@id='navbarNav']//span[.='" + region + "']"));
        waitForAndClickElement(By.xpath("//*[@id='navbarNav']//a[contains(@href,'judgment-approval') and contains(@href,'" + region + "')]"));
        return getNewPage(LotcheckQueuePage.class);
    }

    public LotcheckQueuePage navigationToFinishedQueue(String region) {
        extentLogger.logInfo("Navigate To Finished Queue : " + region);
        waitForAndClickElement(By.xpath("//*[@id='navbarNav']//span[.='" + region + "']"));
        waitForAndClickElement(By.xpath("//*[@id='navbarNav']//a[contains(@href,'finished') and contains(@href,'" + region + "')]"));
        return getNewPage(LotcheckQueuePage.class);
    }

    public LotcheckLandingPage clickOnUserNameMenu() {
        extentLogger.logInfo("");
        waitForAndClickElement(mnuUserMenu);
        return this;
    }

    public LotcheckHomePage clickOnSignoutButton() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnSignOuInUserMenu);
        return getNewPage(LotcheckHomePage.class);
    }

    public LotcheckEditAccountPreferences clickMyAccount() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnMyAccountInUserMenu);
        return getNewPage(LotcheckEditAccountPreferences.class);
    }

    public String getLanguage() {
        String language = getText(lblLanguage);
        extentLogger.logInfo("Language:" + language);
        return language;
    }

    public LotcheckLandingPage clickCancelButtonOnEditAccountPreferences() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnCancel);
        return this;
    }

    public LotcheckQueuePage clickFinish(LotcheckRegion locale) {
        switch (locale) {
            case NCL:
                this.waitForAndClickElement(
                        By.cssSelector(".col-4:nth-child(1) li:nth-child(8) a")
                );
                break;
            case NOA:
                this.waitForAndClickElement(
                        By.cssSelector(".col-4:nth-child(2) li:nth-child(8) a")
                );
                break;
            case NOE:
                this.waitForAndClickElement(
                        By.cssSelector(".col-4:nth-child(3) li:nth-child(8) a")
                );
                break;

            default:
        }

        return getNewPage(LotcheckQueuePage.class);
    }

    public String getLabelLanguage() {
        String language = getText(lblLanguage);
        extentLogger.logInfo("Language:" + language);
        return language;
    }

    public LotcheckQueuePage clickOverallLotcheck(LotcheckRegion locale) {
        switch (locale) {
            case NCL:
                this.waitForAndClickElement(
                        By.cssSelector(".col-4:nth-child(1) li:nth-child(1) a")
                );
                break;
            case NOA:
                this.waitForAndClickElement(
                        By.cssSelector(".col-4:nth-child(2) li:nth-child(1) a")
                );
                break;
            case NOE:
                this.waitForAndClickElement(
                        By.cssSelector(".col-4:nth-child(3) li:nth-child(1) a")
                );
                break;
            default:
        }

        return getNewPage(LotcheckQueuePage.class);
    }


    public LotcheckQueuePage clickPendingResolution(LotcheckRegion locale) {
        switch (locale) {
            case NCL:
                this.waitForAndClickElement(nclPendingReSolutionSelector);
                break;
            case NOA:
                this.waitForAndClickElement(noaPendingReSolutionSelector);
                break;
            case NOE:
                this.waitForAndClickElement(noePendingReSolutionSelector);
                break;
            default:
        }

        return getNewPage(LotcheckQueuePage.class);
    }


    public LotcheckQueuePage navigationToOverallLotcheckQueue(String region) {
        extentLogger.logInfo("Navigate To Tester Lotcheck Queue : " + region);
        waitForAndClickElement(By.xpath("//*[@id='navbarNav']//span[.='" + region + "']"));
        waitForAndClickElement(By.xpath("//*[@id='navbarNav']//a[contains(@href,'overall-lotcheck') and contains(@href,'" + region + "')]"));
        return getNewPage(LotcheckQueuePage.class);
    }

    public LotcheckIssueTranslationTasksPage navigationToLotcheckIssueTranslationTasks() {
        extentLogger.logInfo("");
        waitForAndClickElement(By.xpath("//*[@id='navbarNav']//span[.='Translation Tasks']"));
        waitForAndClickElement(By.xpath("//*[@id='navbarNav']//a[contains(@href,'lotcheck-issue-translation-tasks')]"));
        return getNewPage(LotcheckIssueTranslationTasksPage.class);
    }

    public LotcheckSubmissionSearchPage navigateToSubmissionSearch() {
        waitForAndClickElement(submissionSearch);
        extentLogger.logInfo("");
        waitForElementVisible(By.xpath("//div[@data-testid=\"spinner-loading\"]"), WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        waitForElementNotVisible(By.xpath("//div[@data-testid=\"spinner-loading\"]"), WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        waitDocumentReady();
        return getNewPage(LotcheckSubmissionSearchPage.class);

    }

    public LotcheckIssueTranslationTasksPage navigationToAllTranslationTasks() {
        extentLogger.logInfo("");
        waitForAndClickElement(By.xpath("//*[@id='navbarNav']//span[.='Translation Tasks']"));
        waitForAndClickElement(By.xpath("//*[@id='navbarNav']//a[contains(@href,'all-translation-tasks')]"));
        return getNewPage(LotcheckIssueTranslationTasksPage.class);
    }
    public RequiredApprovalTranslationTasksPage navigationToRequiredApprovalTranslationTasks() {
        extentLogger.logInfo("");
        waitForAndClickElement(ddTranslationTasks);
        waitForAndClickElement(btnRequiredApprovalTranslationTasks);
        return getNewPage(RequiredApprovalTranslationTasksPage.class);
    }

    public AllTranslationTasksPage navigationToLCAllTranslationTasks() {
        extentLogger.logInfo("");
        waitForAndClickElement(ddTranslationTasks);
        waitForAndClickElement(btnAllTranslationTasks);
        return getNewPage(AllTranslationTasksPage.class);
    }
}