import java.sql.Connection;

public class GerenciadorContasApp {
    private static ContaDao dao;
    public static void main (String [] args)throws Exception {
        String url;
        url = "jdbc:postgresql://aws-0-us-west-2.pooler.supabase.com:6543/postgres?user=postgres.kmgceisasvvupvssughl&password=HS74@#hs28101997";
        Connection conexao;
        conexao = ConnectionFactory.getConnection(url);
        dao = new ContaDao(conexao);
        lacoMenuPrincipal();
    }
 private static void lacoMenuPrincipal() {
        boolean sair = false;
        while(!sair) {
            int op = solicitarOperacao();
            switch(op) {
                case 1: criarConta(); break;
                case 2: mostrarContas(); break;
                case 3:
                case 4:
                case 5: sair = true; break;
                default: IO.println("Opção inválida!\n");
            }
        }
    }
    private static int solicitarOperacao() {
        StringBuilder menu = new StringBuilder("");
        menu.append("GERENCIADOR DE CONTAS\n");
        menu.append("(1) Criar nova conta\n");
        menu.append("(2) Consultar contas\n");
        menu.append("(3) Alterar saldo de uma conta\n");
        menu.append("(4) Apagar uma conta\n");
        menu.append("(5) Sair\n");
        menu.append("Escolha uma opção: ");
        int opcao = Integer.parseInt(IO.readln(menu.toString()));
        return opcao;
    }   
    private static void criarConta() {
        IO.print("Número da conta a ser criada: ");
        int nro = Integer.parseInt(IO.readln());
        IO.print("Saldo da conta a ser criada: ");
        double saldo = Double.parseDouble(IO.readln());
        Conta c = new Conta(nro, saldo);
        if (dao.criar(c)) {
            IO.println("Conta criada com sucesso!");
        }
        else {
            IO.println("Não foi possível criar esta conta!");
        }
    }   
    private static void mostrarContas() {

    } 
    private static void alterarConta() {
        IO.print("Número da conta a ser criada: ");
        int nro = Integer.parseInt(IO.readln());
        

    }
    private static void apagarConta() {

    }
}