import java.util.Date;


public class LastUpdateTimeReloader extends DataReloader {

    protected LastUpdateTimeReloader(Project project, PeriodicReloadPolicy reloadPolicy) {
        super(project, reloadPolicy);
    }

    @Override
    protected void loadData() {
        System.out.println("Loading last update time for project " + project.getName());
        System.out.println("(Checking the database to see when the data was last refreshed)");
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
        project.setLastUpdateTime("Project update time calculated: " + new Date(System.currentTimeMillis()));
    }

}
