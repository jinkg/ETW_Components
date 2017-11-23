#ifdef GL_ES
precision mediump float;
#define PRECISION mediump
#else
#define PRECISION
#endif

varying PRECISION vec3 vViewDirection;
varying PRECISION vec3 vLightDir;
varying PRECISION vec3 vReflectedLightDir;
varying PRECISION float vSpecular;
varying PRECISION float vTransparency;

void main() {
	

	gl_FragColor = vec4(vSpecular,vSpecular,vSpecular,vTransparency);
}

