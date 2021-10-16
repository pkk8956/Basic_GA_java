package main;

class Individual implements Comparable<Individual>
{
    double x1;
    double x2;

    private final String bin_x1;
    private final String bin_x2;

    public String getBin_x2() {return bin_x2;}
    public String getBin_x1() {return bin_x1;}

    double fitness_function;

    String ToBin(double d){
        return Long.toBinaryString(Double.doubleToRawLongBits(d));
    }

    public Individual(double x1, double x2) {
        this.x1=x1;
        this.x2=x2;
        this.bin_x1=ToBin(this.x1);
        this.bin_x2=ToBin(this.x2);
        this.fitness_function = Math.pow((Math.pow(this.x1,2) + this.x2 - 11),2) + Math.pow((this.x1 + Math.pow(this.x2,2) - 7),2);
    }

    @Override
    public String toString() {
        String s1 = String.format("%.20f", fitness_function);
        String s2 = String.format("%.10f", x1);
        String s3 = String.format("%.10f", x2);
        return "individual [" +
                "X1 = " + s2 +
                ", X2 = " + s3
//                ", bin_x1 = '" + bin_x1 + '\'' +
//                ", bin_x2 = '" + bin_x2 + '\'' +
                + Color.RED_BOLD +", Fitness function = " + s1 + Color.RESET + ']';
    }

    public int compareTo(Individual o) {
        return Double.compare(fitness_function, o.fitness_function);
    }
}
