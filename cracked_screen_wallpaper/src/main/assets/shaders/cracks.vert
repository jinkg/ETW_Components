uniform mat4 projectionMatrix;
uniform mat4 modelviewMatrix;

attribute vec3 aPosition;
attribute vec3 aNormal;
attribute vec2 aTexcoord;

varying vec3 vViewDirection;
varying vec3 vLightDir;
varying vec3 vReflectedLightDir;
varying float vSpecular;
varying float vTransparency;

uniform vec4 crackTS;


void main()
{
	vec3 vPosition = vec3(aPosition.xy * crackTS.z + crackTS.xy, aPosition.z-30.0);

	vViewDirection = normalize(vec3(projectionMatrix[3].x+5.0,projectionMatrix[3].y-5.0,-30.0));
	vLightDir = vec3(aPosition.x,aPosition.y,aPosition.z-0.1);
	vReflectedLightDir = normalize(reflect(vLightDir, aNormal));

	vSpecular = max(0.0, -dot(vReflectedLightDir, vViewDirection));
	vTransparency = abs(vSpecular-0.5)*crackTS.w;
	vSpecular *= 5.0;
		
    gl_Position = projectionMatrix*vec4(vPosition, 1.0);
}
