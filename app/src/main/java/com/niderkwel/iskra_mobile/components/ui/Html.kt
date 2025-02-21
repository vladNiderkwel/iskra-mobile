package com.niderkwel.iskra_mobile.components.ui

import android.graphics.text.LineBreaker
import android.os.Build
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.niderkwel.iskra_mobile.R

@Composable
fun Html(
    html: String,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            TextView(context).apply {
                text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
                setTextColor(resources.getColor(R.color.onSurface, null))
                textSize = 16f
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                    lineHeight = 96
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                    justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
            }
        },
        modifier = modifier
    )
}