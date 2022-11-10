package com.example.cep;

public class AddRecipeDataHolder {

    String chef, name, ingredient, procedure, url;

    public AddRecipeDataHolder() {
    }

    public AddRecipeDataHolder(String chef, String name, String ingredient, String procedure, String url) {
        this.chef = chef;
        this.name = name;
        this.ingredient = ingredient;
        this.procedure = procedure;
        this.url = url;
    }

    public String getChef() {
        return chef;
    }

    public void setChef(String chef) {
        this.chef = chef;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
