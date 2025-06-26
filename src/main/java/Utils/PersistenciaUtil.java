package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class PersistenciaUtil {
    
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapterUtil())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimerUtil())
            .create();

    public static <T> void salvarEmArquivo(List<T> lista, String nomeArquivo) {
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            gson.toJson(lista, writer);
            System.out.println("Quantidade de itens a salvar em " + nomeArquivo + ": " + lista.size());
            System.out.println("Dados salvos em: " + nomeArquivo);
        } catch (Exception e) {
            System.out.println("Erro ao salvar " + nomeArquivo + ": " + e.getMessage());
        }
    }

    public static <T> List<T> carregarDeArquivo(String nomeArquivo, Type tipoLista) {
        try (Reader reader = new FileReader(nomeArquivo)) {
            return gson.fromJson(reader, tipoLista);
        } catch (Exception e) {
            System.out.println("Erro ao carregar " + nomeArquivo + ": " + e.getMessage());
            return null;
        }
    }
}
