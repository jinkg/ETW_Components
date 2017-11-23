#ifdef GL_ES
precision highp float;
#define PRECISION highp
#else
#define PRECISION
#endif

varying PRECISION vec2 vTexcoord0;
varying PRECISION vec2 vTexcoord1;
varying PRECISION vec2 vTexcoord2;
varying PRECISION vec2 vTexcoord3;
varying PRECISION vec2 vTexcoord4;
uniform sampler2D colorTexture;

void main() {
	vec4 col = texture2D(colorTexture, vTexcoord0);
	gl_FragColor = (
		col*0.2270270270+
		texture2D(colorTexture, vTexcoord1)*0.3162162162+
		texture2D(colorTexture, vTexcoord2)*0.3162162162+
		texture2D(colorTexture, vTexcoord3)*0.0702702703+
		texture2D(colorTexture, vTexcoord4)*0.0702702703
	);
}

