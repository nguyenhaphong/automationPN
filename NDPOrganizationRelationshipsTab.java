package net.nintendo.automation.ui.models.ndp;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.ndp.admin.existing_adminTab.NDPAdminOrganizationRelationshipsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NDPOrganizationRelationshipsTab extends CommonUIComponent {
    private static final By addRelationshipButton = By.xpath("//button[@class='btn add-relationship btn-primary pull-right']");
    private final By relatedOrganizationUUIDTextbox = By.xpath("//input[@id='_noacompanyinfoportlet_WAR_noacompanyadminportlet_relatedOrganizationUUID']");
    private final By relatedOrganizationRelationshipDropdown = By.xpath("//select[@id='_noacompanyinfoportlet_WAR_noacompanyadminportlet_orgRelationship']");
    private final By sharedPublishingAgreementsRadio = By.xpath("//input[@class='field agreement-type shared-agreement']");
    private final By submitButton = By.xpath("//button[@class='btn submit-button btn-primary pull-right btn-primary']");
    private final By alertSuccess = By.xpath("//div[@class='alert alert-success']");
    private final By tabOrganizationRelationships = By.xpath("//a[contains(text(),'Organization Relationships')]");
    private final By messageSuccess = By.xpath("//div[@class='alert alert-success']");

    public NDPOrganizationRelationshipsTab(WebDriver driver) {
        super(driver, addRelationshipButton);
    }

    public NDPOrganizationRelationshipsTab clickAddRelationship() {
        waitForAndClickElement(addRelationshipButton);
        waitDocumentReady();
        extentLogger.logInfo("");
        return this;
    }

    public NDPOrganizationRelationshipsTab enterRelatedOrganizationUUID(String relateOrgUuid) {
        waitForAndClickElement(relatedOrganizationUUIDTextbox);
        typeText(relatedOrganizationUUIDTextbox, relateOrgUuid);
        typeTab(relatedOrganizationUUIDTextbox);
        extentLogger.logInfo(relateOrgUuid);
        return this;
    }

    public NDPOrganizationRelationshipsTab selectRelatedOrganizationRelationship(String relatedOrganizationRelationship) {
        selectValueOnDropDown(relatedOrganizationRelationshipDropdown, relatedOrganizationRelationship);
        extentLogger.logInfo(relatedOrganizationRelationship);

        return this;
    }

    public NDPOrganizationRelationshipsTab selectSharedPublishingAgreements() {
        selectRadio(sharedPublishingAgreementsRadio);
        extentLogger.logInfo("");
        return this;
    }

    public NDPOrganizationRelationshipsTab clickSubmit() {
        waitForAndClickElement(submitButton);
        waitDocumentReady();
        extentLogger.logInfo("");
        return this;
    }

    public String getSuccessMessage() {
        waitForElementVisible(alertSuccess, WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        String value = getText(alertSuccess).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public NDPTopNavigationBar navTopNavigationBar() {
        extentLogger.logInfo("");
        return getNewPage(NDPTopNavigationBar.class);
    }

    public boolean isOrganizationRelationshipCorrect(String releateOrdUUID, String relationship, String sharedPulishing) {
        boolean value = false;
        if (!elementNotExists(By.xpath(String.format("//td[text()='%s']/following-sibling::td[text()='%s']/following-sibling::td[text()='%s']", releateOrdUUID, relationship, sharedPulishing)))) {
            value = true;
        }
        extentLogger.logInfo("Organization UUID: " + releateOrdUUID + " Relationship: " + relationship + " Shared Publishing Rights: " + sharedPulishing + " :" + value);
        return value;
    }

    public NDPAdminOrganizationRelationshipsPage clickOrganizationRelationshipsTab() {
        waitForAndClickElement(tabOrganizationRelationships);
        extentLogger.logInfo("");
        return getNewPage(NDPAdminOrganizationRelationshipsPage.class);
    }

    public String getMessageSuccess() {
        String value = getText(messageSuccess);
        extentLogger.logInfo(value);
        return value;
    }
}
