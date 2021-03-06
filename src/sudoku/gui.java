package sudoku;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;
import cs2.Button;
import cs2.Shape;
import cs2.TextShape;
import cs2.Window;
import cs2.WindowSide;

public class gui implements Observer
{
    
    private Window window;
    private Button solve;
    private Button quit;
    private Board board;
    private solver sudoku;
   
       
    public gui(Board b)
    {
       sudoku = new solver(b.getBoard());
       //this.sudoku.addObserver(this);
        
       window = new Window();
       window.setSize(800, 800);
       window.setTitle("Sudoku");
       
       solve = new Button("Solve");
       solve.onClick(this, "clickedSolve");
       window.addButton(solve, WindowSide.NORTH);
       
       quit = new Button("Quit");
       quit.onClick(this, "clickedQuit");
       window.addButton(quit, WindowSide.NORTH); 
       
       board = new Board();
       
       drawBoard();
    }
    
    public void initButtons()
    {
        solve.enable();
        quit.enable();
    }
    
    public void clickedSolve(Button button) 
    {
        solve.disable();
        quit.disable();
        new Thread()
        {
            public void run()
            {
                solver s = new solver(board.getBoard());
                s.solve(board.getBoard());
            }
        }.start();
        if (board.solved())
        {
            quit.enable();
        }
    }
    
    private void sleep()
    {
        try
        {
            Thread.sleep(10);
        }
        catch (Exception e)
        {
            
        }
    }
    
    
    public void clickedQuit(Button button)
    {
        System.exit(0);
    }
    
    public void drawBoard()
    {
        int x = window.getGraphPanelWidth();
        int y = window.getGraphPanelHeight();
        int gridW = x / 8;
        int gridY = y / 8;
        
        Shape leftSide = new Shape(gridW, gridY, 4, 540, Color.BLACK);
        window.addShape(leftSide);
        
        Shape topSide = new Shape(gridW, gridY, 540, 4, Color.BLACK);
        window.addShape(topSide);
        
        Shape rightSide = new Shape(gridW + 540, gridY, 4, 540, Color.BLACK);
        window.addShape(rightSide);
        
        Shape bottomSide = new Shape(gridW, gridY + 540, 544, 4, Color.BLACK);
        window.addShape(bottomSide);
        
        for (int i = 1; i < 9; i++)
        {
            int bold = 0;
            if (i % 3 == 0)
            {
                bold = 2;
            }
            else
            {
                bold = 0;
            }
            
            Shape gridLineW = new Shape(gridW, gridY + (60*i), 540, 2 + bold, Color.BLACK);
            window.addShape(gridLineW);
            
            Shape gridLineH = new Shape(gridW + (60*i), gridY, 2 + bold, 540, Color.BLACK);
            window.addShape(gridLineH);                         
        }  
        
        int[][] b = board.getBoard();
        
        gridW = gridW + 30;
        gridY = gridY + 30;
        
        for (int j = 0; j < board.rows(); j++)
        {
            for (int k = 0; k < board.cols(); k++)
            {
                String text = ((Integer)b[j][k]).toString();
                TextShape num = new TextShape(gridW + (60*k), gridY + (60*j), text, Color.BLACK);
                window.addShape(num);
            }
        }
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        // TODO Auto-generated method stub
        
    }
    
    
    
}
