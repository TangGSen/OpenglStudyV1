#ifdef GL_ES
    #ifdef GL_FRAGMENT_PRECISION_HIGH
        precision highp float;
    #else
        precision mediump float;
    #endif
#endif
uniform sampler2D U_Texture;
varying vec4 V_color;
varying vec2 V_textcoord;

void main(){
    gl_FragColor = V_color*texture2D(U_Texture,V_textcoord);
}