//VERTEX
uniform mat4  projectionMatrix;
uniform mat4  modelviewMatrix;
uniform float progress;

attribute vec4 aPosition;

varying vec2 vTexcoord;
varying vec4 vColor;

void main()
{
	vTexcoord = aPosition.xy*0.5+0.5;
	vColor.rgb = vec3(0.5)+vec3(0.5)*smoothstep(aPosition.z-0.01, aPosition.z+0.01, progress);
	vColor.a   = clamp(1.5-abs(0.5-progress), 0.0, 1.0);
    gl_Position = projectionMatrix*modelviewMatrix*vec4(aPosition.xy, 0.0, 1.0);
}

//FRAGMENT
precision mediump float;

uniform sampler2D tex;
varying vec2 vTexcoord;
varying vec4 vColor;

void main() {
	gl_FragColor = texture2D(tex, vTexcoord) * vColor;
}

