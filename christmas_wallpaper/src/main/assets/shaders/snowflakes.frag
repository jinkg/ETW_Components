#ifdef GL_ES
precision mediump float;
#define PRECISION mediump
#else
#define PRECISION
#endif

varying PRECISION vec4 vPosition;
varying PRECISION vec2 vTexcoord;
varying PRECISION float vColor;
uniform sampler2D colorTexture;


void main() {
	gl_FragColor.rgb = texture2D(colorTexture, vTexcoord).rgb*vColor;
	gl_FragColor.a = vPosition.z;
}

