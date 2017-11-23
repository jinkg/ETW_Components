//VERTEX
uniform mat4 projectionMatrix;
uniform mat4 modelviewMatrix;
uniform vec2 brightnessAndSize;
uniform mat3 rotation;
uniform vec3 light;
uniform vec4 lighting;

attribute vec2 aPosition;
attribute vec4 Col;

varying vec4 vColor;
varying vec3 vTexcoordCloudsSky;


void main() {
    vec2 cospos = cos(aPosition*brightnessAndSize.y);
    vec2 sinpos = sin(aPosition*brightnessAndSize.y);
    vec4 aNormal = vec4(rotation*vec3(vec2(cospos.x, sinpos.x)*cospos.y, sinpos.y)*1.005, 1.0);
	vec4 vPosition = modelviewMatrix*aNormal;
 	vec3 vNormal = (modelviewMatrix*vec4(aNormal.xyz,0.0)).xyz;
    float haze = 1.0+dot(vNormal, normalize(vPosition.xyz))*0.7;
	vColor = (Col*brightnessAndSize.x*2.0)*(1.0-haze)*lighting.y*4.0*vec4(1.1,1.0,1.2,1.0);


    vTexcoordCloudsSky = mix(aNormal.xyz, reflect(
            -normalize((vec4(vPosition.xyz,0.0)*modelviewMatrix).xyz), aNormal.xyz), 0.025);

    gl_Position = projectionMatrix*vPosition;
}

//FRAGMENT
precision mediump float;

uniform samplerCube texCloudNormals;
varying mediump vec4 vColor;
varying mediump vec3 vTexcoordCloudsSky;

void main() {
    vec4 cloudNormal = textureCube(texCloudNormals, vTexcoordCloudsSky);
    float cloudDensity = dot(cloudNormal.xyz-0.5,cloudNormal.xyz-0.5);

	gl_FragColor =vColor*5.0;//*cloudDensity;
}

