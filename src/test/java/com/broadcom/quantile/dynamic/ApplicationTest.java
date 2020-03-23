package com.broadcom.quantile.dynamic;

import org.junit.Test;

import java.util.Arrays;

public class ApplicationTest {
    @Test
    public void referenceVerification() {
        double[] inputs1 = {
                0.02, 0.5, 0.74, 3.39, 0.83
        };

        double[] inputs2 = {
                22.37
//                22.37, 10.15, 15.43, 38.62, 15.92
        };

        double[] inputs3 = {
                10.15
//                34.60, 10.28, 1.47, 0.40, 0.05
        };

        double[] inputs4 = {
                15.43
//                11.39, 0.27, 0.42, 0.09, 11.37
        };

        Quantile q50 = new Quantile(0.50);

        q50.update(inputs1);
        System.out.println(q50.toString());
        System.out.println(String.format("median: %f", q50.getQuantileValue()));

        q50.update(inputs2);
        System.out.println(q50.toString());
        System.out.println(String.format("median: %f", q50.getQuantileValue()));

        q50.update(inputs3);
        System.out.println(q50.toString());
        System.out.println(String.format("median: %f", q50.getQuantileValue()));

        q50.update(inputs4);
        System.out.println(q50.toString());
        System.out.println(String.format("median: %f", q50.getQuantileValue()));
    }
}
