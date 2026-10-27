package ro.ase.semdam_1085;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomAdapterBNR extends ArrayAdapter<CursValutar> {

    private Context context;
    private int resource;
    private List<CursValutar> cursList;
    private LayoutInflater layoutInflater;

    public CustomAdapterBNR(@NonNull Context context, int resource,
                            List<CursValutar> list, LayoutInflater layoutInflater) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.cursList = list;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);

        CursValutar cursValutar = cursList.get(position);

        if (cursValutar != null) {
            TextView tvDataCurs = view.findViewById(R.id.dataCurs);
            tvDataCurs.setText(cursValutar.getDataCurs());

            TextView tvCursEur = view.findViewById(R.id.cursEur);
            tvCursEur.setText(cursValutar.getCursEur());

            TextView tvCursUsd = view.findViewById(R.id.cursUsd);
            tvCursUsd.setText(cursValutar.getCursUsd());

            TextView tvCursGbp = view.findViewById(R.id.cursGBP);
            tvCursGbp.setText(cursValutar.getCursGbp());

            TextView tvCursXau = view.findViewById(R.id.cursXau);
            tvCursXau.setText(cursValutar.getCursXau());
        }

        return view;
    }
}
