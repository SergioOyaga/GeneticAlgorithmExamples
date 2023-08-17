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
 * by adding one new CustomChromosome at the end of the genome.
 */
@AllArgsConstructor
public class GenomeMutationAddChromosome implements Mutation {
    /**
     * Integer with the width of the image, delimits the x in the polygon vertexes.
     */
    private final int width;
    /**
     * Integer with the height of the image, delimits the y in the polygon vertexes.
     */
    private final int height;
    /**
     * This integer specifies the iteration at which the search for new polygons should cease. Initially, during optimization,
     * the addition of new polygons is allowed to identify suitable shapes for adequately representing the image. This approach
     * often results in the addition of numerous similar polygons, contributing to the proximity of polygons (due to the crossover
     * operation). Typically, this mutation is disabled before its counterpart, "GenomeMutationRemoveChromosome," is removed.
     * The objective is to eliminate some "unnecessary" polygons (e.g., those with alpha=1.0, very small polygons, or polygons
     * concealed behind others) and prevent the introduction of new ones.
     */
    private final int maxIterations;
    /**
     * Integer with the number of vertexes that each polygon must have.
     */
    private final int numberOfVertexes;

    /**
     * Function that applies the mutations to the Genomes ArrayList{@literal <CustomChromosome>};
     * We just add one new random CustomChromosome at the end of the list.
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
        Polygon newPolygon = new Polygon();
        for (int i = 0; i < this.numberOfVertexes; i++) {
            newPolygon.addPoint(
                    RandomGenerator.getDefault().nextInt(this.width),
                    RandomGenerator.getDefault().nextInt(this.height));
        }
        CustomChromosome newChromosome = new CustomChromosome(new Color(
                RandomGenerator.getDefault().nextFloat(),
                RandomGenerator.getDefault().nextFloat(),
                RandomGenerator.getDefault().nextFloat(),
                RandomGenerator.getDefault().nextFloat()
        ), newPolygon);
        genome.add(newChromosome);
        ((CustomGenome) gaPart).setGeneticInformation(new ArrayList<>(){{add(background);add(genome);}});
    }
}
