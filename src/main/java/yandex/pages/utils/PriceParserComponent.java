package yandex.pages.utils;

/**
 * Класс для парсинга цен из текста.
 * Предоставляет методы для извлечения числового значения цены.
 *
 *
 * @author Габбасова Лилиана Альбертовна
 * @version 2
 */
public class PriceParserComponent {
    /**
     * Константа для обозначения невалидной цены.
     */
    private static final double INVALID_PRICE = 0.0;

    /**
     * Метод для извлечения числового значения цены из текста.
     * Удаляет все нечисловые символы и преобразует оставшуюся строку в число.
     *
     * @param priceText текст содержащий цену
     * @return числовое значение цены или 0.0 если цена не может быть распознана
     */
    public static double extractPriceFromText(String priceText) {
        if (priceText == null) return INVALID_PRICE;
        String cleanPrice = priceText
                .replace("Цена", "")
                .replace("₽", "")
                .replace(" ", "")
                .replace(" ", "")
                .trim();
        cleanPrice = cleanPrice.replaceAll("[^\\d]", "");

        if (!cleanPrice.isEmpty() && cleanPrice.matches("\\d+")) {
            return Double.parseDouble(cleanPrice);
        }

        return INVALID_PRICE;
    }
}