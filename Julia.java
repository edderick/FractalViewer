import javax.swing.*;
import java.awt.*;

/**
 * This class represents a Julia Set
 * Calculations for pixels to be displayed are performed by this class
 */
public class Julia extends FractalSet{
    
    /**
     * Default constructor for a Julia Set
     * Calls the parent constructor and calculate method
     * @param width The width of the pixels that are drawn to screen
     * @param height The height of the pixels that are drawn to screen
     * @param constant The constant used in calculating the set
     */
    public Julia(int width, int height, Complex constant){
        super(width, height, constant);
        calculate();
    }

    /**
     * Checks if a complex number diverges within the limit of iterations
     * @param c The complex number to be tested
     * @return The amount of iterations before diverging
     */
    public int testDiverge(Complex c){
       //Clone inputted complex number     
       Complex z = new Complex(c.getRealPart(), c.getImaginaryPart());
        int i = 0;
                
        //If the modulus of z is less than 2, and the iteration limit has not been exeded     
        while((z.modulusSquared() < 4) && (i < iterations)){
            i++;
            z.square();
            z.add(constant);
        }
        return i;
    }

    /** 
     * @return The name of this kind of fractal
     */
    public String toString(){
        return "Julia";
    }

}
