package kw.artpuzzle.group.group;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ArrayMap;
import com.kw.gdx.resource.cocosload.CocosResource;

import kw.artpuzzle.data.GameData;
import kw.artpuzzle.data.SelectItemBean;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/11 19:35
 */
public class SelectDiffItem extends Group {
    private Group rootgroup;
    public SelectDiffItem(SelectItemBean selectItemBean){
        setName("selectItemBean"+selectItemBean.getId());
        rootgroup = CocosResource.loadFile("cocos/selectitem.json");
        addActor(rootgroup);
        setSize(rootgroup.getWidth(),rootgroup.getHeight());
        Actor piecemodel = rootgroup.findActor("piecemodel");
        piecemodel.setColor(Color.valueOf("#dee4eb"));
        Label piececoinlabel = rootgroup.findActor("piececoinlabel");
        piececoinlabel.setColor(Color.BLACK);
        piececoinlabel.setText(selectItemBean.getRewardcoin());
        Label piecesnum = rootgroup.findActor("piecesnum");
        piecesnum.setText(selectItemBean.getPiecesnum());
        piecesnum.setColor(Color.valueOf("#7d8493"));
        Actor selectitem = rootgroup.findActor("selectitem");
        selectitem.setOrigin(Align.center);
        selectitem.setScale(0.8f);
    }

    public void select(){
        Actor piecemodel = rootgroup.findActor("piecemodel");
        piecemodel.setColor(Color.valueOf("#35c75a"));
        Label piecesnum = rootgroup.findActor("piecesnum");
        piecesnum.setColor(Color.valueOf("#7d8493"));
    }

    public void unselect(){
        Actor piecemodel = rootgroup.findActor("piecemodel");
        piecemodel.setColor(Color.valueOf("#dee4eb"));
        Label piecesnum = rootgroup.findActor("piecesnum");
        piecesnum.setColor(Color.WHITE);
    }
}
