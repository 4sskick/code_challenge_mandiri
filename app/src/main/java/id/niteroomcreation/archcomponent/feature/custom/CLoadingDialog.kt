package id.niteroomcreation.archcomponent.feature.custom

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import id.niteroomcreation.archcomponent.R

/**
 * Created by Septian Adi Wijaya on 19/02/2023.
 * please be sure to add credential if you use people's code
 */
class CLoadingDialog {

    companion object {
        val TAG = CLoadingDialog.javaClass.simpleName

        private lateinit var dialog: Dialog


        fun progressDialog(context: Context): Dialog {
            dialog = Dialog(context)
            val inflate = LayoutInflater.from(context).inflate(R.layout.c_loading, null)

            dialog.setContentView(inflate)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            return dialog
        }
    }

}