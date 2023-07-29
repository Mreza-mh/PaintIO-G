import java.awt.*;

public class Tile {

    private final int x, y;
    Player owner;
    Player rad;



    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Player getOwner() {
        return owner;
    }
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Player getRad() {return rad;}
    public void setRad(Player rad) {this.rad = rad;}


    public Color getColor() {

        if (owner != null && rad == null) {
            return owner.getColor();
        } else if (owner == null && rad != null) {
            return (
                    new Color(getRad().getColor().getRed(), getRad().getColor().getGreen(), getRad().getColor().getBlue(), 127)
            );
        } else if (owner != null && rad != null) {
            float alpha = 0.3f;
            return new Color(
                    (int) ((alpha * getOwner().getColor().getRed()) + ((1 - alpha) * getRad().getColor().getRed())),
                    (int) ((alpha * getOwner().getColor().getGreen()) + ((1 - alpha) * getRad().getColor().getGreen())),
                    (int) ((alpha * getOwner().getColor().getBlue()) + ((1 - alpha) * getRad().getColor().getBlue()))
            );

        }

        //BACKGR
        else if ((x + y) % 2 == 0) {
            return Color.white;
        } else {
            return (new Color(100,140,220,100));
        }
    }


}
