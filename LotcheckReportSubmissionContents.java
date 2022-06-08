package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.hyrule.HyruleAuthLogoutPage;
import net.nintendo.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LotcheckReportSubmissionContents extends CommonUIComponent {
    private final By lnkNavUser = By.xpath("//div[contains(@class,'nav-user')]//span");
    private static final String pageTitle = "Contents Submission: LCR - LCMS";

    private final By btnSignOut = By.xpath("//i[@class='fas fa-sign-out-alt mr-1']");
    private final By lblReleaseVerValue = By.xpath("//tr[@data-testid=\"add-on-contents-row\"]//td[4]");
    private final By lblPrivateVerValue = By.xpath("//tr[@data-testid=\"add-on-contents-row\"]//td[5]");
    private final By lblArchiveNoColumn = By.xpath("//table[@class='table table-striped table-nin blue-sep']//th[text()='ArchiveNo']");
    private final By lblRequiredApplicationVerValue = By.xpath("//tr[@data-testid=\"add-on-contents-row\"]//td[6]");
    private final By lblRomIdHashValue = By.xpath("//tr[@data-testid=\"add-on-contents-row\"]//td[3]");
    private static final By uniqueElement = By.xpath("//div[@aria-labelledby=\"content-tab\"]");

    public LotcheckReportSubmissionContents(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public String getLblReleaseVerValue() {
        String value = getText(lblReleaseVerValue);
        extentLogger.logInfo(value);
        return value;
    }

    public String getLblPrivateVerValue() {
        String value = getText(lblPrivateVerValue);
        extentLogger.logInfo(value);
        return value;
    }

    public String getLblRequiredApplicationVerValue() {
        String value = getText(lblRequiredApplicationVerValue);
        extentLogger.logInfo(value);
        return value;
    }

    public boolean archiveNoColumnIsNotVisible() {
        Boolean value = elementNotExists(lblArchiveNoColumn);
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public String getLblRomIdHashValue() {
        String value = getText(lblRomIdHashValue);
        extentLogger.logInfo(value);
        return value;
    }

    public HyruleAuthLogoutPage logOut() {
        openLinkInSameTab(lnkNavUser);
        extentLogger.logInfo("");
        waitForAndClickElement(btnSignOut);
        extentLogger.logInfo("");
        return getNewPage(HyruleAuthLogoutPage.class);
    }
    public LotcheckReportNav navBar() {
        return getNewPage(LotcheckReportNav.class);
    }

    public LotcheckReportSubmissionContents waitPageLoaded(){
        WaitUtils.idle(2000);
        waitForElementVisible(uniqueElement, WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        return this;
    }


    public LotcheckReportMatrix navReportMatrix(){
        return getNewPage(LotcheckReportMatrix.class);
    }

}