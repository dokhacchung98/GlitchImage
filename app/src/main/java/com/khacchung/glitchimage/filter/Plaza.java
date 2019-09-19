package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Plaza extends FilterRender {
    private static float f6078E = 0.0f;
    private static float f6079F = (MyApplication.imgWidth / 2.0f);
    private static float f6080I = 0.0f;
    private static float f6081J = (MyApplication.imgHeight / 2.0f);
    public static boolean f6082v = false;
    private float f6083A;
    private long f6084B = System.currentTimeMillis();
    private String f6085C = "touchX";
    private int f6086D;
    private String f6087G = "touchY";
    private int f6088H;
    private long f6089K;
    private int f6090w;
    private int f6091x;
    private String f6092y = "iTime";
    private int f6093z;

    public Plaza() {
        setFragmentShader("precision highp float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat rng2(vec2 seed){\nreturn fract(sin(dot(seed * floor(iTime * 60.), vec2(127.1,311.7))) * 43758.5453123);\n}\nfloat rng(float seed){\nreturn rng2(vec2(seed, 1.0));\n}\nvoid main(){\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0+mouse.x/iResolution.x*1.;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.;\namounty = 1.0-amounty;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec2 blockS = floor(uv * vec2(iResolution.x/70., iResolution.y/70.));\nvec2 blockL = floor(uv * vec2(iResolution.x/70., iResolution.y/70.));\nfloat lineNoise = pow(rng2(blockS), 1.0) * pow(rng(7.2341), 1.0);\nif( uv.x > amountx-0.3*rng(10.) && uv.x < amountx+0.3*rng(11.)){\nif( uv.y > amounty-0.3*rng(13.) && uv.y < amounty+0.3*rng(12.)){  \nvec4 col1 = texture2D(inputImageTexture, uv);\n vec4 col2 = texture2D(inputImageTexture, uv + vec2(lineNoise * 0.25 * rng(5.0), 0));\nvec4 col3 = texture2D(inputImageTexture, uv - vec2(lineNoise * 0.25 * rng(31.0), 0));\ngl_FragColor = vec4(vec3(col1.x, col2.y, col3.z), 1.0);\n}else{\ngl_FragColor = texture2D(inputImageTexture, uv);\n}\n}else{\ngl_FragColor = texture2D(inputImageTexture, uv);\n}\n}");
        this.f6089K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6093z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6092y);
        this.f6086D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6085C);
        this.f6088H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6087G);
        this.f6090w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6091x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6093z, this.f6083A);
        GLES20.glUniform1f(this.f6086D, f6078E);
        GLES20.glUniform1f(this.f6088H, f6080I);
        GLES20.glUniform1f(this.f6090w, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6091x, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f6082v) {
            this.f6084B = System.currentTimeMillis() - this.f6089K;
            if (this.f6084B > 2000) {
                this.f6089K = System.currentTimeMillis();
            }
        }
        this.f6083A = (((float) this.f6084B) / 1000.0f) * 2.0f * 3.14159f * 0.1f;
        f6078E = f6079F;
        f6080I = f6081J;
    }

    public static void change(float f, float f2) {
        f6079F = f;
        f6081J = f2;
    }
}
