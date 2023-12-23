package com.kw.gdx.shader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/11 10:50
 */
public class ShaderManager {
    private HashMap<String, ShaderProgram> cacheProgram;
    private static ShaderManager manager;

    private ShaderManager(){
        cacheProgram = new HashMap<>();
    }

    public static ShaderManager getManager() {
        if (manager == null){
            manager = new ShaderManager();
        }
        return manager;
    }


    public ShaderProgram getType(Class clazz){
        String simpleName = clazz.getSimpleName();
        if (cacheProgram.containsKey(simpleName)) {
            return cacheProgram.get(simpleName);
        }

        ShaderProgram program = null;
        BaseShader factory = null;
        try {
            factory = (BaseShader) clazz.getConstructors()[0].newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (factory!=null) {
            program = factory.createShderProgram();
            cacheProgram.put(simpleName, program);
        }
        return program;
    }
}
