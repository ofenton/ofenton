package com.yahoo;

/**
 * Created on 3/29/15.
 */
public class SudokuSolver {

    SudokuSolver() {
        char[][] board = {
                {'.', '.','9','7','4','8','.','.','.'}
                ,{'7','.','.','.','.','.','.','.','.'}
                ,{'.','2','.','1','.','9','.','.','.'}
                ,{'.','.','7','.','.','.','2','4','.'}
                ,{'.','6','4','.','1','.','5','9','.'}
                ,{'.','9','8','.','.','.','3','.','.'}
                ,{'.','.','.','8','.','3','.','2','.'}
                ,{'.','.','.','.','.','.','.','.','6'}
                ,{'.','.','.','2','7','5','9','.','.'}};
        printSudoku(board);
    }

    private String sudokoToString(char[][] board) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < board.length; i++) {
            sb.append((i == 0 ? "{" : ",{"));
            for (int j = 0; j < board[i].length; j++) {
                if (j != 0 ) sb.append(",");
                sb.append(board[i][j]);
            }
            sb.append("}");
        }
        return sb.toString();
    }

    public void printSudoku(char[][] board) {
        System.out.println("START: " + sudokoToString(board));
        solveSudoku(board);
        System.out.println("END  : " + sudokoToString(board));
    }

    public void solveSudoku(char[][] board) {
        solveSudoku(board, 0, 0);
    }

    private boolean solveSudoku(char[][] board, int x, int y) {
        if (board[x][y] != '.') {

            if ((x == 8) && (y == 8)) return true;

            // MORE WORK
            boolean complete = false;
            if (x == board.length-1 && y < board.length-1) {
                complete = solveSudoku(board, 0, y+1);
            } else if (x < board.length-1) {
                complete = solveSudoku(board, x+1, y);
            }

            // SUCCESS
            if (complete) return true;
        }

        if (board[x][y] == '.') {
            for (int num = 1; num <= 9; num++) {
                board[x][y] = Character.forDigit(num, 10);
                if (isValid(board, x, y)) {

                    if ((x == 8) && (y == 8)) return true;

                    // MORE WORK
                    boolean complete = false;
                    if (x == board.length-1 && y < board.length-1) {
                        complete = solveSudoku(board, 0, y+1);
                    } else if (x < board.length-1) {
                        complete = solveSudoku(board, x+1, y);
                    }

                    // SUCCESS
                    if (complete) return true;

                }
                board[x][y] = '.';
            }
        }

        // No solution
        return false;
    }

    private boolean isValid(char[][] board, int x, int y) {

        // checkRow(board, x, y);
        for (int i = 0; i < board.length; i++) {
            if (i == x) continue;
            if (board[x][y] == board[i][y]) return false;
        }

        // checkColumn(board, x, y);
        for (int i = 0; i < board.length; i++) {
            if (i == y) continue;
            if (board[x][y] == board[x][i]) return false;
        }

        // checkBox(board, x, y);
        // x = 0, 1, 2 | 3, 4, 5 | 6, 7, 8 => x % 3
        // y = 0, 1, 2 | 3, 4, 5 | 6, 7, 8
        int xAbs = (int) x / 3; // 0, 1, 2
        int yAbs = (int) y / 3; // 0, 1, 2

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (x == (xAbs * 3 + i) && y == (yAbs * 3 + j)) continue;
                if (board[xAbs * 3 + i][yAbs * 3 + j] == board[x][y]) return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        new SudokuSolver();
    }
}
