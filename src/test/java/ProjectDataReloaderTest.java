import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.ZonedDateTime;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectDataReloaderTest {

    private final Clock clock;
    {
        ZonedDateTime time = ZonedDateTime.parse("2024-08-23T14:00:00+00:00[UTC]");
        clock = Clock.fixed(time.toInstant(), time.getZone());
    }

    @Test
    public void characterize_static_and_live_reloaders() throws InterruptedException {
        TestingPrinter out = new TestingPrinter();

        ProjectDataReloader reloader1 = ProjectDataReloader.getReloaderForType(
                new Project("project1", ProjectType.STATIC, out),
                out, clock);

        ProjectDataReloader reloader2 = ProjectDataReloader.getReloaderForType(
                new Project("project2", ProjectType.LIVE, out),
                out, clock);

        reloader1.start();
        SECONDS.sleep(1);

        reloader2.start();
        SECONDS.sleep(180);

        reloader1.stop();
        reloader2.stop();

        assertEquals("Starting project data reloading thread for project \"project1\", type: STATIC\nStarting reloading for project project1\nDone reloading for project project1\nProject: project1; type: STATIC\nneverUpdated\nneverUpdated\nneverUpdated\n\nLoading login statistics for project project1\n(Talking to our login server via http request)\nStarting project data reloading thread for project \"project2\", type: LIVE\nStarting reloading for project project2\nLoading project details for project project2\n(Talking to database and updating our project-related objects.)\nLoading last update time for project project2\n(Checking the database to see when the data was last refreshed)\nDone reloading for project project2\nProject: project2; type: LIVE\nProject details created: Fri Aug 23 16:00:00 CEST 2024\nneverUpdated\nneverUpdated\n\nLoading login statistics for project project2\n(Talking to our login server via http request)\nStarting reloading for project project1\nDone reloading for project project1\nProject: project1; type: STATIC\nneverUpdated\nneverUpdated\nLogin statistics looked up: Fri Aug 23 16:00:00 CEST 2024\n\nStarting reloading for project project2\nDone reloading for project project2\nProject: project2; type: LIVE\nProject details created: Fri Aug 23 16:00:00 CEST 2024\nProject update time calculated: Fri Aug 23 16:00:00 CEST 2024\nLogin statistics looked up: Fri Aug 23 16:00:00 CEST 2024\n\nLoading last update time for project project2\n(Checking the database to see when the data was last refreshed)\nStarting reloading for project project1\nDone reloading for project project1\nProject: project1; type: STATIC\nneverUpdated\nneverUpdated\nLogin statistics looked up: Fri Aug 23 16:00:00 CEST 2024\n\nLoading login statistics for project project1\n(Talking to our login server via http request)\nStarting reloading for project project2\nDone reloading for project project2\nProject: project2; type: LIVE\nProject details created: Fri Aug 23 16:00:00 CEST 2024\nProject update time calculated: Fri Aug 23 16:00:00 CEST 2024\nLogin statistics looked up: Fri Aug 23 16:00:00 CEST 2024\n\nLoading project details for project project2\n(Talking to database and updating our project-related objects.)\nLoading last update time for project project2\n(Checking the database to see when the data was last refreshed)\nStarting reloading for project project1\nDone reloading for project project1\nProject: project1; type: STATIC\nneverUpdated\nneverUpdated\nLogin statistics looked up: Fri Aug 23 16:00:00 CEST 2024\n\nStarting reloading for project project2\nDone reloading for project project2\nProject: project2; type: LIVE\nProject details created: Fri Aug 23 16:00:00 CEST 2024\nProject update time calculated: Fri Aug 23 16:00:00 CEST 2024\nLogin statistics looked up: Fri Aug 23 16:00:00 CEST 2024\n\nLoading last update time for project project2\n(Checking the database to see when the data was last refreshed)\nStarting reloading for project project1\nDone reloading for project project1\nProject: project1; type: STATIC\nneverUpdated\nneverUpdated\nLogin statistics looked up: Fri Aug 23 16:00:00 CEST 2024\n\nLoading login statistics for project project1\n(Talking to our login server via http request)\nStarting reloading for project project2\nDone reloading for project project2\nProject: project2; type: LIVE\nProject details created: Fri Aug 23 16:00:00 CEST 2024\nProject update time calculated: Fri Aug 23 16:00:00 CEST 2024\nLogin statistics looked up: Fri Aug 23 16:00:00 CEST 2024\n\nLoading project details for project project2\n(Talking to database and updating our project-related objects.)\nLoading last update time for project project2\n(Checking the database to see when the data was last refreshed)\nStarting reloading for project project1\nDone reloading for project project1\nProject: project1; type: STATIC\nneverUpdated\nneverUpdated\nLogin statistics looked up: Fri Aug 23 16:00:00 CEST 2024\n\nStarting reloading for project project2\nDone reloading for project project2\nProject: project2; type: LIVE\nProject details created: Fri Aug 23 16:00:00 CEST 2024\nProject update time calculated: Fri Aug 23 16:00:00 CEST 2024\nLogin statistics looked up: Fri Aug 23 16:00:00 CEST 2024\n\nLoading last update time for project project2\n(Checking the database to see when the data was last refreshed)\nStopping project persistence reloading thread for project \"project1\"...\nStopping project persistence reloading thread for project \"project2\"...\n", out.sb.toString());
    }

    @Test
    public void characterize_static_reloader() throws InterruptedException {
        TestingPrinter out = new TestingPrinter();

        ProjectDataReloader reloader1 = ProjectDataReloader.getReloaderForType(
                new Project("project1", ProjectType.STATIC, out),
                out, clock);

        reloader1.start();
        SECONDS.sleep(180);

        reloader1.stop();

        assertEquals("Starting project data reloading thread for project \"project1\", type: STATIC\nStarting reloading for project project1\nDone reloading for project project1\nProject: project1; type: STATIC\nneverUpdated\nneverUpdated\nneverUpdated\n\nLoading login statistics for project project1\n(Talking to our login server via http request)\nStarting reloading for project project1\nDone reloading for project project1\nProject: project1; type: STATIC\nneverUpdated\nneverUpdated\nLogin statistics looked up: Fri Aug 23 16:00:00 CEST 2024\n\nStarting reloading for project project1\nDone reloading for project project1\nProject: project1; type: STATIC\nneverUpdated\nneverUpdated\nLogin statistics looked up: Fri Aug 23 16:00:00 CEST 2024\n\nLoading login statistics for project project1\n(Talking to our login server via http request)\nStarting reloading for project project1\nDone reloading for project project1\nProject: project1; type: STATIC\nneverUpdated\nneverUpdated\nLogin statistics looked up: Fri Aug 23 16:00:00 CEST 2024\n\nStarting reloading for project project1\nDone reloading for project project1\nProject: project1; type: STATIC\nneverUpdated\nneverUpdated\nLogin statistics looked up: Fri Aug 23 16:00:00 CEST 2024\n\nLoading login statistics for project project1\n(Talking to our login server via http request)\nStarting reloading for project project1\nDone reloading for project project1\nProject: project1; type: STATIC\nneverUpdated\nneverUpdated\nLogin statistics looked up: Fri Aug 23 16:00:00 CEST 2024\n\nStopping project persistence reloading thread for project \"project1\"...\n", out.sb.toString());
    }

    @Test
    public void characterize_live_reloader() throws InterruptedException {
        TestingPrinter out = new TestingPrinter();

        ProjectDataReloader reloader2 = ProjectDataReloader.getReloaderForType(
                new Project("project2", ProjectType.LIVE, out),
                out, clock);

        reloader2.start();
        SECONDS.sleep(180);

        reloader2.stop();

        assertEquals("Starting project data reloading thread for project \"project2\", type: LIVE\nStarting reloading for project project2\nLoading project details for project project2\n(Talking to database and updating our project-related objects.)\nLoading last update time for project project2\n(Checking the database to see when the data was last refreshed)\nDone reloading for project project2\nLoading login statistics for project project2\n(Talking to our login server via http request)\nProject: project2; type: LIVE\nneverUpdated\nneverUpdated\nneverUpdated\n\nStarting reloading for project project2\nDone reloading for project project2\nProject: project2; type: LIVE\nProject details created: Fri Aug 23 16:00:00 CEST 2024\nProject update time calculated: Fri Aug 23 16:00:00 CEST 2024\nLogin statistics looked up: Fri Aug 23 16:00:00 CEST 2024\n\nLoading last update time for project project2\n(Checking the database to see when the data was last refreshed)\nStarting reloading for project project2\nDone reloading for project project2\nProject: project2; type: LIVE\nProject details created: Fri Aug 23 16:00:00 CEST 2024\nProject update time calculated: Fri Aug 23 16:00:00 CEST 2024\nLogin statistics looked up: Fri Aug 23 16:00:00 CEST 2024\n\nLoading project details for project project2\n(Talking to database and updating our project-related objects.)\nLoading last update time for project project2\n(Checking the database to see when the data was last refreshed)\nStarting reloading for project project2\nDone reloading for project project2\nProject: project2; type: LIVE\nProject details created: Fri Aug 23 16:00:00 CEST 2024\nProject update time calculated: Fri Aug 23 16:00:00 CEST 2024\nLogin statistics looked up: Fri Aug 23 16:00:00 CEST 2024\n\nLoading last update time for project project2\n(Checking the database to see when the data was last refreshed)\nStarting reloading for project project2\nDone reloading for project project2\nProject: project2; type: LIVE\nProject details created: Fri Aug 23 16:00:00 CEST 2024\nProject update time calculated: Fri Aug 23 16:00:00 CEST 2024\nLogin statistics looked up: Fri Aug 23 16:00:00 CEST 2024\n\nLoading project details for project project2\n(Talking to database and updating our project-related objects.)\nLoading last update time for project project2\n(Checking the database to see when the data was last refreshed)\nStarting reloading for project project2\nDone reloading for project project2\nProject: project2; type: LIVE\nProject details created: Fri Aug 23 16:00:00 CEST 2024\nProject update time calculated: Fri Aug 23 16:00:00 CEST 2024\nLogin statistics looked up: Fri Aug 23 16:00:00 CEST 2024\n\nLoading last update time for project project2\n(Checking the database to see when the data was last refreshed)\nStopping project persistence reloading thread for project \"project2\"...\n", out.sb.toString());
    }
}
