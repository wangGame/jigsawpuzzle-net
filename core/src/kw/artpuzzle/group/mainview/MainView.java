package kw.artpuzzle.group.mainview;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ArrayMap;
import com.kw.gdx.scrollpane.ScrollPane;

import kw.artpuzzle.data.GameData;
import kw.artpuzzle.data.LevelBean;
import kw.artpuzzle.group.group.BannerGroup;
import kw.artpuzzle.group.group.ItemGroup;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/4 19:54
 */
public class MainView extends BaseView {
    private ArrayMap<Integer, LevelBean> levelData;
    private ScrollPane levelScrollPanel;
    private Table contentTable;
    private Runnable runnable;
    public MainView(Runnable runnable){
        this.runnable = runnable;
        levelData = GameData.getInstance().getLevelSortData();
        levelScrollPanel = new ScrollPane(contentTable = new Table(){{
        }}){
            @Override
            public void act(float delta) {
                super.act(delta);
                if (getScrollPercentY()>0.75) {
                    addLevelItem();
                    contentTable.pack();
                    levelScrollPanel.validate();
                }
            }
        };
        for (int i = 0; i < initNumber; i++) {
            addLevelItem();
        }
        contentTable.pack();
        contentTable.align(Align.top);
        levelScrollPanel.setSize(getWidth(),getHeight());
        levelScrollPanel.setPosition(getWidth()/2.0f,getHeight()/2.0f,Align.center);
        addActor(levelScrollPanel);
    }

    public void addLevelItem(){
        super.addLevelItem();
        if (levelIndex>levelData.size)return;
        levelIndex ++;
        LevelBean levelBean = levelData.get(levelIndex);
        contentTable.add(new ItemGroup(levelBean, runnable)).pad(15);
        if (levelIndex % 2 == 0) {
            contentTable.row();
        }
    }
}
