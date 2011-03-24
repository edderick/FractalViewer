import javax.swing.JFrame;

/*
 *A Class that is used to create a new frame
 */
public class MyFrame extends JFrame{

    /*
     *Constructor for myFrame
     *@param title The title displayed in the window border
     */
    public MyFrame(String title){
        super(title);
        init();
    }

    public void init(){
        setVisible(true);
        setSize(100,100);
    }
}

