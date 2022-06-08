package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;

public class LotcheckReportSubmissionRequiredApprovalPartnerThread extends CommonUIComponent {

    private static final By uniqueElement = By.xpath("//button[contains(text(),'Reply to Partner')]");
    private static final String pageTitle = "Partner Thread Required Approvals Submission: LCR - LCMS";

    private final By btnAttachFile = By.xpath("//*[@class='text-center d-flex justify-content-center file-picker-title']");
    private final By btnReplyToPartner = By.xpath("//*[@data-testid='submit-button']");
    private final By lblAttachFile = By.xpath("//*[@class='text-break']");
    private final By txtContent = By.xpath("//body[@contenteditable='true']");
    private final By frmDescription = By.xpath("//*[@id=\"cke_3_contents\"]/iframe");
    private final By frmMessage = By.xpath("//*[@id=\"cke_172_contents\"]/iframe");

    private final By frmDescription2 = By.xpath("//*[@id=\"cke_7_contents\"]/iframe");
    private final By attachFile = By.xpath("//*[@class='text-break']");
    private final By attachedFile = By.xpath("//*[@class='document-link']");
    private final By lblMessageInput = By.xpath("//*[@data-cke-title='Rich Text Editor, editor6']//..//..//body");
    private final By lblMessage = By.xpath("//*[@data-cke-title='Rich Text Editor, editor3']//..//..//body");
    private final By imgFile = By.xpath("//img[@alt='Attached file']");
    private final By lnkEditPostEN = By.xpath("//*[@class='clearfix']//ul//li[1]//i");
    private final By errorMessage = By.xpath("//*[@class='text-danger']");
    private final By btnCloseEditPost = By.xpath("//button[@class='close']//span");

    public LotcheckReportSubmissionRequiredApprovalPartnerThread(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public LotcheckLandingPage navLanding() {
        return getNewPage(LotcheckLandingPage.class);
    }

    public boolean displayImgFile() {
        extentLogger.logInfo("");
        try {
            WebElement mail = driver.findElement(imgFile);
            return mail.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String enterDescription(String value) {
        driver.switchTo().frame(driver.findElement(frmDescription));
        typeText(txtContent, value);
        driver.switchTo().defaultContent();
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckReportSubmissionRequiredApprovalPartnerThread enterDescription2nd(String value) {
        driver.switchTo().frame(driver.findElement(frmDescription2));
        typeText(txtContent, value);
        driver.switchTo().defaultContent();
        extentLogger.logInfo(value);
        return this;
    }

    public LotcheckReportSubmissionRequiredApprovalPartnerThread selectFileInPC(String fileName) {
        extentLogger.logInfo("");
        String path = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator;
        try {
            Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\Autoit\\FileUpload2.exe" + " " + System.getProperty("user.dir") + path + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public LotcheckReportSubmissionRequiredApprovalPartnerThread clickAttachFile() {
        waitForAndClickElement(btnAttachFile);
        extentLogger.logInfo("");
        return this;
    }

    public String getFileBeforeReply() {
        String file = getText(attachFile);
        extentLogger.logInfo(file);
        return file;
    }

    public String getFileAfterReply() {
        String file = getText(attachedFile);
        extentLogger.logInfo(file);
        return file;
    }

    public LotcheckReportSubmissionRequiredApprovalPartnerThread clickReplyToPartner() {
        waitForAndClickElement(btnReplyToPartner);
        extentLogger.logInfo("");
        return this;
    }

    public String getFile() {
        String file = getText(lblAttachFile);
        extentLogger.logInfo(file);
        return file;
    }

    public String getMessage() {
        driver.switchTo().frame(driver.findElement(frmMessage));
        String message = getText(lblMessageInput);
        driver.switchTo().defaultContent();
        extentLogger.logInfo(message);
        return message;
    }

    public LotcheckReportSubmissionRequiredApprovalPartnerThread clickEditPostEN() {
        WebElement element = driver.findElement(lnkEditPostEN);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", element);
        extentLogger.logInfo("");
        return this;

    }

    public LotcheckReportSubmissionRequiredApprovalPartnerThread enterMessageField4001Character(String value) {
        driver.switchTo().frame(driver.findElement(By.xpath("//label[@for='messageEn']//..//iframe")));
        typeText(By.xpath("//body[@contenteditable='true']"), value);
        driver.switchTo().defaultContent();
        extentLogger.logInfo(value);
        return this;
    }

    public String getTextErrorMessage() {
        String message = getText(errorMessage);
        extentLogger.logInfo(message);
        return message;
    }

    public LotcheckReportSubmissionRequiredApprovalPartnerThread clickCloseEditPost() {
        waitForAndClickElement(btnCloseEditPost);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionRequiredApprovalPartnerThread refreshPage() {
        extentLogger.logInfo("");
        driver.navigate().refresh();
        return this;
    }
}
