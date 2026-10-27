# Explaining the Android `Intent` Code

```java
Intent intent = new Intent(this, AddActivity.class);
intent.putExtra("editStudent", studentObj);
startActivity(intent);
```

This code opens `AddActivity` and sends a student object to it. The receiving
activity can use the object to populate an edit form.

## Line-by-line explanation

### 1. Create the `Intent`

```java
Intent intent = new Intent(this, AddActivity.class);
```

An `Intent` is a message that Android uses to start another component, such as
an activity.

- `this` refers to the current activity.
- `AddActivity.class` identifies the activity that should be opened.
- The intent therefore describes a transition from the current activity to
  `AddActivity`.

### 2. Add data to the `Intent`

```java
intent.putExtra("editStudent", studentObj);
```

`putExtra` attaches additional data to the intent as a key-value pair.

- `"editStudent"` is the key used to identify the data.
- `studentObj` is the value being sent to `AddActivity`.

The object must be supported by Android intent extras. For example, the
student class can implement `Serializable` or `Parcelable`.

### 3. Start the destination activity

```java
startActivity(intent);
```

This tells Android to launch `AddActivity` using the intent. The destination
activity can then read the extra value.

## Reading the data in `AddActivity`

If `studentObj` implements `Serializable`, the receiving activity can retrieve
it like this:

```java
Student student = (Student) getIntent().getSerializableExtra("editStudent");
```

The key must match the key used with `putExtra`:

```java
"editStudent"
```

Once retrieved, `AddActivity` can use `student` to fill in the input fields
for editing.

## Creating and filling `studentObj`

`studentObj` is an instance of the `Student` class. It is created and filled
with data from the UI fields:

```java
Student studentObj = new Student();

studentObj.setName(nameLabel.getText().toString());
studentObj.setAge(ageLabel.getText().toString());
```

### What this code does

- `new Student()` creates a new `Student` object.
- `nameLabel.getText().toString()` reads the name from the UI field and
  converts it to a `String`.
- `studentObj.setName(...)` stores the name in the object.
- `ageLabel.getText().toString()` reads the age from the UI field and
  converts it to a `String`.
- `studentObj.setAge(...)` stores the age in the object.

The completed object is then passed to `AddActivity`:

```java
intent.putExtra("editStudent", studentObj);
```

Here, `"editStudent"` is the key used to identify the object, and
`studentObj` is the student data being sent. `AddActivity` can retrieve this
object and use its values to populate an edit form.
