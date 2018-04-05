package examplejava;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamExceptionHandling {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /**
     * Converts given date string in format "MM/dd/yyyy" to LocalDate.
     */
    private static LocalDate parseDate(String dateString) {
        return LocalDate.from(formatter.parse(dateString));
    }
    public static void main(String args[]) {

        List<String> dates = Arrays.asList("12/31/2014",
                "01-01-2015",
                "12/31/2015",
                "not a date",
                "01/01/2016");

        List<DayOfWeek> result = new ArrayList<>();
        for(String dateString: dates){

            try{
                LocalDate date = parseDate(dateString);
                result.add(DayOfWeek.from(date));
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        }

        Stream.of("12/31/2014",
                "01-01-2015",
                "12/31/2015",
                "not a date",
                "01/01/2016")
                .map(StreamExceptionHandling::parseDate) //parse string to LocalDate
                .map(DayOfWeek::from) // Map LocalDate to Day of Week
                .forEach(System.out::println); // Print
    }
}
