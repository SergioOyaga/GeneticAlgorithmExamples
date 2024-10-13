package org.soyaga.examples.ImageMaker.SimplePolyImageMaker.Mutations;

import lombok.AllArgsConstructor;
import org.soyaga.examples.ImageMaker.SimplePolyImageMaker.CustomChromosome;
import org.soyaga.examples.ImageMaker.SimplePolyImageMaker.CustomGenome;
import org.soyaga.ga.MutationPolicy.Mutations.Mutation;
import java.awt.*;
import java.util.ArrayList;
import java.util.random.RandomGenerator;

/**
 * Class that applies a mutation to a CustomGenome (backgroundColor, ArrayList{@literal <CustomChromosome>}),
 * by removing one random CustomChromosome of the genome.
 */
@AllArgsConstructor
public class GenomeMutationRemoveChromosome implements Mutation {
    /**
     * This integer specifies the iteration at which to cease the removal of polygons. Initially, during optimization, the addition
     * of new polygons is permitted to discover suitable shapes that can represent the image reasonably well. This approach
     * often results in numerous similar polygons being positioned very close together (attributed to the crossover operation).
     * Typically, the removal of this mutation occurs after its counterpart, "GenomeMutationAddChromosome," has been removed.
     * The objective is to eliminate some "unnecessary" polygons (e.g., those with alpha=1.0, very small polygons, or polygons
     * concealed behind others).
     */
    private final int maxIterations;

    /**
     * Function that applies the Mutations to the Genomes ArrayList{@literal <CustomChromosome>};
     * We just remove one random CustomChromosome.
     *
     * @param gaPart ArrayList{@literal <Color,ArrayList<CustomChromosome>>}. In this case,the array is what we edit.
     * @param mutationArgs Undefined array of objects containing information needed to mutate the part. In this case,
     *                    an Integer with the iteration number.
     */
    @Override
    public void apply(Object gaPart, Object... mutationArgs) {
        Integer iteration = (Integer)mutationArgs[0];
        if(iteration>this.maxIterations) return;
        Color background =(Color) ((CustomGenome) gaPart).getGeneticInformation().get(0);
        ArrayList<CustomChromosome> genome = (ArrayList<CustomChromosome>) ((CustomGenome) gaPart).getGeneticInformation().get(1);
        if(genome.size()==1)return;
        genome.remove(RandomGenerator.getDefault().nextInt(genome.size()));
        ((CustomGenome) gaPart).setGeneticInformation(new ArrayList<>(){{add(background);add(genome);}});
    }
}

