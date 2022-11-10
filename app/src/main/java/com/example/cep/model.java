package com.example.cep;

public class model {


    String chef, ingredient, name, procedure, url;

    public model() {
    }

    public model(String chef, String ingredient, String name, String procedure, String url) {
        this.chef = chef;
        this.ingredient = ingredient;
        this.name = name;
        this.procedure = procedure;
        this.url = url;
    }

    public String getChef() {
        return chef;
    }

    public void setChef(String chef) {
        this.chef = chef;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
