#ifdef GL_ES
precision highp float;
#define PRECISION highp
#else
#define PRECISION
#endif

varying PRECISION vec2 vTexcoord0;
uniform sampler2D colorTexture;
uniform vec2 blur_dir;

void main() {

	vec4 col0 = texture2D(colorTexture, vTexcoord0);

	vec2 vTexcoord1 = vTexcoord0+blur_dir * 1.3846153846 * col0.a;
	vec2 vTexcoord2 = vTexcoord0-blur_dir * 1.3846153846 * col0.a;
	vec2 vTexcoord3 = vTexcoord0+blur_dir * 3.2307692308 * col0.a;
	vec2 vTexcoord4 = vTexcoord0-blur_dir * 3.2307692308 * col0.a;
 

	vec4 col1 = texture2D(colorTexture, vTexcoord1);
	vec4 col2 = texture2D(colorTexture, vTexcoord2);
	vec4 col3 = texture2D(colorTexture, vTexcoord3);
	vec4 col4 = texture2D(colorTexture, vTexcoord4);
	
	gl_FragColor = (
		texture2D(colorTexture, vTexcoord0)*0.2270270270+
		texture2D(colorTexture, vTexcoord1)*0.3162162162+
		texture2D(colorTexture, vTexcoord2)*0.3162162162+
		texture2D(colorTexture, vTexcoord3)*0.0702702703+
		texture2D(colorTexture, vTexcoord4)*0.0702702703
	);
}

