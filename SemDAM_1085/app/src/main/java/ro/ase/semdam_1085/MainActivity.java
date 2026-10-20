package ro.ase.semdam_1085;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public ListView listView;

    List<Student> listaStudenti = new ArrayList<>();

    public static final int REQUEST_CODE_ADD = 100;
    public static final int REQUEST_CODE_EDIT = 200;

    public static final String EDIT_STUDENT = "EditStudent";
    //psfs -- face fix ce e deasupra
    public int poz;
    //pozitia pe care editez ca sa stiu unde suprascriu in listview cand ma intorc din add activity in main activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        listView = findViewById(R.id.listView);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Student student = listaStudenti.get(position);

                ArrayAdapter<Student> adapter = (ArrayAdapter<Student>) listView.getAdapter();
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).setTitle("Confirmare stergere").setMessage("Esti sigur ca vrei sa stergi studentul?")
                        .setNegativeButton("NU", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).setPositiveButton("DA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listaStudenti.remove(student);
                                adapter.notifyDataSetChanged();
                                dialog.cancel();
                            }
                        }).create();

                dialog.show();
                return true;

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Student student = listaStudenti.get(position);
                poz = position; // salvam pozitia pentru onActivityResult
                Intent intent = new Intent(getApplicationContext(),AddActivity.class);
                intent.putExtra(EDIT_STUDENT,student); // folosim EDIT_STUDENT în loc de AddActivity.ADD_STUDENT
                startActivityForResult(intent,REQUEST_CODE_EDIT);
            }
        });
        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK&& data!=null)
        {
            Student student= (Student) data.getSerializableExtra(AddActivity.ADD_STUDENT);
            if(student!=null)
            {
                listaStudenti.add(student);
                ArrayAdapter<Student> adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,listaStudenti);
                listView.setAdapter(adapter);
            }
        }
        else if(requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK && data!=null)
        {
            Student student= (Student) data.getSerializableExtra(AddActivity.ADD_STUDENT);
            if(student!=null)
            {
                listaStudenti.get(poz).setNume(student.getNume());
                listaStudenti.get(poz).setMedie(student.getMedie());
                listaStudenti.get(poz).setFacultate(student.getFacultate());
                listaStudenti.get(poz).setAreBursa(student.isAreBursa());
                listaStudenti.get(poz).setDataNasterii(student.getDataNasterii());

                ArrayAdapter<Student> adapter = (ArrayAdapter<Student>) listView.getAdapter();
                adapter.notifyDataSetChanged();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meniu_principal, menu);
        return true;
    }
//ce se intampla cand apas pe ce buton!!
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem optiunieMeniu) {
        if(optiunieMeniu.getItemId() == R.id.optiune1)
        {
            Intent intent = new Intent(getApplicationContext(),BNRActivity.class);
            startActivity(intent);

            return true;
        }
        else if(optiunieMeniu.getItemId()==R.id.optiune2)
        {
            //TO DO
            return true;
        }
        else if(optiunieMeniu.getItemId()==R.id.optiune3)
        {
            //TO DO
            return true;
        }
        else if(optiunieMeniu.getItemId()==R.id.optiune4)
        {
            //TO DO
            return true;
        }
        return false;
    }
}