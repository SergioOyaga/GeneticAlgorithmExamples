package org.soyaga.examples.NQueenProblem.CustomGA;

import lombok.AllArgsConstructor;
import org.soyaga.Initializer.GAInitializer;
import org.soyaga.ga.Individual;
import org.soyaga.ga.NewbornPolicy.NewbornPolicy;

import java.util.Set;

/**
 * Class that applies a CustomNewbornPolicy in the optimization procedure.
 */
@AllArgsConstructor
public class CustomNewbornPolicy implements NewbornPolicy {
    /**
     * Elitist number for even generations.
     */
    private final Integer newbornNumberEven;
    /**
     * Elitist number for odd generations.
     */
    private final Integer newbornNumberOdd;
    /**
     * Function that returns the newborn number following a policy. Ej.:
     * <ul>
     *      <li>Selects the best Individuals that will pass directly to the next generation.</li>
     *      <li>In even iteration one number of best Individuals.</li>
     *      <li>In Odd iteration other number of best Individuals.</li>
     *      <li><b>***</b></li>
     * </ul>
     *
     * @param gaInitializer GAInitializer used to create the new individuals
     * @param policyArgs    VarArgs containing the additional information needed to apply the newborn policy.
     */
    @Override
    public Set<Individual> apply(GAInitializer gaInitializer, Object... policyArgs) {
        Integer newbornNumber=((Integer)policyArgs[0])%2.==0.?this.newbornNumberEven:this.newbornNumberOdd;
        return gaInitializer.initializeIndividuals(newbornNumber);
    }
}
