package net.nintendo.automation.ui.models.lcms;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class RequiredApprovalTranslationTasksPage extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@id='content']//h2[contains(text(),'Required Approval Translation Tasks')]");
    private static final String pageTitle = "Required Approval Translation Tasks - LCMS";
    private final By lblStatusDefault = By.id("StatusId");
    private final By lblStatus = By.xpath("//th[@data-testid='translation-tasks-grid-header'][2]");
    private final By lstStatus = By.xpath("//*[@data-testid='translation-tasks-grid-row']//td[2]");


    public RequiredApprovalTranslationTasksPage(WebDriver driver) {
        super(driver, uniqueElement);
    }
    public String defaultOptionForStatusFilter() {
        String statusDefault = getText(lblStatusDefault);
        extentLogger.logInfo("The default option for the Status filter is: " + statusDefault);
        return statusDefault;
    }

    public String getValueStatusTranslationTasks(int i) {
        List<WebElement> rowsTable = findElements(lstStatus);
        List<String> valueDemoType = List.of(rowsTable.get(i).getText().split("/n"));
        extentLogger.logInfo(valueDemoType.get(0));
        return valueDemoType.get(0);
    }

    public LotcheckLandingPage lotcheckLandingPage() {
        return getNewPage(LotcheckLandingPage.class);
    }
}
