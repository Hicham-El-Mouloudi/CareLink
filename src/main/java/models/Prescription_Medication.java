package models;

public class Prescription_Medication {
    private int id;
    private String dosageInstructions;
    private String duration;
    private int prescriptionId;
    private int medicationId;

    public Prescription_Medication(int id, String dosageInstructions, String duration, int prescriptionId, int medicationId) {
        this.id = id;
        this.dosageInstructions = dosageInstructions;
        this.duration = duration;
        this.prescriptionId = prescriptionId;
        this.medicationId = medicationId;
    }
    public Prescription_Medication(String dosageInstructions, String duration, int prescriptionId, int medicationId) {
        this.dosageInstructions = dosageInstructions;
        this.duration = duration;
        this.prescriptionId = prescriptionId;
        this.medicationId = medicationId;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDosageInstructions() { return dosageInstructions; }
    public void setDosageInstructions(String dosageInstructions) { this.dosageInstructions = dosageInstructions; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public int getPrescriptionId() { return prescriptionId; }
    public void setPrescriptionId(int prescriptionId) { this.prescriptionId = prescriptionId; }
    public int getMedicationId() { return medicationId; }
    public void setMedicationId(int medicationId) { this.medicationId = medicationId; }
}