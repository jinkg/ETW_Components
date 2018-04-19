uniform mat4 projectionMatrix;
uniform mat4 modelviewMatrix;

attribute vec4 aPosition;
attribute vec2 UVMap;

varying vec2 vTexcoord;

void main()
{
	vTexcoord = UVMap;
    gl_Position = projectionMatrix*(modelviewMatrix*aPosition);
}