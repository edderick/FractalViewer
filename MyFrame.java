import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.JTextField;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.Point;
import javax.swing.event.*;
import javax.swing.JComboBox;

/**
 * A Class that is used to create a new frame
 */
public class MyFrame extends JFrame{
    //Instance variables - Mostly swing components
    FractalPanel leftPanel, rightPanel;
    FractalSet mandelbrotSet, juliaSet, burningShipSet;
    JTextField mbRMinField, mbRMaxField, mbIMinField, mbIMaxField,
    iterationsField, selectedRealField, selectedImaginaryField;
    JLabel mbRMinLabel, mbRMaxLabel, mbIMinLabel, mbIMaxLabel,
        mbIterationsLabel, mbRPointClickedLabel, mbIPointClickedLabel,
        selectedRLabel, selectedILabel, fractalTypeLabel, saveLabel;
    JButton applyButton, resetButton, saveButton;
    JComboBox fractalTypeBox, saveBox;
    Container leftContainer, rightContainer, realFieldContainer,
        imaginaryFieldContainer, iterationsFieldContainer, saveContainer,
        limitsButtonContainer, selectedContainer, typeListContainer;

    /** 
     * The main method; Gets called first
     * Simply creates this object
     */
    public static void main(String[] args){
        MyFrame myFrame = new MyFrame("Fractal Viewer");
    }

    /**
     * Constructor for myFrame
     * @param title The title displayed in the window border
     */
    public MyFrame(String title){
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
    }

    /**
     * Called by the constructor
     * Sets up the graphical interface
     */
    public void init(){
        //Call the methods to make the gui            
        createContainers();
        createSets();
        createFractalPanels();
        createFields();       
        createLabels();   
        createButtons();
        addComponents();
        
        setLayout(new GridLayout(1,2));
    	setVisible(true);
        setSize(1020,650);

    }

    /**
     * Creates the containers for the various groups of components
     * Used to keep labels next to their fields etc
     */
    private void createContainers(){
        leftContainer = new JPanel();
        rightContainer = new JPanel();
        realFieldContainer = new JPanel();
        imaginaryFieldContainer = new JPanel();
        iterationsFieldContainer = new JPanel();
        limitsButtonContainer = new JPanel();
        selectedContainer = new JPanel();
        typeListContainer = new JPanel();
        saveContainer = new JPanel();
    }   

    /**
     * Creates the sets that can be displayed
     */
    private void createSets(){
        mandelbrotSet = new Mandelbrot(500,500);
        burningShipSet = new BurningShip(500,500);
        //The complex number 1 - phi has been uses as this was the given example
        juliaSet = new Julia(500, 500, new Complex(-0.61803399, 0));
    }

    /**
     * Creates the panels on which the fractals are drawn
     */
    private void createFractalPanels(){
        leftPanel = new FractalPanel(mandelbrotSet);
        MouseInputListener ml = new MandelbrotMouseListener();
        leftPanel.addMouseListener(ml);
        leftPanel.addMouseMotionListener(ml);
       
        rightPanel = new FractalPanel(juliaSet);
    }

    /**
     * Create the various fields
     */
    private void createFields(){
        mbRMaxField = new JTextField(5);
        mbRMinField = new JTextField(5);
        mbIMaxField = new JTextField(5);
        mbIMinField = new JTextField(5);
        iterationsField = new JTextField(5);
        selectedRealField = new JTextField(5);
        selectedImaginaryField = new JTextField(5);

        fractalTypeBox = new JComboBox();
        fractalTypeBox.addItem(mandelbrotSet);
        fractalTypeBox.addItem(burningShipSet);
        fractalTypeBox.addItemListener(new fractalTypeListener());

        saveBox = new JComboBox();
        saveBox.addItem(rightPanel.getSet().getConstant());
        saveBox.addItemListener(new saveBoxListener());

        //Fields are updated so they contain the values of the fractals
        updateFields();
    } 

    /**
     * Create various labels
     */
    private void createLabels(){
        mbRMaxLabel = new JLabel("Real Max:");
        mbRMinLabel = new JLabel("Real Min:");
        mbIMaxLabel = new JLabel("Imaginary Max:");
        mbIMinLabel = new JLabel("Imaginary Min:");
        mbIterationsLabel = new JLabel("Iterations:");
        mbRPointClickedLabel = new JLabel("Real Point:");
        mbIPointClickedLabel = new JLabel("Imaginary Point:");       
        fractalTypeLabel = new JLabel("Type of set:");
        selectedILabel = new JLabel("Selected Imaginary:");
        selectedRLabel = new JLabel("Selected Real:");
        saveLabel = new JLabel("Saved Julias:");
    }

    /**
     * Create the Apply and Reset Buttons, and associate them with listeners
     * (More buttons could be added)
     */
    private void createButtons(){
        applyButton = new JButton("Apply");
        applyButton.addActionListener(new applyButtonListener());

        resetButton = new JButton("Reset");
        resetButton.addActionListener(new resetButtonListener());
    
        saveButton = new JButton("Save");
        saveButton.addActionListener(new saveButtonListener());
    }

    /**
     * Add all the components to their corresponding containers
     * Then add these containers to more containers etc.
     */
    private void addComponents(){
        leftContainer.add(leftPanel);
        rightContainer.add(rightPanel);
        
        realFieldContainer.add(mbRMaxLabel);
        realFieldContainer.add(mbRMaxField);
        realFieldContainer.add(mbRMinLabel);
        realFieldContainer.add(mbRMinField);
        leftContainer.add(realFieldContainer);
        
        imaginaryFieldContainer.add(mbIMaxLabel);
        imaginaryFieldContainer.add(mbIMaxField);
        imaginaryFieldContainer.add(mbIMinLabel);
        imaginaryFieldContainer.add(mbIMinField);
        leftContainer.add(imaginaryFieldContainer);
        
        iterationsFieldContainer.add(mbIterationsLabel);
        iterationsFieldContainer.add(iterationsField);
        leftContainer.add(iterationsFieldContainer);

        limitsButtonContainer.add(resetButton);       
        limitsButtonContainer.add(applyButton);
        leftContainer.add(limitsButtonContainer);

        selectedContainer.add(selectedRLabel);
        selectedContainer.add(selectedRealField);
        selectedContainer.add(selectedILabel);
        selectedContainer.add(selectedImaginaryField);
        rightContainer.add(selectedContainer);
        
        typeListContainer.add(fractalTypeLabel);
        typeListContainer.add(fractalTypeBox);
        rightContainer.add(typeListContainer);
        
        saveContainer.add(saveLabel);
        saveContainer.add(saveBox);
        saveContainer.add(saveButton);
        rightContainer.add(saveContainer);

        getContentPane().add(leftContainer);
        getContentPane().add(rightContainer);
    }


    /**
     * Updates the fields on screen to display the correct information about the sets
     */
    private void updateFields(){
        iterationsField.setText(Integer.toString(leftPanel.getSet().getIterations()));
        mbIMinField.setText(Double.toString(leftPanel.getSet().getMin().getImaginaryPart()));
        mbIMaxField.setText(Double.toString(leftPanel.getSet().getMax().getImaginaryPart()));
        mbRMinField.setText(Double.toString(leftPanel.getSet().getMin().getRealPart()));
        mbRMaxField.setText(Double.toString(leftPanel.getSet().getMax().getRealPart()));
        mbIMinField.setText(Double.toString(leftPanel.getSet().getMin().getImaginaryPart()));
        iterationsField.setText(Integer.toString(leftPanel.getSet().getIterations()));
        selectedRealField.setText(Double.toString(rightPanel.getSet().getConstant().getRealPart()));
        selectedImaginaryField.setText(Double.toString(rightPanel.getSet().getConstant().getImaginaryPart()));
    }

    /**
     * Action Listener for the apply button
     */
    private class applyButtonListener implements ActionListener{
        
        /**
         * Called when the apply button is pressed
         * Sets the limits etc of the mandelbrot and juliasets to the values in the fields
         */
        public void actionPerformed(ActionEvent e){

            leftPanel.getSet().setLimits(
                new Complex(Double.parseDouble(mbRMinField.getText()),
                            Double.parseDouble(mbIMinField.getText())),
                new Complex(Double.parseDouble(mbRMaxField.getText()), 
                            Double.parseDouble(mbIMaxField.getText())));

            leftPanel.getSet().setIterations(Integer.decode(iterationsField.getText()));
            leftPanel.updatePixels();
       
            rightPanel.getSet().setIterations(Integer.decode(iterationsField.getText()));
            rightPanel.updatePixels();
             
        }

    }

     /**
     * Action Listener for the reset button
     */
    private class resetButtonListener implements ActionListener{
        
        /**
         * Called when the reset button is pressed
         * Sets the limits etc of the left panels fractal to the defualts
         */
        public void actionPerformed(ActionEvent e){
            leftPanel.getSet().setLimits(new Complex(-2, -1.6), new Complex(2, 1.6));
            updateFields();
            leftPanel.updatePixels();
        }
    }

     /**
     * Action Listener for the save button
     */
    private class saveButtonListener implements ActionListener{
        
        /**
         * Called when the reset button is pressed
         * Adds the current constant to the combobox
         */
        public void actionPerformed(ActionEvent e){
            saveBox.addItem(rightPanel.getSet().getConstant());
            saveBox.setSelectedItem(rightPanel.getSet().getConstant());
        }
    }

    /**
     * Mouse Listener for the mandelbrot panel
     */
    private class MandelbrotMouseListener extends MouseInputAdapter{

        Point start, end;
        
        /**
         * Called when the user clickes on the mandelbrot panel
         * Sets the fields to this value
         * Recalculates and draws the julia set to use this constant
         */
        public void mouseClicked(MouseEvent e){

            Point p = e.getPoint();

            Complex pointClicked = leftPanel.getSet().complexAtPixel(p.x, p.y);

            rightPanel.getSet().setConstant(pointClicked);
            rightPanel.updatePixels();

            updateFields(); 

        }

        /**
         * Called when the Mouse button is pressed down
         * Sets the start point of the drag, and tells the left panel
         * that the use is dragging
         */
        public void mousePressed(MouseEvent e){
            start = e.getPoint();
            leftPanel.startDrag(start);  
        }

        /**
         * Called when the mouse is moved and the button is down
         * Tells the left panel where the mouse has been dragged to
         */
        public void mouseDragged(MouseEvent e){
            leftPanel.moveMouse(e.getPoint());
        }
          
        /**
         * Called when the mouse button is released
         * Tells the fractal panel where the mouse was dragged to
         */    
        public void mouseReleased(MouseEvent e){
            leftPanel.stopDrag(e.getPoint());
            updateFields();   
            
        }
    }

    
    /**
     * Listener for fractal type combo box
     */
    private class fractalTypeListener implements ItemListener{
    
        /**
         * Called when the user choses a new fractal from the list
         * Changes the fractal to the users choice
         */    
        public void itemStateChanged(ItemEvent e){
            leftPanel.setSet((FractalSet)e.getItem());
            leftPanel.updatePixels();
            updateFields();
        }
    }

    
    /**
     * Listener for saved julia sets combo box
     */
    private class saveBoxListener implements ItemListener{
    
        /**
         * Called when the user choses a new fractal from the list
         * Changes the julia set to the users choice
         */    
        public void itemStateChanged(ItemEvent e){
            rightPanel.getSet().setConstant((Complex)e.getItem());
            rightPanel.updatePixels();
            updateFields();
        }
    }
}
