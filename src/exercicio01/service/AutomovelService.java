package exercicio01.service;

import exercicio01.model.Automovel;

import java.io.*;
import java.util.*;

public class AutomovelService {
    private List<Automovel> automoveis;
    private final String arquivo = "automoveis.txt";

    public AutomovelService() {
        automoveis = new ArrayList<>();
        carregar();
    }

    public boolean inserir(Automovel a) {
        if (buscarPorPlaca(a.getPlaca()) != null) return false;
        automoveis.add(a);
        return true;
    }

    public boolean remover(String placa) {
        Automovel a = buscarPorPlaca(placa);
        if (a != null) {
            automoveis.remove(a);
            return true;
        }
        return false;
    }

    public boolean alterar(String placa, Automovel novosDados) {
        Automovel a = buscarPorPlaca(placa);
        if (a != null) {
            a.setModelo(novosDados.getModelo());
            a.setMarca(novosDados.getMarca());
            a.setAno(novosDados.getAno());
            a.setValor(novosDados.getValor());
            return true;
        }
        return false;
    }

    public Automovel buscarPorPlaca(String placa) {
        for (Automovel a : automoveis) {
            if (a.getPlaca().equalsIgnoreCase(placa)) return a;
        }
        return null;
    }

    public List<Automovel> listar(String criterio) {
        List<Automovel> copia = new ArrayList<>(automoveis);
        switch (criterio.toLowerCase()) {
            case "placa" -> copia.sort(Comparator.comparing(Automovel::getPlaca));
            case "modelo" -> copia.sort(Comparator.comparing(Automovel::getModelo));
            case "marca" -> copia.sort(Comparator.comparing(Automovel::getMarca));
        }
        return copia;
    }

    public void salvar() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            for (Automovel a : automoveis) {
                bw.write(a.toCSV());
                bw.newLine();
            }
            System.out.println("Dados salvos com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    private void carregar() {
        File f = new File(arquivo);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Automovel a = Automovel.fromCSV(linha);
                if (a != null) automoveis.add(a);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar: " + e.getMessage());
        }
    }
}
