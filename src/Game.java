import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Timer;


public class Game extends JPanel {

    private TileMap tileMap;
    private MainPlayer mainPlayer;

    final int unitSize = 29;
    final int rowsCount = 35;
    final int colsCount = 51;

    String selectedShapee;

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

            Random r = new Random();

            player.setX(r.nextInt(150) + 3000);
            player.setY(r.nextInt(150) + 3000);

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
        if (isActive) { System.out.println("falsee");
        } else {
            System.out.println("falsee");
        }

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
                //shape
                if (selectedShapee == "Square") {
                    g.fillRect(drawX, drawY, unitSize, unitSize);
                } else if (selectedShapee == "Circle") {
                    g.fillOval(drawX, drawY, unitSize, unitSize);
                } else if (selectedShapee == "SemiCircle") {
                    g.fillArc(drawX, drawY, unitSize, unitSize, 0, 180);
                }

            }
        }
        int s = 0;
        int h = 52;
        int playersPerLine = 5;
        int playerCount = 0;

        for (Player player : playersList) {
            if (player.isAlive) {

                g.drawString(player.getName() + "|| " + (player.getX() - 3000) + "," + (player.getY() - 3000), s, h);
                s += 300;
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
        new Move(tileMap,this,playersList,mainPlayer);
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







