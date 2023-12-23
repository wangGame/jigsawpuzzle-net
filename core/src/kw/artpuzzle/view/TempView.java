package kw.artpuzzle.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;

import kw.artpuzzle.constant.GameConstant;

public class TempView extends Group {
    private Image preImage;
    private Group splitPicGroup;
    public TempView(Texture texture){
        preImage = new Image(texture);
        splitPicGroup = new Group();
        addActor(splitPicGroup);
        Texture textureTemp = Asset.getAsset().getTexture("out1/" + 0 + ".png");
        float startX = -textureTemp.getWidth() / 4.0f;
        float startY = -textureTemp.getHeight() / 4.0f;
        int index = 0;
        for (int i = 0; i < 36; i++) {
            Image image = new Image(Asset.getAsset().getTexture("out1/"+i+".png"));
            image.setName(i+"");
            splitPicGroup.addActor(image);
            image.setVisible(false);
            image.setPosition(startX, startY);
            startX += image.getWidth()-300;
            index++;
            if (index == 6) {
                index = 0;
                startX = -textureTemp.getWidth()/4.0f;
                startY += image.getHeight()-300;
            }
        }
        splitPicGroup.setSize(1800,1800);
        preImage.setSize(1800,1800);
        splitPicGroup.setOrigin(Align.center);

        setSize(splitPicGroup.getWidth(),splitPicGroup.getHeight());
        preImage.setPosition(getWidth()/2.0f,getHeight()/2.0f,Align.center);
        addActor(preImage);
        preImage.setTouchable(Touchable.disabled);
        preImage.getColor().a = 0;
        splitPicGroup.setPosition(getWidth()/2.0f,getHeight()/2.0f,Align.center);
        Image playImg = new Image(Asset.getAsset().getTexture("white.png"));
        addActor(playImg);
        playImg.setSize(getWidth(),getHeight());
        playImg.setPosition(getWidth()/2.0f,getHeight()/2.0f,Align.center);
        playImg.setColor(Color.valueOf("000000"));
        playImg.getColor().a = 0.2f;
        playImg.toBack();
    }

    @Override
    public void setScale(float scaleXY) {
        super.setScale(scaleXY);
        GameConstant.modelScale = scaleXY;
    }

    public void setShowPre(boolean isShowPre) {
        preImage.clearActions();
        if (isShowPre){
            preImage.addAction(Actions.fadeIn(0.2f));
        }else {
            preImage.addAction(Actions.fadeOut(0.2f));
        }
    }

    public void addFindActor(Group group){
        splitPicGroup.addActor(group);
    }
}
