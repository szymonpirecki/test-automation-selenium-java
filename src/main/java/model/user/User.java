package model.user;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class User {
    private SocialTitle socialTitle;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date birthdate;
    private boolean receivePartnerOffers;
    private boolean agreeToDataPrivacy;
    private boolean subscribeToNewsletter;
    private boolean acceptTermsAndPrivacy;
}
