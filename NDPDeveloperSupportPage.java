package net.nintendo.automation.ui.models.ndp;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NDPDeveloperSupportPage extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//span[text()=' Developer Support ']");
    private static final String pageTitle = "Developer Support - Nintendo Developer Portal";
    private static By lnkNintendo3DS = By.xpath("//span[contains(text(),'Nintendo 3DS')]/..");
    private static By lnkForumsEnglishNintendo3DS = By.xpath("//a[contains(@href,'/group/development/wtc6ppr2/forums/english')]");

    public NDPDeveloperSupportPage(WebDriver driver) {
        super(driver, uniqueElement, pageTitle);
    }

    public NDPDeveloperSupportPage clickNintendo3DS() {
        waitForAndClickElement(lnkNintendo3DS);
        extentLogger.logInfo("");
        return this;
    }

    public NDPNintendo3DSForumsEnglishSupportPage selectForumsEnglishForNintendo3DS() {
        waitForAndClickElement(lnkForumsEnglishNintendo3DS);
        extentLogger.logInfo("");
        return getNewPage(NDPNintendo3DSForumsEnglishSupportPage.class);
    }
}
