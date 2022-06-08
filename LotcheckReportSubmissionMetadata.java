package net.nintendo.automation.ui.models.lcms.submission_reports;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LotcheckReportSubmissionMetadata extends CommonUIComponent {
    private final By lblOncardaocXmlFile = By.xpath("//li[@id='oncardaoc.xml']");
    private static final String pageTitle = "Metadata Submission: LCR - LCMS";

    private static final By uniqueElement = By.xpath("//div[@data-testid='metadata-container']");
    private final By downloadProdXcieFileElement = By.xpath("//li[@id='prod.xcie.result.xml']/ancestor::ul/ancestor::div[@class='col-xs-12 col-md-8']/following-sibling::div[@class='col-xs-8 col-md-4 text-right']//button");
    private final By developmentROMIDHashElement = By.xpath("//div[@class='content-section'][1]//dd[@class='col-sm-8'][4]");
    private final By loadingIcon = By.xpath("//div[@data-testid=\"spinner-loading\"]");
    private final By downloadOncardaocXmlFileButtonElement = By.xpath("//li[@id='oncardaoc.xml']/ancestor::ul/ancestor::div[@class='col-xs-12 col-md-8']/following-sibling::div[@class='col-xs-8 col-md-4 text-right']//button");
    private final By oncardaocXmlFileElement = By.xpath("//li[@id='oncardaoc.xml']");
    private final By prodXcieFileElement = By.xpath("//li[@id='prod.xcie.result.xml']");
    private final By listSubProgram = By.xpath("//div[@class='mb-2 font-weight-bold']");
    private final By expandCollapseSubProgram = By.xpath("//button[@id='subDevRomXmls-label']");
    private final By downloadSubProgramButtons = By.xpath("//div[@id='subDevRomXmls-accordion']//button[@class='btn btn-link download']");
    private final By listXmlFileOfSubProgramElement = By.xpath("//div[@id='subDevRomXmls-accordion']//div[@class='MuiTypography-root MuiTreeItem-label MuiTypography-body1']");
    private final By fileNameAtRomInfo = By.xpath("//h3[contains(text(),'Encryption ROM Info (Card)')]/../div[1]//dt[text()='File Name']/..//dd[1]");
    private final By fileNameAtEncryptionROMInfoDL = By.xpath("//h3[@class='mt-4']/../div[2]//dt[text()='File Name']/..//dd[1]");
    private final By fileNameAtEncryptionROMInfoCard = By.xpath("//h3[@class='mt-4']/../div[3]//dt[text()='File Name']/..//dd[1]");


    public LotcheckReportSubmissionMetadata(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public String getValueOnCnmtXmlFile(String index) {
        String value = getTextMetadata(By.xpath(String.format("//li[@id='AddOnContent']/following::li[%s]", index)));
        value = value.split(":")[1].trim();
        extentLogger.logInfo(value);
        return value;
    }

    public String getValueOnOncardaocXml(String field, String index) {
        String actualValue = "";
        if (field == "Type_Application") {
            actualValue = getTextMetadata(By.xpath("//li[@id='Application']//div[@class='MuiTypography-root MuiTreeItem-label MuiTypography-body1']"));

        } else if (field == "Type_AOC") {
            actualValue = getTextMetadata(By.xpath("//li[@id='AddOnContent']//div[@class='MuiTypography-root MuiTreeItem-label MuiTypography-body1']"));
        } else if (field.contains("AOC")) {
            actualValue = getTextMetadata(By.xpath(String.format("//li[@id='AddOnContent']/following::li[%s]//div[@class='MuiTypography-root MuiTreeItem-label MuiTypography-body1']", index)));
        } else {
            actualValue = getTextMetadata(By.xpath(String.format("//li[@id='Application']/following::li[%s]//div[@class='MuiTypography-root MuiTreeItem-label MuiTypography-body1']", index)));
        }
        actualValue = actualValue.split(":")[1].trim();
        extentLogger.logInfo(field + ": " + actualValue);
        return actualValue;
    }

    public LotcheckReportSubmissionMetadata clickExpandXmlFile(String file) {
        expandContainer(By.xpath(String.format("//li[contains(@id,'%s')]", file)));
        WaitUtils.idle(5000);
        extentLogger.logInfo(file);
        return this;
    }

    public LotcheckReportSubmissionMetadata clickCollapseXmlFile(String file) {
        waitForElementAndClick(By.xpath(String.format("//li[contains(@id,'%s')]/div/div[@class='MuiTreeItem-iconContainer']", file)));
        WaitUtils.idle(3000);
        extentLogger.logInfo(file);
        return this;
    }

    public String getDevelopmentROMIDHash() {
        String value = getText(developmentROMIDHashElement);
        extentLogger.logInfo(value);
        return value;
    }

    public Boolean oncardaocXmlFileIsDisplayed() {
        extentLogger.logInfo("");
        return waitForElementVisible(oncardaocXmlFileElement).isDisplayed();
    }

    public Boolean prodXcieFileIsDisplayed() {
        extentLogger.logInfo("");
        return waitForElementVisible(prodXcieFileElement).isDisplayed();
    }

    public LotcheckReportSubmissionMetadata clickDownloadOncardaocXmlFile(String fileName) {
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + fileName;
        checkDeleteFile(fileName);
        waitForElementAndClick(downloadOncardaocXmlFileButtonElement);
        WaitUtils.idle(3000);
        extentLogger.logInfo(path);
        return this;
    }

    public LotcheckReportSubmissionMetadata clickDownloadProdXcieFile(String fileName) {
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + fileName;
        checkDeleteFile(fileName);
        refreshPageSLCMS();
        waitForElementVisible(downloadProdXcieFileElement, WaitTimes.WAIT_FOR_DOCUMENT_READY_TIMEOUT_SECS);
        waitForElementAndClick(downloadProdXcieFileElement);
        WaitUtils.idle(3000);
        extentLogger.logInfo(path);
        return this;
    }

    private long getFileSize(String fileName) {
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + fileName;
        try {
            return Files.size(Paths.get(path));

        } catch (IOException e) {
            return 0L;
        }
    }

    public long getFileSizeFromPath(String fileName) {
        WaitUtils.waitUntilComplete(1000L, 400000L, fileName, (input) -> {
            return getFileSize(input) > 0;
        });
        extentLogger.logInfo(fileName);
        return getFileSize(fileName);
    }

    private LotcheckReportSubmissionMetadata checkDeleteFile(String fileName) {
        if (getFileSize(fileName) != 0) {
            String path = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator;
            File file = new File(System.getProperty("user.dir") + path + fileName);
            file.delete();
        }
        extentLogger.logInfo(fileName);
        return this;
    }

    public Map<String, String> getXmlData(String fileName) {
        Map<String, String> xmlData = new HashMap<>();
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + fileName;
        long sise = getFileSizeFromPath(fileName);
        if (sise != 0L) {
            try {
                File file = new File(path);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(file);
                doc.getDocumentElement().normalize();
                NodeList nodeList = doc.getElementsByTagName("OriginalAddOnContentMeta");
                Node node = nodeList.item(0);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    xmlData.put("Type_AOC", ((Element) node).getElementsByTagName("Type").item(0).getTextContent());
                    xmlData.put("Id_AOC", ((Element) node).getElementsByTagName("Id").item(0).getTextContent());
                    xmlData.put("Version_AOC", ((Element) node).getElementsByTagName("Version").item(0).getTextContent());
                    xmlData.put("Digest_AOC", ((Element) node).getElementsByTagName("Digest").item(0).getTextContent());
                    xmlData.put("Tag_AOC", ((Element) node).getElementsByTagName("Tag").item(0).getTextContent());
                }
                NodeList nodeList2 = doc.getElementsByTagName("Application");
                Node node2 = nodeList2.item(0);
                if (node2.getNodeType() == node2.ELEMENT_NODE) {
                    xmlData.put("Type_Application", ((Element) node2).getElementsByTagName("Type").item(0).getTextContent());
                    xmlData.put("Id_Application", ((Element) node2).getElementsByTagName("Id").item(0).getTextContent());
                    xmlData.put("Version_Application", ((Element) node2).getElementsByTagName("Version").item(0).getTextContent());
                    xmlData.put("Digest_Application", ((Element) node2).getElementsByTagName("Digest").item(0).getTextContent());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        extentLogger.logInfo("");
        return xmlData;
    }

    public LotcheckReportSubmissionMetadata refreshPageSLCMS() {
        extentLogger.logInfo("");
        driver.navigate().refresh();
        waitForElementVisible(loadingIcon, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        waitForElementNotVisible(loadingIcon, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        waitForElementVisible(developmentROMIDHashElement, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        return this;
    }

    public int getNumberOfSubProgram() {
        List<WebElement> elements = findElements(listSubProgram);
        int numberOfSubProgram = elements.size();
        extentLogger.logInfo(String.valueOf(numberOfSubProgram));
        return numberOfSubProgram;
    }

    public int getNumberOfXmlFiles(String file) {
        List<WebElement> elements = findElements(By.xpath(String.format("//div[@class='mb-2 font-weight-bold']/following::div//li[contains(@id,'%s')]", file)));
        int numberOfFiles = elements.size();
        extentLogger.logInfo(String.valueOf(numberOfFiles));
        return numberOfFiles;
    }

    public String getStateOfOverallSubProgram() {
        String value = getAttributes(expandCollapseSubProgram, "aria-expanded");
        extentLogger.logInfo(value);
        return value;
    }

    public LotcheckReportSubmissionMetadata clickOnSubProgramXmlFileButton() {
        waitForAndClickElement(expandCollapseSubProgram);
        waitDocumentReady();
        WaitUtils.idle(3000);
        extentLogger.logInfo("");
        return this;
    }

    public boolean isIdOffsetOfControlTypeIsAscending() {
        boolean value = false;
        for (int i = 0; i < 10; i++) {
            String idOffset_01 = String.valueOf(i);
            String idOffset_02 = String.valueOf(i + 1);
            if (checkIdOffsetOfControlTypeIsAscending(idOffset_01, idOffset_02)) {
                value = true;
            }
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    private boolean checkIdOffsetOfControlTypeIsAscending(String idOffset_01, String idOffset_02) {
        boolean value = false;
        if (!elementNotExists(By.xpath(String.format("//li[@id='Control']//following-sibling::li[@id='%s']//following::li[@id='Control']//following-sibling::li[@id='%s']", idOffset_01, idOffset_02)))) {
            value = true;
        }
        return value;
    }

    public boolean isIdOffsetOfProgramTypeIsAscending() {
        boolean value = false;
        for (int i = 0; i < 10; i++) {
            String idOffset_01 = String.valueOf(i);
            String idOffset_02 = String.valueOf(i + 1);
            if (checkIdOffsetOfProgramTypeIsAscending(idOffset_01, idOffset_02)) {
                value = true;
            }
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

    private boolean checkIdOffsetOfProgramTypeIsAscending(String idOffset_01, String idOffset_02) {
        boolean value = false;
        if (!elementNotExists(By.xpath(String.format("//li[@id='Program']//following-sibling::li[@id='%s']//following::li[@id='Program']//following-sibling::li[@id='%s']", idOffset_01, idOffset_02)))) {
            value = true;
        }
        return value;
    }

    public LotcheckReportSubmissionMetadata clickDownloadAllFiles() {
        List<WebElement> elements = findElements(downloadSubProgramButtons);
        for (WebElement element : elements) {
            element.click();
            waitDocumentReady();
            extentLogger.logInfo("");
        }
        return this;
    }

    public ArrayList<String> getListXmlFileOfSubProgram() {
        ArrayList<String> listXmlFileOfSubProgram = new ArrayList<String>();
        List<WebElement> elements = findElements(listXmlFileOfSubProgramElement);
        for (WebElement element : elements) {
            String fileName = element.getText().trim();
            listXmlFileOfSubProgram.add(fileName);
            extentLogger.logInfo(fileName);
        }
        return listXmlFileOfSubProgram;
    }

    public String getFileNameAtRomInfo() {
        String fileName = getText(fileNameAtRomInfo);
        extentLogger.logInfo(fileName);
        return fileName;
    }

    public String getFileNameAtEncryptionROMInfoDL(){
        String fileName = getText(fileNameAtEncryptionROMInfoDL);
        extentLogger.logInfo(fileName);
        return fileName;
    }

    public String getFileNameAtEncryptionROMInfoCard(){
        String fileName = getText(fileNameAtEncryptionROMInfoCard);
        extentLogger.logInfo(fileName);
        return fileName;
    }

}
