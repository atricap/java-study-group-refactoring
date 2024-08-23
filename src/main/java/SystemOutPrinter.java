public class SystemOutPrinter implements Printer {

    @Override
    public void println(String s) {
        System.out.println(s);
    }

    @Override
    public void println() {
        System.out.println();
    }
}
