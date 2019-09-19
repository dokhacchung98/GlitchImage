package com.khacchung.glitchimage.filter;

import android.opengl.GLES20;

import cn.ezandroid.ezfilter.core.FilterRender;

public class WobbleRender extends FilterRender {
    private String UNIFORM_OFFSET = "offset";
    private float mOffset;
    private int mOffsetHandler;
    private long mStartTime;

    public WobbleRender() {
        setFragmentShader("precision mediump float;\n\nuniform sampler2D inputImageTexture;\nuniform float offset;\n\nvarying vec2 textureCoordinate;\n\nvoid main()\n{\n    vec2 texcoord = textureCoordinate;\n    texcoord.x += sin(texcoord.y * 4.0 * 2.0 * 3.14159 + offset) / 100.0;\n    gl_FragColor = texture2D(inputImageTexture, texcoord);\n}");
        this.mStartTime = System.currentTimeMillis();
    }

    @Override
    protected void initShaderHandles() {
        super.initShaderHandles();
        this.mOffsetHandler = GLES20.glGetUniformLocation(this.mProgramHandle, this.UNIFORM_OFFSET);
    }

    @Override
    protected void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.mOffsetHandler, this.mOffset);
    }

    @Override
    protected void onDraw() {
        super.onDraw();
        long currentTimeMillis = System.currentTimeMillis() - this.mStartTime;
        if (currentTimeMillis > 20000) {
            this.mStartTime = System.currentTimeMillis();
        }
        this.mOffset = (float) currentTimeMillis / 1000.0f * 2.0f * 3.14159f * 0.75f;
    }
}
