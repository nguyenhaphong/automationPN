package net.nintendo.automation.ui.models.lcms;

import net.nintendo.automation.ui.constants.TextConstants;
import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthLogoutPage;
import net.nintendo.automation.ui.models.testrail.TestRailLoginPage;
import net.nintendo.automation.utils.WaitUtils;
import net.nintendo.automation_core.common_utils.enums.LotcheckRegion;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.*;

public class LotcheckQueuePage extends CommonUIComponent {

    private static By userNameMenuElement = By.xpath("//*[@id='hyrule-nav']//li[contains(@class,'notification')]//div[contains(@class,'nav-link dropdown-toggle')]");
    private static By btnLogout = By.xpath("//*[@id='hyrule-nav']//div[contains(@class,'profile-dropdown')]/button[@type='submit']");
    private static By nameTestPlanElement = By.xpath("//a[contains(@href,'lotcheck-report-submission-level')]");
    private static By btnCloseSuccessMessage = By.xpath("//button[@class='Toastify__close-button Toastify__close-button--success']");
    private static By btnErrorPopup = By.xpath("//button[@class='Toastify__close-button Toastify__close-button--success']"); // can edit
    private final By lnkNavUser = By.xpath("//div[contains(@class,'nav-user')]//span");
    private final By lnkSignOut = By.xpath("//i[@class='fas fa-sign-out-alt mr-1']");
    private final By lblJudgmentStatus = By.xpath("//tr[@data-testid='test-plan-row'][1]//td[8]//strong");
    private final By lblNameTestPlan = By.xpath("//tr[@data-testid='test-plan-row'][1]//td[2]//a");
    private static final By pnlDateTestPlan = By.xpath("//div[@data-testid='test-plan-date-section']");
    private static final By rowTestPlan = By.xpath("//table//tr[@data-testid='test-plan-row']");

    private static final By lnkPlannedTestDateNotSetTab = By.cssSelector("li.nav-item:nth-of-type(6)");
    private static final By ddpInitialCode = By.xpath("//button[@id='dropdownMenuLink' and contains(text(), 'Initial Code')]");
    private static final By iconAdd = By.cssSelector("i.fas.fa-plus");
    private final By btnSearch = By.xpath("//button[@class='btn btn-search btn-outline-secondary']");
    private final By txtSearchInitialCode = By.cssSelector("input[placeholder='Search...']");
    private final By iconLoading = By.xpath("//div[@data-testid=\"spinner-loading\"]");
    private final By pnlSummaryResult = By.xpath("//small[@class='text-muted']");
    private final By lnkLandingPage = By.xpath("//a[@href='#/landing']");
    private final By iconLoad = By.xpath("//div[@data-testid='spinner-loading']");
    private final By lblPlanTestDate = By.xpath("//tr[@data-testid='test-plan-row'][1]//td[@class='align-middle'][4]");
    private final By hlkTestrail = By.xpath("//a[@data-testid=\"testrail-link\"]");
    private static final By lblEncryptionStatus = By.xpath("//tr[@data-testid='test-plan-row']//td[6]");
    private static By lblTestplanStatus = By.xpath("//tr[@data-testid='test-plan-row'][1]//td[8]");
    private final By lstTestPlanDateSection = By.xpath("//div[@data-testid='test-plan-date-section']//table[@class='table table-queue table-nin table-striped table-sm']");
    private final By lblRemainTime = By.xpath("//tr[@data-testid='test-plan-row'][1]//td[9]");
    private final By pnlRemainingTask = By.xpath("//tr[@data-testid='test-plan-row'][1]//td[9]//br[2]");
    private final By pnlRemainingTime = By.xpath("//tr[@data-testid='test-plan-row'][1]//td[9]//br[1]");
    private static By btnManagerUser = By.xpath("//*[@id='hyrule-nav']//div[contains(@class,'profile-dropdown')]/button[@type='button']");
    private static By moreButton = By.id("MoreId");
    private static By iconLCO = By.xpath("//*[@data-testid='lco-icon-test']");
    private static By lotcheckOwnerCondition = By.id("Lotcheck OwnerId");
    private static By filterProductName = By.xpath("//button[@id='dropdownMenuLink' and contains(text(), 'Product Name (English)')]");
    private final By txtProductNameFilter = By.xpath("//button[@id='dropdownMenuLink' and contains(text(), 'Product Name (English)')]//..//input[@type='text']");
    private static By filterTestPlanJudgement = By.xpath("//button[@id='dropdownMenuLink' and contains(text(), 'Test Plan Judgment')]");
    private static By btnTestPlanJudgement = By.id("Test Plan JudgmentId");

    private final By iconPlusProductName = By.xpath("//button[@id='dropdownMenuLink' and contains(text(), 'Product Name (English)')]//..//input[@placeholder='Search...']//..//button[@data-testid='button-plus']");
    private final By searchButton = By.xpath("//button[@class='btn btn-search btn-outline-secondary right-oriented-search-btn']");
    //private final By searchIcon = By.xpath("//button[@class='btn btn-search btn-outline-secondary right-oriented-search-btn']");
    private final By searchIcon = By.xpath("//button[@class='btn btn-search btn-outline-secondary']");
    private final By iconLoadPage = By.xpath("//div[@data-testid='spinner-loading']");

    private static By testPlanLink = By.xpath("//*[@data-testid='test-plan-row']//a[contains(@href,'lotcheck-report-submission-level')]");
    private static By nclSelector = By.xpath("//div[@id='navbarNav']//span[contains(text(),'NCL')]");
    private static By noaSelector = By.xpath("//div[@id='navbarNav']//span[contains(text(),'NOA')]");
    private static By noeSelector = By.xpath("//div[@id='navbarNav']//span[contains(text(),'NOE')]");
    private final By lstRowVisible = By.cssSelector("i.fas.fa-ellipsis-h");

    private final By btnJudgementCancel = By.cssSelector("button.btn.btn-secondary.mr-auto");
    private final By btnJudgementConfirm = By.xpath("//button[@type='submit' and text()='Confirm']");
    private final By lblErrorJudgment = By.xpath("//div[@class='ReactModal__Content ReactModal__Content--after-open modal-dialog']//div[@role='alert']");
    private final By btnCloseErrorJudgment = By.xpath("//div[@class='ReactModal__Content ReactModal__Content--after-open modal-dialog']//button[@data-testid='close-button']");

    private static final By lnkSchedule = By.cssSelector("li.nav-item:nth-of-type(1)");


    private static final String pageTitle = "";

    public LotcheckQueuePage(WebDriver driver) {
        super(driver, pnlDateTestPlan);
    }

    public LotcheckLandingPage lotcheckLandingPage() {
        return getNewPage(LotcheckLandingPage.class);
    }

    public LotcheckQueuePage searchByInitialCode(String searchString) {
        waitForElementNotVisible(iconLoad, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        this.waitForAndClickElement(ddpInitialCode);
        extentLogger.logInfo("");
        this.waitForElementVisible(txtSearchInitialCode, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        extentLogger.logInfo("");
        this.typeText(txtSearchInitialCode, searchString);
        extentLogger.logInfo("");
        this.waitForAndClickElement(iconAdd);
        extentLogger.logInfo("");
        this.waitForAndClickElement(btnSearch);
        extentLogger.logInfo("");
        waitForElementNotVisible(iconLoad, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return getNewPage(LotcheckQueuePage.class);
    }

    public LotcheckTestPlanRow waitUntilTableVisible() {
        extentLogger.logInfo("");
        waitForElementVisible(rowTestPlan);
        return getNewPage(LotcheckTestPlanRow.class);
    }

    public LotcheckTestPlanRow clickPlannedTestDateNotSet() {
        extentLogger.logInfo("");
        this.waitForAndClickElement(lnkPlannedTestDateNotSetTab);
        waitForElementVisible(By.xpath("//tr[@data-testid='test-plan-row']"), WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return getNewPage(LotcheckTestPlanRow.class);
    }

    public LotcheckReportSubmissionOverview navigateToSubmissionLevelInTesplan(String releaseVersion) {
        extentLogger.logInfo("Opening Lotcheck Report");
        WebElement element = driver.findElement(By.xpath("//a[contains(@href,'lotcheck-report-submission-level')]//div[text()='" + releaseVersion + "']"));
        String parentHandle = driver.getWindowHandle(); // get the current window handle
        System.out.println(parentHandle);               //Prints the parent window handle
        String anchorURL = element.getAttribute("href"); //Assuming u are clicking on a link which opens a new browser window
        element.click();
        driver.switchTo().window(parentHandle);
        driver.close();//Clicking on this window
        for (String winHandle : driver.getWindowHandles())  //Gets the new window handle
            driver.switchTo().window(winHandle);
        // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        waitForElementNotVisible(iconLoading, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return getNewPage(LotcheckReportSubmissionOverview.class);
    }

    public LotcheckReportSubmissionOverview navigateToSubmissionLevelOnFirstTestPlan() {
        extentLogger.logInfo("navigate To Submission Level On First Test Plan");
        List<WebElement> testPlanElement = driver.findElements(nameTestPlanElement);
        String parentHandle = driver.getWindowHandle();
        testPlanElement.get(0).click();
        driver.switchTo().window(parentHandle);
        driver.close();
        for (String winHandle : driver.getWindowHandles())
            driver.switchTo().window(winHandle);
        return getNewPage(LotcheckReportSubmissionOverview.class);
    }

    public LotcheckQueuePage refreshPageSLCMS() {
        extentLogger.logInfo("");
        driver.navigate().refresh();
        waitForElementVisible(iconLoading, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        waitForElementNotVisible(iconLoading, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        waitDocumentReady();
        //waitForElementVisible(summaryResult, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        return this;
    }

    //TODO: Find element and re-name of parameter
    public String numberTestPlanDisplay() {
        String numberTextTestPlan = getText(By.xpath("//li[@data-testid='filter-tab'][6]"));
        System.out.println(numberTextTestPlan);
        Character getNumber = numberTextTestPlan.charAt(25);
        String numberTestPlan = String.valueOf(getNumber);
        System.out.println(getNumber);
        extentLogger.logInfo("Number Test Plan is : " + numberTestPlan);
        return numberTestPlan;
    }

    public String numberTestPlanNotSetDisplay() {
        String numberTextTestPlan = getText(By.xpath("//li[@data-testid='filter-tab'][6]//span"));
        System.out.println(numberTextTestPlan);
        String[] separated = numberTextTestPlan.split("/");
        String numberTestPlan = String.valueOf(separated[0]).trim();
        System.out.println(numberTestPlan);
        extentLogger.logInfo("Number Test Plan is : " + numberTestPlan);
        return numberTestPlan;
    }

    public LotcheckQueuePage clickOnUserNameMenu() {
        extentLogger.logInfo("click On User Name Menu");
        waitForAndClickElement(userNameMenuElement);
        return this;
    }

    public LotcheckEditAccountPreferences clickManagerAccountButton() {
        extentLogger.logInfo("click On User Name Menu");
        waitForAndClickElement(btnManagerUser);
        return getNewPage(LotcheckEditAccountPreferences.class);
    }

    public LotcheckHomePage clickOnLogoutButton() {
        extentLogger.logInfo("click On Logout Button");
        waitForAndClickElement(btnLogout);
        return getNewPage(LotcheckHomePage.class);
    }

    public LotcheckQueuePage closeSuccessMessage() {
        extentLogger.logInfo("close Success Message ");
        if (elementNotExists(btnCloseSuccessMessage)) {
            waitForElementVisible(btnCloseSuccessMessage, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
            waitForAndClickElement(btnCloseSuccessMessage);
        }
        return this;
    }

    public boolean errorDisplayed() {
        extentLogger.logInfo("refresh Page If Error ");
        List<WebElement> elements = findElements(btnErrorPopup);
        if (!elements.isEmpty()) {
            return true;
        }
        return false;
    }

    public HyruleAuthLogoutPage logOut() {
        openLinkInSameTab(lnkNavUser);
        extentLogger.logInfo("");
        waitForAndClickElement(lnkSignOut);
        extentLogger.logInfo("");
        return getNewPage(HyruleAuthLogoutPage.class);
    }

    public String getJudgmentStatus() {
        String judgmentStatus = getText(lblJudgmentStatus);
        extentLogger.logInfo(judgmentStatus);
        return judgmentStatus;
    }

    public String getTestPlanName() {
        String nameTestPlan = getText(lblNameTestPlan);
        extentLogger.logInfo(nameTestPlan);
        return nameTestPlan;
    }

    public Boolean testplanStillDisplayedInJudgmentApprovalQueue(String gameCode) {
        waitDocumentReady();
        Boolean value = !elementNotExists(By.xpath(String.format("//a[contains(text(),'%s')]", gameCode)));
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public LotcheckQueuePage clickSearchIcon() {
        this.waitForAndClickElement(btnSearch);
        waitForElementVisible(iconLoading, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        waitForElementNotVisible(iconLoading, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        waitForElementVisible(pnlSummaryResult, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        extentLogger.logInfo("");
        return getNewPage(LotcheckQueuePage.class);
    }

    public LotcheckLandingPage navLandingPage() {
        waitDocumentReady();
        waitForElementVisible(lnkLandingPage, WaitTimes.WAIT_FOR_DOCUMENT_READY_TIMEOUT_SECS);
        waitForAndClickElement(lnkLandingPage);
        extentLogger.logInfo("");
        return getNewPage(LotcheckLandingPage.class);
    }

    public LotcheckQueuePage closeMessage() {
        extentLogger.logInfo("close Success Message ");
        waitForElementVisible(btnCloseSuccessMessage, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        waitForAndClickElement(btnCloseSuccessMessage);
        return this;
    }

    public String getPlanTestDate() {
        String planTestDate = getText(lblPlanTestDate);
        extentLogger.logInfo(planTestDate);
        return planTestDate;
    }

    public boolean isDisplayedTestPlan(String value) {
        boolean exist = false;
        if (!elementNotExists(By.xpath(String.format("//a[contains(text(),'%s')]", value)))) {
            exist = true;
        }
        extentLogger.logInfo(String.valueOf(value));
        return exist;
    }

    public LotcheckQueuePage clickGoToTestRail() {
        waitForAndClickElement(hlkTestrail);
        extentLogger.logInfo("");
        return this;
    }

    public TestRailLoginPage navigateToNewTab() {
        String parentHandle = driver.getWindowHandle();
        driver.switchTo().window(parentHandle);
        driver.close();
        for (String winHandle : driver.getWindowHandles())
            driver.switchTo().window(winHandle);
        return getNewPage(TestRailLoginPage.class);
    }

    public String getTestPlanStatus() {
        String testPlanStatus = getText(lblTestplanStatus).substring(0, 8);
        extentLogger.logInfo(testPlanStatus);
        return testPlanStatus;
    }

    public String getTestPlanStatus(int index) {
        String testPlanStatus = getText(lblTestplanStatus).substring(0, index);
        extentLogger.logInfo(testPlanStatus);
        return testPlanStatus;
    }

    public String getEncryptionStatus() {
        String encryptionStatus = getText(lblEncryptionStatus);
        extentLogger.logInfo(encryptionStatus);
        return encryptionStatus;
    }

    public String getJudgmentStatusUpp(int index) {
        List<WebElement> selector = driver.findElements(lblJudgmentStatus);
        String judgmentStatusValue = selector.get(index - 1).getText();
        extentLogger.logInfo(judgmentStatusValue);
        return judgmentStatusValue;
    }


    public Integer getNumberTestPlanDisplay() {
        String value = getText(By.xpath("//li[@data-testid='filter-tab'][6]//span"));
        int numberTextTestPlan = Integer.parseInt(value.split("/")[0].trim());
        extentLogger.logInfo(String.valueOf(numberTextTestPlan));
        return numberTextTestPlan;
    }

    public int collectNumberTestPlan() {
        extentLogger.logInfo("collect Number Test Plan");
        List<WebElement> lstTestPlan = driver.findElements(lstTestPlanDateSection);
        return lstTestPlan.size();
    }

    public LotcheckQueuePage clickPlannedTestDateNotSetIfNull() {
        extentLogger.logInfo("");
        this.waitForAndClickElement(By.cssSelector("li.nav-item:nth-of-type(6)"));
        return this;
    }

    public String getTestPlanRemaining() {
        String value = getText(lblRemainTime);
        extentLogger.logInfo(value);
        return value;
    }

    public boolean isRemainingTaskInTestPlanDisplayed() {
        boolean value = false;
        if (!elementNotExists(pnlRemainingTask)) {
            value = true;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public boolean isRemainingTimeInTestPlanDisplayed() {
        boolean value = false;
        if (!elementNotExists(pnlRemainingTime)) {
            value = true;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public String numberTotalTestPlanDisplayInTodayTab() {
        String numberTextTestPlan = getText(By.xpath("//li[@data-testid='filter-tab'][2]//span"));
        String numberTestPlan = numberTextTestPlan.split(" ")[2];
        System.out.println(numberTestPlan);
        extentLogger.logInfo("Number Test Plan is : " + numberTestPlan);
        return numberTestPlan;
    }

    public LotcheckQueuePage selectFromDateAndToDateByFilterName(String filterName, int fromDate, int toDate) {
        By btnFilter = By.xpath(String.format("//div[contains(text(),'%s')]/..", filterName));
        waitForElementClickable(btnFilter);
        waitForAndClickElement(btnFilter);
        extentLogger.logInfo("");
        this.selectFromDate(filterName, fromDate);
        this.selectToDate(filterName, toDate);
        typeTab(btnFilter);
        return getNewPage(LotcheckQueuePage.class);
    }

    public LotcheckQueuePage selectFromDate(String filterName, int fromDate) {
        waitForAndClickElement(By.xpath(String.format("//div[contains(text(),'%s')]/../..//div[contains(@class,'form-group')][1]//input[contains(@class,'date-alt-field')]", filterName)));
        new CalendarModalSLCMS(this.driver).chooseDateInCalenderSLCMS(fromDate);
        extentLogger.logInfo("From date be added (days): " + fromDate);
        return this;
    }

    public LotcheckQueuePage selectToDate(String filterName, int toDate) {
        waitForAndClickElement(By.xpath(String.format("//div[contains(text(),'%s')]/../..//div[contains(@class,'form-group')][2]//input[contains(@class,'date-alt-field')]", filterName)));
        new CalendarModalSLCMS(this.driver).chooseDateInCalenderSLCMS(toDate);
        extentLogger.logInfo("To date be added (days): " + toDate);
        return this;
    }

    public String numberTestPlanDisplayInTodayTab() {
        String numberTextTestPlan = getText(By.xpath("//li[@data-testid='filter-tab'][2]//span"));
        String numberTestPlan = numberTextTestPlan.split(" ")[0];
        System.out.println(numberTestPlan);
        extentLogger.logInfo("Number Test Plan is : " + numberTestPlan);
        return numberTestPlan;
    }

    public LotcheckQueuePage approveSeleniumTestPlan() {
        filterSeleniumTestPlan();
        String count = numberTestPlanNotSetDisplay();
        Boolean hasError = false;
        List<WebElement> lstTestPlanRow;
        while (!count.equals("0")) {
            lstTestPlanRow = driver.findElements(lstRowVisible);
            WebElement element;
            if (!hasError)
                element = lstTestPlanRow.get(0);
            else
                element = lstTestPlanRow.get(1);
            element.click();
            waitDocumentReady();
            WaitUtils.idle(1000);
            selectElementFromDropDown(TextConstants.approveJudgement);
            WaitUtils.idle(1000);
            clickJudgementConfirm();
            WaitUtils.idle(6000);
            if (!elementNotExists(lblErrorJudgment)) {
                waitForAndClickElement(btnCloseErrorJudgment);
                hasError = true;
            }

        }
        return this;
    }

    private void filterSeleniumTestPlan() {
        clickPlannedTestDateNotSet();
        selectMoreFilter("Product Name (English)");
        selectMoreFilter("Test Plan Judgment");
        clickProductNameCondition();
        searchByProductNameEN("Selenium");
        clickTestPlanJudgementCondition();
        selectConditionJudgement(TextConstants.NG);
        selectConditionJudgement(TextConstants.Canceled);
        selectConditionJudgement(TextConstants.MetadataNG);
        selectConditionJudgement(TextConstants.E_manualNG);
        selectConditionJudgement(TextConstants.Suspended);
        selectConditionJudgement(TextConstants.OK);
        clickSearchIcon();
    }

    public LotcheckQueuePage selectElementFromDropDown(String text) {
        extentLogger.logInfo("");
        this.waitForAndClickElement(
                By.xpath("//button[text()='" + text + "']")
        );
        return getNewPage(LotcheckQueuePage.class);
    }


    public LotcheckQueuePage clickJudgementConfirm() {
        waitForElementClickable(btnJudgementCancel, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        this.waitForAndClickElement(btnJudgementConfirm);
        extentLogger.logInfo("");
        waitForElementNotVisible(btnJudgementCancel, WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        return getNewPage(LotcheckQueuePage.class);
    }

    public LotcheckQueuePage unselectMoreFilter(String value) {
        WebElement element = driver.findElement(moreButton);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", element);
        //waitForAndClickElement(moreButton);
        extentLogger.logInfo("Click More Button");
        selectCheckbox(By.xpath("//*[@id='MoreMenuId']//label[contains(text(),' " + value + "')]//input"), "false");
        extentLogger.logInfo("Click value");
        waitForAndClickElement(searchIcon);
        extentLogger.logInfo("click Search Icon");
        waitForElementNotVisible(iconLoad, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        extentLogger.logInfo("WAIT_FOR_UPLOAD_PROCESSING_SECS");
        return this;
    }

    public LotcheckQueuePage selectMoreFilter(String value) {
        WebElement elm = driver.findElement(moreButton);
        waitForAndClickElement(moreButton);
        extentLogger.logInfo("Click More Button");
        selectCheckbox(By.xpath("//*[@id='MoreMenuId']//label[contains(text(),' " + value + "')]//input"), "true");
        extentLogger.logInfo("Click value");
        return this;
    }

  /*  public LotcheckQueuePage selectMoreFilter(String value) {
        waitForAndClickElement(moreButton);
        extentLogger.logInfo("");
        selectCheckbox(By.xpath("//*[@id='MoreMenuId']//label[contains(text(),' " + value + "')]//input"), "true");
        extentLogger.logInfo("");
        this.waitForAndClickElement(btnSearch);
        extentLogger.logInfo("");
        waitForElementNotVisible(iconLoad, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return getNewPage(LotcheckQueuePage.class);
    }*/

    public LotcheckQueuePage clickLotcheckOwnerCondition() {
        Actions actions = new Actions(driver);
        WebElement lotcheckOwnerCondition = driver.findElement(By.id("Lotcheck OwnerId"));
        actions.moveToElement(lotcheckOwnerCondition).perform();
        waitForAndClickElement(lotcheckOwnerCondition);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckQueuePage clickProductNameCondition() {
        Actions actions = new Actions(driver);
        WebElement productNameCondition = driver.findElement(filterProductName);
        actions.moveToElement(productNameCondition).perform();
        waitForAndClickElement(productNameCondition);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckQueuePage clickTestPlanJudgementCondition() {
        Actions actions = new Actions(driver);
        WebElement judgementCondition = driver.findElement(btnTestPlanJudgement);
        actions.moveToElement(judgementCondition).perform();
        waitForAndClickElement(judgementCondition);
        extentLogger.logInfo("");
        return this;
    }


    public LotcheckQueuePage searchByProductNameEN(String searchString) {
        extentLogger.logInfo(searchString);
        this.waitForElementVisible(txtProductNameFilter);
        extentLogger.logInfo("");
        this.typeText(txtProductNameFilter, searchString);
        this.waitForAndClickElement(iconPlusProductName);
        waitForElementNotVisible(iconLoadPage, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return getNewPage(LotcheckQueuePage.class);
    }

    public LotcheckQueuePage selectConditionRegion(String conditionRegion) {
        selectCheckbox(By.xpath("//*[@id='Lotcheck OwnerMenuId']//label[contains(text(),'" + conditionRegion + "')]//input"), "true");
        extentLogger.logInfo("");
        this.waitForAndClickElement(btnSearch);
        extentLogger.logInfo("");
        waitForElementNotVisible(iconLoad, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return getNewPage(LotcheckQueuePage.class);
    }

    public LotcheckQueuePage unselectConditionRegion(String conditionRegion) {
        selectCheckbox(By.xpath("//*[@id='Lotcheck OwnerMenuId']//label[contains(text(),'" + conditionRegion + "')]//input"), "false");
        extentLogger.logInfo("");
        this.waitForAndClickElement(btnSearch);
        extentLogger.logInfo("");
        waitForElementNotVisible(iconLoad, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return getNewPage(LotcheckQueuePage.class);
    }

    public LotcheckQueuePage selectConditionJudgement(String status) {
        selectCheckbox(By.xpath("//*[@id='Test Plan JudgmentMenuId']//label[contains(text(),'" + status + "')]//input"), "true");
        extentLogger.logInfo("");
       /* this.waitForAndClickElement(btnSearch);
        extentLogger.logInfo("");
        waitForElementNotVisible(iconLoad, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);*/
        return getNewPage(LotcheckQueuePage.class);
    }

    public LotcheckQueuePage clickTab(int i) {
        Actions actions = new Actions(driver);
        WebElement element = driver.findElement(By.xpath("//*[@id='myTab']//li[@data-testid='filter-tab'][" + i + "]//button[@data-testid='filter-tab-button']"));
        actions.moveToElement(element).perform();
        waitForAndClickElement(element);
        extentLogger.logInfo("");
        return this;
    }

    public String getNumberTestPlan(int i) {
        String numberTP = getText(By.xpath("//*[@id='myTab']//li[@data-testid='filter-tab'][" + i + "]//button[@data-testid='filter-tab-button']//span")).split("/")[0].trim();
        extentLogger.logInfo(numberTP);
        return numberTP;
    }

    public String getNameQueue(int i) {
        WebElement e = driver.findElement(By.xpath("//*[@id='myTab']//li[@data-testid='filter-tab'][" + i + "]//button[@data-testid='filter-tab-button']"));
        String value = e.getText().trim();
        List<WebElement> children = e.findElements(By.xpath("./*"));
        for (WebElement child : children) {
            value = value.replaceFirst(child.getText(), "").trim();
        }
        extentLogger.logInfo(value);
        return value;
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

    public String numberTestPlanDisplayInFinishQueue() {
        String numberTextTestPlan = getText(By.xpath("//button[@id='today-tab']//span")).split("\n")[0];
        System.out.println(numberTextTestPlan);
        extentLogger.logInfo("Number Test Plan is : " + numberTextTestPlan);
        return numberTextTestPlan;
    }

    public LotcheckQueuePage clickTestPlan(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", element);
        waitForElementNotVisible(iconLoad, Duration.ofSeconds(300));
        extentLogger.logInfo("");
        return this;
    }

    public String getLotCheckOwnerInSubmissionReport() {
        waitForElementVisible(By.xpath("//*[@id='overview-container']//dd[2]"), Duration.ofSeconds(300));
        String locheckOwner = getText(By.xpath("//*[@id='overview-container']//dd[2]"));
        String getTextLCOwner = "";
        if (locheckOwner.contains("(")) {
            getTextLCOwner = locheckOwner.replace("(", "").replace(")", "");
        }
        if (locheckOwner.contains("\n")) {
            getTextLCOwner = locheckOwner.split("\n")[0].trim();
        }
        extentLogger.logInfo(getTextLCOwner);
        return getTextLCOwner;
    }


    public HashMap<String, String> getTestPlan(HashMap<String, String> regionAndTestPlan) {// return hashmap<lotcheckOwner, testplan name>
        extentLogger.logInfo("get Test Plan");
        List<WebElement> listTestPlan = findElements(testPlanLink);
        // HashMap<String, String> regionAndTestPlan = new LinkedHashMap<String, String>();
        for (int i = 1; i <= 3; i++) { // select random 3 test plan per tab
            Random rand = new Random();
            int randomValue = rand.nextInt((listTestPlan.size()));
            System.out.println("listtl.sze() " + i + " = " + listTestPlan.size());
            WebElement element = listTestPlan.get(randomValue);
            String parentHandle = driver.getWindowHandle();
            this.clickTestPlan(element);// click ramdom testplan name
            String testPlane = getText(element);
            extentLogger.logInfo("");
            Set<String> windownHandles = driver.getWindowHandles();
            List<String> windownHandlesList = new ArrayList<>(windownHandles);
            driver.switchTo().window(windownHandlesList.get(1));
            WaitUtils.idle(5000);
            String lcOwner = getLotCheckOwnerInSubmissionReport();
            driver.close();
            driver.switchTo().window(parentHandle);
            regionAndTestPlan.put(testPlane, lcOwner); //key: testplan Name, value: lcOwner
            WaitUtils.idle(2000);
        }
        return regionAndTestPlan;

    }

    public HashMap<String, String> getTestPlan() {// return hashmap<lotcheckOwner, testplan name>
        extentLogger.logInfo("get Test Plan");
        List<WebElement> listTestPlan = findElements(testPlanLink);
        HashMap<String, String> regionAndTestPlan = new LinkedHashMap<String, String>();
        for (int i = 1; i <= 2; i++) { // select random 3 test plan per tab
            Random rand = new Random();
            int randomValue = rand.nextInt((listTestPlan.size()));
            System.out.println("listtl.sze() " + i + " = " + listTestPlan.size());
            WebElement element = listTestPlan.get(randomValue);
            String parentHandle = driver.getWindowHandle();
            this.clickTestPlan(element);// click ramdom testplan name
            String testPlane = getText(element);
            extentLogger.logInfo("");
            Set<String> windownHandles = driver.getWindowHandles();
            List<String> windownHandlesList = new ArrayList<>(windownHandles);
            driver.switchTo().window(windownHandlesList.get(1));
            WaitUtils.idle(3000);
            String lcOwner = getLotCheckOwnerInSubmissionReport();
            driver.close();
            driver.switchTo().window(parentHandle);
            regionAndTestPlan.put(testPlane, lcOwner); //key: testplan Name, value: lcOwner
            WaitUtils.idle(2000);
        }
        return regionAndTestPlan;

    }

    public LotcheckQueuePage selectQueue(String queue, LotcheckRegion locale) {
        switch (locale) {
            case NCL:
                waitForAndClickElement(nclSelector);
                waitForAndClickElement(By.xpath("//div[@id='navbarNav']//span[contains(text(),'NCL')]//..//..//a[contains(text(),'" + queue + "')]"));
                break;
            case NOA:
                this.waitForAndClickElement(noaSelector);
                waitForAndClickElement(By.xpath("//div[@id='navbarNav']//span[contains(text(),'NOA')]//..//..//a[contains(text(),'" + queue + "')]"));
                break;
            case NOE:
                this.waitForAndClickElement(noaSelector);
                waitForAndClickElement(By.xpath("//div[@id='navbarNav']//span[contains(text(),'NOE')]//..//..//a[contains(text(),'" + queue + "')]"));
                break;
            default:
                //nothing
        }
        extentLogger.logInfo("");
        return getNewPage(LotcheckQueuePage.class);
    }

    public LotcheckReportSubmissionOverview navigateToSubmissionLevelInTestplanByTargetDelivery(String targetDelivery) {
        extentLogger.logInfo("Opening Lotcheck Report");
        WebElement element = driver.findElement(By.xpath("//a[contains(@href,'lotcheck-report-submission-level')]//div[text()='" + targetDelivery + "']"));
        String parentHandle = driver.getWindowHandle(); // get the current window handle
        System.out.println(parentHandle);               //Prints the parent window handle
        String anchorURL = element.getAttribute("href"); //Assuming u are clicking on a link which opens a new browser window
        element.click();
        driver.switchTo().window(parentHandle);
        driver.close();//Clicking on this window
        for (String winHandle : driver.getWindowHandles())  //Gets the new window handle
            driver.switchTo().window(winHandle);
        // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        waitForElementNotVisible(iconLoading, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return getNewPage(LotcheckReportSubmissionOverview.class);
    }

    public String getValueInputInSearchInitialCode() {
        String getValueInputInSearchInitialCode = getValueAttributes(txtSearchInitialCode);
        extentLogger.logInfo(getValueInputInSearchInitialCode);
        return getValueInputInSearchInitialCode;
    }

    public LotcheckQueuePage inputInitialCodeFilter(String searchString) {
        waitForElementNotVisible(iconLoad, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        this.waitForAndClickElement(ddpInitialCode);
        extentLogger.logInfo("");
        this.waitForElementVisible(txtSearchInitialCode, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        extentLogger.logInfo("");
        this.typeText(txtSearchInitialCode, searchString);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionOverview navigateToSubmissionLevelInTestplan(String releaseVersion) {
        extentLogger.logInfo("Opening Lotcheck Report");
        WebElement element = driver.findElement(By.xpath("//a[contains(@href,'lotcheck-report-submission-level')]//div[text()='" + releaseVersion + "']"));
        String parentHandle = driver.getWindowHandle(); // get the current window handle
        System.out.println(parentHandle);               //Prints the parent window handle
        String anchorURL = element.getAttribute("href"); //Assuming u are clicking on a link which opens a new browser window
        element.click();
        driver.switchTo().window(parentHandle);
        driver.close();//Clicking on this window
        for (String winHandle : driver.getWindowHandles())  //Gets the new window handle
            driver.switchTo().window(winHandle);
        // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        waitForElementNotVisible(iconLoading, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return getNewPage(LotcheckReportSubmissionOverview.class);
    }

    public LotcheckTestPlanRow clickSchedule() {
        extentLogger.logInfo("");
        this.waitForAndClickElement(lnkSchedule);
        waitForElementVisible(By.xpath("//tr[@data-testid='test-plan-row']"), WaitTimes.WAIT_FOR_ELEMENT_VISIBLE_TIMEOUT_SECS.multipliedBy(30));
        return getNewPage(LotcheckTestPlanRow.class);
    }


}
