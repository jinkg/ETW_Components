uniform mat4 projectionMatrix;
uniform mat4 modelviewMatrix;

attribute vec3 aPosition;
attribute vec3 aNormal;
attribute vec2 aTexcoord;

varying vec2 vTexcoord0;
varying vec2 vTexcoord1;
varying vec2 vTexcoord2;
varying vec2 vTexcoord3;
varying vec2 vTexcoord4;

uniform vec2 blur_dir;
uniform float filterStrength;

void main()
{
	vTexcoord0 = vec2(aTexcoord.x, aTexcoord.y);
	vTexcoord1 = vec2(aTexcoord.x, aTexcoord.y)+blur_dir*filterStrength*0.5;
	vTexcoord2 = vec2(aTexcoord.x, aTexcoord.y)-blur_dir*filterStrength*0.5;
	vTexcoord3 = vec2(aTexcoord.x, aTexcoord.y)+vec2(blur_dir.y,-blur_dir.x)*filterStrength*0.5;
	vTexcoord4 = vec2(aTexcoord.x, aTexcoord.y)-vec2(blur_dir.y,-blur_dir.x)*filterStrength*0.5;
    gl_Position = vec4(aPosition, 1.0);
}
