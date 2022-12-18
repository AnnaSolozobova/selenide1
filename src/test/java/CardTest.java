import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofSeconds;

public class CardTest {
    @BeforeEach
    void shouldOpenBrowser() {
        open("http://localhost:9999/");
    }


    @Test
    public void happyPathTest() {
        String meetingDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val(meetingDate);
        SelenideElement form = $x("//form[contains(@class,form)]");
        SelenideElement notif = $x("//div[@data-test-id=\"notification\"]");
        $x("//input[@placeholder=\"Город\"]").val("Москва");
        $x("//span[text()=\"Москва\"]").shouldHave(Condition.exactText("Москва"));
        form.$x(".//span[@data-test-id=\"name\"]//input").val("Александр Иванов");
        form.$x(".//span[@data-test-id=\"phone\"]//input").setValue("+79031243456");
        form.$x(".//span[@class=\"checkbox__box\"]").click();
        form.$x(".//span[text()=\"Забронировать\"]//ancestor::button").click();
        notif.should(visible, ofSeconds(15));
        notif.$x(".//div[@class='notification__title']").should(text("Успешно!"));
        $("[data-test-id=notification] .notification__content").should(exactText("Встреча успешно забронирована на " + meetingDate));
    }



}
