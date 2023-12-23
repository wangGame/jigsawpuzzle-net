package kw.artpuzzle.group.group;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ArrayMap;

import kw.artpuzzle.data.GameData;
import kw.artpuzzle.data.LevelBean;
import kw.artpuzzle.jigsawfile.EnterLevelFile;
import kw.artpuzzle.jigsawfile.PlayedLevelFile;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/5 14:50
 */
public class MyPuzzleGroup extends MyPzBaseGroup{
    private ArrayMap mapV;
    private ArrayMap<String, LevelBean> levelUUIDMap;
    private int index;
    public MyPuzzleGroup(){
        mapV = EnterLevelFile.getJigsawfile().getMapV();
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
        if (levelBean == null)return;
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
    public static MyPzBaseGroup getInstance() {
        if (myPuzzleGroup == null) {
            return myPuzzleGroup = new MyPuzzleGroup();
        }else {
            return myPuzzleGroup;
        }
    }

    @Override
    public void update() {
        super.update();
        initPanel();
    }
}
