package ca.ACOII.lockstep.model;

public class Skill {
    private String id, name;
    private int trainingCost, salaryExpense;

    public Skill() {
        id = null;
        name = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTrainingCost() {
        return trainingCost;
    }

    public void setTrainingCost(int trainingCost) {
        this.trainingCost = trainingCost;
    }

    public int getSalaryExpense() {
        return salaryExpense;
    }

    public void setSalaryExpense(int salaryExpense) {
        this.salaryExpense = salaryExpense;
    }
}
