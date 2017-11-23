attribute vec2 aPosition;
attribute vec2 aTexcoord;

varying vec2 vTexcoord1;
varying vec2 vTexcoord2;
varying vec2 vTexcoord3;
varying vec2 vTexcoord4;

uniform vec2 blurDist;

void main()
{
	vTexcoord1 = vec2(aTexcoord.x+blurDist.x, aTexcoord.y+blurDist.y);
	vTexcoord2 = vec2(aTexcoord.x-blurDist.x, aTexcoord.y+blurDist.y);
	vTexcoord3 = vec2(aTexcoord.x+blurDist.x, aTexcoord.y-blurDist.y);
	vTexcoord4 = vec2(aTexcoord.x-blurDist.x, aTexcoord.y-blurDist.y);
    gl_Position = vec4(aPosition, 0.0, 1.0);
}
