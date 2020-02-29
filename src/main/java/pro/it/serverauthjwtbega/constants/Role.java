package pro.it.serverauthjwtbega.constants;

import java.util.Arrays;
import java.util.List;

public enum Role {
    ADMIN,
    MANAGER_ESTABLISHMENT,
    CLIENT,
    ROOT,
    SUPERVISOR;

    public static List<Role> getRoleEmployers(){
        return Arrays.asList(ADMIN, ROOT,SUPERVISOR);
    }

    public static List<Role> getRoleClient(){
        return Arrays.asList(CLIENT);
    }

    public static List<Role> getRoleEstablish(){
        return Arrays.asList(MANAGER_ESTABLISHMENT);
    }
}
