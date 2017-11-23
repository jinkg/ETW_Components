#ifdef GL_ES
precision lowp float;
#define PRECISION lowp
#else
#define PRECISION
#endif

varying PRECISION vec2 vTexcoord;
uniform sampler2D originalTexture;

void main() {
	float b = 1.0-texture2D(originalTexture, vTexcoord,3.0).a;
	
	gl_FragColor = texture2D(originalTexture, vTexcoord,b*3.0);
}

