import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class FractalPanel extends JPanel{
    
    //Instance Variables    
    FractalSet set;
    int pixels[][];
    int width, height;
    BufferedImage fractalImage;
    Graphics2D fractalGraphics;
    boolean dragging; //Flag for drawing zoom selection box
    Point start, end; //Begining and end of selection box
    int minX, minY, maxX, maxY; //Constraints of selection box
   
    
    /**
     * Default constructor for FractalPanel
     * Sets the set to draw and its dimensions
     * @param set The set that the panel will display by default
     */
    public FractalPanel(FractalSet set){
        //Calling the panels constructor with true enables double buffering
        super(true);
        this.set = set;
        this.width = set.getWidth();
        this.height = set.getHeight();
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));  
            
        fractalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        fractalGraphics = fractalImage.createGraphics();
        
        updatePixels();   
        dragging = false;
    }

    /**
     * Provides public access to the set
     * @return The set represented by the panel
     */
    public FractalSet getSet(){
        return set;
    }

    /**
     * Provides public setting of the set
     * @param set The set to set the set to
     */
    public void setSet(FractalSet set){
       //Maybe I shouldn't have called sets sets... :p
        this.set = set;
    }  

    /**
     * Calculates the pixels for the set and draws them to a bufferedImage
     * Which in turn is drawn to the screen
     */
    public void updatePixels(){
        set.calculate();
        pixels = set.getPixels();
       
        //Loops through all the pixels, if it reaches the iteration maximum, draw it black
        //Otherwise it is drawn with a hue of teh ratio of it's iterations to the maximum 
        if(pixels != null){
            for (int x = 0; x < width; x++){
                 for (int y = 0; y < height; y++){
                     if(pixels[x][y] == set.getIterations()) fractalImage.setRGB(x,y,0);
                     else{
                          fractalImage.setRGB(
                             x, y, Color.HSBtoRGB(
                                     (float)pixels[x][y] / (float)set.getIterations(),
                                     (float)1, 
                                     (float)1 ));
                     }
                 }
            }
        }
        
        repaint();

    }

    /**
     * Calculates the Minimums and maximums of a box 
     * for zooming or drawing the box
     * Accounts for the fact a user could drag upwards
     */
    private void calculateBox(){
         if (!start.equals(end)){
            if(start.x > end.x){
                maxX = start.x;
                minX = end.x;
            }
            else {
                maxX = end.x;
                minX = start.x;
            }
            if(start.y > end.y){
                maxY = start.y;
                minY = end.y;
            }
            else {
                maxY = end.y;
                minY = start.y;
            }
        }
    }

    /**
     * Changes the limits of the fractal such that the image is zoomed to those constraints
     */
    public void zoom(){
            calculateBox();
            
            //Finds the complex numbers that represent the square
            Complex minC = set.complexAtPixel(minX, minY);
            Complex maxC = set.complexAtPixel(maxX, maxY);

            set.setLimits(minC, maxC);
            updatePixels();
        
    }

    /**
     * Draws a white box where the user has dragged
     * Must be called from paint
     */
    protected void drawBox(Graphics g){
        g.setColor(new Color(255,255,255)); //White
        calculateBox();
        g.drawRect(minX, minY , maxX - minX, maxY - minY);
    }

    /** 
     * Called when the user presses down the mouse button
     * Sets the box to the current pixel
     * @param start The current position of the mouse
     */
    public void startDrag(Point start){
        this.start = start;
        this.end = start;
    }

    /**
     * Called when the user releases the mouse button
     * Sets the end of the box to the current location
     * If needed calls zoom
     * @param end The current position of the mouse
     */
    public void stopDrag(Point end){
        dragging = false;
        this.end = end;
        if(!start.equals(end))  zoom();
        repaint();
    }

    /**
     * Called everytime the mouse changes position and the button is pressed
     * Sets dragging to true, sets the end to the current point
     * @param current The current point of the mouse
     */
    public void moveMouse(Point current){
        dragging = true;
        this.end = current;
        repaint();
    }

    /**
     * Called when the screen needs to be repainted
     * Draws the fractal, and if needed a selection box
     */
    public void  paint(Graphics g){
        super.paint(g);
        g.drawImage((fractalImage),0,0,this);
        if(dragging) drawBox(g);
    }

}
