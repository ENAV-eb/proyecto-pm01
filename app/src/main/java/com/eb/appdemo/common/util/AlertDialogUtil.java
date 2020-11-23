package com.eb.appdemo.common.util;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

public class AlertDialogUtil {

    /**
     * Shows alert dialog with provided message.
     *
     * @param error Error message to be displayed.
     * @param context Application context.
     */
    public static void showAlertDialog(String error, Context context , String title) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title);

            builder.setMessage("Debe ingresar el campo "+ error);
            builder.setNegativeButton("CONTINUAR", null);

            AlertDialog dialog = builder.create();
            dialog.show();

        } catch (final Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
