#ifdef GL_ES
precision mediump float;
#endif


//input from vertex shader
varying vec4 v_color;
varying vec2 v_textCoords;
uniform sampler2D u_texture;

void main() {
    vec2 uv  = v_textCoords.xy;
    float dx = 0.13;
    float dy = 0.13;
    vec2 coord = vec2(dx * floor(uv.x / dx), dy * floor(uv.y / dy));
    vec3 tc = texture2D(u_texture, coord).xyz;
    //    float xx = (tc.r + tc.g + tc.b) / 3.0;
    gl_FragColor = vec4(vec3(tc), 1.0);
}