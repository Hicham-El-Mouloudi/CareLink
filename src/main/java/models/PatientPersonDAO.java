package models;

import java.util.*;

/**
 * Provides combined Patient and Person data for TableView.
 */
public class PatientPersonDAO {
    private PatientDAO patientDAO;
    private PersonDAO personDAO;

    public PatientPersonDAO() {
        this.patientDAO = new PatientDAO();
        this.personDAO = new PersonDAO();
    }    public List<PatientPerson> getAllPatientPersons() {
        try {
            List<Patient> patients = patientDAO.getAllPatients();
            List<PatientPerson> result = new ArrayList<>();
            for (Patient patient : patients) {
                Person person = personDAO.getPersonById(patient.getPersonId());
                if (person != null) {
                    result.add(new PatientPerson(
                        patient.getId(),
                        person.getEmail(), // Assuming CNI is stored in email for now, adjust as needed
                        person.getFullName(),
                        calculateAge(person.getDateOfBirth()),
                        person.getGender(),
                        patient.getMedicalConditions()
                    ));
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return empty list in case of error
        }
    }

    private int calculateAge(java.util.Date dateOfBirth) {
        if (dateOfBirth == null) return 0;
        Calendar dob = Calendar.getInstance();
        dob.setTime(dateOfBirth);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }    public boolean deletePatientPerson(PatientPerson patientPerson) {
        try {
            // Delete from Patient first (foreign key constraint)
            boolean patientDeleted = patientDAO.deletePatient(patientPerson.getId());
            // Then delete from Person
            boolean personDeleted = personDAO.deletePerson(patientPerson.getId());
            return patientDeleted && personDeleted;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public PatientPerson getPatientPersonById(int id) {
        try {
            Patient patient = patientDAO.getPatientById(id);
            if (patient != null) {
                Person person = personDAO.getPersonById(patient.getPersonId());
                if (person != null) {
                    return new PatientPerson(
                        patient.getId(),
                        person.getEmail(),
                        person.getFullName(),
                        calculateAge(person.getDateOfBirth()),
                        person.getGender(),
                        patient.getMedicalConditions()
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("PatientPersonDAO : Error while calling 'getPatientPersonById'");
        }
        return null;
    }

    /**
     * Deletes all patients and their associated persons from the database.
     * @return true if successful, false otherwise
     */
    public boolean deleteAllPatientPersons() {
        try {
            // Delete all patients first (to avoid FK constraint issues)
            boolean patientsDeleted = patientDAO.deleteAllPatients();
            // Then delete all persons
            boolean personsDeleted = personDAO.deleteAllPersons();
            return patientsDeleted && personsDeleted;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
