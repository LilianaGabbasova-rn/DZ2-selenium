package yandex.pages.home;

import yandex.pages.base.BasePage;
import helpers.ConfigProvider;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * Класс для работы на главной странице Яндекс Маркета.
 * Предоставляет возможность открыть каталог по выбранному параметру.
 *
 * @author Габбасова Лилиана Альбертовна
 * @version 2
 * @see BasePage
 */
public class YandexMarketHomePageCatalog extends BasePage {

    /**
     * Локатор кнопки для открытия каталога.
     */
    private final By BUTTON_CATALOG_LOCATOR = By.xpath("//button[./span[text()='Каталог']]");

    /**
     * Конструктор класса Главной страницы Яндекс маркета.
     *
     * @param driver экземпляр WebDriver для взаимодействия с браузером.
     */
    public YandexMarketHomePageCatalog(WebDriver driver) {
        super(driver);
    }

    /**
     * Метод для открытия страницы Яндекс маркета.
     */
    @Step("Открытие главной страницы Яндекс Маркета")
    public void openPage() {
        driver.get(ConfigProvider.URL);
    }

    /**
     * Метод, который позволяет открывать каталог через кнопку.
     */
    @Step("Открытие каталога")
    public void openCatalog() {
        WebElement buttonCatalog = waitElementVisible(BUTTON_CATALOG_LOCATOR);
        buttonCatalog.click();
    }

    /**
     * Метод для получения локатора категории по названию.
     *
     * @param categoryName название категории.
     * @return локатор категории.
     */
    private By getCategoryLocator(String categoryName) {
        return By.xpath("//div[@data-auto='catalog-content']//li[.//span[text()='" + categoryName + "']]");
    }

    /**
     * Метод для получения локатора подкатегории по названию.
     *
     * @param subcategoryName название подкатегории.
     * @return локатор подкатегории.
     */
    private By getSubcategoryLocator(String subcategoryName) {
        return By.xpath("//div[@role='tabpanel']//div[@data-zone-name='linkSnippet']//a[text()='" + subcategoryName + "']");
    }

    /**
     * Метод для выбора категории товара в каталоге.
     * Наводит курсор на указанную категорию.
     *
     * @param category категория товара.
     */
    @Step("Выбор категории каталога: категория '{category}'")
    public void navigateCatalogCategory(String category) {
        WebElement categoryElement = waitElementVisible(getCategoryLocator(category));
        new Actions(driver).moveToElement(categoryElement).perform();
    }

    /**
     * Метод для выбора подкатегории товара в каталоге.
     * Кликает на указанную подкатегорию.
     *
     * @param subcategory подкатегория товара
     */
    @Step("Выбор подкатегории каталога: подкатегория '{subcategory}'")
    public void navigateCatalogSubcategory(String subcategory) {
        WebElement subcategoryElement = waitElementClickable(getSubcategoryLocator(subcategory));
        subcategoryElement.click();
    }

    /**
     * Метод, который позволяет выбирать категорию и подкатегорию товара.
     * Помогает открыть страницу по выбранным параметрам.
     *
     * @param category    категория товара
     * @param subcategory подкатегория товара
     */
    @Step("Выбор категории и подкатегории каталога: категория '{category}', подкатегория '{subcategory}'")
    public void navigateCatalog(String category, String subcategory) {
        navigateCatalogCategory(category);
        navigateCatalogSubcategory(subcategory);
    }
}