public class TestingPrinter implements Printer {

    public StringBuffer sb = new StringBuffer();

    @Override
    public void println(String s) {
        sb.append(s).append('\n');
    }

    @Override
    public void println() {
        sb.append('\n');
    }
}
