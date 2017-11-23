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

uniform float filterStrength;

void main() {
	gl_FragColor.rgb = (
		texture2D(colorTexture, vTexcoord1).rgb * 0.25 +
		texture2D(colorTexture, vTexcoord2).rgb * 0.25 +
		texture2D(colorTexture, vTexcoord3).rgb * 0.25 +
		texture2D(colorTexture, vTexcoord4).rgb * 0.25
	) * filterStrength + texture2D(colorTexture, vTexcoord0).rgb * (1.0-filterStrength);
}

