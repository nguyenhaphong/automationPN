package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthLogoutPage;
import net.nintendo.automation.ui.models.lcms.LotcheckEditAccountPreferences;
import net.nintendo.automation.utils.WaitUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;

public class LotcheckReportSubmissionLotcheckIssuePartnerThread extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//button[contains(text(),'Reply to Partner')]");
    private final By navUser = By.xpath("//div[contains(@class,'nav-user')]//span");
    private final By lnkSignOut = By.xpath("//i[@class='fas fa-sign-out-alt mr-1']");
    private final By lblAutoPost = By.xpath("//*[@data-testid='message-thread-container']//div[@class='message-container'][4]//div[@class='message-body']");
    private final By lnkLotcheckThreadTab = By.xpath("//a[contains(@href,'partner')]//..//..//li[3]");
    private final By lkLotcheckThreadTab = By.xpath("//a[contains(@href,'partner')]//..//..//li[3]//a");
    private final By btnAttachFile = By.xpath("//*[@class='text-center d-flex justify-content-center file-picker-title']");
    //private final By btnEditPost = By.xpath("//button//i[@class='far fa-edit']");
    private final By btnEditPost = By.xpath("//*[@class='far fa-edit']");
    private final By btnRemoveAttachFile = By.xpath("//*[@class='remove-upload']");
    private final By btnSaveEditPost = By.xpath("//*[@data-testid='set-planned-test-date-modal-form']//button[@data-testid='submit-button']");
    private final By pnlAlert = By.xpath("//*[@role='alert']");
    private final By ddlPartnerThreadStatus = By.xpath("//label[@class='col-form-labe thread-status-selectl']/..//select[@class='form-control']");
    private final By ddlLicType = By.xpath("//select[@id='lci']");
    private final By btnReplyToPartner = By.xpath("//button[@data-testid='submit-button']");
    private final By lnkIssueDetailTab = By.xpath("//a[contains(@href,'/details')]");
    private final By lblContentWarningMessage = By.xpath("//div[@data-testid='alert-warning']");
    private final By btnMyAccountInUserMenu = By.xpath("//*[@id='hyrule-nav']//button[@data-testid='add-to-schedule-group-button']");
    private final By ifrMessage = By.xpath("//div[@class='message-container'][4]//iframe");
    private final By loadingIcon = By.xpath("//div[@data-testid=\"spinner-loading\"]");
    private final By btnIcon = By.xpath("//div[@data-testid='lotcheck-issues-container']/div/div/button[@id='dropdownMenuLink']");
    private final By btnTwoLanguageDisplay = By.xpath("//button[@data-testid='display-translations-button']");
    private final By imageIcon = By.xpath("//a[@class='cke_button cke_button__image cke_button_off']//span[@class='cke_button_icon cke_button__image_icon']");
    private final By txtUrlOnImagePropertiesDialog = By.xpath("//input[@class='cke_dialog_ui_input_text']");
    private final By ddAlignment = By.xpath("//select[@class='cke_dialog_ui_input_select']");
    private final By btnOkOnImagePropertiesDialog = By.xpath("//a[@title='OK']");
    private final By bodyContent = By.xpath("//body[@class='cke_editable cke_editable_themed cke_contents_ltr cke_show_borders']");

    public LotcheckReportSubmissionLotcheckIssuePartnerThread(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public LotcheckReportSubmissionLotcheckIssueDetails gotoLCIssueDetail() {
        return getNewPage(LotcheckReportSubmissionLotcheckIssueDetails.class);
    }

    public LotcheckReportSubmissionLotcheckIssueDetails nav() {
        return getNewPage(LotcheckReportSubmissionLotcheckIssueDetails.class);
    }


    public boolean brokenImageIsDisplayed(String image) {
        driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='message-container'][4]//iframe")));
        WebElement imageElement = driver.findElement(By.xpath(String.format("//img[contains(@src,'%s')]", image)));
        Boolean value = imageElement.getAttribute("naturalWidth").equals("0");
        driver.switchTo().defaultContent();
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public String getContentTextOfMessage(String image) {
        waitForElementVisible(By.xpath("//div[@class='message-container'][4]//iframe"), WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='message-container'][4]//iframe")));
        WaitUtils.idle(2000);
        String value = getText(By.xpath(String.format("//img[contains(@src,'%s')]/ancestor::body[@class='cke_editable cke_editable_themed cke_contents_ltr cke_show_borders']", image))).split("\n")[0].trim();
        driver.switchTo().defaultContent();
        extentLogger.logInfo(value);
        return value;
    }

    public HyruleAuthLogoutPage logOut() {
        openLinkInSameTab(navUser);
        extentLogger.logInfo("");
        waitForAndClickElement(lnkSignOut);
        extentLogger.logInfo("");
        return getNewPage(HyruleAuthLogoutPage.class);
    }

    public LotcheckReportSubmissionLotcheckIssuePartnerThread getTextAutoPost() {
        String value = getText(lblAutoPost);
        extentLogger.logInfo(value);
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssuePartnerThread waitAlertDone() {
        waitForElementNotVisible(pnlAlert);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssuePartnerThread clickRemoveFile() {
        waitForAndClickElement(btnRemoveAttachFile);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssuePartnerThread clickSaveEditPost() {
        waitForAndClickElement(btnSaveEditPost);
        extentLogger.logInfo("");
        waitForElementNotVisible(btnSaveEditPost);
        return this;
    }

    public LotcheckEditAccountPreferences clickMyAccount() {
        openLinkInSameTab(navUser);
        extentLogger.logInfo("");
        waitForAndClickElement(btnMyAccountInUserMenu);
        extentLogger.logInfo("");
        return getNewPage(LotcheckEditAccountPreferences.class);
    }

    public LotcheckReportSubmissionLotcheckIssuePartnerThread selectFileInPC(String fileName) {
        extentLogger.logInfo("");
        String path = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator;
        try {
            Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\Autoit\\FileUpload2.exe" + " " + System.getProperty("user.dir") + path + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssuePartnerThread clickAttachFile() {
        waitForAndClickElement(btnAttachFile);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssuePartnerThread clickReplyToPartner() {
        waitForAndClickElement(btnReplyToPartner);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssueThread clickLotcheckThreadTab() {
        WebElement element = driver.findElement(By.xpath("//a[contains(@href,'partner')]//..//..//li[3]//a"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", element);
        extentLogger.logInfo("");
        waitDocumentReady();
        return getNewPage(LotcheckReportSubmissionLotcheckIssueThread.class);

    }

    public LotcheckReportSubmissionLotcheckIssuePartnerThread selectPartnerThreadStatus(String status) {
        extentLogger.logInfo(status);
        selectValueOnDropDown(ddlPartnerThreadStatus, status);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssuePartnerThread selectReplyToPartner() {
        extentLogger.logInfo("");
        waitForElementClickable(btnReplyToPartner);
        waitForAndClickElement(btnReplyToPartner);
        waitDocumentReady();
        return getNewPage(LotcheckReportSubmissionLotcheckIssuePartnerThread.class);
    }

    public LotcheckReportSubmissionLotcheckIssueDetails selectIssueDetailTab() {
        waitForAndClickElement(lnkIssueDetailTab);
        extentLogger.logInfo("");
        waitDocumentReady();
        return getNewPage(LotcheckReportSubmissionLotcheckIssueDetails.class);
    }

    public String getContentWarningMessage() {
        waitForElementVisible(lblContentWarningMessage);
        String value = getText(lblContentWarningMessage);
        extentLogger.logInfo(value);
        return value;
    }

    public boolean checkLICTypeDisable() {
        boolean value = true;
        boolean elementDisable = getElementAttribute(ddlLicType, "disabled");
        if (elementDisable != true) {
            return false;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public boolean checkPartnerThreadStatusDisable() {
        boolean value = true;
        boolean elementDisable = getElementAttribute(ddlPartnerThreadStatus, "disabled");
        if (elementDisable != true) {
            return false;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public String getFileName() {
        waitForElementVisible(By.xpath("//div[@class='message-container'][4]//iframe"), WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        WebElement frm = driver.findElement(By.xpath("//div[@class='message-container'][4]//iframe"));
        driver.switchTo().frame(frm);
        WaitUtils.idle(2000);
        WebElement text = driver.findElement(By.tagName("body"));
        String value = text.getText().split("\n")[2].trim();
        driver.switchTo().defaultContent();
        extentLogger.logInfo(value);
        return value;

    }

    public String getMessage() {
        waitForElementVisible(By.xpath("//div[@class='message-container'][4]//iframe"), WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        WebElement frm = driver.findElement(By.xpath("//div[@class='message-container'][4]//iframe"));
        driver.switchTo().frame(frm);
        WaitUtils.idle(2000);
        WebElement text = driver.findElement(By.tagName("body"));
        String value = text.getText().split("\n")[0].trim();
        driver.switchTo().defaultContent();
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckReportSubmissionLotcheckIssuePartnerThread clickEditPost() {
        waitForAndClickElement(btnEditPost);
        extentLogger.logInfo("Click edit post");
        return this;
    }

    public boolean editPostButtonNotVisible() {
        extentLogger.logInfo("");
        try {
            WebElement element = driver.findElement(btnEditPost);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public LotcheckReportSubmissionLotcheckIssuePartnerThread refreshPageSLCMS() {
        extentLogger.logInfo("refresh page");
        driver.navigate().refresh();
        waitForElementVisible(loadingIcon, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        waitForElementNotVisible(loadingIcon, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        //waitForElementVisible(summaryResult, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        return this;
    }

    public boolean imageIsDisplayed(String image) {
        driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='message-container'][3]//iframe")));
        WebElement imageElement = driver.findElement(By.xpath(String.format("//img[contains(@src,'%s')]", image)));
        Boolean value = imageElement.getCssValue("float").equals("left");
        driver.switchTo().defaultContent();
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public String getWidthImage(String image) {
        driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='message-container'][3]//iframe")));
        WebElement imageElement = driver.findElement(By.xpath(String.format("//img[contains(@src,'%s')]", image)));
        String value = imageElement.getCssValue("Width");
        driver.switchTo().defaultContent();
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public String getHeightImage(String image) {
        driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='message-container'][3]//iframe")));
        WebElement imageElement = driver.findElement(By.xpath(String.format("//img[contains(@src,'%s')]", image)));
        String value = imageElement.getCssValue("Height");
        driver.switchTo().defaultContent();
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public LotcheckReportSubmissionLotcheckIssuePartnerThread addImage(String link) {
        waitForAndClickElement(imageIcon);
        waitForAndClickElement(txtUrlOnImagePropertiesDialog);
        typeText(txtUrlOnImagePropertiesDialog, link);
        typeTab(txtUrlOnImagePropertiesDialog);
        extentLogger.logInfo(link);
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssuePartnerThread selectAlignment(String alignment) {
        selectValueOnDropDown(ddAlignment, alignment);
        extentLogger.logInfo(alignment);
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssuePartnerThread clickOKButton() {
        waitForAndClickElement(btnOkOnImagePropertiesDialog);
        extentLogger.logInfo("");
        return this;
    }

    public boolean imageUploadIsDisplayed(String image) {
        driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='message-container'][4]//iframe")));
        WebElement imageElement = driver.findElement(By.xpath(String.format("//img[contains(@src,'%s')]", image)));
        Boolean value = imageElement.getCssValue("float").equals("right");
        driver.switchTo().defaultContent();
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public String getWidthImageUpload(String image) {
        driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='message-container'][4]//iframe")));
        WebElement imageElement = driver.findElement(By.xpath(String.format("//img[contains(@src,'%s')]", image)));
        String value = imageElement.getCssValue("Width");
        driver.switchTo().defaultContent();
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public String getHeightImageUpload(String image) {
        driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='message-container'][4]//iframe")));
        WebElement imageElement = driver.findElement(By.xpath(String.format("//img[contains(@src,'%s')]", image)));
        String value = imageElement.getCssValue("Height");
        driver.switchTo().defaultContent();
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public LotcheckReportSubmissionLotcheckIssuePartnerThread clickTwoLanguageDisplayButton() {
        waitForAndClickElement(btnIcon);
        waitForAndClickElement(btnTwoLanguageDisplay);
        extentLogger.logInfo("");
        return this;
    }

    public boolean isCommentDisplayed(String index, String comment) {
        waitDocumentReady();
        driver.switchTo().frame(driver.findElement(By.xpath(String.format("//div[@class='message-container'][%s]//iframe", index))));
        if (getText(bodyContent).equals(comment)) {
            return true;
        } else {
            return false;
        }
    }

}
