package net.nintendo.automation.ui.models.ndp;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NDPCreateProductPage extends CommonUIComponent {
    private static final By productTypeDropDownList = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_productType");
    private static final String page_title = "Create Product - Nintendo Developer Portal";

    private final By ddlPartyType = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_partyType");
    private final By ddlTargetDeliveryFormat = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_targetDeliveryFormat");
    private final By ddlCopyDataFromListId = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_parent-release");
    private final By ddlBaseProductListId = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_baseProduct");
    private final By txtProductName = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_productName");
    private final By txtProductNameJp = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_productNameJp");
    private final By txtProductNameKana = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_productNameKana");
    private final By ddlCommonProduct = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_commonTitle");
    private final By cboCopyDataFeatures = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_copyFeatureDataCheckbox");
    private final By btnCreate = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_createButton");
    private final By ddlUserGroup = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_organization");
    private final By ddlBasedProduct = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_baseProduct");
    private final By ddlCopyData = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_parent-release");
    private final By ddlCopyDataFromParentProduct = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_parent-projectProduct");
    private final By ddlGroupRole = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_organizationAccess");
    private final By cboChinaSaleRegion = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_targetSalesRegionsPhysical-sales-region-chinaCheckbox");
    private final By cboChinaDigitalSaleRegion = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_targetSalesRegionsDigital-sales-region-chinaCheckbox");
    private final By cboAsiaSaleRegion = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_targetSalesRegionsPhysical-sales-region-hong-kong-taiwan-koreaCheckbox");
    private final By cboAsiaDigitalSaleRegion = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_targetSalesRegionsDigital-sales-region-hong-kong-taiwan-koreaCheckbox");
    private final By cboAmericaSaleRegion = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_targetSalesRegionsPhysical-sales-region-americasCheckbox");
    private final By cboAmericaDigitalSaleRegion = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_targetSalesRegionsDigital-sales-region-americasCheckbox");
    private final By cboEuropeSaleRegion = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_targetSalesRegionsPhysical-sales-region-europeCheckbox");
    private final By cboEuropeDigitalSaleRegion = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_targetSalesRegionsDigital-sales-region-europeCheckbox");
    private final By cboJapanSaleRegion = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_targetSalesRegionsPhysical-sales-region-japanCheckbox");
    private final By cboJapanDigitalSaleRegion = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_targetSalesRegionsDigital-sales-region-japanCheckbox");

    public NDPCreateProductPage(WebDriver driver) {
        super(driver, productTypeDropDownList);
    }

    public NDPCreateProductPage selectProductType(String productType) {
        selectValueOnDropDown(productTypeDropDownList, productType);
        extentLogger.logInfo(productType);
        return this;
    }

    public NDPCreateProductPage selectPublishingRelationship(String publishingRelationType) {
        selectValueOnDropDown(ddlPartyType, publishingRelationType);
        extentLogger.logInfo(publishingRelationType);
        return this;
    }

    public NDPCreateProductPage selectTargetDeliveryFormat(String targetDeliveryFormat) {
        selectValueOnDropDown(ddlTargetDeliveryFormat, targetDeliveryFormat);
        extentLogger.logInfo(targetDeliveryFormat);
        return this;
    }

    public NDPCreateProductPage selectDigitalSalesRegion() {
        selectCheckbox(cboChinaDigitalSaleRegion, "true");
        return this;
    }

    public NDPCreateProductPage selectDigitalJapanSalesRegion() {
        selectCheckbox(cboJapanDigitalSaleRegion, "true");
        extentLogger.logInfo("Japan Digital");
        return this;
    }

    public NDPCreateProductPage selectDigitalEuropeSalesRegion() {
        selectCheckbox(cboEuropeDigitalSaleRegion, "true");
        extentLogger.logInfo("Europe Digital");
        return this;
    }

    public NDPCreateProductPage selectDigitalAmericasSalesRegion() {
        selectCheckbox(cboAmericaDigitalSaleRegion, "true");
        extentLogger.logInfo("Americas Digital");
        return this;
    }

    public NDPCreateProductPage selectDigitalAsiaSalesRegion() {
        selectCheckbox(cboAsiaDigitalSaleRegion, "true");
        extentLogger.logInfo("Asia Digital");
        return this;
    }

    public NDPCreateProductPage selectDigitalChinaSalesRegion() {
        selectCheckbox(cboChinaDigitalSaleRegion, "true");
        extentLogger.logInfo("China Digital");
        return this;
    }

    public NDPCreateProductPage selectPhysicalJapanSalesRegion() {
        selectCheckbox(cboJapanSaleRegion, "true");
        extentLogger.logInfo("Japan Physical");
        return this;
    }

    public NDPCreateProductPage selectPhysicalEuropeSalesRegion() {
        selectCheckbox(cboEuropeSaleRegion, "true");
        extentLogger.logInfo("Europe Physical");
        return this;
    }

    public NDPCreateProductPage selectPhysicalAmericasSalesRegion() {
        selectCheckbox(cboAmericaSaleRegion, "true");
        extentLogger.logInfo("Americas Physical");
        return this;
    }

    public NDPCreateProductPage selectPhysicalAsiaSalesRegion() {
        selectCheckbox(cboAsiaSaleRegion, "true");
        extentLogger.logInfo("Asia Physical");
        return this;
    }

    public NDPCreateProductPage selectPhysicalChinaSalesRegion() {
        selectCheckbox(cboChinaSaleRegion, "true");
        extentLogger.logInfo("China Physical");
        return this;
    }

    public NDPCreateProductPage selectProductName(String productName) {
        waitForAndClickElement(txtProductName);
        typeText(txtProductName, productName);
        typeTab(txtProductName);
        extentLogger.logInfo(productName);
        return this;
    }

    public NDPCreateProductPage selectProductNameJp(String productName) {
        waitForAndClickElement(txtProductNameJp);
        typeText(txtProductNameJp, productName);
        typeTab(txtProductNameJp);
        extentLogger.logInfo(productName);
        return this;
    }

    public NDPCreateProductPage selectProductNameKana(String productName) {
        waitForAndClickElement(txtProductNameKana);
        typeText(txtProductNameKana, productName);
        typeTab(txtProductNameKana);
        extentLogger.logInfo(productName);
        return this;
    }

    public NDPCreateProductPage selectCommonProduct(String commonProduct) {
        selectValueOnDropDown(ddlCommonProduct, commonProduct);
        extentLogger.logInfo(commonProduct);
        return this;
    }

    public NDPCreateProductPage selectUserGroup(String userGroup) {
        selectValueOnDropDown(ddlUserGroup, userGroup);
        extentLogger.logInfo(userGroup);
        return this;
    }

    public NDPCreateProductPage selectBasedProduct(String basedProduct) {
        By basedProductDropDownSelector = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_baseProduct");
        selectValueOnDropDown(basedProductDropDownSelector, basedProduct);
        return this;

    }

    public NDPCreateProductPage selectBasedProduct(Integer productId) {
        By basedProductDropDownSelector = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_baseProduct");
        selectOptionValueOnDropDown(basedProductDropDownSelector, "" + productId);
        extentLogger.logInfo("" + productId);
        return this;
    }

    public NDPCreateProductPage selectGroupRole(String groupRole) {
        selectValueOnDropDown(ddlGroupRole, groupRole);
        extentLogger.logInfo(groupRole);
        return this;
    }

    public NDPCreateProductPage selectCopyDataFromProduct(String productName) {
        selectValueOnDropDown(ddlCopyDataFromParentProduct, productName);
        extentLogger.logInfo(productName);
        return this;
    }

    public NDPCreateProductPage selectCopyDataFromRelease(String releaseVersion) {
        selectValueOnDropDown(ddlCopyData, releaseVersion);
        extentLogger.logInfo(releaseVersion);
        return this;
    }

    public NDPCreateProductPage selectCopyDataFromRelease(Integer releaseId) {
        By copyDataFromDropDownSelector = By.id("_noauserproductcreateportlet_WAR_noaprojectsportlet_parent-release");
        selectOptionValueOnDropDown(copyDataFromDropDownSelector, "" + releaseId);
        extentLogger.logInfo("" + releaseId);
        return this;
    }

    public String getProductTypeValue() {
        String value = getDropDownSelectedValue(productTypeDropDownList);
        extentLogger.logInfo(value);

        return value;
    }

    public boolean isPublishingRelationshipDisplayed() {
        boolean isDisplayed = getIsDisplayedNoWait(ddlPartyType);
        extentLogger.logInfo(Boolean.toString(isDisplayed));
        return isDisplayed;
    }

    public NDPMyProductsPage clickCreateButton() {
        waitForAndClickElement(btnCreate);
        extentLogger.logInfo("");
        return getNewPage(NDPMyProductsPage.class);
    }

    public Boolean baseProductAtTestConditionIsDisplayed(String productName_BaseProduct, String initialCode_BaseProduct) {
        extentLogger.logInfo(productName_BaseProduct + initialCode_BaseProduct);
        return waitForElementVisible(By.xpath(String.format("//select[@id='_noauserproductcreateportlet_WAR_noaprojectsportlet_baseProduct']//option[text()='%s (%s)']", productName_BaseProduct, initialCode_BaseProduct))).isDisplayed();
    }

    public Boolean baseProductAtTestConditionIsSelected(String productName_BaseProduct, String initialCode_BaseProduct) {
        extentLogger.logInfo(productName_BaseProduct + initialCode_BaseProduct);
        return waitForElementVisible(By.xpath(String.format("//select[@id='_noauserproductcreateportlet_WAR_noaprojectsportlet_baseProduct']//option[text()='%s (%s)']", productName_BaseProduct, initialCode_BaseProduct))).isSelected();
    }

    public NDPCreateProductPage selectBaseProduct(String baseProduct) {
        selectValueOnDropDown(ddlBaseProductListId, baseProduct);
        extentLogger.logInfo(baseProduct);
        return this;
    }

    public String getSelectedCopyDataFromValue() {
        String value = getSelectedValue(ddlCopyDataFromListId);
        extentLogger.logInfo(value);
        return value;
    }

    public NDPCreateProductPage selectCopyDataFrom(String releaseVersion) {
        selectValueOnDropDown(ddlCopyDataFromListId, releaseVersion);
        extentLogger.logInfo(releaseVersion);
        return this;
    }

    public NDPCreateProductPage clickCopyDataFeatures() {
        selectCheckbox(cboCopyDataFeatures);
        return this;
    }
}
