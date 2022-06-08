package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LotcheckPublishToPublicDialog extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//select[@name='lciType']");
    private final By ddlLCIType = By.xpath("//select[@name='lciType']");
    private final By btnPublish = By.xpath("//button[@data-testid=\"submit-button\"]");
    private final By btnCancel = By.xpath("//button[@class='btn btn-secondary mr-auto']");
    private final By cbPartnerThreadTranslationNeed = By.xpath("//input[@name='translationNeeded']");

    public LotcheckPublishToPublicDialog(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public LotcheckPublishToPublicDialog selectLciType(String value) {
        selectValueOnDropDown(ddlLCIType, value);
        extentLogger.logInfo(value);
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssueDetails clickPublish() {
        waitForAndClickElement(btnPublish);
        waitForElementNotVisible(btnCancel, WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        extentLogger.logInfo("value");
        return getNewPage(LotcheckReportSubmissionLotcheckIssueDetails.class);
    }

    public LotcheckPublishToPublicDialog selectPartnerThreadTranslationNeededCheckbox(String checked) {
        selectCheckbox(cbPartnerThreadTranslationNeed, checked);
        extentLogger.logInfo(checked);
        return this;
    }

}
