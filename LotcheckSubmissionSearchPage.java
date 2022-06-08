package net.nintendo.automation.ui.models.lcms;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthLogoutPage;
import net.nintendo.automation.ui.models.lcms.submission_reports.LotcheckReportSubmissionOverview;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LotcheckSubmissionSearchPage extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//h1[text()='Submission Search']");
    private static final String pageTitle = "Submission Search (Keyword) - LCMS";
    private final By lnkGameCodeMenu = By.cssSelector(".text-center .fa-ellipsis-h");
    private final By lstTestPlanRow = By.xpath("//tr[@data-testid='submissionRow']");
    private final By iconLoadPage = By.xpath("//div[@data-testid='spinner-loading']");
    private final By lnkReleaseVersionFilter = By.xpath("//button[@id='dropdownMenuLink' and contains(text(), 'Release Version')]");
    private final By txtReleaseVersionFilter = By.xpath("//button[text()='Release Version']//..//input[@placeholder='Search...']");
    private final By lnkIconPlus = By.xpath("//button[text()='Release Version']//..//input[@placeholder='Search...']//..//button[@data-testid='button-plus']");
    private final By releaseVersionFilter = By.xpath("//button[@id='dropdownMenuLink' and contains(text(), 'Release Version')]");
    private final By btnReleaseVersionFilter = By.xpath("//button[text()='Release Version']//..//input[@type='text']");
    private final By iconPlusReleaseVersion = By.xpath("//button[text()='Release Version']//..//input[@placeholder='Search...']//..//button[@data-testid='button-plus']");
    private final By btnSearch = By.xpath("//button[@class='btn btn-search btn-outline-secondary right-oriented-search-btn']");
    private final By searchIcon = By.xpath("//button[@class='btn btn-search btn-outline-secondary right-oriented-search-btn']");
    private static By actionMenu = By.xpath("//div[@class='submission-search-grid__header']//i[@class='fas fa-ellipsis-h']");
    private static By exportAllColumns = By.xpath("//div[@class='submission-search-grid__header']//div[@aria-labelledby='dropdownMenuLink']/button[3]");
    private static By exportVisibleColumns = By.xpath("//div[@class='submission-search-grid__header']//div[@aria-labelledby='dropdownMenuLink']/button[2]");
    private static By chooseColumns = By.xpath("//div[@class='submission-search-grid__header']//div[@aria-labelledby='dropdownMenuLink']/button[1]");
    private static By noResultFound = By.xpath("//h4[text()='No Results Found.']");
    private final By navUser = By.xpath("//div[contains(@class,'nav-user')]//span");
    private final By signOutElement = By.xpath("//i[@class='fas fa-sign-out-alt mr-1']");
    private static By txtSearch = By.xpath("//input[@placeholder='Search by Initial Code, Product Name, or Publisher...']");

    public LotcheckSubmissionSearchPage(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public String getTitle() {
        String title = getPageTitle();
        extentLogger.logInfo(title);
        return title;
    }

    public LotcheckSubmissionSearchPage selectMenuForGameCode() {
        extentLogger.logInfo("");
        waitForAndClickElement(lnkGameCodeMenu);
        return this;
    }

    public LotcheckReportSubmissionOverview selectDropdownItem() {
        extentLogger.logInfo("");
        openLinkInSameTab(By.linkText("View Lotcheck Report"));
        //waitForElementNotVisible(By.xpath("//div[@data-testid=\"spinner-loading\"]"), WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        return getNewPage(LotcheckReportSubmissionOverview.class);
    }

    public LotcheckSubmissionSearchPage selectActionMenu(String gameCode) {
        openLinkInSameTab(By.xpath(String.format("//td[contains(text(),'%s')]/following-sibling::td[@class='align-middle text-center']//button//i", gameCode)));
        extentLogger.logInfo(gameCode);
        return this;
    }

    public int getNumberTestPlan() {
        List<WebElement> testPlan = findElements(lstTestPlanRow);
        int num = testPlan.size();
        extentLogger.logInfo("Number TestPlan:" + num);
        return num;
    }

    public LotcheckSubmissionSearchPage searchBySubmissionVersion(String searchString) {
        extentLogger.logInfo(searchString);
        this.waitForAndClickElement(By.xpath("//button[@id='dropdownMenuLink' and contains(text(), 'Submission Version')]"));
        extentLogger.logInfo("");
        this.waitForElementVisible(By.xpath("//button[text()='Submission Version']//..//input[@placeholder='Search...']"));
        extentLogger.logInfo("");
        this.typeText(By.xpath("//button[text()='Submission Version']//..//input[@placeholder='Search...']"), searchString);
        this.waitForAndClickElement(By.xpath("//button[text()='Submission Version']//..//input[@placeholder='Search...']//..//button[@data-testid='button-plus']"));
        waitForElementNotVisible(By.xpath("//div[@data-testid='spinner-loading']"), WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return getNewPage(LotcheckSubmissionSearchPage.class);
    }

    public LotcheckSubmissionSearchPage searchByReleaseVersion(String searchString) {
        extentLogger.logInfo(searchString);
        this.waitForAndClickElement(releaseVersionFilter);
        extentLogger.logInfo("");
        this.waitForElementVisible(btnReleaseVersionFilter);
        extentLogger.logInfo("");
        this.typeText(btnReleaseVersionFilter, searchString);
        this.waitForAndClickElement(iconPlusReleaseVersion);
        waitForElementNotVisible(iconLoadPage, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return getNewPage(LotcheckSubmissionSearchPage.class);
    }

    public boolean waitTestPlanIsDisplay() {
        extentLogger.logInfo("");
        waitForElementVisible(By.xpath("//tr[@data-testid='submissionRow']"), WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return true;
    }

    public LotcheckReportSubmissionOverview selectSubmission(String gameCode, String releaseVersion, String submissionVersion) {
        waitForAndClickElement(By.xpath(String.format("//td[contains(text(),'%s')]/following-sibling::td[text()='%s']/following-sibling::td[text()='%s']/ancestor::tr", gameCode, releaseVersion, submissionVersion)));
        ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(0));
        driver.close();
        driver.switchTo().window(tabs2.get(1));
        return getNewPage(LotcheckReportSubmissionOverview.class);
    }


    public LotcheckSubmissionSearchPage selectFilterByName(String filterName) {
        By btnFormat = By.xpath(String.format("//button[contains(@id,'%s') and @type='button']", filterName));
        //waitForAndClickElement(btnFormat);
        extentLogger.logInfo("selectFilterByName");
        WebElement element = driver.findElement(btnFormat);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", element);
        return this;
    }


    public LotcheckSubmissionSearchPage typeTabFilter(String filterName) {
        typeTab(By.xpath(String.format("//button[contains(@id,'%sId')]", filterName)));
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckSubmissionSearchPage typeTabSearchFilter(String filterName) {
        typeTab(By.xpath(String.format("//button[@id='dropdownMenuLink' and contains(text(), '%s')]", filterName)));
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckSubmissionSearchPage selectOptionOfFilterByFilterName(String filterName, String option) {
        this.selectFilterByName(filterName);
        extentLogger.logInfo("");
        selectCheckbox(By.xpath(String.format("//ul[contains(@id,'%sMenuId')]//label[contains(text(),'%s')]/input", filterName, option)));
        extentLogger.logInfo("");
        this.typeTabFilter(filterName);
        extentLogger.logInfo(filterName + " : " + option);
        return this;
    }

    public LotcheckSubmissionSearchPage uncheckOptionOfFilterByFilterName(String filterName, String option) {
        this.selectFilterByName(filterName);
        extentLogger.logInfo("");
        uncheckCheckbox(By.xpath(String.format("//ul[contains(@id,'%sMenuId')]//label[contains(text(),'%s')]/input", filterName, option)));
        extentLogger.logInfo("");
        this.typeTabFilter(filterName);
        extentLogger.logInfo(filterName + " : " + option);
        return this;
    }

    public LotcheckSubmissionSearchPage enterValueFilter(String filterName, String searchString) {
        this.waitForElementClickable(By.xpath(String.format("//button[@id='dropdownMenuLink' and contains(text(), '%s')]", filterName)), WaitTimes.WAIT_FOR_SCRIPT_TIMEOUT_SECS);
        this.waitForAndClickElement(By.xpath(String.format("//button[@id='dropdownMenuLink' and contains(text(), '%s')]", filterName)));
        extentLogger.logInfo(filterName);
        this.waitForElementVisible(By.xpath(String.format("//button[text()='%s']//..//input[@placeholder='Search...']", filterName)));
        extentLogger.logInfo(filterName);
        this.typeText(By.xpath(String.format("//button[text()='%s']//..//input[@placeholder='Search...']", filterName)), searchString);
        extentLogger.logInfo(searchString);
        this.waitForAndClickElement(By.xpath(String.format("//button[text()='%s']//..//input[@placeholder='Search...']//..//button[@data-testid='button-plus']", filterName)));
        extentLogger.logInfo("");
        this.typeTabSearchFilter(filterName);
        return this;
    }


    public String currentFilterValue(String filterName) {
        String value = getText(By.xpath(String.format("//button[contains(@id,'%sId')]/strong", filterName)));
        extentLogger.logInfo(filterName + value);
        return value;
    }


    public String currentFilterValueOfSearchFilter(String filterName) {
        String value = getText(By.xpath(String.format("//button[@id='dropdownMenuLink' and contains(text(), '%s')]/strong", filterName)));
        extentLogger.logInfo(filterName + " : " + value);
        return value;
    }

    public String currentFilterValueOfDateFilter(String filterName) {
        String value = getText(By.xpath(String.format("//button[@id='dropdownMenuLink']/div[text()='%s']/strong", filterName)));
        extentLogger.logInfo(filterName + value);
        return value;
    }

    public LotcheckSubmissionSearchPage clickSearchIcon() {
        this.waitForAndClickElement(searchIcon);
        extentLogger.logInfo("");
        waitForElementNotVisible(By.xpath("//div[@data-testid='spinner-loading']"), WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return this;
    }

    public boolean isNoResultFound() {
        Boolean found;
        if (elementNotExists(noResultFound)) {
            found = false;
        } else {
            found = true;
        }
        extentLogger.logInfo(found.toString());
        return found;
    }

    public String getValueOfSubmissionTable(int row, int column) {
        By searchRow = By.xpath(String.format("//tbody/tr[%d]/td[%d]", row, column));
        waitForElementVisible(searchRow, WaitTimes.WAIT_FOR_ELEMENT_VISIBLE_TIMEOUT_SECS);
        String value = getText(searchRow);
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckSubmissionSearchPage selectFromDateAndToDateByFilterName(String filterName, int fromDate, int toDate) {
        By btnFilter = By.xpath(String.format("//button[@id='dropdownMenuLink']/div[text()='%s']/..", filterName));
        waitForElementClickable(btnFilter);
        waitForAndClickElement(btnFilter);
        extentLogger.logInfo("");
        this.selectFromDate(filterName, fromDate);
        this.selectToDate(filterName, toDate);
        typeTab(btnFilter);
        return this;
    }

    public LotcheckSubmissionSearchPage selectFromDate(String filterName, int fromDate) {
        waitForAndClickElement(By.xpath(String.format("//button[@id='dropdownMenuLink']/div[text()='%s']/../../div//div[contains(@class,'form-group')][1]//input[contains(@class,'date-alt-field ')]", filterName)));
        new CalendarModalSLCMS(this.driver).chooseDateInCalenderSLCMS(fromDate);
        extentLogger.logInfo("From date be added (days): " + fromDate);
        return this;
    }

    public LotcheckSubmissionSearchPage selectToDate(String filterName, int toDate) {
        waitForAndClickElement(By.xpath(String.format("//button[@id='dropdownMenuLink']/div[text()='%s']/../../div//div[contains(@class,'form-group')][2]//input[contains(@class,'date-alt-field ')]", filterName)));
        new CalendarModalSLCMS(this.driver).chooseDateInCalenderSLCMS(toDate);
        extentLogger.logInfo("To date be added (days): " + toDate);
        return this;
    }

    public LotcheckSubmissionSearchPage waitToLoading() {
        waitForElementNotVisible(iconLoadPage, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckSubmissionSearchPage selectSearchIcon() {
        this.waitForAndClickElement(searchIcon);
        extentLogger.logInfo("");
        waitForElementNotVisible(By.xpath("//div[@data-testid='spinner-loading']"), WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return this;
    }

    public String getFreeToPlayData(String fileName, String gameCode) {
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + fileName;
        File file = new File(path);
        if (file.exists()) {
            System.out.println("File exist");
        }
        String freeToPlayValue = null;
        BufferedReader buf;
        try {
            buf = new BufferedReader(new FileReader(path));
            String strings = buf.lines().collect(Collectors.joining());
            String cell[] = strings.split("(?=,HAC)");
            int num = cell.length;
            for (int i = 0; i < num; i++) {
                if (cell[i].contains(gameCode)) {
                    String[] rowValue = cell[i].split(",");
                    freeToPlayValue = rowValue[rowValue.length - 1];
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        extentLogger.logInfo(freeToPlayValue);
        return freeToPlayValue;
    }

    public ArrayList<String> getGameCodeData(String fileName, int number) {
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + fileName;
        File file = new File(path);
        if (file.exists()) {
            System.out.println("File exist");
        }
        ArrayList<String> freeToPlayValue = new ArrayList<>();
        BufferedReader buf;
        try {
            buf = new BufferedReader(new FileReader(path));
            String strings = buf.lines().collect(Collectors.joining());
            String cell[] = strings.split("(?=,HAC)");
            for (int i = 1; i < number; i++) {
                String[] rowValue = cell[i].split(",");
                freeToPlayValue.add(rowValue[1]);
                extentLogger.logInfo(rowValue[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return freeToPlayValue;
    }

    public LotcheckSubmissionSearchPage clickActionMenu() {
        waitForAndClickElement(actionMenu);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckSubmissionSearchPage deleteFile(String fileName) throws Exception {
        String path = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator;
        File file = new File(System.getProperty("user.dir") + path + fileName);
        file.delete();
        extentLogger.logInfo(fileName);
        return this;
    }

    public LotcheckSubmissionSearchPage selectExportAllColumns(String fileName) throws Exception {
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + fileName;
        if (getFileSizeFromPath(fileName) != 0L) {
            deleteFile(fileName);
            waitForAndClickElement(exportAllColumns);
        } else {
            waitForAndClickElement(exportAllColumns);
        }
        extentLogger.logInfo(path);
        return this;
    }

    public LotcheckSubmissionSearchPage selectExportVisibleColumns(String fileName) throws Exception {
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + fileName;
        if (getFileSizeFromPath(fileName) != 0L) {
            deleteFile(fileName);
            waitForAndClickElement(exportVisibleColumns);
        } else {
            waitForAndClickElement(exportVisibleColumns);
        }
        extentLogger.logInfo(path);
        return this;
    }

    private long getFileSize(String fileName) {
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + fileName;
        try {

            return Files.size(Paths.get(path));

        } catch (IOException e) {
            return 0L;
        }
    }

    public long getFileSizeFromPath(String fileName) {
        long size = getFileSize(fileName);
        for (int i = 0; i < 5; i++) {
            if (size == 0L) {
                WaitUtils.idle(10000);
                size = getFileSize(fileName);
            } else {
                i = 5;
                return size;
            }
        }
        extentLogger.logInfo(fileName);
        return 0L;
    }

    public LotcheckSubmissionSearchChooseColumnsModal selectChooseColumns() {
        waitForAndClickElement(chooseColumns);
        extentLogger.logInfo("");
        return getNewPage(LotcheckSubmissionSearchChooseColumnsModal.class);
    }

    public LotcheckSubmissionSearchPage chooseFilterOnMoreByName(String filterName) {
        By filterElement = By.xpath(String.format("//ul[@id='MoreMenuId']//label[contains(text(),' %s')]", filterName));
        waitForElementVisible(filterElement, WaitTimes.WAIT_FOR_ELEMENT_VISIBLE_TIMEOUT_SECS);
        selectCheckbox(filterElement);
        extentLogger.logInfo("");
        return this;
    }

    public ArrayList<String> getOptionsValueOfFilter(String filterName) {
        this.selectFilterByName(filterName);
        List<WebElement> options = driver.findElements(By.xpath(String.format("//ul[contains(@id,'%sMenuId')]//label", filterName)));
        ArrayList<String> values = new ArrayList<>();
        for (WebElement option : options) {
            values.add(option.getText().trim());
        }
        extentLogger.logInfo(values.toString());
        this.typeTabFilter(filterName);
        return values;
    }

    public boolean isSubmissionDisplayedCorrect(String gameCode, String releaseVersion, String submissionVersion) {
        boolean value = false;
        int row = getNumberTestPlan();
        boolean check = elementNotExists(By.xpath(String.format("//td[text()='%s']/following-sibling::td[text()='%s']/following-sibling::td[text()='%s']/ancestor::tr", gameCode, releaseVersion, submissionVersion)));
        if (!check && row == 2) {
            value = true;
        }
        extentLogger.logInfo(releaseVersion + "." + submissionVersion + ": " + value);
        return value;
    }

    public HyruleAuthLogoutPage logOut() {
        openLinkInSameTab(navUser);
        extentLogger.logInfo("");
        waitForAndClickElement(signOutElement);
        extentLogger.logInfo("");
        return getNewPage(HyruleAuthLogoutPage.class);
    }

    public LotcheckSubmissionSearchPage clickSearchButton() {
        waitForAndClickElement(btnSearch);
        waitForElementNotVisible(iconLoadPage, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        extentLogger.logInfo("");
        return this;
    }

    public ArrayList<String> getGameCodeDataExportVisibleColumnFile(String fileName, int number) {
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + fileName;
        File file = new File(path);
        if (file.exists()) {
            System.out.println("File exist");
        }
        ArrayList<String> gameCode = new ArrayList<>();
        BufferedReader buf;
        try {
            buf = new BufferedReader(new FileReader(path));
            String strings = buf.lines().collect(Collectors.joining());
            String cell[] = strings.split("(?=HAC)");
            for (int i = 1; i < number; i++) {
                String[] rowValue = cell[i].split(",");
                gameCode.add(rowValue[0]);
                extentLogger.logInfo(rowValue[0]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return gameCode;
    }

    public LotcheckSubmissionSearchPage searchSubmission(String valueSearch) {
        waitForElementVisible(txtSearch);
        typeText(txtSearch, valueSearch);
        waitForAndClickElement(searchIcon);
        waitForElementNotVisible(iconLoadPage, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        extentLogger.logInfo("Search Submission");
        return getNewPage(LotcheckSubmissionSearchPage.class);
    }

    public LotcheckReportSubmissionOverview selectSubmission(String gameCode, String releaseVersion, String submissionVersion, String productName) {
        for (int i = 0; i < 10; i++) {
            if (elementNotExists(By.xpath(String.format("//td[text()='%s']/following-sibling::td[text()='%s']/following-sibling::td[text()='%s']/ancestor::tr", gameCode, releaseVersion, submissionVersion)))) {
                driver.navigate().refresh();
                waitForElementVisible(uniqueElement, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
                WaitUtils.idle(3000);
                typeText(By.xpath("//nav//input[@name='searchInput']"), productName);
                extentLogger.logInfo(" input search");
                waitForAndClickElement(By.xpath("//nav//i[contains(@class,'fa-search')]"));
                extentLogger.logInfo(" click search button");
                waitForElementVisible(By.xpath("//div[@data-testid=\"spinner-loading\"]"), WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
                waitForElementNotVisible(By.xpath("//div[@data-testid=\"spinner-loading\"]"), WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
                waitDocumentReady();

            } else {
                break;
            }
        }

        waitForAndClickElement(By.xpath(String.format("//td[text()='%s']/following-sibling::td[text()='%s']/following-sibling::td[text()='%s']/ancestor::tr", gameCode, releaseVersion, submissionVersion)));
        ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(0));
        driver.close();
        driver.switchTo().window(tabs2.get(1));
        return getNewPage(LotcheckReportSubmissionOverview.class);
    }


}
