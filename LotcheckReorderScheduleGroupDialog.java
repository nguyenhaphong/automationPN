package net.nintendo.automation.ui.models.lcms;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;

public class LotcheckReorderScheduleGroupDialog extends CommonUIComponent {
    private static By btnConfirm = By.xpath("//div[@data-testid=\"reorder-schedule-group-modal\"]//button[@type='submit']");

    public LotcheckReorderScheduleGroupDialog(WebDriver driver) {
        super(driver, btnConfirm);
    }

    public LotcheckTestPlanRow clickConfirm() {
        extentLogger.logInfo("");
        waitForAndClickElement(btnConfirm);
        return getNewPage(LotcheckTestPlanRow.class);
    }

    public LotcheckReorderScheduleGroupDialog reorderScheduleGroup(String scheduleGroup_From, String scheduleGroup_To) throws IOException {
        WebElement from = driver.findElement(By.xpath(String.format("//div[@data-testid=\"reorder-card\" and text()='%s']", scheduleGroup_From)));
        WebElement to = driver.findElement(By.xpath(String.format("//div[@data-testid=\"reorder-card\" and text()='%s']", scheduleGroup_To)));

        dragAndDrop(from,to);
        return this;
    }

    public boolean checkOrderSchedule(String scheduleGroup_01, String scheduleGroup_02){
        boolean value=false;
        By element=By.xpath(String.format("//div[@data-testid=\"reorder-card\" and contains(text(),'%s')]/ancestor::div[@class='reorder-card']/following-sibling::div[@class='reorder-card']//div[@data-testid=\"reorder-card\" and contains(text(),'%s')]",scheduleGroup_01,scheduleGroup_02));
        if(!elementNotExists(element)){
            value=true;
        }
        extentLogger.logInfo(String.valueOf(value));
        return value;
    }

}
