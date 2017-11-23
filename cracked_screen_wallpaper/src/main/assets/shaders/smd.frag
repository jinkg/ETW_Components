#ifdef GL_ES
precision mediump float;
#define PRECISION mediump
#else
#define PRECISION
#endif

varying PRECISION vec2 vTexcoord;
varying PRECISION vec3 vViewDirection;
varying PRECISION vec3 vReflectedLightDir;

uniform sampler2D colorTexture;
uniform vec2 lightingParams;

void main() {

	vec3 col = texture2D(colorTexture, vTexcoord).rgb;
	float vSpecular = pow( max(0.0, -dot(normalize(vReflectedLightDir), vViewDirection)), 5.0)*lightingParams.y;
	
	gl_FragColor = vec4( 
			col * (lightingParams.x) + vSpecular, 1.0);

}

