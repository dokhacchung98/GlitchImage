package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Lens extends FilterRender {
    private static float f6556A = (MyApplication.imgWidth / 2.0f);
    private static float f6557D;
    private static float f6558E = (MyApplication.imgHeight / 2.0f);
    private static float f6559z;
    private String f6560B = "touchY";
    private int f6561C;
    private int f6562v;
    private int f6563w;
    private String f6564x = "touchX";
    private int f6565y;

    public Lens() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nvec2 barrelDistortion( vec2 p, vec2 amt ) {\np = 2.0 * p - 1.0;\nfloat maxBarrelPower = sqrt(5.0);\nfloat radius = dot(p,p); //faster but doesn't match above accurately\np *= pow(vec2(radius), maxBarrelPower * amt);\nreturn p * 0.5 + 0.5;\n}\nvec2 brownConradyDistortion(vec2 uv, float scalar) {\nuv = (uv - 0.5 ) * 2.0;\nif( true ) {\nfloat barrelDistortion1 = -0.02 * scalar; // K1 in text books\nfloat barrelDistortion2 = 0.0 * scalar; // K2 in text books\nfloat r2 = dot(uv,uv);\nuv *= 1.0 + barrelDistortion1 * r2 + barrelDistortion2 * r2 * r2;\n}\nreturn (uv / 2.0) + 0.5;\n}\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0+mouse.x/iResolution.x*50.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nfloat scalar = 1.0 * amountx;\nvec4 colourScalar = vec4(700.0, 560.0, 490.0, 1.0);\ncolourScalar /= max(max(colourScalar.x, colourScalar.y), colourScalar.z);\ncolourScalar *= 2.0;\ncolourScalar *= scalar;\nvec4 sourceCol = texture2D(inputImageTexture, uv);\nconst float numTaps = 8.0;\ngl_FragColor = vec4( 0.0 );\nfor( float tap = 0.0; tap < numTaps; tap += 1.0 ) {\ngl_FragColor.r += texture2D(inputImageTexture, brownConradyDistortion(uv, colourScalar.r)).r;\ngl_FragColor.g += texture2D(inputImageTexture, brownConradyDistortion(uv, colourScalar.g)).g;\ngl_FragColor.b += texture2D(inputImageTexture, brownConradyDistortion(uv, colourScalar.b)).b;\ncolourScalar *= 0.99;\n}\ngl_FragColor /= numTaps;\ngl_FragColor.a = 1.0;\n}");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6565y = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6564x);
        this.f6561C = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6560B);
        this.f6562v = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6563w = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6565y, f6559z);
        GLES20.glUniform1f(this.f6561C, f6557D);
        GLES20.glUniform1f(this.f6562v, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6563w, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f6559z = f6556A;
        f6557D = f6558E;
    }

    public static void change(float f, float f2) {
        f6556A = f;
        f6558E = f2;
    }
}
