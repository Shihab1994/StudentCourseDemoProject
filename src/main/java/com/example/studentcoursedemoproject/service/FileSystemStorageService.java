package com.example.studentcoursedemoproject.service;


import com.example.studentcoursedemoproject.config.StorageProperties;
import com.example.studentcoursedemoproject.exception.StorageException;
import com.example.studentcoursedemoproject.exception.StorageFileNotFoundException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;


@Service
public class FileSystemStorageService implements StorageService {

    private final Path fileDir;
    private final Path profileImageDir;
    private String  fileUrl;
    private final String host;
    private final Integer port;
    private final String username;
    private final String password;



    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.fileDir = Paths.get(properties.getFilesDir());
        this.profileImageDir = Paths.get(properties.getFilesDir() + "/" + properties.getProfileImageDir());
        this.host=properties.getHost();
        this.port=properties.getPort();
        this.username=properties.getUsername();
        this.password=properties.getPassword();
    }

    @Override
    public void store(String fileName, MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store an empty file.");
            }

            uploadFile(file,this.fileDir.toString());

        }catch (Exception e) {
            throw new StorageException("Failed to store file.", e);
        }

    }

    @Override
    public String getFileUrl() {
        return fileUrl;
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.fileDir, 1)
                    .filter(path -> !path.equals(this.fileDir))
                    .map(this.fileDir::relativize);
        }catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return fileDir.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        }catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(fileDir.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(profileImageDir);
        }catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }


    protected void uploadFile(MultipartFile file, String uploadPath) {

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(host, port);
            boolean isLogin = ftpClient.login(username, password);
            if (!isLogin) {
                System.out.println("Could not login to the server");
            }
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-");
            String date = dateFormat.format(new Date());
            fileUrl = date + file.getOriginalFilename().replace(' ', '-');

            // APPROACH #2: uploads second file using an OutputStream
            File dir = new File(uploadPath + "/");

            //Create Directory
            if (!dir.exists())
                isLogin = ftpClient.makeDirectory(uploadPath);
            if (isLogin) {
                System.out.println("Successfully created directory: " + uploadPath);
            } else {
                System.out.println("Failed to create directory. See server's reply.");
            }

            File serverFile = new File(dir + File.separator + fileUrl);
            InputStream inputStream = file.getInputStream();

            System.out.println("Start uploading file");
            OutputStream outputStream = ftpClient.storeFileStream(String.valueOf(serverFile));
            byte[] bytesIn = new byte[4096];
            int read = 0;

            while ((read = inputStream.read(bytesIn)) != -1) {
                outputStream.write(bytesIn, 0, read);
            }
            inputStream.close();
            outputStream.close();

            boolean completed = ftpClient.completePendingCommand();
            if (completed) {
                System.out.println("The file is uploaded successfully.");
            }

        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }


}
