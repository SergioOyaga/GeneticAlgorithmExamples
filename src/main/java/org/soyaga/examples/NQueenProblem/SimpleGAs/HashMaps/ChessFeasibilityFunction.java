package org.soyaga.examples.NQueenProblem.SimpleGAs.HashMaps;

import lombok.AllArgsConstructor;
import org.soyaga.ga.Evaluable.FeasibilityFunction;
import org.soyaga.ga.GeneticInformationContainer.Chromosome.HashMapChromosome;
import org.soyaga.ga.GeneticInformationContainer.Gen.GenericGen;
import org.soyaga.ga.GeneticInformationContainer.Genome.Genome;
import org.soyaga.ga.GeneticInformationContainer.Genome.HashMapGenome;

import java.util.Map;

/**
 * This class Is in charge of providing an evaluate method to compute the feasibility of a solution. This is the
 * feasibility of an individuals' genome.
 */
@AllArgsConstructor
public class ChessFeasibilityFunction extends FeasibilityFunction {
    /**
     * Number of Queens.
     */
    private final Integer nQueens;


    /**
     * Computes the feasibility of an individuals Genome. In this case we consider that the solution is feasible if the
     * genome contains exactly N Queens. This is N trues as gen values.
     * @param genome Genome object to evaluate.
     * @param objects VarArgs Object that allow to keep/retain information from the evaluation to be used in the
     *                 decision-making.
     * @return Double with the Feasibility value.
     */
    @Override
    public Double evaluate(Genome genome, Object... objects) {
        int count = 0;
        HashMapGenome<Integer, HashMapChromosome> genomeObject = (HashMapGenome) genome;
        for(Map.Entry<Integer,HashMapChromosome> chromosome:genomeObject.getGeneticInformation()){
            HashMapChromosome<Integer,GenericGen> genes = chromosome.getValue();
            for(Map.Entry<Integer,GenericGen> gen:genes.getGeneticInformation()){
                if((Boolean) gen.getValue().getGeneticInformation()){
                    count+=1;
                }
            }
        }
        return Math.abs(count-this.nQueens)*1.;
    }
}