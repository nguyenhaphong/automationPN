package net.nintendo.automation.ui.models.ndp;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.List;

public class CalendarModal extends CommonUIComponent {
    private static final By uniqueElement = By.cssSelector("#ui-datepicker-div");
    private static final String pageTitle = "";

    public CalendarModal(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public void chooseDateInCalender(int numberOfDaysToAdd) {
        extentLogger.logInfo("");
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime dueDate = now.plusDays(numberOfDaysToAdd);
        int numberOfYear = dueDate.getYear() - now.getYear();
        int numberOfMonth = dueDate.getMonth().getValue() - now.getMonth().getValue();
        int numberOfClickOnIncreaseMonth = numberOfMonth + (numberOfYear * 12);

        // click on next month until it reaches a desired month
        while (numberOfClickOnIncreaseMonth > 0) {
            waitForAndClickElement(
                    By.cssSelector("#ui-datepicker-div > div > a.ui-datepicker-next.ui-corner-all > span")
            );
            numberOfClickOnIncreaseMonth--;
        }

        List<WebElement> cells = findElements(By.xpath("//div[@id='ui-datepicker-div']/table/tbody/tr/td/a"));
        for (WebElement cell : cells) {
            if (cell.getText().equals(String.valueOf(dueDate.getDayOfMonth()))) {
                cell.click();
                break;
            }
        }
    }

    public String getMonth() {
        Calendar cal = Calendar.getInstance();
        System.out.println(cal);
        int month = cal.get(Calendar.MONTH);
        System.out.println(month);
        int nextMonth = month + 3;
        System.out.println(nextMonth);
        String value = "January";
        if (nextMonth == 1) {
            value = "January";
        } else if (nextMonth == 2) {
            value = "February";
        } else if (nextMonth == 3) {
            value = "March";
        } else if (nextMonth == 4) {
            value = "April";
        } else if (nextMonth == 5) {
            value = "May";
        } else if (nextMonth == 6) {
            value = "June";
        } else if (nextMonth == 7) {
            value = "July";
        } else if (nextMonth == 8) {
            value = "August";
        } else if (nextMonth == 9) {
            value = "September";
        } else if (nextMonth == 10) {
            value = "October";
        } else if (nextMonth == 11) {
            value = "November";
        } else if (nextMonth == 12) {
            value = "December";
        } else {
            value = "January";
        }
        extentLogger.logInfo(value);
        return value;
    }

    public String getYear() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int nextMonth = month + 1;
        int year = cal.get(Calendar.YEAR);
        int yearNext = year + 1;
        String yearCurrent = String.valueOf(year);
        String yearNextCurrent = String.valueOf(yearNext);
        if (month == 10) {
            extentLogger.logInfo(yearNextCurrent);
            return yearNextCurrent;
        }
        extentLogger.logInfo(yearCurrent);
        return yearCurrent;
    }

}
