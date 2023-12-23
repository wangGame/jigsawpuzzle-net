package kw.artpuzzle.pref;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/11 16:37
 */
public class JigsawPreference {
    private Preferences preferences;

    public String getTheme() {
        return preferences.getString("theme","0.png");
    }

    public void saveTheme(String name){
        preferences.putString("theme",name);
        preferences.flush();
    }

    private static class instance{
        private static final JigsawPreference jigsawPreference = new JigsawPreference();
    }
    private JigsawPreference(){
        preferences = Gdx.app.getPreferences("jigsawpuzzle");
    }

    public static JigsawPreference getInstance(){
        return instance.jigsawPreference;
    }

    public void saveCoinNum(int num){
        preferences.putInteger("jigsawcoin",num + getCoinNum());
    }

    public int getCoinNum(){
        return preferences.getInteger("jigsawcoin",0);
    }
}
