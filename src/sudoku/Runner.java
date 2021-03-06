package sudoku;

public class Runner 
{

    public static void main(String[] args)
    {
        Board b = new Board();
        SudokuGUI g = new SudokuGUI();
        Generator newBoard = new Generator();
        int[] thisSudoku = newBoard.generateGrid();
        newBoard.print(thisSudoku);
        newBoard.zeroOut(thisSudoku);
        newBoard.print (thisSudoku);
        b.convert(thisSudoku);
        System.out.print("\n\n\n");
        System.out.print(b.toString());
    }
}
