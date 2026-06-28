package mg.itu.framework;

@Controller("test")
public class TestController {
    
    @RequestMapping("/test/hello")
    public String hello() {
        System.out.println("Méthode hello() appelée");
        return "mappings";
    }
    
    @RequestMapping("/test/info")
    public String info() {
        System.out.println("Méthode info() appelée");
        return "mappings";
    }
}