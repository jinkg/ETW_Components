#ifdef GL_ES
precision lowp float;
#define PRECISION lowp
#else
#define PRECISION
#endif

varying PRECISION vec2 vTexcoord;
uniform sampler2D colorTexture;

void main() {
	gl_FragColor = texture2D(colorTexture, vTexcoord);
}

