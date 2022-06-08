package net.nintendo.automation.ui.models.lcms.lotcheck_issue_translation;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.utils.WaitUtils;
import org.openqa.selenium.*;

import java.util.List;

public class LotcheckIssueTranslationIssueDetails extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@data-testid='issues-details']");
    private final By lblPrivateTitleEN = By.xpath("//dt[contains(text(),'Private Title')]//..//dd[1]//li[@class='en']");
    private final By lblPrivateDescriptionEN = By.xpath("//dt[contains(text(),'Private Title')]//..//dd[2]//li[@class='en']");
    private final By lblTitle_JP = By.xpath("//*[@id='content']//dd[1]//li[2]");
    private final By txtTitleRequired_EN = By.xpath("//*[@id='content']//dd[1]//li[1]//span");
    private final By txtTitleRequired_JP = By.xpath("//*[@id='content']//dd[1]//li[2]//span");
    private final By txtDescription_EN = By.xpath("//*[@id='content']//dd[2]//li[1]");
    private final By txtDescription_JP = By.xpath("//*[@id='content']//dd[2]//li[2]");
    private final By txtDescriptionRequired_EN = By.xpath("//*[@id='content']//dd[2]//li[1]//span");
    private final By txtDescriptionRequired_JP = By.xpath("//*[@id='content']//dd[2]//li[2]//span");
    private final By btnEditPost = By.xpath("//div[@data-testid='issues-details-edit-button']//button");

    private final By txtEditPostTitle_EN = By.xpath("//textarea[@data-testid='edit-issue-privateTitle.en']");
    private final By txtEditPostTitle_JP = By.xpath("//textarea[@data-testid='edit-issue-privateTitle.ja']");
    private final By lblEditPostTitleRequired_EN = By.xpath("//*[@id='content']//dd[1]//li[1]/span");
    private final By lblEditPostTitleRequired_JP = By.xpath("//*[@id='content']//dd[1]//li[2]/span");
    private final By txtEditPostDescription_EN = By.xpath("//textarea[@data-testid='edit-issue-privateDescription.en']");
    private final By txtEditPostDescription_JP = By.xpath("//textarea[@data-testid='edit-issue-privateDescription.ja']");
    private final By lblEditPostDescriptionRequired_EN = By.xpath("//*[@id='content']//dd[2]//li[1]/span");
    private final By lblEditPostDescriptionRequired_JP = By.xpath("//*[@id='content']//dd[2]//li[1]/span");
    private final By btnTranslationComplete = By.xpath("//*[@id='content']//h2//button");
    private final By txtComment = By.xpath("//*[@data-testid='edit-task-comment']");
    private final By btnSaveComment = By.xpath("//*[@data-testid='edit-task-comment']//..//..//..//button[@data-testid='submit-button']");
    //private final By pnlAlert = By.xpath("//*[@role='alert']");
    private final By pnlAlert = By.xpath("//div[@class='Toastify__toast Toastify__toast--success']");
    private final By lblPublishedTitle_EN = By.xpath("//*[@id='content']//dd[3]//li[1]");
    private final By lblPublishedTitle_JP = By.xpath("//*[@id='content']//dd[3]//li[2]");

    private final By lblPublishedTitleRequired_EN = By.xpath("//*[@id='content']//dd[3]//li[1]/span");
    private final By lblPublishedTitleRequired_JP = By.xpath("//*[@id='content']//dd[3]//li[2]/span");
    private final By lblPublishedDescription_EN = By.xpath("//*[@id='content']//dd[4]//li[1]");
    private final By lblPublishedDescription_JP = By.xpath("//*[@id='content']//dd[4]//li[2]");
    private final By lblPublishedDescriptionRequired_EN = By.xpath("//*[@id='content']//dd[4]//li[1]//span");
    private final By lblPublishedDescriptionRequired_JP = By.xpath("//*[@id='content']//dd[4]//li[2]//span");
    private final By txtPublishedTitle_EN = By.xpath("//textarea[@data-testid='edit-issue-publicTitle.en']");
    private final By txtPublishedDescription_JP = By.xpath("//textarea[@data-testid='edit-issue-publicDescription.ja']");
    private final By btnSaveEdit = By.xpath("//*[@data-testid='submit-button']");
    private final By btnCloseWarningMessage = By.xpath("//button[@class='Toastify__close-button Toastify__close-button--error']");
    private final By txtPublishedTitle_JP = By.xpath("//textarea[@data-testid='edit-issue-publicTitle.ja']");

    private final By txtPublishedDescription_EN = By.xpath("//textarea[@data-testid='edit-issue-publicDescription.en']");
    private final By txtReasonForRequestEN = By.xpath("//textarea[@data-testid='edit-issue-reasonForWaiver.en']");
    private final By txtReasonForRequestJP = By.xpath("//textarea[@data-testid='edit-issue-reasonForWaiver.ja']");
    private final By txtResponseForApprovalOrRejectionEN = By.xpath("//textarea[@data-testid='edit-issue-responseToWaiver.en']");
    private final By txtResponseForApprovalOrRejectionJP = By.xpath("//textarea[@data-testid='edit-issue-responseToWaiver.ja']");
    private final By lblResponseForApprovalOrRejection_JP = By.xpath("//*[@id='content']//dd[6]//li[2]//span");
    private final By txtReasonForRequest_JP = By.xpath("//textarea[@data-testid='edit-issue-reasonForWaiver.ja']");
    private final By txtReasonForRequest_EN = By.xpath("//textarea[@data-testid='edit-issue-reasonForWaiver.en']");
    private final By btnCancelEdit = By.xpath("//button[@data-testid='set-planned-test-date-modal-cancel']");
    private final By lblResponseForApprovalOrRejection_EN = By.xpath("//*[@id='content']//dd[6]//li[1]//span");
    private final By lblReasonForRequestRequired_EN = By.xpath("//*[@id='content']//dd[5]//li[1]//span");
    private final By lblReasonForRequestRequired_JP = By.xpath("//*[@id='content']//dd[5]//li[2]//span");

    public LotcheckIssueTranslationIssueDetails(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public LotcheckLandingPage navLandingPage() {
        return getNewPage(LotcheckLandingPage.class);
    }

    public String getPrivateTitleEN() {
        String value = getText(lblPrivateTitleEN);
        extentLogger.logInfo(value);
        return value;
    }

    public String getLblPrivateDescriptionEN() {
        String value = getText(lblPrivateDescriptionEN);
        extentLogger.logInfo(value);
        return value;
    }

    public String getPrivateTitleJP() {
        String value = getText(lblTitle_JP);
        extentLogger.logInfo(value);
        return value;
    }

    public String getTitleRequired_EN() {
        String value = getText(txtTitleRequired_EN);
        extentLogger.logInfo(value);
        return value;
    }

    public String getTitleRequired_JP() {
        String value = getText(txtTitleRequired_JP);
        extentLogger.logInfo(value);
        return value;
    }

    public String getPrivateDescriptionJP() {
        WebElement e = driver.findElement(txtDescription_JP);
        String value = e.getText().trim();
        List<WebElement> children = e.findElements(By.xpath("./*"));
        for (WebElement child : children) {
            value = value.replaceFirst(child.getText(), "").trim();
        }
        extentLogger.logInfo("");
        return value;
    }

    public String getDescriptionTextRequired_EN() {
        String value = getText(txtDescriptionRequired_EN);
        extentLogger.logInfo(value);
        return value;
    }

    public String getDescriptionTextRequired_JP() {
        String value = getText(txtDescriptionRequired_JP);
        extentLogger.logInfo(value);
        return value;
    }

    public String getTitleTextRequiredColorHighlighted_EN() {
        String color = checkBackgroundColor(txtTitleRequired_EN);
        extentLogger.logInfo("Color text Required is: " + color);
        return color;
    }

    public String getTitleTextRequiredColorHighlighted_JP() {
        String color = checkBackgroundColor(txtTitleRequired_JP);
        extentLogger.logInfo("Color text Required is: " + color);
        return color;
    }

    public String getDescriptionTextRequiredColorHighlighted_EN() {
        String color = checkBackgroundColor(txtDescriptionRequired_EN);
        extentLogger.logInfo("Color text Required is: " + color);
        return color;
    }

    public String getDescriptionTextRequiredColorHighlighted_JP() {
        String color = checkBackgroundColor(txtDescriptionRequired_JP);
        extentLogger.logInfo("Color text Required is: " + color);
        return color;
    }

    public LotcheckIssueTranslationIssueDetails clickEditPost() {
        waitForElementClickable(btnEditPost);
        waitForAndClickElement(btnEditPost);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckIssueTranslationIssueDetails waitAlertDone() {
        if (!elementNotExists(pnlAlert)) {
            waitForElementNotVisible(pnlAlert);
        }
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckIssueTranslationIssueDetails clickTranslationCompleteButton() {
        WebElement element = driver.findElement(By.xpath("//*[@id='content']//h2//button"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", element);

        extentLogger.logInfo("");
        return this;

    }

    public LotcheckIssueTranslationIssueDetails enterCommentTranslationComplete(String comment) {
        typeText(txtComment, comment);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckIssueTranslationIssueDetails clickSaveComment() {
        waitForAndClickElement(btnSaveComment);
        extentLogger.logInfo("");
        waitForElementNotVisible(btnSaveComment);
        return this;
    }

    public boolean requiredPublishHighlightIsRemovedTitleEN() {
        extentLogger.logInfo("");
        try {
            WebElement element = driver.findElement(lblPublishedTitleRequired_EN);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean requiredPublishHighlightIsRemovedTitleJP() {
        extentLogger.logInfo("");
        try {
            WebElement element = driver.findElement(lblPublishedTitleRequired_JP);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean requiredPublishHighlightIsRemovedDescriptionEN() {
        extentLogger.logInfo("");
        try {
            WebElement element = driver.findElement(lblPublishedDescriptionRequired_EN);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String editPostTitle_EN() {
        String value = getText(txtEditPostTitle_EN);
        extentLogger.logInfo(value);
        return value;
    }

    public String editPostTitle_JP() {
        String value = getText(txtEditPostTitle_JP);
        extentLogger.logInfo(value);
        return value;
    }

    public String editPostTitleTextRequired_EN() {
        String value = getText(lblEditPostTitleRequired_EN);
        extentLogger.logInfo(value);
        return value;
    }

    public String editPostTitleTextRequired_JP() {
        String value = getText(lblEditPostTitleRequired_JP);
        extentLogger.logInfo(value);
        return value;
    }

    public String editPostDescription_EN() {
        String value = getText(txtEditPostDescription_EN);
        extentLogger.logInfo(value);
        return value;
    }

    public String editPostDescription_JP() {
        String value = getText(txtEditPostDescription_JP);
        extentLogger.logInfo(value);
        return value;
    }

    public String editPostDescriptionTextRequired_EN() {
        String value = getText(lblEditPostDescriptionRequired_EN);
        extentLogger.logInfo(value);
        return value;
    }

    public String editPostDescriptionTextRequired_JP() {
        String value = getText(lblEditPostDescriptionRequired_JP);
        extentLogger.logInfo(value);
        return value;
    }

    public String getPublishedDescription_JP() {
        WebElement e = driver.findElement(lblPublishedDescription_JP);
        String value = e.getText().trim();
        List<WebElement> children = e.findElements(By.xpath("./*"));
        for (WebElement child : children) {
            value = value.replaceFirst(child.getText(), "").trim();
        }
        return value;

    }

    public String publishedDescriptionTextRequired_EN() {
        String value = getText(lblPublishedDescriptionRequired_EN);
        extentLogger.logInfo(value);
        return value;
    }

    public String publishedDescriptionTextRequired_JP() {
        String value = getText(lblPublishedDescriptionRequired_JP);
        extentLogger.logInfo(value);
        return value;
    }

    public String getPublishedTitleTextRequiredColorHighlighted_EN() {
        String color = checkBackgroundColor(lblPublishedTitleRequired_EN);
        extentLogger.logInfo("Color text Required is: " + color);
        return color;
    }

    public String getPublishedTitleTextRequiredColorHighlighted_JP() {
        String color = checkBackgroundColor(lblPublishedTitleRequired_JP);
        extentLogger.logInfo("Color text Required is: " + color);
        return color;
    }

    public String getPublishedDescriptionTextRequiredColorHighlighted_EN() {
        String color = checkBackgroundColor(lblPublishedDescriptionRequired_EN);
        extentLogger.logInfo("Color text Required is: " + color);
        return color;
    }

    public String getPublishedDescriptionTextRequiredColorHighlighted_JP() {
        String color = checkBackgroundColor(lblPublishedDescriptionRequired_JP);
        extentLogger.logInfo("Color text Required is: " + color);
        return color;
    }

    public String getPublishedTitle_EN() {

        WebElement e = driver.findElement(lblPublishedTitle_EN);
        String value = e.getText().trim();
        List<WebElement> children = e.findElements(By.xpath("./*"));
        for (WebElement child : children) {
            value = value.replaceFirst(child.getText(), "").trim();
        }
        return value;

    }

    public String publishedTitle_JP() {
        String value = getText(lblPublishedTitle_JP);
        extentLogger.logInfo(value);
        return value;
    }

    public String publishedTitleTextRequired_EN() {
        String value = getText(lblPublishedTitleRequired_EN);
        extentLogger.logInfo(value);
        return value;
    }

    public String publishedTitleTextRequired_JP() {
        String value = getText(lblPublishedTitleRequired_JP);
        extentLogger.logInfo(value);
        return value;
    }


    public LotcheckIssueTranslationIssueDetails enterNewPublishedDescriptionJP(String comment) {
        clearTextValue(txtPublishedDescription_JP);
        typeText(txtPublishedDescription_JP, comment);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckIssueTranslationIssueDetails enterPublishedTitleEN(String comment) {
        clearTextValue(txtPublishedTitle_EN);
        WaitUtils.idle(3000);
        extentLogger.logInfo("");
        typeText(txtPublishedTitle_EN, comment);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckIssueTranslationIssueDetails enterPublishedDescriptionJP(String comment) {
        clearTextValue(txtPublishedDescription_JP);
        WaitUtils.idle(3000);
        extentLogger.logInfo("");
        typeText(txtPublishedDescription_JP, comment);
        extentLogger.logInfo("");
        return this;

    }

    public LotcheckIssueTranslationIssueDetails clickSaveEditButton() {
        waitForAndClickElement(btnSaveEdit);
        extentLogger.logInfo("");
        waitForElementNotVisible(btnSaveEdit);
        return this;

    }

    public boolean requiredHighlightIsRemovedTitleEN() {
        extentLogger.logInfo("");
        try {
            WebElement element = driver.findElement(txtTitleRequired_EN);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean requiredHighlightIsRemovedTitleJP() {
        extentLogger.logInfo("");
        try {
            WebElement element = driver.findElement(txtTitleRequired_JP);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean requiredHighlightIsRemovedDescriptionEN() {
        extentLogger.logInfo("");
        try {
            WebElement element = driver.findElement(txtDescriptionRequired_EN);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean requiredHighlightIsRemovedDescriptionJP() {
        extentLogger.logInfo("");
        try {
            WebElement element = driver.findElement(txtDescriptionRequired_JP);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getPrivateDescriptionEN() {
        WebElement e = driver.findElement(txtDescription_EN);
        String value = e.getText().trim();
        List<WebElement> children = e.findElements(By.xpath("./*"));
        for (WebElement child : children) {
            value = value.replaceFirst(child.getText(), "").trim();
        }

        return value;
    }

    public Boolean displaySuccessMessage() {
        extentLogger.logInfo("");
        try {
            WebElement mail = driver.findElement(pnlAlert);
            return mail.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public LotcheckIssueTranslationIssueDetails closeWarningMessage() {
        extentLogger.logInfo("close warning Message ");
        if (!elementNotExists(btnCloseWarningMessage)) {
            waitForElementVisible(btnCloseWarningMessage, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
            waitForAndClickElement(btnCloseWarningMessage);
        }
        return this;
    }

    public LotcheckIssueTranslationIssueDetails enterPublishedTitleJP(String comment) {
        clearTextValue(txtPublishedTitle_JP);
        WaitUtils.idle(3000);
        extentLogger.logInfo("");
        typeText(txtPublishedTitle_JP, comment);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckIssueTranslationIssueDetails enterPublishedDescriptionEN(String comment) {
        clearTextValue(txtPublishedDescription_EN);
        WaitUtils.idle(3000);
        extentLogger.logInfo("");
        typeText(txtPublishedDescription_EN, comment);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckIssueTranslationIssueDetails enterReasonForRequestEN(String comment) {
        clearTextValue(txtReasonForRequestEN);
        WaitUtils.idle(3000);
        extentLogger.logInfo("");
        typeText(txtReasonForRequestEN, comment);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckIssueTranslationIssueDetails enterReasonForRequestJP(String comment) {
        clearTextValue(txtReasonForRequestJP);
        WaitUtils.idle(3000);
        extentLogger.logInfo("");
        typeText(txtReasonForRequestJP, comment);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckIssueTranslationIssueDetails enterResponseForApprovalOrRejectionEN(String comment) {
        clearTextValue(txtResponseForApprovalOrRejectionEN);
        WaitUtils.idle(3000);
        extentLogger.logInfo("");
        typeText(txtResponseForApprovalOrRejectionEN, comment);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckIssueTranslationIssueDetails enterResponseForApprovalOrRejectionJP(String comment) {
        clearTextValue(txtResponseForApprovalOrRejectionJP);
        WaitUtils.idle(3000);
        extentLogger.logInfo("");
        typeText(txtResponseForApprovalOrRejectionJP, comment);
        extentLogger.logInfo("");
        return this;
    }

    public boolean requiredPublishHighlightIsDisplayedResponseForApprovalOrRejectionEN() {
        extentLogger.logInfo("");
        try {
            WebElement element = driver.findElement(lblResponseForApprovalOrRejection_EN);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean requiredPublishHighlightIsDisplayedResponseForApprovalOrRejectionJP() {
        extentLogger.logInfo("");
        try {
            WebElement element = driver.findElement(lblResponseForApprovalOrRejection_JP);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean requiredPublishHighlightIsRemovedDescriptionJP() {
        extentLogger.logInfo("");
        try {
            WebElement element = driver.findElement(lblPublishedDescriptionRequired_JP);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean requiredPublishHighlightIsDisplayedReasonForRequestEN() {
        extentLogger.logInfo("");
        try {
            WebElement element = driver.findElement(lblReasonForRequestRequired_EN);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean requiredPublishHighlightIsDisplayedReasonForRequestJP() {
        extentLogger.logInfo("");
        try {
            WebElement element = driver.findElement(lblReasonForRequestRequired_JP);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isPublishTitleENInputVisible() {
        Boolean isVisible = true;
        if (elementNotExists(txtPublishedTitle_EN)) {
            isVisible = false;
        }
        extentLogger.logInfo(isVisible.toString());
        return isVisible;
    }

    public boolean isPublishTitleJPInputVisible() {
        Boolean isVisible = true;
        if (elementNotExists(txtPublishedTitle_JP)) {
            isVisible = false;
        }
        extentLogger.logInfo(isVisible.toString());
        return isVisible;
    }

    public boolean isPublishDescriptionENInputVisible() {
        Boolean isVisible = true;
        if (elementNotExists(txtPublishedDescription_EN)) {
            isVisible = false;
        }
        extentLogger.logInfo(isVisible.toString());
        return isVisible;
    }

    public boolean isPublishDescriptionJPInputVisible() {
        Boolean isVisible = true;
        if (elementNotExists(txtPublishedDescription_JP)) {
            isVisible = false;
        }
        extentLogger.logInfo(isVisible.toString());
        return isVisible;
    }

    public boolean isReasonForRequestENInputVisible() {
        Boolean isVisible = true;
        if (elementNotExists(txtReasonForRequest_EN)) {
            isVisible = false;
        }
        extentLogger.logInfo(isVisible.toString());
        return isVisible;
    }

    public boolean isReasonForRequestJPInputVisible() {
        Boolean isVisible = true;
        if (elementNotExists(txtReasonForRequest_JP)) {
            isVisible = false;
        }
        extentLogger.logInfo(isVisible.toString());
        return isVisible;
    }

    public String publishedDescription_EN() {
        WebElement e = driver.findElement(lblPublishedDescription_EN);
        String value = e.getText().trim();
        List<WebElement> children = e.findElements(By.xpath("./*"));
        for (WebElement child : children) {
            value = value.replaceFirst(child.getText(), "").trim();
        }
        return value;
    }

    public String publishedDescriptionENAfterEdit() {
        String value = getText(lblPublishedDescription_EN).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public String publishedDescriptionJPAfterEdit() {
        String value = getText(lblPublishedDescription_JP).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckIssueTranslationIssueDetails onlyClickSaveEditButton() {
        waitForAndClickElement(btnSaveEdit);
        extentLogger.logInfo("");
        return this;

    }

    public boolean isSaveButtonEnabledAfterClickSaveEditButton() {
        Boolean isEnabled = driver.findElement(btnSaveEdit).isEnabled();
        extentLogger.logInfo(isEnabled.toString());
        return isEnabled;
    }

    public boolean isCancelButtonEnabledAfterClickSaveEditButton() {
        Boolean isEnabled = driver.findElement(btnCancelEdit).isEnabled();
        extentLogger.logInfo(isEnabled.toString());
        return isEnabled;
    }

    public boolean isSaveEditButtonVisible() {
        Boolean isVisible = true;
        if (elementNotExists(btnSaveEdit)) {
            isVisible = false;
        }
        extentLogger.logInfo(isVisible.toString());
        return isVisible;
    }

    public boolean isCancelEditButtonVisible() {
        Boolean isVisible = true;
        if (elementNotExists(btnCancelEdit)) {
            isVisible = false;
        }
        extentLogger.logInfo(isVisible.toString());
        return isVisible;
    }

    public boolean isEditButtonVisible() {
        Boolean isVisible = true;
        if (elementNotExists(btnEditPost)) {
            isVisible = false;
        }
        extentLogger.logInfo(isVisible.toString());
        return isVisible;
    }

    public boolean isEditButtonEnable() {
        Boolean isEnable = driver.findElement(btnEditPost).isEnabled();
        extentLogger.logInfo(isEnable.toString());
        return isEnable;
    }

    public String getPublishedDescriptionENValue() {
        String value = getText(txtPublishedDescription_EN).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public String getPublishedDescriptionJPValue() {
        String value = getText(txtPublishedDescription_JP).trim();
        extentLogger.logInfo(value);
        return value;
    }


}
