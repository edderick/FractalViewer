MyFrame.class: MyFrame.java FractalPanel.class
	javac MyFrame.java
Complex.class: Complex.java
	javac Complex.java
Mandelbrot.class: Mandelbrot.java Complex.class FractalSet.class
	javac Mandelbrot.java
Julia.class: Julia.java Complex.class FractalSet.class
	javac Julia.java
BurningShip.class: BurningShip.java Complex.class FractalSet.class
	javac BurningShip.java
FractalSet.class: FractalSet.java Complex.class 
	javac FractalSet.java
FractalPanel.class: FractalPanel.java FractalSet.class Mandelbrot.class Julia.class BurningShip.class
	javac FractalPanel.java

clean: 
	rm *.class
