package net.nintendo.automation.ui.models.hyrule;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HyruleAuthLogoutPage extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//span[@class='glyphicon glyphicon-user']");
    private static final String pageTitle = "Hyrule Authorization Server";

    public HyruleAuthLogoutPage(WebDriver driver) {
        super(driver, uniqueElement);
    }
}
