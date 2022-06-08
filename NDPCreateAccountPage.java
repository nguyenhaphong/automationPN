package net.nintendo.automation.ui.models.ndp;

import lombok.extern.slf4j.Slf4j;
import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

@Slf4j
public class NDPCreateAccountPage extends CommonUIComponent {
    private final static By uniqueElement = By.className("nin-registration-title");
    private final static String pageTitle = "Create Account - Nintendo Developer Portal";

    private final By radOrganization = By.xpath("//input[@onclick='_noaregistrationportlet_WAR_noaomasportlet_setIsIndividual(false);']");
    private final By txtOrganizationalName = By.id("_noaregistrationportlet_WAR_noaomasportlet_companyName");
    private final By txtDoingBusinessAsName = By.id("_noaregistrationportlet_WAR_noaomasportlet_dbaName");
    private final By txtAddressLine1 = By.id("_noaregistrationportlet_WAR_noaomasportlet_addressLine1");
    private final By txtAddressLine2 = By.id("_noaregistrationportlet_WAR_noaomasportlet_addressLine2");
    private final By txtCity = By.id("_noaregistrationportlet_WAR_noaomasportlet_city");
    private final By ddlCountry = By.id("_noaregistrationportlet_WAR_noaomasportlet_country");
    private final By txtRegion = By.id("_noaregistrationportlet_WAR_noaomasportlet_regionOther");
    private final By txtPostalCode = By.id("_noaregistrationportlet_WAR_noaomasportlet_postalCode");
    private final By cboExistingAgreement = By.id("_noaregistrationportlet_WAR_noaomasportlet_existingAgreementFlagCheckbox");
    private final By txtFirstName = By.id("_noaregistrationportlet_WAR_noaomasportlet_firstName");
    private final By txtLastName = By.id("_noaregistrationportlet_WAR_noaomasportlet_lastName");
    private final By txtJobTitle = By.id("_noaregistrationportlet_WAR_noaomasportlet_jobTitle");
    private final By txtNickname = By.id("_noaregistrationportlet_WAR_noaomasportlet_nickname");
    private final By txtChooseAnNintendoDeveloperId = By.id("_noaregistrationportlet_WAR_noaomasportlet_screenName");
    private final By txtEmailAddress = By.id("_noaregistrationportlet_WAR_noaomasportlet_emailAddress");
    private final By txtConfirmEmailAddress = By.id("_noaregistrationportlet_WAR_noaomasportlet_confirmEmailAddress");
    private final By radAreYouAuthorizedYes = By.xpath("//input[@onclick='_noaregistrationportlet_WAR_noaomasportlet_setIsAuthorized(true);']");
    private final By cboYouMustBeAtLeast18 = By.id("_noaregistrationportlet_WAR_noaomasportlet_isLegalAgeCheckbox");
    private final By btnReviewApplication = By.id("_noaregistrationportlet_WAR_noaomasportlet_review-button");
    private final By btnEdit = By.id("_noaregistrationportlet_WAR_noaomasportlet_final-step-button");
    private final By btnSubmitApplication = By.id("_noaregistrationportlet_WAR_noaomasportlet_submit-button");

    public NDPCreateAccountPage(WebDriver driver) {
        super(driver, uniqueElement, pageTitle);
    }

    public Boolean getOrganizationRadioButtonStatus() {
        Boolean status = getIsSelectedNoWait(radOrganization);
        extentLogger.logInfo(status.toString());
        return status;
    }

    public Boolean getAreYouAuthorizedYesStatus() {
        Boolean status = getIsSelectedNoWait(radAreYouAuthorizedYes);
        extentLogger.logInfo(status.toString());
        return status;
    }

    public NDPCreateAccountPage selectAreYouAuthorizedYesRadioButton() {
        selectRadio(radAreYouAuthorizedYes);
        extentLogger.logInfo("");
        return this;
    }

    public NDPCreateAccountPage selectOrganizationRadiobutton() {
        selectRadio(radOrganization);
        extentLogger.logInfo("");
        return this;
    }

    public NDPCreateAccountPage enterOrganizationName(String orgName) {
        waitForAndClickElement(txtOrganizationalName);
        typeText(txtOrganizationalName, orgName);
        typeTab(txtOrganizationalName);
        extentLogger.logInfo(orgName);
        return this;
    }

    public NDPCreateAccountPage enterDoingAsBusinessName(String orgName) {
        waitForAndClickElement(txtDoingBusinessAsName);
        typeText(txtDoingBusinessAsName, orgName);
        typeTab(txtDoingBusinessAsName);
        extentLogger.logInfo(orgName);
        return this;
    }

    public NDPCreateAccountPage enterAddressLine1(String address) {
        waitForAndClickElement(txtAddressLine1);
        typeText(txtAddressLine1, address);
        typeTab(txtAddressLine1);
        extentLogger.logInfo(address);
        return this;
    }

    public NDPCreateAccountPage enterAddressLine2(String address) {
        waitForAndClickElement(txtAddressLine2);
        typeText(txtAddressLine2, address);
        typeTab(txtAddressLine2);
        extentLogger.logInfo(address);
        return this;
    }

    public NDPCreateAccountPage enterCity(String city) {
        waitForAndClickElement(txtCity);
        typeText(txtCity, city);
        typeTab(txtCity);
        extentLogger.logInfo(city);
        return this;
    }

    public NDPCreateAccountPage selectCountry(String country) {
        waitForElementVisible(ddlCountry,WaitTimes.WAIT_FOR_SUBMIT_PROCESSING_SECS);
        selectValueOnDropDown(ddlCountry, country);
        extentLogger.logInfo(country);
        return this;
    }

    public NDPCreateAccountPage enterRegion(String region) {
        waitForAndClickElement(txtRegion);
        typeText(txtRegion, region);
        typeTab(txtRegion);
        extentLogger.logInfo(region);
        return this;
    }

    public NDPCreateAccountPage enterPostalCode(String postalCode) {
        waitForAndClickElement(txtPostalCode);
        typeText(txtPostalCode, postalCode);
        typeTab(txtPostalCode);
        extentLogger.logInfo(postalCode);
        return this;
    }

    public NDPCreateAccountPage selectExistingAgreement() {
        selectCheckbox(cboExistingAgreement, "true");
        extentLogger.logInfo("");
        return this;
    }

    public NDPCreateAccountPage enterFirstName(String firstName) {
        waitForAndClickElement(txtFirstName);
        typeText(txtFirstName, firstName);
        typeTab(txtFirstName);
        extentLogger.logInfo(firstName);
        return this;
    }

    public NDPCreateAccountPage enterLastName(String lastName) {
        waitForAndClickElement(txtLastName);
        typeText(txtLastName, lastName);
        typeTab(txtLastName);
        extentLogger.logInfo(lastName);
        return this;
    }

    public NDPCreateAccountPage enterJobTitle(String jobTitle) {
        waitForAndClickElement(txtJobTitle);
        typeText(txtJobTitle, jobTitle);
        typeTab(txtJobTitle);
        extentLogger.logInfo(jobTitle);
        return this;
    }

    public NDPCreateAccountPage enterNickname(String nickname) {
        waitForAndClickElement(txtNickname);
        typeText(txtNickname, nickname);
        typeTab(txtNickname);
        extentLogger.logInfo(nickname);
        return this;
    }

    public NDPCreateAccountPage enterNintendoDeveloperID(String ndid) {
        waitForAndClickElement(txtChooseAnNintendoDeveloperId);
        typeText(txtChooseAnNintendoDeveloperId, ndid);
        typeTab(txtChooseAnNintendoDeveloperId);
        extentLogger.logInfo(ndid);
        return this;
    }

    public NDPCreateAccountPage enterEmailAddress(String emailAddress) {
        waitForAndClickElement(txtEmailAddress);
        typeText(txtEmailAddress, emailAddress);
        typeTab(txtEmailAddress);
        extentLogger.logInfo(emailAddress);
        return this;
    }

    public NDPCreateAccountPage enterConfirmEmailAddress(String emailAddress) {
        waitForAndClickElement(txtConfirmEmailAddress);
        typeText(txtConfirmEmailAddress, emailAddress);
        typeTab(txtConfirmEmailAddress);
        extentLogger.logInfo(emailAddress);
        return this;
    }

    public NDPCreateAccountPage selectYouMustBeAtLeast18() {
        selectCheckbox(cboYouMustBeAtLeast18, "true");
        extentLogger.logInfo("");
        return this;
    }

    public NDPCreateAccountPage deselectYouMustBeAtLeast18() {
        selectCheckbox(cboYouMustBeAtLeast18, "false");
        extentLogger.logInfo("");
        return this;
    }

    public NDPCreateAccountPage clickReviewApplicationButton() {
        waitForAndClickElement(btnReviewApplication);
        waitDocumentReady();
        extentLogger.logInfo("");
        return this;
    }

    public NDPCreateAccountPage clickEditButton() {
        waitForAndClickElement(btnEdit);
        extentLogger.logInfo("");
        return this;
    }

    public NDPConfirmationPage clickSubmitApplicationButton() {
        WaitUtils.idle(2000);
        waitForAndClickElement(btnSubmitApplication);
        waitForElementNotVisible(btnSubmitApplication, WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        extentLogger.logInfo("");
        return getNewPage(NDPConfirmationPage.class);
    }

    public NDPCreateAccountPage waitForAutoScrollStop() {
        float lastX, lastY;
        float currentX = 0, currentY = 0;
        do {
            lastX = currentX;
            lastY = currentY;
            currentX = getClientViewCoordinate("x");
            currentY = getClientViewCoordinate("y");
            log.info("last x: " + lastX);
            log.info("last y: " + lastY);
            log.info("current x: " + currentX);
            log.info("current y: " + currentY);
        } while ((lastX != currentX) || (lastY != currentY));
        return this;
    }
}
