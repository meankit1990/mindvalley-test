package com.mindvalley.test

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter

class BindingUtils {
    companion object {

        @JvmStatic
        @BindingAdapter(value = ["imageUrl", "placeholder"], requireAll = false)
        fun addSrc(imageView: ImageView, bitmapImage: Bitmap?, placeholder: Drawable?) {
            if (bitmapImage != null) {
                if (placeholder == null)
                    loadImage(imageView, bitmapImage)
                else
                    loadImage(imageView,bitmapImage,placeholder)
            } else {
                loadImage(imageView, placeholder)
            }
        }

        private fun loadImage(imageView: ImageView, placeholder: Drawable?) {
            imageView.setImageDrawable(placeholder)
        }

        private fun loadImage(imageView: ImageView, bitmapImage: Bitmap, placeholder: Drawable) {
            imageView.setImageDrawable(placeholder)
            imageView.setImageBitmap(bitmapImage)
        }

        private fun loadImage(imageView: ImageView?, bitmapImage: Bitmap?) {
            imageView?.setImageBitmap((bitmapImage))
        }

    }

}
