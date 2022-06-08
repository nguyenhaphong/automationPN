package net.nintendo.automation.ui.models.ndp;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NDPNintendo3DSForumsEnglishSupportPage extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//h5[@id='main-content' and contains(text(),'English Language Support')]");
    private static By btnCreateThread = By.xpath("//button[@class='btn' and @onclick]");
    private static By txtSubject = By.id("_gtsmessageboardsportlet_WAR_gtsmessageboardsportlet_subject");
    private static By txtDevelopmentEnvironment = By.id("_gtsmessageboardsportlet_WAR_gtsmessageboardsportlet_devenv");
    private static By iframeComment = By.xpath("//iframe[@class='cke_wysiwyg_frame cke_reset']");
    private static By txtComment = By.xpath("//body[@contenteditable]");
    private static By btnOk = By.xpath("//button[@type='submit' and @id]");
    private final By alertSuccess = By.xpath("//div[@class='alert alert-success']");
    private static By btnMyPosts = By.xpath("//a[contains(@href,'/forums/english/-/gts_message_boards/mypost')]");
    private static By nickName = By.xpath("//div[@class='mb-nickname']/a");
    private static By srcProfileImage = By.xpath("//img[contains(@class,'profile-image')]");
    private static By numberOfPosts = By.xpath("//div[contains(@class,'profile-posts')]");
    private static By nameOrganization = By.xpath("//td[contains(@class,'name-organization')]");

    public NDPNintendo3DSForumsEnglishSupportPage(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public NDPNintendo3DSForumsEnglishSupportPage selectMyPosts() {
        waitForAndClickElement(btnMyPosts);
        extentLogger.logInfo("");
        return this;
    }

    public String getNickNameOnPost() {
        String value = getText(nickName);
        extentLogger.logInfo(value);
        return value;
    }

    public String getSRCImage() {
        String value = getAttributes(srcProfileImage, "src");
        extentLogger.logInfo(value);
        return value;
    }

    public String getNumberOfPosts() {
        String value = getText(numberOfPosts).split(":")[1].trim();
        extentLogger.logInfo(" Number of posts: " + value);
        return value;
    }

    public String getNameOrganization() {
        String value = getText(nameOrganization);
        extentLogger.logInfo(value);
        return value;
    }

    public NDPNintendo3DSForumsEnglishSupportPage selectPostBySubject(String subject) {
        waitForAndClickElement(By.xpath(String.format("//a[contains(@href,'gts_message_boards/thread')]/span[contains(text(),'%s')]", subject)));
        extentLogger.logInfo("select subject: " + subject);
        return this;
    }

    public boolean isCreateThreadButtonVisible() {
        Boolean isVisible;
        if (elementNotExists(btnCreateThread)) {
            isVisible = false;
        } else {
            isVisible = true;
        }
        extentLogger.logInfo(isVisible.toString());
        return isVisible;
    }

    public NDPNintendo3DSForumsEnglishSupportPage selectCreateThread() {
        waitForAndClickElement(btnCreateThread);
        extentLogger.logInfo("");
        return this;
    }

    public NDPNintendo3DSForumsEnglishSupportPage enterSubject(String subject) {
        waitForAndClickElement(txtSubject);
        typeText(txtSubject, subject);
        typeTab(txtSubject);
        extentLogger.logInfo(subject);
        return this;
    }

    public NDPNintendo3DSForumsEnglishSupportPage enterDevelopmentEnvironment(String developmentEnvironment) {
        waitForAndClickElement(txtDevelopmentEnvironment);
        typeText(txtDevelopmentEnvironment, developmentEnvironment);
        typeTab(txtDevelopmentEnvironment);
        extentLogger.logInfo(developmentEnvironment);
        return this;
    }

    public NDPNintendo3DSForumsEnglishSupportPage enterComment(String comment) {
        WebElement eleFrame = driver.findElement(iframeComment);
        driver.switchTo().frame(eleFrame);
        waitForElementVisible(txtComment);
        typeText(txtComment, comment);
        typeTab(txtComment);
        driver.switchTo().defaultContent();
        extentLogger.logInfo(comment);
        return this;
    }

    public NDPNintendo3DSForumsEnglishSupportPage clickOkButton() {
        waitForAndClickElement(btnOk);
        extentLogger.logInfo("");
        return this;
    }

    public String getSuccessAlert() {
        String message = getTextIfElementExists(alertSuccess).trim();
        extentLogger.logInfo(message);
        return message;
    }
}
