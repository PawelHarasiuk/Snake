import java.awt.*;

public class Apple{
    private int x;
    private int y;
    public ApplePowerUp applePowerUp;

    public Apple(Snake snake){
        spawnApple(snake);
    }

    public void spawnApple(Snake player) {
        boolean onSnake = true;
        boolean xBad = true;
        boolean yBad = true;


        while(onSnake || yBad || xBad) {
            onSnake = false;
            xBad = false;
            yBad = false;

            this.applePowerUp = randomPowerUp();
            x = Game.dimension + (int)(Math.random() * (Game.width/Game.dimension))*Game.dimension;
            y = Game.dimension + (int)(Math.random() * (Game.height/Game.dimension))*Game.dimension;

            if(x<Game.dimension || x>(Game.width-100))
                xBad = true;

            if(y<Game.dimension || y>(Game.height-100))
                yBad = true;

            for(Rectangle rectangle : player.getBody())
                if(rectangle.x == x && rectangle.y == y)
                    onSnake = true;
        }
    }

    public ApplePowerUp randomPowerUp(){
        if(Math.random()<0.2){
            return ApplePowerUp.SAVE;
        }else{
            return ApplePowerUp.NORMAL;
        }
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
