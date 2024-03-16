import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    id("java")
    id("checkstyle")
    id("pmd")
    id("org.springframework.boot") version "3.2.2"
    id("org.openapi.generator") version "7.4.0"
}

group = "org.library"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_19
    targetCompatibility = JavaVersion.VERSION_19
}

repositories {
    mavenCentral()
}

dependencies {
    // rest assured
    implementation("io.rest-assured:rest-assured:5.4.0")

    // openAPI
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
    implementation("org.openapitools:openapi-generator-gradle-plugin:7.4.0")
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")

    // junit
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // lombok
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
}

sourceSets {
    main {
        java {
            srcDir("${layout.buildDirectory.get()}/generated/sources/swagger/src/main/java")
        }
    }
}

checkstyle {
    toolVersion = "10.14.0"
    config = resources.text.fromFile("$projectDir/src/main/resources/check_rules/checkstyle.xml")
}

pmd {
    toolVersion = "6.55.0"
    isConsoleOutput = true
    ruleSets = listOf("$projectDir/src/main/resources/check_rules/pmdrules.xml")
}

tasks.compileJava {
    options.encoding = "UTF-8"

    dependsOn(provider {
        tasks.filter { task -> task.name.endsWith("Api") && task.name.startsWith("generate") }
    })
}

tasks.register<GenerateTask>("generateLibraryControlSystemApi") {
    val openApiPackage = "org.library"
    val serviceName = "library_control_system"

    inputSpec.set("$projectDir/src/main/resources/swagger/$serviceName/swagger.json")
    outputDir.set("${layout.buildDirectory.get()}/generated/swagger")
    generateApiTests.set(false)
    skipValidateSpec.set(true)
    generateModelDocumentation.set(false)
    generateApiDocumentation.set(false)
    generatorName.set("java")
    apiPackage.set("$openApiPackage.api.$serviceName")
    invokerPackage.set("$openApiPackage.invoker.$serviceName")
    modelPackage.set("$openApiPackage.model.$serviceName")
    library.set("rest-assured")
    configOptions.set(
            mapOf(
                    "dateLibrary" to "java8",
                    "serializationLibrary" to "jackson"
            )
    )
}

tasks.test {
    useJUnitPlatform()
}