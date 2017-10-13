#ifdef GL_ES
precision mediump float;
#endif

varying vec4 V_color;
uniform sampler2D U_Texture;
varying vec2 V_textcoord;
void main(){
    gl_FragColor = V_color*texture2D(U_Texture,V_textcoord);
}