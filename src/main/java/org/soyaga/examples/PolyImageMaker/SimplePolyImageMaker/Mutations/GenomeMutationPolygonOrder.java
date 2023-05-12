package org.soyaga.examples.PolyImageMaker.SimplePolyImageMaker.Mutations;

import lombok.AllArgsConstructor;
import org.soyaga.examples.PolyImageMaker.SimplePolyImageMaker.CustomGenome;
import org.soyaga.ga.MutationPolicy.Mutations.Mutation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;

/**
 * Class that applies a mutation to a CustomGenome (backgroundColor, ArrayList&lt;CustomChromosome&gt;),
 * by randomly swapping the positions of two CustomChromosomes.
 */
@AllArgsConstructor
public class GenomeMutationPolygonOrder implements Mutation {
    /**
     * Integer with the iteration when to stop swapping CustomChromosomes. Typically, this mutation  is suppressed
     * "early" in the optimization, because the order on how we plot the polygons is relevant in the output image.
     */
    private final int maxIterations;
    /**
     * Function that applies the mutations to the Genome Arraylist of CustomChromosomes.
     * We just swap two CustomChromosomes.
     * @param gaPart       Genome, Chromosome or Gen to mutate. In this case CustomGenome.
     * @param mutationArgs Undefined array of objects containing information needed to mutate the part. In this case,
     *                    an Integer with the iteration number.
     */
    @Override
    public void apply(Object gaPart, Object... mutationArgs) {
        Integer iteration = (Integer)mutationArgs[0];
        if(iteration>this.maxIterations) return;
        ArrayList genome = (ArrayList)((CustomGenome) gaPart).getGeneticInformation().get(1);
        int size = genome.size();
        IntStream.range(0, 2).forEach(x ->
                Collections.swap(genome,
                        RandomGenerator.getDefault().nextInt(size),
                        RandomGenerator.getDefault().nextInt(size)));
    }
}
