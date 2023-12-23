package kw.artpuzzle.group.group;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;

import kw.artpuzzle.data.GameData;
import kw.artpuzzle.data.LevelBean;
import kw.artpuzzle.jigsawfile.EnterLevelFile;
import kw.artpuzzle.jigsawfile.PlayedLevelFile;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/5 14:51
 */
public class MyPlayingGroup extends MyPzBaseGroup {
    private ArrayMap<String,String> mapV;
    private int index;
    private ArrayMap<String, LevelBean> levelUUIDMap;
    public MyPlayingGroup(){
        mapV = new ArrayMap<>();
        ArrayMap<String,String> mapVAll = EnterLevelFile.getJigsawfile().getMapV(true);
        ArrayMap<String,String> mapVPalyed = PlayedLevelFile.getJigsawfile().getMapV();
        for (ObjectMap.Entry<String, String> stringStringEntry : mapVAll) {
            if (mapVPalyed.containsKey(stringStringEntry.key))continue;
            mapV.put(stringStringEntry.key,stringStringEntry.value);
        }
        levelUUIDMap = GameData.getInstance().levelUUIDMap;
    }

    public void initPanel(){
        for (int i = 0; i < 10; i++) {
            addPanel();
//            ArrayMap<Integer, LevelBean> levelSortData = GameData.getInstance().getLevelSortData();
//            LevelBean levelBean1 = levelSortData.get(1);
//
//            getContentTable().add(new ItemGroup(levelBean1, new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            }));
        }
    }

    public void addPanel(){
        if (index >= mapV.size)return;
        Table contentTable = getContentTable();
        String keyAt = (String) mapV.getKeyAt(index);
        LevelBean levelBean = levelUUIDMap.get(keyAt);
        contentTable.add(new ItemGroup(levelBean, new Runnable() {
            @Override
            public void run() {

            }
        })).pad(25);
        index++;
        if (index % 2 == 0){
            contentTable.row();
        }
        contentTable.pack();
        levelListScrollPanel.validate();
    }

    public static MyPuzzleGroup myPuzzleGroup;


    @Override
    public void update() {
        super.update();
        initPanel();
    }
    public static MyPzBaseGroup getInstance() {
        return new MyPlayingGroup();
    }
}
