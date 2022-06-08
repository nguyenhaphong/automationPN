package net.nintendo.automation.ui.models.lcms;

import net.nintendo.automation.ui.models.common.CommonUIComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LotcheckSubmissionSearchChooseColumnsModal extends CommonUIComponent {
    private static final By uniqueElement = By.xpath("//div[@data-testid='choose-columns-modal']");
    private static By freeToPlayOption = By.id("freeToPlay.Free to Play");
    private static By btnConfirm = By.xpath("//div[@data-testid='choose-columns-modal']//button[@type='submit']");

    public LotcheckSubmissionSearchChooseColumnsModal(WebDriver driver) {
        super(driver, uniqueElement);
    }

    public LotcheckSubmissionSearchChooseColumnsModal addFreeToPlayColumn() {
        waitForAndClickElement(freeToPlayOption);
        extentLogger.logInfo("");
        return this;
    }

    public LotcheckSubmissionSearchPage clickConfirmButton() {
        waitForAndClickElement(btnConfirm);
        extentLogger.logInfo("");
        waitDocumentReady();
        return getNewPage(LotcheckSubmissionSearchPage.class);
    }
}
