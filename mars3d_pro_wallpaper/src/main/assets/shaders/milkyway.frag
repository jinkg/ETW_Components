#ifdef GL_ES
precision mediump float;
#define PRECISION mediump
#else
#define PRECISION
#endif

varying PRECISION vec2 vTexcoord;
uniform sampler2D tex;
uniform PRECISION vec2 params;
uniform PRECISION vec4 lighting;

void main() {
	gl_FragColor.rgb = texture2D(tex, vTexcoord).rgb*lighting.x+lighting.y;
}

