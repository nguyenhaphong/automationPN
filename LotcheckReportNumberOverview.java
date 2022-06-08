package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LotcheckReportNumberOverview extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@data-testid='number-overview-page']");
    private static final String pageTitle = "Overview Number: LCR - LCMS";

    public LotcheckReportNumberOverview(WebDriver driver) {
        super(driver, uniqueElement, pageTitle);
    }

    public LotcheckReportMatrix matrix() {
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportMatrix.class);
    }
}
