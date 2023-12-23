#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_textCoords;
uniform sampler2D u_texture;
uniform float ra;

void main() {
    vec2 boxPos= vec2(0.5f, 0.5f);
    vec2 boxBnd = vec2(0.5f - ra, 0.5f - ra);
    vec4 tc = v_color * texture2D(u_texture, v_textCoords);
    vec3 col = tc.rgb;
    float alpha = length(max(abs(v_textCoords - boxPos) - boxBnd, 0.0)) - ra;
    if(alpha <= 0.0){
        gl_FragColor = vec4(col,1.0);
    }else{
        gl_FragColor = vec4(1.0, 1.0, 1.0,.0);
    }
}