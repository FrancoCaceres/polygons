package com.diametrical.polygons;

import com.diametrical.polygons.geo.Point;
import com.google.common.base.Preconditions;
import processing.core.PConstants;
import processing.core.PShape;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DivisiblePolygon {
    private List<Point> coordinates;
    private DividingStrategy<DivisiblePolygon> dividingStrategy;
    private boolean hollow;
    private Color color;
    private boolean divided = false;
    private int mutationProbability;

    private DivisiblePolygon() {}

    List<DivisiblePolygon> divide() {
        if(divided) {
            return Collections.emptyList();
        }
        divided = true;
        List<DivisiblePolygon> children = dividingStrategy.divide(this);
        Random r = new Random(System.currentTimeMillis());
        for(DivisiblePolygon child : children) {
            int roll = Math.abs(r.nextInt()) % 100;
            if(roll < mutationProbability) {
                child.color = Color.fromRandom(r);
                child.hollow = r.nextBoolean();
            }
        }
        return children;
    }

    public PShape getShape() {
        PShape s = new PShape(PShape.GEOMETRY);
        s.beginShape();
        s.colorMode(PConstants.RGB, 255, 255, 255, 255);
        s.stroke(color.getR(), color.getG(), color.getB());
        if(hollow) {
            s.noFill();
        } else {
            s.fill(color.getR(), color.getG(), color.getB());
        }
        for(Point coordinate : coordinates) {
            s.vertex(coordinate.getX(), coordinate.getY());
        }
        s.endShape(PConstants.CLOSE);
        return s;
    }

    public List<Point> getCoordinates() {
        return coordinates;
    }

    public DividingStrategy<DivisiblePolygon> getDividingStrategy() {
        return dividingStrategy;
    }

    public boolean isHollow() {
        return hollow;
    }

    public Color getColor() {
        return color;
    }

    public boolean isDivided() {
        return divided;
    }

    public int getMutationProbability() {
        return mutationProbability;
    }

    public int getSideCount() {
        return coordinates.size();
    }

    public static class Builder {
        private List<Point> coordinates;
        private DividingStrategy<DivisiblePolygon> dividingStrategy;
        private boolean hollow;
        private Color color;
        private int mutationProbability = 0;

        public Builder() {}

        public Builder withCoordinates(List<Point> coordinates) {
            this.coordinates = coordinates;
            return this;
        }

        public Builder withCoordinates(Point... coordinates) {
            this.coordinates = Arrays.asList(coordinates);
            return this;
        }

        public Builder withDividingStrategy(DividingStrategy<DivisiblePolygon> dividingStrategy) {
            this.dividingStrategy = dividingStrategy;
            return this;
        }

        public Builder withHollow(boolean hollow) {
            this.hollow = hollow;
            return this;
        }

        public Builder withColor(Color color) {
            this.color = color;
            return this;
        }

        public Builder withMutationProbability(int mutationProbability) {
            Preconditions.checkArgument(mutationProbability >= 0 && mutationProbability <= 100);
            this.mutationProbability = mutationProbability;
            return this;
        }

        public DivisiblePolygon build() {
            DivisiblePolygon divisiblePolygon = new DivisiblePolygon();
            Preconditions.checkNotNull(this.coordinates);
            Preconditions.checkNotNull(this.dividingStrategy);
            divisiblePolygon.coordinates = this.coordinates;
            divisiblePolygon.dividingStrategy = this.dividingStrategy;
            divisiblePolygon.hollow = this.hollow;
            if(this.color == null) {
                this.color = Color.defaultForeground();
            }
            divisiblePolygon.color = this.color;
            divisiblePolygon.mutationProbability = this.mutationProbability;
            return divisiblePolygon;
        }
    }

}
