package me.aroze.tictactoe;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static int[][] matrix = new int[3][3];
    static int currentPlayer = 0;

    public static void main(String[] args) {
        resetMatrix();
        printReferenceBoard();
        System.out.println();
        printMatrix();
        while (true) nextTurn();
    }

    static void clearTerminal() { System.out.println("\n".repeat(50)); }

    static char getChar(int a) {
        return switch (a) {
            case 0 -> 'X';
            case 1 -> 'O';
            default -> ' ';
        };
    }

    static boolean checkForWin() {
        // yes. this is terrible

        // check rows
        for (int i = 0; i < 3; i++) {
            if (matrix[i][0] == matrix[i][1] && matrix[i][1] == matrix[i][2] && matrix[i][2] != -1) return true;
        }

        // check columns
        for (int i = 0; i < 3; i++) {
            if (matrix[0][i] == matrix[1][i] && matrix[1][i] == matrix[2][i] && matrix[2][i] != -1) return true;
        }

        // check diagonals
        if (matrix[0][0] == matrix[1][1] && matrix[1][1] == matrix[2][2] && matrix[2][2] != -1) return true;
        if (matrix[0][2] == matrix[1][1] && matrix[1][1] == matrix[2][0] && matrix[2][0] != -1) return true;

        return false;
    }

    static boolean checkForDraw() {
        for (int x = 0; x < 3; x++) for (int y = 0; y < 3; y++) if (matrix[x][y] == -1) return false;
        return true;
    }

    static void printReferenceBoard() {
        System.out.println("Reference board:");
        System.out.println("123\n456\n789");
    }

    static void printMatrix() {
        System.out.println("Ongoing Game:");
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                String format = "\u001b[0m";
                if (x != 2) format = "\u001b[4m";
                System.out.print(format + getChar(matrix[x][y]));
                if (y != 2) System.out.print("|");
            } System.out.println();
        }
    }

    static void resetMatrix() { for (int x = 0; x < 3; x++) for (int y = 0; y < 3; y++) matrix[x][y] = -1; }

    static void setMatrix(int a, int player) {
        int posX = Math.floorDiv(a-1, 3);
        int posY = a-(3*posX)-1;
        matrix[posX][posY] = player;
    }

    static int getMatrix(int a) {
        int posX = Math.floorDiv(a-1, 3);
        int posY = a-(3*posX)-1;
        return matrix[posX][posY];
    }

    static int getInput() {
        Scanner scanner = new Scanner(System.in);
        int a;
        try { a = scanner.nextInt(); }
        catch (InputMismatchException e) {
            System.out.print("Invalid input. Please try again: ");
            return getInput();
        }
        return a;
    }

    static void nextTurn() {
        if (currentPlayer == 0) currentPlayer = 1;
        else currentPlayer = 0;

        System.out.print("\nPlayer " + (currentPlayer) + " ("+ getChar(currentPlayer) + ")'s turn: Pick a location (1-9): ");

        int a = getInput();

        while (a < 1 || a > 9) {
            System.out.print("That's not a number between 1 and 9. Please try again: ");
            a = getInput();
        }

        while (getMatrix(a) != -1) {
            System.out.print("That location is already taken. Pick another: ");
            a = getInput();
        }

        setMatrix(a, currentPlayer);

        clearTerminal();

        if (checkForWin()) {
            printMatrix();
            System.out.println("\nGame Over!");
            System.out.println("Player " + (currentPlayer) + " ("+ getChar(currentPlayer) + ") won!");
            System.exit(0);
        }

        if (checkForDraw()) {
            printMatrix();
            System.out.println("\nGame Over!");
            System.out.println("It's a draw! There were no remaining locations left in the board");
            System.exit(0);
        }

        printReferenceBoard();
        System.out.println();
        printMatrix();
    }

}