#define AMPLITUDE 1.2

#define RIPPLE_AMPLITUDE 1.0
#define FREQUENCY 0.1
#define PI 3.14285714286
#define STEP 1.0
//0.001;

attribute vec4 a_Position;
attribute vec2 a_TextureCoordinates;
uniform mat4 u_Matrix;
uniform mat4 it_Matrix;
//uniform mat4 u_modelMatrix;
uniform float time;
uniform vec2 customPoint;
uniform mat4 cameraTexmat;
varying vec2 v_TextureCoordinates;
varying vec4 normalVector;
varying float isRange;

vec4 getPositon(vec4 originPosition){
    vec4 VertexCoord = originPosition;
    //	VertexCoord = u_modelMatrix * VertexCoord;
    //	VertexCoord = VertexCoord - vec4(-1.0, 1.0, 59.655617, 0.0);
    //    VertexCoord = VertexCoord - vec4(0.95, 0.0, 0.57, 0.0);
        float distance = length(VertexCoord);

    //    vec4 rotateResult = u_modelMatrix * vec4(customPoint, 0.0);
    //	float secondDistance = length(VertexCoord - vec4(4.0, 0.0, 3.655617, 0.0));
        float secondDistance = length(VertexCoord - vec4(customPoint.x, 0.0, customPoint.y, 0.0));
       // float secondDistance = length(vec4(VertexCoord.x, VertexCoord.y, VertexCoord.z, 0.0) - vec4(customPoint.x, customPoint.y, customPoint.z, 0.0));
        float centerCount = 0.0;
        float vertextY = 0.0;

    //    if(time == -1.0){//表示一个波浪停止了
    //
    //	}else
    //	{
//             if(distance < time*10.0){
//                  vertextY += sin( 2.0 * PI * distance * (FREQUENCY + time / 2.0))* (RIPPLE_AMPLITUDE - time);
//                  centerCount = centerCount + 1.0;
//             }
             if(secondDistance < time *10.0){
                  vertextY += sin( 2.0 * PI * secondDistance * (FREQUENCY + time / 2.0))* (RIPPLE_AMPLITUDE - time);
                  centerCount = centerCount + 1.0;
             }
    //	}


        if(centerCount == 2.0){
            vertextY = vertextY / 2.0;
        }else if(centerCount == 1.0){

        }

        VertexCoord.y += vertextY;
        return VertexCoord;
}

float getOffsetPosition(vec4 position){
        vec4 VertexCoord = position;
        float distance = length(VertexCoord);
        float secondDistance = length(VertexCoord - vec4(customPoint.x, 0.0, customPoint.y, 0.0));
        float centerCount = 0.0;
        float vertextY = 0.0;
        float maxDistance = time*10.0;

             if(distance < time*10.0){
//                  vertextY += sin( 2.0 * PI * distance * (FREQUENCY + time / 2.0))* (RIPPLE_AMPLITUDE - time);
//                  centerCount = centerCount + 1.0;

//                   if(maxDistance - distance <= maxDistance*0.2){
//                        vertextY = vertextY / 2.0;
//                   }
                    return 1.0;
             }
             if(secondDistance < time*10.0){
//                  vertextY += sin( 2.0 * PI * secondDistance * (FREQUENCY + time / 2.0))* (RIPPLE_AMPLITUDE - time);
//                  centerCount = centerCount + 1.0;

//                  if(maxDistance - secondDistance <= maxDistance*0.2){
//                       vertextY = vertextY / 2.0;
//                  }
                    return 1.0;
             }

//        if(centerCount == 2.0){
//            vertextY = vertextY / 2.0;
//        }else if(centerCount == 1.0){
//
//        }

//        if(time >= 5.0){
//            vertextY = 0.0;
//        }

//        VertexCoord.y += vertextY;
//        return VertexCoord;
          return 0.0;
}

void main()
{
	v_TextureCoordinates = a_TextureCoordinates;

//	v_TextureCoordinates = (cameraTexmat * vec4(a_TextureCoordinates, 1.0, 1.0)).xy;
//    v_TextureCoordinates = a_TextureCoordinates.xy;

	/*vec4 VertexCoord = a_Position;
//	VertexCoord = u_modelMatrix * VertexCoord;
//	VertexCoord = VertexCoord - vec4(-1.0, 1.0, 59.655617, 0.0);
//    VertexCoord = VertexCoord - vec4(0.95, 0.0, 0.57, 0.0);
	float distance = length(VertexCoord);

//    vec4 rotateResult = u_modelMatrix * vec4(customPoint, 0.0);
//	float secondDistance = length(VertexCoord - vec4(4.0, 0.0, 3.655617, 0.0));
    float secondDistance = length(VertexCoord - vec4(customPoint.x, 0.0, customPoint.y, 0.0));
   // float secondDistance = length(vec4(VertexCoord.x, VertexCoord.y, VertexCoord.z, 0.0) - vec4(customPoint.x, customPoint.y, customPoint.z, 0.0));
    float centerCount = 0.0;
    float vertextY = 0.0;

//    if(time == -1.0){//表示一个波浪停止了
//
//	}else
//	{
	     if(distance < time*10.0){
        	  vertextY += sin( 2.0 * PI * distance * (FREQUENCY + time / 2.0))* (RIPPLE_AMPLITUDE - time);
        	  centerCount = centerCount + 1.0;
         }
         if(secondDistance < time*10.0){
        	  vertextY += sin( 2.0 * PI * secondDistance * (FREQUENCY + time / 2.0))* (RIPPLE_AMPLITUDE - time);
        	  centerCount = centerCount + 1.0;
         }
//	}


//    if(centerCount == 2.0){
//        vertextY = vertextY / 2.0;
//    }else if(centerCount == 1.0){
//
//    }

    VertexCoord.y += vertextY;*/

    vec4 VertexCoord = getPositon(a_Position);

    vec4 leftCoord = a_Position;
    leftCoord.x = leftCoord.x - STEP;
    leftCoord = getPositon(leftCoord);
    vec3 left = vec3(leftCoord.x, leftCoord.y, leftCoord.z);

    vec4 rightCoord = a_Position;
    rightCoord.x = rightCoord.x + STEP;
    rightCoord = getPositon(rightCoord);
    vec3 right = vec3(rightCoord.x, rightCoord.y, rightCoord.z);

    vec4 topCoord = a_Position;
    topCoord.z = topCoord.z + STEP;
    topCoord = getPositon(topCoord);
    vec3 top = vec3(topCoord.x, topCoord.y, topCoord.z);

    vec4 downCoord = a_Position;
    downCoord.z = downCoord.z - STEP;
    downCoord = getPositon(downCoord);
    vec3 down = vec3(downCoord.x, downCoord.y, downCoord.z);

    vec3 horizonVector = left - right;
    vec3 verticalVector = top - down;
    //cross(horizonVector, verticalVector);
    vec4 normal = vec4(cross(horizonVector, verticalVector), 0.0);
    normalVector = normalize(it_Matrix * normal);
//    normalVector = vec3(0.3, 0.6, 0.4);


  /*  vec4 leftCoord = a_Position;
    leftCoord.x = leftCoord.x - STEP;
    leftCoord = getPositon(leftCoord);
    vec3 left = vec3(leftCoord.x, leftCoord.y, leftCoord.z);

//    vec4 rightCoord = a_Position;
//    rightCoord.x = rightCoord.x + STEP;
//    rightCoord = getPositon(rightCoord);
//    vec3 right = vec3(rightCoord.x, rightCoord.y, rightCoord.z);

    vec4 topCoord = a_Position;
    topCoord.z = topCoord.z + STEP;
    topCoord = getPositon(topCoord);
    vec3 top = vec3(topCoord.x, topCoord.y, topCoord.z);

//    vec4 downCoord = a_Position;
//    downCoord.z = downCoord.z - STEP;
//    downCoord = getPositon(downCoord);
//    vec3 down = vec3(downCoord.x, downCoord.y, downCoord.z);

    vec3 currentPoint = vec3(VertexCoord.x, VertexCoord.y, VertexCoord.z);
    vec3 horizonVector = currentPoint - left;
    vec3 verticalVector = currentPoint - top;
    //cross(horizonVector, verticalVector);
    vec4 normal = vec4(cross(horizonVector, verticalVector), 0.0);
    normalVector = it_Matrix * normal;*/

    isRange = getOffsetPosition(a_Position);

    gl_Position = u_Matrix * VertexCoord;
}
