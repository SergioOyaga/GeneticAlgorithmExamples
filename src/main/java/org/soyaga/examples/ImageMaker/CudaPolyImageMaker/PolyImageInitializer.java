package org.soyaga.examples.ImageMaker.CudaPolyImageMaker;

import lombok.AllArgsConstructor;
import org.soyaga.Initializer.GAInitializer;
import org.soyaga.ga.Evaluable.Objective.ObjectiveFunction;
import org.soyaga.ga.Individual;

import java.awt.*;
import java.util.ArrayList;
import java.util.random.RandomGenerator;

/**
 * This class allow the initialization of new Individuals from scratch.
 */
@AllArgsConstructor
public class PolyImageInitializer extends GAInitializer {
    private final Integer numberOfPolygons, numberOfVertexes, height, width;
    private final ObjectiveFunction objectiveFunction;

    /**
     * Function that initializes a new individual from scratch. In this case each individual have one CustomGenome,
     * composed of a background Color and a lis of CustomChromosomes (Color, ArrayList&lt;CustomChromosomes&gt;).
     * At the same time, each CustomChromosome is composed of one Color and one Polygon (Color, Polygon).
     * @return Individual randomly initialized.
     */
    @Override
    public Individual initializeIndividual() {
        ArrayList<CustomChromosome> chromosomes=new ArrayList<>();
        for (int i=0;i<this.numberOfPolygons;i++){
            Polygon polygon = new Polygon();
            for(int j=0;j<this.numberOfVertexes;j++){
                polygon.addPoint(RandomGenerator.getDefault().nextInt(width),
                        RandomGenerator.getDefault().nextInt(height));
            }
            CustomChromosome chromosome= new CustomChromosome( new Color(
                    RandomGenerator.getDefault().nextFloat(),
                    RandomGenerator.getDefault().nextFloat(),
                    RandomGenerator.getDefault().nextFloat(),
                    RandomGenerator.getDefault().nextFloat()),polygon);
            chromosomes.add(chromosome);
        }
        CustomGenome genome = new CustomGenome(new Color(
                    RandomGenerator.getDefault().nextFloat(),
                    RandomGenerator.getDefault().nextFloat(),
                    RandomGenerator.getDefault().nextFloat()),
                chromosomes);
        return new Individual(genome,null, this.objectiveFunction, 100.);

    }
}
