package server.logic.validation;

import server.entities.Incident;

public class IncidentValidation {
	
	private static final String DATE_REGEX = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
    private static final String HIGHWAY_REGEX = "[A-Za-z]{2}-\\d{3}";
    private static final String KM_REGEX = "\\d{1,3}";
    private static final String INCIDENT_TYPE_REGEX = "\\d+";
    private static final String RANGE_REGEX = "\\d{1,3}-\\d{1,3}";

    public boolean validateReportIncident(Incident incident) {
        return validateDate(incident.getDate())
                && validateHighway(incident.getHighway())
                && validateKm(incident.getKm())
                && validateIncidentType(incident.getIncident_type());
    }
    
    public boolean validateShowIncidentsList(Incident incident, String lanesRange) {
        return validateDate(incident.getDate())
                && validateHighway(incident.getHighway())
                && validateRange(lanesRange);
    }

    private static boolean validateDate(String date) {
        return date.matches(DATE_REGEX);
    }

    private static boolean validateHighway(String highway) {
        return highway.matches(HIGHWAY_REGEX);
    }

    private static boolean validateKm(int km) {
        String kmString = Integer.toString(km);
        return kmString.matches(KM_REGEX);
    }

    private static boolean validateIncidentType(int incidentType) {
        String incidentTypeString = Integer.toString(incidentType);
        return incidentTypeString.matches(INCIDENT_TYPE_REGEX);
    }
    
    private static boolean validateRange(String range) {
        if (range == null) {
            return true; // Range can be null
        }
        return range.matches(RANGE_REGEX);
    }
}
