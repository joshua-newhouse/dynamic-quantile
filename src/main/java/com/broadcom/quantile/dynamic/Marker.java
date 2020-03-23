package com.broadcom.quantile.dynamic;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class Marker {
    protected double q;
    protected int n;
    protected double nPrime;
    protected double dPrime;

    public Marker(double q, int n, double nPrime, double dPrime) {
        this.q = q;
        this.n = n;
        this.nPrime = nPrime;
        this.dPrime = dPrime;
    }

    public double getValue() {
        return this.q;
    }

    public abstract void update(double value);

    @Override
    public String toString() {
        ToStringBuilder.setDefaultStyle(ToStringStyle.JSON_STYLE);

        return new ToStringBuilder(this)
                .append("q", this.q)
                .append("n", this.n)
                .append("n'", this.nPrime)
                .append("d'", this.dPrime)
                .build();
    }

    protected void updateDesiredPosition() {
        this.nPrime += this.dPrime;
    }
}
