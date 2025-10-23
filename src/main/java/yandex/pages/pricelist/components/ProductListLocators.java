package yandex.pages.pricelist.components;

import org.openqa.selenium.By;

/**
 * Интерфейс с локаторами для списка товаров на странице прайс-листа.
 * Содержит локаторы для работы с карточками товаров, ценами и индикаторами загрузки.
 *
 * @author Габбасова Лилиана Альбертовна
 * @version 2
 */
public interface ProductListLocators {
    /**
     * Локатор карточек товаров.
     */
    By PRODUCT_CARDS = By.xpath("//article//span[@itemprop='name']");

    /**
     * Локатор ссылок на карточки товаров.
     */
    By PRODUCT_CARDS_LINK = By.xpath("//article//a[span[@itemprop='name']]");

    /**
     * Локатор элементов с ценами товаров.
     */
    By PRODUCT_PRICE = By.xpath("//article//span[contains(text(),'Цена')]");

    /**
     * Локатор индикатора загрузки.
     */
    By LOADING_INDICATOR = By.xpath("//div[@data-auto='rollSkeleton']");

    /**
     * Локатор загрузки.
     */
    By LOADER = By.xpath("//div[contains(@data-auto,'loader')]");
}
