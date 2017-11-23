uniform mat4 projectionMatrix;
uniform mat4 modelviewMatrix;

attribute vec4 aPosition;
attribute vec2 aTexcoord;

varying vec4 vPosition;
varying vec2 vTexcoord;

uniform vec2 focalParams;

void main()
{
	vPosition = modelviewMatrix * aPosition;
    gl_Position = projectionMatrix * vPosition;
    vPosition.z = -(vPosition.z+focalParams[0])*focalParams[1];
	vTexcoord = aTexcoord;
}
