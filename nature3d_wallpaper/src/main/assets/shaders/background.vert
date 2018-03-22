uniform mat4 projectionMatrix;
uniform mat4 modelviewMatrix;

attribute vec4 aPosition;
attribute vec2 UVMap;

varying vec2 vTexcoord;

void main()
{
	vec4 vPosition = modelviewMatrix * aPosition;
	vTexcoord = UVMap;
    gl_Position = projectionMatrix*vPosition;
}