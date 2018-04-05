package examplejava;

import java.util.Arrays;
import java.util.List;

public class StatsCalculatorV1 {

    enum Methods implements Stats{
        SUM {
            @Override
            public double calculate(List<Integer> values) {
                return (double) values.stream().mapToInt(i -> i).sum();
            }
        },
        COUNT {
            @Override
            public double calculate(List<Integer> values) {
                return (double) values.stream().count();
            }
        },
        AVG {
            @Override
            public double calculate(List<Integer> values) {
                return SUM.calculate(values) / COUNT.calculate(values);
            }
        }

    }

    interface Stats{
        double calculate(List<Integer> values);

    };

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

        System.out.println(Methods.AVG.calculate(numbers));
        System.out.println(Methods.SUM.calculate(numbers));
    }


}
