
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author asus
 */
public class ServerMain {
    
    private int port;
    private Server server;
    public ServerMain(int port)
    {
        this.port=port;
        server=new Server(port);
    }
    
    public static void main(String []args)
    {
        int port;
        if(args.length >= 1)//yani diyoki 1 parametre girilecek komut satırından
        {
            System.out.println("Usage java -jar filename [port]");
            port=Integer.parseInt(args[0]);
            new ServerMain(port);
          
        }
        
        
        Scanner scan = new Scanner(System.in);
        System.out.println("Sunucunun Hizmet Vereceği Port Numarasını Giriniz:");
        port=scan.nextInt();
        new ServerMain(port);//burada server aynı uygulamada bir program gibi tanımladı içindeki thread lerle veriyi alıyor sürekli onu açmak içinde main dosyası tanımladı main alıyor bilgileri ana classtan nesne
        //türetiyor o nesnede serverdan yeni nesne açıyor boru gibi dusun o nesne üzerinden server ile iletişim kuruluyor o kadar nesne tanımlama bundan
    }
    
}
