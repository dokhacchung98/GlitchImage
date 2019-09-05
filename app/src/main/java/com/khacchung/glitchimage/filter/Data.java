package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Data extends FilterRender {
    private static float f6410E = 0.0f;
    private static float f6411F = (MyApplication.imgWidth / 2.0f);
    private static float f6412I = 0.0f;
    private static float f6413J = (MyApplication.imgHeight / 2.0f);
    public static boolean f6414v = false;
    private float f6415A;
    private long f6416B = System.currentTimeMillis();
    private String f6417C = "touchX";
    private int f6418D;
    private String f6419G = "touchY";
    private int f6420H;
    private long f6421K;
    private int f6422w;
    private int f6423x;
    private String f6424y = "iTime";
    private int f6425z;

    public Data() {
        setFragmentShader("precision lowp float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat random2d(vec2 n) { \nreturn fract(sin(dot(n, vec2(12.9898, 4.1414))) * 1.0);\n}\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\n    float amountx = 1.0+mouse.x/iResolution.x*100.;\namountx = 100.-amountx;\n    float amounty = 1.0+mouse.y/iResolution.y*100.;\n    \n    //Speed 0-1\n    float speed = 1.0;\n    \n    //Global time\n    float time = floor(iTime*(speed*60.0));\n    \n    //Coordinates\n    vec2 uv = gl_FragCoord.xy/iResolution.xy;\n    \n    //Input texture\n   \tvec4 col = texture2D(inputImageTexture, uv);\n    \n    //Every block\n    for (float i = 0.0; i < 5.-(amounty/20.); i += 1.0) {\n        \n        //Get random every block\n        float rnd = random2d(vec2(time , float(i*100.)));\n\n        //Random distance each block\n        float dist = (i/0.1+(rnd*10.))+amounty-10.;\n\n        //Progressive Y \n        float offset = (amountx/50.*i/10.)+(rnd/10.);\n\n        //Color goes dark\n        float c = (0.7-(i/6.))+(rnd/2.);\n\n        //New texture\n\t\tvec4 bg = texture2D(inputImageTexture, uv + vec2(offset, 0.0))*c;\n\n        //Black space\n        bg = mix(vec4(0.0), bg, clamp(4.-floor(offset*510.)+(1.-uv.x)*500., 0., 1.));\n        \n        float f = clamp(4.-floor(dist)*4.+(1.-uv.y)*300. , 0., 1.);\n\n        float f2 = (rnd>uv.x)?1.:0.;\n\n        vec4 rowColor = mix(bg, col, f2);\n\n        col = mix(col, rowColor, f);\n\n        f = clamp(4.-floor(dist+2.)*4.+(1.-uv.y)*300., 0., 1.);\n\n        col = mix(col, bg, f);\n        \n    }\n    \n    //Moire\n    gl_FragColor = col;\n}");
        this.f6421K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6425z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6424y);
        this.f6418D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6417C);
        this.f6420H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6419G);
        this.f6422w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6423x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6425z, this.f6415A);
        GLES20.glUniform1f(this.f6418D, f6410E);
        GLES20.glUniform1f(this.f6420H, f6412I);
        GLES20.glUniform1f(this.f6422w, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6423x, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f6414v) {
            this.f6416B = System.currentTimeMillis() - this.f6421K;
            if (this.f6416B > 2000) {
                this.f6421K = System.currentTimeMillis();
            }
        }
        this.f6415A = (((float) this.f6416B) / 1000.0f) * 2.0f * 3.14159f * 0.75f;
        f6410E = f6411F;
        f6412I = f6413J;
    }

    public static void change(float f, float f2) {
        f6411F = f;
        f6413J = f2;
    }
}
