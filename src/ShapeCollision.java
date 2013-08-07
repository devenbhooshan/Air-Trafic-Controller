import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

class ShapeCollision {

    private BufferedImage img;
    private Area[] obstacles = new Area[4];
    private Area walls;

    int x; 
    int y;
    int xDelta = 3;
    int yDelta = 2;

    /** A method to determine if two instances of Area intersect */
    public boolean doAreasCollide(Area area1, Area area2) {
        boolean collide = false;

        Area collide1 = new Area(area1);
        collide1.subtract(area2);
        if (!collide1.equals(area1)) {
            collide = true;
        }

        Area collide2 = new Area(area2);
        collide2.subtract(area1);
        if (!collide2.equals(area2)) {
            collide = true;
        }

        return collide;
    }

    ShapeCollision() {
        int w = 400;
        int h = 200;
        img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        final JLabel imageLabel = new JLabel(new ImageIcon(img));
        x = w/2;
        y = h/2;

        //circle 
        obstacles[0] = new Area(new Ellipse2D.Double(40, 40, 30, 30));

        int[] xTriangle = {330,360,345};
        int[] yTriangle = {60,60,40};
        //triangle 
        obstacles[1] = new Area(new Polygon(xTriangle, yTriangle, 3));

        int[] xDiamond = {60,80,60,40};
        int[] yDiamond = {120,140,160,140};
        //diamond 
        obstacles[2] = new Area(new Polygon(xDiamond, yDiamond, 4));

        int[] xOther = {360,340,360,340};
        int[] yOther = {130,110,170,150};
        // other 
        obstacles[3] = new Area(new Polygon(xOther, yOther, 4));

        walls = new Area(new Rectangle(0,0,w,h));

        ActionListener animate = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                animate();
                imageLabel.repaint();
            }
        };
        Timer timer = new Timer(50, animate);

        timer.start();
        JOptionPane.showMessageDialog(null, imageLabel);
        timer.stop();
    }

    public void animate() {
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.BLUE);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        x+=xDelta;
        y+=yDelta;
        int s = 15;
        Area player = new Area(new Ellipse2D.Double(x, y, s, s));

        // Acid test of edge collision;
        if (doAreasCollide(player,walls)) {
            if ( x+s>img.getWidth() || x<0 ) {
                xDelta *= -1;
            } 
            if(y+s>img.getHeight() || y<0 ) {
                yDelta *= -1;
            }
        }
        g.setColor(Color.ORANGE);
        for (Area obstacle : obstacles) {
            if (doAreasCollide(obstacle, player)) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.GREEN);
            }
            g.fill(obstacle);
        }

        g.setColor(Color.YELLOW);
        g.fill(player);


        g.dispose();
    }

    public static void main(String[] args) {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                new ShapeCollision();
            }
        };
        // Swing GUIs should be created and updated on the EDT
        // http://docs.oracle.com/javase/tutorial/uiswing/concurrency/initial.html
        SwingUtilities.invokeLater(r);
    }
}