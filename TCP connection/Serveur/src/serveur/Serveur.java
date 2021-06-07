//réalise par Ghannoum Jihad - Nouira Nessrine - Satouri Maha
//IATIC 3
package serveur;
import java.net.*; 
import java.io.*; 
import java.util.concurrent.TimeUnit;

public class Serveur {

  
    private Socket          socket   = null; 
    private ServerSocket    server   = null; 
    private DataInputStream in       =  null; 
    private DataOutputStream out     = null; 
  
   
    public Serveur(int port) throws InterruptedException 
    { 
        
        try
        { 
            server = new ServerSocket(port); 
            System.out.println("Serveur démarré"); 
  
            System.out.println("En attente d'un client ..."); 
  
            socket = server.accept(); 
            System.out.println("Client accepté"); 
  
            
            in = new DataInputStream( 
                new BufferedInputStream(socket.getInputStream())); 
  
            String line = ""; 
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
  
            
            while (!line.equals("FIN")) 
            { 
                try
                { 
                    line = in.readUTF(); 
                    
                    String[] value = line.split(",");
                    
                   
                    if(value.length == 2 )
                    {
                        int nbrP = Integer.parseInt(value[0]);
                        int rcvwindow = Integer.parseInt(value[1]);
                        String messageP="";
                        int nbrPEnvoye = 0;
                        
                           for(int c = 1;c<=nbrP;c++)
                           {
                               messageP=messageP+"Paquet"+c+" ";
                               nbrPEnvoye++;
                               if(nbrPEnvoye == rcvwindow)
                               {
                                   oos.writeObject(messageP);
                                   messageP="";
                                   nbrPEnvoye=0;
                               }
                                   
                           }
                           if((nbrP%rcvwindow) != 0)
                               oos.writeObject(messageP);
                           oos.writeObject("Terminer");
                        
                    }
                    else
                    {
                        oos.writeObject("les paquets erroné(s)");
                        oos.writeObject("Terminer");
                    }
                     
          
            
                   
  
                } 
                catch(IOException i) 
                { 
                    System.out.println(i); 
                } 
            } 
            System.out.println("en cours de fermeture ... Après 30 senconds "); 
            TimeUnit.SECONDS.sleep(30);
  
            
            socket.close(); 
            in.close(); 
            System.out.println("connexion fermée "); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
    } 
  
    public static void main(String args[]) throws InterruptedException 
    { 
        Serveur server = new Serveur(5000); 
    } 
    
}
