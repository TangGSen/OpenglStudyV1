attribute vec4 position;
attribute vec2 textcoord;
uniform mat4 ModelMatrix;
uniform mat4 ViewMatrix;
uniform mat4 ProjectionMatrix;
varying vec2 V_textcoord;
void main(){
    V_textcoord = textcoord;
    gl_Position = ProjectionMatrix*ViewMatrix*ModelMatrix*position;
}