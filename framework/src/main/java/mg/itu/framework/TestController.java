package mg.itu.framework;

@Controller("test")
public class TestController {
    // Ce contrôleur sera automatiquement détecté
    public String execute() {
        return "test_view";
    }
}