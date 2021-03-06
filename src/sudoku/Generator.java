package sudoku;
import java.util.*;

public class Generator 
{

    private int[] grid;
    
    public int[] generateGrid()
    {
        ArrayList<Integer> numsArray = new ArrayList<>(9);
        grid = new int[81];
        for (int i = 1; i <= 9; i++) numsArray.add(i);
        
        for (int j = 0; j < 81; j++)
        {
            if (j % 9 == 0) Collections.shuffle(numsArray);
            int boxPos = ((j / 3) % 3) * 9 + ((j % 27) / 9) * 3 + (j / 27) * 27 + (j % 3);
            grid[boxPos] = numsArray.get(j % 9);  
        }
        
        boolean[] sorted = new boolean[81];
        
        for (int k = 0; k < 9; k++)
        {
            boolean backtrack = false;
            
            for (int l = 0; l < 2; l++)
            {
                boolean[] registered = new boolean[10];
                int rowOrigin = k * 9;
                int colOrigin = k;
            
            
                ROW_COL: for (int m = 0; m < 9; m++)
                {
                
                    int step = (l % 2 == 0 ? rowOrigin + m: colOrigin + (m * 9));
                    int num = grid[step];
                    
                    if (!registered[num]) registered[num] = true;
                    else 
                    {
                        for (int n = m; n >= 0; n--)
                        {
                            int scan = (l % 2 == 0 ? k * 9 + n: k + 9 * n);
                            if (grid[scan] == num)
                            {
                                for (int o = (l % 2 == 0 ? (k % 3 + 1) * 3 : 0); o < 9; o++)
                                {
                                    if (l % 2 == 1 && o % 3 <= k % 3) continue;
                                    int boxOrigin = ((scan % 9) / 3) * 3 + (scan / 27) * 27;
                                    int boxStep = boxOrigin + (o / 3) * 9 + (o % 3);
                                    int boxNum = grid[boxStep];
                                    if ((!sorted[scan] && !sorted[boxStep] && !registered[boxNum]) 
                                        || (sorted[scan] && !registered[boxNum] && 
                                            (l % 2 == 0 ? boxStep % 9 == scan % 9: boxStep / 9 == scan / 9)))
                                    {
                                        grid[scan] = boxNum;
                                        grid[boxStep] = num;
                                        registered[boxNum] = true;
                                        continue ROW_COL;
                                    }
                                    else if (o == 8)
                                    {
                                        int searchingNo = num;
                                        boolean[] blindSwapIndex = new boolean[81];
                                        
                                        // Failsafe
                                        for (int p = 0; p < 18; p++)
                                        {
                                            SWAP : for (int q = 0; q <= m; q++)
                                            {
                                                int pacing = (l % 2 == 0 ? rowOrigin + q: colOrigin + q * 9);
                                                if (grid[pacing] == searchingNo)
                                                {
                                                    int adjacentCell = -1;
                                                    int adjacentNo = -1;
                                                    int decrement = (l % 2 == 0 ? 9 : 1);
                                                    
                                                    for (int r = 1; r < 3 - (k % 3); r++)
                                                    {
                                                        adjacentCell = pacing + (l % 2 == 0 ? (r + 1) * 9 : r + 1);
                                                        
                                                        if ((l % 2 == 0 && adjacentCell >= 81) ||
                                                            (l % 2 == 1 && adjacentCell % 9 == 0))
                                                            adjacentCell -= decrement;
                                                        else 
                                                        {
                                                            adjacentNo = grid[adjacentCell];
                                                            if (k % 3 != 0 || r != 1 || blindSwapIndex[adjacentCell]
                                                                || registered[adjacentNo])
                                                                adjacentCell -= decrement;
                                                        }
                                                        adjacentNo = grid[adjacentCell];
                                                        
                                                        if (!blindSwapIndex[adjacentCell])
                                                        {
                                                            blindSwapIndex[adjacentCell] = true;
                                                            grid[pacing] = adjacentNo;
                                                            grid[adjacentCell] = searchingNo;
                                                            searchingNo = adjacentNo;
                                                            
                                                            if (!registered[adjacentNo])
                                                            {
                                                                registered[adjacentNo] = true;
                                                                continue ROW_COL;
                                                            }
                                                            break SWAP;
                                                        }
                                                    }
                                                }       
                                            }
                                        }
                                        
                                        backtrack = true;
                                        break ROW_COL;
                                    } 
                                }
                            }
                        }
                    }
                } 
                
                if (l % 2 == 0)
                    for (int s = 0; s < 9; s++) sorted[k * 9 + s] = true;
                else if (!backtrack)
                    for (int t = 0; t < 9; t++) sorted[k + t * 9] = true;
                else
                {
                    backtrack = false;
                    for (int u = 0; u < 9; u++) sorted[k * 9 + u] = false;
                    for (int v = 0; v < 9; v++) sorted[(k - 1) * 9 + v] = false;
                    for (int w = 0; w < 9; w++) sorted[k - 1 + w * 9] = false;
                    k -= 2;
                }
            }
        }
        
        if (!isPerfect(grid)) throw new RuntimeException("Error : Imperfect Grid Generated.");
              
        return grid;         
    }
       
    //public void print(int[] grid)
    //{
    //    if (grid.length != 81) throw new IllegalArgumentException("The grid must be a single dimensional grid of length 81"); 
    //    for (int i = 0; i < 81; i++) System.out.print("[" + grid[i] + "] " + ((i+1)% 9 == 0 && i > 0 ? "\n" : ""));      
    //}
    
    public void print(int[] grid)
    {
        if (grid.length != 81) throw new IllegalArgumentException("The grid must be a single dimensional grid of length 81");
        StringBuilder s = new StringBuilder();
        
        for (int i = 0; i < 81; i++)
        {
            if (i % 3 == 0 && i != 0 && (i - 1) % 9 != 8)
            {
                s.append("| ");
            }
            
            if (i % 27 == 0 && i > 0)
            {
                s.append("- - - - - - - - - - -\n");
            }
            
            if (i % 9 == 8)
            {
                s.append(grid[i] + "\n");
            }
            else
            {
                s.append(grid[i] + " ");
            }
        }
        System.out.print(s.toString() + "\n\n\n");
    }
    
    public void zeroOut(int[] grid)
    {
        int runtime = (int) (Math.random() * (81 - 17));
        
        for (int j = 0; j < runtime; j++)
        {
            int random = (int) (Math.random() * 81);
            
            while (grid[random] == 0)
            {
                random = (int) (Math.random() * 81);
            }
            
            grid[random] = 0;
        }
    }
    
    public boolean isPerfect(int[] grid)
    {
        if (grid.length != 81) throw new IllegalArgumentException("The grid must be a single dimensional grid of length 81"); 
        for (int i = 0; i < 9; i++)
        {
            boolean[] registered = new boolean[10];
            registered[0] = true;
            int boxOrigin = (i * 3) % 9 + ((i * 3) / 9) * 27;
            
            for (int j = 0; j < 9; j++)
            {
                int boxStep = boxOrigin + (j / 3) * 9 + (j % 3);
                int boxNum = grid[boxStep];
                registered[boxNum] = true;
            }
            
            for (boolean b: registered) if (!b) return false;                          
        }
        
        for (int i = 0; i < 9; i++)
        {
            boolean[] registered = new boolean[10];
            registered[0] = true;
            int rowOrigin = i * 9;
            
            for (int j = 0; j < 9; j++)
            {
                int rowStep = rowOrigin + j;
                int rowNum = grid[rowStep];
                registered[rowNum] = true;
            }
            
            for (boolean b: registered) if (!b) return false;
        }
        
        for (int i = 0; i < 9; i++)
        {
            boolean[] registered = new boolean[10];
            registered[0] = true;
            int colOrigin = i * 9;
            
            for (int j = 0; j < 9; j++)
            {
                int colStep = colOrigin + j;
                int colNum = grid[colStep];
                registered[colNum] = true;
            }
            
            for (boolean b: registered) if (!b) return false;
        }
        return true;
    }
      
}
