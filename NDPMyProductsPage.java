package net.nintendo.automation.ui.models.ndp;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.ndp.existing_product.NDPProductDashboardPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NDPMyProductsPage extends CommonUIComponent {
    private static final By uniqueElement = By.cssSelector("a[href*='_noauserproductlistportlet_WAR_noaprojectsportlet_javax.portlet.action=createNewUserProduct']");
    private static final String page_title = "My Products - Nintendo Developer Portal";
    private final By lnkUserNameMenu = By.xpath("//*[@id='nin-quick-nav']/li[3]/a/span");
    private final By btnSignOut = By.xpath("//*[@id='nin-quick-nav']/li[3]/section/ul/li[3]/a");
    private final By btnManagerAccount = By.xpath("//a[contains(@href,'/group/development/account')]");
    public final By btnEnglish = By.xpath("//button[contains(text(),'English')]");
    public final By btnJapan = By.xpath("//button[contains(text(),'日本語')]");
    private static By lnkMyProducts = By.cssSelector("a[href*='group/development/products']");
    private final By saleRegion = By.xpath("//div[@class='product-name']/following-sibling::span[contains(@class,'sales-region')]");

    public NDPMyProductsPage(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public NDPTopNavigationBar navTopNavigationBar() {
        extentLogger.logInfo("");
        return getNewPage(NDPTopNavigationBar.class);
    }

    public NDPTopNavigation navTopNavigation() {
        extentLogger.logInfo("");
        return getNewPage(NDPTopNavigation.class);
    }

    public NDPProductDashboardPage clickProductByGameCode(String gameCode) {
        By selector = By.xpath("//td[contains(text(),'" + gameCode + "')]");
        waitForAndClickElement(selector);
        extentLogger.logInfo(gameCode);
        return getNewPage(NDPProductDashboardPage.class);
    }

    public NDPCreateProductPage createNewProduct() {
        waitForAndClickElement(uniqueElement);
        extentLogger.logInfo("");
        return getNewPage(NDPCreateProductPage.class);
    }

    public NDPProductDashboardPage clickProductByName(String productName) {
        String selector = String.format("//td//div[@class='product-name' and contains(text(), '%s')]", productName);
        waitForAndClickElement(By.xpath(selector));
        extentLogger.logInfo(productName);
        return getNewPage(NDPProductDashboardPage.class);
    }

    public int countProductByName(String productName) {
        String selector = String.format("//td//div[@class='product-name' and contains(text(), '%s')]", productName);
        int count = findElements(By.xpath(selector)).size();
        extentLogger.logInfo("" + count);
        return count;
    }

    public String getProductNameInRow(int row) {
        String selector = String.format("//tr[%s]//td//div[@class='product-name']", "" + row);
        String value = getText(By.xpath(selector));
        extentLogger.logInfo(value);
        return value;
    }

    public String getGameCodeInRow(int row) {
        String selector = String.format("//tr[%s]//td[2]", "" + row);
        String value = getText(By.xpath(selector));
        extentLogger.logInfo(value);
        return value;
    }

    public String getProductTypeInRow(int row) {
        String selector = String.format("//tr[%s]//td[3]", "" + row);
        String value = getText(By.xpath(selector));
        extentLogger.logInfo(value);
        return value;
    }

    public Boolean checkProductNameDisplay(String name) {
        extentLogger.logInfo("Check display:" + name);
        String selector = String.format("//tr//td//div[@class='product-name' and text()='%s']", "" + name);
        String value = getText(By.xpath(selector));
        if (!value.isBlank())
            return true;
        return false;
    }

    public Boolean onCardAOCProductIsDisplayed(String productName_Oncard, String initialCode_BaseProduct, String productType) {
        extentLogger.logInfo("productName_Oncard: " + productName_Oncard + "initialCode_BaseProduct: " + initialCode_BaseProduct + "productType: " + productType);
        return waitForElementVisible(By.xpath(String.format("//div[text()='%s']/ancestor::td/following-sibling::td[contains(text(),'%s')]/following-sibling::td[text()=' %s ']/ancestor::tr", productName_Oncard, initialCode_BaseProduct.substring(0, 4), productType))).isDisplayed();
    }

    public NDPMyProductsPage clickOnUserNameMenu() {
        extentLogger.logInfo("");
        waitForAndClickElement(lnkUserNameMenu);
        return this;
    }

    public NDPHomePage clickOnSignOutButton() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnSignOut);
        return getNewPage(NDPHomePage.class);
    }

    public NDPMyAccountPage clickManagerAccountButton() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnManagerAccount);
        return getNewPage(NDPMyAccountPage.class);
    }

    public NDPMyProductsPage clickEnglishButton() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnEnglish);
        return this;
    }

    public NDPMyProductsPage clickJapanButton() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnJapan);
        return this;
    }

    public boolean productCreatedIsDisplayed(String productName) {
        boolean value = false;
        if (!elementNotExists(By.xpath(String.format("//div[contains(text(),'%s')]", productName)))) {
            value = true;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public NDPMyProductsPage clickMyProducts() {
        waitForAndClickElement(lnkMyProducts);
        extentLogger.logInfo("");
        return getNewPage(NDPMyProductsPage.class);
    }

    public boolean productAppearsOnTheListUnderMyProducts(String productName) {
        boolean value = false;
        if (!elementNotExists(By.xpath(String.format("//td//div[@class='product-name' and contains(text(), '%s')]", productName)))) {
            value = true;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public String getSaleRegion() {
        String value = getText(saleRegion).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public String getTextColorOfSaleRegion() {
        WebElement element = driver.findElement(saleRegion);
        String value = element.getCssValue("color");
        extentLogger.logInfo(value);
        return value;
    }


}
