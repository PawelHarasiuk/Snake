import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel implements ActionListener{
    private final Snake snake;
    private final Apple apple;
    private final Game game;
    public static GameState gameState;
    private Image redAppleImage;
    private Image greenAppleImage;

    public GamePanel(Game game){
        snake = game.getSnake();
        apple = game.getApple();
        this.game = game;
        setImages();
        Timer timer = new Timer(100,this);
        timer.start();
        gameState=GameState.START;
        this.addKeyListener(game);
        this.setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.black);

        if(gameState == GameState.START){
            g.setColor(Color.green);
            g.drawString("PRESS ANY KEY", Game.width/2 -3*Game.dimension, Game.height/2 - 3*Game.dimension);
            g.setColor(Color.white);
            g.drawString("USE TO CONTROLE GAME", Game.width/2 - 90, Game.height/2);
            g.drawString("   W   ", Game.width/2 - 30, Game.height/2 + 2*Game.dimension);
            g.drawString("A  S  D", Game.width/2 - 30, Game.height/2 + 3*Game.dimension);
            g.drawString("USE  P  TO PAUSE GAME", Game.width/2 - 85, Game.height/2 + 5*Game.dimension);
        }
        else if(gameState == GameState.RUNNING){
                if (apple.applePowerUp == ApplePowerUp.SAVE) {
                    g.drawImage(greenAppleImage, apple.getX(), apple.getY(), null);
                } else {
                    g.drawImage(redAppleImage, apple.getX(), apple.getY(), null);
                }
                g.setColor(Color.green);
                for (Rectangle rectangle : snake.getBody()) {
                g.fillRect(rectangle.x, rectangle.y, Game.dimension, Game.dimension);
            }
        }
        else{
            g.setColor(Color.white);
            g.drawString("Your Score: " + Game.score, Game.width / 2 - 60, Game.height / 2 - 50);
            g.drawString("Highest Score: " + Game.highestScore, Game.width / 2 - 70, Game.height / 2 - 50 + 2 * Game.dimension);
            g.drawString("PRESS R TO PLAY AGAIN", Game.width/2 - 100, Game.height/2 - 50 + 4*Game.dimension);
            g.drawString("PRESS ANY KYE TO CONTINUE FROM CHECKPOINT", Game.width/2 - 180, Game.height/2 - 50 + 6*Game.dimension);
        }
    }

    public void setImages(){
        BufferedImage redApplePNG = null;
        BufferedImage greenApplePNG = null;
        try {
            redApplePNG = ImageIO.read(new File("src\\images\\red.png"));
            greenApplePNG = ImageIO.read(new File("src\\images\\green.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        if (redApplePNG != null){
            redAppleImage = redApplePNG.getScaledInstance(Game.dimension, Game.dimension, Image.SCALE_DEFAULT);
        }
        if(greenApplePNG != null) {
            greenAppleImage = greenApplePNG.getScaledInstance(Game.dimension, Game.dimension, Image.SCALE_DEFAULT);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        game.checkStateOfGame();
    }
}
