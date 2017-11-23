attribute vec3 aPosition;
varying vec2 vTexcoord;

void main()
{
	vTexcoord = vec2(aPosition.x, aPosition.y)*0.5+0.5;
    gl_Position = vec4(aPosition, 1.0);
}
