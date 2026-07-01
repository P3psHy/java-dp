package fr.etl_off;

public class EtlOffApp {
    public String buildMessage(String input) {
        return "ETL OFF ready: " + input;
    }

    public static void main(String[] args) {
        String name = args.length > 0 ? args[0] : "demo";
        System.out.println(new EtlOffApp().buildMessage(name));
    }
}
