//VERTEX
uniform mat4 projectionMatrix;
uniform mat4 modelviewMatrix;
uniform float time;
uniform vec4 lighting;
uniform mat3 auroraColors;

attribute vec3 aColor;
attribute vec3 aPosition;
attribute vec3 aPositionNext;
attribute vec3 aPositionPrev;
attribute vec3 aNormal;
attribute vec3 aNormalNext;
attribute vec3 aNormalPrev;
attribute vec4 aTexcoord;

varying highp vec2 vTexcoord;
varying vec4 vColor;

highp vec3 getModulated(vec3 pos, vec3 normal, vec2 tex) {
	float lateral = (sin(time+tex.x)+0.2)*0.01 *(1.0- tex.y) - (sin(time+tex.x)-1.0)*0.005 * (tex.y)-0.003;
	float transversal = sin(time+tex.x*4.5)*0.01;

	return pos * lateral + normal * transversal + pos;
}

void main()
{

	vec3 pos = getModulated(aPosition, aNormal, aTexcoord.xy);
	vec3 posNext = getModulated(aPositionNext, aNormalNext, aTexcoord.zy);
	vec3 posPrev = getModulated(aPositionPrev, aNormalPrev, aTexcoord.wy);
	
	vec3 vNormal1 = normalize((modelviewMatrix*vec4(cross(pos, posNext - pos), 0.0)).xyz);
	vec3 vNormal2 = normalize((modelviewMatrix*vec4(cross(pos, pos - posPrev), 0.0)).xyz);
	

	vColor = vec4(auroraColors * aColor, 1.0);
	vTexcoord = aTexcoord.xy + vec2(sin(time+aTexcoord.x*4.0)*0.1, 0.0);

	vec4 vPosition = modelviewMatrix*vec4(pos, 1.0);
	vec3 vNormal = (modelviewMatrix*vec4(pos, 0.0)).xyz;
	
	float l = length(vPosition.xyz);
	
	float d1 = dot(vPosition.xyz/l, vNormal1);
	float d2 = dot(vPosition.xyz/l, vNormal2);
	float d = dot(vPosition.xyz/l, vNormal);
	vColor *= smoothstep(0.0,0.5, abs(d1*d2)) * step(0.0, d1*d2) * smoothstep(0.1, -0.1, d);
	
	
    gl_Position = projectionMatrix*vPosition;
}

//FRAGMENT
precision mediump float;

varying highp vec2 vTexcoord;
varying mediump vec4 vColor;
uniform sampler2D tex;

void main() {
	gl_FragColor = texture2D(tex, vTexcoord)*vColor;
}

