package com.voanhhao.kiemtra_hk2.cau1;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.voanhhao.kiemtra_hk2.R;
import com.voanhhao.kiemtra_hk2.databinding.ActivityMain2Binding;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity2 extends AppCompatActivity {
    ActivityMain2Binding binding;
    ExecutorService executorService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        executorService = Executors.newSingleThreadExecutor();
        addEvents();
    }

    private void addEvents() {
        binding.btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.linearLsv.removeAllViews();
                execLongRunningTask();
            }
        });
    }

    private void execLongRunningTask() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                final int[] numbOfView = {Integer.parseInt(binding.edtMes.getText().toString())};
                Random random = new Random();
                final int[] textViewCounter = {0};

                for (int i = 1; i <= numbOfView[0]; i++) {
                    int percent, randNumb;
                    percent = i * 100 / numbOfView[0];
                    randNumb = random.nextInt(9);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.txtLoading.setText(percent + "%");
                            binding.progressbar.setProgress(percent);

                            // Chỉ tạo hàng ngang mới nếu là TextView đầu tiên của hàng
                            if (textViewCounter[0] % 3 == 0) {
                                LinearLayout rowLinearLayout = new LinearLayout(MainActivity2.this);
                                rowLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                rowLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT));
                                binding.linearLsv.addView(rowLinearLayout);
                            }

                            // Lấy hàng ngang cuối cùng trong linearLsv để thêm TextView vào
                            LinearLayout currentRow = (LinearLayout) binding.linearLsv.getChildAt(binding.linearLsv.getChildCount() - 1);

                            TextView textView = new TextView(MainActivity2.this);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    0, // Chiều rộng bằng 0 để có thể sử dụng weight
                                    200, // Chiều cao cố định
                                    1 // Trọng số để chia đều không gian
                            );
                            params.setMargins(10, 10, 10, 10);

                            if (randNumb % 2 == 0) {
                                textView.setBackgroundColor(Color.rgb(0, 0, 255)); // Màu xanh dương
                            } else {
                                textView.setBackgroundColor(Color.rgb(128, 128, 128)); // Màu xám
                            }

                            textView.setText(String.valueOf(randNumb));
                            textView.setTextColor(Color.WHITE);
                            textView.setGravity(Gravity.CENTER);
                            textView.setLayoutParams(params);

                            currentRow.addView(textView);
                            textViewCounter[0]++;

                            if (percent == 100) {
                                binding.txtLoading.setText("DONE!");
                            }
                        }
                    });

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (executorService != null){
            executorService.shutdownNow();
        }
    }
}