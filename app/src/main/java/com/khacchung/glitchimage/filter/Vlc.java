package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Vlc extends FilterRender {
    private static float f6254A = (MyApplication.imgWidth / 2.0f);
    private static float f6255D;
    private static float f6256E = (MyApplication.imgHeight / 2.0f);
    private static float f6257z;
    private String f6258B = "touchY";
    private int f6259C;
    private int f6260v;
    private int f6261w;
    private String f6262x = "touchX";
    private int f6263y;

    public Vlc() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\n#define r iResolution.xy\n#define P(id,a,b,c,d,e,f,g,h) if( id == int(pos.y) ){ int pa = a+2*(b+2*(c+2*(d+2*(e+2*(f+2*(g+2*(h))))))); cha = floor(mod(float(pa)/pow(2.,float(pos.x)-1.),2.)); }\nfloat gray(vec3 _i) {\n    return _i.x*0.299+_i.y*0.587+_i.z*0.114;\n}\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 1.0+mouse.x/iResolution.x*20.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\n    vec2 uv = vec2(floor(gl_FragCoord.x/8./amountx)*8.*amountx,floor(gl_FragCoord.y/12./amountx)*12.*amountx)/r;\n    ivec2 pos = ivec2(mod(gl_FragCoord.x/amountx,8.),mod(gl_FragCoord.y/amountx,12.));\n    vec4 tex = texture2D(inputImageTexture,uv);\n    float cha = 0.;\n    \n    float g = gray(tex.xyz);\n    if( g < .125 )\n    {\n        P(11,0,0,0,0,0,0,0,0);\n        P(10,0,0,0,0,0,0,0,0);\n        P(9,0,0,0,0,0,0,0,0);\n        P(8,0,0,0,0,0,0,0,0);\n        P(7,0,0,0,0,0,0,0,0);\n        P(6,0,0,0,0,0,0,0,0);\n        P(5,0,0,0,0,0,0,0,0);\n        P(4,0,0,0,0,0,0,0,0);\n        P(3,0,0,0,0,0,0,0,0);\n        P(2,0,0,0,0,0,0,0,0);\n        P(1,0,0,0,0,0,0,0,0);\n        P(0,0,0,0,0,0,0,0,0);\n    }\n    else if( g < .25 ) // .\n    {\n        P(11,0,0,0,0,0,0,0,0);\n        P(10,0,0,0,0,0,0,0,0);\n        P(9,0,0,0,0,0,0,0,0);\n        P(8,0,0,0,0,0,0,0,0);\n        P(7,0,0,0,0,0,0,0,0);\n        P(6,0,0,0,0,0,0,0,0);\n        P(5,0,0,0,0,0,0,0,0);\n        P(4,0,0,1,1,1,0,0,0);\n        P(3,0,0,1,1,1,0,0,0);\n        P(2,0,0,0,0,0,0,0,0);\n        P(1,0,0,0,0,0,0,0,0);\n        P(0,0,0,0,0,0,0,0,0);\n    }\n    else if( g < .375 ) // ;\n    {\n        P(11,0,0,0,0,0,0,0,0);\n        P(10,0,0,0,0,0,0,0,0);\n        P(9,0,0,0,0,0,0,0,0);\n        P(8,0,0,1,1,1,0,0,0);\n        P(7,0,0,1,1,1,0,0,0);\n        P(6,0,0,0,0,0,0,0,0);\n        P(5,0,0,0,0,0,0,0,0);\n        P(4,0,0,1,1,1,0,0,0);\n        P(3,0,0,1,1,1,0,0,0);\n        P(2,0,0,0,1,1,0,0,0);\n        P(1,0,0,1,1,0,0,0,0);\n        P(0,0,0,0,0,0,0,0,0);\n    }\n    else if( g < .5 ) // X\n    {\n        P(11,0,0,0,0,0,0,0,0);\n        P(10,1,1,0,0,1,1,0,0);\n        P(9,1,1,0,0,1,1,0,0);\n        P(8,1,1,0,0,1,1,0,0);\n        P(7,0,1,1,1,1,0,0,0);\n        P(6,0,0,1,1,0,0,0,0);\n        P(5,0,1,1,1,1,0,0,0);\n        P(4,1,1,0,0,1,1,0,0);\n        P(3,1,1,0,0,1,1,0,0);\n        P(2,1,1,0,0,1,1,0,0);\n        P(1,0,0,0,0,0,0,0,0);\n        P(0,0,0,0,0,0,0,0,0);\n    }\n    else if(g < .625 ) // %\n    {\n        P(11,0,0,0,0,0,0,0,0);\n        P(10,0,0,0,0,0,0,0,0);\n        P(9,1,1,0,0,0,1,0,0);\n        P(8,1,1,0,0,1,1,0,0);\n        P(7,0,0,0,1,1,0,0,0);\n        P(6,0,0,1,1,0,0,0,0);\n        P(5,0,1,1,0,0,0,0,0);\n        P(4,1,1,0,0,1,1,0,0);\n        P(3,1,0,0,0,1,1,0,0);\n        P(2,0,0,0,0,0,0,0,0);\n        P(1,0,0,0,0,0,0,0,0);\n        P(0,0,0,0,0,0,0,0,0);\n    }\n    else if(g < .75 ) // t\n    {\n        P(11,0,0,0,0,0,0,0,0);\n        P(10,0,0,1,0,0,0,0,0);\n        P(9,0,1,1,0,0,0,0,0);\n        P(8,1,1,1,1,1,1,0,0);\n        P(7,0,1,1,0,0,0,0,0);\n        P(6,0,1,1,0,0,0,0,0);\n        P(5,0,1,1,0,0,0,0,0);\n        P(4,0,1,1,0,1,1,0,0);\n        P(3,0,0,1,1,1,0,0,0);\n        P(2,0,0,0,0,0,0,0,0);\n        P(1,0,0,0,0,0,0,0,0);\n        P(0,0,0,0,0,0,0,0,0);\n    }\n    else if(g < .875 ) // 8\n    {\n        P(11,0,0,0,0,0,0,0,0);\n        P(10,0,1,1,1,1,1,0,0);\n        P(9,1,1,0,0,0,1,1,0);\n        P(8,1,1,0,0,0,1,1,0);\n        P(7,1,1,1,0,0,1,1,0);\n        P(6,0,1,1,1,1,1,0,0);\n        P(5,1,1,0,0,1,1,1,0);\n        P(4,1,1,0,0,0,1,1,0);\n        P(3,1,1,0,0,0,1,1,0);\n        P(2,0,1,1,1,1,1,0,0);\n        P(1,0,0,0,0,0,0,0,0);\n        P(0,0,0,0,0,0,0,0,0);\n    }\n    else // @\n    {\n        P(11,0,0,0,0,0,0,0,0);\n        P(10,0,1,1,1,1,1,0,0);\n        P(9,1,1,0,0,0,1,1,0);\n        P(8,1,1,0,0,0,1,1,0);\n        P(7,1,1,0,1,1,1,1,0);\n        P(6,1,1,0,1,1,1,1,0);\n        P(5,1,1,0,1,1,1,1,0);\n        P(4,1,1,0,0,0,0,0,0);\n        P(3,1,1,0,0,0,0,0,0);\n        P(2,1,1,0,0,0,0,0,0);\n        P(1,0,1,1,1,1,1,0,0);\n        P(0,0,0,0,0,0,0,0,0);\n    }\n    vec4 tex1 = texture2D(inputImageTexture,uv+vec2(0.0,0.0));\n    vec3 col = tex.xyz*max(tex.x,max(tex.y,tex.z));\n    vec3 col1 = tex1.xyz/max(tex1.x,max(tex1.y,tex1.z));\n    vec4 pixel = vec4(col,1.);\n    vec4 ascii = vec4(col1*cha,1.);\n    gl_FragColor = mix(pixel, ascii, 0.5);\n}");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6263y = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6262x);
        this.f6259C = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6258B);
        this.f6260v = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6261w = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6263y, f6257z);
        GLES20.glUniform1f(this.f6259C, f6255D);
        GLES20.glUniform1f(this.f6260v, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6261w, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f6257z = f6254A;
        f6255D = f6256E;
    }

    public static void change(float f, float f2) {
        f6254A = f;
        f6256E = f2;
    }
}
