package ua.kiev.unicyb.diploma.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@Slf4j
public class ZipService {
    public void unzip(InputStream inputStream, String outputFolder) throws IOException {

        byte[] buffer = new byte[1024];

        //get the zip file content
        ZipInputStream zis =
                new ZipInputStream(inputStream);
        //get the zipped file list entry
        ZipEntry ze = zis.getNextEntry();

        while (ze != null) {

            String fileName = ze.getName();

            String path =  outputFolder + "/" + fileName;
            File newFile = new File(path);

            if (fileName.endsWith("/")) {
                newFile.mkdirs();
            }

            File file = new File(path.endsWith("/") ? path.substring(0, path.length() - 2) : path);
            log.debug("Unzipped file: {}", newFile.getName());
            FileOutputStream fos = new FileOutputStream(file);

            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }

            fos.close();
            ze = zis.getNextEntry();
        }

        zis.closeEntry();
        zis.close();
    }
}
