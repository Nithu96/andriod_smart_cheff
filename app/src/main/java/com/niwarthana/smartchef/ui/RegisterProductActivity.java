package com.niwarthana.smartchef.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.niwarthana.smartchef.R;
import com.niwarthana.smartchef.db.DBHelper;
import com.niwarthana.smartchef.model.Food;
import com.niwarthana.smartchef.util.Utils;

public class RegisterProductActivity extends AppCompatActivity {
    TextInputLayout name, weight, price, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_product);

        Button saveBtn = findViewById(R.id.btn_save);
        name = findViewById(R.id.txt_name);
        weight = findViewById(R.id.txt_weight);
        price = findViewById(R.id.txt_price);
        description = findViewById(R.id.txt_description);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs(name, weight, price, description)) {
                    saveProduct(new Food(
                            name.getEditText().getText().toString(),
                            Double.parseDouble(weight.getEditText().getText().toString()),
                            Double.parseDouble(price.getEditText().getText().toString()),
                            description.getEditText().getText().toString()
                    ));
                }

            }
        });
    }


    private void saveProduct(Food food) {

        if (new DBHelper(RegisterProductActivity.this).insertFood(food)) {
            clearText(name, weight, price, description);
            Utils.getInstance().showSuccessPopup(RegisterProductActivity.this, getResources().getString(R.string.success), getResources().getString(R.string.success_msg_add_product));
        } else {
            Utils.getInstance().showErrorPopup(RegisterProductActivity.this, getResources().getString(R.string.error), getResources().getString(R.string.error_msg_add_product));

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
