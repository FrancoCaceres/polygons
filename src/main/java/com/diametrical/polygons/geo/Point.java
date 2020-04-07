package com.diametrical.polygons.geo;

import com.google.common.base.Preconditions;

public class Point {
    private float x;
    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isBelow(Point other) {
        Preconditions.checkNotNull(other);
        return this.y < other.getY();
    }

    public boolean isLeftOf(Point other) {
        Preconditions.checkNotNull(other);
        return this.x < other.getX();
    }
}
