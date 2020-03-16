import helpers.Pair;
import java.util.ArrayList;

public class Snake {
    private ArrayList<Pair<Integer, Integer>> snakeCoords = new ArrayList<Pair<Integer, Integer>>();
    private Pair<Integer, Integer> snakeDir = new Pair<>(0, 0);
    private Pair<Integer, Integer> lastMove = new Pair<>(0, 0);
    private Pair<Integer, Integer> size = new Pair<>(0, 0);

    public Snake(int x, int y) {
        size.set(x, y);
        addSnakePart(0, 0);
        setSnakeDir(1, 0);
    }

    private void setSnakeDir(int x, int y) {
        snakeDir.set(x, y);
    }

    public void goUp() {
        if (!lastMove.sameAs(0, -1) || snakeCoords.size() < 2) {
            setSnakeDir(0, 1);
        }
    }

    public void goDown() {
        if (!lastMove.sameAs(0, 1) || snakeCoords.size() < 2) {
            setSnakeDir(0, -1);
        }
    }

    public void goLeft() {
        if (!lastMove.sameAs(1, 0) || snakeCoords.size() < 2) {
            setSnakeDir(-1, 0);
        }
    }

    public void goRight() {
        if (!lastMove.sameAs(-1, 0) || snakeCoords.size() < 2) {
            setSnakeDir(1, 0);
        }
    }

    private void addSnakePart(int x, int y) {
        snakeCoords.add(0, new Pair<>(x, y));
    }

    public void makeMove(boolean ateApple) {
        lastMove.copy(snakeDir);
        Pair<Integer, Integer> head = snakeCoords.get(0);
        addSnakePart(head.x() + snakeDir.x(), head.y() + snakeDir.y());
        if (!ateApple) {
            snakeCoords.remove(snakeCoords.size() - 1);
        }
        fixOutOfBounds();
    }

    public ArrayList<Pair<Integer, Integer>> getSnakeCoords() {
        return snakeCoords;
    }

    public void fixOutOfBounds() {
        Pair<Integer, Integer> head = snakeCoords.get(0);
        if (head.x() >= size.x()) {
            snakeCoords.remove(0);
            addSnakePart(0, head.y());
        } else if (head.x() < 0) {
            snakeCoords.remove(0);
            addSnakePart(size.x() - 1, head.y());
        }
        if (head.y() >= size.y()) {
            snakeCoords.remove(0);
            addSnakePart(head.x(), 0);
        } else if (head.y() < 0) {
            snakeCoords.remove(0);
            addSnakePart(head.x(), size.y() - 1);
        }
    }

    public boolean contains(int x, int y) {
        for (Pair<Integer, Integer> coord : snakeCoords) {
            if (coord.sameAs(x, y)) return true;
        }
        return false;
    }

    public boolean ateItself() {
        for (int i = 0; i < snakeCoords.size() - 1; i++) {
            for (int j = i + 1; j < snakeCoords.size(); j++) {
                if (snakeCoords.get(i).sameAs(snakeCoords.get(j))) return true;
            }
        }
        return false;
    }
}
