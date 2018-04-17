package examplejava;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.stream.Stream;

public class OptionalExample {
    public static void main(String[] args) {
        //Creating Optionals
        Optional<String> name = Optional.of("Hello");

        //Transforming
        name.map(String::toUpperCase);

        //Filter non empty from list of Optionals
        Stream.of(name, Optional.empty())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(System.out::println); //Prints Hello

        //Do something only if not null
        name.ifPresent(System.out::println);

        //Default values in case of null
        int length = name.map(String::length).orElse(0);

        //Throw exception in case of null
        length = name.map(String::length).orElseThrow(() -> new IllegalArgumentException("Can't be null"));
    }

    public static String javaMethodWithNullable(String nullableInput,
                                                @NotNull  String nonNullableAnnotatedInput ,
                                                @Nullable String nullableAnnotatedInput){
        return nullableInput.toUpperCase() + nonNullableAnnotatedInput.toUpperCase() + nullableAnnotatedInput.toUpperCase();
    }


}
