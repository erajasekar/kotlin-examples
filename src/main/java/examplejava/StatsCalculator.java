package examplejava;

public class StatsCalculator {

    interface Stats{};

    class Avg implements Stats{

        private Avg INSTANCE = new Avg();

        private Avg(){}
    }


}
