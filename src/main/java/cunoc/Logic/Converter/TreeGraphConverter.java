package cunoc.Logic.Converter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import cunoc.Logic.Tree.NodeBinary;
import cunoc.Logic.Tree.Tree;

import java.awt.Font;
import java.awt.Color;
import java.awt.BasicStroke;

public class TreeGraphConverter<T> {

    private final int spectText = 50;
    private Tree<T> tree;
    private Graphics2D graphic;
    private int sizeText;
    private BufferedImage image;

    public TreeGraphConverter(Tree<T> tree, int sizeText) {
        this.tree = tree;
        this.sizeText = sizeText;
    }

    public BufferedImage converter() {
        int length = tree.getHeight();

        int height = ((tree.getHeight() * tree.getWide()) * this.sizeText) / 2;
        int width = length * this.sizeText * tree.getWide();

        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        width = width - (width / 6);
        int center = width / 2;
        Font font = new Font("Tahoma", Font.PLAIN, sizeText);

        this.graphic = image.createGraphics();
        this.graphic.setFont(font);
        // drawing and coloring
        String finalString = tree.basePrintTree();
        this.graphic.drawString(finalString, 0, 0);
        for (int i = 0; i < length; i++) {
            NodeBinary<T>[] arrayNode2 = new NodeBinary[(((i + 1) * 2))];
            NodeBinary<T> arrayNode[] = tree.arrayNodeLevel(i, tree.getMain(), arrayNode2, 0);
            for (int j = 0; j < arrayNode.length; j++) {
                if (arrayNode[j] != null) {
                    String printNode = arrayNode[j].getData().toString();
                    int possX = (center * j * 2) + center;
                    int possY = ((i * sizeText * 4) + spectText);
                    int widthText = (printNode.length() * sizeText);
                    int heightText = sizeText + (sizeText / 2);
                    graphic.setPaint(Color.WHITE);// color rectangulo
                    this.graphic.fillRect(possX, possY, widthText, heightText);
                    graphic.setPaint(Color.BLACK);// color text
                    this.graphic.drawString(printNode, possX, possY + sizeText);
                    if (i < length - 1) {
                        if (arrayNode[j].getSonL() != null) {
                            printArrow(graphic, possX, possY, possX - (center / 2) + (widthText / 2),
                                    (((i + 1) * sizeText * 4) + spectText), Color.CYAN, 3);
                        }
                        if (arrayNode[j].getSonR() != null) {
                            printArrow(graphic, possX + widthText, possY, possX + (center / 2) + (widthText / 2),
                                    (((i + 1) * sizeText * 4) + spectText), Color.CYAN, 3);
                        }
                    }
                }
            }
            center /= 2;
        }
        graphic.dispose();
        return image;
    }

    private void printArrow(Graphics2D graphic, int possOneX, int possOneY, int possTwoX, int possTwoY, Color color,
            int stroke) {
        graphic.setPaint(color);
        graphic.setStroke(new BasicStroke(stroke));
        graphic.drawLine(possOneX, possOneY, possTwoX, possTwoY);
    }

}
