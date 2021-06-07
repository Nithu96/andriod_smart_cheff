package com.niwarthana.smartchef.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.niwarthana.smartchef.R;
import com.niwarthana.smartchef.db.DBHelper;
import com.niwarthana.smartchef.model.Food;
import com.niwarthana.smartchef.util.Utils;

import java.util.List;

public class SearchActivity extends AppCompatActivity {
    protected RecyclerView mRecyclerView;
    protected CustomAdapter mAdapter;
    protected LayoutManagerType mCurrentLayoutManagerType;
    private List<Food> mDataSet;
    protected RecyclerView.LayoutManager mLayoutManager;
    private EditText txtSearch;
    private ImageButton btnSearch;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mRecyclerView = findViewById(R.id.recycler_view_connection);
        txtSearch = findViewById(R.id.text_search);
        btnSearch = findViewById(R.id.btn_search);

        mLayoutManager = new LinearLayoutManager(SearchActivity.this);
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(0);
        mDataSet = new DBHelper(SearchActivity.this).getAllFoods();
        mAdapter = new CustomAdapter(mDataSet);
        mRecyclerView.setAdapter(mAdapter);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataSet = new DBHelper(SearchActivity.this).searchProduct(txtSearch.getText().toString());
                mAdapter = new CustomAdapter(mDataSet);
                mRecyclerView.setAdapter(mAdapter);

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
//                this.setIsRecyclable(false);

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
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public CustomAdapter(List<Food> dataSet) {
            mDataSet = dataSet;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_item_food, viewGroup, false);

            return new ViewHolder(v);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

            viewHolder.getTxtName().setText(mDataSet.get(position).getName());
            viewHolder.getTxtWeight().setText(mDataSet.get(position).getWeight() + "g");
            viewHolder.getTxtDescription().setText(mDataSet.get(position).getDescription());
            viewHolder.getTxtPrice().setText(Utils.getInstance().formatPrice(mDataSet.get(position).getPrice()));
            if (mDataSet.get(position).getAvailability() == 1) {
                viewHolder.checkBox.setChecked(true);
            } else {
                viewHolder.checkBox.setChecked(false);
            }
            viewHolder.checkBox.setEnabled(false);
        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }

    }

}
