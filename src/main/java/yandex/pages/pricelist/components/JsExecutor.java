package yandex.pages.pricelist.components;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import yandex.pages.base.BasePage;

/**
 * Класс для выполнения JavaScript команд в браузере.
 * Предоставляет методы для прокрутки страницы и взаимодействия с элементами через JavaScript.
 *
 * @author Габбасова Лилиана Альбертовна
 * @version 2
 * @see BasePage
 * @see JavascriptExecutor
 */
public class JsExecutor extends BasePage {
    /**
     * Экземпляр JavascriptExecutor для выполнения JavaScript команд.
     */
    private final JavascriptExecutor js;

    /**
     * Конструктор класса для выполнения JavaScript команд.
     *
     * @param driver экземпляр WebDriver для взаимодействия с браузером.
     */
    public JsExecutor(WebDriver driver) {
        super(driver);
        this.js = (JavascriptExecutor) driver;
    }

    /**
     * Метод для прокрутки страницы к указанному элементу.
     *
     * @param element веб-элемент к которому нужно прокрутить страницу
     */
    public void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true); window.scrollBy(0, -300);", element);
    }

    /**
     * Метод для прокрутки страницы в начало.
     */
    public void scrollToTop() {
        js.executeScript("window.scrollTo(0, 0)");
    }

    /**
     * Метод для клика по элементу с использованием JavaScript.
     *
     * @param element веб-элемент по которому нужно кликнуть
     */
    public void clickWithJS(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }
}