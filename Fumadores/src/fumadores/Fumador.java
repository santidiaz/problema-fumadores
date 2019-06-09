package fumadores;

import static constantes.Constantes.*;

public class Fumador extends Thread {
    
    public String nombre;
    private Agente agente;
    private final String elemento_que_posee;
    private String elementos_que_le_faltan;
 
    public Fumador(String nombre, Agente agente, String elemento) {
        this.nombre = nombre;
        this.agente = agente;
        this.elemento_que_posee = elemento;
        this.cargarElementosFaltantes();
    }
 
    private void cargarElementosFaltantes() {
        switch (elemento_que_posee) {
            case TABACO:
                elementos_que_le_faltan = PAPEL_FOSFOROS;
                break;
            case PAPEL:
                elementos_que_le_faltan = TABACO_FOSFOROS;
                break;
            case FOSFOROS:
                elementos_que_le_faltan = PAPEL_TABACO;
                break;
            default:
                break;
        }
    }
    
    @Override
    public void run() {
        while (true) {
            try {                                
                if (agente.termino) {
                    return;
                }

                obtenerSemaforoLatch();
                sincronizarLatch();
                obtenerSemaforoElemento();

                if (agente.termino) {
                    System.out.println(nombre + " se retira de la sala porque el agente se fue.");
                    System.out.println("#####################################################");
                    return;
                }
                
                if (agente.elementos_entregados.equals(elementos_que_le_faltan)) {
                    System.out.println("#####################################################");
                    System.out.println(nombre + " tiene " + elemento_que_posee + " y consiguio "
                            + agente.elementos_entregados + ".");
                    System.out.println("* " + nombre + " coloca los papel, tabaco y fosforos en la mesa *");
                    Thread.sleep(1000);
                    System.out.println("* rola un cigarro *");
                    Thread.sleep(300);
                    System.out.println("* lo prende y comienza a fuma *");
                    Thread.sleep(300);
                    System.out.println("*(_̅_(̅_̅_̅_̅_̅()ڪے*");
                    Thread.sleep(300);
                    System.out.println("* tira el cigarro *");
                    System.out.println("#####################################################");
                    Thread.sleep(500);
                }
                
                agente.semaforo_fumar.release();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void sincronizarLatch() {
        synchronized (agente.latch) {
            agente.latch.countDown();
        }
    }
    
    private void obtenerSemaforoLatch() {
        try {
            agente.semaforo_latch.acquire();
        } catch (InterruptedException latch_exception) {
            latch_exception.printStackTrace();
        }
    }    
    
    private void obtenerSemaforoElemento() {
        try {
            agente.semaforo_elemento.acquire();
        } catch (InterruptedException latch_exception) {
            latch_exception.printStackTrace();
        }
    }    

}
