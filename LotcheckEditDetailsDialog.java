package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LotcheckEditDetailsDialog extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@data-testid='edit-issues-form-container']");
    private final By txtTitleEN = By.xpath("//textarea[@data-testid='edit-issue-privateTitle.en']");
    private final By txtDescriptionEN = By.xpath("//textarea[@data-testid='edit-issue-privateDescription.en']");
    private final By btnSave = By.xpath("//button[@data-testid='submit-button']");
    private final By btnClose = By.xpath("//button[@class='btn btn-secondary mr-auto']");
    private final By cbLCThreadTranslationNeed = By.xpath("//div/div[contains(@class,'lc-checkbox')][1]//input");
    private final By cbPartnerThreadTranslationNeed = By.xpath("//div/div[contains(@class,'lc-checkbox')][2]//input");

   private final By txtTitleJA = By.xpath("//textarea[@data-testid='edit-issue-privateTitle.ja']");
    private final By txtDescriptionJA = By.xpath("//textarea[@data-testid='edit-issue-privateDescription.ja']");

    public LotcheckEditDetailsDialog(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public LotcheckEditDetailsDialog editTitleEN(String title) {
        clearTextValue(txtTitleEN);
        typeText(txtTitleEN, title);
        typeTab(txtTitleEN);
        extentLogger.logInfo(title);
        return this;
    }

    public LotcheckEditDetailsDialog editDescriptionEN(String description) {
        waitForAndClickElement(txtTitleEN);
        clearTextValue(txtDescriptionEN);
        typeText(txtDescriptionEN, description);
        typeTab(txtDescriptionEN);
        extentLogger.logInfo(description);
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssueDetails clickSaveButton() {
        waitForAndClickElement(btnSave);
        waitForElementNotVisible(btnClose, WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionLotcheckIssueDetails.class);
    }
    public LotcheckEditDetailsDialog selectLCThreadTranslationNeededCheckbox(String checked){
        selectCheckbox(cbLCThreadTranslationNeed,checked);
        extentLogger.logInfo(checked);
        return this;
    }
    public LotcheckEditDetailsDialog selectPartnerThreadTranslationNeededCheckbox(String checked){
        selectCheckbox(cbPartnerThreadTranslationNeed,checked);
        extentLogger.logInfo(checked);
        return this;
    }

    public LotcheckEditDetailsDialog editTitleJA(String title) {
        clearTextValue(txtTitleJA);
        typeText(txtTitleJA, title);
        typeTab(txtTitleJA);
        extentLogger.logInfo(title);
        return this;
    }

    public LotcheckEditDetailsDialog editDescriptionJA(String description) {
        waitForAndClickElement(txtTitleJA);
        clearTextValue(txtDescriptionJA);
        typeText(txtDescriptionJA, description);
        typeTab(txtDescriptionJA);
        extentLogger.logInfo(description);
        return this;
    }



}
