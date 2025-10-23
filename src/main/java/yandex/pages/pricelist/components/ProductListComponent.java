package yandex.pages.pricelist.components;

import io.qameta.allure.Allure;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import yandex.pages.base.BasePage;
import yandex.pages.pricelist.card.ProductCardPage;
import yandex.pages.utils.PriceParserComponent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Класс для работы со списком товаров на странице прайс-листа.
 * Предоставляет методы для проверки товаров по фильтрам и навигации по страницам.
 *
 * @author Габбасова Лилиана Альбертовна
 * @version 2
 * @see BasePage
 * @see ProductListLocators
 * @see PaginationComponent
 * @see LoadProduct
 */
public class ProductListComponent extends BasePage implements ProductListLocators {
    /**
     * Множество для отслеживания проверенных товаров.
     */
    private Set<String> checkedProducts = new HashSet<>();

    /**
     * Константа для ожидаемого количества окон при открытии карточки товара.
     */
    private static final int EXPECTED_WINDOWS_COUNT = 2;

    /**
     * Компонент для работы с пагинацией.
     */
    protected PaginationComponent pagination;

    /**
     * Компонент для ожидания загрузки товаров.
     */
    public LoadProduct load;

    /**
     * Конструктор компонента списка товаров.
     *
     * @param driver экземпляр WebDriver для взаимодействия с браузером.
     */
    public ProductListComponent(WebDriver driver) {
        super(driver);
        this.pagination = new PaginationComponent(driver);
        this.load = new LoadProduct(driver);
    }

    /**
     * Метод для получения списка карточек товаров c ожиданием появления их в доме.
     *
     * @return список веб-элементов карточек товаров
     */
    protected List<WebElement> getProductCards() {
        return waitForElementsPresence(PRODUCT_CARDS);
    }

    /**
     * Метод для проверки минимального количества товаров на странице.
     *
     * @param countProducts минимальное количество товаров для проверки
     * @return true если количество товаров на странице больше или равно указанному значению
     */
    public boolean isMinimumProductsPage(int countProducts) {
        pagination.scrollToPagination();
        load.waitForProductsLoad();
        getProductCards();
        List<WebElement> priceList = getProductCards();
        for (WebElement element : priceList) {
            waitElementVisible(element);
        }
        return priceList.size() >= countProducts;
    }

    /**
     * Метод для проверки соответствия всех товаров на всех страницах заданным фильтрам.
     *
     * @param minPrice минимальная цена для фильтрации
     * @param maxPrice максимальная цена для фильтрации
     * @param brands   список брендов для фильтрации
     * @return true если все товары на всех страницах соответствуют фильтрам
     */
    public boolean checkAllPagesMatchFilters(double minPrice, double maxPrice, List<String> brands) {
        checkedProducts.clear();
        while (pagination.isNextPageAvailable()) {
            pagination.scrollToPagination();
            load.waitForProductsLoad();
        }
        pagination.scrollToPagination();
        load.waitForProductsLoad();

        if (!checkCurrentPageMatchesFilters(minPrice, maxPrice, brands)) {
            return false;
        }
        return true;
    }

    /**
     * Метод для проверки соответствия товаров на текущей странице заданным фильтрам.
     *
     * @param minPrice минимальная цена для фильтрации
     * @param maxPrice максимальная цена для фильтрации
     * @param brands   список брендов для фильтрации
     * @return true если все товары на текущей странице соответствуют фильтрам
     */
    private boolean checkCurrentPageMatchesFilters(double minPrice, double maxPrice, List<String> brands) {
        load.waitForProductsLoad();

        List<WebElement> productCards = driver.findElements(PRODUCT_CARDS);
        List<WebElement> priceElements = driver.findElements(PRODUCT_PRICE);

        String originalWindow = driver.getWindowHandle();

        for (int i = 0; i < productCards.size(); i++) {
            WebElement productCard = productCards.get(i);
            WebElement priceElement = priceElements.get(i);

            JavascriptExecutor js = (JavascriptExecutor) driver;
            String productName = (String) js.executeScript(
                    "return arguments[0].textContent || arguments[0].innerText;", productCard);

            String priceText = (String) js.executeScript(
                    "return arguments[0].textContent || arguments[0].innerText;", priceElement);
            productName = productName.trim();

            PriceParserComponent priceParser = new PriceParserComponent();
            double productPrice = priceParser.extractPriceFromText(priceText);

            String productKey = productName + "_" + productPrice;
            Allure.step("Товар: " + productName + ", цена: " + productPrice);
            if (checkedProducts.contains(productKey)) {
                continue;
            }

            if (productPrice < minPrice || productPrice > maxPrice) {
                return false;
            }

            boolean brandValid = isProductBrandValid(productName, brands);
            if (!brandValid) {
                brandValid = checkBrandInProductCard(productCard, brands, originalWindow);
            }

            if (brandValid) {
                checkedProducts.add(productKey);
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Метод для проверки бренда товара через открытие карточки товара.
     *
     * @param productCard    веб-элемент карточки товара
     * @param brands         список ожидаемых брендов
     * @param originalWindow идентификатор исходного окна браузера
     * @return true если бренд товара соответствует одному из ожидаемых брендов
     */
    private boolean checkBrandInProductCard(WebElement productCard, List<String> brands, String originalWindow) {
        String productName = productCard.getText();
        List<WebElement> productLinks = findElements(PRODUCT_CARDS_LINK);
        int index = -1;
        for (int i = 0; i < productLinks.size(); i++) {
            if (productLinks.get(i).getText().contains(productName)) {
                index = i;
                break;
            }
        }

        if (index == -1) return false;
        WebElement productLink = productLinks.get(index);
        load.waitForProductsLoad();

        String currentPageUrl = driver.getCurrentUrl();
        pagination.js.clickWithJS(productLink);
        webDriverWait.until(ExpectedConditions.numberOfWindowsToBe(EXPECTED_WINDOWS_COUNT));

        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        ProductCardPage productCardPage = new ProductCardPage(driver);
        boolean brandMatches = productCardPage.isBrandMatches(brands);

        driver.close();
        driver.switchTo().window(originalWindow);

        load.waitForProductsLoad();
        webDriverWait.until(d -> d.getCurrentUrl().equals(currentPageUrl));
        return brandMatches;
    }

    /**
     * Метод для проверки валидности бренда товара по названию.
     *
     * @param productName название товара
     * @param brands      список ожидаемых брендов
     * @return true если название товара содержит один из ожидаемых брендов
     */
    public boolean isProductBrandValid(String productName, List<String> brands) {
        for (String brand : brands) {
            if (productName.toLowerCase().contains(brand.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Метод для получения названия первого товара на странице.
     *
     * @return название первого товара или пустую строку если товаров нет
     */
    public String getFirstProductName() {
        pagination.js.scrollToTop();
        load.waitForProductsLoad();
        List<WebElement> products = getProductCards();
        return products.isEmpty() ? "" : products.get(0).getText();
    }

    /**
     * Метод для проверки наличия товара по названию.
     *
     * @param productName название товара для поиска
     * @return true если товар с указанным названием найден на странице
     */
    public boolean isProductPresent(String productName) {
        pagination.scrollToPagination();
        load.waitForProductsLoad();

        List<WebElement> products = getProductCards();
        for (WebElement product : products) {
            String actualProductName = product.getText();
            if (actualProductName.contains(productName)) {
                return true;
            }
        }
        return false;
    }
}