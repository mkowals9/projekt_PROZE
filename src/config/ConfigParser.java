package config;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/** Responsible for loading configuration and other things from resources*/
public class ConfigParser {

    public static ConfigParser instance;
    private boolean loaded = false;
    private Document data;
    private HashMap<String, GameMode> gameModes = new HashMap<>();
    private ArrayList<GameMode> gameModesList = new ArrayList<>();
    private final HashMap<String, String> lvlToMapPath = new HashMap<>();
    private static String configPath;
    private ArrayList<Integer> availableLevels = new ArrayList<>();
    private HashMap<String, String> heroNameToPath = new HashMap<>();

    public static void setConfigPath(String configPath) {
        ConfigParser.configPath = configPath;
    }

    public static ConfigParser getInstance() {
        if (instance == null) {
            instance = new ConfigParser();
            instance.load();
        }
        return instance;
    }

    /** loading config.xml file which contains basic config details, as well as map paths etc.*/
    public void load()  {
        if (loaded)
                return;
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            data = builder.parse(new File(configPath));
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            System.out.println("LOG Resources.load : " + ex);
        }
        loadMapPaths();
        loadGameModes();
        loadHeroes();
        loaded = true;
    }

    /** Returns loaded map, as a list of strings responsible for a single row on map */
    public ArrayList<String> getMapTxt(int lvl) {
        String path = getMapPath(lvl);
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert stream != null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String strLine;
        ArrayList<String> lines = new ArrayList<>();
        try {
            while ((strLine = reader.readLine()) != null) {
                String lastWord = strLine.substring(strLine.lastIndexOf(" ")+1);
                lines.add(lastWord);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public HashMap<String, String> getLvlToMapPath() {
        return lvlToMapPath;
    }

    public HashMap<String, GameMode> getGameModes() {
        return gameModes;
    }

    public void setGameModes(ArrayList<GameMode> gameModes) {
        this.gameModesList = gameModes;
        this.gameModes = new HashMap<>();
        for (GameMode mode: gameModes)
            this.gameModes.put(mode.getName(), mode);
    }

    public String getMapPath(int lvl){
        return lvlToMapPath.get(String.valueOf(lvl));
    }

    public int getPointsForMap(int lvl) {
        return (int) (1000 * Math.pow(2, lvl));
    }

    public GameMode getGameMode(String mode) {
        return gameModes.get(mode);
    }

    public ArrayList<String> getAvailableGameModeNames() {

        ArrayList<String> modesNames = new ArrayList<>();
        for (GameMode gm: gameModesList) {
            modesNames.add(gm.getName());
        }
        return modesNames;
    }

    public String getScoresPath() {
        NodeList tmp = data.getElementsByTagName("scores");
        Element element = (Element) tmp.item(0);
        return element.getAttribute("path");
    }

    public ConfigParser() {
        if (instance != null) {
            throw new IllegalStateException("Singleton already constructed");
        }
    }

    public void setAvailableLevels(ArrayList<Integer> availableLevels) {
        System.out.println(String.format("Setting availableLevels %s", availableLevels));
        this.availableLevels = availableLevels;
    }

    public boolean isLvlAvailable(int lvl) {
        return availableLevels.contains(lvl);
    }

    public HashMap<String, String> getHeroNameToPath() {
        return heroNameToPath;
    }

    private void loadMapPaths() {
        NodeList maps = data.getElementsByTagName("map");
        for(int i = 0; i< maps.getLength(); i++) {
            Element test = (Element) maps.item(i);
            System.out.println(
                    String.format(
                            "Map lvl: %s  |   Path: %s",
                            test.getAttribute("lvl"),
                            test.getAttribute("path")
                    )
            );
            lvlToMapPath.put(test.getAttribute("lvl"), test.getAttribute("path"));
        }
        for (String lvl: lvlToMapPath.keySet())
            availableLevels.add(Integer.valueOf(lvl));
    }

    /** Read game-modes from config.xml */
    private void loadGameModes() {
        if(AppState.getInstance().isServerConnected())
        {
            for(GameMode game: AppState.getInstance().getBomberClient().gameModeLoader.load()){
            gameModesList.add(game);
            gameModes.put(game.getName(),game);
            }
        }
        else{
        NodeList modes = data.getElementsByTagName("gameMode");
        for(int i = 0; i< modes.getLength(); i++) {
            Element test = (Element) modes.item(i);
            GameMode newMode =  new GameMode(
                    Integer.parseInt(test.getAttribute("speed")),
                    Integer.parseInt(test.getAttribute("bot_speed")),
                    Integer.parseInt(test.getAttribute("hp")),
                    Integer.parseInt(test.getAttribute("score_divider")),
                    test.getAttribute("lvl")
            );
            gameModes.put(test.getAttribute("lvl"), newMode);
            gameModesList.add(newMode);
        }}
    }

    /** Read paths to hero graphics from config.xml */
    private void loadHeroes() {
        NodeList modes = data.getElementsByTagName("hero");
        for(int i = 0; i< modes.getLength(); i++) {
            Element item = (Element) modes.item(i);
            heroNameToPath.put(item.getAttribute("name"), item.getAttribute("path"));
        }}
}
