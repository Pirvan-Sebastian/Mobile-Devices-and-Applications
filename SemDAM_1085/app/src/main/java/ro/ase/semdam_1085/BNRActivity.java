package ro.ase.semdam_1085;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BNRActivity extends AppCompatActivity {

    public static final String ADD_CURS = "addCurs";

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
        //salvez in variabile id urile(referinte la) campurile din XML
        TextView tvDate = findViewById(R.id.tvDate);
        EditText etEUR = findViewById(R.id.editTextEUR);
        EditText etUSD = findViewById(R.id.editTextUSD);
        EditText etGBP = findViewById(R.id.editTextGBP);
        EditText etXAU = findViewById(R.id.editTextXAU);

        Button btnShow = findViewById(R.id.btnShow);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = tvDate.getText().toString();
                String eur = etEUR.getText().toString();
                String usd = etUSD.getText().toString();
                String gbp = etGBP.getText().toString();
                String xau = etXAU.getText().toString();
                CursValutar curs = new CursValutar(date, eur, usd, gbp, xau);

                Intent intent = new Intent(getApplicationContext(), AfisareCurs.class);
                intent.putExtra(ADD_CURS, curs);
                startActivity(intent);
            }
        });
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

//in bnr activity dau click pe buton si ma duce in afisare activity nou pt bnr