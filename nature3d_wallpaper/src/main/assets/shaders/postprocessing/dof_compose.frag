#ifdef GL_ES
precision lowp float;
#define PRECISION lowp
#else
#define PRECISION
#endif

varying PRECISION vec2 vTexcoord;
uniform sampler2D blurredTexture;

void main() {
	gl_FragColor.rgb = texture2D(blurredTexture, vTexcoord).rgb;
	gl_FragColor.a = 1.0;
}

