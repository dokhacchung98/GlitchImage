package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class DragImage extends FilterRender {
    private static float f6426A = (MyApplication.imgWidth / 2.0f);
    private static float f6427D;
    private static float f6428E = (MyApplication.imgHeight / 2.0f);
    private static float f6429z;
    private String f6430B = "touchY";
    private int f6431C;
    private int f6432v;
    private int f6433w;
    private String f6434x = "touchX";
    private int f6435y;

    public DragImage() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\n#define SIN01(a) (sin(a)*0.5 + 0.5)\nvec3 rgb2hsv(vec3 c) {\nvec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);\nvec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));\nvec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));\nfloat d = q.x - min(q.w, q.y);\nfloat e = 1.0e-10;\nreturn vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);\n}\nvoid main() {\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = (0.0+mouse.x/iResolution.x*1.0)*5.;\nfloat amounty = 0.0-mouse.y/iResolution.y*1.0;\nvec2 uv = gl_FragCoord.xy / iResolution.xy;\nvec3 hsv = rgb2hsv(texture2D(inputImageTexture, uv).rgb);\nfloat angle = hsv.x + atan(uv.y, uv.x) + 0.0;\nmat2 RotationMatrix = mat2( cos( angle ), -sin( angle ), sin( angle ),  cos( angle ));\nvec3 col = texture2D(inputImageTexture, uv + RotationMatrix * vec2(log(max(SIN01(amountx - 1.0)-0.2, 0.0 + amountx * amountx)*0.2 + 1.  ),0) * hsv.y).rgb;\ngl_FragColor = vec4(col,1.0);\n}");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6435y = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6434x);
        this.f6431C = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6430B);
        this.f6432v = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6433w = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6435y, f6429z);
        GLES20.glUniform1f(this.f6431C, f6427D);
        GLES20.glUniform1f(this.f6432v, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6433w, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f6429z = f6426A;
        f6427D = f6428E;
    }

    public static void change(float f, float f2) {
        f6426A = f;
        f6428E = f2;
    }
}
