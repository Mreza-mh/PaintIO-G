import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Timer;


public class Game extends JPanel {

    Player player;
    private TileMap tileMap;
    private MainPlayer mainPlayer;

    final int unitSize = 29;
    final int rowsCount = 35;
    final int colsCount = 51;

    public static BufferedImage image;
    String selectedShapee;
    boolean active;

    private ArrayList<Player> playersList = new ArrayList<>();
    public Game() {
        tileMap = new TileMap();
    }


    public void setUp(int botCount, int gameSpeed, MainPlayer newPLayer, boolean isActive, String selectedShape) {

//---------------- Creatr players

        playersList.add(newPLayer);
        mainPlayer = newPLayer;

        for (int i = 0; i < botCount; i++) {
            playersList.add(new Enemy());
        }

//----------------  location & 3*3

        for (Player player : playersList) {

            Random rand = new Random();

            player.setX(rand.nextInt(150) + 3000);
            player.setY(rand.nextInt(150) + 3000);

            ArrayList<Tile> startTiles = new ArrayList<>();

            for (int i = (player.getX() - 1); i <= (player.getX() + 1); i++)
                for (int j = (player.getY() - 1); j <= (player.getY() + 1); j++) {
                    startTiles.add(tileMap.getTile(i, j));
                }

            for (Tile tile : startTiles) {
                tile.setOwner(player);
                player.getOwnedTiles().add(tile);
            }

        }

//---------------- Starts a timer & Set game speed
        final int INITIAL_DELAY = 0;
        final int PERIOD_INTERVAL = 1000 / gameSpeed;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), INITIAL_DELAY, PERIOD_INTERVAL);

//---------------- Menu input
        if (isActive) {
            active=false;
        }  else  {
            active=true;}

        selectedShapee = selectedShape;

    }
//------------------ [ End setUp ] -----------------------


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

//---------------- get & draw  BG Tile
        ArrayList<Tile> areaTiles = new ArrayList<>();
        for (int i = (mainPlayer.getX() - (colsCount / 2)); i <= (mainPlayer.getX() + (colsCount / 2)); i++)
            for (int j = (mainPlayer.getY() - (rowsCount / 2)); j <= (mainPlayer.getY() + (rowsCount / 2)); j++) {
                areaTiles.add(tileMap.getTile(i, j));
            }

        int Counter = 0;
        for (int i = 0; i < colsCount; i++) {
            for (int j = 0; j < rowsCount; j++) {
                Tile tile = areaTiles.get(Counter);
                g.setColor(tile.getColor());
                g.fillRect(i * unitSize, j * unitSize, unitSize, unitSize);
                Counter++;
            }
        }

//---------------- draw players
        int drawX;
        int drawY;
        int screenWidth = colsCount * unitSize;
        int screenHeight = rowsCount * unitSize;

        g.setFont(new Font(null, Font.PLAIN, 30));

        for (Player player : playersList) {
            if (player.isAlive) {

                drawX = (player.getX() - mainPlayer.getX()) * unitSize + ((screenWidth - unitSize) / 2);
                drawY = (player.getY() - mainPlayer.getY()) * unitSize + ((screenHeight - unitSize) / 2);

                g.setColor(player.getColor().darker());

                g.drawString(player.getName(), drawX - (unitSize / 2), (drawY + (2 * unitSize)));

                //--------------------shape
                Random rand = new Random();
                int rando = rand.nextInt(2)+1;

                try {
                    image = ImageIO.read(new File("src\\img\\rsz_apex-arena-rank.jpg"));
                } catch (IOException e) {
                    g.fillRect(drawX, drawY, unitSize, unitSize);}


                if (selectedShapee == "3-Par") {
                    g.drawImage(image,drawX, drawY,null);
                } else if (selectedShapee == "Circle") {
                    g.fillOval(drawX, drawY, unitSize, unitSize);
                } else if (selectedShapee == "Pac-Man") {
                    //----------------------------------------------------------------------------------------------------------
                    if (player.getDirection() == 8) {
                        switch (rando) {
                            case 1 -> {
                                g.fillArc(drawX, drawY, unitSize, unitSize, 140, 180);
                                g.fillArc(drawX, drawY, unitSize, unitSize, 220, 180);
                            }
                            case 2 -> {
                                g.fillArc(drawX, drawY, unitSize, unitSize, 100, 180);
                                g.fillArc(drawX, drawY, unitSize, unitSize, 260, 180);
                            }
                        }
                    } else if (player.getDirection() == 6) {
                        switch (rando) {
                            case 1 -> {
                                g.fillArc(drawX, drawY, unitSize, unitSize, 50, 180);
                                g.fillArc(drawX, drawY, unitSize, unitSize, 130, 180);
                            }
                            case 2 -> {
                                g.fillArc(drawX, drawY, unitSize, unitSize, 10, 180);
                                g.fillArc(drawX, drawY, unitSize, unitSize, 170, 180);
                            }
                        }
                    } else if (player.getDirection() == 4) {
                        switch (rando) {
                            case 1 -> {
                                g.fillArc(drawX, drawY, unitSize, unitSize, 230, 180);
                                g.fillArc(drawX, drawY, unitSize, unitSize, 310, 180);
                            }
                            case 2 -> {
                                g.fillArc(drawX, drawY, unitSize, unitSize, 190, 180);
                                g.fillArc(drawX, drawY, unitSize, unitSize, 350, 180);
                            }
                        }

                    } else if (player.getDirection() == 2) {
                        switch (rando) {
                            case 1 -> {
                                g.fillArc(drawX, drawY, unitSize, unitSize, 330, 180);
                                g.fillArc(drawX, drawY, unitSize, unitSize, 30, 180);
                            }
                            case 2 -> {
                                g.fillArc(drawX, drawY, unitSize, unitSize, 290, 180);
                                g.fillArc(drawX, drawY, unitSize, unitSize, 70, 180);
                            }
                        }
                    }
                }
//----------------------------------------------------------------------------------------------------------
            }
        }

// header
        int s = 0;
        int h = 52;
        int playersPerLine = 4;
        int playerCount = 0;

        for (Player player : playersList) {
            if (player.isAlive) {

                g.setColor(Color.black);
                g.drawString(player.getName() + ": " + (player.getX() - 3000) + "," + (player.getY() - 3000)+"|| P : "+( player.ownedTiles.size()), s, h);
                s += 375;
                playerCount++;

                if (playerCount == playersPerLine) {
                    s = 0;
                    h += 58;
                    playerCount = 0;
                }
            }

        }
    }


    private void  Move () {
        for (Player player : playersList) {

            if (!active) {
                new MoveoWeapon(tileMap, this, playersList, mainPlayer, player);
            } else {
                new SmartMove(tileMap, this, playersList, mainPlayer, player);
            }

            if (player instanceof MainPlayer) {
                if (!(mainPlayer.isAlive)){
                    player.finishMP3("src/img/Game-Over-Sound-Effect-1.mp3",mainPlayer);
                }
            }


        }

    }
    // ----------------------------------------------------------------
    private class ScheduleTask extends TimerTask {

        @Override
        public void run() {
            Move();
            repaint();
        }
    }


}







