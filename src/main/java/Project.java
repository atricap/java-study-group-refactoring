
import java.time.Clock;
import java.util.Date;

public class Project {
    
    private final String name;
    private final ProjectType type;
    private String projectDetails = "neverUpdated";
    private String lastUpdateTime = "neverUpdated";
    private String loginStatistics = "neverUpdated";
    private Printer out;

    public Project(String name, ProjectType type) {
        this(name, type, new SystemOutPrinter());
    }

    public Project(String name, ProjectType type, Printer out) {
        this.name = name;
        this.type = type;
        this.out = out;
    }

    public String getName() {
        return  name;
    }

    public ProjectType getType() {
        return type;
    }

    public String getProjectDetails() {
        return projectDetails;
    }

    public void setProjectDetails(String projectDetails) {
        this.projectDetails = projectDetails;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String string) {
        this.lastUpdateTime = string;
    }

    public String getLoginStatistics() {
        return loginStatistics;
    }

    public void setLoginStatistics(String loginStatistics) {
        this.loginStatistics = loginStatistics;
    }

    public String getPretty() {
        StringBuilder sb = new StringBuilder();
        sb.append("Project: " + getName() + "; type: " + getType());
        sb.append(System.lineSeparator());
        sb.append(getProjectDetails());
        sb.append(System.lineSeparator());
        sb.append(getLastUpdateTime());
        sb.append(System.lineSeparator());
        sb.append(getLoginStatistics());
        return sb.toString();
    }

    /**
     * Called once per reload period
     */
    protected void reloadProjectData(int reloadsCounter1, Printer out1, Clock clock1) {
        switch (getType()) {
            case LIVE:
                // load details every other reload attempt
                if (reloadsCounter1 % 2 == 0) {
                    startToLoadProjectDetails(out1, clock1);
                }

                //do this often
                startToLoadLastUpdateTime(out1, clock1);

                // don't need this very often..
                // load login statistics every five hundred reload attempts
                if (reloadsCounter1 % 500 == 0) {
                    startToLoadLoginStatistics(out1, clock1);
                }

                break;
            case STATIC:
                // load details every other reload attempt
                if (reloadsCounter1 % 2 == 0) {
                    startToLoadLoginStatistics(out1, clock1);
                }

                break;
            default:
                throw new IllegalStateException("Illegal project type or null");
        }
    }

    protected void startToLoadProjectDetails(Printer out1, Clock clock1) {
        new Thread(() -> loadProjectDetails(out1, clock1)).start();
    }

    protected void startToLoadLastUpdateTime(Printer out1, Clock clock1) {
        new Thread(() -> loadLastUpdateTime(out1, clock1)).start();
    }

    protected void startToLoadLoginStatistics(Printer out1, Clock clock1) {
        new Thread(() -> loadLoginStatistics(out1, clock1)).start();
    }

    protected void loadProjectDetails(Printer out1, Clock clock1) {
        out1.println("Loading project details for project " + getName());
        out1.println("(Talking to database and updating our project-related objects.)");
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
        setProjectDetails("Project details created: " + new Date(clock1.millis()));
    }

    protected void loadLastUpdateTime(Printer out1, Clock clock1) {
        out1.println("Loading last update time for project " + getName());
        out1.println("(Checking the database to see when the data was last refreshed)");
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
        setLastUpdateTime("Project update time calculated: " + new Date(clock1.millis()));
    }

    protected void loadLoginStatistics(Printer out1, Clock clock1) {
        out1.println("Loading login statistics for project " + getName());
        out1.println("(Talking to our login server via http request)");
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
        setLoginStatistics("Login statistics looked up: " + new Date(clock1.millis()));
    }
}
