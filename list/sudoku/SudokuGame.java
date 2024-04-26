package list.sudoku;

public class SudokuGame {
    private SudokuPuzzle puzzle;

    public SudokuGame(SudokuPuzzle puzzle) {
        this.puzzle = puzzle;
    }

    public SudokuPuzzle getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(SudokuPuzzle puzzle) {
        this.puzzle = puzzle;
    }
}
