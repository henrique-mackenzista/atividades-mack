import java.util.List;

public interface ITitularDao {
    boolean criar(Titular t);
    List<Titular> lerTodos();
    Titular buscarPeloNumeroTitular(long numero);
    boolean atualizarTitularNome(Titular t);
    boolean atualizarTitularRG(Titular t);
    boolean atualizarTitularCPF(Titular t);
    boolean apagarTitular(Titular t);
}