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
 *<p> extract the person related data (rendez-vous et traitment) 
 * @author amine el mahi
 * 
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
    /**
     * extract data from the {@link models.PatientPerson} instance and from the database 
     * and store them in the class fields . more specifically the {@link models.Appointment }
     * and {@link models.Traitement} using the private mthodes {@link #fetchAppointments(int)}
     * and {@link #fetchTraitementEnCours(int)}.
     * @param p {@link PatientPerson} that will be printed using {@link export.Exporter}
     */
    public PrintablePatient(PatientPerson p) {
        //initisalize the connections
        pdao = new PatientDAO();
        tdao = new TraitementDAO();
        ppdao = new PatientPersonDAO();
        adao = new AppointmentDAO();
        //set the fields 
        this.nomComplet = p.getFullName();
        this.age = p.getAge() ;
        this.sexe = p.getSex();
        this.contacts = "";
        this.email = p.getEmail();
        this.telephone = "";
        this.remarquesMedicales =p.getMedicalConditions();
        // fetch data related to the patient 
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
    /**
     * fetch treatments related to {@code the patient } with the id passed as parameter
     * @param : patientId 
     * @return : a list formated as a string with \n as separator 
     */
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
            return "no treatment found";
        }
    }
    //
    /**
     * fetch related 
     * @param patientId
     * @return
     */
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
