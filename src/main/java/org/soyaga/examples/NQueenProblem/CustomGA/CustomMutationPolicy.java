package org.soyaga.examples.NQueenProblem.CustomGA;

import lombok.AllArgsConstructor;
import org.soyaga.ga.Individual;
import org.soyaga.ga.MutationPolicy.MutationPolicy;
import org.soyaga.ga.Population;

import java.util.random.RandomGenerator;

/**
 * Class that applies a CustomMutationPolicy in the optimization procedure.
 */
@AllArgsConstructor
public class CustomMutationPolicy implements MutationPolicy {
    /**
     * Double containing the probability of mutate each CustomBase.
     */
    private final Double mutationProbability;
    /**
     * Mutation to apply to the CustomBase.
     */
    private final CustomMutation mutation;

    /**
     * Function that applies the Mutations following a policy. Ej.:
     *
     * <ul>
     *      <li>Mutates CustomBase Objects contained in each Gen.</li>
     *      <li>In even iteration mutate only rows.</li>
     *      <li>In Odd iteration mutate only columns.</li>
     *      <li>Only if the probability of mutate each base allow the mutation.</li>
     * </ul>
     *
     * @param population Population where to mutate Individuals.
     * @param policyArgs VarArgs containing the additional information needed to apply the mutation policy.
     */
    @Override
    public void apply(Population population, Object... policyArgs) {
        for(Individual ind:population.getPopulation()) {
            CustomGenome genome = (CustomGenome) ind.getGenome();
            for (CustomChromosome chromosome : genome.getGeneticInformation()) {
                for(CustomGen gen:chromosome.getGeneticInformation()){
                    if((Integer) policyArgs[0]%2==0 &&
                            RandomGenerator.getDefault().nextDouble()<this.mutationProbability){
                        mutation.apply(gen.getGeneticInformation().get(0), policyArgs);
                    } else if ((Integer) policyArgs[0]%2!=0 &&
                            RandomGenerator.getDefault().nextDouble()<this.mutationProbability) {
                        mutation.apply(gen.getGeneticInformation().get(1), policyArgs);
                    }
                }
            }
        }
    }
}
