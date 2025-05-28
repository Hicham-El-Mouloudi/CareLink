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
    }

    public List<PatientPerson> getAllPatientPersons() throws Exception {
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
    }

    public boolean deletePatientPerson(PatientPerson patientPerson) throws Exception {
        // Delete from Patient first (foreign key constraint)
        boolean patientDeleted = patientDAO.deletePatient(patientPerson.getId());
        // Then delete from Person
        boolean personDeleted = personDAO.deletePerson(patientPerson.getId());
        return patientDeleted && personDeleted;
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
