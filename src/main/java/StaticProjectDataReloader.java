
import java.time.Clock;

/**
 * A reloader for projects that are no longer "live."  Project details and data
 * are not expected to change, so only login statistics are required to be kept
 * fresh.
 *
 */
public class StaticProjectDataReloader extends ProjectDataReloader {

    protected StaticProjectDataReloader(Project project) {
        super(project);
    }

    protected StaticProjectDataReloader(Project project, Printer out, Clock clock) {
        super(project, out, clock);
    }

    protected StaticProjectDataReloader(Project project, Printer out, Clock clock, long reloadMillis, long sleepingMillis) {
        super(project, out, clock, reloadMillis, sleepingMillis);
    }

    @Override
    protected void reloadProjectData() {
        
        // load details every other reload attempt
        if (reloadsCounter % 2 == 0) {
            new Thread( new Runnable() {
                
                @Override
                public void run() {
                    loadLoginStatistics();
                    
                }
            }).start();
        }
        
    }

}
