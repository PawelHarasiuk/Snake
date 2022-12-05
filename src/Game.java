import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Game extends JFrame implements KeyListener {
    private final Snake snake;
    private final Apple apple;
    private final GamePanel panel;
    public static final int width=600;
    public static final int height=600;
    public static final int dimension=20;
    public static int score;
    public static int highestScore;
    private int saveGame=3;

    public Game(){
        this.snake = new Snake();
        this.apple = new Apple(snake);
        this.panel = new GamePanel(this);
        this.add(panel);
        this.setTitle("Snake Game");
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void start(){
        readFile();
        GamePanel.gameState = GameState.RUNNING;
    }

    public void checkStateOfGame(){
        if(GamePanel.gameState == GameState.RUNNING) {
            if(checkFoodCollision()) {
                snake.grow();
                apple.spawnApple(snake);
            }
            else if(checkWallCollision() || checkSelfCollision()) {
                GamePanel.gameState =GameState.END;
            }
            else {
                snake.move();
            }
        }
    }

    private boolean checkFoodCollision() {
        if(snake.getX() == apple.getX() && snake.getY() == apple.getY()){
            if(apple.applePowerUp==ApplePowerUp.SAVE){
                saveGame=snake.getBody().size();
            }
                setTitle("Score: " + (snake.getBody().size()-2) + "      Checkpoint: " + (saveGame-2) + "      Highest Score: " + highestScore);
            return true;
        }
        return false;
    }

    private boolean checkWallCollision() {
        if(snake.getX() < 0 || snake.getX() >= width-Game.dimension || snake.getY() < 0 || snake.getY() >= height-2*Game.dimension){
            reset();
            setTitle("Snake Game");
            return true;
        }
        return false;
    }

    private boolean checkSelfCollision() {
        for(int i = 1; i < snake.getBody().size(); i++) {
            if(snake.getX() == snake.getBody().get(i).x && snake.getY() == snake.getBody().get(i).y) {
                reset();
                setTitle("Snake Game");
                return true;
            }
        }
        return false;
    }

    public void reset(){
        score=snake.getBody().size()-3;
        writeToFile();
        for (int i = 0; i < saveGame; i++) {
            snake.getBody().get(i).setLocation((width / 2) - i * dimension, height / 2);
        }
        snake.setDirection(Direction.RIGHT);
        for (int i = snake.getBody().size()-1; i>=saveGame; i--) {
            snake.getBody().remove(i);
        }
    }

    public void writeToFile(){
        try {
            PrintWriter writer = new PrintWriter("HighestScore.txt");
            if(score>highestScore) {
                writer.println(score);
            }else {
                writer.println(highestScore);
            }
                writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void readFile(){
        File file = new File("HighestScore.txt");
        try {
            Scanner scanner = new Scanner(file);
            highestScore = scanner.nextInt();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e){}
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'r' && GamePanel.gameState != GameState.RUNNING) {
                saveGame=3;
            for (int i = snake.getBody().size()-1; i>=saveGame; i--)
                snake.getBody().remove(i);
        }
        if(GamePanel.gameState == GameState.RUNNING){
            if (e.getKeyChar() == 'w' && snake.getDirection() != Direction.DOWN) {
                snake.up();
            }
            if (e.getKeyChar() == 's' && snake.getDirection() != Direction.UP) {
                snake.down();
            }
            if (e.getKeyChar() == 'a' && snake.getDirection() != Direction.RIGHT) {
                snake.left();
            }
            if (e.getKeyChar() == 'd' && snake.getDirection() != Direction.LEFT) {
                snake.right();
            }if (e.getKeyChar() == 'p') {
                GamePanel.gameState = GameState.START;
            }
        }else {
            this.start();
            }
        }
    @Override
    public void keyReleased(KeyEvent e){}

    public Apple getApple() {
        return apple;
    }

    public Snake getSnake() {
        return snake;
    }
}
