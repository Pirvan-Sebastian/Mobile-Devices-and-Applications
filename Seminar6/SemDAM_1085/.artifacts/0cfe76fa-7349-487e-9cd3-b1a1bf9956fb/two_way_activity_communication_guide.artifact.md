# Two-Way Activity Communication (`startActivityForResult`) Guide

This guide explains **how code and data are exchanged step-by-step at runtime** when using `startActivityForResult`, `setResult`, and `onActivityResult` in Android.

---

## Code Reference

### Step 1: Launching with a Request Code (in `MainActivity`)
```java
public static final int REQUEST_CODE_ADD = 100;
// ...
Intent intent = new Intent(this, AddActivity.class);
startActivityForResult(intent, REQUEST_CODE_ADD);
```

### Step 2: Returning the Result (in `AddActivity`)
```java
Intent resultIntent = new Intent();
resultIntent.putExtra("addStudent", student);
setResult(RESULT_OK, resultIntent);
finish(); // Closes AddActivity and returns to MainActivity
```

### Step 3: Receiving the Result (in `MainActivity`)
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

## Step-by-Step Runtime Execution Flow

### 🟢 1. Request Phase (`MainActivity`)
1. **User Action:** The user taps the Add button in `MainActivity`.
2. **Code Execution:**
   * `MainActivity` creates an `Intent` targeting `AddActivity.class`.
   * It calls `startActivityForResult(intent, REQUEST_CODE_ADD)` (where `REQUEST_CODE_ADD = 100`).
3. **Behind the Scenes:**
   * `MainActivity` tells the **Android OS**: *"Please open `AddActivity`, and tag this request with ID `100` so I can identify the response when it comes back."*
   * `MainActivity` enters the background (`onPause()` $\rightarrow$ `onStop()`).
   * Android OS creates and displays `AddActivity`.

---

### 🟡 2. Input & Packaging Phase (`AddActivity`)
1. **User Action:** The user fills in the form and clicks **Save**.
2. **Code Execution:**
   * `AddActivity` creates a `Student` object from the form fields.
   * It creates an empty `resultIntent` and attaches the `Student` object using `putExtra("addStudent", student)`.
   * It calls `setResult(RESULT_OK, resultIntent)` to set the result status code to `RESULT_OK` (meaning operation succeeded) and attaches `resultIntent` as the payload.
   * It calls `finish()` to request closure of `AddActivity`.
3. **Behind the Scenes:**
   * `AddActivity` is destroyed by Android (`onDestroy()`).
   * Android OS brings `MainActivity` back to the foreground.

---

### 🔵 3. Reception & Unpacking Phase (`MainActivity`)
1. **Automatic Callback:** As `MainActivity` resumes, the Android OS automatically invokes `MainActivity.onActivityResult()` before displaying the screen.
2. **Argument Passing by Android OS:**
   * `requestCode` = `100` *(matches the ID `MainActivity` sent in Step 1)*
   * `resultCode` = `RESULT_OK` *(matches the status code set by `AddActivity` in Step 2)*
   * `data` = `resultIntent` *(the `Intent` carrying the `"addStudent"` extra)*
3. **Code Execution:**
   * The `if` condition checks: Is `requestCode == 100`? Is `resultCode == RESULT_OK`? Is `data != null`?
   * If `true`, it unpacks the `Student` object: `Student student = (Student) data.getSerializableExtra("addStudent")`.
   * It adds the student to `listaStudenti`.
   * It calls `adapter.notifyDataSetChanged()` to refresh the `ListView` UI on screen.

---

## 📊 Summary Diagram

```
[ MainActivity ]  --- (startActivityForResult, REQUEST_CODE = 100) --->  [ AddActivity ]
                                                                                |
                                                                        (User fills form & clicks Save)
                                                                                |
[ MainActivity ]  <--- (setResult(RESULT_OK, intent) + finish()) --------  [ AddActivity ]
       |
(onActivityResult triggers: unpacks "addStudent" -> updates ListView)
```
