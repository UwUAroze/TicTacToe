package me.aroze.tictactoe;

import java.util.Scanner;

public class Main {
    static int[][] matrix = new int[3][3];
    static int currentPlayer = 0;

    public static void main(String[] args) {
        resetMatrix();
        printMatrix();
        while (true) nextTurn();
    }

    static void clearTerminal() {System.out.println("\n".repeat(100)); }

    static char getChar(int a) {
        return switch (a) {
            case 0 -> 'X';
            case 1 -> 'O';
            default -> '.';
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

    static void printReferenceBoard() {
        System.out.println("Reference board:");
        System.out.println("123\n456\n789");
    }

    static void printMatrix() {
        System.out.println("Ongoing Game:");
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                System.out.print(getChar(matrix[x][y]));
            } System.out.println();
        }
    }

    static void resetMatrix() { for (int x = 0; x < 3; x++) for (int y = 0; y < 3; y++) matrix[x][y] = -1; }

    static void setMatrix(int a, int player) {
        int posX = Math.floorDiv(a-1, 3);
        int posY = a-(3*posX)-1;
        matrix[posX][posY] = player;
    }

    static void nextTurn() {
        if (currentPlayer == 0) currentPlayer = 1;
        else currentPlayer = 0;

        System.out.print("\nPlayer " + (currentPlayer) + " ("+ getChar(currentPlayer) + ")'s turn: Pick a location (1-9): ");
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        setMatrix(a, currentPlayer);

        clearTerminal();

        if (checkForWin()) {
            printMatrix();
            System.out.println("\nGame Over!");
            System.out.println("Player " + (currentPlayer) + " ("+ getChar(currentPlayer) + ") won!");
            System.exit(0);
        }

        printReferenceBoard();
        System.out.println();
        printMatrix();
    }

}