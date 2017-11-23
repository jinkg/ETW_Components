#ifdef GL_ES
precision mediump float;
#define PRECISION mediump
#else
#define PRECISION
#endif

varying PRECISION vec4 vPosition;
varying PRECISION vec2 vTexcoord;
uniform sampler2D colorTexture;


void main() {
	vec3 col = texture2D(colorTexture, vTexcoord).rgb;
	gl_FragColor = vec4(col, max(1.0-col.g*1.0,vPosition.z));
}

