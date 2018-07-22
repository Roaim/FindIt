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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.roaimsapp.findit.adapter.NumberAdapter;
import com.roaimsapp.findit.adapter.NumberClickListener;
import com.roaimsapp.findit.data.NumberDatabase;
import com.roaimsapp.findit.data.model.Number;
import com.roaimsapp.findit.data.model.Segment;
import com.roaimsapp.findit.databinding.ActivityNumberBinding;

import java.util.List;

public class NumberActivity extends AppCompatActivity implements NumberClickListener, TextView.OnEditorActionListener {
    private static final String TAG = "NumberActivity";
    private ActivityNumberBinding binding;
    private NumberDatabase database;
    private NumberAdapter numberAdapter;
    private Segment segment;

    public static void start(Context context, Segment segment) {
        Intent intent = new Intent(context, NumberActivity.class);
        intent.putExtra(Constants.Database.COLUMN_ID, segment.getId());
        intent.putExtra(Constants.Database.COLUMN_NAME, segment.getName());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        segment = getSegment(getIntent());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_number);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setTitle(segment.getName());

        database = NumberDatabase.getDatabase(this);
        Tasker.start(segment.getId(), new Tasker.Callback<Integer, List<Number>>() {
            @Override
            public List<Number> onBackground(Integer input) {
                return database.numberDao().getAllNumbers(segment.getId());
            }

            @Override
            public void onUi(List<Number> output) {
                numberAdapter = new NumberAdapter(output, NumberActivity.this);
                binding.main.recyclerViewNumber.setLayoutManager(new LinearLayoutManager(NumberActivity.this));
                binding.main.recyclerViewNumber.setAdapter(numberAdapter);
            }
        }, binding.main.progressBar);

        binding.main.editTextAddNumber.setOnEditorActionListener(this);
    }

    private Segment getSegment(Intent intent) {
        int id = intent.getIntExtra(Constants.Database.COLUMN_ID, -1);
        String name = intent.getStringExtra(Constants.Database.COLUMN_NAME);
        Segment segment = new Segment(name);
        segment.setId(id);
        return segment;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE && !binding.main.editTextAddNumber.getText().toString().isEmpty()) {
            String textNumber = binding.main.editTextAddNumber.getText().toString();
            final Number number = new Number(textNumber);
            number.setSegmentId(segment.getId());

            Tasker.start(number, new Tasker.Callback<Number, Number>() {
                @Override
                public Number onBackground(Number input) {
                    List<Number> numbers = database.numberDao().getNumbers(segment.getId());
                    Number[] nums = new Number[numbers.size()];
                    for (int i = 0; i < numbers.size(); i++) {
                        Number num = numbers.get(i);
                        if (i==0) {
                            input.setPrev(num.getNumber());
                            num.setNext1(input.getNumber());
                        } else if (i==1) {
                            num.setNext2(input.getNumber());
                        } else if (i==2) {
                            num.setNext3(input.getNumber());
                        }
                        nums[i] = num;
                    }
                    database.numberDao().update(nums);
                    numberAdapter.update(nums);

                    int insert = (int) database.numberDao().insert(input);
                    input.setId(insert);

                    return input;
                }

                @Override
                public void onUi(Number output) {
                    numberAdapter.addNumber(output);
                    numberAdapter.notifyDataSetChanged();
                    binding.main.editTextAddNumber.setText("");
                }
            }, binding.main.progressBar);
        }
        return false;
    }

    @Override
    public void onNumberClick(Number number) {
        Log.d(TAG, "onNumberClick() called with: number = [" + number + "]");
        final Snackbar snackbar = Snackbar.make(binding.main.editTextAddNumber, number.toString(), Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Close", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public void onButtonClick(ImageView imageView, TextView textView, EditText editText, Number number) {
        Log.d(TAG, "onButtonClick() called with: number = [" + number + "]");
        if (textView.getVisibility() == View.VISIBLE) {
            textView.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.ic_done);
        } else {
            textView.setVisibility(View.VISIBLE);
            editText.setVisibility(View.GONE);
            imageView.setImageResource(R.drawable.ic_edit);

            String input = editText.getText().toString();
            if (!input.isEmpty()) {
                int index = numberAdapter.getIndexOf(number);
                number.setNumber(input);
                updateNumber(number, index);
            }
        }
    }

    private void updateNumber(final Number number, final int index) {
        Tasker.start(number, new Tasker.Callback<Number, Number>() {
            @Override
            public Number onBackground(Number input) {
                if (input.getNext1() != null) {
                    Number nextNumber = database.numberDao().getNextNumber(input.getSegmentId(), input.getId());
                    nextNumber.setPrev(input.getNumber());
                    database.numberDao().update(nextNumber);
                    numberAdapter.update(nextNumber, index + 1);
                }
                if (input.getPrev() != null) {
                    List<Number> prevNumbers = database.numberDao().getPrevNumbers(input.getSegmentId(), input.getId());
                    Number[] nums = new Number[prevNumbers.size()];
                    for (int i = 0; i < prevNumbers.size(); i++) {
                        Number num = prevNumbers.get(i);
                        if (i==0) {
                            num.setNext1(input.getNumber());
                        } else if (i==1) {
                            num.setNext2(input.getNumber());
                        } else if (i==2) {
                            num.setNext3(input.getNumber());
                        }
                        nums[i] = num;
                    }
                    numberAdapter.update(index, nums);
                    database.numberDao().update(nums);
                }
                database.numberDao().update(input);
                return input;
            }

            @Override
            public void onUi(Number output) {
                numberAdapter.update(number, index);
                numberAdapter.notifyDataSetChanged();
            }
        }, binding.main.progressBar);
    }


}
