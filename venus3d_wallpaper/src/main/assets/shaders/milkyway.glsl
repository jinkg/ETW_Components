//VERTEX
uniform mat4 projectionMatrix;
uniform mat4 modelviewMatrix;

attribute vec4 aPosition;
attribute vec3 aNormal;

varying vec3 vTexcoord;

void main()
{
	vTexcoord = aNormal.xyz*vec3(1.0,-1.0,1.0);
    gl_Position = projectionMatrix*(modelviewMatrix*(aPosition*vec4(1.0/1.9, 1.0/1.9, 1.0/1.9, 1.0)));
}

//FRAGMENT
#ifdef GL_ES
precision mediump float;
#define PRECISION mediump
#else
#define PRECISION
#endif

varying PRECISION vec3 vTexcoord;
uniform samplerCube tex;
uniform PRECISION vec2 params;
uniform PRECISION vec2 lighting_milkyway;

void main() {
	gl_FragColor.rgb = textureCube(tex, vTexcoord).rgb*lighting_milkyway.x+lighting_milkyway.y;
}

