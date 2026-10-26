# Absolute Beginner's Guide: Building the Student Management Android App from Scratch

Welcome! If this is your first time using Android Studio and Java, don't worry. This guide will walk you through every single click, button, menu, and line of code needed to build this app from scratch.

---

## Part 1: Android Studio UI Orientation (Where to Click)

Before writing any code, let's get familiar with Android Studio's interface:
1. **Project Panel (Left Side):** This is where you see all your files. Make sure the dropdown at the top of this panel is set to **Android** (or **Project**).
   * **`app > src > main > java > ro.ase.semdam_1085`**: Where your Java code files (`.java`) live.
   * **`app > src > main > res > layout`**: Where your UI screen designs (`.xml`) live.
   * **`app > src > main > res > values > strings.xml`**: Where text strings are stored.
   * **`app > src > main > AndroidManifest.xml`**: The app's configuration file.
2. **Code Editor (Center):** Where you write your Java and XML code. You can switch between **Design** view and **Code** view using tabs at the top right of the editor when viewing XML layouts.
3. **Run Button (Top Right):** A green play button ▶️ (or shortcut `Shift + F10`) that builds your app and installs it on your emulator or physical phone.
4. **Logcat (Bottom):** A panel used to view logs, error messages, and lifecycle messages (`Log.e`).

---

## Part 2: Creating a New Project

1. Open **Android Studio**.
2. Click **New Project** (or go to `File > New > New Project`).
3. Select **Views Activity** (previously called Empty Activity) and click **Next**.
4. Fill in the project details:
   * **Name:** `SemDAM_1085`
   * **Package name:** `ro.ase.semdam_1085`
   * **Save location:** Choose your preferred folder.
   * **Language:** **Java** (Make sure Java is selected, not Kotlin!)
   * **Minimum SDK:** API 24 or higher.
5. Click **Finish** and wait for Gradle to sync (watch the loading bar at the bottom right).

---

## Part 3: Creating the Data Model (`Student.java`)

We need a class to represent a Student.

1. **How to create:** Right-click on your package folder (`ro.ase.semdam_1085`) in the left panel > **New** > **Java Class** > Type `Student` and press Enter.
2. **Code:** Replace the content with:

```java
package ro.ase.semdam_1085;

import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable {

    private String nume;
    private Date dataNasterii;
    private float medie;
    private String facultate;
    private boolean areBursa;

    public Student(String nume, Date dataNasterii, float medie, String facultate, boolean areBursa) {
        this.nume = nume;
        this.dataNasterii = dataNasterii;
        this.medie = medie;
        this.facultate = facultate;
        this.areBursa = areBursa;
    }

    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }

    public Date getDataNasterii() { return dataNasterii; }
    public void setDataNasterii(Date dataNasterii) { this.dataNasterii = dataNasterii; }

    public float getMedie() { return medie; }
    public void setMedie(float medie) { this.medie = medie; }

    public String getFacultate() { return facultate; }
    public void setFacultate(String facultate) { this.facultate = facultate; }

    public boolean isAreBursa() { return areBursa; }
    public void setAreBursa(boolean areBursa) { this.areBursa = areBursa; }

    @Override
    public String toString() {
        return "Student{" +
                "nume='" + nume + '\'' +
                ", dataNasterii=" + dataNasterii +
                ", medie=" + medie +
                ", facultate='" + facultate + '\'' +
                ", areBursa=" + areBursa +
                '}';
    }
}
```

> [!TIP]
> **Why `Serializable`?** Implementing `Serializable` allows us to pass `Student` objects between different Activities inside an `Intent`.

---

## Part 4: Designing the UI Layouts (`res/layout`)

Go to `app > src > main > res > layout`. You will design or open XML files here.

### 1. Main Screen (`activity_main.xml`)
This shows the list of students and a floating add button.
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <ListView
        android:id="@+id/listView"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/floatingActionButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginEnd="24dp"
        android:layout_marginBottom="24dp"
        android:clickable="true"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:srcCompat="@android:drawable/ic_input_add" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### 2. Add / Edit Form (`activity_add.xml`)
Create a new layout XML file named `activity_add.xml`.
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".AddActivity">

    <EditText
        android:id="@+id/editTextNume"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="16dp"
        android:hint="Introduceti numele"
        android:inputType="text"
        app:layout_constraintTop_toTopOf="parent" />

    <EditText
        android:id="@+id/editTextDate"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="16dp"
        android:hint="MM/dd/yyyy"
        android:inputType="date"
        app:layout_constraintTop_toBottomOf="@+id/editTextNume" />

    <EditText
        android:id="@+id/editTextMedie"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="16dp"
        android:hint="Introduceti media"
        android:inputType="numberDecimal"
        app:layout_constraintTop_toBottomOf="@+id/editTextDate" />

    <Spinner
        android:id="@+id/spinnerFacultate"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="16dp"
        app:layout_constraintTop_toBottomOf="@+id/editTextMedie" />

    <RadioGroup
        android:id="@+id/radioGroup"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="16dp"
        android:orientation="horizontal"
        app:layout_constraintTop_toBottomOf="@+id/spinnerFacultate"
        app:layout_constraintStart_toStartOf="parent">

        <RadioButton
            android:id="@+id/radioButtonDA"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:checked="true"
            android:text="DA" />

        <RadioButton
            android:id="@+id/radioButtonNU"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="NU" />
    </RadioGroup>

    <Button
        android:id="@+id/btnAdd"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="16dp"
        android:text="Salveaza Student"
        app:layout_constraintTop_toBottomOf="@+id/radioGroup" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

---

## Part 5: Coding the Activities (`.java`)

### 1. `MainActivity.java`
Manages the list of students, handles adding/editing via `startActivityForResult`, long-press deletion, and options menu.

```java
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public ListView listView;
    List<Student> listaStud = new ArrayList<>();
    public static final int REQUEST_CODE_ADD = 100;
    public static final int REQUEST_CODE_EDIT = 200;
    public static final String EDIT_STUDENT = "editStudent";
    public int poz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD);
        });

        // Long click to delete
        listView.setOnItemLongClickListener((adapterView, view, position, id) -> {
            Student student = listaStud.get(position);
            ArrayAdapter adapter = (ArrayAdapter) listView.getAdapter();

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Confirmare stergere")
                    .setMessage("Doriti sa stergeti?")
                    .setNegativeButton("NU", (dialog, i) -> dialog.cancel())
                    .setPositiveButton("DA", (dialog, i) -> {
                        listaStud.remove(student);
                        adapter.notifyDataSetChanged();
                        dialog.cancel();
                    })
                    .show();
            return true;
        });

        // Normal click to edit
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            poz = position;
            Intent intent = new Intent(getApplicationContext(), AddActivity.class);
            intent.putExtra(EDIT_STUDENT, listaStud.get(position));
            startActivityForResult(intent, REQUEST_CODE_EDIT);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK && data != null) {
            Student student = (Student) data.getSerializableExtra(AddActivity.ADD_STUDENT);
            if (student != null) {
                listaStud.add(student);
                ArrayAdapter<Student> adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, listaStud);
                listView.setAdapter(adapter);
            }
        } else if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK && data != null) {
            Student student = (Student) data.getSerializableExtra(AddActivity.ADD_STUDENT);
            if (student != null) {
                listaStud.get(poz).setNume(student.getNume());
                listaStud.get(poz).setDataNasterii(student.getDataNasterii());
                listaStud.get(poz).setMedie(student.getMedie());
                listaStud.get(poz).setFacultate(student.getFacultate());
                listaStud.get(poz).setAreBursa(student.isAreBursa());

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.optiune1) {
            Intent intent = new Intent(this, BNRActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
}
```

### 2. `AddActivity.java`
Populates the spinner, checks if editing an existing student, validates input, and sends data back.

```java
package ro.ase.semdam_1085;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    public static final String ADD_STUDENT = "addStudent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Intent intent = getIntent();

        String[] facultati = {"CSIE", "FABIZ", "REI", "MRK"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, facultati);
        Spinner spinnerFacultati = findViewById(R.id.spinnerFacultate);
        spinnerFacultati.setAdapter(adapter);

        EditText etNume = findViewById(R.id.editTextNume);
        EditText etDataNasterii = findViewById(R.id.editTextDate);
        EditText etMedie = findViewById(R.id.editTextMedie);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        // If editing, populate fields
        if (intent.hasExtra(MainActivity.EDIT_STUDENT)) {
            Student student = (Student) intent.getSerializableExtra(MainActivity.EDIT_STUDENT);
            etNume.setText(student.getNume());
            etDataNasterii.setText(new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(student.getDataNasterii()));
            etMedie.setText(String.valueOf(student.getMedie()));

            for (int i = 0; i < adapter.getCount(); i++) {
                if (adapter.getItem(i).equals(student.getFacultate())) {
                    spinnerFacultati.setSelection(i);
                    break;
                }
            }
            if (student.isAreBursa()) {
                radioGroup.check(R.id.radioButtonDA);
            } else {
                radioGroup.check(R.id.radioButtonNU);
            }
        }

        Button btnAdauga = findViewById(R.id.btnAdd);
        btnAdauga.setOnClickListener(view -> {
            if (etNume.getText().toString().isEmpty()) {
                etNume.setError("Introduceti numele!");
            } else if (etDataNasterii.getText().toString().isEmpty()) {
                etDataNasterii.setError("Introduceti data nasterii!");
            } else if (etMedie.getText().toString().isEmpty()) {
                etMedie.setError("Introduceti media!");
            } else {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                    Date dataNasterii = sdf.parse(etDataNasterii.getText().toString());
                    String nume = etNume.getText().toString();
                    float medie = Float.parseFloat(etMedie.getText().toString());
                    String facultate = spinnerFacultati.getSelectedItem().toString();

                    RadioButton rb = findViewById(radioGroup.getCheckedRadioButtonId());
                    boolean areBursa = rb.getText().toString().equals("DA");

                    Student student = new Student(nume, dataNasterii, medie, facultate, areBursa);

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(ADD_STUDENT, student);
                    setResult(RESULT_OK, resultIntent);
                    finish();

                } catch (Exception e) {
                    Toast.makeText(this, "Erore: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
```

---

## Part 6: Registering Activities in `AndroidManifest.xml`

Open `app > src > main > AndroidManifest.xml`. Make sure all activities (`MainActivity`, `AddActivity`, `AfisareActivity`, `BNRActivity`) are declared inside the `<application>` tag:

```xml
<application
    android:allowBackup="true"
    android:dataExtractionRules="@xml/data_extraction_rules"
    android:fullBackupOnly="true"
    android:icon="@mipmap/ic_launcher"
    android:label="@string/app_name"
    android:roundIcon="@mipmap/ic_launcher_round"
    android:supportsRtl="true"
    android:theme="@style/Theme.SemDAM_1085">

    <activity
        android:name=".MainActivity"
        android:exported="true">
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />
            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
    </activity>

    <activity android:name=".AddActivity" android:exported="false" />
    <activity android:name=".AfisareActivity" android:exported="false" />
    <activity android:name=".BNRActivity" android:exported="false" />

</application>
```

---

## Part 7: Running the App

1. Click the **Device Manager** icon on the right sidebar in Android Studio (or go to `Tools > Device Manager`) to start an Emulator, or plug in your Android phone with USB Debugging enabled.
2. Click the green **Run** button ▶️ at the top right toolbar (or press `Shift + F10`).
3. Test adding a student, editing by tapping, and deleting by long-pressing!
