package yandex.pages.pricelist.search;

import io.qameta.allure.Step;
import yandex.pages.home.YandexMarketHomePageCatalog;
import org.openqa.selenium.WebDriver;
import yandex.pages.pricelist.YandexMarketProductListingPage;

/**
 * Класс для работы со страницей после результата поиска в Яндекс Маркете.
 *
 * @author Габбасова Лилиана Альбертовна
 * @version 2
 * @see YandexMarketHomePageCatalog
 */
public class YandexPageResultSearch extends YandexMarketProductListingPage {
    /**
     * Конструктор класса страницы после результата поиска в Яндекс Маркете.
     *
     * @param driver экземпляр WebDriver для взаимодействия с браузером.
     */
    public YandexPageResultSearch(WebDriver driver) {
        super(driver);
    }

    /**
     * Метод, который проверяет результаты поиска на содержание запомненного наименования товара на первой странице.
     * Сравнивает наименования товаров на странице результатов с сохраненным названием.
     *
     * @return true если наименование товара найдено в результатах поиска, иначе false
     */
    @Step("Проверка результатов поиска на содержание товара:{productName} на первой странице")
    public boolean isProductPresentInResults(String productName) {
        return productList.isProductPresent(productName);
    }

}
