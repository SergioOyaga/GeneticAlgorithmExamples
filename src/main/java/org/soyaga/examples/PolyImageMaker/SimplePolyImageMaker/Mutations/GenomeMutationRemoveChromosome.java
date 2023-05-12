package org.soyaga.examples.PolyImageMaker.SimplePolyImageMaker.Mutations;

import lombok.AllArgsConstructor;
import org.soyaga.examples.PolyImageMaker.SimplePolyImageMaker.CustomGenome;
import org.soyaga.ga.MutationPolicy.Mutations.Mutation;
import java.awt.*;
import java.util.ArrayList;
import java.util.random.RandomGenerator;

/**
 * Class that applies a mutation to a CustomGenome (backgroundColor, ArrayList&lt;CustomChromosome&gt;),
 * by removing one random CustomChromosome of the genome.
 */
@AllArgsConstructor
public class GenomeMutationRemoveChromosome implements Mutation {
    /**
     * Integer with the iteration when to stop removing Polygons. In the beginning of the optimization we allow
     * the addition of new polygons because we want to find new suitable polygons to represent the image in some good
     * enough way. This approach use to end with lots of similar polygons placed very close (due to the crossover).
     * Typically, we remove this mutation after we remove its counterpart "GenomeMutationAddChromosome",
     * because we want to remove some "unnecessary" polygons (alpha=1.0, very small polygons, polygons hidden behind
     * others...).
     */
    private final int maxIterations;
    /**
     * Function that applies the mutations to the Genomes ArrayList&lt;customChromosome&gt;
     * We just remove one random CustomChromosome.
     * @param gaPart       Genome, Chromosome or Gen to mutate. In this case CustomGenome.
     * @param mutationArgs Undefined array of objects containing information needed to mutate the part. In this case,
     *                    an Integer with the iteration number.
     */
    @Override
    public void apply(Object gaPart, Object... mutationArgs) {
        Integer iteration = (Integer)mutationArgs[0];
        if(iteration>this.maxIterations) return;
        Color background =(Color) ((CustomGenome) gaPart).getGeneticInformation().get(0);
        ArrayList genome = (ArrayList) ((CustomGenome) gaPart).getGeneticInformation().get(1);
        if(genome.size()==1)return;
        genome.remove(RandomGenerator.getDefault().nextInt(genome.size()));
        ((CustomGenome) gaPart).setGeneticInformation(new ArrayList<>(){{add(background);add(genome);}});
    }
}

