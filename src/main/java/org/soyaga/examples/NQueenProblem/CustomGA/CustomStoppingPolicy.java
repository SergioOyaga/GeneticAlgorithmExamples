package org.soyaga.examples.NQueenProblem.CustomGA;

import lombok.AllArgsConstructor;
import org.soyaga.ga.StoppingPolicy.StoppingCriteriaPolicy;

/**
 * Class that applies a CustomStoppingPolicy in the optimization procedure.
 */
@AllArgsConstructor
public class CustomStoppingPolicy implements StoppingCriteriaPolicy {
    /**
     * Integer with the maximum number of iterations to perform.
     */
    private Integer maxGenerations;

    /**
     * Function that checks if the CustomGeneticAlgorithm has to continue optimizing or not. It stops if the maximum
     * number of iterations is achieved or if the fitness function of the best individual is optimal (0 in this case).
     *
     * @param stopArgs VarArgs containing the additional information needed to apply the stopping criteria policy.
     * @return Boolean, <b><i> true </i></b> if it has to continue, <b><i> false </i></b> if not.
     */
    @Override
    public Boolean hasToContinue(Object... stopArgs) {
        return ((Integer) stopArgs[0]< maxGenerations)&&(Double) stopArgs[1] >0.;
    }
}
