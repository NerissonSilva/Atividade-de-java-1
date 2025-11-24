import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ControleEstoque {

    static class Produto {
        private String nome;
        private String descricao;
        private int quantidadeEstoque;
        private double precoUnitario;
        private String categoria;
        private int quantidadeMinima;

        public Produto(String nome, String descricao, int quantidadeEstoque, double precoUnitario, String categoria, int quantidadeMinima) {
            this.nome = nome;
            this.descricao = descricao;
            this.quantidadeEstoque = quantidadeEstoque;
            this.precoUnitario = precoUnitario;
            this.categoria = categoria;
            this.quantidadeMinima = quantidadeMinima;
        }

        
        public String getNome() { return nome; }
        public String getDescricao() { return descricao; }
        public int getQuantidadeEstoque() { return quantidadeEstoque; }
        public double getPrecoUnitario() { return precoUnitario; }
        public String getCategoria() { return categoria; }
        public int getQuantidadeMinima() { return quantidadeMinima; }

        public void setPrecoUnitario(double precoUnitario) { this.precoUnitario = precoUnitario; }
        public void setQuantidadeEstoque(int quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }

        @Override
        public String toString() {
            return "Produto{" +
                    "nome='" + nome + '\'' +
                    ", descricao='" + descricao + '\'' +
                    ", quantidadeEstoque=" + quantidadeEstoque +
                    ", precoUnitario=" + precoUnitario +
                    ", categoria='" + categoria + '\'' +
                    ", quantidadeMinima=" + quantidadeMinima +
                    '}';
        }

        public double valorTotalEstoque() {
            return this.quantidadeEstoque * this.precoUnitario;
        }
    }

    
    private List<Produto> produtos = new ArrayList<>();

    public void cadastrarProduto(Produto p) {
        produtos.clear(); 
        produtos.add(p);
    }

    public void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            for (Produto p : produtos) {
                System.out.println(p);
            }
        }
    }

    public void filtrarPorCategoria(String categoria) {
        boolean achou = false;
        for (Produto p : produtos) {
            if (p.getCategoria().equalsIgnoreCase(categoria)) {
                System.out.println(p);
                achou = true;
            }
        }
        if (!achou) {
            System.out.println("Nenhum produto encontrado na categoria '" + categoria + "'.");
        }
    }

    public void ordenarPorCategoria() {
        produtos.sort(Comparator.comparing(Produto::getCategoria));
        System.out.println("Ordenado por categoria.");
    }

    public void removerProduto() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto para remover.");
        } else {
            produtos.clear();
            System.out.println("Produto removido.");
        }
    }

    public void atribuirPreco(double novoPreco) {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado para atribuir preço.");
        } else {
            Produto p = produtos.get(0);
            p.setPrecoUnitario(novoPreco);
            System.out.println("Preço atualizado: " + p);
        }
    }

    public void listarComSubtotalPorCategoria() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        
        ordenarPorCategoria();

        
        String categoriaAtual = null;
        double subtotal = 0.0;
        for (Produto p : produtos) {
            if (categoriaAtual == null) {
                categoriaAtual = p.getCategoria();
            }
            if (!p.getCategoria().equals(categoriaAtual)) {
    
                System.out.println("Subtotal da categoria '" + categoriaAtual + "': " + subtotal);
                
                categoriaAtual = p.getCategoria();
                subtotal = 0.0;
            }
            double valor = p.valorTotalEstoque();
            System.out.println(p + " -> valor total em estoque: " + valor);
            subtotal += valor;
        }
        
        System.out.println("Subtotal da categoria '" + categoriaAtual + "': " + subtotal);
    }


    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean sair = false;

        while (!sair) {
            System.out.println("\n=== Menu Controle de Estoque ===");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Listar produto");
            System.out.println("3 - Filtrar por categoria");
            System.out.println("4 - Ordenar por categoria");
            System.out.println("5 - Remover produto");
            System.out.println("6 - Atribuir preço ao produto");
            System.out.println("7 - Listagem com subtotal por categoria");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            int opc = scanner.nextInt();
            scanner.nextLine(); 

            switch (opc) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Descrição: ");
                    String desc = scanner.nextLine();
                    System.out.print("Quantidade em estoque: ");
                    int qtd = scanner.nextInt();
                    System.out.print("Preço unitário: ");
                    double preco = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Categoria: ");
                    String categoria = scanner.nextLine();
                    System.out.print("Quantidade mínima: ");
                    int qtdMin = scanner.nextInt();
                    scanner.nextLine();

                    Produto novo = new Produto(nome, desc, qtd, preco, categoria, qtdMin);
                    cadastrarProduto(novo);
                    System.out.println("Produto cadastrado!");
                    break;

                case 2:
                    listarProdutos();
                    break;

                case 3:
                    System.out.print("Categoria para filtrar: ");
                    String catFiltrar = scanner.nextLine();
                    filtrarPorCategoria(catFiltrar);
                    break;

                case 4:
                    ordenarPorCategoria();
                    System.out.println("Produtos após ordenação:");
                    listarProdutos();
                    break;

                case 5:
                    removerProduto();
                    break;

                case 6:
                    System.out.print("Novo preço unitário: ");
                    double novoPreco = scanner.nextDouble();
                    scanner.nextLine();
                    atribuirPreco(novoPreco);
                    break;

                case 7:
                    listarComSubtotalPorCategoria();
                    break;

                case 0:
                    sair = true;
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        ControleEstoque controle = new ControleEstoque();
        controle.mostrarMenu();
    }
}
