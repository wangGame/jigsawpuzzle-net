package kw.artpuzzle.dialog;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.listener.OrdinaryButtonListener;
import com.kw.gdx.resource.annotation.ScreenResource;
import com.kw.gdx.view.dialog.base.BaseDialog;

import kw.artpuzzle.data.LevelBean;
import kw.artpuzzle.group.ItemImage;
import kw.artpuzzle.pref.JigsawPreference;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/11 16:18
 */
@ScreenResource("cocos/getpic.json")
public class GetPicDialog extends BaseDialog {
    private Runnable runnable;
    private LevelBean levelBean;
    public GetPicDialog(LevelBean levelBean, Runnable runnable) {
        this.runnable = runnable;
        this.levelBean = levelBean;
    }

    @Override
    public void show() {
        super.show();
        Group preview = dialogGroup.findActor("preview");
        ItemImage image = new ItemImage(levelBean);
        preview.addActor(image);
        image.setSize(preview.getWidth()-20,preview.getHeight()-20);
        image.setPosition(preview.getWidth()/2.0f,preview.getHeight()/2.0f, Align.center);

        dialogGroup.findActor("btncontinue").addListener(new OrdinaryButtonListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (JigsawPreference.getInstance().getCoinNum()>levelBean.getUnlockCost()){
                    runnable.run();
                    JigsawPreference.getInstance().saveCoinNum(-levelBean.getUnlockCost());
                }else {

                }
            }
        });
    }
}
