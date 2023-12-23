package kw.artpuzzle.jigsawfile;

import com.badlogic.gdx.utils.ArrayMap;
import com.kw.gdx.file.BaseFile;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/17 8:49
 */
public class EnterLevelFile extends BaseFile<String,String> {

    private static EnterLevelFile jigsawfile;

    public EnterLevelFile() {
        super("enterLevelFile.txt");
    }

    public static EnterLevelFile getJigsawfile() {
        if (jigsawfile == null) {
            jigsawfile = new EnterLevelFile();
        }
        return jigsawfile;
    }

    public void saveLevelId(String id){
        super.saveMapV(id,true);
    }

}
