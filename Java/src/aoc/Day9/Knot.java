package aoc.Day9;

import java.util.Objects;

public class Knot {
    private int x;
    private int y;

    public Knot() {
        x = 0;
        y = 0;
    }

    public Knot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public Knot getCopy() {
        return new Knot(x, y);
    }

    public boolean testMove(int x, int y, Knot k, boolean direct) {
        boolean result = false;
        int oldX = this.x;
        int oldY = this.y;
        move(x, y);
        boolean check;
        if (direct) {
            check = touchesDirect(k);
        } else {
            check = touchesKnot(k);
        }
        if (check) {
            result = true;
        } else {
            //revert move
            this.x = oldX;
            this.y = oldY;
        }
        return result;
    }

    public boolean testPos(int x, int y, Knot k) {
        return this.x + x == k.x && this.y + y == k.y;
    }

    public boolean touchesDirect(Knot k) {
        return testPos(0, -1, k)
                || testPos(1, 0, k)
                || testPos(0, 0, k)
                || testPos(-1, 0, k)
                || testPos(0, 1, k);
    }

    public boolean touchesKnot(Knot k) {
        return testPos(1, -1, k)
                || testPos(0, -1, k)
                || testPos(-1, -1, k)
                || testPos(1, 0, k)
                || testPos(0, 0, k)
                || testPos(-1, 0, k)
                || testPos(1, 1, k)
                || testPos(0, 1, k)
                || testPos(-1, 1, k);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Knot knot = (Knot) o;
        return x == knot.x && y == knot.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public boolean sameColumnRow(Knot k) {
        return x == k.x || y == k.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
