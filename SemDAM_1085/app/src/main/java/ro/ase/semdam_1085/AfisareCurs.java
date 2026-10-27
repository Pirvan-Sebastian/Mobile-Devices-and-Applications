package ro.ase.semdam_1085;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class AfisareCurs  extends AppCompatActivity {

    List<CursValutar> listaCurs = new ArrayList<>();

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

        CursValutar curs = (CursValutar) intent.getSerializableExtra(BNRActivity.ADD_CURS);
        if (curs != null) {
            listaCurs.add(curs);
        }

//        ArrayAdapter<CursValutar> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1, listaCurs);
        CustomAdapterBNR adapter = new CustomAdapter(getApplicationContext(),
                R.layout.elem_listview, listaCurs, getLayoutInflater()){

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                CursValutar curs1 = listaCurs.get(position);

                TextView tvMedie = view.findViewById(R.id.tvMedie);

                return view;
            }
        };
//        CustomAdapterBNR adapter = (CustomAdapterBNR) listView.getAdapter();
        listViewAfisare.setAdapter(adapter);
    }
}
