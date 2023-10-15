FROM debian:bullseye

RUN apt-get update -y

RUN apt-get install -y openjdk-17-jdk

RUN apt-get install -y wget zip

RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN apt-get install -y ./google-chrome-stable_current_amd64.deb


RUN LATEST_VERSION=$(wget -qO - https://chromedriver.storage.googleapis.com/LATEST_RELEASE) \
    && wget https://chromedriver.storage.googleapis.com/$LATEST_VERSION/chromedriver_linux64.zip \
    && unzip chromedriver_linux64.zip \
    && mv chromedriver /opt/chromedriver \
    && chmod +x /opt/chromedriver

RUN LATEST_GRADLE_VERSION=$(wget -qO - https://services.gradle.org/versions/current | sed -n 's/.*"version" : "\([^"]*\)".*/\1/p') \
    && wget "https://services.gradle.org/distributions/gradle-${LATEST_GRADLE_VERSION}-bin.zip" -O /tmp/gradle.zip \
    && unzip -d /opt /tmp/gradle.zip \
    && ln -s /opt/gradle-${LATEST_GRADLE_VERSION} /opt/gradle \
    && rm /tmp/gradle.zip


ENV PATH="/opt/gradle/bin:${PATH}"


WORKDIR /app


COPY . .

RUN gradle build --no-daemon --exclude-task test
