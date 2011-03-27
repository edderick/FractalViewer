import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.JTextField;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.Point;
 
/*
 *A Class that is used to create a new frame
 */
public class MyFrame extends JFrame{
    //Mandelbrot Panel Julia Set Panel
    FractalPanel mbPanel, jsPanel;
    FractalSet mandelbrotSet, juliaSet;
    JTextField mbRMinField, mbRMaxField, mbIMinField, mbIMaxField,
    mbIterationsField, mbRPointClickedField, mbIPointClickedField;
    JLabel mbRMinLabel, mbRMaxLabel, mbIMinLabel, mbIMaxLabel,
     mbIterationsLabel, mbRPointClickedLabel, mbIPointClickedLabel;
    JButton applyButton;

    public static void main(String[] args){

        MyFrame myFrame = new MyFrame("Fractal Viewer");

    }

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
        mbPanel.addMouseListener(new MandelbrotMouseListener());
       
        juliaSet = new Julia(500, 500, new Complex(-0.61803399, 0));
        jsPanel = new FractalPanel(juliaSet);

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
        mbRPointClickedField = new JTextField(5);
        mbRPointClickedField.setText("-0.61803399");
        mbIPointClickedField = new JTextField(5);
        mbIPointClickedField.setText("0");

        mbRMaxLabel = new JLabel("Real Max:");
        mbRMinLabel = new JLabel("Real Min:");
        mbIMaxLabel = new JLabel("Imaginary Max:");
        mbIMinLabel = new JLabel("Imaginary Min:");
        mbIterationsLabel = new JLabel("Iterations:");
        mbRPointClickedLabel = new JLabel("Real Point:");
        mbIPointClickedLabel = new JLabel("Imaginary Point:");       


        applyButton = new JButton("Apply");
        applyButton.addActionListener(new applyButtonListener());

       //Closest thing java offers to with statement
        Container cp = getContentPane();   
        cp.add(mbPanel);
        cp.add(jsPanel);
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
        cp.add(applyButton);
        cp.add(mbIPointClickedField);
        cp.add(mbRPointClickedField);


        setLayout(new FlowLayout());
    	setVisible(true);
        setSize(1020,600);
        //pack();

    }

    private class applyButtonListener implements ActionListener{

        public void actionPerformed(ActionEvent e){

            mandelbrotSet.setLimits(
                new Complex(Double.parseDouble(mbRMinField.getText()),
                            Double.parseDouble(mbIMinField.getText())),
                new Complex(Double.parseDouble(mbRMaxField.getText()), 
                            Double.parseDouble(mbIMaxField.getText())));

            mandelbrotSet.setIterations(Integer.decode(mbIterationsField.getText()));
            mandelbrotSet.calculate();
            mbPanel.updatePixels();
       
            juliaSet.setIterations(Integer.decode(mbIterationsField.getText()));
            jsPanel.updatePixels();
             
        }

    }
    
    private class MandelbrotMouseListener extends MouseAdapter{

        public void mouseClicked(MouseEvent e){

            Point p = e.getPoint();

            Complex pointClicked = ((FractalPanel)e.getSource()).set.complexAtPixel(p.x, p.y);

            mbRPointClickedField.setText(Double.toString(pointClicked.getRealPart()));
            mbIPointClickedField.setText(Double.toString(pointClicked.getImaginaryPart()));

            juliaSet.setConstant(pointClicked);
            juliaSet.calculate();
            jsPanel.updatePixels();


        }


    }

}

