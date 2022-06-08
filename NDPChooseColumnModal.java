package net.nintendo.automation.ui.models.ndp;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class NDPChooseColumnModal extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@id='chooseColumnDialog']");
    private final By cbPublisher = By.xpath("//input[@id='publisherName.Publisher']");
    private final By btConfirm = By.xpath("//button[@class='btn btn-primary float-right']");
    private final By cbColumnSelect = By.xpath("//input[@type='checkbox' and @checked ='checked']");
    private final By btSelectColumns = By.xpath("//button[@id='_noasearchportlet_WAR_noasearchportlet_applyButton']");

    public NDPChooseColumnModal(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public int collectNumberColumnSelect() {
        List<WebElement> rowsTable = findElements(cbColumnSelect);
        int num = 0;
        for (WebElement row : rowsTable) {
            List<WebElement> columnRow = row.findElements(By.tagName("td"));
            num++;
        }
        extentLogger.logInfo("Number: " + num);
        return num;
    }

    public NDPChooseColumnModal checkColumn(int location) {
        selectCheckbox(By.xpath(String.format("//tr[%s]//input[@type='checkbox']", location)));
        extentLogger.logInfo("Location: " + location);
        return this;
    }

    public NDPChooseColumnModal clickSelectColumnsButton() {
        waitForAndClickElement(btSelectColumns);
        extentLogger.logInfo("");
        return this;
    }
}
