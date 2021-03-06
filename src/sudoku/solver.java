package sudoku;
import java.util.*;
// import java.util.Observable;

public class solver // extends Observable
{
    private int[][] board;
    
    public solver()
    {
        board = new int[9][9];
    }
    
    public boolean valid(int[][] b, int num, int[] pos)
    {
        for (int i = 0; i < b.length; i++)
        {
            if (b[pos[0]][i] == num && pos[1] != i)
            {
                return false;
            }
        }
        
        for (int j = 0; j < b.length; j++)
        {
            if (b[j][pos[1]] == num && pos[0] != j)
            {
                return false;
            }
        }
        
        int xBox = pos[1] / 3;
        int yBox = pos[0] / 3;
        
        for (int k = (yBox * 3); k < ((yBox * 3) + 3); k++)
        {
            for (int l = (xBox * 3); l < ((xBox * 3) + 3); l++)
            {
                int[] coord = {k, l};
                if (b[k][l] == num && coord != pos)
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    public int[] findEmpty(int[][] b)
    {
        for (int i = 0; i < b.length; i++)
        {
            for (int j = 0; j < b[i].length; j++)
            {
                if (b[i][j] == 0)
                {
                    int[] point = {i, j};
                    return point;
                }
            }
        }
        return null;
    }
    
    public boolean solve(int[][] b)
    {
        int[] found = findEmpty(b);
        if (found == null)
        {
            return true;
        }
        
        for (int i = 1; i < 10; i++)
        {
            if (valid(b, i, found))
            {
                b[found[0]][found[1]] = i;
                
                if (solve(b))
                {
                    return true;
                }
                
                b[found[0]][found[1]] = 0;
            }
        }
        return false;
        
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

    public int[][] generateNewGame()
    {
        ArrayList<Integer> num = new ArrayList<>();
        for (int j = 1; j <= 9; j++)
        {
            num.add(j);            
        }
                                        
        for (int i = 0; i < 17; i++)
        {
            if (board[num.get(0) - 1][num.get(1) - 1] != 0)
            {
                board[num.get(0) - 1][num.get(1) - 1] = num.get(2);
            }
            Collections.shuffle(num);
        }
        
        while (!checkValid(board))
        {
            for (int i = 0; i < 17; i++)
            {
                if (board[num.get(0) - 1][num.get(1) - 1] != 0)
                {
                    board[num.get(0) - 1][num.get(1) - 1] = num.get(2);
                }
                Collections.shuffle(num);
            }
        }
        
        return board;
    }
    
    public boolean checkValid(int[][] board)
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                int point1 = board[i][j];
                
                for (int k = 0; k < 9; k++)
                {
                    if (board[i][k] == point1 && j != k)
                    {
                        return false;
                    }
                    else if (board[k][j] == point1 && i != k)
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
}
