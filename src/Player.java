import java.awt.*;
import java.util.ArrayList;


public class Player {

    int x;
    int y;
    String name;
    int Direction;
    private final Color color;
    boolean isAlive = true;


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
ArrayList<Tile> radTiles = new ArrayList<>();

    public ArrayList<Tile> getOwnedTiles() {return ownedTiles;}
    public void setOwnedTiles(ArrayList<Tile> ownedTiles) {this.ownedTiles = ownedTiles;}


    public void processMovement(TileMap tileMap, ArrayList<Player> playersList) {
        if (isAlive) {


            Tile tile = tileMap.getTile(x, y);
            if (tile.getOwner() != this && tile.getRad() == null) {
                tile.setRad(this);
                radTiles.add(tile);
            } else if (tile.getOwner() == null && tile.getRad() != this) {
                tile.getRad().die(tile);
                tile.setRad(this);
            }
        }
    }

    void die(Tile tile){

        isAlive = false;

        for (Tile t:ownedTiles) {
            t.setOwner(null);
        }
        for (Tile t:radTiles) {
            t.setRad(null);
        }

    }

}
