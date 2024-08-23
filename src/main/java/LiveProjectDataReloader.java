
import java.time.Clock;

/**
 * A reloader for a "live" project, where project data is expected to change
 * frequently.  Periodic reloads are necessary for time of last update, 
 * project details, and login statistics.
 */
public class LiveProjectDataReloader extends ProjectDataReloader {

    protected LiveProjectDataReloader(Project project) {
        super(project);
    }

    protected LiveProjectDataReloader(Project project, Printer out, Clock clock) {
        super(project, out, clock);
    }

    protected LiveProjectDataReloader(Project project, Printer out, Clock clock, long reloadMillis, long sleepingMillis) {
        super(project, out, clock, reloadMillis, sleepingMillis);
    }

    @Override
    protected void reloadProjectData() {
        // load details every other reload attempt
        if (reloadsCounter % 2 == 0) {
            new Thread(this::loadProjectDetails).start();
        }
        
        //do this often
        new Thread(this::loadLastUpdateTime).start();
        
        // don't need this very often..
        // load login statistics every five hundred reload attempts
        if (reloadsCounter % 500 == 0) {
            new Thread(this::loadLoginStatistics).start();
        }
    }

}
