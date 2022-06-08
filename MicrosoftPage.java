package net.nintendo.automation.ui.models.hyrule;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MicrosoftPage extends CommonUIComponent {
    private static final By uniqueElement = By.id("lightbox");
    private final By btnContinue = By.xpath("//button[@class='btn btn-lg btn-nin']");

    public MicrosoftPage(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public LotcheckLandingPage signIn(String user, String pass) {
        waitForAndClickElement(btnContinue);
        typeText(By.xpath("//input[@name='loginfmt']"), user);
        extentLogger.logInfo("");
        waitForAndClickElement(By.xpath("//input[@id='idSIButton9']"));
        extentLogger.logInfo("");
        typeText(By.xpath("//input[@name='passwd']"), pass);
        waitForAndClickElement(By.xpath("//input[@id='idSIButton9']"));
        extentLogger.logInfo("");
        waitForAndClickElement(By.xpath("//input[@id='idBtn_Back']"));
        extentLogger.logInfo("");
        return getNewPage(LotcheckLandingPage.class);
    }
}
