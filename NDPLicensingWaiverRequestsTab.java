package net.nintendo.automation.ui.models.ndp;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class NDPLicensingWaiverRequestsTab extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//h5[contains(@id,'main-content')]");
    private static final String pageTitle = "";
    private static By iconLoading = By.xpath("//div[contains(@class,'nin-loader-global search-loader')]");
    private static By txtEnterTextSearch = By.xpath("//input[@class='keyword-search']");
    private static By btnMagnifyingIcon = By.xpath("//i[contains(@class,'basic-search-button')]");
    private static By lnkLicensingWaiverRequestTask = By.xpath("//div[contains(@class,'dataTables_scrollBody')]//tbody/tr");
    private static By licensingWaiverRequestTaskElement = By.xpath("//div[contains(@class,'dataTables_scrollBody')]//tbody/tr");
    private final By showItemsPearPage = By.xpath("//select[contains(@name,'DataTables_Table')]");
    private final By noRecordText = By.xpath("//div[@class='alert alert-info search-success']");
    private final By btChooseColumn = By.xpath("//a[@class='choose-column-link']");

    public NDPLicensingWaiverRequestsTab(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public NDPLicensingWaiverRequestsTab waitingForResultTableLoaded() {
        extentLogger.logInfo("");
        waitForElementNotVisible(iconLoading, WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        return this;
    }

    public NDPLicensingWaiverRequestsTab enterSearchingBox(String enterTextSearch) {
        extentLogger.logInfo("enterSearchingBox");
        waitForAndClickElement(txtEnterTextSearch);
        typeText(txtEnterTextSearch, enterTextSearch);
        typeTab(txtEnterTextSearch);
        return this;
    }

    public NDPLicensingWaiverRequestsTab clickOnMagnifyingIconButton() {
        extentLogger.logInfo("clickOnMagnifyingIconButton");
        waitForAndClickElement(btnMagnifyingIcon);
        return this;
    }

    public NDPViewTaskPage openLicensingWaiverRequestByPendingStatus() {
        extentLogger.logInfo("openLicensingWaiverRequestByStatus");
        List<WebElement> rowsTable = findElements(lnkLicensingWaiverRequestTask);
        int rowsCount = rowsTable.size();
        for (WebElement row : rowsTable) {
            List<WebElement> columnRow = row.findElements(By.tagName("td"));
            String statusText = columnRow.get(7).getText().trim();
            if (statusText.equals("pending")) {
                row.click();
                break;
            }
        }
        return getNewPage(NDPViewTaskPage.class);
    }

    public int collectNumberOfRequestByPendingStatus() {
        extentLogger.logInfo("openLicensingWaiverRequestByStatus");
        List<WebElement> rowsTable = findElements(lnkLicensingWaiverRequestTask);
        int num = 0;
        for (WebElement row : rowsTable) {
            List<WebElement> columnRow = row.findElements(By.tagName("td"));
            String statusText = columnRow.get(7).getText().trim();
            if (statusText.equals("pending")) {
                num++;
            }
        }
        return num;
    }

    public NDPLicensingWaiverRequestsTab selectShowItemsPerPage(String valueItems) {
        selectValueOnDropDown(showItemsPearPage, valueItems);
        extentLogger.logInfo(valueItems);
        return this;
    }

    public int collectNumberOfRequestOnTable() {
        List<WebElement> rowsTable = findElements(licensingWaiverRequestTaskElement);
        int num = 0;
        for (WebElement row : rowsTable) {
            List<WebElement> columnRow = row.findElements(By.tagName("td"));
            num++;
        }
        extentLogger.logInfo("Number: " + num);
        return num;
    }

    public String getProductAtTable(int location) {
        String value = getText(By.xpath(String.format("//tr[%s]//td[7]", location)));
        extentLogger.logInfo(value);
        return value;
    }

    public String getCompanyAtTable(int location, int locationRow) {
        String value = getText(By.xpath(String.format("//tr[contains(@class,'data-table-row')][%s]//td[%s]", location, locationRow)));
        extentLogger.logInfo(value);
        return value;
    }

    public String getAssignAtTable(int location) {
        String value = getText(By.xpath(String.format("//tr[contains(@class,'data-table-row')][%s]//td[1]", location)));
        extentLogger.logInfo(value);
        return value;
    }

    public String getGuidelineAtTable(int location) {
        String value = getText(By.xpath(String.format("//tr[contains(@class,'data-table-row')][%s]//td[4]", location)));
        extentLogger.logInfo(value);
        return value;
    }

    public boolean comparativeValue(String value, String expected) {
        boolean expectedResult = value.contains(expected);
        extentLogger.logInfo(value + ": " + expectedResult);
        return expectedResult;
    }

    public String getNoRecordsFounds() {
        String value = getText(noRecordText);
        extentLogger.logInfo(value);
        return value;
    }

    public NDPChooseColumnModal clickChooseColumn() {
        waitForAndClickElement(btChooseColumn);
        extentLogger.logInfo("");
        return getNewPage(NDPChooseColumnModal.class);
    }

}