package com.market.yandex;

import helpers.ConfigProvider;
import helpers.CustomAllure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;

import java.util.concurrent.TimeUnit;
/**
 * Базовый абстрактный класс для настройки браузера в тестах.
 * Настраивает Chrome браузер перед каждым тестом и закрывает его после.
 *
 * @author Габбасова Лилиана Альбертовна
 * @version 2
 */
public abstract class BaseTest {
    /**
     * Веб-драйвер для управления браузером.
     */
    protected WebDriver driver;

    /**
     * Метод, который выполняется перед каждым тестом.
     * Инициализирует веб-драйвер и настраивает параметры браузера.
     */
    @BeforeEach
    void setUp() {
        System.setProperty("webdriver.chrome.driver", ConfigProvider.CHROME_DRIVER_PATH);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new ChromeDriver(chromeOptions);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(ConfigProvider.IMPLICIT_WAIT, TimeUnit.SECONDS);
        CustomAllure customAllure = new CustomAllure(driver);
        driver = new EventFiringDecorator<>(customAllure).decorate(driver);
    }

    /**
     * Метод, который выполняется после каждого теста.
     * Закрывает веб-драйвер.
     */
    @AfterEach
    void quitDriver() {
        driver.quit();
    }
}