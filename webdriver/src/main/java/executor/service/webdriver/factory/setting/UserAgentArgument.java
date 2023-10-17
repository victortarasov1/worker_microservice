package executor.service.webdriver.factory.setting;

public enum UserAgentArgument {
    CHROME("--user-agent=");
    private final String argument;

    UserAgentArgument(String argument) {
        this.argument = argument;
    }
    public String getArgument() {
        return argument;
    }
}
