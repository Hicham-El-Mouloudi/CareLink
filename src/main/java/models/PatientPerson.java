package models;

/**
 * Combines Patient and Person data for TableView display.
 */
public class PatientPerson {
    private int id;
    private String email;
    private String fullName;
    private int age;
    private String sex;
    private String medicalConditions;

    public PatientPerson() {
        id = 0; email = ""; fullName = ""; age = 0; sex = "Male"; medicalConditions = "";
    }
   

    public PatientPerson(int id, String email, String fullName, int age, String sex, String medicalConditions) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.age = age;
        this.sex = sex;
        this.medicalConditions = medicalConditions;
    }

    public int getId() { return id; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
    public int getAge() { return age; }
    public String getSex() { return sex; }
    public String getMedicalConditions() { return medicalConditions; }

    public void setId(int id) { this.id = id; }
    public void setCni(String email) { this.email = email; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setAge(int age) { this.age = age; }
    public void setSex(String sex) { this.sex = sex; }
    public void setMedicalConditions(String medicalConditions) { this.medicalConditions = medicalConditions; }
}
