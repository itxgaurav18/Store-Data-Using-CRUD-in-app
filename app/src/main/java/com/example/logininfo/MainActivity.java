package com.example.logininfo;



import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextFirstName, editTextLastName, editTextPhone, editTextEmail, editTextAddress, editTextUserId;
    private Button buttonAdd, buttonShowUsers, buttonUpdate, buttonDelete;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        editTextUserId =findViewById(R.id.editTextUserId);// New EditText for user ID
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextAddress = findViewById(R.id.editTextAddress);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonShowUsers = findViewById(R.id.buttonShowUsers);
        buttonUpdate = findViewById(R.id.buttonUpdate);  // New button for updating
        buttonDelete = findViewById(R.id.buttonDelete);  // New button for deleting

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Add user to the database
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    String firstName = editTextFirstName.getText().toString();
                    String lastName = editTextLastName.getText().toString();
                    String phone = editTextPhone.getText().toString();
                    String email = editTextEmail.getText().toString();
                    String address = editTextAddress.getText().toString();

                    long id = databaseHelper.insertUser(firstName, lastName, phone, email, address);
                    if (id > 0) {
                        Toast.makeText(MainActivity.this, "User Added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Error Adding User", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Show all users
        buttonShowUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = databaseHelper.getAllUsers();
                if (cursor.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "No Users Found", Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuilder userData = new StringBuilder();
                while (cursor.moveToNext()) {
                    userData.append("ID: ").append(cursor.getInt(0)).append("\n");
                    userData.append("First Name: ").append(cursor.getString(1)).append("\n");
                    userData.append("Last Name: ").append(cursor.getString(2)).append("\n");
                    userData.append("Phone: ").append(cursor.getString(3)).append("\n");
                    userData.append("Email: ").append(cursor.getString(4)).append("\n");
                    userData.append("Address: ").append(cursor.getString(5)).append("\n\n");
                }
                Toast.makeText(MainActivity.this, userData.toString(), Toast.LENGTH_LONG).show();
            }
        });

        // Update user in the database
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    String id = editTextUserId.getText().toString();
                    String firstName = editTextFirstName.getText().toString();
                    String lastName = editTextLastName.getText().toString();
                    String phone = editTextPhone.getText().toString();
                    String email = editTextEmail.getText().toString();
                    String address = editTextAddress.getText().toString();

                    boolean updated = databaseHelper.updateUser(Integer.parseInt(id), firstName, lastName, phone, email, address);
                    if (updated) {
                        Toast.makeText(MainActivity.this, "User Updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Error Updating User", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Delete user from the database
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editTextUserId.getText().toString();
                boolean deleted = databaseHelper.deleteUser(Integer.parseInt(id));
                if (deleted) {
                    Toast.makeText(MainActivity.this, "User Deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error Deleting User", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Validate the input fields before updating or adding
    private boolean validateInputs() {
        if (editTextFirstName.getText().toString().trim().isEmpty()) {
            editTextFirstName.setError("First name required");
            return false;
        }
        if (editTextLastName.getText().toString().trim().isEmpty()) {
            editTextLastName.setError("Last name required");
            return false;
        }
        if (editTextPhone.getText().toString().trim().isEmpty()) {
            editTextPhone.setError("Phone number required");
            return false;
        }
        if (editTextEmail.getText().toString().trim().isEmpty()) {
            editTextEmail.setError("Email required");
            return false;
        }
        if (editTextAddress.getText().toString().trim().isEmpty()) {
            editTextAddress.setError("Address required");
            return false;
        }
        return true;
    }
}

