package helpers;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.WebDriverListener;

import java.lang.reflect.Method;

/**
 * Кастомный класс-наблюдатель для интеграции Allure Reports с WebDriver.
 * Реализует интерфейс WebDriverListener для перехвата вызовов методов WebDriver.
 * Автоматически создает скриншоты после выполнения каждого метода и добавляет их в отчет Allure.
 *
 * @author Габбасова Лилиана Альбертовна
 * @version 2
 * @see WebDriverListener
 */

public class CustomAllure implements WebDriverListener {
    /**
     * Веб-драйвер для управления браузером и создания скриншотов.
     */
    private WebDriver driver;

    /**
     * Жизненный цикл Allure для управления вложениями и шагами.
     */
    private AllureLifecycle lifecycle;

    /**
     * Конструктор класса CustomAllure.
     *
     * @param driver экземпляр WebDriver для взаимодействия с браузером.
     */
    public CustomAllure(WebDriver driver) {
        this.driver = driver;
        this.lifecycle = Allure.getLifecycle();
    }

    /**
     * Метод, выполняемый после любого вызова метода WebDriver.
     * Автоматически создает скриншот с описанием имени вызванного метода.
     *
     * @param target объект, у которого был вызван метод.
     * @param method вызванный метод.
     * @param args   аргументы метода.
     * @param result результат выполнения метода.
     */
    @Override
    public void afterAnyCall(Object target, Method method, Object[] args, Object result) {
        takeScreenShot("Метод: " + method.getName());
    }

    /**
     * Метод для создания скриншота и добавления его в отчет Allure.
     *
     * @param description описание скриншота для отображения в отчете.
     */
    public void takeScreenShot(String description) {
        byte[] screenBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        lifecycle.addAttachment(description, "image/png", "png", screenBytes);
    }
}