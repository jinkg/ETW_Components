#ifdef GL_ES
precision mediump float;
#define PRECISION mediump
#else
#define PRECISION
#endif

varying PRECISION vec2 vTexcoord1;
varying PRECISION vec2 vTexcoord2;
varying PRECISION vec2 vTexcoord3;
varying PRECISION vec2 vTexcoord4;
uniform sampler2D colorTexture;

void main() {
	gl_FragColor = (
		texture2D(colorTexture, vTexcoord1) * 0.25+
		texture2D(colorTexture, vTexcoord2) * 0.25+
		texture2D(colorTexture, vTexcoord3) * 0.25+
		texture2D(colorTexture, vTexcoord4) * 0.25
	);
}

