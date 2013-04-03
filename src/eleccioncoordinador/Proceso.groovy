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
    public ArrayList<Proceso> peers;
    
    public UDPClient udpClient = new UDPClient();
    public UDPServer udpServer;
    
    public Proceso( int id, String ip, int port ){
        this.ip = ip;
        this.id = id;
        this.port = port;
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
    
    /** Establece todos los procesos involucrados en el SD*/
    public void setPeers(ArrayList<Proceso> peers){
        this.peers = peers;
    }
    
    /** Obtiene los Peers mayores (que tienen el id mayor o igual ? ) a el mismo*/
    public ArrayList<Proceso> getGreaterPeers(){
        
    }
}

