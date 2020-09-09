package display;

import java.awt.Canvas;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.WindowEvent;

public class Display {

    private JFrame frame;
    private Canvas canvas;

    private String title;
    private int width, height;
    private Dimension dimension;

    //CONSTRUCTOR
    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        createDisplay();
    }

    /* creating and setting up the frame canvas */
    private void createDisplay() {
        dimension = new Dimension(width, height);

        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(dimension);
        canvas.setMaximumSize(dimension);
        canvas.setMinimumSize(dimension);
        canvas.setFocusable(false);

        frame.add(canvas);
        frame.pack();
    }

    public void close() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    //GETTERS SETTERS
    public Canvas getCanvas() {
        return canvas;
    }

    public JFrame getFrame() {
        return frame;
    }
}