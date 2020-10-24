package elements;

import java.util.ArrayList;

/** Class used to build map from .txt files  */
public class MapParser {

    static public ArrayList<Block> parseTxt(ArrayList<String> lines) {
        String[] diments = lines.remove(0).split("x"); //usuwa z pliku 1 linie i rozdziela wymiary
        ArrayList<Block> blocks = new ArrayList<>();
        Block new_block;
        int width = Integer.parseInt(diments[0]);
        int height = Integer.parseInt(diments[1]);
        int row = 0, col;

        for (String line :lines) {
            col = 0;
            for (char block: line.toCharArray()) {
                new_block = parseChar(block, col, row, width, height);
                if (new_block != null) {
                    blocks.add(new_block);
                }
                col++;
            }
            row++;
        }
        return blocks;
    }

    /** creates blocks depending on certain sign from txt file, returns created block*/
    static private Block parseChar(char block, int col, int row, int width, int height) {
        Block new_block = null;
        switch (block){
            case 'S': {
                new_block = new SolidBlock(col, row, width, height);
                break;
            }
            case 'W': {
                new_block = new WeakBlock(col, row, width, height);
                break;
            }
            case 'E': {
                new_block = new ExitBlock(col, row, width, height);
                break;
            }
            case 'C':{
                new_block = new CharacterBlock(col,row,width,height);
                break;
            }
            case 'H':{
                new_block = new HorizontalMonsterBlock(col,row,width,height);
                break;
            }
            case 'V':{
                new_block = new VerticalMonsterBlock(col,row,width,height);
                break;
            }
            case 'P':{
                new_block = new SpeedPowerUpBlock(col,row,width,height);
                break;
            }
            case 'B':{
                new_block = new BotSlowPowerUpBlock(col,row,width,height);
                break;
            }
            case 'M':{
                new_block = new ExtraBombPowerUpBlock(col,row,width,height);
                break;
            }
            case 'R':{
                new_block = new BonusPointsPowerUp(col,row,width,height);
                break;
            }
            default:
                break;
        }
        return new_block;
    }

}
