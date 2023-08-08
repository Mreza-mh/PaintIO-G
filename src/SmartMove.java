import java.awt.event.*;
import java.time.*;
import java.util.*;

public class SmartMove {

  private static int enterCount = (13*(3))+1;
  private static LocalDateTime lastSpacePressTime;
  static int size ;

    public SmartMove(TileMap tileMap, Game game, ArrayList<Player> playersList, MainPlayer mainPlayer) {

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
                                case KeyEvent.VK_SPACE -> {
                                    LocalDateTime currentTime = LocalDateTime.now();
                                    if (lastSpacePressTime == null || Duration.between(lastSpacePressTime, currentTime).getSeconds() >= 3) {
                                        space(mainPlayer.getX(), mainPlayer.getY(), playersList, mainPlayer);
                                        lastSpacePressTime = currentTime;
                                    }
                                }
                                case KeyEvent.VK_ENTER -> {
                                    if (enterCount > 0) {
                                        enter(mainPlayer.getX(), mainPlayer.getY(), playersList, mainPlayer, tileMap);
                                        enterCount--;
                                        System.out.println(enterCount);
                                    } else {
                                        System.out.println("Finish");
                                    }
                                    break;
                                }
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
                //-------------------------------[Enemy]
                else {
                    Tile t;        int lo = player.ownedTiles.size() - 1;      t = player.ownedTiles.get(lo);

                    int xt = t.getX();          int yt = t.getY();

                    int xp = player.getX();     int yp = player.getY();
            //--------------
                    if (size < 35) {//------------------ random

                        Random rando = new Random();
                        int random = rando.nextInt(20) + 1;
                        switch (random) {
                            case 1 -> player.setDirection(6);
                            case 2 -> player.setDirection(4);
                            case 3 -> player.setDirection(8);
                            case 4 -> player.setDirection(2);
                            default -> {
                            }
                        }
                        size++;
                        if (player.Direction == 8) {
                            player.setY((player.getY() - 1));//u

                        } else if (player.Direction == 2) {
                            player.setY((player.getY() + 1));//p

                        } else if (player.Direction == 6) {
                            player.setX((player.getX() + 1));//r

                        } else if (player.Direction == 4) {
                            player.setX((player.getX() - 1));//l
                        }
                    }
            //--------------
                    if (size == 35) {//------------------ stop
                        size++;
                    }
            //--------------
                    if (size > 35) {//------------------ return

//__________________________________________________________________________________________
                        if ((xp == xt) && (yp == yt)) {
                            size = 0;
//__________________________________________________________________________________________
                        } else if ((xp >= xt) && (yp >= yt)) {//r p

                            Random rando = new Random();
                            int rand = rando.nextInt(2) + 1;
                            switch (rand) {
                                case 1 -> {
                                    if (xp != xt) {
                                        player.setX((player.getX() - 1));//L
                                    }
                                }
                                case 2 -> {
                                    if (yp != yt) {
                                        player.setY((player.getY() - 1));//u
                                    }
                                }
                                default -> {
                                }
                            }
//__________________________________________________________________________________________
                        } else if ((xp <= xt) && (yp >= yt)) {//l p

                            Random rando = new Random();
                            int rand = rando.nextInt(2) + 1;
                            switch (rand) {
                                case 1 -> {
                                    if (xp != xt) {
                                        player.setX((player.getX() + 1));//r
                                    }
                                }
                                case 2 -> {
                                    if (yp != yt) {
                                        player.setY((player.getY() - 1));//u

                                    }
                                }
                                default -> {
                                }
                            }
//__________________________________________________________________________________________
                        } else if ((xp >= xt) && (yp <= yt)) {//r u

                            Random rando = new Random();
                            int rand = rando.nextInt(2) + 1;
                            switch (rand) {
                                case 1 -> {
                                    if (xp != xt) {
                                        player.setX((player.getX() - 1));//L
                                    }
                                }
                                case 2 -> {
                                    if (yp != yt) {
                                        player.setY((player.getY() + 1));//p
                                    }
                                }
                                default -> {
                                }
                            }
//__________________________________________________________________________________________
                        } else if ((xp <= xt) && (yp <= yt)) {//l u

                            Random rando = new Random();
                            int rand = rando.nextInt(2) + 1;
                            switch (rand) {
                                case 1 -> {
                                    if (xp != xt) {
                                        player.setX((player.getX() + 1));//r

                                    }
                                }
                                case 2 -> {
                                    if (yp != yt) {
                                        player.setY((player.getY() + 1));//p
                                    }
                                }
                                default -> {
                                }
                            }
                        }
                    }
                }
            }
        }
        for (Player player : playersList) {
            player.processMovement(tileMap, playersList);
        }

    }


//------------------------[ WEAPON ]-----------------------------------------------

    void space(int x1, int y1, ArrayList<Player> playersList, MainPlayer mainPlayer) {
        int xx = x1;
        int yy = y1;

        for (Player player : playersList) {

            if (player instanceof MainPlayer) {
            } else {
                if (mainPlayer.getDirection() == 8) {
                    if (player.getX() == xx && player.getY() <= yy) {
                        player.die();

                    }
                }
                if (mainPlayer.getDirection() == 2) {
                    if (player.getX() == xx && player.getY() >= yy) {
                        player.die();

                    }
                }
                if (mainPlayer.getDirection() == 6) {
                    if (player.getY() == yy && player.getX() >= xx) {
                        player.die();

                    }
                }
                if (mainPlayer.getDirection() == 4) {
                    if (player.getY() == yy && player.getX() <= xx) {
                        player.die();

                    }
                }
            }
        }
    }






//*----------------------------------------------------------------



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


