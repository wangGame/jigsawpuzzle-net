package kw.artpuzzle.shader;

import com.kw.gdx.shader.BaseShader;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/12 12:39
 */
public class MskShader extends BaseShader {
    public MskShader(){
        vertPath = "shader/corners.vert";
        fragPath = "shader/msks.glsl";
    }
}
