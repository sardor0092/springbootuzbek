package net.idrok.idrok.Controller;

import net.idrok.idrok.Service.FileStoregeService;
import net.idrok.idrok.entity.FileStorge;
import net.idrok.idrok.repository.FileStorgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api")
public class FileStoregeController {

    @Autowired
    FileStoregeService fileStoregeService;
    @Autowired
    FileStorgeRepository fileStorgeRepository;

    public FileStoregeController(FileStoregeService fileStorageService) {
        this.fileStoregeService = fileStorageService;
    }


    @Value("${upload.folder}")
    private String uploadFolder;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile multipartFile) {
        fileStoregeService.save(multipartFile);
        return multipartFile.getOriginalFilename() + "file saqlandi";
    }

    @GetMapping("/preview/{hashId}")
    public ResponseEntity previewFile(@PathVariable String hashId) throws IOException {

        FileStorge fileStorage = fileStoregeService.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; fileName=\"" + URLEncoder.encode(fileStorage.getName()))
                .contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .contentLength(fileStorage.getFileSize())
                .body(new FileUrlResource(String.format("%s/%s",uploadFolder,  fileStorage.getUploadPath())));
    }





    @GetMapping("/download/{hashId}")
    public ResponseEntity downloadFile(@PathVariable String hashId) throws IOException {

        FileStorge fileStorage = fileStoregeService.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + URLEncoder.encode(fileStorage.getName()))
                .contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .contentLength(fileStorage.getFileSize())
                .body(new FileUrlResource(String.format("%s/%s",uploadFolder,  fileStorage.getUploadPath())));
    }

         @DeleteMapping("/delete/{hashId}")
    public FileStorge delete(@PathVariable String hashId) {
        fileStoregeService.delete(hashId);
        return fileStoregeService.findByHashId("fayl o'chirildi");


         }







}
