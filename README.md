# API Dog Spring

## Démarrage

Pour commencer avec cette application, suivez les étapes ci-dessous :

### Cloner le Repository

Tout d'abord, clonez le repository depuis GitHub :

```bash
git clone https://github.com/C4rpz/DogAPI_Spring.git 
cd DogAPI_Spring 
```

### Configuration

Avant d'exécuter l'application, configurez la connexion à la base de données. 
Créez un fichier `application.properties` dans le répertoire `src/main/resources`, copiez-collez le contenu de `application.properties.example` et remplacez les valeurs par vos informations :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/VOTRE_BASE_DE_DONNEES
spring.datasource.username=VOTRE_USERNAME
spring.datasource.password=VOTRE_MOT_DE_PASSE
```

### Compilation et exécution de l'application

Utilisez Maven pour compiler et exécuter l'application :

Exécutez `DogApiSpringApplication.java` dans `src/main/java/com/example/DogAPI_Spring`
```bash
mvn clean install
mvn spring-boot:run
```

### Accéder à l'application

Une fois l'application en cours d'exécution, accédez-y via l'URL suivante : `http://localhost:8085`.

## Résolution des problèmes

Si vous rencontrez des problèmes, vérifiez les points suivants :
- Assurez-vous que votre base de données est bien démarrée et accessible.
- Vérifiez que votre fichier `application.properties` contient les bons identifiants de connexion à la base de données.
- Consultez les logs de l'application pour plus d'informations sur les erreurs éventuelles.
