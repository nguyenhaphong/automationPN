package net.nintendo.automation.ui.models.common;

import net.nintendo.automation.junit.TestLogger;
import net.nintendo.automation.ui.errors.UIException;
import net.nintendo.automation_core.extent_reports.extensions.IJUnitTestReporter;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.InvocationTargetException;

public class PageManager {
    /**
     * Gets a new page that extends {@link CommonUIComponent}.
     *
     * @param pageClass        Class to instantiate
     * @param driver           {@link WebDriver}
     * @param extentTestReport {@link IJUnitTestReporter}
     * @return new instance after calling {@link CommonUIComponent#waitIsLoaded()} and {@link CommonUIComponent#waitForPageTitle()}
     */
    public static <T extends CommonUIComponent> T getNewPage(Class<T> pageClass, WebDriver driver, IJUnitTestReporter extentTestReport) {
        T page = getInstance(pageClass, driver);
        page.waitIsLoaded();
        page.waitForPageTitle();

        TestLogger logger = new TestLogger(extentTestReport);
        page.setExtentLogger(logger);

        return page;
    }

    private static <T extends CommonUIComponent> T getInstance(Class<T> tClass, WebDriver driver) {
        try {
            tClass.getDeclaredConstructor(WebDriver.class).trySetAccessible();
            return tClass.getDeclaredConstructor(WebDriver.class).newInstance(driver);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new UIException("Class not in correct state: " + tClass.getSimpleName(), e);
        }
    }
}
