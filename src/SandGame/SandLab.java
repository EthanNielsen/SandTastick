import java.awt.*;
import java.util.*;

public class SandLab
{
  //Step 4,6
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int SAND = 2;
  public static final int WATER = 3;
  
  public static final int LEFT = 1;
  public static final int RIGHT = 2;
  public static final int DOWN = 3;
  
  //do not add any more fields below
  private int[][] grid;
  private SandDisplay display;
  
  
  /**
   * Constructor for SandLab
   * @param numRows The number of rows to start with
   * @param numCols The number of columns to start with;
   */
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    // Change this value to add more buttons
    //Step 4,6
    names = new String[4];
    // Each value needs a name for the button
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    
    //1. Add code to initialize the data member grid with same dimensions
    grid = new int[numRows][numCols];
    
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  {
    //2. Assign the values associated with the parameters to the grid
   grid[row][col] = tool;
  }

  //copies each element of grid into the display
  public void updateDisplay()
  {
      //Step 3
   //Hint - use a nested for loop
    for(int x = 0; x < display.getNumRows(); x++) {
    	for(int y = 0; y < display.getNumCols(); y++) {
    		if(grid[x][y] == EMPTY ) {
    			display.setColor(x, y, Color.BLACK);
    		} else if (grid[x][y] == METAL) {
    			display.setColor(x, y, Color.GRAY);
    		} else if (grid[x][y] == SAND) {
    			display.setColor(x, y, Color.YELLOW);
    		} else if (grid[x][y] == WATER) {
    			display.setColor(x, y, Color.BLUE);
    		}
    	}
    }
  }

  //Step 5,7
  //called repeatedly.
  //causes one random particle in grid to maybe do something.
  public void step()
  {
    //Remember, you need to access both row and column to specify a spot in the array
    //The scalar refers to how big the value could be
    //int someRandom = (int) (Math.random() * scalar)
    //remember that you need to watch for the edges of the array
    int randomRow = (int) (Math.random() * display.getNumRows());
    int randomCol = (int) (Math.random() * display.getNumCols());
    
    if(grid[randomRow][randomCol] == SAND) {
    	if(randomRow + 1 < display.getNumRows()) {
    		if(grid[randomRow+1][randomCol] == EMPTY || grid[randomRow+1][randomCol] == WATER) {
    			grid[randomRow][randomCol] = EMPTY;
    			grid[randomRow+1][randomCol] = SAND;
    			
    		}
    	}
    }
	  
	if(grid[randomRow][randomCol] == WATER) {
		int randomDirection = (int) (Math.random() * 4);
		
		if(randomDirection == LEFT) {
			if(randomCol-1 >= 0) {
				if(grid[randomRow][randomCol-1] == EMPTY) {
					grid[randomRow][randomCol-1] = WATER;
				}
			}
		}
		
		if(randomDirection == RIGHT) {
			if(randomCol+1 < display.getNumCols()) {
				if(grid[randomRow][randomCol+1] == EMPTY) {
					grid[randomRow][randomCol+1] = WATER;
				}
			}
		}
		
		if(randomDirection == DOWN) {
			if(randomRow + 1 < display.getNumRows()) {
	    		if(grid[randomRow+1][randomCol] == EMPTY) {
	    			grid[randomRow+1][randomCol] = WATER;
	    		}
	    	}
		}
		
		
	}
    
  }
  
  //do not modify this method!
  public void run()
  {
    while (true) // infinite loop
    {
      for (int i = 0; i < display.getSpeed(); i++)
      {
        step();
      }
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
      {
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
      }
    }
  }
}
