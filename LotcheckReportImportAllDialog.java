package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class LotcheckReportImportAllDialog extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@data-testid='submission-import-file-modal']");
    private final By btnImport = By.xpath("//div[@data-testid='submission-import-file-modal']//button[@type='submit']");
    private final By btnSelectFile = By.xpath("//div[@class='text-center d-flex justify-content-center file-picker-title']");
    private static By iconLoading = By.xpath("//div[@data-testid='spinner-loading']");

    public LotcheckReportImportAllDialog(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public LotcheckReportImportAllDialog clickSelectFile() {
        waitForAndClickElement(btnSelectFile);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionOverview clickImport() {
        waitForElementClickable(btnImport);
        waitForAndClickElement(btnImport);
        waitForElementVisible(By.xpath("//div[@role='alert']"), WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        waitForElementNotVisible(By.xpath("//div[@role='alert']"), WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionOverview.class);
    }

    public LotcheckReportSubmissionFeatures clickImport_Features() {
        waitForElementClickable(btnImport);
        waitForAndClickElement(btnImport);
        waitForElementVisible(By.xpath("//div[@role='alert']"), WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        waitForElementNotVisible(By.xpath("//div[@role='alert']"), WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionFeatures.class);
    }

    public LotcheckReportSubmissionTestScenarios clickImport_TestScenarios() {
        waitForElementClickable(btnImport);
        waitForAndClickElement(btnImport);
        waitForElementVisible(By.xpath("//div[@role='alert']"), WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        waitForElementNotVisible(By.xpath("//div[@role='alert']"), WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionTestScenarios.class);
    }

    public LotcheckReportImportAllDialog selectFileInPC(String fileName) {
        extentLogger.logInfo("");
        String path = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator;
        try {
            Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\Autoit\\FileUpload2.exe" + " " + System.getProperty("user.dir") + path + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public LotcheckReportSubmissionFeatures selectInvalidFileInPC(String fileName) {
        extentLogger.logInfo("");
        String path = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator;
        try {
            Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\Autoit\\FileUpload2.exe" + " " + System.getProperty("user.dir") + path + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getNewPage(LotcheckReportSubmissionFeatures.class);
    }
}
