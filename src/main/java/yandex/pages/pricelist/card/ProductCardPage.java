package yandex.pages.pricelist.card;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import yandex.pages.base.BasePage;

import java.util.List;

/**
 * Класс для работы с карточкой товара на странице прайс-листа.
 * Предоставляет методы для получения информации о бренде товара.
 *
 * @author Габбасова Лилиана Альбертовна
 * @version 2
 * @see BasePage
 */
public class ProductCardPage extends BasePage {

    /**
     * Локатор изображения бренда в карточке товара.
     */
    private final By BRAND_IMAGE = By.xpath("//div[@data-zone-name='showAll']//img");

    /**
     * Конструктор страницы карточки товара.
     *
     * @param driver экземпляр WebDriver для взаимодействия с браузером.
     */
    public ProductCardPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Метод для получения названия бренда товара из изображения.
     * Извлекает текст из атрибута alt изображения бренда.
     *
     * @return название бренда или пустую строку если бренд не найден
     */
    @Step("Получение бренда товара из карточки через изображение")
    public String getBrandFromImage() {
        List<WebElement> brandImages = findElements(BRAND_IMAGE);
        if (brandImages.isEmpty()) {
            return "";
        }

        WebElement brandImage = brandImages.get(0);
        waitElementVisible(brandImage);
        String altText = brandImage.getAttribute("alt");
        if (altText == null || altText.trim().isEmpty()) {
            return "";
        }

        return altText.trim();
    }

    /**
     * Метод для проверки соответствия бренда товара ожидаемым брендам.
     * Сравнивает полученный бренд с каждым из ожидаемых брендов.
     *
     * @param brands список ожидаемых брендов для проверки
     * @return true если бренд товара соответствует одному из ожидаемых брендов, а иначе false.
     */
    @Step("Проверка соответствия бренда товара ожидаемым брендам: {expectedBrands}")
    public boolean isBrandMatches(List<String> brands) {
        String actualBrand = getBrandFromImage();
        if (actualBrand.isEmpty()) {
            return false;
        }

        for (String expectedBrand : brands) {
            if (actualBrand.equalsIgnoreCase(expectedBrand)) {
                return true;
            }
        }
        return false;
    }
}