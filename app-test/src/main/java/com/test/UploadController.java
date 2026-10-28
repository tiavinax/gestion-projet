package com.test;

import mg.itu.framework.Controller;
import mg.itu.framework.ModelView;
import mg.itu.framework.RequestMapping;
import mg.itu.framework.MapFile;
import com.test.model.User;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

@Controller
public class UploadController {
    
    // Dossier de stockage des fichiers
    private static final String UPLOAD_DIR = "/home/tiavina-anjaranomena/tomcat/tomcat/webapps/app-test/uploads";
    
    // ============================================
    // Formulaire d'upload
    // ============================================
    @RequestMapping(value = "/sprint8", method = "GET")
    public ModelView form() {
        ModelView mv = new ModelView("sprint8_form");
        mv.addAttribute("serverTime", new Date());
        return mv;
    }
    
    // ============================================
    // Upload d'un seul fichier (paramètres simples)
    // ============================================
    @RequestMapping(value = "/sprint8/upload-single", method = "POST")
    public ModelView uploadSingle(String nom, String email, MapFile fichier) {
        System.out.println("=== Upload Single ===");
        System.out.println("Nom: " + nom);
        System.out.println("Email: " + email);
        System.out.println("Fichier: " + fichier);
        
        String savedPath = null;
        if (fichier != null) {
            savedPath = saveFile(fichier);
        }
        
        ModelView mv = new ModelView("sprint8_result");
        mv.addAttribute("mode", "single");
        mv.addAttribute("nom", nom);
        mv.addAttribute("email", email);
        mv.addAttribute("fichier", fichier);
        mv.addAttribute("savedPath", savedPath);
        mv.addAttribute("serverTime", new Date());
        return mv;
    }
    
    // ============================================
    // Upload multiple (MapFile[])
    // ============================================
    @RequestMapping(value = "/sprint8/upload-multiple", method = "POST")
    public ModelView uploadMultiple(MapFile[] fichiers) {
        System.out.println("=== Upload Multiple ===");
        
        StringBuilder savedPaths = new StringBuilder();
        if (fichiers != null) {
            for (MapFile f : fichiers) {
                String path = saveFile(f);
                savedPaths.append(path).append(";");
                System.out.println("  Fichier: " + f);
            }
        }
        
        ModelView mv = new ModelView("sprint8_result");
        mv.addAttribute("mode", "multiple");
        mv.addAttribute("fichiers", fichiers);
        mv.addAttribute("savedPaths", savedPaths.toString());
        mv.addAttribute("serverTime", new Date());
        return mv;
    }
    
    // ============================================
    // Upload via objet User (binding object + MapFile)
    // ============================================
    @RequestMapping(value = "/sprint8/upload-object", method = "POST")
    public ModelView uploadObject(User user) {
        System.out.println("=== Upload Object ===");
        System.out.println("User: " + user.getName() + ", " + user.getEmail());
        System.out.println("Fichier: " + user.getFichier());
        
        String savedPath = null;
        if (user.getFichier() != null) {
            savedPath = saveFile(user.getFichier());
        }
        
        ModelView mv = new ModelView("sprint8_result");
        mv.addAttribute("mode", "object");
        mv.addAttribute("user", user);
        mv.addAttribute("savedPath", savedPath);
        mv.addAttribute("serverTime", new Date());
        return mv;
    }
    
    // ============================================
    // Sauvegarde physique du fichier
    // ============================================
    private String saveFile(MapFile fichier) {
        try {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            String uniqueName = System.currentTimeMillis() + "_" + fichier.getFileName();
            File targetFile = new File(dir, uniqueName);
            
            try (FileOutputStream fos = new FileOutputStream(targetFile)) {
                fos.write(fichier.getContenu());
            }
            
            System.out.println("Fichier sauvegarde: " + targetFile.getAbsolutePath());
            return targetFile.getAbsolutePath();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}