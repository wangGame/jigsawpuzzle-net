package kw.artpuzzle.jigsawfile;

import com.kw.gdx.file.BaseFile;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/17 8:50
 */
public class PlayedLevelFile extends BaseFile {
    public PlayedLevelFile() {
        super("playedlevelfile.txt");
    }


    private static PlayedLevelFile jigsawfile;
    public static PlayedLevelFile getJigsawfile() {
        if (jigsawfile == null) {
            jigsawfile = new PlayedLevelFile();
        }
        return jigsawfile;
    }
}
