package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.utils.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;

public class LotcheckReportMatrix extends CommonUIComponent {
    private final By lnkGoToProductLevel = By.xpath("//a[@class='go-to-product-level']");
    private static final By uniqueElement = By.xpath("//table[@class='table-matrix']");
    private static final String pageTitle = "";
    private final By lblSkipLotcheck = By.xpath("//span[@class='skip-lotcheck']");
    private final By lstTestPlanStatus = By.xpath("//a[@class='cell-title-link']");
    private final By btnChangeJudgment = By.xpath("//button[@class='change-judgment']");
    private final By testPlanReportLinks = By.xpath("//a[contains(@href,'lotcheck-report-testplan')]");
    private final By numberReportLinks = By.xpath("//a[contains(@href,'lotcheck-report-number-level')]");
    private static By iconLCO = By.xpath("//*[@class='region region-lco']//span");
    private static By backColorDepartment = By.xpath("//*[@class='region region-lco']");
    private static By overview = By.xpath("//a[contains(@href,'submission-level/overview') and contains(text(),'Overview')]");
    private static By testPlanLink = By.xpath("//*[@id='content']//tbody/tr/td[1]/a");
    private static By statusNCL = By.xpath("//*[@id='content']//tbody/tr/td[2]/a");
    private final By errorMessage = By.xpath("//div[@class='alert alert-danger']");
    private final By btnCloseMessage = By.xpath("//button[@class='close']");
    private final By iconLoadPage = By.xpath("//div[@data-testid='spinner-loading']");
    private final By lblJudgmentResultAndTestPlanName = By.xpath("//td[@class='activeRow']");

    public LotcheckReportMatrix(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public LotcheckReportMatrix waitToLoading() {
        waitForElementNotVisible(iconLoadPage, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportProductProductInformation clickGoToProductLevel() {
        WebElement element = driver.findElement(lnkGoToProductLevel);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportProductProductInformation.class);
    }

    public String getSkipTestPlan() {
        String value = getText(lblSkipLotcheck);
        extentLogger.logInfo(value);
        return value;
    }

    public boolean testPlanForExists(String type) {
        waitForElementVisible(By.xpath("//a[contains(text(),'" + type + "')]"), WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return true;
    }

    public boolean testPlanForNotExist(String type) {
        boolean value = false;
        if (elementNotExists(By.xpath("//a[contains(text(),'" + type + "')]"))) {
            value = true;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public String getTestPlanStatus(int index) {
        List<WebElement> testPlansStatus = driver.findElements(lstTestPlanStatus);
        String value = testPlansStatus.get(index).getText();
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckChangeSubmissionJudgment clickChangeJudgment() {
        waitForAndClickElement(btnChangeJudgment);
        extentLogger.logInfo("");
        return getNewPage(LotcheckChangeSubmissionJudgment.class);
    }

    public LotcheckReportNumberOverview clickGoToNumberReport() {
        List<WebElement> numberReport = driver.findElements(numberReportLinks);
        waitForElementClickable(numberReport.get(0), Duration.ofMinutes(3));
        waitForAndClickElement(numberReport.get(0));
        waitDocumentReady();
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportNumberOverview.class);
    }

    public LotcheckReportTestPlanOverview clickGoToTestPlanReport() {
        List<WebElement> testPlanReport = driver.findElements(testPlanReportLinks);
        waitForElementClickable(testPlanReport.get(0), Duration.ofMinutes(3));
        waitForAndClickElement(testPlanReport.get(0));
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportTestPlanOverview.class);
    }

    public boolean displayLCONCL() {
        extentLogger.logInfo("");
        try {
            WebElement element = driver.findElement(iconLCO);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String backColorMatrixIsWhite() {
        String color = checkBackgroundColor(backColorDepartment);
        extentLogger.logInfo(color);
        return color;
    }

    public LotcheckReportTestPlanOverview clickStatusTestPlanNCL() {
        WebElement element = driver.findElement(statusNCL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", element);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportTestPlanOverview.class);
    }

    public LotcheckReportNumberOverview clickTestPlanLink() {
        WebElement element = driver.findElement(testPlanLink);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", element);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportNumberOverview.class);
    }

    public LotcheckReportSubmissionOverview clickOverviewTabSubmissionReport() {
        waitForAndClickElement(overview);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionOverview.class);
    }

    public LotcheckReportSubmissionOverview clickOverviewTabNumberReport() {
        waitForAndClickElement(overview);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionOverview.class);
    }

    public LotcheckReportTestPlanOverview clickOverviewTabTestPlanReport() {
        waitForAndClickElement(overview);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportTestPlanOverview.class);
    }

    public String getErrorMessage() {
        waitForElementVisible(errorMessage, WaitTimes.WAIT_FOR_ELEMENT_VISIBLE_TIMEOUT_SECS.multipliedBy(5));
        String value = getText(errorMessage).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckReportMatrix clickCloseMessage() {
        waitForAndClickElement(btnCloseMessage);
        extentLogger.logInfo("");
        waitDocumentReady();
        return this;
    }

    public boolean isErrorMessageNotDisplayed() {
        boolean value = elementNotExists(errorMessage);
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public LotcheckReportNav navBar() {
        return getNewPage(LotcheckReportNav.class);
    }

    public LotcheckReportMatrix waitPageLoaded() {
        WaitUtils.idle(2000);
        waitForElementVisible(uniqueElement, WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        return this;
    }

    public LotcheckReportSubmissionOverview navLcrOverview() {
        return getNewPage(LotcheckReportSubmissionOverview.class);
    }

    public String getJudgmentResult() {
        String judgmentResult = getText(lblJudgmentResultAndTestPlanName).split("\n")[1];
        extentLogger.logInfo(judgmentResult);
        return judgmentResult;
    }

    public LotcheckLandingPage navLandingPage() {
        return getNewPage(LotcheckLandingPage.class);
    }

}
