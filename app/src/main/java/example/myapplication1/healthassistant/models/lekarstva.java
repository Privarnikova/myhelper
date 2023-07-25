package example.myapplication1.healthassistant.models;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class lekarstva {
    private String name, datnach,interv,prod,kol,doz,t1,t2,t3;
    private int y;

    public lekarstva( String name, String datnach, String interv, String prod, String kol, String doz,String t1,String t2,String t3, int y ) {
        this.name = name;
        this.datnach = datnach;
        this.interv = interv;
        this.prod = prod;
        this.kol = kol;
        this.doz = doz;
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getT1() {
        return t1;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }

    public String getT2() {
        return t2;
    }

    public void setT2(String t2) {
        this.t2 = t2;
    }

    public String getT3() {
        return t3;
    }

    public void setT3(String t3) {
        this.t3 = t3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatnach() {
        return datnach;
    }

    public void setDatnach(String datnach) {
        this.datnach = datnach;
    }

    public String getInterv() {
        return interv;
    }

    public void setInterv(String interv) {
        this.interv = interv;
    }

    public String getProd() {
        return prod;
    }

    public void setProd(String prod) {
        this.prod = prod;
    }

    public String getKol() {
        return kol;
    }

    public void setKol(String kol) {
        this.kol = kol;
    }

    public String getDoz() {
        return doz;
    }

    public void setDoz(String doz) {
        this.doz = doz;
    }


}
