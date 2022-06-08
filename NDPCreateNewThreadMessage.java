package net.nintendo.automation.ui.models.ndp;

import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.models.common.CommonUIComponent;
import net.nintendo.automation.ui.models.ndp.existing_product.tabs.NDPProductDashboardMessagesTab;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NDPCreateNewThreadMessage extends CommonUIComponent {
    private static final By messageBoardForm = By.xpath("//section[@class='nin-page-description']");
    private static final String pageTitle = "Message Board - Nintendo Developer Portal";

    public NDPCreateNewThreadMessage(WebDriver webDriver) {
        super(webDriver, messageBoardForm, pageTitle);
    }

    private final By txtTopic = By.id("_noaprojectmessageboardsportlet_WAR_noaprojectsportlet_subject");
    private final By cboDeveloperSupport = By.id("_noaprojectmessageboardsportlet_WAR_noaprojectsportlet_DEVELOPER_SUPPORTCheckbox");
    private final By cboLicensing = By.id("_noaprojectmessageboardsportlet_WAR_noaprojectsportlet_LICENSINGCheckbox");
    private final By cboLotCheck = By.id("_noaprojectmessageboardsportlet_WAR_noaprojectsportlet_LOTCHECKCheckbox");
    private final By btnPost = By.id("_noaprojectmessageboardsportlet_WAR_noaprojectsportlet_publishButton");
    private final By lblMessageSuccessful = By.xpath("//*[@class='alert alert-success']");
    private final By btnReturnMessage = By.xpath("//*[@class='icon-arrow-left']");

    public NDPCreateNewThreadMessage inputTopicThread(String topicText) {
        typeText(txtTopic, topicText);
        typeTab(txtTopic);
        extentLogger.logInfo(topicText);
        return this;
    }

    public NDPCreateNewThreadMessage selectDeveloperSupportCheckbox() {
        selectCheckbox(cboDeveloperSupport, "true");
        extentLogger.logInfo("");
        return this;
    }

    public NDPCreateNewThreadMessage selectLicensingCheckbox() {
        selectCheckbox(cboLicensing, "true");
        extentLogger.logInfo("");
        return this;
    }

    public NDPCreateNewThreadMessage selectLotcheckCheckbox() {
        selectCheckbox(cboLotCheck, "true");
        extentLogger.logInfo("");
        return this;
    }

    public NDPCreateNewThreadMessage clickPostButton() {
        waitForAndClickElement(btnPost);
        extentLogger.logInfo("");
        return this;
    }

    public NDPCreateNewThreadMessage waitingForRequestSuccessful() {
        extentLogger.logInfo("");
        waitForElementVisible(lblMessageSuccessful, WaitTimes.PAGE_LOAD_TIMEOUT_SECS);
        return this;
    }

    public NDPProductDashboardMessagesTab clickReturnMessageTab() {
        waitForAndClickElement(btnReturnMessage);
        extentLogger.logInfo("");
        return getNewPage(NDPProductDashboardMessagesTab.class);
    }
}
