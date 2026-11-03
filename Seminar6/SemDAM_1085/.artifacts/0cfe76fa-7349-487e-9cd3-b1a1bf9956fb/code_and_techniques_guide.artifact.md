# Master Techniques & Code Breakdown Guide (With Drag-and-Drop UI Instructions)

This guide breaks down every technique used in this project and explains **how to do it yourself from scratch**, specifically focusing on how to design screens using **Android Studio's Drag-and-Drop (Design tab)**.

---

## Part 1: How to Do UI Design with Drag-and-Drop in Android Studio

When you open any XML file (like `activity_main.xml` or `activity_add.xml`), look at the top right corner of the code editor. You will see three tabs: **Design**, **Split**, and **Code**.
* Click **Design** to see the visual layout editor.
* **Palette (Left panel inside Design):** Contains widgets like TextView, EditText, Button, ListView, Spinner, RadioGroup, FloatingActionButton.
* **Component Tree (Bottom-left panel):** Shows the hierarchy of your UI elements.
* **Attributes (Right panel):** Where you set IDs (`id`), text (`text`), hints (`hint`), and layout constraints.

### 📋 Step-by-Step Drag-and-Drop Instructions for Each Screen:

#### 1. Main Activity (`activity_main.xml`)
* **Goal:** A screen with a list and an add button.
* **Drag & Drop steps:**
  1. Open `activity_main.xml` and switch to the **Design** tab.
  2. From the **Palette** (under *Containers* or *Legacy*), drag a **ListView** onto the screen.
     * In the **Attributes** panel on the right, set its **ID** to `@+id/listView`.
     * Constrain it to parent top, left, right, and bottom (or let it fill the screen).
  3. From the **Palette** (under *Buttons*), drag a **Floating Action Button** (`FloatingActionButton`) to the bottom-right corner.
     * Set its **ID** to `@+id/floatingActionButton`.
     * Set the `src` attribute (icon) to `@android:drawable/ic_input_add`.

#### 2. Add / Edit Activity (`activity_add.xml`)
* **Goal:** A form to input student details.
* **Drag & Drop steps:**
  1. Open `activity_add.xml` in **Design** view (ConstraintLayout).
  2. Drag three **Plain Text** (`EditText`) widgets onto the screen:
     * 1st EditText: ID = `editTextNume`, Hint = `"Introduceti numele"`
     * 2nd EditText: ID = `editTextDate`, Hint = `"MM/dd/yyyy"`, InputType = `date`
     * 3rd EditText: ID = `editTextMedie`, Hint = `"Introduceti media"`, InputType = `numberDecimal`
  3. Drag a **Spinner** (Dropdown) onto the screen:
     * ID = `spinnerFacultate`
  4. Drag a **Radio Group** (from *Buttons* > *Radio Group*) onto the screen:
     * ID = `radioGroup`, Orientation = `horizontal`
     * Inside it, make sure there are two **Radio Button**s: ID = `radioButtonDA` (Text: "DA") and ID = `radioButtonNU` (Text: "NU").
  5. Drag a **Button** onto the screen:
     * ID = `btnAdd`, Text = `"Salveaza Student"`

---

## Part 2: Core Java Techniques Explained

### Technique 1: Making a Class Serializable (Passing Objects via Intent)
* **What it is:** Android intents can pass primitive types (Strings, ints, floats), but not custom objects unless they implement `Serializable`.
* **How to do it:**
  ```java
  public class Student implements Serializable {
      // fields, constructor, getters, setters
  }
  ```
* **How to send it:**
  ```java
  Intent intent = new Intent(this, AddActivity.class);
  intent.putExtra("editStudent", studentObj);
  startActivity(intent);
  ```
* **How to receive it:**
  ```java
  Student student = (Student) getIntent().getSerializableExtra("editStudent");
  ```

---

### Technique 2: Populating a `ListView` with an `ArrayAdapter`
* **What it is:** Connects a Java `List<Student>` to a UI `ListView`.
* **How to do it:**
  ```java
  ListView listView = findViewById(R.id.listView);
  List<Student> listaStud = new ArrayList<>();

  // After adding items to listaStud:
  ArrayAdapter<Student> adapter = new ArrayAdapter<>(
          this,
          android.R.layout.simple_list_item_1,
          listaStud
  );
  listView.setAdapter(adapter);
  ```
* **Refreshing the list:** When you add or delete items, tell the adapter to redraw:
  ```java
  adapter.notifyDataSetChanged();
  ```

---

### Technique 3: Handling Clicks and Long Clicks on a `ListView`
* **Short Click (Edit):**
  ```java
  listView.setOnItemClickListener((parent, view, position, id) -> {
      Student selectedStudent = listaStud.get(position);
      // Pass to AddActivity for editing
  });
  ```
* **Long Click (Delete with Confirmation Dialog):**
  ```java
  listView.setOnItemLongClickListener((parent, view, position, id) -> {
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

      return true; // true means long click was handled
  });
  ```

---

### Technique 4: Two-Way Activity Communication (`startActivityForResult`)
* **Why use it?** When `MainActivity` opens `AddActivity` to add or edit a student, `AddActivity` needs to send the resulting `Student` object back to `MainActivity`.
* **Step 1: Launching with a Request Code (in `MainActivity`)**
  ```java
  public static final int REQUEST_CODE_ADD = 100;
  // ...
  Intent intent = new Intent(this, AddActivity.class);
  startActivityForResult(intent, REQUEST_CODE_ADD);
  ```
* **Step 2: Returning the result (in `AddActivity`)**
  ```java
  Intent resultIntent = new Intent();
  resultIntent.putExtra("addStudent", student);
  setResult(RESULT_OK, resultIntent);
  finish(); // Closes AddActivity and returns to MainActivity
  ```
* **Step 3: Receiving the result (in `MainActivity`)**
  ```java
  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK && data != null) {
          Student student = (Student) data.getSerializableExtra("addStudent");
          // Add to list and update adapter!
      }
  }
  ```

---

### Technique 5: Setting Up and Reading a `Spinner` (Dropdown)
* **How to populate in Java:**
  ```java
  String[] facultati = {"CSIE", "FABIZ", "REI", "MRK"};
  ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
          this,
          android.R.layout.simple_spinner_dropdown_item,
          facultati
  );
  Spinner spinner = findViewById(R.id.spinnerFacultate);
  spinner.setAdapter(spinnerAdapter);
  ```
* **How to get the selected value:**
  ```java
  String selectedFacultate = spinner.getSelectedItem().toString();
  ```
* **How to pre-select an item (when editing):**
  ```java
  for (int i = 0; i < spinnerAdapter.getCount(); i++) {
      if (spinnerAdapter.getItem(i).equals(student.getFacultate())) {
          spinner.setSelection(i);
          break;
      }
  }
  ```

---

### Technique 6: Input Validation and Error Handling
* **Checking empty fields:**
  ```java
  if (etNume.getText().toString().isEmpty()) {
      etNume.setError("Introduceti numele!");
  }
  ```
* **Parsing Dates and Numbers safely:**
  ```java
  try {
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
      Date data = sdf.parse(etDataNasterii.getText().toString());
      float medie = Float.parseFloat(etMedie.getText().toString());
      // Proceed with creating object...
  } catch (Exception e) {
      Toast.makeText(this, "Erore: " + e.getMessage(), Toast.LENGTH_LONG).show();
  }
  ```

---

### Technique 7: Options Menu (Action Bar Menu)
* **Step 1: Create Menu XML** (`res/menu/meniu_principal.xml`):
  ```xml
  <menu xmlns:android="http://schemas.android.com/apk/res/android">
      <item android:id="@+id/optiune1" android:title="Activitate BNR" />
  </menu>
  ```
* **Step 2: Inflate menu in Activity (`MainActivity.java`)**:
  ```java
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
  ```

---

### Technique 8: Activity Lifecycle Logging
* **Why do it?** To track when an activity starts, pauses, stops, or restarts.
* **How to do it:**
  ```java
  @Override
  protected void onStart() {
      super.onStart();
      Log.e("lifecycle", "Apel metoda onStart");
  }
  ```
  *(Repeat for `onResume`, `onPause`, `onStop`, `onDestroy`). Open **Logcat** at the bottom of Android Studio and filter by `lifecycle` to see these messages appear when you navigate between screens.*
