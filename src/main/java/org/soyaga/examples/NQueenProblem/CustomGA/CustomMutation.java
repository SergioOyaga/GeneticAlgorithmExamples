package org.soyaga.examples.NQueenProblem.CustomGA;

import lombok.AllArgsConstructor;
import org.soyaga.ga.MutationPolicy.Mutations.Mutation;

import java.util.random.RandomGenerator;

/**
 * Class that applies a mutation to a CustomBase object. It randomizes the value of the base between 0 and nQueens-1.
 */
@AllArgsConstructor
public class CustomMutation implements Mutation {
    /**
     * Integer with the number of queens.
     */
    private final Integer nQueens;

    /**
     * Function that applies the Mutations to a CustomBase.
     * We could have used different Mutation behavior depending on the context (iterations, convergence...).
     *
     * @param gaPart CustomBase to mutate.
     * @param mutationArgs Undefined array of objects containing information needed to mutate the part.
     */
    @Override
    public void apply(Object gaPart, Object... mutationArgs) {
        //
        CustomBase base = (CustomBase) gaPart;
        base.setGeneticInformation(RandomGenerator.getDefault().nextInt(this.nQueens));
    }
}
