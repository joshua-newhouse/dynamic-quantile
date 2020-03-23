package com.broadcom.quantile.dynamic;

public class MidMarker extends Marker {
    private Marker lNeighbor;
    private Marker rNeighbor;

    public MidMarker(double q, int n, double nPrime, double dPrime) {
        super(q, n, nPrime, dPrime);
    }

    public void setNeighbors(Marker left, Marker right) {
        this.lNeighbor = left;
        this.rNeighbor = right;
    }

    @Override
    public void update(double value) {
        if (value < this.q) {
            this.n++;
        }

        this.updateDesiredPosition();
        updateQuantile();
    }

    private void updateQuantile() {
        double offsetFromDesired = this.nPrime - this.n;
        int offsetFromRNeighbor = rNeighbor.n - this.n;
        int offsetFromLNeighbor = lNeighbor.n - this.n;
        int displacement = offsetFromDesired < 0.0 ? -1 : 1;

        if (offsetFromDesired >= 1 && offsetFromRNeighbor > 1 ||
                offsetFromDesired <= -1 && offsetFromLNeighbor < -1) {
            double qTmp = pSquared(displacement);
            if (lNeighbor.q >= qTmp || qTmp >= rNeighbor.q) {
                qTmp = linear(displacement);
            }

            this.q = qTmp;
        }

        this.n += displacement;
    }

    private double pSquared(int displacement) {
        int neighborSpan = rNeighbor.n - lNeighbor.n;
        int lNeighborOffset = this.n - lNeighbor.n;
        int rNeighborOffset = rNeighbor.n - this.n;

        double qDifRNeighbor = rNeighbor.q - this.q;
        double qDifLNeighbor = this.q - lNeighbor.q;

        return this.q + (double) displacement / (double) neighborSpan * (
                (double) (lNeighborOffset + displacement) * qDifRNeighbor / (double) rNeighborOffset
              + (double) (rNeighborOffset - displacement) * qDifLNeighbor / (double) lNeighborOffset
        );
    }

    private double linear(int displacement) {
        double neighborQ = displacement < 0 ? lNeighbor.q : rNeighbor.q;
        int neighborOffset = displacement < 0 ? lNeighbor.n : rNeighbor.n;

        double qDif = neighborQ - this.q;
        int offsetDif = neighborOffset - this.n;

        return this.q + (double) displacement * qDif / (double) offsetDif;
    }
}
