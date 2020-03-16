package helpers;

import java.util.Objects;

public class Pair<X, Y> {

    private X x;
    private Y y;

    public Pair() {
    }

    public Pair(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X x() {
        return x;
    }

    public Y y() {
        return y;
    }

    public void setX(X x) {
        this.x = x;
    }

    public void setY(Y y) {
        this.y = y;
    }

    public void set(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Pair{" + x +
                ", " + y +
                '}';
    }

    public boolean sameAs(Pair<X, Y> o) {
        if (this.x() == o.x() && this.y() == o.y()) return true;
        return false;
    }

    public boolean sameAs(X x, Y y) {
        if (this.x == x && this.y == y) return true;
        return false;
    }

    public void copy (Pair<X, Y> o) {
        x = o.x();
        y = o.y();
    }
}
