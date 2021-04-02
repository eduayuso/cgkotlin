package dev.eduayuso.cgkotlin.convexhull.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun ViewGroup.inflate(@LayoutRes resource: Int): View {

    return LayoutInflater.from(context).inflate(resource, this, false)
}

fun ImageView.bindImageUrl(url: String?) {

    if (url != null && url.isNotBlank()) {
        Picasso.get()
                .load(url)
                .into(this)
    }
}

fun ImageView.bindAvatarUrl(url: String?) {

    if (url != null && url.isNotBlank()) {
        Picasso.get()
                .load(url)
                .transform(CropCircleTransformation())
                .into(this)
    }
}

fun TextView.bindDate(date: String?) {

    if (date != null && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        val DATE_UTC_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        var formatter = DateTimeFormatter.ofPattern(DATE_UTC_PATTERN)
        this.text = LocalDate.parse(date, formatter).toString()
    } else {
        this.text = date
    }
}