package pl.pozadr.ocrsystem.service.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;

public interface FileService {
    boolean uploadFileFromUser(MultipartFile file);
    File getImageFile();
    void deleteImageFile();
    Optional<String> getImgBase64Format();

}
