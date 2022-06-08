package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.utils.WaitUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;

public class LotcheckReportSubmissionLotcheckIssueThread extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//button[contains(text(),'Reply to Lotcheck')]");
    //private static final By uniqueElement = By.xpath("//*[contains(text(),'Thread Status')]");
    private final By lblTitle = By.xpath("//div[@class='issue-thread-container']//h3");

    private final By ddlLotcheckThreadStatus = By.id("dropdown");
    private final By btnAttachFile = By.xpath("//*[@class='text-center d-flex justify-content-center file-picker-title']");
    private final By btnReplyToPartner = By.xpath("//*[@data-testid='submit-button']");
    private final By lblAttachFile = By.xpath("//*[@class='image-attached-block']//img");
    private final By lblAlert = By.xpath("//*[@role='alert']");
    //private final By btnEditPost = By.xpath("//button//i[@class='far fa-edit']");
    private final By btnEditPost = By.xpath("//*[@class='far fa-edit']");
    private final By lnkRemoveAttach = By.xpath("//*[@class='remove-upload']");

    private final By btnSaveEditPost = By.xpath("//*[@data-testid='set-planned-test-date-modal-form']//button[@data-testid='submit-button']");
    private final By lblAutoPost = By.xpath("//*[@data-testid='message-thread-container']//div[@class='message-container'][5]//div[@class='message-body']");
    private final By lotcheckThreadStatus = By.id("dropdown");

    public LotcheckReportSubmissionLotcheckIssueThread(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public String getTitleLotcheckThread() {
        String value = getText(lblTitle);
        extentLogger.logInfo(value);
        return value;
    }

    public boolean fileIsDisplay() {
        extentLogger.logInfo("");
        try {
            WebElement mail = driver.findElement(lblAttachFile);
            return mail.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public LotcheckReportSubmissionLotcheckIssueThread getTextAutoPost() {
        String value = getText(lblAutoPost);
        extentLogger.logInfo(value);
        return this;
    }

    public String getMessage() {
        driver.switchTo().frame(driver.findElement(By.xpath("//*[@id=\"cke_928_contents\"]/iframe")));
        String message = getText(By.xpath("//*[@data-cke-title='Rich Text Editor, editor20']//..//..//body"));
        driver.switchTo().defaultContent();
        extentLogger.logInfo(message);
        return message;
    }

    public String getFile() {
        String file = getText(lblAttachFile);
        extentLogger.logInfo(file);
        return file;
    }

    public LotcheckReportSubmissionLotcheckIssueThread enterMessage(String value) {
        driver.switchTo().frame(driver.findElement(By.xpath("//*[@class='categories']//div[3][@data-testid='message-editor']//iframe")));
        typeText(By.xpath("//body[@contenteditable='true']"), value);
        driver.switchTo().defaultContent();
        extentLogger.logInfo(value);
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssueThread clickAttachFile() {
        waitForAndClickElement(btnAttachFile);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssueThread selectFileInPC(String fileName) {
        extentLogger.logInfo("");
        String path = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator;
        try {
            Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\Autoit\\FileUpload2.exe" + " " + System.getProperty("user.dir") + path + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssueThread clickReplyToPartner() {
        waitForAndClickElement(btnReplyToPartner);
        extentLogger.logInfo("");
        waitForElementNotVisible(btnReplyToPartner);
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssueThread waitAlertDone() {
        waitForElementNotVisible(lblAlert);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssueThread clickEditPost() {
        waitForAndClickElement(btnEditPost);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssueThread clickRemoveFile() {
        waitForAndClickElement(lnkRemoveAttach);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssueThread clickSaveEditPost() {
        waitForAndClickElement(btnSaveEditPost);
        extentLogger.logInfo("");
        waitForElementNotVisible(btnSaveEditPost);
        return this;
    }

    public String changeLotcheckThreadStatusToClose(String value) {
        WebElement element = driver.findElement(By.id("dropdown"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", element);
        selectValueOnDropDown(lotcheckThreadStatus, value);
        extentLogger.logInfo(value);
        return value;

    }

    public boolean checkLotcheckThreadStatusDisable() {
        boolean value = true;
        boolean elementDisable = getElementAttribute(ddlLotcheckThreadStatus, "disabled");
        if (elementDisable != true) {
            return false;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public String getMessageAfterChangeLotcheckStatus() {
        waitForElementVisible(By.xpath("//div[@class='message-container'][5]//iframe"), WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        WebElement frm = driver.findElement(By.xpath("//div[@class='message-container'][5]//iframe"));
        driver.switchTo().frame(frm);
        WaitUtils.idle(2000);
        WebElement text = driver.findElement(By.tagName("body"));
        String value = text.getText().split("\n")[0].trim();
        driver.switchTo().defaultContent();
        extentLogger.logInfo(value);
        return value;
    }

    public String getFileNameAfterChangeLotcheckStatus() {
        waitForElementVisible(By.xpath("//div[@class='message-container'][5]//iframe"), WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        WebElement frm = driver.findElement(By.xpath("//div[@class='message-container'][5]//iframe"));
        driver.switchTo().frame(frm);
        WaitUtils.idle(2000);
        WebElement text = driver.findElement(By.tagName("body"));
        String value = text.getText().split("\n")[2].trim();
        driver.switchTo().defaultContent();
        extentLogger.logInfo(value);
        return value;
    }

    public String getFileName() {
        waitForElementVisible(By.xpath("//div[@class='message-container'][4]//iframe"), WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='message-container'][4]//iframe")));
        WaitUtils.idle(2000);
        String value = getText(By.xpath(String.format("//*[@data-cke-title='Rich Text Editor, editor4']//..//..//body")));
        driver.switchTo().defaultContent();
        extentLogger.logInfo(value);
        return value;
    }
}
