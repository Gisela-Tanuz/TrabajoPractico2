package com.tanuz.trabajopractico2;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.util.Log;

import androidx.annotation.Nullable;

public class ServicioDeMensaje extends Service  {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         super.onStartCommand(intent, flags, startId);
         verMensajes();
         return START_STICKY;
    }

    private void verMensajes() {

        Uri mensaje = Uri.parse("content://sms/inbox");
        ContentResolver cr = getContentResolver();
       Thread Tarea = new Thread(new Runnable() {

        @Override
        public void run() {
            while (true) {

                Cursor c = cr.query(mensaje, null, null, null, null);
                String id = "";
                String nro = "";
                String nombre = "";
                String msj = "";
                int i = 1;
                if (c != null && c.getCount() > 0) {
                    while (c.moveToNext() && i < 6) {
                        id = c.getString(c.getColumnIndex(Telephony.Sms._ID));
                        nro = c.getString(c.getColumnIndex(Telephony.Sms.ADDRESS));
                        nombre = c.getString(c.getColumnIndex(Telephony.Sms.PERSON));
                        msj = c.getString(c.getColumnIndex(Telephony.Sms.BODY));

                        Log.d("mensajes", id + "/" + nro + "/" + nombre + "/" + msj);
                        i++;
                    }c.close();
                }
                try {
                    Thread.sleep(9000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
        });
       Tarea.start();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
