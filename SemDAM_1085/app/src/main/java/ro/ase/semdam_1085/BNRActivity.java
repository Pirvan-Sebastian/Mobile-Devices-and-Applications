package ro.ase.semdam_1085;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BNRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bnr);

        Log.e("lifecycle", "Apel metoda onCreate");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnShow = findViewById(R.id.btnShow);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("lifecycle", "Apel metoda onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("lifecycle", "Apel metoda onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("lifecycle", "Apel metoda onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("lifecycle", "Apel metoda onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("lifecycle", "Apel metoda onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("lifecycle", "Apel metoda onDestroy");
    }
}