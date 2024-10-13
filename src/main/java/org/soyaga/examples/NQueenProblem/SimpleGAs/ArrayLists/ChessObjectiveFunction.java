package org.soyaga.examples.NQueenProblem.SimpleGAs.ArrayLists;

import org.soyaga.ga.Evaluable.Objective.ObjectiveFunction;
import org.soyaga.ga.GeneticInformationContainer.Chromosome.ArrayListChromosome;
import org.soyaga.ga.GeneticInformationContainer.Gen.GenericGen;
import org.soyaga.ga.GeneticInformationContainer.Genome.Genome;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class evaluates the objective function of an individual's genome.
 * It measures how well adapted an individual's genome is to its environment,
 * bringing it closer to the optimal solution.
 */
public class ChessObjectiveFunction implements ObjectiveFunction {
    /**
     * This function calculates the quality of an individual's genome
     * by checking for collisions only along the diagonals.
     *
     * @param genome The Genome object to be evaluated.
     * @param objects VarArgs Object that retains information from the evaluation for use in decision-making.
     * @return A Double containing the value of the objective function for this individual.
     */
    @Override
    public Double evaluate(Genome<?> genome, Object... objects) {
        ArrayList<ArrayListChromosome<GenericGen<Integer>>> chromosomes =
                (ArrayList<ArrayListChromosome<GenericGen<Integer>>>) genome.getGeneticInformation();
        double Objective=0.;
        int numberOfQueens = chromosomes.size();
        int [] f1=new int [numberOfQueens],f2=new int [numberOfQueens];//to Stores the altered genome.

        for(int i = 0; i< numberOfQueens; i++){
            Integer row = ((chromosomes.get(i).getGeneticInformation()).get(0)).getGeneticInformation();
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
