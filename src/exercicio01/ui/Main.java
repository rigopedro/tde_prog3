package exercicio01.ui;

import exercicio01.model.Automovel;
import exercicio01.service.AutomovelService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AutomovelService service = new AutomovelService();
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("""
                    1 - Incluir automóvel
                    2 - Remover automóvel
                    3 - Alterar dados de automóvel
                    4 - Consultar automóvel por placa
                    5 - Listar automóveis (ordenado)
                    6 - Salvar e sair
                    Escolha uma opção:""");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> {
                    System.out.print("Placa: ");
                    String placa = sc.nextLine();

                    // Verificar se placa já existe antes de continuar
                    if (service.buscarPorPlaca(placa) != null) {
                        System.out.println("Placa já existente. Tente novamente outra placa.");
                        break; // Sai do case para voltar ao menu
                    }
                    System.out.print("Modelo: ");
                    String modelo = sc.nextLine();
                    System.out.print("Marca: ");
                    String marca = sc.nextLine();
                    System.out.print("Ano: ");
                    int ano = sc.nextInt();
                    System.out.print("Valor: ");
                    double valor = sc.nextDouble();
                    sc.nextLine();
                    Automovel a = new Automovel(placa, modelo, marca, ano, valor);
                    if (service.inserir(a)) System.out.println("Automóvel inserido.");
                    else System.out.println("Erro ao inserir.");
                }
                case 2 -> {
                    System.out.print("Placa a remover: ");
                    String placa = sc.nextLine();
                    if (service.remover(placa)) System.out.println("Removido.");
                    else System.out.println("Não encontrado.");
                }
                case 3 -> {
                    System.out.print("Placa do automóvel a alterar: ");
                    String placa = sc.nextLine();
                    System.out.print("Novo modelo: ");
                    String modelo = sc.nextLine();
                    System.out.print("Nova marca: ");
                    String marca = sc.nextLine();
                    System.out.print("Novo ano: ");
                    int ano = sc.nextInt();
                    System.out.print("Novo valor: ");
                    double valor = sc.nextDouble();
                    sc.nextLine();
                    Automovel novo = new Automovel(placa, modelo, marca, ano, valor);
                    if (service.alterar(placa, novo)) System.out.println("Alterado.");
                    else System.out.println("Não encontrado.");
                }
                case 4 -> {
                    System.out.print("Placa a consultar: ");
                    String placa = sc.nextLine();
                    Automovel a = service.buscarPorPlaca(placa);
                    System.out.println(a != null ? a : "Não encontrado.");
                }
                case 5 -> {
                    System.out.print("Ordenar por (placa/modelo/marca): ");
                    String criterio = sc.nextLine();
                    List<Automovel> lista = service.listar(criterio);
                    for (Automovel a : lista) System.out.println(a);
                }
                case 6 -> {
                    service.salvar();
                    System.out.println("Encerrando...");
                }
                default -> System.out.println("Opção inválida.");
            }
            System.out.println();
        } while (opcao != 6);
    }
}
