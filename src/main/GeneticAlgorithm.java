package main;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeneticAlgorithm {

    public void start(){
        List<Individual> population = new ArrayList<Individual>();
        initPopulation(population);

        for (int i = 0; i < Parameters.MAX_ITERATIONS; i++) {
            Collections.sort(population);
            System.out.println("Step № " + i + "--> Top " + population.get(0));
            if (population.get(0).fitness_function < Parameters.ACCURACY ) break;
            population = crossing(population);
        }
    }

    private List<Individual> crossing(List<Individual> population){

        int size = (int) (Parameters.SIZE_POPULATION * Parameters.PERCENTAGE_OF_PAIR_TO_CROSSING);
        List<Individual> children = new ArrayList<Individual>();
        selection(population,children,size);

        for (int i = 0; i < Parameters.SIZE_POPULATION; i++) {

            int firstIndividualNumber = (int) (Math.random() * Parameters.SIZE_POPULATION * Parameters.SURVIVE_RATE);
            int secondIndividualNumber = (int) (Math.random() * Parameters.SIZE_POPULATION * Parameters.SURVIVE_RATE);

            int crossingPosition_x1 = (population.get(i).getBin_x1().length() /2);
            int crossingPosition_x2 = (population.get(i).getBin_x2().length() /2);

            String str1 = population.get(firstIndividualNumber).getBin_x1().substring(0, crossingPosition_x1) +
                    population.get(secondIndividualNumber).getBin_x1().substring(crossingPosition_x1);

            String str2 = population.get(firstIndividualNumber).getBin_x2().substring(0, crossingPosition_x2) +
                    population.get(secondIndividualNumber).getBin_x2().substring(crossingPosition_x2);

            double child_x1 = Double.longBitsToDouble(new BigInteger(str1, 2).longValue());
            double child_x2 = Double.longBitsToDouble(new BigInteger(str2, 2).longValue());

            if (Math.random() < Parameters.MUTATION_RATE){
                child_x1 = Double.longBitsToDouble(new BigInteger(mutation(str1), 2).longValue());
                child_x2 = Double.longBitsToDouble(new BigInteger(mutation(str2), 2).longValue());
            }
//            if (Double.isNaN(child_x1) | Double.isInfinite(child_x1)) child_x1 = 1;
//            if (Double.isNaN(child_x2) | Double.isInfinite(child_x2)) child_x2 = 1;

            Individual child = new Individual(child_x1, child_x2);
            children.add(child);
        }
        return children;
    }

    private String mutation(String str){
        StringBuilder s = new StringBuilder(str);
        int mutationPosition = (int) (Math.random() * str.length());

        for (int i = 0; i < mutationPosition; i ++){
            if(s.charAt(mutationPosition)=='0')s.setCharAt(mutationPosition,'1');
            else if(s.charAt(mutationPosition)=='1') s.setCharAt(mutationPosition,'0');
        }
        return s.toString();
    }

    private void selection(List<Individual> population, List<Individual> children, int size) {
        for(int i=0; i<size; i++){
            children.add(population.get(i));
        }
    }

    private void initPopulation(List<Individual> population){
        for (int i = 0; i < Parameters.SIZE_POPULATION; i++) {
            double x1 = getRandomDoubleInRange();
            double x2 = getRandomDoubleInRange();
            Individual individual = new Individual(x1,x2);
            population.add(individual);
        }
        showPopulation(population);
    }

    private double getRandomDoubleInRange(){
        return (Math.random() * ((Parameters.RANGE_MAX - Parameters.RANGE_MIN) + 1)) + Parameters.RANGE_MIN;
    }

    private void showPopulation (List<Individual> population){
        for (Individual individual : population) {
            System.out.println(individual);
        }
    }
}
