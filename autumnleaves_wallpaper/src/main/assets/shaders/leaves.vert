uniform mat4 projectionMatrix;
uniform mat4 modelviewMatrix;
uniform vec3 wind;
attribute vec3 aPosition;
attribute vec2 aTexcoord;
attribute vec4 aColor;
attribute vec4 aNormal;
varying vec3 vPosition;
varying vec2 vTexcoord;
varying vec3 vColor;

void main()
{
	vPosition = (modelviewMatrix*vec4(aPosition*1.0+sin(aPosition*0.3*(1.0+sin(aPosition.x*1.27+wind.x*9.0))+wind.y)*(1.2+sin(aPosition.x*1.2+wind.z*3.0))*0.05*aColor.b, 1.0)).xyz;
	vColor = vec3(aColor.r, aColor.g, 0.0) * abs(aNormal.z);
	vTexcoord = aTexcoord;
    gl_Position = projectionMatrix*vec4(vPosition, 1.0);
}
