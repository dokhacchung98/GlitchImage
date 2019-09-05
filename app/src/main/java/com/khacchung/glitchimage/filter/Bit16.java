package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Bit16 extends FilterRender {
    private static float f6310A = (MyApplication.imgWidth / 2.0f);
    private static float f6311D;
    private static float f6312E = (MyApplication.imgHeight / 2.0f);
    private static float f6313z;
    private String f6314B = "touchY";
    private int f6315C;
    private int f6316v;
    private int f6317w;
    private String f6318x = "touchX";
    private int f6319y;

    public Bit16() {
        setFragmentShader("precision lowp float;\nuniform sampler2D inputImageTexture;\nuniform float iTime;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\nfloat hash(vec2 p) {\nreturn fract(1e4 * sin(17.0 * p.x + p.y * 0.1) * (0.1 + abs(sin(p.y * 13.0 + p.x))));\n}\nfloat compare(vec3 a, vec3 b) {\na = max(vec3(0.0), a - min(a.r, min(a.g, a.b)) * 0.25);\nb = max(vec3(0.0), b - min(b.r, min(b.g, b.b)) * 0.25);\na*=a*a;\nb*=b*b;\nvec3 diff = (a - b);\nreturn dot(diff, diff);\n}\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0-mouse.x/iResolution.x*100.0;\nfloat amounty = 0.0+mouse.y/iResolution.y*1.0;\nfloat pixelSize = amountx;\nvec2 c = floor(gl_FragCoord.xy / pixelSize);\nvec2 coord = c * pixelSize;\nvec3 src = texture2D(inputImageTexture, coord / iResolution.xy).rgb;\nvec3 dst0 = vec3(0), dst1 = vec3(0);\nfloat best0 = 1e3, best1 = 1e3;\n#define TRY(R, G, B) { const vec3 tst = vec3(R, G, B); float err = compare(src, tst); if (err < best0) { best1 = best0; dst1 = dst0; best0 = err; dst0 = tst; } }\nTRY(0.078431, 0.047059, 0.109804);\nTRY(0.266667, 0.141176, 0.203922);\nTRY(0.188235, 0.203922, 0.427451);\nTRY(0.305882, 0.290196, 0.305882);\nTRY(0.521569, 0.298039, 0.188235);\nTRY(0.203922, 0.396078, 0.141176);\nTRY(0.815686, 0.274510, 0.282353);\nTRY(0.458824, 0.443137, 0.380392);\nTRY(0.349020, 0.490196, 0.807843);\nTRY(0.823529, 0.490196, 0.172549);\nTRY(0.521569, 0.584314, 0.631373);\nTRY(0.427451, 0.666667, 0.172549);\nTRY(0.823529, 0.666667, 0.600000);\nTRY(0.427451, 0.760784, 0.792157);\nTRY(0.854902, 0.831373, 0.368627);\nTRY(0.870588, 0.933333, 0.839216);\n#undef TRY\nbest0 = sqrt(best0); best1 = sqrt(best1);\ngl_FragColor = vec4(mod(c.x + c.y, 2.0) >  (hash(c * 2.0) * 0.75) + (best1 / (best0 + best1)) ? dst1 : dst0, 1.0);\n}");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6319y = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6318x);
        this.f6315C = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6314B);
        this.f6316v = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6317w = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6319y, f6313z);
        GLES20.glUniform1f(this.f6315C, f6311D);
        GLES20.glUniform1f(this.f6316v, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6317w, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f6313z = f6310A;
        f6311D = f6312E;
    }

    public static void change(float f, float f2) {
        f6310A = f;
        f6312E = f2;
    }
}
