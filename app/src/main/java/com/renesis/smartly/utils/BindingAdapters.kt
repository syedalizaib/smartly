package com.renesis.smartly.utils

import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.text.Html
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textfield.TextInputLayout
import com.renesis.smartly.R
import com.renesis.smartly.base.MyEditText


val TAG = "BindingAdapters"


@BindingAdapter("app:hideIfNotZero")
fun hideIfNotEmptyList(view: View, number: LiveData<Int>) {
    view.visibility = if (number.value == 0)
        View.VISIBLE
    else
        View.GONE
}

@BindingAdapter("app:isEnabled")
fun enabledBinding(view: View, enabled: LiveData<Boolean?>) {
    view.isEnabled = enabled.value!!
}

@BindingAdapter("app:inputError")
fun inputError(editText: EditText, error: LiveData<String?>?) {
    if (error?.value.isNullOrEmpty()) {
        editText.setBackgroundResource(R.drawable.bg_solid_rounded_gray)
        editText.error = null
    } else {
        editText.error = error?.value.orEmpty()
        editText.setBackgroundResource(R.drawable.bg_edit_text_error)
    }
}

@BindingAdapter("app:onError")
fun onError(editText: EditText, error: LiveData<String?>?) {
    if (error?.value.isNullOrEmpty()) {
        editText.setBackgroundResource(R.drawable.bg_solid_rounded_gray)

    } else {
        editText.setBackgroundResource(R.drawable.bg_edit_text_error)
    }
}


@BindingAdapter("app:errorText")
fun errorText(textInput: TextInputLayout, error: LiveData<String?>?) {
    if (error?.value.isNullOrEmpty()) {
        textInput.error = null
    } else {
        textInput.error = error?.value
    }
}

//CUSTOM VIEW

@BindingAdapter("app:errorField")
fun errorField(textView: MyEditText, error: String?) {
    textView.errorText = error
}

@InverseBindingAdapter(attribute = "app:errorField")
fun errorFieldInverse(textView: MyEditText): String? {
    return textView.errorText
}

@BindingAdapter(value = ["errorFieldAttrChanged"])
fun setChangeListener(textView: MyEditText, listener: InverseBindingListener?) {
    if (listener != null) {
        textView.setErrorChangedListener { listener.onChange() }
    }
}

@BindingAdapter(value = ["app:onValidation"])
fun setValidationListener(textView: MyEditText, function: () -> Unit) {
    textView.setValidationListener {
        function.invoke()
    }

}

@BindingAdapter("app:imageUrl")
fun setImageUrl(imageView: ImageView, imageUrl: LiveData<String?>?) {

    imageUrl?.value?.let {

        Glide.with(imageView.context)
            .load(it)
            .apply(RequestOptions().circleCrop())
            .into(imageView)
    }
}

@BindingAdapter("app:imageId")
fun setImageId(imageView: ImageView, imageId: Int?) {

    imageId?.let {
        imageView.setImageDrawable(imageView.context.getDrawable(imageId))
    }
}

@BindingAdapter("app:iconColor")
fun setIconColor(imageView: ImageView, iconColor: String?) {
    iconColor?.let { color ->
        val gradientDrawable =
            imageView.context.getDrawable((R.drawable.ic_folder)) as GradientDrawable
        gradientDrawable.setColor(Color.parseColor("#$iconColor"))
        imageView.setImageDrawable(gradientDrawable)
    }
}

@BindingAdapter("app:visibleIfTrue")
fun visibleIfTrue(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
    if (isVisible) view.requestFocus()
}

@BindingAdapter("app:enableIfTrue")
fun enableIfTrue(view: View, isEnable: Boolean) {
    view.isEnabled = isEnable
}

@BindingAdapter("app:playAnimation")
fun playAnimation(view: ImageView, isPlay: Boolean) {
    val frameAnimation: AnimationDrawable = view.background as AnimationDrawable
    if (isPlay) frameAnimation.start() else frameAnimation.stop()
}

@BindingAdapter("app:correctAnswerAfterAttempt")
fun correctAnswerAfterAttempt(view: AppCompatButton, answer: String) {
    if (!answer.equals("null") && view.text.equals(answer))
        view.setBackgroundResource(R.drawable.button_correct)
}

@BindingAdapter("app:resetButtons")
fun resetButtons(view: AppCompatButton, isEnabled: Boolean) {
    if (isEnabled)
        view.setBackgroundResource(R.drawable.button_normal_background)
}

@BindingAdapter("app:setText")
fun setText(view: View, text: String) {
    if (view is TextView) {
        view.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            Html.fromHtml(text).toString()
        }
    } else if (view is AppCompatButton) {
        view.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            Html.fromHtml(text).toString()
        }
    }
}
