package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.email.ViewHtmlInbucketLotcheckEmailPage;
import net.nintendo.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LotcheckReportSubmissionOverview extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@data-testid='submission-overview-page']//h2[text()='Overview']");
    private static final String pageTitle = "Overview Submission: LCR - LCMS";

    private final By lblGameCodeValue = By.xpath("//table[@class='table-matrix']//a[contains(@href,'submission-level/overview')]");
    private static By lblTitleOverPage = By.xpath("//div[@data-testid='submission-overview-page']//h2");
    private static By rowStripedElement = By.xpath("//div[@id='overview-container']/dl[@class='row striped']");
    private final By btnMenuIcon = By.xpath("//button[@id='dropdownMenuLink']");
    private final By btnChangeLcoOption = By.xpath("//button[@data-testid=\"change-lotcheck-owner-button\"]");
    private final By lblAlertSuccess = By.xpath("//div[@role='alert']");
    private final By lblLotcheckOwnerValue = By.xpath("//dd[2]");
    private final By pnlLoading = By.xpath("//div[@data-testid=\"spinner-loading\"]");
    private final By lnkLotcheckIssueTab = By.xpath("//a[contains(@href,'lotcheck-issue')]");
    private final By lblLotcheckTestDueDate = By.xpath("////dt[text()= 'Test Due Date']/preceding-sibling::dd");
    private final By lnkGotoProductLevel = By.xpath("//a[@class='go-to-product-level']");
    private final By lnkLotcheckRequiredApprovalTab = By.xpath("//a[contains(@href,'required-approvals')]");
    private static By iconLoading = By.xpath("//div[@data-testid='spinner-loading']");
    private final By pnlLotcheckTestDueDate = By.xpath("//dt[text()= 'Test Due Date']/preceding-sibling::dd");
    private final By lblRemainTime = By.xpath("//tr[@class='activeRow']//td[2]");
    private final By pnlRemainingTime = By.xpath("//tr[@class='activeRow']//td[2]//br");
    private final By lotcheckOwnerOriginal = By.xpath("//dd[1]");
    private final By homeRegionProduct = By.xpath("//dd[3]");
    private final By homeRegionOrganization = By.xpath("//dd[4]");
    private final By loadingIcon = By.xpath("//div[@data-testid=\"spinner-loading\"]");
    private final By lotcheckIssueTab = By.xpath("//a[contains(@href,'lotcheck-issue')]");
    private final By lotcheckTestDueDate = By.xpath("////dt[text()= 'Test Due Date']/preceding-sibling::dd");
    private final By linkGotoProductLevel = By.xpath("//a[@class='go-to-product-level']");
    private final By lotcheckRequiredApprovalTab = By.xpath("//a[contains(@href,'required-approvals')]");
    private final By menuIcon = By.xpath("//button[@id='dropdownMenuLink']");
    private final By errorMessage = By.xpath("//div[@data-testid='multiple-error-messages-modal']//div[@class='alert alert-danger']");
    private final By btnClose_errorDialog = By.xpath("//button[@class='close']");
    private final By btnSourceLanguageDisplay = By.xpath("//button[@data-testid='sourceDisplay-button']");
    private final By btnTargetLanguageDisplay = By.xpath("//button[@data-testid='targetDisplay-button']");
    private final By btnSourceAndTargetLanguageDisplay = By.xpath("//button[@data-testid='sourceAndTargetDisplay-button']");
    private final By developerCommentsSourceFieldName = By.xpath("//li[@data-testid='submission-overview-all-else-source']//span[@class='translation-display-label']");
    private final By developerCommentsTargetFieldName = By.xpath("//li[@data-testid='submission-overview-all-else-target']//span[@class='translation-display-label']");
    private final By developerCommentsTargetValue = By.xpath("//li[@data-testid='submission-overview-all-else-target']//span[2]");
    private final By developerCommentsSourceValue = By.xpath("//li[@data-testid='submission-overview-all-else-source']//span[2]");
    private final By developerCommentsTranslateStatus = By.xpath("//dt//span[contains(@class,'badge badge')]");
    private final By btnExportAll = By.xpath("//button[@class='btn dropdown-item'][1]");
    private final By btnImportAll = By.xpath("//button[@class='btn dropdown-item'][2]");
    private final By lblSubmission = By.xpath("//h1[@class='mt-3']");

    public LotcheckReportSubmissionOverview(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public String getTitlePage() {
        String titlePage = getText(lblTitleOverPage);
        extentLogger.logInfo(titlePage);
        return titlePage;
    }

    public LotcheckReportNav navBar() {
        return getNewPage(LotcheckReportNav.class);
    }

    public LotcheckReportMatrix matrix() {
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportMatrix.class);
    }

    public HashMap<SubmissionOverviewAttribute, String> getOverviewAttributes() {
        extentLogger.logInfo("Getting Overview Attributes");
        HashMap<SubmissionOverviewAttribute, String> overviewAttributes = new HashMap<>();
        WebElement rowStripped = waitForElementVisible(rowStripedElement);
        List<WebElement> attributeNames = rowStripped.findElements(By.tagName("dt"));
        List<WebElement> attributesValues = rowStripped.findElements(By.tagName("dd"));

        for (int i = 0; i < attributeNames.size(); i++) {
            SubmissionOverviewAttribute key = SubmissionOverviewAttribute.fromString(
                    attributeNames.get(i).getText());
            if (key != null) {
                overviewAttributes.put(key, attributesValues.get(i).getText());
            }
        }
        return overviewAttributes;
    }

    public enum SubmissionOverviewAttribute {
        LotcheckOwner_Original("Lotcheck Owner (Original)"),
        LotcheckOwner("Lotcheck Owner"),
        HomeRegion_Product("Home Region (Product)"),
        HomeRegion_Organization("Home Region (Organization)"),
        TestDueDate("Test Due Date"),
        InternalStatus("Internal Status"),
        PlannedSubmissionDate("Expected Submission Date"),
        PlannedReleaseDate("Expected Release Date"),
        SubmittedBy("Submitted By"),
        TestRequestedDate("Test Requested Date"),
        TestSetupDate("Test Setup Date"),
        TestSetupDate_Final("Test Setup Date (Final)"),
        TestStartDate("Test Start Date"),
        TestStartDate_Final("Test Start Date (Final)"),
        TestEndDate("Test End Date"),
        TestEndDate_Final("Test End Date (Final)"),
        SubmissionJudgmentDate("Submission Judgment Date"),
        SubmissionJudgmentDate_Final("Submission Judgment Date (Final)"),
        ChangeSubmissionJudgment("Change Submission Judgment"),
        DeveloperComments_Translate("Developer Comments Translate"),
        DeveloperComments("Developer Comments"),
        CompactedAppIncluded("Compacted App Included");

        private final String value;

        SubmissionOverviewAttribute(String value) {
            this.value = value;
        }

        public static SubmissionOverviewAttribute fromString(String s) {
            return Arrays.stream(SubmissionOverviewAttribute.values())
                    .filter(v -> v.value.equals(s))
                    .findFirst()
                    .orElse(null);
        }
    }

    public String getGameCodeOfSubmission() {
        String value = getText(lblGameCodeValue).substring(0, 11);
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckReportSubmissionOverview clickMenu() {
        waitForAndClickElement(btnMenuIcon);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckChangeLotcheckOwnerDialog selectChangeLCO() {
        waitForAndClickElement(btnChangeLcoOption);
        extentLogger.logInfo("");
        return getNewPage(LotcheckChangeLotcheckOwnerDialog.class);
    }

    public String getMessageSuccessully() {
        String value = getText(lblAlertSuccess);
        extentLogger.logInfo(value);
        return value;
    }

    public String getLotCheckTestDueDate() {
        String value = getText(lblLotcheckTestDueDate);
        extentLogger.logInfo(value);
        return value;
    }

    public String getLotcheckOwnerValue() {
        String value = getText(lblLotcheckOwnerValue);
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckReportSubmissionOverview refreshPageSLCMS() {
        extentLogger.logInfo("");
        driver.navigate().refresh();
        waitForElementVisible(pnlLoading, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        waitForElementNotVisible(pnlLoading, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        waitForElementVisible(uniqueElement, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        return this;
    }

    public LotcheckReportSubmissionLotcheckIssues clickLotchekIssueTab() {
        waitForAndClickElement(lnkLotcheckIssueTab);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionLotcheckIssues.class);
    }

    public LotcheckReportProductProductInformation gotoProductLevel() {
        extentLogger.logInfo("");
        waitForAndClickElement(lnkGotoProductLevel);
        return getNewPage(LotcheckReportProductProductInformation.class);
    }

    public LotcheckReportSubmissionRequiredApproval clickRequiredApprovalTab() {
        waitForAndClickElement(lnkLotcheckRequiredApprovalTab);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportSubmissionRequiredApproval.class);
    }

    public LotcheckReportSubmissionOverview waitingForIconNotVisible() {
        extentLogger.logInfo("");
        waitForElementNotVisible(iconLoading, Duration.ofMinutes(15));
        return this;
    }

    public boolean isTestDueDateDisplayed() {
        boolean value = false;
        if (!elementNotExists(pnlLotcheckTestDueDate)) {
            value = true;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public String getRemainingTime() {
        String value = getText(lblRemainTime);
        extentLogger.logInfo(value);
        return value;
    }

    public boolean isRemainingTimeDisplayed() {
        boolean value = false;
        if (!elementNotExists(pnlRemainingTime)) {
            value = true;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    public String getLotcheckOwnerOriginal() {
        String value = getText(lotcheckOwnerOriginal);
        extentLogger.logInfo(value);
        return value;
    }

    public String getHomeRegionProduct() {
        String value = getText(homeRegionProduct);
        extentLogger.logInfo(value);
        return value;
    }

    public String getHomeRegionOrganization() {
        String value = getText(homeRegionOrganization);
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckReportSubmissionOverview waitPageLoaded() {
        waitForElementNotVisible(menuIcon, WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        waitForElementVisible(menuIcon, WaitTimes.FEATURES_SAVE_TIMEOUT_SECS);
        return this;
    }

    public LotcheckReportSubmissionOverview checkTargetAndSourceLanguageDisplayed() {
        waitDocumentReady();
        if (!elementNotExists(btnSourceAndTargetLanguageDisplay)) {
            clickMenu();
            WaitUtils.idle(2000);
            waitForAndClickElement(btnSourceAndTargetLanguageDisplay);
            WaitUtils.idle(2000);
        }
        extentLogger.logInfo("");
        return this;
    }

    public String getDeveloperCommentsTranslateStatus() {
        String value = getText(developerCommentsTranslateStatus).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public String getColorOfDeveloperCommentsTranslateStatus() {
        String value = driver.findElement(developerCommentsTranslateStatus).getCssValue("background-color");
        extentLogger.logInfo(value);
        return value;
    }

    public String getDeveloperCommentsSourceFieldName() {
        String value = getText(developerCommentsSourceFieldName).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public String getDeveloperCommentsTargetFieldName() {
        String value = getText(developerCommentsTargetFieldName).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public String getDeveloperCommentsSourceValue() {
        String value = getText(developerCommentsSourceValue).trim();
        extentLogger.logInfo(value);
        return value;
    }

    public String getDeveloperCommentsTargetValue() {
        String value = driver.findElements(developerCommentsTargetValue).get(0).getText();
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckReportExportAllDialog clickExportAll() {
        waitForAndClickElement(btnExportAll);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportExportAllDialog.class);

    }

    public LotcheckReportImportAllDialog clickImportAll() {
        waitForAndClickElement(btnImportAll);
        extentLogger.logInfo("");
        return getNewPage(LotcheckReportImportAllDialog.class);

    }

    public long getFileSizeFromPath(String fileName) {
        long size = getFileSize(fileName);
        for (int i = 0; i < 5; i++) {
            if (size == 0L) {
                WaitUtils.idle(10000);
                size = getFileSize(fileName);
            } else {
                i = 5;
                return size;
            }
        }
        extentLogger.logInfo(fileName);
        return 0L;
    }

    private long getFileSize(String fileName) {
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + fileName;
        try {
            return Files.size(Paths.get(path));

        } catch (IOException e) {
            return 0L;
        }
    }

    public String getXlfData(String fileName, String nodeParent, String nodeChild) {
        String value = "";
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + fileName;
        long size = getFileSize(fileName);
        for (int i = 0; i < 5; i++) {
            if (size == 0L) {
                WaitUtils.idle(10000);
                size = getFileSize(fileName);
            } else {
                i = 5;
            }
        }

        try {
            File file = new File(path);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeList1 = doc.getElementsByTagName(nodeParent);
            int k = 0;
            for (int i = 0; i < nodeList1.getLength(); i++) {
                if (nodeList1.item(i).getAttributes().getNamedItem("resname").getNodeValue().contains("OV_3281338_developerComments")) {
                    k = i;
                }
            }
            NodeList nodeList2 = doc.getElementsByTagName(nodeChild);
            value = nodeList2.item(k).getTextContent();
        } catch (Exception e) {
            e.printStackTrace();
        }

        extentLogger.logInfo(nodeChild + ": " + value);
        return value;
    }

    public String getXlfData(String fileName, String field) {
        String value = "";
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + fileName;
        long size = getFileSize(fileName);
        for (int i = 0; i < 5; i++) {
            if (size == 0L) {
                WaitUtils.idle(10000);
                size = getFileSize(fileName);
            } else {
                i = 5;
            }
        }

        try {
            File file = new File(path);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("xliff");
            Node node = nodeList.item(0);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                value = ((Element) node).getElementsByTagName("file").item(0).getAttributes().getNamedItem(field).getNodeValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        extentLogger.logInfo(field + ": " + value);
        return value;
    }

    public LotcheckReportSubmissionOverview editXlfFile(String fileName) {
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + fileName;
        try {
            File file = new File(path);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeList1 = doc.getElementsByTagName("trans-unit");
            int k = 0;
            for (int i = 0; i < nodeList1.getLength(); i++) {
                if (nodeList1.item(i).getAttributes().getNamedItem("resname").getNodeValue().contains("OV_3281338_developerComments")) {
                    k = i;
                }
            }
            NodeList nodeList2 = doc.getElementsByTagName("target");
            Node node = nodeList2.item(k);
            node.appendChild(doc.createTextNode("Test translator"));
            node.appendChild(doc.createElement("br"));
            node.appendChild(doc.createTextNode("Developer comments test"));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(file);
            transformer.transform(domSource, streamResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        extentLogger.logInfo(fileName);
        return this;
    }

    public String getErrorMessage() {
        waitForElementVisible(errorMessage, WaitTimes.WAIT_FOR_UNIQUE_ELEMENT_VISIBILITY_SECS);
        String value = getText(errorMessage);
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckReportSubmissionOverview clickCloseDialog() {
        waitForElementVisible(btnClose_errorDialog, WaitTimes.WAIT_FOR_ELEMENT_NOT_VISIBLE_TIMEOUT_SECS);
        waitDocumentReady();
        WaitUtils.idle(2000);
        waitForAndClickElement(btnClose_errorDialog);
        extentLogger.logInfo("");
        return this;
    }

    public String getReleaseVersionOfSubmission() {
        String value = getText(lblGameCodeValue).substring(13, 15);
        extentLogger.logInfo(value);
        return value;
    }

    public String getSubmissionVersionOfSubmission() {
        String value = getText(lblGameCodeValue).substring(16, 18);
        extentLogger.logInfo(value);
        return value;
    }

    public ViewHtmlInbucketLotcheckEmailPage backToPreviousPage() {
        driver.navigate().back();
        return getNewPage(ViewHtmlInbucketLotcheckEmailPage.class);
    }

    public boolean isLotcheckReportSubmissionDisplayed() {
        boolean value = false;
        if (!elementNotExists(lblSubmission)) {
            value = true;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

}
