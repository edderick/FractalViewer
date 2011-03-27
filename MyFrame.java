import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JTextField;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Graphics;
 
/*
 *A Class that is used to create a new frame
 */
public class MyFrame extends JFrame{
    //Mandelbrot Panel Julia Set Panel
    FractalPanel mbPanel, jsPanel;
    FractalSet mandelbrotSet, juliaSet;
    JTextField mbRMinField, mbRMaxField, mbIMinField, mbIMaxField,
    mbIterationsField;
    JLabel mbRMinLabel, mbRMaxLabel, mbIMinLabel, mbIMaxLabel,
     mbIterationsLabel;

    /*
     *Constructor for myFrame
     *@param title The title displayed in the window border
     */
    public MyFrame(String title){
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
    }

    public void init(){
        //True sets it to double buffer
        mandelbrotSet = new Mandelbrot(500,500);
        mbPanel = new FractalPanel(mandelbrotSet);
        mbPanel.setBackground(Color.yellow);
       
        //jsPanel = new SetPanel(true);
        //jsPanel.setPreferredSize(new Dimension(500,500));
        //jsPanel.setBackground(Color.yellow);
        

        mbRMaxField = new JTextField(5);
        mbRMaxField.setText(Double.toString(mandelbrotSet.getRealMax()));
        mbRMinField = new JTextField(5);
        mbRMinField.setText(Double.toString(mandelbrotSet.getRealMin()));
        mbIMaxField = new JTextField(5);
        mbIMaxField.setText(Double.toString(mandelbrotSet.getImaginaryMax()));
        mbIMinField = new JTextField(5);
        mbIMinField.setText(Double.toString(mandelbrotSet.getImaginaryMin()));
        mbIterationsField = new JTextField(5);
        mbIterationsField.setText(Integer.toString(mandelbrotSet.getIterations()));

        mbRMaxLabel = new JLabel("Real Max:");
        mbRMinLabel = new JLabel("Real Min:");
        mbIMaxLabel = new JLabel("Imaginary Max:");
        mbIMinLabel = new JLabel("Imaginary Min:");
        mbIterationsLabel = new JLabel("Iterations:");
    
       //Closest thing java offers to with statement
        Container cp = getContentPane();   
        cp.add(mbPanel);
        //cp.add(jsPanel);
        cp.add(mbRMaxLabel);
        cp.add(mbRMaxField);
        cp.add(mbRMinLabel);
        cp.add(mbRMinField);
        cp.add(mbIMaxLabel);
        cp.add(mbIMaxField);
        cp.add(mbIMinLabel);
        cp.add(mbIMinField);
        cp.add(mbIterationsLabel);
        cp.add(mbIterationsField);       
         
        
        setLayout(new FlowLayout());
    	setVisible(true);
        setSize(1020,600);
        //pack();

    }

    

}

