package com.example.mymiwokapp;

public class ReportCard {
    String name;
    String englishgrade;
    String mathgrade;
    String physicsgrade;
    String chemistrygrade;
    String biologygrade;

    ReportCard()
    {
         name="John Doe";
         englishgrade="A";
        mathgrade="A-";
         physicsgrade="A+";
         chemistrygrade="A+";
         biologygrade="B-";
    }
    public String getEnglishgrade() {
        return englishgrade;
    }
    public void setEnglishgrade(){
        this.englishgrade = englishgrade;
    }

    public String getMathgrade() {
        return mathgrade;
    }

    public void setMathgrade(String mathgrade) {
        this.mathgrade = mathgrade;
    }

    public String getPhysicsgrade() {
        return physicsgrade;
    }

    public void setPhysicsgrade(String physicsgrade) {
        this.physicsgrade = physicsgrade;
    }

    public String getChemistrygrade() {
        return chemistrygrade;
    }

    public String getBiologygrade() {
        return biologygrade;
    }

    public void setChemistrygrade(String chemistrygrade) {
        this.chemistrygrade = chemistrygrade;
    }

    public void setBiologygrade(String biologygrade) {
        this.biologygrade = biologygrade;
    }

    @Override
    public String toString()
    {
        String s="Name: "+name+" EnglishGrade: "+getEnglishgrade()+" PhysicsGrade: "+getPhysicsgrade()+" MathGrade: "+getMathgrade()+" ChemistryGrade: "+getChemistrygrade()+" BiologyGrade: "+getBiologygrade();
        return (s);
    }
    public static void main(String [] args)
    {
        ReportCard r= new ReportCard();
        System.out.println(r.toString());
    }
}
