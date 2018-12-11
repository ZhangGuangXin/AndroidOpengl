precision highp float;


varying vec2 v_TextureCoordinates;
uniform sampler2D u_TextureUnit;
vec3 lum = vec3(0.2126, 0.7152, 0.0722);
float p00,p10,p20,p01,p21,p02,p12,p22,x,y,px,py,distance;

void main()
{
    float x = 1.0/256.0;
    float y = 1.0/256.0;
    p00 = dot(texture2D(u_TextureUnit, v_TextureCoordinates+vec2(-x, y)).rgb, lum);
    p10 = dot(texture2D(u_TextureUnit, v_TextureCoordinates+vec2(-x,0.)).rgb, lum);
    p20 = dot(texture2D(u_TextureUnit, v_TextureCoordinates+vec2(-x,-y)).rgb, lum);
    p01 = dot(texture2D(u_TextureUnit, v_TextureCoordinates+vec2(0., y)).rgb, lum);
    p21 = dot(texture2D(u_TextureUnit, v_TextureCoordinates+vec2(0.,-y)).rgb, lum);
    p02 = dot(texture2D(u_TextureUnit, v_TextureCoordinates+vec2( x, y)).rgb, lum);
    p12 = dot(texture2D(u_TextureUnit, v_TextureCoordinates+vec2( x,0.)).rgb, lum);
    p22 = dot(texture2D(u_TextureUnit, v_TextureCoordinates+vec2( x,-y)).rgb, lum);
    // Apply Sobel Operator
    px = p00 + 1.0*p10 + p20 - (p02 + 1.0*p12 + p22);
    py = p00 + 1.0*p01 + p02 - (p20 + 1.0*p21 + p22);

    distance = px*px+py*py;
//    gl_FragColor = vec4(distance, distance, distance, 1.0);

    // Check frequency change with given threshold
    if ((distance = px*px+py*py) > 0.15 ){
        gl_FragColor = vec4(0.0, 1.0, 0.0, 1.0);
    }else{
//     gl_FragColor = vec4(1.0);
        vec4 TextureFragile = texture2D(u_TextureUnit, v_TextureCoordinates);
        gl_FragColor = TextureFragile;
     }


//    vec4 TextureFragile = texture2D(u_TextureUnit, v_TextureCoordinates);
//    gl_FragColor = TextureFragile;
}