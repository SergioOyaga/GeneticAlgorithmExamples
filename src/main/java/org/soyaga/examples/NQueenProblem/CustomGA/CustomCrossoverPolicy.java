package org.soyaga.examples.NQueenProblem.CustomGA;

import lombok.AllArgsConstructor;
import org.soyaga.ga.CrossoverPolicy.CrossoverPolicy;
import org.soyaga.ga.CrossoverPolicy.ParentCross.Crossover;
import org.soyaga.ga.CrossoverPolicy.ParentSelection.Selection;

/**
 * Class that applies a crossover policy varying the number of crossed individuals to produce depending on the context.
 */
@AllArgsConstructor
public class CustomCrossoverPolicy implements CrossoverPolicy {
    /**
     * Crossover number for even generations.
     */
    private final Integer crossedNumberEven;
    /**
     * Crossover number for odd generations.
     */
    private final Integer crossedNumberOdd;
    /**
     * Selection Object.
     */
    private final Selection selectionMethodology;
    /**
     * Crossover Object.
     */
    private final Crossover crossoverMethodology;

    /**
     * Function that returns the number of crossover individuals to create depending on the generation`s parity.
     * @param policyArgs VarArgs containing the additional information needed to compute the crossover number.
     * @return Integer containing the number of crossover individuals to create.
     */
    @Override
    public Integer getCrossoverNumber(Object... policyArgs) {
        return ((Integer)policyArgs[0])%2.==0.? this.crossedNumberEven: this.crossedNumberOdd;
    }

    /**
     * Function that returns a Selection Instance containing the parent selection procedure.
     * We could have multiple Selection methodologies to choose from depending on the context (iterations, convergence...).
     * @param policyArgs VarArgs containing the additional information needed to obtain the Selection.
     * @return Selection procedure.
     */
    @Override
    public Selection getParentSelectionMethodology(Object... policyArgs) {
        return this.selectionMethodology;
    }

    /**
     * Function that returns a Crossover Instance containing the parent crossover procedure.
     * We could have multiple Cross methodologies to choose from depending on the context (iterations, convergence...).
     * @param policyArgs VarArgs containing the additional information needed to obtain the Crossover.
     * @return Crossover procedure.
     */
    @Override
    public Crossover getParentCrossMethodology(Object... policyArgs) {
        return this.crossoverMethodology;
    }
}
