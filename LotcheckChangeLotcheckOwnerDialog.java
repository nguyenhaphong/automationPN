package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LotcheckChangeLotcheckOwnerDialog extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//button[@class='btn btn btn-primary float-right']");
    private final By ddpNewLCO = By.xpath("//select[@id='dropdown']");
    private final By btnSave = By.xpath("//button[@class='btn btn btn-primary float-right']");
    private final By btnCancel = By.xpath("//button[@data-testid=\"change-lotcheck-owner-modal-cancel\"]");

    public LotcheckChangeLotcheckOwnerDialog(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public LotcheckChangeLotcheckOwnerDialog selectNewLCO(String newLco) {
        selectValueOnDropDown(ddpNewLCO, newLco);
        extentLogger.logInfo(newLco);
        return this;
    }

    public LotcheckReportSubmissionOverview clickSave() {
        waitForAndClickElement(btnSave);
        waitForElementNotVisible(btnCancel, WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionOverview.class);
    }
}
