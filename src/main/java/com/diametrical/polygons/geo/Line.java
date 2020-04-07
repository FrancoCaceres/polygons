package com.diametrical.polygons.geo;

import com.google.common.base.Preconditions;

public class Line {
    private Point start;
    private Point end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getCenter() {
        float centerX = (start.getX() + end.getX()) / 2;
        float centerY = (start.getY() + end.getY()) / 2;
        return new Point(centerX, centerY);
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public static class Builder {
        private Point start;
        private Point end;

        public Builder() {}

        public Builder withStart(Point point) {
            this.start = point;
            return this;
        }

        public Builder withEnd(Point point) {
            this.end = point;
            return this;
        }

        public Line build() {
            Preconditions.checkNotNull(start);
            Preconditions.checkNotNull(end);
            return new Line(start, end);
        }
    }
}
