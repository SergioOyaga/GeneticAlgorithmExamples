package org.soyaga.examples.NQueenProblem.AdHocGA;


import org.soyaga.Initializer.GAInitializer;
import org.soyaga.ga.CrossoverPolicy.CrossoverPolicy;
import org.soyaga.ga.ElitismPolicy.ElitismPolicy;
import org.soyaga.ga.GeneticAlgorithm.SimpleGeneticAlgorithm;
import org.soyaga.ga.GeneticInformationContainer.Genome.ArrayListGenome;
import org.soyaga.ga.MutationPolicy.MutationPolicy;
import org.soyaga.ga.NewbornPolicy.NewbornPolicy;
import org.soyaga.ga.StoppingPolicy.StoppingCriteriaPolicy;

import java.util.ArrayList;

/**
 * Extends SimpleGeneticAlgorithm and defines how we gather the results.
 */
public class ChessGA extends SimpleGeneticAlgorithm {
    private final Integer nQueens;
    public ChessGA(String ID, Integer initialPopulationSize, StoppingCriteriaPolicy stoppingCriteriaPolicy,
                   CrossoverPolicy crossoverPolicy, MutationPolicy mutationPolicy, ElitismPolicy elitismPolicy,
                   NewbornPolicy newbornPolicy, GAInitializer gaInitializer, Integer nQueens) {
        super(ID, initialPopulationSize, stoppingCriteriaPolicy, crossoverPolicy, mutationPolicy, elitismPolicy,
                newbornPolicy, gaInitializer);
        this.nQueens =nQueens;
    }

    /**
     * Transform the genome of the best individual to a String representing its value.
     * @return String.
     */
    @Override
    public Object getResult() {
        String indInfo = this.population.getBestIndividual().toString();
        StringBuilder solution = new StringBuilder("\n");
        ArrayListGenome genomeObject = (ArrayListGenome) this.population.getBestIndividual().getGenome();
        ArrayList genomeInformation = genomeObject.getGeneticInformation();
        for(int i=0;i<nQueens;i++){
            Integer queenPos=(Integer) genomeInformation.get(i);
            for(int j=0;j<nQueens;j++){
                if(j==queenPos){
                    solution.append(" Q ");}
                else{
                    solution.append(" _ ");}
            }
            solution.append('\n');
        }
        return indInfo+solution;
    }
}
