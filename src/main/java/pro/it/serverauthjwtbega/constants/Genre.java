package pro.it.serverauthjwtbega.constants;

public enum Genre {
    M("M"),F("F");

    private String decription;

    Genre(String decription) {
        this.decription = decription;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public static Genre search(String sexo){
        switch (sexo){
            case "M": return Genre.M;
            case "F": return Genre.F;
            default:return null;
        }
    }

}
