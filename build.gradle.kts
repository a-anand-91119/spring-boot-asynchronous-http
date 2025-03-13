plugins {
	java
	alias(libs.plugins.springframework.boot)
	alias(libs.plugins.spring.dependency.management)
	alias(libs.plugins.spotless)
}

group = "dev.notyouraverage.project.one.http"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(23)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(libs.spring.boot.actuator)
	implementation(libs.spring.boot.web)
	implementation(libs.spring.kafka)
	implementation(libs.springdoc.webmvc.ui)

	implementation(libs.opentelemetry.exporter.otlp)
	implementation(libs.micrometer.tracing.bridge.otel)

	compileOnly(libs.lombok)
	developmentOnly(libs.spring.boot.compose)
	developmentOnly(libs.spring.boot.devtools)
	annotationProcessor(libs.lombok)

	testImplementation(libs.spring.boot.test)
	testImplementation(libs.spring.kafka.test)
	testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.withType<Test> {
	useJUnitPlatform()
}

spotless {
	java {
		removeUnusedImports()
		eclipse("4.29").configFile("spotless.xml")
		trimTrailingWhitespace()
		endWithNewline()
	}
}