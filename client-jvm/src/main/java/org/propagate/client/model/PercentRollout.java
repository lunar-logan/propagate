package org.propagate.client.model;

import java.io.Serializable;

public class PercentRollout implements Serializable {
    private final String variation;
    private final int percent;

    public PercentRollout(String variation, int percent) {
        this.variation = variation;
        this.percent = percent;
    }

    public String getVariation() {
        return variation;
    }

    public int getPercent() {
        return percent;
    }

    @Override
    public String toString() {
        return "PercentRollout{" +
                "variation='" + variation + '\'' +
                ", percent=" + percent +
                '}';
    }
}
