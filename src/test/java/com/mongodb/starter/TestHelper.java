package com.mongodb.starter;

import com.mongodb.starter.models.Person;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Arrays.asList;

@Component
class TestHelper {

    Person getMax() {
        return new Person().setFirstName("Maxime")
                .setLastName("Beugnet")
                .setAge(31)
                .setInsurance(true);
    }

    Person getAlex() {
        return new Person().setFirstName("Alex")
                .setLastName("Beugnet")
                .setAge(27)
                .setInsurance(false);
    }

    List<Person> getListMaxAlex() {
        return asList(getMax(), getAlex());
    }
}
