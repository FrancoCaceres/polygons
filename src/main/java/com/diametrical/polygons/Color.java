package com.diametrical.polygons;

import java.util.Random;

public class Color {
    private int r;
    private int g;
    private int b;

    private Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    int getR() {
        return r;
    }

    int getG() {
        return g;
    }

    int getB() {
        return b;
    }

    public static Color fromRgb(int r, int g, int b) {
        return new Color(r, g, b);
    }

    public static Color fromRandom(Random random) {
        int r = Math.abs(random.nextInt()) % 256;
        int g = Math.abs(random.nextInt()) % 256;
        int b = Math.abs(random.nextInt()) % 256;
        return fromRgb(r, g, b);
    }

    public static Color defaultForeground() {
        return fromRgb(255, 255, 255); // white
    }
}
