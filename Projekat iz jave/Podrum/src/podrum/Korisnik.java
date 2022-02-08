
package podrum;

import java.io.PrintWriter;
import java.util.ArrayList;


public class Korisnik {
    private String ime, prezime, korisnicko_ime, sifra;
    private boolean admin;

    public Korisnik(String ime, String prezime, String korisnicko_ime, String sifra, boolean admin) {
        this.ime = ime;
        this.prezime = prezime;
        this.korisnicko_ime = korisnicko_ime;
        this.sifra = sifra;
        this.admin = admin;
    }

    public boolean isAdmin() {
        return admin;
    }

    
    

    public String getMail() {
        return korisnicko_ime;
    }

    public String getSifra() {
        return sifra;
    }
    
    public boolean proveraNaloga(String korisnicko_ime, String sifra){
   
        if(korisnicko_ime.equals(this.korisnicko_ime) && sifra.equals(this.sifra))
            return true;
        else 
            return false;
    }

    @Override
    public String toString() {
        return "Korisnik{" + "ime=" + ime + ", prezime=" + prezime + ", admin=" + admin + "}\n";
    }
    
    
    public static boolean proveriIme(ArrayList<Korisnik> a, String ime){
        
        for(Korisnik k : a){
           if(ime.equals(k.getMail()))
               return true;
        }
        
        return false;

    }
    
     public void upis(PrintWriter pw, boolean poslednji) {
       
       
        pw.println(ime);
        pw.println(prezime);
        pw.println(korisnicko_ime);
        pw.println(sifra);
        if(poslednji)
            pw.print(admin);
        else
            pw.println(admin);
    }
    
}
