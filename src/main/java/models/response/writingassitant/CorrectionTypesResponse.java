package models.response.writingassitant;

import java.util.List;

public class CorrectionTypesResponse {
    private List<CorrectionType> correctionTypes;

    public List<CorrectionType> getCorrectionTypes() {
        return correctionTypes;
    }

    public static class CorrectionType {
        private String title;
        private String description;
        private String message;
        private int id;
        private int category;

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getMessage() {
            return message;
        }

        public int getId() {
            return id;
        }

        public int getCategory() {
            return category;
        }
    }
}
