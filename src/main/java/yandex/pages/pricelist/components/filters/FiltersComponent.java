package yandex.pages.pricelist.components.filters;

import yandex.pages.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Класс для фильтрации на странице прайс-листа.
 * Товары можно фильтровать по цене и по брендам.
 *
 * @author Габбасова Лилиана Альбертовна
 * @version 2
 * @see BasePage
 */
public class FiltersComponent extends BasePage {
    /**
     * Локатор поля для ввода минимальной цены.
     */
    private final By MIN_PRICE = By.xpath("//input[contains(@id,'min') and contains(@id,'price')]");

    /**
     * Локатор поля для ввода максимальной цены.
     */
    private final By MAX_PRICE = By.xpath("//input[contains(@id,'max') and contains(@id,'price')]");

    /**
     * Локатор кнопки "Показать еще" для отображения всех брендов.
     */
    private final By SHOW_MORE_BUTTON = By.xpath("//div[@data-auto='filter' and contains(.//span,'Бренд')]//div[@data-zone-name='showMoreFilters']//button");

    /**
     * Локатор поля ввода для поиска бренда.
     */
    private final By INPUT_BRAND = By.xpath("//div[@data-auto='filter' and contains(.//span,'Бренд')]//input");

    /**
     * Конструктор фильтрации на странице прайс-листа.
     *
     * @param driver экземпляр WebDriver для взаимодействия с браузером.
     */
    public FiltersComponent(WebDriver driver) {
        super(driver);
    }

    /**
     * Метод для получения локатора бренда по названию.
     *
     * @param brand название бренда.
     * @return локатор бренда.
     */
    private By getBrandLocator(String brand) {
        return By.xpath("//div[@data-auto='filter' and contains(.//span,'Бренд')]//label[@role='checkbox']//span[text()='" + brand + "']");
    }


    /**
     * Метод, который устанавливает значение минимальной цены для фильтрации товаров.
     * После установки значения товары будут отфильтрованы по диапазону цен от указанного значения.
     *
     * @param min минимальное значение цены для фильтрации
     */
    @Step("Установка минимальной цены: {min}")
    public void setMinPrice(double min) {
        WebElement minPrice = waitElementVisible(MIN_PRICE);
        minPrice.click();
        minPrice.clear();
        minPrice.sendKeys(Double.toString(min));
    }

    /**
     * Метод, который устанавливает значение минимальной цены для фильтрации товаров.
     * После установки значения товары будут отфильтрованы по диапазону цен до указанного значения.
     *
     * @param max минимальное значение цены для фильтрации
     */
    @Step("Установка максимальной цены: {max}")
    public void setMaxPrice(double max) {
        WebElement maxPrice = waitElementVisible(MAX_PRICE);
        maxPrice.click();
        maxPrice.clear();
        maxPrice.sendKeys(Double.toString(max));
    }

    /**
     * Метод, который устанавливает значение бренда для фильтрации товаров.
     * После установки значения товары будут отфильтрованы по выбранному бренду.
     *
     * @param brand минимальное значение цены для фильтрации
     */
    @Step("Фильтрация по бренду: {brand}")
    public void filterByBrand(String brand) {
        List<WebElement> brandElements = findElements(getBrandLocator(brand));
        if (!selectBrandFromList(brandElements, brand)) {
            showAllBrands();
            searchAndSelectBrand(brand);
        }
    }

    /**
     * Метод, который показывает все бренды.
     * После нажатия кнопки, открывается список всех доступных брендов.
     */
    public void showAllBrands() {
        List<WebElement> showMoreButton = findElements(SHOW_MORE_BUTTON);
        if (showMoreButton.get(0).isDisplayed()) {
            showMoreButton.get(0).click();
        } else {
            System.out.println("Кнопка неактивна показать больше");
        }
    }

    /**
     * Метод, который выполняет поиск и выбор бренда через поисковую строку.
     * Используется когда бренд не отображается в основном списке.
     *
     * @param brand название бренда для поиска и выбора.
     */
    public void searchAndSelectBrand(String brand) {
        List<WebElement> brandElements = findElements(getBrandLocator(brand));

        if (!brandElements.isEmpty() && brandElements.get(0).isDisplayed()) {
            WebElement brandElement = waitElementClickable(brandElements.get(0));
            brandElement.click();
        } else {
            searchBrandInInput(brand);
        }
    }

    /**
     * Метод, который выполняет поиск бренда через поле ввода.
     * Вводит название бренда в поисковую строку и выбирает найденный результат.
     *
     * @param brand название бренда для поиска.
     */
    private void searchBrandInInput(String brand) {
        WebElement inputBrand = waitElementVisible(INPUT_BRAND);
        inputBrand.click();
        inputBrand.clear();
        inputBrand.sendKeys(brand);

        WebElement foundBrand = waitElementClickable(getBrandLocator(brand));
        foundBrand.click();
    }

    /**
     * Метод, который выбирает бренд из списка доступных брендов.
     * Выполняет поиск бренда в переданном списке элементов и кликает на него если находит.
     *
     * @param brandElements список элементов брендов.
     * @param brand         название бренда для выбора.
     * @return true если бренд был найден и выбран, false если бренд не найден в списке.
     */
    private boolean selectBrandFromList(List<WebElement> brandElements, String brand) {
        for (WebElement brandElement : brandElements) {
            if (brandElement.getText().equalsIgnoreCase(brand)) {
                waitElementVisible(brandElement);
                brandElement.click();
                return true;
            }
        }
        return false;
    }
}