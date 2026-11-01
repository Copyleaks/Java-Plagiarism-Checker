package models.response.writingassitant;

import java.util.List;

public class Corrections {
    private Text text;

    public Text getText() {
        return text;
    }

    public static class Text {
        private Chars chars;

        public Chars getChars() {
            return chars;
        }

        public static class Chars {
            private List<Integer> types;
            private List<Integer> starts;
            private List<Integer> lengths;
            private List<String> operationTexts;

            public List<Integer> getTypes() {
                return types;
            }

            public List<Integer> getStarts() {
                return starts;
            }

            public List<Integer> getLengths() {
                return lengths;
            }

            public List<String> getOperationTexts() {
                return operationTexts;
            }
        }
    }
}
