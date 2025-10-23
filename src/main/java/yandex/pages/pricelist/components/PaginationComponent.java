package yandex.pages.pricelist.components;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import yandex.pages.base.BasePage;

import java.util.List;

/**
 * Класс для работы с пагинацией на странице прайс-листа.
 * Предоставляет методы для навигации по страницам с товарами.
 *
 * @author Габбасова Лилиана Альбертовна
 * @version 2
 * @see BasePage
 * @see JsExecutor
 */
public class PaginationComponent extends BasePage {
    /**
     * Локатор элемента пагинации.
     */
    private final By PAGINATION_PAGE = By.xpath("//div[@data-auto='pagination-page']");

    /**
     * Локатор кнопки перехода на следующую страницу.
     */
    private final By NEXT_PAGE_BUTTON = By.xpath("//div[@data-zone-name='next']");

    /**
     * JavascriptExecutor для выполнения JavaScript команд.
     */
    protected JsExecutor js;

    /**
     * Конструктор компонента пагинации.
     *
     * @param driver экземпляр WebDriver для взаимодействия с браузером.
     */
    public PaginationComponent(WebDriver driver) {
        super(driver);
        this.js = new JsExecutor(driver);
    }

    /**
     * Метод для прокрутки страницы к элементу пагинации.
     */
    public void scrollToPagination() {
        List<WebElement> paginationElement = findElements(PAGINATION_PAGE);
        js.scrollToElement(paginationElement.get(0));
    }

    /**
     * Метод для проверки доступности следующей страницы.
     *
     * @return true если кнопка следующей страницы доступна и отображается, false в противном случае
     */
    public boolean isNextPageAvailable() {
        List<WebElement> nextPages = findElements(NEXT_PAGE_BUTTON);
        return !nextPages.isEmpty() && nextPages.get(0).isEnabled() && nextPages.get(0).isDisplayed();
    }
}