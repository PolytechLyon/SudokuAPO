package src.mypackage;
import java.util.ArrayList;
import java.util.List;

public class Logger {
    // Liste pour stocker les messages de log
    private List<String> logs;

    // Constructeur qui initialise la liste de logs
    public Logger() {
        this.logs = new ArrayList<>();
    }

    // Méthode pour ajouter une étape ou un message au log
    public void log(String step) {
        logs.add(step);
    }

    // Méthode pour retourner une copie de la liste des logs
    public List<String> getLogs() {
        return new ArrayList<>(logs);
    }

    // Méthode pour afficher (exporter) les logs sur la console
    public void exportLogs() {
        for (String step : logs) {
            System.out.println(step);
        }
    }

}
