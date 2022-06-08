package net.nintendo.automation.ui.models.hyrule;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.ui.users.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class HyruleAuthPage extends CommonUIComponent {
    private static final By uniqueElement = By.id("ovd-login-link");
    private static final String pageTitle = "Hyrule Authorization Server";
    private final By txtUsername = By.id("username");
    private final By txtPassword = By.id("password");
    private final By btnLogin = By.cssSelector("button.btn.btn-lg.btn-nin.btn-block");
    private final By btnContinue = By.xpath("//button[@class='btn btn-lg btn-nin']");
    private final By txtHyruleUserName = By.xpath("//input[@name='loginfmt']");
    private final By btnHyruleNext = By.xpath("//input[@id='idSIButton9']");
    private final By txtHyrulePassword = By.xpath("//input[@name='passwd']");
    private final By btnHyruleBack = By.xpath("//input[@id='idBtn_Back']");
    private final By txtOtherTitle = By.xpath("//div[@id='otherTile']");

    public HyruleAuthPage(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public LotcheckLandingPage loginWithOvdLink(User user) {
        extentLogger.logInfo("");
        return loginWithOvdLink(user.getUserName(), user.getPassword());
    }

    public LotcheckLandingPage loginWithOvdLink(String username, String password) {
        extentLogger.logInfo("");
        waitForAndClickElement(uniqueElement);

        waitForAndClickElement(txtUsername);
        // Really don't like this but RemoteWebDriver doesn't seem to keep up without it
        try {
            TimeUnit.MILLISECONDS.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        typeText(txtUsername, username);
        typeText(txtPassword, password);
        waitForAndClickElement(btnLogin);

        return getNewPage(LotcheckLandingPage.class);
    }

    public LotcheckLandingPage signIn(String user, String pass) {
        extentLogger.logInfo("");
        waitForAndClickElement(btnContinue);
        typeText(txtHyruleUserName, user);
        waitForAndClickElement(btnHyruleNext);
        typeText(txtHyrulePassword, pass);
        waitForAndClickElement(btnHyruleNext);
        waitForAndClickElement(btnHyruleBack);
        return getNewPage(LotcheckLandingPage.class);
    }

    public LotcheckLandingPage signInOther(String user, String pass) {
        extentLogger.logInfo("");
        waitForAndClickElement(btnContinue);
        waitForAndClickElement(txtOtherTitle);
        typeText(txtHyruleUserName, user);
        waitForAndClickElement(btnHyruleNext);
        typeText(txtHyrulePassword, pass);
        waitForAndClickElement(btnHyruleNext);
        return getNewPage(LotcheckLandingPage.class);
    }
}
