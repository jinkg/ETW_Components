#ifdef GL_ES
precision mediump float;
#define PRECISION mediump
#else
#define PRECISION
#endif

varying PRECISION vec2 vTexcoord;
varying PRECISION vec4 vColor;
uniform sampler2D colorTexture;

void main() {
	gl_FragColor = texture2D(colorTexture, vTexcoord) * vColor;
}

