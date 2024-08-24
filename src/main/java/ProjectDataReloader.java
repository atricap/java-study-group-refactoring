
import java.time.Clock;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.MILLISECONDS;


/**
 * A class concerned with reloading a server-side cache of data, to avoid unnecessary 
 * and expensive calls outside the system (i.e. to persistence, to login server, etc).  
 * This is NOT good code, but it's realistic code... in that I found it in one of my 
 * systems.  We'll refactor it and make it better.
 */
public class ProjectDataReloader {
    
    private static final long DEFAULT_RELOAD_MILLIS = 30000;
    private static final long DEFAULT_SLEEPING_MILLIS = 1000;

    private final long reloadMillis;
    private final long sleepingMillis;

    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    private boolean stopped = false;

    /**
     * The per-project reloading thread
     */
    private Thread reloaderThread;
    
    protected final Project project;
    
    /**
     * Counts how many times the reloadProjectData() method has been called,
     * used for reloading data types more or less often
     */
    protected final AtomicInteger reloadsCounter = new AtomicInteger(0);

    protected Printer out;
    protected Clock clock;

    public static ProjectDataReloader getReloaderForType(Project project) {
        return getReloaderForType(project, new SystemOutPrinter(), Clock.systemUTC());
    }

    public static ProjectDataReloader getReloaderForType(Project project, Printer out, Clock clock) {

        ProjectType type = project.getType();
        if (type.equals(ProjectType.STATIC)) {
            return new ProjectDataReloader(project, out, clock);
        } else if (type.equals(ProjectType.LIVE)) {
            return new ProjectDataReloader(project, out, clock);
        }
        return null;
    }

    protected ProjectDataReloader(Project project) {
        this(project, new SystemOutPrinter(), Clock.systemUTC());
    }

    public ProjectDataReloader(Project project, Printer out, Clock clock) {
        this(project, out, clock, DEFAULT_RELOAD_MILLIS, DEFAULT_SLEEPING_MILLIS);
    }

    public ProjectDataReloader(Project project, Printer out, Clock clock, long reloadMillis, long sleepingMillis) {
        this.project = project;
        this.out = out;
        this.clock = clock;
        this.reloadMillis = reloadMillis;
        this.sleepingMillis = sleepingMillis;
    }

    public void start() {
        out.println(String.format("Starting project data reloading thread for project \"%s\", type: %s",
                project.getName(), project.getType()));

        executor.scheduleAtFixedRate(this::tryReloadProjectData, 0, reloadMillis, MILLISECONDS);
    }

    public void stop() {

        out.println(String.format("Stopping project persistence reloading thread for project \"%s\"...", project.getName()));

        executor.shutdown();

        out.println("Stopped project persistence reloading thread for project \"" + project.getName() + "\"");
    }

    private void tryReloadProjectData() {
        try {
            // call a project-type-specific reloading procedure that reloads some of the project data from
            // persistence
            out.println("Starting reloading for project " + project.getName());
            project.reloadProjectData(reloadsCounter.get(), out, clock);
            out.println("Done reloading for project " + project.getName());
            out.println(project.getPretty());
            out.println();

        } catch (Exception e) {
            System.err.println("Could not load project data for ptoject " + project.getName() + " : "
                    + e.getMessage());
        }

        reloadsCounter.incrementAndGet();
    }

    public static void main(String[] args) {
        ProjectDataReloader reloader1 = getReloaderForType(new Project("project1", ProjectType.STATIC));
        
        ProjectDataReloader reloader2 = getReloaderForType(new Project("project2", ProjectType.LIVE));
        
        reloader1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            
        }
        reloader2.start();
        
        try {
            Thread.sleep(180000);
        } catch (InterruptedException e) {
        }
        
        reloader1.stop(); reloader2.stop();
    }
}
