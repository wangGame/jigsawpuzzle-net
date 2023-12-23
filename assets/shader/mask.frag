#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif
varying vec2 v_texCoords;
varying LOWP vec4 v_color;
uniform sampler2D u_texture;
uniform sampler2D u_texture1;


void main() {

    LOWP vec4 pic = texture2D(u_texture, v_texCoords.xy);
    LOWP vec4 msk = texture2D(u_texture1, v_texCoords.xy);
    LOWP vec3 masked = pic.rgb * vec3(msk.r) + msk.ggg;
    gl_FragColor.a = msk.a * v_color.a;
    gl_FragColor.rgb = masked;
}