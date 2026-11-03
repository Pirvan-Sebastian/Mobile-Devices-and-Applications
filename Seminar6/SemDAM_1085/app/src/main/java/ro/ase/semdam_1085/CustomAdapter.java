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

public class CustomAdapter extends ArrayAdapter<Student> {

    private Context context;
    private int resource;
    private List<Student> studentList;
    LayoutInflater layoutInflater;
    public CustomAdapter(@NonNull Context context, int resource,
                         List<Student> list, LayoutInflater layoutInflater) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.studentList = list;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);

        Student student = studentList.get(position);

        if(student!=null)
        {
            //populez view-ul cu datele obiectului student
            TextView tvNume = view.findViewById(R.id.tvNume);
            tvNume.setText(student.getNume());

            TextView tvDataNasterii = view.findViewById(R.id.tvDataNasterii);
            tvDataNasterii.setText(student.getDataNasterii().toString());
            //.toString() transforma in string ceva ce nu e string

            TextView tvMedie = view.findViewById(R.id.tvMedie);
            tvMedie.setText(String.valueOf(student.getMedie()));
            //String.valueOf(fac string din primitive ce nu au toString())
            TextView tvFacultate = view.findViewById(R.id.tvFacultate);
            tvFacultate.setText(student.getFacultate());

            TextView tvBursa = view.findViewById(R.id.tvBursa);
            tvBursa.setText(String.valueOf(student.isAreBursa()));
        }

        return view;
    }
}
