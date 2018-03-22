uniform mat4 projectionMatrix;
uniform mat4 modelviewMatrix;

attribute vec3 aPosition;
attribute vec2 baked;
attribute float aWind;

varying vec3 vPosition;
varying vec2 vTexcoord;

uniform vec3 wind;

void main()
{
	vPosition = (modelviewMatrix*vec4(aPosition*1.0+sin(aPosition*0.7*(1.0+sin(aPosition.x*2.27+wind.x*9.0))+wind.y)*(1.2+sin(aPosition.x*3.0+wind.z*3.0))*0.03*aWind, 1.0)).xyz;
	vTexcoord = baked;
    gl_Position = projectionMatrix*vec4(vPosition, 1.0);
}
