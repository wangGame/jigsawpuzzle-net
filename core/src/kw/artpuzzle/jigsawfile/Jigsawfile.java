package kw.artpuzzle.jigsawfile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.kw.gdx.file.BaseFile;

import kw.artpuzzle.constant.LevelConfig;
import kw.artpuzzle.data.SaveHistoryBean;
import kw.artpuzzle.pref.JigsawPreference;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/11 17:02
 */
public class Jigsawfile extends BaseFile {

    private Jigsawfile() {
        super("jigsawhistoryfile.txt");
    }

    public void saveHistoryPic(SaveHistoryBean bean){
        FileHandle local = Gdx.files.local("history/" + LevelConfig.levelIndex.getLevelUUID() + ".txt");
        if (local.exists()) {
            local.delete();
        }
        local.writeString(bean.getSplitNum()+"\n",true);
        local.writeString(bean.getLeftNum()+"\n",true);
        Array<Integer> leftPicIndex = bean.getLeftPicIndex();
        for (int i = 0; i < leftPicIndex.size; i++) {
            if (i == leftPicIndex.size-1){
                local.writeString(leftPicIndex.get(i)+"", true);
            }else {
                local.writeString(leftPicIndex.get(i) + ",", true);
            }
        }
    }

    private static Jigsawfile jigsawfile;
    public static Jigsawfile getJigsawfile() {
        if (jigsawfile == null) {
            jigsawfile = new Jigsawfile();
        }
        return jigsawfile;
    }
}
