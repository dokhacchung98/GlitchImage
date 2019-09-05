package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class CRT1 extends FilterRender {
    private static float f6320E = 0.0f;
    private static float f6321F = (MyApplication.imgWidth / 2.0f);
    private static float f6322I = 0.0f;
    private static float f6323J = (MyApplication.imgHeight / 2.0f);
    public static boolean f6324v = false;
    private float f6325A;
    private long f6326B = System.currentTimeMillis();
    private String f6327C = "touchX";
    private int f6328D;
    private String f6329G = "touchY";
    private int f6330H;
    private long f6331K;
    private int f6332w;
    private int f6333x;
    private String f6334y = "iTime";
    private int f6335z;

    public CRT1() {
        setFragmentShader("precision highp float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat rng2(vec2 seed)\n{\n    return fract(sin(dot(seed * floor(iTime * 50.), vec2(127.1,311.7))) * 43758.5453123);\n}\nfloat rng(float seed)\n{\n    return rng2(vec2(seed, 1.0));\n}\nvoid main() {\n\tvec2 uv = gl_FragCoord.xy / iResolution.xy;\n    vec2 blockS = floor(uv * vec2(iResolution.x/30., iResolution.y/30.));\n    vec2 blockL = floor(uv * vec2(iResolution.x/20., iResolution.y/20.));\n    \n    float r = rng2(uv);\n    vec3 noise = (vec3(r, 1. - r, r / 2. + 0.5) * 1.0 - 2.0) * 0.2;\n    \n    float lineNoise = pow(rng2(blockS), 8.0) * pow(rng2(blockL), 8.0) - pow(rng(7.2341), 17.0) * 3.;\n    \n    vec4 col1 = texture2D(inputImageTexture, uv + vec2(lineNoise * 0.07 * rng(5.0), 0));\n    vec4 col2 = texture2D(inputImageTexture, uv + vec2(-0.01, 0.0));\n    vec4 col3 = texture2D(inputImageTexture, uv - vec2(lineNoise * 0.07 * rng(31.0), 0));\n    \n\tgl_FragColor = vec4(vec3(col1.x, col2.y, col3.z) + noise, 1.0);\n    \n}");
        this.f6331K = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6335z = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6334y);
        this.f6328D = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6327C);
        this.f6330H = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6329G);
        this.f6332w = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6333x = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6335z, this.f6325A);
        GLES20.glUniform1f(this.f6328D, f6320E);
        GLES20.glUniform1f(this.f6330H, f6322I);
        GLES20.glUniform1f(this.f6332w, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6333x, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        if (!f6324v) {
            this.f6326B = System.currentTimeMillis() - this.f6331K;
            if (this.f6326B > 2000) {
                this.f6331K = System.currentTimeMillis();
            }
        }
        this.f6325A = (((float) this.f6326B) / 1000.0f) * 2.0f * 3.14159f * 0.1f;
        f6320E = f6321F;
        f6322I = f6323J;
    }

    public static void change(float f, float f2) {
        f6321F = f;
        f6323J = f2;
    }
}
