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

import com.roaimsapp.findit.adapter.SearchAdapter;
import com.roaimsapp.findit.adapter.SegmentAdapter;
import com.roaimsapp.findit.adapter.SegmentSpinnerAdapter;
import com.roaimsapp.findit.data.NumberDatabase;
import com.roaimsapp.findit.data.dao.NumberDao;
import com.roaimsapp.findit.data.model.Number;
import com.roaimsapp.findit.data.model.Segment;
import com.roaimsapp.findit.databinding.ActivityMainBinding;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "SearchActivity";
    ActivityMainBinding binding;
    private NumberDatabase database;
    private Segment mSegment;
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        database = NumberDatabase.getDatabase(this);

        searchAdapter = new SearchAdapter();
        binding.include.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.include.recyclerView.setAdapter(searchAdapter);

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
                String[] split = new String[1];
                if (s.contains(" ")) {
                    split = s.split(" ");
                } else if (!s.isEmpty()){
                    split[0] = s;
                }
                Tasker.start(split, new Tasker.Callback<String[], List<Number>>() {
                    @Override
                    public List<Number> onBackground(String[] input) {
                        int length = input.length;
                        Log.d(TAG, "onBackground: length: " + length);
                        if (length > 3) return null;
                        searchAdapter.setLength(length);
                        NumberDao numberDao = database.numberDao();
                        List<Number> numbers = null;
                        if (length == 1) {
                            if (mSegment != null) {
                                numbers = numberDao.getNumbers(input[0], mSegment.getId());
                            } else {
                                numbers = numberDao.getNumbers(input[0]);
                            }
                        } else if (length == 2) {
                            if (mSegment != null) {
                                numbers = numberDao.getNumbers(input[0], input[1], mSegment.getId());
                            } else {
                                numbers = numberDao.getNumbers(input[0], input[1]);
                            }
                        } else if (length == 3) {
                            if (mSegment != null) {
                                numbers = numberDao.getNumbers(input[0], input[1], input[2], mSegment.getId());
                            } else {
                                numbers = numberDao.getNumbers(input[0], input[1], input[2]);
                            }
                        }
                        return numbers;
                    }

                    @Override
                    public void onUi(List<Number> output) {
                        Log.d(TAG, "onQueryTextSubmit::onUi() called with: output = [" + output + "]");
                        if (output != null) {
                            searchAdapter.addAll(output);
                        }
                    }
                }, binding.include.progressBar);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(TAG, "onQueryTextChange() called with: s = [" + s + "]");
                if (s.isEmpty()) {
                    prev = "";
                    searchAdapter.clear();
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
        Log.d(TAG, "onItemSelected() called with: i = [" + i + "], l = [" + l + "]");
        if (adapterView.getAdapter() instanceof SegmentSpinnerAdapter) {
            Segment item = (Segment) adapterView.getItemAtPosition(i);
            if (item.getId()==-1) mSegment = null;
            else mSegment = item;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.d(TAG, "onNothingSelected() called");
        mSegment = null;
    }
}
