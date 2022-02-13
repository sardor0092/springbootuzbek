package net.idrok.idrok.Service;

import net.idrok.idrok.entity.FileStoregeStatus;
import net.idrok.idrok.entity.FileStorge;
import net.idrok.idrok.repository.FileStorgeRepository;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class FileStoregeService {

    @Value("${upload.folder}")
    private String uploadFolder;
    @Autowired
    FileStorgeRepository fileStorgeRepository;

    private final Hashids hashids;

    public FileStoregeService(FileStorgeRepository fileStorgeRepository) {
        this.hashids = new Hashids(getClass().getName(), 6);
    }


    public void save(MultipartFile multipartFile) {
        FileStorge fileStorge = new FileStorge();
        fileStorge.setName(multipartFile.getOriginalFilename());
        fileStorge.setExtension(multipartFile.getOriginalFilename());
        fileStorge.setFileSize(multipartFile.getSize());
        fileStorge.setContentType(multipartFile.getContentType());
        fileStorgeRepository.save(fileStorge);
        Date now = new Date();
        File uploadFolder = new File(String.format("%s/upload_files/%d/%d/%d", this.uploadFolder, 1900 + now.getYear(), 1 + now.getMonth(), now.getDate()));
        if (!uploadFolder.exists() && uploadFolder.mkdirs()) {
            System.out.println("Aytilgan papkalar yaratildi: ");
        }
        fileStorge.setHashId(hashids.encode(fileStorge.getId()));
        fileStorge.setUploadPath(String.format("upload_files/%d/%d/%d/%s.%s"
                , 1900 + now.getYear(), 1 + now.getMonth(),
                now.getDate(), fileStorge.getHashId(),
                fileStorge.getExtension()));
        fileStorgeRepository.save(fileStorge);
        uploadFolder = uploadFolder.getAbsoluteFile();
        File file = new File(uploadFolder, String.format("%s.%s", fileStorge.getHashId(),
                fileStorge.getExtension()));
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Transactional(readOnly = true)
    public FileStorge findByHashId(String hashId) {
        return fileStorgeRepository.findByHashId(hashId);
    }

    public void delete(String hashId) {
        FileStorge fileStorge = findByHashId(hashId);
        File file = new File(String.format("%s/%s", this.uploadFolder, fileStorge.getUploadPath()));
        if (file.delete()) {

            fileStorgeRepository.delete(fileStorge);

        }
    }

    @Scheduled(cron = "0 0 0 ")
    public void deleteAllDreft() {
        List<FileStorge> fileStorgeList = fileStorgeRepository.findAllByFileStorageStatus(FileStoregeStatus.DRAFT);
        for (FileStorge fileStorge : fileStorgeList) {
            delete(fileStorge.getHashId());
        }


    }


    private String getExt(String fileName) {
        String ext = null;
        if (fileName != null && fileName.isEmpty()) {
            int dut = fileName.lastIndexOf('.');
            if (dut > 0 && dut <= fileName.length() - 2) {
                ext = fileName.substring(dut + 1);
                {

                }

            }
        }
        return ext;
    }


}
