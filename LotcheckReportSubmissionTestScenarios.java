package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LotcheckReportSubmissionTestScenarios extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//h2[@class='mb-3']");
    private final By btnHowToCheckNPLNFriendList = By.xpath("//button[@class='btn btn-link question-icon collapsed NPLN']");
    private final By btnHowToCheckNPLNFriendPresence = By.xpath("//button[@class='btn btn-link question-icon collapsed NPLN']");
    private static By btnExpandAllCollapseAll = By.xpath("//div[@id='collapseScenesInputDevices']/div/button");
    private final By menuIcon = By.xpath("//button[@id='dropdownMenuLink']");
    private final By errorMessage = By.xpath("//div[@data-testid='multiple-error-messages-modal']//div[@class='alert alert-danger']");
    private final By btnClose_errorDialog = By.xpath("//button[@class='close']");
    private final By btnSourceAndTargetLanguageDisplay = By.xpath("//button[@data-testid='sourceAndTargetDisplay-button']");
    private final By btnExportAll = By.xpath("//button[@class='btn dropdown-item'][1]");
    private final By btnImportAll = By.xpath("//button[@class='btn dropdown-item'][2]");
    private final By btnSourceLanguageDisplay = By.xpath("//button[@data-testid='sourceDisplay-button']");
    private final By btnTargetLanguageDisplay = By.xpath("//button[@data-testid='targetDisplay-button']");
    private final By iconLoading = By.xpath("//div[@data-testid='spinner-loading']");
    private final By listSourceFields = By.xpath("//li[@data-testid='test-scenarios-all-else-source']");
    private final By listTargetFields = By.xpath("//li[@data-testid='test-scenarios-all-else-target']");
    private final By listTargetValue = By.xpath("//li[@data-testid='test-scenarios-all-else-target']//span[2]");
    private final By listTargetFollowSourceFields = By.xpath("//li[@data-testid='test-scenarios-all-else-source']/following-sibling::li[@data-testid='test-scenarios-all-else-target']");
    private final By btnHowToCheckTouchScreen = By.xpath("//button[@class='btn btn-link question-icon collapsed InputDevices' and text ()='How to check touch screen']");
    private final By btnHowToCheckNintendoSwitchProController = By.xpath("//button[@class='btn btn-link question-icon collapsed InputDevices' and text ()='How to check Nintendo Switch Pro " +
            "Controller']");

    public LotcheckReportSubmissionTestScenarios(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public boolean howToCheckNPLNFriendListTestScenariosIsDisplay() {
        boolean value = true;
        if (elementNotExists(btnHowToCheckNPLNFriendList)) {
            value = false;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public boolean howToCheckNPLNFriendPresenceInformationIsDisplay() {
        boolean value = true;
        if (elementNotExists(btnHowToCheckNPLNFriendPresence)) {
            value = false;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public LotcheckReportSubmissionTestScenarios clickExpandAndCollapseHowToCheckNPLNFriendList() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnHowToCheckNPLNFriendList);
        return this;
    }

    public LotcheckReportSubmissionTestScenarios clickDownloadFile(String fileName) {
        extentLogger.logInfo(fileName);
        waitForAndClickElement(By.xpath("//button[text()='" + fileName + "']"));
        return this;
    }

    public LotcheckReportSubmissionTestScenarios selectExpandAllCollapseAllButton() {
        waitForAndClickElement(btnExpandAllCollapseAll);
        extentLogger.logInfo("");
        return this;
    }

    public String getMethodNameOfTestScenario(String testScenario) {
        By element = By.xpath(String.format("//button[contains(text(),'%s')]/../..//dd[1]/li[1]/span[2]", testScenario));
        String methodName = getText(element);
        extentLogger.logInfo(testScenario + ", method name is: " + methodName);
        return methodName;
    }

    public String getExplainHowToDemonstrateThisMethod(String testScenario) {
        By element = By.xpath(String.format("//button[contains(text(),'%s')]/../..//dd[2]/li[1]/span[2]", testScenario));
        String explain = getText(element);
        extentLogger.logInfo(testScenario + ", Explain How to Demonstrate This Method is: " + explain);
        return explain;
    }

    public LotcheckReportSubmissionTestScenarios waitPageLoaded() {
        waitForElementNotVisible(menuIcon, WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        waitForElementVisible(menuIcon, WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        return this;
    }

    public LotcheckReportNav navBar() {
        return getNewPage(LotcheckReportNav.class);
    }

    public LotcheckReportSubmissionTestScenarios clickExpandAllCollapseAll() {
        List<WebElement> elements = findElements(btnExpandAllCollapseAll);
        for (WebElement element : elements) {
            element.click();
            waitDocumentReady();
        }
        extentLogger.logInfo("");
        return this;
    }

    public boolean isFieldStructureCorrect() {
        boolean value = false;
        List<WebElement> elements = findElements(listTargetFields);
        List<WebElement> elements2 = findElements(listSourceFields);
        if (elements.size() == elements2.size() && !elementNotExists(listTargetFollowSourceFields)) {
            value = true;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public String getTranslateText(String testScenario) {
        String value = getText(By.xpath(String.format("//button[text()='%s']/following-sibling::span", testScenario))).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public String getColorOfTranslateField(String testScenario) {
        String value = driver.findElement(By.xpath(String.format("//button[text()='%s']/following-sibling::span", testScenario))).getCssValue("background-color");
        extentLogger.logInfo(value);
        return value;
    }

    public String getSourceValue(String testScenario, String field) {
        String value = getText(By.xpath(String.format("//button[text()='%s']/ancestor::h4/following::div//dt[text()='%s']/following-sibling::dd[1]//li[@data-testid='test-scenarios-all-else-source']//span[2]", testScenario, field))).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public String getTargetValue(String testScenario, String field) {
        String value = getText(By.xpath(String.format("//button[text()='%s']/ancestor::h4/following::div//dt[text()='%s']/following-sibling::dd[1]//li[@data-testid='test-scenarios-all-else-target']//span[2]", testScenario, field))).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public boolean isAllTargetValueBlank() {
        boolean value = true;
        List<WebElement> elements = findElements(listTargetValue);
        for (WebElement element : elements) {
            if (!element.getText().equals("")) {
                value = false;
            }
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public LotcheckReportSubmissionTestScenarios clickMenu() {
        waitForAndClickElement(menuIcon);
        waitDocumentReady();
        extentLogger.logInfo("");
        return this;
    }

    public String getMenuOptions(int index) {
        waitDocumentReady();
        String value = getText(By.xpath(String.format("//div[@aria-labelledby='dropdownMenuLink']//button[contains(@class,'dropdown-item')][%s]", index))).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public boolean isNotAnyTargetFieldDisplayed() {
        boolean value = false;
        if (elementNotExists(listTargetFields)) {
            value = true;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public boolean isNotAnySourceFieldDisplayed() {
        boolean value = false;
        if (elementNotExists(listSourceFields)) {
            value = true;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public LotcheckReportExportAllDialog clickExportAll() {
        waitForAndClickElement(btnExportAll);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportExportAllDialog.class);

    }

    public LotcheckReportImportAllDialog clickImportAll() {
        waitForAndClickElement(btnImportAll);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportImportAllDialog.class);

    }

    public LotcheckReportSubmissionTestScenarios clickSourceLanguageDisplay() {
        waitForAndClickElement(btnSourceLanguageDisplay);
        extentLogger.logInfo("");
        return this;

    }

    public LotcheckReportSubmissionTestScenarios clickTargetLanguageDisplay() {
        waitForAndClickElement(btnTargetLanguageDisplay);
        extentLogger.logInfo("");
        return this;

    }

    public LotcheckReportSubmissionTestScenarios refreshPage() {
        driver.navigate().refresh();
        waitForElementVisible(iconLoading, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        waitForElementNotVisible(iconLoading, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        waitDocumentReady();
        return this;
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

    private long getFileSize(String fileName) {
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + fileName;
        try {
            return Files.size(Paths.get(path));

        } catch (IOException e) {
            return 0L;
        }
    }

    public String getErrorMessage() {
        waitForElementVisible(errorMessage, WaitTimes.WAIT_FOR_UNIQUE_ELEMENT_VISIBILITY_SECS);
        String value = getText(errorMessage);
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckReportSubmissionTestScenarios clickCloseDialog() {
        waitForElementVisible(btnClose_errorDialog, WaitTimes.WAIT_FOR_UNIQUE_ELEMENT_VISIBILITY_SECS);
        waitDocumentReady();
        WaitUtils.idle(2000);
        waitForAndClickElement(btnClose_errorDialog);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionTestScenarios checkTargetAndSourceLanguageDisplayed() {
        waitDocumentReady();
        if (!elementNotExists(btnSourceAndTargetLanguageDisplay)) {
            clickMenu();
            WaitUtils.idle(2000);
            waitForAndClickElement(btnSourceAndTargetLanguageDisplay);
            WaitUtils.idle(2000);
        }
        extentLogger.logInfo("");
        return this;
    }

    public boolean howToCheckTouchScreenIsDisplay() {
        boolean value = true;
        if (elementNotExists(btnHowToCheckTouchScreen)) {
            value = false;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public boolean howToCheckNintendoSwitchProControllerIsDisplay() {
        boolean value = true;
        if (elementNotExists(btnHowToCheckNintendoSwitchProController)) {
            value = false;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

}
