import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectDataReloaderFastTest {

    @Test
    public void characterize_static_and_live_reloaders_1_5() throws InterruptedException {
        TestingPrinter out = new TestingPrinter();

        ProjectDataReloaderTest.run_static_and_live_reloaders(1, 5, out);

        assertEquals("Starting project data reloading thread for project \"project1\", type: STATIC\nStarting reloading for project project1\nDone reloading for project project1\nLoading login statistics for project project1\n(Talking to our login server via http request)\nProject: project1; type: STATIC\nneverUpdated\nneverUpdated\nneverUpdated\n\nStarting project data reloading thread for project \"project2\", type: LIVE\nStarting reloading for project project2\nLoading project details for project project2\n(Talking to database and updating our project-related objects.)\nLoading last update time for project project2\n(Checking the database to see when the data was last refreshed)\nDone reloading for project project2\nProject: project2; type: LIVE\nProject details created: Fri Aug 23 16:00:00 CEST 2024\nProject update time calculated: Fri Aug 23 16:00:00 CEST 2024\nneverUpdated\n\nLoading login statistics for project project2\n(Talking to our login server via http request)\nStopping project persistence reloading thread for project \"project1\"...\nStopping project persistence reloading thread for project \"project2\"...\n", out.sb.toString());
    }

    @Test
    public void characterize_static_reloader_1_5() throws InterruptedException {
        TestingPrinter out = new TestingPrinter();

        ProjectDataReloaderTest.run_static_reloader(5, out);

        assertEquals("Starting project data reloading thread for project \"project1\", type: STATIC\nStarting reloading for project project1\nDone reloading for project project1\nProject: project1; type: STATIC\nneverUpdated\nneverUpdated\nneverUpdated\n\nLoading login statistics for project project1\n(Talking to our login server via http request)\nStopping project persistence reloading thread for project \"project1\"...\n", out.sb.toString());
    }

    @Test
    public void characterize_live_reloader_1_5() throws InterruptedException {
        TestingPrinter out = new TestingPrinter();

        ProjectDataReloaderTest.run_live_reloader(5, out);

        assertEquals("Starting project data reloading thread for project \"project2\", type: LIVE\nStarting reloading for project project2\nDone reloading for project project2\nProject: project2; type: LIVE\nneverUpdated\nneverUpdated\nneverUpdated\n\nLoading project details for project project2\n(Talking to database and updating our project-related objects.)\nLoading login statistics for project project2\n(Talking to our login server via http request)\nLoading last update time for project project2\n(Checking the database to see when the data was last refreshed)\nStopping project persistence reloading thread for project \"project2\"...\n", out.sb.toString());
    }

}
