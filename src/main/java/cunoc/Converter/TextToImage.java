package cunoc.Converter;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;

public class TextToImage extends JFrame {
    private String text;
    private File fileOut;
    private int sizeText;
    private Graphics2D graphic;


    public TextToImage(String text, File fileOut, int sizeText) {
        this.text = text;
        this.fileOut = fileOut;
        this.sizeText = sizeText;
    }

    public TextToImage(Graphics2D graphic) {
        this.graphic = graphic;
    }

    public boolean converter() {

        Font font = new Font("Tahoma", Font.PLAIN, sizeText);
        String array[] = this.text.split("\n");
        int width = array[0].length() * this.sizeText;
        int height = (( this.sizeText * array.length) *10)  ;

        // create a BufferedImage object
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        graphic = image.createGraphics();
       // drawing and coloring
        graphic.setColor(Color.BLACK);
        graphic.fillRect(0, 0, width, height);
        graphic.setFont(font);
        graphic.setPaint(Color.CYAN);
        graphic.fillRect(50, 50, width - 100, height - 100);
        graphic.setColor(Color.BLACK);
        for (int i = 0; i < array.length; i++) {
            graphic.drawString(array[i], 150, (50 * i) + 100);
        }
        graphic.dispose();
        return converterFinal(image);
    }

    public boolean converterGraphic(int w, int h) {
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        return converterFinal(image);
    }

    public boolean converterFinal(BufferedImage image) {
        try {
            ImageIO.write(image, "png", fileOut);
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
