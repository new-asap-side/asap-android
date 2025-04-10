package asap.aljyo.core.event

import android.content.Context
import android.widget.Toast

fun Context.shortToast(content: String) =
    Toast.makeText(this, content, Toast.LENGTH_SHORT).show()

fun Context.longToast(content: String) =
    Toast.makeText(this, content, Toast.LENGTH_LONG).show()