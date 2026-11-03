package ro.ase.semdam_1085;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class AfisareCurs extends AppCompatActivity {

    private final List<CursValutar> listaCurs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_afisare_curs_valutar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView listViewAfisare = findViewById(R.id.listViewAfisare);

        Intent intent = getIntent();
        if (intent != null) {
            CursValutar curs = IntentCompat.getSerializableExtra(intent, BNRActivity.ADD_CURS, CursValutar.class);
            if (curs != null) {
                listaCurs.add(curs);
            }
        }

        CustomAdapterBNR adapter = new CustomAdapterBNR(this,
                R.layout.elem_listview_bnr, listaCurs, getLayoutInflater());
        listViewAfisare.setAdapter(adapter);
    }
}
