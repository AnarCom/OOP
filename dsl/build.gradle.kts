import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.0"
	kotlin("jvm") version "1.6.21"
}

group = "org.nsu"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation(kotlin("script-runtime"))
	implementation("commons-io:commons-io:2.11.0")
	testImplementation ("org.junit.jupiter:junit-jupiter-api:5.8.2")
	testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.8.2")
	testImplementation("io.mockk:mockk:1.12.4")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
