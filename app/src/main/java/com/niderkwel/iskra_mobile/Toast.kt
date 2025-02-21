package com.niderkwel.iskra_mobile

import android.content.Context
import android.widget.Toast

fun createToast(context: Context,message: String, length: Int) =
    Toast.makeText(context, message, length).show()