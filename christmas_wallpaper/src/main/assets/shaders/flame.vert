uniform mat4 projectionMatrix;
uniform mat4 modelviewMatrix;
uniform vec3 wind;

attribute vec4 aPosition;
attribute vec3 aColor;
attribute vec2 aTexcoord;

varying vec2 vTexcoord;
varying vec4 vColor;

void main()
{
	vTexcoord = aTexcoord ;
	vColor = 1.1 + cos(wind.yyyy)*cos(wind.zzzz)*0.2;
    gl_Position = projectionMatrix*modelviewMatrix*(aPosition+ 
    	vec4(cos(wind.yz*2.0)*cos(wind.yx*2.0) * vec2(0.08,0.05) * aColor.r, 0.0, 0.0));
}
