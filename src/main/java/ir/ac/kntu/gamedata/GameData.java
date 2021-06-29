package ir.ac.kntu.gamedata;

import ir.ac.kntu.gamebuilder.GameAriaBuilder;
import ir.ac.kntu.gameobjects.Block;
import ir.ac.kntu.model.GameStatus;
import ir.ac.kntu.model.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class GameData {

    public static final File MAP_1 = new File("src/main/resources/maps/map_1.txt");

    public static ArrayList<Player> PLAYERS = new ArrayList<>();

    private static GameStatus gameStatus = GameStatus.STOP;

    private static boolean gameControl = false;

    private static boolean stopControl = false;

    private static int currentScore = 0;

    public final static int FIRST_HEALTH_OF_PLAYER = 3;

    public final static int NUMBER_OF_LEVELS = 3;

    public final static int EMPTY_BLOCK = 0;

    public final static int BLOCK = 1;

    public final static int PLAYER_CHARACTER = 2;

    public final static int GAP = 40;

    public final static int SIZE_OF_GAME_ACTION_ARIA = 18;

    public final static int REAL_SIZE_OF_GAME_ACTION_ARIA = SIZE_OF_GAME_ACTION_ARIA * GAP;

    public final static int START_X_GAME_ACTION_ARIA = 30;

    public final static int START_Y_GAME_ACTION_ARIA = 80;

    public final static int END_X_GAME_ACTION_ARIA = REAL_SIZE_OF_GAME_ACTION_ARIA;

    public final static int END_Y_GAME_ACTION_ARIA = REAL_SIZE_OF_GAME_ACTION_ARIA;

    public final static int[][] MAP_DATA = new int[SIZE_OF_GAME_ACTION_ARIA][SIZE_OF_GAME_ACTION_ARIA];

    public final static Block[][] BLOCKS = new Block[SIZE_OF_GAME_ACTION_ARIA][SIZE_OF_GAME_ACTION_ARIA];


    public static int calculateRealXY(int fake) {
        return fake * GAP;
    }

    public static ArrayList<Player> players() {
        return (ArrayList<Player>) sortPlayers().clone();
    }

    public static void addPlayer(Player newPlayer) {
        PLAYERS.add(newPlayer);
    }

    public static void gameControlOn() {
        gameControl = true;
    }

    public static void gameControlOff() {
        gameControl = false;
    }

    public static Block getBlockInSpecificFakeXY(int x, int y) {
        return BLOCKS[x][y];
    }

    public static void stopGame() {
        gameStatus = GameStatus.STOP;
        gameControl = false;
    }

    public static void runGame() {
        gameStatus = GameStatus.RUNNING;
        gameControl = true;
    }

    public static GameStatus gameStatus() {
        return gameStatus;
    }

    public static boolean isGameControl() {
        return gameControl;
    }

    public static boolean isStopControl() {
        return stopControl;
    }

    public static void stopControlOn() {
        stopControl = true;
    }

    public static void stopControlOff() {
        stopControl = false;
    }

    public static int getCurrentScore() {
        return currentScore;
    }

    public static void increaseScore(int value) {
        currentScore += value;
        GameAriaBuilder.getScore().setText("" + currentScore);
    }

    public static int[][] importMap(String fileURLAddress) {
        try {
            File mapFile = new File(fileURLAddress);
            if (mapFile.exists()) {
                Scanner reader = new Scanner(mapFile);
                while (reader.hasNextInt()) {
                    for (int i = 0; i < MAP_DATA.length; i++) {
                        for (int j = 0; j < MAP_DATA[0].length; j++) {
                            MAP_DATA[i][j] = reader.nextInt();
                        }
                    }
                }
            }
        } catch (FileNotFoundException ignored) {
        }
        return MAP_DATA;
    }

    public static void printGameDate() {
        for (int i = 0; i < MAP_DATA.length; i++) {
            for (int j = 0; j < MAP_DATA[0].length; j++) {
                System.out.print(MAP_DATA[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void saveOrUpdatePlayersToFile() {
        File playersFile = new File("src/main/java/ir/ac/kntu/playersFile");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(playersFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(PLAYERS);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void readOrImportFileToPlayers() {
        File playersFile = new File("src/main/java/ir/ac/kntu/playersFile");
        if (playersFile.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(playersFile);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                PLAYERS = (ArrayList<Player>) objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static ArrayList<Player> sortPlayers() {
        PLAYERS.sort(Comparator.comparing(Player::getHighScore).reversed());
        for (Player p : PLAYERS) {
            p.setRank(PLAYERS.indexOf(p) +1);
        }
        return PLAYERS;
    }

}
