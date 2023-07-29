import java.awt.*;
import java.util.ArrayList;


public class Player {

    int x;
    int y;
    String name;
    int Direction;
    private final Color color;

    public Player(String name) {
        this.name = name;
        color = new Color((int) (Math.random() * 0x100000));
    }


    public int getX() {return x;}
    public void setX(int x) {this.x = x;}

    public int getY() {return y;}
    public void setY(int y) {this.y = y;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getDirection() {return Direction;}
    public void setDirection(int direction) {Direction = direction;}

    public Color getColor() {return color;}


ArrayList<Tile> ownedTiles = new ArrayList<>();


    public ArrayList<Tile> getOwnedTiles() {return ownedTiles;}
    public void setOwnedTiles(ArrayList<Tile> ownedTiles) {this.ownedTiles = ownedTiles;}


    public void processMovement(TileMap tileMap, ArrayList<Player> playersList) {


        Tile tile = tileMap.getTile(x, y);
        if (tile.getOwner() != this && tile.getRad() == null) {
            tile.setRad(this);
        } else if (tile.getOwner() == null && tile.getRad() != this) {
            // die
            tile.setRad(this);
        }

    }


}
