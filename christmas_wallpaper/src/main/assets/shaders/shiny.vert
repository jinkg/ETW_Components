uniform mat4 projectionMatrix;
uniform mat4 modelviewMatrix;

attribute vec4 aPosition;
attribute vec3 aNormal;
attribute vec2 aTexcoord;

varying vec4 vPosition;
varying vec3 vNormal;
varying vec2 vTexcoord;

uniform vec2 focalParams;


void main()
{
	vPosition = modelviewMatrix * aPosition;
    gl_Position = projectionMatrix * vPosition;
    vPosition.z = -(vPosition.z+focalParams[0])*focalParams[1];
	vNormal = (modelviewMatrix * vec4(aNormal, 0.0)).xyz;
	vTexcoord = aTexcoord;
    
}
