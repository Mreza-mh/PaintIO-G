import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

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
                                case KeyEvent.VK_SPACE -> space(mainPlayer.getX(), mainPlayer.getY(),playersList );

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



    boolean up = false;
    boolean down = false;
    boolean R = false;
    boolean L = false;
    boolean timer = true;
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);


    void space(int x, int y, ArrayList<Player> playersList) {
        int xx = x;
        int yy = y;

        for (Player player : playersList) {

            if (player instanceof MainPlayer) {
            } else {
                if (timer) {
                    if (player.getDirection() == 8) {
                        if (player.getX() == xx && player.getY() <= yy) {
                            player.die();
                            startSwitching();
                        }
                    }  if (player.getDirection() == 2) {
                        if (player.getX() == xx && player.getY() >= yy) {
                            player.die();
                            startSwitching();
                        }
                    }if (player.getDirection() == 6) {
                        if (player.getY() == yy && player.getX() >= xx) {
                            player.die();
                            startSwitching();
                        }
                    }  if (player.getDirection() == 4) {
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
        }, 3, TimeUnit.SECONDS);
    }




}





