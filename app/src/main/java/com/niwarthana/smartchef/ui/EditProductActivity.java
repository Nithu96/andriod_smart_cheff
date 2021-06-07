package com.niwarthana.smartchef.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.niwarthana.smartchef.R;
import com.niwarthana.smartchef.db.DBHelper;
import com.niwarthana.smartchef.model.Food;
import com.niwarthana.smartchef.util.Utils;

public class EditProductActivity extends AppCompatActivity {
    private TextInputLayout name, weight, price, description;
    private TextView txtAvailability;
    private CheckBox checkBox;
    private int id;
    private Food food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id");
        }
        Button saveBtn = findViewById(R.id.btn_save);
        name = findViewById(R.id.txt_name);
        weight = findViewById(R.id.txt_weight);
        price = findViewById(R.id.txt_price);
        description = findViewById(R.id.txt_description);
        txtAvailability = findViewById(R.id.txt_availability);
        checkBox = findViewById(R.id.checkBox);

        food = new DBHelper(EditProductActivity.this).getFood(id);
        name.getEditText().setText(food.getName());
        weight.getEditText().setText(food.getWeight() + "");
        price.getEditText().setText(food.getPrice() + "");
        description.getEditText().setText(food.getDescription());
        if (food.getAvailability() == 1) {
            checkBox.setChecked(true);
            txtAvailability.setText(getResources().getString(R.string.available));
        } else if (food.getAvailability() == 0) {
            checkBox.setChecked(false);
            txtAvailability.setText(getResources().getString(R.string.not_available));
        }


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs(name, weight, price, description)) {
                    saveProduct(new Food(
                            food.getId(),
                            name.getEditText().getText().toString(),
                            Double.parseDouble(weight.getEditText().getText().toString()),
                            Double.parseDouble(price.getEditText().getText().toString()),
                            description.getEditText().getText().toString(),
                            getAvailability()
                    ));
                }

            }
        });
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (checkBox.isChecked()) {
                            txtAvailability.setText(getResources().getString(R.string.available));
                        } else {
                            txtAvailability.setText(getResources().getString(R.string.not_available));
                        }
                    }
                });

            }
        });

    }

    private int getAvailability() {
        if (checkBox.isChecked()) {
            return 1;
        } else {
            return 0;
        }
    }

    private void saveProduct(Food food) {

        if (new DBHelper(EditProductActivity.this).updateFood(food)) {
//            clearText(name, weight, price, description);
            Button button = Utils.getInstance().showSuccessPopup(EditProductActivity.this, getResources().getString(R.string.success), getResources().getString(R.string.success_msg_update_product));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.getInstance().hidePopup(EditProductActivity.this);
                    finish();
                }
            });


        } else {
            Utils.getInstance().showErrorPopup(EditProductActivity.this, getResources().getString(R.string.error), getResources().getString(R.string.error_msg_add_product));

        }
    }

    private void clearText(TextInputLayout name, TextInputLayout weight, TextInputLayout price, TextInputLayout description) {
        name.getEditText().setText("");
        weight.getEditText().setText("");
        price.getEditText().setText("");
        description.getEditText().setText("");
        name.getEditText().setSelected(true);
    }

    private boolean validateInputs(TextInputLayout name, TextInputLayout weight, TextInputLayout price, TextInputLayout description) {
        boolean isValid = true;
        if (name.getEditText().getText().toString().isEmpty()) {
            isValid = false;

            name.setError(getResources().getString(R.string.empty));
        }

        if (weight.getEditText().getText().toString().isEmpty()) {
            isValid = false;
            weight.setError(getResources().getString(R.string.empty));
        }
        if (price.getEditText().getText().toString().isEmpty()) {
            isValid = false;
            price.setError(getResources().getString(R.string.empty));
        }
        if (description.getEditText().getText().toString().isEmpty()) {
            isValid = false;
            description.setError(getResources().getString(R.string.empty));
        }
        return isValid;
    }
}
