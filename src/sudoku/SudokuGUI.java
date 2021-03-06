package sudoku;

import java.awt.Color;
// import java.util.Observable;
import cs2.Button;
import cs2.Shape;
import cs2.TextShape;
import cs2.Window;
import cs2.WindowSide;

public class SudokuGUI 
{

    private Window window;
    private Button solve;
    private Button quit;
    private Button newGame; 
    private int[][] board;
    private int[] grid;
    private SudokuSolver sudoku;
    private Generator game;
   
       
    public SudokuGUI()
    {
       sudoku = new SudokuSolver();
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
       
       newGame = new Button("New Game");
       newGame.onClick(this, "clickedNewGame");
       window.addButton(newGame, WindowSide.NORTH);
       
       game = new Generator();
       grid = game.generateGrid();
       game.zeroOut(grid);
       board = sudoku.convert(grid);
       
       
       drawBoard();
    }
    
    public void initButtons()
    {
        solve.enable();
        quit.enable();
        newGame.enable();
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
    
    public void clickedSolve(Button button) 
    {
        solve.disable();
        quit.disable();
        newGame.disable();
        window.removeAllShapes();
        sudoku.solve(board);
        drawBoard();
        initButtons();
        //new Thread()
        //{
        //    public void run()
        //    {
        //        SudokuSolver s = new SudokuSolver();
        //        s.solve(board);
        //    }
        //}.start();
        if (sudoku.solved(board))
        {
            quit.enable();
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
        
        int[][] b = board;
        
        gridW = gridW + 30;
        gridY = gridY + 30;
        
        for (int j = 0; j < board.length; j++)
        {
            for (int k = 0; k < board[j].length; k++)
            {
                String text = ((Integer)b[j][k]).toString();
                TextShape num = new TextShape(gridW + (60*k), gridY + (60*j), text, Color.BLACK);
                window.addShape(num);
            }
        }
    }
    
    public void clickedNewGame(Button button)
    {
        window.removeAllShapes();
        grid = game.generateGrid();
        game.zeroOut(grid);
        sudoku.convert(grid);
        drawBoard();
        
    }
    
    
}
