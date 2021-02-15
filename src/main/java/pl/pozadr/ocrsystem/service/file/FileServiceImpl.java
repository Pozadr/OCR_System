package pl.pozadr.ocrsystem.service.file;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.pozadr.ocrsystem.config.AppConstants;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Base64;
import java.util.Optional;

/**
 * Processes the data uploaded to the application.
 */
@Service
public class FileServiceImpl implements FileService {
    Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    private Path pathToImgFile;

    /**
     * Uploads user given file to the application.
     * @param file - given file from frontend.
     * @return - status uploaded/not uploaded.
     */
    @Override
    public boolean uploadFileFromUser(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            pathToImgFile = Paths.get(AppConstants.UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), pathToImgFile, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (InvalidPathException ex) {
            logger.error("Error: path converting. " + ex.getReason());
        } catch (IOException e) {
            logger.error("Error: copying file to local storage. ");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @return - File from given path.
     */
    @Override
    public File getImageFile() {
        return pathToImgFile.toFile();
    }

    /**
     * Deletes the file used in application after processing.
     */
    @Override
    public void deleteImageFile() {
        pathToImgFile.toFile().delete();
    }

    /**
     * Encodes image file to Base-64 format.
     * Thymeleaf requires Base-64 format to display the uploaded image.
     * @return - Base64-encoded image.
     */
    @Override
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
