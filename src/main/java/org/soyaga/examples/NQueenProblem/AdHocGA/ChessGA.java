package org.soyaga.examples.NQueenProblem.AdHocGA;


import org.soyaga.Initializer.GAInitializer;
import org.soyaga.ga.CrossoverPolicy.CrossoverPolicy;
import org.soyaga.ga.ElitismPolicy.ElitismPolicy;
import org.soyaga.ga.GeneticAlgorithm.StatsGeneticAlgorithm;
import org.soyaga.ga.GeneticInformationContainer.Genome.ArrayListGenome;
import org.soyaga.ga.MutationPolicy.MutationPolicy;
import org.soyaga.ga.NewbornPolicy.NewbornPolicy;
import org.soyaga.ga.StatsRetrievalPolicy.StatsRetrievalPolicy;
import org.soyaga.ga.StoppingPolicy.StoppingCriteriaPolicy;

import java.util.ArrayList;

/**
 * Extends StatsGeneticAlgorithm and defines how we gather the results.
 */
public class ChessGA extends StatsGeneticAlgorithm {
    private final Integer nQueens;
    /**
     * Constructor that matches its super constructor.
     *
     * @param ID String with the GA description.
     * @param initialPopulationSize Integer with the initial population size.
     * @param stoppingCriteriaPolicy StoppingCriteriaPolicy.
     * @param crossoverPolicy CrossoverPolicy.
     * @param mutationPolicy MutationPolicy.
     * @param elitismPolicy ElitismPolicy.
     * @param newbornPolicy NewbornPolicy.
     * @param gaInitializer GAInitializer.
     * @param nQueens Integer with the number of queens.
     * @param statsRetrievalPolicy StatsRetrievalPolicy.
     */
    public ChessGA(String ID, Integer initialPopulationSize, StoppingCriteriaPolicy stoppingCriteriaPolicy,
                   CrossoverPolicy crossoverPolicy, MutationPolicy mutationPolicy, ElitismPolicy elitismPolicy,
                   NewbornPolicy newbornPolicy, GAInitializer gaInitializer, Integer nQueens, StatsRetrievalPolicy
                   statsRetrievalPolicy) {
        super(ID, initialPopulationSize, stoppingCriteriaPolicy, crossoverPolicy, mutationPolicy, elitismPolicy,
                newbornPolicy, gaInitializer,statsRetrievalPolicy);
        this.nQueens =nQueens;
    }

    /**
     * Transform the genome of the best individual into a String representing its value.
     *
     * @return String.
     */
    @Override
    public Object getResult() {
        String indInfo = this.population.getBestIndividual().toString();
        StringBuilder solution = new StringBuilder("\n");
        ArrayListGenome<Integer> genomeObject = (ArrayListGenome<Integer>) this.population.getBestIndividual().getGenome();
        ArrayList<Integer> genomeInformation = genomeObject.getGeneticInformation();
        for(int i=0;i<nQueens;i++){
            Integer queenPos= genomeInformation.get(i);
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
