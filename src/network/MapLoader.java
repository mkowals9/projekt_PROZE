package network;

import elements.Block;
import elements.MapParser;

import java.util.ArrayList;
import java.util.Collections;

/** Class responsible for loading maps from server */
public class MapLoader {
    private BomberClient bomberClient;

    public MapLoader(BomberClient bomberClient) {
        this.bomberClient = bomberClient;
    }

    /** load a list of available maps from server */
    public ArrayList<Integer> loadMapLvls() {
        String resp = bomberClient.sendRequest("get/map/list");
        ArrayList<Integer> levels = new ArrayList<Integer>();
        if (!resp.startsWith("200")) {
            // no handling should be necessary
        } else {
            resp = resp.replace("200/", "");
               for (String lvl : resp.split(","))
                   levels.add(Integer.parseInt(lvl));
        }
        return levels;
    }

    /** load a specific map from server */
    public ArrayList<Block> loadMap(int lvl) {
        String resp = bomberClient.sendRequest("get/map/" + lvl);
        ArrayList<Block> blocks = new ArrayList<Block>();
        if (!resp.startsWith("200")) {
            // no handling should be necessary
        } else {
            resp = resp.replace("200/", "");
            ArrayList<String> lines = new ArrayList<>();
            Collections.addAll(lines, resp.split("\\|"));
            blocks = MapParser.parseTxt(lines);
        }
        return blocks;
    }
}
