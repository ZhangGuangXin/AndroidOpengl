precision highp float;


varying vec2 v_TextureCoordinates;
uniform sampler2D u_TextureUnit;
vec2 xy, uv;

vec2 barrelDistorition(vec2 p){
    float theta = atan(p.y, p.x);
    float radius = sqrt(p.x*p.x + p.y*p.y);
    radius = pow(radius,  0.955);
    p.x = radius * cos(theta);
    p.y = radius * sin(theta);
    return p + 0.5;
}

void main()
{
      xy = v_TextureCoordinates - vec2(0.5);
      float distance = sqrt(xy.x * xy.x + xy.y * xy.y);
      if(distance > 0.35){
           vec4 TextureFragile = vec4(0.0, 0.0, 0.0, 0.0);
           gl_FragColor = TextureFragile;
      }else{
           uv = barrelDistorition(xy);
           vec4 TextureFragile = texture2D(u_TextureUnit, uv);
           gl_FragColor = TextureFragile;
      }

}