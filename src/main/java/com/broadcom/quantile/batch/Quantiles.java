package com.broadcom.quantile.batch;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.Arrays;

public class Quantiles {

    public static double getPercentile(double[] arr, int p) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        Arrays.stream(arr).forEach(stats::addValue);
        return stats.getPercentile(p);
    }
}
