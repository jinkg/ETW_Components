uniform mat4 projectionMatrix;
uniform mat4 modelviewMatrix;

attribute vec3 aPosition;
attribute vec3 aOffset;
attribute vec2 aTexcoord;

varying vec2 vTexcoord;

uniform vec3 circuitTS;
uniform vec3 middleSize;
uniform vec3 borderSize;
uniform vec3 position;

void main()
{
	vec3 ninepatchedPos = aPosition * middleSize + aOffset * borderSize + position;
	vec3 vPosition = ninepatchedPos * circuitTS.z + vec3(circuitTS.xy, -30.2);
	vTexcoord = aTexcoord;
    gl_Position = projectionMatrix*vec4(vPosition,1.0);
}
