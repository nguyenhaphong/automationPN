package net.nintendo.automation.ui.models.ndp;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.time.Duration;

public class NDPMyAccountPage extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@class='nin-section-title']//h5");
    private static final String page_title = "My Account - Nintendo Developer Portal";
    private final By btnEditProfile = By.xpath("//div[@class='layout '][2]//a[contains(@href,'_noacompanyusersportlet_WAR_noacompanyadminportlet_action=UPDATE_USER')]");
    private final By ddlSelectLanguage = By.id("_noacompanyusersportlet_WAR_noacompanyadminportlet_languageId");
    private final By btnSaveChanges = By.xpath("//div[@class='layout '][2]//button[@type='submit']");
    private final By lblOrganization = By.id("_noacompanyusersportlet_WAR_noacompanyadminportlet_organization");
    private final By cboCompanyNxUser = By.xpath("//input[@id='_noacompanyusersportlet_WAR_noacompanyadminportlet_Company NX UserCheckbox']");
    private final By cboCompanyNXUser = By.id("_noacompanyusersportlet_WAR_noacompanyadminportlet_Company NX UserCheckbox");
    private final By cboCompanyFinance = By.id("_noacompanyusersportlet_WAR_noacompanyadminportlet_Company FinanceCheckbox");
    private final By cancelButton = By.xpath("//a[@class='btn nin-btn-cancel pull-left']");
    private final By companyNxUserCheckbox = By.xpath("//input[@id='_noacompanyusersportlet_WAR_noacompanyadminportlet_Company NX UserCheckbox']");
    private final By organizationElement = By.id("_noacompanyusersportlet_WAR_noacompanyadminportlet_organization");
    private final By companyNXUserCheckbox = By.id("_noacompanyusersportlet_WAR_noacompanyadminportlet_Company NX UserCheckbox");
    private static By btnChange = By.xpath("//i[@class='icon-picture']/..");
    private static By chooseFile = By.xpath("//body//input[@id='_2_fileName']");
    private static By changeLogoIframe = By.xpath("//div[@id='_noacompanyusersportlet_WAR_noacompanyadminportlet_changeLogo']//iframe");
    private static By btnSave = By.id("_2_submitButton");
    private static By iconMenu = By.xpath("//span[contains(@class,'fa fa-ellipsis-h ')]");
    private static By lnkEditProfile = By.xpath("//a[contains(@class,'edit-profile-link')]");
    private static By lnkRequestMyInfo = By.xpath("//a[contains(@class,'request-user-info')]");
    private static By lnkDeleteUser = By.xpath("//a[contains(@class,'remove-user')]");
    private final By txtFirstName = By.id("_noacompanyusersportlet_WAR_noacompanyadminportlet_firstName");
    private final By txtLastName = By.id("_noacompanyusersportlet_WAR_noacompanyadminportlet_lastName");
    private final By txtNickName = By.id("_noacompanyusersportlet_WAR_noacompanyadminportlet_nickname");
    private final By alertSuccess = By.xpath("//div[@class='alert alert-success']");

    public NDPMyAccountPage(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public NDPMyAccountPage clickEditProfileButton() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnEditProfile);
        return this;
    }

    public NDPMyAccountPage selectLanguageUser(String value) {
        extentLogger.logInfo(value);
        selectValueOnDropDown(ddlSelectLanguage, value);
        return this;
    }

    public NDPMyAccountPage selectMenuIcon() {
        waitForAndClickElement(iconMenu);
        extentLogger.logInfo("");
        return this;
    }


    public NDPMyAccountPage selectChangeAvatar() {
        waitForAndClickElement(btnChange);
        extentLogger.logInfo("");
        return this;
    }

    public NDPMyAccountPage enterAvatarFile(File file) {
        driver.switchTo().frame(driver.findElement(changeLogoIframe));
        typeText(chooseFile, file.getAbsolutePath());
        extentLogger.logInfo("");
        return this;
    }

    public NDPMyAccountPage selectSaveButton() {
        waitForAndClickElement(btnSave);
        extentLogger.logInfo("");
        waitForElementNotVisible(btnSave, Duration.ofSeconds(30));
        driver.switchTo().defaultContent();
        return this;
    }


    public NDPMyAccountPage clickSaveChangesButton() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnSaveChanges);
        return this;
    }

    public String getOrganizationName() {
        String nameOrganization = getValueAttributes(lblOrganization);
        extentLogger.logInfo(nameOrganization);
        return nameOrganization;
    }

    public NDPTopNavigation navTopNavigation() {
        extentLogger.logInfo("");
        return getNewPage(NDPTopNavigation.class);
    }

    public NDPMyAccountPage selectCompanyFinanceRole() {
        selectCheckbox(cboCompanyFinance, "true");
        extentLogger.logInfo("");
        return this;
    }

    public boolean isCompanyNXUserDisplayed() {
        boolean value = false;
        if (!elementNotExists(companyNxUserCheckbox)) {
            value = true;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public NDPMyAccountPage clickCancel() {
        waitForAndClickElement(cancelButton);
        waitDocumentReady();
        extentLogger.logInfo("");
        return this;
    }

    public NDPTopNavigationBar navTopNavigationBar() {
        extentLogger.logInfo("");
        return getNewPage(NDPTopNavigationBar.class);
    }


    public NDPMyAccountPage selectCompanyNxUserRole() {
        selectCheckbox(companyNXUserCheckbox, "true");
        extentLogger.logInfo("");
        return this;
    }

    public boolean isEditProfileOptionDisplayed() {
        Boolean isDisplayed = driver.findElement(lnkEditProfile).isDisplayed();
        extentLogger.logInfo(isDisplayed.toString());
        return isDisplayed;
    }

    public NDPMyAccountPage selectEditProfileOption() {
        waitForAndClickElement(lnkEditProfile);
        extentLogger.logInfo("");
        return this;
    }

    public boolean isRequestMyInfoOptionDisplayed() {
        Boolean isDisplayed = driver.findElement(lnkRequestMyInfo).isDisplayed();
        extentLogger.logInfo(isDisplayed.toString());
        return isDisplayed;
    }

    public boolean isDeleteUserOptionDisplayed() {
        Boolean isDisplayed = driver.findElement(lnkDeleteUser).isDisplayed();
        extentLogger.logInfo(isDisplayed.toString());
        return isDisplayed;
    }

    public NDPMyAccountPage enterFirstName(String firstName) {
        waitForAndClickElement(txtFirstName);
        typeText(txtFirstName, firstName);
        typeTab(txtFirstName);
        extentLogger.logInfo(firstName);
        return this;
    }

    public NDPMyAccountPage enterLastName(String lastName) {
        waitForAndClickElement(txtLastName);
        typeText(txtLastName, lastName);
        typeTab(txtLastName);
        extentLogger.logInfo(lastName);
        return this;
    }

    public NDPMyAccountPage enterNickName(String nickName) {
        waitForAndClickElement(txtNickName);
        typeText(txtNickName, nickName);
        typeTab(txtNickName);
        extentLogger.logInfo(nickName);
        return this;
    }

    public String getFirstName() {
        String value = getValueAttributes(txtFirstName);
        extentLogger.logInfo(value);
        return value;
    }

    public String getLastName() {
        String value = getValueAttributes(txtLastName);
        extentLogger.logInfo(value);
        return value;
    }

    public String getNickName() {
        String value = getValueAttributes(txtNickName);
        extentLogger.logInfo(value);
        return value;
    }

    public String getSuccessAlert() {
        String message = getTextIfElementExists(alertSuccess).trim();
        extentLogger.logInfo(message);
        return message;
    }
}