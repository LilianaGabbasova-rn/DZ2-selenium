package helpers;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * Класс для предоставления параметров тестирования.
 * Содержит методы для передачи тестовых данных в параметризованные тесты.
 *
 * @author Габбасова Лилиана Альбертовна
 * @version 2
 */
public class DataProvider {
    /**
     * Метод для предоставления параметров тестирования фильтрации ноутбуков.
     *
     * @return поток аргументов, содержащий категорию, подкатегорию, минимальную и
     * максимальную цену, а также названия брендов для фильтрации и количество товаров на странице.
     */
    public static Stream<Arguments> parametersCheck() {
        return Stream.of(
                Arguments.of(ConfigProvider.CATEGORY,
                        ConfigProvider.SUBCATEGORY,
                        ConfigProvider.MIN_PRICE,
                        ConfigProvider.MAX_PRICE,
                        ConfigProvider.BRANDS,
                        ConfigProvider.MIN_PRODUCTS_ON_PAGE)
        );
    }
}
