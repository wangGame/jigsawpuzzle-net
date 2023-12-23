package kw.artpuzzle.group.mainview;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ArrayMap;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.scrollpane.ScrollPane;

import kw.artpuzzle.data.GameData;
import kw.artpuzzle.data.LevelBean;
import kw.artpuzzle.group.group.ItemGroup;
import kw.artpuzzle.utils.DailyUtils;
import kw.artpuzzle.utils.DateBean;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/4 21:30
 */
public class DailyView extends BaseView {
    private ScrollPane scrollPane;
    private DateBean dateBean;
    private int currentMonthDays;
    private Table contentTable;
    private Runnable runnable;

    public DailyView(Runnable runnable){
        this.runnable = runnable;
        contentTable = new Table();
        dateBean = DailyUtils.currentDateBean();
        currentMonthDays = DailyUtils.currentMonthDay(dateBean.getYear(),dateBean.getMonth());
        scrollPane = new ScrollPane(contentTable){
            @Override
            public void act(float delta) {
                super.act(delta);
                if (getScrollPercentY()>0.9f){
                    addOneMonthData(false);
                    scrollPane.layout();
                    scrollPane.validate();
                }
            }
        };
        scrollPane.layout();
        scrollPane.validate();
        addOneMonthData(true);
        scrollPane.setSize(getWidth(),getHeight());
        addActor(scrollPane);
    }

    private void addOneMonthData(boolean b) {
        ArrayMap<Integer, LevelBean> levelBeanArrayMap = GameData.getInstance().readyDaily(dateBean.getYear()
                + "-" + (dateBean.getMonth()+1) + ".csv");
        contentTable.add(new Group() {{
            Label label = new Label("", new Label.LabelStyle() {{
                font = Asset.getAsset().loadBitFont("cocos/font/inter-semi-32.fnt");
            }});
            label.setColor(Color.BLACK);
            addActor(label);
            label.setText(dateBean.getYear()+" "+dateBean.getMonth());
            label.pack();
            setSize(465, 50);
            label.setPosition(0, 25, Align.left);
        }}).pad(15);

        contentTable.add(new Group() {{
            setSize(465, 50);
        }}).pad(15);
        contentTable.row();
        int tempDay = currentMonthDays;
        if (b){
            tempDay = dateBean.getDay();
        }
        int index = 0;
        for (int i = 1; i <= tempDay; i++) {
            LevelBean levelBean = levelBeanArrayMap.get(tempDay - i+1);
            if (levelBean == null){
                levelBean = new LevelBean();
            }
            contentTable.add(new ItemGroup(levelBean,runnable)).pad(15);
            index ++ ;
            if (index % 2 == 0) {
                contentTable.row();
            }
        }
        if (tempDay % 2 == 1) {
            contentTable.add(new Group() {{
                setSize(465, 50);
            }}).pad(15);
            contentTable.row();
        }
        contentTable.pack();
        DailyUtils.subMonth(dateBean);
        currentMonthDays = DailyUtils.currentMonthDay(dateBean.getYear(), dateBean.getMonth());

        System.out.println(levelIndex);
    }

}