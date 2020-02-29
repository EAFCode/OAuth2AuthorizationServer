package pro.it.serverauthjwtbega.constants;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum Currency {
    KZ("KZ","Kwanza");

    String description;
    String code;

    Currency( String code,String description) {
        this.description = description;
        this.code = code;
    }
}
