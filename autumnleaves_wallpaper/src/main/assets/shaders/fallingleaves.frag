#ifdef GL_ES
precision mediump float;
#define PRECISION mediump
#else
#define PRECISION
#endif

varying PRECISION vec3 vPosition;
varying PRECISION vec2 vTexcoord;
varying PRECISION vec3 vColor;
uniform sampler2D colorTexture;
uniform vec2 focalParams;

void main() {
	gl_FragColor = vec4(texture2D(colorTexture, vTexcoord).rgb * vColor, -(vPosition.z+focalParams[0])*focalParams[1]);
}

