package kw.artpuzzle.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;

import java.util.ArrayList;

public class ModelUtils {
    private ArrayList<ModelGroup> allModels;
    private ArrayList<ModelGroup> borderModels;
    private ArrayList<ModelGroup> insetModels;
    private TempView tempView;
    private Texture texture;
    public ModelUtils(String targetTextureName,int row,int colomn){
        allModels = new ArrayList<>();
        borderModels = new ArrayList<>();
        insetModels = new ArrayList<>();
        texture = Asset.getAsset().getLocalTexture(targetTextureName);
        if (texture == null){
            texture = Asset.getAsset().getTexture("61a0bd0f50a6b7268b7113de.png");
        }
        int width = texture.getWidth();
        int height = texture.getHeight();
        int modelIndex = -1;
        tempView = new TempView(texture);
        for (int i3 = 0; i3 < row; i3++) {
            for (int i4 = 0; i4 < colomn; i4++) {
                modelIndex ++;
                ModelGroup maskImage = new ModelGroup("out1/"+modelIndex+".png");
                maskImage.setTexure(texture);
                maskImage.setPicIndex(modelIndex);
                Actor actor = tempView.findActor(modelIndex+"");
                maskImage.setImageSize(actor.getWidth(), actor.getHeight());
                maskImage.setPosX((actor.getX(Align.center)+actor.getWidth()/4.0f));
                maskImage.setPosY((actor.getY(Align.center)+actor.getHeight()/4.0f));
                maskImage.setStartU(((actor.getX())/(width * 1.0f)) * (width/1800.0f));
                maskImage.setStartV(1.0f -((actor.getY()+actor.getHeight())/height)*(height/1800.0f));
                allModels.add(maskImage);
                if (i3 == 0 || i4 == 0){
                    borderModels.add(maskImage);
                }else {
                    insetModels.add(maskImage);
                }
            }
        }
    }

    public Texture getTexture() {
        return texture;
    }

    public ArrayList<ModelGroup> getAllModels() {
        return allModels;
    }

    public TempView getTempView() {
        return tempView;
    }

    public ArrayList<ModelGroup> getBorderModels() {
        return borderModels;
    }

}