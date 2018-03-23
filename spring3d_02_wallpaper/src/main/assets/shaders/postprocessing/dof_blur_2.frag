#ifdef GL_ES
precision mediump float;
#define PRECISION mediump
#else
#define PRECISION
#endif

varying PRECISION vec2 vTexcoord;
uniform sampler2D colorTexture;
uniform vec2 blurDist1;
uniform vec2 blurDist2;
uniform vec2 blurDist3;
uniform vec2 blurDist4;

void main() {
	float a = texture2D(colorTexture, vTexcoord).a;
	gl_FragColor = (
		texture2D(colorTexture, vTexcoord.x+blurDist1*a)+
		texture2D(colorTexture, vTexcoord.x+blurDist2*a)+
		texture2D(colorTexture, vTexcoord.x+blurDist3*a)+
		texture2D(colorTexture, vTexcoord.x+blurDist4*a)
	) * 0.25;
}

