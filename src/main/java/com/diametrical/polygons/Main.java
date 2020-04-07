package com.diametrical.polygons;

import com.diametrical.polygons.geo.Point;
import com.diametrical.polygons.strategies.dividing.RandomSingleCut;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main extends PApplet {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 1200;

    private List<DivisiblePolygon> divisiblePolygons = new ArrayList<>(1);
    private boolean paused = false;
    private Random r = new Random(System.currentTimeMillis());

    public void settings() {
        size(WIDTH, HEIGHT);

        divisiblePolygons.add(new DivisiblePolygon.Builder()
            .withCoordinates(Arrays.asList(
                new Point(1, 1),
                new Point(WIDTH - 2, 2),
                new Point(WIDTH - 2, HEIGHT - 2),
                new Point(1, HEIGHT - 2)
            ))
            .withDividingStrategy(new RandomSingleCut())
            .withHollow(r.nextBoolean())
            .withColor(Color.fromRandom(r))
            .withMutationProbability(45)
            .build()
        );
    }

    public void draw() {
        if(paused) {
            return;
        }

        colorMode(RGB);
        background(0);

        List<DivisiblePolygon> newDivisiblePolygons = new ArrayList<>();
        for(DivisiblePolygon divisiblePolygon : divisiblePolygons) {
            shape(divisiblePolygon.getShape());
            newDivisiblePolygons.addAll(divisiblePolygon.divide());
        }
        divisiblePolygons.addAll(newDivisiblePolygons);

        paused = true;
    }

    public void mouseClicked() {
        paused = false;
    }

    public static void main(String... args) {
        PApplet.main(Main.class.getName());
    }
}
