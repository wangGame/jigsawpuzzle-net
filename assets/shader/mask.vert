attribute vec4 a_position;
attribute vec2 a_texCoord0;
attribute vec4 a_color;
uniform mat4 u_projTrans;
varying vec2 v_texCoords;
varying vec4 v_color;
void main() {
    v_texCoords.xy = a_texCoord0;
    v_color = a_color;
    v_color.a = v_color.a * (255.0/254.0);
    gl_Position = u_projTrans * a_position;
}
