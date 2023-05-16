package org.soyaga.examples.ImageMaker.SimplePolyImageMaker.Mutations;

import lombok.AllArgsConstructor;
import org.soyaga.examples.ImageMaker.SimplePolyImageMaker.CustomChromosome;
import org.soyaga.examples.ImageMaker.SimplePolyImageMaker.CustomGenome;
import org.soyaga.ga.MutationPolicy.Mutations.Mutation;
import java.awt.*;
import java.util.ArrayList;
import java.util.random.RandomGenerator;

/**
 * Class that applies a mutation to a CustomGenome (backgroundColor, ArrayList&lt;CustomChromosome&gt;),
 * by appending one CustomChromosome at the end of the genome.
 */
@AllArgsConstructor
public class GenomeMutationAddChromosome implements Mutation {
    /**
     * Integer with the width of the image, delimits the x in the polygon vertexes.
     */
    private final int width;
    /**
     * Integer with the height of the image, delimits the y the polygon vertexes.
     */
    private final int height;
    /**
     * Integer with the iteration when to stop looking for new Polygons. In the beginning of the optimization we allow
     * the addition of new polygons because we want to find new suitable  polygons to represent the image in some good
     * enough way. Typically, we remove this mutation before we remove its counterpart "GenomeMutationRemoveChromosome",
     * because we want to remove some "unnecessary" polygons (alpha=1.0, very small polygons, polygons hidden behind
     * others...) and avoid the addition of new ones.
     */
    private final int maxIterations;
    /**
     * Integer with the number of vertexes that each polygon must have.
     */
    private final int numberOfVertexes;
    /**
     * Function that applies the mutations to the Genomes ArrayList&lt;customChromosome&gt;.
     * We just append one new CustomChromosome.
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
