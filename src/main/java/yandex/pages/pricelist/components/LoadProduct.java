package yandex.pages.pricelist.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import yandex.pages.base.BasePage;

import java.util.List;

/**
 * Класс для ожидания загрузки товаров на странице прайс-листа.
 * Обеспечивает ожидание исчезновения индикаторов загрузки перед взаимодействием с товарами.
 *
 *
 * @author Габбасова Лилиана Альбертовна
 * @version 2
 * @see ProductListLocators
 * @see BasePage
 */
public class LoadProduct extends BasePage implements ProductListLocators {
    /**
     * Конструктор класса для ожидания загрузки товаров.
     *
     * @param driver экземпляр WebDriver для взаимодействия с браузером.
     */
    public LoadProduct(WebDriver driver) {
        super(driver);
    }

    /**
     * Метод для ожидания полной загрузки товаров.
     * Ожидает исчезновения загрузки.
     */
    public void waitForProductsLoad() {
        List<WebElement> loaders = findElements(LOADER);
        if (!loaders.isEmpty() && loaders.get(0).isDisplayed()) {
            waitInvisibilityElement(LOADER);
        }

        List<WebElement> loadingIndicators = findElements(LOADING_INDICATOR);
        if (!loadingIndicators.isEmpty() && loadingIndicators.get(0).isDisplayed()) {
            waitInvisibilityElement(LOADING_INDICATOR);
        }
    }
}