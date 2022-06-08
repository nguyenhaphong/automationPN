package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

public class LotcheckReportSubmissionProductInformation extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@data-testid='product-info-container']");
    private static final String pageTitle = "Product Information Submission: LCR - LCMS";

    private static By btnExpandReleaseInformation = By.xpath("//button[text()='Release Information']");
    private static By btnExpandAgeRating = By.xpath("//button[@aria-controls='ageRatingsInfo']");
    private static By lstNumberRating = By.xpath("//div[@id='ageRatingsInfo']//table/tbody/tr");
    private static By lstRatingValue = By.xpath("//div[@id='ageRatingsInfo']//table/tbody/tr/td[2]");
    private static By lstRatingDescriptors = By.xpath("//div[@id='ageRatingsInfo']//table/tbody/tr/td[4]");
    private static By lstAgeRatingCertificate = By.xpath("//div[@id='ageRatingsInfo']//table/tbody/tr/td/button");
    private static By lblTitleProductInfoPage = By.xpath("//div[@data-testid='product-info-container']//h2");
    private static By lblTargetSalesRegionsDigital = By.xpath("//dt[contains(@class,'col-sm-4')]/../dd[3]//li[@class='']");
    private static By lblStatus = By.xpath("//dt[contains(@class,'col-sm-4')]/../dd[4]");
    private static By lblDisplayVersion = By.xpath("//dt[contains(@class,'col-sm-4')]/../dd[5]");
    private static By lblReleaseVersion = By.xpath("//dt[contains(@class,'col-sm-4')]/../dd[6]");
    private static By lblSubmissionVersion = By.xpath("//dt[contains(@class,'col-sm-4')]/../dd[7]");
    private static By lblReleaseType = By.xpath("//dt[contains(@class,'col-sm-4')]/../dd[8]");
    private static By lblExpectedSubmissionDate = By.xpath("//dt[contains(@class,'col-sm-4')]/../dd[9]");
    private static By lblExpectedReleaseDate = By.xpath("//dt[contains(@class,'col-sm-4')]/../dd[10]");
    private static By lblFreeToPlay = By.xpath("//dt[contains(@class,'col-sm-4')]/../dd[11]");
    private static By lblProvisionalTitleEnglishOrJapanese = By.xpath("//div[@data-testid='features-all-else-value']");
    private static By btnAgeRatings = By.xpath("//button[text()='Age Ratings']");
    private static By lstRatingAgency = By.xpath("//table[@class='table table-nin table-striped']//tr//td[1]");
    private static By lstRating = By.xpath("//table[@class='table table-nin table-striped']//tr//td[2]");
    private static By lstRatingMethod = By.xpath("//table[@class='table table-nin table-striped']//tr//td[5]");
    private static By lblDemoType = By.xpath("//dt[contains(@class,'col-sm-4')]/../dd[12]");
    private final By icon = By.xpath("//table[@class='table table-nin table-striped']//tr//td[3]//img");
    private static By ageRatingCertificateText = By.xpath("//table[@class='table table-nin table-striped']//tr//td[6]");
    private final By freeToPlayFieldName = By.xpath("//dt[@class='col-sm-4'][11]");
    private static By expandReleaseInformationElement = By.xpath("//button[text()='Release Information']");

    public LotcheckReportSubmissionProductInformation(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public LotcheckReportNav navBar() {
        return getNewPage(LotcheckReportNav.class);
    }

    public LotcheckReportSubmissionProductInformation expandReleaseInformation() {
        expandContainer(btnExpandReleaseInformation);
        return this;
    }

    public String getTitleProductInfoPage() {
        String titlePage = getText(lblTitleProductInfoPage);
        extentLogger.logInfo(titlePage);
        return titlePage;
    }

    public String getSubmissionType() {
        return getText(By.xpath("//dt[contains(text(),'Submission Type')]/../dd"));
    }

    public String getCardSize() {
        return getText(By.xpath("//div[contains(text(),'Card Size')]/../../dt"));
    }

    public LotcheckReportSubmissionProductInformation expandAgeRating() {
        extentLogger.logInfo("");
        expandContainer(btnExpandAgeRating);
        return this;
    }

    public String getTargetSalesRegionsDigital() {
        String targetSalesRegionsDigital = getText(lblTargetSalesRegionsDigital);
        extentLogger.logInfo(targetSalesRegionsDigital);
        return targetSalesRegionsDigital;
    }

    public String getStatus() {
        String status = getText(lblStatus);
        extentLogger.logInfo(status);
        return status;
    }

    public String getDisplayVersion() {
        String displayVersion = getText(lblDisplayVersion);
        extentLogger.logInfo(displayVersion);
        return displayVersion;
    }

    public String getReleaseVersion() {
        String releaseVersion = getText(lblReleaseVersion);
        extentLogger.logInfo(releaseVersion);
        return releaseVersion;
    }

    public String getSubmissionVersion() {
        String submissionVersion = getText(lblSubmissionVersion);
        extentLogger.logInfo(submissionVersion);
        return submissionVersion;
    }

    public String getReleaseType() {
        String releaseType = getText(lblReleaseType);
        extentLogger.logInfo(releaseType);
        return releaseType;
    }

    public String getExpectedSubmissionDate() {
        String expectedSubmissionDate = getText(lblExpectedSubmissionDate);
        extentLogger.logInfo(expectedSubmissionDate);
        return expectedSubmissionDate;
    }

    public String getExpectedReleaseDate() {
        String expectedReleaseDate = getText(lblExpectedReleaseDate);
        extentLogger.logInfo(expectedReleaseDate);
        return expectedReleaseDate;
    }

    public String getFreeToPlay() {
        String freeToPlay = getText(lblFreeToPlay);
        extentLogger.logInfo(freeToPlay);
        return freeToPlay;
    }

    public String getFreeToPlayFieldName() {
        String value = getText(freeToPlayFieldName).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckLandingPage navLandingPage() {
        return getNewPage(LotcheckLandingPage.class);
    }

    public String getProvisionalTitleEnglishOrJapanese() {
        String provisionalTitle = getText(lblProvisionalTitleEnglishOrJapanese);
        extentLogger.logInfo(provisionalTitle);
        return provisionalTitle;
    }

    public String getUseOfROMsThatDifferBetweenRegions(String option) {
        String useOfROMsThatDifferBetweenRegions = getText(By.xpath("//div[contains(text(),'" + option + "')]/../../dt"));
        extentLogger.logInfo(useOfROMsThatDifferBetweenRegions);
        return useOfROMsThatDifferBetweenRegions;
    }

    public String getLanguageUsedForTextEntryInFeatures(String option) {
        String languageUsedForTextEntryInFeatures = getText(By.xpath("//div[contains(text(),'" + option + "')]/../../dt"));
        extentLogger.logInfo(languageUsedForTextEntryInFeatures);
        return languageUsedForTextEntryInFeatures;
    }

    public int collectNumberRating() {
        extentLogger.logInfo("collect Number Rating");
        List<WebElement> ratingAgency = findElements(lstNumberRating);
        int num = ratingAgency.size();
        return num;
    }

    public String getRatingValue(int i) {
        extentLogger.logInfo("get Rating Value");
        List<WebElement> ratingValue = findElements(lstRatingValue);
        return ratingValue.get(i).getText().trim();
    }

    public String getRatingDescriptors(int i) {
        extentLogger.logInfo("get Rating Descriptors");
        List<WebElement> ratingDescriptors = findElements(lstRatingDescriptors);
        return ratingDescriptors.get(i).getText().trim();
    }

    public LotcheckReportSubmissionProductInformation selectDownloadAgeRatingCertificate() {
        extentLogger.logInfo("select Download Age Rating Certificate");
        List<WebElement> ratingDescriptors = findElements(lstAgeRatingCertificate);
        ratingDescriptors.get(0).click();
        return this;
    }

    public LotcheckReportSubmissionProductInformation expandAgeRatings() {
        extentLogger.logInfo("");
        expandContainer(btnAgeRatings);
        return this;
    }

    public LotcheckReportSubmissionProductInformation waitOpenAgeRatings() {
        waitForElementNotVisible(lstRatingAgency, Duration.ofMinutes(20));
        return this;
    }

    public String getAgeRatingsRatingAgency(int locationRatingAgency) throws InterruptedException {
        By selector = lstRatingAgency;
        List<WebElement> rowsTable = findElements(selector);
        WaitUtils.idle(1000);
        String valueRatingAgency = rowsTable.get(locationRatingAgency).getText();
        extentLogger.logInfo("Age Ratings: " + valueRatingAgency);
        return valueRatingAgency;
    }

    public String getRating(int locationRating) {
        By selector = lstRating;
        List<WebElement> rowsTable = findElements(selector);
        String valueRating = rowsTable.get(locationRating).getText();
        extentLogger.logInfo(valueRating);
        return valueRating;
    }

    public String getRatingMethod(int locationRatingMethod) {
        By selector = lstRatingMethod;
        List<WebElement> rowsTable = findElements(selector);
        String valueRatingMethod = rowsTable.get(locationRatingMethod).getText();
        extentLogger.logInfo(valueRatingMethod);
        return valueRatingMethod;
    }

    public String getRatingByNumber(int locationRating) {
        By selector = lstRating;
        List<WebElement> rowsTable = findElements(selector);
        String valueRating = rowsTable.get(locationRating).getText();
        extentLogger.logInfo(valueRating);
        return valueRating;
    }

    public String getRatingMethodByNumber(int locationRatingMethod) {
        By selector = lstRatingMethod;
        List<WebElement> rowsTable = findElements(selector);
        String valueRatingMethod = rowsTable.get(locationRatingMethod).getText();
        extentLogger.logInfo(valueRatingMethod);
        return valueRatingMethod;
    }

    public String getDemoType() {
        String demoType = getText(lblDemoType);
        extentLogger.logInfo(demoType);
        return demoType;
    }

    public String getAgeRatingCertificate(int locationRatingMethod) {
        By selector = ageRatingCertificateText;
        List<WebElement> rowsTable = findElements(selector);
        String valueRatingMethod = rowsTable.get(locationRatingMethod).getText();
        extentLogger.logInfo(valueRatingMethod);
        return valueRatingMethod;
    }

    public String getSrcIconAgeRating() {
        String srcIcon = getAttributes(icon, "src");
        String ratingIcon = srcIcon.split("/")[8].split(".png")[0] + ".png";
        extentLogger.logInfo(ratingIcon);
        return ratingIcon;
    }

    public LotcheckReportSubmissionProductInformation waitPageLoaded() {
        waitForElementNotVisible(freeToPlayFieldName, WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        waitForElementVisible(expandReleaseInformationElement, WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        return this;
    }

}