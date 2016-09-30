package de.querra.mobile.runlazydroid.widgets;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;

import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.data.RealmInterface;
import de.querra.mobile.runlazydroid.data.entities.finder.RealmOperator;

public class DeleteEntryDialogBuilder {
    public static void show(Context context, final RealmInterface realmObject, final Runnable runnable){
        new AlertDialog.Builder(context)
                .setTitle(R.string.delete_entry)
                .setMessage(R.string.delete_entry_query)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RealmOperator.delete(realmObject);
                        new Handler().post(runnable);
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .setCancelable(true)
                .create()
                .show();
    }
}
