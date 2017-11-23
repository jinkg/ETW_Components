uniform mat4 projectionMatrix;
uniform mat4 modelviewMatrix;

attribute vec4 aPosition;
attribute vec3 aNormal;
attribute vec2 aTexcoord;

varying vec4 vPosition;
varying vec2 vTexcoord;
varying float vColor;

uniform vec2 focalParams;
uniform vec2 texOffset;

void main()
{
	vPosition = modelviewMatrix * aPosition;
	vColor = abs((modelviewMatrix * vec4(aNormal.xyz,0.0)).z)+0.5;
    gl_Position = projectionMatrix * vPosition;
    vPosition.z = -(vPosition.z+focalParams[0])*focalParams[1];
	vTexcoord = aTexcoord * 0.25 + texOffset;
}
