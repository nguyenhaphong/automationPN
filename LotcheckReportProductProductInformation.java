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

public class LotcheckReportProductProductInformation extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@data-testid='product-info-container']");
    private static final String pageTitle = "Product Information Product: LCR - LCMS";

    private final By lblVersionValue = By.xpath("//tr[@data-testid='related-release-row-0']//td[1]");
    private final By lblReleaseTypeValue = By.xpath("//tr[@data-testid='related-release-row-0']//td[2]");
    private final By lblReleaseVersionValue = By.xpath("//tr[@data-testid='related-release-row-0']//td[3]");
    private final By lblSubmissionVersionValue = By.xpath("//tr[@data-testid='related-release-row-0']//td[4]");
    private final By lblSubmissionJudgmentValue = By.xpath("//tr[@data-testid='related-release-row-0']//td[5]");
    private final By lblMediaTypeValue = By.xpath("//tr[@data-testid='related-release-row-0']//td[6]");
    private final By lblCompactedAppIncludeValue = By.xpath("//tr[@data-testid='related-release-row-0']//td[7]");
    private final By lblArchiveNoAOCValue = By.xpath("//tr[@data-testid='related-aoc-row-0']//td[1]");
    private final By lblSubmissionVersionAOCValue = By.xpath("//tr[@data-testid='related-aoc-row-0']//td[2]");
    private final By lblSubmissionJudgmentAOCValue = By.xpath("//tr[@data-testid='related-aoc-row-0']//td[3]");
    private final By lblGameCodeOnCardAOCValue = By.xpath("//tr[@data-testid='related-on-card-aoc-row-0']//td[1]");
    private final By lblReleaseVersionOnCardAOCValue = By.xpath("//tr[@data-testid='related-on-card-aoc-row-0']//td[2]");
    private final By lblSubmissionVersionOnCardAOCValue = By.xpath("//tr[@data-testid='related-on-card-aoc-row-0']//td[3]");
    private final By lblSubmissionJudgmentOnCardAOCValue = By.xpath("//tr[@data-testid='related-on-card-aoc-row-0']//td[4]");
    private final By lblViewSubmissionOnCardAOCValue = By.xpath("//tr[@data-testid='related-on-card-aoc-row-0']//td[5]");
    private final By lnkViewSubmissionOnCardAOC = By.xpath("//tr[@data-testid='related-on-card-aoc-row-0']//td[5]//a");
    private final By lnkMessageTab = By.xpath("//a[contains(@href,'messages')]");
    private final By lblChinaDataStatus = By.xpath("//h3//span[contains(@class,'badge badge-china-complete')]");
    private final By lnkIsbnNotice = By.xpath("//div[@data-testid='china-region-data']//dl//dd/button[@class='document-link'][1]");
    private final By lblNumberISBN = By.xpath("//div[@data-testid='china-region-data']//dl//dd[@class='col-sm-8'][2]");
    private final By lblNumberGAPP = By.xpath("//div[@data-testid='china-region-data']//dl//dd[@class='col-sm-8'][3]");
    private final By lblPublishingEntity = By.xpath("//div[@data-testid='china-region-data']//dl//dd[@class='col-sm-8'][4]");
    private final By lblOperatingEntity = By.xpath("//div[@data-testid='china-region-data']//dl//dd[@class='col-sm-8'][5]");
    private final By lblCopyrightOutsideChina = By.xpath("//div[@data-testid='china-region-data']//dl//dd[@class='col-sm-8'][6]");
    private final By lblReplyToCopyright = By.xpath("//div[@data-testid='china-region-data']//dl//dd[7]/button[@class='document-link']");

    private final By lblCopyrightRegisterNumber = By.xpath("//div[@data-testid='china-region-data']//dl//dd[@class='col-sm-8'][7]");
    private final By viewSubmission = By.xpath("//a[@class='go-to-product-level link-to-submission']");
    private final By messageTab = By.xpath("//a[contains(@href,'messages')]");
    private final By chinaDataStatus = By.xpath("//h3//span[contains(@class,'badge badge-china')]");
    private final By isbnNotice = By.xpath("//div[@data-testid='china-region-data']//dl//dd[1]/button[@class='document-link']");
    private final By numberISBN = By.xpath("//div[@data-testid='china-region-data']//dl//dd[@class='col-sm-8'][2]");
    private final By numberGAPP = By.xpath("//div[@data-testid='china-region-data']//dl//dd[@class='col-sm-8'][3]");
    private final By publishingEntity = By.xpath("//div[@data-testid='china-region-data']//dl//dd[@class='col-sm-8'][4]");
    private final By operatingEntity = By.xpath("//div[@data-testid='china-region-data']//dl//dd[@class='col-sm-8'][5]");
    private final By copyrightOutsideChina = By.xpath("//div[@data-testid='china-region-data']//dl//dd[@class='col-sm-8'][6]");
    private final By copyrightRegisterNumber = By.xpath("//div[@data-testid='china-region-data']//dl//dd[@class='col-sm-8'][8]");

    public LotcheckReportProductProductInformation(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public LotcheckReportMatrix matrix() {
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportMatrix.class);
    }

    public String getArchiveNoAOCValue() {
        String value = getText(lblArchiveNoAOCValue);
        extentLogger.logInfo(value);
        return value;
    }

    public String getSubmissionVersionAOCValue() {
        String value = getText(lblSubmissionVersionAOCValue);
        extentLogger.logInfo(value);
        return value;
    }

    public String getSubmissionJudgmentAOCValue() {
        String value = getText(lblSubmissionJudgmentAOCValue);
        extentLogger.logInfo(value);
        return value;
    }

    public String getGameCodeOnCardAOCValue() {
        String value = getText(lblGameCodeOnCardAOCValue);
        extentLogger.logInfo(value);
        return value;
    }

    public String getReleaseVersionOnCardAOCValue() {
        String value = getText(lblReleaseVersionOnCardAOCValue);
        extentLogger.logInfo(value);
        return value;
    }

    public String getSubmissionVersionOnCardAOCValue() {
        String value = getText(lblSubmissionVersionOnCardAOCValue);
        extentLogger.logInfo(value);
        return value;
    }

    public String getSubmissionJudgmentOnCardAOCValue() {
        String value = getText(lblSubmissionJudgmentOnCardAOCValue);
        extentLogger.logInfo(value);
        return value;
    }

    public String getViewSubmissionOnCardAOCValue() {
        String value = getText(lblViewSubmissionOnCardAOCValue);
        extentLogger.logInfo(value);
        return value;
    }

    public String getDisplayVersionValue() {
        String value = getText(lblVersionValue);
        extentLogger.logInfo(value);
        return value;
    }

    public String getReleaseTypeValue() {
        String value = getText(lblReleaseTypeValue);
        extentLogger.logInfo(value);
        return value;
    }

    public String getReleaseVersionValue() {
        String value = getText(lblReleaseVersionValue);
        extentLogger.logInfo(value);
        return value;
    }

    public String getSubmissionVersionValue() {
        String value = getText(lblSubmissionVersionValue);
        extentLogger.logInfo(value);
        return value;
    }

    public String getSubmissionJudgmentValue() {
        String value = getText(lblSubmissionJudgmentValue);
        extentLogger.logInfo(value);
        return value;
    }

    public String getMediaTypeValue() {
        String value = getText(lblMediaTypeValue);
        extentLogger.logInfo(value);
        return value;
    }

    public String getCompactedAppIncludeValue() {
        String value = getText(lblCompactedAppIncludeValue);
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckReportSubmissionOverview clickViewSubmissionOnCardAOC() {
        extentLogger.logInfo("");
        waitForAndClickElement(lnkViewSubmissionOnCardAOC);
        return getNewPage(LotcheckReportSubmissionOverview.class);
    }


    public String getLblChinaDataStatus() {
        String status = getValueAttributes(lblChinaDataStatus);
        extentLogger.logInfo("Get China Data Status");
        return status;
    }

    public String getNoticeISBN() {
        String value = getText(isbnNotice);
        extentLogger.logInfo("Notice of ISBN Approval / 网络游戏出版物号核发单 (for digital distribution) ");
        return value;
    }


    public String getLblNumberISBN() {
        String isbnNumber = getText(lblNumberISBN);
        extentLogger.logInfo("ISBN Number");
        return isbnNumber;
    }

    public String getLblNumberGAPP() {
        String gappNumber = getValueAttributes(lblNumberGAPP);
        extentLogger.logInfo("GAPP Number");
        return gappNumber;
    }

    public String getLblPublishingEntity() {
        String publisherName = getValueAttributes(lblPublishingEntity);
        extentLogger.logInfo("Publishing Entity");
        return publisherName;
    }

    public String getLblOperatingEntity() {
        String operatingName = getValueAttributes(lblOperatingEntity);
        extentLogger.logInfo("Operating Entity ");
        return operatingName;
    }

    public Boolean isCopyrightOutsideChina() {
        Boolean isOutsideChine = Boolean.parseBoolean(getValueAttributes(lblCopyrightOutsideChina));
        extentLogger.logInfo("Is the copyright owner of this title located outside China");
        return isOutsideChine;
    }

    public String getReplyToCopyright() {
        String replyCopyright = getText(lblReplyToCopyright);
        extentLogger.logInfo("Reply to Copyright Contract Registration");
        return replyCopyright;
    }

    public String getCopyrightContractNumber() {
        String contractNumber = getText(copyrightRegisterNumber);
        extentLogger.logInfo("Copyright Contract Registration Number");
        return contractNumber;
    }

    public LotcheckReportProductProductInformation downloadNoticeNumber() {
        extentLogger.logInfo("Download Notice of ISBN Approval");
        waitForAndClickElement(lnkIsbnNotice);
        return getNewPage(LotcheckReportProductProductInformation.class);
    }

    public LotcheckReportProductProductInformation downloadCopyrightRegistration() {
        extentLogger.logInfo("Download Copyright Registration Document");
        waitForAndClickElement(lblCopyrightRegisterNumber);
        return getNewPage(LotcheckReportProductProductInformation.class);
    }

    public LotcheckReportProductMessage clickMessageTab() {
        waitForAndClickElement(messageTab);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportProductMessage.class);
    }

    public String getChinaDataStatus() {
        String status = getText(chinaDataStatus);
        extentLogger.logInfo("Get China Data Status");
        return status;
    }

    public String getNumberISBN() {
        String isbnNumber = getText(numberISBN);
        extentLogger.logInfo("ISBN Number");
        return isbnNumber;
    }

    public String getNumberGAPP() {
        String gappNumber = getText(numberGAPP);
        extentLogger.logInfo("GAPP Number");
        return gappNumber;
    }

    public String getPublishingEntity() {
        String publisherName = getText(publishingEntity);
        extentLogger.logInfo("Publishing Entity");
        return publisherName;
    }

    public String getOperatingEntity() {
        String operatingName = getText(operatingEntity);
        extentLogger.logInfo("Operating Entity ");
        return operatingName;
    }

    public String getCopyrightOutsideChina() {
        String outsideChine = getText(copyrightOutsideChina);
        extentLogger.logInfo("Is the copyright owner of this title located outside China");
        return outsideChine;
    }

    public String getUrlDownloadFile() {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl;
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

    public LotcheckReportProductProductInformation deleteFile(String fileName) {
        String path = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator;
        File file = new File(System.getProperty("user.dir") + path + fileName);
        file.delete();
        extentLogger.logInfo(fileName);
        return this;
    }

    public boolean testPlanForExists(String gameCode, String type) {
        extentLogger.logInfo("");
        waitForElementVisible(By.xpath("//div[contains(text(),'" + gameCode + "')]/../../../..//a[contains(text(),'" + type + "')]"), WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return true;
    }

    public LotcheckReportSubmissionOverview viewSubmissionOverviewByGameCode(String gameCode) {
        waitForAndClickElement(By.xpath(String.format("//div[contains(text(),'%s')]/../../a[@class='go-to-product-level link-to-submission']", gameCode)));
        extentLogger.logInfo(gameCode);
        return getNewPage(LotcheckReportSubmissionOverview.class);
    }
}