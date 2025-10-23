package yandex.pages.pricelist.components.filters;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import yandex.pages.base.BasePage;

/**
 * Класс для работы с поисковым компонентом на странице прайс-листа.
 * Предоставляет методы для ввода поискового запроса и выполнения поиска.
 *
 * @author Габбасова Лилиана Альбертовна
 * @version 2
 * @see BasePage
 */
public class SearchComponent extends BasePage {
    /**
     * Локатор поля ввода поиска.
     */
    private final By SEARCH_INPUT = By.id("header-search");

    /**
     * Локатор кнопки поиска.
     */
    private final By SEARCH_BUTTON = By.xpath("//button[@data-auto='search-button']");

    /**
     * Конструктор поискового компонента.
     *
     * @param driver экземпляр WebDriver для взаимодействия с браузером.
     */
    public SearchComponent(WebDriver driver) {
        super(driver);
    }

    /**
     * Метод для ввода текста в поисковую строку.
     *
     * @param text текст для поиска
     */
    @Step("Ввод в поисковую строку запомненного значения: {text}")
    public void inputWord(String text) {
        WebElement searchInput = waitElementVisible(SEARCH_INPUT);
        searchInput.clear();
        searchInput.sendKeys(text);
    }

    /**
     * Метод для нажатия кнопки "Найти" для реализации поиска.
     */
    @Step("Нажатие кнопки Найти")
    public void clickButtonSearch() {
        waitElementClickable(SEARCH_BUTTON).click();
    }
}