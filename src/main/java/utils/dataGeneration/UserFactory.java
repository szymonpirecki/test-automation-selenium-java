package utils.dataGeneration;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import model.user.SocialTitle;
import model.user.User;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class UserFactory {
    private static final Faker faker = new Faker();

    public User getAlreadyRegisteredUser() {
        return User.builder()
                .firstName(System.getProperty("firstName"))
                .lastName(System.getProperty("lastName"))
                .socialTitle(SocialTitle.fromString(System.getProperty("socialTitle")))
                .birthdate(convertStringToDate(System.getProperty("birthdate")))
                .email(System.getProperty("email"))
                .password(System.getProperty("password"))
                .receivePartnerOffers(Boolean.parseBoolean(System.getProperty("receivePartnerOffers")))
                .agreeToDataPrivacy(Boolean.parseBoolean(System.getProperty("agreeToDataPrivacy")))
                .subscribeToNewsletter(Boolean.parseBoolean(System.getProperty("subscribeToNewsletter")))
                .acceptTermsAndPrivacy(Boolean.parseBoolean(System.getProperty("acceptTermsAndPrivacy")))
                .build();
    }

    public User getRandomUser() {
        return User.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .socialTitle(SocialTitle.getRandomTitle())
                .birthdate(faker.date().birthday())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .receivePartnerOffers(faker.bool().bool())
                .agreeToDataPrivacy(true)
                .subscribeToNewsletter(faker.bool().bool())
                .acceptTermsAndPrivacy(true)
                .build();
    }

    @SneakyThrows
    public Date convertStringToDate(String dateString) {
        return new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
    }
}
