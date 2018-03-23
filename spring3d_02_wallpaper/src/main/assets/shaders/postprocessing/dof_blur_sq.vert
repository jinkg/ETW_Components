uniform mat4 projectionMatrix;
uniform mat4 modelviewMatrix;

attribute vec3 aPosition;
attribute vec3 aNormal;
attribute vec2 aTexcoord;

varying vec2 vTexcoord0;


void main()
{
	vTexcoord0 = vec2(aTexcoord.x, 1.0-aTexcoord.y);
   gl_Position = vec4(aPosition, 1.0);
}
