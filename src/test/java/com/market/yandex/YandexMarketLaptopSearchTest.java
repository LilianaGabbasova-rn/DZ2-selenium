package com.market.yandex;

import helpers.DataProvider;
import io.qameta.allure.Owner;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;
import yandex.pages.home.YandexMarketHomePageCatalog;
import yandex.pages.pricelist.YandexMarketProductListingPage;
import yandex.pages.pricelist.search.YandexPageResultSearch;

import java.util.List;


/**
 * Тестовый класс для проверки комплексного сценария работы с ноутбуками в Яндекс Маркете.
 * Включает фильтрацию товаров, проверку соответствия фильтрам и поиск запомненного товара.
 *
 * @author Габбасова Лилиана Альбертовна
 * @version 2
 */
public class YandexMarketLaptopSearchTest extends BaseTest {

    /**
     * Параметризованный тестовый метод для проверки комплексного сценария фильтрации и поиска ноутбуков в Яндекс Маркете.
     * Выполняет последовательность шагов: открытие каталога, навигация по категориям, фильтрация по цене и брендам,
     * проверка результатов фильтрации и поиск сохраненного товара.
     *
     * @param category категория товара для перехода в каталоге
     * @param subcategory подкатегория товара для перехода в каталоге
     * @param minPrice минимальное значение цены для фильтрации товаров
     * @param maxPrice максимальное значение цены для фильтрации товаров
     * @param listBrands список брендов для фильтрации товаров
     * @param countProducts минимальное количество товаров для проверки на странице
     * @throws AssertionError если, заголовок страницы не соответствует ожидаемому,
     * на первой странице меньше указанного количества товаров,
     * товары не соответствуют заданным фильтрам,
     * результаты поиска не содержат сохраненное наименование товара.
     * @see DataProvider#parametersCheck() источник тестовых данных.
     * @see YandexMarketHomePageCatalog класс для работы с главной страницей и каталогом.
     * @see YandexMarketProductListingPage класс для работы со страницей после фильтрации.
     * @see YandexPageResultSearch класс для проверки результатов поиска.
     */
    @Owner("Габбасова Лилиана Альбертовна")
    @DisplayName("Яндекс тест - фильтрация и поиск товара")
    @Feature("Фильтрация и поиск товара")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#parametersCheck")
    public void yandexMarketLaptopFilterTest(String category, String subcategory, int minPrice, int maxPrice, List<String> listBrands, int countProducts) {
        YandexMarketHomePageCatalog yandexHomePageCatalog = new YandexMarketHomePageCatalog(driver);
        yandexHomePageCatalog.openPage();

        yandexHomePageCatalog.openCatalog();

        yandexHomePageCatalog.navigateCatalog(category, subcategory);

        YandexMarketProductListingPage yandexMarketProductListingPage = new YandexMarketProductListingPage(driver);
        Assertions.assertTrue(yandexMarketProductListingPage.isTitleContains(subcategory), "Это не та страница, должен быть раздел с "+ subcategory);

        yandexMarketProductListingPage.filterByPrice(minPrice, maxPrice);

        yandexMarketProductListingPage.filterByBrands(listBrands);

        Assertions.assertTrue(yandexMarketProductListingPage.checkFirstPageProductCount(countProducts), "На странице меньше "+countProducts+" товаров");

        Assertions.assertTrue(yandexMarketProductListingPage.checkAllPagesMatchFilters(minPrice,maxPrice,listBrands),"Один из товаров не соответствует фильтрам: цена от " + minPrice + " до "+maxPrice+", бренды: "  + listBrands );

        String productName = yandexMarketProductListingPage.searchForProduct();

        YandexPageResultSearch yandexPageResultSearch = new YandexPageResultSearch(driver);

        Assertions.assertTrue(yandexPageResultSearch.isProductPresentInResults(productName), "Результаты поиска на первой странице должны содержать наименование товара: "+ productName);
    }
}