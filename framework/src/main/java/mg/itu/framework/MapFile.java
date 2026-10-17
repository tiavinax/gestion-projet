package mg.itu.framework;

public class MapFile {
    
    private String fileName;
    private String extension;
    private byte[] contenu;
    
    public MapFile() {
    }
    
    public MapFile(String fileName, String extension, byte[] contenu) {
        this.fileName = fileName;
        this.extension = extension;
        this.contenu = contenu;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public String getExtension() {
        return extension;
    }
    
    public void setExtension(String extension) {
        this.extension = extension;
    }
    
    public byte[] getContenu() {
        return contenu;
    }
    
    public void setContenu(byte[] contenu) {
        this.contenu = contenu;
    }
    
    public long getSize() {
        return contenu != null ? contenu.length : 0;
    }
    
    public String getSizeFormatted() {
        long size = getSize();
        if (size < 1024) return size + " B";
        if (size < 1024 * 1024) return String.format("%.2f KB", size / 1024.0);
        return String.format("%.2f MB", size / (1024.0 * 1024.0));
    }
    
    @Override
    public String toString() {
        return "MapFile{" +
                "fileName='" + fileName + '\'' +
                ", extension='" + extension + '\'' +
                ", size=" + getSize() +
                '}';
    }
}