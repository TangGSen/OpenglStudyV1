attribute vec4 poistion;
attribute vec4 color;
attribute vec4 normal;
uniform mat4 ModelMatrix;
uniform mat4 ViewMatrix;
uniform mat4 ProjectionMatrix;
varying vec4 V_color;
void main(){
    V_color = color;
    gl_Position = ProjectionMatrix*ModelMatrix*ViewMatrix*poistion;
}
