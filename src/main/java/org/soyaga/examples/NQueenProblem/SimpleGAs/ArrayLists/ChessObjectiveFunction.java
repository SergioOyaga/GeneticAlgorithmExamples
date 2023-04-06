package org.soyaga.examples.NQueenProblem.SimpleGAs.ArrayLists;

import org.soyaga.ga.Evaluable.ObjectiveFunction;
import org.soyaga.ga.GeneticInformationContainer.Chromosome.ArrayListChromosome;
import org.soyaga.ga.GeneticInformationContainer.Gen.GenericGen;
import org.soyaga.ga.GeneticInformationContainer.Genome.Genome;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class Evaluates the objective function to an individuals Genome. This is the
 * well adapted is an individuals' genome to its environment (closer to the optimal solution).
 */
public class ChessObjectiveFunction extends ObjectiveFunction {
    /**
     * Function that computes how good is this individuals' Genome. Checks collisions only in the diagonals.
     * @param genome Genome object to evaluate.
     * @param objects VarArgs Object that allow to keep/retain information from the evaluation to be used in the
     *                 decision-making.
     * @return a Double containing the value of the objective function to this individual.
     */
    @Override
    public Double evaluate(Genome genome, Object... objects) {

        ArrayList<ArrayListChromosome> chromosomes = (ArrayList<ArrayListChromosome>) genome.getGeneticInformation();
        Double Objective=0.;
        Integer numberOfQueens = chromosomes.size();
        int [] f1=new int [numberOfQueens],f2=new int [numberOfQueens];//to store the altered genome.

        for(int i = 0; i< numberOfQueens; i++){
            Integer row = (Integer) (((ArrayList<GenericGen>)chromosomes.get(i).getGeneticInformation()).get(0)).getGeneticInformation();
            f1[i]=row-i;//forward
            f2[i]= numberOfQueens -row-i;//backward
        }
        Arrays.sort(f1);//sort
        Arrays.sort(f2);
        for(int i = 1; i< numberOfQueens; i++){//computes the fitness.
            if(f1[i]==f1[i-1]){
                Objective+=2;
            }if(f2[i]==f2[i-1]){
                Objective+=2;
            }
        }
        return Objective;
    }
}
