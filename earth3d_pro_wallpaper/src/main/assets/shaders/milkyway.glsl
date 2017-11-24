//VERTEX
uniform mat4 projectionMatrix;
uniform mat4 modelviewMatrix;

attribute vec4 aNormal;
attribute vec4 aTexcoords;

varying vec2 vTexcoord;

void main()
{
	vTexcoord = aTexcoords.xy;
    gl_Position = projectionMatrix*(modelviewMatrix*(aNormal*vec4(1.0/1.9, 1.0/1.9, 1.0/1.9, 1.0)));
}

//FRAGMENT
precision mediump float;

varying mediump vec2 vTexcoord;
uniform sampler2D tex;
uniform mediump vec2 params;
uniform mediump vec2 lighting;

void main() {
	gl_FragColor.rgb = texture2D(tex, vTexcoord).rgb*lighting.x+lighting.y;
}

