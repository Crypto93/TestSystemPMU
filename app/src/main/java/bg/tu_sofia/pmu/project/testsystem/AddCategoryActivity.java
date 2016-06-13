package bg.tu_sofia.pmu.project.testsystem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bg.tu_sofia.pmu.project.testsystem.persistence.CategoriesDataSource;

public class AddCategoryActivity extends Activity {

    EditText addCatEditText;
    Button addCatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        init();
    }

    private void init() {
        addCatButton = (Button) findViewById(R.id.addCatButton);
        addCatEditText = (EditText) findViewById(R.id.addCatEditText);

        final CategoriesDataSource cds = new CategoriesDataSource(this);

        addCatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    if (cds.insertCategory(addCatEditText.getText().toString())) {
                        Toast.makeText(AddCategoryActivity.this, getResources().getString(R.string.add_category_success), Toast.LENGTH_SHORT).show();
                        AddCategoryActivity.this.finish();
                    } else {
                        Toast.makeText(AddCategoryActivity.this, getResources().getString(R.string.add_category_failure), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddCategoryActivity.this, getResources().getString(R.string.empty_fields), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateFields() {
        if (addCatEditText.getText().toString().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
