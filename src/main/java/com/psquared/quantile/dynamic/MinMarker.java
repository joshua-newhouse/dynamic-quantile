package com.psquared.quantile.dynamic;

public class MinMarker extends Marker {
    public MinMarker(double q, int n, double nPrime, double dPrime) {
        super(q, n, nPrime, dPrime);
    }

    @Override
    public void updatePosition(double value) {
        if (value < this.q) {
            this.q = value;
        }
    }
}
