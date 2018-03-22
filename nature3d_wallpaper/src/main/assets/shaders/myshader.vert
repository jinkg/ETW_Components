uniform mat4 projectionMatrix;
uniform mat4 modelviewMatrix;

attribute vec4 aPosition;
attribute vec2 baked;

varying vec3 vPosition;
varying vec2 vTexcoord;

void main()
{
	vPosition = (modelviewMatrix * aPosition).xyz;
	vTexcoord = baked;
    gl_Position = projectionMatrix*vec4(vPosition, 1.0);
}
