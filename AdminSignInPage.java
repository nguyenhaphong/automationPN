package net.nintendo.automation.ui.models.admin;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.users.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminSignInPage extends CommonUIComponent {
    //private static final By uniqueElement = By.cssSelector("input[name=\"loginid:input\"]");
    private static final By uniqueElement = By.cssSelector("input[name=\"loginid\"]");
    private final By btnSubmit = By.cssSelector("input[name='submit:input']");
    private final By btnContinue = By.id("id1d");
    private static final String page_title = "Login｜NDID";
    private static By txtUserName = By.cssSelector("input[name=\"loginid\"]");
    private static By txtPassword = By.cssSelector("input[name='password']");
    private static By btnAllow = By.cssSelector("button[type='submit']");

    public AdminSignInPage(WebDriver driver) {
        super(driver, uniqueElement, page_title);
    }

    public AdminDevelopmentHome signIn(User user) {
        return signIn(user.getUserName(), user.getPassword());
    }

    public AdminDevelopmentHome signIn(String userName, String password) {
        waitForElementClickable(txtUserName);
        extentLogger.logInfo("");
        typeText(txtUserName, userName);
        extentLogger.logInfo(userName);
        typeText(txtPassword, password);
        extentLogger.logInfo(password);
        waitForAndClickElement(btnAllow);
        extentLogger.logInfo("");
        return getNewPage(AdminDevelopmentHome.class);
    }

    public boolean signInIsDisplay() {
        waitForElementVisible(btnSubmit, WaitTimes.WAIT_FOR_UPLOAD_PROCESSING_SECS);
        return true;
    }
}
