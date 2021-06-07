//réalise par Ghannoum Jihad - Nouira Nessrine - Satouri Maha
//IATIC 3

package client;
import java.net.*; 
import java.io.*; 
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
public class Client {

    private Socket socket            = null; 
    private DataInputStream  input   = null; 
    private DataOutputStream out     = null; 
    private DataInputStream in       =  null;
    int j = 1;
    
    
    public Client(String address, int port) throws InterruptedException, ClassNotFoundException, IOException 
    { 
        
        try
        { 
            socket = new Socket(address, port); 
            System.out.println("Connecté"); 
  
            
            input  = new DataInputStream(System.in); 
  
            
            out    = new DataOutputStream(socket.getOutputStream()); 
            in = new DataInputStream( 
                new BufferedInputStream(socket.getInputStream())); 
        } 
        catch(UnknownHostException u) 
        { 
            System.out.println("Erreur "); 
        } 
        catch(IOException i) 
        { 
            System.out.println("La connexion au serveur a été refusée"); 
        } 
  
        
        String line = ""; 
  
       ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        while (!line.equals("FIN")) 
        { 
            try
            { 
                
                 System.out.println("Nombre des paquets,taille de sa file d’attente");
                line = input.readLine(); 
                out.writeUTF(line); 
                String message="";
                if (!line.equals("FIN")) 
                {
                    while(!message.equals("Terminer"))
                {
                     message = (String) ois.readObject();
                System.out.println( message); 
                }
                }
                
            
            } 
            catch(IOException i) 
            { 
                System.out.println(i); 
            } 
        } 
        System.out.println("en cours de fermeture ... Après 30 senconds "); 
            TimeUnit.SECONDS.sleep(30);
            
  
       
        try
        { 
            input.close(); 
            out.close(); 
            socket.close(); 
            System.out.println("connexion fermée "); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
    } 
  
    public static void main(String args[]) 
    { 
        boolean connecte= false;
        while(!connecte)
        {
            
        
        String adresse = ""; 
        String port = ""; 
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Entrez l'adresse IP du serveur"); 
        adresse = scanner.nextLine();
        System.out.println("Entrez le Port du serveur"); 
        port = scanner.nextLine();
        System.out.println("La connexion au serveur est en cours ... Adresse IP: "+adresse+" Port : "+port); 
                try
                {
                    Client client = new Client(adresse, Integer.parseInt(port)); 
                    connecte = true;
                }
                catch (Exception e)
                {
                    System.out.println("Veuillez vérifier l'adresse IP et le port"); 
                   
                }
        }
        
        
    } 
  
    
}
