package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Lines extends FilterRender {
    private static float f6566E = 0.0f;
    private static float f6567F = (MyApplication.imgWidth / 2.0f);
    private static float f6568I = 0.0f;
    private static float f6569J = (MyApplication.imgHeight / 2.0f);
    public static boolean f6570v = false;
    private float f6571A;
    private long f6572B = System.currentTimeMillis();
    private String f6573C = "touchX";
    private int f6574D;
    private String f6575G = "touchY";
    private int f6576H;
    private long f6577K;
    private int f6578w;
    private int f6579x;
    private String f6580y = "iTime";
    private int f6581z;

    public Lines() {
        setFragmentShader("precision lowp float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvoid main(){\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*1.0;\nfloat amounty = 0.0-mouse.y/iResolution.y*0.05;\nvec4 fg = vec4(0.08, 0.07, 0.08, 1.0);\nvec4 bg = vec4(0.5, 0.14, 0.1, 1.0);\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nfloat t1 = fract(amounty);\nif(sin(1.0 + t1 * 3.0 + tan(gl_FragCoord.y * (amounty*0.4*sin(iTime/5.))) - sqrt(gl_FragCoord.y * t1)) >= 0.7){\nfloat y = uv.y * 12.0;\nif(y - floor(y) >= 0.5){\nuv.x += amountx*0.1;\n}else{\nuv.x -= amountx*0.1;\n}\n}\ngl_FragColor.r = texture2D(inputImageTexture, uv+vec2(amountx*0.04,0.0)).r;\ngl_FragColor.gb = texture2D(inputImageTexture, uv).gb;\n}");
        this.f6577K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6581z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6580y);
        this.f6574D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6573C);
        this.f6576H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6575G);
        this.f6578w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6579x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6581z, this.f6571A);
        GLES20.glUniform1f(this.f6574D, f6566E);
        GLES20.glUniform1f(this.f6576H, f6568I);
        GLES20.glUniform1f(this.f6578w, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6579x, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f6570v) {
            this.f6572B = System.currentTimeMillis() - this.f6577K;
            if (this.f6572B > 20000) {
                this.f6577K = System.currentTimeMillis();
            }
        }
        this.f6571A = (((float) this.f6572B) / 1000.0f) * 2.0f * 3.14159f * 0.05f;
        f6566E = f6567F;
        f6568I = f6569J;
    }

    public static void change(float f, float f2) {
        f6567F = f;
        f6569J = f2;
    }
}
