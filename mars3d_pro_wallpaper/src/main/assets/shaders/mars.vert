#ifdef GL_ES
#define PRECISION mediump
#else
#define PRECISION
#endif

uniform mat4 projectionMatrix;
uniform mat4 modelviewMatrix;
uniform mat4 modelviewProjectionMatrix;

attribute vec4 aPosition;

attribute vec2 UVMap;
varying vec2 vTexcoord;

uniform vec3 light;
uniform PRECISION vec4 lighting;

varying vec3 vLight;

varying float flatLightingLuminance;

varying vec2 clamps;


void main()
{
	vLight = (vec4(light,0.0) * modelviewMatrix).xyz;
	float sl = dot(aPosition.xyz, vLight)/1.9;
	clamps = vec2(-0.05-lighting.x*0.5,0.5-min(0.0,sl+lighting.x)*10.0);
	
	flatLightingLuminance = smoothstep(clamps.x, clamps.y, sl*0.3-lighting.x*0.15);

	vLight*=vec3(1.0,-1.0,-1.0)*1.1;
	clamps = clamps-dot(vec3(-0.5,-0.5,-0.5),vLight);
	
	vTexcoord = UVMap;
    gl_Position = projectionMatrix*(modelviewMatrix*(aPosition*vec4(1.0/1.9, 1.0/1.9, 1.0/1.9, 1.0)));
}