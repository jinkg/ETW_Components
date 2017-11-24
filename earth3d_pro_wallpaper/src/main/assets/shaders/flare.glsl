//VERTEX
uniform mat4 projectionMatrix;
uniform mat4 modelviewMatrix;

attribute vec4 aPosition;
attribute vec4 Col;

varying vec4 vColor;

uniform vec2 params;

void main() {
	vColor = Col*params[0];
    gl_Position = projectionMatrix*(modelviewMatrix[3]+aPosition*params[1]);
}

//FRAGMENT
varying mediump vec4 vColor;
void main() {
	gl_FragColor = vColor*vColor;
}