package fumadores;

import static constantes.Constantes.*;

public class SalaFumadores {

    public SalaFumadores() {
        IniciarSessionDeFumadores();
    }

    private void IniciarSessionDeFumadores() {
        Agente agente = new Agente("Juan");
        Fumador fumador_con_tabaco = new Fumador("Pepe", agente, TABACO);
        Fumador fumador_con_papel = new Fumador("Julieta", agente, PAPEL);
        Fumador fumador_con_fosforos = new Fumador("Rodrigo", agente, FOSFOROS);
        agente.start();
        fumador_con_tabaco.start();
        fumador_con_papel.start();
        fumador_con_fosforos.start();
    }
}