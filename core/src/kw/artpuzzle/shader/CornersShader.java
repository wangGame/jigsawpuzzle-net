package kw.artpuzzle.shader;

import com.kw.gdx.shader.BaseShader;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/11 12:15
 */
public class CornersShader extends BaseShader {
    public CornersShader(){
        vertPath = "shader/corners.vert";
        fragPath = "shader/corners.glsl";
    }
}
