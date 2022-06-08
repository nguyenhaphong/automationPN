package net.nintendo.automation.ui.models.ndp;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class NDPInternalWaiverRequestsTab extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//h5[contains(@id,'main-content')]");
    private static By iconLoading = By.xpath("//div[contains(@class,'nin-loader-global search-loader')]");
    private static By txtEnterTextSearch = By.xpath("//input[@class='keyword-search']");
    private static By btnMagnifyingIcon = By.xpath("//i[contains(@class,'basic-search-button')]");
    private static By lnkInternalWaiverRequestTask = By.xpath("//div[contains(@class,'dataTables_scrollBody')]//tbody/tr");
    private static final String pageTitle = "";

    public NDPInternalWaiverRequestsTab(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public NDPInternalWaiverRequestsTab waitingForResultTableLoadedInInternal() {
        extentLogger.logInfo("");
        waitForElementNotVisible(iconLoading);
        return this;
    }

    public NDPInternalWaiverRequestsTab enterSearchingBoxInternal(String enterTextSearch) {
        extentLogger.logInfo(enterTextSearch);
        By enterTextSearchSelector = txtEnterTextSearch;
        waitForAndClickElement(enterTextSearchSelector);
        typeText(enterTextSearchSelector, enterTextSearch);
        typeTab(enterTextSearchSelector);
        return this;
    }

    public NDPInternalWaiverRequestsTab clickOnMagnifyingIconButtonInternal() {
        extentLogger.logInfo("");
        By magnifyingIconButtonSelector = btnMagnifyingIcon;
        waitForAndClickElement(magnifyingIconButtonSelector);
        return this;
    }

    public NDPViewTaskPage openLicensingWaiverRequestByPendingStatusInternal() {
        extentLogger.logInfo("");
        By selector = lnkInternalWaiverRequestTask;
        List<WebElement> rowsTable = findElements(selector);
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

    public int collectNumberOfRequestByPendingStatusInternal() {
        extentLogger.logInfo("");
        By selector = lnkInternalWaiverRequestTask;
        List<WebElement> rowsTable = findElements(selector);
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
}
