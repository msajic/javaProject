
package podrum;

import java.io.PrintWriter;
import java.util.ArrayList;


public class Zestina extends Roba implements Upis_u_datoteku{
    
    private String tip;

    public Zestina(String tip, String naziv, double cena, double litraza, double promil, int stanje, int kod) {
        super(naziv, cena, litraza, promil, stanje, kod);
        this.tip = tip;
    }

 

    public String getTip() {
        return tip;
    }

    @Override
    public String toString() {
        return "Tip= " + tip + super.toString();
    }
    
    @Override
    public void upis(PrintWriter pw, boolean poslednji) {
       
        pw.println(tip);
        pw.println(super.naziv);
        pw.println(super.cena);
        pw.println(super.litraza);
        pw.println(super.promil);
        pw.println(super.stanje);
        if(poslednji)
            pw.print(super.kod);
        else
            pw.println(super.kod);
    }
    
    
      
   public static boolean proveriKod(ArrayList<Zestina> ip, int kod){
   
       for(Zestina p : ip){
           if(kod == p.getKod())
               return true;       
       }
       
   return false;
   }
    
}
