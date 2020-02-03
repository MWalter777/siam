
package fia.ues.edu.siam.Services.impl;

import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;
import fia.ues.edu.siam.Services.FileService;

@Service("fileServiceImpl")
public class FileServiceImpl implements FileService{

	private final static String DIR_FIlE = "files";
    private final static String DIR_IMGS="imgs";

    @Override
    public String subirFile(MultipartFile archivo, int dir) throws IOException{
        String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", "_");
        Path ruta=getPath(nombreArchivo, dir);
        if(ruta==null){
            return null;
        }
        System.out.println(nombreArchivo);
        System.out.println(ruta.toString());
        Files.copy(archivo.getInputStream(), ruta);
		return nombreArchivo;
	}

    @Override
    public boolean eliminarFile(String nombreFile, int dir) {
        if (nombreFile != null && nombreFile.length() > 0) {
            Path rutaLastFile=getPath(nombreFile, dir);
            if (rutaLastFile==null) {
                return false;
            } 
            File lastFile = rutaLastFile.toFile();
            if (lastFile.exists() && lastFile.canRead()) {//si exites y se puede leer 
                lastFile.delete();
                return true;
            }
        }

        return false;
    }

    @Override
    public Resource cargarFile(String nombreFile, int dir) throws MalformedURLException {
        Path rutaFile=getPath(nombreFile, dir);
        if(rutaFile==null){
            return null;
        }
        Resource recurso = new UrlResource(rutaFile.toUri());
        if (!recurso.exists() && !recurso.isReadable()) {//si no exites o no es leible
            rutaFile = Paths.get("src/main/resources/static/images").resolve("img.png").toAbsolutePath();
            recurso = new UrlResource(rutaFile.toUri());
        }
        return recurso;
        
    }

    @Override
    public Path getPath(String nombreFile, int dir) {
        if(dir==1){//imagenes
            return Paths.get(DIR_IMGS).resolve(nombreFile).toAbsolutePath();
        }else if(dir==2){//archivos
            return Paths.get(DIR_FIlE).resolve(nombreFile).toAbsolutePath();
        }else{
            return null;
        }
    }
}
