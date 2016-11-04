
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Lab4 extends JFrame {

    public Lab4(){
        super("Lab 4");
        ScreenSaver saver = new ScreenSaver();
        setLayout(new BorderLayout());
        add(saver, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        Lab4 lab4 = new Lab4();
        lab4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        lab4.setSize(600, 600);
        lab4.setResizable(true);
        lab4.setVisible(true);
    }
}

class ScreenSaver extends JPanel implements ActionListener, MouseWheelListener {

    private int x[] = new int[60]; //Holds array of x coordinates
    private int y[] = new int[60]; //Holds array of y coordinates
    private int numOfPoints = 0; //number of points
    private int radius = 10; //radius
    private int delay = 1000; //one line per second
    private final int MIN_DELAY = 100; //10 lines per second;
    private final int MAX_DELAY = 2000; //1 line every 2 seconds;
    private Timer timer = null; //initialize timer object

    //Creates the timer, sets the initial delay, and starts the timer
    public ScreenSaver(){
        timer = new Timer(delay, this);
        timer.start();

    }

    //Mouse Wheel Listener added to sense mouse wheel events
    //addAPoint(); method to add points and reset x,y arrays and handle radius and numOfPoints calculations
    //timer delay is updated
    //update graphics repainted to screen
    @Override
    public void actionPerformed(ActionEvent e) {
        addMouseWheelListener(this);
        addAPoint();
        timer.setDelay(delay);
        repaint();
    }

    //Paints components to window
    //g.clearRect(...) clears out window
    //polyline is drawn using information from the x[], y[], and numOfPoints with a color of black
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.clearRect(0, 0, (int)getSize().getWidth(), (int)getSize().getHeight());

        g.setColor(Color.BLACK);
        g.drawPolyline(x, y, numOfPoints);

    }

    //Handle for mouse wheel event. If the user scrolls up delay decreases, but only to a MIN_DELAY
    //If the user scrolls down delay increases, but only to a MAX_DELAY
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        //Spinning the mouse up increases speed
        if(e.getWheelRotation() == -1 && delay > MIN_DELAY){
            delay = delay - 1;
        }

        if(e.getWheelRotation() == 1 && delay < MAX_DELAY){
            delay = delay + 1;
        }
    }

    //Adds new x,y coordinates to the polyline coordinate indexes for x,y
    public void addAPoint(){
        int centerX = (int)getSize().getWidth()/2;
        int centerY = (int)getSize().getHeight()/2;
        double x = centerX + Math.cos(numOfPoints * Math.PI/3) * radius;
        double y = centerY + Math.sin(numOfPoints * Math.PI/3) * radius;

        this.x[numOfPoints] = (int)x;
        this.y[numOfPoints] = (int)y;
        numOfPoints++;
        radius += 3;

        if(numOfPoints == 60){
            numOfPoints = 0;
            radius = 10;
        }
    }

}

