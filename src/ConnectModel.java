
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JOptionPane;

public class ConnectModel {
 
    private int[][] grid;
    private int numTurns;
    private boolean player1;
    private boolean gameOver;
    private LinkedList<int[]> moves; // {row, column, playerNumber}
    private BufferedReader br;
    private BufferedWriter bw;
    private String currLine;

    public ConnectModel() {
        reset();
    }

    public void reset() {
        grid = new int[6][7];
        numTurns = 0;
        player1 = true;
        gameOver = false;
        moves = new LinkedList<>();
    }

    public void pause() {
        File file = new File("files/pause.txt");
        try {
            FileWriter fwrite = new FileWriter(file, false);
            bw = new BufferedWriter(fwrite);
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    bw.write(String.valueOf(grid[i][j]));
                }
                bw.newLine();
            }
            bw.write(String.valueOf(numTurns));

            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    grid[i][j] = 0;
                }
            }
            numTurns = 0;
            gameOver = true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "- Must import a valid file" + System.lineSeparator() +
                    "- Or make sure file is in file path");
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                System.out.println("error when pausing game");
            }
        }
    }

    public void resume() {
        FileReader reader;
        gameOver = false;
        try {
            int numLinesRead = 0;
            reader = new FileReader("files/pause.txt");
            br = new BufferedReader(reader);
            for (int i = 0; i < 6; i++) {
                if (numLinesRead <= 6) {
                    currLine = br.readLine();
                }
                numLinesRead++;
                for (int j = 0; j < 7; j++) {
                    grid[i][j] = Integer.parseInt(currLine.substring(j, j + 1));
                }
            }
            numTurns = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Error when saving file" + System.lineSeparator() +
                    "- Contact provider");
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                System.out.println("Error when closing");
            }
        }
    }

    public void undo() {
        int[] arr = moves.pollLast();
        if (arr != null) {
            int row = arr[0];
            int col = arr[1];
            int state = arr[2];
            grid[row][col] = 0;

            if (state == 1) {
                player1 = true;
            } else if (state == 2) {
                player1 = false;
            }
            if (gameOver) {
                gameOver = false;
            }
        }
        if (numTurns >= 1) {
            numTurns = numTurns - 1;
        }
    }

    // winner will only ever win with 1 combo of horizontal, vertical or
    // diagonal(only one diagonal at a time)
    public int checkWinner() {
        // Check horizontal win by looking for groups of 4
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid[i][j] != 0 && grid[i][j] == grid[i][j + 1] &&
                        grid[i][j + 1] == grid[i][j + 2]
                        && grid[i][j + 2] == grid[i][j + 3]) {
                    gameOver = true;
                    if (grid[i][j] == 1) {
                        return 1;
                    } else if (grid[i][j] == 2) {
                        return 2;
                    }
                }
            }
        }

        // Check vertical win
        for (int i = 0; i < grid[0].length; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[j][i] != 0 && grid[j][i] == grid[j + 1][i] &&
                        grid[j + 1][i] == grid[j + 2][i]
                        && grid[j + 2][i] == grid[j + 3][i]) {
                    gameOver = true;
                    if (grid[j][i] == 1) {
                        return 1;
                    } else if (grid[j][i] == 2) {
                        return 2;
                    }
                }
            }

        }

        // check forward diagonal wins
        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid[i][j] != 0 && grid[i][j] == grid[i - 1][j + 1] && //
                        grid[i - 1][j + 1] == grid[i - 2][j + 2] &&
                        grid[i - 2][j + 2] == grid[i - 3][j + 3]) {
                    gameOver = true;
                    if (grid[i][j] == 1) {
                        return 1;
                    } else if (grid[i][j] == 2) {
                        return 2;
                    }
                }
            }
        }

        // check backward diagonal wins
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid[i][j] != 0 && grid[i][j] == grid[i + 1][j + 1] &&
                        grid[i + 1][j + 1] == grid[i + 2][j + 2]
                        && grid[i + 2][j + 2] == grid[i + 3][j + 3]) {
                    gameOver = true;
                    if (grid[i][j] == 1) {
                        return 1;
                    } else if (grid[i][j] == 2) {
                        return 2;
                    }
                }
            }
        }

        if (numTurns >= 42) {
            gameOver = true;
            return 3;
        } else {
            return 0;
        }
    }

    public boolean getCurrentPlayer() {
        return player1;
    }

    public int getCell(int c, int r) {
        return grid[r][c];
    }

    public boolean playTurn(int c) {
        boolean isFull = grid[0][c] != 0 && grid[1][c] != 0 &&
                grid[2][c] != 0 && grid[3][c] != 0 && grid[4][c] != 0
                && grid[5][c] != 0;

        if (isFull || gameOver) {
            return false;
        }

        numTurns++;
        for (int i = grid.length - 1; i >= 0; i--) {
            if (grid[i][c] == 0) {
                if (player1) {
                    grid[i][c] = 1;
                    int[] state = { i, c, 1 };
                    moves.add(state);
                    if (checkWinner() == 0) {
                        player1 = !player1;
                    }
                    return true;
                } else {
                    grid[i][c] = 2;
                    int[] state = { i, c, 2 };
                    moves.add(state);
                    if (checkWinner() == 0) {
                        player1 = !player1;
                    }
                    return true;
                }
            }
        }
        return true;
    }

    public int getNumMoves() {
        return numTurns;
    }

    public void printGameState() {
        System.out.println("\n\nTurn " + numTurns + ":\n");
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j]);
                if (j < 6) {
                    System.out.print(" | ");
                }
            }
            if (i < 5) {
                System.out.println("\n-------------------------");
            }
        }
    }

}
