package helpers;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.List;

/**
 * Класс для предоставления конфигурационных параметров тестирования.
 * Загружает настройки из конфигурационных файлов и предоставляет к ним доступ.
 *
 * @author Габбасова Лилиана Альбертовна
 * @version 2
 */
public interface ConfigProvider {
    Config config = readConfig();

    /**
     * Метод для чтения конфигурации из файла.
     * Если при запуске указан параметр testProfile, используется указанный файл,
     * иначе загружается tests.conf.
     *
     * @return объект Config с загруженными настройками
     */
    static Config readConfig() {
        return ConfigFactory.systemProperties().hasPath("testProfile")
                ? ConfigFactory.load(ConfigFactory.systemProperties().getString("testProfile")) :
                ConfigFactory.load("tests.conf");
    }

    /**
     * Базовый URL для тестирования
     */
    String URL = config.getString("yandex.url");

    /**
     * Время неявного ожидания в секундах
     */
    Integer IMPLICIT_WAIT = config.getInt("time.implicit_wait");

    /**
     * Время явного ожидания в секундах
     */
    Integer EXPLICIT_WAIT = config.getInt("time.explicit_wait");

    /**
     * Минимальное количество товаров на странице для валидации
     */
    Integer MIN_PRODUCTS_ON_PAGE = config.getInt("validation.min_products_on_page");

    /**
     * Категория товаров для тестирования
     */
    String CATEGORY = config.getString("catalog.category");

    /**
     * Подкатегория товаров для тестирования
     */
    String SUBCATEGORY = config.getString("catalog.subcategory");

    /**
     * Минимальная цена для фильтрации
     */
    Integer MIN_PRICE = config.getInt("filters.price.min");

    /**
     * Максимальная цена для фильтрации
     */
    Integer MAX_PRICE = config.getInt("filters.price.max");

    /**
     * Список брендов для фильтрации
     */
    List<String> BRANDS = config.getStringList("filters.brands");

    /**
     * Путь к драйверу Chrome
     */
    String CHROME_DRIVER_PATH = config.getString("driver.chrome_driver_path");
}