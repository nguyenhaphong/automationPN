package net.nintendo.automation.ui.models.lcms.lotcheck_issue_translation;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LotcheckIssueTranslationTaskDetails extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@data-testid='translation-tasks-container']");
    private final By lnkIssueDetails = By.xpath("//li[@data-testid='filter-tab']//button[text()='Issue Details']");
    private final By lblTranslationTaskType = By.xpath("//*[@id='myTab']//..//div[@class='accordion-content'][1]//dt[2]");
    private final By lblTranslationStatus = By.xpath("//*[@id='myTab']//..//div[@class='accordion-content'][1]//dd[3]");
    private final By btnMenuOverview = By.id("dropdownMenuLink");
    private final By lnkAssignTask = By.xpath("//*[@id='dropdownMenuLink']/..//button[@class='dropdown-item']");
    private final By ddlAssignTo = By.xpath("//*[@data-testid='select-assigned-to']");
    private final By btnSave = By.xpath("//*[@data-testid='submit-button']");
    private final By pnlAlert = By.xpath("//*[@role='alert' and contains(text(),'assigned']");
    private final By lblValueAssignedTo = By.xpath("//*[@id='myTab']//..//div[@class='accordion-content'][1]//dd[1]");

    //private final By alert = By.xpath("//*[@role='alert' and contains(text(),'assigned')]");
    private final By alert =  By.xpath("//div[@class='Toastify__toast Toastify__toast--success']");
    private final By translationTaskType = By.xpath("//*[@id='myTab']//..//div[@class='accordion-content'][1]//dd[2]");
    private final By alertError = By.xpath("//*[@role='alert']");

    private final By translationStatus = By.xpath("//*[@id='myTab']//..//div[@class='accordion-content'][1]//dd[3]");
    private final By valueAssignedTo = By.xpath("//*[@id='myTab']//..//div[@class='accordion-content'][1]//dd[1]");
    private final By txtTitleScreen = By.xpath("//h2[@class='mb-3']");
    private final By btnCloseWarningMessage = By.xpath("//button[@class='Toastify__close-button Toastify__close-button--error']");
    private final By loadingIcon = By.xpath("//div[@data-testid=\"spinner-loading\"]");
    private final By btnCloseOnAlert= By.xpath("//button[@aria-label='close']");

    public LotcheckIssueTranslationTaskDetails(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public LotcheckLandingPage nav() {
        return getNewPage(LotcheckLandingPage.class);
    }

    public LotcheckIssueTranslationTaskDetails waitAssignTaskSuccess() {
        waitForElementNotVisible(pnlAlert);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckLandingPage navLandingPage() {
        return getNewPage(LotcheckLandingPage.class);
    }

    public LotcheckIssueTranslationIssueDetails clickIssueDetail() {
        waitForAndClickElement(lnkIssueDetails);
        extentLogger.logInfo("");
        return getNewPage(LotcheckIssueTranslationIssueDetails.class);
    }

    public LotcheckIssueTranslationTaskDetails clickMenuOverview() {
        waitForAndClickElement(btnMenuOverview);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckIssueTranslationTaskDetails clickAssignTask() {
        waitForElementVisible(lnkAssignTask);
        waitForAndClickElement(lnkAssignTask);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckIssueTranslationTaskDetails selectAssignTo(String account) {
        selectValueOnDropDown(ddlAssignTo, account);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckIssueTranslationTaskDetails clickSave() {
        waitForAndClickElement(btnSave);
        extentLogger.logInfo("");
        waitForElementNotVisible(btnSave);
        waitForElementNotVisible(loadingIcon, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        return this;
    }
   /* public LotcheckIssueTranslationTaskDetails clickSave() {
        waitForAndClickElement(btnSave);
        extentLogger.logInfo("");
        waitForElementNotVisible(btnSave);
        return this;
    }*/

    public String getLblTranslationTaskType() {
        String value = getText(lblTranslationTaskType);
        extentLogger.logInfo(value);
        return value;
    }

    public String getLblTranslationStatus() {
        String value = getText(lblTranslationStatus);
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckIssueTranslationTaskDetails waitAlertAssignTaskDone() {
        waitForElementNotVisible(pnlAlert);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckIssueTranslationTaskDetails waitAssignSuccess() {
        for (int i = 0; i < 50; i++) {
            String value = getText(lblValueAssignedTo);
            if (!value.equals("SLCMS Translator NCL")) {
                driver.navigate().refresh();
                extentLogger.logInfo("");
                waitForElementVisible(uniqueElement, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
                extentLogger.logInfo("");
                clickMenuOverview();
                extentLogger.logInfo("");
                clickAssignTask();
                extentLogger.logInfo("");
                selectAssignTo("Me (SLCMS Translator NCL)");
                extentLogger.logInfo("");
                clickSave();
                WaitUtils.idle(10000);
            } else {
                break;
            }
        }
        return this;
    }

    public String getTitleScreen() {
        String title = getText(txtTitleScreen).split("\n")[0];
        extentLogger.logInfo(title);
        return title;
    }


    public boolean displaySuccessMessage() {
        extentLogger.logInfo("");
        try {
            WebElement element = driver.findElement(alert);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    public String getTranslationTaskType() {
        String value = getText(translationTaskType);
        extentLogger.logInfo(value);
        return value;
    }

    public String getTranslationStatus() {
        String value = getText(translationStatus);
        extentLogger.logInfo(value);
        return value;
    }
    public boolean assignToUserSuccess(String userName){
        Boolean success = false;
        String value = getText(lblValueAssignedTo);
        if (value.equals(userName)) {
            success=true;
        }
        extentLogger.logInfo(success.toString());
        return success;
    }
    public String getAlertError() {
        String value = getText(alertError).trim();
        extentLogger.logInfo(value);
        return value;
    }
    public LotcheckIssueTranslationTaskDetails clickCloseButtonOnAlert() {
        waitForAndClickElement(btnCloseOnAlert);
        extentLogger.logInfo("");
        return this;
    }


}
