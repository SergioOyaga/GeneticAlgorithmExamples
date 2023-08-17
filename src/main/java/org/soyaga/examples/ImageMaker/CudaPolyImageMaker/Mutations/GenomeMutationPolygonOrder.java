package org.soyaga.examples.ImageMaker.CudaPolyImageMaker.Mutations;

import lombok.AllArgsConstructor;
import org.soyaga.examples.ImageMaker.CudaPolyImageMaker.CustomChromosome;
import org.soyaga.examples.ImageMaker.CudaPolyImageMaker.CustomGenome;
import org.soyaga.ga.MutationPolicy.Mutations.Mutation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;

/**
 * Class that applies a mutation to a CustomGenome (backgroundColor, ArrayList{@literal <CustomChromosome>}),
 * by randomly swapping the positions of two CustomChromosomes.
 */
@AllArgsConstructor
public class GenomeMutationPolygonOrder implements Mutation {
    /**
     * This integer determines the iteration at which the swapping of CustomChromosomes should cease. Generally, this mutation
     * is disabled relatively "early" in the optimization process. This is because the sequence in which we arrange the polygons
     * significantly impacts the resulting output image.
     */
    private final int maxIterations;

    /**
     * Function that applies the mutations to the Genomes ArrayList{@literal <CustomChromosome>};
     * We just remove swap two CustomChromosomes.
     *
     * @param gaPart ArrayList{@literal <Color,ArrayList<CustomChromosome>>}. In this case,the array is what we edit.
     * @param mutationArgs Undefined array of objects containing information needed to mutate the part. In this case,
     *                    an Integer with the iteration number.
     */
    @Override
    public void apply(Object gaPart, Object... mutationArgs) {
        Integer iteration = (Integer)mutationArgs[0];
        if(iteration>this.maxIterations) return;
        ArrayList<CustomChromosome> genome = (ArrayList<CustomChromosome>)((CustomGenome) gaPart).getGeneticInformation().get(1);
        int size = genome.size();
        IntStream.range(0, 2).forEach(x ->
                Collections.swap(genome,
                        RandomGenerator.getDefault().nextInt(size),
                        RandomGenerator.getDefault().nextInt(size)));
    }
}
