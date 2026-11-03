# Explaining the Android `Intent` Code in SemDAM_1085

In this project, `Intent` objects are used to pass `Student` objects back and forth between activities (`MainActivity` / `listViewActivity` and `AddActivity`) for both creating and editing students.

---

## 1. Opening `AddActivity` to Edit a Student

When a user clicks on a student in the `ListView`, the app sends the selected `Student` object to `AddActivity` using `startActivityForResult`:

```java
Student student = listaStudenti.get(position);
poz = position; // Save position to know which item to update when returning

Intent intent = new Intent(getApplicationContext(), AddActivity.class);
intent.putExtra(MainActivity.EDIT_STUDENT, student);
startActivityForResult(intent, REQUEST_CODE_EDIT);
```

### Key Components:
- **`Intent intent = new Intent(getApplicationContext(), AddActivity.class);`**: Prepares a transition from the current activity to `AddActivity`.
- **`intent.putExtra(MainActivity.EDIT_STUDENT, student);`**: Attaches the selected `Student` object using the constant key `MainActivity.EDIT_STUDENT` (`"EditStudent"`).
- **`startActivityForResult(intent, REQUEST_CODE_EDIT);`**: Launches `AddActivity` expecting a result (edited student data) back when it finishes.

---

## 2. Reading Data and Populating UI in `AddActivity`

Inside `AddActivity.onCreate()`, the app checks if an `Intent` extra named `MainActivity.EDIT_STUDENT` was passed:

```java
Intent intent = getIntent();

if (intent.hasExtra(MainActivity.EDIT_STUDENT)) {
    // Retrieve the passed Student object
    Student student = (Student) intent.getSerializableExtra(MainActivity.EDIT_STUDENT);

    // Populate the UI fields with student data
    etNume.setText(student.getNume());
    etDataNasterii.setText(new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(student.getDataNasterii()));
    etMedie.setText(String.valueOf(student.getMedie()));

    // Select the correct faculty in the Spinner
    ArrayAdapter<String> adapter1 = (ArrayAdapter<String>) spinnerFacultati.getAdapter();
    for (int i = 0; i < adapter1.getCount(); i++) {
        if (adapter1.getItem(i).equals(student.getFacultate())) {
            spinnerFacultati.setSelection(i);
            break;
        }
    }

    // Set the RadioButton for scholarship status
    if (student.isAreBursa()) {
        radioGroup.check(R.id.radioButtonDA);
    } else {
        radioGroup.check(R.id.radioButtonNU);
    }
}
```

### Key Components:
- **`intent.getSerializableExtra(MainActivity.EDIT_STUDENT)`**: Casts the extra back to a `Student` object. (This works because `Student` implements `java.io.Serializable`).
- **UI Population**: Updates `etNume`, `etDataNasterii`, `etMedie`, `spinnerFacultati`, and `radioGroup` with the student's fields (`nume`, `dataNasterii`, `medie`, `facultate`, `areBursa`).

---

## 3. Creating/Updating a `Student` and Returning the Result

When the user clicks the **Save/Add** button in `AddActivity`:

```java
// Read values from form controls
String numeStudent = etNume.getText().toString();
Date dataNasterii = sdf.parse(etDataNasterii.getText().toString());
float medie = Float.parseFloat(etMedie.getText().toString());
String facultate = spinnerFacultati.getSelectedItem().toString();

RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
boolean areBursa = radioButton.getText().toString().equals("DA");

// Create new Student instance
Student student = new Student(numeStudent, dataNasterii, medie, facultate, areBursa);

// Attach the student object to the intent and set result
intent.putExtra(ADD_STUDENT, student);
setResult(RESULT_OK, intent);
finish(); // Close AddActivity and return
```

### Key Components:
- **`new Student(numeStudent, dataNasterii, medie, facultate, areBursa)`**: Instantiates a `Student` object with all input fields.
- **`setResult(RESULT_OK, intent)`**: Sets the result code to `RESULT_OK` and attaches the intent containing the student data.
- **`finish()`**: Destroys `AddActivity` so control returns to the caller activity.

---

## 4. Handling the Result in `onActivityResult`

Back in `MainActivity` or `listViewActivity`, `onActivityResult()` receives the updated/new student:

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK && data != null) {
        Student student = (Student) data.getSerializableExtra(AddActivity.ADD_STUDENT);
        if (student != null) {
            listaStudenti.add(student);
            ArrayAdapter<Student> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, listaStudenti);
            listView.setAdapter(adapter);
        }
    }
    else if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK && data != null) {
        Student student = (Student) data.getSerializableExtra(AddActivity.ADD_STUDENT);
        if (student != null) {
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
```
