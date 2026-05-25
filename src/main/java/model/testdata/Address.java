package model.testdata;

import java.util.List;

public record Address(String street, String postCode, String city, String country) {

    public List<String> toAddressParts(String firstName, String lastName) {
        return List.of(firstName, lastName, street, postCode, city, country);
    }
}
