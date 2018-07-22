package com.roaimsapp.findit;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.roaimsapp.findit.adapter.SegmentAdapter;
import com.roaimsapp.findit.adapter.SegmentClickListener;
import com.roaimsapp.findit.data.NumberDatabase;
import com.roaimsapp.findit.data.model.Segment;
import com.roaimsapp.findit.databinding.ActivitySaveBinding;

import java.util.List;

public class SaveActivity extends AppCompatActivity implements SegmentClickListener, TextView.OnEditorActionListener {
    private static final String TAG = "SaveActivity";
    ActivitySaveBinding binding;
    private NumberDatabase database;
    private SegmentAdapter segmentAdapter;

    public static void start(Context context) {
        context.startActivity(new Intent(context, SaveActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_save);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        database = NumberDatabase.getDatabase(this);

        Tasker.start(null, new Tasker.Callback<Void, List<Segment>>() {
            @Override
            public List<Segment> onBackground(Void input) {
                return database.segmentDao().getAll();
            }

            @Override
            public void onUi(List<Segment> output) {
                Log.d(TAG, "onCreate::onUi() called with: output = [" + output + "]");
                segmentAdapter = new SegmentAdapter(output, SaveActivity.this);
                binding.include.recyclerViewSegment.setLayoutManager(new LinearLayoutManager(SaveActivity.this));
                binding.include.recyclerViewSegment.setAdapter(segmentAdapter);
            }
        }, binding.include.progressBar);

        binding.include.editTextAddSegment.setOnEditorActionListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binding != null) {
            binding.unbind();
        }
    }

    @Override
    public void onSegmentClick(Segment segment) {
        Log.d(TAG, "onSegmentClick() called with: segment = [" + segment + "]");
        NumberActivity.start(this, segment);
    }

    @Override
    public void onSegmentDeleteClick(Segment segment) {
        Log.d(TAG, "onSegmentDeleteClick() called with: segment = [" + segment + "]");
        Tasker.start(segment, new Tasker.Callback<Segment, Segment>() {
            @Override
            public Segment onBackground(Segment input) {
                database.segmentDao().delete(input);
                return input;
            }

            @Override
            public void onUi(Segment output) {
                segmentAdapter.removeSegment(output);
            }
        }, binding.include.progressBar);
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE && !binding.include.editTextAddSegment.getText().toString().isEmpty()) {
            String segmentName = binding.include.editTextAddSegment.getText().toString();
            Segment segment = new Segment(segmentName);

            Tasker.start(segment, new Tasker.Callback<Segment, Segment>() {
                @Override
                public Segment onBackground(Segment input) {
                    int insert = (int) database.segmentDao().insert(input);
                    input.setId(insert);
                    return input;
                }

                @Override
                public void onUi(Segment output) {
                    segmentAdapter.addSegment(output);
                    binding.include.editTextAddSegment.setText("");
                }
            }, binding.include.progressBar);
        }
        return false;
    }
}
