package net.nintendo.automation.ui.models.lcms;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LotcheckCreateNewScheduleGroupDialog extends CommonUIComponent {
    private static By txtName = By.xpath("//input[@data-testid=\"group-name\"]");
    private final By btnConfirm = By.xpath("//div[@data-testid=\"create-and-edit-schedule-group-modal\"]//button[@data-testid=\"submit-button\"]");

    public LotcheckCreateNewScheduleGroupDialog(WebDriver driver) {
        super(driver, txtName);
    }

    public LotcheckCreateNewScheduleGroupDialog enterScheduleGroupName(String name) {
        extentLogger.logInfo(name);
        waitForAndClickElement(txtName);
        typeText(txtName, name);
        typeTab(txtName);
        return this;
    }

    public LotcheckTestPlanRow clickConfirm() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnConfirm);
        return getNewPage(LotcheckTestPlanRow.class);
    }
}
