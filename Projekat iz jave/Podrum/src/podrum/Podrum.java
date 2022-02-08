
package podrum;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Podrum {

    
    public static void main(String[] args) {
        
       
        boolean radi = true, validno = false;
        
        
        ArrayList<Pivo> inventarPiva = new ArrayList<>();
        ArrayList<Vino> inventarVina = new ArrayList<>();
        ArrayList<Zestina> inventarZestine = new ArrayList<>();
        ArrayList<Korisnik> korisnici = new ArrayList<>();
        
        
        korisnici.add(new Korisnik("Admin", " ", "admin", "admin", true));
        
        ucitaj_korisnike(korisnici);
        ucitaj_vina(inventarVina);
        ucitaj_piva(inventarPiva);
        ucitaj_zestinu(inventarZestine);  
        
        
        
        pocetni_ekran(inventarPiva, inventarVina, inventarZestine, korisnici);
            
        
    }
    
    
    static void pocetni_ekran(ArrayList<Pivo> inventarPiva, ArrayList<Vino> inventarVina, ArrayList<Zestina> inventarZestine, ArrayList<Korisnik> korisnici){
        Scanner unos = new Scanner(System.in);
        int opcija;
        System.out.println("\n\n\nPrijava na nalog!\n");
        System.out.println("1.Ulogujte se\n");
        System.out.println("2.Napravite novi nalog\n");
        System.out.println("3.Izadjite iz programa\n");
        System.out.print("Unesite neku od opcija: ");
        
        opcija = unos.nextInt();
                
        switch(opcija){
        
            case 1: 
                uloguj_se(inventarPiva, inventarVina, inventarZestine, korisnici);
                break;
                
            case 2:
                napraviNalog(inventarPiva, inventarVina, inventarZestine,korisnici);
                break;
                
            case 3:
                System.out.println("Dovidjenja");
                System.exit(0);
                break;
                
            default:  
                 pocetni_ekran(inventarPiva, inventarVina, inventarZestine, korisnici);
        
        
        
        
        }
    }
    
    static void uloguj_se(ArrayList<Pivo> inventarPiva, ArrayList<Vino> inventarVina, ArrayList<Zestina> inventarZestine, ArrayList<Korisnik> korisnici){
        
        Scanner s = new Scanner(System.in);
        boolean log = false, admin = false;
        String mail, sifra;
        System.out.println("\nUloguj se:\n");
        System.out.println("Unesite korisnicko ime: ");
        mail = s.nextLine();
        System.out.println("\nUnesite sifru: ");
        sifra = s.nextLine();
        for(Korisnik k : korisnici)
        {
            if(k.proveraNaloga(mail, sifra))
            {
                admin = k.isAdmin();
                log = true;
                break;
            }
                              
        }
        if(log)
        {
            System.out.println("\nUspesno ste se ulogovali.");
            if(!admin)
                cenovnik(inventarPiva, inventarVina, inventarZestine, korisnici);
            else 
                admin(inventarPiva, inventarVina, inventarZestine, korisnici);
                
        }
        else{
            System.out.println("\nUneli ste pogresne podatke");
            uloguj_se(inventarPiva, inventarVina, inventarZestine, korisnici);
        }
    }

    static void cenovnik(ArrayList<Pivo> inventarPiva, ArrayList<Vino> inventarVina, ArrayList<Zestina> inventarZestine, ArrayList<Korisnik> korisnici){
        
        Scanner s = new Scanner(System.in);
        ArrayList<Integer> komada = new ArrayList<>();
        ArrayList<Roba> racun = new ArrayList<>();
        
        System.out.println("Cenovnik:");
        
        System.out.println("\n_________PIVA_________");
        for(Pivo p : inventarPiva)
            System.out.println(p);
        System.out.println("\n_________VINA_________");
        for (Vino v : inventarVina) 
            System.out.println(v);
        System.out.println("\n________ZESTINA_______");
        for(Zestina z : inventarZestine)
            System.out.println(z);
        System.out.println(" ");
        narucivanje(inventarPiva, inventarVina, inventarZestine, korisnici, komada, racun);
        
        double ukupna_cena = 0;
        for (int i = 0; i < racun.size(); i++) {
            racun.get(i).ispisNarudzbine(komada.get(i));
            ukupna_cena += komada.get(i) * racun.get(i).getCena();
        }
        System.out.println("Ukupna cena: " + ukupna_cena + "rsd");
        
        System.out.println("Ukoliko zelite da potvrdite porudzbinu pritisnite '1', u suprotnom porudzbina se prekida");
        int i = s.nextInt();
        if(i == 1){
            System.out.println("Uspesno ste izvrsili porudzbinu");            
            updateInventar(inventarPiva, inventarVina, inventarZestine, komada, racun);
            upisPiva(inventarPiva);
            upisVina(inventarVina);
            upisZestine(inventarZestine);
        }
        else{
            cenovnik(inventarPiva, inventarVina, inventarZestine, korisnici);
        }
            
        System.out.println("Ukoliko zelite da napravite novu porudzbinu pritisnite '1', a ukoliko ste zavrsili pritisnite '2'.");
        
        do{
            i = s.nextInt();
            if(i == 1)
                cenovnik(inventarPiva, inventarVina, inventarZestine, korisnici);
            else if(i == 2)
                pocetni_ekran(inventarPiva, inventarVina, inventarZestine, korisnici);
            else{
                System.out.println("Uneta opcija nije validna. Molimo Vas pokusajte ponovo");
                System.out.println("Ukoliko zelite da napravite novu porudzbinu pritisnite '1', a ukoliko ste zavrsili pritisnite '2'.");
        
            }
        }while(i != 1 && i != 2);
        
        
    }

    static void ucitaj_korisnike(ArrayList<Korisnik> korisnici){
        try {
            FileInputStream fis = new FileInputStream("korisnici.txt");
            
            Scanner s = new Scanner(fis);
            while(s.hasNext())
            {
                boolean admin = false;
                String ime = s.nextLine();
                String prezime = s.nextLine();
                String k_i = s.nextLine();
                String sifra = s.nextLine();
                String ad = s.nextLine();
                if(ad.equals("true"))
                    admin = true;
                korisnici.add(new Korisnik(ime,prezime,k_i,sifra,admin));
            }
            s.close();
            fis.close();
            
            
        } catch (FileNotFoundException ex) {
            System.out.println("Datoteka nije nadjena");
        } catch (IOException ex) {
            Logger.getLogger(Podrum.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    static void ucitaj_vina(ArrayList<Vino> vina){
        try {
            FileInputStream fis = new FileInputStream("vino.txt");
            
            Scanner s = new Scanner(fis);
            while(s.hasNext())
            {
                String tip = s.nextLine();
                String ukus = s.nextLine();
                String ime = s.nextLine();
                double cena = s.nextDouble();
                s.nextLine();
                double litraza = s.nextDouble();
                s.nextLine();
                double promil = s.nextDouble();
                s.nextLine();
                int stanje = s.nextInt();
                s.nextLine();
                int kod = s.nextInt();
                if(s.hasNext())
                    s.nextLine();
                
                vina.add(new Vino(tip,ukus,ime,cena,litraza,promil,stanje,kod));
            }
            s.close();
            fis.close();
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Podrum.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Podrum.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    static void ucitaj_piva(ArrayList<Pivo> piva){
        try {
            FileInputStream fis = new FileInputStream("pivo.txt");
            
            Scanner s = new Scanner(fis);
            while(s.hasNext())
            {
                String tip = s.nextLine();
                String ime = s.nextLine();
                double cena = s.nextDouble();
                s.nextLine();
                double litraza = s.nextDouble();
                s.nextLine();
                double promil = s.nextDouble();
                s.nextLine();
                int stanje = s.nextInt();
                s.nextLine();
                int kod = s.nextInt();
                if(s.hasNext())
                    s.nextLine();
                
                piva.add(new Pivo(tip,ime,cena,litraza,promil,stanje,kod));
            }
            s.close();
            fis.close();
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Podrum.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Podrum.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    static void ucitaj_zestinu(ArrayList<Zestina> zestina){
        try {
            FileInputStream fis = new FileInputStream("zestina.txt");
            
            Scanner s = new Scanner(fis);
            while(s.hasNext())
            {
                String tip = s.nextLine();
                String ime = s.nextLine();
                double cena = s.nextDouble();
                s.nextLine();
                double litraza = s.nextDouble();
                s.nextLine();
                double promil = s.nextDouble();
                s.nextLine();
                int stanje = s.nextInt();
                s.nextLine();
                int kod = s.nextInt();
                if(s.hasNext())
                    s.nextLine();
                
                zestina.add(new Zestina(tip,ime,cena,litraza,promil,stanje,kod));
            }
            s.close();
            fis.close();
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Podrum.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Podrum.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    static void narucivanje(ArrayList<Pivo> inventarPiva, ArrayList<Vino> inventarVina, ArrayList<Zestina> inventarZestine, ArrayList<Korisnik> korisnici, ArrayList<Integer> kom, ArrayList<Roba> r){
        
        Scanner s = new Scanner(System.in);
        
        boolean postojiKod, krajPorudzbine = false;
        int broj;
        
        while(!krajPorudzbine){

            postojiKod = false;
            System.out.println("Unesite kod artika koji zelite da porucite, ili unesite '0' da izvrsite porudzbinu: ");
            int kod = s.nextInt();

            if(kod == 0)
                break;

           for(Vino v : inventarVina)
            {
                if(v.poredjenje(kod)){
                    r.add(v);
                    postojiKod = true;
                }
            }
            for(Pivo p : inventarPiva)
            {
                if(p.poredjenje(kod)){
                    r.add(p);
                    postojiKod = true;
                }
            }
            for(Zestina z : inventarZestine)
            {
                if(z.poredjenje(kod)){
                    r.add(z);
                    postojiKod = true;
                }
            }
            
            boolean vanOpsega;
            
            if(!postojiKod)
                System.out.println("Ovaj kod nepostoji, unesite validan kod");
            else{
                do{
                    
                    vanOpsega = true;
                    System.out.println("Unesite kolicinu koju zelite da porucite: ");
                    broj = s.nextInt();
                    
                    for(Vino v : inventarVina){
                        if(v.getKod() == kod){
                            if(v.getStanje() < broj){
                                System.out.println("Nemamo dovoljno robe na stanju. Unesite validan broj.");

                            }
                            else{
                                vanOpsega = false;
                            }

                        }
                    }
                    for(Pivo p : inventarPiva){
                        if(p.getKod() == kod){
                           if(p.getStanje() < broj){
                               System.out.println("Nemamo dovoljno robe na stanju. Unesite validan broj.");
                               
                           }
                           else{
                               vanOpsega = false;
                           }
                           
                       }
                    }
                    for(Zestina z : inventarZestine){
                        if(z.getKod() == kod){
                            if(z.getStanje() < broj){
                                System.out.println("Nemamo dovoljno robe na stanju. Unesite validan broj.");

                            }
                            else{
                                vanOpsega = false;
                            }
                        }
                    }

                    if(broj <= 0){
                        System.out.println("Molimo Vas unesite pozitivan broj.\n");
                    }
                    
                }while(broj <= 0 || vanOpsega);
                kom.add(broj);
            }
            
            
            
        }
        
        
       
    }

    static void updateInventar(ArrayList<Pivo> inventarPiva, ArrayList<Vino> inventarVina, ArrayList<Zestina> inventarZestine, ArrayList<Integer> kom, ArrayList<Roba> r){
        
        for (int i = 0; i < r.size(); i++){
            for(Vino v : inventarVina)
            {
                if(v.poredjenje(r.get(i)))
                    v.promenaStanja(kom.get(i));
            }
            for(Pivo p : inventarPiva)
            {
                if(p.poredjenje(r.get(i)))
                    p.promenaStanja(kom.get(i));
            }
            for(Zestina z : inventarZestine)
            {
                if(z.poredjenje(r.get(i)))
                    z.promenaStanja(kom.get(i));
            }
        }
            
        
    }
    
    static void upisPiva(ArrayList<Pivo> inventarPiva){
        
        try {
            boolean poslednji = false;
            PrintWriter pw = new PrintWriter("pivo.txt");
            for (int i = 0; i < inventarPiva.size(); i++) {
                
                if(i == inventarPiva.size()-1)
                    poslednji = true;
                
                inventarPiva.get(i).upis(pw, poslednji);
                
            }
            pw.flush();
            pw.close();
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Podrum.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    static void upisVina(ArrayList<Vino> inventarVina){
        
        try {
            boolean poslednji = false;
            PrintWriter pw = new PrintWriter("vino.txt");
            for (int i = 0; i < inventarVina.size(); i++) {
                
                if(i == inventarVina.size()-1)
                    poslednji = true;
                
                inventarVina.get(i).upis(pw, poslednji);
                
            }
            pw.flush();
            pw.close();
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Podrum.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    static void upisZestine(ArrayList<Zestina> inventarZestine){

            try {
                boolean poslednji = false;
                PrintWriter pw = new PrintWriter("zestina.txt");
                for (int i = 0; i < inventarZestine.size(); i++) {

                    if(i == inventarZestine.size()-1)
                        poslednji = true;

                    inventarZestine.get(i).upis(pw, poslednji);

                }
                pw.flush();
                pw.close();


            } catch (FileNotFoundException ex) {
                Logger.getLogger(Podrum.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    
    static void admin(ArrayList<Pivo> inventarPiva, ArrayList<Vino> inventarVina, ArrayList<Zestina> inventarZestine, ArrayList<Korisnik> korisnici){
        
        System.out.println("1. Dodaj artikl");
        System.out.println("2. Obrisi artikl");
        System.out.println("3. Izmeni stanje robe");
        System.out.println("4. Prikazi inventar");
        System.out.println("5. Vrati se u prethodni meni");
        
        Scanner s = new Scanner(System.in);
        int opcija = s.nextInt();
        
        switch(opcija){
        
            case 1:
                 dodajArtikl(inventarPiva, inventarVina, inventarZestine);
                 System.out.println("Uspresno ste dodali artikl.");
                 upisPiva(inventarPiva);
                 upisVina(inventarVina);
                 upisZestine(inventarZestine);
                 admin(inventarPiva, inventarVina, inventarZestine, korisnici);
                 
                break;
            case 2:
                obrisiArtikl(inventarPiva, inventarVina, inventarZestine);
                System.out.println("Uspresno ste obrisali artikl.");
                upisPiva(inventarPiva);
                upisVina(inventarVina);
                upisZestine(inventarZestine);
                admin(inventarPiva, inventarVina, inventarZestine, korisnici);
                break;
            case 3:
                izmeniStanje(inventarPiva, inventarVina, inventarZestine);
                System.out.println("Uspresno ste izmenili stanje.");
                upisPiva(inventarPiva);
                upisVina(inventarVina);
                upisZestine(inventarZestine);
                admin(inventarPiva, inventarVina, inventarZestine, korisnici);
                break;
            case 4:
                System.out.println("\n_________PIVA_________");
                for(Pivo p : inventarPiva)
                    System.out.println(p);
                System.out.println("\n_________VINA_________");
                for (Vino v : inventarVina) 
                    System.out.println(v);
                System.out.println("\n________ZESTINA_______");
                for(Zestina z : inventarZestine)
                    System.out.println(z);
                System.out.println("");
                admin(inventarPiva, inventarVina, inventarZestine, korisnici);
                break;
            case 5:
                pocetni_ekran(inventarPiva, inventarVina, inventarZestine, korisnici);
                break;
            default:
                admin(inventarPiva, inventarVina, inventarZestine, korisnici);
                break;
        }
            
          
    
    
        
    }
    
    static void dodajArtikl(ArrayList<Pivo> inventarPiva, ArrayList<Vino> inventarVina, ArrayList<Zestina> inventarZestine){
    
        System.out.println("1. Dodaj pivo.");
        System.out.println("2. Dodaj vino.");
        System.out.println("3. Dodaj zestinu");
        
        
        Scanner s = new Scanner(System.in);
        
        int kod, stanje;
        String naziv, tip, ukus;
        double cena, promil, litraza;
        
       
        int opcija = s.nextInt();
        
        switch (opcija){
            
            case 1:
                do{
                    System.out.println("Unesite kod:");
                    kod = s.nextInt();              
                    if(Pivo.proveriKod(inventarPiva, kod) || Vino.proveriKod(inventarVina, kod) || Zestina.proveriKod(inventarZestine, kod))
                        System.out.println("Ovaj kod vec postoji");
                    if(kod <= 0)
                        System.out.println("Kod mora biti veci od nule");
                }while(kod <= 0 || Pivo.proveriKod(inventarPiva, kod) || Vino.proveriKod(inventarVina, kod) || Zestina.proveriKod(inventarZestine, kod));
                
                s.nextLine();
                
                System.out.println("Unesite tip: ");
                tip = s.nextLine();
                
                System.out.println("Unesite naziv: ");
                naziv = s.nextLine();
                
                do{
                    System.out.println("Unesite cenu: ");
                    cena = s.nextDouble();
                    if(cena <= 0)
                    System.out.println("Cena mora biti veca od nule");                
                }while(cena <=0);
                
                do{
                    System.out.println("Unesite litrazu: ");
                    litraza = s.nextDouble();
                    if(litraza <= 0)
                    System.out.println("Litraza mora biti veca od nule");                
                }while(litraza <=0);
                 
                do{
                    System.out.println("Unesite procenat alkohola: ");
                    promil = s.nextDouble();
                    if(promil <= 0)
                    System.out.println("Procenat alkohola mora biti veci od nule");                
                }while(promil <=0);
                  
                do{
                    System.out.println("Unesite stanje: ");
                    stanje = s.nextInt();
                    if(stanje <= 0)
                    System.out.println("Stanje mora biti vece od nule");                
                }while(stanje <=0);
                
                inventarPiva.add(new Pivo(tip, naziv, cena, litraza, promil, stanje, kod));
                break;
                
            case 2:
                 do{
                    System.out.println("Unesite kod:");
                    kod = s.nextInt();              
                    if(Pivo.proveriKod(inventarPiva, kod) || Vino.proveriKod(inventarVina, kod) || Zestina.proveriKod(inventarZestine, kod))
                        System.out.println("Ovaj kod vec postoji");
                    if(kod <= 0)
                        System.out.println("Kod mora biti veci od nule");
                }while(kod <= 0 || Pivo.proveriKod(inventarPiva, kod) || Vino.proveriKod(inventarVina, kod) || Zestina.proveriKod(inventarZestine, kod));
                
                s.nextLine();
                
                System.out.println("Unesite tip: ");
                tip = s.nextLine();
                
                System.out.println("Unesite ukus: ");
                ukus = s.nextLine();
                
                System.out.println("Unesite naziv: ");
                naziv = s.nextLine();
                
                do{
                    System.out.println("Unesite cenu: ");
                    cena = s.nextDouble();
                    if(cena <= 0)
                    System.out.println("Cena mora biti veca od nule");                
                }while(cena <=0);
                
                do{
                    System.out.println("Unesite litrazu: ");
                    litraza = s.nextDouble();
                    if(litraza <= 0)
                    System.out.println("Litraza mora biti veca od nule");                
                }while(litraza <=0);
                 
                do{
                    System.out.println("Unesite procenat alkohola: ");
                    promil = s.nextDouble();
                    if(promil <= 0)
                    System.out.println("Procenat alkohola mora biti veci od nule");                
                }while(promil <=0);
                  
                do{
                    System.out.println("Unesite stanje: ");
                    stanje = s.nextInt();
                    if(stanje <= 0)
                    System.out.println("Stanje mora biti vece od nule");                
                }while(stanje <=0);
                
                inventarVina.add(new Vino(tip,ukus, naziv, cena, litraza, promil, stanje, kod));
                
                break;
                
            case 3:
                 do{
                    System.out.println("Unesite kod:");
                    kod = s.nextInt();              
                    if(Pivo.proveriKod(inventarPiva, kod) || Vino.proveriKod(inventarVina, kod) || Zestina.proveriKod(inventarZestine, kod))
                        System.out.println("Ovaj kod vec postoji");
                    if(kod <= 0)
                        System.out.println("Kod mora biti veci od nule");
                }while(kod <= 0 || Pivo.proveriKod(inventarPiva, kod) || Vino.proveriKod(inventarVina, kod) || Zestina.proveriKod(inventarZestine, kod));

                s.nextLine();
                
                System.out.println("Unesite tip: ");
                tip = s.nextLine();
                
                System.out.println("Unesite naziv: ");
                naziv = s.nextLine();
                
                do{
                    System.out.println("Unesite cenu: ");
                    cena = s.nextDouble();
                    if(cena <= 0)
                    System.out.println("Cena mora biti veca od nule");                
                }while(cena <=0);
                
                do{
                    System.out.println("Unesite litrazu: ");
                    litraza = s.nextDouble();
                    if(litraza <= 0)
                    System.out.println("Litraza mora biti veca od nule");                
                }while(litraza <=0);
                 
                do{
                    System.out.println("Unesite procenat alkohola: ");
                    promil = s.nextDouble();
                    if(promil <= 0)
                    System.out.println("Procenat alkohola mora biti veci od nule");                
                }while(promil <=0);
                  
                do{
                    System.out.println("Unesite stanje: ");
                    stanje = s.nextInt();
                    if(stanje <= 0)
                    System.out.println("Stanje mora biti vece od nule");                
                }while(stanje <=0);
                
                inventarZestine.add(new Zestina(tip, naziv, cena, litraza, promil, stanje, kod));
                
                break;
                
            default:
                dodajArtikl(inventarPiva, inventarVina, inventarZestine);
                break;
        
        
        
        
        }
                
    }
    
    static void obrisiArtikl(ArrayList<Pivo> inventarPiva, ArrayList<Vino> inventarVina, ArrayList<Zestina> inventarZestine){
    
        System.out.println("Unesite kod artikla koji zelite da obrisete: ");
        int kod;
        Scanner s = new Scanner(System.in);
        
        do{
            System.out.println("Unesite kod:");
            kod = s.nextInt();              
            if(!(Pivo.proveriKod(inventarPiva, kod) || Vino.proveriKod(inventarVina, kod) || Zestina.proveriKod(inventarZestine, kod)))
                System.out.println("Ovaj kod ne postoji");
            if(kod <= 0)
                System.out.println("Kod mora biti veci od nule");
        }while(kod <= 0 || !(Pivo.proveriKod(inventarPiva, kod) || Vino.proveriKod(inventarVina, kod) || Zestina.proveriKod(inventarZestine, kod)));
     
        for(int i = 0; i < inventarPiva.size(); i++){
            if(inventarPiva.get(i).getKod() == kod){
                inventarPiva.remove(i);
            }
        }
        
        for(int i = 0; i < inventarVina.size(); i++){
            if(inventarVina.get(i).getKod() == kod){
                inventarVina.remove(i);
            }
        }
        
        for(int i = 0; i < inventarZestine.size(); i++){
            if(inventarZestine.get(i).getKod() == kod){
                inventarZestine.remove(i);
            }
        }
    
    }
    
    static void izmeniStanje(ArrayList<Pivo> inventarPiva, ArrayList<Vino> inventarVina, ArrayList<Zestina> inventarZestine){
    
        System.out.println("Unesite kod artikla kom zelite da promenite stanje: ");
        int kod, stanje;
        Scanner s = new Scanner(System.in);
        
        do{
            System.out.println("Unesite kod:");
            kod = s.nextInt();              
            if(!(Pivo.proveriKod(inventarPiva, kod) || Vino.proveriKod(inventarVina, kod) || Zestina.proveriKod(inventarZestine, kod)))
                System.out.println("Ovaj kod ne postoji");
            if(kod <= 0)
                System.out.println("Kod mora biti veci od nule");
        }while(kod <= 0 || !(Pivo.proveriKod(inventarPiva, kod) || Vino.proveriKod(inventarVina, kod) || Zestina.proveriKod(inventarZestine, kod)));
     
        for(int i = 0; i < inventarPiva.size(); i++){
            if(inventarPiva.get(i).getKod() == kod){
                do{
                    System.out.println("Unesite novo stanje: ");
                    stanje = s.nextInt();
                    if(stanje < 0){
                        System.out.println("Stanje mora biti pozitivan broj.");
                    }
                }while(stanje < 0);
                
                inventarPiva.get(i).setStanje(stanje);
            }
        }
        
        for(int i = 0; i < inventarVina.size(); i++){
            if(inventarVina.get(i).getKod() == kod){
               do{
                    System.out.println("Unesite novo stanje: ");
                    stanje = s.nextInt();
                    if(stanje < 0){
                        System.out.println("Stanje mora biti pozitivan broj.");
                    }
                }while(stanje < 0);
                
                inventarVina.get(i).setStanje(stanje);
            }
        }
        
        for(int i = 0; i < inventarZestine.size(); i++){
            if(inventarZestine.get(i).getKod() == kod){
                do{
                    System.out.println("Unesite novo stanje: ");
                    stanje = s.nextInt();
                    if(stanje < 0){
                        System.out.println("Stanje mora biti pozitivan broj.");
                    }
                }while(stanje < 0);
                
                inventarZestine.get(i).setStanje(stanje);
            }
        }
    
    }
    
    static void napraviNalog(ArrayList<Pivo> inventarPiva, ArrayList<Vino> inventarVina, ArrayList<Zestina> inventarZestine, ArrayList<Korisnik> korisnici){
    
        Scanner s = new Scanner(System.in);
        String korisnicko_ime, ime, prezime, sifra;
        
        do{
            System.out.println("Unesite korisnicko ime: ");
            korisnicko_ime = s.nextLine();
            if(Korisnik.proveriIme(korisnici, korisnicko_ime))
                System.out.println("Ovo korisnicko ime vec postoji. Unesite drugo korisnicko ime: ");
            
        }while(Korisnik.proveriIme(korisnici, korisnicko_ime));
        
        System.out.println("Unesite ime: ");
        ime = s.nextLine();
        
        System.out.println("Unesite prezime: ");
        prezime = s.nextLine();
        
        System.out.println("Unesite sifru: ");
        sifra = s.nextLine();
        
        System.out.println("Napravili ste novi nalog");
        
        Korisnik k = new Korisnik(ime , prezime , korisnicko_ime, sifra, false);
        korisnici.add(k);
        upisKorisnika(korisnici);
        
        pocetni_ekran(inventarPiva, inventarVina, inventarZestine, korisnici);
        
        
        
    }

    static void upisKorisnika(ArrayList<Korisnik> korisnici){
    
        
        try {
            boolean poslednji = false;
            PrintWriter pw = new PrintWriter("korisnici.txt");
            for (int i = 1; i < korisnici.size(); i++) {
                
                if(i == korisnici.size()-1)
                    poslednji = true;
                
                
                Korisnik k = korisnici.get(i);
                k.upis(pw, poslednji);
                
            }
            
            pw.flush();
            pw.close();
   
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Podrum.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    
    
    }



}
    

