#ifdef GL_ES
precision mediump float;
#define PRECISION mediump
#else
#define PRECISION
#endif

varying PRECISION vec2 vTexcoord;
uniform sampler2D colorTexture;
uniform vec4 color;

void main() {
	gl_FragColor = vec4(texture2D(colorTexture, vTexcoord).rgb,1.0) * color;
}

