package com.khacchung.glitchimage.filter;

import android.content.Context;
import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.util.Path;
import cn.ezandroid.ezfilter.extra.MultiBitmapInputRender;

public class Emoji extends MultiBitmapInputRender {
    private static final String[] f6744E = {Path.DRAWABLE.wrap("2131165318")};
    private static float f6745H;
    private static float f6746I = (MyApplication.imgWidth / 2.0f);
    private static float f6747L;
    private static float f6748M = (MyApplication.imgHeight / 2.0f);
    private int f6749C;
    private int f6750D;
    private String f6751F = "touchX";
    private int f6752G;
    private String f6753J = "touchY";
    private int f6754K;

    public Emoji(Context context) {
        super(context, f6744E);
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform sampler2D inputImageTexture2;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\n#define G 11 \nvoid main() {  \nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*100.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\nvec2 fragCoord = vec2(gl_FragCoord.x, gl_FragCoord.y);\nvec2 fragColor = vec2(gl_FragColor.x, gl_FragColor.y);\nvec2 R = iResolution.xy;\nvec2 S = vec2(amountx*1.6,amountx*1.6);\nfragCoord /= S;\nfragColor-=fragColor;\nfloat v = texture2D(inputImageTexture,floor(fragCoord)*S/R).r;\nfragCoord = fract(fragCoord);\nfragCoord.x = fragCoord.x/1.0;\nint c = int(float(G)*v);\nfloat mix1 = 1.0 - amountx/10.;\nif (mix1 < 0.0){\nmix1 = 0.0;\n}\ngl_FragColor.rgb = mix(texture2D(inputImageTexture2, fragCoord/5. + fract( vec2(c, c/5) / 5.)), texture2D(inputImageTexture, gl_FragCoord.xy/R), mix1).rgb;\n}");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6752G = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6751F);
        this.f6754K = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6753J);
        this.f6749C = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6750D = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6752G, f6745H);
        GLES20.glUniform1f(this.f6754K, f6747L);
        GLES20.glUniform1f(this.f6749C, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6750D, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f6745H = f6746I;
        f6747L = f6748M;
    }

    public static void change(float f, float f2) {
        f6746I = f;
        f6748M = f2;
    }
}
