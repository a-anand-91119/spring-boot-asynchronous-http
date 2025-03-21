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

// Add a task for pre-downloading dependencies (used in Dockerfile for layer caching)
tasks.register("downloadDependencies") {
	description = "Download all dependencies to the Gradle cache"
	doLast {
		configurations.compileClasspath.get().files
		configurations.runtimeClasspath.get().files
		configurations.testCompileClasspath.get().files
		configurations.testRuntimeClasspath.get().files
	}
}

// Configure build tasks
tasks.withType<Test> {
	useJUnitPlatform()
	// Increase parallel test execution
	maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
}

// Configure Java compilation for speed
tasks.withType<JavaCompile> {
	options.compilerArgs.add("-Xlint:none")
	options.isFork = true
	options.isIncremental = true
}

spotless {
	java {
		removeUnusedImports()
		eclipse("4.29").configFile("spotless.xml")
		trimTrailingWhitespace()
		endWithNewline()
	}
}