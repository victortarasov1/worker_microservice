FROM debian:bullseye


# prepare system
RUN apt-get update -y && apt-get install -y openjdk-17-jdk wget zip jq


# Download and install Chrome for testing
RUN wget -q https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb && apt-get install -y ./google-chrome-stable_current_amd64.deb

# Download and install ChromeDriver for testing
RUN LATEST_DRIVER_VERSION=$(wget -qO - https://googlechromelabs.github.io/chrome-for-testing/last-known-good-versions.json | jq -r '.channels.Stable.version') \
    && wget https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/$LATEST_DRIVER_VERSION/linux64/chromedriver-linux64.zip \
    && unzip chromedriver-linux64.zip \
    && mv chromedriver-linux64/chromedriver /opt/chromedriver \
    && chmod +x /opt/chromedriver

# Download and install gradle
RUN LATEST_GRADLE_VERSION=$(wget -qO - https://services.gradle.org/versions/current | sed -n 's/.*"version" : "\([^"]*\)".*/\1/p') \
    && wget "https://services.gradle.org/distributions/gradle-${LATEST_GRADLE_VERSION}-bin.zip" -O /tmp/gradle.zip \
    && unzip -d /opt /tmp/gradle.zip \
    && ln -s /opt/gradle-${LATEST_GRADLE_VERSION} /opt/gradle \
    && rm /tmp/gradle.zip


ENV PATH="/opt/gradle/bin:${PATH}"


WORKDIR /app


COPY . .

RUN gradle build
