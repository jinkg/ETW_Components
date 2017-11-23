uniform mat4 projectionMatrix;
uniform mat4 modelviewMatrix;

attribute vec3 aPosition;
attribute vec3 aNormal;
attribute vec2 aTexcoord;

varying vec2 vTexcoord;

uniform vec4 imageScale;
uniform vec4 crackTS;


void main()
{
	vec3 vPosition = (modelviewMatrix*vec4(aPosition.x*crackTS.z+crackTS.x,aPosition.y*crackTS.z+crackTS.y,aPosition.z, 1.0)).xyz;
	vTexcoord = vPosition.xy * 0.016 * imageScale.xy +0.5;
	vTexcoord.x =     vTexcoord.x + imageScale[2];
	vTexcoord.y = 1.0-vTexcoord.y + imageScale[3];
    gl_Position = projectionMatrix*vec4(vPosition, 1.0);
}
