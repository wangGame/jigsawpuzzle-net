#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_textCoords;
uniform sampler2D u_texture;

void main() {
    vec4 pic = v_color * texture2D(u_texture, v_textCoords);

    gl_FragColor=vec4(vec3(v.rgb),(1.0-c)*v.a);
}