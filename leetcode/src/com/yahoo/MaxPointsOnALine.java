package com.yahoo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created on 3/28/15.
 */
public class MaxPointsOnALine {

    MaxPointsOnALine() {
        //Point[] p = {new Point(0,0),new Point(1,1), new Point(1,-1)}; // 2
        //Point[] p = {new Point(0,0),new Point(-1,-1), new Point(2,2)};
        //Point[] p = {new Point(4,0),new Point(4,-1), new Point(4,5)};
        //Point p[] = {new Point(2,3),new Point(3,3),new Point(-5,3)}; // 3
        //Point p[] = {new Point(1,1),new Point(1,1),new Point(2,3)}; // 3
        Point p[] = {new Point(1,1),new Point(1,1),new Point(2,3)}; // 3
        //Point[] p = {new Point(560,248),new Point(0,16),new Point(30,250),new Point(950,187),new Point(630,277),new Point(950,187),new Point(-212,-268),new Point(-287,-222),new Point(53,37),new Point(-280,-100),new Point(-1,-14),new Point(-5,4),new Point(-35,-387),new Point(-95,11),new Point(-70,-13),new Point(-700,-274),new Point(-95,11),new Point(-2,-33),new Point(3,62),new Point(-4,-47),new Point(106,98),new Point(-7,-65),new Point(-8,-71),new Point(-8,-147),new Point(5,5),new Point(-5,-90),new Point(-420,-158),new Point(-420,-158),new Point(-350,-129),new Point(-475,-53),new Point(-4,-47),new Point(-380,-37),new Point(0,-24),new Point(35,299),new Point(-8,-71),new Point(-2,-6),new Point(8,25),new Point(6,13),new Point(-106,-146),new Point(53,37),new Point(-7,-128),new Point(-5,-1),new Point(-318,-390),new Point(-15,-191),new Point(-665,-85),new Point(318,342),new Point(7,138),new Point(-570,-69),new Point(-9,-4),new Point(0,-9),new Point(1,-7),new Point(-51,23),new Point(4,1),new Point(-7,5),new Point(-280,-100),new Point(700,306),new Point(0,-23),new Point(-7,-4),new Point(-246,-184),new Point(350,161),new Point(-424,-512),new Point(35,299),new Point(0,-24),new Point(-140,-42),new Point(-760,-101),new Point(-9,-9),new Point(140,74),new Point(-285,-21),new Point(-350,-129),new Point(-6,9),new Point(-630,-245),new Point(700,306),new Point(1,-17),new Point(0,16),new Point(-70,-13),new Point(1,24),new Point(-328,-260),new Point(-34,26),new Point(7,-5),new Point(-371,-451),new Point(-570,-69),new Point(0,27),new Point(-7,-65),new Point(-9,-166),new Point(-475,-53),new Point(-68,20),new Point(210,103),new Point(700,306),new Point(7,-6),new Point(-3,-52),new Point(-106,-146),new Point(560,248),new Point(10,6),new Point(6,119),new Point(0,2),new Point(-41,6),new Point(7,19),new Point(30,250)};
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < p.length; i++) {
            if (i != 0) sb.append(",");
            sb.append(p[i]);
        }
        sb.append("]");

        System.out.println(maxPoints(p));
    }

    class Point {
        int x;
        int y;
        Point() { x = 0; y = 0; }
        Point(int a, int b) { x = a; y = b; }
        public String toString() {
            return "{" + x + "," + y + "}";
        }
    }

    class Line {
        // y = mx + c
        Double m;
        Double c;

        // A line must have two points
        Line(Point p1, Point p2) {
            if (p1.x == p2.x) {
                m = (double) p1.x; // should prob use another var for this
                c = null;
            } else {
                m = ((double) p1.y - p2.y) / ((double) p1.x - p2.x);
                if (m == -0.0) m = 0.0;
                c = (p1.y - (m * p1.x));
            }
        }


        public String toString() {
            return "{ " + hashCode() + " y = " + m + "x + " + c + " }";
        }

        @Override
        public int hashCode() {
            int result = m != null ? m.hashCode() : 0;
            result = 31 * result + (c != null ? c.hashCode() : 0);
            return result;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Line line = (Line) o;

            if (c != null ? !c.equals(line.c) : line.c != null) return false;
            if (!m.equals(line.m)) return false;

            return true;
        }
    }

    public int maxPoints(Point[] points) {
        if (points.length <= 2) { return points.length; }

        int maxLength = 0;
        Map<Line, Set<Point>> lines = new HashMap();
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                Line l = new Line(points[i], points[j]);
                if (lines.containsKey(l)) {
                    Set s = lines.get(l);
                    s.add(points[i]); // only added if new
                    s.add(points[j]); // only added if new
                    if (s.size() > maxLength) maxLength = s.size();
                    System.out.println("ADD: " + l + ":" + s.size() + ":" + i + ":" + j + ":" + points[i] + ":" + points[j] + ":" + maxLength);
                } else {
                    Set s = new HashSet<Point>();
                    s.add(points[i]);
                    s.add(points[j]);
                    lines.put(l, s);
                    if (maxLength == 0) maxLength = 2;
                    System.out.println("NEW: " + l + ":" + s.size() + ":" + i + ":" + j + ":" + points[i] + ":" + points[j] + ":" + maxLength);
                }
            }
        }

        return maxLength;
    }
}
