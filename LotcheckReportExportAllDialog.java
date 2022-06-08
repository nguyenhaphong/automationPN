package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LotcheckReportExportAllDialog extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@data-testid='submission-export-file-modal']");
    private final By languageDropdown = By.xpath("//select[@id='dropdown']");
    private final By btnExport = By.xpath("//div[@data-testid='submission-export-file-modal']//button[@type='submit']");

    public LotcheckReportExportAllDialog(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public LotcheckReportExportAllDialog selectLanguage(String language) {
        selectValueOnDropDown(languageDropdown, language);
        extentLogger.logInfo(language);
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

    public LotcheckReportSubmissionOverview clickExport(String fileName) {
        if (getFileSizeFromPath(fileName) != 0L) {
            deletefile(fileName);
            waitForAndClickElement(btnExport);
        } else {
            waitForAndClickElement(btnExport);
        }
        waitForElementVisible(By.xpath("//div[@role='alert']"), WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        waitForElementNotVisible(By.xpath("//div[@role='alert']"), WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionOverview.class);
    }

    private LotcheckReportExportAllDialog deletefile(String fileName) {
        String path = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator;
        File file = new File(System.getProperty("user.dir") + path + fileName);
        file.delete();
        extentLogger.logInfo(fileName);
        return this;
    }

    public LotcheckReportSubmissionFeatures clickExport_FeaturesTab(String fileName) {
        if (getFileSizeFromPath(fileName) != 0L) {
            deletefile(fileName);
            waitForAndClickElement(btnExport);
        } else {
            waitForAndClickElement(btnExport);
        }
        waitForElementVisible(By.xpath("//div[@role='alert']"), WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        waitForElementNotVisible(By.xpath("//div[@role='alert']"), WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionFeatures.class);
    }

    public LotcheckReportSubmissionTestScenarios clickExport_TestScenariosTab(String fileName) {
        if (getFileSizeFromPath(fileName) != 0L) {
            deletefile(fileName);
            waitForAndClickElement(btnExport);
        } else {
            waitForAndClickElement(btnExport);
        }
        waitForElementVisible(By.xpath("//div[@role='alert']"), WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        waitForElementNotVisible(By.xpath("//div[@role='alert']"), WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionTestScenarios.class);
    }
}
