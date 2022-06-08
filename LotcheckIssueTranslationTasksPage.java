package net.nintendo.automation.ui.models.lcms;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.lcms.lotcheck_issue_translation.LotcheckIssueTranslationTaskDetails;
import net.nintendo.automation.ui.models.lcms.submission_reports.SaveFilterPopup;
import net.nintendo.automation.ui.models.lcms.submission_reports.SelectChooseColumns;
import net.nintendo.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class LotcheckIssueTranslationTasksPage extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@data-testid='hyruleAppContent']//div[@data-testid='translation-tasks-container']");
    private final By btnInitialCodeFilter = By.xpath("//button[@id='dropdownMenuLink' and contains(text(), 'Initial Code')]");
    private final By txtInitialCodeFilter = By.xpath("//button[text()='Initial Code']//..//input[@placeholder='Search...']");
    private final By iconPlusInitialCode = By.xpath("//button[text()='Initial Code']//..//input[@placeholder='Search...']//..//button[@data-testid='button-plus']");
    private final By iconLoadPage = By.xpath("//div[@data-testid='spinner-loading']");
    private final By lblTitleInTable = By.xpath("//tr[@data-testid='translation-tasks-grid-row']//td[8]");
    private final By lnkTaskDetails = By.xpath("//tr[@data-testid='translation-tasks-grid-row']");
    private final By lstNumberItem = By.xpath("//*[@data-testid='translation-tasks-grid-row']");
    private final By lstStatus = By.xpath("//*[@data-testid='translation-tasks-grid-row']//td[2]");
    private final By lblStatusDefault = By.id("StatusId");
    private final By lblStatus = By.xpath("//th[@data-testid='translation-tasks-grid-header'][2]");
    private final By submissionValueColumn = By.xpath("//div[@data-testid='translation-tasks-container']//tbody//tr//td[7]");
    private final By translationContent = By.xpath("//div[@data-testid='translation-tasks-container']//tbody//tr");
    private final By showItemsPearPage = By.xpath("//button[@id='submission-rows-dropdown']");
    private final By txtSearch = By.xpath("//input[@placeholder='Search by Initial Code, Product Name, or Publisher...']");
    private final By iconSearch = By.xpath("//button[@class='btn btn-search btn-outline-secondary right-oriented-search-btn']");
    private final By mnIconMenu = By.xpath("//button[@id='dropdownMenuLink']//i");
    private final By btnChooseColumns = By.xpath("//div[@class='dropdown-menu dropdown-menu-right show']//button[@class='btn dropdown-item' and @data-testid='submit-button']");
    private final By btnSaveFilterMenu = By.xpath("//div[@data-testid='save-filter-action-menu']//button[@id='dropdownMenuLink']");
    private final By btSaveFilter = By.xpath("//button[contains(text(),'Save Filter')]");


    public LotcheckIssueTranslationTasksPage(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public LotcheckIssueTranslationTasksPage searchByInitialCode(String searchString) {
        extentLogger.logInfo(searchString);
        this.waitForAndClickElement(btnInitialCodeFilter);
        extentLogger.logInfo("");
        this.waitForElementVisible(txtInitialCodeFilter);
        extentLogger.logInfo("");
        this.typeText(txtInitialCodeFilter, searchString);
        this.waitForAndClickElement(iconPlusInitialCode);
        waitForElementNotVisible(iconLoadPage, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return this;
    }

    public String getTitleIssueInTable() {
        String value = getText(lblTitleInTable);
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckIssueTranslationTaskDetails clickViewTaskDetails() {
        //waitForAndClickElement(navigateTaskDetails);
        extentLogger.logInfo("");
        WebElement element = driver.findElement(lnkTaskDetails);
        String parentHandle = driver.getWindowHandle(); // get the current window handle
        System.out.println(parentHandle);               //Prints the parent window handle
        String anchorURL = element.getAttribute("href"); //Assuming u are clicking on a link which opens a new browser window
        element.click();
        driver.switchTo().window(parentHandle);
        driver.close();//Clicking on this window
        for (String winHandle : driver.getWindowHandles())  //Gets the new window handle
            driver.switchTo().window(winHandle);
        return getNewPage(LotcheckIssueTranslationTaskDetails.class);
    }

    public int getNumberTranslationTasks() {
        List<WebElement> numberValue = findElements(lstNumberItem);
        int num = numberValue.size();
        extentLogger.logInfo("Number Of Translation Tasks:" + num);
        return num;
    }

    public String getValueStatusTranslationTasks(int i) {
        List<WebElement> rowsTable = findElements(lstStatus);
        List<String> valueDemoType = List.of(rowsTable.get(i).getText().split("/n"));
        extentLogger.logInfo(valueDemoType.get(0));
        return valueDemoType.get(0);
    }

    public String defaultOptionForStatusFilter() {
        String statusDefault = getText(lblStatusDefault);
        extentLogger.logInfo("The default option for the Status filter is: " + statusDefault);
        return statusDefault;
    }

    public LotcheckIssueTranslationTaskDetails clickViewTaskDetailsProd_addLoop(String initialCode, int num) {
        extentLogger.logInfo("");
        for (int i = 0; i < 50; i++) {
            if (elementNotExists(By.xpath("//tr[@data-testid='translation-tasks-grid-row']//td[contains(text(),'" + initialCode + "')]"))) {
                driver.navigate().refresh();
                waitForElementVisible(uniqueElement, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
                WaitUtils.idle(3000);
            } else {
                break;
            }
        }
        List<WebElement> elements = driver.findElements(By.xpath("//tr[@data-testid='translation-tasks-grid-row']//td[contains(text(),'" + initialCode + "')]"));
        String parentHandle = driver.getWindowHandle(); // get the current window handle
        System.out.println(parentHandle);               //Prints the parent window handle
        String anchorURL = elements.get(num - 1).getAttribute("href"); //Assuming u are clicking on a link which opens a new browser window
        elements.get(num - 1).click();
        driver.switchTo().window(parentHandle);
        driver.close();//Clicking on this window
        for (String winHandle : driver.getWindowHandles())  //Gets the new window handle
            driver.switchTo().window(winHandle);
        waitForElementNotVisible(iconLoadPage, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return getNewPage(LotcheckIssueTranslationTaskDetails.class);
    }


    public LotcheckIssueTranslationTaskDetails clickViewTaskDetailsProd_addLoop(String initialCode) {
        extentLogger.logInfo("");
        for (int i = 0; i < 50; i++) {
            if (elementNotExists(By.xpath("//tr[@data-testid='translation-tasks-grid-row']//td[contains(text(),'" + initialCode + "')]"))) {
                driver.navigate().refresh();
                waitForElementVisible(uniqueElement, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
                WaitUtils.idle(3000);
            } else {
                break;
            }
        }
        WebElement element = driver.findElement(By.xpath("//tr[@data-testid='translation-tasks-grid-row']//td[contains(text(),'" + initialCode + "')]"));
        String parentHandle = driver.getWindowHandle(); // get the current window handle
        System.out.println(parentHandle);               //Prints the parent window handle
        String anchorURL = element.getAttribute("href"); //Assuming u are clicking on a link which opens a new browser window
        element.click();
        driver.switchTo().window(parentHandle);
        driver.close();//Clicking on this window
        for (String winHandle : driver.getWindowHandles())  //Gets the new window handle
            driver.switchTo().window(winHandle);
        return getNewPage(LotcheckIssueTranslationTaskDetails.class);
    }

    public LotcheckIssueTranslationTaskDetails clickViewTaskDetailsProd(String initialCode) {
        extentLogger.logInfo("");
        WebElement element = driver.findElement(By.xpath("//tr[@data-testid='translation-tasks-grid-row']//td[contains(text(),'" + initialCode + "')]"));
        String parentHandle = driver.getWindowHandle(); // get the current window handle
        System.out.println(parentHandle);               //Prints the parent window handle
        String anchorURL = element.getAttribute("href"); //Assuming u are clicking on a link which opens a new browser window
        element.click();
        driver.switchTo().window(parentHandle);
        driver.close();//Clicking on this window
        for (String winHandle : driver.getWindowHandles())  //Gets the new window handle
            driver.switchTo().window(winHandle);
        return getNewPage(LotcheckIssueTranslationTaskDetails.class);
    }

    public String getStatusNameColumn() {
        String value = getText(lblStatus);
        extentLogger.logInfo(value);
        return value;
    }

    public int numberColumnValueOnTable() {
        List<WebElement> rowsTable = findElements(translationContent);
        int num = 0;
        for (WebElement row : rowsTable) {
            List<WebElement> columnRow = row.findElements(By.tagName("td"));
            num++;
        }
        extentLogger.logInfo("Number: " + num);
        return num;
    }

    public String getPublisherColumnAtTable(int location) {
        String value = getText(By.xpath(String.format("//div[@data-testid='translation-tasks-container']//tbody//tr[%s]//td[9]", location)));
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckIssueTranslationTasksPage selectShowItemsPerPage(String valueItems) {
        waitForAndClickElement(showItemsPearPage);
        waitForAndClickElement(By.xpath(String.format("//div[@class='dropdown-menu show']//a[contains(text(),'%s')]", valueItems)));
        //selectValueOnDropDown(showItemsPearPage,valueItems);
        extentLogger.logInfo(valueItems);
        waitForElementNotVisible(iconLoadPage, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return this;
    }

    public String getSubmissionColumnAtTable(int location) {
        String value = getText(By.xpath(String.format("//div[@data-testid='translation-tasks-container']//tbody//tr[%s]//td[7]", location)));
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckIssueTranslationTasksPage reloadPage() {
        driver.navigate().refresh();
        waitForElementNotVisible(iconLoadPage, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return this;
    }

    public LotcheckIssueTranslationTasksPage searchByInitialCodeProductNamePublisher(String searchString) {
        extentLogger.logInfo(searchString);
        this.waitForAndClickElement(txtSearch);
        this.typeText(txtSearch, searchString);
        this.waitForAndClickElement(iconSearch);
        waitForElementNotVisible(iconLoadPage, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return this;
    }

    public boolean comparativeValue(String value, String expected) {
        boolean expectedResult = value.contains(expected);
        extentLogger.logInfo(value + ": " + expectedResult);
        return expectedResult;
    }

    public boolean displayColumn(String columnName) {
        boolean value = true;
        if (elementNotExists(By.xpath(String.format("//table[contains(@class,'tbl-translator')]//th[contains(text(),'%s')]", columnName)))) {
            return false;
        }
        return value;
    }

    public SelectChooseColumns clickActionMenu() {
        waitForAndClickElement(mnIconMenu);
        extentLogger.logInfo("");
        waitForAndClickElement(btnChooseColumns);
        extentLogger.logInfo("");
        return getNewPage(SelectChooseColumns.class);
    }

    public SaveFilterPopup clickSaveFilterMenu() {
        waitForAndClickElement(btnSaveFilterMenu);
        extentLogger.logInfo("");
        waitForAndClickElement(btSaveFilter);
        extentLogger.logInfo("");
        return getNewPage(SaveFilterPopup.class);
    }
}
