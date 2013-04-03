/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eleccioncoordinador

import java.io.Serializable;
/**
 *
 * @author uzielgl
 */
class Mensaje implements Serializable{
    public static final int TIPO_ELECCION = 1;
    public static final int TIPO_OK = 2;
    public static final int TIPO_COORDINADOR = 3;
    
    public int tipo; //El tipo de mensaje que es
    
    public int from_id; //El id del mensaje que envia
    public int to_id; //El id al que envia

}
