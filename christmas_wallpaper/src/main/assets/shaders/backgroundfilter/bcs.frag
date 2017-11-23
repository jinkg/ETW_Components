#ifdef GL_ES
precision highp float;
#define PRECISION highp
#else
#define PRECISION
#endif

varying PRECISION vec2 vTexcoord;
uniform sampler2D colorTexture;
uniform vec3 bcsParams;
uniform vec3 bcsSaturation;

vec3 rgb2hsv(vec3 c)
{
    vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
    vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));
    vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));

    float d = q.x - min(q.w, q.y);
    float e = 1.0e-10;
    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}

vec3 hsv2rgb(vec3 c)
{
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

void main() {
	vec3 color = texture2D(colorTexture, vTexcoord).rgb;
	vec3 hsv = rgb2hsv(color);
	hsv[1] *= max(hsv[2]*4.0*bcsParams[2]+1.0,0.0);
	gl_FragColor = vec4(clamp((hsv2rgb(hsv)+bcsParams[0]) * bcsParams[1] + 0.50,0.0,1.0),1.0);
}

