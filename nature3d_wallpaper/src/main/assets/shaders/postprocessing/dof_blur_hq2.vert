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

void main()
{
	vTexcoord0 = vec2(aTexcoord.x, 1.0-aTexcoord.y);
	vTexcoord1 = vec2(aTexcoord.x, 1.0-aTexcoord.y)+blur_dir * 1.3846153846;
	vTexcoord2 = vec2(aTexcoord.x, 1.0-aTexcoord.y)-blur_dir * 1.3846153846;
	vTexcoord3 = vec2(aTexcoord.x, 1.0-aTexcoord.y)+blur_dir * 3.2307692308;
	vTexcoord4 = vec2(aTexcoord.x, 1.0-aTexcoord.y)-blur_dir * 3.2307692308;
    gl_Position = vec4(aPosition, 1.0);
}
