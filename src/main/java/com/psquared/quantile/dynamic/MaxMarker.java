package com.psquared.quantile.dynamic;

public class MaxMarker extends Marker {
    public MaxMarker(double q, int n, double nPrime, double dPrime) {
        super(q, n, nPrime, dPrime);
    }

    @Override
    public void updatePosition(double value) {
        this.n++;
        this.updateDesiredPosition();

        if (value > this.q) {
            this.q = value;
        }
    }
}
