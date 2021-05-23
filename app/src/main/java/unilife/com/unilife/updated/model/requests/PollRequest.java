package unilife.com.unilife.updated.model.requests;

public class PollRequest {
    String question;
    String group_id;
    Options options;

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public static class Options {
        String option1;
        String option2;
        String option3;
        String option4;
        String option5;
        String option6;
        String option7;
        String option8;
        String option9;
        String option10;

        public void setOption5(String option5) {
            this.option5 = option5;
        }

        public void setOption6(String option6) {
            this.option6 = option6;
        }

        public void setOption7(String option7) {
            this.option7 = option7;
        }

        public void setOption8(String option8) {
            this.option8 = option8;
        }

        public void setOption9(String option9) {
            this.option9 = option9;
        }

        public void setOption10(String option10) {
            this.option10 = option10;
        }

        public void setOption1(String option1) {
            this.option1 = option1;
        }

        public void setOption2(String option2) {
            this.option2 = option2;
        }

        public void setOption3(String option3) {
            this.option3 = option3;
        }

        public void setOption4(String option4) {
            this.option4 = option4;
        }
    }

}
