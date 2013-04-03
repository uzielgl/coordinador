/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eleccioncoordinador
/**
 *
 * @author uzielgl
 */
class Proceso {
    public String ip;
    public int port;
    public int id;
    
    //Todos los procesos involucrados
    ArrayList<Proceso> peers;
    
    UDPClient udpClient = new UDPClient();
    UDPServer udpServer;
    
    public Proceso( int id, String ip, int port ){
        this.ip = ip;
        this.id = id;
        this.port = port;
        
        start();
    }
        
    /** Inicia el servidor del proceso*/
    public void start(){
        udpServer = new UDPServer(ip, port);
        udpServer.start();
    }
    
    /** Detiene el servidor del proceso*/
    public void stop(){
        udpServer.stopServer();
    }
}

