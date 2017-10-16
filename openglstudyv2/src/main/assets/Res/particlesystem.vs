attribute vec4 position;
attribute vec4 color;
uniform mat ModelMatrix;
uniform mat ViewMatrix;
uniform mat ProjectionMatrix;
vaying vec4 V_Color;
void main(){
    V_Color = color;
    gl_PoiontSize = 64.0;//设置点的大小
    gl_Position =  ProjectionMatrix*ViewMatrix*ModelMatrix*position;

}