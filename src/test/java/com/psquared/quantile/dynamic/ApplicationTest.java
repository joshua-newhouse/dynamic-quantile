package com.psquared.quantile.dynamic;

import com.psquared.quantile.batch.ApacheQuantiles;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ApplicationTest {
    @Test
    public void referenceVerificationTest() {
        double[] inputs1 = {
                0.02, 0.15, 0.74, 3.39, 0.83,
                22.37, 10.15, 15.43, 38.62, 15.92
        };

        double[] inputs2 = {
                34.60, 10.28, 1.47, 0.40, 0.05
        };

        double[] inputs3 = {
                11.39, 0.27, 0.42, 0.09, 11.37
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

        assertEquals(1, q50.getMarkerPosition1());
        assertEquals(6, q50.getMarkerPosition2());
        assertEquals(10, q50.getMarkerPosition3());
        assertEquals(16, q50.getMarkerPosition4());
        assertEquals(20, q50.getMarkerPosition5());

        assertEquals(1.0, q50.getMarkerDesiredPosition1(), 0.0);
        assertEquals(5.75, q50.getMarkerDesiredPosition2(), 0.0);
        assertEquals(10.50, q50.getMarkerDesiredPosition3(),0.0);
        assertEquals(15.25, q50.getMarkerDesiredPosition4(), 0.0);
        assertEquals(20.0, q50.getMarkerDesiredPosition5(), 0.0);

        assertEquals(0.02, q50.getMarkerHeight1(), 0.007);
        assertEquals(0.50, q50.getMarkerHeight2(), 0.007);
        assertEquals(4.44, q50.getMarkerHeight3(), 0.007);
        assertEquals(17.22, q50.getMarkerHeight4(), 0.017);
        assertEquals(38.62, q50.getMarkerHeight5(), 0.007);
    }

    @Test
    public void benchmarkTest() {
        int elements = 500000000;
        double[] inputs = new double[elements];

        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = Math.random() * 100000000.0 - 50000.0;
        }

        Quantile q90 = new Quantile(0.90);

        long startTime = System.currentTimeMillis();
        q90.update(inputs);
        long endTime = System.currentTimeMillis();

        double totalTimeSeconds = (double)(endTime - startTime) / 1000.0;

        System.out.println(String.format("Stream Total time in seconds: %f\nObservations per second: %f",
                totalTimeSeconds, (double)elements / totalTimeSeconds));

        System.out.println(q90.toString());
    }

    /* Requires -Xmx16384m setting for the Apache calculation */
    @Test
    public void comparisonTest() {
        int elements = 50000000;
        double[] inputs = new double[elements];

        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = Math.random() * 100000000.0 - 50000.0;
        }

        Quantile q90 = new Quantile(0.90);

        long startTime = System.currentTimeMillis();
        q90.update(inputs);
        long endTime = System.currentTimeMillis();

        double totalTimeSeconds = (double)(endTime - startTime) / 1000.0;

        System.out.println(String.format("Stream Total time in seconds: %f\nObservations per second: %f",
                totalTimeSeconds, (double)elements / totalTimeSeconds));

        System.out.println(q90.toString());

        startTime = System.currentTimeMillis();
        double q1 = ApacheQuantiles.getPercentile(inputs, 90);
        endTime = System.currentTimeMillis();

        totalTimeSeconds = (double)(endTime - startTime) / 1000.0;

        System.out.println(String.format("Batch Total time in seconds: %f\nObservations per second: %f",
                totalTimeSeconds, (double)elements / totalTimeSeconds));

        System.out.println(String.format("Batch quantile: %f", q1));
    }
}
