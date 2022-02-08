
package podrum;




public abstract class Roba {
    protected String naziv;
    protected double cena, litraza, promil;
    protected int stanje, kod;

    public Roba(String naziv, double cena, double litraza, double promil, int stanje, int kod) {
        this.naziv = naziv;
        this.cena = cena;
        this.litraza = litraza;
        this.promil = promil;
        this.stanje = stanje;
        this.kod = kod;
    }

    public int getKod() {
        return kod;
    }

    public int getStanje() {
        return stanje;
    }

    public void setStanje(int stanje) {
        this.stanje = stanje;
    }
    
    

    @Override
    public String toString() {
        return  ", naziv= " + naziv + ", cena= " + cena + ", litraza= " + litraza + ", promil= " + promil + ", stanje= " + stanje + ", kod= " + kod  ;
    }

    
    public void promenaStanja(int i){
        if(i > stanje)
            System.out.println("Trenutno imamo " + stanje +" komada na stanju.");
        else
            stanje -= i;
    }
    
    public boolean poredjenje(Roba r){
        if(this.kod == r.getKod())
            return true;
        else 
            return false;
    }
    
    public boolean poredjenje(int i){
        if(this.kod == i)
            return true;
        else 
            return false;
    }
    
    public void ispisNarudzbine(int i){
        System.out.println("Naziv: " + naziv + " " + litraza + "l Cena/kom:" + cena + "rsd Kom: " + i + " Cena: " + (i*cena) + "rsd");
    }

    public double getCena() {
        return cena;
    }
    
    
}
