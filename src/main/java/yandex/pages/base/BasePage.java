package yandex.pages.base;

import helpers.ConfigProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Базовый класс для всех страниц.
 * Содержит общие методы и поля для работы с веб-элементами.
 *
 * @author Габбасова Лилиана Альбертовна
 * @version 2
 */
public class BasePage {
    /**
     * Веб-драйвер для управления браузером.
     */
    protected WebDriver driver;

    /**
     * Сервис для явного ожидания.
     */
    protected WebDriverWait webDriverWait;

    /**
     * Конструктор класса базовой страницы.
     *
     * @param driver экземпляр WebDriver для взаимодействия с браузером.
     */
    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(ConfigProvider.EXPLICIT_WAIT));
    }

    /**
     * Метод для ожидания видимости элемента по локатору.
     * Возвращает локатор после того как элемент станет видимым.
     *
     * @param locator локатор элемента.
     * @return локатор элемента после ожидания его видимости.
     */
    protected WebElement waitElementVisible(By locator) {
        return webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Метод для ожидания видимости веб-элемента.
     * Возвращает элемент после того как он станет видимым.
     *
     * @param element веб-элемент.
     * @return веб-элемент после ожидания его видимости.
     */
    protected WebElement waitElementVisible(WebElement element) {
        return webDriverWait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Метод для ожидания кликабельности элемента.
     *
     * @param locator локатор элемента.
     * @return кликабельный веб-элемент.
     */
    protected WebElement waitElementClickable(By locator) {
        return webDriverWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Метод для ожидания кликабельности элемента.
     *
     * @param element веб-элемент.
     * @return кликабельный веб-элемент.
     */
    protected WebElement waitElementClickable(WebElement element) {
        return webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Метод для поиска всех элементов по локатору.
     *
     * @param locator локатор элементов.
     * @return список найденных элементов.
     */
    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    /**
     * Метод для ожидания исчезновения элемента по локатору.
     *
     * @param locator локатор элемента.
     * @return true, если элемент исчез.
     */
    protected boolean waitInvisibilityElement(By locator) {
        return webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    /**
     * Метод для ожидания появления элемента в доме по локатору.
     *
     * @param locator локатор элемента.
     * @return true, если элемент есть в доме.
     */
    protected List<WebElement> waitForElementsPresence(By locator) {
        return webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

}