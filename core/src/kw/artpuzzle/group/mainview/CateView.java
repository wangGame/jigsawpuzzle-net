package kw.artpuzzle.group.mainview;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ArrayMap;
import com.kw.gdx.scrollpane.ScrollPane;

import kw.artpuzzle.data.CateBean;
import kw.artpuzzle.data.GameData;
import kw.artpuzzle.data.LevelBean;
import kw.artpuzzle.group.group.CateDetailGroup;
import kw.artpuzzle.group.group.CateItemGroup;
import kw.artpuzzle.group.group.ItemGroup;
import kw.artpuzzle.listener.SignListener;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/4 21:30
 */
public class CateView extends BaseView {
    private ScrollPane cateScrollpane;
    private Table contentTable;
    private ArrayMap<String, CateBean> cateDataMap;
    public CateView(){
        cateDataMap = GameData.getInstance().readCate();
        cateScrollpane = new ScrollPane(contentTable = new Table(){{
              align(Align.top);
        }}){
            @Override
            public void act(float delta) {
                super.act(delta);
                if (getScrollPercentY()>0.8f) {
                    addLevelItem();
                    contentTable.pack();
                    cateScrollpane.validate();
                }
            }
        };
        for (int i = 0; i < initNumber*4; i++) {
            addLevelItem();
        }
        contentTable.pack();
        contentTable.validate();
        cateScrollpane.setSize(getWidth(),getHeight());
        addActor(cateScrollpane);
    }

    public void addLevelItem(){
        super.addLevelItem();
        if (levelIndex>=cateDataMap.size)return;
        String keyAt = cateDataMap.getKeyAt(levelIndex);
        levelIndex ++;
        CateBean cateBean = cateDataMap.get(keyAt);
        CateItemGroup cateGroup = new CateItemGroup(cateBean);
        contentTable.add(cateGroup).padLeft(10).padRight(10).padTop(28).padBottom(28);
        cateGroup.setSignListener(new SignListener() {
            @Override
            public void sign(Object o) {
                CateBean cateName = (CateBean)o;
                CateDetailGroup cateDetailGroup = new CateDetailGroup(cateName);
                cateDetailGroup.setY(1920-142,Align.top);
                CateView.this.addActor(cateDetailGroup);
            }
        });
        contentTable.row();
    }
}