import java.sql.Connection;
import java.util.List;

public class GerenciadorContasApp {
    private static ContaDao contaDao;
    private static TitularDao titularDao;

    public static void main (String [] args) throws Exception {
        String url;
        url = "jdbc:postgresql://aws-0-us-west-2.pooler.supabase.com:6543/postgres?user=postgres.kmgceisasvvupvssughl&password=HS74@#hs28101997";
        Connection conexao;
        conexao = ConnectionFactory.getConnection(url);
        contaDao = new ContaDao(conexao);
        titularDao = new TitularDao(conexao);
        lacoMenuInicial();
    }

    private static void lacoMenuInicial() {
        boolean sair = false;
        while(!sair) {
            int op = solicitarSistema();
            switch(op) {
                case 1: lacoMenuContas(); break;
                case 2: lacoMenuTitulares(); break;
                case 3: sair = true; break;
                default: IO.println("Opção inválida!\n");
            }
        }
    }

    private static int solicitarSistema() {
        StringBuilder menu = new StringBuilder("");
        menu.append("GERENCIADOR\n");
        menu.append("(1) Gerenciar contas\n");
        menu.append("(2) Gerenciar titulares\n");
        menu.append("(3) Sair\n");
        menu.append("Escolha uma opção: ");
        int opcao = Integer.parseInt(IO.readln(menu.toString()));
        return opcao;
    }

    // ================= CONTAS =================

    private static void lacoMenuContas() {
        boolean voltar = false;
        while(!voltar) {
            int op = solicitarOperacaoContas();
            switch(op) {
                case 1: criarConta(); break;
                case 2: mostrarContas(); break;
                case 3: alterarConta(); break;
                case 4: apagarConta(); break;
                case 5: voltar = true; break;
                default: IO.println("Opção inválida!\n");
            }
        }
    }

    private static int solicitarOperacaoContas() {
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
        IO.print("Saldo da conta a ser criado: ");
        double saldo = Double.parseDouble(IO.readln());
        Conta c = new Conta(nro, saldo);
        if (contaDao.criar(c)) {
            IO.println("Conta criada com sucesso!");
        }
        else {
            IO.println("Não foi possível criar esta conta!");
        }
    }

    private static void mostrarContas() {
        List<Conta> contas = contaDao.lerTodas();
        if (contas.isEmpty()){
            IO.println("Nenhuma conta cadastrada");
            return;
        }
        for (Conta c : contas){
            IO.println("Conta: " + c.nroConta() + " e Saldo: " + c.saldo());
        }
    }

    private static void alterarConta() {
        IO.print("Número da conta a ser alterado: ");
        int nro = Integer.parseInt(IO.readln());
        IO.print("Saldo da conta a ser alterado: ");
        double saldo = Double.parseDouble(IO.readln());
        Conta c = new Conta(nro, saldo);
        if(contaDao.atualizar(c)){
            IO.println("Conta alterada com sucesso!");
        }
        else {
            IO.println("Não foi possível criar esta conta!");
        }
    }

    private static void apagarConta() {
        IO.print("Número da conta a ser deletada: ");
        int nro = Integer.parseInt(IO.readln());
        Conta c = contaDao.buscarPeloNumero(nro);
        if (c == null){
            IO.println("Conta não encontrada!\n");
            return;
        }
        if(contaDao.apagar(c)){
            IO.println("Conta apagada com sucesso!\n");
        } else {
            IO.println("Não foi possível apagar a conta!\n");
        }
    }

    // ================= TITULARES =================

    private static void lacoMenuTitulares() {
        boolean voltar = false;
        while(!voltar) {
            int op = solicitarOperacaoTitulares();
            switch(op) {
                case 1: criarTitular(); break;
                case 2: mostrarTitulares(); break;
                case 3: editarTitular(); break;
                case 4: apagarTitular(); break;
                case 5: voltar = true; break;
                default: IO.println("Opção inválida!\n");
            }
        }
    }

    private static int solicitarOperacaoTitulares() {
        StringBuilder menu = new StringBuilder("");
        menu.append("GERENCIADOR DE TITULARES\n");
        menu.append("(1) Cadastrar um novo titular\n");
        menu.append("(2) Consultar titulares\n");
        menu.append("(3) Editar dados de um titular\n");
        menu.append("(4) Apagar o cadastro de um titular\n");
        menu.append("(5) Sair\n");
        menu.append("Escolha uma opção: ");
        int opcao = Integer.parseInt(IO.readln(menu.toString()));
        return opcao;
    }

    private static void criarTitular() {
        IO.print("Número do titular a ser cadastrado: ");
        long nro = Long.parseLong(IO.readln());
        IO.print("Nome do titular: ");
        String nome = IO.readln();
        IO.print("RG do titular: ");
        String rg = IO.readln();
        IO.print("CPF do titular: ");
        String cpf = IO.readln();
        Titular t = new Titular(nro, nome, rg, cpf);
        if (titularDao.criar(t)) {
            IO.println("Titular cadastrado com sucesso!");
        }
        else {
            IO.println("Não foi possível cadastrar este titular!");
        }
    }

    private static void mostrarTitulares() {
        List<Titular> titulares = titularDao.lerTodos();
        if (titulares.isEmpty()){
            IO.println("Nenhum titular cadastrado");
            return;
        }
        for (Titular t : titulares){
            IO.println("Titular: " + t.nro_titular() + " | Nome: " + t.nome()
                    + " | RG: " + t.rg() + " | CPF: " + t.cpf());
        }
    }

    private static void editarTitular() {
        IO.print("Número do titular a ser editado: ");
        long nro = Long.parseLong(IO.readln());
        Titular atual = titularDao.buscarPeloNumeroTitular(nro);
        if (atual == null) {
            IO.println("Titular não encontrado!\n");
            return;
        }
        IO.print("Novo nome: ");
        String nome = IO.readln();
        IO.print("Novo RG: ");
        String rg = IO.readln();
        IO.print("Novo CPF: ");
        String cpf = IO.readln();

        Titular t = new Titular(nro, nome, rg, cpf);
        boolean nomeOk = titularDao.atualizarTitularNome(t);
        boolean rgOk = titularDao.atualizarTitularRG(t);
        boolean cpfOk = titularDao.atualizarTitularCPF(t);

        if (nomeOk && rgOk && cpfOk) {
            IO.println("Titular alterado com sucesso!");
        } else {
            IO.println("Não foi possível alterar todos os dados deste titular!");
        }
    }

    private static void apagarTitular() {
        IO.print("Número do titular a ser deletado: ");
        long nro = Long.parseLong(IO.readln());
        Titular t = titularDao.buscarPeloNumeroTitular(nro);
        if (t == null){
            IO.println("Titular não encontrado!\n");
            return;
        }
        if(titularDao.apagarTitular(t)){
            IO.println("Titular apagado com sucesso!\n");
        } else {
            IO.println("Não foi possível apagar o titular!\n");
        }
    }
}