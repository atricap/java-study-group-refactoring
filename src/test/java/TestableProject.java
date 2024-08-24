
import java.time.Clock;

public class TestableProject extends Project {
    public TestableProject(String name, ProjectType type, Printer out) {
        super(name, type, out);
    }

    @Override
    protected void startToLoadProjectDetails(Printer out1, Clock clock1) {
        // disable heavy dependencies
    }

    @Override
    protected void startToLoadLastUpdateTime(Printer out1, Clock clock1) {
        // disable heavy dependencies
    }

    @Override
    protected void startToLoadLoginStatistics(Printer out1, Clock clock1) {
        // disable heavy dependencies
    }
}
