import java.lang.Math;

public class Complex{
    
    //The real and imaginary parts of the complex number
    double realPart, imaginaryPart;

    /**
     * The deafault constructor for a complex number
     * @param real The real part 
     * @param imaginary The imaginary part
     */
    public Complex(double real, double imaginary){
        this.realPart = real;
        this.imaginaryPart = imaginary;
    }

    /**
    *@return The real part of the complex number
    */
    public double getRealPart(){
        return realPart;
    }

    /**
    *@return The imaginary part of the complex number
    */
    public double getImaginaryPart(){
        return imaginaryPart;
    }

    /**
    *Allows changing of the real part
    *@param num The new value of the real part
    */
    public void setRealPart(double num){
        this.realPart = num;
    }
   
   /**
   *Allows changing of the imaginary part
   *@param num The new value of the imaginary part
   */ 
    public void setImaginaryPart(double num){
        this.imaginaryPart = num;
    }

    /**
    *Squares the complex number
    */
    public void square(){
        //I have used the equation from wikipeida
        //(a+bi)(c+di)=(ac-bd)=(bc+ad)i
        //This simplifies to
        //(a+bi)=(aa-bb)+(2ba)i
        double tempReal = (realPart * realPart) - (imaginaryPart * imaginaryPart);
        double tempImaginary = 2 * realPart * imaginaryPart;
        
        realPart = tempReal;
        imaginaryPart = tempImaginary;
    }

    /**
    *@return The square of the modulus of the complex number
    */
    public double modulusSquared(){
        return realPart * realPart + imaginaryPart * imaginaryPart;
    }

    /**
    *Adds annother complex number to this
    *@param d The compelx number to be added
    */
    public void add(Complex d){
        realPart += d.getRealPart();
        imaginaryPart += d.getImaginaryPart();
    }

    /**
     * Uses mainly in Burning ship fractal
     * Calculates the absolute value of both parts
     */
    public void modulusParts(){
        if (imaginaryPart < 0) imaginaryPart = 0 - imaginaryPart;
        if (realPart < 0) realPart = 0 - realPart;
    }

    public String toString(){
        return Double.toString(realPart) + " + " +  Double.toString(imaginaryPart) + "i";
    }
}
