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
import java.util.HashMap;
import java.util.List;

public class LotcheckReportSubmissionFiles extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@data-testid='files-page']");
    private static final String pageTitle = "Files Submission: LCR - LCMS";
    private final By btnClassInd = By.xpath("//button[text()='classind']");
    private final By btnAcb = By.xpath("//button[text()='acb']");
    private final By btnPegi = By.xpath("//button[text()='pegi']");
    private final By btnEsre = By.xpath("//button[text()='esrb']");
    private final By btnCero = By.xpath("//button[text()='cero']");
    private final By lblFileName = By.xpath("//tr[@data-testid='document-row']//td[1]");
    private final By uploadDateElement = By.xpath("//tr[@data-testid='document-row']//td[2]");
    private final By btnUsk = By.xpath("//button[text()='usk']");
    private final By btnOflc = By.xpath("//button[text()='oflc']");
    private final By lblMessageError = By.xpath("//div[@class='modal-body']//div[@class='alert alert-danger']");
    private final By btnUploadFile = By.xpath("//div[@class='float-right']//button[@data-testid='submit-button'][1]");
    private final By pikUploadFile = By.xpath("//input[@data-testid='file-picker-input']");
    private final By btnUploadAtPopup = By.xpath("//div[@class='modal-footer']//button[@data-testid='submit-button']");
    private final By lstNumberTestScenarioSelected = By.xpath("//i[contains(@class,'fa-folder-open')]");
    private final By btnFileName = By.xpath("//button[@class='document-link']");
    private final By cbFileName = By.xpath("//input[@type='checkbox']");
    private final By numberTestScenarioSelected = By.xpath("//i[contains(@class,'fa-folder-open')]");
    private final By uploadFileElement = By.xpath("//div[contains(@class,'justify-content-center file-picker-title')]");
    private final By uploadFileButton = By.xpath("//div[@class='float-right']//button[@data-testid='submit-button'][1]");
    private final By uploadButtonAtPopup = By.xpath("//div[@class='modal-footer']//button[@data-testid='submit-button']");

    private WebElement focusedTestScene;

    public LotcheckReportSubmissionFiles(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public LotcheckReportSubmissionFiles clickClassInd() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnClassInd);
        return this;
    }

    public LotcheckReportSubmissionFiles clickACB() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnAcb);
        return this;
    }


    public LotcheckReportSubmissionFiles clickPEGI() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnPegi);
        return this;
    }

    public LotcheckReportSubmissionFiles clickESRB() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnEsre);
        return this;
    }

    public LotcheckReportSubmissionFiles clickCERO() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnCero);
        return this;
    }

    public String getFileName() {
        return getText(lblFileName);
    }

    public String getUploadDate() {
        return getText(uploadDateElement);
    }

    public LotcheckReportSubmissionFiles clickUSK() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnUsk);
        return this;
    }

    public LotcheckReportSubmissionFiles clickOFLC() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnOflc);
        return this;
    }

    public String getErrorMessageAtAlert() {
        String message = getText(lblMessageError);
        extentLogger.logInfo(message);
        return message;
    }

    public LotcheckReportSubmissionFiles clickUploadFile() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnUploadFile);
        return this;
    }

    /***
     * Add file to method of actual focused File base on No
     * @return LotcheckReportSubmissionFiles
     */
    public LotcheckReportSubmissionFiles uploadFileAtPopup(String fileName) {
        HashMap<String, String> attributeMap = new HashMap<>();
        //attributeMap.put("opacity", "1");
        attributeMap.put("display", "inline!important");
        String path = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + fileName;
        By locator = pikUploadFile;
        WebElement addFile = findElementPresentInPopup(locator);
        changeStyleAttribute(addFile, attributeMap);
        File file = new File(path);
        typeText(addFile, file.getAbsolutePath());
        extentLogger.logInfo(file.getAbsolutePath());
        return this;
    }

    private WebElement findElementPresentInPopup(By locator) {
        return findNestedElementPresent(focusedTestScene, locator);
    }

    public LotcheckReportSubmissionFiles clickUploadButtonAtPopupUploadFile() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnUploadAtPopup);
        return this;
    }

    public int getLstNumberTestScenarioSelected() {
        List<WebElement> testScenarioSelected = driver.findElements(lstNumberTestScenarioSelected);
        int number = testScenarioSelected.size();
        extentLogger.logInfo(String.valueOf(number));
        return number;
    }

    public LotcheckReportSubmissionFiles selectMethodOfTestScenario(String methodName, int index) {
        waitForAndClickElement(By.xpath(String.format("//button[text()='%s'][%d]", methodName, index)));
        extentLogger.logInfo("method name" + methodName + " No. " + index);
        return this;
    }

    public boolean isFileNameIsDisplayed(String fileName) {
        boolean isDisplayed = findElement(By.xpath("//button[@class='document-link' and text()='" + fileName + "']")).isDisplayed();
        extentLogger.logInfo(Boolean.toString(isDisplayed));
        return isDisplayed;
    }

    public void clickDownloadFile(String fileName) {
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + fileName;
        if (getFileSizeFromPath(fileName) != 0L) {
            deleteFile(fileName);
            clickElementOnMetadata(btnFileName);
        } else {
            clickElementOnMetadata(btnFileName);
        }
        extentLogger.logInfo(path);
    }

    public LotcheckReportSubmissionFiles selectUploadFileAtPopup() {
        waitForAndClickElement(uploadFileElement);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionFiles selectFileInPC(String fileName) {
        extentLogger.logInfo("");
        String path = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator;
        try {
            Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\Autoit\\FileUpload2.exe" + " " + System.getProperty("user.dir") + path + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public long getFileSizeFromPath(String fileName) {
        WaitUtils.waitUntilComplete(1000L, 100000L, fileName, (input) -> {
            return getFileSize(input) > 0;
        });
        extentLogger.logInfo(fileName);
        return getFileSize(fileName);
    }

    private long getFileSize(String fileName) {
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + fileName;
        try {
            return Files.size(Paths.get(path));

        } catch (IOException e) {
            return 0L;
        }
    }

    private LotcheckReportSubmissionFiles deleteFile(String fileName) {
        String path = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator;
        File file = new File(System.getProperty("user.dir") + path + fileName);
        file.delete();
        extentLogger.logInfo(fileName);
        return this;
    }

    public String getNameOfFolder(int index) {
        String value = getText(By.xpath(String.format("//button[@data-testid='folder-button'][%d]", index))).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckReportSubmissionFiles selectCheckboxOfFile() {
        extentLogger.logInfo("Select Checkbox Of File");
        selectCheckbox(cbFileName);
        return this;
    }

    public LotcheckReportNav navBar() {
        return getNewPage(LotcheckReportNav.class);
    }

    public LotcheckReportSubmissionFiles waitPageLoaded() {
        WaitUtils.idle(2000);
        waitForElementVisible(uniqueElement, WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        return this;
    }

    public int getNumberTestScenarioSelected() {
        List<WebElement> testScenarioSelected = driver.findElements(numberTestScenarioSelected);
        int number = testScenarioSelected.size();
        extentLogger.logInfo(String.valueOf(number));
        return number;
    }


}