//VERTEX
uniform mat4  projectionMatrix;
uniform float progress;

attribute vec4 aPosition;

varying vec2 vTexcoord;
varying vec4 vColor;

void main()
{
	vTexcoord = aPosition.xy*0.5+0.5;
	vColor.rgb = vec3(0.5)+vec3(0.5)*smoothstep(aPosition.z-0.01, aPosition.z+0.01, progress);
	vColor.a   = clamp(1.5-abs(0.5-progress), 0.0, 1.0);
    gl_Position = projectionMatrix*vec4(aPosition.xy,-10.0, 1.0);
}

//FRAGMENT
#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D tex;
varying vec2 vTexcoord;
varying vec4 vColor;

void main() {
	gl_FragColor = texture2D(tex, vTexcoord) * vColor;
}

