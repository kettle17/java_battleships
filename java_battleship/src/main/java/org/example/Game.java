package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    Player p1;
    Player p2;
    List<String>[] playerSetup;
    private final Scanner scanner = new Scanner(System.in);

    public Game() {
        p1 = new Player("Player 1");
        p2 = new Player("Player 2");
        playerSetup = new ArrayList[2];
        playerSetup[0] = new ArrayList<>();
        playerSetup[1] = new ArrayList<>();
    }

    public void start() {
        setupGame();
        startGame();
    }

    public boolean isCoordinateValid(String coordinate) {
        char firstChar = Character.toUpperCase(coordinate.charAt(0));
        String numberPart = coordinate.substring(1);
        if (firstChar >= 'A' && firstChar <= 'J') {
            try {
                int number = Integer.parseInt(numberPart);
                if (number >= 1 && number <= 10) {
                    return true;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    public boolean isPlacementValid(String c1, String c2, Player p, Ship ship) {
        char c1Row = Character.toUpperCase(c1.charAt(0));
        char c2Row = Character.toUpperCase(c2.charAt(0));
        int c1Col = Integer.parseInt(c1.substring(1)) - 1; // 0-based index
        int c2Col = Integer.parseInt(c2.substring(1)) - 1; // 0-based index
        int length = ship.getLength();

        // Offsets for surrounding cells (including diagonals)
        int[] dRow = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dCol = {-1, 0, 1, -1, 1, -1, 0, 1};

        if (c1Row == c2Row) { // horizontal
            if (c1Col > c2Col) {
                int temp = c1Col;
                c1Col = c2Col;
                c2Col = temp;
            }
            if (Math.abs(c2Col - c1Col) == length - 1) {
                // Check overlap & adjacency
                for (int col = c1Col; col <= c2Col; col++) {
                    int rowIndex = c1Row - 'A';
                    String checkCoord = p.getPlayerBoardCoordinate(rowIndex, col);
                    if (!checkCoord.equals("~")) {
                        System.out.println("Error! Ship cannot occupy another ship's place! Try again:");
                        return false;
                    }
                    // Check surrounding cells
                    for (int k = 0; k < 8; k++) {
                        int newRow = rowIndex + dRow[k];
                        int newCol = col + dCol[k];
                        if (newRow >= 0 && newRow < 10 && newCol >= 0 && newCol < 10) {
                            if (p.getPlayerBoardCoordinate(newRow, newCol).equals("O")) {
                                System.out.println("Error! You placed it too close to another one. Try again:");
                                return false;
                            }
                        }
                    }
                }
                // Place ship
                for (int i = 0; i < length; i++) {
                    int rowIndex = c1Row - 'A';
                    int colIndex = c1Col + i;
                    ship.setPosition(i, rowIndex, colIndex);
                    p.setPlayerBoardCoordinate(rowIndex, colIndex, "O");
                }
                return true;
            }
            System.out.println("Error! Wrong length of the " + ship.getName() + "! Try again:\n");

        } else if (c1Col == c2Col) { // vertical
            if (c1Row > c2Row) {
                char temp = c1Row;
                c1Row = c2Row;
                c2Row = temp;
            }
            int startRow = c1Row - 'A';
            int endRow = c2Row - 'A';
            if (Math.abs(endRow - startRow) == length - 1) {
                // Check overlap & adjacency
                for (int row = startRow; row <= endRow; row++) {
                    String checkCoord = p.getPlayerBoardCoordinate(row, c1Col);
                    if (!checkCoord.equals("~")) {
                        System.out.println("Error! Ship cannot occupy another ship's place! Try again:");
                        return false;
                    }
                    // Check surrounding cells
                    for (int k = 0; k < 8; k++) {
                        int newRow = row + dRow[k];
                        int newCol = c1Col + dCol[k];
                        if (newRow >= 0 && newRow < 10 && newCol >= 0 && newCol < 10) {
                            if (p.getPlayerBoardCoordinate(newRow, newCol).equals("O")) {
                                System.out.println("Error! You placed it too close to another one. Try again:");
                                return false;
                            }
                        }
                    }
                }
                // Place ship
                for (int i = 0; i < length; i++) {
                    int rowIndex = startRow + i;
                    int colIndex = c1Col;
                    ship.setPosition(i, rowIndex, colIndex);
                    p.setPlayerBoardCoordinate(rowIndex, colIndex, "O");
                }
                return true;
            }
            System.out.println("Error! Wrong length of the " + ship.getName() + "! Try again:\n");

        } else {
            System.out.println("Error! Wrong ship location! Try again:\n");
        }
        return false;
    }

    public void setupGame(){
        //setup player objects

        int currP = 0;
        for (Player currPlayer : new Player[]{p1, p2}) {
            //Players need to place their ships on the game board
            System.out.println(currPlayer.getName() + ", place your ships on the game field");
            currPlayer.outputPlayerBoard();

            for (Ship ship : currPlayer.getShips()) {
                String playerInput1, playerInput2;

                do {
                    System.out.println("Enter the coordinates of the " + ship.getName() + " (" + ship.getLength() + " cells):");
                    String c1 = scanner.next();
                    String c2 = scanner.next();

                    if (isCoordinateValid(c1) && isCoordinateValid(c2)) {
                        if (isPlacementValid(c1, c2, currPlayer, ship)) {
                            break;
                        }
                    } else {
                        System.out.println("Error! Incorrect input! Try again:\n");
                    }
                } while (true);

                currPlayer.outputPlayerBoard();
            }
            changeTurn();
        }
    }

    public void startGame() {
        String playerInput = "";
        Player[] players = {p1, p2};
        List<String>[] playerInputs = new ArrayList[2];
        playerInputs[0] = new ArrayList<>(); // Player 1
        playerInputs[1] = new ArrayList<>(); // Player 2

        int turn = 0; // alternating players
        while (true) {
            Player currPlayer = players[turn % 2];
            Player oppPlayer = players[(turn + 1) % 2];

            currPlayer.outputEnemyBoard();
            System.out.println("---------------------");
            currPlayer.outputPlayerBoard();

            do {
                System.out.print(currPlayer.getName() + ", it's your turn: ");
                playerInput = scanner.nextLine().trim();

                if (playerInput.isEmpty()) {
                    System.out.println("Error! You must enter a coordinate. Try again:\n");
                    continue;
                }

                if (isCoordinateValid(playerInput)) {
                    char rowChar = Character.toUpperCase(playerInput.charAt(0));
                    int shotCol = Integer.parseInt(playerInput.substring(1)) - 1;
                    int shotRow = rowChar - 'A';

                    String result = oppPlayer.processShot(shotRow, shotCol);

                    switch (result) {
                        case "miss":
                            System.out.println("You missed!\n");
                            currPlayer.setEnemyBoardCoordinate(shotRow, shotCol, "M");
                            playerInputs[turn % 2].add(playerInput);
                            break;

                        case "hit":
                            System.out.println("You hit a ship!\n");
                            currPlayer.setEnemyBoardCoordinate(shotRow, shotCol, "X");
                            playerInputs[turn % 2].add(playerInput);
                            break;

                        default:
                            if (result.equals("sunk:last")) {
                                System.out.println("You sank the last ship. You won. Congratulations!");
                                return; // game over immediately
                            } else if (result.startsWith("sunk:")) {
                                String sunkShip = result.substring(5);
                                System.out.println("You sank the " + sunkShip + "!\n");
                                currPlayer.setEnemyBoardCoordinate(shotRow, shotCol, "X");
                                playerInputs[turn % 2].add(playerInput);
                            } else if (result.equals("repeat")) {
                                System.out.println("Error! You’ve already fired here. Try again:\n");
                                continue;
                            }
                    }
                    if (oppPlayer.allShipsSunk()) {
                        System.out.println("You sank the last ship. You won. Congratulations!");
                        return;
                    }
                    break;
                } else {
                    System.out.println("Error! Incorrect input! Try again:\n");
                }
            } while (true);
            changeTurn();
            turn++;
        }
    }

    public void changeTurn() {
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
    }
}
