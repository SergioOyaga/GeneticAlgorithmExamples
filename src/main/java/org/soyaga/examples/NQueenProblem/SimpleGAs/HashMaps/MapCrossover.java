package org.soyaga.examples.NQueenProblem.SimpleGAs.HashMaps;

import org.soyaga.ga.CrossoverPolicy.ParentCross.Crossover;
import org.soyaga.ga.GeneticInformationContainer.Genome.HashMapGenome;
import org.soyaga.ga.Individual;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.random.RandomGenerator;

/**
 * Class that applies a Crossover procedure for two Individuals whose Genomes are HashMapGenome.
 */
public class MapCrossover implements Crossover {
    /**
     * Function that computes an individual given two moving a random number of Chromosome Values to from one to the other .
     * @param parent1   Individual with the first parent.
     * @param parent2   Individual with the second parent.
     * @param crossArgs Undefined Array of elements to perform the crossover procedure.
     * @return Individual containing the offspring information.
     */
    @Override
    public Individual computeChild(Individual parent1, Individual parent2, Object[] crossArgs) {
        HashMapGenome childGenome = (HashMapGenome) parent1.getGenome().createCopy();
        HashMapGenome parent2Genome = (HashMapGenome) parent2.getGenome().createCopy();
        Collection<Map.Entry> parent2Chromosomes = parent2Genome.getGeneticInformation();
        ArrayList<Map.Entry> parent2ChromosomesList = new ArrayList<>(parent2Chromosomes);
        Collections.shuffle(parent2ChromosomesList);
        ArrayList<Map.Entry> parent2Part = new ArrayList<>(parent2ChromosomesList.subList(
                0,RandomGenerator.getDefault().nextInt(parent2ChromosomesList.size())));
        childGenome.addAll(parent2Part);
        return new Individual(childGenome, parent1.getFeasibilityFunction(), parent1.getObjectiveFunction());
    }
}
