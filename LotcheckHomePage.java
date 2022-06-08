package net.nintendo.automation.ui.models.lcms;

import net.nintendo.automation.ui.models.hyrule.HyruleAuthPage;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LotcheckHomePage extends CommonUIComponent {
    private static final By uniqueElement = By.cssSelector(".btn-primary");
    private static final String pageTitle = "Lotcheck - LCMS";

    public LotcheckHomePage(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public HyruleAuthPage continueToLogin() {
        waitForAndClickElement(uniqueElement);
        extentLogger.logInfo("");
        return getNewPage(HyruleAuthPage.class);
    }
}
