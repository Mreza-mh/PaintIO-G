import java.awt.*;
import java.io.FileInputStream;
import java.util.*;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;


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
                tile.setRad(null);
                tile.setOwner(null);
                tile.setRad(this);
                radTiles.add(tile);

            } else if (tile.getOwner() == null && tile.getRad() != this) {

                tile.getRad().die();
                tile.setRad(this);

            }else if((this==tile.getOwner()) && (radTiles!=null)) {

                for (Tile w:radTiles) {
                    w.setRad(null);
                    w.setOwner(this);
                    if (!(ownedTiles.contains(w))){
                    ownedTiles.add(w);
                }}
                radTiles.clear();
                coloring(tileMap,playersList);


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


    void coloring(TileMap tileMap,ArrayList<Player> playersList) {

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

        for (Player player : playersList) {
            if (inside.contains(tileMap.getTile(player.getX(),player.getY()))){
                player.die();
            }
        }


        for (Tile c : inside) {
            c.setOwner(null);
            c.setRad(null);
            c.setOwner(this);
            ownedTiles.add(c);
        }
    }




    public static void finishMP3(String mp3FilePath,MainPlayer mainPlayer) {
        try {
            FileInputStream fileInputStream = new FileInputStream(mp3FilePath);
            Bitstream bitstream = new Bitstream(fileInputStream);

            AdvancedPlayer playeer = new AdvancedPlayer(fileInputStream);

            playeer.setPlayBackListener(new PlaybackListener() {
                @Override
                public void playbackFinished(PlaybackEvent evt) {
                    System.out.println("Finish    "+"your score :  "+(mainPlayer.ownedTiles.size()));
                    if (mainPlayer.ownedTiles.size()<50){
                        System.out.println("poor");  }
                    if ((mainPlayer.ownedTiles.size()>=50) && (200> mainPlayer.ownedTiles.size())){  System.out.println("bad");   }
                    if ((mainPlayer.ownedTiles.size()>=200) && (1000>mainPlayer.ownedTiles.size())){   System.out.println("not bad");  }
                    if ((mainPlayer.ownedTiles.size()>=1000) && (3000>mainPlayer.ownedTiles.size())){  System.out.println("good");  }
                    if ((mainPlayer.ownedTiles.size()>=3000) && (8000>mainPlayer.ownedTiles.size())){  System.out.println("nice");  }
                    if ( mainPlayer.ownedTiles.size()>=80000){   System.out.println("Hero");  }
                }
            });

            playeer.play();

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
