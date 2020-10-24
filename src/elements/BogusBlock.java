package elements;


/** Class representing bogus block */
public class BogusBlock extends Block {

    public BogusBlock(int posX, int posY, int maxX, int maxY) {
        super(posX, posY, maxX, maxY);
        setVisible(true);
    }

    public void resizeStroke() {}

}
