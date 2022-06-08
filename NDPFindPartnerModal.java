package net.nintendo.automation.ui.models.ndp;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class NDPFindPartnerModal extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@aria-describedby=\"ui-id-1\"]");
    private final By txtOrgName = By.xpath("//input[@name='orgSearchString']");
    private final By btnNext = By.xpath("//a[@id='orgSearchResults_next']");
    private final By btnNext_Disable = By.xpath("//a[@id='orgSearchResults_next' and contains(@class,'ui-state-disabled')]");
    private final By searchIcon = By.xpath("//div[@class='nin-search-btn btn org-search-icon']//i");
    private final By btnChooseSelectedPartner = By.xpath("//div[@aria-describedby='ui-id-1']//button[@type='submit']");

    public NDPFindPartnerModal(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public NDPFindPartnerModal enterOrganizationName(String organizationName) {
        waitForAndClickElement(txtOrgName);
        typeText(txtOrgName, organizationName);
        typeTab(txtOrgName);
        extentLogger.logInfo(organizationName);
        return this;
    }

    public NDPFindPartnerModal clickSearch() {
        waitForAndClickElement(searchIcon);
        extentLogger.logInfo("");
        return this;
    }

    public NDPFindPartnerModal logInfoWithScreenshot() {
        extentLogger.logInfoWithScreenshot("Find Partner modal - verify for step 4");
        return this;
    }

    public NDPFindPartnerModal selectOrg(String orgName) {
        WebElement element;
        List<WebElement> elements = findElements(By.xpath(String.format("//input[contains(@value,'%s')]", orgName)));
        int size = elements.size();
        while (size <= 0) {
            List<WebElement> elements1 = driver.findElements(btnNext_Disable);
            if (elements1.size() == 1) {
                break;
            }
            waitForAndClickElement(btnNext);
            elements = findElements(By.xpath(String.format("//input[contains(@value,'%s')]", orgName)));
            size = elements.size();

        }
        selectRadio(By.xpath(String.format("//input[contains(@value,'%s')]", orgName)));
        extentLogger.logInfo(orgName);
        return this;
    }

    public NDPCustomAgreementsPage clickChooseSelectedPartner() {
        waitForAndClickElement(btnChooseSelectedPartner);
        extentLogger.logInfo("");
        return getNewPage(NDPCustomAgreementsPage.class);
    }
}
