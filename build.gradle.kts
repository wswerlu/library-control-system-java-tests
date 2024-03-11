plugins {
    id("java")
    id("checkstyle")
    id("pmd")
}

group = "org.library"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

checkstyle {
    toolVersion = "10.14.0"
    config = resources.text.fromFile("${project.projectDir}/src/main/resources/check_rules/checkstyle.xml")
}

pmd {
    toolVersion = "6.55.0"
    isConsoleOutput = true
    ruleSets = listOf("${project.projectDir}/src/main/resources/check_rules/pmdrules.xml")
}

tasks.test {
    useJUnitPlatform()
}