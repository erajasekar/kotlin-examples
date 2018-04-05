package examplejava;

import com.google.common.math.Quantiles;

import java.util.Arrays;
import java.util.List;

import static examplejava.StatsCalculator.Method.*;

public class StatsCalculator implements Stats {

    private static StatsCalculator SUM_INSTANCE = new StatsCalculator(SUM);
    private static StatsCalculator COUNT_INSTANCE = new StatsCalculator(COUNT);
    private static StatsCalculator AVG_INSTANCE = new StatsCalculator(AVG);

    private int percentile = 0;
    private Method method;

    @Override
    public double calculate(List<Integer> values) {
        switch (method){
            case AVG: return (double) values.stream().mapToInt(i -> i).sum() / values.stream().count()  ;
            case SUM: return (double) values.stream().mapToInt(i -> i).sum();
            case COUNT:return (double) values.stream().count();
            case QUANTILE: return Quantiles.percentiles().index(percentile).compute(values);
            default: throw new IllegalArgumentException("Invalid stat method");
        }

    }


    enum Method{
        SUM ,
        AVG,
        COUNT,
        QUANTILE;

    }

    private StatsCalculator(Method method, int percentile){
        this.method = method;
        this.percentile = percentile;
    }

    private StatsCalculator(Method method){
        this.method = method;
    }

    public static StatsCalculator getSimpleStats(Method method){
        switch (method){
            case AVG:return AVG_INSTANCE;
            case SUM:return SUM_INSTANCE;
            case COUNT:return COUNT_INSTANCE;
            default: throw new IllegalArgumentException("Should be called only for methods AVG OR SUM, OR COUNT");
        }
    }

    private double calculatePercentile(List<Integer> values){
        return Quantiles.percentiles().index(percentile).compute(values);
    }

    public static StatsCalculator getPercentileStats(Method method, int percentile){
        switch (method){
            case QUANTILE: return new StatsCalculator(method, percentile);
            default: throw new IllegalArgumentException("Should be called only for method QUANTILE");
        }
    }

    ;

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

        System.out.println(getSimpleStats(AVG).calculate(numbers));
        System.out.println(getPercentileStats(QUANTILE,70).calculate(numbers));
    }


}
