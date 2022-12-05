import java.awt.*;
import java.util.ArrayList;

public class Snake{
    private final ArrayList<Rectangle> body;
    private final int width=Game.width;
    private final int height=Game.height;
    private final int dimension=Game.dimension;
    private Direction direction;

    public Snake(){
        body = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Rectangle tmp = new Rectangle(dimension, dimension);
            tmp.setLocation(width/2 - i*dimension, height/2);
            body.add(tmp);
        }
        direction=Direction.RIGHT;
    }

    public void move(){
            Rectangle front = body.get(0);
            Rectangle nextStep = new Rectangle(dimension, dimension);

            if (direction == Direction.UP) {
                nextStep.setLocation(front.x, front.y - dimension);
            } else if (direction == Direction.DOWN) {
                nextStep.setLocation(front.x, front.y + dimension);
            } else if (direction == Direction.LEFT) {
                nextStep.setLocation(front.x - dimension, front.y);
            } else {
                nextStep.setLocation(front.x + dimension, front.y);
            }
            body.add(0, nextStep);
            body.remove(body.size() - 1);
    }

    public void grow() {
        Rectangle front = body.get(0);

        Rectangle newBodyPart = new Rectangle(dimension, dimension);

        if(direction==Direction.UP) {
            newBodyPart.setLocation(front.x, front.y - dimension);
        }
        else if(direction==Direction.DOWN) {
            newBodyPart.setLocation(front.x, front.y + dimension);
        }
        else if(direction==Direction.LEFT) {
            newBodyPart.setLocation(front.x - dimension, front.y);
        }
        else{
            newBodyPart.setLocation(front.x + dimension, front.y);
        }
        body.add(0, newBodyPart);
    }

    public ArrayList<Rectangle> getBody() {
        return body;
    }

    public void left(){
        direction=Direction.LEFT;
    }
    public void right(){
        direction=Direction.RIGHT;
    }
    public void up(){
        direction=Direction.UP;
    }
    public void down(){
        direction=Direction.DOWN;
    }

    public int getX() {
        return body.get(0).x;
    }

    public int getY() {
        return body.get(0).y ;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
