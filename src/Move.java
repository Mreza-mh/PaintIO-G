import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Move {

    public Move(TileMap tileMap, Game game, ArrayList<Player> playersList, MainPlayer mainPlayer) {

        for (Player player : playersList) {
            if (player.isAlive) {


                if (player instanceof MainPlayer) {

                    game.setFocusable(true);
                    game.requestFocus();
                    game.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            int pressedKey = e.getKeyCode();

                            switch (pressedKey) {
                                case KeyEvent.VK_LEFT -> player.setDirection(4);
                                case KeyEvent.VK_UP -> player.setDirection(8);
                                case KeyEvent.VK_RIGHT -> player.setDirection(6);
                                case KeyEvent.VK_DOWN -> player.setDirection(2);
                                default -> {
                                }
                            }
                        }
                    });

                    if (mainPlayer.Direction == 8) {
                        mainPlayer.setY((mainPlayer.getY() - 1));
                        //  break;
                    } else if (mainPlayer.Direction == 2) {
                        mainPlayer.setY((mainPlayer.getY() + 1));
                        //  break;
                    } else if (mainPlayer.Direction == 6) {
                        mainPlayer.setX((mainPlayer.getX() + 1));
                        // break;
                    } else if (mainPlayer.Direction == 4) {
                        mainPlayer.setX((mainPlayer.getX() - 1));
                        // break;
                    }

                }

                //***********************
                else {
                    int random = ThreadLocalRandom.current().nextInt(15) + 1;

                    switch (random) {
                        case 1 -> player.setDirection(6);
                        case 2 -> player.setDirection(4);
                        case 3 -> player.setDirection(8);
                        case 4 -> player.setDirection(2);
                        default -> {
                        }
                    }

                    if (player.Direction == 8) {
                        player.setY((player.getY() - 1));

                    } else if (player.Direction == 2) {
                        player.setY((player.getY() + 1));

                    } else if (player.Direction == 6) {
                        player.setX((player.getX() + 1));

                    } else if (player.Direction == 4) {
                        player.setX((player.getX() - 1));
                    }
                }
            }
        }

        for (Player player : playersList) {
            player.processMovement(tileMap, playersList);
        }
    }
}





