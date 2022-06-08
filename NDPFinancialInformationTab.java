package net.nintendo.automation.ui.models.ndp;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NDPFinancialInformationTab extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@id='_noacompanyinfoportlet_WAR_noacompanyadminportlet_tabs11021051109711099105971084510511010211111410997116105111110TabsSection']");
    private final By lblBMSCode_Global = By.xpath("//div[@class='nin-section-user']//p[3]");
    private final By lblPayeeRegionType_Global = By.xpath("//div[@class='nin-section-user']//p[2]");
    private final By lblTaxInfo_Global = By.xpath("//div[@class='nin-section-user']//p[1]");
    private final By lblPayeeName_Global = By.xpath("//div[@class='nin-section-user']//div");

    private final By lblBMSCode_China = By.xpath("//div[@class='nin-section-user']/following::div[@class='nin-section-user']//p[3]");
    private final By lblPayeeRegionType_China = By.xpath("//div[@class='nin-section-user']/following::div[@class='nin-section-user']//p[2]");
    private final By payeeRegionType_China = By.xpath("//div[@class='nin-section-user']/following::div[@class='nin-section-user']//p[2]");

    private final By lblTaxInfo_China = By.xpath("//div[@class='nin-section-user']/following::div[@class='nin-section-user']//p[1]");
    private final By lblPayeeName_China = By.xpath("//div[@class='nin-section-user']/following::div[@class='nin-section-user']//div");

    private final By payeeViewDetail = By.xpath("//div[@id='_noacompanyinfoportlet_WAR_noacompanyadminportlet_tabs11021051109711099105971084510511010211111410997116105111110TabsSection']/section[1]//a[contains(@href,'VIEW_PAYEE_DETAILS')]");
    private final By firstPayee = By.xpath("//div[@id='_noacompanyinfoportlet_WAR_noacompanyadminportlet_tabs11021051109711099105971084510511010211111410997116105111110TabsSection']/section[1]");

    public NDPFinancialInformationTab(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public String getBmsCode_Global() {
        String value = getText(lblBMSCode_Global).split(":")[1].trim();
        extentLogger.logInfo(value);
        return value;
    }

    public String getPayeeRegionType_Global() {
        String value = getText(lblPayeeRegionType_Global).split(":")[1].trim();
        extentLogger.logInfo(value);
        return value;
    }

    public String getTaxInfo_Global() {
        String value = getText(lblTaxInfo_Global).split(":")[1].trim();
        extentLogger.logInfo(value);
        return value;
    }

    public String getPayeeName_Global() {
        String value = getText(lblPayeeName_Global).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public String getBmsCode_China() {
        String value = getText(lblBMSCode_China).split(":")[1].trim();
        extentLogger.logInfo(value);
        return value;
    }

    public String getPayeeRegionType_China() {
        String value = "";
        String bmsCodeValue = getText(payeeRegionType_China);
        if (bmsCodeValue.contains(":")) {
            value = bmsCodeValue.split(":")[1].trim();
        }
        extentLogger.logInfo(value);
        return value;
    }

    public String getTaxInfo_China() {
        String value = getText(lblTaxInfo_China).split(":")[1].trim();
        extentLogger.logInfo(value);
        return value;
    }

    public String getPayeeName_China() {
        String value = getText(lblPayeeName_China).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public NDPTopNavigationBar navTopNavigationBar() {
        extentLogger.logInfo("");
        return getNewPage(NDPTopNavigationBar.class);
    }

    public boolean isPayeeDisplayed() {
        boolean value = false;
        if (!elementNotExists(firstPayee)) {
            value = true;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public boolean isViewDetailPayeeDisplayed() {
        boolean value = false;
        if (!elementNotExists(payeeViewDetail)) {
            value = true;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }
    public boolean isPayeeDisplayed(String payeeName) {
        boolean isDisplayed = false;
        By element = By.xpath(String.format("//div[@class='nin-section-user']/div[text()='%s']",payeeName));
        if (!elementNotExists(element)) {
            isDisplayed = true;
        }
        extentLogger.logInfo(String.valueOf(isDisplayed));
        return isDisplayed;
    }

}
