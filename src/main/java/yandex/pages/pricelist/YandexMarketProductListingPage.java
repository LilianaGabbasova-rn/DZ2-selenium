package yandex.pages.pricelist;

import yandex.pages.home.YandexMarketHomePageCatalog;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import yandex.pages.pricelist.components.filters.FiltersComponent;
import yandex.pages.pricelist.components.ProductListComponent;
import yandex.pages.pricelist.components.filters.SearchComponent;

import java.util.List;

/**
 * Класс для работы со страницей после выбора категорий и подкатегорий в Яндекс Маркете.
 * Предоставляет методы для проверки содержимого страницы, работы со списком товаров
 * и выполнения поиска по сохраненным наименованиям товаров.
 *
 * @author Габбасова Лилиана Альбертовна
 * @version 2
 * @see YandexMarketHomePageCatalog
 */
public class YandexMarketProductListingPage extends YandexMarketHomePageCatalog {
    /**
     * Локатор заголовка страницы.
     */
    private final By PAGE_TITLE = By.xpath("//h1[@data-auto='title']");

    /**
     * Компонент для работы со списком товаров.
     */
    protected final ProductListComponent productList;

    /**
     * Компонент для работы с фильтрами.
     */
    protected final FiltersComponent filters;

    /**
     * Компонент для работы с поиском.
     */
    protected final SearchComponent search;

    /**
     * Конструктор класса страницы товаров после выбора категорий.
     *
     * @param driver экземпляр WebDriver для взаимодействия с браузером.
     */
    public YandexMarketProductListingPage(WebDriver driver) {
        super(driver);
        this.productList = new ProductListComponent(driver);
        this.filters = new FiltersComponent(driver);
        this.search = new SearchComponent(driver);
    }

    /**
     * Метод, который проверяет, что заголовок страницы содержит указанный текст.
     *
     * @param title текст для проверки в заголовке страницы
     * @return true если заголовок содержит указанный текст, иначе false
     */
    @Step("Проверка, что раздел содержит '{title}'")
    public boolean isTitleContains(String title) {
        return waitElementVisible(PAGE_TITLE).getText().contains(title);
    }

    /**
     * Метод для фильтрации товаров по стоимости.
     *
     * @param min минимальная цена для фильтрации
     * @param max максимальная цена для фильтрации
     */
    @Step("Фильтрация по стоимости товара от '{min}' до '{max}' рублей")
    public void filterByPrice(double min, double max) {
        filters.setMinPrice(min);
        filters.setMaxPrice(max);
    }

    /**
     * Метод для фильтрации товаров по брендам.
     *
     * @param brands список брендов для фильтрации
     */
    @Step("Фильтрация по брендам: {brands}")
    public void filterByBrands(List<String> brands) {
        for (String brand : brands) {
            filters.filterByBrand(brand);
        }
        productList.load.waitForProductsLoad();
    }

    /**
     * Метод для проверки количества товаров на первой странице.
     *
     * @param countProducts минимальное количество товаров для проверки
     * @return true если количество товаров на странице больше или равно указанному значению
     */
    @Step("Проверка что на первой странице отображается не менее {countProducts} товаров")
    public boolean checkFirstPageProductCount(int countProducts) {
        return productList.isMinimumProductsPage(countProducts);
    }

    /**
     * Метод для проверки соответствия всех товаров на всех страницах заданным фильтрам.
     *
     * @param minPrice минимальная цена для фильтрации
     * @param maxPrice максимальная цена для фильтрации
     * @param brands   список брендов для фильтрации
     * @return true если все товары на всех страницах соответствуют фильтрам
     */
    @Step("Проверка всех страниц на соответствие фильтрам: цена - от {minPrice} до {maxPrice}, бренды - {brands}")
    public boolean checkAllPagesMatchFilters(double minPrice, double maxPrice, List<String> brands) {
        return productList.checkAllPagesMatchFilters(minPrice, maxPrice, brands);
    }

    /**
     * Метод для получения названия первого товара на странице.
     *
     * @return название первого товара
     */
    @Step("Возвращение в начало списка и получение первого наименования ноутбука")
    public String getFirstProductName() {
        return productList.getFirstProductName();
    }

    /**
     * Метод для выполнения поиска по первому наименованию товара.
     *
     * @return название товара по которому выполнен поиск
     */
    @Step("Поиск по первому наименованию товара")
    public String searchForProduct() {
        String productName = getFirstProductName();
        search.inputWord(productName);
        search.clickButtonSearch();
        return productName;
    }
}