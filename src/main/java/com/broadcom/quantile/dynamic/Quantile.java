package com.broadcom.quantile.dynamic;

import java.util.Arrays;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Quantile {
    private double p;

    private Marker marker1;
    private Marker marker2;
    private Marker marker3;
    private Marker marker4;
    private Marker marker5;

    private boolean initialized = false;

    public Quantile(double percentile) {
        this.p = percentile;
    }

    public void update(double[] inputBuffer) {
        int index = 0;

        if (!initialized) {
            initialize(Arrays.copyOfRange(inputBuffer, 0, 5));
            index = 5;
            initialized = true;
        }

        for (; index < inputBuffer.length; index++) {
            consume(inputBuffer[index]);
        }
    }

    public double getQuantileValue() {
        return marker3.getValue();
    }

    public double getUpperQuantileValue() {
        return marker4.getValue();
    }

    public int getMarkerPosition1() {
        return marker1.n;
    }

    public int getMarkerPosition2() {
        return marker2.n;
    }

    public int getMarkerPosition3() {
        return marker3.n;
    }

    public int getMarkerPosition4() {
        return marker4.n;
    }

    public int getMarkerPosition5() {
        return marker5.n;
    }

    public double getMarkerDesiredPosition1() {
        return marker1.nPrime;
    }

    public double getMarkerDesiredPosition2() {
        return marker2.nPrime;
    }

    public double getMarkerDesiredPosition3() {
        return marker3.nPrime;
    }

    public double getMarkerDesiredPosition4() {
        return marker4.nPrime;
    }

    public double getMarkerDesiredPosition5() {
        return marker5.nPrime;
    }

    public double getMarkerHeight1() {
        return marker1.q;
    }

    public double getMarkerHeight2() {
        return marker2.q;
    }

    public double getMarkerHeight3() {
        return marker3.q;
    }

    public double getMarkerHeight4() {
        return marker4.q;
    }

    public double getMarkerHeight5() {
        return marker5.q;
    }

    @Override
    public String toString() {
        ToStringBuilder.setDefaultStyle(ToStringStyle.JSON_STYLE);

        return new ToStringBuilder(this)
                .append("percentile", this.p)
                .append("marker1", this.marker1)
                .append("marker2", this.marker2)
                .append("marker3", this.marker3)
                .append("marker4", this.marker4)
                .append("marker5", this.marker5)
                .build();
    }

    private void consume(double input) {
        marker1.updatePosition(input);
        marker2.updatePosition(input);
        marker3.updatePosition(input);
        marker4.updatePosition(input);
        marker5.updatePosition(input);

        marker2.updateQuantile();
        marker3.updateQuantile();
        marker4.updateQuantile();
    }

    private void initialize(double[] firstFive) {
        Arrays.sort(firstFive);

        this.marker1 = new MinMarker(firstFive[0], 1, 1.0, 0.0);
        this.marker5 = new MaxMarker(firstFive[4], 5, 5.0, 1.0);

        MidMarker m2 = new MidMarker(firstFive[1], 2, 1.0 + 2.0 * this.p, this.p / 2.0);
        this.marker2 = m2;

        MidMarker m3 = new MidMarker(firstFive[2], 3, 1.0 + 4.0 * this.p, this.p);
        this.marker3 = m3;

        MidMarker m4 = new MidMarker(firstFive[3], 4, 3.0 + 2.0 * this.p, (1.0 + this.p) / 2.0);
        this.marker4 = m4;

        m2.setNeighbors(this.marker1, this.marker3);
        m3.setNeighbors(this.marker2, this.marker4);
        m4.setNeighbors(this.marker3, this.marker5);
    }
}
