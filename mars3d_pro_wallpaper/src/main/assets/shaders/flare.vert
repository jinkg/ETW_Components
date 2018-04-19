uniform mat4 projectionMatrix;
uniform mat4 modelviewMatrix;

attribute vec4 aPosition;
attribute vec4 Col;

varying vec4 vColor;

uniform vec2 params;

void main()
{
	vColor = Col*params[0]*1.7;	
    gl_Position = projectionMatrix*(modelviewMatrix[3]+aPosition*params[1]);
}