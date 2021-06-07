package com.niwarthana.smartchef.model;

public class Recipe {

    private String title;
    private String image_url;
    private String recipe_id;
    private String source_url;

    public Recipe(String title, String image_url, String recipe_id, String source_url) {
        this.title = title;
        this.image_url = image_url;
        this.recipe_id = recipe_id;
        this.source_url = source_url;
    }

    public Recipe() {
    }


    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(String recipe_id) {
        this.recipe_id = recipe_id;
    }
}
