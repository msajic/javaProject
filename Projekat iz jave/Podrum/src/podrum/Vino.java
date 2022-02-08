
package podrum;

import java.io.PrintWriter;
import java.util.ArrayList;


public class Vino extends Roba implements Upis_u_datoteku{
    
    private String tip, ukus;

    public Vino(String tip, String ukus, String naziv, double cena, double litraza, double promil, int stanje, int kod) {
        super(naziv, cena, litraza, promil, stanje, kod);
        this.tip = tip;
        this.ukus = ukus;
    }

    @Override
    public String toString() {
        return "Tip= " + tip + ", ukus= " + ukus + super.toString();
    }


    

    public String getTip(){
        return this.tip;
    }

    
    @Override
    public void upis(PrintWriter pw, boolean poslednji) {
       
        pw.println(tip);
        pw.println(ukus);
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
    
      
   public static boolean proveriKod(ArrayList<Vino> ip, int kod){
   
       for(Vino p : ip){
           if(kod == p.getKod())
               return true;       
       }
       
   return false;
   }
}
