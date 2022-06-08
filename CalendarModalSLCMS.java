package net.nintendo.automation.ui.models.lcms;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.ZonedDateTime;
import java.util.List;

public class CalendarModalSLCMS extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[contains(@class,'flatpickr-calendar animate open')]");
    private static final String pageTitle = "";

    public CalendarModalSLCMS(WebDriver driver) {
        super(driver, uniqueElement);
    }
    public void chooseDateInCalenderSLCMS(int numberOfDaysToAdd) {
        extentLogger.logInfo("");
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime dueDate = now.plusDays(numberOfDaysToAdd);
        int numberOfYear = dueDate.getYear() - now.getYear();
        int numberOfMonth = dueDate.getMonth().getValue() - now.getMonth().getValue();
        int numberOfClickOnIncreaseMonth = numberOfMonth + (numberOfYear * 12);

        // click on next month until it reaches a desired month
        while (numberOfClickOnIncreaseMonth > 0) {
            waitForAndClickElement(By.xpath("//div[contains(@class,'flatpickr-calendar animate open')]//span[@class='flatpickr-next-month']"));
            numberOfClickOnIncreaseMonth--;
        }

        List<WebElement> cells = findElements(By.xpath("//div[contains(@class,'flatpickr-calendar animate open')]//span[@class='flatpickr-day' or @class='flatpickr-day today']"));
        for (WebElement cell : cells) {
            if (cell.getText().equals(String.valueOf(dueDate.getDayOfMonth()))) {
                cell.click();
                break;
            }
        }
    }
}
