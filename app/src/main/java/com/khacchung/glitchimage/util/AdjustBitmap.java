package com.khacchung.glitchimage.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;

public class AdjustBitmap {
    private static int getOrientation(Context context, Uri uri) {
        String[] strArr = new String[]{"orientation"};
        Cursor query = context.getContentResolver().query(uri, strArr,
                null, null, null);
        if (query == null || !query.moveToFirst()) {
            return -1;
        } else return query.getInt(query.getColumnIndex(strArr[0]));
    }

    public static Bitmap getCorrectlyOrientedImage(Context context, Uri uri, int i) throws IOException {
        int i2;
        int i3;
        Bitmap bitmap;
        InputStream openInputStream = context.getContentResolver().openInputStream(uri);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(openInputStream, null, options);
        openInputStream.close();
        int orientation = getOrientation(context, uri);
        if (orientation == 90 || orientation == 270) {
            i2 = options.outHeight;
            i3 = options.outWidth;
        } else {
            i2 = options.outWidth;
            i3 = options.outHeight;
        }
        InputStream openInputStream2 = context.getContentResolver().openInputStream(uri);
        if (i2 > i || i3 > i) {
            float f = (float) i;
            float max = Math.max(((float) i2) / f, ((float) i3) / f);
            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inSampleSize = (int) max;
            bitmap = BitmapFactory.decodeStream(openInputStream2, null, options2);
        } else {
            bitmap = BitmapFactory.decodeStream(openInputStream2);
        }
        Bitmap bitmap2 = bitmap;
        if (openInputStream2 != null) {
            openInputStream2.close();
        }
        if (orientation <= 0) {
            return bitmap2;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate((float) orientation);
        return Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(), matrix, true);
    }
}
