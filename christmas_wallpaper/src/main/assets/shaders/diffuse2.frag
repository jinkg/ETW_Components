#ifdef GL_ES
precision mediump float;
#define PRECISION mediump
#else
#define PRECISION
#endif

varying PRECISION vec4 vPosition;
varying PRECISION vec2 vTexcoord;
uniform sampler2D colorTexture;
uniform vec3 diffuseColor;


void main() {
	gl_FragColor = vec4(texture2D(colorTexture, vTexcoord).rgb*diffuseColor, vPosition.z);
}

