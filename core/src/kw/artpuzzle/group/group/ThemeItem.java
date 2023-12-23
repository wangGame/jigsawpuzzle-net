package kw.artpuzzle.group.group;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.listener.OrdinaryButtonListener;

import kw.artpuzzle.pref.JigsawPreference;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/10 14:15
 */
public class ThemeItem extends Group {
    private String themeName;
    private Runnable runnable;
    private Image iconSelect;
    public ThemeItem(String themeName,Runnable runnable){
        this.themeName = themeName;
        this.runnable = runnable;
        setSize(358,358);
        Texture texture = Asset.getAsset().getTexture("themebg/" + themeName);
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        Image image = new Image(texture);
        addActor(image);
        image.setSize(getWidth(),getHeight());
        iconSelect = new Image(Asset.getAsset().getTexture("common/iconselect.png"));
        addActor(iconSelect);
        if (JigsawPreference.getInstance().getTheme() == null){
            JigsawPreference.getInstance().saveTheme(themeName);
        }else {
            if (!JigsawPreference.getInstance().getTheme().equals(themeName)) {
                iconSelect.setVisible(false);
            }
        }
        iconSelect.setPosition(getWidth() - 20,getHeight() - 20 , Align.topRight);
        setOrigin(Align.center);
        addListener(new OrdinaryButtonListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                runnable.run();
                iconSelect.setVisible(true);
                JigsawPreference.getInstance().saveTheme(themeName);
            }
        });
    }

    public void hideIconSelect(){
        iconSelect.setVisible(false);
    }
}
