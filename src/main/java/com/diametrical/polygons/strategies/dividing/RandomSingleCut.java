package com.diametrical.polygons.strategies.dividing;

import com.diametrical.polygons.Color;
import com.diametrical.polygons.DivisiblePolygon;
import com.diametrical.polygons.geo.Line;
import com.diametrical.polygons.geo.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomSingleCut extends DividingStrategyBase {
    @Override
    public List<DivisiblePolygon> divide(DivisiblePolygon poly) {
        List<DivisiblePolygon> children = new ArrayList<>(2);
        int nCoordinates = poly.getCoordinates().size();
        Random r = new Random();
        int startingCornerIdx = Math.abs(r.nextInt()) % nCoordinates;
        Point startingCorner = poly.getCoordinates().get(startingCornerIdx);
        Point endingCorner;

        if(poly.getSideCount() == 3) {
            // corner to center of opposite side
            List<Point> otherCoordinates = Arrays.asList(
                poly.getCoordinates().get((startingCornerIdx + 1) % nCoordinates),
                poly.getCoordinates().get((startingCornerIdx + 2) % nCoordinates)
            );
            assert otherCoordinates.size() == 2;
            endingCorner =
                new Line.Builder()
                    .withStart(otherCoordinates.get(0))
                    .withEnd(otherCoordinates.get(1))
                    .build()
                    .getCenter();
            children.add(
                new DivisiblePolygon.Builder()
                    .withCoordinates(
                        startingCorner,
                        poly.getCoordinates().get((startingCornerIdx + 1) % nCoordinates),
                        endingCorner
                    )
                    .withDividingStrategy(this)
                    .withHollow(poly.isHollow())
                    .withColor(poly.getColor())
                    .withMutationProbability(poly.getMutationProbability())
                    .build()
            );
            children.add(
                new DivisiblePolygon.Builder()
                    .withCoordinates(
                        startingCorner,
                        endingCorner,
                        poly.getCoordinates().get((startingCornerIdx + 2) % nCoordinates)
                    )
                    .withDividingStrategy(this)
                    .withHollow(poly.isHollow())
                    .withColor(poly.getColor())
                    .withMutationProbability(poly.getMutationProbability())
                    .build()
            );
            return children;
        }

        // more than 4 sides
        int nPossibleChoices = nCoordinates - 3; // minus starting corner and adjacent 2
        int nPlacesToMoveFromFirstPossibleEndingCorner = Math.abs(r.nextInt()) % nPossibleChoices;
        // endingCornerIdx is equal to the the startingCornerIdx plus 2 (1 adjacent coordinate + the first
        // possible ending corner) plus the number of places to move from the first possible ending corner,
        // which could be zero
        int endingCornerIdx = (startingCornerIdx + 2 + nPlacesToMoveFromFirstPossibleEndingCorner) % nCoordinates;

        List<Point> firstPolygonCoordinates = new ArrayList<>();
        List<Point> secondPolygonCoordinates = new ArrayList<>();

        int currentIdx = startingCornerIdx;
        firstPolygonCoordinates.add(poly.getCoordinates().get(currentIdx));
        do {
            currentIdx++;
            currentIdx %= poly.getCoordinates().size();
            firstPolygonCoordinates.add(poly.getCoordinates().get(currentIdx));
        } while(currentIdx != endingCornerIdx);

        currentIdx = endingCornerIdx;
        secondPolygonCoordinates.add(poly.getCoordinates().get(currentIdx));
        do {
            currentIdx++;
            currentIdx %= poly.getCoordinates().size();
            secondPolygonCoordinates.add(poly.getCoordinates().get(currentIdx));
        } while(currentIdx != startingCornerIdx);

        children.add(
            new DivisiblePolygon.Builder()
                .withCoordinates(firstPolygonCoordinates)
                .withDividingStrategy(this)
                .withHollow(poly.isHollow())
                .withColor(poly.getColor())
                .withMutationProbability(poly.getMutationProbability())
                .build()
        );
        children.add(
            new DivisiblePolygon.Builder()
                .withCoordinates(secondPolygonCoordinates)
                .withDividingStrategy(this)
                .withHollow(poly.isHollow())
                .withColor(poly.getColor())
                .withMutationProbability(poly.getMutationProbability())
                .build()
        );
        return children;
    }
}
