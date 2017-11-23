#ifdef GL_ES
precision mediump float;
#define PRECISION mediump
#else
#define PRECISION
#endif

varying PRECISION vec4 vPosition;
varying PRECISION vec2 vTexcoord;
varying PRECISION vec3 vNormal;
uniform sampler2D colorTexture;
uniform vec3 diffuseColor;
uniform vec3 specularColor;


void main() {
	gl_FragColor = vec4(
		texture2D(colorTexture, vTexcoord).g*diffuseColor + 
		pow(max(0.0, dot(normalize(vNormal),vec3(-0.5,0.6,0.6))),6.0)*specularColor,
		vPosition.z);
}

