package pl.pozadr.ocrsystem.service;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Base64;
import java.util.Optional;

@Service
public class FileService {
    Logger logger = LoggerFactory.getLogger(FileService.class);
    private static final String UPLOAD_DIR = "./uploads/";
    private Path pathToImgFile;

    public boolean uploadFileFromUser(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            pathToImgFile = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), pathToImgFile, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (InvalidPathException ex){
            logger.error("Error: path converting. " + ex.getReason());
        } catch (IOException e) {
            logger.error("Error: copying file to local storage. ");
            e.printStackTrace();
        }
        return false;
    }

    public File getImageFile() {
        return pathToImgFile.toFile();
    }

    public void deleteImageFile() {
        pathToImgFile.toFile().delete();
    }

    public Optional<String> getImgBase64Format() {
        try {
            byte[] fileContent = FileUtils.readFileToByteArray(pathToImgFile.toFile());
            return Optional.of(Base64.getEncoder().encodeToString(fileContent));
        } catch (IOException e) {
            logger.error("Error: Base64 encoding. ");
            e.printStackTrace();
        }
        return Optional.empty();
    }





}
