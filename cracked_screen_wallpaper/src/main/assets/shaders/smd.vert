uniform mat4 projectionMatrix;
uniform mat4 modelviewMatrix;

attribute vec3 aPosition;
attribute vec3 aNormal;
attribute vec2 aTexcoord;

varying vec2 vTexcoord;

varying vec3 vViewDirection;
varying vec3 vReflectedLightDir;

uniform vec3 circuitTS;
uniform vec3 lightTS;

void main()
{
	vec3 vPosition = aPosition * circuitTS.z + vec3(circuitTS.xy, -30.2);
	
	vViewDirection = normalize(vec3(projectionMatrix[3].x,projectionMatrix[3].y,-30.0));
	vec3 vLightDir = vPosition - vec3(lightTS.x,lightTS.y,-28.0);
	vReflectedLightDir = reflect(vLightDir, aNormal);
		
	vTexcoord = aTexcoord;
    gl_Position = projectionMatrix*vec4(vPosition, 1.0);
    gl_Position.z += 0.1;
}
