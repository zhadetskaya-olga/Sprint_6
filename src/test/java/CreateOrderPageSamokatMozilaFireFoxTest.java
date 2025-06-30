import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import pages.CreateOrderPageSamokat;
import pages.MainPageSamokat;

import java.util.stream.Stream;

public class CreateOrderPageSamokatMozilaFireFoxTest {
    private WebDriver driver;
    private CreateOrderPageSamokat createOrderPage;
    private MainPageSamokat mainPage;


    @BeforeEach
    public void setUp() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new FirefoxDriver(options);

        createOrderPage = new CreateOrderPageSamokat(driver);
        mainPage = new MainPageSamokat(driver);
        mainPage.acceptCookies();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }

    }

    private static Stream<Arguments> createOrderData() {
        return Stream.of(
                Arguments.of(
                        "Василий", "Пупкин", "ул. Чупапи-Муняня, 20", "Сокольники", "+37529786453",
                        "15.07.2025", "сутки", "чёрный жемчуг", "Не звоните, все равно не подниму"
                ),
                Arguments.of(
                        "Торфин", "Карлсефни", "улица Варваров, 33", "Проспект Мира", "+80983232345",
                        "20.12.2025", "семеро суток", "серая безысходность", "Я сбегу так далеко, как только смогу. Пока мне есть куда бежать за оружие я не возьмусь"
                ),
                Arguments.of(
                        "анна", "каренина", "Проспект Держинского 2", "Черкизовская", "40934340345",
                        "03.01.25", "трое суток", "серая безысходность", ""
                )
        );
    }

    @ParameterizedTest
    @MethodSource("createOrderData")
    public void shouldOpenSuccessWindowAfterCreatingOrder(String name, String surname, String address, String metroStation, String phoneNumber,
                                                          String rentDate, String rentDuration, String color, String comment) {
        mainPage.clickTopOrderButton();

        createOrderPage.fillFirstOrderForm(name, surname, address, metroStation, phoneNumber);
        createOrderPage.fillSecondOrderForm(rentDate, rentDuration, color, comment);

        Assertions.assertTrue(createOrderPage.assertSuccessWindowIsDisplayed(), "Создание заказа не произошло");


    }


}