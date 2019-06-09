package fumadores;

import static constantes.Constantes.*;
import java.util.Random;
import java.util.concurrent.*;

public class Agente extends Thread {

    public String nombre;
    public boolean termino = false;
    public String elementos_entregados = new String();
    public Semaphore semaforo_fumar = new Semaphore(0);
    public Semaphore semaforo_elemento = new Semaphore(0);
    public Semaphore semaforo_latch = new Semaphore(0);
    public CountDownLatch latch;

    private final Random numero_random = new Random();
    private int elemento_actual;

    public Agente(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                latch = new CountDownLatch(3);
                semaforo_latch.release(3);
                try {
                    latch.await();
                } catch (InterruptedException latch_exception) {
                    latch_exception.printStackTrace();
                }

                elemento_actual = numero_random.nextInt(3);
                switch (elemento_actual) {
                    case 0:
                        elementos_entregados = PAPEL_FOSFOROS;
                        break;
                    case 1:
                        elementos_entregados = TABACO_FOSFOROS;
                        break;
                    case 2:
                        elementos_entregados = PAPEL_TABACO;
                        break;
                    default:
                        break;
                }

                System.out.println("El agente " + nombre + " coloca " + elementos_entregados + " en la mesa");
                Thread.sleep(1000);
                semaforo_elemento.release(3);
                try {
                    semaforo_fumar.acquire(3);
                } catch (InterruptedException fumar_exception) {
                    fumar_exception.printStackTrace();
                }
            }
            termino = true;
            System.out.println("************************************");
            System.out.println("El agente " + nombre + " se retiró de la sala.");
            System.out.println("************************************");
            System.out.println("#####################################################");

            semaforo_latch.release(3);
            semaforo_elemento.release(3);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }

    }
}
