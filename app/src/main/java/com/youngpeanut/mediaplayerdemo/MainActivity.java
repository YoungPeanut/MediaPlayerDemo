package com.youngpeanut.mediaplayerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.youngpeanut.mediaplayerdemo.demo3.Demo3Activity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    findViewById(R.id.button5).setOnClickListener(this);
    findViewById(R.id.button6).setOnClickListener(this);
    findViewById(R.id.button7).setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent();

    switch (v.getId()) {
      case R.id.button5:
        intent.setClass(this, Demo1Activity.class);
        break;
      case R.id.button6:
        intent.setClass(this, Demo2Activity.class);
        break;
      case R.id.button7:
        intent.setClass(this, Demo3Activity.class);
        break;
    }

    startActivity(intent);

  }
}
