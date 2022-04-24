package cunoc.Converter;

import cunoc.Tree.NodeBinary;
import cunoc.Tree.Tree;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Font;

public class TreeGraphConverter<T> {
    private Tree tree;
    private Graphics2D graphic;
    private int sizeText;
    private BufferedImage image;

    public TreeGraphConverter(Tree tree, int sizeText) {
        this.tree = tree;
        this.sizeText = sizeText;
    }

    public BufferedImage converter(int width, int height) {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Font font = new Font("Tahoma", Font.PLAIN, sizeText);
        this.graphic = image.createGraphics();
        this.graphic.setFont(font);
        // drawing and coloring
        String finalString = tree.basePrintTree();
        this.graphic.drawString(finalString, 0, 0);
        int length = tree.getHeight();
        int center = width / 2;
        for (int i = 0; i < length; i++) {
            NodeBinary arrayNode[] = tree.arrayNodeLevel(i, tree.getMain(), new NodeBinary[(i * 2) + 1], 0);
            for (int j = 0; j < arrayNode.length; j++) {
                if (arrayNode[j] != null) {
                    String printNode = arrayNode[j].getData().toString();
                    int possX = (center * j *2) + center;
                    int possY = (i * sizeText) + 50;
                    int widthText = printNode.length() * 10;
                    int heightText = sizeText;
                    this.graphic.drawRect(possX, possY, widthText, heightText);
                    this.graphic.drawString(printNode, possX, possY + sizeText);
                }
            }
            center /= 2;
        }
        graphic.dispose();
        return image;
    }

}
