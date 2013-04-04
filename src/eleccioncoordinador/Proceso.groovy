/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eleccioncoordinador
/**
 *
 * @author uzielgl
 */
class Proceso implements ComunicadorListener {
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
        udpServer.listeners.add(this);
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
    public Mensaje sendMessage(Proceso p, Mensaje m){
        m.from_id = id;
        m.to_id = p.id;
        return udpClient.sendMessage( p.ip, p.port, m);
    }
    
    /** Envia un mensaje de elección de coordinación al proceso*/
    public Mensaje sendMessageEleccion(Proceso p){
        Mensaje m = new  Mensaje();
        m.tipo = Mensaje.TIPO_ELECCION;
        return sendMessage(p, m);
    }
    
    /** Lanza los mensajes de elección de coordinador a todos los procesos superiores*/
    public void seleccionarCoordinador(){
        ArrayList<Proceso> greater_peers = this.getGreaterPeers( id );
        boolean ninguno_responde = true;
        for(Proceso p: greater_peers) {
            Mensaje m = sendMessageEleccion( p );
            if( m.tipo == Mensaje.TIPO_SIN_RESPUESTA){
                window.addHistory("El proceso $p.id NO respondió");
            }else{
                window.addHistory("El proceso $p.id respondió");
                ninguno_responde = false;
            }
        }
        /* */
        if( ninguno_responde ){
            window.addHistory("Soy el coordinador");
            window.addHistory("Envio mensaje de que soy coordinador a los demás procesos.");
            Mensaje m = new Mensaje();
            m.tipo = Mensaje.TIPO_COORDINADOR;
            //Enviamos a todos este mensaje
            for( Proceso p: peers) sendMessage(p, m);
        }
    }
    
    /** Este es del listener*/
    public void receiveMessage(Mensaje m){
        if( m.tipo == Mensaje.TIPO_COORDINADOR){
            window.addHistory("Se ha recibido mensaje de coordinador del proceso " + m.from_id);
            window.lblCoordinador.setText( Integer.toString( m.from_id ) );
        }
        if( m.tipo == Mensaje.TIPO_ELECCION){
            seleccionarCoordinador();
        }
    }
}

