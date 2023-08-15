package org.soyaga.examples.NQueenProblem.CustomGA;

import lombok.AllArgsConstructor;
import org.soyaga.ga.ElitismPolicy.ElitismPolicy;

/**
 * Class that applies an elitism policy varying the number of elitist individuals to select depending on the context.
 */
@AllArgsConstructor
public class CustomElitismPolicy implements ElitismPolicy {
    /**
     * Elitist number for even generations.
     */
    private final Integer elitismNumberEven;
    /**
     * Elitist number for odd generations.
     */
    private final Integer elitismNumberOdd;

    /**
     * Function that returns the number of elitist individuals to take into account.
     *
     * @param policyArgs VarArgs containing the additional information needed to compute the elitist number.
     * @return Integer containing the number of elitist individuals to pass to the next generation.
     */
    @Override
    public Integer getElitistNumber(Object... policyArgs) {
        return ((Integer)policyArgs[0])%2.==0.? this.elitismNumberEven: this.elitismNumberOdd;
    }
}
