package com.roaimsapp.findit;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;

import com.roaimsapp.findit.adapter.SegmentAdapter;
import com.roaimsapp.findit.adapter.SegmentSpinnerAdapter;
import com.roaimsapp.findit.data.NumberDatabase;
import com.roaimsapp.findit.data.model.Segment;
import com.roaimsapp.findit.databinding.ActivityMainBinding;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "SearchActivity";
    ActivityMainBinding binding;
    private NumberDatabase database;
    private Segment mSegment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        database = NumberDatabase.getDatabase(this);

        Tasker.start(null, new Tasker.Callback<Void, List<Segment>>() {
            @Override
            public List<Segment> onBackground(Void input) {
                return database.segmentDao().getAll();
            }

            @Override
            public void onUi(List<Segment> output) {
                Log.d(TAG, "onCreate::onUi() called with: output = [" + output + "]");
                Segment segment = new Segment("Choose Segment");
                segment.setId(-1);
                output.add(0, segment);
                binding.include.spinner.setAdapter(new SegmentSpinnerAdapter(SearchActivity.this, output));
                binding.include.spinner.setOnItemSelectedListener(SearchActivity.this);
            }
        });

        binding.include.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            String prev = "";
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "onQueryTextSubmit() called with: s = [" + s + "]");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(TAG, "onQueryTextChange() called with: s = [" + s + "]");
                if (s.isEmpty()) {
                    prev = "";
                    return true;
                }
                if (s.startsWith(" ")) {
                    prev = "";
                    binding.include.searchView.setQuery("", false);
                    return true;
                }
                if (s.matches("^[0-9 ]+$")) {
                    prev = s;
                    return true;
                }
                binding.include.searchView.setQuery(prev, false);
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binding != null) {
            binding.unbind();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_saved_number) {
            SaveActivity.start(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getAdapter() instanceof SegmentSpinnerAdapter) {
            Segment item = (Segment) adapterView.getItemAtPosition(i);
            if (item.getId()==-1) mSegment = null;
            else mSegment = item;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        mSegment = null;
    }
}
