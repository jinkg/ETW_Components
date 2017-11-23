uniform mat4 projectionMatrix;
uniform mat4 modelviewMatrix;
uniform vec3 wind;
attribute vec3 aPosition;
attribute vec2 aTexcoord;
attribute vec4 aColor;
attribute vec3 aNormal;
varying vec3 vPosition;
varying vec2 vTexcoord;
varying vec3 vColor;

void main()
{
	vPosition = (modelviewMatrix*vec4(
		aPosition*1.0+
			vec3(0.0,0.0,sin(aPosition*(1.0+sin(aPosition.x*1.27+wind.x*9.0))+wind.y)*(1.2+sin(aPosition.x*1.2+wind.z*3.0))*0.15*aColor.b), 1.0)).xyz;
	float brightness = abs( (modelviewMatrix * vec4(aNormal,0.0)).z) * 0.5 + 0.5;
	vColor = vec3(aColor.r*brightness, aColor.g*brightness, 0.0);
	vTexcoord = aTexcoord;
    gl_Position = projectionMatrix*vec4(vPosition, 1.0);
}
