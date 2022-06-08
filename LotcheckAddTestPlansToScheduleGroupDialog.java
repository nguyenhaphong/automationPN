package net.nintendo.automation.ui.models.lcms;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LotcheckAddTestPlansToScheduleGroupDialog extends CommonUIComponent {
    private static By cbbTestPlan = By.xpath("//div[@role=\"combobox\"]");
    private final By btnConfirm = By.xpath("//div[@data-testid=\"add-test-plans-modal\"]//button[@type='submit']");
    private final By optionTestPlan = By.xpath("//div[@role='option']");
    private final By txtSearch=By.xpath("//input[@class=\"search\"]");

    public LotcheckAddTestPlansToScheduleGroupDialog(WebDriver driver) {
        super(driver, cbbTestPlan);
    }

    public LotcheckAddTestPlansToScheduleGroupDialog selectTestPlan(String gameCode) {
        extentLogger.logInfo(gameCode);
        By element=By.xpath(String.format("//span[@class=\"text\" and contains(text(),'%s')]",gameCode));
        waitForAndClickElement(cbbTestPlan);
        waitForAndClickElement(element);
        typeTab(txtSearch);
        return this;
    }

    public LotcheckTestPlanRow clickConfirm() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnConfirm);
        return getNewPage(LotcheckTestPlanRow.class);
    }
}
