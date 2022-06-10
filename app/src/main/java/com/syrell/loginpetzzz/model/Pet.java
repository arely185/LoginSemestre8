package com.syrell.loginpetzzz.model;

public class Pet {
    String name, race, specie;
    public Pet(){}

    public Pet(String name, String race, String specie){
        this.name = name;
        this.race= race;
        this.specie = specie;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getRace(){
        return race;
    }
    public void setRace(String race){
        this.race = race;
    }
    public String getSpecie(){
        return specie;
    }
    public void setSpecie(String specie){
        this.specie = specie;
    }
}
