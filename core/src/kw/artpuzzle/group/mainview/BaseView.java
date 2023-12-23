package kw.artpuzzle.group.mainview;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.constant.Constant;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/4 21:39
 */
public abstract class BaseView extends Group {
    protected Label infoLabel;
    protected int levelIndex;
    protected int initNumber = 10;
    protected final String TAG = getClass().getSimpleName();
    public BaseView(){
        setSize(Constant.GAMEWIDTH, Constant.GAMEHIGHT - 142 - 142);
        infoLabel = new Label("",new Label.LabelStyle(){{
            font = Asset.getAsset().loadBitFont("cocos/font/inter-semi-32.fnt");
        }});
        addActor(infoLabel);
        infoLabel.setText(TAG);
        infoLabel.setColor(Color.BLACK);
        infoLabel.pack();
        infoLabel.setPosition(getWidth()/2.0f,getHeight()/2.0f,Align.center);
    }

    public void addLevelItem(){
        System.out.println(levelIndex);
    }

    public void update(){}
}
