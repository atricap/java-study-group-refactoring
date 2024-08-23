
import java.time.Clock;
import java.util.Date;


/**
 * A class concerned with reloading a server-side cache of data, to avoid unnecessary 
 * and expensive calls outside the system (i.e. to persistence, to login server, etc).  
 * This is NOT good code, but it's realistic code... in that I found it in one of my 
 * systems.  We'll refactor it and make it better.
 */
public abstract class ProjectDataReloader {
    
    private static final long DEFAULT_RELOAD_MILLIS = 30000;
    private static final long DEFAULT_SLEEPING_MILLIS = 1000;

    private long reloadMillis;
    private long sleepingMillis;
    
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
    protected int reloadsCounter = 0;

    protected Printer out;
    protected Clock clock;

    public static ProjectDataReloader getReloaderForType(Project project) {
        return getReloaderForType(project, new SystemOutPrinter(), Clock.systemUTC());
    }

    public static ProjectDataReloader getReloaderForType(Project project, Printer out, Clock clock) {

        ProjectType type = project.getType();
        if (type.equals(ProjectType.STATIC)) {
            return new StaticProjectDataReloader(project, out, clock);
        } else if (type.equals(ProjectType.LIVE)) {
            return new LiveProjectDataReloader(project, out, clock);
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

    /**
     * Called once per reload period
     */
    protected abstract void reloadProjectData();

    public void start() {
        reloaderThread = new Thread(this::run);

        reloaderThread.start();
    }

    public void stop() {

        out.println(String.format("Stopping project persistence reloading thread for project \"%s\"...", project.getName()));

        stopped = true;
    }

    private void run() {
        out.println(String.format("Starting project data reloading thread for project \"%s\", type: %s",
                project.getName(), project.getType()));

        while (!stopped) {
            boolean shouldBrake = runSingle();
            if (shouldBrake) break;
        }

        out.println("Stopped project persistence reloading thread for project \"" + project.getName() + "\"");
    }

    private boolean runSingle() {
        long startTime = clock.millis();

        boolean shouldBrake = tryReloadProjectData();
        if (shouldBrake) return true;

        long timeUsedForLastReload = clock.millis() - startTime;

        sleepUntilNextFetch(timeUsedForLastReload);
        reloadsCounter++;
        return false;
    }

    private boolean tryReloadProjectData() {
        try {
            // call a project-type-specific reloading procedure that reloads some of the project data from
            // persistence
            out.println("Starting reloading for project " + project.getName());
            reloadProjectData();
            out.println("Done reloading for project " + project.getName());
            out.println(project.getPretty());
            out.println();

            // check the termination flag
            synchronized (ProjectDataReloader.this) {
                if (stopped) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.err.println("Could not load project data for ptoject " + project.getName() + " : "
                    + e.getMessage());
        }
        return false;
    }

    private void sleepUntilNextFetch(long timeUsedForLastReload) {
        if (timeUsedForLastReload >= reloadMillis) {
            return;
        }

        long timeLeftToSleep = reloadMillis - timeUsedForLastReload;

        while (timeLeftToSleep > 0) {

            synchronized (ProjectDataReloader.this) {
                if (stopped) {
                    break;
                }
            }

            try {
                Thread.sleep(sleepingMillis);
            } catch (InterruptedException ex) {
                continue;
            }

            timeLeftToSleep -= sleepingMillis;
        }
    }

    protected void loadProjectDetails() {
        out.println("Loading project details for project " + project.getName());
        out.println("(Talking to database and updating our project-related objects.)");
        //this could be a lot of lines of code and involve collaborators, helpers, etc
        //...
        //...
        //...
        //...
        //...
        //...
        //...
        //... Talk to database,
        //... Build domain objects,
        //... Update stuff
        //...
        //...
        //...
        //...
        //...
        //... Clear previously cached data
        //...
        //... Cache fresh data
        project.setProjectDetails("Project details created: " + new Date(clock.millis()));
    }

    protected void loadLastUpdateTime() {
        out.println("Loading last update time for project " + project.getName());
        out.println("(Checking the database to see when the data was last refreshed)");
        // this might also be a lot of lines of code
        //...
        //...
        //...
        //...
        //...
        //...
        //... Look at database
        //...
        //...
        //...
        //...
        //... Clear previously cached data
        //...
        //... Cache fresh data
        project.setLastUpdateTime("Project update time calculated: " + new Date(clock.millis()));
    }

    protected void loadLoginStatistics() {
        out.println("Loading login statistics for project " + project.getName());
        out.println("(Talking to our login server via http request)");
        // This might involve other collaborators/helpers to make the http request and
        // handle the response.
        //...
        //...
        //...
        //...
        //...
        //... Talk to login server
        //...
        //...
        //...
        //... Clear previously cached data
        //...
        //... Cache fresh data
        project.setLoginStatistics("Login statistics looked up: " + new Date(clock.millis()));
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
