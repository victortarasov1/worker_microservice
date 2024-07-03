package executor.service.factory.setting;

public enum BrowserOptions {
    DISABLE_SANDBOX("--no-sandbox"),
    HEADLESS("--headless"),
    DISABLE_DEV_SHM_USAGE("--disable-dev-shm-usage");

    private final String option;

    BrowserOptions(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }
}
