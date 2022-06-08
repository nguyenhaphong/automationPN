package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.lcms.submission_reports.collapsible.LotcheckReportSubmissionFeaturesProgramSpecs;
import net.nintendo.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LotcheckReportSubmissionFeatures extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@data-testid='features-page']");
    private static final String pageTitle = "Features Submission: LCR - LCMS";

    private final By btnProgramSpec = By.xpath("//button[text()='Program Specifications']");
    private final By lnkCollapse = By.xpath("//div[@id='collapseScene0']");
    private final By menuIcon = By.xpath("//button[@id='dropdownMenuLink']");
    private final By programSpecElement = By.xpath("//button[text()='Program Specifications']");
    private final By collapseElement = By.xpath("//div[@id='collapseScene0']");
    private final By btnExportAll = By.xpath("//button[@class='btn dropdown-item'][1]");
    private final By btnImportAll = By.xpath("//button[@class='btn dropdown-item'][2]");
    private final By btnSourceLanguageDisplay = By.xpath("//button[@data-testid='sourceDisplay-button']");
    private final By btnTargetLanguageDisplay = By.xpath("//button[@data-testid='targetDisplay-button']");
    private final By btnSourceAndTargetLanguageDisplay = By.xpath("//button[@data-testid='sourceAndTargetDisplay-button']");
    private final By errorMessage = By.xpath("//div[@data-testid='multiple-error-messages-modal']//div[@class='alert alert-danger']");
    private final By btnClose_errorDialog = By.xpath("//button[@class='close']");
    private final By listSourceFields = By.xpath("//li[@data-testid='features-all-else-source']");
    private final By listTargetFields = By.xpath("//li[@data-testid='features-all-else-target']");
    private final By iconLoading = By.xpath("//div[@data-testid='spinner-loading']");
    private final By listTargetValue = By.xpath("//li[@data-testid='features-all-else-target']//span[2]");
    private final By listSourceValue = By.xpath("//li[@data-testid='features-all-else-source']//span[2]");
    private final By listTargetFollowSourceFields = By.xpath("//li[@data-testid='features-all-else-source']/following-sibling::li[@data-testid='features-all-else-target']");


    public LotcheckReportSubmissionFeatures(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public LotcheckReportNav navBar() {
        return getNewPage(LotcheckReportNav.class);
    }

    public LotcheckReportSubmissionFeaturesProgramSpecs expandProgramSpecifications() {
        expandContainer(btnProgramSpec);
        // need to look for something that will become visible after expanding
        waitForElementVisible(lnkCollapse);
        return getNewPage(LotcheckReportSubmissionFeaturesProgramSpecs.class);
    }

    public String getTranslateText(String feature) {
        String value = getText(By.xpath(String.format("//button[text()='%s']/following-sibling::span", feature))).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public String getColorOfTranslateField(String feature) {
        String value = driver.findElement(By.xpath(String.format("//button[text()='%s']/following-sibling::span", feature))).getCssValue("background-color");
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckReportSubmissionFeatures clickMenu() {
        waitForElementClickable(menuIcon, WaitTimes.WAIT_FOR_UNIQUE_ELEMENT_VISIBILITY_SECS);
        waitDocumentReady();
        waitForAndClickElement(menuIcon);
        waitDocumentReady();
        extentLogger.logInfo("");
        return this;
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

    public LotcheckReportSubmissionFeatures checkTargetAndSourceLanguageDisplayed() {
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
    public LotcheckReportSubmissionFeatures expandFeature(String feature){
        waitDocumentReady();
        waitForAndClickElement(By.xpath(String.format("//button[text()='%s']",feature)));
        waitDocumentReady();
        WaitUtils.idle(2000);
        extentLogger.logInfo(feature);
        return this;
    }

    public LotcheckReportSubmissionFeatures collapseFeature(String feature){
        waitForAndClickElement(By.xpath(String.format("//button[text()='%s']",feature)));
        waitDocumentReady();
        WaitUtils.idle(2000);
        extentLogger.logInfo(feature);
        return this;
    }
    public String getErrorMessage() {
        waitForElementVisible(errorMessage, WaitTimes.WAIT_FOR_UNIQUE_ELEMENT_VISIBILITY_SECS);
        String value = getText(errorMessage);
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckReportSubmissionFeatures clickCloseDialog() {
        waitForElementVisible(btnClose_errorDialog,WaitTimes.WAIT_FOR_UNIQUE_ELEMENT_VISIBILITY_SECS);
        waitDocumentReady();
        WaitUtils.idle(2000);
        waitForAndClickElement(btnClose_errorDialog);
        extentLogger.logInfo("");
        return this;
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
    public String getMenuOptions(int index) {
        String value = getText(By.xpath(String.format("//div[@aria-labelledby='dropdownMenuLink']//button[contains(@class,'dropdown-item')][%s]", index))).trim();
        extentLogger.logInfo(value);
        return value;
    }


    public LotcheckReportSubmissionFeatures refreshPage() {
        extentLogger.logInfo("");
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
        extentLogger.logInfo("");
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + fileName;
        try {
            return Files.size(Paths.get(path));

        } catch (IOException e) {
            return 0L;
        }
    }

    public String getSourceValue(String feature, String field) {
        String value = getText(By.xpath(String.format("//button[text()='%s']/ancestor::h4/following-sibling::div//div[text()='%s']/ancestor::dd/following-sibling::dt//li[@data-testid='features-all-else-source']//span[2]", feature, field))).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public String getTargetValue(String feature, String field) {
        String value = getText(By.xpath(String.format("//button[text()='%s']/ancestor::h4/following-sibling::div//div[text()='%s']/ancestor::dd/following-sibling::dt//li[@data-testid='features-all-else-target']//span[2]", feature, field))).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckReportSubmissionFeatures clickSourceLanguageDisplay() {
        waitForAndClickElement(btnSourceLanguageDisplay);
        waitForElementNotVisible(listTargetFields,WaitTimes.WAIT_FOR_UNIQUE_ELEMENT_VISIBILITY_SECS);
        extentLogger.logInfo("");
        return this;

    }

    public LotcheckReportSubmissionFeatures clickTargetLanguageDisplay() {
        waitForAndClickElement(btnTargetLanguageDisplay);
        waitForElementNotVisible(listSourceFields,WaitTimes.WAIT_FOR_ELEMENT_NOT_VISIBLE_TIMEOUT_SECS);
        extentLogger.logInfo("");
        return this;
    }
    public LotcheckReportSubmissionFeatures waitPageLoaded(){
        extentLogger.logInfo("");
        waitForElementNotVisible(menuIcon, WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        waitForElementVisible(menuIcon,WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        return this;
    }
}
