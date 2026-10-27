package ro.ase.semdam_1085;

import java.io.Serializable;

public class CursValutar implements Serializable {

    private String dataCurs;
    private String cursEur;
    private String cursUsd;
    private String cursGbp;
    private String cursXau;

    public CursValutar(String dataCurs, String cursEur, String cursUsd, String cursGbp, String cursXau) {
        this.dataCurs = dataCurs;
        this.cursEur = cursEur;
        this.cursUsd = cursUsd;
        this.cursGbp = cursGbp;
        this.cursXau = cursXau;
    }

    public String getDataCurs() {
        return dataCurs;
    }

    public void setDataCurs(String dataCurs) {
        this.dataCurs = dataCurs;
    }

    public String getCursEur() {
        return cursEur;
    }

    public void setCursEur(String cursEur) {
        this.cursEur = cursEur;
    }

    public String getCursUsd() {
        return cursUsd;
    }

    public void setCursUsd(String cursUsd) {
        this.cursUsd = cursUsd;
    }

    public String getCursGbp() {
        return cursGbp;
    }

    public void setCursGbp(String cursGbp) {
        this.cursGbp = cursGbp;
    }

    public String getCursXau() {
        return cursXau;
    }

    public void setCursXau(String cursXau) {
        this.cursXau = cursXau;
    }

    @Override
    public String toString() {
        return "CursValutar{" +
                "dataCurs='" + dataCurs + '\'' +
                ", cursEur='" + cursEur + '\'' +
                ", cursUsd='" + cursUsd + '\'' +
                ", cursGbp='" + cursGbp + '\'' +
                ", cursXau='" + cursXau + '\'' +
                '}';
    }
}
