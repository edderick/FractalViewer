/**
 * Abstract class representing a fractal set
 */
public abstract class FractalSet{
    
    //Instance variables
    protected Complex min, max, constant;
    protected int iterations, pixelHeight, pixelWidth;
    protected int pixels[][];

    /**
     * The default constructor for FractalSet
     * Sets the max and min to their default value
     * Sets instance variables to teh parameters
     * Calls the parent constructor and calculate method
     * @param width The width of the pixels that are drawn to screen
     * @param height The height of the pixels that are drawn to screen
     * @param constant The constant used in calculating the set
     */
    public FractalSet(int width, int height, Complex constant){
        min = new Complex(-2, -1.6);
        max = new Complex(2, 1.6);
        iterations = 100;
        pixelWidth = width;
        pixelHeight = height;
        this.constant = constant;
    }

    /**
     * Calculates the number of iterations before divergece for each pixel
     * Changes the pixels array
     */
    public void calculate(){
        //The step is the change of the part of the complex number per ixel
        double realStep = (max.getRealPart() - min.getRealPart()) / pixelWidth;
        double imaginaryStep = (max.getImaginaryPart() - min.getImaginaryPart()) / pixelHeight;

        //Calculates divergance of each pixel
        pixels =  new int[pixelWidth][pixelHeight];
        for(int r = 0; r < pixelWidth; r++){
            for(int i = 0; i < pixelHeight; i++){
                double real = min.getRealPart() + (realStep * r);
                double imaginary = min.getImaginaryPart() + (imaginaryStep *i);
                pixels[r][i] = testDiverge(new Complex(real, imaginary));
            }
        }
    }

    
    /** 
     * Sets the limits of the fractal to be drawn
     * @param min The minumum point to be drawn
     * @param max The maximum point to be drawn
     */
    public void setLimits(Complex min, Complex max){
        this.min = min;
        this.max = max;
    }

    /**
     * @return the minimum point to be drawn
     */
    public Complex getMin(){
        return min;
    }

    /**
     *@return the maximum point to be drawn
     */
    public Complex getMax(){
        return max;
    }

    /**
     * Sets the constant to a new value
     * @param c The value to set the constant to
     */
    public void setConstant(Complex c){
        this.constant = c;
    }

    /**
     * Checks if a complex number diverges within the limit of iterations
     * @param c The complex number to be tested
     * @return The amount of iterations before diverging
     */
    protected abstract int testDiverge(Complex c);

    /**
     * @return The constant
     */
    public Complex getConstant(){
        return constant;
    }

   /**
    * @return The width
    */ 
    public int getWidth(){
        return pixelWidth;
    }

    /**
     * @return The height
     */
    public int  getHeight(){
        return pixelHeight;
    }

    /**
     * @return The array of the divergence of the pixels
     */
    public int[][] getPixels(){
        return pixels;
    }

    /**
     * @return The iteration limit
     */   
    public int getIterations(){
        return iterations;
    }

    /**
     * Sets the iteration limit to a new value
     * @param iterations The value to set the iteration limit to
     */
    public void setIterations(int iterations){
        this.iterations = iterations;
    }

    /** 
     * Calculates the complex number represented by a pixel
     * @param x The x coordinate of the pixel
     * @param y The y coordinate of the pixel
     * @return The complex number represented 
     */
    public Complex complexAtPixel(int x, int y){
        double realStep = (max.getRealPart() - min.getRealPart()) / pixelWidth;
        double imaginaryStep = (max.getImaginaryPart() - min.getImaginaryPart()) / pixelHeight;

        return new Complex(min.getRealPart() + (x*realStep), min.getImaginaryPart() + (y*imaginaryStep));

    }   


}
