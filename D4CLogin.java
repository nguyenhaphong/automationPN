package net.nintendo.automation.ui.models.d4c;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.users.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class D4CLogin extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//input[@name='loginid']");
    private static final String page_title = "Login｜NDID";

    private final By btnContinue = By.id("id1d");

    public D4CLogin(WebDriver driver) {
        super(driver, uniqueElement, page_title);
    }

    public D4CRomPublishSuit signIn(User user) {
        return signIn(user.getUserName(), user.getPassword());
    }

    public D4CRomPublishSuit signIn(String userName, String password) {
        waitForElementClickable(By.xpath("//input[@name='loginid']"));
        extentLogger.logInfo("");
        typeText(By.xpath("//input[@name='loginid']"), userName);
        extentLogger.logInfo(userName);
        typeText(By.xpath("//input[@name='password']"), password);
        extentLogger.logInfo(password);
        waitForAndClickElement(By.xpath("//button[@type='submit' and text()='Allow']"));
        extentLogger.logInfo("");
        return getNewPage(D4CRomPublishSuit.class);
    }
}
