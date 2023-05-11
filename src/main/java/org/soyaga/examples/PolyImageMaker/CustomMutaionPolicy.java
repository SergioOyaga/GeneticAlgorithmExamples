package org.soyaga.examples.PolyImageMaker;

import lombok.AllArgsConstructor;
import org.soyaga.ga.Individual;
import org.soyaga.ga.MutationPolicy.MutationPolicy;
import org.soyaga.ga.MutationPolicy.Mutations.Mutation;
import org.soyaga.ga.Population;
import java.util.ArrayList;
import java.util.random.RandomGenerator;

/**
 * This class performs Mutations in the Genome and Chromosome levels, considering a specific order in the
 * application of mutations and a fixed probability rate for each one.
 */
@AllArgsConstructor
public class CustomMutaionPolicy implements MutationPolicy {
    /**
     * List of Genome mutations. These are mutations that requires the known of the whole genome structure to be able to
     * happen.
     */
    private final ArrayList<Mutation> genomeMutations;
    /**
     * List of Chromosome mutations. These are mutations that requires the known of a whole chromosome to be able to
     * happen.
     */
    private final ArrayList<Mutation> chromosomeMutations;

    /**
     * Array with doubles between [0.,1.] that defines the upper threshold for each Genome mutation.
     */
    private final double[] genomeMutationProb;
    /**
     * Array with doubles between [0.,1.] that defines the upper threshold for each Chromosome mutation.
     */
    private final double[] chromosomeMutationProb;


    /**
     * Function that applies the mutations following a policy.
     * In this case, a constant probability threshold is applied to each mutation.
     * @param policyArgs context variables that allow to personalize the mutation application. In this case,
     *                    an Integer with the iteration number that is passed to eah mutation.
     */
    @Override
    public void apply(Population population, Object... policyArgs) {
        population.getPopulation().stream().parallel().forEach(ind -> this.mutateIndividual(ind,policyArgs));
    }

    /**
     * Function that mutates an individual following an ArrayList oder with different probability depending on the
     * mutation.
     * @param individual Individual object which genome we want to mutate.
     * @param mutationArgs context variables that allow to personalize the mutation application. In this case,
     *                    an Integer with the iteration number that is passed to eah mutation.
     */
    private void mutateIndividual(Individual individual, Object... mutationArgs) {
        //Mutate the  genome.
        if(this.genomeMutations.size()>0){
            for (int index=0;index<this.genomeMutations.size();index++){
                Mutation genomeMutation=this.genomeMutations.get(index);
                if(RandomGenerator.getDefault().nextDouble()<this.genomeMutationProb[index]){
                    genomeMutation.apply(individual.getGenome(),mutationArgs);
                }
            }
        }
        //Mutate the chromosomes.
        if (this.chromosomeMutations.size()>0) {
            ArrayList<CustomChromosome> chromosomes = (ArrayList<CustomChromosome>)((ArrayList<Object>)individual.getGenome().getGeneticInformation()).get(1);
            for (Object chromosome : chromosomes) {
                for (int index=0;index<this.chromosomeMutations.size();index++){
                    Mutation chromosomeMutation = this.chromosomeMutations.get(index);
                    if (RandomGenerator.getDefault().nextDouble() < this.chromosomeMutationProb[index]) {
                        chromosomeMutation.apply(chromosome,mutationArgs);
                    }
                }
            }
        }
    }
}
