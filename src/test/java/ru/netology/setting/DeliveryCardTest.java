package ru.netology.setting;


import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DeliveryCardTest {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    //    присваиваем формат даты
    LocalDate todayPlusThree = LocalDate.now().plusDays(3);
    //    прибавляет дни к текущей дате
    String dateToEnter = todayPlusThree.format(formatter);

    @Test
    void orderDeliveryRequest() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");

        form.$("[data-test-id=city] input").setValue("Ярославль");
        form.$("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        form.$("[data-test-id=date] input").setValue(dateToEnter);
        form.$("[data-test-id=name] input").setValue("Иванов Анатолий");
        form.$("[data-test-id=phone] input").setValue("+31961992949");


        form.$("[data-test-id=agreement]").click();
        form.$$("[role=button]").find(exactText("Запланировать")).click();

        $(withText("Встреча успешно запланирована на")).waitUntil(visible, 15000);
        $(withText("Встреча успешно запланирована на")).shouldHave(text(dateToEnter));
    }




    @Test
    void orderDeliveryRequestRepeat() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");

        form.$("[data-test-id=city] input").setValue("Ярославль");
        form.$("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        form.$("[data-test-id=date] input").setValue(dateToEnter);
        form.$("[data-test-id=name] input").setValue("Иванов Анатолий");
        form.$("[data-test-id=phone] input").setValue("+31961992949");


        form.$("[data-test-id=agreement]").click();
        form.$$("[role=button]").find(exactText("Запланировать")).click();

        $(withText("Необходимо подтверждение")).waitUntil(visible, 15000);
    }

    @Test
    void orderDeliveryNotCity() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");

        form.$("[data-test-id=city] input").setValue("Порт");
        form.$("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        form.$("[data-test-id=date] input").setValue(dateToEnter);
        form.$("[data-test-id=name] input").setValue("Иванов Анатолий");
        form.$("[data-test-id=phone] input").setValue("+79991992949");

        form.$("[data-test-id=agreement]").click();
        form.$$("[role=button]").find(exactText("Запланировать")).click();

        $(withText("Доставка в выбранный город недоступна")).waitUntil(visible, 15000);
    }

    @Test
    void orderDeliveryNotName() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");

        form.$("[data-test-id=city] input").setValue("Москва");
        form.$("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        form.$("[data-test-id=date] input").setValue(dateToEnter);
        form.$("[data-test-id=name] input").setValue("");
        form.$("[data-test-id=phone] input").setValue("+79991992949");

        form.$("[data-test-id=agreement]").click();
        form.$$("[role=button]").find(exactText("Запланировать")).click();

        $(withText("Поле обязательно для заполнения")).waitUntil(visible, 15000);
    }

    @Test
    void orderDeliveryNotCorrectName() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");

        form.$("[data-test-id=city] input").setValue("Москва");
        form.$("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        form.$("[data-test-id=date] input").setValue(dateToEnter);
        form.$("[data-test-id=name] input").setValue("Igor Petrov");
        form.$("[data-test-id=phone] input").setValue("+79991992949");

        form.$("[data-test-id=agreement]").click();
        form.$$("[role=button]").find(exactText("Запланировать")).click();

        $(withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(visible, 15000);
    }

    @Test
    void orderDeliveryNotCorrectTel() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");

        form.$("[data-test-id=city] input").setValue("Москва");
        form.$("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        form.$("[data-test-id=date] input").setValue("17.08.2020");
        form.$("[data-test-id=name] input").setValue("Иван Иванов");
        form.$("[data-test-id=phone] input").setValue("123456");

        form.$("[data-test-id=agreement]").click();
        form.$$("[role=button]").find(exactText("Запланировать")).click();

        $(withText("Встреча успешно запланирована на")).waitUntil(visible, 15000);
    }

    @Test
    void orderDeliveryNotClickCheckbox() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");

        form.$("[data-test-id=city] input").setValue("Москва");
        form.$("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        form.$("[data-test-id=date] input").setValue(dateToEnter);
        form.$("[data-test-id=name] input").setValue("Иван Иванов");
        form.$("[data-test-id=phone] input").setValue("+0 000 000 000");


        form.$$("[role=button]").find(exactText("Запланировать")).click();

        $(".input_invalid[data-test-id=agreement]").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    void orderDeliveryDate() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");

        form.$("[data-test-id=city] input").setValue("Ярославль");
        form.$("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        form.$("[data-test-id=date] input").setValue("19.07.2020");
        form.$("[data-test-id=name] input").setValue("Иванов Анатолий");
        form.$("[data-test-id=phone] input").setValue("+78901992949");

        form.$("[data-test-id=agreement]").click();
        form.$$("[role=button]").find(exactText("Запланировать")).click();

        $(withText("Заказ на выбранную дату невозможен")).waitUntil(visible, 15000);
    }

    @Test
    void orderDeliveryNotCorrectDate() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");

        form.$("[data-test-id=city] input").setValue("Ярославль");
        form.$("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        form.$("[data-test-id=date] input").setValue("00.00.0000");
        form.$("[data-test-id=name] input").setValue("Иванов Анатолий");
        form.$("[data-test-id=phone] input").setValue("+71191992949");

        form.$("[data-test-id=agreement]").click();
        form.$$("[role=button]").find(exactText("Запланировать")).click();

        $(withText("Неверно введена дата")).waitUntil(visible, 15000);
    }


}


