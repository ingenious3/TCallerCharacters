package com.example.tcallercharacters.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tcallercharacters.R;
import com.example.tcallercharacters.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.tv_val_10thchar) TextView textView10thCharValue;
    @BindView(R.id.tv_val_every10thchar) TextView textViewEvery10thCharValue;
    @BindView(R.id.tv_val_wordcount) TextView textViewWordCountValue;
    @BindView(R.id.start) Button startButton;
    MainPresenter presenter;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new MainPresenter(this);
        presenter.init();

        textViewEvery10thCharValue.setMovementMethod(new ScrollingMovementMethod());
        textViewWordCountValue.setMovementMethod(new ScrollingMovementMethod());
    }

    @OnClick(R.id.start)
    void buttonClick() {
        if(presenter != null) {
            presenter.onButtonClick();
        }
    }


    @Override
    public void showResult(String val10thChar, String valEvery10thChar, String wordCounts) {
        if(!val10thChar.equalsIgnoreCase("")) {
            textView10thCharValue.setText(val10thChar);
        }
        if(!valEvery10thChar.equalsIgnoreCase("")) {
            textViewEvery10thCharValue.setText(valEvery10thChar);
        }
        if(!wordCounts.equalsIgnoreCase("")) {
            textViewWordCountValue.setText(wordCounts);
        }
    }

    @Override
    public void showProgress() {
        startButton.setEnabled(false);
        startButton.setClickable(false);
        if(dialog == null) {
            dialog = new ProgressDialog(this);
        }
        dialog.setMessage("Loading ....");
        dialog.show();
    }

    @Override
    public void hideProgress() {
        startButton.setEnabled(true);
        startButton.setClickable(true);
        if(dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void showError() {
        Toast.makeText(this,"Something went wrong. Please try again", Toast.LENGTH_LONG);
        startButton.setEnabled(true);
        startButton.setClickable(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
