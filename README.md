# Application de Suivi des Traitements Médicaux (CareLink)

CareLinko est une application JavaFX moderne et professionnelle permettant la gestion complète des patients, rendez-vous, traitements et prescriptions médicales. Elle est conçue pour offrir une expérience utilisateur fluide, intuitive et sécurisée pour les professionnels de santé.

## Fonctionnalités principales

- **Gestion des patients** :
  - Ajout, modification, suppression de patients
  - Recherche avancée par nom, email, âge, sexe, ou traitements
  - Visualisation et exportation des dossiers patients en PDF

- **Gestion des rendez-vous** :
  - Calendrier interactif pour la planification des rendez-vous
  - Ajout, modification, suppression de rendez-vous
  - Filtrage par date, patient, statut

- **Gestion des traitements** :
  - Création et édition de traitements médicaux
  - Suivi de l’évolution, statut, et historique des traitements
  - Association des traitements aux patients et rendez-vous

- **Gestion des prescriptions** :
  - Ajout de prescriptions liées à un traitement
  - Saisie du nom, dosage, durée, instructions et notes

- **Authentification & Sécurité** :
  - Connexion sécurisée par identifiant et mot de passe
  - Gestion des comptes médecins (inscription, connexion)

- **Interface utilisateur moderne** :
  - Design minimaliste et professionnel (palette bleue #0077b6/#1b7895)
  - Formulaires responsives, cartes, effets d’ombre, boutons stylisés
  - Navigation fluide entre les vues (patients, rendez-vous, traitements, aide)

## Technologies utilisées
- Java 21
- JavaFX 21+
- Maven
- MySQL (JDBC)
- FXML & CSS personnalisés

## Structure du projet
```
src/
  main/
    java/
      controlers/         # Contrôleurs JavaFX (MVC)
      models/             # Modèles de données (Patient, Doctor, etc.)
      ...
    resources/
      views/              # Fichiers FXML & CSS
      ...
  test/
    java/                # Tests unitaires
```

## Lancement du projet
1. **Prérequis** :
   - Java 21+
   - Maven
   - MySQL (base de données configurée)
2. **Configuration** :
   - Renseigner les identifiants de connexion à la base dans `DBCredentials.java`
3. **Compilation & exécution** :
   ```sh
   mvn clean javafx:run
   ```

## Licence
Ce projet est fourni à des fins pédagogiques.