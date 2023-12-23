package kw.artpuzzle.listener;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import kw.artpuzzle.view.ModelGroup;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/4 10:50
 */
public class MyClickListener extends ClickListener {
    private ModelGroup group;
    public MyClickListener(ModelGroup group){
        this.group = group;
    }

    public ModelGroup getGroup() {
        return group;
    }
}
