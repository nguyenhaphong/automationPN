package net.nintendo.automation.ui.models.common;

import lombok.extern.slf4j.Slf4j;
import net.nintendo.automation.junit.TestLogger;
import net.nintendo.automation.ui.constants.WaitTimes;
import net.nintendo.automation.ui.errors.UIException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
public abstract class CommonUIComponent {
    protected final WebDriver driver;
    private final JavascriptExecutor javascriptExecutor;
    private final By uniqueElement;
    private final String pageTitle;

    protected TestLogger extentLogger;

    protected CommonUIComponent(WebDriver driver, By uniqueElement) {
        this(driver, uniqueElement, null);
    }

    protected CommonUIComponent(WebDriver driver, By uniqueElement, String pageTitle) {
        this.driver = driver;
        this.uniqueElement = uniqueElement;
        this.javascriptExecutor = (JavascriptExecutor) driver;
        this.extentLogger = new TestLogger(null); // init to null. PageManager will call setter below.
        this.pageTitle = pageTitle;
    }

    protected void waitForElementAndClick(By locator) {
        WebElement element = waitForElementVisible(locator);
        element.click();
    }

    public void waitIsLoaded() throws Error {
        // Wait until the unique element is visible in the browser and ready to use. This helps make sure the page is
        // loaded before the next step of the tests continue.
        waitIsLoaded(WaitTimes.WAIT_FOR_UNIQUE_ELEMENT_VISIBILITY_SECS);

    }

    protected void waitIsLoaded(Duration waitTime) {
        // Wait until the unique element is visible in the browser and ready to use. This helps make sure the page is
        // loaded before the next step of the tests continue.
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        wait.until(ExpectedConditions.visibilityOfElementLocated(uniqueElement));
    }

    public void waitIsSubmit() throws Error {
        WebDriverWait wait = new WebDriverWait(driver, WaitTimes.WAIT_FOR_SUBMIT_PROCESSING_SECS);
        wait.until(ExpectedConditions.visibilityOfElementLocated(uniqueElement));
    }

    public void waitForPageTitle() throws Error {
        if (pageTitle != null) {
            log.info("Looking for page title: " + pageTitle);
            WebDriverWait wait = new WebDriverWait(driver, WaitTimes.WAIT_FOR_PAGE_TITLE);
            wait.until(ExpectedConditions.titleIs(pageTitle));
        } else {
            log.info("Page title is null. Skipping check");
        }
    }

    protected void typeText(By locator, String text) {
        clearText(locator);
        waitForElementVisible(locator).sendKeys(text);
    }

    protected void typeText(WebElement element, String text) {
        clearText(element);
        waitForElementVisible(element).sendKeys(text);
    }

    protected void typeTab(By locator) {
        waitForElementVisible(locator).sendKeys(Keys.TAB);
    }

    protected void typeTab(WebElement element) {
        waitForElementVisible(element).sendKeys((Keys.TAB));
    }

    protected void typeTextIfValuePresent(By locator, String text) {
        if (StringUtils.isNotBlank(text)) {
            typeText(locator, text);
        }
    }

    protected void clearText(By locator) {
        waitForElementVisible(locator).clear();
    }

    protected void clearText(WebElement element) {
        waitForElementVisible(element).clear();
    }

    protected void clearTextValue(By locator) {
        WebElement toClear = driver.findElement(locator);
        toClear.sendKeys(Keys.CONTROL + "a");
        toClear.sendKeys(Keys.DELETE);
    }

    public String checkBackgroundColor(By record) {
        WebElement element = driver.findElement(record);
        String colorCode = Color.fromString(element.getCssValue("background-color")).asHex().toUpperCase();
        extentLogger.logInfo("Actual: Color Code is " + colorCode);
        return colorCode;
    }

    protected String getElement(By locator) {
        WebElement element = driver.findElement(locator);
        String valueElement = element.getAttribute("href");
        return valueElement;
    }

    protected String getText(By locator) {
        return waitForElementVisible(locator).getText().trim();
    }

    protected String getText(WebElement ele) {
        return waitForElementVisible(ele).getText().trim();
    }

    protected String getTextIfElementExists(By locator) {
        List<WebElement> elements = findElements(locator);
        if (elements.isEmpty()) {
            return "";
        }
        return getText(locator);
    }

    protected String getTextIfElementClickable(By locator) {
        waitForElementClickable(locator);
        return getText(locator);
    }

    protected String getDropDownSelectedValue(By locator) {
        WebElement dropDownMenu = waitForElementVisible(locator);
        Select selectValue = new Select(dropDownMenu);
        return selectValue.getFirstSelectedOption().getText().trim();
    }

    protected void selectOptionValueOnDropDown(By locator, String value) {
        WebElement dropDownMenu = waitForElementVisible(locator);
        Select selectValue = new Select(dropDownMenu);
        selectValue.selectByValue(value);
    }

    protected void selectValueOnDropDown(By locator, String text) {
        WebElement dropDownMenu = waitForElementVisible(locator);
        Select selectValue = new Select(dropDownMenu);
        selectValue.selectByVisibleText(text);
    }

    protected void selectValueOnDropDownIfValuePresent(By locator, String text) {
        if (StringUtils.isNotBlank(text)) {
            selectValueOnDropDown(locator, text);
        }
    }

    protected void clickCheckbox(By locator) {
        WebElement element = driver.findElement(locator);
        javascriptExecutor.executeScript("arguments[0].click();", element);
    }

    protected void openLinkInSameTab(By locator) {
        WebElement element = driver.findElement(locator);
        javascriptExecutor.executeScript("arguments[0].target='_self'", element);
        element.click();
    }

    protected String getSelectedValue(By locator) {
        WebElement dropDownMenu = waitForElementVisible(locator);
        Select selectValue = new Select(dropDownMenu);
        return selectValue.getFirstSelectedOption().getText().trim();
    }

    protected void removeAttribute(By locator, String attribute) {
        if (!attribute.equalsIgnoreCase("readonly")) {
            throw new UIException("Removing an unsupported attribute: " + attribute);
        }
        WebElement element = driver.findElement(locator);
        String js = String.format("arguments[0].removeAttribute('%s')", attribute);
        javascriptExecutor.executeScript(js, element);
    }

    protected void changeStyleAttribute(WebElement element, HashMap<String, String> attributeMap) {
        String attributeMapString =
                attributeMap.entrySet().stream()
                        .map(e -> e.getKey() + ": " + e.getValue())
                        .collect(Collectors.joining(";"));
        String js = String.format("$(arguments[0]).attr('style', '%s')", attributeMapString);
        javascriptExecutor.executeScript(js, element);
    }

    protected void changeStyleAttribute(By locator, HashMap<String, String> attributeMap) {
        String attributeMapString =
                attributeMap.entrySet().stream()
                        .map(e -> e.getKey() + ": " + e.getValue())
                        .collect(Collectors.joining(";"));
        String js = String.format("$(arguments[0]).attr('style', '%s')", attributeMapString);
        WebElement element = driver.findElement(locator);
        javascriptExecutor.executeScript(js, element);
    }


    protected WebElement findNestedElementPresent(WebElement element, By by) {
        return waitForNestedElementPresent(element, by);
    }

    protected WebElement waitForNestedElementPresent(WebElement parent, By locator) {
        return waitForNestedElementPresent(parent, locator, WaitTimes.WAIT_FOR_ELEMENT_VISIBLE_TIMEOUT_SECS);
    }

    protected WebElement waitForNestedElementPresent(WebElement parent, By locator, Duration waitTime) {
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(parent, locator));
    }

    protected boolean waitForElementValueToBe(WebElement locator, String value) {
        WebDriverWait wait = new WebDriverWait(driver, WaitTimes.WAIT_FOR_ELEMENT_CLICKABLE_TIMEOUT_SECS);
        return wait.until(ExpectedConditions.attributeContains(locator, "value", value));
    }

    protected List<WebElement> findNestedElementVisible(WebElement element, By by) {
        return waitForNestedElementVisible(element, by);
    }

    protected String getValueAttributesIfPresence(WebElement element) {
        return element.getAttribute("value");
    }

    protected List<WebElement> waitForNestedElementVisible(WebElement parent, By locator) {
        return waitForNestedElementVisible(parent, locator, WaitTimes.WAIT_FOR_ELEMENT_VISIBLE_TIMEOUT_SECS);
    }

    protected List<WebElement> waitForNestedElementVisible(WebElement parent, By locator, Duration waitTime) {
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        return wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(parent, locator));
    }

    protected void selectCheckbox(By locator) {
        WebElement element = driver.findElement(locator); // can't wait here
        if (!element.isSelected()) {
            clickCheckbox(locator);
        }
    }

    protected void uncheckCheckbox(By locator) {
        WebElement element = driver.findElement(locator);// can't wait here
        if (element.isSelected()) {
            clickCheckbox(locator);
        }
    }

    protected void selectCheckboxIfTrue(By locator, String value) {
        if (value.equalsIgnoreCase("true")) {
            selectCheckbox(locator);
        }
    }

    protected void selectCheckbox(By locator, String value) {
        WebElement element = driver.findElement(locator);
        if (!element.getAttribute("type").equalsIgnoreCase("checkbox")) {
            throw new UIException("tried to check / uncheck element that is not a checkbox: " + locator.toString());
        }

        if (StringUtils.containsIgnoreCase(value, "true")) {
            selectCheckbox(locator);
        } else {
            uncheckCheckbox(locator);
        }
    }

    protected void selectRadio(By locator) {
        selectRadio(locator, "true");
    }

    protected void selectRadio(By locator, String value) {
        WebElement element = driver.findElement(locator);
        if (!element.getAttribute("type").equalsIgnoreCase("radio")) {
            throw new UIException("tried to check / uncheck element that is not a checkbox: " + locator.toString());
        }

        if (StringUtils.containsIgnoreCase(value, "true")) {
            selectCheckbox(locator);
        }
    }

    protected void uploadFile(By locator, String fileName) {
        WebElement upload = driver.findElement(locator);
        String path = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + fileName;
        File file = new File(path);
        upload.sendKeys(file.getAbsolutePath());
    }

    protected void hoverMouse(By locator) {
        WebElement element = driver.findElement(locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    protected void enterKeyBoard(By locator) {
        WebElement textboxElement = driver.findElement(locator);
        textboxElement.sendKeys(Keys.ENTER);
    }

    protected Boolean elementNotExists(By locator) {
        List<WebElement> elements = findElements(locator);
        if (elements.isEmpty()) {
            return true;
        }
        return false;
    }

    public void screenShot() throws IOException {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "DataFile" + File.separator + screenshot;
        FileUtils.copyFile(screenshot, new File(path + ".jpg"));
    }

    protected List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    protected WebElement findElement(By by) {
        return driver.findElement(by);
    }


    protected WebElement findElementInContext(SearchContext ele, By by) {
        waitForElementVisible(by);
        return ele.findElement(by);
    }

    protected WebElement waitForElementVisible(By locator) {
        return waitForElementVisible(locator, WaitTimes.WAIT_FOR_ELEMENT_VISIBLE_TIMEOUT_SECS);
    }

    protected WebElement waitForElementVisible(WebElement ele) {
        return waitForElementVisible(ele, WaitTimes.WAIT_FOR_ELEMENT_VISIBLE_TIMEOUT_SECS);
    }

    protected WebElement waitForElementVisible(By locator, Duration waitTime) {
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForElementNotVisible(By locator) {
        waitForElementNotVisible(locator,
                WaitTimes.WAIT_FOR_ELEMENT_NOT_VISIBLE_TIMEOUT_SECS);
    }

    protected WebElement waitForElementVisible(WebElement ele, Duration waitTime) {
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        return wait.until(ExpectedConditions.visibilityOf(ele));
    }

    protected void waitForElementNotVisible(By locator, Duration waitTime) {
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected boolean getIsChecked(By locator) {
        return waitForElementVisible(locator).isSelected();
    }

    protected WebElement waitForElementClickable(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, WaitTimes.WAIT_FOR_ELEMENT_CLICKABLE_TIMEOUT_SECS);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement waitForElementClickable(By locator, Duration waitTime) {
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement waitForElementClickable(WebElement element, Duration waitTime) {
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }


    protected void waitForElementDisplay(By locator, String matchText, Duration minutes) {
        WebDriverWait wait = new WebDriverWait(driver, WaitTimes.WAIT_FOR_ELEMENT_CLICKABLE_TIMEOUT_SECS);
        wait.withTimeout(minutes).until(ExpectedConditions.textToBePresentInElementLocated(locator, matchText.trim()));
        //wait.until(ExpectedConditions.textToBePresentInElementLocated(locator,matchText.trim()));
    }

    protected void waitForAndClickElement(By locator) {
        this.waitForElementClickable(locator).click();
    }

    protected void waitForAndClickElement(WebElement element) {
        this.waitForElementClickable(element).click();
    }

    protected WebElement waitForElementClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, WaitTimes.WAIT_FOR_ELEMENT_CLICKABLE_TIMEOUT_SECS);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected float getClientViewCoordinate(String property) {
        String getCoordinateScript = "return document.documentElement.getClientRects()[0].%s";
        //"-1602.666748046875"
        return Float.parseFloat(javascriptExecutor.executeScript(String.format(getCoordinateScript, property)).toString());
    }

    protected void removeFocusFromElement() {
        this.waitForElementClickable(By.cssSelector("body"));
    }

    protected String getClassAttributes(By locator) {
        return waitForElementVisible(locator).getAttribute("class");
    }

    protected String getValueAttributes(By locator) {
        return waitForElementVisible(locator).getAttribute("value");
    }

    protected String getValueAttributes(WebElement ele) {
        return waitForElementVisible(ele).getAttribute("value");
    }

    protected void expandContainer(By locator) {
        WebElement element = waitForElementVisible(locator);
        String expanded = element.getAttribute("aria-expanded");
        if (StringUtils.equalsIgnoreCase(expanded, "false")) {
            element.click();
        }
    }

    protected void clickElementOnMetadata(By locator) {
        WebElement element = waitForElementVisible(locator);
        element.click();
    }

    protected String takeScreenShot(String path) {
        try {
            WebDriver augmentedDriver = new Augmenter().augment(driver);
            File source = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
            path = path + source.getName();
            FileUtils.copyFile(source, new File(path));
        } catch (IOException e) {
            path = "Failed to capture screenshot: " + e.getMessage();
        }
        return path;
    }

    protected String getTextMetadata(By locator) {
        WebElement element = waitForElementVisible(locator);
        return element.getText();
    }

    protected boolean getIsSelected(By locator) {
        return waitForElementVisible(locator).isSelected();
    }

    protected boolean getIsSelectedNoWait(By locator) {
        return driver.findElement(locator).isSelected();
    }

    protected boolean getIsDisplayedNoWait(By locator) {
        boolean isDisplayed = false;
        List<WebElement> elementList = findElements(locator);
        if (elementList.size() > 0) {
            isDisplayed = elementList.get(0).isDisplayed();
        }
        return isDisplayed;
    }

    protected String getAttributes(By locator, String attributeName) {
        return waitForElementVisible(locator).getAttribute(attributeName);
    }

    protected boolean getElementAttribute(By locator, String value) {
        WebElement element = driver.findElement(locator);
        String valueElement = element.getAttribute(value);
        return true;
    }


    protected void waitDocumentReady() {
        WebDriverWait wait = new WebDriverWait(driver, WaitTimes.WAIT_FOR_DOCUMENT_READY_TIMEOUT_SECS);
        wait.until(webDriver ->
                javascriptExecutor.executeScript("return document.readyState").toString()
                        .equalsIgnoreCase("complete"));
    }

    protected void scrollToBottom()
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        //Scroll down till the bottom of the page
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");

    }
    protected void dragAndDrop(WebElement from, WebElement to) throws IOException {
        //https://github.com/SeleniumHQ/selenium/issues/3269 - Selenium Actions drag and drop not working on chrome browser
        //https://github.com/seleniumhq/selenium-google-code-issue-archive/issues/3604#issuecomment-192002262 - Workaround with jQuery
        //https://gist.github.com/rcorreia/2362544 - jQuery drag and drop helper
        File file = new File("src/test/resources/drag_and_drop_helper.js");
        String dragAndDropHelper = Files.readString(file.toPath(), StandardCharsets.UTF_8);
        javascriptExecutor.executeScript(dragAndDropHelper);
        String cmd = "$(arguments[0]).simulateDragDrop({dropTarget: $(arguments[1])});";
        javascriptExecutor.executeScript(cmd, from, to);
    }

    protected void executeJavaScript(String cmd) {
        javascriptExecutor.executeScript(cmd);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    protected <T extends CommonUIComponent> T getNewPage(Class<T> pageClass) {
        return PageManager.getNewPage(pageClass, driver, extentLogger.getReport());
    }

    public void setExtentLogger(TestLogger extentLogger) {
        this.extentLogger = extentLogger;
    }

    protected String getValueOnDropdownAfterSelect(By locator) {
        WebElement dropDownMenu = waitForElementVisible(locator);
        Select selectValue = new Select(dropDownMenu);
        return selectValue.getFirstSelectedOption().getText();
    }

    public void scrollToElement(WebElement ele)  {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].scrollIntoView(true);", ele);
    }

    public void clickToElementByJS(WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
        jsExecutor.executeScript("arguments[0].click();", element);
    }
}
