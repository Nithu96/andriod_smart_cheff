package com.niwarthana.smartchef.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.niwarthana.smartchef.R;
import com.niwarthana.smartchef.model.Recipe;
import com.niwarthana.smartchef.util.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecipesListActivity extends AppCompatActivity {
    protected RecyclerView mRecyclerView;
    protected CustomAdapter mAdapter;
    protected LayoutManagerType mCurrentLayoutManagerType;
    private List<Recipe> mDataSet;
    protected RecyclerView.LayoutManager mLayoutManager;

    private ProgressDialog pDialog;
    private TextView txtNoData;
    private String recipes;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            recipes = extras.getString("recipes");
        }

        mRecyclerView = findViewById(R.id.recycler_view_connection);
        txtNoData = findViewById(R.id.no_data);
//        Button btnAddToKitchen = findViewById(R.id.btn_save);


        mLayoutManager = new LinearLayoutManager(RecipesListActivity.this);
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(0);
        new loadRecipes().execute(recipes);


    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        /**
         * Provide a reference to the type of views that you are using (custom ViewHolder)
         */
        public class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView txtName;

            public ViewHolder(View v) {
                super(v);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(mDataSet.get(getAdapterPosition()).getSource_url()));
                        startActivity(i);
                    }
                });
                txtName = v.findViewById(R.id.txt_name);

            }

            public TextView getTxtName() {
                return txtName;
            }

        }

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public CustomAdapter(List<Recipe> dataSet) {
            mDataSet = dataSet;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_item_recipes, viewGroup, false);

            return new CustomAdapter.ViewHolder(v);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final CustomAdapter.ViewHolder viewHolder, final int position) {

            viewHolder.getTxtName().setText(mDataSet.get(position).getTitle());

        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }

    }

    private class loadRecipes extends AsyncTask<String, Void, Void> {
        List<Recipe> recipes = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(RecipesListActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(String... arg0) {
            try {

                String urlStr = Constants.RECIPE_URL + "?key=" + Constants.API_KEY + "&q=" + arg0[0] + "&sort=r";
                System.out.println("urlStr : " + urlStr);
                URL url = new URL(urlStr);
                HttpURLConnection con = null;

                con = (HttpURLConnection) url.openConnection();


                // optional default is GET
                con.setRequestMethod("GET");

                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                System.out.println(response.toString());

                JSONObject jsonObj = new JSONObject(response.toString());
                JSONArray contacts = jsonObj.getJSONArray("recipes");
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    Recipe recipe = new Recipe();

                    recipe.setTitle(c.getString("title"));
                    recipe.setImage_url(c.getString("image_url"));
                    recipe.setRecipe_id(c.getString("recipe_id"));
                    recipe.setSource_url(c.getString("source_url"));
                    recipes.add(recipe);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (recipes.size() == 0) {
                txtNoData.setVisibility(View.VISIBLE);
            }else {
                txtNoData.setVisibility(View.GONE);
            }
            mDataSet = recipes;
            mAdapter = new CustomAdapter(mDataSet);
            mRecyclerView.setAdapter(mAdapter);
        }


    }
}