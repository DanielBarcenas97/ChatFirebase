package com.example.chatfirebase.common.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class UtilsCommon {

    /*
     * Codificar un correo electronico
     * */

    public static String getEmailEncoded(String email){
        String preKey  = email.replace("_","__");
        return  preKey.replace(".","_");
    }

    /*
     *  Cargar imágenes básicas
     * */
    public static void loadImage(Context context, String url, ImageView target) {

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(target);
    }
}
