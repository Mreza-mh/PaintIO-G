import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.*;


public class MoveoWeapon {

    private static int enterCount = (13*(2))+2;

    public MoveoWeapon(TileMap tileMap, Game game, ArrayList<Player> playersList, MainPlayer mainPlayer) {

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
                                case KeyEvent.VK_SPACE ->space(mainPlayer.getX(), mainPlayer.getY(),playersList ,mainPlayer);
                                case KeyEvent.VK_ENTER->{if (enterCount > 0) {
                                        enter(mainPlayer.getX(), mainPlayer.getY(), playersList, mainPlayer, tileMap);
                                        enterCount --;
                                    } else {
                                        System.out.println("Finish");}break;}

                                default -> {
                                }
                            }
                        }
                    });

                    if (mainPlayer.Direction == 8) {
                        mainPlayer.setY((mainPlayer.getY() - 1));

                    } else if (mainPlayer.Direction == 2) {
                        mainPlayer.setY((mainPlayer.getY() + 1));

                    } else if (mainPlayer.Direction == 6) {
                        mainPlayer.setX((mainPlayer.getX() + 1));

                    } else if (mainPlayer.Direction == 4) {
                        mainPlayer.setX((mainPlayer.getX() - 1));
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

// ------------- [weapon straight ] ------------------------
 boolean timer = true;
 ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    void space(int x, int y, ArrayList<Player> playersList , MainPlayer mainPlayer) {
        int xx = x;
        int yy = y;

        for (Player player : playersList) {

            if (player instanceof MainPlayer) {
            } else {
                if (timer) {
                    if (mainPlayer.getDirection() == 8) {
                        if (player.getX() == xx && player.getY() <= yy) {
                            player.die();
                            startSwitching();
                        }
                    }  if (mainPlayer.getDirection() == 2) {
                        if (player.getX() == xx && player.getY() >= yy) {
                            player.die();
                            startSwitching();
                        }
                    }if (mainPlayer.getDirection() == 6) {
                        if (player.getY() == yy && player.getX() >= xx) {
                            player.die();
                            startSwitching();
                        }
                    }  if (mainPlayer.getDirection() == 4) {
                        if (player.getY() == yy && player.getX() <= xx) {
                            player.die();
                            startSwitching();
                        }
                    }
                }
            }
        }
    }

    public void startSwitching() {
        timer = false;
        System.out.println("off");
        executor.schedule(() -> {
            timer = true;
            System.out.println("ready to fire");
        }, 10, TimeUnit.SECONDS);
    }

//*------------------[  weapon 3*3 ]---------------------------------

 void enter(int x2, int y2, ArrayList<Player> playersList, MainPlayer mainPlayer, TileMap tileMap) {

        int xx2 = x2;
        int yy2 = y2;
        for (Player player : playersList) {
            if (player.isAlive) {

                if (player instanceof MainPlayer) {
                    if (mainPlayer.getDirection() == 8) {//up
                        xx2 = x2;
                        yy2 = y2 - 7;
                    } else if (mainPlayer.getDirection() == 2) {//down
                        xx2 = x2;
                        yy2 = y2 + 7;
                    } else if (mainPlayer.getDirection() == 6) {//R
                        xx2 = x2 + 7;
                        yy2 = y2;
                    } else if (mainPlayer.getDirection() == 4) {//L
                        xx2 = x2 - 7;
                        yy2 = y2;
                    }

                } else {
                    if (player.getX() <= (xx2 + 1) && player.getX() >= (xx2 - 1) &&
                            player.getY() <= (yy2 + 1) && player.getY() >= (yy2 - 1)) {
                        player.die();
                    }
                }
            }
        }

        for (Player player : playersList) {
            if (player.isAlive) {

                if (player instanceof MainPlayer) {
                    if (mainPlayer.getDirection() == 8) {//up
                        xx2 = x2;
                        yy2 = y2 - 7;
                    } else if (mainPlayer.getDirection() == 2) {//down
                        xx2 = x2;
                        yy2 = y2 + 7;
                    } else if (mainPlayer.getDirection() == 6) {//R
                        xx2 = x2 + 7;
                        yy2 = y2;
                    } else if (mainPlayer.getDirection() == 4) {//L
                        xx2 = x2 - 7;
                        yy2 = y2;
                    }

                    ArrayList<Tile> weaponA = new ArrayList<>();
                    for (int i = (xx2 - 1); i <= (xx2 + 1); i++)
                        for (int j = (yy2 - 1); j <= (yy2 + 1); j++) {
                            weaponA.add(tileMap.getTile(i, j));
                        }
                    for (Tile tile : weaponA) {
                        tile.setOwner(player);
                        player.getOwnedTiles().add(tile);
                    }
                }
            }
        }
    }
 }







