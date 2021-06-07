package com.niwarthana.smartchef.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.niwarthana.smartchef.R;
import com.niwarthana.smartchef.db.DBHelper;
import com.niwarthana.smartchef.model.Food;
import com.niwarthana.smartchef.util.Utils;

import java.util.List;

public class RecipesActivity extends AppCompatActivity {
    protected RecyclerView mRecyclerView;
    protected CustomAdapter mAdapter;
    protected LayoutManagerType mCurrentLayoutManagerType;
    private List<Food> mDataSet;
    protected RecyclerView.LayoutManager mLayoutManager;
    private ProgressDialog pDialog;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        mRecyclerView = findViewById(R.id.recycler_view_connection);
        Button findRecipe = findViewById(R.id.btn_save);


        mLayoutManager = new LinearLayoutManager(RecipesActivity.this);
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(0);
        mDataSet = new DBHelper(RecipesActivity.this).getAvailableFoods();


        for (Food f : mDataSet) {
            f.setAvailability(0);
        }
        mAdapter = new RecipesActivity.CustomAdapter(mDataSet);
        mRecyclerView.setAdapter(mAdapter);


        findRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String recipes = "";
                for (Food food : mDataSet) {
                    if (food.getAvailability() == 1) {
                        recipes = recipes + food.getName() + ",";
                    }
                }
                if(recipes.endsWith(",")) {

                    recipes= recipes.substring(0, recipes.length() - 1);
                }
                Intent i = new Intent(RecipesActivity.this, RecipesListActivity.class);
                i.putExtra("recipes", recipes);
                startActivity(i);
            }
        });
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        /**
         * Provide a reference to the type of views that you are using (custom ViewHolder)
         */
        public class ViewHolder extends RecyclerView.ViewHolder {

            private final CheckBox checkBox;
            private final TextView txtName, txtWeight, txtDescription, txtPrice;

            public ViewHolder(View v) {
                super(v);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                txtName = v.findViewById(R.id.txt_name);
                txtWeight = v.findViewById(R.id.txt_weight);
                txtDescription = v.findViewById(R.id.txt_description);
                txtPrice = v.findViewById(R.id.txt_price);
                checkBox = v.findViewById(R.id.checkBox);

            }

            public CheckBox getCheckBox() {
                return checkBox;
            }

            public TextView getTxtName() {
                return txtName;
            }

            public TextView getTxtWeight() {
                return txtWeight;
            }

            public TextView getTxtDescription() {
                return txtDescription;
            }

            public TextView getTxtPrice() {
                return txtPrice;
            }
        }

        /**
         * Initialize the data set of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public CustomAdapter(List<Food> dataSet) {
            mDataSet = dataSet;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_item_food, viewGroup, false);

            return new CustomAdapter.ViewHolder(v);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final CustomAdapter.ViewHolder viewHolder, final int position) {

            viewHolder.getTxtName().setText(mDataSet.get(position).getName());
            viewHolder.getTxtWeight().setText(mDataSet.get(position).getWeight() + "g");
            viewHolder.getTxtDescription().setText(mDataSet.get(position).getDescription());
            viewHolder.getTxtPrice().setText(Utils.getInstance().formatPrice(mDataSet.get(position).getPrice()));

            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Food currentItem = mDataSet.get(position);
                    if (viewHolder.getCheckBox().isChecked()) {
                        currentItem.setAvailability(1);
                    } else {
                        currentItem.setAvailability(0);
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }

    }
}