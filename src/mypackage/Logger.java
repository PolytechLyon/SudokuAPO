package src.mypackage;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe Logger permet de gérer les logs dans l'application.
 * Elle permet d'ajouter des messages de log, d'obtenir la liste des logs ou de les exporter vers la console.
 */
public class Logger {

    /** Liste pour stocker les messages de log */
    private List<String> logs;

    /**
     * Constructeur de la classe Logger.
     * Initialise une nouvelle liste vide pour les logs.
     */
    public Logger() {
        this.logs = new ArrayList<>();
    }

    /**
     * Ajoute un message de log à la liste des logs.
     *
     * @param step Le message ou l'étape à ajouter au log.
     */
    public void log(String step) {
        logs.add(step);
    }

    /**
     * Retourne une copie des logs stockés.
     *
     * @return Une nouvelle liste contenant tous les messages de log.
     */
    public List<String> getLogs() {
        return new ArrayList<>(logs);
    }

    /**
     * Affiche tous les messages de log dans la console.
     * Chaque message est imprimé sur une nouvelle ligne.
     */
    public void exportLogs() {
        for (String step : logs) {
            System.out.println(step);
        }
    }
}

