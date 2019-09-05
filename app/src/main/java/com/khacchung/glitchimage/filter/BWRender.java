package com.khacchung.glitchimage.filter;

import android.content.Context;
import android.opengl.GLES20;

import cn.ezandroid.ezfilter.core.util.Path;
import cn.ezandroid.ezfilter.extra.IAdjustable;
import cn.ezandroid.ezfilter.extra.MultiBitmapInputRender;

public class BWRender extends MultiBitmapInputRender implements IAdjustable {
    private static final String[] BITMAP_PATHS = {Path.ASSETS.wrap("filter/bw.png")};
    private static final String UNIFORM_MIX = "u_mix";
    private float mMix = 1.0f;
    private int mMixHandle;

    public float getProgress() {
        return 0;
    }

    public BWRender(Context context) {
        super(context, BITMAP_PATHS);
        setFragmentShader("precision lowp float;\n uniform lowp float u_mix;\n \n varying highp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2;\n \n \n void main()\n{\n    lowp vec4 sourceImageColor = texture2D(inputImageTexture, textureCoordinate);\n    \n    vec3 texel = texture2D(inputImageTexture, textureCoordinate).rgb;\n    texel = vec3(dot(vec3(0.3, 0.6, 0.1), texel));\n    texel = vec3(texture2D(inputImageTexture2, vec2(texel.r, .16666)).r);\n    mediump vec4 fragColor = vec4(texel, 1.0);\n    gl_FragColor = mix(sourceImageColor, fragColor, u_mix);\n}");
    }

    /* access modifiers changed from: protected */
    public void initShaderHandles() {
        super.initShaderHandles();
        this.mMixHandle = GLES20.glGetUniformLocation(this.mProgramHandle, UNIFORM_MIX);
    }

    /* access modifiers changed from: protected */
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.mMixHandle, this.mMix);
    }

    public void adjust(float f) {
        this.mMix = f;
    }
}
