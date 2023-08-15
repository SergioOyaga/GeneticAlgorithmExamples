package org.soyaga.examples.NQueenProblem.CustomGA;

import org.soyaga.ga.CrossoverPolicy.ParentSelection.Selection;
import org.soyaga.ga.Individual;
import org.soyaga.ga.Population;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.random.RandomGenerator;

/**
 * This class represents a TournamentSelection. In this case, this custom class is just a copy of the built-in class
 * TournamentSelection.
 */
public class CustomSelection implements Selection {
    /**
     * Selection that although receives the generation number is just a copy of the TournamentSelection.
     * We could have varied the behavior of the selection based on the context (iterations, convergence...).
     *
     * @param population Population from where to pick the Individual.
     * @param crossArgs  Undefined Array of elements to perform the parent selection.
     * @return An <b>Individual</b> corresponding to the new parent.
     */
    @Override
    public Individual selectParent(Population population, Object... crossArgs) {
        List<Individual> aux= new ArrayList<>(population.getPopulation());
        List<Individual> subAux;
        Collections.shuffle(aux);
        int startIndex= RandomGenerator.getDefault().nextInt(population.getPopulationSize());
        int endIndex= RandomGenerator.getDefault().nextInt(population.getPopulationSize());
        if(startIndex<=endIndex){
            subAux=aux.subList(startIndex,endIndex+1);
        }
        else{
            subAux=aux.subList(0,endIndex+1);
            subAux.addAll(aux.subList(startIndex, population.getPopulationSize()));
        }
        Collections.sort(subAux);
        return subAux.get(0);
    }
}
