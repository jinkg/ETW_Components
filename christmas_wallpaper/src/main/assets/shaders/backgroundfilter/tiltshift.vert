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
varying float strength;
uniform float filterStrength;

void main()
{
	strength = abs(0.7-aTexcoord.y)*filterStrength*2.0;
		
	vTexcoord0 = vec2(aTexcoord.x, aTexcoord.y);
	vTexcoord1 = vec2(aTexcoord.x, aTexcoord.y)+blur_dir*strength;
	vTexcoord2 = vec2(aTexcoord.x, aTexcoord.y)-blur_dir*strength;
	vTexcoord3 = vec2(aTexcoord.x, aTexcoord.y)+vec2(blur_dir.y,-blur_dir.x)*strength;
	vTexcoord4 = vec2(aTexcoord.x, aTexcoord.y)-vec2(blur_dir.y,-blur_dir.x)*strength;
    gl_Position = vec4(aPosition, 1.0);
}
