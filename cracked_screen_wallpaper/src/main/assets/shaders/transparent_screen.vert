uniform mat4 projectionMatrix;
uniform mat4 modelviewMatrix;

attribute vec3 aPosition;
attribute vec3 aNormal;
attribute vec2 aTexcoord;

varying vec2 vTexcoord;
varying vec3 vPosition;
varying vec3 vNormal;

uniform vec3 crackTS;

void main()
{
	vPosition = (modelviewMatrix*vec4(aPosition.x*crackTS.z+crackTS.x,aPosition.y*crackTS.z+crackTS.y,aPosition.z*0.4-0.1, 1.0)).xyz;
    gl_Position = projectionMatrix*vec4(vPosition, 1.0);
}
