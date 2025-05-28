package ru.mirea.perovka.cryptoloader;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MyLoader extends AsyncTaskLoader<String> {
    public static final String ARG_WORD = "clown";
    private final byte[] cryptText;
    private final byte[] key;

    public MyLoader(@NonNull Context context, Bundle args) {
        super(context);
        cryptText = args.getByteArray(ARG_WORD);
        key = args.getByteArray("key");
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        SecretKey originalKey = new SecretKeySpec(key, 0, key.length, "AES");
        return CryptoUtils.decryptMsg(cryptText, originalKey);
    }
}

