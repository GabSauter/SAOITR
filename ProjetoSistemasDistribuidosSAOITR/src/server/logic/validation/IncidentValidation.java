package server.logic.validation;

import server.entities.Incident;

public class IncidentValidation {
	
	private static final String DATE_REGEX = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
    private static final String HIGHWAY_REGEX = "[A-Za-z]{2}-\\d{3}";
    private static final String KM_REGEX = "\\d{1,3}";
    private static final String INCIDENT_TYPE_REGEX = "\\d+";
    private static final String RANGE_REGEX = "\\d{1,3}-\\d{1,3}";
    
    public boolean validateReportIncident(Incident incident) {
        boolean isValid = true;

        if (!validateDate(incident.getDate())) {
            System.out.println("Erro: Data inválida.");
            isValid = false;
        }
        if (!validateHighway(incident.getHighway())) {
            System.out.println("Erro: Rodovia inválida.");
            isValid = false;
        }
        if (!validateKm(incident.getKm())) {
            System.out.println("Erro: Km inválido.");
            isValid = false;
        }
        if (!validateIncidentType(incident.getIncident_type())) {
            System.out.println("Erro: Tipo de incidente inválido.");
            isValid = false;
        }
        return isValid;
    }

    public boolean validateShowIncidentsList(Incident incident, String lanesRange) {
        boolean isValid = true;
        if (!validateDate(incident.getDate())) {
            System.out.println("Erro: Data inválida.");
            isValid = false;
        }
        if (!validateHighway(incident.getHighway())) {
            System.out.println("Erro: Rodovia inválida.");
            isValid = false;
        }
        if (!validateRange(lanesRange)) {
            System.out.println("Erro: Faixa de pistas inválida.");
            isValid = false;
        }
        return isValid;
    }

    private static boolean validateDate(String date) {
        if (!date.matches(DATE_REGEX)) {
            System.out.println("Erro: Formato de data inválido.");
            return false;
        }
        return true;
    }

    private static boolean validateHighway(String highway) {
        if (!highway.matches(HIGHWAY_REGEX)) {
            System.out.println("Erro: Formato de rodovia inválido.");
            return false;
        }
        return true;
    }

    private static boolean validateKm(int km) {
        String kmString = Integer.toString(km);

        if (!kmString.matches(KM_REGEX)) {
            System.out.println("Erro: Km inválido.");
            return false;
        }
        return true;
    }

    private static boolean validateIncidentType(int incidentType) {
        String incidentTypeString = Integer.toString(incidentType);

        if (!incidentTypeString.matches(INCIDENT_TYPE_REGEX)) {
            System.out.println("Erro: Tipo de incidente inválido.");
            return false;
        }
        return true;
    }

    private static boolean validateRange(String range) {
        if (range == null) {
            return true; // Range can be null
        }
        if (!range.matches(RANGE_REGEX)) {
            System.out.println("Erro: Formato de faixa de pistas inválido.");
            return false;
        }
        return true;
    }
}
