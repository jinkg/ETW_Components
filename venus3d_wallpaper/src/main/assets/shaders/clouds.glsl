//VERTEX
uniform mat4 projectionMatrix;
uniform mat4 modelviewMatrix;
uniform mat4 modelviewProjectionMatrix;

uniform vec3 light;
uniform vec4 lighting;
uniform vec2 cloud_params;

attribute vec4 aPosition;
attribute vec3 aNormal;

varying vec3 vTexcoord;
varying vec4 vLight;
varying vec2 vShadow;
varying vec4 lum1;
varying vec4 lum2;

void main()
{
	vec4 position = aPosition*vec4(1.0/1.9, 1.0/1.9, 1.0/1.9, 1.0);
	vec4 pos = modelviewMatrix*position;
   	vec3 normal = (modelviewMatrix*vec4(position.xyz, 0.0)).xyz;

	float flatLightingLuminance = max(0.0,dot(position.xyz, light)+0.1)*(0.5+lighting.z*3.0)*lighting.x;

	vLight.xyz = light * flatLightingLuminance;
	vLight.a = flatLightingLuminance+lighting.y;


	float haze =  smoothstep(0.1, 0.4,-dot(normal.xyz, normalize(pos.xyz)));
	vec4 lum = vec4(vec3(dot(vLight.xyz,position.xyz)), haze);
	lum.rgb += vLight.a;

	lum1 = vec4( 0.325,0.420, 0.541, 2.0) * lum;
	lum2 = vec4( 0.616, 0.475, 0.239, -2.0+cloud_params.x*(3.0)+(1.0-haze)*2.0+flatLightingLuminance) * lum;

	vTexcoord = aNormal;
    gl_Position = projectionMatrix*(modelviewMatrix*position);
}



//FRAGMENT
#ifdef GL_ES
precision mediump float;
#else
#endif

varying vec3 vTexcoord;
varying vec3 vPosition;
varying vec4 lum1;
varying vec4 lum2;
uniform samplerCube tex;
uniform vec4 lighting;
varying vec4 vLight;

void main() {
	gl_FragColor = textureCube(tex, vTexcoord).rgbb * lum1 + lum2;
}

