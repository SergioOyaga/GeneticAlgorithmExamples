package org.soyaga.examples.NQueenProblem.SimpleGAs.HashSets;

import org.soyaga.ga.Evaluable.FeasibilityFunction;
import org.soyaga.ga.GeneticInformationContainer.Chromosome.HashSetChromosome;
import org.soyaga.ga.GeneticInformationContainer.Genome.Genome;
import org.soyaga.ga.GeneticInformationContainer.Genome.HashSetGenome;

/**
 * This class Is in charge of providing an evaluate method to compute the feasibility of a solution. This is the
 * feasibility of an individuals' genome.
 */
public class ChessFeasibilityFunction extends FeasibilityFunction {

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
        HashSetGenome<HashSetChromosome> genomeObject = (HashSetGenome) genome;
        int nQueens = genomeObject.getGeneticInformation().size();
        for(HashSetChromosome<ChessGen> chromosome:genomeObject.getGeneticInformation()){
            for(ChessGen gen:chromosome.getGeneticInformation()){
                if((Boolean) gen.getGeneticInformation()){
                    count+=1;
                }
            }
        }
        return Math.abs(count-nQueens)*1.;
    }
}
