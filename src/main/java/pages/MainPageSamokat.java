package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;


public class MainPageSamokat {
    private final WebDriver driver;
    //УРЛ
    private final String websiteURL = "https://qa-scooter.praktikum-services.ru";
    //Кнопка Пинять куки
    private final By acceptCookiesButton = By.xpath(".//button[@id='rcc-confirm-button']");
    //Аккордеон
    private final By accordionBlock = By.xpath("//*[@id='root']/div/div/div[5]/div[2]/div");
    //Кнопка Заказать маленькая
    private final By createOrderTopButton = By.xpath("//button[text()='Заказать' and @class='Button_Button__ra12g']");
    //Кнопка Заказать большая
    private final By createOrderBottomButton = By.xpath("//*[@id='root']/div/div/div[4]/div[2]/div[5]/button");
    //Форма заказа
    private final By createorderForm = By.xpath("/html/body/div/div/div[2]");


    public MainPageSamokat(WebDriver driver) {
        this.driver = driver;
        driver.get(websiteURL);
    }

    public void acceptCookies() {
        try {
            WebElement cookieBanner = new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.presenceOfElementLocated(acceptCookiesButton));
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.visibilityOf(cookieBanner));
            driver.findElement(acceptCookiesButton).click();

        } catch (Exception e) {

        }
    }
    public boolean assertCreateOrderFormIsOpened(){
        return driver.findElement(createorderForm).isDisplayed();
    }


    public void clickTopOrderButton() {
        driver.findElement(createOrderTopButton).click();
    }

    public void clickBottomOrderButton() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});",  driver.findElement(createOrderBottomButton));
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(createOrderBottomButton));
        driver.findElement(createOrderBottomButton).click();
    }

    public void clickQuestionButton(int questionNumber) {
        WebElement button = driver.findElement(By.xpath(".//*[@id='accordion__heading-" + questionNumber + "']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", driver.findElement(accordionBlock));
        new WebDriverWait(driver, Duration.ofSeconds(1)).until(ExpectedConditions.elementToBeClickable(button));
        try {
            button.click();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Вопроса под номером " + questionNumber + " не существует");
        }
    }

    public String getTextFromAnswerPanel(int answerNumber) {

        try {

            WebElement questionButton = driver.findElement(
                    By.xpath(".//*[@id='accordion__heading-" + answerNumber + "']"));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center'});", questionButton);
            WebElement answerPanel = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath(".//div[@id='accordion__panel-" + answerNumber + "']/p")));

            return answerPanel.getAttribute("textContent").trim();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Ответа под номером " + answerNumber + " не существует");
        }
    }

}

