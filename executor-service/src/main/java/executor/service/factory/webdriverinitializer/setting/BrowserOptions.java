package executor.service.factory.webdriverinitializer.setting;

public enum BrowserOptions {
    DISABLE_SANDBOX("--no-sandbox");

    private final String option;

    BrowserOptions(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }
}
