package org.soyaga.examples.PolyImageMaker.SimplePolyImageMaker;

import org.soyaga.ga.CrossoverPolicy.ParentCross.Crossover;
import org.soyaga.ga.GeneticInformationContainer.Chromosome.Chromosome;
import org.soyaga.ga.GeneticInformationContainer.Genome.Genome;
import org.soyaga.ga.Individual;
import java.awt.*;
import java.util.ArrayList;
import java.util.random.RandomGenerator;

/**
 * Class that contains the information on how to cross two individuals. Similar to the TwoPointCrossover, but the
 * CustomGenome has to be split into background and polygons (Color, ArrayList&lt;CustomChromosome&gt). The background
 * color is the mix of the two parents background color. The list of Chromosomes are mixed using the typical two point
 * crossover, taking into account that parent can have different genome lengths.
 */
public class CustomCrossover implements Crossover {
    /** Function that computes an individual given two parents using something similar to the two points crossoverPolicy.
     * @param parent1 Individual with the first parent.
     * @param parent2 Individual with the second parent.
     * @param crossArgs Undefined Array of elements to perform the crossover procedure (Not used here).
     * @return Individual containing the offspring information.
     */
    @Override
    public Individual computeChild(Individual parent1, Individual parent2, Object ... crossArgs) {
        Genome parent1Genome = parent1.getGenome().createCopy();
        Genome parent2Genome = parent2.getGenome().createCopy();
        Color paren1Background = (Color)((ArrayList<Object>)parent1Genome.getGeneticInformation()).get(0);
        ArrayList<Chromosome> parent1Chromosomes = (ArrayList<Chromosome>)((ArrayList<Object>)parent1Genome.getGeneticInformation()).get(1);
        Color paren2Background = (Color)((ArrayList<Object>)parent2Genome.getGeneticInformation()).get(0);
        ArrayList<Chromosome> parent2Chromosomes = (ArrayList<Chromosome>)((ArrayList<Object>)parent2Genome.getGeneticInformation()).get(1);

        int sizeP1 = parent1Chromosomes.size();
        int sizeP2 = parent2Chromosomes.size();
        int startIndex= RandomGenerator.getDefault().nextInt(sizeP1);
        int endIndex= RandomGenerator.getDefault().nextInt(sizeP1);
        ArrayList<Object> offspringGenomeList = new ArrayList<>();
        if(startIndex<=endIndex){
            offspringGenomeList.addAll(parent2Chromosomes.subList(0, Math.min(startIndex, sizeP2)));
            offspringGenomeList.addAll(parent1Chromosomes.subList(startIndex,endIndex));
            if(endIndex<sizeP2)
                offspringGenomeList.addAll(parent2Chromosomes.subList(endIndex,sizeP2));
        }
        else{
            offspringGenomeList.addAll(parent1Chromosomes.subList(0,endIndex));
            if(endIndex<sizeP2)
                offspringGenomeList.addAll(parent2Chromosomes.subList(endIndex,Math.min(startIndex,sizeP2)));
            offspringGenomeList.addAll(parent1Chromosomes.subList(startIndex,sizeP1));
        }
        Color offspringBackground =mix(paren1Background,paren2Background);
        parent1Genome.setGeneticInformation(new ArrayList<>(){{add(offspringBackground);add(offspringGenomeList);}});
        return new Individual(parent1Genome,parent1.getFeasibilityFunction(),parent1.getObjectiveFunction());
    }

    /**
     * Function that mixes two colors assuming they are equally relevant.
     * @param Color1 Color with the first parent background.
     * @param Color2 Color with the second parent background.
     * @return Color resulting from the mis of the two previous colors.
     */
    private static Color mix(Color Color1, Color Color2) {
        return new Color((int) ((Color1.getRed() + Color2.getRed()) * 0.5),
                (int) ((Color1.getGreen() + Color2.getGreen()) * 0.5),
                (int) ((Color1.getBlue() + Color2.getBlue()) * 0.5));
    }
}

