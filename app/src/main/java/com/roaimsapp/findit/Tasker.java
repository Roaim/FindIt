package com.roaimsapp.findit;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by Roaim on 22-Jul-18.
 */

public class Tasker<I,O> extends AsyncTask<I,Void,O> {
    private Callback<I,O> mCallback;
    private ProgressBar progressBar;

    public void setCallback(Callback<I, O> callback) {
        this.mCallback = callback;
    }

    private Tasker() {}

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public static <I,O>void start(I input, Callback<I, O> callback) {
        start(input, callback, null);
    }

    public static <I,O>void start(I input, Callback<I, O> callback, ProgressBar progressBar) {
        Tasker<I, O> ioTasker = new Tasker<>();
        ioTasker.setCallback(callback);
        if (progressBar != null) {
            ioTasker.setProgressBar(progressBar);
        }
        ioTasker.execute(input);
    }

    private void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    protected O doInBackground(I... inputs) {
        return mCallback.onBackground(inputs[0]);
    }

    @Override
    protected void onPostExecute(O output) {
        super.onPostExecute(output);
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
            progressBar = null;
        }
        mCallback.onUi(output);
    }

    public interface Callback<I,O> {
        O onBackground(I input);
        void onUi(O output);
    }
}
