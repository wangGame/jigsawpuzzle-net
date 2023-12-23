package kw.artpuzzle.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

import kw.artpuzzle.constant.GameConstant;
import kw.artpuzzle.view.ModelGroup;
import kw.artpuzzle.view.TempView;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/4 10:42
 */
public class GameLogicUtils {
    private TempView view;
    private Vector2 targetPos;
    public GameLogicUtils(TempView view){
        this.view = view;
        targetPos = new Vector2();
    }

    public Vector2 findTargetPos(String name){
        Actor actor = view.findActor(name);
        targetPos.set(actor.getX(Align.center) ,actor.getY(Align.center));
        return targetPos;
    }

    public boolean check(Vector2 targetPos,Vector2 vector2){
        Vector2 vector21 = new Vector2(vector2);
        Vector2 sub = vector21.sub(targetPos);
        float v = sub.x * sub.x + sub.y * sub.y;

        if (v<10000.0f/(GameConstant.modelScale* GameConstant.modelScale)){
            return true;
        }else {
            return false;
        }
    }

    public void convertTarget(Vector2 vector2) {
        view.stageToLocalCoordinates(vector2);
    }

    public void addActor(ModelGroup group) {
        view.addFindActor(group);
    }
}
