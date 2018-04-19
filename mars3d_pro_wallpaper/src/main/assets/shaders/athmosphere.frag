#ifdef GL_ES
precision mediump float;
#define PRECISION mediump
#else
#define PRECISION
#endif

varying PRECISION vec4 vColor;

void main() {
	gl_FragColor = vColor*vColor;
}

