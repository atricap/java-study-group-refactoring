
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
}
