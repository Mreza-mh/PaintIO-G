import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;


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

    public int getDirection() {return Direction;}
    public void setDirection(int direction) {Direction = direction;}

    public Color getColor() {return color;}


ArrayList<Tile> ownedTiles = new ArrayList<>();
ArrayList<Tile> radTiles = new ArrayList<>();

    public ArrayList<Tile> getOwnedTiles() {return ownedTiles;}


    public void processMovement(TileMap tileMap, ArrayList<Player> playersList) {
        if (isAlive) {

            Tile tile = tileMap.getTile(x, y);
            if (tile.getOwner() != this && tile.getRad() == null) {
                tile.setRad(this);
                radTiles.add(tile);
            } else if (tile.getOwner() == null && tile.getRad() != this) {

                tile.getRad().die();
                tile.setRad(this);

            }else if((this==tile.getOwner()) && (radTiles!=null)) {

                for (Tile w:radTiles) {
                    w.setRad(null);
                    w.setOwner(this);
                    ownedTiles.add(w);
                }

                coloring(tileMap);


            }



        }
    }

    void die(){

    isAlive = false;

        for (Tile t:ownedTiles) {
            t.setOwner(null);
        }
        for (Tile t:radTiles) {
            t.setRad(null);
        }
    }


    void coloring(TileMap tileMap) {

        int maxX = Integer.MIN_VALUE;
        int minX = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;

        for (Tile t : ownedTiles) {
            int x = t.getX();
            int y = t.getY();

            maxX = Math.max(maxX, x);
            minX = Math.min(minX, x);
            maxY = Math.max(maxY, y);
            minY = Math.min(minY, y);
        }


        ArrayList<Tile> outside = new ArrayList<>();
        ArrayList<Tile> inside = new ArrayList<>();
        ArrayList<Tile> visited = new ArrayList<>();
        ArrayList<Tile> toCheck = new ArrayList<>();

        int y;
        int x;
        for (Tile t : ownedTiles) {
            y = t.getY();
            x = t.getX();
            toCheck.add(tileMap.getTile(x,y - 1));
            toCheck.add(tileMap.getTile(x,y + 1));
            toCheck.add(tileMap.getTile(x - 1,y));
            toCheck.add(tileMap.getTile(x + 1,y));
        }

        for (Tile t : toCheck) {
            if (!inside.contains(t)) {
                Stack<Tile> stack = new Stack<>();
                boolean cont = true;

                stack.push(t);
                while (!stack.empty() ) {
                    Tile checktile = stack.pop();
                    if (!visited.contains(checktile) && checktile.getOwner() != this) {
                        y = checktile.getY();
                        x = checktile.getX();
                        if (x < minX || x > maxX || y < minY || y > maxY || outside.contains(checktile)) {
                            cont = false;
                        } else {
                            visited.add(checktile);
                            stack.push(tileMap.getTile(x, y - 1));
                            stack.push(tileMap.getTile(x, y + 1));
                            stack.push(tileMap.getTile(x - 1, y));
                            stack.push(tileMap.getTile(x + 1, y));
                        }
                    }
                }
                if (cont) {
                    inside.addAll(visited);
                } else {
                    outside.addAll(visited);
                }
                visited.clear();
            }
        }

        for (Tile c : inside) {
            c.setOwner(null);
            c.setRad(null);
            c.setOwner(this);
            ownedTiles.add(c);
        }
    }



}
