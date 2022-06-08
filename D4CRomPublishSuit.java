package net.nintendo.automation.ui.models.d4c;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class D4CRomPublishSuit extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//a[@id='navbarBrand']");
    private static final String page_title = "Privacy error";
    private final By lnkRomTab = By.xpath("//a[@href='/roms']");
    private final By connectionIsNotPrivate = By.xpath("//div[@id='main-message']//h1");
    private final By btnAdvance = By.xpath("//button[@id='details-button']");
    private final By lnkProceed = By.xpath("//a[@id='proceed-link']");

    public D4CRomPublishSuit(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public D4CRomTab clickTabRom() {
        extentLogger.logInfo("");
        waitForAndClickElement(lnkRomTab);
        return getNewPage(D4CRomTab.class);
    }

    public String getTitleConnect() {
        String titleConnect = getText(uniqueElement);
        extentLogger.logInfo(titleConnect);
        return titleConnect;
    }

    public D4CRomPublishSuit clickAdvanceButton() {
        waitForAndClickElement(btnAdvance);
        extentLogger.logInfo("");
        return this;
    }

    public D4CRomPublishSuit clickProceedLink() {
        waitForAndClickElement(lnkProceed);
        extentLogger.logInfo("");
        return this;
    }

}
