import helpers.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Map extends JFrame implements KeyListener {

    private Snake snake;
    private int xSize = 30, ySize = 10;
    private double ratio = 3.0;
    private Pair<Integer, Integer> apple = new Pair<>(0, 0);
    private boolean ateApple = false;
    private boolean paused = false;
    private boolean exitGame = false;
    private Random rand = new Random();

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JLayeredPane layers = new JLayeredPane();
    private JPanel game = new JPanel();
    private JPanel messages = new JPanel();
    private JLabel pausedMessage;
    private JLabel gameOverMessage;
    private JPanel scoreboard = new JPanel();
    private JLabel score;
    private JPanel[][] panels;
    private ImageIcon pauseIcon = createImageIcon("public/pause_icon.png", "Pause Icon");
    private ImageIcon gameOverIcon = createImageIcon("public/game_over_icon.png", "Game Over Icon");

    public Map() {
        super("Snake");
        snake = new Snake(xSize, ySize);
        setUpMap();
    }

    public Map(int xSize, int ySize) {
        super("Snake");
        this.xSize = xSize;
        this.ySize = ySize;
        ratio = (double)xSize/ySize;
        snake = new Snake(xSize, ySize);
        setUpMap();
    }

    private void setUpMap() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(screenSize.width, screenSize.height);
        setResizable(false);
        addKeyListener(this);

        add(layers);

        setUpPanels();
        setUpMessages();
        setUpScoreboard();

        setVisible(true);
        generateApple();

    }

    private void setUpPanels() {
        layers.add(game, Integer.valueOf(0));
        game.setSize(screenSize.width, (int)(screenSize.width/ratio));
        game.setLayout(new GridLayout(ySize, xSize));

        panels = new JPanel[xSize][ySize];
        for (int y = ySize - 1; y >= 0; y--) {
            for (int x = 0; x < xSize; x++) {
                panels[x][y] = new JPanel();
                game.add(panels[x][y]);
            }
        }
    }

    private void setUpMessages() {
        layers.add(messages, Integer.valueOf(1));

        pausedMessage = new JLabel(pauseIcon);
        pausedMessage.setVisible(false);
        gameOverMessage = new JLabel(gameOverIcon);
        gameOverMessage.setVisible(false);

        messages.setSize(screenSize.width, (int)(screenSize.width/ratio));
        messages.setLayout(new GridBagLayout());
        messages.setOpaque(false);
        messages.add(pausedMessage);
        messages.add(gameOverMessage);
    }

    private void setUpScoreboard() {
        layers.add(scoreboard, Integer.valueOf(1));

        score = new JLabel();

        scoreboard.setSize(screenSize.width, (int)(screenSize.width/ratio));
        scoreboard.setOpaque(false);
        scoreboard.add(score);
    }

    protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            System.out.println(imgURL);
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private void generateApple() {
        apple.set(rand.nextInt(xSize), rand.nextInt(ySize));
    }

    private void updateStatus() {
        score.setText(Integer.valueOf(snakeSize() - 1).toString());
        if (isGameOver()) {
            pausedMessage.setVisible(false);
            gameOverMessage.setVisible(isGameOver());
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch( keyCode ) {
            case KeyEvent.VK_UP:
                snake.goUp();
                break;
            case KeyEvent.VK_DOWN:
                snake.goDown();
                break;
            case KeyEvent.VK_LEFT:
                snake.goLeft();
                break;
            case KeyEvent.VK_RIGHT :
                snake.goRight();
                break;
            case KeyEvent.VK_ESCAPE:
                exitGame = true;
                break;
            case KeyEvent.VK_SPACE:
                paused = !isGameOver() && !paused;
                pausedMessage.setVisible(paused);
                break;
            default:
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }

    public void draw() {
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                if (snake.contains(x, y)) {
                    panels[x][y].setBackground(new Color(96, 223, 61));
                } else if (apple.sameAs(x, y)) {
                    panels[x][y].setBackground(new Color(223, 40, 37));
                } else {
                    panels[x][y].setBackground(new Color(225, 223, 206));
                }
            }
        }
    }

    public void update() {
        if (!paused) {
            snake.makeMove(ateApple);
            ateApple = snake.contains(apple.x(), apple.y());
            if (ateApple) {
                generateApple();
            }
        }
        updateStatus();
        draw();
    }

    public boolean isGameOver() { return exitGame || snake.ateItself(); }

    public int snakeSize() {
        return snake.getSnakeCoords().size();
    }

    public static void main (String[] args) {
        Map map = new Map(50, 20);
        while (true) {
            map.update();
            if (map.isGameOver()) break;
            try {
               Thread.sleep((int)(25 + 75/Math.exp(0.1*(map.snakeSize() - 1))));
            } catch (InterruptedException e) {

            }
        }
    }
}
