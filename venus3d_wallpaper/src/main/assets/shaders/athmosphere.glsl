//VERTEX
uniform mat4 projectionMatrix;
uniform mat4 modelviewMatrix;

attribute vec4 aPosition;
attribute vec4 aColor;

varying vec4 vColor;

uniform vec3 light;
varying vec3 vLight;
uniform vec4 params;

void main()
{
	vec3 vLight = (vec4(light,0.0) * modelviewMatrix).xyz;
	vec4 vPosition = modelviewMatrix*aPosition;
	vec3 vNormal = normalize( (modelviewMatrix*vec4(aPosition.xyz,0.0)).xyz);
	float sphere_luminance = dot(normalize(aPosition.xyz),vLight);
	float v = max(0.0,dot(params.xy,vNormal.xy)*0.5-0.1 + sphere_luminance*0.75+max(0.0,light.z)*0.35);
	
	vColor = aColor * vec4(v,v*0.95,v, params.z * (v*0.1+1.0));
	vColor.rgb *= params.w;
	
    gl_Position = projectionMatrix*vPosition;
}

//FRAGMENT
#ifdef GL_ES
precision mediump float;
#define PRECISION mediump
#else
#define PRECISION
#endif

varying PRECISION vec4 vColor;

void main() {
	gl_FragColor = vColor*vColor;
}

