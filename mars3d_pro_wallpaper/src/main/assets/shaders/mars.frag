#ifdef GL_ES
precision mediump float;
#define PRECISION mediump
#else
#define PRECISION
#endif

varying PRECISION vec2 vTexcoord;
varying PRECISION vec3 vLight;
varying float flatLightingLuminance;
uniform sampler2D tex;
uniform sampler2D normaltex;
uniform PRECISION vec4 lighting;

varying PRECISION vec2 clamps;

void main() {
	gl_FragColor.rgb = texture2D(tex, vTexcoord).rgb * ( 
		max(
			smoothstep(clamps.x, clamps.y, dot(texture2D(normaltex, vTexcoord).xyz,vLight)),
			flatLightingLuminance
		) * lighting.y*0.5 + lighting.z);
}

