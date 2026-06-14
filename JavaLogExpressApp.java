// Importa as classes necessárias para ler dados do usuário e guardar vários veículos.
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// 1. CONTRATO DO SISTEMA (classe abstrata que define o comportamento comum dos veículos).
abstract class Veiculo {
    // Atributos privados da classe base: cada veículo tem placa, modelo e capacidade de carga.
    private String placa;
    private String modelo;
    protected double capacidadeCargaKg;

    // Construtor da classe Veiculo: recebe os dados básicos e inicializa os atributos.
    public Veiculo(String placa, String modelo, double capacidadeCargaKg) {
        this.placa = placa;
        this.modelo = modelo;
        this.capacidadeCargaKg = capacityValida(capacidadeCargaKg);
    }

    // Método privado que valida a capacidade de carga. Se for menor ou igual a zero, usa 100 kg.
    private double capacityValida(double carga) {
        return carga > 0 ? carga : 100; // Validação simples de encapsulamento.
    }

    // Métodos getters para acessar os dados dos veículos de forma segura.
    public String getPlaca() { return placa; }
    public String getModelo() { return modelo; }
    public double getCapacidadeCargaKg() { return capacidadeCargaKg; }

    // Método abstrato: cada subclasse deve implementar seu próprio cálculo de frete.
    public abstract double calcularCustoFrete(double distanciaKm);
}

// Subclasse Van: representa um veículo menor, herdando os atributos de Veiculo.
class Van extends Veiculo {
    // Construtor da Van: chama o construtor da classe pai (Veiculo) com placa, modelo e capacidade.
    public Van(String placa, String modelo, double capacidadeCargaKg) {
        super(placa, modelo, capacidadeCargaKg);
    }

    // Sobrescreve o cálculo do frete para vans.
    @Override
    public double calcularCustoFrete(double distanciaKm) {
        // Frete base: R$ 2,50 por quilômetro.
        double frete = distanciaKm * 2.50;

        // Se a capacidade for maior que 1000 kg, adiciona taxa extra de risco.
        if (capacidadeCargaKg > 1000) {
            frete += 30.0;
        }

        return frete;
    }
}

// Subclasse Caminhao: representa um veículo pesado com um atributo extra chamado eixos.
class Caminhao extends Veiculo {
    // Atributo específico do caminhão.
    private int eixos;

    // Construtor do caminhão: recebe todos os dados e repassa os básicos para a superclasse.
    public Caminhao(String placa, String modelo, double capacidadeCargaKg, int eixos) {
        super(placa, modelo, capacidadeCargaKg);
        setEixos(eixos);
    }

    // Getter do número de eixos.
    public int getEixos() {
        return eixos;
    }

    // Setter com validação simples: se o valor vier inválido, usa 2 eixos por padrão.
    public void setEixos(int eixos) {
        this.eixos = eixos > 0 ? eixos : 2;
    }

    // Sobrescreve o cálculo do frete para caminhões.
    @Override
    public double calcularCustoFrete(double distanciaKm) {
        // Fórmula: distância * R$ 4,50 * número de eixos.
        return distanciaKm * 4.50 * eixos;
    }
}

// 2. CLASSE PRINCIPAL DO SISTEMA
public class JavaLogExpressApp {
    public static void main(String[] args) {
        // Cria um objeto Scanner para ler entradas do usuário.
        Scanner leitor = new Scanner(System.in);

        // Cria uma lista dinâmica que guarda todos os veículos cadastrados.
        List<Veiculo> frota = new ArrayList<>();

        // Variável para controlar o menu. Começa em -1 para entrar no loop.
        int opcao = -1;

        // Loop principal do sistema: continua até o usuário escolher 0.
        while (opcao != 0) {
            // Exibe o menu principal.
            System.out.println("\n--- JAVALOG EXPRESS - SISTEMA DE FROTA ---");
            System.out.println("1. Cadastrar Van no Sistema");
            System.out.println("2. Cadastrar Caminhão no Sistema");
            System.out.println("3. Listar Veículos da Frota e Custos de Frete Simulados");
            System.out.println("0. Encerrar Sistema");
            System.out.print("Escolha uma opção: ");

            // Lê a opção escolhida pelo usuário.
            opcao = leitor.nextInt();
            leitor.nextLine(); // Limpa o Enter deixado pelo nextInt para não quebrar a próxima leitura.

            // Decide o que fazer com base na opção escolhida.
            switch (opcao) {
                // Opção 1: cadastrar uma Van.
                case 1:
                    System.out.print("Digite a Placa da Van: ");
                    String pVan = leitor.nextLine();
                    System.out.print("Digite o Modelo: ");
                    String mVan = leitor.nextLine();
                    System.out.print("Capacidade de Carga (kg): ");
                    double cVan = leitor.nextDouble();
                    leitor.nextLine(); // Limpa a quebra de linha após ler o double.

                    // Adiciona a nova van na lista dinâmica da frota.
                    frota.add(new Van(pVan, mVan, cVan));
                    System.out.println("Van cadastrada com sucesso!");
                    break;

                // Opção 2: cadastrar um Caminhão.
                case 2:
                    System.out.print("Digite a Placa do Caminhão: ");
                    String pCam = leitor.nextLine();
                    System.out.print("Digite o Modelo: ");
                    String mCam = leitor.nextLine();
                    System.out.print("Capacidade de Carga (kg): ");
                    double cCam = leitor.nextDouble();
                    System.out.print("Número de Eixos do Caminhão: ");
                    int eixos = leitor.nextInt();
                    leitor.nextLine(); // Limpa a quebra de linha após ler o int.

                    // Adiciona o novo caminhão na lista dinâmica da frota.
                    frota.add(new Caminhao(pCam, mCam, cCam, eixos));
                    System.out.println("Caminhão cadastrado com sucesso!");
                    break;

                // Opção 3: exibir relatório da frota com simulação de frete para 100 km.
                case 3:
                    System.out.println("\n--- RELATÓRIO DA FROTA E SIMULAÇÃO DE FRETE (Para 100km) ---");
                    if (frota.isEmpty()) {
                        System.out.println("Nenhum veículo cadastrado na frota.");
                    } else {
                        // Percorre a lista usando for-each, sem precisar saber o tipo exato do veículo.
                        for (Veiculo v : frota) {
                            // Se o objeto for um caminhão, mostra o número de eixos.
                            if (v instanceof Caminhao) {
                                Caminhao cam = (Caminhao) v;
                                System.out.printf("Veículo: Caminhão | Placa: %s | Modelo: %s | Eixos: %d | Frete Simulado: R$ %.2f%n",
                                        v.getPlaca(), v.getModelo(), cam.getEixos(), v.calcularCustoFrete(100));
                            } else {
                                // Se não for caminhão, assume que é uma van.
                                System.out.printf("Veículo: Van | Placa: %s | Modelo: %s | Frete Simulado: R$ %.2f%n",
                                        v.getPlaca(), v.getModelo(), v.calcularCustoFrete(100));
                            }
                        }
                    }
                    break;

                // Opção 0: encerra o sistema.
                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;

                // Qualquer valor diferente de 0, 1, 2 e 3 entra aqui.
                default:
                    System.out.println("Opção inválida!");
            }
        }

        // Fecha o Scanner ao final para liberar recursos.
        leitor.close();
    }
}
