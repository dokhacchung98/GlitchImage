package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import com.khacchung.glitchimage.application.MyApplication;

import cn.ezandroid.ezfilter.core.FilterRender;

public class Gba2 extends FilterRender {
    private static float f6298A = (MyApplication.imgWidth / 2.0f);
    private static float f6299D;
    private static float f6300E = (MyApplication.imgHeight / 2.0f);
    private static float f6301z;
    private String f6302B = "touchY";
    private int f6303C;
    private int f6304v;
    private int f6305w;
    private String f6306x = "touchX";
    private int f6307y;

    public Gba2() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float touchX;\nuniform float touchY;\n#define iMouse vec2(touchX,touchY)\nuniform float width;\nuniform float height;\n#define iResolution vec2(width,height)\n#define GB_LINES 0 // lines dont look good with dithering\n#define GB_DESATURATE_BRIGHTER 0\n#define GB_DITHERING 1 // 0: no dither, 1: checkers dithering\nconst vec4 color1 = vec4(8. / 255., 025. / 255., 032. / 255., 1.);\nconst vec4 color2 = vec4(050. / 255., 106. / 255., 79. / 255., 1.);\nconst vec4 color3 = vec4(137. / 255., 192. / 255., 111. / 255., 1.);\nconst vec4 color4 = vec4(223. / 255., 246. / 255., 208. / 255., 1.);\nvoid main(){\nvec2 mouse = iMouse.x==0.?iResolution.xy:iMouse.xy;\nfloat amountx = 0.0+mouse.x/iResolution.x*100.0;\nfloat amounty = 0.0-mouse.y/iResolution.y*1.0;\n#if (GB_LINES > 0)\nfloat lineTest = float(GB_LINES) - 0.45;\nif (mod(gl_FragCoord.x, amountx) < lineTest || mod(gl_FragCoord.y, amountx) < lineTest) {\ngl_FragColor = (color3 + color4 * 2.) / 3.;\nreturn;\n}\n#endif\nvec2 nearestFragCoord = gl_FragCoord.xy - fract(gl_FragCoord.xy / amountx) * amountx; //not needed with nearest\nvec2 uv = (nearestFragCoord.xy / iResolution) / amountx;\n#if (GB_DITHERING == 1)\nint level = int(max(1.0, ceil(texture2D(inputImageTexture,uv).r * 7.)));\nfloat dither = mod((floor(gl_FragCoord.y / amountx) + floor(gl_FragCoord.x / amountx)), 2.);\nif (mod(float(level), 2.) < 1.) {\nlevel += (1 - int(dither) * 2);\n}\nif(level == 1) gl_FragColor = color1;\nelse if(level == 3) gl_FragColor = color2;\nelse if(level == 5) gl_FragColor = color3;\nelse if(level == 7) gl_FragColor = color4;\n#if (GB_DESATURATE_BRIGHTER > 0)\ngl_FragColor = (gl_FragColor + vec4(float(level) / 7., float(level) / 7., float(level) / 7., 1.)) / 2.;\n#endif\n#else\nint level = int(max(1.0, ceil(texture2D(inputImageTexture,uv).r * 4.)));\nif(level == 1) gl_FragColor = color1;\nelse if(level == 2) gl_FragColor = color2;\nelse if(level == 3) gl_FragColor = color3;\nelse gl_FragColor = color4;\n#if (GB_DESATURATE_BRIGHTER > 0)\ngl_FragColor = (gl_FragColor + vec4(float(level) / 4., float(level) / 4., float(level) / 4., 1.)) / 2.;\n#endif\n#endif\nfloat mix1 = 1.0 - touchX/10.;\nif (mix1 < 0.0){\nmix1 = 0.0;\n}\ngl_FragColor = mix(gl_FragColor, texture2D(inputImageTexture, uv), mix1);\n}");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.f6307y = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6306x);
        this.f6303C = GLES20.glGetUniformLocation(this.mProgramHandle, this.f6302B);
        this.f6304v = GLES20.glGetUniformLocation(this.mProgramHandle, "width");
        this.f6305w = GLES20.glGetUniformLocation(this.mProgramHandle, "height");
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.f6307y, f6301z);
        GLES20.glUniform1f(this.f6303C, f6299D);
        GLES20.glUniform1f(this.f6304v, MyApplication.imgWidth);
        GLES20.glUniform1f(this.f6305w, MyApplication.imgHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw() {
        super.onDraw();
        f6301z = f6298A;
        f6299D = f6300E;
    }

    public static void change(float f, float f2) {
        f6298A = f;
        f6300E = f2;
    }
}
