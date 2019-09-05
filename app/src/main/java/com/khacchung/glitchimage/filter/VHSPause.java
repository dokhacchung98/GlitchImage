package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class VHSPause extends FilterRender {
    private static float f6222E = 0.0f;
    private static float f6223F = 0.0f;
    private static float f6224I = 0.0f;
    private static float f6225J = 0.0f;
    public static boolean f6226v = false;
    private float f6227A;
    private long f6228B = System.currentTimeMillis();
    private String f6229C = "touchX";
    private int f6230D;
    private String f6231G = "touchY";
    private int f6232H;
    private long f6233K;
    private int f6234w;
    private int f6235x;
    private String f6236y = "iTime";
    private int f6237z;

    public VHSPause() {
        setFragmentShader("precision highp float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat rand(vec2 co){\nreturn fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);\n}\nvoid main(){\nvec4 texColor = vec4(0);\nvec2 samplePosition = gl_FragCoord.xy / iResolution.xy;\nif(touchY > 0.0){\nfloat whiteNoise = 9999.0;\nsamplePosition.x = samplePosition.x+(rand(vec2(iTime,gl_FragCoord.y))-0.5)/64.0;\nsamplePosition.y = samplePosition.y+(rand(vec2(iTime))-0.5)/32.0;\ntexColor = texColor + (vec4(-0.5)+vec4(rand(vec2(gl_FragCoord.y,iTime)),rand(vec2(gl_FragCoord.y,iTime+1.0)),rand(vec2(gl_FragCoord.y,iTime+2.0)),0))*0.1;\nwhiteNoise = rand(vec2(floor(samplePosition.y*80.0),floor(samplePosition.x*50.0))+vec2(iTime,0));\nif (whiteNoise > 11.5-30.0*samplePosition.y || whiteNoise < 1.5-5.0*samplePosition.y) {\ntexColor = texColor + texture2D(inputImageTexture,samplePosition);\n} else {\ntexColor = vec4(1);\n}\n}else{\ntexColor = texture2D(inputImageTexture, samplePosition);\n}\ngl_FragColor = texColor;\n}");
        this.f6233K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6237z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6236y);
        this.f6230D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6229C);
        this.f6232H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6231G);
        this.f6234w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6235x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6237z, this.f6227A);
        GLES20.glUniform1f(this.f6230D, f6222E);
        GLES20.glUniform1f(this.f6232H, f6224I);
        GLES20.glUniform1f(this.f6234w, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6235x, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f6226v) {
            this.f6228B = System.currentTimeMillis() - this.f6233K;
            if (this.f6228B > 1000) {
                this.f6233K = System.currentTimeMillis();
            }
        }
        this.f6227A = (((float) this.f6228B) / 500.0f) * 2.0f * 3.14159f * 0.1f;
        f6222E = f6223F;
        f6224I = f6225J;
    }

    public static void change(float f, float f2) {
        f6223F = f;
        f6225J = f2;
    }
}
