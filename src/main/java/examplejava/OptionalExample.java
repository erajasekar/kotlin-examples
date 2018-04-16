package examplejava;

import java.util.Optional;

public class OptionalExample {
    public static void main(String[] args) {
        //Creating Optionals
        Optional<String> name = Optional.of("Hello");

        //Transforming
        name.map(String::toUpperCase);

        //Filter non null
        name.filter(s -> !s.isEmpty());

        //Do something only if not null
        name.ifPresent(System.out::println);

        //Default values in case of null
        int length = name.map(String::length).orElse(0);
        length = name.map(String::length).orElseThrow(() -> new IllegalArgumentException("Can't be null"));


    }
}
