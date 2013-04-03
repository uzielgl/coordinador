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
    
    public FrameCoordinador window;
    
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
    public ArrayList<Proceso> getGreaterPeers( int id_process ){
        ArrayList<Proceso> greater_peers = new ArrayList<Proceso>();
        for( Proceso p: peers )
            if( p.id > id_process )
                greater_peers.add( p )
        println "Peers Mayores : " + greater_peers;
        return greater_peers;
    }
    
    /** */
    public void sendMessage(Proceso p, Mensaje m){
        m.from_id = id;
        m.to_id = p.id;
        udpClient.sendMessage( p.ip, p.port, m);
    }
    
    /** Envia un mensaje de elección de coordinación al proceso*/
    public void sendMessageEleccion(Proceso p){
        Mensaje m = new  Mensaje();
        m.tipo = Mensaje.TIPO_ELECCION;
        sendMessage(p, m);
    }
    
    /** Lanza los mensajes de elección de coordinador a todos los procesos superiores*/
    public void seleccionarCoordinador(){
        ArrayList<Proceso> greater_peers = this.getGreaterPeers( id );
        for(Proceso p: greater_peers) sendMessageEleccion( p );
    }
}

