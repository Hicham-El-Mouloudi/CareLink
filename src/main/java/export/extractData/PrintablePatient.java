package export.extractData;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Appointment;
import models.AppointmentDAO;
import models.PatientDAO;
import models.PatientPerson;
import models.PatientPersonDAO;
import models.Traitement;
/**
 * handle the communication between the model and the printer 
 */
import models.TraitementDAO;
public class PrintablePatient {
  
    PatientDAO pdao;
    PatientPersonDAO ppdao;
    TraitementDAO tdao;
    AppointmentDAO adao;

    private String nomComplet;
    private int age;
    private String sexe;
    private String contacts;
    private String email;
    private String telephone;
    private String remarquesMedicales;
    private String traitementsEnCours;
    private List<Appointment> rendezVousList;

    // Constructor
    public PrintablePatient(PatientPerson p) {
        pdao = new PatientDAO();
        tdao = new TraitementDAO();
        ppdao = new PatientPersonDAO();
        adao = new AppointmentDAO();
        this.nomComplet = p.getFullName();
        this.age = p.getAge() ;
        this.sexe = p.getSex();
        this.contacts = "";
        this.email = p.getEmail();
        this.telephone = "";
        this.remarquesMedicales =p.getMedicalConditions();
        pdao = new PatientDAO();
        ppdao = new PatientPersonDAO();
        this.traitementsEnCours = fetchTraitementEnCours(p.getId());
        this.rendezVousList = fetchAppointments(p.getId());
    }

    // Getters
    public String getNomComplet() { return nomComplet; }
    public int getAge() { return age; }
    public String getSexe() { return sexe; }
    public String getContacts() { return contacts; }
    public String getEmail() { return email; }
    public String getTelephone() { return telephone; }
    public String getRemarquesMedicales() { return remarquesMedicales; }
    public String getTraitementsEnCours() { return traitementsEnCours; }
    public List<Appointment> getRendezVousList() { return rendezVousList; }
    //
    private String fetchTraitementEnCours(int patientId){
        String result= new String();
        var found = false;
        try{ 
        for (Traitement t : tdao.getAllTraitements()) {
                if (t.getPatientId() == patientId) {
                    found = true;
                    String traitLine="Description : " +t.getDescription() +"\t Notes :"+ t.getNotesObservation()+"\n";
                    result.concat(traitLine);
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        if(found)
        return result;
        else {
            return "aucun traitment est trouve";
        }
    }
    //
    private List<Appointment> fetchAppointments(int patientId){
        List<Appointment> result = new ArrayList<>();
        try
        {for( Appointment apt : adao.getAllAppointments()){
            if(apt.getPatientId()== patientId){
                result.add(apt);
            }
        }}
        catch(SQLException e){
            e.printStackTrace();
        }
        return result;

    }

}
