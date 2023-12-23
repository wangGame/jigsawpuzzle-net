#ifdef GL_ES
precision mediump float;
#endif


//input from vertex shader
varying vec4 v_color;
varying vec2 v_textCoords;
uniform sampler2D u_texture;
uniform sampler2D u_texture3;

uniform float u;
uniform float u2;
uniform float v;
uniform float v2;


void main() {
     vec4 normal1 = texture2D(u_texture, v_textCoords)* v_color;
     float vvv = v + (v_textCoords.y) / (v2 - v);
     float uuu = u + (v_textCoords.x) / (u2 - u);
     vec2 vecCoords = vec2(uuu,vvv);
     vec4 normal = texture2D(u_texture3, vecCoords)* v_color;
    // float colorTol = normal.r + normal.g + normal.b;
    // if(colorTol > 1.0){
    //      colorTol = 1.0f;
    // }
    // gl_FragColor =  vec4(vec3(normal1.rgb)-vec3(normal.b)/3.0f  * colorTol,1);

    // gl_FragColor.a =  normal.a;


     vec3 masked = normal.rgb * vec3(normal1.r) + normal1.ggg;
     gl_FragColor.a = normal1.a * v_color.a;
     gl_FragColor.rgb = masked;
}