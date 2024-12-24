package com.tech.students.service;

import com.tech.students.config.FileStorageProperties;
import com.tech.students.exception.UploadFileException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {

    private final Path fileStorageLocation;

    public FileService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            throw new UploadFileException("Algo deu errado ao tentar criar a pasta", e);
        }
    }

    /**
     *  Este método salva um arquivo
     * @param file armazena o arquivo
     * A variável 'fileName' recebe apenas o nome do arquivo
     * O 'IF' verifica se o arquivo é válido
     * A variável 'targetLocation' verifica se o arquivo já existe ou não para dar sequência oa resto do fluxo
     * O método 'copy' da classe File usa o inputStream para pegar o tod0 o conteudo do arquivo, depois, usa o 'targetLocation'
     * para concatenar o diretório com o nome do arquivo e, depois disso, é usado REPLACE_EXISTING para caso já exista um arquivo
     * com o mesmo nome do que está sendo salvo, irá substituir o conteudo do arquivo já existente
     * @return retorna o nome do arquivo
     */
    public String saveFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new UploadFileException("Invalid file");
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException e) {
            throw new UploadFileException("Erro ao tentar salvar o arquivo");
        }
    }

    public String getContentType(HttpServletRequest request, Resource resource) {
        String contentType =  null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch (IOException e) {
            throw new RuntimeException("Não foi possível determinar o tipo de arquivo");
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return contentType;
    }
}
