package net.nintendo.automation.ui.models.lcms;

import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.utils.WaitUtils;
import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.List;

public class LotcheckTestPlanRow extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//tr[@data-testid='test-plan-row']");
    private static final String pageTitle = "";
    private static By lstTestPlanSuspended = By.xpath("//table[@class='table table-queue table-nin table-striped table-sm']/tbody/tr/td[8]/strong");
    private static By lstTestPlans = By.xpath("//table[@class='table table-queue table-nin table-striped table-sm']/tbody/tr");
    private static By menuActions = By.xpath("//td[@class='align-middle text-center']/div/button/i[@class='fas fa-ellipsis-h']");
    private final By lstNameTestPlan = By.xpath("//a[contains(@href,'lotcheck-report-submission-level')]");
    private final By lstRowVisible = By.cssSelector("i.fas.fa-ellipsis-h");
    private final By btnCancelOnPopupConfirm = By.xpath("//div[@data-testid='action-confirmation-modal']//button[@class='btn btn-secondary mr-auto']");
    private final By rowVisibleElement = By.cssSelector("i.fas.fa-ellipsis-h");
    private static By iconLCO = By.xpath("//*[@data-testid='lco-icon-test']");
    private final By menuSchedule = By.xpath("//button[@class='collapse-link']/ancestor::h1/preceding-sibling::div//button[@id='dropdownMenuLink']");
    private final By btnCreateNewScheduleGroup = By.xpath("//button[@class='collapse-link']/ancestor::h1/preceding-sibling::div//button[@class='dropdown-item'][1]");
    private final By btnReorderScheduleGroup = By.xpath("//button[@class='collapse-link']/ancestor::h1/preceding-sibling::div//button[@class='dropdown-item'][2]");
    private final By btnSaveChange = By.xpath("//div[@class='save-buttons']//button[@data-testid=\"submit-button\"]");
    private final By iconLoading = By.xpath("//div[@data-testid=\"spinner-loading\"]");

    public LotcheckTestPlanRow(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public LotcheckTestPlanActionConfirmationModal selectAssignmentComplete() {
        extentLogger.logInfo("");
        this.selectElementFromDropDown(TextConstants.assignmentComplete);
        return getNewPage(LotcheckTestPlanActionConfirmationModal.class);
    }

    public LotcheckTestPlanActionConfirmationModal selectStartTestSetupDev() {
        extentLogger.logInfo("");
        this.selectElementFromDropDown(TextConstants.startTestSetupDev);
        return getNewPage(LotcheckTestPlanActionConfirmationModal.class);
    }

    public LotcheckTestPlanActionConfirmationModal selectStartTestSetupProd() {
        extentLogger.logInfo("");
        this.selectElementFromDropDown(TextConstants.startTestSetupProd);
        return getNewPage(LotcheckTestPlanActionConfirmationModal.class);
    }

    public LotcheckTestPlanActionConfirmationModal selectSetupCompleteDev() {
        extentLogger.logInfo("");
        this.selectElementFromDropDown("Setup Complete (DEV)");
        return getNewPage(LotcheckTestPlanActionConfirmationModal.class);
    }

    public LotcheckTestPlanActionConfirmationModal selectSetupCompleteProd() {
        extentLogger.logInfo("");
        this.selectElementFromDropDown("Setup Complete (PROD)");
        return getNewPage(LotcheckTestPlanActionConfirmationModal.class);
    }

    public LotcheckTestPlanActionConfirmationModal selectApproveJudgmentOfFirstTestPlan() throws InterruptedException {
        extentLogger.logInfo("");
        this.selectElementFromDropDownOnFirstTestPlan(TextConstants.approveJudgement);
        return getNewPage(LotcheckTestPlanActionConfirmationModal.class);
    }

    public LotcheckTestPlanActionConfirmationModal selectApproveJudgment() {
        extentLogger.logInfo("");
        this.selectElementFromDropDown(TextConstants.approveJudgement);
        return getNewPage(LotcheckTestPlanActionConfirmationModal.class);
    }

    public LotcheckTestPlanActionConfirmationModal selectRejectJudgment() {
        extentLogger.logInfo("");
        this.selectElementFromDropDown(TextConstants.rejectJudgement);
        return getNewPage(LotcheckTestPlanActionConfirmationModal.class);
    }

    // TODO: Find xpath to define element and rename locator
    public String getTestSetupText() {
        return getText(By.cssSelector(".align-middle:nth-child(7)"));
    }

    public boolean doesRowExist() {
        extentLogger.logInfo("");
        List<WebElement> elements = this.findElements(lstRowVisible);
        return elements.size() > 0;
    }

    public boolean isRowVisible() {
        extentLogger.logInfo("");
        WebElement element = driver.findElement(lstRowVisible);
        return element.isDisplayed();
    }

    public LotcheckTestPlanRow selectMenuActionOfFirstTestPlan() {
        extentLogger.logInfo("");
        driver.findElements(menuActions).get(0).click();
        return this;
    }

    public LotcheckTestPlanActionConfirmationModal selectElementFromDropDownOnFirstTestPlan(String text) throws InterruptedException {
        extentLogger.logInfo("");
        this.selectMenuActionOfFirstTestPlan();
        this.waitForAndClickElement(
                By.xpath("//div/button[text()='" + text + "']")
        );
        WaitUtils.idle(60000);
        return getNewPage(LotcheckTestPlanActionConfirmationModal.class);
    }

    public LotcheckTestPlanActionConfirmationModal selectElementFromDropDown(String text) {
        extentLogger.logInfo("");
        this.waitForAndClickElement(lstRowVisible);
        this.waitForAndClickElement(
                By.xpath("//div/button[text()='" + text + "']")
        );
        return getNewPage(LotcheckTestPlanActionConfirmationModal.class);
    }

    public LotcheckTestPlanRow startAlert() {
        extentLogger.logInfo("");
        waitForElementNotVisible(btnCancelOnPopupConfirm, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        waitForAndClickElement(By.xpath("//*[@id='root']//div[@role='alert']"));
        return this;
    }

    public int numberTestPlanSuspendedDisplay() {
        extentLogger.logInfo("");
        extentLogger.logInfo("number Test Plan Suspended Display");
        List<WebElement> testPlanSuspended = driver.findElements(lstTestPlanSuspended);
        return testPlanSuspended.size();
    }

    public boolean verifyTestPlanNumberDisplay(int numberTestPlan) {
        extentLogger.logInfo("verify Test Plan Display");
        List<WebElement> testPlanSuspended = driver.findElements(lstTestPlans);
        if (testPlanSuspended.size() == numberTestPlan) {
            return true;
        } else return false;
    }

    public LotcheckReportSubmissionOverview navigateToSubmissionLevelOnFirstTestPlan() {
        extentLogger.logInfo("navigate To Submission Level On First Test Plan");
        List<WebElement> testPlanElement = driver.findElements(lstNameTestPlan);
        String parentHandle = driver.getWindowHandle();
        testPlanElement.get(0).click();
        driver.switchTo().window(parentHandle);
        driver.close();
        for (String winHandle : driver.getWindowHandles())
            driver.switchTo().window(winHandle);
        return getNewPage(LotcheckReportSubmissionOverview.class);
    }

    public LotcheckTestPlanRow performAction(String text) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        List<WebElement> elements = findElements(rowVisibleElement);
        js.executeScript("arguments[0].click();", elements.get(0));
        WaitUtils.idle(5000);
        List<WebElement> elements1 = findElements(By.xpath("//div/button[text()='" + text + "']"));
        js.executeScript("arguments[0].click();", elements1.get(0));
        WaitUtils.idle(3000);
        waitDocumentReady();
        List<WebElement> elements2 = findElements(By.xpath("//div[@data-testid='action-confirmation-modal']//button[@data-testid='submit-button']"));
        js.executeScript("arguments[0].click();", elements2.get(0));
        WaitUtils.idle(10000);
        int i = 0;
        while (i < 200) {
            if (elementNotExists(By.xpath("//div[@data-testid='action-confirmation-modal']//button[@data-testid='submit-button']"))) {
                break;
            }
            i++;
            WaitUtils.idle(5000);
        }
        WaitUtils.idle(3000);
        return this;
    }

    public boolean isTestPlanDisplayed(String gameCode) {
        boolean value = false;
        if (!elementNotExists(By.xpath(String.format("//a[contains(text(),'%s')]", gameCode)))) {
            value = true;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public ArrayList<String> getTargetDeliveryFormat() {
        List<WebElement> testplans = driver.findElements(By.xpath("//div[@class='lotcheck-distribution-number']"));
        ArrayList<String> values = new ArrayList<>();
        for (WebElement testplan : testplans) {
            String value = testplan.getText().trim();
            if (value.contains(TextConstants.card_Type)) {
                values.add(TextConstants.card_Type);
            } else if (value.contains(TextConstants.dl_Type)) {
                values.add(TextConstants.dl_Type);
            }
        }
        extentLogger.logInfo(values.toString());
        return values;
    }

    public boolean iconLCOIsDisplay() {
        extentLogger.logInfo("");
        try {
            WebElement element = driver.findElement(iconLCO);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public LotcheckLandingPage navLotcheckLandingPage() {
        return getNewPage(LotcheckLandingPage.class);
    }

    public LotcheckTestPlanRow clickMenuSchedule() {
        extentLogger.logInfo("");
        waitForAndClickElement(menuSchedule);
        return this;
    }

    public LotcheckCreateNewScheduleGroupDialog selectCreateNewScheduleGroup() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnCreateNewScheduleGroup);
        return getNewPage(LotcheckCreateNewScheduleGroupDialog.class);
    }

    public LotcheckReorderScheduleGroupDialog selectReorderScheduleGroup() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnReorderScheduleGroup);
        return getNewPage(LotcheckReorderScheduleGroupDialog.class);
    }

    public LotcheckTestPlanRow clickSaveChange() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnSaveChange);
        waitForElementVisible(iconLoading, WaitTimes.WAIT_FOR_ELEMENT_VISIBLE_TIMEOUT_SECS.multipliedBy(30));
        waitForElementNotVisible(iconLoading, WaitTimes.WAIT_FOR_ELEMENT_VISIBLE_TIMEOUT_SECS.multipliedBy(30));
        return this;
    }

    public LotcheckTestPlanRow clickMenuSchedule(String scheduleGroup) {
        extentLogger.logInfo(scheduleGroup);
        waitForAndClickElement(By.xpath(String.format("//strong[text()='%s']/ancestor::h4/following-sibling::div//button[@id='dropdownMenuLink']", scheduleGroup)));
        return this;
    }

    public LotcheckAddTestPlansToScheduleGroupDialog selectAddTestPlans(String scheduleGroup) {
        extentLogger.logInfo(scheduleGroup);
        waitForAndClickElement(By.xpath(String.format("//strong[text()='%s']/ancestor::h4/following-sibling::div//button[@data-testid=\"add-test-plans-button\"]", scheduleGroup)));
        return getNewPage(LotcheckAddTestPlansToScheduleGroupDialog.class);
    }

    public LotcheckQueuePage refreshPageSLCMS() {
        extentLogger.logInfo("");
        driver.navigate().refresh();
        waitForElementVisible(iconLoading, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        waitForElementNotVisible(iconLoading, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        return getNewPage(LotcheckQueuePage.class);
    }

    public boolean isScheduleGroupNotDisplayed(String scheduleGroup) {
        boolean value = false;
        if (elementNotExists(By.xpath(String.format("//div[@class='group']//strong[@title='%s']", scheduleGroup)))) {
            value = true;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

}