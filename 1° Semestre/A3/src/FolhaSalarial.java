

import java.util.Scanner;
import java.util.ArrayList;

class Funcionario {
    String nome;
    int matricula;
    double vendas, percentual;
    double produzida, valorPeca;
    TipoFuncionario tipo;

    public double calcularComissao() {
        return vendas * (percentual / 100);
    }

    public double calcularBonus() {
        return produzida * valorPeca;
    }

    public double calcularSalario(double salarioBase) {
        if (tipo == TipoFuncionario.COMISSIONADO) {
            return salarioBase + calcularComissao();
        } else if (tipo == TipoFuncionario.PRODUCAO) {
            return salarioBase + calcularBonus();
        }
        return salarioBase;
    }
}

enum TipoFuncionario {
    PADRAO, COMISSIONADO, PRODUCAO
}

public class FolhaSalarial {

    public static Funcionario cadastrar(Scanner sc, ArrayList<Funcionario> funcionarios) {
        Funcionario p = new Funcionario();

        System.out.println("Nome: ");
        p.nome = sc.nextLine();

        System.out.println("Matricula: ");
        p.matricula = sc.nextInt();

        while (p.matricula <=0 || verificarMatricula(funcionarios, p.matricula)) {
        	if (p.matricula <=0) {
        		System.out.println("Matricula 0 ou negativa inválida! Digite novamente a matricula: ");
        		p.matricula = sc.nextInt();
        	}else {
            System.out.println("Erro! Matricula já existente! Digite novamente a matricula: ");
            p.matricula = sc.nextInt();}
        }

        sc.nextLine();
        return p;
    }

    public static boolean verificarMatricula(ArrayList<Funcionario> funcionarios, int matricula) {
        for (Funcionario f : funcionarios) {
            if (f.matricula == matricula) {
                return true;
            }
        }
        return false;
    }
    
    	 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Funcionario> funcionarios = new ArrayList<>();

        int escolha;
        final double SALARIO_BASE = 2000;
        Funcionario p;

        System.out.println("Bem vindo! O que deseja fazer? ");

        do {
            System.out.println(
                "1) Cadastrar Funcionário Padrão.\n" +
                "2) Cadastrar Funcionário Comissionado.\n" +
                "3) Cadastrar Funcionário de Produção.\n" +
                "4) Mostrar folha de pagamento.\n" +
                "0) Encerrar."
            );

            escolha = sc.nextInt();
            sc.nextLine();

            switch (escolha) {
                case 1:
                    p = cadastrar(sc, funcionarios);
                    p.tipo = TipoFuncionario.PADRAO;
                    funcionarios.add(p);
                    break;

                case 2:
                    p = cadastrar(sc, funcionarios);
                    p.tipo = TipoFuncionario.COMISSIONADO;

                    System.out.println("Qual o valor das vendas? ");
                    p.vendas = sc.nextDouble();
                    while (p.vendas < 0) {
                        System.out.println("Valores negativos inválidos! Digite novamente: ");
                        p.vendas = sc.nextDouble();
                    }

                    System.out.println("Qual a comissão? ");
                    p.percentual = sc.nextDouble();
                    while (p.percentual < 0 || p.percentual > 100) {
                        System.out.println("Percentual inválido! Digite entre 0 e 100: ");
                        p.percentual = sc.nextDouble();
                       
                    }

                    
                    funcionarios.add(p);
                    break;

                case 3:
                    p = cadastrar(sc, funcionarios);
                    p.tipo = TipoFuncionario.PRODUCAO;

                    System.out.println("Quantas peças foram produzidas? ");
                    p.produzida = sc.nextDouble();
                    while (p.produzida < 0) {
                        System.out.println("Valores negativos inválidos! Digite novamente: ");
                        p.produzida = sc.nextDouble();
                    }

                    System.out.println("Qual o valor das peças? ");
                    p.valorPeca = sc.nextDouble();
                    while (p.valorPeca < 0) {
                        System.out.println("Valores negativos inválidos! Digite novamente: ");
                        p.valorPeca = sc.nextDouble();
                        
                       
                    }

                    
                    funcionarios.add(p);
                    break;

                case 4:
                    for (Funcionario funcionario : funcionarios) {
                        double salario = funcionario.calcularSalario(SALARIO_BASE);

                        System.out.println("Funcionário: " + funcionario.nome);
                        System.out.println("Matricula: " + funcionario.matricula);

                        if (funcionario.tipo == TipoFuncionario.COMISSIONADO) {
                            double comissao = funcionario.calcularComissao();
                            System.out.println("Função: Comissionado");
                            System.out.printf("Comissao: R$ %.2f\n" , comissao);
                        } else if (funcionario.tipo == TipoFuncionario.PRODUCAO) {
                            double bonus = funcionario.calcularBonus();
                            System.out.println("Função: Produção");
                            System.out.printf("Bônus de produtividade: R$ %.2f\n" , bonus);
                        } else {
                            System.out.println("Função: Padrão");
                        }

                        System.out.printf("Salário: R$ %.2f\n", salario);
                        System.out.println("------------------");
                    }
                    break;

                case 0:
                    System.out.println("Programa Encerrado");
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (escolha != 0);

        sc.close();
    }
}
