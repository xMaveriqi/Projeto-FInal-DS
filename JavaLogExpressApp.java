import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class Veiculo {
    private String placa;
    private String modelo;
    protected double capacidadeCargaKg;
    
    public Veiculo(String placa, String modelo, double capacidadeCargaKg) {
        this.placa = placa;
        this.modelo = modelo;
        this.capacidadeCargaKg = capacityValida(capacidadeCargaKg);
    }

      private double capacityValida(double carga) {
        return carga > 0 ? carga : 100; 
    }
    
    public String getPlaca() { return placa; }
    public String getModelo() { return modelo; }
    public double getCapacidadeCargaKg() { return capacidadeCargaKg; }
    
    public abstract double calcularCustoFrete(double distanciaKm);
}
class Van extends Veiculo {
      public Van(String placa, String modelo, double capacidadeCargaKg) {
        super(placa, modelo, capacidadeCargaKg);
    }
    @Override
    public double calcularCustoFrete(double distanciaKm) {
        double frete = distanciaKm * 2.50;
        if (capacidadeCargaKg > 1000) {
            frete += 30.0;
        }
        return frete;
    }
}
class Caminhao extends Veiculo {
    private int eixos;
    public Caminhao(String placa, String modelo, double capacidadeCargaKg, int eixos) {
        super(placa, modelo, capacidadeCargaKg);
        setEixos(eixos);
    }
    public int getEixos() {
        return eixos;
    }
    public void setEixos(int eixos) {
        this.eixos = eixos > 0 ? eixos : 2;
    }
    @Override
    public double calcularCustoFrete(double distanciaKm) {.
        return distanciaKm * 4.50 * eixos;
    }
}

public class JavaLogExpressApp {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        
        List<Veiculo> frota = new ArrayList<>();
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- JAVALOG EXPRESS - SISTEMA DE FROTA ---");
            System.out.println("1. Cadastrar Van no Sistema");
            System.out.println("2. Cadastrar Caminhão no Sistema");
            System.out.println("3. Listar Veículos da Frota e Custos de Frete Simulados");
            System.out.println("0. Encerrar Sistema");
            System.out.print("Escolha uma opção: ");
            
            opcao = leitor.nextInt();
            leitor.nextLine();
            .
            switch (opcao) {
                case 1:
                    System.out.print("Digite a Placa da Van: ");
                    String pVan = leitor.nextLine();
                    System.out.print("Digite o Modelo: ");
                    String mVan = leitor.nextLine();
                    System.out.print("Capacidade de Carga (kg): ");
                    double cVan = leitor.nextDouble();
                    leitor.nextLine();
                
                    frota.add(new Van(pVan, mVan, cVan));
                    System.out.println("Van cadastrada com sucesso!");
                    break;
                
                case 2:
                    System.out.print("Digite a Placa do Caminhão: ");
                    String pCam = leitor.nextLine();
                    System.out.print("Digite o Modelo: ");
                    String mCam = leitor.nextLine();
                    System.out.print("Capacidade de Carga (kg): ");
                    double cCam = leitor.nextDouble();
                    System.out.print("Número de Eixos do Caminhão: ");
                    int eixos = leitor.nextInt();
                    leitor.nextLine(); 
                
                    frota.add(new Caminhao(pCam, mCam, cCam, eixos));
                    System.out.println("Caminhão cadastrado com sucesso!");
                    break;
                
                case 3:
                    System.out.println("\n--- RELATÓRIO DA FROTA E SIMULAÇÃO DE FRETE (Para 100km) ---");
                    if (frota.isEmpty()) {
                        System.out.println("Nenhum veículo cadastrado na frota.");
                    } else {
                        for (Veiculo v : frota) {
                            if (v instanceof Caminhao) {
                                Caminhao cam = (Caminhao) v;
                                System.out.printf("Veículo: Caminhão | Placa: %s | Modelo: %s | Eixos: %d | Frete Simulado: R$ %.2f%n",
                                        v.getPlaca(), v.getModelo(), cam.getEixos(), v.calcularCustoFrete(100));
                            } else {
                                System.out.printf("Veículo: Van | Placa: %s | Modelo: %s | Frete Simulado: R$ %.2f%n",
                                        v.getPlaca(), v.getModelo(), v.calcularCustoFrete(100));
                            }
                        }
                    }
                    break;
                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
        leitor.close();
    }
}
