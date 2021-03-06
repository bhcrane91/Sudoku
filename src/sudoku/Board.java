package sudoku;
import java.util.*;

public class Board 
{
    private int[][] board = {
            {7,8,0,4,0,0,1,2,0},
            {6,0,0,0,7,5,0,0,9},
            {0,0,0,6,0,1,0,7,8},
            {0,0,7,0,4,0,2,6,0},
            {0,0,1,0,5,0,9,3,0},
            {9,0,4,0,6,0,0,0,5},
            {0,7,0,3,0,0,0,1,2},
            {1,2,0,0,0,7,4,0,0},
            {0,4,9,2,0,6,0,0,7}
        };
    private int row;
    private int col;
    private boolean solved;
    private int[] newBoard;
    private ArrayList<Integer> nums;
    
    public Board()
    {
        newBoard = new int[81];
        row = board.length;
        col = board[0].length;
        nums = new ArrayList<Integer>();
        
        
        // solved = false;
    }
    
    public void createBoard()
    {
        
    }
    
    public int[][] getBoard()
    {
        return board;
    }
    
    public int rows()
    {
        return row;
    }
    
    public int cols()
    {
        return col;
    }
    
    public boolean solved()
    {
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                if (board[i][j] == 0)
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    public int[][] convert(int[] grid)
    {
        int n = 0;
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                board[i][j] = grid[n];
                n++;
            }
        }
        return board;
    }
    
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < board.length; i++) 
        {
            if ((i % 3) == 0 && i != 0)
            {
                s.append("- - - - - - - - - - -\n");
            }
            
            for (int j = 0; j < board[i].length; j++)
            {
                if ((j % 3) == 0 && j != 0)
                {
                    s.append("| ");
                }
                
                if (j == 8)
                {
                    s.append(board[i][j] + "\n");
                }
                else 
                {
                    s.append(board[i][j] + " ");
                }
            }
        }
        return s.toString();
    }

    
}
