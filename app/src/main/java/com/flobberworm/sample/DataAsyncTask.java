package com.flobberworm.sample;

import android.os.AsyncTask;

import java.util.List;

/**
 * DataAsyncTask
 * Created by Kornan on 2017/12/18.
 */
public class DataAsyncTask extends AsyncTask<String, String, List<String>> {
    private OnDataEvent onDataEvent;

    public DataAsyncTask(OnDataEvent onDataEvent) {
        this.onDataEvent = onDataEvent;
    }

    @Override
    protected List<String> doInBackground(String... strings) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Sample.getSample();
    }

    @Override
    protected void onPostExecute(List<String> data) {
        onDataEvent.complete(data);
    }

    interface OnDataEvent {
        void complete(List<String> data);
    }
}