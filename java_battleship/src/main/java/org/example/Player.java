package org.example;

public class Player {
    public final int length = 10;
    private String[][] playerBoard; //what the user sets up for the opponent
    private String[][] enemyBoard; //what the user sees during gameplay
    private String name;
    private Ship[] ships;

    public Player(String name){
        playerBoard = new String[length][length];
        enemyBoard = new String[length][length];
        ships = new Ship[] {
                new Ship("Aircraft Carrier", 5),
                new Ship("Battleship", 4),
                new Ship("Submarine", 3),
                new Ship("Cruiser", 3),
                new Ship("Destroyer", 2)
        };
        for (int y=0; y<length; y++){
            for (int x=0; x<length; x++) {
                playerBoard[y][x] = "~";
                enemyBoard[y][x] = "~";
            }
        }
        this.name = name;
    }

    public String getName() {
         return name;
    }

    public String getPlayerBoardCoordinate(int row, int col) {
        return playerBoard[row][col];
    }

    public void setPlayerBoardCoordinate(int row, int col, String value) {
        playerBoard[row][col] = value;
    }

    public String getEnemyBoardCoordinate(int row, int col) {
        return enemyBoard[row][col];
    }

    public void setEnemyBoardCoordinate(int row, int col, String value) {
        enemyBoard[row][col] = value;
    }

    public Ship[] getShips() {
        return ships;
    }

    public String processShot(int row, int col) {
        String current = playerBoard[row][col];

        if (current.equals("~")) {
            playerBoard[row][col] = "M";
            return "miss";
        }
        if (current.equals("O")) {
            playerBoard[row][col] = "X";
            for (Ship ship : ships) {
                if (ship.registerHit(row, col)) {
                    if (ship.isSunk()) {
                        if (allShipsSunk()) {
                            return "sunk:last";
                        }
                        return "sunk:" + ship.getName();
                    }
                    return "hit";
                }
            }
        }
        return "repeat";
    }

    public boolean allShipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    public void outputPlayerBoard() {
        outputBoard(playerBoard);
    }

    public void outputEnemyBoard() {
        outputBoard(enemyBoard);
    }

    public void outputBoard(String[][] board) {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int y=0; y<length; y++){
            System.out.print(((char) (y + 65)) + " ");
            for (int x=0; x<length; x++) {
                System.out.print(board[y][x] + " ");
            }
            System.out.print("\n");
        }
    }

}
