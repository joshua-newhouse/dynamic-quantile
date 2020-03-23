package com.broadcom.quantile.dynamic;

import com.broadcom.quantile.batch.Quantiles;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.IntStream;

public class ApacheDescStatsTest {

    @Test
    public void testQuantile() {

        double[] arr = new double[10];
        IntStream.rangeClosed(1, 10).forEach( i-> arr[i-1]=i );

        double q1 = Quantiles.getPercentile(arr, 75);
        double q2 = Quantiles.getPercentile(arr, 25);

        Assert.assertEquals(8.25, q1, 0);
        Assert.assertEquals(2.75, q2, 0);
    }

}
