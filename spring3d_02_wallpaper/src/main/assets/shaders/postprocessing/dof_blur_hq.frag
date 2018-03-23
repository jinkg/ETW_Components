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
	vec4 col1 = texture2D(colorTexture, vTexcoord1);
	vec4 col2 = texture2D(colorTexture, vTexcoord2);
	vec4 col3 = texture2D(colorTexture, vTexcoord3);
	vec4 col4 = texture2D(colorTexture, vTexcoord4);
	
	vec4 weights = vec4(0.3162162162, 0.3162162162, 0.0702702703, 0.0702702703)*
					vec4(col1.a, col2.a, col3.a, col4.a);
	gl_FragColor = (
		texture2D(colorTexture, vTexcoord0)* (1.0-dot(weights,vec4(1.0,1.0,1.0,1.0)))+
		col1*weights[0]+
		col2*weights[1]+
		col3*weights[2]+
		col4*weights[3]
	);
}

