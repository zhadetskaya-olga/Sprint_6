package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;


public class CreateOrderPageSamokat {
    private final WebDriver driver;
    //поле Имя
    private final By nameField = By.xpath("//*[@placeholder='* Имя']");
    // поле Фамилия
    private final By surnameField = By.xpath("//*[@id='root']/div/div[2]/div[2]/div[2]/input");
    //поле Адрес:куда везти заказ
    private final By addressField = By.xpath("//*[@id='root']/div/div[2]/div[2]/div[3]/input");
    //поле Телефон: на него позвонит курьер
    private final By phoneField = By.xpath("//input[contains(@placeholder, '* Телефон: на него позвонит курьер')]");
    //кнопка Далее
    private final By nextStepButton = By.xpath("//div[@class='Order_NextButton__1_rCA']/button[text()='Далее']");
    //поле Станция метро
    public final By metroStationField = By.xpath("//div[@class='select-search__value']");
    //поле ввода Станции метро
    public final By metroStationInput = By.xpath("//input[@tabindex='0']");
    //кнопка с нужной станцией метро*
    public final By metroStationButton = By.xpath("//button[contains(@class, 'select-search__option')]");

    //поле Когда привезти самокат
    private final By dateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    //стрелочка для разворачивания выпадающего списка Срок аренды
    public final By durationDropDownArrow = By.xpath("//span[@class='Dropdown-arrow']");
    //Выпадающий список Срок аренды
    public final By durationDropDownMenu = By.xpath("//div[@class='Dropdown-menu']");
    //опции выпадающего списка Срок аренды
    public final By durationDropDownOptions = By.xpath("//div[@class='Dropdown-option']");
//    //поле с чекбоксами Цвет самоката
//    public final By setColorCheckBoxes = By.xpath("//div[@class='Order_Checkboxes__3lWSI']");

    //поле Комментарий для курьера
    private final By commentField = By.xpath("//input[contains(@placeholder, 'Комментарий')]");
    //кнопка Заказать под второй формой
    private final By finalOrderButton = By.xpath("//div[@class='Order_Buttons__1xGrp']/button[text()='Заказать']");
    //кнопка Да на форме Хотите оформить заказ
    public final By confirmOrderButton = By.xpath("//div[@class='Order_Modal__YZ-d3']//button[text()='Да']");
    //окно Заказ оформлен
    private final By successCreatingOrderWindow = By.xpath(".//*[@class='Order_Modal__YZ-d3']");

    public CreateOrderPageSamokat(WebDriver driver) {
        this.driver = driver;
    }

    public void clickNextStepButton() {
        driver.findElement(nextStepButton).click();
    }

    public void setNameAndSurname(String name, String surname) {
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(surnameField).sendKeys(surname);
    }

    public void setAddress(String address) {
        driver.findElement(addressField).click();
        driver.findElement(addressField).sendKeys(address);
    }

    //
    public void setStation(String stationName) {
        driver.findElement(metroStationField).click();
        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions.visibilityOfElementLocated(metroStationButton));
        try {
            driver.findElement(By.xpath(".//div[contains(text(),'" + stationName + "')]/parent::button")).click();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Станции " + stationName + " не существует!");
        } catch (ElementClickInterceptedException e) {
            MainPageSamokat mainPage = new MainPageSamokat(driver);
            mainPage.acceptCookies();
        }
    }


    public void setPhoneNumber(String number) {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOf(driver.findElement(phoneField)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", driver.findElement(phoneField));
        driver.findElement(phoneField).sendKeys(number);
    }

    public void fillFirstOrderForm(String name, String surname, String address, String stationName, String phoneNumber) {
        setNameAndSurname(name, surname);
        setAddress(address);
        setStation(stationName);
        setPhoneNumber(phoneNumber);
        clickNextStepButton();
    }

    public void SetDate(String date) {
        driver.findElement(dateField).sendKeys(date);
    }


    public void setRentDuration(String rentDuration) {
        driver.findElement(durationDropDownArrow).click();

        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions.visibilityOfElementLocated(durationDropDownMenu));

        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions.visibilityOfElementLocated(durationDropDownOptions));

        try {
            driver.findElement(By.xpath(".//div[@role='option' and text()='" + rentDuration + "']")).click();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Срока аренды на " + rentDuration + " не существует!");
        }

    }

    public void setColor(String color) {

        if (color.equals("чёрный жемчуг")) {
            driver.findElement(By.xpath(".//input[@id='black']")).click();

        } else if (color.equals("серая безысходность")) {
            driver.findElement(By.xpath(".//input[@id='grey']")).click();
        } else {
            throw new NoSuchElementException("Самоката имеющего " + color + " цвет не существует!");
        }
    }

    public void setComment(String comment) {
        driver.findElement(commentField).sendKeys(comment);
    }

    public void fillSecondOrderForm(String date, String rentDuration, String color, String comment) {
        SetDate(date);
        setRentDuration(rentDuration);
        setColor(color);
        setComment(comment);
        driver.findElement(finalOrderButton).click();
        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions.visibilityOfElementLocated(confirmOrderButton));

        driver.findElement(confirmOrderButton).click();
    }

    public boolean assertSuccessWindowIsDisplayed() {
        try {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement successWindow = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(successCreatingOrderWindow)
            );
            return successWindow.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}